package com.example.lab_project;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.lab_project.helpers.DatePicker;
import com.example.lab_project.helpers.Utils;

public class InitValidatePostFormFields {

    public static void init_listeners(Switch property_status_switch,
                                      Switch property_balcony_switch,
                                      Switch property_garden_switch,
                                      EditText property_city_editText,
                                      Button   property_availability_date_button,
                                      EditText property_availability_date_editText,
                                      EditText property_construction_year_editText,
                                      EditText property_description_editText,
                                      EditText property_number_of_bedrooms_editText,
                                      EditText property_postal_address_editText,
                                      EditText property_rental_price_editText,
                                      EditText property_surface_area_editText,
                                      TextView property_availability_date_textView){

        property_status_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked==true) {
                    property_status_switch.setText("Finished");
                    if(property_availability_date_button != null) {
                        property_availability_date_button.setVisibility(View.GONE);
                    } else {
                        property_availability_date_editText.setVisibility(View.GONE);
                    }
                    property_availability_date_textView.setVisibility(View.GONE);
                }
                else {
                    property_status_switch.setText("UnFinished");
                    if(property_availability_date_button != null) {
                        property_availability_date_button.setVisibility(View.VISIBLE);
                    } else {
                        property_availability_date_editText.setVisibility(View.VISIBLE);
                    }
                    property_availability_date_textView.setVisibility(View.VISIBLE);
                }
            }
        });


        property_balcony_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked==true)
                    property_balcony_switch.setText("Yes");
                else
                    property_balcony_switch.setText("No");
            }
        });

        property_garden_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked==true)
                    property_garden_switch.setText("Yes");
                else
                    property_garden_switch.setText("No");
            }
        });

        property_city_editText.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    return true;
                }
                return false;
            }
        });

//        property_availability_date_editText.setOnKeyListener(new EditText.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//                    return true;
//                }
//                return false;
//            }
//        });


        property_construction_year_editText.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    return true;
                }
                return false;
            }
        });

        property_description_editText.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    return true;
                }
                return false;
            }
        });

        property_number_of_bedrooms_editText.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    return true;
                }
                return false;
            }
        });

        property_postal_address_editText.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    return true;
                }
                return false;
            }
        });

        property_rental_price_editText.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    return true;
                }
                return false;
            }
        });

        property_surface_area_editText.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    return true;
                }
                return false;
            }
        });
    }

    public static boolean check_property_form_fields(Switch property_status_switch,
                                                     Switch property_balcony_switch,
                                                     Switch property_garden_switch,
                                                     EditText property_city_editText,
                                                     Button property_availability_date_button,
                                                     EditText property_availability_date_editText,
                                                     EditText property_construction_year_editText,
                                                     EditText property_description_editText,
                                                     EditText property_number_of_bedrooms_editText,
                                                     EditText property_postal_address_editText,
                                                     EditText property_rental_price_editText,
                                                     EditText property_surface_area_editText) {
        int valid = 1;
        if(Utils.isEmpty(property_city_editText.getText().toString())){
            valid = 0;
            property_city_editText.setError("This field can't be empty!");
        }

        if(!property_status_switch.isChecked()) { // check availability date just in case the status is off.
            if(property_availability_date_button != null) {
                if (property_availability_date_button.getText().toString().trim().equalsIgnoreCase("Select Date")) {
                    valid = 0;
                    property_availability_date_button.setError("Please select a Date!");
                } else if (!Utils.isAvailabilityDateValid(property_availability_date_button.getText().toString())) {
                    valid = 0;
                    property_availability_date_button.setError("Date format is not valid. Date format must be: YY-MM-DD");
                }
            } else {
                if (Utils.isEmpty(property_availability_date_editText.getText().toString().trim())) {
                    valid = 0;
                    property_availability_date_editText.setError("Please select a Date!");
                } else if (!Utils.isAvailabilityDateValid(property_availability_date_editText.getText().toString())) {
                    valid = 0;
                    property_availability_date_editText.setError("Date format is not valid. Date format must be: YY-MM-DD");
                }
            }
        }

        if(Utils.isEmpty(property_postal_address_editText.getText().toString())){
            valid = 0;
            property_postal_address_editText.setError("This field can't be empty!");
        } else if (!Utils.isNumeric(property_postal_address_editText.getText().toString())) {
            valid = 0;
            property_postal_address_editText.setError("Postal address must be a number!");
        }

        if(Utils.isEmpty(property_number_of_bedrooms_editText.getText().toString())){
            valid = 0;
            property_number_of_bedrooms_editText.setError("This field can't be empty!");
        }

        if(Utils.isEmpty(property_surface_area_editText.getText().toString())) {
            valid = 0;
            property_surface_area_editText.setError("This field can't be empty!");
        }

        if(Utils.isEmpty(property_construction_year_editText.getText().toString())){
            valid = 0;
            property_construction_year_editText.setError("This field can't be empty!");
        }

        if(Utils.isEmpty(property_rental_price_editText.getText().toString())){
            valid = 0;
            property_rental_price_editText.setError("This field can't be empty!");
        }

        if(Utils.isEmpty(property_description_editText.getText().toString())){
            valid = 0;
            property_description_editText.setError("This field can't be empty!");
        }

        if(valid == 1)
            return true;
        else
            return false;
    }

}
