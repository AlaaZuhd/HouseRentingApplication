package com.example.lab_project;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab_project.helpers.DataBaseHelper;
import com.example.lab_project.helpers.SharedPrefManager;
import com.example.lab_project.models.Image;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoResultsFoundFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoResultsFoundFragment extends Fragment {

    SharedPrefManager sharedPrefManager;
    Intent intent;
    int user_type;
    String user_email_address;
    String tenant_renting_email;
    boolean is_authenticated = false;


    private View root_view;
    private String no_results_type; // Search, Tenant History, .// .
    TextView no_results_found_textView;
    ImageView no_results_found_imageView;
    Button no_results_found_button;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NoResultsFoundFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoResultsFoundFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoResultsFoundFragment newInstance(String param1, String param2) {
        NoResultsFoundFragment fragment = new NoResultsFoundFragment();
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
        root_view = inflater.inflate(R.layout.fragment_no_results_found, container, false);
        return root_view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        no_results_found_textView = (TextView) root_view.findViewById(R.id.no_results_found_textView);
        no_results_found_imageView = (ImageView) root_view.findViewById(R.id.no_results_found_imageView);
        no_results_found_button= (Button) root_view.findViewById(R.id.no_results_found_button);
        sharedPrefManager = SharedPrefManager.getInstance(getActivity());

        // get user authentication.
        get_user_authentication();

        final int[] i = {0};
        Bundle bundle = getArguments();
        if (bundle != null) {
            no_results_type = bundle.getString("no_results_type");
            if (no_results_type.equals("tenant history"))
                tenant_renting_email = bundle.getString("email");
            init_view();
        }
    }

    public void init_view(){
        if(no_results_type == "search"){
            no_results_found_textView.setText("No Search Results to Display");
            no_results_found_button.setText("Try again");
            no_results_found_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(getActivity(), SearchActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }
            });
        }else if(no_results_type == "tenant history"){
            no_results_found_textView.setText("No Renting History to Display");
            no_results_found_button.setText("Rent a Property Now");
            if (!user_email_address.equals(tenant_renting_email))
                no_results_found_button.setVisibility(View.GONE);
            no_results_found_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(getActivity(), SearchActivity.class); // move to renttt. .
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }
            });
        }else if(no_results_type == "agency history"){
            no_results_found_textView.setText("No Renting History to Display");
            no_results_found_button.setText("Rent a Property Now"); // to approve ....
            no_results_found_button.setVisibility(View.GONE);
//            no_results_found_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    intent = new Intent(getActivity(), SearchActivity.class); // move to renttt. .
//                    getActivity().startActivity(intent);
//                    getActivity().finish();
//                }
//            });
        } else if (no_results_type == "no notifications"){
            no_results_found_textView.setText("There is no notifications to Display");
            no_results_found_button.setText("Back to Home");
            no_results_found_imageView.setImageResource(R.drawable.no_notification);
            no_results_found_imageView.setRotation(0);
            no_results_found_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(getActivity(), HomeActivity.class); // move to renttt. .
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }
            });
        }
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