package com.example.lab_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab_project.helpers.DataBaseHelper;
import com.example.lab_project.helpers.DatePicker;
import com.example.lab_project.helpers.SharedPrefManager;
import com.example.lab_project.helpers.Utils;
import com.example.lab_project.models.Property;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class EditListOfPropertiesActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    Intent intent;
    int user_type;
    String user_email_address;

    DataBaseHelper dataBaseHelper =new DataBaseHelper(EditListOfPropertiesActivity.this,"Lab_Project.db",null,1);
    List<PropertySummaryFragment> propertySummaryFragments = new ArrayList<>();
    final FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;
    List<Property> properties = new ArrayList<>();
    Button edit_properties_arrowback_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list_of_properties);

        // get user authentication.
        get_user_authentication();

        edit_properties_arrowback_button = (Button) findViewById(R.id.edit_properties_arrowback_button);

        edit_properties_arrowback_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Do the notification work and get the old animation
                intent = new Intent(EditListOfPropertiesActivity.this, NavigationDrawerActivity.class);
                startActivity(intent);
            }
        });

        if(user_type == 0) {
            // tenant user can't be here. -> will never happened (go to home activity...)
            Toast.makeText(EditListOfPropertiesActivity.this, "Tenant User can't be here!", Toast.LENGTH_SHORT).show();
            intent = new Intent(EditListOfPropertiesActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        // get list of properties for the current renting agency.

        final Cursor[] temp = {dataBaseHelper.get_properties_for_a_renting_agency(user_email_address)};
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
            property.setPosting_date(Date.valueOf(temp[0].getString(13)));
            property.setIs_active(temp[0].getInt(14) == 1? true: false);
            properties.add(property);
        }
        TextView tx1 = (TextView) findViewById(R.id.edit_list_of_properties_city_textView);

        // display the list of properties.
        for(int i=0; i<properties.size(); i++) {
            PropertySummaryFragment property_summary_fragment = new PropertySummaryFragment();

            Bundle bundle = new Bundle();
            bundle.putString("city", ""+ properties.get(i).getCity());
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
            bundle.putBoolean("is_active", properties.get(i).isIs_active());
            bundle.putString("posting_date", properties.get(i).getPosting_date().toString());
            bundle.putInt("property_id", properties.get(i).getProperty_id());
            bundle.putString("renting_agency_owner_id", properties.get(i).getRenting_agency_owner_id());
            bundle.putString("fragment_tag", "Fragment_"+i);
            property_summary_fragment.setArguments(bundle);

            propertySummaryFragments.add(property_summary_fragment);
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.edit_list_of_properties_scrollview_layout, propertySummaryFragments.get(i), "Fragment_" + i);
            fragmentTransaction.commit();
            getSupportFragmentManager().executePendingTransactions();
            if(fragmentManager.findFragmentByTag("Fragment_" + i) != null)
                System.out.println("Not null from inside activity");
        }
        // enable a listener for the buttons to display a dialog contains the details of the selected property.

    }

    public void get_user_authentication(){
        sharedPrefManager =SharedPrefManager.getInstance(this);
        user_email_address = sharedPrefManager.readString("username", "username");
        user_type = sharedPrefManager.readInt("type", 100);

        System.out.println("Username: " + user_email_address + "\t Type: " + user_type);

        if(user_email_address.equals("username") || (user_type != 0 && user_type != 1)){
            // user is not authenticated so direct to the login page.
            Toast.makeText(EditListOfPropertiesActivity.this, "User is not authenticated, you need to log in", Toast.LENGTH_SHORT).show();
            intent = new Intent(EditListOfPropertiesActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }


}