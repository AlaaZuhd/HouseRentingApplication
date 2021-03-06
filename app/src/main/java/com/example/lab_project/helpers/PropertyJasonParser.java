package com.example.lab_project.helpers;

import com.example.lab_project.models.Property;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PropertyJasonParser {
    public static List<Property> getObjectFromJason(String jason) {
        List<Property> properties;
        try {
            JSONArray jsonArray = new JSONArray(jason);
            properties = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject = (JSONObject) jsonArray.get(i);
                Property property = new Property();
                property.setCity(jsonObject.getString("city"));
                property.setPostal_address(jsonObject.getInt("postal_address"));
                property.setSurface_area(jsonObject.getDouble("surface_area"));
                property.setConstruction_year(jsonObject.getInt("construction_year"));
                property.setNumber_of_bedrooms(jsonObject.getInt("number_of_bedrooms"));
                property.setRental_price(jsonObject.getDouble("rental_price"));
                property.setStatus(jsonObject.getBoolean("status"));
                property.setBalcony(jsonObject.getBoolean("balcony"));
                property.setGarden(jsonObject.getBoolean("garden"));
                property.setAvailability_date(jsonObject.getString("availability_date"));
                property.setDescription(jsonObject.getString("description"));
                property.setPosting_date(Date.valueOf(jsonObject.getString("posting_date")));
                property.setIs_active(jsonObject.getBoolean("is_active"));



                properties.add(property);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return properties;
    }
}



