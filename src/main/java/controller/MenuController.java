package controller;

import util.InputReader;

public class MenuController {

    // The welcome message for the application
    public final static String TITLE = """
            \nWelcome to the TO-DO List application
            To get started, enter one of the following options
                            
            """;

    /**
     * Get the main menu
     *
     * @return mainMenu
     */
    public String getMainMenu() {
        return """
                \n1. View your to-do list
                2. Add to your to-do list
                3. Remove from your to-do list
                4. Clear to-do list
                5. Update to-do list""";
    }

    /**
     * Get the item editor menu
     *
     * @return itemEditorMenu
     */
    public String getItemEditorMenu() {
        return """
                \n1. Edit title
                2. Edit description
                3. Edit due date
                4. Edit status
                5. Return to main menu""";
    }

    /**
     * Get the status editor menu
     *
     * @return statusEditorMenu
     */
    public String getStatusEditorMenu() {
        return """
                \n1. PENDING
                2. PROGRESS
                3. COMPLETE
                4. Return to item editor""";
    }

    /**
     * Request an integer value from the user
     * returns -1 if the user enters a non-integer value
     *
     * @param reader to read user input from
     * @return int integer entered by user, or -1
     */
    public int requestUserOption(String menu, InputReader reader) {
        System.out.println(menu);
        try {
            return Integer.parseInt(reader.getNextText("\nEnter an option:")); // Option was parsed to an Integer, and can be returned

        } catch (NumberFormatException e) {
            System.out.println("\nInvalid option");
            return -1; // Option was not of the correct data type
        }
    }
}
