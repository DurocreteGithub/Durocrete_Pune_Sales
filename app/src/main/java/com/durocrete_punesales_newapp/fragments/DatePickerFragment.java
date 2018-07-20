package com.durocrete_punesales_newapp.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by root on 30/4/16.
 */
public class DatePickerFragment extends DialogFragment {
    DatePickerDialog.OnDateSetListener dateSetListener;
    int editText;

    public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
        dateSetListener  = ondate;

    }
    private int year, month, day;
    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");

    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
    }


    public void show(FragmentActivity activity, String s) {
    }
}
