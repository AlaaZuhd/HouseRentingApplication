package com.example.lab_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lab_project.helpers.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {

    final FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;
    SharedPrefManager sharedPrefManager;
    Intent intent;

    int user_type;
    String user_email_address;

    Button profile_arrowback_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // get user authentication.
        get_user_authentication();

        profile_arrowback_button = (Button) findViewById(R.id.profile_arrowback_button);

        profile_arrowback_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Do the notification work and get the old animation
                intent = new Intent(ProfileActivity.this, NavigationDrawerActivity.class);
                startActivity(intent);
            }
        });

        // pass the email to each fragment.
        Bundle bundle = new Bundle();
        bundle.putString("email_address", user_email_address);
        Intent intent = getIntent();
        boolean view_mode = intent.getBooleanExtra("view_other_profile", false);
        String tenant_email_address = intent.getStringExtra("tenant_email_address");
        if(view_mode){
            bundle.putString("email_address", tenant_email_address);
            bundle.putBoolean("view_other_profile", view_mode);
        } else {
            bundle.putString("email_address", user_email_address);
            bundle.putBoolean("view_other_profile", false);
        }

        if(view_mode || user_type == 0){ // add tenant fragment layout
            TenantProfileFragment tenant_profile_fragment = new TenantProfileFragment();
            tenant_profile_fragment.setArguments(bundle);
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.profile_layout, tenant_profile_fragment, "Tenant_Profile_Fragment");
            fragmentTransaction.commit();
            getSupportFragmentManager().executePendingTransactions();
        } else if(user_type == 1) {
            RentingAgencyProfileFragment renting_agency_profile_fragment = new RentingAgencyProfileFragment();
            renting_agency_profile_fragment.setArguments(bundle);
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.profile_layout, renting_agency_profile_fragment, "TRenting_Agency_Profile_Fragment");
            fragmentTransaction.commit();
            getSupportFragmentManager().executePendingTransactions();
        }

    }

    public void get_user_authentication(){
        sharedPrefManager =SharedPrefManager.getInstance(this);
        user_email_address = sharedPrefManager.readString("username", "username");
        user_type = sharedPrefManager.readInt("type", 100);
        if(user_email_address.equals("username") || (user_type != 0 && user_type != 1)){
            // user is not authenticated so direct to the login page.
            Toast.makeText(ProfileActivity.this, "User is not authenticated, you need to log in", Toast.LENGTH_SHORT).show();
            intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}