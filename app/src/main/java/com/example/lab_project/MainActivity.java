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

<<<<<<< HEAD
import com.example.lab_project.helpers.ConnectionAsyncTask;
import com.example.lab_project.helpers.DataBaseHelper;
=======
>>>>>>> b93fd345c720ed4123daa669fe976f07866e2b1b
import com.example.lab_project.models.Image;
import com.example.lab_project.models.Property;
import com.smarteist.autoimageslider.SliderView;

import java.io.ByteArrayOutputStream;
<<<<<<< HEAD
import java.io.File;
=======
>>>>>>> b93fd345c720ed4123daa669fe976f07866e2b1b
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

<<<<<<< HEAD
    Button get_started_button;
    ProgressBar progress_bar;
=======
    // testing image loading from gallery.
    private static int RESULT_LOAD_IMAGE = 1;
    ArrayList<Bitmap> bmp_images = new ArrayList<Bitmap>();
    ArrayList<ByteArrayOutputStream> image_output_stream = new ArrayList<ByteArrayOutputStream>();
    List<byte[]> images_bytes = new ArrayList<byte[]>();
    int index =1;
    // -------------------------------------------


    Button button;
>>>>>>> b93fd345c720ed4123daa669fe976f07866e2b1b
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
<<<<<<< HEAD
=======
//    public void setProgress(boolean progress) {
//        ProgressBar progressBar = (ProgressBar)
//                findViewById(R.id.progressBar);
//        if (progress) {
//            progressBar.setVisibility(View.VISIBLE);
//        } else {
//            progressBar.setVisibility(View.GONE);
//        }
//    }


    // testing image loading from gallery.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.imageView_load);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            // need this steps to tore them in the data base.
//            bmp_images.add(BitmapFactory.decodeFile(picturePath));
            DataBaseHelper dataBaseHelper =new DataBaseHelper(MainActivity.this,"Lab_Project.db",null,1);

            Property new_property = new Property("AAAA",344,34.4,2002,5,20.2,false,true,true, Date.valueOf("2015-03-31"),"hhh", "y@y.com");
        dataBaseHelper.insert_property(new_property);
            Image img1 = new Image("img1", BitmapFactory.decodeFile(picturePath), new_property.getProperty_id());
            dataBaseHelper.insert_property_image(img1);
            index++;
//            bmp_images.get(0).compress(Bitmap.CompressFormat.PNG, 100, image_output_stream.get(0));
//            images_bytes.add(image_output_stream.get(0).toByteArray());
        }
    }
    // -----------------------------------------

>>>>>>> b93fd345c720ed4123daa669fe976f07866e2b1b
}
