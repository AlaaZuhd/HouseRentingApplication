package com.example.lab_project;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab_project.helpers.DataBaseHelper;
import com.example.lab_project.helpers.SharedPrefManager;
import com.example.lab_project.helpers.Utils;
import com.example.lab_project.models.Notification;
import com.example.lab_project.models.Property;
import com.example.lab_project.models.RequestProperty;
import com.example.lab_project.models.User;
import com.google.android.material.bottomsheet.BottomSheetDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    DataBaseHelper dataBaseHelper;
    SharedPrefManager sharedPrefManager;
    Intent intent;
    int user_type;
    String user_email_address;
    boolean is_authenticated = false;
    Notification notification;
    RequestProperty requestProperty;
    Property property;
    User user;
    boolean is_out_of_date_request = false;
    TextView notification_summary_message_textView;
    TextView notification_sending_date_textView;
    LinearLayout notification_fragment_layout;
    LinearLayout notification_apply_request_details_layout;
    ScrollView notification_scrollview;

    TextView notification_request_details_requester_id;
    TextView notification_request_details_requested_property_id;
    TextView notification_request_details_renting_period;
    TextView notification_request_details_request_date;
    TextView notification_request_details_request_status;


    Button notification_view_tenant_profile_button;
    Button notification_view_tenant_renting_history_button;
    Button notification_approve_button;
    Button notification_reject_button;
    Button notification_close_button;

    ConstraintLayout notification_reject_details_layout;
    TextView notification_reject_details_property_id_textView;
    Button notification_reject_details_try_renting_button;

    ConstraintLayout notification_approve_details_layout;
    TextView notification_approve_details_property_id_textView;
    Button notification_approve_details_see_rented_property_button;


    String notification_mes;

    private View root_view;
    private String fragment_tag;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
        root_view = inflater.inflate(R.layout.fragment_notification, container, false);
        return root_view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dataBaseHelper =new DataBaseHelper(getActivity(), "Lab_Project.db",null,1);


        // get references for views
        get_references();

        Bundle bundle = getArguments();
        if (bundle != null) {
            fragment_tag = bundle.getString("fragment_tag");
            user_email_address = bundle.getString("user_email_address");
            init_notification(bundle);
            get_request();
            init_views();
        } else {

        }

    }

    public void get_references(){
        notification_summary_message_textView = (TextView) root_view.findViewById(R.id.notification_summary_message_textView);
        notification_sending_date_textView = (TextView) root_view.findViewById(R.id.notification_sending_date_textView);
        notification_fragment_layout = (LinearLayout) root_view.findViewById(R.id.notification_fragment_layout);
        notification_apply_request_details_layout = (LinearLayout) getActivity().findViewById(R.id.notification_apply_request_details_layout);
        notification_scrollview = (ScrollView) getActivity().findViewById(R.id.notification_scrollview);
//        notification_tenant_id_textView = (TextView) getActivity().findViewById(R.id.notification_tenant_id_textView);
//        notification_property_id_textView = (TextView) getActivity().findViewById(R.id.notification_property_id_textView);
//        notification_request_date_textView = (TextView) getActivity().findViewById(R.id.notification_request_date_textView);
//
        notification_request_details_requester_id = (TextView) getActivity().findViewById(R.id.request_details_requester_id);
        notification_request_details_requested_property_id = (TextView) getActivity().findViewById(R.id.request_details_property_id);
        notification_request_details_renting_period = (TextView) getActivity().findViewById(R.id.request_details_renting_period);
        notification_request_details_request_status = (TextView) getActivity().findViewById(R.id.request_details_request_status);
        notification_request_details_request_date = (TextView) getActivity().findViewById(R.id.request_details_request_date);

        notification_view_tenant_profile_button = (Button) getActivity().findViewById(R.id.notification_view_tenant_profile_button);
        notification_view_tenant_renting_history_button = (Button) getActivity().findViewById(R.id.notification_view_tenant_renting_history_button);
        notification_approve_button = (Button) getActivity().findViewById(R.id.notification_approve_button);
        notification_reject_button = (Button) getActivity().findViewById(R.id.notification_reject_button);
        notification_close_button = (Button) getActivity().findViewById(R.id.notification_close_button);

        notification_reject_details_layout = (ConstraintLayout) getActivity().findViewById(R.id.notification_reject_details_layout);
        notification_reject_details_property_id_textView = (TextView) getActivity().findViewById(R.id.notification_reject_details_property_id_textView);
        notification_reject_details_try_renting_button = (Button) getActivity().findViewById(R.id.notification_reject_details_try_renting_button);

        notification_approve_details_layout = (ConstraintLayout) getActivity().findViewById(R.id.notification_approve_details_layout);
        notification_approve_details_property_id_textView = (TextView) getActivity().findViewById(R.id.notification_approve_details_property_id_textView);
        notification_approve_details_see_rented_property_button = (Button) getActivity().findViewById(R.id.notification_approve_details_see_rented_property_button);

        notification_fragment_layout.setOnLongClickListener(new View.OnLongClickListener(){
            @Override public boolean onLongClick(    View v){
                showNotificationBottomSheetDialog();
                return true;
            }
        });
    }

    public void showNotificationBottomSheetDialog(){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(R.layout.notification_bottom_sheet_dialog);

        LinearLayout show = bottomSheetDialog.findViewById(R.id.notification_show_layout);
        LinearLayout delete = bottomSheetDialog.findViewById(R.id.notification_delete_layout);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delete this notifcation.
                dataBaseHelper.delete_notification_by_id(notification.getNotification_id());
                notification_fragment_layout.removeAllViews();
                notification_fragment_layout.setVisibility(View.GONE);
                bottomSheetDialog.dismiss();
            }});

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification_scrollview.setVisibility(View.GONE);
                // update notification to mark it as read.

                // if appy request
                if(notification.getNotification_type().equals("apply_request")) {
                    init_apply_request_layout();
                    notification_apply_request_details_layout.setVisibility(View.VISIBLE);
                } else if (notification.getNotification_type().equals("approve")) {
                    init_approve_layout();
                    notification_approve_details_layout.setVisibility(View.VISIBLE);
                } else if (notification.getNotification_type().equals("reject")) {
                    init_reject_layout();
                    notification_reject_details_layout.setVisibility(View.VISIBLE);
                }
                bottomSheetDialog.dismiss();
            }});

        bottomSheetDialog.show();
    }

    public void init_notification(Bundle bundle){
        notification = new Notification(bundle.getInt("notification_id"),
                bundle.getString("from_id"),
                bundle.getString("to_id"),
                bundle.getInt("request_id"),
                bundle.getBoolean("is_read"),
                bundle.getString("sending_date"),
                bundle.getString("notification_type"));

        System.out.println(notification.toString());
    }

    public void get_request(){
        // get the request..
        Cursor temp = dataBaseHelper.get_property_request(notification.getRequest_id());
        int flag = 0;
        while (temp.moveToNext()) {
            requestProperty = new RequestProperty();
            requestProperty.setRequest_property_id(temp.getInt(0));
            requestProperty.setProperty_id(temp.getInt(1));
            requestProperty.setTenant_id(temp.getString(2));
            requestProperty.setRequest_date(temp.getString(3));
            requestProperty.setIs_approved(temp.getInt(4) == 1? true: false);
            requestProperty.setIs_rejected(temp.getInt(5) == 1? true: false);
            requestProperty.setFrom_date(temp.getString(6));
            requestProperty.setTo_date(temp.getString(7));
            System.out.println(requestProperty.isIs_approved() + " " + requestProperty.isIs_rejected() + notification.getRequest_id());
            flag = 1;
            break;
        }
        property = new Property();
        property.setProperty_id(requestProperty.getProperty_id());
            // get the user.
//        Cursor temp = dataBaseHelper.get_property_request(notification.getRequest_id());
//        while (temp.moveToNext()) { //User found
//            requestProperty = new RequestProperty();
//            requestProperty.setRequest_property_id(temp.getInt(0));
//            requestProperty.setProperty_id(temp.getInt(1));
//            requestProperty.setTenant_id(temp.getString(2));
//            requestProperty.setRequest_date(temp.getString(3));
//        }
            // get the property;
    }

    public void init_views(){

        notification_apply_request_details_layout.setVisibility(View.GONE);
        notification_reject_details_layout.setVisibility(View.GONE);

        if(notification.getNotification_type().equals("apply_request")){
            notification_mes = "A request from the tenant with ID: " +notification.getFrom_id() + " for renting your Property with ID: " + property.getProperty_id() + " is waiting for your approval.";
        } else if (notification.getNotification_type().equals("approve")) {
            notification_mes = "Congratulations, Yor request for renting Property with ID: " + property.getProperty_id() + " has been approved.";
        } else if (notification.getNotification_type().equals("reject")) {
            notification_mes = "Unfortunately, Yor request for renting Property with ID: " + property.getProperty_id() + " has been rejected.";
        }
        notification_summary_message_textView.setText(notification_mes);
        notification_sending_date_textView.setText(notification.getSending_date());

        if(!notification.isIs_read()){
            notification_fragment_layout.setBackgroundColor(Color.parseColor("#266200EE"));
            notification_summary_message_textView.setTypeface(null, Typeface.BOLD);
            notification_sending_date_textView.setTypeface(null, Typeface.BOLD);
        } else {
            notification_fragment_layout.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        notification_fragment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().findViewById(R.id.notification_delete_all_button).setVisibility(View.GONE);
                notification_scrollview.setVisibility(View.GONE);
                // update notification to mark it as read.
                System.out.println("going to mark notification as read");
                System.out.println(dataBaseHelper.update_is_read_notification(notification.getNotification_id(), true));

                // if appy request
                if(notification.getNotification_type().equals("apply_request")) {
                    init_apply_request_layout();
                    notification_apply_request_details_layout.setVisibility(View.VISIBLE);
                } else if (notification.getNotification_type().equals("approve")) {
                    init_approve_layout();
                    notification_approve_details_layout.setVisibility(View.VISIBLE);
                } else if (notification.getNotification_type().equals("reject")) {
                    init_reject_layout();
                    notification_reject_details_layout.setVisibility(View.VISIBLE);
                }

                notification_fragment_layout.setBackgroundColor(Color.parseColor("#ffffff"));
                notification_summary_message_textView.setTypeface(null, Typeface.NORMAL);
                notification_sending_date_textView.setTypeface(null, Typeface.NORMAL);

            }});

    }

    public void init_apply_request_layout() {
        get_request();
//        notification_tenant_id_textView.setText("From: " + notification.getFrom_id());
//        notification_property_id_textView.setText("Requested Property ID: " + property.getProperty_id());
//        notification_request_date_textView.setText("Request Date: " + notification.getSending_date());

        notification_request_details_requester_id.setText(requestProperty.getTenant_id());
        notification_request_details_requested_property_id.setText(requestProperty.getProperty_id() + "");
        notification_request_details_renting_period.setText(requestProperty.getFrom_date() + " - " + requestProperty.getTo_date());
        notification_request_details_request_date.setText("Request Date: " + requestProperty.getRequest_date());
        if (!requestProperty.isIs_approved() && !requestProperty.isIs_rejected() && !Utils.isAvailabilityDateValid(requestProperty.getFrom_date())){
            is_out_of_date_request = true;
        notification_request_details_request_status.setText("Out of Date Request");
        } else {
            notification_request_details_request_status.setText(requestProperty.isIs_approved() ? "Approved" : requestProperty.isIs_rejected() ? "Rejected" : "Pended");
        }
        // if request was handled => rejected or approved then hide both approve and reject buttons.
        if(is_out_of_date_request) {
            notification_reject_button.setVisibility(View.GONE);
            notification_approve_button.setVisibility(View.GONE);
        }
        else if(requestProperty.isIs_approved() == true || requestProperty.isIs_rejected() == true) {
            notification_reject_button.setVisibility(View.GONE);
            notification_approve_button.setVisibility(View.GONE);
            System.out.println("Hi hi hi hih hih hih ih hi what happend???????");
        } else {
            notification_reject_button.setVisibility(View.VISIBLE);
            notification_approve_button.setVisibility(View.VISIBLE);
        }

        notification_view_tenant_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra("view_other_profile", true);
                intent.putExtra("tenant_email_address", notification.getFrom_id());
                getActivity().startActivity(intent);
            }
        });

        notification_view_tenant_renting_history_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), TenantHistoryActivity.class);
                intent.putExtra("view_other_profile", true);
                intent.putExtra("tenant_email_address", notification.getFrom_id());
                getActivity().startActivity(intent);
            }
        });

        notification_approve_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // reflect the approval on the request..
                boolean approval_status = dataBaseHelper.reject_request(requestProperty.getRequest_property_id());
                if(approval_status) {
                    // update the tenant_property table to add a new row.
                    dataBaseHelper.insert_property_tenant(requestProperty.getTenant_id(), requestProperty.getProperty_id(), requestProperty.getFrom_date(), requestProperty.getTo_date());
                    // reject all other requests for this property...
                    reject_conflicted_requests_for_renting_property(requestProperty);
                    // send a notification to th euser with type approve.
                    String sending_date = Utils.convert_date_to_String(Utils.get_current_date(), "yyyy-MM-dd");
                    Notification notification = new Notification(user_email_address, requestProperty.getTenant_id(), requestProperty.getRequest_property_id(), false, sending_date, "approve");
                    int notification_id = dataBaseHelper.insert_notification(notification);
                    if(notification_id != -1) {
                        notification.setNotification_id(notification_id);
                        notification_approve_button.setVisibility(View.GONE);
                        notification_reject_button.setVisibility(View.GONE);
                        notification_request_details_request_status.setText("Approved");
                    }else {
                        Toast.makeText(getActivity(), "Failed to send a notification to the user!", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getActivity(), "Rejection Failed, please try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        notification_reject_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // reflect the rejection on the database.
                boolean rejection_status = dataBaseHelper.reject_request(requestProperty.getRequest_property_id());
                if(rejection_status) {
                    // send a notification to th euser with type reject..
                    String sending_date = Utils.convert_date_to_String(Utils.get_current_date(), "yyyy-MM-dd");
                    Notification notification = new Notification(user_email_address, requestProperty.getTenant_id(), requestProperty.getRequest_property_id(), false, sending_date, "reject");
                    int notification_id = dataBaseHelper.insert_notification(notification);
                    if(notification_id != -1) {
                        notification.setNotification_id(notification_id);
                        notification_approve_button.setVisibility(View.GONE);
                        notification_reject_button.setVisibility(View.GONE);
                        notification_request_details_request_status.setText("Rejected");
                    }else {
                        Toast.makeText(getActivity(), "Failed to send a notification to the user!", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getActivity(), "Rejection Failed, please try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        notification_close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification_apply_request_details_layout.setVisibility(View.GONE);
                notification_scrollview.setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.notification_delete_all_button).setVisibility(View.VISIBLE);
            }
        });
    }

    public void init_reject_layout() {
        get_request();
        notification_reject_details_property_id_textView.setText(requestProperty.getProperty_id() + "");
        notification_reject_details_property_id_textView.setTextColor(Color.parseColor("#f620ee"));

        notification_reject_details_try_renting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // move to the rent..
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    public void init_approve_layout() {
        get_request();
        notification_approve_details_property_id_textView.setText(requestProperty.getProperty_id() + "");
        notification_approve_details_property_id_textView.setTextColor(Color.parseColor("#f620ee"));

        notification_approve_details_see_rented_property_button.setVisibility(View.GONE);

        notification_approve_details_see_rented_property_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // move to the rented history..
            }
        });
    }



    public void reject_conflicted_requests_for_renting_property(RequestProperty request) {
        // get all pended requests for this property.
        String sql = "SELECT * FROM PROPERTY_REQUEST WHERE IS_REJECTED = 0 AND IS_APPROVED = 0 AND PROPERTY_ID =  " + request.getProperty_id() + " AND NOT (FROM_DATE >= ('"+ request.getTo_date() + "') OR TO_DATE <= ('" + request.getFrom_date() + "') )";
        Cursor temp = dataBaseHelper.search_request_property(sql);
        while (temp.moveToNext()) {
            RequestProperty requestProperty = new RequestProperty();
            requestProperty.setRequest_property_id(temp.getInt(0));
            requestProperty.setProperty_id(temp.getInt(1));
            requestProperty.setTenant_id(temp.getString(2));
            requestProperty.setRequest_date(temp.getString(3));
            requestProperty.setIs_approved(temp.getInt(4) == 1? true: false);
            requestProperty.setIs_rejected(temp.getInt(5) == 1? true: false);
            reject_request(requestProperty.getRequest_property_id(), requestProperty.getTenant_id(), user_email_address);
        }
    }

    public void reject_request(int request_id, String tenant_id, String owner_id){
        // reflect the rejection on the database.
        boolean rejection_status = dataBaseHelper.reject_request(request_id);
        if(rejection_status) {
            // send a notification to th euser with type reject..
            String sending_date = Utils.convert_date_to_String(Utils.get_current_date(), "yyyy-MM-dd");
            Notification notification = new Notification(owner_id, tenant_id, request_id, false, sending_date, "reject");
            int notification_id = dataBaseHelper.insert_notification(notification);
            notification.setNotification_id(notification_id);
            if(notification_id != -1) {
            }else {
                Toast.makeText(getActivity(), "Failed to send a notification to the user!", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(getActivity(), "Rejection Failed, please try again!", Toast.LENGTH_SHORT).show();
        }
    }
}