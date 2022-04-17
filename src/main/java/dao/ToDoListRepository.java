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
import java.time.temporal.ChronoUnit;
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
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO sql4486328.ToDoList (title,description,timestamp,dueDate,status) VALUES (?,?,?,?,?)");
            addItemToStatementParams(statement, item);
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
     */
    public ListItem getItemByTitle(String title) throws ListItemNotFoundException {
        openConnection();
        ListItem item;
        try {
            ResultSet set = executeStatement("SELECT * FROM sql4486328.ToDoList where title=?", title);
            if (set.next()) { // Move the ResultSet forward one

                item = new ListItem();

                item.setTitle(set.getString("title"));
                item.setText(set.getString("description"));
                String timeStamp = set.getString("timestamp").replace("T", " ");
                String dueDate = set.getString("dueDate").replace("T", " ");

                item.setTimestamp(DateParser.parseStringToLocalDateTime(timeStamp, "yyyy-MM-dd HH:mm:ss"));
                if (!dueDate.equals("None")) {
                    item.setDueDate(DateParser.parseStringToLocalDateTime(dueDate, "yyyy-MM-dd HH:mm"));
                }
                return item;
            }
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
     * Updates a ListItem in the database
     *
     * @param item to replace with
     */
    public void updateListItem(ListItem item) {
        openConnection();
        try {
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO sql4486328.ToDoList (title,description,timestamp,dueDate,status) VALUES (?,?,?,?,?)");
            addItemToStatementParams(statement, item);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /**
     * Prepares the passed statement and adds the params if there is any as statement parameters.
     * Returns the ResultSet
     * - Should not be used on statements that don't return a ResultSet
     *
     * @param statement to execute
     * @param params String[] parameters
     * @return ResultSet returned from the database
     * @throws SQLException if a syntax error occurs, or an attempt to use the method on a statement which does
     *                      not return a ResultSet
     */
    private ResultSet executeStatement(String statement, String... params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        if (params[0].length() > 0) {
            for (int i = 0; i < params.length; i++){
                try {
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
        String title = item.getTitle();
        String description = item.getText();
        String timestamp = item.getTimestamp().truncatedTo(ChronoUnit.SECONDS).toString();
        String dueDate = (item.getDueDate() == null ? "None" : item.getDueDate().toString());

        ItemStatus status = item.getStatus();
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
