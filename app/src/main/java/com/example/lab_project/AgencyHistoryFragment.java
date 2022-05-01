package com.example.lab_project;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgencyHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgencyHistoryFragment extends Fragment {
    TextView city;
    TextView postalAddress;
    TextView from;
    TextView to;
    TextView tenant_name;

    private View root_view;
    private String fragment_tag;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AgencyHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgencyHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AgencyHistoryFragment newInstance(String param1, String param2) {
        AgencyHistoryFragment fragment = new AgencyHistoryFragment();
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
        root_view = inflater.inflate(R.layout.fragment_agency_history, container, false);
        return root_view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        city = (TextView) root_view.findViewById(R.id.agency_history_city_plainText);
        postalAddress = (TextView) root_view.findViewById(R.id.agency_history_postal_address_plainText);
        from = (TextView) root_view.findViewById(R.id.agency_history_from_textPlain);
        to = (TextView) root_view.findViewById(R.id.agency_history_to_plainText);
        tenant_name = (TextView) root_view.findViewById(R.id.agency_history_tenant_name_plainText);
        city.setEnabled(false);
        postalAddress.setEnabled(false);
        from.setEnabled(false);
        to.setEnabled(false);
        tenant_name.setEnabled(false);

        Bundle bundle = getArguments();
        if (bundle!=null){
            fragment_tag = bundle.getString("fragment_tag");
        }
        city.setText(bundle.getString("city"));
        postalAddress.setText(bundle.getString("postalAddress"));
        from.setText(bundle.getString("from"));
        to.setText(bundle.getString("to"));
        tenant_name.setText(bundle.getString("tenant_name"));
    }

}