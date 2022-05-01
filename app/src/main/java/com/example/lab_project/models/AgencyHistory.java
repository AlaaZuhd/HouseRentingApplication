package com.example.lab_project.models;


public class AgencyHistory {
    int property_id;
    String tenant_id;
    String city;
    String postal_address;
    String from_date;
    String to_date;
    String tenant_name;

    public AgencyHistory(int property_id, String tenant_id, String city, String postal_address, String from_date, String to_date, String tenant_name) {
        this.property_id = property_id;
        this.tenant_id = tenant_id;
        this.city = city;
        this.postal_address = postal_address;
        this.from_date = from_date;
        this.to_date = to_date;
        this.tenant_name = tenant_name;
    }

    public AgencyHistory(){
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal_address() {
        return postal_address;
    }

    public void setPostal_address(String postal_address) {
        this.postal_address = postal_address;
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

    public String getTenant_name() {
        return tenant_name;
    }

    public void setTenant_name(String tenant_name) {
        this.tenant_name = tenant_name;
    }

    @Override
    public String toString() {
        return "AgencyHistory{" +
                "property_id=" + property_id +
                ", tenant_id='" + tenant_id + '\'' +
                ", city='" + city + '\'' +
                ", postal_address='" + postal_address + '\'' +
                ", from_date='" + from_date + '\'' +
                ", to_date='" + to_date + '\'' +
                ", tenant_name='" + tenant_name + '\'' +
                '}';
    }
}
