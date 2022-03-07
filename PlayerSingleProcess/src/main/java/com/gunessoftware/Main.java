package com.gunessoftware;

import java.util.LinkedList;
import java.util.Queue;

public class Main {

    public static void main(String[] args) {
        Queue<Message> sentMessageQueue = new LinkedList<>();
        Queue<Message> receivedMessageQueue = new LinkedList<>();

        Player initiator = new Player(sentMessageQueue, receivedMessageQueue);
        Player consumer = new Player(sentMessageQueue, receivedMessageQueue);

        for(int i = 0; i < 10; i++) {
            initiator.sendMessage();
            consumer.consumeSentMessage();
            initiator.getReply();
        }
    }
}
