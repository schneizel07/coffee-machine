package com.rishabhks.models;

import java.util.LinkedList;

public class OrdersQueue {
    // Maintaining a queue for all the orders that are coming in.
    private final LinkedList<String> queue = new LinkedList<>();
    public synchronized void addOrderToLast(String orderItem) {
        queue.addLast(orderItem);
        notify();
    }

    public synchronized String getNextOrder() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        return queue.removeFirst();
    }

    public int size() {
        return this.queue.size();
    }
}
