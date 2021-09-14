package com.mySampleApplication.client;

import java.io.Serializable;

public class Post implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String comment;
    private String date;

    public Post(String username, String comment, String date) {
        this.username = username;
        this.comment = comment;
        this.date = date;
    }

    public Post() {
    }

    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }
}
