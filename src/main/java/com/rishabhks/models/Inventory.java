package com.rishabhks.models;

import org.json.simple.JSONObject;

import java.util.HashMap;

public class Inventory extends HashMap<String, Integer> {
    private static Integer MAX_QUANTITY_POSSIBLE = 1000;
    private static Integer MIN_QUANTITY_REQUIRED = 10;

    public boolean checkIngredientsAvailability(String orderItem, Inventory requiredIngredients) {
        for (Entry<String,Integer> entry : requiredIngredients.entrySet()) {
            String ingredientName = entry.getKey();
            Integer requiredQty = entry.getValue();

            boolean canHoldIngredient = this.canHoldIngredient(ingredientName);

            if (!canHoldIngredient) {
                System.out.println(orderItem + " cannot be prepared because " + ingredientName + " is not available");
                return false;
            } else if (this.checkShortage(ingredientName, requiredQty)) {
                System.out.println(orderItem + " cannot be prepared because " + ingredientName + " is not sufficient");
                return false;
            }
        }
        return true;
    }

    public boolean monitor() {
        boolean hasSufficientIngredients = true;
        for (Entry<String,Integer> entry : this.entrySet()) {
            String ingredientName = entry.getKey();
            Integer quantity = entry.getValue();

            String quantityStr = "Sufficient";

            if (quantity <= MIN_QUANTITY_REQUIRED) {
                quantityStr = "Low";
                hasSufficientIngredients = false;
            }
            System.out.println(ingredientName + " : " + quantityStr);
        }

        return hasSufficientIngredients;
    }

    public boolean refillIngredient(String ingredientName, Integer quantity) {
        if (quantity < 0) {
            return false;
        }

        if (!this.containsKey(ingredientName)) {
            return false;
        }

        Integer currentQty = this.get(ingredientName);
        Integer newQty = Math.min(currentQty + quantity, MAX_QUANTITY_POSSIBLE);

        this.put(ingredientName, newQty);
        return true;
    }

    public boolean requestIngredients(String orderItem, Inventory requiredIngredients) {
        for (Entry<String,Integer> entry : requiredIngredients.entrySet()) {
            String ingredientName = entry.getKey();
            Integer requiredQty = entry.getValue();

            boolean ableToFetchIngredient = this.fetchIngredient(ingredientName, requiredQty);

            if (!ableToFetchIngredient) {
                return false;
            }
        }
        return true;
    }

    private boolean canHoldIngredient(String ingredientName) {
        return this.containsKey(ingredientName);
    }

    private boolean ingredientEmpty(String ingredientName) {
        return !this.canHoldIngredient(ingredientName) || this.get(ingredientName) <= 0;
    }

    private boolean checkShortage(String ingredientName, Integer requiredQty) {
        return this.ingredientEmpty(ingredientName) || this.get(ingredientName) < requiredQty;
    }

    private boolean fetchIngredient(String ingredientName, Integer requiredQty) {
        if (this.checkShortage(ingredientName, requiredQty)) {
            return  false;
        }

        Integer reducedQty = this.get(ingredientName) - requiredQty;
        this.put(ingredientName, reducedQty);
        return true;
    }

    public static Inventory fromJson(JSONObject jsonObject) {
        Inventory inventory = new Inventory();

        for (Object keyObj : jsonObject.keySet()) {
            String key = (String) keyObj;
            Long value = (Long) jsonObject.get(key);
            inventory.put(key, value.intValue());
        }

        return inventory;
    }
}
