//package com.example.lab_project.helpers;
//import android.app.Activity;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.widget.Toast;
//
//import com.example.lab_project.LoginActivity;
//import com.example.lab_project.MainActivity;
//import com.example.lab_project.models.Property;
//
//import java.util.List;
//
//public class ConnectionAsyncTask extends AsyncTask<String, String, String> {
//    Activity activity;
//    Intent intent;
//    public ConnectionAsyncTask(Activity activity) {
//        this.activity = activity;
//    }
//    @Override
//    protected void onPreExecute() {
//        ((MainActivity) activity).setButtonText("connecting");
//        ((MainActivity) activity).setProgress_bar(true);
//        super.onPreExecute();
//    }
//    @Override
//    protected String doInBackground(String... params) {
//            String data = HttpManager.getData(params[0]);
//            System.out.println(data);
//            return data;
//    }
//    @Override
//    protected void onPostExecute(String s) {
//        intent = new Intent((MainActivity)activity, LoginActivity.class);
//        ((MainActivity) activity).setProgress_bar(false);
//        if (s == null) {
//            Toast.makeText((MainActivity) activity, "Problem in connecting to the server, check your internet connection and try again", Toast.LENGTH_SHORT).show();
//        } else {
//            super.onPostExecute(s);
//            ((MainActivity) activity).setButtonText("connected");
//            List<Property> properties =
//                    PropertyJasonParser.getObjectFromJason(s);
//            ((MainActivity) activity).fillProperties(properties);
//            //If successful reading, go to the login page.
//            activity.startActivity(intent);
//            activity.finish();
//        }
//    }
//}











package com.example.lab_project.helpers;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.lab_project.MainActivity;
import com.example.lab_project.NavigationDrawerActivity;
import com.example.lab_project.helpers.PropertyJasonParser;
import com.example.lab_project.models.Property;

import java.util.List;

public class ConnectionAsyncTask extends AsyncTask<String, String, String> {
    Activity activity;
    Intent intent;
    public ConnectionAsyncTask(Activity activity) {
        this.activity = activity;
    }
    @Override
    protected void onPreExecute() {
        if(Utils.counter == 0) {
            ((MainActivity) activity).setButtonText("connecting");
            ((MainActivity) activity).setProgress_bar(true);
            Utils.counter ++;
        }
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        if(params[0].equals("get")) {
            System.out.println(params[1]);
            String data = HttpManager.getData(params[1]);
            System.out.println(data);

            return data;
        }
        else if(params[0].equals("post")){//then this is put
            //params[2] is the data to be added, it would be fields separated by commas.
            HttpManager.postData(params[1], params[2], params[3]);
        }
        else{
            HttpManager.deleteData(params[1], params[2]);
        }
        return null;
        //   return HttpManager.putData(params[0], "{\"name\":\"p\"}");
    }

    @Override
    protected void onPostExecute(String s) {
        if(Utils.counter == 1) {
            intent = new Intent((MainActivity) activity, NavigationDrawerActivity.class);
            if (s == null) {
            } else {
                super.onPostExecute(s);
                ((MainActivity) activity).setButtonText("connected");
                ((MainActivity) activity).setProgress_bar(false);
                List<Property> properties =
                        PropertyJasonParser.getObjectFromJason(s);
                ((MainActivity) activity).fillProperties(properties);
                //If successful reading, go to the login page.
                activity.startActivity(intent);
                activity.finish();
            }
            Utils.counter ++ ;
        }
    }
}
