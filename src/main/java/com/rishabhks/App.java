package com.rishabhks;

import com.rishabhks.models.CoffeeMachine;
import com.rishabhks.models.Inventory;
import com.rishabhks.models.Menu;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        String inputPath = "./input.json";

        try {
            // fileResponse would be of type { coffee_machine: <CoffeeMachine>, orders: <ListOfOrders> }
            Map<String, Object> fileResponse = parseInput(inputPath);
            CoffeeMachine coffeeMachine = (CoffeeMachine) fileResponse.get("cm");
            ArrayList<String> orders = (ArrayList<String>) fileResponse.get("orders");

            coffeeMachine.startMachine();
            for(String order: orders) {
                coffeeMachine.newOrder(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Object> parseInput(String filePath) throws Exception {
        FileReader fR = new FileReader(filePath);
        JSONParser jp = new JSONParser();

        JSONObject jsonObj =  (JSONObject) jp.parse(fR);
        JSONObject coffeeMachineConfig = (JSONObject) jsonObj.get("machine");

        JSONObject coffeeMachineOutlets = (JSONObject) coffeeMachineConfig.get("outlets");
        Long numOutletsLong = (Long) coffeeMachineOutlets.get("count_n");
        Integer numOutlets = numOutletsLong.intValue();

        JSONObject inventoryJSON = (JSONObject) coffeeMachineConfig.get("total_items_quantity");
        Inventory inventory = Inventory.fromJson(inventoryJSON);

        JSONObject menuJSON = (JSONObject) coffeeMachineConfig.get("beverages");
        Menu menu = Menu.fromJson(menuJSON);
        CoffeeMachine cm = new CoffeeMachine(numOutlets, inventory, menu);

        ArrayList<String> availableBeverages = new ArrayList<>();

        for (Object keyObj : menuJSON.keySet()) {
            String key = (String) keyObj;
            availableBeverages.add(key);
        }

        HashMap<String, Object> res = new HashMap<>();
        res.put("cm", cm);
        res.put("orders", availableBeverages);

        return res;
    }
}
