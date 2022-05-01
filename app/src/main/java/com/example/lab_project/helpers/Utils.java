package com.example.lab_project.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.lab_project.HomeActivity;
import com.example.lab_project.ProfileActivity;
import com.example.lab_project.models.Property;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

public class Utils {


    public static String[]    gender_options = { "Male", "Female" };
    public static String[]    nationality_options = { "Palestinian", "Jordanian", "Egyptian", "Syrian", "Lebanese", "American", "Brazilian", "Japanese", "Spain" };
    public static String[]    current_residence_country_options = { "Palestine", "Jordan", "Egypt", "Syria", "Lebanon", "USA", "Brazil", "Japan", "Spain"};
    public static String[][]  city_options = {  {"Ramallah", "Jerusalem", "Salfeet"},
                                                {"Amman", "Jerash", "Zarqa"},
                                                {"Cairo", "Alexandria", "Aswan"},
                                                {"Damascus", "Homs", "Aleppo"},
                                                {"Beirut", "Tripoli", "Sidon"},
                                                {"New York", "Chicago"},
                                                {"Rio De Janeiro", "Manaus"},
                                                {"Tokyo", "Osaka"},
                                                {"Madrid", "Barcelona"}};
    public static String []   area_codes = {"+00970", "+00962", "+20", "+00963", "+961", "+1", "+55", "+81", "+34"};

    public static ArrayAdapter<String> objArr;

    public static int counter = 0;


    public static boolean validateEmailAddress(String email_address, DataBaseHelper dataBaseHelper) {
        // validate email address:
        // 1- ensue that the entered email have the email format.
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email_address.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email_address).matches()) {
            // 2- then ensure that the email is not registered before.
            //Connect to database and query on the tenant table for the given email
            Cursor temp = dataBaseHelper.get_tenant(email_address);
            if (!temp.moveToNext()) {
                temp = dataBaseHelper.get_renting_agency(email_address);
                if (!temp.moveToNext()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean validate_name(String name, int min_char_number, int max_char_number) {
        int name_length = name.trim().length();
        if(name_length>=min_char_number && name_length<=max_char_number)
            return true;
        return false;
    }

    public static boolean validate_password_format(String password, int min_char_number, int max_char_number) {
        int password_length = password.length();
        String numRegex = ".*[0-9].*";
        String lowerRegex = ".*[a-z].*";
        String upperRegex = ".*[A-Z].*";
        String specialRegex = ".*[@#$!%}{].*";
        if(password_length >= min_char_number && password_length <= max_char_number) {
            if(password.matches(numRegex)&&password.matches(lowerRegex)&&password.matches(upperRegex)&&password.matches(specialRegex)){
                return true;
            }
        }
        return false;
    }


    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static boolean isDoubleNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static boolean isValidDate(String date){
        try{
            Date date_obj = Date.valueOf(date);
            return true;
        }catch (Exception exp){
            return false;
        }
    }

    public static Date get_current_date(){
        long millis=System.currentTimeMillis();
        System.out.println("Date: " + new java.sql.Date(millis));
        return  new java.sql.Date(millis);
//        return new java.sql.Date(Calendar.getInstance().getTime().getTime());
    }

    public static boolean compare_dates(Date date1, Date date2 ){
        if(date2.before(date1)){
            return false;
        }
        return true;
    }

    public static boolean isAvailabilityDateValid(String date) {
        // check if date is valid.
        if(isValidDate(date)){
            Date date_obj = Date.valueOf(date);
            // check if is it's for present and future dates.
            //java.sql.Date today_date = new java.sql.Date(new java.util.Date);
            java.sql.Date today_date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            if(date_obj.before(today_date)){
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String data) {
        if(data.length() == 0)
            return true;
        else return false;
    }

    public static boolean isPhoneNumber(String str){
        String numRegex = "[0-9]+";
        if(str.matches(numRegex)){
            return true;
        }
        return false;
    }


    public static boolean sql_string_to_boolean(String str) {
        if("1".equals(str)) return true;
        else return false;
    }

    public static int get_city_index(String city, int country_index){
        for(int i=0; i<city_options[country_index].length; i++){
            if(city_options[country_index][i].equals(city))
                return i;
        }
        return -1; // not found.
    }

    public static int get_country_index(String country){
        for(int i=0; i<current_residence_country_options.length; i++){
            if(current_residence_country_options[i].equals(country))
                return i;
        }
        return -1; // not found.
    }

    public static int get_nationality_index(String nationality){
        for(int i=0; i<nationality_options.length; i++){
            if(nationality_options[i].equals(nationality))
                return i;
        }
        return -1; // not found
    }


    public static int get_gender_index(String gender){
        for(int i=0; i<gender_options.length; i++){
            if(gender_options[i].equals(gender))
                return i;
        }
        return -1; // not found
    }

    public static String convert_date_to_String(Date date, String format){
        DateFormat df = new SimpleDateFormat(format);
        String str_date = df.format(date);
        System.out.println("STR: " + str_date);
        return str_date;
    }

    public static boolean check_update_is_active_property(Property property, DataBaseHelper dataBaseHelper, Context context) {
        // is this property rented before or not.
        Cursor temp = dataBaseHelper.get_tenants_renting_specific_property(property.getProperty_id());
        int flag = 0;
        while (temp.moveToNext()) {
            flag = 1;
            break;
        }
        if(flag == 0){ //  if it's not rented before check the availability date
            // if it was available to be rented for up to 90 days then deactivate it.
            // get the difference between the current date and the availability date.
            if(!property.isIs_active())
                return false;
            long days = days_between_two_dates(Utils.convert_date_to_String(property.getAvailability_date(), "yyyy-MM-DD"), Utils.convert_date_to_String(Utils.get_current_date(), "yyyy-MM-DD"));
            if(days > 90){
                // update is_Active state.
                property.setIs_active(false);
                dataBaseHelper.update_is_active_property(property.getProperty_id(), false);

                Property updated_property = property;
                // update done sueccssfully in database.
                // 1- delete the propery from the firestore.
                ConnectionAsyncTask connectionAsyncTask = new
                        ConnectionAsyncTask((Activity) context);
                connectionAsyncTask.execute("delete","https://firestore.googleapis.com/v1/projects/" +
                        "house-renting-application/databases/(default)/documents/Property?key=" +
                        "AIzaSyCz95fmYr66fzDaeg9_A2pfEs77BVF6kfs", updated_property.getProperty_id() + "");
                ConnectionAsyncTask connectionAsyncTask2 = new
                        ConnectionAsyncTask((Activity) context);
                // 2- post the property with the updated values.
                String posted_data = "" + updated_property.getCity() + "," + updated_property.getPostal_address() + "," + updated_property.getSurface_area() + "," + updated_property.getConstruction_year() + "," + updated_property.getNumber_of_bedrooms() + "," + updated_property.getRental_price() +  "," + updated_property.isStatus() + "," + updated_property.isBalcony() + "," + updated_property.isGarden() + "," + updated_property.getAvailability_date() + "," + updated_property.getDescription() + "," + updated_property.getPosting_date() + "," + updated_property.isIs_active() + "," + updated_property.getProperty_id() + "," + updated_property.getRenting_agency_owner_id();
                connectionAsyncTask2.execute("post","https://firestore.googleapis.com/v1/projects/" +
                        "house-renting-application/databases/(default)/documents/Property?key=" +
                        "AIzaSyCz95fmYr66fzDaeg9_A2pfEs77BVF6kfs", posted_data, updated_property.getProperty_id() + "");


                return false;
            } else {
            }
        }
        return true;
    }

    public static long days_between_two_dates(String date1, String date2){
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
        java.util.Date d1 = null;
        java.util.Date d2 = null;
        try {
            d1 = format.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            d2 = format.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = d2.getTime() - d1.getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays;
    }

    public static String capitalize(String str) {
        str = str.toLowerCase();
        char[] charArray = str.toCharArray();
        boolean foundSpace = true;
        for(int i = 0; i < charArray.length; i++) {
            // if the array element is a letter
            if(Character.isLetter(charArray[i])) {

                // check space is present before the letter
                if(foundSpace) {

                    // change the letter into uppercase
                    charArray[i] = Character.toUpperCase(charArray[i]);
                    foundSpace = false;
                }
            }
            else {
                // if the new character is not character
                foundSpace = true;
            }
        }
        // convert the char array to the string
        str = String.valueOf(charArray);
        return str;
    }

}
