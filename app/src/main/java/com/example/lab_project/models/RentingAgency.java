package com.example.lab_project.models;

public class RentingAgency extends User{
    private String name;
    private int renting_agency_id;// remove it later
    //Constructors

    public RentingAgency() {
    }
    public RentingAgency(String email_address, String name, String password, String confirm_password, String country, String city, String phone_number) {
        super(email_address, password, confirm_password, country, city, phone_number);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getRenting_agency_id() {
        return renting_agency_id;
    }

    public void setRenting_agency_id(int renting_agency_id) {
        this.renting_agency_id = renting_agency_id;
    }

    //toString method
    @Override
    public String toString() {
        return "RentingAgency{" +
                ", name='" + name + '\'' +
                '}';
    }
}
