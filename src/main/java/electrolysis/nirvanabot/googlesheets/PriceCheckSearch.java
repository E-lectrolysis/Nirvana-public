package electrolysis.nirvanabot.googlesheets;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import electrolysis.nirvanabot.googlesheets.Sheet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriceCheckSearch extends Sheet {
    private static final String APPLICATION_NAME = "PC Search";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();



    /**
     *price checks an item
     */
    public String[] pcItem(String item) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        boolean filled = false;
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1C4N7xMq9boNuYVQB-a9LtM6MHIlaYFO4OvW9BQdXIX8";
        final String range = "Non-Mythics!A2:H";
        final String mythicsRange = "Mythics!A2:H";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        ValueRange responseMythics = service.spreadsheets().values()
                .get(spreadsheetId, mythicsRange)
                .execute();
        List<List<Object>> values = response.getValues();
        List<List<Object>> valuesMythics = responseMythics.getValues();

        /*
        0 item
        1 type
        2 tier
        3 price range
        4 stats
        5 Primary stats
        6 secondary stats
        7 demand
        8 notes
         */

        String[] checkedItem = new String[9];

        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (List row : values) {
                if (item.equalsIgnoreCase((String) row.get(0))) {
                    for (int i = 0; i < 9; i++) {
                        try {
                            checkedItem[i] = (String) row.get(i);
                        } catch (Exception e) {
                            checkedItem[i] = "none";
                        }
                    }
                    filled = true;
                    break;
                }

            }
             if (!filled) {
                 for (List mythicRow : valuesMythics) {
                     if (item.equalsIgnoreCase((String) mythicRow.get(0))) {
                         for (int i = 0; i < 9; i++) {
                             try {
                                 checkedItem[i] = (String) mythicRow.get(i);
                             } catch (Exception e) {
                                 checkedItem[i] = "none";
                             }
                         }
                         filled = true;
                         break;
                     }

                 }
            }
        }

        if(filled) {
            return checkedItem;
        } else {
            return null;
        }
    }

    /**
     * Lists items that can be price checked
     * @param type mythic or non-mythic
     * @return a list of items that can be price checked
     * @throws Exception cuz it sucks
     */
    public ArrayList<String> listItems(String type) throws Exception{
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "REDACTED";
        final String range = type + "!A2:A";
        ArrayList<String> listOfItems = new ArrayList<String>();
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        for (List row : values) {
            listOfItems.add((String)row.get(0));
        }
        return listOfItems;
    }
}