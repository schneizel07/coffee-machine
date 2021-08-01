package com.rishabhks.models;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MenuTest {

    private Menu menu = new Menu();

    @Before
    public void setUp() {

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
    }

    @Test
    public void testFromJson() throws ParseException {
        String jsonString = "{ " +
                "\"hot_tea\": { \"hot_water\": 200, \"hot_milk\": 100, \"ginger_syrup\": 10, \"sugar_syrup\": 10, \"tea_leaves_syrup\": 30 }," +
                "\"hot_coffee\": { \"hot_water\": 100, \"ginger_syrup\": 30, \"hot_milk\": 400, \"sugar_syrup\": 50, \"tea_leaves_syrup\": 30 }," +
                "\"black_tea\": { \"hot_water\": 300, \"ginger_syrup\": 30, \"sugar_syrup\": 50, \"tea_leaves_syrup\": 30 }," +
                "\"green_tea\": { \"hot_water\": 100, \"ginger_syrup\": 30, \"sugar_syrup\": 50, \"green_mixture\": 30 }" +
                "}";
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);

        assertEquals(this.menu, Menu.fromJson(jsonObject));
    }
}