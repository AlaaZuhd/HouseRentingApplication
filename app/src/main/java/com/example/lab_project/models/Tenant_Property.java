package com.example.lab_project.models;

public class Tenant_Property {
    String city;
    String postal_address;
    String from_date;
    String to_date;
    String renting_agency_name;

    public Tenant_Property(String city, String postal_address, String from_date, String to_date, String renting_agency_name) {
        this.city = city;
        this.postal_address = postal_address;
        this.from_date = from_date;
        this.to_date = to_date;
        this.renting_agency_name = renting_agency_name;
    }

    public Tenant_Property() {

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

    public String getRenting_agency_name() {
        return renting_agency_name;
    }

    public void setRenting_agency_name(String renting_agency_name) {
        this.renting_agency_name = renting_agency_name;
    }

    @Override
    public String toString() {
        return "Tenant_Property{" +
                "city='" + city + '\'' +
                ", postal_address='" + postal_address + '\'' +
                ", from_address='" + from_date + '\'' +
                ", to_address='" + to_date + '\'' +
                ", renting_agency_name='" + renting_agency_name + '\'' +
                '}';
    }
}
