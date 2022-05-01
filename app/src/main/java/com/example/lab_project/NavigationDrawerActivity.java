package com.example.lab_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NavigationDrawerActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    //variables
    SharedPrefManager sharedPrefManager;
    Intent intent;
    int user_type;
    String user_email_address;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button notificationButton;


    final ArrayList<Property> properties = new ArrayList<>();
    List<PropertySummaryFragment> propertySummaryFragments = new ArrayList<>();
    final FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;
    DataBaseHelper dataBaseHelper =new DataBaseHelper(NavigationDrawerActivity.this,"Lab_Project.db",null,1);

    ImageView property_image[] = new ImageView[5]; // to hole the first image of each property.
    TextView property_city[] = new TextView[5]; // to hold the city..
    TextView property_availability_date[] = new TextView[5];
    TextView property_number_of_bedrooms[] = new TextView[5];
    Button property_view[] = new Button[5];//
    LinearLayout linear_layouts[] = new LinearLayout[5];
    SliderView sliderView;
    final ArrayList<Image> Images = new ArrayList<>();

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        sharedPrefManager =SharedPrefManager.getInstance(this);

        // get user authentication.
        get_user_authentication();

        final ImageView imageView = (ImageView)
                findViewById(R.id.notificationImageView);

        notificationButton = (Button) findViewById(R.id.notificationButton);

        if(check_user_unread_in_notifications()) {
            //Check Logic to put the notification image
            TransitionDrawable transitionDrawable = (TransitionDrawable) imageView.getDrawable();
            transitionDrawable.startTransition(2000);
        }
        //

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Do the notification work and get the old animation
                imageView.setImageResource(R.drawable.ic_notification);
                intent = new Intent(NavigationDrawerActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });



        //Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar  = findViewById(R.id.toolbar);
        //ToolBar

        //getActionBar().hide();//Ocultar ActivityBar anterior
        //setSupportActionBar(toolbar);
        //navigation drawer menu
        navigationView.bringToFront();
        if(user_type == 1){ // if renting agency
            navigationView.getMenu().findItem(R.id.nav_history_tenant).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_rent).setVisible(false);
        }else{ // if tenant
            navigationView.getMenu().findItem(R.id.nav_edit).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_post_property).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_history_agency).setVisible(false);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        Intent intent;
                        switch (menuItem.getItemId()){
                            case R.id.nav_home:
                                intent = new Intent(NavigationDrawerActivity.this, NavigationDrawerActivity.class);
                                startActivity(intent);
                                break;
//                            case R.id.nav_logout:
//                                Toast.makeText(this, "Not Supported Yet", Toast.LENGTH_SHORT).show();
//                                break;
                            case R.id.nav_post_property:
                                intent = new Intent(NavigationDrawerActivity.this, PostPropertyActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_edit:
                                intent = new Intent(NavigationDrawerActivity.this, EditListOfPropertiesActivity.class);
                                startActivity(intent);
                                break;

                            case R.id.nav_profile:
                                intent = new Intent(NavigationDrawerActivity.this, ProfileActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_search:
                                intent = new Intent(NavigationDrawerActivity.this, SearchActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_logout:
                                // remove authentication....
                                remove_user_authentication();
                                intent = new Intent(NavigationDrawerActivity.this, LoginActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_history_tenant:
                                intent = new Intent(NavigationDrawerActivity.this, TenantHistoryActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_history_agency:
                                intent = new Intent(NavigationDrawerActivity.this, AgencyHistoryActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_rent:
                                intent = new Intent(NavigationDrawerActivity.this, SearchActivity.class);
                                startActivity(intent);
                                break;

                        }
                        // close drawer when item is tapped
                        //drawerLayout.closeDrawers();
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        return true;
                    }
                });
        // --------------------------------------------------------------

        if((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)  == Configuration.UI_MODE_NIGHT_YES){
            findViewById(R.id.home_layout).setBackgroundColor(R.color.black);
        }

        dataBaseHelper.get_tables_names();



        //get properties and save them in properties array list.
        final Cursor[] temp = {dataBaseHelper.get_active_properties()};
        while (temp[0].moveToNext()) { //User found
            Property property = new Property();
            property.setProperty_id(Integer.parseInt(temp[0].getString(0)));
            property.setCity(temp[0].getString(1));
            property.setPostal_address(Integer.parseInt(temp[0].getString(2)));
            property.setSurface_area(Double.parseDouble(temp[0].getString(3)));
            property.setConstruction_year(Integer.parseInt(temp[0].getString(4)));
            property.setNumber_of_bedrooms(Integer.parseInt(temp[0].getString(5)));
            property.setRental_price(Double.parseDouble(temp[0].getString(6)));
            property.setStatus(Utils.sql_string_to_boolean(temp[0].getString(7)));
            property.setBalcony(Utils.sql_string_to_boolean(temp[0].getString(8)));
            property.setGarden(Utils.sql_string_to_boolean(temp[0].getString(9)));
            property.setAvailability_date(Date.valueOf(temp[0].getString(10)));
            property.setDescription(temp[0].getString(11));
            property.setRenting_agency_owner_id(temp[0].getString(12));
            property.setPosting_date(Date.valueOf(temp[0].getString(13)));
            property.setIs_active(temp[0].getInt(14) == 1? true: false);
            properties.add(property);
        }
        // display the list of properties.
        int j= 0;
        for(int i=0; i<properties.size() && j<5; i++) {
            boolean is_active = Utils.check_update_is_active_property(properties.get(i), dataBaseHelper, this);
            if(!is_active)
                break;
            else
                j++;
            PropertySummaryFragment property_summary_fragment = new PropertySummaryFragment();

            Bundle bundle = new Bundle();
            bundle.putString("city", ""+ properties.get(i).getCity());
            bundle.putInt("postal_address", properties.get(i).getPostal_address());
            bundle.putDouble("surface_area", properties.get(i).getSurface_area());
            bundle.putInt("construction_year", properties.get(i).getConstruction_year());
            bundle.putInt("number_of_bedrooms", properties.get(i).getNumber_of_bedrooms());
            bundle.putDouble("rental_price", properties.get(i).getRental_price());
            bundle.putBoolean("status", properties.get(i).isStatus());
            bundle.putBoolean("balcony", properties.get(i).isBalcony());
            bundle.putBoolean("garden", properties.get(i).isGarden());
            bundle.putString("availability_date", properties.get(i).getAvailability_date().toString());
            bundle.putString("description", properties.get(i).getDescription());
            bundle.putInt("property_id", properties.get(i).getProperty_id());
            bundle.putString("renting_agency_owner_id", properties.get(i).getRenting_agency_owner_id());
            bundle.putBoolean("is_active", properties.get(i).isIs_active());
            bundle.putString("posting_date", properties.get(i).getPosting_date().toString());
            bundle.putString("fragment_tag", "Fragment_"+j);
            property_summary_fragment.setArguments(bundle);

            propertySummaryFragments.add(property_summary_fragment);
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.home_layout, property_summary_fragment, "Fragment_" + j);
            fragmentTransaction.commit();
            getSupportFragmentManager().executePendingTransactions();
            if(fragmentManager.findFragmentByTag("Fragment_" + j) != null)
                System.out.println("Not null from inside activity");
        }
    }


    public void remove_user_authentication() {
        sharedPrefManager.removeKey("username");
        sharedPrefManager.removeKey("type");
    }

    public void get_user_authentication(){
        sharedPrefManager = SharedPrefManager.getInstance(this);
        user_email_address = sharedPrefManager.readString("username", "username");
        user_type = sharedPrefManager.readInt("type", 100);


        if(user_email_address.equals("username") || (user_type != 0 && user_type != 1)){
            // user is not authenticated so direct to the login page.
            Toast.makeText(NavigationDrawerActivity.this, "User is not authenticated, you need to log in", Toast.LENGTH_SHORT).show();
            intent = new Intent(NavigationDrawerActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public boolean check_user_unread_in_notifications() { // if there is notifications return true, else false.
        String sql = "SELECT * FROM NOTIFICATION WHERE TO_ID = \"" + user_email_address +"\" AND IS_READ = 0" ;
        Cursor temp = dataBaseHelper.search_notification(sql);
        while (temp.moveToNext()) { //User found
            return true;
        }
        return false;
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
        //post_property_availability_date_button.setText(Utils.convert_date_to_String(sqlDate, "yyyy-MM-dd"));
        DatePickerFragment date_picker = new DatePickerFragment();
        date_picker.setDate(Utils.convert_date_to_String(sqlDate, "yyyy-MM-dd"));
    }

}