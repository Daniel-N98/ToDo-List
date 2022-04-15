package controller;

import util.InputReader;

public class MenuController {

    public MenuController() {
        String TITLE = """
                Welcome to the TO-DO List application
                To get started, enter one of the following options
                
                """;
        System.out.println(TITLE);
    }

    public void printMainMenu(){
        String menu = """
                    1. View your TO-DO list
                    2. Add to your TO-DO list
                    3. Remove from your TO-DO list
                    4. Clear TO-DO list
                    5. Update TO-DO list
                    """;

        System.out.println(menu);
    }

    public int requestUserOption(InputReader reader) {
        try {
            return Integer.parseInt(reader.getNextText("Enter an option:")); // Option was parsed to an Integer, and can be returned

        } catch (NumberFormatException e) {
            System.out.println("Invalid option");
            return -1; // Option was not of the correct data type
        }
    }
}
