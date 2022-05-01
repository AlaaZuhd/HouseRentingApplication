package com.example.lab_project.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab_project.NavigationDrawerActivity;
import com.example.lab_project.PostPropertyActivity;
import com.example.lab_project.ProfileActivity;
import com.example.lab_project.models.RentingAgency;
import com.example.lab_project.models.Tenant;

public class InitValidateProfileFormFields {


    public static DataBaseHelper dataBaseHelper_out;
    public static void init_references_init_listeners_for_tenant_fields(Tenant tenant,
                                     Context context,
                                     DataBaseHelper dataBaseHelper,
                                     LinearLayout profile_tenant_change_password_layout,
                                     Button profile_tenant_email_address_button,
                                     Button profile_tenant_first_name_button,
                                     Button profile_tenant_last_name_button,
                                     Button profile_tenant_occupation_button,
                                     Button profile_tenant_phone_number_button,
                                     Button profile_tenant_gender_button,
                                     Button profile_tenant_nationality_button,
                                     Button profile_tenant_current_residence_country_button,
                                     Button profile_tenant_city_button,
                                     Button profile_tenant_gross_monthly_salary_button,
                                     Button profile_tenant_family_size_button,
                                     Button profile_tenant_family_size_dec_button,
                                     Button profile_tenant_family_size_inc_button,
                                     Button profile_tenant_change_password_button,
                                    Button profile_tenant_email_address_cancel_button,
                                    Button profile_tenant_first_name_cancel_button,
                                    Button profile_tenant_last_name_cancel_button,
                                    Button profile_tenant_occupation_cancel_button,
                                    Button profile_tenant_phone_number_cancel_button,
                                    Button profile_tenant_gender_cancel_button,
                                    Button profile_tenant_nationality_cancel_button,
                                    Button profile_tenant_current_residence_country_cancel_button,
                                    Button profile_tenant_city_cancel_button,
                                    Button profile_tenant_gross_monthly_salary_cancel_button,
                                    Button profile_tenant_family_size_cancel_button,
                                    Button profile_tenant_change_password_cancel_button,
                                     EditText profile_tenant_email_address_editText,
                                     EditText profile_tenant_first_name_editText,
                                     EditText profile_tenant_last_name_editText,
                                     EditText profile_tenant_occupation_editText,
                                     EditText profile_tenant_family_size_editText,
                                     EditText profile_tenant_phone_number_editText,
                                     EditText profile_tenant_password_editText,
                                     EditText profile_tenant_new_password_editText,
                                     EditText profile_tenant_confirm_new_password_editText,
                                     EditText profile_tenant_gross_monthly_salary_editText,
                                     Spinner profile_tenant_gender_spinner,
                                     Spinner profile_tenant_nationality_spinner,
                                     Spinner profile_tenant_current_residence_country_spinner,
                                     Spinner profile_tenant_city_spinner,
                                                                        TextView tenant_area_code_textview,
                                                                        ImageView tenant_area_code_imageView,
                                                                        int [] area_code_flag_ids){


        dataBaseHelper_out = dataBaseHelper;
//        profile_tenant_email_address_editText.setText(tenant.getFamily_size());

        // ---------------------------------------------------------------------------------------- //
        // change save mode to edit mode and return the original data into the textview. and hide the cancel button
//        profile_tenant_email_address_cancel_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                profile_tenant_email_address_button.setText("Edit");
//                profile_tenant_email_address_editText.setText(tenant.getEmail_address());
//                profile_tenant_email_address_cancel_button.setVisibility(View.GONE);
//            }
//        });

        profile_tenant_first_name_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_tenant_first_name_button.setText("Edit");
                profile_tenant_first_name_editText.setText(tenant.getFirst_name());
                profile_tenant_first_name_editText.setEnabled(false);
                profile_tenant_first_name_cancel_button.setVisibility(View.GONE);
            }
        });

        profile_tenant_last_name_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_tenant_last_name_button.setText("Edit");
                profile_tenant_last_name_editText.setText(tenant.getLast_name());
                profile_tenant_last_name_editText.setEnabled(false);
                profile_tenant_last_name_cancel_button.setVisibility(View.GONE);
            }
        });

        profile_tenant_occupation_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_tenant_occupation_button.setText("Edit");
                profile_tenant_occupation_editText.setText(tenant.getOccupation());
                profile_tenant_occupation_editText.setEnabled(false);
                profile_tenant_occupation_cancel_button.setVisibility(View.GONE);
            }
        });

        profile_tenant_phone_number_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_tenant_phone_number_button.setText("Edit");
                profile_tenant_phone_number_editText.setText(tenant.getPhone_number() + "");
                profile_tenant_phone_number_editText.setEnabled(false);
                profile_tenant_phone_number_cancel_button.setVisibility(View.GONE);
            }
        });

        profile_tenant_gender_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_tenant_gender_button.setText("Edit");
                profile_tenant_gender_spinner.setSelection(Utils.get_gender_index(tenant.getGender()));
                profile_tenant_gender_spinner.setEnabled(false);
                profile_tenant_gender_cancel_button.setVisibility(View.GONE);
            }
        });

        profile_tenant_nationality_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_tenant_nationality_button.setText("Edit");
                profile_tenant_nationality_spinner.setSelection(Utils.get_nationality_index(tenant.getNationality()));
                profile_tenant_nationality_spinner.setEnabled(false);
                profile_tenant_nationality_cancel_button.setVisibility(View.GONE);
            }
        });

        profile_tenant_current_residence_country_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_tenant_current_residence_country_button.setText("Edit");
                profile_tenant_current_residence_country_spinner.setSelection(Utils.get_country_index(tenant.getCurrent_country()));
                profile_tenant_city_spinner.setSelection(Utils.get_city_index(tenant.getCity(), Utils.get_country_index(tenant.getCurrent_country())));
                profile_tenant_current_residence_country_spinner.setEnabled(false);
                profile_tenant_city_spinner.setEnabled(false);

                Utils.objArr = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item, Utils.city_options[Utils.get_country_index(tenant.getCurrent_country())]);
                profile_tenant_city_spinner.setAdapter(Utils.objArr);
                profile_tenant_city_spinner.setSelection(Utils.get_city_index(tenant.getCity(), Utils.get_country_index(tenant.getCurrent_country())));

                profile_tenant_current_residence_country_cancel_button.setVisibility(View.GONE);
            }
        });

        profile_tenant_city_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_tenant_city_button.setText("Edit");
                profile_tenant_city_spinner.setSelection(Utils.get_city_index(tenant.getCity(), Utils.get_country_index(tenant.getCurrent_country())));
                profile_tenant_city_spinner.setEnabled(false);
                profile_tenant_city_cancel_button.setVisibility(View.GONE);
            }
        });

        profile_tenant_gross_monthly_salary_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_tenant_gross_monthly_salary_button.setText("Edit");
                profile_tenant_gross_monthly_salary_editText.setText(tenant.getSalary() + "");
                profile_tenant_gross_monthly_salary_cancel_button.setVisibility(View.GONE);
            }
        });

        profile_tenant_family_size_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_tenant_family_size_button.setText("Edit");
                profile_tenant_family_size_editText.setText(tenant.getFamily_size() + "");
                profile_tenant_family_size_cancel_button.setVisibility(View.GONE);
                profile_tenant_family_size_dec_button.setEnabled(false);
                profile_tenant_family_size_inc_button.setEnabled(false);
                profile_tenant_family_size_editText.setEnabled(false);
            }
        });

        profile_tenant_change_password_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_tenant_change_password_layout.setVisibility(View.GONE);
                profile_tenant_change_password_cancel_button.setVisibility(View.GONE);
                profile_tenant_change_password_button.setText("Change Password");
                profile_tenant_password_editText.setText("");
                profile_tenant_new_password_editText.setText("");
                profile_tenant_confirm_new_password_editText.setText("");
            }
        });


        // ----------------------------------------------------------------------------//

//        profile_tenant_email_address_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(profile_tenant_email_address_button.getText().toString().equals("Edit")) {
//                    profile_tenant_email_address_button.setText("Save");
//                    profile_tenant_email_address_editText.setEnabled(true);
//                } else {
//                    profile_tenant_email_address_button.setText("Edit");
//                    profile_tenant_email_address_editText.setEnabled(false);
//                    // check if valid
//                    // if valid then update database.
//
//                    // if not valid or updating the database faild then return the old value.
//                }
//            }
//        });

        profile_tenant_first_name_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                if(profile_tenant_first_name_button.getText().toString().equals("Edit")) {
                    profile_tenant_first_name_button.setText("Save");
                    profile_tenant_first_name_editText.setEnabled(true);
                    profile_tenant_first_name_cancel_button.setVisibility(View.VISIBLE);
                } else {
                    if(!profile_tenant_first_name_editText.getText().toString().isEmpty() &&
                            Utils.validate_name(profile_tenant_first_name_editText.getText().toString(), 3, 20)){
                        // update database.
                        boolean res = update_tenant("FIRST_NAME",profile_tenant_first_name_editText.getText().toString(), tenant.getEmail_address());
                        flag = res? 1: 0;
                        if(flag == 1){
                            profile_tenant_first_name_button.setText("Edit");
                            profile_tenant_first_name_editText.setEnabled(false);
                            profile_tenant_first_name_cancel_button.setVisibility(View.GONE);
                            tenant.setFirst_name(profile_tenant_first_name_editText.getText().toString());
                        } else {{
                            Toast.makeText(context , "Problem in updating your profile", Toast.LENGTH_LONG).show();
                        }}
                    } else {
                        profile_tenant_first_name_editText.setError("Name must contain 3 to 20 characters");
                    }
                }
            }
        });


        profile_tenant_last_name_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                if (profile_tenant_last_name_button.getText().toString().equals("Edit")) {
                    profile_tenant_last_name_button.setText("Save");
                    profile_tenant_last_name_editText.setEnabled(true);
                    profile_tenant_last_name_cancel_button.setVisibility(View.VISIBLE);
                } else {
                    if(!profile_tenant_last_name_editText.getText().toString().isEmpty() &&
                            Utils.validate_name(profile_tenant_last_name_editText.getText().toString(), 3, 20)){
                        // update database.
                        boolean res = update_tenant("LAST_NAME",profile_tenant_last_name_editText.getText().toString(), tenant.getEmail_address());
                        flag = res? 1: 0;
                        if(flag == 1){
                            profile_tenant_last_name_button.setText("Edit");
                            profile_tenant_last_name_editText.setEnabled(false);
                            profile_tenant_last_name_cancel_button.setVisibility(View.GONE);
                            tenant.setLast_name(profile_tenant_last_name_editText.getText().toString());
                        } else {{
                            Toast.makeText(context , "Problem in updating your profile", Toast.LENGTH_LONG).show();
                        }}
                    } else {
                        profile_tenant_last_name_editText.setError("Name must contain 3 to 20 characters");
                    }
                }
            }
        });

        profile_tenant_occupation_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                if (profile_tenant_occupation_button.getText().toString().equals("Edit")) {
                    profile_tenant_occupation_button.setText("Save");
                    profile_tenant_occupation_editText.setEnabled(true);
                    profile_tenant_occupation_cancel_button.setVisibility(View.VISIBLE);
                } else {
                    if(!profile_tenant_occupation_editText.getText().toString().isEmpty() &&
                            Utils.validate_name(profile_tenant_occupation_editText.getText().toString(), 1, 20)){
                        // update database.
                        boolean res = update_tenant("OCCUPATION",profile_tenant_occupation_editText.getText().toString(), tenant.getEmail_address());
                        flag = res? 1: 0;
                        if(flag == 1){
                            profile_tenant_occupation_button.setText("Edit");
                            profile_tenant_occupation_editText.setEnabled(false);
                            profile_tenant_occupation_cancel_button.setVisibility(View.GONE);
                            tenant.setOccupation(profile_tenant_occupation_editText.getText().toString());
                        } else {{
                            Toast.makeText(context , "Problem in updating your profile", Toast.LENGTH_LONG).show();
                        }}
                    } else {
                        profile_tenant_occupation_editText.setError("Name must contain up tp 20 characters only");
                    }
                }
            }
        });

        profile_tenant_phone_number_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                if(profile_tenant_phone_number_button.getText().toString().equals("Edit")) {
                    profile_tenant_phone_number_button.setText("Save");
                    profile_tenant_phone_number_editText.setEnabled(true);
                    profile_tenant_phone_number_cancel_button.setVisibility(View.VISIBLE);
                } else {
                    if(!profile_tenant_phone_number_editText.getText().toString().isEmpty() &&
                            Utils.isPhoneNumber(profile_tenant_phone_number_editText.getText().toString())){
                        // update database.
                        boolean res = update_tenant("PHONE_NUMBER",profile_tenant_phone_number_editText.getText().toString(), tenant.getEmail_address());
                        flag = res? 1: 0;
                        if(flag == 1){
                            profile_tenant_phone_number_button.setText("Edit");
                            profile_tenant_phone_number_editText.setEnabled(false);
                            profile_tenant_phone_number_cancel_button.setVisibility(View.GONE);
                            tenant.setPhone_number(profile_tenant_phone_number_editText.getText().toString());
                        } else {{
                            Toast.makeText(context , "Problem in updating your profile", Toast.LENGTH_LONG).show();
                        }}
                    } else {
                        profile_tenant_phone_number_editText.setError("Not Valid phone number");
                    }
                }
            }
        });

        profile_tenant_gross_monthly_salary_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                if (profile_tenant_gross_monthly_salary_button.getText().toString().equals("Edit")) {
                    profile_tenant_gross_monthly_salary_button.setText("Save");
                    profile_tenant_gross_monthly_salary_editText.setEnabled(true);
                    profile_tenant_gross_monthly_salary_cancel_button.setVisibility(View.VISIBLE);
                } else {
                    if(!profile_tenant_gross_monthly_salary_editText.getText().toString().isEmpty() &&
                            Utils.isDoubleNumber(profile_tenant_gross_monthly_salary_editText.getText().toString())){
                        // update database.
                        boolean res = update_tenant("SALARY",profile_tenant_gross_monthly_salary_editText.getText().toString(), tenant.getEmail_address());
                        flag = res? 1: 0;
                        if(flag == 1){
                            profile_tenant_gross_monthly_salary_button.setText("Edit");
                            profile_tenant_gross_monthly_salary_editText.setEnabled(false);
                            profile_tenant_gross_monthly_salary_cancel_button.setVisibility(View.GONE);
                            tenant.setSalary(Double.parseDouble(profile_tenant_gross_monthly_salary_editText.getText().toString()));
                        } else {{
                            Toast.makeText(context , "Problem in updating your profile", Toast.LENGTH_LONG).show();
                        }}
                    } else {
                        profile_tenant_gross_monthly_salary_editText.setError("Not Valid salary");
                    }
                }
            }
        });

        profile_tenant_family_size_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                if (profile_tenant_family_size_button.getText().toString().equals("Edit")) {
                    profile_tenant_family_size_button.setText("Save");
                    profile_tenant_family_size_editText.setEnabled(true);
                    profile_tenant_family_size_dec_button.setEnabled(true);
                    profile_tenant_family_size_inc_button.setEnabled(true);
                    profile_tenant_family_size_cancel_button.setVisibility(View.VISIBLE);
                } else {
                    if(!profile_tenant_family_size_editText.getText().toString().isEmpty() &&
                            Utils.isNumeric(profile_tenant_family_size_editText.getText().toString())){
                        // update database.
                        boolean res = update_tenant("FAMILY_SIZE",profile_tenant_family_size_editText.getText().toString(), tenant.getEmail_address());
                        flag = res? 1: 0;
                        if(flag == 1){
                            profile_tenant_family_size_button.setText("Edit");
                            profile_tenant_family_size_editText.setEnabled(false);
                            profile_tenant_family_size_dec_button.setEnabled(false);
                            profile_tenant_family_size_inc_button.setEnabled(false);
                            profile_tenant_family_size_cancel_button.setVisibility(View.GONE);
                            tenant.setFamily_size(Integer.parseInt(profile_tenant_family_size_editText.getText().toString()));
                        } else {
                            Toast.makeText(context , "Problem in updating your profile", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        profile_tenant_family_size_editText.setError("Not Valid Family size");
                    }
                }
            }
        });

        profile_tenant_family_size_dec_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_tenant_family_size_editText.setText((Integer.parseInt(profile_tenant_family_size_editText.getText().toString()) - 1) +  "");
            }
        });

        profile_tenant_family_size_inc_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_tenant_family_size_editText.setText((Integer.parseInt(profile_tenant_family_size_editText.getText().toString()) + 1) + "");
            }
        });


        profile_tenant_change_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                if(profile_tenant_change_password_button.getText().toString().equals("Change Password")) {
                    profile_tenant_change_password_button.setText("Save Password");
                    profile_tenant_change_password_layout.setVisibility(View.VISIBLE);
                    profile_tenant_change_password_cancel_button.setVisibility(View.VISIBLE);
                } else {

                    if(profile_tenant_password_editText.getText().toString().equals(tenant.getPassword())) {
                        if (Utils.validate_password_format(profile_tenant_new_password_editText.getText().toString(), 8, 15)) {
                            // check if confirm password matches the password
                            if(profile_tenant_new_password_editText.getText().toString().equals(profile_tenant_confirm_new_password_editText.getText().toString())){
                                // update password;
                                String encrypted_password = "";
                                try {
                                    encrypted_password = AESEncryption_Decryption.encrypt(profile_tenant_new_password_editText.getText().toString());
                                    boolean res = update_tenant("PASSWORD",encrypted_password, tenant.getEmail_address());
                                    flag = res? 1: 0;
                                    if(flag == 1){
                                        profile_tenant_change_password_button.setText("Change Password");
                                        profile_tenant_change_password_cancel_button.setVisibility(View.GONE);
                                        profile_tenant_change_password_layout.setVisibility(View.GONE);
                                        profile_tenant_password_editText.setText("");
                                        profile_tenant_new_password_editText.setText("");
                                        profile_tenant_confirm_new_password_editText.setText("");
                                        tenant.setPassword(profile_tenant_new_password_editText.getText().toString());
                                    } else {
                                        Toast.makeText(context , "Problem in updating your profile", Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(context, "Error in decrypting tenant password", Toast.LENGTH_SHORT).show();
                                    // may go back to home.
                                    Intent intent = new Intent(context, NavigationDrawerActivity.class);
                                    context.startActivity(intent);
//                                    context.finish();
                                }

                            } else {
                                profile_tenant_confirm_new_password_editText.setError("Confirm password doesn't match new password");
                            }
                        } else {
                            profile_tenant_new_password_editText.setError("Invalid password: Password must contains at least one digit, one lowercase letter, one uppercase letter, one special character $%#@!{}");
                        }
                    } else {
                        profile_tenant_password_editText.setError("Invalid password");
                    }
                }
            }
        });

        profile_tenant_gender_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                if (profile_tenant_gender_button.getText().toString().equals("Edit")) {
                    profile_tenant_gender_button.setText("Save");
                    profile_tenant_gender_spinner.setEnabled(true);
                    profile_tenant_gender_cancel_button.setVisibility(View.VISIBLE);
                } else {
                    // update database.
                    boolean res = update_tenant("GENDER",profile_tenant_gender_spinner.getSelectedItem().toString(), tenant.getEmail_address());
                    flag = res? 1: 0;
                    if(flag == 1){
                        profile_tenant_gender_button.setText("Edit");
                        profile_tenant_gender_spinner.setEnabled(false);
                        profile_tenant_gender_cancel_button.setVisibility(View.GONE);
                        tenant.setGender(profile_tenant_gender_spinner.getSelectedItem().toString());
                    } else {
                        Toast.makeText(context , "Problem in updating your profile", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        profile_tenant_nationality_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                if (profile_tenant_nationality_button.getText().toString().equals("Edit")) {
                    profile_tenant_nationality_button.setText("Save");
                    profile_tenant_nationality_spinner.setEnabled(true);
                    profile_tenant_nationality_cancel_button.setVisibility(View.VISIBLE);
                } else {
                    // update database.
                    boolean res = update_tenant("NATIONALITY",profile_tenant_nationality_spinner.getSelectedItem().toString(), tenant.getEmail_address());
                    flag = res? 1: 0;
                    if(flag == 1){
                        profile_tenant_nationality_button.setText("Edit");
                        profile_tenant_nationality_spinner.setEnabled(false);
                        profile_tenant_nationality_cancel_button.setVisibility(View.GONE);
                        tenant.setNationality(profile_tenant_nationality_spinner.getSelectedItem().toString());
                    } else {
                        Toast.makeText(context , "Problem in updating your profile", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        profile_tenant_city_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                if (profile_tenant_city_button.getText().toString().equals("Edit")) {
                    profile_tenant_city_button.setText("Save");
                    profile_tenant_city_spinner.setEnabled(true);
                    profile_tenant_city_cancel_button.setVisibility(View.VISIBLE);
                } else {
                    // update database.
                    boolean res = update_tenant("CITY",profile_tenant_city_spinner.getSelectedItem().toString(), tenant.getEmail_address());
                    flag = res? 1: 0;
                    if(flag == 1){
                        profile_tenant_city_button.setText("Edit");
                        profile_tenant_city_spinner.setEnabled(false);
                        profile_tenant_city_cancel_button.setVisibility(View.GONE);
                        tenant.setCity(profile_tenant_city_spinner.getSelectedItem().toString());
                    } else {
                        Toast.makeText(context , "Problem in updating your profile", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        profile_tenant_current_residence_country_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                if (profile_tenant_current_residence_country_button.getText().toString().equals("Edit")) {
                    profile_tenant_current_residence_country_button.setText("Save");
                    profile_tenant_current_residence_country_spinner.setEnabled(true);
                    profile_tenant_city_spinner.setEnabled(true);
                    profile_tenant_current_residence_country_cancel_button.setVisibility(View.VISIBLE);
                } else {
                    // update database.
                    boolean res = update_tenant("CITY",profile_tenant_city_spinner.getSelectedItem().toString(), tenant.getEmail_address());
                    res = update_tenant("CURRENT_COUNTRY",profile_tenant_current_residence_country_spinner.getSelectedItem().toString(), tenant.getEmail_address());
                    flag = res? 1: 0;
                    if(flag == 1){
                        profile_tenant_current_residence_country_button.setText("Edit");
                        profile_tenant_current_residence_country_spinner.setEnabled(false);
                        profile_tenant_current_residence_country_cancel_button.setVisibility(View.GONE);
                        profile_tenant_city_spinner.setEnabled(false);
                        tenant.setCurrent_country(profile_tenant_current_residence_country_spinner.getSelectedItem().toString());
                        tenant.setCity(profile_tenant_city_spinner.getSelectedItem().toString());
                    } else {
                        Toast.makeText(context , "Problem in updating your profile", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        profile_tenant_current_residence_country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    int country_index = Utils.get_country_index(item.toString().trim());
                    if(country_index != -1) {
                        // update the city options and update the area code.
                        Utils.objArr = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item, Utils.city_options[country_index]);
                        profile_tenant_city_spinner.setAdapter(Utils.objArr);
                        update_area_code(tenant_area_code_imageView, tenant_area_code_textview, area_code_flag_ids, country_index);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
            }
        });

    }

    public static void update_area_code(ImageView imageview, TextView textview, int [] area_code_flag_ids, int index){
        textview.setText(Utils.area_codes[index] + "");
        imageview.setImageResource(area_code_flag_ids[index]);
    }

    public static boolean update_tenant(String field, String value, String tenant_id){
        ContentValues contentValues = new ContentValues();
        contentValues.put(field, value);
        return dataBaseHelper_out.update_tenant(contentValues, tenant_id);
    }









    // --------------------------------------------------------------------------------------- //
    //                                        Renting agency part                              //


    public static void init_references_init_listeners_for_renting_agency_fields
                        (RentingAgency renting_agency,
                         Context context,
                         DataBaseHelper dataBaseHelper,
                         LinearLayout profile_renting_agency_change_password_layout,
                         Button profile_renting_agency_email_address_button,
                         Button profile_renting_agency_name_button,
                         Button profile_renting_agency_phone_number_button,
                         Button profile_renting_agency_country_button,
                         Button profile_renting_agency_city_button,
                         Button profile_renting_agency_change_password_button,
                         Button profile_renting_agency_email_address_cancel_button,
                         Button profile_renting_agency_name_cancel_button,
                         Button profile_renting_agency_phone_number_cancel_button,
                         Button profile_renting_agency_country_cancel_button,
                         Button profile_renting_agency_city_cancel_button,
                         Button profile_renting_agency_change_password_cancel_button,
                         EditText profile_renting_agency_email_address_editText,
                         EditText profile_renting_agency_name_editText,
                         EditText profile_renting_agency_phone_number_editText,
                         EditText profile_renting_agency_password_editText,
                         EditText profile_renting_agency_new_password_editText,
                         EditText profile_renting_agency_confirm_new_password_editText,
                         Spinner profile_renting_agency_country_spinner,
                         Spinner profile_renting_agency_city_spinner,
                         TextView renting_agency_area_code_textview,
                         ImageView renting_agency_area_code_imageview,
                         int [] area_code_flag_ids){


        dataBaseHelper_out = dataBaseHelper;

        // ---------------------------------------------------------------------------------------- //
        // change save mode to edit mode and return the original data into the textview. and hide the cancel button
//        profile_renting_agency_email_address_cancel_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                profile_renting_agency_email_address_button.setText("Edit");
//                profile_renting_agency_email_address_editText.setText(renting_agency.getEmail_address());
//                profile_renting_agency_email_address_editText.setEnabled(false);
//                profile_renting_agency_email_address_cancel_button.setVisibility(View.GONE);
//            }
//        });

        profile_renting_agency_name_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_renting_agency_name_button.setText("Edit");
                profile_renting_agency_name_editText.setText(renting_agency.getName());
                profile_renting_agency_name_editText.setEnabled(false);
                profile_renting_agency_name_cancel_button.setVisibility(View.GONE);
            }
        });

        profile_renting_agency_phone_number_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_renting_agency_phone_number_button.setText("Edit");
                profile_renting_agency_phone_number_editText.setText(renting_agency.getPhone_number() + "");
                profile_renting_agency_phone_number_editText.setEnabled(false);
                profile_renting_agency_phone_number_cancel_button.setVisibility(View.GONE);
            }
        });

        profile_renting_agency_country_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_renting_agency_country_button.setText("Edit");
                profile_renting_agency_country_spinner.setSelection(Utils.get_country_index(renting_agency.getCountry()));
                profile_renting_agency_city_spinner.setSelection(Utils.get_city_index(renting_agency.getCity(), Utils.get_country_index(renting_agency.getCountry())));

                Utils.objArr = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item, Utils.city_options[Utils.get_country_index(renting_agency.getCountry())]);
                profile_renting_agency_city_spinner.setAdapter(Utils.objArr);
                profile_renting_agency_city_spinner.setSelection(Utils.get_city_index(renting_agency.getCity(), Utils.get_country_index(renting_agency.getCountry())));
                profile_renting_agency_country_spinner.setEnabled(false);
                profile_renting_agency_city_spinner.setEnabled(false);

                profile_renting_agency_country_cancel_button.setVisibility(View.GONE);
            }
        });

        profile_renting_agency_city_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_renting_agency_city_button.setText("Edit");
                profile_renting_agency_city_spinner.setSelection(Utils.get_city_index(renting_agency.getCity(), Utils.get_country_index(renting_agency.getCountry())));
                profile_renting_agency_city_spinner.setEnabled(false);
                profile_renting_agency_city_cancel_button.setVisibility(View.GONE);
            }
        });

        profile_renting_agency_change_password_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_renting_agency_change_password_layout.setVisibility(View.GONE);
                profile_renting_agency_change_password_cancel_button.setVisibility(View.GONE);
                profile_renting_agency_change_password_button.setText("Change Password");
                profile_renting_agency_password_editText.setText("");
                profile_renting_agency_new_password_editText.setText("");
                profile_renting_agency_confirm_new_password_editText.setText("");
            }
        });


        // ----------------------------------------------------------------------------//

//        profile_renting_agency_email_address_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(profile_renting_agency_email_address_button.getText().toString().equals("Edit")) {
//                    profile_renting_agency_email_address_button.setText("Save");
//                    profile_renting_agency_email_address_editText.setEnabled(true);
//                } else {
//                    profile_renting_agency_email_address_button.setText("Edit");
//                    profile_renting_agency_email_address_editText.setEnabled(false);
//                    // check if valid
//                    // if valid then update database.
//
//                    // if not valid or updating the database faild then return the old value.
//                }
//            }
//        });

        profile_renting_agency_name_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                if(profile_renting_agency_name_button.getText().toString().equals("Edit")) {
                    profile_renting_agency_name_button.setText("Save");
                    profile_renting_agency_name_editText.setEnabled(true);
                    profile_renting_agency_name_cancel_button.setVisibility(View.VISIBLE);
                } else {
                    if(!profile_renting_agency_name_editText.getText().toString().isEmpty() &&
                            Utils.validate_name(profile_renting_agency_name_editText.getText().toString(), 3, 20)){
                        // update database.
                        boolean res = update_renting_agency("NAME",profile_renting_agency_name_editText.getText().toString(), renting_agency.getEmail_address());
                        flag = res? 1: 0;
                        if(flag == 1){
                            profile_renting_agency_name_button.setText("Edit");
                            profile_renting_agency_name_editText.setEnabled(false);
                            profile_renting_agency_name_cancel_button.setVisibility(View.GONE);
                            renting_agency.setName(profile_renting_agency_name_editText.getText().toString());
                        } else {{
                            Toast.makeText(context , "Problem in updating your profile", Toast.LENGTH_LONG).show();
                        }}
                    } else {
                        profile_renting_agency_name_editText.setError("Name must contain 3 to 20 characters");
                    }
                }
            }
        });


        profile_renting_agency_phone_number_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                if(profile_renting_agency_phone_number_button.getText().toString().equals("Edit")) {
                    profile_renting_agency_phone_number_button.setText("Save");
                    profile_renting_agency_phone_number_editText.setEnabled(true);
                    profile_renting_agency_phone_number_cancel_button.setVisibility(View.VISIBLE);
                } else {
                    if(!profile_renting_agency_phone_number_editText.getText().toString().isEmpty() &&
                            Utils.isPhoneNumber(profile_renting_agency_phone_number_editText.getText().toString())){
                        // update database.
                        boolean res = update_renting_agency("PHONE_NUMBER",profile_renting_agency_phone_number_editText.getText().toString(), renting_agency.getEmail_address());
                        flag = res? 1: 0;
                        if(flag == 1){
                            profile_renting_agency_phone_number_button.setText("Edit");
                            profile_renting_agency_phone_number_editText.setEnabled(false);
                            profile_renting_agency_phone_number_cancel_button.setVisibility(View.GONE);
                            renting_agency.setPhone_number(profile_renting_agency_phone_number_editText.getText().toString());
                        } else {{
                            Toast.makeText(context , "Problem in updating your profile", Toast.LENGTH_LONG).show();
                        }}
                    } else {
                        profile_renting_agency_phone_number_editText.setError("Not Valid phone number");
                    }
                }
            }
        });


        profile_renting_agency_change_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                if(profile_renting_agency_change_password_button.getText().toString().equals("Change Password")) {
                    profile_renting_agency_change_password_button.setText("Save Password");
                    profile_renting_agency_change_password_layout.setVisibility(View.VISIBLE);
                    profile_renting_agency_change_password_cancel_button.setVisibility(View.VISIBLE);
                } else {

                    if(profile_renting_agency_password_editText.getText().toString().equals(renting_agency.getPassword())) {
                        if (Utils.validate_password_format(profile_renting_agency_new_password_editText.getText().toString(), 8, 15)) {
                            // check if confirm password matches the password
                            if(profile_renting_agency_new_password_editText.getText().toString().equals(profile_renting_agency_confirm_new_password_editText.getText().toString())){
                                // update password;
                                try {
                                    String encrypted_password = AESEncryption_Decryption.encrypt(profile_renting_agency_new_password_editText.getText().toString());
                                    boolean res = update_renting_agency("PASSWORD", encrypted_password, renting_agency.getEmail_address());
                                    flag = res ? 1 : 0;
                                    if (flag == 1) {
                                        profile_renting_agency_change_password_button.setText("Change Password");
                                        profile_renting_agency_change_password_cancel_button.setVisibility(View.GONE);
                                        profile_renting_agency_change_password_layout.setVisibility(View.GONE);
                                        profile_renting_agency_password_editText.setText("");
                                        profile_renting_agency_new_password_editText.setText("");
                                        profile_renting_agency_confirm_new_password_editText.setText("");
                                        renting_agency.setPassword(profile_renting_agency_new_password_editText.getText().toString());
                                    } else {
                                        Toast.makeText(context, "Problem in updating your profile", Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(context, "Error in decrypting tenant password", Toast.LENGTH_SHORT).show();
                                    // may go back to home.
                                    Intent intent = new Intent(context, NavigationDrawerActivity.class);
                                    context.startActivity(intent) ;
                                    //context.finish();
                                }

                            } else {
                                profile_renting_agency_confirm_new_password_editText.setError("Confirm password doesn't match new password");
                            }
                        } else {
                            profile_renting_agency_new_password_editText.setError("Invalid password: Password must contains at least one digit, one lowercase letter, one uppercase letter, one special character $%#@!{}");
                        }
                    } else {
                        profile_renting_agency_password_editText.setError("Invalid password");
                    }
                }
            }
        });

        profile_renting_agency_city_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                if (profile_renting_agency_city_button.getText().toString().equals("Edit")) {
                    profile_renting_agency_city_button.setText("Save");
                    profile_renting_agency_city_spinner.setEnabled(true);
                    profile_renting_agency_city_cancel_button.setVisibility(View.VISIBLE);
                } else {
                    // update database.
                    boolean res = update_renting_agency("CITY",profile_renting_agency_city_spinner.getSelectedItem().toString(), renting_agency.getEmail_address());
                    flag = res? 1: 0;
                    if(flag == 1){
                        profile_renting_agency_city_button.setText("Edit");
                        profile_renting_agency_city_spinner.setEnabled(false);
                        profile_renting_agency_city_cancel_button.setVisibility(View.GONE);
                        renting_agency.setCity(profile_renting_agency_city_spinner.getSelectedItem().toString());
                    } else {
                        Toast.makeText(context , "Problem in updating your profile", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        profile_renting_agency_country_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                if (profile_renting_agency_country_button.getText().toString().equals("Edit")) {
                    profile_renting_agency_country_button.setText("Save");
                    profile_renting_agency_country_spinner.setEnabled(true);
                    profile_renting_agency_city_spinner.setEnabled(true);
                    profile_renting_agency_country_cancel_button.setVisibility(View.VISIBLE);
                } else {
                    // update database.
                    boolean res = update_renting_agency("CITY",profile_renting_agency_city_spinner.getSelectedItem().toString(), renting_agency.getEmail_address());
                    res = update_renting_agency("COUNTRY",profile_renting_agency_country_spinner.getSelectedItem().toString(), renting_agency.getEmail_address());
                    flag = res? 1: 0;
                    if(flag == 1){
                        profile_renting_agency_country_button.setText("Edit");
                        profile_renting_agency_country_spinner.setEnabled(false);
                        profile_renting_agency_country_cancel_button.setVisibility(View.GONE);
                        profile_renting_agency_city_spinner.setEnabled(false);
                        renting_agency.setCountry(profile_renting_agency_country_spinner.getSelectedItem().toString());
                        renting_agency.setCity(profile_renting_agency_city_spinner.getSelectedItem().toString());
                    } else {
                        Toast.makeText(context , "Problem in updating your profile", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        profile_renting_agency_country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    int country_index = Utils.get_country_index(item.toString().trim());
                    if(country_index != -1) {
                        // update the city options and update the area code.
                        Utils.objArr = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item, Utils.city_options[country_index]);
                        profile_renting_agency_city_spinner.setAdapter(Utils.objArr);
                        update_area_code(renting_agency_area_code_imageview, renting_agency_area_code_textview, area_code_flag_ids, country_index);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
            }
        });

    }

    public static boolean update_renting_agency(String field, String value, String tenant_id){
        ContentValues contentValues = new ContentValues();
        contentValues.put(field, value);
        return dataBaseHelper_out.update_renting_agency(contentValues, tenant_id);
    }
















}
