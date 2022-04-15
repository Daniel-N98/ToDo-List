package controller;

import util.InputReader;

public class MenuController {

    public final static String TITLE = """
            Welcome to the TO-DO List application
            To get started, enter one of the following options
                            
            """;

    /**
     * Constructor for the MenuController class
     */
    public MenuController() {
    }

    /**
     * Print out the main menu to console
     */
    public void printMainMenu() {
        String menu = """
                1. View your TO-DO list
                2. Add to your TO-DO list
                3. Remove from your TO-DO list
                4. Clear TO-DO list
                5. Update TO-DO list
                """;

        System.out.println(menu);
    }

    /**
     * Print out the item editor menu to console
     */
    public void printItemEditorMenu() {
        String itemEditorMenu = """
                1. Edit title
                2. Edit description
                3. Edit due date
                4. Cancel
                """;

        System.out.println(itemEditorMenu);
    }

    /**
     * Request an integer value from the user
     * returns -1 if the user enters a non-integer value
     *
     * @param reader to read user input from
     * @return int integer entered by user, or -1
     */
    public int requestUserOption(InputReader reader) {
        try {
            return Integer.parseInt(reader.getNextText("Enter an option:")); // Option was parsed to an Integer, and can be returned

        } catch (NumberFormatException e) {
            System.out.println("Invalid option");
            return -1; // Option was not of the correct data type
        }
    }
}
