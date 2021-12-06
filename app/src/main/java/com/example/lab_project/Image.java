package com.example.lab_project;

import android.graphics.Bitmap;

// image model class
public class Image {

    // image attributes
    private String image_name;
    private Bitmap image;
    private Property property; // specify this image belongs to which property.

    // constructors
    public Image() {
    }

    public Image(String image_name, Bitmap image, Property property) {
        this.image_name = image_name;
        this.image = image;
        this.property = property;
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
                ", property=" + property +
                '}';
    }
}
