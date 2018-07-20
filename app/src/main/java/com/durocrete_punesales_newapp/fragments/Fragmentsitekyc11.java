package com.durocrete_punesales_newapp.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.durocrete_punesales_newapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.durocrete_punesales_newapp.Utillity.MyPreferenceManager;
import com.durocrete_punesales_newapp.Utillity.Utility;
import com.durocrete_punesales_newapp.activity.MainActivity;
import com.durocrete_punesales_newapp.model.Sightkyc;
import com.durocrete_punesales_newapp.model.Util;
import com.durocrete_punesales_newapp.network.CallWebservice;
import com.durocrete_punesales_newapp.network.IUrls;
import com.durocrete_punesales_newapp.network.VolleyResponseListener;

/**
 * Created by root on 30/6/17.
 */

public class Fragmentsitekyc11 extends Fragment {

    Button btnNext;
    private EditText etSiteaddress;
    private EditText etBuilduparea;
    private EditText etxreerano;
    private EditText etConstrunctionperoid;
    private EditText etCompletion_Date;
    private EditText etRcc_consultant;
    private EditText etArchitecture;
    private Spinner spConstrunction_management;
    private Spinner spgeo_investigation;
    private LinearLayout rcclayout, Architect_layout, construnction_layout;
    private int datePickerInput;
    private TextView newaddedrcc, newaddedarchitect, newoutsource;
    private ArrayList<Sightkyc> sightkyclist;
    private ArrayList<String> rccConsultantList;
    private ArrayList<String> architectureList;
    Sightkyc sightkyc;
    MyPreferenceManager Sharedpref;
    private TextView clientname, Sitename;
    String Rcc_flag;
    String Architecture_flag;
    String Construnction_flag;
    String Siteaddress;
    String Builduparea;
    String Construnctionperiod;
    String completion_Date;
    String Construnction_management;
    String geo_investigation;
    MainActivity mainActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnetupdatesitekyc11, container, false);

        Initview(view);
        getMyActivity().getSiteupdatelist().clear();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("siteId", Sharedpref.getStringPreferences(MyPreferenceManager.Siteid));


        sightkyclist = new ArrayList<>();




        CallWebservice.getWebservice(true, mainActivity, Request.Method.POST, IUrls.Get_Site_Details, hashMap, new VolleyResponseListener<Sightkyc>() {
            @Override
            public void onResponse(Sightkyc[] object, String message, String key) {


                if (object[0] instanceof Sightkyc) {
                    for (Sightkyc siteslist : object) {
                        sightkyclist.add(siteslist);
                    }
                }

                etSiteaddress.setText(sightkyclist.get(0).getSiteaddress());
                etBuilduparea.setText(sightkyclist.get(0).getBuild_Up_Area());
                etConstrunctionperoid.setText(sightkyclist.get(0).getConstruction_Period());
                etCompletion_Date.setText(sightkyclist.get(0).getCompletion_Date());
                clientname.setText(sightkyclist.get(0).getClientName());
                Sitename.setText(Sharedpref.getStringPreferences(MyPreferenceManager.Site_name));
                etRcc_consultant.setText(sightkyclist.get(0).getRCC_Consultant());
                etArchitecture.setText(sightkyclist.get(0).getArchitect());
                etxreerano.setText(sightkyclist.get(0).getRerano());


                ArrayAdapter<String> abc = (ArrayAdapter<String>) spConstrunction_management.getAdapter();
                spConstrunction_management.setSelection(abc.getPosition(sightkyclist.get(0).getConstructionManagement()));

                ArrayAdapter<String> abcd = (ArrayAdapter<String>) spgeo_investigation.getAdapter();
                spgeo_investigation.setSelection(abcd.getPosition(sightkyclist.get(0).getGeo_tech_Invest()));


//

//                ArrayAdapter<String> array_spinner=(ArrayAdapter<String>)spRcc_Consultant.getAdapter();
//                spRcc_Consultant.setSelection(array_spinner.getPosition(sightkyclist.get(0).getRCC_Consultant()));

//                etRcc_Consultant.setText(sightkyclist.get(0).getRCC_Consultant());
//                etArchitecture.setText(sightkyclist.get(0).getArchitect());
//                etConstrunction_management.setText(sightkyclist.get(0).getConstructionManagement());


            }

            //
//
            @Override
            public void onError(String message) {
                Log.v("tag", message.toString());

                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

            }
        }, Sightkyc[].class);


        etCompletion_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(R.id.etxcomplitiondate);
            }
        });


        spgeo_investigation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                geo_investigation = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        spConstrunction_management.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position == 2) {

                    LayoutInflater li = LayoutInflater.from(getActivity());
                    View promptsView = li.inflate(R.layout.construnction_dialog, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getActivity());

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    final EditText userInput = (EditText) promptsView
                            .findViewById(R.id.construnctionmanagement);

                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Utility.hideSoftKeyboard(getActivity());
                                            if (userInput.getText().toString().length() == 0) {
                                                Log.d("tag1",""+userInput.getText().toString().length());
                                                Toast.makeText(getActivity(), "Please enter Construnction Management Name.", Toast.LENGTH_SHORT).show();
                                            spConstrunction_management.setSelection(0);


                                            } else {
                                                Construnction_management = userInput.getText().toString().trim();
                                                Construnction_flag = "true";
                                                newoutsource.setText(Construnction_management);
                                                construnction_layout.setVisibility(View.VISIBLE);
                                                dialog.dismiss();
                                            }
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            Utility.hideSoftKeyboard(getActivity());
                                            spConstrunction_management.setSelection(0);

                                        }
                                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                } else {
                    construnction_layout.setVisibility(View.GONE);
                    Construnction_flag = "false";
                    Construnction_management = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valid()) {
                    if (Util.isNetworkConnected(getActivity())) {
                        Utility.hideSoftKeyboard(getActivity());
                        sendData();

                    } else {
                        Util.showToast(getActivity(), "Connection not available, Please try later.");
                    }
                }
            }
        });

        return view;
    }

    private void sendData() {
        Siteaddress = etSiteaddress.getText().toString().trim();
        Builduparea = etBuilduparea.getText().toString().trim();
        Construnctionperiod = etConstrunctionperoid.getText().toString().trim();
        completion_Date = etCompletion_Date.getText().toString().trim();
//                Rcc_consultant = etRcc_Consultant.getText().toString().trim();
//                Architecture = etArchitecture.getText().toString().trim();
//                Construnction_management = etConstrunction_management.getText().toString().trim();


        getMyActivity().getSightkycpojo().setSiteaddress(Siteaddress);
        getMyActivity().getSightkycpojo().setBuild_Up_Area(Builduparea);
        getMyActivity().getSightkycpojo().setConstruction_Period(Construnctionperiod);
        getMyActivity().getSightkycpojo().setCompletion_Date(completion_Date);
        getMyActivity().getSightkycpojo().setRCC_Consultant(etRcc_consultant.getText().toString().trim());
        getMyActivity().getSightkycpojo().setRcc_flag(Rcc_flag);
        getMyActivity().getSightkycpojo().setArchitect(etArchitecture.getText().toString().trim());
        getMyActivity().getSightkycpojo().setArchitecture_flag(Architecture_flag);
        getMyActivity().getSightkycpojo().setConstrunction_flag(Construnction_flag);
        getMyActivity().getSightkycpojo().setConstructionManagement(Construnction_management);
        getMyActivity().getSightkycpojo().setGeo_tech_Invest(geo_investigation);
        getMyActivity().getSightkycpojo().setSiteId(Sharedpref.getStringPreferences(MyPreferenceManager.Siteid));
        getMyActivity().getSightkycpojo().setRerano(etxreerano.getText().toString());


        Fragment fragment = null;
        fragment = new Fragmentsitekyc2();
        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack(null);
            ft.commit();
        }
    }


    private boolean valid() {
//        if (etSiteaddress.getText().toString().trim().length() == 0) {
////            Util.showToast(getActivity(), "Please Enter Site Address.");
//            etSiteaddress.setError("Please Enter Site Address.");
//            etSiteaddress.requestFocus();
//            return false;
//
//        } else if (etBuilduparea.getText().toString().trim().length() == 0) {
////            Util.showToast(getActivity(), "Please Enter Build up Area.");
//            etBuilduparea.setError("Please Enter Build up Area.");
//            etBuilduparea.requestFocus();
//            return false;
//
//        } else if (etConstrunctionperoid.getText().toString().trim().length() == 0) {
////            Util.showToast(getActivity(), "Please Enter Construnction Period .");
//            etConstrunctionperoid.setError("Please Enter Construnction Period.");
//            etConstrunctionperoid.requestFocus();
//            return false;
//
//        } else if (etCompletion_Date.getText().toString().trim().length() == 0) {
////            Util.showToast(getActivity(), "Please Enter Completion Date.");
//            etCompletion_Date.setError("Please Enter Completion Date.");
//            etCompletion_Date.requestFocus();
//            return false;
//
//        } else if (etRcc_consultant.getText().toString().trim().equalsIgnoreCase("Select RCC")) {
//            Toast.makeText(mainActivity, "Please Enter rcc Consultant", Toast.LENGTH_SHORT).show();
//            return false;
//        } else if (etArchitecture.getText().toString().trim().equalsIgnoreCase("Select Architecture")) {
//            Toast.makeText(mainActivity, "Please Enter Architect", Toast.LENGTH_SHORT).show();
//            return false;
//        } else if (Construnction_management.equalsIgnoreCase("Select Option")) {
//
//            Toast.makeText(mainActivity, "Please Select Construnction Management", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        else if (geo_investigation.equalsIgnoreCase("Select Option")) {
//
//            Toast.makeText(mainActivity, "Please Select Geo Technical Investigation.", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        return true;
    }


    private void Initview(View view) {
        btnNext = (Button) view.findViewById(R.id.btnnext1);
        etCompletion_Date = (EditText) view.findViewById(R.id.etxcomplitiondate);
        etSiteaddress = (EditText) view.findViewById(R.id.etxsiteaddress);
        etBuilduparea = (EditText) view.findViewById(R.id.etxbuidlduparea);
        etConstrunctionperoid = (EditText) view.findViewById(R.id.etxconstperiod);
        etRcc_consultant = (EditText) view.findViewById(R.id.etxrccconsultant);
        etArchitecture = (EditText) view.findViewById(R.id.etxarchitecture);
        etxreerano=(EditText)view.findViewById(R.id.etxreerano);
        spConstrunction_management = (Spinner) view.findViewById(R.id.spconst_mngmt);
        spgeo_investigation = (Spinner) view.findViewById(R.id.spgeo_invest);
        clientname = (TextView) view.findViewById(R.id.tvclientname);
        Sitename = (TextView) view.findViewById(R.id.tvsitename);
        sightkyc = new Sightkyc();
        Sharedpref = new MyPreferenceManager(getActivity());
        mainActivity = (MainActivity) getActivity();
        rccConsultantList = new ArrayList<>();
        architectureList = new ArrayList<>();
        rcclayout = (LinearLayout) view.findViewById(R.id.rcc_layout);
        newaddedrcc = (TextView) view.findViewById(R.id.newaddedrcc);
        Architect_layout = (LinearLayout) view.findViewById(R.id.Architect_layout);
        newaddedarchitect = (TextView) view.findViewById(R.id.newaddedarchitect);
        construnction_layout = (LinearLayout) view.findViewById(R.id.Construnction_layout);
        newoutsource = (TextView) view.findViewById(R.id.newoutsource);

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
                case R.id.etxcomplitiondate:
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

                    etCompletion_Date.setText(String.valueOf(Fday) + "/" + Fmonth + "/" + year);
                    break;
            }

        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Site KYC");
    }

    public MainActivity getMyActivity() {
        return (MainActivity) getActivity();
    }
}
