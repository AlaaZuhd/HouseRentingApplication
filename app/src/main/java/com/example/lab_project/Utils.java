package com.example.lab_project;

import android.database.Cursor;
import android.util.Patterns;
import android.view.View;

import java.util.regex.Pattern;

import javax.xml.validation.Validator;

public class Utils {

    public static boolean validateEmailAddress(String email_address, DataBaseHelper dataBaseHelper) {
        // validate email address:
        // 1- ensue that the entered email have the email format.
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        System.out.println("|" + email_address + "|");
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
        if(password_length >= min_char_number && password_length <= max_char_number) {
            final Pattern textPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(=?.*[$%#@!{}]).+$");
            if(textPattern.matcher(password).matches()){
                return true;
            }
//            char[] c = password.toCharArray();
//            StringBuffer sb =new StringBuffer();
//            boolean flag =false;
//            for (char ch: c) {
//                if(Character.isDigit(ch)) {
//                    sb.append(ch);
//                    flag = true;
//                    break;
//                }
//            }
//            if(flag) { // if the string contains at least one digit  (number) continue the validation process
//
//            }
        }
        System.out.println("Not valid");
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
}
