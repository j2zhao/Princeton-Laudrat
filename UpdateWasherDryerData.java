/*****************************************************************************
  * Princeton Laundrat - UpdateWasherDryerData
  *
  * Created: 11/14/15
  * Last Modified: 11/14/15
  * 
  * Compile: javac UpdateWasherDryerData.java
  * Execute: java UpdateWasherDryerData
  * 
  * Description:
  *     Reads webpage:
  *         http://classic.laundryview.com/laundry_room.php?view=c&lr=ROOM_ID
  *     Sends lastest washer/dryer availability data to Parse database:
  *         PrincetonLaundrat.
  ***************************************************************************/

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.IOException;

public class UpdateWasherDryerData {

    /************************************
    * Constants
    ************************************/

    // URL of the webpage from which data is retrieved
    private static String URL = "http://classic.laundryview.com/laundry_room.php?view=c&lr=";

    // Number of laundry rooms for which to get data
    private static int NUM_ROOMS = 0;

    // Index to represent washer & dryer
    private static int washerIndex = 0;
    private static int dryerIndex  = 1;


    // Returns the xth number found in a String
    private static int findXthNumber(String s, int x) {
        int num = -1;
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            num = Character.getNumericValue(s.charAt(i));
            if (num >= 0 && num <= 9) {
                count++;
            }
            if (count == x) {
                break;
            }
        }

        return num;
    }

    // Parses Laundryview website for availability data for washers/dryers in
    // room with ID roomID; index 1 is washers, index 2 is dryers
    private static int[] getAvailability(int roomID, int index) throws IOException {
        // Assembles url using the room ID
        String url = URL + roomID;

        // Filters to look for in the html document
        String tag    = "div";                   // tag filter
        String attr   = "class";                 // attribute filter
        String value  = "\"monitor-total\"";     // attribute value filter
        String filter = tag + "[" + attr + "=" + value + "]";

        // Gets data from html document
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new IOException("Could not find URL");
        }
        Elements items = doc.select(filter);
        int[] data = new int[2];
        int i = 0;
        for (Element item : items) {
            String text = item.text();

            // text says "WASHERS: x of y available ... DRYERS: p of q available ..."
            data[0] = findXthNumber(text, 1);
            data[1] = findXthNumber(text, 3);
        }

        System.out.println("Washers: " + data[0] + " Dryers: " + data[1]);          // for testing only
        return data;
    }


    // Gets number of washers available
    private static int getWasherAvailability(int roomID) throws IOException {
        int[] data = getAvailability(roomID, washerIndex);
        return data[washerIndex];
    }


    // Gets number of dryers available
    private static int getDryerAvailability(int roomID) throws IOException {
        int[] data = getAvailability(roomID, dryerIndex);
        return data[dryerIndex];
    }


    // Parses Laundryview website for availability data for washers/dryers,
    // and updates the Parse database with the new data
    public static void main(String[] args) throws IOException {
        // Imports roomIDs from text file
        String inputName = "object_and_room_IDs.txt";
        In input = new In(inputName);
        while (input.hasNextChar()) {
            // Counts number of rooms
            input.readString();      // two items per room
            input.readInt();
            NUM_ROOMS++;
            System.out.println(NUM_ROOMS);                               // for testing only
        }
        int[] roomIDs = new int[NUM_ROOMS];
        input.close();
        input = new In(inputName);
        for (int i = 0; i < NUM_ROOMS; i++) {
            input.readString();      // skip objectID
            roomIDs[i] = input.readInt();
        }
        input.close();


        // Retrieves availability of washers/dryers in each room
        int[] roomWashers = new int[NUM_ROOMS];
        int[] roomDryers  = new int[NUM_ROOMS];
        for (int i = 0; i < NUM_ROOMS; i++) {
            roomWashers[i] = getWasherAvailability(roomIDs[i]);
            roomDryers[i]  = getDryerAvailability(roomIDs[i]);
        }

        // Saves data to a file
        String outputName = "laundry_rooms_data.txt";
        Out output = new Out(outputName);
        for (int i = 0; i < NUM_ROOMS; i++) {
            output.print(roomIDs[i] + " " + roomWashers[i] + " " + roomDryers[i] + " ");
        }
        output.close();
    }
}