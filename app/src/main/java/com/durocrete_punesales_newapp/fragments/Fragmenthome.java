package com.durocrete_punesales_newapp.fragments;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.durocrete_punesales_newapp.R;
import com.durocrete_punesales_newapp.Utillity.MyPreferenceManager;
import com.durocrete_punesales_newapp.activity.MainActivity;
import com.durocrete_punesales_newapp.activity.Siteallocationactivity;
import com.durocrete_punesales_newapp.bills.Fragmentbills;
import com.durocrete_punesales_newapp.model.Login;
import com.durocrete_punesales_newapp.network.CallWebservice;
import com.durocrete_punesales_newapp.network.IUrls;
import com.durocrete_punesales_newapp.network.VolleyResponseListener;
import com.durocrete_punesales_newapp.reports.Fragmentreports;

import java.util.HashMap;


/**
 * Created by root on 19/5/17.
 */

public class Fragmenthome extends Fragment implements View.OnClickListener {

    private TextView sitename;
    private Button Client_kyc;
    private Button Site_kyc;
    private Button enquiry;
    private Button placeorder;
    private Button checkin;
    private Button checkout;
    private Button sitevisitsummary;
    private Button Salesenginnerhistory;
    //    private Button logout;
    private Button gpssetting;
    private Button Reports;
    private Button Bills;


    MainActivity mainActivity;
    private GPSTracker tracker;
    private Double latitude = 0.0;
    Double longitude = 0.0;

    MyPreferenceManager sharedPref;
    private String message;
    private boolean isGPSEnable = false;
    private boolean isNetworkEnable = false;
    private boolean doubleBackToExitPressedOnce = false;
    private boolean isFirstGPSEnable = true;
    Siteallocationactivity siteallocationactivity;
    private static String TAG = Siteallocationactivity.class.getSimpleName();
    LocationManager locationManager;
    ;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmenthome, container, false);


        Initview(view);


        getLatNLong();
        sharedPref = new MyPreferenceManager(getActivity());

        sitename.setText(sharedPref.getStringPreferences(MyPreferenceManager.Site_name));

//        setGPSSetting();

//
//
        if (!sharedPref.getBooleanPreferences(MyPreferenceManager.check_out_bit)) {
            checkin.setEnabled(true);
            checkout.setEnabled(false);
            Client_kyc.setEnabled(false);
            Site_kyc.setEnabled(false);
            enquiry.setEnabled(false);
            placeorder.setEnabled(false);
            checkout.setEnabled(false);
            sitevisitsummary.setEnabled(false);
            gpssetting.setEnabled(false);
            Reports.setEnabled(false);
            Bills.setEnabled(false);

        } else {
            checkin.setEnabled(false);
            checkout.setEnabled(true);
            Client_kyc.setEnabled(true);
            Site_kyc.setEnabled(true);
            enquiry.setEnabled(true);
            placeorder.setEnabled(true);
            checkout.setEnabled(true);
            sitevisitsummary.setEnabled(true);
            gpssetting.setEnabled(true);
            Reports.setEnabled(true);
            Bills.setEnabled(true);
        }


        return view;
    }


    private void getLatNLong() {
        tracker = new GPSTracker(getActivity());
        if (tracker.canGetLocation()) {
            Location currentLocation = tracker.getLocation();
            Log.v("Tag2", "latitude : " + currentLocation.getLatitude() + " longitude : " + currentLocation.getLongitude());
            latitude = currentLocation.getLatitude();
            longitude = currentLocation.getLongitude();
            Log.v("Tag1", "latitude : " + latitude + " longitude : " + longitude);
        }

    }


    private void senddata(Double latitude, Double longitude) {


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("siteId", sharedPref.getStringPreferences(MyPreferenceManager.Siteid));
        hashMap.put("userId", sharedPref.getStringPreferences(MyPreferenceManager.Username));
        hashMap.put("latitude", String.valueOf(latitude));
        hashMap.put("longitude", String.valueOf(longitude));

        CallWebservice.getWebservice(true, mainActivity, Request.Method.POST, IUrls.locate_location, hashMap, new VolleyResponseListener<Login>() {
            @Override
            public void onResponse(Login[] object, String message, String key) {

                Toast.makeText(mainActivity, message, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(String message) {
                Log.v("tag", message.toString());
                Toast.makeText(mainActivity, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }, Login[].class);

    }


    private void Initview(View view) {
        sitename = (TextView) view.findViewById(R.id.Sitename);
        Client_kyc = (Button) view.findViewById(R.id.clientkyc);
        Site_kyc = (Button) view.findViewById(R.id.sitekyc);
        enquiry = (Button) view.findViewById(R.id.enquiry);
        checkin = (Button) view.findViewById(R.id.checkin);
        checkout = (Button) view.findViewById(R.id.checkout);
        sitevisitsummary = (Button) view.findViewById(R.id.sitevisitlog);
//        Salesenginnerhistory=(Button)view.findViewById(R.id.saleshistory);
//        logout=(Button)view.findViewById(R.id.logoutbutton);
        gpssetting = (Button) view.findViewById(R.id.gpssetting);
        placeorder = (Button) view.findViewById(R.id.placeorder);
        mainActivity = (MainActivity) getActivity();
        Reports = (Button) view.findViewById(R.id.report);
        Bills = (Button) view.findViewById(R.id.bills);

        Client_kyc.setOnClickListener(this);
        Site_kyc.setOnClickListener(this);
        enquiry.setOnClickListener(this);
        checkin.setOnClickListener(this);
        checkout.setOnClickListener(this);
        sitevisitsummary.setOnClickListener(this);
//        Salesenginnerhistory.setOnClickListener(this);
//        logout.setOnClickListener(this);
        gpssetting.setOnClickListener(this);
        placeorder.setOnClickListener(this);
        Reports.setOnClickListener(this);
        Bills.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        Fragment fragment = null;

        switch (v.getId()) {
            case R.id.clientkyc:
                fragment = new Fragmentupdateclientkyc();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment).addToBackStack(null);
                ft.commit();
                break;

            case R.id.sitekyc:
                fragment = new Fragmentsitekyc11();
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment).addToBackStack(null);
                ft.commit();
                break;

            case R.id.enquiry:
                fragment = new Fragmentenquiry();
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment).addToBackStack(null);
                ft.commit();
                break;


            case R.id.placeorder:
                fragment = new Fragmentplaceorder();
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment).addToBackStack(null);
                ft.commit();
                break;


            case R.id.gpssetting:
                senddata(latitude, longitude);

                break;


            case R.id.checkin:
                fragment = new Fragmentcheckin();
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment).addToBackStack(null);
                ft.commit();
                break;


            case R.id.checkout:
                fragment = new Fragmentcheckout();
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment).addToBackStack(null).
                        commit();
                break;


            case R.id.sitevisitlog:
                fragment = new Sitevisitdetail();
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment).addToBackStack(null).
                        commit();
                break;


            case R.id.report:
                fragment = new Fragmentreports();
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment).addToBackStack(null).
                        commit();
                break;

            case R.id.bills:
                fragment = new Fragmentbills();
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment).addToBackStack(null).
                        commit();
                break;


//                case R.id.saleshistory:
//                    Intent intent = new Intent(getActivity(), Saleshistory.class);
//                    startActivity(intent);
//                     getActivity().finish();
//                    break;
//
//                case R.id.logoutbutton:
//                    sharedPref.clearSharedPreference();
//                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
//                    startActivity(intent1);
//                    getActivity().finish();
//                    break;

        }

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Home");
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
