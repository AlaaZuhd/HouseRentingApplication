package com.example.lab_project;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab_project.helpers.AESEncryption_Decryption;
import com.example.lab_project.helpers.DataBaseHelper;
import com.example.lab_project.helpers.InitValidateProfileFormFields;
import com.example.lab_project.helpers.Utils;
import com.example.lab_project.models.RentingAgency;
import com.example.lab_project.models.Tenant;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RentingAgencyProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RentingAgencyProfileFragment extends Fragment {


    View root_view;
    RentingAgency renting_agency = new RentingAgency();
    DataBaseHelper dataBaseHelper;
    public ArrayAdapter<String> objArr;

    Boolean is_view_mode = false;



    EditText profile_renting_agency_email_address_editText;
    EditText profile_renting_agency_name_editText;
    EditText profile_renting_agency_phone_number_editText;
    EditText profile_renting_agency_password_editText;
    EditText profile_renting_agency_new_password_editText;
    EditText profile_renting_agency_confirm_new_password_editText;
    Spinner profile_renting_agency_country_spinner;
    Spinner profile_renting_agency_city_spinner;

    Button profile_renting_agency_email_address_button;
    Button profile_renting_agency_name_button;
    Button profile_renting_agency_phone_number_button;
    Button profile_renting_agency_country_button;
    Button profile_renting_agency_city_button;
    Button profile_renting_agency_change_password_button;

    Button profile_renting_agency_email_address_cancel_button;
    Button profile_renting_agency_name_cancel_button;
    Button profile_renting_agency_phone_number_cancel_button;
    Button profile_renting_agency_country_cancel_button;
    Button profile_renting_agency_city_cancel_button;
    Button profile_renting_agency_change_password_cancel_button;

    TextView profile_user_name_textview;

    TextView    renting_agency_area_code_textview;
    ImageView renting_agency_area_code_imageview;
    LinearLayout profile_renting_agency_change_password_layout;

    int []      area_code_flag_ids= {0,0,0,0,0,0,0,0,0};
    int index = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RentingAgencyProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RentingAgencyProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RentingAgencyProfileFragment newInstance(String param1, String param2) {
        RentingAgencyProfileFragment fragment = new RentingAgencyProfileFragment();
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
        root_view = inflater.inflate(R.layout.fragment_renting_agency_profile, container, false);
        dataBaseHelper =new DataBaseHelper(getActivity(), "Lab_Project.db",null,1);
        Bundle bundle = getArguments();
        if (bundle != null){
            String email_address = bundle.getString("email_address");
            // get renting agency from database.
            dataBaseHelper =new DataBaseHelper(getActivity(), "Lab_Project.db",null,1);
            Cursor temp = dataBaseHelper.get_renting_agency(email_address);
            int flag=0;
            while (temp.moveToNext()) { //User found
                flag=1;
                try {
                    String decrypted_password = AESEncryption_Decryption.decrypt(temp.getString(2));
                    renting_agency.setName(temp.getString(1));
                    renting_agency.setPassword(decrypted_password);
                    renting_agency.setCountry(temp.getString(3));
                    renting_agency.setCity(temp.getString(4));
                    renting_agency.setPhone_number(temp.getString(5));
                    renting_agency.setEmail_address(email_address);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error in decrypting tenant password", Toast.LENGTH_SHORT).show();
                    // may go back to home.
                    Intent intent = new Intent(getActivity(), NavigationDrawerActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }
            }
            init_area_code_flag_ids();
            get_references();
            if(flag == 1){
                // there is a renting agency with the specified email address.
                InitValidateProfileFormFields.init_references_init_listeners_for_renting_agency_fields(renting_agency,
                        getActivity(),
                        dataBaseHelper,
                        profile_renting_agency_change_password_layout,
                        profile_renting_agency_email_address_button,
                        profile_renting_agency_name_button,
                        profile_renting_agency_phone_number_button,
                        profile_renting_agency_country_button,
                        profile_renting_agency_city_button,
                        profile_renting_agency_change_password_button,
                        profile_renting_agency_email_address_cancel_button,
                        profile_renting_agency_name_cancel_button,
                        profile_renting_agency_phone_number_cancel_button,
                        profile_renting_agency_country_cancel_button,
                        profile_renting_agency_city_cancel_button,
                        profile_renting_agency_change_password_cancel_button,
                        profile_renting_agency_email_address_editText,
                        profile_renting_agency_name_editText,
                        profile_renting_agency_phone_number_editText,
                        profile_renting_agency_password_editText,
                        profile_renting_agency_new_password_editText,
                        profile_renting_agency_confirm_new_password_editText,
                        profile_renting_agency_country_spinner,
                        profile_renting_agency_city_spinner,
                        renting_agency_area_code_textview,
                        renting_agency_area_code_imageview,
                        area_code_flag_ids);

            } else{
                // there is no tenant.
                // this will never happen.
                Toast.makeText(getActivity(), "No Renting Agency", Toast.LENGTH_SHORT).show();
                // i may return to the login activity
            }
        }

        return root_view;
    }

    public void init_area_code_flag_ids() {
        Resources res = getResources();
        for(int i=0; i<9; i++){
            int id = res.getIdentifier("flag"+(i+1), "drawable", getActivity().getPackageName());
            area_code_flag_ids[i] = id;
        }
    }


    public void get_references(){
        profile_renting_agency_email_address_editText = (EditText) root_view.findViewById(R.id.profile_renting_agency_email_address_editText);
        profile_renting_agency_name_editText = (EditText) root_view.findViewById(R.id.profile_renting_agency_name_editText);
        profile_renting_agency_phone_number_editText = (EditText) root_view.findViewById(R.id.profile_renting_agency_phone_number_editText);
        profile_renting_agency_password_editText = (EditText) root_view.findViewById(R.id.profile_renting_agency_password_editText);
        profile_renting_agency_new_password_editText = (EditText) root_view.findViewById(R.id.profile_renting_agency_new_password_editText);
        profile_renting_agency_confirm_new_password_editText = (EditText) root_view.findViewById(R.id.profile_renting_agency_confirm_new_password_editText);
        profile_renting_agency_country_spinner = (Spinner) root_view.findViewById(R.id.profile_renting_agency_country_spinner);
        profile_renting_agency_city_spinner = (Spinner) root_view.findViewById(R.id.profile_renting_agency_city_spinner);

        profile_renting_agency_country_spinner.setEnabled(false);
        profile_renting_agency_city_spinner.setEnabled(false);

        profile_renting_agency_email_address_button = (Button) root_view.findViewById(R.id.profile_renting_agency_email_address_button);
        profile_renting_agency_name_button = (Button) root_view.findViewById(R.id.profile_renting_agency_name_button);
        profile_renting_agency_phone_number_button = (Button) root_view.findViewById(R.id.profile_renting_agency_phone_number_button);
        profile_renting_agency_country_button = (Button) root_view.findViewById(R.id.profile_renting_agency_country_button);
        profile_renting_agency_city_button = (Button) root_view.findViewById(R.id.profile_renting_agency_city_button);
        profile_renting_agency_change_password_button = (Button) root_view.findViewById(R.id.profile_renting_agency_change_password_button);

        profile_renting_agency_email_address_cancel_button = (Button) root_view.findViewById(R.id.profile_renting_agency_email_address_cancel_button);
        profile_renting_agency_name_cancel_button = (Button) root_view.findViewById(R.id.profile_renting_agency_name_cancel_button);
        profile_renting_agency_phone_number_cancel_button = (Button) root_view.findViewById(R.id.profile_renting_agency_phone_number_cancel_button);
        profile_renting_agency_country_cancel_button = (Button) root_view.findViewById(R.id.profile_renting_agency_country_cancel_button);
        profile_renting_agency_city_cancel_button = (Button) root_view.findViewById(R.id.profile_renting_agency_city_cancel_button);
        profile_renting_agency_change_password_cancel_button = (Button) root_view.findViewById(R.id.profile_renting_agency_cancel_change_password_button);

        profile_renting_agency_change_password_layout = (LinearLayout) root_view.findViewById(R.id.profile_renting_agency_change_password_layout);

        profile_user_name_textview = (TextView) getActivity().findViewById(R.id.profile_user_name_textview);

        renting_agency_area_code_textview = (TextView) root_view.findViewById(R.id.profile_renting_agency_area_code_textview);
        renting_agency_area_code_imageview = root_view.findViewById(R.id.profile_renting_agency_area_code_imageview);

        profile_renting_agency_email_address_editText.setText(renting_agency.getEmail_address());
        profile_renting_agency_name_editText.setText(renting_agency.getName());
        profile_renting_agency_phone_number_editText.setText(renting_agency.getPhone_number());

        profile_renting_agency_city_spinner.setEnabled(true);
        profile_renting_agency_country_spinner.setEnabled(true);

        int country_index = Utils.get_country_index(renting_agency.getCountry());
        objArr = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, Utils.current_residence_country_options);
        profile_renting_agency_country_spinner.setAdapter(objArr);
        profile_renting_agency_country_spinner.setSelection(country_index);

        int city_index = Utils.get_city_index(renting_agency.getCity(), country_index);
        objArr = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, Utils.city_options[country_index]);
        profile_renting_agency_city_spinner.setAdapter(objArr);
        profile_renting_agency_city_spinner.setSelection(city_index);

        profile_renting_agency_city_spinner.setEnabled(false);
        profile_renting_agency_country_spinner.setEnabled(false);

        profile_user_name_textview.setText(renting_agency.getName());

        index = country_index;
        InitValidateProfileFormFields.update_area_code(renting_agency_area_code_imageview, renting_agency_area_code_textview, area_code_flag_ids, index);


        if(is_view_mode){
            profile_renting_agency_email_address_button.setVisibility(View.GONE);
            profile_renting_agency_name_button.setVisibility(View.GONE);
            profile_renting_agency_phone_number_button.setVisibility(View.GONE);
            profile_renting_agency_country_button.setVisibility(View.GONE);
            profile_renting_agency_city_button.setVisibility(View.GONE);
            profile_renting_agency_change_password_button.setVisibility(View.GONE);

        }



    }


}