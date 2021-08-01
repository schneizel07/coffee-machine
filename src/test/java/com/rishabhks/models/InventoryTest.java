package com.rishabhks.models;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InventoryTest {

    private Inventory inventory = new Inventory();

    @Before
    public void setUp() {
        inventory.put("hot_water", 500);
        inventory.put("hot_milk", 500);
        inventory.put("ginger_syrup", 100);
        inventory.put("sugar_syrup", 100);
        inventory.put("tea_leaves_syrup", 100);
    }

    // Test checkIngredientsAvailability with insufficient ingredient
    @Test
    public void testAvailabilityWithInsufficientIngredient() {
        Inventory testRecipe = new Inventory();
        testRecipe.put("hot_water", 100);
        testRecipe.put("ginger_syrup", 30);
        testRecipe.put("sugar_syrup", 50);
        testRecipe.put("tea_leaves_syrup", 110);

        assertFalse(this.inventory.checkIngredientsAvailability("test_item", testRecipe));
    }

    // Should return false if ingredients are available in sufficient quantity
    @Test
    public void testMonitorWithSufficientIngredients() {
        assertTrue(this.inventory.monitor());
    }

    @Test
    public void testFromJson() throws ParseException {
        String jsonString = "{\"hot_water\": 500, \"hot_milk\": 500, \"ginger_syrup\": 100, \"sugar_syrup\": 100, \"tea_leaves_syrup\": 100 }";
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);

        assertEquals(this.inventory, Inventory.fromJson(jsonObject));
    }
}