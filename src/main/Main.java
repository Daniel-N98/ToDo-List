package main;

import main.controller.ToDoController;
import main.database.DBConnector;

public class Main {

    public static void main(String[] args) {

        DBConnector connector = new DBConnector();
        new ToDoController();
    }
}
