package util;

import dao.ToDoListRepository;
import model.ListItem;

import java.io.ByteArrayInputStream;

public class TestUtil {

    /**
     * Utility for simulating user input in the console window.
     * Builds a list of input strings, separated by the "line.separator"
     *
     * @param inputLines to print out (as user)
     */
    public static void simulateUserInput(String... inputLines){
        StringBuilder builder = new StringBuilder();
        // Ensures the Line separator will work on different operating systems
        String lineSeparator = System.getProperty("line.separator");
        for (String inputLine : inputLines) {
            // Enters the next line
            builder.append(inputLine);
            // Enters the next line
            builder.append(lineSeparator);
        }
        // Sets the input stream
        System.setIn(new ByteArrayInputStream(builder.toString().getBytes()));
    }

    /**
     * Creates and stores an example ListItem in the database for test cases
     * Title, timestamp and ItemStatus are instantiated. Others are not.
     */
    public static ListItem createExampleItem(){
        ToDoListRepository repository = new ToDoListRepository();

        // Create an example ListItem
        ListItem item = new ListItem();
        item.setTitle("Example list item");

        // Add the ListItem to the database directly
        repository.addListItem(item);

        return item;
    }
}
