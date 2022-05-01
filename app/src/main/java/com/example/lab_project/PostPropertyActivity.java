package com.example.lab_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab_project.helpers.ConnectionAsyncTask;
import com.example.lab_project.helpers.DataBaseHelper;
import com.example.lab_project.helpers.DatePicker;
import com.example.lab_project.helpers.SharedPrefManager;
import com.example.lab_project.helpers.Utils;
import com.example.lab_project.models.Image;
import com.example.lab_project.models.Property;

import java.io.File;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PostPropertyActivity extends AppCompatActivity implements DatePicker.DatePickerListener {

    SharedPrefManager sharedPrefManager;
    Intent intent;
    int user_type;
    String user_email_address;
    int valid = 1;
    int index = 0;
    int number_of_removed_images = 0;
    private static int RESULT_LOAD_IMAGE = 1;


    EditText post_property_city_editText;
    Button post_property_availability_date_button;
    EditText post_property_number_of_bedrooms_editText;
    EditText post_property_postal_address_editText;
    EditText post_property_surface_area_editText;
    EditText post_property_construction_year_editText;
    EditText post_property_rental_price_editText;
    EditText post_property_description_editText;
    Switch post_property_status_switch;
    Switch post_property_balcony_switch;
    Switch post_property_garden_switch;
    Button post_property_upload_image_button;
    Button post_property_post_property_button;
    LinearLayout post_property_upload_images_layout;
    LinearLayout post_property_availability_date_layout;

    TextView post_property_availability_date_textView;

    List<Bitmap> images_bitmap = new ArrayList<Bitmap>();
    List<Button> delete_buttons = new ArrayList<Button>();
    List<Integer> deleted_indexes = new ArrayList<Integer>();

    Button post_arrowback_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_property);

        // get user authentication.
        get_user_authentication();

        post_arrowback_button = (Button) findViewById(R.id.post_arrowback_button);

        post_arrowback_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Do the notification work and get the old animation
                intent = new Intent(PostPropertyActivity.this, NavigationDrawerActivity.class);
                startActivity(intent);
            }
        });

        if(user_type == 0) {
            // tenant user can't be here. -> will never happened (go to home activity...)
            Toast.makeText(PostPropertyActivity.this, "Tenant User can't be here!", Toast.LENGTH_SHORT).show();
            intent = new Intent(PostPropertyActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        post_property_city_editText = (EditText) findViewById(R.id.post_property_city_editText);
        post_property_number_of_bedrooms_editText = (EditText) findViewById(R.id.post_property_number_of_bedrooms_editText);
        post_property_postal_address_editText = (EditText) findViewById(R.id.post_property_postal_address_editText);
        post_property_surface_area_editText = (EditText) findViewById(R.id.post_property_surface_area_editText);
        post_property_construction_year_editText = (EditText) findViewById(R.id.post_property_construction_year_editText);
        post_property_rental_price_editText = (EditText) findViewById(R.id.post_property_rental_price_editText);
        post_property_description_editText = (EditText) findViewById(R.id.post_property_description_editText);
        post_property_status_switch = (Switch) findViewById(R.id.post_property_status_switch);
        post_property_balcony_switch = (Switch) findViewById(R.id.post_property_balcony_switch);
        post_property_garden_switch = (Switch) findViewById(R.id.post_property_garden_switch);
        post_property_availability_date_button = (Button) findViewById(R.id.button3post_property_availability_date_button);
        post_property_upload_image_button = (Button) findViewById(R.id.post_property_upload_image_button);
        post_property_post_property_button = (Button) findViewById(R.id.post_property_post_property_button);
        post_property_upload_images_layout = (LinearLayout) findViewById(R.id.post_property_upload_images_layout);
        post_property_availability_date_layout = (LinearLayout) findViewById(R.id.post_property_availability_date_layout);

        post_property_availability_date_textView = (TextView) findViewById(R.id.post_property_availability_date_textView);


        post_property_availability_date_button.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  post_property_availability_date_button.setError(null);
                  DialogFragment dataPickerFragment = new DatePicker();
                  dataPickerFragment.setCancelable(false);
                  dataPickerFragment.show(getSupportFragmentManager(),"date Picker");
              }
          }
        );


        post_property_upload_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // upload photo.
                // if permissions to reach external files is not garanted then ask for permission
                if (ContextCompat.checkSelfPermission(PostPropertyActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(PostPropertyActivity.this, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }, 0);
                }
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


        post_property_post_property_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = InitValidatePostFormFields.check_property_form_fields(post_property_status_switch,
                        post_property_balcony_switch,
                        post_property_garden_switch,
                        post_property_city_editText,
                        post_property_availability_date_button,
                        null,
                        post_property_construction_year_editText,
                        post_property_description_editText,
                        post_property_number_of_bedrooms_editText,
                        post_property_postal_address_editText,
                        post_property_rental_price_editText,
                        post_property_surface_area_editText);
                if(valid) {
                    final boolean[] is_sure_no_img = {true};
                    ;
                    if(images_bitmap.size() == 0) { // check if there is no photo has been uploaded.
                    }
                    if(is_sure_no_img[0]){
                        Date available_date;
                        if(post_property_status_switch.isChecked()){
                            available_date = Utils.get_current_date();
                        } else {
                            available_date = Date.valueOf(post_property_availability_date_button.getText().toString().trim());
                        }
                        // add to database.
                        DataBaseHelper dataBaseHelper =new DataBaseHelper(PostPropertyActivity.this,"Lab_Project.db",null,1);
                        Property new_property = new Property(Utils.capitalize(post_property_city_editText.getText().toString()),
                                Integer.parseInt(post_property_postal_address_editText.getText().toString().trim()),
                                Double.parseDouble(post_property_surface_area_editText.getText().toString().trim()),
                                Integer.parseInt(post_property_construction_year_editText.getText().toString().trim()),
                                Integer.parseInt(post_property_number_of_bedrooms_editText.getText().toString().trim()),
                                Double.parseDouble(post_property_rental_price_editText.getText().toString().trim()),
                                post_property_status_switch.isChecked(),
                                post_property_balcony_switch.isChecked(),
                                post_property_garden_switch.isChecked(),
                                available_date,
                                post_property_description_editText.getText().toString().trim(),
                                user_email_address,
                                Utils.get_current_date(),
                                true);
                        dataBaseHelper.insert_property(new_property);
                        // post
                        String posted_data = "" + new_property.getCity() + "," + new_property.getPostal_address() + "," + new_property.getSurface_area() + "," + new_property.getConstruction_year() + "," + new_property.getNumber_of_bedrooms() + "," + new_property.getRental_price() +  "," + new_property.isStatus() + "," + new_property.isBalcony() + "," + new_property.isGarden() + "," + new_property.getAvailability_date() + "," + new_property.getDescription() + "," + new_property.getPosting_date() + "," + new_property.isIs_active() + "," + new_property.getProperty_id() + "," + new_property.getRenting_agency_owner_id();
                        ConnectionAsyncTask connectionAsyncTask = new
                                ConnectionAsyncTask(PostPropertyActivity.this);
                        connectionAsyncTask.execute("post","https://firestore.googleapis.com/v1/projects/" +
                                "house-renting-application/databases/(default)/documents/Property?key=" +
                                "AIzaSyCz95fmYr66fzDaeg9_A2pfEs77BVF6kfs", posted_data, new_property.getProperty_id() + "");

                        for (int i=0; i< images_bitmap.size(); i++){
                            Image img = new Image("img" + i, images_bitmap.get(i), new_property.getProperty_id());
                            dataBaseHelper.insert_property_image(img);
                        }

                        images_bitmap.clear();

                        intent = new Intent(PostPropertyActivity.this, NavigationDrawerActivity.class);
                        PostPropertyActivity.this.startActivity(intent);
                        PostPropertyActivity.this.finish();
                    }
                } else{
                    // do something.
                }
            }
        });

        InitValidatePostFormFields.init_listeners(post_property_status_switch,
                post_property_balcony_switch,
                post_property_garden_switch,
                post_property_city_editText,
                post_property_availability_date_button,
                null,
                post_property_construction_year_editText,
                post_property_description_editText,
                post_property_number_of_bedrooms_editText,
                post_property_postal_address_editText,
                post_property_rental_price_editText,
                post_property_surface_area_editText,
                post_property_availability_date_textView);
    }


    // testing image loading from gallery.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            LinearLayout image_container_layout = new LinearLayout(this);
            image_container_layout.setOrientation(LinearLayout.HORIZONTAL);
            image_container_layout.setLayoutParams(new
                    LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup
                    .LayoutParams.WRAP_CONTENT));
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(450, 500);
            params1.gravity = Gravity.CENTER_VERTICAL | Gravity.START;
            imageView.setLayoutParams(params1);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            Button delete_image_button = new Button(this);
//            delete_image_button.setLayoutParams(new
//                    LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup
//                    .LayoutParams.MATCH_PARENT));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150, 150);
            params.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;

            delete_image_button.setLayoutParams(params);
            delete_image_button.setBackground(getDrawable(R.drawable.delete));
            image_container_layout.addView(imageView);
            image_container_layout.addView(delete_image_button);


            File file_image = new File(picturePath);
            if(file_image.length()> 2096720){
                Toast.makeText(PostPropertyActivity.this, "Image size is too big, please try another one", Toast.LENGTH_LONG).show();
            } else {
                post_property_upload_images_layout.addView(image_container_layout, 1);
                Bitmap img_bitmap = BitmapFactory.decodeFile(picturePath);
                images_bitmap.add(img_bitmap);
                delete_buttons.add(delete_image_button);

                delete_image_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index_to_be_deleted = delete_buttons.indexOf(delete_image_button);
                        images_bitmap.remove(img_bitmap);
                        post_property_upload_images_layout.removeView(image_container_layout);
                    }
                });
                index ++;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month);
        cal.set(Calendar.DAY_OF_MONTH,day);

        String date = DateFormat.getDateInstance().format(cal.getTime());
        DateFormat originalFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.forLanguageTag(date));
        DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date2 = originalFormat.parse(date);
        String formattedDate = targetFormat.format(date2);
        post_property_availability_date_button.setText(formattedDate);
    }


    public void get_user_authentication(){
        sharedPrefManager = SharedPrefManager.getInstance(this);
        user_email_address = sharedPrefManager.readString("username", "username");
        user_type = sharedPrefManager.readInt("type", 100);
        if(user_email_address.equals("username") || (user_type != 0 && user_type != 1)){
            // user is not authenticated so direct to the login page.
            Toast.makeText(PostPropertyActivity.this, "User is not authenticated, you need to log in", Toast.LENGTH_SHORT).show();
            intent = new Intent(PostPropertyActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
