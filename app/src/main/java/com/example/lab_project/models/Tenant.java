package com.example.lab_project.models;

public class Tenant extends User{
    private String first_name;
    private String last_name;
    private String gender;
    private String nationality;
    private double salary;
    private String occupation;
    private int family_size;
    private String current_country;
    private int tenant_id;// remove it later

    //Constructors

    public Tenant() {
    }

    public Tenant(String email_address, String first_name, String last_name, String gender, String password, String confirm_password, String nationality, double salary, String occupation, int family_size, String current_country, String city, String phone_number) {
        super(email_address, password, confirm_password, current_country, city, phone_number);
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.nationality = nationality;
        this.salary = salary;
        this.occupation = occupation;
        this.family_size = family_size;
        this.current_country = super.getCountry();
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
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", gender='" + gender + '\'' +
                ", nationality='" + nationality + '\'' +
                ", salary=" + salary +
                ", occupation='" + occupation + '\'' +
                ", family_size=" + family_size +
                ", current_country='" + current_country + '\'' +
                '}';
    }
}
