package model;

import types.ItemStatus;

import java.time.LocalDateTime;

public class ListItem {

    private String title;
    private String text;
    private LocalDateTime timestamp;
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

    public ItemStatus getStatus(){
        return this.status;
    }

    public void setStatus(ItemStatus status){
        this.status = status;
    }


}
