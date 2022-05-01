package com.example.lab_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lab_project.helpers.DataBaseHelper;
import com.example.lab_project.helpers.SharedPrefManager;
import com.example.lab_project.helpers.Utils;
import com.example.lab_project.models.Property;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    Intent intent;
    int user_type;
    String user_email_address;
    boolean is_authenticated = false;
    List<Property> properties = new ArrayList<>();
    List<PropertySummaryFragment> propertySummaryFragments = new ArrayList<>();
    final FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;


    //    String[] city_options = {"Any City", "Ramallah", "Jerusalem", "Salfeet", "Amman", "Jerash", "Zarqa", "Cairo", "Alexandria", "Aswan", "Damascus", "Homs", "Aleppo", "Beirut", "Tripoli", "Sidon", "New York", "Chicago", "Rio de Janeiro", "Manaus", "Tokyo", "Osaka", "Madrid", "Barcelona"};
    String[] status_options = {"Any","Finished", "Unfinished"};
    ArrayAdapter<String> objArr;
    ArrayAdapter<String> objArr2;
    Spinner status_spinner;
    EditText min_surface_area,
            max_surface_area,
            min_num_bedrooms,
            max_num_bedrooms,
            min_rental_price,
            city;
    CheckBox balcony_checkbox, garden_checkbox;
    Button search;
    int status_index = -1;
    boolean quick_validate_for_ranges = true;
    DataBaseHelper dataBaseHelper =new DataBaseHelper(SearchActivity.this,"Lab_Project.db",null,1);

    Button search_arrowback_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        findViewById(R.id.search_scrollview).setVisibility(View.VISIBLE);
        findViewById(R.id.search_result_scrollview).setVisibility(View.GONE);
        findViewById(R.id.search_error_layout).setVisibility(View.GONE);


//        adding Random properties
//        Property new_property = new Property("AAAA",344,34.4,2002,5,20.2,false,true,true, Date.valueOf("2015-03-31"),"hhh", "rawan@gmail.com");
//        dataBaseHelper.insert_property(new_property);
//        Property new_property = new Property("BBBB",344,34.4,2002,5,20.2,false,false,true, Date.valueOf("2015-03-31"),"hhh", "rawan@gmail.com");
//        dataBaseHelper.insert_property(new_property);

        // get user authentication.
        get_user_authentication();

        search_arrowback_button = (Button) findViewById(R.id.search_arrowback_button);

        search_arrowback_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Do the notification work and get the old animation
                intent = new Intent(SearchActivity.this, NavigationDrawerActivity.class);
                startActivity(intent);
            }
        });


        status_spinner = (Spinner)findViewById(R.id.search_status_spinner);
        city = (EditText) findViewById(R.id.search_city_editText);
        min_surface_area = (EditText)findViewById(R.id.search_minimum_surface_area_plainText);
        max_surface_area = (EditText)findViewById(R.id.search_maximum_surface_area_plainText);
        min_num_bedrooms = (EditText)findViewById(R.id.search_minimum_number_of_bedrooms_plainText);
        max_num_bedrooms = (EditText)findViewById(R.id.search_maximum_number_of_bedrooms_plainText);
        min_rental_price = (EditText)findViewById(R.id.search_minimum_renting_price_plainText);
        balcony_checkbox = (CheckBox)findViewById(R.id.search_balcony_checkBox);
        garden_checkbox = (CheckBox)findViewById(R.id.search_garden_checkBox);
        search = (Button)findViewById(R.id.search_search_button);

        objArr2 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, status_options);
        objArr2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status_spinner.setAdapter(objArr2);
        status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                status_index = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Quik validation
                quick_validate_for_ranges = validate();
                if(quick_validate_for_ranges) {
                    //if all valid, make sure to delete the error mssages and the red background
                    validated_edit();
                    //Trying to get the string Ready,
                    int flag = 0;
                    String query = "SELECT * FROM PROPERTY WHERE ";
                    if (!city.getText().toString().equals("")) {
                        query += "CITY=\"" + Utils.capitalize(city.getText().toString()) + "\"";
                        flag = 1;
                    }
                    if (!min_surface_area.getText().toString().equals("")) {
                        double minimum_surface_area_value = Double.parseDouble(min_surface_area.getText().toString());
                        if (flag == 1)
                            query += " AND SURFACE_AREA > \"" + minimum_surface_area_value + "\"";
                        else {
                            query += "SURFACE_AREA > \"" + minimum_surface_area_value + "\"";
                            flag = 1;
                        }
                    }
                    if (!max_surface_area.getText().toString().equals("")) {
                        double maximum_surface_area_value = Double.parseDouble(max_surface_area.getText().toString());
                        if (flag == 1)
                            query += " AND SURFACE_AREA < \"" + maximum_surface_area_value + "\"";
                        else {
                            query += "SURFACE_AREA < \"" + maximum_surface_area_value + "\"";
                            flag = 1;
                        }
                    }
                    if (!min_num_bedrooms.getText().toString().equals("")) {
                        int minimum_num_bedrooms_value = Integer.parseInt(min_num_bedrooms.getText().toString());
                        if (flag == 1)
                            query += " AND NUMBER_OF_BED_ROOMS > \"" + minimum_num_bedrooms_value + "\"";
                        else {
                            query += "NUMBER_OF_BED_ROOMS > \"" + minimum_num_bedrooms_value + "\"";
                            flag = 1;
                        }
                    }
                    if (!max_num_bedrooms.getText().toString().equals("")) {
                        int maximum_num_bedrooms_value = Integer.parseInt(max_num_bedrooms.getText().toString());
                        if (flag == 1)
                            query += " AND NUMBER_OF_BED_ROOMS < \"" + maximum_num_bedrooms_value + "\"";
                        else {
                            query += "NUMBER_OF_BED_ROOMS < \"" + maximum_num_bedrooms_value + "\"";
                            flag = 1;
                        }
                    }
                    if (!min_rental_price.getText().toString().equals("")) {
                        double minimum_rental_value = Double.parseDouble(min_rental_price.getText().toString());
                        if (flag == 1)
                            query += " AND RENTAL_PRICE > \"" + minimum_rental_value + "\"";
                        else {
                            query += "RENTAL_PRICE > \"" + minimum_rental_value + "\"";
                            flag = 1;
                        }
                    }
                    if (ifBalconyChecked()){
                        if(flag == 1)
                            query += " AND BALCONY ";
                        else{
                            query += "BALCONY ";
                            flag = 1;
                        }


                    }
                    if (ifGardenChecked()){
                        if(flag == 1)
                            query += " AND GARDEN ";
                        else{
                            query += "GARDEN ";
                            flag = 1;
                        }

                    }
                    if (status_index != -1 && status_index!=0) {
                        if (flag == 1) {
                            if (status_index == 1)
                                query += " AND STATUS ";
                            else
                                query += " AND NOT STATUS ";
                        } else {
                            if (status_index == 1)
                                query += "STATUS ";
                            else
                                query += "NOT STATUS ";

                        }
                        flag = 1;
                        status_index = -1;

                    }
                    if (flag == 0) {
                        query = "SELECT * FROM PROPERTY ";
                    }
                    Cursor temp = dataBaseHelper.search_property(query);
                    while (temp.moveToNext()) { //User found
                        Property property = new Property();
                        property.setProperty_id(Integer.parseInt(temp.getString(0)));
                        property.setCity(temp.getString(1));
                        property.setPostal_address(Integer.parseInt(temp.getString(2)));
                        property.setSurface_area(Double.parseDouble(temp.getString(3)));
                        property.setConstruction_year(Integer.parseInt(temp.getString(4)));
                        property.setNumber_of_bedrooms(Integer.parseInt(temp.getString(5)));
                        property.setRental_price(Double.parseDouble(temp.getString(6)));
                        property.setStatus(Utils.sql_string_to_boolean(temp.getString(7)));
                        property.setBalcony(Utils.sql_string_to_boolean(temp.getString(8)));
                        property.setGarden(Utils.sql_string_to_boolean(temp.getString(9)));
                        property.setAvailability_date(Date.valueOf(temp.getString(10)));
                        property.setDescription(temp.getString(11));
                        property.setRenting_agency_owner_id(temp.getString(12));
                        property.setPosting_date(Date.valueOf(temp.getString(13)));
                        property.setIs_active(temp.getInt(14)==1? true: false);
                        properties.add(property);
                    }
                    reset();
                    int j =0;
                    if(properties.size()>0) {
                        findViewById(R.id.search_scrollview).setVisibility(View.GONE);
                        findViewById(R.id.search_result_scrollview).setVisibility(View.VISIBLE);
                        findViewById(R.id.search_error_layout).setVisibility(View.GONE);
                        // display the list of properties.
                        for (int i = 0; i < properties.size(); i++) {
                            boolean is_active = Utils.check_update_is_active_property(properties.get(i), dataBaseHelper, SearchActivity.this); // automatic update for the is_active state.
                            if(!is_active) {
                                if(!properties.get(i).getRenting_agency_owner_id().equals(user_email_address)) // if the owner is not the registred user then don't display this property
                                    continue;
                            }
                            j++;
                            PropertySummaryFragment property_summary_fragment = new PropertySummaryFragment();

                            Bundle bundle = new Bundle();
                            bundle.putString("city", "" + properties.get(i).getCity());
                            bundle.putInt("postal_address", properties.get(i).getPostal_address());
                            bundle.putDouble("surface_area", properties.get(i).getSurface_area());
                            bundle.putInt("construction_year", properties.get(i).getConstruction_year());
                            bundle.putInt("number_of_bedrooms", properties.get(i).getNumber_of_bedrooms());
                            bundle.putDouble("rental_price", properties.get(i).getRental_price());
                            bundle.putBoolean("status", properties.get(i).isStatus());
                            bundle.putBoolean("balcony", properties.get(i).isBalcony());
                            bundle.putBoolean("garden", properties.get(i).isGarden());
                            bundle.putString("availability_date", properties.get(i).getAvailability_date().toString());
                            bundle.putString("description", properties.get(i).getDescription());
                            bundle.putInt("property_id", properties.get(i).getProperty_id());
                            bundle.putString("renting_agency_owner_id", properties.get(i).getRenting_agency_owner_id());
                            bundle.putBoolean("is_active", properties.get(i).isIs_active());
                            bundle.putString("posting_date", properties.get(i).getPosting_date().toString());
                            bundle.putString("fragment_tag", "Fragment_" + j);
                            property_summary_fragment.setArguments(bundle);

                            propertySummaryFragments.add(property_summary_fragment);
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(R.id.search_result_scrollview_layout, propertySummaryFragments.get(i), "Fragment_" + j);
                            fragmentTransaction.commit();
                            getSupportFragmentManager().executePendingTransactions();
                            if (fragmentManager.findFragmentByTag("Fragment_" + j) != null)
                                System.out.println("Not null from inside activity");
                        }
                    }
                    if(j==0){
                        // display a messgae to say nothing matching your results.

                        findViewById(R.id.search_scrollview).setVisibility(View.GONE);
                        findViewById(R.id.search_result_scrollview).setVisibility(View.GONE);
//                        findViewById(R.id.search_error_layout).setVisibility(View.VISIBLE);
//                        Button try_again_button = findViewById(R.id.search_try_again_button);
//                        try_again_button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                findViewById(R.id.search_scrollview).setVisibility(View.VISIBLE);
//                                findViewById(R.id.search_result_scrollview).setVisibility(View.GONE);
//                                findViewById(R.id.search_error_layout).setVisibility(View.GONE);
//                            }
//                        });
                        NoResultsFoundFragment  no_results_found_fragment = new NoResultsFoundFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("no_results_type", "search");
                        no_results_found_fragment.setArguments(bundle);

                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.search_layout, no_results_found_fragment, "Fragment_");
                        fragmentTransaction.commit();
                        getSupportFragmentManager().executePendingTransactions();
                        if(fragmentManager.findFragmentByTag("Fragment_") != null)
                            System.out.println("Not null from inside activity");
                    }
                }
            }
        });


    }
    public boolean ifBalconyChecked() {
        if(balcony_checkbox.isChecked()){
            return true;
        }
        return false;
    }
    public boolean ifGardenChecked() {
        if(garden_checkbox.isChecked()){
            return true;
        }
        return false;
    }
    public boolean validate(){
        double min_surface = -1 ;
        double max_surface = -1;
        if(!min_surface_area.getText().toString().equals("") && !max_surface_area.getText().toString().equals("")){
            try {
                min_surface = Double.parseDouble(min_surface_area.getText().toString());
                max_surface = Double.parseDouble(max_surface_area.getText().toString());
                if(min_surface > max_surface) {
                    min_surface_area.setError("Minimum can not be greater than maximum!");
                    min_surface_area.setBackgroundColor(Color.parseColor("#66FF0000"));
                    max_surface_area.setError("Maximum can not be less than minimum!");
                    max_surface_area.setBackgroundColor(Color.parseColor("#66FF0000"));
                    return false;
                }
            }catch (Exception e){
                return false;
            }
        }
        int min_bedrooms = -1;
        int max_bedrooms = -1;
        if(!min_num_bedrooms.getText().toString().equals("") && !max_num_bedrooms.getText().toString().equals("")){
            try {
                min_bedrooms = Integer.parseInt(min_num_bedrooms.getText().toString());
                max_bedrooms = Integer.parseInt(max_num_bedrooms.getText().toString());
                if(min_bedrooms > max_bedrooms) {
                    min_num_bedrooms.setError("Minimum can not be greater than maximum!");
                    min_num_bedrooms.setBackgroundColor(Color.parseColor("#66FF0000"));
                    max_num_bedrooms.setError("Maximum can not be less than minimum!");
                    max_num_bedrooms.setBackgroundColor(Color.parseColor("#66FF0000"));
                    return false;
                }
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }
    public void validated_edit(){
        //min_surface_area.setBackgroundColor(Color.parseColor("#FFFFFF"));
        min_surface_area.setError(null);
        //max_surface_area.setBackgroundColor(Color.parseColor("#FFFFFF"));
        max_surface_area.setError(null);
        //min_num_bedrooms.setBackgroundColor(Color.parseColor("#FFFFFF"));
        min_num_bedrooms.setError(null);
        //max_num_bedrooms.setBackgroundColor(Color.parseColor("#FFFFFF"));
        max_num_bedrooms.setError(null);
    }
    public void reset(){
        status_spinner.setSelection(0);
        balcony_checkbox.setChecked(false);
        garden_checkbox.setChecked(false);
        city.setText("");
        min_surface_area.setText("");
        max_surface_area.setText("");
        min_num_bedrooms.setText("");
        max_num_bedrooms.setText("");
        min_rental_price.setText("");

    }

    public void get_user_authentication(){
        sharedPrefManager =SharedPrefManager.getInstance(this);
        user_email_address = sharedPrefManager.readString("username", "username");
        user_type = sharedPrefManager.readInt("type", 100);


        if(user_email_address.equals("username") || (user_type != 0 && user_type != 1)){
            is_authenticated = false;
        } else {
            is_authenticated = true;
        }
    }
}