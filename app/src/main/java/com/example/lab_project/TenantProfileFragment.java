package com.example.lab_project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.lab_project.helpers.SharedPrefManager;
import com.example.lab_project.helpers.Utils;
import com.example.lab_project.models.Tenant;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TenantProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TenantProfileFragment extends Fragment {

    View root_view;
    Tenant tenant= new Tenant();
    DataBaseHelper dataBaseHelper;
    public ArrayAdapter<String> objArr;

    boolean is_view_mode = false;

    EditText profile_tenant_email_address_editText;
    EditText profile_tenant_first_name_editText;
    EditText profile_tenant_last_name_editText;
    EditText profile_tenant_occupation_editText;
    EditText profile_tenant_family_size_editText;
    EditText profile_tenant_phone_number_editText;
    EditText profile_tenant_password_editText;
    EditText profile_tenant_new_password_editText;
    EditText profile_tenant_confirm_new_password_editText;
    EditText profile_tenant_gross_monthly_salary_editText;
    Spinner profile_tenant_gender_spinner;
    Spinner profile_tenant_nationality_spinner;
    Spinner profile_tenant_current_residence_country_spinner;
    Spinner profile_tenant_city_spinner;

    Button profile_tenant_email_address_button;
    Button profile_tenant_first_name_button;
    Button profile_tenant_last_name_button;
    Button profile_tenant_occupation_button;
    Button profile_tenant_phone_number_button;
    Button profile_tenant_gender_button;
    Button profile_tenant_nationality_button;
    Button profile_tenant_current_residence_country_button;
    Button profile_tenant_city_button;
    Button profile_tenant_gross_monthly_salary_button;
    Button profile_tenant_family_size_button;
    Button profile_tenant_family_size_dec_button;
    Button profile_tenant_family_size_inc_button;
    Button profile_tenant_change_password_button;

    Button profile_tenant_email_address_cancel_button;
    Button profile_tenant_first_name_cancel_button;
    Button profile_tenant_last_name_cancel_button;
    Button profile_tenant_occupation_cancel_button;
    Button profile_tenant_phone_number_cancel_button;
    Button profile_tenant_gender_cancel_button;
    Button profile_tenant_nationality_cancel_button;
    Button profile_tenant_current_residence_country_cancel_button;
    Button profile_tenant_city_cancel_button;
    Button profile_tenant_gross_monthly_salary_cancel_button;
    Button profile_tenant_family_size_cancel_button;
    Button profile_tenant_change_password_cancel_button;

    TextView profile_user_name_textview;

    TextView    tenant_area_code_textview;
    ImageView   tenant_area_code_imageview;
    LinearLayout profile_tenant_change_password_layout;

    int []      area_code_flag_ids= {0,0,0,0,0,0,0,0,0};
    int index = 0;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TenantProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TenantProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TenantProfileFragment newInstance(String param1, String param2) {
        TenantProfileFragment fragment = new TenantProfileFragment();
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
        root_view = inflater.inflate(R.layout.fragment_tenant_profile, container, false);
        dataBaseHelper =new DataBaseHelper(getActivity(), "Lab_Project.db",null,1);

        Bundle bundle = getArguments();
        if (bundle != null){
            String email_address = bundle.getString("email_address");
            is_view_mode = bundle.getBoolean("view_other_profile");
            // get tenant from database.
            dataBaseHelper =new DataBaseHelper(getActivity(), "Lab_Project.db",null,1);
            Cursor temp = dataBaseHelper.get_tenant(email_address);
            int flag=0;
            while (temp.moveToNext()) { //User found
                flag=1;
                try {
                    String decrypted_password = AESEncryption_Decryption.decrypt(temp.getString(4));
                    tenant.setFirst_name(temp.getString(1));
                    tenant.setLast_name(temp.getString(2));
                    tenant.setGender(temp.getString(3));
                    tenant.setPassword(decrypted_password);
                    tenant.setNationality(temp.getString(5));
                    tenant.setSalary(temp.getDouble(6));
                    tenant.setOccupation(temp.getString(7));
                    tenant.setFamily_size(temp.getInt(8));
                    tenant.setCurrent_country(temp.getString(9));
                    tenant.setCity(temp.getString(10));
                    tenant.setPhone_number(temp.getString(11));
                    tenant.setEmail_address(email_address);
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
                // there is a tenant.
                InitValidateProfileFormFields.init_references_init_listeners_for_tenant_fields(tenant,
                        getActivity(),
                        dataBaseHelper,
                        profile_tenant_change_password_layout,
                        profile_tenant_email_address_button,
                        profile_tenant_first_name_button,
                        profile_tenant_last_name_button,
                        profile_tenant_occupation_button,
                        profile_tenant_phone_number_button,
                        profile_tenant_gender_button,
                        profile_tenant_nationality_button,
                        profile_tenant_current_residence_country_button,
                        profile_tenant_city_button,
                        profile_tenant_gross_monthly_salary_button,
                        profile_tenant_family_size_button,
                        profile_tenant_family_size_dec_button,
                        profile_tenant_family_size_inc_button,
                        profile_tenant_change_password_button,
                        profile_tenant_email_address_cancel_button,
                        profile_tenant_first_name_cancel_button,
                        profile_tenant_last_name_cancel_button,
                        profile_tenant_occupation_cancel_button,
                        profile_tenant_phone_number_cancel_button,
                        profile_tenant_gender_cancel_button,
                        profile_tenant_nationality_cancel_button,
                        profile_tenant_current_residence_country_cancel_button,
                        profile_tenant_city_cancel_button,
                        profile_tenant_gross_monthly_salary_cancel_button,
                        profile_tenant_family_size_cancel_button,
                        profile_tenant_change_password_cancel_button,
                        profile_tenant_email_address_editText,
                        profile_tenant_first_name_editText,
                        profile_tenant_last_name_editText,
                        profile_tenant_occupation_editText,
                        profile_tenant_family_size_editText,
                        profile_tenant_phone_number_editText,
                        profile_tenant_password_editText,
                        profile_tenant_new_password_editText,
                        profile_tenant_confirm_new_password_editText,
                        profile_tenant_gross_monthly_salary_editText,
                        profile_tenant_gender_spinner,
                        profile_tenant_nationality_spinner,
                        profile_tenant_current_residence_country_spinner,
                        profile_tenant_city_spinner,
                        tenant_area_code_textview,
                        tenant_area_code_imageview,
                        area_code_flag_ids);

            } else{
                // there is no tenant.
                // this will never happen.
                Toast.makeText(getActivity(), "No User", Toast.LENGTH_SHORT).show();
                // i may return to the home activity.
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
        profile_tenant_email_address_editText = (EditText) root_view.findViewById(R.id.profile_tenant_email_address_editText);
        profile_tenant_first_name_editText = (EditText) root_view.findViewById(R.id.profile_tenant_first_name_editText);
        profile_tenant_last_name_editText  = (EditText) root_view.findViewById(R.id.profile_tenant_last_name_editText);
        profile_tenant_occupation_editText = (EditText) root_view.findViewById(R.id.profile_tenant_occupation_editText);
        profile_tenant_family_size_editText = (EditText) root_view.findViewById(R.id.profile_tenant_family_size_editText);
        profile_tenant_phone_number_editText = (EditText) root_view.findViewById(R.id.profile_tenant_phone_number_editText);
        profile_tenant_password_editText = (EditText) root_view.findViewById(R.id.profile_tenant_password_editText);
        profile_tenant_new_password_editText = (EditText) root_view.findViewById(R.id.profile_tenant_new_password_editText);
        profile_tenant_confirm_new_password_editText = (EditText) root_view.findViewById(R.id.profile_tenant_confirm_new_password_editText);
        profile_tenant_gross_monthly_salary_editText = (EditText) root_view.findViewById(R.id.profile_tenant_gross_monthly_salary_editText);
        profile_tenant_gender_spinner = (Spinner) root_view.findViewById(R.id.profile_tenant_gender_spinner);
        profile_tenant_nationality_spinner = (Spinner) root_view.findViewById(R.id.profile_tenant_nationality_spinner);
        profile_tenant_current_residence_country_spinner = (Spinner) root_view.findViewById(R.id.profile_tenant_current_residence_country_spinner);
        profile_tenant_city_spinner = (Spinner) root_view.findViewById(R.id.profile_tenant_city_spinner);

        profile_tenant_gender_spinner.setEnabled(false);
        profile_tenant_nationality_spinner.setEnabled(false);
        profile_tenant_current_residence_country_spinner.setEnabled(false);
        profile_tenant_city_spinner.setEnabled(false);

        profile_tenant_email_address_button = (Button) root_view.findViewById(R.id.profile_tenant_email_address_button);
        profile_tenant_first_name_button = (Button) root_view.findViewById(R.id.profile_tenant_first_name_button);
        profile_tenant_last_name_button = (Button) root_view.findViewById(R.id.profile_tenant_last_name_button);
        profile_tenant_occupation_button = (Button) root_view.findViewById(R.id.profile_tenant_occupation_button);
        profile_tenant_phone_number_button = (Button) root_view.findViewById(R.id.profile_tenant_phone_number_button);
        profile_tenant_gender_button = (Button) root_view.findViewById(R.id.profile_tenant_gender_button);
        profile_tenant_nationality_button = (Button) root_view.findViewById(R.id.profile_tenant_nationality_button);
        profile_tenant_current_residence_country_button = (Button) root_view.findViewById(R.id.profile_tenant_current_residence_country_button);
        profile_tenant_city_button = (Button) root_view.findViewById(R.id.profile_tenant_city_button);
        profile_tenant_gross_monthly_salary_button = (Button) root_view.findViewById(R.id.profile_tenant_gross_monthly_salary_button);
        profile_tenant_family_size_button = (Button) root_view.findViewById(R.id.profile_tenant_family_size_button);
        profile_tenant_family_size_dec_button = (Button) root_view.findViewById(R.id.profile_tenant_family_size_dec_button);
        profile_tenant_family_size_inc_button = (Button) root_view.findViewById(R.id.profile_tenant_family_size_inc_button);
        profile_tenant_change_password_button = (Button) root_view.findViewById(R.id.profile_tenant_change_password_button);

        profile_tenant_email_address_cancel_button = (Button) root_view.findViewById(R.id.profile_tenant_email_address_cancel_button);
        profile_tenant_first_name_cancel_button = (Button) root_view.findViewById(R.id.profile_tenant_first_name_cancel_button);
        profile_tenant_last_name_cancel_button = (Button) root_view.findViewById(R.id.profile_tenant_last_name_cancel_button);
        profile_tenant_occupation_cancel_button = (Button) root_view.findViewById(R.id.profile_tenant_occupation_cancel_button);
        profile_tenant_phone_number_cancel_button = (Button) root_view.findViewById(R.id.profile_tenant_phone_number_cancel_button);
        profile_tenant_gender_cancel_button = (Button) root_view.findViewById(R.id.profile_tenant_gender_cancel_button);
        profile_tenant_nationality_cancel_button = (Button) root_view.findViewById(R.id.profile_tenant_nationality_cancel_button);
        profile_tenant_current_residence_country_cancel_button = (Button) root_view.findViewById(R.id.profile_tenant_current_residence_country_cancel_button);
        profile_tenant_city_cancel_button = (Button) root_view.findViewById(R.id.profile_tenant_city_cancel_button);
        profile_tenant_gross_monthly_salary_cancel_button = (Button) root_view.findViewById(R.id.profile_tenant_gross_monthly_salary_cancel_button);
        profile_tenant_family_size_cancel_button = (Button) root_view.findViewById(R.id.profile_tenant_family_size_cancel_button);
        profile_tenant_change_password_cancel_button = (Button) root_view.findViewById(R.id.profile_tenant_cancel_change_password_button);

        profile_tenant_change_password_layout = (LinearLayout) root_view.findViewById(R.id.profile_tenant_change_password_layout);


        profile_user_name_textview = (TextView) getActivity().findViewById(R.id.profile_user_name_textview);

        tenant_area_code_textview = (TextView) root_view.findViewById(R.id.profile_tenant_area_code_textview);
        tenant_area_code_imageview = root_view.findViewById(R.id.profile_tenant_area_code_imageview);


        // init
        profile_tenant_email_address_editText.setText(tenant.getEmail_address());
        profile_tenant_first_name_editText.setText(tenant.getFirst_name());
        profile_tenant_last_name_editText.setText(tenant.getLast_name());
        profile_tenant_occupation_editText.setText(tenant.getOccupation());
        profile_tenant_family_size_editText.setText(tenant.getFamily_size() + "");
        profile_tenant_phone_number_editText.setText(tenant.getPhone_number() + "");
        profile_tenant_gross_monthly_salary_editText.setText(tenant.getSalary() + "");

        int country_index = Utils.get_country_index(tenant.getCurrent_country());
        objArr = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, Utils.current_residence_country_options);
        profile_tenant_current_residence_country_spinner.setAdapter(objArr);
        profile_tenant_current_residence_country_spinner.setSelection(country_index);

        int city_index = Utils.get_city_index(tenant.getCity(), country_index);
        objArr = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, Utils.city_options[country_index]);
        profile_tenant_city_spinner.setAdapter(objArr);
        profile_tenant_city_spinner.setSelection(city_index);

        int nationality_index = Utils.get_nationality_index(tenant.getNationality());
        objArr =  new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, Utils.nationality_options);
        profile_tenant_nationality_spinner.setAdapter(objArr);
        profile_tenant_nationality_spinner.setSelection(nationality_index);

        int gender_index = Utils.get_gender_index(tenant.getGender());
        objArr = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, Utils.gender_options);
        profile_tenant_gender_spinner.setAdapter(objArr);
        profile_tenant_gender_spinner.setSelection(gender_index);


        profile_user_name_textview.setText(tenant.getFirst_name() + " " + tenant.getLast_name());

        index = country_index;
        InitValidateProfileFormFields.update_area_code(tenant_area_code_imageview, tenant_area_code_textview, area_code_flag_ids, index);


        if(is_view_mode){
            profile_tenant_email_address_button.setVisibility(View.GONE);
            profile_tenant_first_name_button.setVisibility(View.GONE);
            profile_tenant_last_name_button.setVisibility(View.GONE);
            profile_tenant_occupation_button.setVisibility(View.GONE);
            profile_tenant_phone_number_button.setVisibility(View.GONE);
            profile_tenant_gender_button.setVisibility(View.GONE);
            profile_tenant_nationality_button.setVisibility(View.GONE);
            profile_tenant_current_residence_country_button.setVisibility(View.GONE);
            profile_tenant_city_button.setVisibility(View.GONE);
            profile_tenant_gross_monthly_salary_button.setVisibility(View.GONE);
            profile_tenant_family_size_button.setVisibility(View.GONE);
            profile_tenant_family_size_dec_button.setVisibility(View.GONE);
            profile_tenant_family_size_inc_button.setVisibility(View.GONE);
            profile_tenant_change_password_button.setVisibility(View.GONE);

            profile_tenant_email_address_cancel_button.setVisibility(View.GONE);
            profile_tenant_first_name_cancel_button.setVisibility(View.GONE);
            profile_tenant_last_name_cancel_button.setVisibility(View.GONE);
            profile_tenant_occupation_cancel_button.setVisibility(View.GONE);
            profile_tenant_phone_number_cancel_button.setVisibility(View.GONE);
            profile_tenant_gender_cancel_button.setVisibility(View.GONE);
            profile_tenant_nationality_cancel_button.setVisibility(View.GONE);
            profile_tenant_current_residence_country_cancel_button.setVisibility(View.GONE);
            profile_tenant_city_cancel_button.setVisibility(View.GONE);
            profile_tenant_gross_monthly_salary_cancel_button.setVisibility(View.GONE);
            profile_tenant_family_size_cancel_button.setVisibility(View.GONE);
            profile_tenant_change_password_cancel_button.setVisibility(View.GONE);

        }


    }


}