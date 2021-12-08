package com.example.lab_project.models;

public class RentingAgency {
    private String email_address;
    private String name;
    private String password;
    private String confirm_password;
    private String country;
    private String city;
    private String phone_number;
    private int renting_agency_id;// remove it later
    //Constructors

    public RentingAgency() {
    }
    public RentingAgency(String email_address, String name, String password, String confirm_password, String country, String city, String phone_number) {
        this.email_address = email_address;
        this.name = name;
        this.password = password;
        this.confirm_password = confirm_password;
        this.country = country;
        this.city = city;
        this.phone_number = phone_number;
    }

    //Getters and Setters
    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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
                "email_address='" + email_address + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", confirm_password='" + confirm_password + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", phone_number='" + phone_number + '\'' +
                '}';
    }
}
