//Photo
package com.example.lab_project.models;
import java.sql.Date;

public class Property {
    private String city;
    private int postal_address;
    private double surface_area;
    private int construction_year;
    private int number_of_bedrooms;
    private double rental_price;
    private boolean status;     //finished or not
    private boolean balcony;
    private boolean garden;
    private Date availability_date;
    private String description;
    private Date posting_date;
    private boolean is_active;
    //private RentingAgency property_owner;
    private int property_id;
    private String renting_agency_owner_id;

    //Constructors
    public Property() {
    }


    public Property(String city, int postal_address, double surface_area, int construction_year, int number_of_bedrooms, double rental_price, boolean status, boolean balcony, boolean garden, Date availability_date, String description,  int property_id) {
        this.city = city;
        this.postal_address = postal_address;
        this.surface_area = surface_area;
        this.construction_year = construction_year;
        this.number_of_bedrooms = number_of_bedrooms;
        this.rental_price = rental_price;
        this.status = status;
        this.balcony = balcony;
        this.garden = garden;
        this.availability_date = availability_date;
        this.description = description;
        this.property_id = property_id;
    }

    public Property(String city, int postal_address, double surface_area, int construction_year, int number_of_bedrooms, double rental_price, boolean status, boolean balcony, boolean garden, Date availability_date, String description, String renting_agency_owner_id, Date posting_date, boolean is_active) {
        this.city = city;
        this.postal_address = postal_address;
        this.surface_area = surface_area;
        this.construction_year = construction_year;
        this.number_of_bedrooms = number_of_bedrooms;
        this.rental_price = rental_price;
        this.status = status;
        this.balcony = balcony;
        this.garden = garden;
        this.availability_date = availability_date;
        this.description = description;
        this.renting_agency_owner_id = renting_agency_owner_id;
        this.posting_date = posting_date;
        this.is_active = is_active;
    }



    public void setAvailability_date(Date availability_date) {
        this.availability_date = availability_date;
    }

    public String getRenting_agency_owner_id() {
        return renting_agency_owner_id;
    }

    public void setRenting_agency_owner_id(String renting_agency_owner_id) {
        this.renting_agency_owner_id = renting_agency_owner_id;
    }


    //Getters and Setters
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostal_address() {
        return postal_address;
    }

    public void setPostal_address(int postal_address) {
        this.postal_address = postal_address;
    }

    public double getSurface_area() {
        return surface_area;
    }

    public void setSurface_area(double surface_area) {
        this.surface_area = surface_area;
    }

    public int getConstruction_year() {
        return construction_year;
    }

    public void setConstruction_year(int construction_year) {
        this.construction_year = construction_year;
    }

    public int getNumber_of_bedrooms() {
        return number_of_bedrooms;
    }

    public void setNumber_of_bedrooms(int number_of_bedrooms) {
        this.number_of_bedrooms = number_of_bedrooms;
    }

    public double getRental_price() {
        return rental_price;
    }

    public void setRental_price(double rental_price) {
        this.rental_price = rental_price;
    }

    public boolean isStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isBalcony() {
        return this.balcony;
    }

    public void setBalcony(boolean balcony) {
        this.balcony = balcony;
    }

    public boolean isGarden() {
        return this.garden;
    }

    public void setGarden(boolean garden) {
        this.garden = garden;
    }

    public Date getAvailability_date() {
        return availability_date;
    }

    public void setAvailability_date(String availability_date) {
        System.out.println(availability_date);
        this.availability_date = Date.valueOf(availability_date);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProperty_id() {
        return property_id;
    }

    public void setProperty_id(int property_id) {
        this.property_id = property_id;
    }

    public Date getPosting_date() {
        return posting_date;
    }

    public void setPosting_date(Date posting_date) {
        this.posting_date = posting_date;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    //toString method

    @Override
    public String toString() {
        return "Property{" +
                "city='" + city + '\'' +
                ", postal_address=" + postal_address +
                ", surface_area=" + surface_area +
                ", construction_year=" + construction_year +
                ", number_of_bedrooms=" + number_of_bedrooms +
                ", rental_price=" + rental_price +
                ", status=" + status +
                ", balcony=" + balcony +
                ", garden=" + garden +
                ", availability_date=" + availability_date +
                ", description='" + description + '\'' +
                ", property_id=" + property_id +
                '}';
    }

}
