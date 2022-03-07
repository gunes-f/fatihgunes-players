package com.gunessoftware;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        ThreadSafeQueue sentMessageQueue = new ThreadSafeQueue();
        ThreadSafeQueue receivedMessageQueue = new ThreadSafeQueue();

        Player initiator = new Player(sentMessageQueue, receivedMessageQueue, PlayerRole.SENDER);
        Player consumer = new Player(sentMessageQueue, receivedMessageQueue, PlayerRole.RECEIVER);

        initiator.start();
        consumer.start();
    }
}
