package dao;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

class DBConnector {

    private Connection connection;

    /**
     * Constructor for the DBConnector class
     * begins the connection process
     */
    protected DBConnector() {
        connect();
    }

    /**
     * Attempts to connect to the database with the details in the String[]
     * Instantiates the Connection variable with the connection if successful
     */
    private void connect(){
        try {
            String[] details = getDBDetails();
            if (details == null) {
                return;
            }
            String url  = details[0]; // db.host from the DB details file. jdbc:mysql://serverName/
            // Gets the connection.                                 DB name     User        Password
            this.connection = DriverManager.getConnection(url + details[1], details[2], details[3]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the connParams.txt file with a delimiter, and stores the details in a String[].
     *
     * @return details from connParams.txt
     */
    private String[] getDBDetails() {
        try {
            String connFilePath = "src/main/java/resources/connParams.txt"; // Path to the file containing DB details
            Scanner scanner = new Scanner(new File(connFilePath)); // Opens the scanner
            String[] details = new String[4];
            int counter = 0;
            while (scanner.hasNextLine()){
                // Store the details from the DB details file into the Array. Split the nextLine and get the second (1st) element.
                details[counter++] = scanner.nextLine().split(" = ")[1];
            }

            scanner.close(); // Close the scanner
            return details;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get the connection
     *
     * @return connection
     */
    protected Connection getConnection() {
        return this.connection;
    }
}
