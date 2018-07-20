package com.durocrete_punesales_newapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.durocrete_punesales_newapp.R;
import com.durocrete_punesales_newapp.Utillity.MyPreferenceManager;
import com.durocrete_punesales_newapp.model.Login;
import com.durocrete_punesales_newapp.network.CallWebservice;
import com.durocrete_punesales_newapp.network.IUrls;
import com.durocrete_punesales_newapp.network.VolleyResponseListener;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText etuserId;
    private EditText etpassword;
    private Button btnlogin;
    private String Username;
    private String Password;
    MyPreferenceManager sharepref;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Initview();

        if(sharepref.getBooleanPreferences(MyPreferenceManager.Loggedin))
        {
            Intent intent = new Intent(LoginActivity.this, Siteallocationactivity.class);
            startActivity(intent);
            finish();
        }

        Loginprocess();


    }

    private void Loginprocess() {

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                Username = etuserId.getText().toString();
                Password = etpassword.getText().toString();

                if (etuserId.getText().toString().length() == 0) {
                    Toast.makeText(LoginActivity.this, "Please Enter User ID.",
                            Toast.LENGTH_SHORT).show();
                }
                else if (!(etuserId.getText().toString().trim().isEmpty()) && !(isValidEmail(etuserId.getText().toString().trim()))){
                    Toast.makeText(LoginActivity.this, "Please Enter Valid Email ID.",
                            Toast.LENGTH_SHORT).show();

                }

                else if (etpassword.getText().toString().length() == 0) {
                    Toast.makeText(LoginActivity.this, "Please Enter Password.",
                            Toast.LENGTH_SHORT).show();
                }
                else {

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("userId", Username);
                    hashMap.put("pass", Password);


                    CallWebservice.getWebservice(true, LoginActivity.this, Request.Method.POST, IUrls.sign_in_sales, hashMap, new VolleyResponseListener<Login>() {
                        @Override
                        public void onResponse(Login[] object, String message, String key) {

//                            Toast.makeText(LoginActivity.this, "Registration Successful.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, Siteallocationactivity.class);
                            startActivity(intent);
                            finish();
                            sharepref.setStringPreferences(MyPreferenceManager.Username, message);
                            sharepref.setStringPreferences(MyPreferenceManager.Password, Password);
                            sharepref.setBooleanPreferences(MyPreferenceManager.Loggedin,true);

                        }
                        @Override
                        public void onError(String message) {
                            Toast.makeText(LoginActivity.this, "Invalid User ID or Password", Toast.LENGTH_SHORT).show();
                            sharepref.setBooleanPreferences(MyPreferenceManager.Loggedin,false);

                        }
                    }, Login[].class);
                }



            }
        });
    }

    private void Initview() {
        etuserId = (EditText) findViewById(R.id.etxuserID);
        etpassword = (EditText) findViewById(R.id.etxuserPIN);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        sharepref=new MyPreferenceManager(this);
    }

    private boolean isValidEmail(String emailInput) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailInput);
        return matcher.matches();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(LoginActivity.this, "Double click to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1000);
    }
}
