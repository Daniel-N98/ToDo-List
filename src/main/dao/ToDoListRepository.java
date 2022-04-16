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

public class ToDoListRepository {

    private final Connection connection;

    public ToDoListRepository() {
        this.connection = new DBConnector().getConnection();
    }

    public void addListItem(ListItem item) {
        String title = item.getTitle();
        String description = item.getText();
        String timestamp = item.getTimestamp().truncatedTo(ChronoUnit.SECONDS).toString();
        String dueDate = (item.getDueDate() == null ? "None" : item.getDueDate().toString());
        ItemStatus status = item.getStatus();

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO todolist.todolist (title,description,timestamp,dueDate,status) VALUES (?,?,?,?,?)");
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, timestamp);
            statement.setString(4, dueDate);
            statement.setString(5, status.toString());

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeListItem(ListItem item) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM todolist.todolist WHERE title=?");
            statement.setString(1, item.getTitle());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeAllItems() {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM todolist.todolist");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ListItem getItemByTitle(String title){
        ListItem item = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM todolist.todolist");
            ResultSet set = statement.executeQuery();
            item = new ListItem();

            item.setTitle(set.getString("title"));
            item.setText(set.getString("description"));
            item.setTimestamp(DateParser.parseStringToLocalDateTime(set.getString("timestamp"), "yyyy-MM-dd HH:mm"));
            item.setDueDate(DateParser.parseStringToLocalDateTime(set.getString("dueDate"), "yyyy-MM-dd HH:mm"));
        } catch (InvalidDateTimeFormatException | SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
}
