package com.example.lab_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
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
    LinearLayout linear_layouts[] = new LinearLayout[5];


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        Property new_property = new Property("TTTT",344,34.4,2002,5,20.2,false,true,true, Date.valueOf("2015-03-31"),"hhh", "y@y.com");
//        dataBaseHelper.insert_property(new_property);



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

            int id = res.getIdentifier("home_imageView"+(i+1), "id", getPackageName());
            property_image[i] = (ImageView)findViewById(id);
            id = res.getIdentifier("home_city_textView"+(i+1), "id", getPackageName());
            property_city[i] = (TextView)findViewById(id);
            id = res.getIdentifier("home_availability_date_textView"+(i+1), "id", getPackageName());
            System.out.println("pagckge name: " + getPackageName() + ", id: " + id);
            property_availability_date[i] = (TextView)findViewById(id);
            id = res.getIdentifier("home_number_of_bedrooms_textView"+(i+1), "id", getPackageName());
            property_number_of_bedrooms[i] = (TextView)findViewById(id);
            id = res.getIdentifier("home_view_details_button"+(i+1), "id", getPackageName());
            property_view[i] = (Button)findViewById(id);
            // load the one img for the property.
            temp = dataBaseHelper.get_one_image_for_property(properties.get(i).getProperty_id());
            Image img = new Image();
            int flag=0;
            while (temp.moveToNext()) { //User found
                img.setImage(BitmapFactory.decodeByteArray(temp.getBlob(0), 0, temp.getBlob(0).length));
                flag=1;
            }

            if(flag == 1){
                property_image[i].setImageBitmap(img.getImage());
            } else{
                // set an img from our menu
            }

            //Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
            property_city[i].setText("City: " + properties.get(0).getCity());
            property_availability_date[i].setText("Available date: " + properties.get(0).getAvailability_date());
            property_number_of_bedrooms[i].setText("Number of Bedrooms: " + properties.get(0).getNumber_of_bedrooms());

            id = res.getIdentifier("home_linear_layout"+(i+1), "id", getPackageName());
            linear_layouts[i] = (LinearLayout) findViewById(id);
            linear_layouts[i].setVisibility(View.VISIBLE);

        }

//        final ViewPropertyFragment firstFragment = new ViewPropertyFragment();
//        final FragmentManager fragmentManager = getSupportFragmentManager();
//        property_view[0].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.add(R.id.home_root_layout, firstFragment, "FristFrag");
//                fragmentTransaction.commit();
//            }
//        });

    }


}