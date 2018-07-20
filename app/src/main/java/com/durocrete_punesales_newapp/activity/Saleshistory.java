package com.durocrete_punesales_newapp.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.durocrete_punesales_newapp.R;
import com.durocrete_punesales_newapp.Utillity.MyPreferenceManager;
import com.durocrete_punesales_newapp.Utillity.Utility;
import com.durocrete_punesales_newapp.adapter.Summaryadapter;
import com.durocrete_punesales_newapp.model.Summary;
import com.durocrete_punesales_newapp.network.CallWebservice;
import com.durocrete_punesales_newapp.network.IUrls;
import com.durocrete_punesales_newapp.network.VolleyResponseListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Saleshistory extends AppCompatActivity {

    ArrayList<Summary> salessummarylist;
    RecyclerView summaryrecycler;
    Summaryadapter salessummaryadapter;
    MyPreferenceManager Sharedpref;
    EditText select_date;
    private int datePickerInput;
    private Button check;
    private TextView nodata1;
    int year;
    int month;
    int day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saleshistory);

        summaryrecycler = (RecyclerView) findViewById(R.id.salessummaryrecycler);
        Sharedpref = new MyPreferenceManager(Saleshistory.this);
        select_date = (EditText) findViewById(R.id.date);
        check = (Button) findViewById(R.id.datecheck);
        nodata1 = (TextView) findViewById(R.id.nodata);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c.getTime());
        select_date.setText(formattedDate);

        HashMap<String, String> hashMap1 = new HashMap<>();
        hashMap1.put("userId", Sharedpref.getStringPreferences(MyPreferenceManager.Username));
        hashMap1.put("date", select_date.getText().toString().trim());
        salessummarylist = new ArrayList<>();

        CallWebservice.getWebservice(true, Saleshistory.this, Request.Method.POST, IUrls.sales_history, hashMap1, new VolleyResponseListener<Summary>() {
            @Override
            public void onResponse(Summary[] object, String message, String key) {

                if (message.equalsIgnoreCase("1")) {
                    if (object[0] instanceof Summary) {
                        for (Summary routeObject : object) {
                            salessummarylist.add(routeObject);
                        }
                    }
                    salessummaryadapter = new Summaryadapter(Saleshistory.this, salessummarylist);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Saleshistory.this);
                    summaryrecycler.setLayoutManager(mLayoutManager);
                    summaryrecycler.setItemAnimator(new DefaultItemAnimator());
                    summaryrecycler.setAdapter(salessummaryadapter);
                } else {
                    summaryrecycler.setVisibility(View.GONE);
                    nodata1.setVisibility(View.VISIBLE);
                    nodata1.setText("No logs Available.");
//                                Toast.makeText(Saleshistory.this, "Data Not Found", Toast.LENGTH_SHORT).show();
//                                salessummarylist.clear();
//                                salessummaryadapter = new Summaryadapter(Saleshistory.this, salessummarylist);
//                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Saleshistory.this);
//                                summaryrecycler.setLayoutManager(mLayoutManager);
//                                summaryrecycler.setItemAnimator(new DefaultItemAnimator());
//                                summaryrecycler.setAdapter(salessummaryadapter);
                }

            }

            @Override
            public void onError(String message) {
                Log.v("tag", message.toString());


                Toast.makeText(Saleshistory.this, "Please check Internet Connection", Toast.LENGTH_SHORT).show();

            }
        }, Summary[].class);

        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();

            }
        });


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salessummarylist = new ArrayList<>();


                if (select_date.getText().toString().trim().length() == 0) {
                    Toast.makeText(Saleshistory.this, "Please Select Date", Toast.LENGTH_SHORT).show();
                } else {

                    HashMap<String, String> hashMap1 = new HashMap<>();
                    hashMap1.put("userId", Sharedpref.getStringPreferences(MyPreferenceManager.Username));
                    hashMap1.put("date", select_date.getText().toString().trim());

                    CallWebservice.getWebservice(true, Saleshistory.this, Request.Method.POST, IUrls.sales_history, hashMap1, new VolleyResponseListener<Summary>() {
                        @Override
                        public void onResponse(Summary[] object, String message, String key) {

                            if (message.equalsIgnoreCase("1")) {
                                if (object[0] instanceof Summary) {
                                    for (Summary routeObject : object) {
                                        salessummarylist.add(routeObject);
                                    }
                                }
                                salessummaryadapter = new Summaryadapter(Saleshistory.this, salessummarylist);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Saleshistory.this);
                                summaryrecycler.setLayoutManager(mLayoutManager);
                                summaryrecycler.setItemAnimator(new DefaultItemAnimator());
                                summaryrecycler.setAdapter(salessummaryadapter);
                            } else {
                                summaryrecycler.setVisibility(View.GONE);
                                nodata1.setVisibility(View.VISIBLE);
                                nodata1.setText("No logs Available.");
//                                Toast.makeText(Saleshistory.this, "Data Not Found", Toast.LENGTH_SHORT).show();
//                                salessummarylist.clear();
//                                salessummaryadapter = new Summaryadapter(Saleshistory.this, salessummarylist);
//                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Saleshistory.this);
//                                summaryrecycler.setLayoutManager(mLayoutManager);
//                                summaryrecycler.setItemAnimator(new DefaultItemAnimator());
//                                summaryrecycler.setAdapter(salessummaryadapter);
                            }

                        }

                        @Override
                        public void onError(String message) {
                            Log.v("tag", message.toString());


                            Toast.makeText(Saleshistory.this, "Please check Internet Connection", Toast.LENGTH_SHORT).show();

                        }
                    }, Summary[].class);
                }
            }
        });


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
//            Sharedpref.clearSharedPreference();
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        } else if (id == android.R.id.home) {
//            Intent intent = new Intent(this, Siteallocationactivity.class);
//            startActivity(intent);
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }


    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dlg = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                select_date.setText(Utility.formatDateForDisplay(cal.getTime(), "dd/MM/yyyy"));
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        dlg.show();
    }

    DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {


        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            switch (datePickerInput) {

                case R.id.date:
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


                    select_date.setText(String.valueOf(year) + "-" + Fmonth + "-" + Fday);

                    break;
            }

        }
    };


    @Override
    public void onBackPressed() {
        // code here to show dialog
        super.onBackPressed();

        Intent intent = new Intent(this, Siteallocationactivity.class);
        startActivity(intent);
        finish();
    }

}
