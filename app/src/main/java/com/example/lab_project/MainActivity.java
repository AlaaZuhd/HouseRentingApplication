////updating the link, with new added properties.
package com.example.lab_project;
//
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.lab_project.models.Property;

import java.io.ByteArrayOutputStream;
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
