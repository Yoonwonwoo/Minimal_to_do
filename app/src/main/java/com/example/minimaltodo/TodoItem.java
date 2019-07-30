package com.example.minimaltodo;

import java.io.Serializable;
import java.util.Date;

public class TodoItem implements Serializable {
    String title, description;
    Date date;

    public TodoItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TodoItem(String title, String description, Date date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }
}
