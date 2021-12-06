package com.example.lab_project;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import java.util.List;

public class ConnectionAsyncTask extends AsyncTask<String, String, String> {
    Activity activity;
    Intent intent;
    public ConnectionAsyncTask(Activity activity) {
        this.activity = activity;
    }
    @Override
    protected void onPreExecute() {
        ((MainActivity) activity).setButtonText("connecting");
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(String... params) {
            String data = HttpManager.getData(params[0]);
            return data;
    }
    @Override
    protected void onPostExecute(String s) {
        intent = new Intent((MainActivity)activity,Login.class);
        if (s == null) {
            System.out.println("------In post");
        } else {
            super.onPostExecute(s);
            ((MainActivity) activity).setButtonText("connected");
            List<Property> properties =
                    PropertyJasonParser.getObjectFromJason(s);
            ((MainActivity) activity).fillProperties(properties);
            //If successful reading, go to the login page.
            activity.startActivity(intent);
            activity.finish();
        }
    }
}
