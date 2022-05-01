package com.example.lab_project.helpers;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpManager {
    public static String getData(String URL)  {
        BufferedReader bufferedReader = null;
        try {
            java.net.URL url = new URL(URL);
            HttpURLConnection httpURLConnection =
                    (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new
                    InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            int line_index = 0;
            int counter = 0;
            stringBuilder.append("[");
            int flag = 0;
            String line = bufferedReader.readLine();
            while (line != null) {
                if(line_index == 1 || line.contains("updateTime") || line.contains("createTime") || line.contains("name") || line.contains("fields") || line.contains("[") || line.contains("]") || line.contains("}")) {
                }
                else if(line.contains("{") && line.trim().length()>1 ){
                    if(counter == 0 && line_index > 5){
                        stringBuilder.append(",{");
                    }
                    else if(counter == 0){
                        stringBuilder.append("{");
                    }
                    stringBuilder.append((line.split("\\{"))[0].trim() + " ");
                    counter ++;
                }
                else if(line.contains(":") && counter!= 15){
                    stringBuilder.append((line.split(":"))[1].trim() + ",");
                }
                else if(line.contains(":") && counter== 15){
                    stringBuilder.append((line.split(":"))[1].trim() + "}");
                    counter = 0;
                }
                line = bufferedReader.readLine();
                line_index++;
            }
            stringBuilder.append("]");
            return stringBuilder.toString();
        } catch (Exception ex) {
        }
        return null;
    }
    public static String postData(String URL, String Data, String properyt_id){
        try {
            //https://firestore.googleapis.com/v1/projects/house-renting-application/databases/(default)/documents/Property?key=AIzaSyCz95fmYr66fzDaeg9_A2pfEs77BVF6kfs&documentId=manualid
            URL = URL + "&documentId="+properyt_id;
            URL httpbinEndpoint = new URL(URL);
            HttpsURLConnection myConnection
                    = (HttpsURLConnection) httpbinEndpoint.openConnection();

            myConnection.setRequestMethod("POST");

            myConnection.setRequestProperty("Content-Type", "application/json");
            // Create the data
            String [] data_fields = Data.split(",");
            for (int i=0;i<data_fields.length;i++){
            }
            //                    private String city;
//                    private int postal_address;
//                    private double surface_area;
//                    private int construction_year;
//                    private int number_of_bedrooms;
//                    private double rental_price;
//                    private boolean status;     //finished or not
//                    private boolean balcony;
//                    private boolean garden;
//                    private Date availability_date;
//                    private String description;
//                    private Date posting_date;
//                    private boolean is_active;
//                    private int property_id;
//                    private String renting_agency_owner_id;






            String x = "yay";
            String myData = "{\"fields\": { \"city\": { \"stringValue\": \""+data_fields[0]+"\" } , \"postal_address\": { \"integerValue\": \""+data_fields[1]+"\"  },  \"surface_area\": { \"doubleValue\": \""+data_fields[2]+"\" }, \"construction_year\": { \"integerValue\": \""+data_fields[3]+"\" }, \"number_of_bedrooms\": { \"integerValue\": \""+data_fields[4]+"\" }, \"rental_price\": {\"doubleValue\": \""+data_fields[5]+"\" }, \"status\": { \"booleanValue\": \""+data_fields[6]+"\" }, \"balcony\": { \"booleanValue\": \""+data_fields[7]+"\" }, \"garden\": { \"booleanValue\": \""+data_fields[8]+"\" }, \"availability_date\": { \"stringValue\": \""+data_fields[9]+"\"}, \"description\": { \"stringValue\": \""+data_fields[10]+"\"}, \"posting_date\": { \"stringValue\": \""+data_fields[11]+"\"},\"is_active\": { \"booleanValue\": \""+data_fields[12]+"\"},\"property_id\": { \"integerValue\": \""+data_fields[13]+"\" }, \"renting_agency_owner_id\": { \"stringValue\": \""+data_fields[14]+"\"  } } }";
            // Enable writing
            myConnection.setDoOutput(true);

            // Write the data
            myConnection.getOutputStream().write(myData.getBytes());

            myConnection.connect();
            Log.e("Response", myConnection.getResponseMessage() + "");


        } catch (Exception e) {
            Log.e(e.toString(), "Something with request");
        }

        return null;
    }


    //deleteeeeeee
    public static String deleteData(String URL, String property_id){
        try {
            URL = "https://firestore.googleapis.com/v1/projects/house-renting-application/databases/(default)/documents/Property/"+property_id+"?key=AIzaSyCz95fmYr66fzDaeg9_A2pfEs77BVF6kfs";
            URL httpbinEndpoint = new URL(URL);
            HttpsURLConnection myConnection
                    = (HttpsURLConnection) httpbinEndpoint.openConnection();

            myConnection.setRequestMethod("DELETE");
            myConnection.setRequestProperty("Content-Type", "application/json");
            myConnection.connect();
            Log.e("Response", myConnection.getResponseMessage() + "");


        } catch (Exception e) {
            Log.e(e.toString(), "Something with request");
        }

        return null;
    }
}
