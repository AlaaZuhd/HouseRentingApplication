package com.example.lab_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
<<<<<<< HEAD
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
=======
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
>>>>>>> b93fd345c720ed4123daa669fe976f07866e2b1b
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
<<<<<<< HEAD
import android.widget.Toast;

import com.example.lab_project.helpers.DataBaseHelper;
import com.example.lab_project.helpers.SharedPrefManager;
import com.example.lab_project.helpers.Utils;
import com.example.lab_project.helpers.slideradapter;
=======
>>>>>>> b93fd345c720ed4123daa669fe976f07866e2b1b
import com.example.lab_project.models.Image;
import com.example.lab_project.models.Property;


import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;


import java.sql.Date;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    final ArrayList<Property> properties = new ArrayList<>();
    DataBaseHelper dataBaseHelper =new DataBaseHelper(HomeActivity.this,"Lab_Project.db",null,1);
    SharedPrefManager sharedPrefManager;
    Intent intent;
    int user_type;
    String user_email_address;

    ImageView property_image[] = new ImageView[5]; // to hole the first image of each property.
    TextView property_city[] = new TextView[5]; // to hold the city..
    TextView property_availability_date[] = new TextView[5];
    TextView property_number_of_bedrooms[] = new TextView[5];
    Button property_view[] = new Button[5];//
    LinearLayout linear_layouts[] = new LinearLayout[5];
<<<<<<< HEAD
    SliderView sliderView;
=======
>>>>>>> b93fd345c720ed4123daa669fe976f07866e2b1b

    final ArrayList<Image> Images = new ArrayList<>();

<<<<<<< HEAD
=======



>>>>>>> b93fd345c720ed4123daa669fe976f07866e2b1b
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

<<<<<<< HEAD
=======
//        Property new_property = new Property("TTTT",344,34.4,2002,5,20.2,false,true,true, Date.valueOf("2015-03-31"),"hhh", "y@y.com");
//        dataBaseHelper.insert_property(new_property);
>>>>>>> b93fd345c720ed4123daa669fe976f07866e2b1b



        //get properties and save them in properties array list.
        final Cursor[] temp = {dataBaseHelper.get_most_up_to_five_added_properties()};
        while (temp[0].moveToNext()) { //User found
            Property property = new Property();
            property.setProperty_id(Integer.parseInt(temp[0].getString(0)));
            property.setCity(temp[0].getString(1));
            property.setPostal_address(Integer.parseInt(temp[0].getString(2)));
            property.setSurface_area(Double.parseDouble(temp[0].getString(3)));
            property.setConstruction_year(Integer.parseInt(temp[0].getString(4)));
            property.setNumber_of_bedrooms(Integer.parseInt(temp[0].getString(5)));
            property.setRental_price(Double.parseDouble(temp[0].getString(6)));
            property.setStatus(Utils.sql_string_to_boolean(temp[0].getString(7)));
            property.setBalcony(Utils.sql_string_to_boolean(temp[0].getString(8)));
            property.setGarden(Utils.sql_string_to_boolean(temp[0].getString(9)));
            property.setAvailability_date(Date.valueOf(temp[0].getString(10)));
            property.setDescription(temp[0].getString(11));
            property.setRenting_agency_owner_id(temp[0].getString(12));
            properties.add(property);
        }


        int properties_length = properties.size();
        Resources res = getResources();
        for (int i=0; i<properties.size(); i++){
<<<<<<< HEAD
=======

            int id = res.getIdentifier("home_imageView"+(i+1), "id", getPackageName());
            property_image[i] = (ImageView)findViewById(id);
            id = res.getIdentifier("home_city_textView"+(i+1), "id", getPackageName());
            property_city[i] = (TextView)findViewById(id);
            id = res.getIdentifier("home_availability_date_textView"+(i+1), "id", getPackageName());
            System.out.println("pagckge name: " + getPackageName() + ", id: " + id);
            property_availability_date[i] = (TextView)findViewById(id);
            id = res.getIdentifier("home_number_of_bedrooms_textView"+(i+1), "id", getPackageName());
            property_number_of_bedrooms[i] = (TextView)findViewById(id);
            id = res.getIdentifier("home_number_of_bedrooms_textView"+(i+1), "id", getPackageName());
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
>>>>>>> b93fd345c720ed4123daa669fe976f07866e2b1b

            int id = res.getIdentifier("home_imageView"+(i+1), "id", getPackageName());
            property_image[i] = (ImageView)findViewById(id);
            id = res.getIdentifier("home_city_textView"+(i+1), "id", getPackageName());
            property_city[i] = (TextView)findViewById(id);
            id = res.getIdentifier("home_availability_date_textView"+(i+1), "id", getPackageName());
            property_availability_date[i] = (TextView)findViewById(id);
            id = res.getIdentifier("home_number_of_bedrooms_textView"+(i+1), "id", getPackageName());
            property_number_of_bedrooms[i] = (TextView)findViewById(id);
            id = res.getIdentifier("home_view_details_button"+(i+1), "id", getPackageName());
            property_view[i] = (Button)findViewById(id);
            // load the one img for the property.
            temp[0] = dataBaseHelper.get_one_image_for_property(properties.get(i).getProperty_id());
            Image img = new Image();
            //Images.add(img);
            int flag=0;
            while (temp[0].moveToNext()) { //User found
                img.setImage_id(temp[0].getInt(0));
                img.setImage(BitmapFactory.decodeByteArray(temp[0].getBlob(2), 0, temp[0].getBlob(2).length));
                flag=1;
            }
            if(flag == 1){
                property_image[i].setImageBitmap(img.getImage());
            } else{// set an img from our menu
                property_image[i].setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.image_not_available));
            }

            int finalI = i;
            property_view[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(HomeActivity.this);
                    // not working !!!!!!!!!!!!!!
                    dialog.getWindow().setLayout((int)(getResources().getDisplayMetrics().widthPixels*0.50),
                            (int)(getResources().getDisplayMetrics().widthPixels*0.50));
                    //We have added a title in the custom layout. So let's disable the default title.
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
                    dialog.setCancelable(false);
                    //Mention the name of the layout of your custom dialog.
                    dialog.setContentView(R.layout.property_info_dialog);
                    sliderView= dialog.findViewById(R.id.imagesilder);
                    temp[0] = dataBaseHelper.get_all_image_for_property(properties.get(finalI).getProperty_id());
                    int flag=0;
                    Images.clear();
                    while (temp[0].moveToNext()) { //User found
                        Image img = new Image();
                        img.setImage_id(temp[0].getInt(0));
                        img.setImage(BitmapFactory.decodeByteArray(temp[0].getBlob(2), 0, temp[0].getBlob(2).length));
                        flag = 1;
                        Images.add(img);
                    }

                    slideradapter slideradapter= new slideradapter(Images);
                    sliderView.setSliderAdapter(slideradapter);
                    sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
                    sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
                    sliderView.startAutoCycle();

                    //ImageView property_image_imageview = dialog.findViewById(R.id.home_property_imageView);
                    Button property_close_button = dialog.findViewById(R.id.home_property_close_button);
                    EditText property_city_editText = dialog.findViewById(R.id.home_property_city_editText);
                    EditText property_availability_date_editText = dialog.findViewById(R.id.home_property_availability_date_editText);
                    EditText property_number_of_bedrooms_editText = dialog.findViewById(R.id.home_property_number_of_bedrooms_editText);
                    EditText property_postal_address_editText = dialog.findViewById(R.id.home_property_postal_address_editText);
                    EditText property_surface_area_editText = dialog.findViewById(R.id.home_property_surface_area_editText);
                    EditText property_construction_year_editText = dialog.findViewById(R.id.home_property_construction_year_editText);
                    EditText property_rental_price_editText = dialog.findViewById(R.id.home_property_rental_price_editText);
                    Switch property_status_switch = dialog.findViewById(R.id.home_property_status_switch);
                    Switch property_balcony_switch = dialog.findViewById(R.id.home_property_balcony_switch);
                    Switch property_garden_switch = dialog.findViewById(R.id.home_property_garden_switch);
                    EditText property_description_editText = dialog.findViewById(R.id.home_property_description_editText);
                    EditText property_renting_agency_owner_id_editText = dialog.findViewById(R.id.home_property_renting_agency_owner_id_editText);

                    //property_image_imageview.setImageBitmap(img.getImage());
                    property_city_editText.setText(properties.get(finalI).getCity());
                    property_availability_date_editText.setText(properties.get(finalI).getAvailability_date().toString());
                    property_number_of_bedrooms_editText.setText(properties.get(finalI).getNumber_of_bedrooms()+ "");
                    property_postal_address_editText.setText(properties.get(finalI).getPostal_address() + "");
                    property_surface_area_editText.setText(properties.get(finalI).getSurface_area() + "");
                    property_construction_year_editText.setText(properties.get(finalI).getConstruction_year() + "");
                    property_rental_price_editText.setText(properties.get(finalI).getRental_price() + "");
                    property_status_switch.setText(properties.get(finalI).isStatus() + "");
                    if(properties.get(finalI).isStatus()) property_status_switch.setChecked(true);
                    else property_status_switch.setChecked(false);
                    property_balcony_switch.setText(properties.get(finalI).isBalcony() + "");
                    if(properties.get(finalI).isBalcony()) property_balcony_switch.setChecked(true);
                    else property_balcony_switch.setChecked(false);
                    property_garden_switch.setText(properties.get(finalI).isGarden() + "");
                    if(properties.get(finalI).isGarden()) property_garden_switch.setChecked(true);
                    else property_garden_switch.setChecked(false);
                    property_description_editText.setText(properties.get(finalI).getDescription() + "");
                    property_renting_agency_owner_id_editText.setText(properties.get(finalI).getRenting_agency_owner_id() + "");

                    property_close_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });

            //Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
            property_city[i].setText("City: " + properties.get(0).getCity());
            property_availability_date[i].setText("Available date: " + properties.get(0).getAvailability_date());
            property_number_of_bedrooms[i].setText("Number of Bedrooms: " + properties.get(0).getNumber_of_bedrooms());

            id = res.getIdentifier("home_linear_layout"+(i+1), "id", getPackageName());
            linear_layouts[i] = (LinearLayout) findViewById(id);
            linear_layouts[i].setVisibility(View.VISIBLE);
        }
    }

    public void get_user_authentication(){
        sharedPrefManager =SharedPrefManager.getInstance(this);
        user_email_address = sharedPrefManager.readString("username", "username");
        user_type = sharedPrefManager.readInt("type", 100);


        if(user_email_address.equals("username") || (user_type != 0 && user_type != 1)){
            // user is not authenticated so direct to the login page.
            Toast.makeText(HomeActivity.this, "User is not authenticated, you need to log in", Toast.LENGTH_SHORT).show();
            intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }


}