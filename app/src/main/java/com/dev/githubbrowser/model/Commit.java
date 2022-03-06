package com.dev.githubbrowser.model;

public class Commit {
    String date;
    String aurl;
    String username;
    String message;
    String id;

    public Commit(String date, String aurl, String username, String message, String id) {
        this.date = date;
        this.aurl = aurl;
        this.username = username;
        this.message = message;
        this.id = id;
    } public Commit(String date, String aurl, String username, String message) {
        this.date = date;
        this.aurl = aurl;
        this.username = username;
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAurl() {
        return aurl;
    }

    public void setAurl(String aurl) {
        this.aurl = aurl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
