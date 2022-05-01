////updating the link, with new added properties.
package com.example.lab_project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.lab_project.helpers.ConnectionAsyncTask;
import com.example.lab_project.helpers.DataBaseHelper;
import com.example.lab_project.models.Image;
import com.example.lab_project.models.Property;
import com.smarteist.autoimageslider.SliderView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Button get_started_button;
    ProgressBar progress_bar;
    Intent intent;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(MainActivity.this, NavigationDrawerActivity.class);

        get_started_button = (Button) findViewById(R.id.get_started_button);
        progress_bar = (ProgressBar) findViewById(R.id.main_progressBar);
        linearLayout = (LinearLayout) findViewById(R.id.layout);

        get_started_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ConnectionAsyncTask connectionAsyncTask = new
                            ConnectionAsyncTask(MainActivity.this);
                    connectionAsyncTask.execute("get","https://firestore.googleapis.com/v1/projects/" +
                            "house-renting-application/databases/(default)/documents/Property?key=" +
                            "AIzaSyCz95fmYr66fzDaeg9_A2pfEs77BVF6kfs");
                }catch (Exception e){
                }
            }
        });
    }

    public void setButtonText(String text) {
        get_started_button.setText(text);
    }

    public void setProgress_bar(boolean status) {
        if(status)
            progress_bar.setVisibility(View.VISIBLE);
        else
            progress_bar.setVisibility(View.INVISIBLE);
    }

    public void fillProperties(List<Property> properties) {
        LinearLayout linearLayout = (LinearLayout)
                findViewById(R.id.layout);
        linearLayout.removeAllViews();
        for (int i = 0; i < properties.size(); i++) {
            TextView textView = new TextView(this);
            textView.setText(properties.get(i).toString());
            linearLayout.addView(textView);
        }
    }
}
