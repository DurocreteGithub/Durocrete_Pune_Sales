package com.durocrete_punesales_newapp.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.durocrete_punesales_newapp.R;
import com.durocrete_punesales_newapp.Utillity.CustomDatePickerListener;
import com.durocrete_punesales_newapp.Utillity.MyPreferenceManager;
import com.durocrete_punesales_newapp.Utillity.Utility;
import com.durocrete_punesales_newapp.activity.MainActivity;
import com.durocrete_punesales_newapp.model.Login;
import com.durocrete_punesales_newapp.model.Util;
import com.durocrete_punesales_newapp.network.CallWebservice;
import com.durocrete_punesales_newapp.network.IUrls;
import com.durocrete_punesales_newapp.network.VolleyResponseListener;

import java.util.Calendar;
import java.util.HashMap;

import static com.durocrete_punesales_newapp.fragments.Fragmentcheckin.colourflag;

/**
 * Created by root on 1/7/17.
 */

public class Fragmentcheckout extends Fragment {

    EditText contactname;
    EditText contactmobile;
    EditText contactDesignation;
    EditText leadDetails;
    Spinner Response;
    String Status;
    EditText Next_visit_Date;
    private int datePickerInput;
    int year;
    int month;
    int day;
    String regex = "[0-9]+";


    Button btnCheckout;
    MyPreferenceManager Sharedpref;
    MainActivity mainActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnetcheckout, container, false);

        Initview(view);

        Next_visit_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDatePicker(R.id.etxnextvisit);

                Utility.selectDatePicker1(getActivity(), 0, new CustomDatePickerListener() {
                    @Override
                    public void onDateSet(Calendar calendar) {
                        Next_visit_Date.setText(Utility.formatDateForDisplay(getActivity(), calendar.getTime()));
                    }
                });
            }
        });

        Response.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0)
                {
                    Status=parent.getItemAtPosition(position).toString().trim();
                }
                else
                {
                    Status="select";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utility.hideSoftKeyboard(getActivity());

                if (Util.isNetworkConnected(getActivity())) {
                    if (Sharedpref.getBooleanPreferences(MyPreferenceManager.check_out_bit)) {
                        if (contactname.getText().toString().trim().length() == 0) {
                            contactname.setError("Please Enter Person Visited name");
                            contactname.requestFocus();
                        } else if (contactmobile.getText().toString().trim().length() == 0) {
                            contactmobile.setError("Please Enter Contact Mobile No. ");
                            contactmobile.requestFocus();

                        } else if (contactmobile.getText().toString().trim().length() < 5 ) {
                            contactmobile.setError("Invalid Contact No.");
                            contactmobile.requestFocus();

                        } else if (contactDesignation.getText().toString().trim().length() == 0) {
                            contactDesignation.setError("Please Enter Contact Designation ");
                            contactDesignation.requestFocus();

                        } else if (contactDesignation.getText().toString().trim().matches(regex)) {
                            contactDesignation.setError("Designation name should contain characters.");
                            contactDesignation.requestFocus();
                        } else if (leadDetails.getText().toString().trim().matches(regex)) {
                            leadDetails.setError("Visit Details should contain characters.");
                            leadDetails.requestFocus();
                        } else if (leadDetails.getText().toString().trim().length() == 0) {
                            leadDetails.setError("Please Enter Visit Details ");
                            leadDetails.requestFocus();
                        } else if (Status.equalsIgnoreCase("select")) {
                            Toast.makeText(mainActivity, "Please Select Status", Toast.LENGTH_SHORT).show();
                        } else {

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("siteId", Sharedpref.getStringPreferences(MyPreferenceManager.Siteid));
                            hashMap.put("userId", Sharedpref.getStringPreferences(MyPreferenceManager.Username));
                            hashMap.put("contact_person", contactname.getText().toString().trim());
                            hashMap.put("contact_no", contactmobile.getText().toString().trim());
                            hashMap.put("designation", contactDesignation.getText().toString().trim());
                            hashMap.put("Lead_discription", leadDetails.getText().toString().trim());
                            hashMap.put("Response", Status);
                            hashMap.put("Next_date", Next_visit_Date.getText().toString().trim());
                            hashMap.put("Id", Sharedpref.getStringPreferences(MyPreferenceManager.check_In_Id));


                            CallWebservice.getWebservice(true, mainActivity, Request.Method.POST, IUrls.Check_Out, hashMap, new VolleyResponseListener<Login>() {
                                @Override
                                public void onResponse(Login[] object, String message, String key) {

                                    Toast.makeText(mainActivity, "Check Out Successful.", Toast.LENGTH_SHORT).show();
                                    Sharedpref.setBooleanPreferences(MyPreferenceManager.check_out_bit, false);
                                    Sharedpref.setBooleanPreferences(MyPreferenceManager.done_bit, false);
                                    colourflag = false;
                                    contactname.setText("");
                                    contactmobile.setText("");
                                    contactDesignation.setText("");
                                    leadDetails.setText("");
                                    Response.setSelection(0);
                                    Next_visit_Date.setText("");
//                                    android.support.v4.app.Fragment
//                                            fragment = null;
//                                    fragment = new Fragmenthome();
//                                    if (fragment != null) {
//                                        FragmentTransaction ft = getFragmentManager().beginTransaction();
//                                        ft.replace(R.id.content_frame, fragment);
//                                        ft.commit();
//                                    }

                                    getActivity().getSupportFragmentManager().popBackStack();
                                }

                                @Override
                                public void onError(String message) {
                                    Log.v("tag", message.toString());
                                    Toast.makeText(mainActivity, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                                    Sharedpref.setBooleanPreferences(MyPreferenceManager.check_out_bit, true);

                                }
                            }, Login[].class);

                        }
                    } else {
                        Toast.makeText(mainActivity, "Please Check in First", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Util.showToast(getActivity(), "Connection not available, Please try later.");
                }
            }
        });

        return view;
    }



    private void Initview(View view) {


        contactname = (EditText) view.findViewById(R.id.etxcontactpersonname);
        contactmobile = (EditText) view.findViewById(R.id.etxcontactmobileno);
        contactDesignation = (EditText) view.findViewById(R.id.etxDesignation);
        leadDetails = (EditText) view.findViewById(R.id.etxleaddetails);
        Response = (Spinner) view.findViewById(R.id.spresponse);
        Next_visit_Date = (EditText) view.findViewById(R.id.etxnextvisit);
        btnCheckout = (Button) view.findViewById(R.id.btncheckout);
        Sharedpref = new MyPreferenceManager(getActivity());
        mainActivity = (MainActivity) getActivity();


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Check Out");
    }


    private void showDatePicker(int pickerId) {
        DatePickerFragment date = new DatePickerFragment();
        Log.e("inside the datepicker", "");
        datePickerInput = pickerId;


        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);

        date.setCallBack(onDate);
        date.show(getActivity().getFragmentManager(), "Date Picker");

    }

    DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            switch (datePickerInput) {
                case R.id.etxnextvisit:
                    String Fmonth, Fday;
                    if (monthOfYear < 10) {
                        Fmonth = "0" + (monthOfYear + 1);
                    } else {
                        Fmonth = String.valueOf((monthOfYear + 1));
                    }

                    if (dayOfMonth < 10) {
                        Fday = "0" + dayOfMonth;
                    } else {
                        Fday = String.valueOf(dayOfMonth);
                    }

                    Next_visit_Date.setText(String.valueOf(year) + "-" + Fmonth + "-" + Fday);
                    break;
            }

        }
    };


    public MainActivity getMyActivity() {
        return (MainActivity) getActivity();
    }
}
