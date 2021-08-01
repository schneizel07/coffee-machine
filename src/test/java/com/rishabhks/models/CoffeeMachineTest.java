package com.rishabhks.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CoffeeMachineTest {
    CoffeeMachine coffeeMachine;

    @Before
    public void setUp() {
        try {
            this.coffeeMachine = new CoffeeMachine(3, this.coffeeMachineInventoryInit(), this.coffeeMachineMenuInit());
            this.coffeeMachine.startMachine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Should return false if ingredients are consumed and less than min threshold
    @Test
    public void testMonitorWithInsufficientIngredients() throws InterruptedException {
        this.coffeeMachine.newOrder("hot_coffee");
        this.coffeeMachine.newOrder("black_tea");

        while (true) {
            if (this.coffeeMachine.pendingOrdersCount() <= 0) {
                break;
            }
        }

        Thread.sleep(20000);
        assertFalse(this.coffeeMachine.monitorInventory());
    }

    private Inventory coffeeMachineInventoryInit() {
        Inventory inventory = new Inventory();
        inventory.put("hot_water", 500);
        inventory.put("hot_milk", 500);
        inventory.put("ginger_syrup", 100);
        inventory.put("sugar_syrup", 100);
        inventory.put("tea_leaves_syrup", 100);

        return inventory;
    }

    private Menu coffeeMachineMenuInit() {
        Menu menu = new Menu();

        // Item 1
        Inventory item1Recipe = new Inventory();
        item1Recipe.put("hot_water", 200);
        item1Recipe.put("hot_milk", 100);
        item1Recipe.put("ginger_syrup", 10);
        item1Recipe.put("sugar_syrup", 10);
        item1Recipe.put("tea_leaves_syrup", 30);
        menu.put("hot_tea", item1Recipe);

        // Item 2
        Inventory item2Recipe = new Inventory();
        item2Recipe.put("hot_water", 100);
        item2Recipe.put("ginger_syrup", 30);
        item2Recipe.put("hot_milk", 400);
        item2Recipe.put("sugar_syrup", 50);
        item2Recipe.put("tea_leaves_syrup", 30);
        menu.put("hot_coffee", item2Recipe);

        // Item 3
        Inventory item3Recipe = new Inventory();
        item3Recipe.put("hot_water", 300);
        item3Recipe.put("ginger_syrup", 30);
        item3Recipe.put("sugar_syrup", 50);
        item3Recipe.put("tea_leaves_syrup", 30);
        menu.put("black_tea", item3Recipe);

        // Item 4
        Inventory item4Recipe = new Inventory();
        item4Recipe.put("hot_water", 100);
        item4Recipe.put("ginger_syrup", 30);
        item4Recipe.put("sugar_syrup", 50);
        item4Recipe.put("green_mixture", 30);
        menu.put("green_tea", item4Recipe);

        return menu;
    }

}