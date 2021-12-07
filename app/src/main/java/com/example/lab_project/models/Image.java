package com.example.lab_project.models;

import android.graphics.Bitmap;

// image model class
public class Image {

    // image attributes
    private int image_id;
    private String image_name;
    private Bitmap image;
    private int property_id; // specify this image belongs to which property.
    private Property property; // to add the reference ..............................

    // constructors
    public Image() {
    }

    public Image(String image_name, Bitmap image, int property_id) {
        this.image_name = image_name;
        this.image = image;
        this.property_id = property_id;
    }

    // constructor with img id
    public Image(int image_id, String image_name, Bitmap image, int property_id) {
        this.image_id = image_id;
        this.image_name = image_name;
        this.image = image;
        this.property_id = property_id;
    }

    // getters and setters
    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public Bitmap getImage() {
        return image;
    }


    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public int getProperty_id() {
        return property_id;
    }

    public void setProperty_id(int property_id) {
        this.property_id = property_id;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "Image{" +
                "image_name='" + image_name + '\'' +
                ", image=" + image +
                ", property_id=" + property_id +
                '}';
    }
}
