package com.example.lab_project.helpers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import java.text.ParseException;
import java.util.Calendar;

public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public interface DatePickerListener{
        void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) throws ParseException;

    }

    DatePickerListener mListener;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            mListener = (DatePickerListener) context;
        }catch (Exception e){
            throw new ClassCastException(getActivity().toString()+"Must implement DatePickerListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),this,year,month,day);
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
        try {
            mListener.onDateSet(datePicker,i,i1,i2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
