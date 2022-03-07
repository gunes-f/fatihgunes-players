package com.gunessoftware;

import java.util.HashMap;
import java.util.Map;

/**
 * Player implementation that is used to send messages from one player to another.
 * Contains a counter that counts which player has sent how many messages to this instance,
 * a reference to sending message queue, and a reference to reply queue, which is
 * used to send a reply to the sender. All instances of this class can run on a separate
 * thread.
 */
public class Player extends Thread {
    private final Map<Player, Integer> counter = new HashMap<>();
    private final ThreadSafeQueue sentMessageQueue;
    private final ThreadSafeQueue receivedMessageQueue;
    private final String messageString = "Original message from sender";
    private final PlayerRole role;

    public Player(ThreadSafeQueue sentMessageQueue, ThreadSafeQueue receivedMessageQueue, PlayerRole role) {
        this.sentMessageQueue = sentMessageQueue;
        this.receivedMessageQueue = receivedMessageQueue;
        this.role = role;
    }

    public void run() {
        if (role == PlayerRole.SENDER || role == PlayerRole.SENDER_RECEIVER) {
            int messageCount = 0;
            while(messageCount < 10) {
                Message message = new Message(messageString, this);
                System.out.println("Message sent: " + message.getMessage());
                sentMessageQueue.add(message);
                messageCount++;
            }
            sentMessageQueue.terminate();

            while (true) {
                Message message  = receivedMessageQueue.remove();
                if (message == null) {
                    break;
                }
                System.out.println("Reply Received: " + message.getMessage());

            }
        }

        if (role == PlayerRole.RECEIVER || role == PlayerRole.SENDER_RECEIVER) {
            while (true) {
                Message message  = sentMessageQueue.remove();
                if (message == null) {
                    receivedMessageQueue.terminate();
                    System.out.println("No more messages from sentMessageQueue, terminating...");
                    break;
                }

                Integer messageCount = counter.getOrDefault(message.getSender(), 0);
                counter.put(message.getSender(), ++messageCount);

                Message reply = new Message(message.getMessage() + " - " + messageCount, message.getSender());
                receivedMessageQueue.add(reply);

                System.out.println("Message Replied: " + reply.getMessage());
            }
        }
    }
}
