package controller;

import util.InputReader;

public class Menu {
    private final ToDoController controller;
    private final InputReader reader;

    public Menu() {
        this.controller = new ToDoController();
        this.reader = new InputReader();

        String TITLE = """
                Welcome to the TO-DO List application
                To get started, enter one of the following options
                
                """;
        System.out.println(TITLE);
        int option;

        do {
            String menu = """
                    1. View your TO-DO list
                    2. Add to your TO-DO list
                    3. Remove from your TO-DO list
                    4. Clear TO-DO list
                    5. Update TO-DO list""";

            System.out.println(menu);

            option = requestUserOption();

        } while (option != 5);
    }

    private int requestUserOption() {
        int option;

        try {
            option = Integer.parseInt(reader.getNextText("Enter an option:"));
            return option;
        } catch (NumberFormatException e) {
            System.out.println("Invalid option");
        }

        return -1;
    }

    private void selectMenu(int option) {

        switch (option) {
            case 1 -> controller.printToDoList();
            case 2 -> controller.createToDoItem();
            case 3 -> controller.removeToDoItem();
            case 4 -> controller.clearToDoList();
        }
    }
}
