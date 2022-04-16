package controller;

import util.InputReader;

public class MenuController {

    public final static String TITLE = """
            \nWelcome to the TO-DO List application
            To get started, enter one of the following options
                            
            """;

    /**
     * Get the main menu
     * @return mainMenu
     */
    public String getMainMenu() {
        return """
                \n1. View your TO-DO list
                2. Add to your TO-DO list
                3. Remove from your TO-DO list
                4. Clear TO-DO list
                5. Update TO-DO list""";
    }

    /**
     * Get the item editor menu
     * @return itemEditorMenu
     */
    public String getItemEditorMenu() {
        return """
                \n1. Edit title
                2. Edit description
                3. Edit due date
                4. Edit status
                5. Cancel""";
    }

    /**
     * Get the status editor menu
     * @return statusEditorMenu
     */
    public String getStatusEditorMenu(){
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
    public int requestUserOption(InputReader reader) {
        try {
            return Integer.parseInt(reader.getNextText("\nEnter an option:")); // Option was parsed to an Integer, and can be returned

        } catch (NumberFormatException e) {
            System.out.println("\nInvalid option");
            return -1; // Option was not of the correct data type
        }
    }
}
