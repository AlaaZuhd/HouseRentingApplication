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
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.lab_project.models.Image;
import com.example.lab_project.models.Property;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    // testing image loading from gallery.
    private static int RESULT_LOAD_IMAGE = 1;
    ArrayList<Bitmap> bmp_images = new ArrayList<Bitmap>();
    ArrayList<ByteArrayOutputStream> image_output_stream = new ArrayList<ByteArrayOutputStream>();
    List<byte[]> images_bytes = new ArrayList<byte[]>();
    // -------------------------------------------


    Button button;
    Intent intent;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.get_started_button);
        intent = new Intent(MainActivity.this, NavigationDrawerActivity.class);

        // testing image loading from gallery.
        Button load = (Button)findViewById(R.id.button);
        ImageView img = (ImageView)findViewById(R.id.imageView_load);

        load.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // if permissions to reach external files is not garanted then ask for permission
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }, 0);
               }
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        // -----------------------------------------------------

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ConnectionAsyncTask connectionAsyncTask = new
                            ConnectionAsyncTask(MainActivity.this);
                    // Create new links here: https://designer.mocky.io/design/confirmation
                    connectionAsyncTask.execute("https://run.mocky.io/v3/90bb82a6-0304-4812-b14c-1abd83f3d332");
//                    CallAPI callAPI = new CallAPI();
//                    callAPI.execute("https://run.mocky.io/v3/90bb82a6-0304-4812-b14c-1abd83f3d332","{ \"city\": \"Ramallah!!!!!!!\" ,\"postal_address\":\"1234\",\"surface_area\":\"1140\",\"construction_year\":\"2014\",\"number_of_bedrooms\":\"2\",\"rental_price\":\"1200\",\"status\":\"true\",\"balcony\":\"true\",\"garden\":\"false\",\"availability_date\":\"2015-02-02\",\"description\":\"dsd\"}");
//                    System.out.println("---!-1-1-1-1-1-1-1-");
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
    // testing image loading from gallery.
    int index = 1;
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
            Property new_property = new Property("AAAA",344,34.4,2002,5,20.2,false,true,true, Date.valueOf("2015-03-31"),"hhh", "rawan@gmail.com");
            dataBaseHelper.insert_property(new_property);
            Image img1 = new Image("img1", BitmapFactory.decodeFile(picturePath), new_property.getProperty_id());
            dataBaseHelper.insert_property_image(img1);
            index++;
//            bmp_images.get(0).compress(Bitmap.CompressFormat.PNG, 100, image_output_stream.get(0));
//            images_bytes.add(image_output_stream.get(0).toByteArray());
        }
    }
    // -----------------------------------------
//    public void setProgress(boolean progress) {
//        ProgressBar progressBar = (ProgressBar)
//                findViewById(R.id.progressBar);
//        if (progress) {
//            progressBar.setVisibility(View.VISIBLE);
//        } else {
//            progressBar.setVisibility(View.GONE);
//        }
//    }


//    // testing image loading from gallery.
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//            ImageView imageView = (ImageView) findViewById(R.id.imageView_load);
//            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//            // need this steps to tore them in the data base.
//            bmp_images.add(BitmapFactory.decodeFile(picturePath));
//
//            bmp_images.get(0).compress(Bitmap.CompressFormat.PNG, 100, image_output_stream.get(0));
//            images_bytes.add(image_output_stream.get(0).toByteArray());
//        }
//    }
//    // -----------------------------------------




}
