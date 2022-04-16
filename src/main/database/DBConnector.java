package main.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DBConnector {

    public DBConnector(){
        try {
            String[] details = getDbDetails();
            if (details == null){
                return;
            }                                        // URL      Username     Password
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + details[0], details[1], details[2]);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private String[] getDbDetails(){
        try {
            String connFilePath = "src/resources/connParams.txt";
            Scanner scanner = new Scanner(new File(connFilePath));
            String[] details = new String[3];
            scanner.useDelimiter(", ");
            details[0] = scanner.next();
            details[1] = scanner.next();
            details[2] = scanner.next();
            return details;
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}
