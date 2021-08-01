package com.rishabhks.models;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class CoffeeMachine implements Runnable {
    private final static int PREPARATION_TIME_IN_SECONDS = 10;
    private final Integer maxSlots;
    private final Inventory inventory;
    private final Menu menu;
    private final OrdersQueue ordersQueue = new OrdersQueue();
    private final HashMap<String, Thread> activeWorkersMap = new HashMap<>();

    public CoffeeMachine(Integer maxSlots, Inventory inventory, Menu menu) throws Exception {
        if (maxSlots < 1) {
            throw new Exception("Invalid slots count");
        }
        this.maxSlots = maxSlots;
        this.inventory = inventory;
        this.menu = menu;
    }

    // Return if we were able to refill the ingredient or not
    public boolean refillIngredient(String ingredientName, Integer quantity) {
        return this.inventory.refillIngredient(ingredientName, quantity);
    }

    // Checks and returns if all the ingredients are in sufficient quantity or not
    public boolean monitorInventory() {
        return this.inventory.monitor();
    }

    // Orders which are yet in queue
    public int pendingOrdersCount() {
        return this.ordersQueue.size();
    }

    public boolean newOrder(String orderItem) {
        boolean isKnownBeverage = this.menu.isKnownBeverage(orderItem);
        if (!isKnownBeverage) {
            return false;
        }
        ordersQueue.addOrderToLast(orderItem);
        return true;
    }

    private void serveOrder(String orderItem) throws Exception {
        Inventory requiredIngredients = this.menu.getIngredientsDetails(orderItem);
        if (requiredIngredients == null) {
            throw new Exception("Invalid Order Item");
        }

        boolean ingredientsAvailable = this.inventory.checkIngredientsAvailability(orderItem, requiredIngredients);

        if (!ingredientsAvailable) {
            return;
        }

        try {
            boolean gotAllIngredients = this.inventory.requestIngredients(orderItem, requiredIngredients);

            if (!gotAllIngredients) {
                return;
            }

            TimeUnit.SECONDS.sleep(PREPARATION_TIME_IN_SECONDS);
            System.out.println(orderItem + " served");
        } catch (InterruptedException e) {
            System.out.println(orderItem + " preparation interrupted");
            e.printStackTrace();
        }
    }

    public void startMachine() throws Exception {
        if (this.maxSlots < 1) {
            throw new Exception("Invalid slots count");
        }
        for (int i = 0; i < this.maxSlots; i++) {
            String workerName = Integer.toString(i);
            Thread worker = new Thread(this, workerName);
            this.activeWorkersMap.put(workerName, worker);
            worker.start();
        }
    }

    @Override
    public void run() {
        String workerName = Thread.currentThread().getName();
        boolean isValidWorker = this.activeWorkersMap.containsKey(workerName);
        if (!isValidWorker) return;

        try {
            while (true) {
                String orderItem = this.ordersQueue.getNextOrder();
                if (orderItem == null) return;

                this.serveOrder(orderItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
