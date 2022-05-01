package com.example.lab_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lab_project.helpers.DataBaseHelper;
import com.example.lab_project.helpers.SharedPrefManager;
import com.example.lab_project.models.Property;
import com.example.lab_project.models.Tenant;
import com.example.lab_project.models.Tenant_Property;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class TenantHistoryActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    Intent intent;
    int user_type;
    String user_email_address;

    DataBaseHelper dataBaseHelper = new DataBaseHelper(TenantHistoryActivity.this,"Lab_Project.db",null,1);
    List<TenantHistoryFragment> tenantHistoryFragments = new ArrayList<>();
    final FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;
    ArrayList<Integer> properties_ids = new ArrayList<>();
    List<Tenant_Property> information_to_be_displayed = new ArrayList<>();

    Button tenant_history_arrowback_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_history);

        // get user authentication.
        get_user_authentication();

        tenant_history_arrowback_button = (Button) findViewById(R.id.tenant_history_arrowback_button);

        tenant_history_arrowback_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Do the notification work and get the old animation
                intent = new Intent(TenantHistoryActivity.this, NavigationDrawerActivity.class);
                startActivity(intent);
            }
        });

        intent = getIntent();
        boolean view_mode = intent.getBooleanExtra("view_other_profile", false);
        String tenant_email_address = intent.getStringExtra("tenant_email_address");

        if(!view_mode){
            tenant_email_address = user_email_address;
        }

        Cursor temp = dataBaseHelper.get_properties_rented_by_specific_tenant(tenant_email_address);
        while (temp.moveToNext()) { //User found
            Tenant_Property tenant_property = new Tenant_Property();
            tenant_property.setFrom_date(temp.getString(2));
            tenant_property.setTo_date(temp.getString(3));
            properties_ids.add(Integer.parseInt(temp.getString(1)));
            information_to_be_displayed.add(tenant_property);
        }
        String renting_agency_id="";
        for(int i = 0; i < properties_ids.size();i++ ){
            //for one specific-id, get from property information, then the agency information
            Cursor temp2 = dataBaseHelper.get_property_information(properties_ids.get(i));
            while (temp2.moveToNext()) {
                information_to_be_displayed.get(i).setCity(temp2.getString(1));
                information_to_be_displayed.get(i).setPostal_address(temp2.getString(2));
                renting_agency_id = temp2.getString(12);
            }
            Cursor temp3 = dataBaseHelper.get_renting_agency(renting_agency_id);
            while (temp3.moveToNext()){
                information_to_be_displayed.get(i).setRenting_agency_name(temp3.getString(1));
            }
            TenantHistoryFragment tenant_history_fragment = new TenantHistoryFragment();
            Bundle bundle = new Bundle();
            bundle.putString("city", information_to_be_displayed.get(i).getCity());
            bundle.putString("postalAddress", information_to_be_displayed.get(i).getPostal_address());
            bundle.putString("from",information_to_be_displayed.get(i).getFrom_date());
            bundle.putString("to",information_to_be_displayed.get(i).getTo_date());
            bundle.putString("renting_agency_name",information_to_be_displayed.get(i).getRenting_agency_name());
            tenant_history_fragment.setArguments(bundle);
            tenantHistoryFragments.add(tenant_history_fragment);
            fragmentTransaction = fragmentManager.beginTransaction();
            //
            fragmentTransaction.add(R.id.tenant_history_scrollview_layout, tenantHistoryFragments.get(i),"Fragment_"+i);
            fragmentTransaction.commit();
            getSupportFragmentManager().executePendingTransactions();
            if(fragmentManager.findFragmentByTag("Fragment_"+i) != null)
                System.out.println("Not null from inside activity");
        }
        if(properties_ids.size() == 0){

            findViewById(R.id.tenant_history_scrollview).setVisibility(View.GONE);

            NoResultsFoundFragment  no_results_found_fragment = new NoResultsFoundFragment();
            Bundle bundle = new Bundle();
            bundle.putString("no_results_type", "tenant history");
            bundle.putString("email", tenant_email_address);
            no_results_found_fragment.setArguments(bundle);

            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.tenant_history_layout, no_results_found_fragment, "Fragment_");
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
        if(user_email_address.equals("username") || (user_type != 0 && user_type != 1)){
            // user is not authenticated so direct to the login page.
            Toast.makeText(TenantHistoryActivity.this, "User is not authenticated, you need to log in", Toast.LENGTH_SHORT).show();
            intent = new Intent(TenantHistoryActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

}