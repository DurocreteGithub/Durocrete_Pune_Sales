package com.durocrete_punesales_newapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.durocrete_punesales_newapp.R;
import com.durocrete_punesales_newapp.Utillity.MyPreferenceManager;
import com.durocrete_punesales_newapp.Utillity.Utility;
import com.durocrete_punesales_newapp.activity.MainActivity;
import com.durocrete_punesales_newapp.model.Clientinfo;
import com.durocrete_punesales_newapp.model.Updateclient;
import com.durocrete_punesales_newapp.model.Util;
import com.durocrete_punesales_newapp.network.CallWebservice;
import com.durocrete_punesales_newapp.network.IUrls;
import com.durocrete_punesales_newapp.network.VolleyResponseListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by root on 19/5/17.
 */

public class Fragmentupdateclientkyc extends Fragment {


    private TextView etxClientname, sitename;
    private EditText etxOfficeAddress;
    private EditText etxOwnerName;
    private EditText etxEmailId;
    private EditText etxMobileNo;
    private EditText etxPanNO;
    private EditText etxTaNo;
    private Spinner GST;
    private EditText state;
    private EditText city;
    private EditText etxAccountperson;
    private EditText etxAccountmobile;
    private MainActivity mainActivity;
    private Button btnUpdateClientKyc;
    private List<Clientinfo> clientinfolist;
    MyPreferenceManager sharedPref;
    String gstselection;
    int gstselectionno;
    LinearLayout lineargstno;
    LinearLayout linearstate;
    LinearLayout linearcity;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnetupdateclientkyc1, container, false);

        Initview(view);

        GST.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    lineargstno.setVisibility(View.GONE);
                    linearstate.setVisibility(View.GONE);
                    linearcity.setVisibility(View.GONE);
                }

                if (position != 0) {
                    switch (position) {
                        case 1:
                            lineargstno.setVisibility(View.VISIBLE);
                            linearstate.setVisibility(View.VISIBLE);
                            linearcity.setVisibility(View.VISIBLE);
                            gstselection = parent.getItemAtPosition(position).toString();
                            gstselectionno = position;
                            break;

                        case 2:
                            lineargstno.setVisibility(View.GONE);
                            linearstate.setVisibility(View.GONE);
                            linearcity.setVisibility(View.GONE);
                            gstselection = parent.getItemAtPosition(position).toString();
                            gstselectionno = position;
                            break;


                    }
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (Util.isNetworkConnected(getActivity())) {
            getalldata();
        } else {
            Util.showToast(getActivity(), "Connection not available, Please try later.");
        }

        btnUpdateClientKyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valid()) {
                    if (Util.isNetworkConnected(getActivity())) {
                        Utility.hideSoftKeyboard(getActivity());
                        makerequest();

                    } else {
                        Util.showToast(getActivity(), "Connection not available, Please try later.");
                    }
                }
            }
        });

        return view;

    }

    private void getalldata() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("siteId", sharedPref.getStringPreferences(MyPreferenceManager.Siteid));


        clientinfolist = new ArrayList<>();

        CallWebservice.getWebservice(true, mainActivity, Request.Method.POST, IUrls.get_client_by_siteId, hashMap, new VolleyResponseListener<Clientinfo>() {
            @Override
            public void onResponse(Clientinfo[] object, String message, String key) {

                if (object[0] instanceof Clientinfo) {
                    for (Clientinfo siteslist : object) {
                        clientinfolist.add(siteslist);
                    }
                }
                etxClientname.setText(clientinfolist.get(0).getClientName());
                etxOfficeAddress.setText(clientinfolist.get(0).getOfficeAddress());
                etxOwnerName.setText(clientinfolist.get(0).getDirectorName());
                etxMobileNo.setText(clientinfolist.get(0).getMobileNo());
                etxEmailId.setText(clientinfolist.get(0).getEmailId());
                etxPanNO.setText(clientinfolist.get(0).getPAN());
                etxTaNo.setText(clientinfolist.get(0).getTAN());
                etxAccountperson.setText(clientinfolist.get(0).getAc_person());
                etxAccountmobile.setText(clientinfolist.get(0).getAc_contact());
                sharedPref.setStringPreferences(MyPreferenceManager.ClientID, clientinfolist.get(0).getClientId());
                sitename.setText(sharedPref.getStringPreferences(MyPreferenceManager.Site_name));
                city.setText(clientinfolist.get(0).getCity());
                state.setText(clientinfolist.get(0).getState());

                ArrayAdapter<String> abc = (ArrayAdapter<String>) GST.getAdapter();
                GST.setSelection(abc.getPosition(clientinfolist.get(0).getGstselection()));

            }

            @Override
            public void onError(String message) {
                Log.v("tag", message.toString());
                Toast.makeText(getActivity(), "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new Fragmenthome()).addToBackStack(null);
                ft.commit();

            }
        }, Clientinfo[].class);

    }

    private void makerequest() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("clId", sharedPref.getStringPreferences(MyPreferenceManager.ClientID));
        hashMap.put("officeAddress", etxOfficeAddress.getText().toString());
        hashMap.put("ownerName", etxOwnerName.getText().toString());
        hashMap.put("emailId", etxEmailId.getText().toString());
        hashMap.put("ac_person", etxAccountperson.getText().toString());
        hashMap.put("mobile_no", etxAccountmobile.getText().toString());
        hashMap.put("mobile", etxMobileNo.getText().toString());
        hashMap.put("PanNo", etxPanNO.getText().toString());
        hashMap.put("TanNo", etxTaNo.getText().toString());
        hashMap.put("gstselection", gstselection);
        hashMap.put("state", state.getText().toString());
        hashMap.put("city", city.getText().toString());


        CallWebservice.getWebservice(true, mainActivity, Request.Method.POST, IUrls.Update_client, hashMap, new VolleyResponseListener<Updateclient>() {
            @Override
            public void onResponse(Updateclient[] object, String message, String key) {

                Toast.makeText(getActivity(), "Client Data Updated Successfully", Toast.LENGTH_SHORT).show();
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new Fragmenthome());
                ft.commit();

            }

            @Override
            public void onError(String message) {
                Log.v("tag", message.toString());

            }
        }, Updateclient[].class);

    }

    private boolean valid() {

////        if (etxOfficeAddress.getText().toString().trim().length() == 0) {
////            etxOfficeAddress.setError("Please Enter Site Address.");
////            etxOfficeAddress.requestFocus();
////            return false;
////
////        } else
//
        if (gstselectionno == 0) {
            Toast.makeText(mainActivity, "Please Select GST.", Toast.LENGTH_SHORT).show();
            return false;

        } else if (gstselectionno == 1 && etxTaNo.getText().toString().trim().length() == 0) {
            etxTaNo.setError("Please Enter GST NO.");
            etxTaNo.requestFocus();
            return false;

        } else if (gstselectionno == 1 && state.getText().toString().trim().length() == 0) {
            state.setError("Please Enter State Name.");
            state.requestFocus();
            return false;

        } else if (gstselectionno == 1 && city.getText().toString().trim().length() == 0) {
            city.setError("Please Enter City Name.");
            city.requestFocus();
            return false;
        }

//         else if (etxPanNO.getText().toString().trim().length() == 0) {
//            etxPanNO.setError("Please Enter PAN NO.");
//            etxPanNO.requestFocus();
//            return false;
//
//        } else if (etxPanNO.getText().toString().trim().length() < 10) {
//            etxPanNO.setError("Invalid PAN No.");
//            etxPanNO.requestFocus();
//            return false;
//        } else if (etxOwnerName.getText().toString().trim().length() == 0) {
//            etxPanNO.setError("Please Enter Owner Name.");
//            etxPanNO.requestFocus();
//            return false;
//
//        } else if (etxMobileNo.getText().toString().trim().length() == 0) {
//            etxMobileNo.setError("Please Enter Mobile No.");
//            etxMobileNo.requestFocus();
//            return false;
//        }

        else if (etxMobileNo.getText().toString().trim().length() != 0 && etxMobileNo.getText().toString().trim().length() < 10) {
            etxMobileNo.setError("Invalid Mobile No.");
            etxMobileNo.requestFocus();
            return false;
        }
//        else if (etxEmailId.getText().toString().trim().length() == 0) {
//            etxEmailId.setError("Please Enter Email Id.");
//            etxEmailId.requestFocus();
//            return false;
//        }
        else if (!(etxEmailId.getText().toString().trim().isEmpty()) && !(loop(etxEmailId.getText().toString().trim()))) {
            etxEmailId.setError("Invalid Email Id.");
            etxEmailId.requestFocus();
            return false;
        }
//        } else if (etxAccountperson.getText().toString().trim().length() == 0) {
//            etxPanNO.setError("Please Enter Accountant Person Name.");
//            etxPanNO.requestFocus();
//            return false;
//
//        } else if (etxAccountmobile.getText().toString().trim().length() == 0) {
//            etxAccountmobile.setError("Please Enter Accountant Mobile No.");
//            etxAccountmobile.requestFocus();
//            return false;
//        }
        else if (etxAccountmobile.getText().toString().trim().length() != 0 && etxAccountmobile.getText().toString().trim().length() < 5) {
            etxAccountmobile.setError("Invalid Accountant Mobile No.");
            etxAccountmobile.requestFocus();
            return false;
        }

        return true;
    }

    private boolean loop(String trim) {

        String[] test = trim.split(",");
        for (int i = 0; i < test.length; i++) {
            if (!isValidEmail(test[i])) {
                break;
            }
        }
        return true;
    }

    private void Initview(View view) {

        etxOfficeAddress = (EditText) view.findViewById(R.id.etxofficeaddress);
        etxOwnerName = (EditText) view.findViewById(R.id.tvdirectorname);
        etxEmailId = (EditText) view.findViewById(R.id.etxclientemailid);
        etxMobileNo = (EditText) view.findViewById(R.id.etxmobileno);
        etxPanNO = (EditText) view.findViewById(R.id.etxpanno);
        etxTaNo = (EditText) view.findViewById(R.id.etxtanno);
        etxAccountperson = (EditText) view.findViewById(R.id.Accountname);
        etxAccountmobile = (EditText) view.findViewById(R.id.Accountcontact);
        etxClientname = (TextView) view.findViewById(R.id.tvclientname);
        mainActivity = (MainActivity) getActivity();
        btnUpdateClientKyc = (Button) view.findViewById(R.id.btnclUpdateclient);
        sitename = (TextView) view.findViewById(R.id.tvsitename);
        sharedPref = new MyPreferenceManager(getActivity());
        clientinfolist = new ArrayList<>();
        GST = (Spinner) view.findViewById(R.id.spgst_array);
        state = (EditText) view.findViewById(R.id.state);
        city = (EditText) view.findViewById(R.id.city);
        lineargstno = (LinearLayout) view.findViewById(R.id.lineargstno);
        linearstate = (LinearLayout) view.findViewById(R.id.linearstate);
        linearcity = (LinearLayout) view.findViewById(R.id.linearcity);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Client KYC");
    }


    private boolean isValidEmail(String emailInput) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailInput);
        return matcher.matches();
    }

}

