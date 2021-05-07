package electrolysis.nirvanabot.googlesheets;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuildSheetSearch extends Sheet {
    private static final String APPLICATION_NAME = "Build Archive Search";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * gets the builds from spreadsheet
     */
    public ArrayList<String[]> getStuff(String parameters, String type) throws IOException, GeneralSecurityException {

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "REDACTED";
        final String range = type +"!A2:H";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();

        parameters = parameters.toLowerCase();

        /*
        0 author
        1 class
        2 elements
        3 weapon
        4 playstyle
        5 build link
        6 notes
        7 last updated
         */
        String[] paramCompare = parameters.split(", ");


        ArrayList<String[]> builds = new ArrayList<String[]>();
        ArrayList<String[]> buildsShortened = new ArrayList<String[]>();

        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {

            //adds builds + reqs that can be checked
            for (List row : values) {
                // Print columns A and E, which correspond to indices 0 and 4.
                String[] buildThing = new String[4];
                String[] fullBuild = new String[8];
                for(int i = 0; i < row.size(); i++) {

                    fullBuild[i] = (String)row.get(i);
                    if(i >= 1 && i <= 4) {
                        buildThing[i-1] = ((String)(row.get(i))).toLowerCase();
                    }

                }
                buildsShortened.add(buildThing); //shortened build list
                builds.add(fullBuild); //build list
            }


            int leftShift = 0; //amt of times a thing has been deleted
            for(int i = 0; i < buildsShortened.size(); i++) {
                boolean meetsReqs = false;
                String[] currentBuild = buildsShortened.get(i);


                //checks if build meets all reqs
                //paramCompare is all the params, basically the reqs
                for (int j = 0; j < paramCompare.length; j++) {
                    meetsReqs = false;
                    for (int k = 0; k < currentBuild.length; k++) {

                        if(paramCompare[j].contains("e:") || paramCompare[j].contains("E:")) {
                            meetsReqs = checkElements(paramCompare[j], currentBuild[1]);

                        }else if(paramCompare[j].equalsIgnoreCase(currentBuild[k])) {
                            meetsReqs = true;
                        }

                    }
                    if(!meetsReqs) { //removes builds that don't meet req
                        builds.remove(i-leftShift);
                        leftShift++;
                        break;
                    }
                    //removes a build from list lol

                }
            }


        }

        return builds;
    }

    /**
     * checks elements
     * @param parameter parameters from user
     * @param params2 parameters from sheet
     * @return whether elements match
     */
    private boolean checkElements(String parameter, String params2) {
        String elements = parameter.substring(2);
        boolean elementsAreGood = false;
        if(parameter.contains("rainbow") && params2.contains("rainbow")) {
            return true;
        } else if(params2.contains("rainbow") && !parameter.contains("rainbow") && params2.length() > 8) {
            String[] temp = params2.split(" ");
            params2 = temp[1]; //checks the one element the rainbow focuses on idk

        } else if (!parameter.equals("rainbow") && params2.equals("rainbow")) {

            return false;
        }
            for (int i = 0; i < elements.length(); i++) {
                elementsAreGood = false;
                    if (params2.contains(Character.toString(elements.charAt(i)))) {
                        elementsAreGood = true;
                    } else {
                        break;
                    }
            }

        return elementsAreGood;

    }


}