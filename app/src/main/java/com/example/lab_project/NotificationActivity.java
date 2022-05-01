package com.example.lab_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.lab_project.helpers.DataBaseHelper;
import com.example.lab_project.helpers.SharedPrefManager;
import com.example.lab_project.helpers.Utils;
import com.example.lab_project.models.Notification;
import com.example.lab_project.models.Property;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    Intent intent;
    int user_type;
    String user_email_address;
    boolean is_authenticated = false;
    DataBaseHelper dataBaseHelper =new DataBaseHelper(NotificationActivity.this,"Lab_Project.db",null,1);
    List<Notification> notifications = new ArrayList<>();
    List<NotificationFragment> notificationFragments = new ArrayList<>();
    final FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;

    ScrollView notification_scrollview;
    LinearLayout notification_scrollview_layout;
    Button notification_delete_all_button;
    Button notification_arrowback_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notification_scrollview = (ScrollView) findViewById(R.id.notification_scrollview);
        notification_scrollview_layout = (LinearLayout) findViewById(R.id.notification_scrollview_layout);
        notification_delete_all_button = (Button) findViewById(R.id.notification_delete_all_button);

        notification_arrowback_button = (Button) findViewById(R.id.notification_arrowback_button);

        notification_arrowback_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Do the notification work and get the old animation
                intent = new Intent(NotificationActivity.this, NavigationDrawerActivity.class);
                startActivity(intent);
            }
        });


        // get user authentication.
        get_user_authentication();
        Cursor temp = dataBaseHelper.get_user_in_notification(user_email_address);
        while (temp.moveToNext()) { //User found
            Notification notification = new Notification();
            notification.setNotification_id(temp.getInt(0));
            notification.setFrom_id(temp.getString(1));
            notification.setTo_id(temp.getString(2));
            notification.setRequest_id(temp.getInt(3));
            notification.setIs_read(temp.getInt(4) ==1? true: false);
            notification.setSending_date(temp.getString(5));
            notification.setNotification_type(temp.getString(6));
            notifications.add(notification);
        }

        if(notifications.size() == 0 ){
            // diaply no results found page with suitable text.
            findViewById(R.id.notification_scrollview).setVisibility(View.GONE);
            NoResultsFoundFragment  no_results_found_fragment = new NoResultsFoundFragment();
            Bundle bundle = new Bundle();
            bundle.putString("no_results_type", "no notifications");
            no_results_found_fragment.setArguments(bundle);

            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.notification_root_layout, no_results_found_fragment, "Fragment_");
            fragmentTransaction.commit();
            getSupportFragmentManager().executePendingTransactions();
            if(fragmentManager.findFragmentByTag("Fragment_") != null)
                System.out.println("Not null from inside activity");
        } else{
            int notifications_size = notifications.size();
            for(int i=notifications_size-1; i>=0; i--){
                NotificationFragment notification_fragment = new NotificationFragment();

                Bundle bundle = new Bundle();
                bundle.putString("from_id", "" + notifications.get(i).getFrom_id());
                bundle.putString("to_id", notifications.get(i).getTo_id());
                bundle.putInt("request_id", notifications.get(i).getRequest_id());
                bundle.putInt("notification_id", notifications.get(i).getNotification_id());
                bundle.putBoolean("is_read", notifications.get(i).isIs_read());
                bundle.putString("sending_date", notifications.get(i).getSending_date());
                bundle.putString("notification_type", notifications.get(i).getNotification_type());
                bundle.putString("user_email_address", user_email_address);
                notification_fragment.setArguments(bundle);

                notificationFragments.add(notification_fragment);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.notification_scrollview_layout, notificationFragments.get(notifications_size - i-1), "Fragment_" + (notifications_size - i));
                fragmentTransaction.commit();
                getSupportFragmentManager().executePendingTransactions();

            }
        }

        notification_delete_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delete all notifications.
                if(dataBaseHelper.delete_all_notifications_for_user(user_email_address)){
                    Toast.makeText(NotificationActivity.this, "Notifications has been deleted successfully!", Toast.LENGTH_SHORT).show();
                }
                // update the layout ----> remove all views.
                notification_scrollview.removeAllViews();
            }});

    }

    public void get_user_authentication() {
        sharedPrefManager = SharedPrefManager.getInstance(this);
        user_email_address = sharedPrefManager.readString("username", "username");
        user_type = sharedPrefManager.readInt("type", 100);


        if (user_email_address.equals("username") || (user_type != 0 && user_type != 1)) {
            is_authenticated = false;
        } else {
            is_authenticated = true;
        }
    }

    }