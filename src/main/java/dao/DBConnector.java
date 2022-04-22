package dao;

import java.io.File;
import java.io.FileNotFoundException;
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
            //String url = "jdbc:mysql://localhost:3306/";
            String url  = "jdbc:mysql://sql4.freesqldatabase.com/";
            this.connection = DriverManager.getConnection(url + details[0], details[1], details[2]);
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
            String connFilePath = "src/main/java/resources/connParams.txt";
            Scanner scanner = new Scanner(new File(connFilePath));
            String[] details = new String[3];
            scanner.useDelimiter(", ");
            details[0] = scanner.next();
            details[1] = scanner.next();
            details[2] = scanner.next();

            scanner.close();
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
