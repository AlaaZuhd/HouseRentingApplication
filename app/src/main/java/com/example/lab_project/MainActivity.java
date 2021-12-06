//updating the link, with new added properties.
package com.example.lab_project;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    Button button;
    Intent intent;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.get_started_button);
        intent = new Intent(MainActivity.this,Login.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ConnectionAsyncTask connectionAsyncTask = new
                            ConnectionAsyncTask(MainActivity.this);
                    // Create new links here: https://designer.mocky.io/design/confirmation
                    connectionAsyncTask.execute("https://run.mocky.io/v3/90bb82a6-0304-4812-b14c-1abd83f3d332");
                }catch (Exception e){
                    System.out.println("------------------");
                }
            }
        });
        linearLayout = (LinearLayout) findViewById(R.id.layout);
    }
    public void setButtonText(String text) {
        button.setText(text);
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
//    public void setProgress(boolean progress) {
//        ProgressBar progressBar = (ProgressBar)
//                findViewById(R.id.progressBar);
//        if (progress) {
//            progressBar.setVisibility(View.VISIBLE);
//        } else {
//            progressBar.setVisibility(View.GONE);
//        }
//    }
}
