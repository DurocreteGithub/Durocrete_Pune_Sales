package com.durocrete_punesales_newapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.durocrete_punesales_newapp.R;

import java.util.HashMap;

import com.durocrete_punesales_newapp.Utillity.MyPreferenceManager;
import com.durocrete_punesales_newapp.Utillity.Utility;
import com.durocrete_punesales_newapp.model.Addnewsitemodel;
import com.durocrete_punesales_newapp.model.Util;
import com.durocrete_punesales_newapp.network.CallWebservice;
import com.durocrete_punesales_newapp.network.IUrls;
import com.durocrete_punesales_newapp.network.VolleyResponseListener;

public class AddnewSite extends AppCompatActivity {


    private EditText etSiteName;
    private EditText etClientName;
    private EditText etSEName;
    private Button btnAddSite;
    MyPreferenceManager Sharedpref;
    AddnewSite addnewSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_site_1);

        Initview();

        btnAddSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valid()) {
                    if (Util.isNetworkConnected(addnewSite)) {
                        Utility.hideSoftKeyboard(addnewSite);
                        makerequest();
                    } else {
                        Util.showToast(addnewSite, "Connection not available, Please try later.");
                    }
                }
            }
        });
    }

    private void makerequest() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("siteName", etSiteName.getText().toString().trim());
        hashMap.put("clientName", etClientName.getText().toString().trim());
        hashMap.put("contact_no", etSEName.getText().toString().trim());


        CallWebservice.getWebservice(true, AddnewSite.this, Request.Method.POST, IUrls.Add_new_Site, hashMap, new VolleyResponseListener<Addnewsitemodel>() {
            @Override
            public void onResponse(Addnewsitemodel[] object, String message, String key) {

                Toast.makeText(AddnewSite.this, "Site Added Successfully...Please wait for the approval!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddnewSite.this, Siteallocationactivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(String message) {
                Log.v("tag", message.toString());
                Toast.makeText(AddnewSite.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();

            }
        }, Addnewsitemodel[].class);
    }


    private boolean valid() {
        if (etSiteName.getText().toString().trim().length() == 0) {
            etSiteName.setError("Please Enter Site Name.");
            etSiteName.requestFocus();
            return false;

        } else if (etClientName.getText().toString().trim().length() == 0) {
            etClientName.setError("Please Enter Client Name.");
            etClientName.requestFocus();
            return false;

        } else if (etSEName.getText().toString().trim().length() == 0) {
            etSEName.setError("Please Enter Contact Mobile No.");
            etSEName.requestFocus();
            return false;

        }
        else if (etSEName.getText().toString().trim().length() != 10) {
            etSEName.setError("Please Enter Valid Mobile No.");
            etSEName.requestFocus();
            return false;

        }


        return true;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main1, menu);
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
//
//        if(id==android.R.id.home)
//        {
//            Intent intent=new Intent(this,Siteallocationactivity.class);
//            startActivity(intent);
//            finish();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }



    private void Initview() {
        etSiteName = (EditText)findViewById(R.id.et_site_name);
        etClientName = (EditText)findViewById(R.id.et_client_name);
        etSEName = (EditText)findViewById(R.id.et_se_name);
        btnAddSite = (Button)findViewById(R.id.btn_add_site);
        Sharedpref= new MyPreferenceManager(AddnewSite.this);
        this.addnewSite= AddnewSite.this;
    }
//
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent=new Intent(this,Siteallocationactivity.class);
        startActivity(intent);
        finish();
    }


}
