package com.example.lab_project;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import com.example.lab_project.helpers.DatePickerFragment;
import com.example.lab_project.helpers.SharedPrefManager;
import com.example.lab_project.helpers.Utils;
import com.example.lab_project.helpers.slideradapter;
import com.example.lab_project.models.Image;
import com.example.lab_project.models.Notification;
import com.example.lab_project.models.Property;
import com.example.lab_project.models.RequestProperty;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.w3c.dom.Text;

import java.io.File;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PropertySummaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PropertySummaryFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static int RESULT_LOAD_IMAGE = 1;

    String property_renting_status = "available";

    DataBaseHelper dataBaseHelper;
    SharedPrefManager sharedPrefManager;
    Intent intent;
    int user_type;
    String user_email_address;
    boolean is_authenticated = false;

    ImageView property_image_imageview;
    TextView property_city_textview;
    TextView property_surface_area_textview;
    TextView property_number_of_bedrooms_textview;
    TextView property_rental_price_textview;
    Button property_view_details_button;

    SliderView sliderView;
    Button property_close_button;
    Button property_delete_button;
    Button property_edit_button;
    Button property_update_button;
    Button property_repost_button;
    EditText property_city_editText;
//    Button property_availability_date_button;
    EditText property_availability_date_editText;
    EditText property_number_of_bedrooms_editText;
    EditText property_postal_address_editText;
    EditText property_surface_area_editText;
    EditText property_construction_year_editText;
    EditText property_rental_price_editText;
    Switch property_status_switch;
    Switch property_balcony_switch;
    Switch property_garden_switch;
    EditText property_description_editText;
    EditText property_renting_agency_owner_id_editText;

    Button property_upload_image_button;
    Button property_apply_button;
    LinearLayout property_upload_images_inner_layout;
    LinearLayout property_upload_images_layout;
    LinearLayout property_availability_date_layout;

    TextView property_availability_date_textView;



    private View root_view;
    private String fragment_tag;
    Property property, updated_property;
    Image property_image;

    final ArrayList<Image> Images = new ArrayList<>();
    List<Bitmap> images_bitmap = new ArrayList<Bitmap>();
    List<Integer> deleted_images_indexes = new ArrayList<Integer>();

    ArrayList<Image> images_default_list = new ArrayList<Image>();

    ArrayList<LinearLayout> added_linear_layout_list = new ArrayList<LinearLayout>();




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PropertySummaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PropertySummaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PropertySummaryFragment newInstance(String param1, String param2) {
        PropertySummaryFragment fragment = new PropertySummaryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root_view = inflater.inflate(R.layout.fragment_property_summary, container, false);
        return root_view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Image img = new Image();
        img.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.image_not_available));
        images_default_list.add(img);

        property_image_imageview = (ImageView) root_view.findViewById(R.id.edit_list_of_properties_imageView);
        property_city_textview = (TextView) root_view.findViewById(R.id.edit_list_of_properties_city_textView);
        property_surface_area_textview = (TextView) root_view.findViewById(R.id.edit_list_of_properties_surface_area_textView);
        property_number_of_bedrooms_textview = (TextView) root_view.findViewById(R.id.edit_list_of_properties_number_of_bedrooms_textView);
        property_rental_price_textview = (TextView) root_view.findViewById(R.id.edit_list_of_properties_rental_price_textView);
        property_view_details_button = (Button) root_view.findViewById(R.id.edit_list_of_properties_view_details_button);
        sharedPrefManager =SharedPrefManager.getInstance(getActivity());


        // get user authentication.
        get_user_authentication();

        final int[] i = {0};
        Bundle bundle = getArguments();
        if (bundle != null) {
            fragment_tag = bundle.getString("fragment_tag");
            property_city_textview.setText("City: " + bundle.getString("city"));
            property_number_of_bedrooms_textview.setText("Number of Bedrooms: " + bundle.getInt("number_of_bedrooms") + "");
            property_surface_area_textview.setText("Surface Area: " + bundle.getDouble("surface_area") + "");
            property_rental_price_textview.setText(bundle.getDouble("rental_price") + "$");
            init_property(bundle);
            dataBaseHelper =new DataBaseHelper(getActivity(), "Lab_Project.db",null,1);
            Cursor temp = dataBaseHelper.get_one_image_for_property(property.getProperty_id());
            img = new Image();
            //Images.add(img);
            int flag=0;
            while (temp.moveToNext()) { //User found
                img.setImage_id(temp.getInt(0));
                img.setImage(BitmapFactory.decodeByteArray(temp.getBlob(2), 0, temp.getBlob(2).length));
                flag=1;
            }
            if(flag == 1){
                property_image = img;
                property_image_imageview.setImageBitmap(property_image.getImage());
            } else{
                // set an img from our menu
                property_image_imageview.setImageDrawable(getActivity().getDrawable(R.drawable.image_not_available));

            }
            property_view_details_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(getActivity());
                    // not working !!!!!!!!!!!!!!
                    dialog.getWindow().setLayout((int)(getResources().getDisplayMetrics().widthPixels*0.50),
                            (int)(getResources().getDisplayMetrics().widthPixels*0.50));

                    //We have added a title in the custom layout. So let's disable the default title.
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
                    dialog.setCancelable(false);
                    //Mention the name of the layout of your custom dialog.
                    dialog.setContentView(R.layout.property_info_dialog);

                    init_property_dialog_view(dialog);
                    get_property_images();
                    init_image_slider();

                    if(user_email_address.equals(property.getRenting_agency_owner_id())) {
                        // set visibility for delete and edit buttons to Visible
                        property_delete_button.setVisibility(View.VISIBLE);
                        property_edit_button.setVisibility(View.VISIBLE);
                        if(!property.isIs_active()){
                           property_repost_button.setVisibility(View.VISIBLE);
                        }
                    }

//                    property_availability_date_button.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            DialogFragment datePicker = new DatePickerFragment();
//                            datePicker.setTargetFragment(PropertySummaryFragment.this, 0);
//                            datePicker.show(getFragmentManager(), "date picker");
//
//
////                            DialogFragment dataPickerFragment = new DatePicker();
////                            dataPickerFragment.setCancelable(false);
////                            // set the targetFragment to receive the results, specifying the request code
////                            dataPickerFragment.setTargetFragment(PropertySummaryFragment.this, 11);
////                            // show the datePicker
////                            dataPickerFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
//////                            dataPickerFragment.show(getActivity().getSupportFragmentManager(), "date Picker");
//                        }
//                    });

                    // handle the close button (to close the dialog)
                    property_close_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    // handle the delete button (to delete the selected property)
                    property_delete_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // display a confirm dialog.
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Confirm the delete operation")
                                    .setMessage("Do you really want to delete this property from your list?")
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        // if confirmed then delete the property from the database, show a toast message as notification and return to the edit list activity.
                                        public void onClick(DialogInterface confirm_dialog, int whichButton) {
                                            // delete property
                                            boolean flag = dataBaseHelper.delete_property_by_id(property.getProperty_id());
                                            if(flag == true) {

                                                // delete the propery from the firestore.
                                                ConnectionAsyncTask connectionAsyncTask = new
                                                        ConnectionAsyncTask(getActivity());
                                                connectionAsyncTask.execute("delete","https://firestore.googleapis.com/v1/projects/" +
                                                        "house-renting-application/databases/(default)/documents/Property?key=" +
                                                        "AIzaSyCz95fmYr66fzDaeg9_A2pfEs77BVF6kfs", property.getProperty_id() + "");
                                                // display a toast ms g
                                                Toast.makeText(getActivity(), "Property has been deleted successfully>", Toast.LENGTH_SHORT).show();
                                                // update the edit list activity or remove the needed fragment.
                                                getActivity().getSupportFragmentManager().beginTransaction().remove(getActivity().getSupportFragmentManager().findFragmentByTag(fragment_tag)).commit();
                                                // close the main property dialog.
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(getActivity(), "Deleteting is unsuccessful", Toast.LENGTH_SHORT).show();
                                            }
                                        }})
                                    .setNegativeButton(android.R.string.no, null).show(); // else stay in the dialog with (don't do any thing)
                        }
                    });

                    // handle the edit button (to enable the user to edit the property fields)
                    property_edit_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            added_linear_layout_list.clear();
                            deleted_images_indexes.clear();
                            images_bitmap.clear();
                            property_delete_button.setVisibility(View.GONE);
                            property_edit_button.setVisibility(View.GONE);
                            property_update_button.setVisibility(View.VISIBLE);
                            sliderView.setVisibility(View.GONE);
                            set_property_fields_enabled(true);
                            InitValidatePostFormFields.init_listeners(property_status_switch,
                                    property_balcony_switch,
                                    property_garden_switch,
                                    property_city_editText,
                                    null,
                                    property_availability_date_editText,
                                    property_construction_year_editText,
                                    property_description_editText,
                                    property_number_of_bedrooms_editText,
                                    property_postal_address_editText,
                                    property_rental_price_editText,
                                    property_surface_area_editText,
                                    property_availability_date_textView);
                            // show edit and upload layout for images.

                            // add images into the upload_images_layout
                            display_images();
                            property_update_button.setVisibility(View.VISIBLE);
                            property_upload_images_layout.setVisibility(View.VISIBLE);
                            set_property_update_button_listener();
                        }
                    });

                    // handle the update button to save updates to database.
                    property_update_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // check fields...
                            boolean isVlaid = InitValidatePostFormFields.check_property_form_fields(property_status_switch,
                                    property_balcony_switch,
                                    property_garden_switch,
                                    property_city_editText,
                                    null,
                                    property_availability_date_editText,
                                    property_construction_year_editText,
                                    property_description_editText,
                                    property_number_of_bedrooms_editText,
                                    property_postal_address_editText,
                                    property_rental_price_editText,
                                    property_surface_area_editText);
                            // if valid update and refresh the dialog to reflect changes.
                            if(isVlaid){
                                // update property in database.
                                update_property_obj();
                                boolean res = dataBaseHelper.update_property(updated_property);
                                if(res){
                                    // update done sueccssfully in database.
                                    // 1- delete the propery from the firestore.
                                    ConnectionAsyncTask connectionAsyncTask = new
                                            ConnectionAsyncTask(getActivity());
                                    connectionAsyncTask.execute("delete","https://firestore.googleapis.com/v1/projects/" +
                                            "house-renting-application/databases/(default)/documents/Property?key=" +
                                            "AIzaSyCz95fmYr66fzDaeg9_A2pfEs77BVF6kfs", updated_property.getProperty_id() + "");
                                    ConnectionAsyncTask connectionAsyncTask2 = new
                                            ConnectionAsyncTask(getActivity());
                                    // 2- post the property with the updated values.
                                    String posted_data = "" + Utils.capitalize(updated_property.getCity()) + "," + updated_property.getPostal_address() + "," + updated_property.getSurface_area() + "," + updated_property.getConstruction_year() + "," + updated_property.getNumber_of_bedrooms() + "," + updated_property.getRental_price() +  "," + updated_property.isStatus() + "," + updated_property.isBalcony() + "," + updated_property.isGarden() + "," + updated_property.getAvailability_date() + "," + updated_property.getDescription() + "," + updated_property.getPosting_date() + "," + updated_property.isIs_active() + "," + updated_property.getProperty_id() + "," + updated_property.getRenting_agency_owner_id();
                                    connectionAsyncTask2.execute("post","https://firestore.googleapis.com/v1/projects/" +
                                            "house-renting-application/databases/(default)/documents/Property?key=" +
                                            "AIzaSyCz95fmYr66fzDaeg9_A2pfEs77BVF6kfs", posted_data, updated_property.getProperty_id() + "");


                                    // update list of images.
                                    update_property_images();

                                    // refresh the dialog to reflect changes. (already changed)
                                    set_property_fields_enabled(false);

                                    Toast.makeText(getActivity(), "Updating done successfully", Toast.LENGTH_SHORT).show();

                                    if(Images.size()>0)
                                        property_image_imageview.setImageBitmap(Images.get(0).getImage());
                                    else
                                        property_image_imageview.setImageDrawable(getActivity().getDrawable(R.drawable.image_not_available));
                                    property_city_textview.setText("City: " + property_city_editText.getText());
                                    property_number_of_bedrooms_textview.setText("Number of Bedrooms: " + property_number_of_bedrooms_editText.getText());
                                    property_rental_price_textview.setText("Rental price: " + property_rental_price_editText.getText());
                                    property_surface_area_textview.setText("Surface area: " + property_surface_area_editText.getText());

                                    // refresh image slider to reflect changes.
                                    init_image_slider();

                                    // reinit dialog buttons.
                                    property_upload_images_inner_layout.removeAllViewsInLayout();
                                    added_linear_layout_list.clear();
                                    property_upload_images_layout.setVisibility(View.GONE);
                                    property_delete_button.setVisibility(View.VISIBLE);
                                    property_edit_button.setVisibility(View.VISIBLE);
                                    property_update_button.setVisibility(View.GONE);
                                    sliderView.setVisibility(View.VISIBLE);
                                }else {
                                    // if a problem in the update query display a toast message.
                                    Toast.makeText(getActivity(), "Updating is not completed", Toast.LENGTH_SHORT).show();
                                    // may i will stay in this location...
                                }
                            }
                        }
                    });


                    property_repost_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // mark this activity as active (is_active = true)
                            dataBaseHelper.update_is_active_property(property.getProperty_id(), true);
                            // update the posting date to the current date
                            Date new_posting_date = Utils.get_current_date();
                            property.setPosting_date(new_posting_date);
                            property.setIs_active(true);
                            String new_posting_date_str = Utils.convert_date_to_String(new_posting_date, "yyyy-MM-dd");
                            dataBaseHelper.update_posting_date_property(property.getProperty_id(), new_posting_date_str);
                            Property updated_property = property;
                            // update done sueccssfully in database.
                            // 1- delete the propery from the firestore.
                            ConnectionAsyncTask connectionAsyncTask = new
                                    ConnectionAsyncTask(getActivity());
                            connectionAsyncTask.execute("delete","https://firestore.googleapis.com/v1/projects/" +
                                    "house-renting-application/databases/(default)/documents/Property?key=" +
                                    "AIzaSyCz95fmYr66fzDaeg9_A2pfEs77BVF6kfs", updated_property.getProperty_id() + "");
                            ConnectionAsyncTask connectionAsyncTask2 = new
                                    ConnectionAsyncTask(getActivity());
                            // 2- post the property with the updated values.
                            String posted_data = "" + Utils.capitalize(updated_property.getCity()) + "," + updated_property.getPostal_address() + "," + updated_property.getSurface_area() + "," + updated_property.getConstruction_year() + "," + updated_property.getNumber_of_bedrooms() + "," + updated_property.getRental_price() +  "," + updated_property.isStatus() + "," + updated_property.isBalcony() + "," + updated_property.isGarden() + "," + updated_property.getAvailability_date() + "," + updated_property.getDescription() + "," + updated_property.getPosting_date() + "," + updated_property.isIs_active() + "," + updated_property.getProperty_id() + "," + updated_property.getRenting_agency_owner_id();
                            connectionAsyncTask2.execute("post","https://firestore.googleapis.com/v1/projects/" +
                                    "house-renting-application/databases/(default)/documents/Property?key=" +
                                    "AIzaSyCz95fmYr66fzDaeg9_A2pfEs77BVF6kfs", posted_data, updated_property.getProperty_id() + "");
                            // hide the repost button.
                            property_repost_button.setVisibility(View.GONE);
                        }
                    });

                    property_apply_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            get_user_authentication(); // check authentication.
                            if(is_authenticated){

                                // take renting period ..
                                final Dialog renting_period_dialog = new Dialog(getActivity());

                                //We have added a title in the custom layout. So let's disable the default title.
                                renting_period_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
                                renting_period_dialog.setCancelable(false);
                                //Mention the name of the layout of your custom dialog.
                                renting_period_dialog.setContentView(R.layout.renting_period_dialog);

                                Button continue_button = (Button) renting_period_dialog.findViewById(R.id.continue_button);
                                Button cancel_button = (Button) renting_period_dialog.findViewById(R.id.cancel_button);
                                EditText from_date_editTextDate = (EditText) renting_period_dialog.findViewById(R.id.from_date_editTextDate);
                                EditText to_date_editTextDate = (EditText) renting_period_dialog.findViewById(R.id.to_date_editTextDate);
                                continue_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // validate....
                                        // if valid send renting request.
                                        String from_date = from_date_editTextDate.getText().toString();
                                        String to_date = to_date_editTextDate.getText().toString();
                                        boolean is_valid = true;
                                        if(!Utils.isValidDate(from_date)){
                                            is_valid = false;
                                            from_date_editTextDate.setError("Invalid date format: format must be: YY-Mm-DD");
                                        }
                                        if(!Utils.isValidDate(to_date)){
                                            is_valid = false;
                                            to_date_editTextDate.setError("Invalid date format: format must be: YY-Mm-DD");
                                        }
                                        if(is_valid){
                                            // check if from_date is not in the previous
                                            if(!Utils.compare_dates(Utils.get_current_date(), Date.valueOf(from_date))){
                                                is_valid = false;
                                                from_date_editTextDate.setError("From date can't be in the past");
                                            }
                                            if(!Utils.compare_dates(Date.valueOf(from_date), Date.valueOf(to_date))){
                                                is_valid = false;
                                                to_date_editTextDate.setError("To Date can't be before the from date");
                                            }
                                            // from date must br larger than the avaiability data in case the property status is unfinished.
                                            if(!property.isStatus()){
                                                if(!Utils.compare_dates(property.getAvailability_date(), Date.valueOf(from_date))){
                                                    is_valid = false;
                                                    from_date_editTextDate.setError("From date can't be before the availability date, because this property will not be available until that date");
                                                }
                                            }
                                            if(is_valid) {
                                                // check if the property is valid in this range...
                                                String sql = "SELECT * FROM TENANT_PROPERTY WHERE PROPERTY_ID = " + property.getProperty_id() + " AND NOT (START_DATE >= ('" + to_date + "') OR  END_DATE <= ('" + from_date + "'))";
                                                Cursor temp = dataBaseHelper.search_tenant_property(sql);
                                                int flag = 0;
                                                while(temp.moveToNext()){
                                                    flag = 1;
                                                    break;
                                                }
                                                if(flag == 1){
                                                    // property is not valid...
                                                    Toast.makeText(getActivity(), "Property is not valid at this period :(, try another period", Toast.LENGTH_LONG).show();
                                                }
                                                else {
                                                    // property is valid to be rented in this period...
                                                    //                                // insert a request
                                                    String request_date = Utils.convert_date_to_String(Utils.get_current_date(), "yyyy-MM-dd");
                                                    RequestProperty new_request = new RequestProperty(property.getProperty_id(), user_email_address, request_date, from_date, to_date);
                                                    int request_id = dataBaseHelper.insert_request_property(new_request);
                                                    new_request.setRequest_property_id(request_id);
                                                    // send a notification.
                                                    Notification new_notification = new Notification(user_email_address, property.getRenting_agency_owner_id(), new_request.getRequest_property_id(), false, request_date, "apply_request");
                                                    int notification_id = dataBaseHelper.insert_notification(new_notification);
                                                    new_notification.setNotification_id(notification_id);
                                                    Toast.makeText(getActivity(), "Yor request is now PENDED, please wait until the owner respond to your request :)", Toast.LENGTH_LONG).show();
//                                                    property_apply_button.setVisibility(View.GONE);
                                                    renting_period_dialog.dismiss();
                                                }
                                            }

                                        }
                                    }
                                });

                                cancel_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        renting_period_dialog.dismiss();
                                    }
                                });

                                renting_period_dialog.show();
                                renting_period_dialog.getWindow().setLayout((int)(getResources().getDisplayMetrics().widthPixels*0.9f),
                                        (int)(getResources().getDisplayMetrics().widthPixels*0.6f));

                            }else {
                                intent = new Intent(getActivity(), LoginActivity.class);
                                intent.putExtra("Back", true);
                                getActivity().startActivity(intent); // login then back here to continue renting
                            }
                        }
                    });
                    dialog.show();
                }
            });
        }
    }

    public void init_property(Bundle bundle){
            property = new Property(bundle.getString("city"),
                    bundle.getInt("postal_Address"),
                    bundle.getDouble("surface_area"),
                    bundle.getInt("construction_year"),
                    bundle.getInt("number_of_bedrooms"),
                    bundle.getDouble("rental_price"),
                    bundle.getBoolean("status"),
                    bundle.getBoolean("balcony"),
                    bundle.getBoolean("garden"),
                    Date.valueOf(bundle.getString("availability_date")),
                    bundle.getString("description"),
                    bundle.getString("renting_agency_owner_id"),
                    Date.valueOf(bundle.getString("posting_date")),
                    bundle.getBoolean("is_active"));
            property.setProperty_id(bundle.getInt("property_id"));
    }

    public void init_property_dialog_view(Dialog dialog) {
        // get references.
        sliderView= dialog.findViewById(R.id.imagesilder);
        property_close_button = dialog.findViewById(R.id.home_property_close_button);
        property_delete_button = dialog.findViewById(R.id.home_property_delete_button);
        property_edit_button = dialog.findViewById(R.id.home_property_edit_button);
        property_update_button = dialog.findViewById(R.id.home_property_update_button);
        property_repost_button = dialog.findViewById(R.id.home_property_repost_button);
        property_apply_button  = dialog.findViewById(R.id.home_property_apply_button);
        property_city_editText = dialog.findViewById(R.id.home_property_city_editText);
//        property_availability_date_button = dialog.findViewById(R.id.home_property_availability_date_button);
        property_availability_date_editText = dialog.findViewById(R.id.home_property_availability_date_editText);
        property_number_of_bedrooms_editText = dialog.findViewById(R.id.home_property_number_of_bedrooms_editText);
        property_postal_address_editText = dialog.findViewById(R.id.home_property_postal_address_editText);
        property_surface_area_editText = dialog.findViewById(R.id.home_property_surface_area_editText);
        property_construction_year_editText = dialog.findViewById(R.id.home_property_construction_year_editText);
        property_rental_price_editText = dialog.findViewById(R.id.home_property_rental_price_editText);
        property_status_switch = dialog.findViewById(R.id.home_property_status_switch);
        property_balcony_switch = dialog.findViewById(R.id.home_property_balcony_switch);
        property_garden_switch = dialog.findViewById(R.id.home_property_garden_switch);
        property_description_editText = dialog.findViewById(R.id.home_property_description_editText);
        property_renting_agency_owner_id_editText = dialog.findViewById(R.id.home_property_renting_agency_owner_id_editText);
        property_upload_image_button = dialog.findViewById(R.id.home_property_upload_image_button);
        property_upload_images_layout = dialog.findViewById(R.id.home_property_upload_images_layout);
        property_upload_images_inner_layout = dialog.findViewById(R.id.home_property_upload_images_inner_layout);
        property_availability_date_layout = dialog.findViewById(R.id.home_property_availability_date_layout);

        property_availability_date_textView = dialog.findViewById(R.id.home_property_availability_date_textView);

        // init all references.
        property_city_editText.setText(property.getCity());
//        property_availability_date_button.setText(property.getAvailability_date()+"");
        property_availability_date_editText.setText(property.getAvailability_date() + "");
        property_number_of_bedrooms_editText.setText(property.getNumber_of_bedrooms() +"");
        property_postal_address_editText.setText(property.getPostal_address() +"");
        property_surface_area_editText.setText(property.getSurface_area() + "");
        property_construction_year_editText.setText(property.getConstruction_year() + "");
        property_rental_price_editText.setText(property.getRental_price() + "");
        property_status_switch.setText(property.isStatus() + "");
        if(property.isStatus()) property_status_switch.setChecked(true);
        else property_status_switch.setChecked(false);
        property_balcony_switch.setText(property.isBalcony() + "");
        if(property.isBalcony()) property_balcony_switch.setChecked(true);
        else property_balcony_switch.setChecked(false);
        property_garden_switch.setText(property.isGarden() + "");
        if(property.isGarden()) property_garden_switch.setChecked(true);
        else property_garden_switch.setChecked(false);

        property_description_editText.setText(property.getDescription());
        property_renting_agency_owner_id_editText.setText(property.getRenting_agency_owner_id());

        if(!property_status_switch.isChecked()){
            property_availability_date_layout.setVisibility(View.VISIBLE);
        } else {
            property_availability_date_layout.setVisibility(View.GONE);
        }

        property_status_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!property_status_switch.isChecked()){
                    property_availability_date_layout.setVisibility(View.VISIBLE);
                } else {
                    property_availability_date_layout.setVisibility(View.GONE);
                }
            }
        });

        if(user_type == 1) {
            property_apply_button.setVisibility(View.GONE);
        }

        if(user_type == 0) {
            // test if this property is already requested (and still pended) by the reqistered user.
            Cursor temp = dataBaseHelper.get_pending_property_request_by_tenant(user_email_address, property.getProperty_id());
            int flag = 0;
            while(temp.moveToNext()){
                property_renting_status = "pending";
                flag = 1;
            }
            if(flag == 1){
                //property_apply_button.setVisibility(View.GONE);
            }

        }
    }

    public void get_property_images() {
        Images.clear();
        Cursor temp = dataBaseHelper.get_all_image_for_property(property.getProperty_id());
        int flag=0;
        Images.clear();
        while (temp.moveToNext()) { //User found
            Image img = new Image();
            img.setImage_id(temp.getInt(0));
            img.setImage(BitmapFactory.decodeByteArray(temp.getBlob(2), 0, temp.getBlob(2).length));
            flag = 1;
            Images.add(img);
        }
        if(flag == 0){
            // add default img to Images list.
        }
    }

    public void init_image_slider(){
        slideradapter slideradapter;
        if(Images.size() == 0) {
            slideradapter= new slideradapter(images_default_list);
        }
        else {
            slideradapter = new slideradapter(Images);
        }
        sliderView.setSliderAdapter(slideradapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();
    }

    public void set_property_fields_enabled(boolean isEnabled){
        property_city_editText.setEnabled(isEnabled);
//        property_availability_date_button.setEnabled(isEnabled);
        property_availability_date_editText.setEnabled(isEnabled);
        property_number_of_bedrooms_editText.setEnabled(isEnabled);
        property_postal_address_editText.setEnabled(isEnabled);
        property_surface_area_editText.setEnabled(isEnabled);
        property_construction_year_editText.setEnabled(isEnabled);
        property_rental_price_editText.setEnabled(isEnabled);
        property_status_switch.setEnabled(isEnabled);
        property_balcony_switch.setEnabled(isEnabled);
        property_garden_switch.setEnabled(isEnabled);
        property_description_editText.setEnabled(isEnabled);
        property_renting_agency_owner_id_editText.setEnabled(isEnabled);


    }

    public void update_property_obj(){
        Date available_date;
        if(property_status_switch.isChecked()){
            available_date = Utils.get_current_date();
        } else {
            available_date = Date.valueOf(property_availability_date_editText.getText().toString().trim());
        }
        updated_property = new Property(Utils.capitalize(property_city_editText.getText().toString()),
                Integer.parseInt(property_postal_address_editText.getText().toString()),
                Double.parseDouble(property_surface_area_editText.getText().toString()),
                Integer.parseInt(property_construction_year_editText.getText().toString()),
                Integer.parseInt(property_number_of_bedrooms_editText.getText().toString()),
                Double.parseDouble(property_rental_price_editText.getText().toString()),
                property_status_switch.isChecked()? true: false,
                property_balcony_switch.isChecked()? true: false,
                property_garden_switch.isChecked()? true: false,
                available_date,
                property_description_editText.getText().toString(),
                property.getRenting_agency_owner_id(),
                property.getPosting_date(),
                property.isIs_active());
        updated_property.setProperty_id(property.getProperty_id());
        property = updated_property;
    }

    public void display_images(){
        for(int i=0; i<Images.size(); i++){

            LinearLayout image_container_layout = new LinearLayout(getActivity());
            ImageView imageView = new ImageView(getActivity());
            Button delete_image_button = new Button(getActivity());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150, 150);
            params.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
            delete_image_button.setLayoutParams(params);

            init_display_img_layout(image_container_layout, imageView, delete_image_button);
            imageView.setImageBitmap(Images.get(i).getImage());
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(450, 500);
            params1.gravity = Gravity.CENTER_VERTICAL | Gravity.START;
            imageView.setLayoutParams(params1);

            property_upload_images_inner_layout.addView(image_container_layout, 0);
            added_linear_layout_list.add(image_container_layout);
            int finalI = i;
            delete_image_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Image img = Images.remove(finalI);
                    deleted_images_indexes.add(finalI);
                    property_upload_images_inner_layout.removeView(image_container_layout);
                    added_linear_layout_list.remove(image_container_layout);
                }
            });

        }
    }

    public void set_property_update_button_listener() {

        property_upload_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if permissions to reach external files is not garanted then ask for permission
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }, 0);
                }
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }

    // testing image loading from gallery.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == getActivity().RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            LinearLayout image_container_layout = new LinearLayout(getActivity());
            ImageView imageView = new ImageView(getActivity());
            Button delete_image_button = new Button(getActivity());

            init_display_img_layout(image_container_layout, imageView, delete_image_button);

            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            File file_image = new File(picturePath);
            if(file_image.length()> 2096720){
                Toast.makeText(getActivity(), "Image size is too big, please try another one", Toast.LENGTH_LONG).show();
            } else {
                property_upload_images_inner_layout.addView(image_container_layout, 0);
                added_linear_layout_list.add(image_container_layout);
                Bitmap img_bitmap = BitmapFactory.decodeFile(picturePath);
                images_bitmap.add(img_bitmap);

                delete_image_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        images_bitmap.remove(img_bitmap);
                        property_upload_images_inner_layout.removeView(image_container_layout);
                        added_linear_layout_list.remove(image_container_layout);
                    }
                });
            }
        }
    }

    public void init_display_img_layout(LinearLayout image_container_layout,ImageView imageView, Button delete_image_button){
        image_container_layout.setOrientation(LinearLayout.HORIZONTAL);
        image_container_layout.setLayoutParams(new
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup
                .LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(450, 500);
        params1.gravity = Gravity.CENTER_VERTICAL | Gravity.START;
        imageView.setLayoutParams(params1);
        delete_image_button.setBackground(getActivity().getDrawable(R.drawable.delete));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150, 150);
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
        delete_image_button.setLayoutParams(params);
        image_container_layout.addView(imageView);
        image_container_layout.addView(delete_image_button);
    }

    public void update_property_images(){

        // delete needed images
        for(int i=0; i< deleted_images_indexes.size(); i++){
            dataBaseHelper.delete_image_by_id(Images.get(deleted_images_indexes.get(i)).getImage_id());
            Images.remove(Images.get(deleted_images_indexes.get(i)));
            sliderView.dataSetChanged();
        }
        // add needed images
        for(int i=0; i<images_bitmap.size(); i++){
            Image img = new Image("img" + i, images_bitmap.get(i), property.getProperty_id());
            Images.add(img);
            sliderView.dataSetChanged();
            dataBaseHelper.insert_property_image(img);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month);
        cal.set(Calendar.DAY_OF_MONTH,day);

        String dateTemp = DateFormat.getDateInstance().format(cal.getTime().getYear()) + "-" + DateFormat.getDateInstance().format(cal.getTime().getMonth())+"-"+DateFormat.getDateInstance().format(cal.getTime().getDay());
        String date = DateFormat.getDateInstance().format(cal.getTime());
        SimpleDateFormat formatter3=new SimpleDateFormat("MMM dd, yyyy");
        java.util.Date date3= null;
        try {
            date3 = formatter3.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date sqlDate = new java.sql.Date(date3.getTime());
    }


    public void get_user_authentication(){
        sharedPrefManager = SharedPrefManager.getInstance(getActivity());
        user_email_address = sharedPrefManager.readString("username", "username");
        user_type = sharedPrefManager.readInt("type", 100);
        if(user_email_address.equals("username") || (user_type != 0 && user_type != 1)){
            // user is not authenticated so direct to the login page.
            is_authenticated = false;
        } else {
            is_authenticated = true;
        }
    }

}
