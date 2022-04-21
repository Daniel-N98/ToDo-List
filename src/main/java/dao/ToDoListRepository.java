package dao;

import exceptions.InvalidDateTimeFormatException;
import exceptions.ListItemNotFoundException;
import model.ListItem;
import types.ItemStatus;
import util.DateParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ToDoListRepository {

    /*
     *  The purpose of the ToDoListRepository class is to handle all interactions between the application,
     *  and the todolist table in the database.
     */

    private Connection connection;

    /**
     * Constructor for the ToDoListRepository
     * Instantiates the DBConnector
     */
    public ToDoListRepository() {
        this.connection = new DBConnector().getConnection();
    }

    /**
     * Inserts a new ListItem into the database
     *
     * @param item to add into the database
     */
    public void addListItem(ListItem item) {
        openConnection();
        try {
            // Prepare the statement to be executed
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO sql4486328.ToDoList (title,description,timestamp,dueDate,status) VALUES (?,?,?,?,?)");
            // Insert item properties as parameters to the statement
            addItemToStatementParams(statement, item);
            // Execute the PreparedStatement
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /**
     * Removes a ListItem from the database
     *
     * @param title to be removed from the database
     * @throws ListItemNotFoundException if the list item is not found
     */
    public void removeListItem(String title) throws ListItemNotFoundException {
        openConnection();
        try {
            PreparedStatement statement = this.connection.prepareStatement("DELETE FROM sql4486328.ToDoList WHERE title=?");
            statement.setString(1, title);

            if (statement.executeUpdate() == 0) {
                throw new ListItemNotFoundException("Item '" + title + "' cannot be found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /**
     * Removes all items from the todolist table in the database
     */
    @SuppressWarnings("SqlWithoutWhere") // Suppress the IDE from showing a warning for the 'delete query'.
    public void removeAllItems() {       // In this situation, we are sure we want to clear it.
        openConnection();
        try {
            PreparedStatement statement = this.connection.prepareStatement("DELETE FROM sql4486328.ToDoList");
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /**
     * Searches for a ListItem in the database, and returns it
     *
     * @param title of the ListItem
     * @return ListItem or null
     * @throws ListItemNotFoundException if the list item is not found
     */
    public ListItem getItemByTitle(String title) throws ListItemNotFoundException {
        openConnection();
        ListItem item;
        try {
            ResultSet set = executeStatement("SELECT * FROM sql4486328.ToDoList where title=?", title);
            if (set.next()) {
                String dueDateStr = set.getString("dueDate").replace("T", " ");

                LocalDateTime timeStamp = DateParser.parseStringToLocalDateTime(set.getString("timestamp").replace("T", " "), "yyyy-MM-dd HH:mm");
                LocalDateTime dueDate = !dueDateStr.equals("None") ? DateParser.parseStringToLocalDateTime(dueDateStr, "yyyy-MM-dd HH:mm") : null;
                // Create the ListItem object with the ResultSet elements
                item = new ListItem(set.getString("title"),
                        set.getString("description"),
                        timeStamp,
                        dueDate,
                        ItemStatus.valueOf(set.getString("status")));
                return item;
            }
            // ResultSet is empty
            throw new ListItemNotFoundException("Item '" + title + "' cannot be found");
        } catch (InvalidDateTimeFormatException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }

    /**
     * Checks whether a list item is present within the todolist table
     *
     * @param title of the list item
     * @return true if the item exists, false otherwise
     */
    public boolean doesListItemExist(String title) {
        openConnection();
        try {
            // Returns true if the returned ResultSet has next
            return executeStatement("Select * from sql4486328.ToDoList WHERE title=?", title).next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }

    /**
     * Get all list items from within the todolist table
     *
     * @return listItems list of all items
     */
    public List<ListItem> getAllListItems() {
        openConnection();
        List<ListItem> listItems = new ArrayList<>();
        try {
            ResultSet set = executeStatement("Select title from sql4486328.ToDoList", "");
            while (set.next()) {
                // Get the ListItem from the title, and add it to the List.
                ListItem item = getItemByTitle(set.getString("title"));
                listItems.add(item);
            }
        } catch (SQLException | ListItemNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return listItems;
    }

    /**
     * Prepares the passed statement and adds the params if there is any as statement parameters.
     * Returns the ResultSet
     * - Should not be used on statements that don't return a ResultSet
     *
     * @param statement to execute
     * @param params    String[] parameters
     * @return ResultSet returned from the database
     * @throws SQLException if a syntax error occurs, or an attempt to use the method on a statement which does
     *                      not return a ResultSet
     */
    private ResultSet executeStatement(String statement, String... params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        if (params[0].length() > 0) {
            for (int i = 0; i < params.length; i++) {
                try {
                    // Set the parameters for the statement (+1 since the parameters begin at index '1')
                    preparedStatement.setString((i + 1), params[i]);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return preparedStatement.executeQuery();
    }

    /**
     * Add ListItem variables as parameters to the provided statement
     *
     * @param statement to add ListItem variables to
     * @param item      to add variables from
     * @throws SQLException error
     */
    private void addItemToStatementParams(PreparedStatement statement, ListItem item) throws SQLException {
        // Declare the properties of the ListItem ready to be inserted into the statement
        String title = item.getTitle();
        String description = item.getDescription();
        String timestamp = item.getTimestamp().toString();
        // The DueDate may not have been set by the user, if it has not, then instantiate the String to 'None'
        String dueDate = (item.getDueDate() == null ? "None" : item.getDueDate().toString());

        ItemStatus status = item.getStatus();
        // Set statement parameters equal to each of the ListItem's properties.
        statement.setString(1, title);
        statement.setString(2, description);
        statement.setString(3, timestamp);
        statement.setString(4, dueDate);
        statement.setString(5, status.toString());
    }

    /**
     * Opens the connection to the database
     */
    private void openConnection() {
        this.connection = new DBConnector().getConnection();
    }

    /**
     * Closes the connection to the database
     * The connection should be closed as soon as possible using this method
     */
    private void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
