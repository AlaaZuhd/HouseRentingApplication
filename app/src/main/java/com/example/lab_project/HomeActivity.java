package com.example.lab_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lab_project.models.Image;
import com.example.lab_project.models.Property;
import com.example.lab_project.models.RentingAgency;

import java.sql.Date;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    final ArrayList<Property> properties = new ArrayList<>();
    DataBaseHelper dataBaseHelper =new DataBaseHelper(HomeActivity.this,"Lab_Project.db",null,1);
    ImageView property_image[] = new ImageView[5]; // to hole the first image of each property.
    TextView property_city[] = new TextView[5]; // to hold the city..
    TextView property_availability_date[] = new TextView[5];
    TextView property_number_of_bedrooms[] = new TextView[5];
    Button property_view[] = new Button[5];//





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Property new_property = new Property("Ram",344,34.4,2002,5,20.2,false,true,true, Date.valueOf("2015-03-31"),"hhh", "r@r.com");
        dataBaseHelper.insert_property(new_property);

        //get properties and save them in properties array list.
        Cursor temp = dataBaseHelper.get_most_up_to_five_added_properties();
        while (temp.moveToNext()) { //User found
            Property property = new Property();
            property.setProperty_id(Integer.parseInt(temp.getString(0)));
            property.setCity(temp.getString(1));
            property.setPostal_address(Integer.parseInt(temp.getString(2)));
            property.setSurface_area(Double.parseDouble(temp.getString(3)));
            property.setConstruction_year(Integer.parseInt(temp.getString(4)));
            property.setNumber_of_bedrooms(Integer.parseInt(temp.getString(5)));
            property.setRental_price(Double.parseDouble(temp.getString(6)));
            property.setStatus(Boolean.parseBoolean(temp.getString(7)));
            property.setBalcony(Boolean.parseBoolean(temp.getString(8)));
            property.setGarden(Boolean.parseBoolean(temp.getString(9)));
            property.setAvailability_date(Date.valueOf(temp.getString(10)));
            property.setDescription(temp.getString(11));
            property.setRenting_agency_owner_id(temp.getString(12));
            properties.add(property);
        }

        int properties_length = properties.size();
        Resources res = getResources();
        for (int i=0; i<properties.size(); i++){
            int id = res.getIdentifier("home_imageView"+i, "id", getPackageName());
            property_image[0] = (ImageView)findViewById(id);
            id = res.getIdentifier("home_city_textView"+i, "id", getPackageName());
            property_city[0] = (TextView)findViewById(id);
            id = res.getIdentifier("home_availability_date_textView"+i, "id", getPackageName());
            property_availability_date[0] = (TextView)findViewById(id);
            id = res.getIdentifier("home_number_of_bedrooms_textView"+i, "id", getPackageName());
            property_number_of_bedrooms[0] = (TextView)findViewById(id);

            // load images, we need another query from Image table....
            property_city[0].setText("City: " + properties.get(0).getCity());
            property_availability_date[0].setText("Available date: " + properties.get(0).getAvailability_date());
            property_number_of_bedrooms[0].setText("Number of Bedrooms: " + properties.get(0).getNumber_of_bedrooms());

        }

    }
}