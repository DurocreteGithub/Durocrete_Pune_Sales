package com.durocrete_punesales_newapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.durocrete_punesales_newapp.model.Checkin;
import com.durocrete_punesales_newapp.model.Login;
import com.durocrete_punesales_newapp.model.SiteDetailModel;
import com.durocrete_punesales_newapp.network.CallWebservice;
import com.durocrete_punesales_newapp.network.IUrls;
import com.durocrete_punesales_newapp.network.VolleyResponseListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 1/7/17.
 */

public class Fragmentcheckin extends Fragment {

    Button btnCheckIn;
    private GPSTracker tracker;
    private Double latitude = 0.0;
    Double longitude = 0.0;
    private SiteDetailModel siteDetailModel = null;
    MyPreferenceManager Sharedpref;
    MainActivity mainActivity;
    TextView Clientname;
    TextView Sitename;
    TextView Siteaddress;
    TextView Contactpersonname;
    TextView Contactmobileno;
    TextView Sitelatitude;
    TextView Sitelongitude;
    ArrayList<Checkin> checkinlist;
    public static boolean colourflag = false;
    private boolean isGPSEnable = false;
    private boolean isNetworkEnable = false;
    private boolean doubleBackToExitPressedOnce = false;
    private boolean isFirstGPSEnable = true;
    Siteallocationactivity siteallocationactivity;
    private static String TAG = Siteallocationactivity.class.getSimpleName();
    LocationManager locationManager;;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentcheckin, container, false);

        Initview(view);
        setGPSSetting();

        checkindata();

        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                senddata(latitude, longitude);
            }
        });


//    private void getLatNLong() {
//        tracker = new GPSTracker(getActivity());
//        if (tracker.canGetLocation()) {
//            Location currentLocation = tracker.getLocation();
//            Log.v(TAG, "latitude : " + currentLocation.getLatitude() + " longitude : " + currentLocation.getLongitude());
//
//            if (currentLocation != null && siteDetailModel.getSiteLongitude() == 0 && siteDetailModel.getSiteLongitude() == 0) {
//                latitude = currentLocation.getLatitude();
//                longitude = currentLocation.getLongitude();
//                Log.v(TAG, "latitude : " + latitude + " longitude : " + longitude);
//            }
//        }
//    }
        return view;
    }

    public void setGPSSetting()
    {
        locationManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        if (!isGPSEnable && !isNetworkEnable) {
            Log.v(TAG, " Both are not available :" + " isGPSEnable : " + isGPSEnable + " isNetworkEnable : " + isNetworkEnable);
            new AlertDialog.Builder(mainActivity)
                    .setTitle("GPS Connection Not Available")
                    .setMessage(
                            "Please Go to Setting And enable Gps location on High accuracy mode.")
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                    startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
                                }
                            })
                    .setIcon(R.drawable.warning).show();
        }

//        Log.v(TAG, "isFirstGPSEnable = " + isFirstGPSEnable);
//        Log.v(TAG, "rlParentLayout.getVisibility() = " + rlParentLayout.getVisibility());
//        Log.v(TAG, "sitesSelectionAdapter.getSideObjectArrayList() = " + routeSelectionAdapter.getRouteObjectArrayList().size());
//        if (isGPSEnable ||(rlParentLayout.getVisibility() == View.GONE) ||  (routeSelectionAdapter.getRouteObjectArrayList().size() == 0)) {
        if (isGPSEnable && isFirstGPSEnable) {
            isFirstGPSEnable = false;
            getLatNLong();
        }
/*
        if(!SharedPreference.getInstanceProfileData(mActivity).getCheckIn().equals("0") && !isFirstGPSEnable){
            makeSideRequest(SharedPreference.getInstanceProfileData(mActivity).getRouteId());
        }*/
    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            getLatNLong();
        }
    }

    private void checkindata() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("siteId", Sharedpref.getStringPreferences(MyPreferenceManager.Siteid));


        checkinlist = new ArrayList<>();

        CallWebservice.getWebservice(true, mainActivity, Request.Method.POST, IUrls.check_in_Details, hashMap, new VolleyResponseListener<Checkin>() {
            @Override
            public void onResponse(Checkin[] object, String message, String key) {

                if (object[0] instanceof Checkin) {
                    for (Checkin siteslist : object) {
                        checkinlist.add(siteslist);
                    }
                }

                Clientname.setText(checkinlist.get(0).getClientname());
                Sitename.setText(checkinlist.get(0).getSitename());
                Siteaddress.setText(checkinlist.get(0).getSiteaddress());
                Contactpersonname.setText(checkinlist.get(0).getContactpersonname());
                Contactmobileno.setText(checkinlist.get(0).getContactno());
                Sitelatitude.setText("" + checkinlist.get(0).getLatitude());
                Sitelongitude.setText("" + checkinlist.get(0).getLongitude());

            }

            @Override
            public void onError(String message) {
                Log.v("tag", message.toString());

                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

            }
        }, Checkin[].class);


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

        if (!Sharedpref.getBooleanPreferences(MyPreferenceManager.check_out_bit)) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("siteId", Sharedpref.getStringPreferences(MyPreferenceManager.Siteid));
            hashMap.put("userId", Sharedpref.getStringPreferences(MyPreferenceManager.Username));
            hashMap.put("latitude", String.valueOf(latitude));
            hashMap.put("longitude", String.valueOf(longitude));

            CallWebservice.getWebservice(true, mainActivity, Request.Method.POST, IUrls.Check_In, hashMap, new VolleyResponseListener<Login>() {
                @Override
                public void onResponse(Login[] object, String message, String key) {

                    Toast.makeText(mainActivity, "Check In Successful.", Toast.LENGTH_SHORT).show();
                    Sharedpref.setBooleanPreferences(MyPreferenceManager.check_out_bit, true);
                    Sharedpref.setBooleanPreferences(MyPreferenceManager.done_bit, true);
                    colourflag = true;
                    Sharedpref.setStringPreferences(MyPreferenceManager.check_In_Id, message);

//                    FragmentTransaction ft = getFragmentManager().beginTransaction();
//                    ft.replace(R.id.content_frame, new Fragmenthome()).commit();
                    getActivity().getSupportFragmentManager().popBackStack();

                }

                @Override
                public void onError(String message) {
                    Log.v("tag", message.toString());
                    Toast.makeText(mainActivity, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }, Login[].class);
        } else {
            Toast.makeText(getActivity(), "Please Check out First", Toast.LENGTH_SHORT).show();
        }

    }

    private void Initview(View view) {

        btnCheckIn = (Button) view.findViewById(R.id.check_in);
        Sharedpref = new MyPreferenceManager(getActivity());
        mainActivity = (MainActivity) getActivity();
        Clientname = (TextView) view.findViewById(R.id.tvclientname);
        Sitename = (TextView) view.findViewById(R.id.etxtanno);
        Siteaddress = (TextView) view.findViewById(R.id.Accountname);
        Contactpersonname = (TextView) view.findViewById(R.id.Accountcontact);
        Contactmobileno = (TextView) view.findViewById(R.id.Contactmobileno);
        Sitelatitude = (TextView) view.findViewById(R.id.sitelatitude);
        Sitelongitude = (TextView) view.findViewById(R.id.sitelongitude);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Check In");
    }


    public MainActivity getMyActivity() {
        return (MainActivity) getActivity();
    }
}
