package com.example.lab_project.models;

public class Notification {
//
//    "NOTIFICATION_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
//            "FORM_ID TEXT, " +
//            "TO_ID TEXT, " +
//            "REQUEST_ID INTEGER, " +
//            "IS_READ BOOLEAN, " +
//            "SENDING_DATE TEXT, " +

    private int notification_id;
    private String from_id;
    private String to_id;
    private int request_id;
    private boolean is_read;
    private String sending_date;
    private String notification_type;

    public Notification() {
    }

    public Notification(String from_id, String to_id, int request_id, boolean is_read, String sending_date, String notification_type) {
        this.notification_id = notification_id;
        this.from_id = from_id;
        this.to_id = to_id;
        this.request_id = request_id;
        this.is_read = is_read;
        this.sending_date = sending_date;
        this.notification_type = notification_type;
    }

    public Notification(int notification_id, String from_id, String to_id, int request_id, boolean is_read, String sending_date, String notification_type) {
        this.notification_id = notification_id;
        this.from_id = from_id;
        this.to_id = to_id;
        this.request_id = request_id;
        this.is_read = is_read;
        this.sending_date = sending_date;
        this.notification_type = notification_type;
    }

    public int getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(int notification_id) {
        this.notification_id = notification_id;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getTo_id() {
        return to_id;
    }

    public void setTo_id(String to_id) {
        this.to_id = to_id;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public boolean isIs_read() {
        return is_read;
    }

    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
    }

    public String getSending_date() {
        return sending_date;
    }

    public void setSending_date(String sending_date) {
        this.sending_date = sending_date;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notification_id=" + notification_id +
                ", from_id='" + from_id + '\'' +
                ", to_id='" + to_id + '\'' +
                ", request_id=" + request_id +
                ", is_read=" + is_read +
                ", sending_date='" + sending_date + '\'' +
                ", notification_type='" + notification_type + '\'' +
                '}';
    }
}
