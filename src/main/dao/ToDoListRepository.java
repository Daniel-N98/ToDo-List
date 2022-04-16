package main.dao;

import main.exceptions.InvalidDateTimeFormatException;
import main.exceptions.ListItemNotFoundException;
import main.model.ListItem;
import main.types.ItemStatus;
import main.util.DateParser;

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

    private final Connection connection;

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
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO todolist.todolist (title,description,timestamp,dueDate,status) VALUES (?,?,?,?,?)");
            addItemToStatementParams(statement, item);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a ListItem from the database
     *
     * @param title to be removed from the database
     */
    public void removeListItem(String title) throws ListItemNotFoundException {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM todolist.todolist WHERE title=?");
            statement.setString(1, title);
            if (statement.executeUpdate() == 0) { // The amount of records returned == 0
                throw new ListItemNotFoundException("Item '" + title + "' cannot be found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes all items from the todolist table in the database
     */
    public void removeAllItems() {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM todolist.todolist");
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Searches for a ListItem in the database, and returns it
     *
     * @param title of the ListItem
     * @return ListItem or null
     */
    public ListItem getItemByTitle(String title) throws ListItemNotFoundException {
        ListItem item;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM todolist.todolist where title=?");
            statement.setString(1, title);
            ResultSet set = statement.executeQuery();
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
        try {
            PreparedStatement statement = connection.prepareStatement("Select * from todolist.todolist WHERE title=?");
            statement.setString(1, title);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get all list items from within the todolist table
     *
     * @return listItems list of all items
     */
    public List<ListItem> getAllListItems() {
        List<ListItem> listItems = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("Select title from todolist.todolist");
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                ListItem item = getItemByTitle(set.getString("title"));
                listItems.add(item);
            }
        } catch (SQLException | ListItemNotFoundException e) {
            e.printStackTrace();
        }
        return listItems;
    }

    /**
     * Updates a ListItem in the database
     *
     * @param item to replace with
     */
    public void updateListItem(ListItem item){
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO todolist.todolist (title,description,timestamp,dueDate,status) VALUES (?,?,?,?,?)");
            addItemToStatementParams(statement, item);
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Add ListItem variables as parameters to the provided statement
     *
     * @param statement to add ListItem variables to
     * @param item to add variables from
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
}
