package com.rishabhks.models;

import org.json.simple.JSONObject;

import java.util.HashMap;

public class Menu extends HashMap<String, Inventory> {

    public boolean isKnownBeverage(String itemName) {
        return this.containsKey(itemName);
    }

    public Inventory getIngredientsDetails(String itemName) {
        if (this.containsKey(itemName)) {
            return this.get(itemName);
        }

        return null;
    }

    public static Menu fromJson(JSONObject jsonObject) {
        Menu menu = new Menu();

        for (Object keyObj : jsonObject.keySet()) {
            String key = (String) keyObj;

            JSONObject recipeJson = (JSONObject) jsonObject.get(key);
            menu.put(key, Inventory.fromJson(recipeJson));
        }

        return menu;
    }
}
