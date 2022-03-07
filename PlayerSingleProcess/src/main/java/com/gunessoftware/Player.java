package com.gunessoftware;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * Player implementation that is used to send messages from one player to another.
 * Contains a counter that counts which player has sent how many messages to this instance,
 * a reference to sending message queue, and a reference to reply queue, which is
 * used to send a reply to the sender.
 */
public class Player {
    private final Map<Player, Integer> counter = new HashMap<>();
    private final Queue<Message> sentMessageQueue;
    private final Queue<Message> receivedMessageQueue;
    private final String messageString = "Original message from sender";

    public Player(Queue<Message> sentMessageQueue, Queue<Message> receivedMessageQueue) {
        this.sentMessageQueue = sentMessageQueue;
        this.receivedMessageQueue = receivedMessageQueue;
    }

    public void sendMessage() {
        Message message = new Message(messageString, this);
        sentMessageQueue.add(message);
        System.out.println("Message sent: " + message.getMessage());
    }

    public void consumeSentMessage() {
        Message message = sentMessageQueue.remove();

        Integer messageCount = counter.getOrDefault(message.getSender(), 0);
        counter.put(message.getSender(), ++messageCount);

        Message reply = new Message(message.getMessage() + " - " + messageCount, message.getSender());
        receivedMessageQueue.add(reply);
    }

    public void getReply() {
        Message message = receivedMessageQueue.remove();
        System.out.println("Reply: " + message.getMessage());
    }

}
