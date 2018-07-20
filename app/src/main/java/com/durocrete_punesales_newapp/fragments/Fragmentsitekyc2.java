package com.durocrete_punesales_newapp.fragments;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.durocrete_punesales_newapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.durocrete_punesales_newapp.Utillity.CustomDatePickerListener;
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

public class Fragmentsitekyc2 extends Fragment {

    Button btnUpdate;
    private EditText etnoofbuilding;
    private EditText etnoofproposedbuilding;
    private EditText etproposedDate;
    private EditText etnoofundeeconst;
    private Spinner sp_rcc;
    private Spinner sp_block_work_plaster;
    private Spinner sp_finishes;
    Sightkyc sightkyc;

    String construnction_management;
    String rcc;
    String block_work_plaster;
    String finishes;
    MyPreferenceManager Sharedpref;
    MainActivity mainActivity;
    private ArrayList<Sightkyc> sightkyclist;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnetupdatesitekyc2, container, false);

        Initview(view);

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

                etnoofundeeconst.setText(sightkyclist.get(0).getUnder_const_building());
                etnoofproposedbuilding.setText(sightkyclist.get(0).getProposed_building());
                etnoofbuilding.setText(sightkyclist.get(0).getNo_of_Buildings());
                etproposedDate.setText(sightkyclist.get(0).getProposed_date());

                ArrayAdapter<String> abc = (ArrayAdapter<String>) sp_rcc.getAdapter();
                sp_rcc.setSelection(abc.getPosition(sightkyclist.get(0).getRCC()));

                ArrayAdapter<String> abcd = (ArrayAdapter<String>) sp_block_work_plaster.getAdapter();
                sp_block_work_plaster.setSelection(abcd.getPosition(sightkyclist.get(0).getBlock_Work_Plaster()));

                ArrayAdapter<String> abcde = (ArrayAdapter<String>) sp_finishes.getAdapter();
                sp_finishes.setSelection(abcde.getPosition(sightkyclist.get(0).getFinishes()));

            }

            @Override
            public void onError(String message) {
                Log.v("tag", message.toString());

                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

            }
        }, Sightkyc[].class);

        etproposedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDatePicker(R.id.etxnextvisit);

                Utility.selectDatePicker1(getActivity(), 0, new CustomDatePickerListener() {
                    @Override
                    public void onDateSet(Calendar calendar) {
                        etproposedDate.setText(Utility.formatDateForDisplay(getActivity(), calendar.getTime()));
                    }
                });
            }
        });


        sp_rcc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rcc = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_block_work_plaster.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                block_work_plaster = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_finishes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                finishes = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
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

    private boolean valid() {
//        if (etnoofbuilding.getText().toString().trim().length() == 0) {
////            Util.showToast(getActivity(), "Please Enter Site Address.");
//            etnoofbuilding.setError("Please Enter No. of Buildings.");
//            etnoofbuilding.requestFocus();
//            return false;
//
//        } else if (etnoofproposedbuilding.getText().toString().trim().length() == 0) {
////            Util.showToast(getActivity(), "Please Enter Build up Area.");
//            etnoofproposedbuilding.setError("Please Enter No. of Proposed Building.");
//            etnoofproposedbuilding.requestFocus();
//            return false;
//
//        } else if (etproposedDate.getText().toString().trim().length() == 0) {
////            Util.showToast(getActivity(), "Please Enter Construnction Period .");
//            etproposedDate.setError("Please Enter Proposed Date.");
//            etproposedDate.requestFocus();
//            return false;
//
//        } else if (etnoofundeeconst.getText().toString().trim().length() == 0) {
////            Util.showToast(getActivity(), "Please Enter Completion Date.");
//            etnoofundeeconst.setError("Please Enter No. of Under Construnction Building.");
//            etnoofundeeconst.requestFocus();
//            return false;
//        }
//        else if(rcc.equalsIgnoreCase("Select status"))
//        {
//            Toast.makeText(mainActivity, "Please Select Rcc Status Work", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        else if(block_work_plaster.equalsIgnoreCase("Select status"))
//        {
//            Toast.makeText(mainActivity, "Please Select Block Status Work Status Work", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        else if(finishes.equalsIgnoreCase("Select status"))
//        {
//            Toast.makeText(mainActivity, "Please Select Finishes Status Work", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        return true;

    }

    private void sendData() {
        getMyActivity().getSightkycpojo().setRCC(rcc);
        getMyActivity().getSightkycpojo().setNo_of_Buildings(etnoofbuilding.getText().toString().trim());
        getMyActivity().getSightkycpojo().setFinishes(finishes);
        getMyActivity().getSightkycpojo().setBlock_Work_Plaster(block_work_plaster);
        getMyActivity().getSightkycpojo().setProposed_building(etnoofproposedbuilding.getText().toString().trim());
        getMyActivity().getSightkycpojo().setProposed_date(etproposedDate.getText().toString().trim());
        getMyActivity().getSightkycpojo().setUnder_const_building(etnoofundeeconst.getText().toString().trim());

        android.support.v4.app.Fragment fragment = null;
        fragment = new SelectMaterialFragment();
        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack(null);
            ft.commit();
        }

    }

    private void Initview(View view) {

        btnUpdate = (Button) view.findViewById(R.id.btnclUpdateclient);
        etnoofbuilding = (EditText) view.findViewById(R.id.etxnoofcomplete);
        sp_rcc = (Spinner) view.findViewById(R.id.rcc);
        sp_block_work_plaster = (Spinner) view.findViewById(R.id.block_work_plaster);
        sp_finishes = (Spinner) view.findViewById(R.id.finishes);
        sp_rcc = (Spinner) view.findViewById(R.id.rcc);
        Sharedpref = new MyPreferenceManager(getActivity());
        sightkyc = new Sightkyc();
        mainActivity = (MainActivity) getActivity();
        etnoofproposedbuilding = (EditText) view.findViewById(R.id.etxproposed);
        etproposedDate = (EditText) view.findViewById(R.id.proposeddate);
        etnoofundeeconst = (EditText) view.findViewById(R.id.etxnoofbuilding);


    }

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
