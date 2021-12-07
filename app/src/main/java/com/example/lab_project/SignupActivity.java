package com.example.lab_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.health.SystemHealthManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.example.lab_project.models.RentingAgency;
import com.example.lab_project.models.Tenant;
import com.hbb20.CountryCodePicker;

//import com.hbb20.CountryCodePicker;


public class SignupActivity extends AppCompatActivity {

    Intent intent;

    EditText    tenant_email_address,
                tenant_first_name,
                tenant_last_name,
                tenant_password,
                tenant_confirm_password,
                tenant_gross_monthly_salary,
                tenant_occupation,
                tenant_family_size,
                tenant_phone_number;
    Spinner     tenant_gender,
                tenant_nationality,
                tenant_current_residence_country,
                tenant_city;

    EditText    renting_agency_email_address,
                renting_agency_name,
                renting_agency_password,
                renting_agency_confirm_password,
                renting_agency_phone_number;
    Spinner     renting_agency_country,
                renting_agency_city;

    String[]    gender_options = { "Male", "Female" };
    String[]    nationality_options = { "Palestinian", "Jordanian", "Egyptian", "Syrian", "Lebanese", "American", "Brazilian", "Japanese", "Spain" };
    String[]    current_residence_country_options = { "Palestine", "Jordan", "Egypt", "Syria", "Lebanon", "USA", "Brazil", "Japan", "Spain"};
    String[][]  city_options = { {"Ramallah", "Jerusalem", "Salfeet"},
                                {"Amman", "JordanX", "JordanY"},
                                {"EgyptX", "EgyptY"},
                                {"SyriaX", "SyriaY", "SyriaZ"},
                                {"LebanonX", "LebanonY", "LebanonZ"},
                                {"USAX", "USAY"},
                                {"BrazilX", "BrazilY", "BrazilZ"},
                                {"JapanX", "JapanY"},
                                {"SpainX", "SpainY", "SpainZ"}};
    String []   area_codes = {"+00970", "+00962", "+20", "+00963", "+961", "+1", "+55", "+81", "+34"};

    ArrayAdapter<String> objArr;

    int index =0;

    DataBaseHelper dataBaseHelper =new DataBaseHelper(SignupActivity.this,"Lab_Project.db",null,1);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button signup_as_tenant = (Button)findViewById(R.id.signup_tenant_button);
        Button signup_as_renting_agency = (Button)findViewById(R.id.signup_renting_agency_button);
        ScrollView tenant_scroll_view = (ScrollView)findViewById(R.id.signup_tenant_scrollView);
        ScrollView renting_agency_scroll_view = (ScrollView)findViewById(R.id.signup_renting_agency_scrollView);
        Button signup_tenant_create_account = (Button)findViewById(R.id.signup_tenant_create_account_button);
        Button signup_renting_agency_create_account = (Button)findViewById(R.id.signup_renting_agency_create_account_button);
        // initially the signup forms are hidden. (View.GONE: This view is invisible, and it doesn't take any space for layout purposes.)
        tenant_scroll_view.setVisibility(View.GONE);
        renting_agency_scroll_view.setVisibility(View.GONE);

        // references for fields in tenant sigup form

        tenant_email_address = (EditText)findViewById(R.id.signup_tenant_email);
        tenant_first_name = (EditText)findViewById(R.id.signup_tenant_name_editText);
        tenant_last_name = (EditText)findViewById(R.id.singup_tenant_last_name_editText);
        tenant_password = (EditText)findViewById(R.id.signup_tenant_password_editText);
        tenant_confirm_password = (EditText)findViewById(R.id.signup_tenant_confirm_password_editText);
        tenant_gross_monthly_salary = (EditText)findViewById(R.id.signup_tenant_gross_monthly_salary_editText);
        tenant_occupation = (EditText)findViewById(R.id.signup_tenant_occupation_editText);
        tenant_family_size = (EditText)findViewById(R.id.signup_tenant_family_size_editText);
        tenant_phone_number = (EditText)findViewById(R.id.signup_tenant_phone_number_editText);

        tenant_gender = (Spinner)findViewById(R.id.signup_tenant_gender_spinner);
        objArr = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, gender_options);
        tenant_gender.setAdapter(objArr);

        tenant_nationality = (Spinner)findViewById(R.id.signup_tenant_nationality_spinner);
        objArr = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, nationality_options);
        tenant_nationality.setAdapter(objArr);

        tenant_current_residence_country = (Spinner)findViewById(R.id.signup_tenant_country_spinner);
        objArr = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, current_residence_country_options);
        tenant_current_residence_country.setAdapter(objArr);

        tenant_city = (Spinner)findViewById(R.id.signup_tenant_city_spinner);
        objArr = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, city_options[0]);
        tenant_city.setAdapter(objArr);
        // ----------------------------------------------------------------------------------------------------//

        // references for fields in renting agency signup form

        renting_agency_email_address = (EditText)findViewById(R.id.signup_renting_agency_email);
        renting_agency_name = (EditText)findViewById(R.id.signup_renting_agency_name_editText);
        renting_agency_password = (EditText)findViewById(R.id.signup_renting_agency_password_editText);
        renting_agency_confirm_password = (EditText)findViewById(R.id.signup_renting_agency_confirm_password_editText);
        renting_agency_phone_number = (EditText)findViewById(R.id.signup_renting_agency_phone_number_editText);

        renting_agency_country = (Spinner)findViewById(R.id.signup_renting_agency_country_spinner);
        objArr = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, current_residence_country_options);
        renting_agency_country.setAdapter(objArr);

        renting_agency_city = (Spinner)findViewById(R.id.signup_renting_agency_city_spinner);
        objArr = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, city_options[0]);
        renting_agency_city.setAdapter(objArr);

        // ------------------------------------------------------------------------------------------------------//

        tenant_current_residence_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    index = findIndex(item.toString().trim());
                    if(index != -1) {
                        // update the city options and update the area code.
                        update_city_sipnner();
                        update_area_code();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
            }
        });

        renting_agency_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    index = findIndex(item.toString().trim());
                    if(index != -1) {
                        // update the city options
                        update_city_sipnner();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
            }
        });




//        CountryCodePicker ccp = (CountryCodePicker)findViewById(R.id.ccp);
//        System.out.println("CCP: " + ccp.getSelectedCountryCode());
//        //ccp.setCcpDialogShowNameCode(false);
//        ccp.setShowPhoneCode(false);





        // generate all countries
//        Locale[] locales = Locale.getAvailableLocales();
//        ArrayList<String> countries = new ArrayList<String>();
//        for (Locale locale : locales) {
//            String country = locale.getDisplayCountry();
//            if (country.trim().length()>0 && !countries.contains(country)) {
//                countries.add(country);
//            }
//        }
//        Collections.sort(countries);
//        for (String country : countries) {
//            System.out.println(country);
//        }





        signup_as_tenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tenant_scroll_view.setVisibility(View.VISIBLE);
                renting_agency_scroll_view.setVisibility(View.GONE);
            }
        });

        signup_as_renting_agency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renting_agency_scroll_view.setVisibility(View.VISIBLE);
                tenant_scroll_view.setVisibility(View.GONE);
            }
        });

        signup_tenant_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean are_data_fields_valid = validate_tenant_fields();
                if (are_data_fields_valid) {
                    // add the user into the data base
                    Tenant new_tenant = new Tenant(tenant_email_address.getText().toString(),
                                        tenant_first_name.getText().toString(),
                                        tenant_last_name.getText().toString(),
                                        tenant_gender.getSelectedItem().toString(),
                                        tenant_password.getText().toString(),
                                        tenant_confirm_password.getText().toString(),
                                        tenant_nationality.getSelectedItem().toString(),
                                        Double.parseDouble(tenant_gross_monthly_salary.getText().toString()),
                                        tenant_occupation.getText().toString(),
                                        Integer.parseInt(tenant_family_size.getText().toString()),
                                        tenant_current_residence_country.getSelectedItem().toString(),
                                        tenant_city.getSelectedItem().toString(),
                                        tenant_phone_number.getText().toString());
                    dataBaseHelper.insert_tenant(new_tenant);
                    // go into the login page,
                    intent = new Intent(SignupActivity.this, Login.class);
                    startActivity(intent);
                    finish(); // finish the current activity so user can't back to it again.
                }
            }
        });

        signup_renting_agency_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean are_data_fields_valid = validate_renting_agency_fields();
                if (are_data_fields_valid) {
                    // add the user into the data base
                    RentingAgency new_renting_agency = new RentingAgency(renting_agency_email_address.getText().toString(),
                                                        renting_agency_name.getText().toString(),
                                                        renting_agency_password.getText().toString(),
                                                        renting_agency_confirm_password.getText().toString(),
                                                        renting_agency_country.getSelectedItem().toString(),
                                                        renting_agency_city.getSelectedItem().toString(),
                                                        renting_agency_phone_number.getText().toString());
                    dataBaseHelper.insert_renting_agency(new_renting_agency);
                    // go into the login page,
                    intent = new Intent(SignupActivity.this, Login.class);
                    startActivity(intent);
                    finish(); // finish the current activity so user can't back to it again.
                }
            }
        });

    }

    public void update_city_sipnner(){
        objArr = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, city_options[index]);
        tenant_city.setAdapter(objArr);
    }

    public void update_area_code(){
        objArr = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, city_options[index]);
        tenant_city.setAdapter(objArr);
    }

    public int findIndex(String selectedCountry) {
        for(int i=0; i<current_residence_country_options.length; i++){
            if(current_residence_country_options[i].equals(selectedCountry)){
                return i;
            }
        }
        return -1;
    }

    public boolean validate_tenant_fields() {
        boolean is_email_valid = Utils.validateEmailAddress(tenant_email_address.getText().toString(), dataBaseHelper);
        boolean is_first_name_valid = Utils.validate_name(tenant_first_name.getText().toString(), 3, 20);
        boolean is_last_name_valid = Utils.validate_name(tenant_last_name.getText().toString(), 3, 20);
        boolean is_gender_valid = tenant_gender.getSelectedItem().toString().isEmpty() ? false: true;
        boolean is_password_format_valid = Utils.validate_password_format(tenant_password.getText().toString(), 8, 15);
        boolean is_confirm_password_valid = tenant_password.getText().toString().equals(tenant_confirm_password.getText().toString()) ? true : false;
        boolean is_gross_monthly_salary_valid = Utils.isDoubleNumber(tenant_gross_monthly_salary.getText().toString());
        boolean is_occupation_valid = Utils.validate_name(tenant_occupation.getText().toString(), 1, 20);
        boolean is_family_size_valid = Utils.isNumeric(tenant_family_size.getText().toString());
        boolean is_phone_number_valid = Utils.isNumeric(tenant_phone_number.getText().toString());
        boolean is_city_valid = true;
        boolean is_nationality_valid = true;
        boolean is_current_country_valid = true;
        // i think no need since by default there is an options that's selected.
        // -- spinner
        // -- spinner
        // -- spinner
        boolean are_all_fields_valid = true;
        if(!is_email_valid){
            are_all_fields_valid = false;
            tenant_email_address.setError("Invalid email address");
            tenant_email_address.setBackgroundColor(Color.parseColor("#66FF0000"));
        }
        if(!is_first_name_valid){
            are_all_fields_valid = false;
            tenant_first_name.setError("Invalid name: name must consists at min 8 character and at max 20 characters");
            tenant_first_name.setBackgroundColor(Color.parseColor("#66FF0000"));
        }
        if(!is_last_name_valid){
            are_all_fields_valid = false;
            tenant_last_name.setError("Invalid name: name must consists at min 8 character and at max 20 characters");
            tenant_last_name.setBackgroundColor(Color.parseColor("#66FF0000"));
        }
        if(!is_gender_valid){
            are_all_fields_valid = false;
            // display error msg or mark the field in red.
            tenant_gender.setBackgroundColor(Color.parseColor("#66FF0000"));
        }
        if(!is_password_format_valid){
            are_all_fields_valid = false;
            tenant_password.setError("Invalid password: Password must contains at least one digit, one lowercase letter, one uppercase letter, one special character $%#@!{}");
            tenant_password.setBackgroundColor(Color.parseColor("#66FF0000"));
        }
        if(!is_confirm_password_valid){
            are_all_fields_valid = false;
            tenant_confirm_password.setError("Invalid confirm password: confirm password doesn't match with thr password");
            tenant_confirm_password.setBackgroundColor(Color.parseColor("#66FF0000"));
        }
        if(!is_gross_monthly_salary_valid){
            are_all_fields_valid = false;
            tenant_gross_monthly_salary.setError("Invalid gross monthly salary");
            tenant_gross_monthly_salary.setBackgroundColor(Color.parseColor("#66FF0000"));
        }
        if(!is_occupation_valid){
            are_all_fields_valid = false;
            tenant_occupation.setError("Invalid occupation: occupation name must consists of at max 20 characters");
            tenant_occupation.setBackgroundColor(Color.parseColor("#66FF0000"));
        }
        if(!is_family_size_valid){
            are_all_fields_valid = false;
            tenant_family_size.setError("Invalid family size");
            tenant_family_size.setBackgroundColor(Color.parseColor("#66FF0000"));
        }
        if(!is_phone_number_valid){
            are_all_fields_valid = false;
            tenant_phone_number.setError("Invalid phone number");
            tenant_phone_number.setBackgroundColor(Color.parseColor("#66FF0000"));
        }
        if(!is_city_valid){
            are_all_fields_valid = false;
            //tenant_occupation.setError("Invalid occupation: occupation name must consists of at max 20 characters");
            tenant_city.setBackgroundColor(Color.parseColor("#66FF0000"));
        }
        if(!is_current_country_valid){
            are_all_fields_valid = false;
            //tenant_family_size.setError("Invalid family size");
            tenant_current_residence_country.setBackgroundColor(Color.parseColor("#66FF0000"));
        }
        if(!is_nationality_valid){
            are_all_fields_valid = false;
            //tenant_phone_number.setError("Invalid phone number");
            tenant_nationality.setBackgroundColor(Color.parseColor("#66FF0000"));
        }

        return are_all_fields_valid;
    }

    public boolean validate_renting_agency_fields(){
        boolean is_email_valid = Utils.validateEmailAddress(renting_agency_email_address.getText().toString(), dataBaseHelper);
        boolean is_name_valid = Utils.validate_name(renting_agency_name.getText().toString(), 1, 20);
        boolean is_password_format_valid = Utils.validate_password_format(renting_agency_password.getText().toString(), 8, 15);
        boolean is_confirm_password_valid = renting_agency_password.getText().toString().equals(renting_agency_confirm_password.getText().toString()) ? true : false;
        boolean is_phone_number_valid = Utils.isNumeric(renting_agency_phone_number.getText().toString());
        // check spinners selection (or no need).
        boolean are_all_fields_valid = true;
        if(!is_email_valid){
            are_all_fields_valid = false;
            renting_agency_email_address.setError("Invalid email");
            renting_agency_email_address.setBackgroundColor(Color.parseColor("#66FF0000"));
        }
        if(!is_name_valid){
            are_all_fields_valid = false;
            renting_agency_name.setError("Invalid name: name must consists at max 20 characters");
            renting_agency_name.setBackgroundColor(Color.parseColor("#66FF0000"));
        }
        if(!is_password_format_valid){
            are_all_fields_valid = false;
            renting_agency_password.setError("Invalid password: Password must contains at least one digit, one lowercase letter, one uppercase letter, one special character $%#@!{}");
            renting_agency_password.setBackgroundColor(Color.parseColor("#66FF0000"));
        }
        if(!is_confirm_password_valid){
            are_all_fields_valid = false;
            renting_agency_confirm_password.setError("Invalid confirm password: confirm password doesn't match with thr password");
            renting_agency_confirm_password.setBackgroundColor(Color.parseColor("#66FF0000"));
        }
        if(!is_phone_number_valid){
            are_all_fields_valid = false;
            renting_agency_phone_number.setError("Invalid phone number");
            renting_agency_phone_number.setBackgroundColor(Color.parseColor("#66FF0000"));
        }
        return are_all_fields_valid;
    }

}