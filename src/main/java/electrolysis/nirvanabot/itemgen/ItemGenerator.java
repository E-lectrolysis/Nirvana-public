package electrolysis.nirvanabot.itemgen;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ItemGenerator {

    public ItemGenerator() {

    }

    public String searchItem(String search) throws Exception {

        Gson gson = new Gson();

        String response = "";

        while (search.charAt(0) == ' ') {
            search = search.substring(1);
        }

        boolean exactExists = false; //checks for an exact match


        //code that gets a json and converts it into a wynn item thing
        URL itemSearch = new URL("https://api.wynncraft.com/public_api.php?action=itemDB&search=" + search); //wynnapi url
        URLConnection yc = itemSearch.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine;
        inputLine = in.readLine();
        ItemList itemList = gson.fromJson(inputLine, ItemList.class);
        Item currentItem = null;


        if (itemList.getItems().length > 1) { //in event of more than one matching item

            for (int i = 0; i < itemList.getItems().length; i++) {
                if (itemList.getItems()[i].getName().equalsIgnoreCase(search)) { //exact match checker
                    exactExists = true;
                    currentItem = itemList.getItems()[i];
                }
            }

            if (!exactExists) {

                response += "Matching Items: "; //displays matching items... unless list is too long
                for (int i = 0; i < itemList.getItems().length; i++) {
                    response += itemList.getItems()[i].getName();
                    if (i < itemList.getItems().length - 1) {
                        response += ", ";
                    }

                }

                if (response.length() > 2000) {
                    response = "A ton of items were found, specify your search more";
                }
            }
        } else if (itemList.getItems().length < 1) { //if nothing is found
            response = "No Items Found.";
        } else if (itemList.getItems().length == 1) { //one item
            exactExists = true;
            currentItem = itemList.getItems()[0];
        }

        //if an item is found do the thing lol
        if (exactExists && currentItem != null) {
            response = currentItem.generateItemThing();
        }


        return response;

    }
}
