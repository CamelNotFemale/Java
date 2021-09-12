package com.example.models;

import com.example.utils.LocaleManager;

public class Post implements Cloneable {
    private String username;
    private String comment;
    private String date;

    public Post(String username, String comment) {
        this.username = username;
        this.comment = comment;
        this.date = LocaleManager.getTime();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public Post copy() {
        Post copy = new Post(this.username, this.comment);
        copy.setDate(this.date);
        return copy;
    }
}
