package model;

import types.ItemStatus;

import java.time.LocalDateTime;

public class ListItem {

    private String title;
    private String text;
    private LocalDateTime timestamp;
    private LocalDateTime dueDate;
    private ItemStatus status;

    public ListItem(String title, String text, ItemStatus status){
        this.title = title;
        this.text = text;
        this.timestamp = LocalDateTime.now();
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getDueDate(){
        return this.dueDate != null ? this.dueDate : null;
    }

    public void setDueDate(LocalDateTime dueDate){
        this.dueDate = dueDate;
    }

    public ItemStatus getStatus(){
        return this.status;
    }

    public void setStatus(ItemStatus status){
        this.status = status;
    }

    @Override
    public String toString() {
        return "ListItem{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", timestamp=" + timestamp +
                ", dueDate=" + dueDate.toString().replace("T", " ") +
                ", status=" + status +
                '}';
    }
}
