package com.exuberant.code2create4.models;

public class Alert {

    String title;
    String date;
    String message;
    String time;

    public Alert(String title, String date, String message, String time) {
        this.title = title;
        this.date = date;
        this.message = message;
        this.time = time;
    }

    public Alert() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
