package com.example.lab_project.models;

public class RequestProperty {

    private int request_property_id;
    private int property_id;
    private String tenant_id;
    private String request_date;
    private boolean is_approved;
    private boolean is_rejected;
    private String from_date;
    private String to_date;

    public RequestProperty() {
    }

    public RequestProperty(int property_id, String tenant_id, String request_date, String from_date, String to_date) {
        this.property_id = property_id;
        this.tenant_id = tenant_id;
        this.request_date = request_date;
        this.from_date = from_date;
        this.to_date = to_date;
    }

    public RequestProperty(int property_id, String tenant_id, String request_date, boolean is_approveed, boolean is_rejected, String from_date, String to_date) {
        this.property_id = property_id;
        this.tenant_id = tenant_id;
        this.request_date = request_date;
        this.is_approved = is_approveed;
        this.is_rejected = is_rejected;
        this.from_date = from_date;
        this.to_date = to_date;
    }

    public RequestProperty(int request_property_id, int property_id, String tenant_id, String request_date, boolean is_approveed, boolean is_rejected, String from_date, String to_date) {
        this.request_property_id = request_property_id;
        this.property_id = property_id;
        this.tenant_id = tenant_id;
        this.request_date = request_date;
        this.is_approved = is_approveed;
        this.is_rejected = is_rejected;
        this.from_date = from_date;
        this.to_date = to_date;
    }

    public int getRequest_property_id() {
        return request_property_id;
    }

    public void setRequest_property_id(int request_property_id) {
        this.request_property_id = request_property_id;
    }

    public int getProperty_id() {
        return property_id;
    }

    public void setProperty_id(int property_id) {
        this.property_id = property_id;
    }

    public String getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }

    public String getRequest_date() {
        return request_date;
    }

    public void setRequest_date(String request_date) {
        this.request_date = request_date;
    }


    public boolean isIs_approved() {
        return is_approved;
    }

    public void setIs_approved(boolean is_approved) {
        this.is_approved = is_approved;
    }

    public boolean isIs_rejected() {
        return is_rejected;
    }

    public void setIs_rejected(boolean is_rejected) {
        this.is_rejected = is_rejected;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    @Override
    public String toString() {
        return "RequestProperty{" +
                "request_property_id=" + request_property_id +
                ", property_id=" + property_id +
                ", tenant_id='" + tenant_id + '\'' +
                ", request_date='" + request_date + '\'' +
                ", is_approved=" + is_approved +
                ", is_rejected=" + is_rejected +
                ", from_date='" + from_date + '\'' +
                ", to_date='" + to_date + '\'' +
                '}';
    }
}
