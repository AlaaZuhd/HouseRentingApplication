package com.example.lab_project.models;

public class Tenant {
    private String email_address;
    private String first_name;
    private String last_name;
    private String gender;
    private String password;
    private String confirm_password;
    private String nationality;
    private double salary;
    private String occupation;
    private int family_size;
    private String current_country;
    private String city;
    private String phone_number;
    private int tenant_id;// remove it later

    //Constructors

    public Tenant() {
    }
    public Tenant(String email_address, String first_name, String last_name, String gender, String password, String confirm_password, String nationality, double salary, String occupation, int family_size, String current_country, String city, String phone_number) {
        this.email_address = email_address;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.password = password;
        this.confirm_password = confirm_password;
        this.nationality = nationality;
        this.salary = salary;
        this.occupation = occupation;
        this.family_size = family_size;
        this.current_country = current_country;
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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getFamily_size() {
        return family_size;
    }

    public void setFamily_size(int family_size) {
        this.family_size = family_size;
    }

    public String getCurrent_country() {
        return current_country;
    }

    public void setCurrent_country(String current_country) {
        this.current_country = current_country;
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

    public int getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(int tenant_id) {
        this.tenant_id = tenant_id;
    }

    //toString method

    @Override
    public String toString() {
        return "Tenant{" +
                "email_address='" + email_address + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", gender='" + gender + '\'' +
                ", password='" + password + '\'' +
                ", confirm_password='" + confirm_password + '\'' +
                ", nationality='" + nationality + '\'' +
                ", salary=" + salary +
                ", occupation='" + occupation + '\'' +
                ", family_size=" + family_size +
                ", current_country='" + current_country + '\'' +
                ", city='" + city + '\'' +
                ", phone_number='" + phone_number + '\'' +
                '}';
    }
}
