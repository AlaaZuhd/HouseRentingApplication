package com.example.lab_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lab_project.helpers.DataBaseHelper;
import com.example.lab_project.helpers.SharedPrefManager;
import com.example.lab_project.models.AgencyHistory;
import com.example.lab_project.models.Tenant_Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AgencyHistoryActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    Intent intent;
    int user_type;
    String user_email_address;
    Button agency_history_arrowback_button;

    DataBaseHelper dataBaseHelper = new DataBaseHelper(AgencyHistoryActivity.this,"Lab_Project.db",null,1);
    List<AgencyHistoryFragment> agencyHistoryFragments = new ArrayList<>();
    final FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;
    List<Integer>properities_ids = new ArrayList<Integer>();
    List<AgencyHistory> information_to_be_displayed = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency_history);

        // get user authentication.
        get_user_authentication();

        agency_history_arrowback_button = (Button) findViewById(R.id.agency_history_arrowback_button);

        agency_history_arrowback_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Do the notification work and get the old animation
                intent = new Intent(AgencyHistoryActivity.this, NavigationDrawerActivity.class);
                startActivity(intent);
            }
        });

        //Get all properties for a specific agency
        Cursor temp = dataBaseHelper.get_properties_for_specific_agency(user_email_address);
        while (temp.moveToNext()) {
            properities_ids.add(Integer.parseInt(temp.getString(0)));
        }
        //Get Tenant_IDs for each property from tenant_property table
        for(int i=0;i<properities_ids.size();i++){
            Cursor temp2 = dataBaseHelper.get_tenants_renting_specific_property(properities_ids.get(i));
            while (temp2.moveToNext()) {
                AgencyHistory data_to_be_displayed = new AgencyHistory();
                data_to_be_displayed.setTenant_id(temp2.getString(0));
                data_to_be_displayed.setProperty_id(Integer.parseInt(temp2.getString(1)));
                data_to_be_displayed.setFrom_date(temp2.getString(2));
                data_to_be_displayed.setTo_date(temp2.getString(3));
                information_to_be_displayed.add(data_to_be_displayed);
            }
        }
        //get Tenant Name
        for(int i = 0 ;i<information_to_be_displayed.size();i++){
            Cursor temp3 = dataBaseHelper.get_tenant(information_to_be_displayed.get(i).getTenant_id());
            while (temp3.moveToNext()) {
                information_to_be_displayed.get(i).setTenant_name(temp3.getString(1)+" "+temp3.getString(2));
            }
            Cursor temp4 = dataBaseHelper.get_property_information(information_to_be_displayed.get(i).getProperty_id());
            while (temp4.moveToNext()){
                information_to_be_displayed.get(i).setCity(temp4.getString(1));
                information_to_be_displayed.get(i).setPostal_address(temp4.getString(2));
            }

            AgencyHistoryFragment agency_history_fragment = new AgencyHistoryFragment();
            Bundle bundle = new Bundle();
            bundle.putString("city", information_to_be_displayed.get(i).getCity());
            bundle.putString("postalAddress",information_to_be_displayed.get(i).getPostal_address());
            bundle.putString("from",information_to_be_displayed.get(i).getFrom_date());
            bundle.putString("to",information_to_be_displayed.get(i).getTo_date());
            bundle.putString("tenant_name",information_to_be_displayed.get(i).getTenant_name());
            agency_history_fragment.setArguments(bundle);
            agencyHistoryFragments.add(agency_history_fragment);
            fragmentTransaction = fragmentManager.beginTransaction();
            //
            fragmentTransaction.add(R.id.agency_history_scrollview_layout, agencyHistoryFragments.get(i),"Fragment_"+i);
            fragmentTransaction.commit();
            getSupportFragmentManager().executePendingTransactions();
            if(fragmentManager.findFragmentByTag("Fragment_"+i) != null)
                System.out.println("Not null from inside activity");
        }
        if(information_to_be_displayed.size() == 0) {
            findViewById(R.id.agency_history_scrollview).setVisibility(View.GONE);

            NoResultsFoundFragment  no_results_found_fragment = new NoResultsFoundFragment();
            Bundle bundle = new Bundle();
            bundle.putString("no_results_type", "agency history");
            bundle.putString("email", user_email_address);
            no_results_found_fragment.setArguments(bundle);

            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.agency_history_layout, no_results_found_fragment, "Fragment_");
            fragmentTransaction.commit();
            getSupportFragmentManager().executePendingTransactions();
            if(fragmentManager.findFragmentByTag("Fragment_") != null)
                System.out.println("Not null from inside activity");
        }

    }

    public void get_user_authentication(){
        sharedPrefManager =SharedPrefManager.getInstance(this);
        user_email_address = sharedPrefManager.readString("username", "username");
        user_type = sharedPrefManager.readInt("type", 100);

        System.out.println("Username: " + user_email_address + "\t Type: " + user_type);

        if(user_email_address.equals("username") || (user_type != 0 && user_type != 1)){
            // user is not authenticated so direct to the login page.
            Toast.makeText(AgencyHistoryActivity.this, "User is not authenticated, you need to log in", Toast.LENGTH_SHORT).show();
            intent = new Intent(AgencyHistoryActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}