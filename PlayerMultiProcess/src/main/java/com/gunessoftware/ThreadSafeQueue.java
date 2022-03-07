package com.gunessoftware;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class serves a thread safe queue implementation, which means it can be used
 * safely with multiple processes. Contains a queue of messages and supports add and
 * remove functionalities.
 */
public class ThreadSafeQueue {
    private Queue<Message> queue = new LinkedList<>();
    private boolean isEmpty = true;
    private boolean isTerminate = false;
    private final int CAPACITY = 5;

    public synchronized void add(Message message) {
        while (queue.size() == CAPACITY) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        queue.add(message);
        isEmpty = false;
        notify();
    }

    public synchronized Message remove() {
        Message message = null;
        while (isEmpty && !isTerminate) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }

        if (queue.size() == 1) {
            isEmpty = true;
        }
        if (queue.size() == 0 && isTerminate) {
            return null;
        }

        message = queue.remove();
        if (queue.size() == CAPACITY - 1) {
            notifyAll();
        }
        return message;
    }

    public synchronized void terminate() {
        isTerminate = true;
        notifyAll();
    }
}
