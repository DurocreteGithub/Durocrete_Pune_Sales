package com.durocrete_punesales_newapp.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.durocrete_punesales_newapp.R;
import com.durocrete_punesales_newapp.Utillity.MyPreferenceManager;
import com.durocrete_punesales_newapp.adapter.SiteallocationAdapter;
import com.durocrete_punesales_newapp.fragments.GPSTracker;
import com.durocrete_punesales_newapp.network.CallWebservice;
import com.durocrete_punesales_newapp.network.IUrls;
import com.durocrete_punesales_newapp.network.VolleyResponseListener;

import java.util.ArrayList;
import java.util.HashMap;

import static android.os.Build.VERSION_CODES.M;

public class Siteallocationactivity extends AppCompatActivity {

    private RecyclerView recyclerSites;
    ArrayList<com.durocrete_punesales_newapp.model.Siteallocation> siteallocationArrayList;
    SiteallocationAdapter siteallocationAdapter;
    private Button AddnewSite;
    MyPreferenceManager sharedpref;
    private Button logout,Salesenginnerhistory;
    private boolean isGPSEnable = false;
    private boolean isNetworkEnable = false;
    private boolean doubleBackToExitPressedOnce = false;
    private boolean isFirstGPSEnable = true;
    Siteallocationactivity siteallocationactivity;
    private static String TAG = Siteallocationactivity.class.getSimpleName();
    LocationManager locationManager;;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private GPSTracker tracker;
    private Double latitude = 0.0;
    Double longitude = 0.0;
    public static boolean colourflag = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siteallocation);


        Initview();
//        setGPSSetting();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            checkLocationPermission();
//        }


        AddnewSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Siteallocationactivity.this, com.durocrete_punesales_newapp.activity.AddnewSite.class);
                startActivity(intent);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedpref.clearSharedPreference();
                Intent intent1 = new Intent(Siteallocationactivity.this, LoginActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        Salesenginnerhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Siteallocationactivity.this, Saleshistory.class);
                startActivity(intent);
                 finish();
            }
        });


            }


    private void checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= M) {
            if (ContextCompat.checkSelfPermission(Siteallocationactivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                //Request Location Permission
                checkALLPermission();
            }
            else
            {
                makeRouteRequest();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkALLPermission() {

        if (ContextCompat.checkSelfPermission(Siteallocationactivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Siteallocationactivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new android.support.v7.app.AlertDialog.Builder(Siteallocationactivity.this)
                        .setTitle("Permission Needed")
                        .setMessage("This app needs the Permissions for proper functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.v("AAA 99 ", "permission granted");
                                //Prompt the user once explanation has been shown
                                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();

            /*requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA},
                    REQUEST_ID_MULTIPLE_PERMISSIONS);*/

            } else {
                // No explanation needed, we can request the permission.
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }



/*
        if(!SharedPreference.getInstanceProfileData(mActivity).getCheckIn().equals("0") && !isFirstGPSEnable){
            makeSideRequest(SharedPreference.getInstanceProfileData(mActivity).getRouteId());
        }*/


    private void makeRouteRequest() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", sharedpref.getStringPreferences(MyPreferenceManager.Username));


        CallWebservice.getWebservice(true, Siteallocationactivity.this, Request.Method.POST, IUrls.Site_allocation, hashMap, new VolleyResponseListener<com.durocrete_punesales_newapp.model.Siteallocation>() {
            @Override
            public void onResponse(com.durocrete_punesales_newapp.model.Siteallocation[] object, String message, String key) {

                if (object[0] instanceof com.durocrete_punesales_newapp.model.Siteallocation) {
                    for (com.durocrete_punesales_newapp.model.Siteallocation siteslist : object) {
                        siteallocationArrayList.add(siteslist);
                    }

                    siteallocationAdapter = new SiteallocationAdapter(Siteallocationactivity.this, siteallocationArrayList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Siteallocationactivity.this);
                    recyclerSites.setLayoutManager(mLayoutManager);
                    recyclerSites.setItemAnimator(new DefaultItemAnimator());
                    recyclerSites.setAdapter(siteallocationAdapter);
                }

            }

            @Override
            public void onError(String message) {
                Log.v("tag", message.toString());
                Toast.makeText(Siteallocationactivity.this, message, Toast.LENGTH_SHORT).show();
            }

        }, com.durocrete_punesales_newapp.model.Siteallocation[].class);


    }


    private void getLatNLong() {
        tracker = new GPSTracker(Siteallocationactivity.this);
        if (tracker.canGetLocation()) {
            Location currentLocation = tracker.getLocation();
            Log.v("Tag2", "latitude : " + currentLocation.getLatitude() + " longitude : " + currentLocation.getLongitude());
            latitude = currentLocation.getLatitude();
            longitude = currentLocation.getLongitude();
            Log.v("Tag1", "latitude : " + latitude + " longitude : " + longitude);

        }
    }

    public void setGPSSetting() {
        locationManager = (LocationManager) siteallocationactivity.getSystemService(Context.LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        if (!isGPSEnable && !isNetworkEnable) {
            Log.v(TAG, " Both are not available :" + " isGPSEnable : " + isGPSEnable + " isNetworkEnable : " + isNetworkEnable);
            new AlertDialog.Builder(siteallocationactivity)
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
//
//        Log.v(TAG, "isFirstGPSEnable = " + isFirstGPSEnable);
//        Log.v(TAG, "rlParentLayout.getVisibility() = " + rlParentLayout.getVisibility());
//        Log.v(TAG, "sitesSelectionAdapter.getSideObjectArrayList() = " + routeSelectionAdapter.getRouteObjectArrayList().size());
//        if (isGPSEnable ||(rlParentLayout.getVisibility() == View.GONE) ||  (routeSelectionAdapter.getRouteObjectArrayList().size() == 0)) {
        if (isGPSEnable && isFirstGPSEnable) {
            isFirstGPSEnable = false;
            makeRouteRequest();
        }
    }


            @Override
            public void onActivityResult ( int requestCode, int resultCode, Intent data){
                super.onActivityResult(requestCode, resultCode, data);
                if (requestCode == 0) {
                    makeRouteRequest();
                }
            }





    private void Initview() {
                recyclerSites=(RecyclerView)findViewById(R.id.rv_sites);
                siteallocationArrayList=new ArrayList<>();
                AddnewSite=(Button)findViewById(R.id.AddnewSite);
                sharedpref=new MyPreferenceManager(Siteallocationactivity.this);
                Salesenginnerhistory=(Button)findViewById(R.id.saleshistory);
                logout=(Button)findViewById(R.id.logoutbutton);
        this.siteallocationactivity = Siteallocationactivity.this;

            }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.Sale_history) {
//            Intent intent=new Intent(this,Saleshistory.class);
//            startActivity(intent);
//            finish();
//
//        }else if(id==R.id.sign_out)
//        {
//            sharedpref.clearSharedPreference();
//            Intent intent=new Intent(this,LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(Siteallocationactivity.this, "Double click to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1000);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= M) {
            checkLocationPermission();
        } else {
            makeRouteRequest();
        }
    }

}






