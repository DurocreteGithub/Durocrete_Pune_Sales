package com.durocrete_punesales_newapp.activity;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.durocrete_punesales_newapp.R;
import com.durocrete_punesales_newapp.Utillity.MyPreferenceManager;
import com.durocrete_punesales_newapp.fragments.Fragmenthome;
import com.durocrete_punesales_newapp.Test.Testrequestform;
import com.durocrete_punesales_newapp.model.Sightkyc;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MyPreferenceManager sharedpref;
    List<Sightkyc> siteupdatelist;

    Sightkyc sightkycpojo;
    LocationManager locationManager;
    MainActivity mainActivity;
    List<Testrequestform> testrequestformList;



    private boolean isGPSEnable = false;
    private boolean isNetworkEnable = false;
    private boolean doubleBackToExitPressedOnce = false;
    private boolean isFirstGPSEnable = true;
    private static String TAG = MainActivity.class.getSimpleName();

    public List<Sightkyc> getSiteupdatelist() {
        return siteupdatelist;
    }

    public void setSiteupdatelist(List<Sightkyc> siteupdatelist) {
        this.siteupdatelist = siteupdatelist;
    }

    public Sightkyc getSightkycpojo() {
        return sightkycpojo;
    }

    public void setSightkycpojo(Sightkyc sightkycpojo) {
        this.sightkycpojo = sightkycpojo;
    }

    public List<Testrequestform> getTestrequestformList() {
        return testrequestformList;
    }

    public void setTestrequestformList(List<Testrequestform> testrequestformList) {
        this.testrequestformList = testrequestformList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        sharedpref = new MyPreferenceManager(MainActivity.this);
        siteupdatelist = new ArrayList<>();
        testrequestformList = new ArrayList<>();

        sightkycpojo = new Sightkyc();

        android.support.v4.app.Fragment fragment = null;
        fragment = new Fragmenthome();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
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
//            Intent intent = new Intent(this, Saleshistory.class);
//            startActivity(intent);
//            finish();
//
//        } else if (id == R.id.sign_out) {
//            sharedpref.clearSharedPreference();
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
   Intent intent=new Intent(this,Siteallocationactivity.class);
            startActivity(intent);
            finish();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 1000);

        }
    }


    public void removefragmewnt() {
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().popBackStack();

    }





}


