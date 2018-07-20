package com.durocrete_punesales_newapp.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.durocrete_punesales_newapp.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.durocrete_punesales_newapp.Utillity.MyPreferenceManager;
import com.durocrete_punesales_newapp.Utillity.Utility;
import com.durocrete_punesales_newapp.activity.MainActivity;
import com.durocrete_punesales_newapp.adapter.MaterialNQuantityAdapter;
import com.durocrete_punesales_newapp.adapter.MaterialSelectionAdapter;
import com.durocrete_punesales_newapp.adapter.OnMaterialChangeListener;
import com.durocrete_punesales_newapp.model.Lab;
import com.durocrete_punesales_newapp.model.MaterialDetailModel;
import com.durocrete_punesales_newapp.model.Sightkyc;
import com.durocrete_punesales_newapp.model.Updateclient;
import com.durocrete_punesales_newapp.model.Util;
import com.durocrete_punesales_newapp.network.CallWebservice;
import com.durocrete_punesales_newapp.network.CallWebservice1;
import com.durocrete_punesales_newapp.network.IUrls;
import com.durocrete_punesales_newapp.network.VolleyResponseListener;

public class SelectMaterialFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, OnMaterialChangeListener {
    private static String TAG = SelectMaterialFragment.class.getSimpleName();
    private Spinner spinnerMaterial, spinnerQuantity;
    private ListView lvCollectedMaterial;
    private Button btnAddMaterial, btnNext, btnCheckOut;
    private ArrayList<MaterialDetailModel> materialDetailArrayList = null;
    private MaterialSelectionAdapter materialSelectionAdapter = null;
    private MaterialDetailModel materialDetailModel = new MaterialDetailModel();
    private MaterialNQuantityAdapter materialNQuantityAdapter = null;
    private ArrayList<MaterialDetailModel> addedMaterialList = null;
    private ArrayList<Lab>Lablist=null;
    private String siteId;
    Boolean flag=false;
    private JSONArray jsonArray1;
    MyPreferenceManager Sharedpref;
    private ArrayList<Testlab> testlabArrayList = null;
    Sightkyc sightkyc;
    JsonArray jsonArray;
    ProgressDialog progressDialog;
    String[] quantitySpinnerDataArray = new String[]{"Select Lab", "Abcd", "PQR", "XYZ", "QWER", "ASDF", "ZXCV", "MNBV"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnetupdatesitekyc3, container, false);

//        siteId=Sharedpref.getStringPreferences(MyPreferenceManager.Siteid);

//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            siteId = bundle.getInt(SyncStateContract.Constants.SITEID);
//        }

        spinnerMaterial = (Spinner) view.findViewById(R.id.spinnerMaterial);
        spinnerQuantity = (Spinner) view.findViewById(R.id.spinnerQuantity);
        lvCollectedMaterial = (ListView) view.findViewById(R.id.lvCollectedMaterial);
        btnAddMaterial = (Button) view.findViewById(R.id.btnAddMaterial);
        btnNext = (Button) view.findViewById(R.id.btnNext);
        Sharedpref = new MyPreferenceManager(getActivity());
        sightkyc = new Sightkyc();
        materialSelectionAdapter = new MaterialSelectionAdapter(getActivity(), R.layout.row_select_route, new ArrayList<MaterialDetailModel>());
        spinnerMaterial.setAdapter(materialSelectionAdapter);
        materialNQuantityAdapter = new MaterialNQuantityAdapter(getActivity(), R.layout.row_material_selection, new ArrayList<MaterialDetailModel>(), this);
//        spinnerQuantity.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.row_select_route, quantitySpinnerDataArray));
        lvCollectedMaterial.setAdapter(materialNQuantityAdapter);
        addedMaterialList = new ArrayList<>();
        Lablist=new ArrayList<>();
        spinnerMaterial.setOnItemSelectedListener(this);
        spinnerQuantity.setOnItemSelectedListener(this);
        btnAddMaterial.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        makeGetMaterialRequest();

        return view;
    }




    private void makeGetMaterialRequest() {


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        CallWebservice1.getWebservice(true, getActivity(), Request.Method.POST, IUrls.getMaterialList, null, new VolleyResponseListener<MaterialDetailModel>() {
            @Override
            public void onResponse(MaterialDetailModel[] object, String message, String key) {

                if (object[0] instanceof MaterialDetailModel) {
                    materialDetailArrayList = new ArrayList<MaterialDetailModel>();
                    MaterialDetailModel materialDetailModel = (MaterialDetailModel) object[0];
                    Log.v(TAG, materialDetailModel.toString());

                    for (MaterialDetailModel materialDetail : object) {
                        materialDetailArrayList.add(materialDetail);
                    }
                    setquantitydata();
                    materialSelectionAdapter.setArray(materialDetailArrayList);
                    flag=true;
//                    spinnerMaterial.setAdapter(materialSelectionAdapter);
                    /*set adapter to Quentity Spinner also*/
//                    spinnerQuantity.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, quantitySpinnerDataArray));
                }
            }

            @Override
            public void onError(String message) {
                Log.v("tag", message.toString());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

            }
        }, MaterialDetailModel[].class);
    }

    private void setquantitydata() {
        CallWebservice1.getWebservice(true, getActivity(), Request.Method.POST, IUrls.Get_labs, null, new VolleyResponseListener<Lab>() {
            @Override
            public void onResponse(Lab[] object, String message, String key) {

                if (object[0] instanceof Lab) {
                    Lablist = new ArrayList<Lab>();

                    for (Lab lab1 : object) {
                        Lablist.add(lab1);
                    }
                    getmateriallabdata();
                    ArrayAdapter<Lab> adapter = new ArrayAdapter<Lab>(getActivity(), android.R.layout.simple_spinner_item, Lablist);
                    spinnerQuantity.setAdapter(adapter);





//                    spinnerMaterial.setAdapter(materialSelectionAdapter);
                    /*set adapter to Quentity Spinner also*/
//                    spinnerQuantity.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, quantitySpinnerDataArray));
                }

            }

            @Override
            public void onError(String message) {
                Log.v("tag", message.toString());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

            }
        }, Lab[].class);

    }

    private void getmateriallabdata() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("siteId", Sharedpref.getStringPreferences(MyPreferenceManager.Siteid));
        Log.d("siteid", Sharedpref.getStringPreferences(MyPreferenceManager.Siteid));

        CallWebservice1.getWebservice(true, getActivity(), Request.Method.POST, IUrls.Material_labs, hashMap, new VolleyResponseListener<MaterialDetailModel>() {
            @Override
            public void onResponse(MaterialDetailModel[] object, String message, String key) {

                progressDialog.dismiss();

                if (object[0] instanceof MaterialDetailModel) {

                    for (MaterialDetailModel materialDetail : object) {
                        addedMaterialList.add(materialDetail);
                    }
//                    getMyActivity().getSightkycpojo().setAddedMaterialList(addedMaterialList);
                    materialNQuantityAdapter.setArray(addedMaterialList);
                }
            }

            public void onError(String message) {
                Log.v("tag", message.toString());
                progressDialog.dismiss();
//                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

            }
        }, MaterialDetailModel[].class);

//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Login With Details....");
//        progressDialog.show();
//
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, IUrls.Material_labs,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        Log.d("a",response);
//
//                        JSONObject jsonObject = null;
//                        progressDialog.dismiss();
//                        try {
//                            jsonObject = new JSONObject(response);
//                            jsonArray1 = jsonObject.getJSONArray(IConstants.RESPONSE_ARRAY);
//                            GsonBuilder gsonBuilder = new GsonBuilder();
//                            Gson gson = gsonBuilder.create();
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d("tag", "Error: " + error.getMessage());
//
//                progressDialog.dismiss();
//
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("siteid", Sharedpref.getStringPreferences(MyPreferenceManager.Siteid));
//                return params;
//            }
//
//        };
//        AppController.getInstance().addToRequestQueue(stringRequest);
//    }


    }






    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        switch (parent.getId()) {
            case R.id.spinnerMaterial:
                Log.v(TAG, "spinnerMaterial is clicked : " + materialDetailArrayList.get(position).getMaterialName());
                materialDetailModel.setMaterialName(materialDetailArrayList.get(position).getMaterialName());
                materialDetailModel.setMaterialId(materialDetailArrayList.get(position).getMaterialId());
                break;
            case R.id.spinnerQuantity:
                Log.v(TAG, "spinnerQuantity is clicked : " + new ArrayList<String>(Arrays.asList(quantitySpinnerDataArray)).get(position));
                materialDetailModel.setQuantityId(Lablist.get(position).getId());
                materialDetailModel.setMaterialQuantity(Lablist.get(position).getLab_name());
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddMaterial:

                Log.v(TAG, "btnAddMaterial is clicked MaterialID : " + materialDetailModel.getMaterialQuantity());
                Log.v("AAA", "" + materialDetailModel.getMaterialQuantity().startsWith("Select"));
                Log.v("AAA", "" + materialDetailModel.getMaterialQuantity().contains("Select"));
                Log.v("AAA", "" + materialDetailModel.getMaterialQuantity().contentEquals("Select"));


                if (materialDetailModel != null /*&& !materialDetailModel.getMaterialQuantity().trim().equals("") && !materialDetailModel.getMaterialName().trim().equals("")*/) {
                    /*if (!materialDetailModel.getMaterialName().startsWith("Select")
                            && !materialDetailModel.getMaterialQuantity().startsWith("Select")) {*/
                    if (!materialDetailModel.getMaterialName().startsWith("Select")) {
                        if (!materialDetailModel.getMaterialQuantity().startsWith("Select")) {
                            Log.v(TAG, "removeDuplicateMaterial : " + removeDuplicateMaterial(materialDetailModel));
                           /* if(addedMaterialList.size() == 0) {
                                addedMaterialList.add(materialDetailModel);
                                Log.v(TAG, "addedMaterialList sizevd : " + addedMaterialList.size());
                                materialNQuantityAdapter.setArray(addedMaterialList);
                                materialDetailModel = new MaterialDetailModel();
                            }else {
                                for (int i = 0; i < addedMaterialList.size(); i++) {
                                    if (addedMaterialList.contains(materialDetailModel)) {
                                        Toast.makeText(getActivity(), " Material is already present:", Toast.LENGTH_SHORT).show();
                                        break;
                                    } else {
                                        Log.v(TAG, "addedMaterialList size : " + addedMaterialList.size());
                                        addedMaterialList.add(materialDetailModel);
                                        materialNQuantityAdapter.setArray(addedMaterialList);
                                        materialDetailModel = new MaterialDetailModel();
                                    }
                                }
                                *//*for (int i = 0; i < addedMaterialList.size(); i++) {
                                    if (materialDetailModel.getMaterialId().equals(addedMaterialList.get(i).getMaterialId())) {
                                        Toast.makeText(getActivity(), " Material is already present:", Toast.LENGTH_SHORT).show();
                                    } else {
                                        addedMaterialList.contains(materialDetailModel);
                                        Log.v(TAG, "addedMaterialList size : " + addedMaterialList.size());
                                        materialNQuantityAdapter.setArray(addedMaterialList);
                                        materialDetailModel = new MaterialDetailModel();
                                    }
                                }*//*
                            }*/
                            if (!removeDuplicateMaterial(materialDetailModel)) {
                                addedMaterialList.add(materialDetailModel);
                                Log.v(TAG, "addedMaterialList size : " + addedMaterialList.size());
                                materialNQuantityAdapter.setArray(addedMaterialList);
                                materialDetailModel = new MaterialDetailModel();
                                spinnerMaterial.setSelection(0);
                                spinnerQuantity.setSelection(0);

                            } else {
                                Toast.makeText(getActivity(), " Material is already present.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Select Lab ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Select Material ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Select Material and Lab first...", Toast.LENGTH_SHORT).show();
                }
                /*} else {
                    Toast.makeText(getActivity(), "Select Next Material Yype", Toast.LENGTH_SHORT).show();
                }*/
                break;

            case R.id.btnNext:

//                makesiteupdaterequest();

                Utility.hideSoftKeyboard(getActivity());
                if (addedMaterialList != null) {
                    getMyActivity().getSightkycpojo().setAddedMaterialList(addedMaterialList);
                    getMyActivity().getSiteupdatelist().add(getMyActivity().getSightkycpojo());
                } else {
                    getMyActivity().getSiteupdatelist().add(getMyActivity().getSightkycpojo());
                }
                Gson gson = new Gson();
                JsonElement element = gson.toJsonTree(getMyActivity().getSiteupdatelist(), new TypeToken<List<Sightkyc>>() {
                }.getType());
                if (!element.isJsonArray()) {

                }

                jsonArray = element.getAsJsonArray();
                String tag1=jsonArray.toString();

                if (Util.isNetworkConnected(getActivity())) {
                    Utility.hideSoftKeyboard(getActivity());
                    makerequest();

                } else {
                    Sharedpref.setStringPreferences(MyPreferenceManager.jsonarray,tag1);
                    Toast.makeText(getActivity(), "Site KYC Updated Successfully", Toast.LENGTH_SHORT).show();
                    getMyActivity().removefragmewnt();
                    android.support.v4.app.Fragment fragment = null;
                    fragment = new Fragmenthome();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();

                }

                break;


        }

    }

    private void makerequest() {

        HashMap<String, String> param = new HashMap<>();
        param.put("data", jsonArray.toString());

        CallWebservice.getWebservice(true, getActivity(), Request.Method.POST, IUrls.Update_Site, param, new VolleyResponseListener<Updateclient>() {
            @Override
            public void onResponse(Updateclient[] object, String message, String key) {

                Toast.makeText(getActivity(), "Site KYC Updated Successfully", Toast.LENGTH_SHORT).show();
                getMyActivity().removefragmewnt();

            }

            @Override
            public void onError(String message) {
                Log.v("tag", message.toString());
                Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                android.support.v4.app.Fragment fragment = null;
                fragment = new Fragmenthome();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();

            }
        }, Updateclient[].class);

    }



    private void makesiteupdaterequest() {
        ArrayList<String> materialIds = new ArrayList<>();
        ArrayList<String> materialQuantity = new ArrayList<>();

        for (int i = 0; i < addedMaterialList.size(); i++) {
            materialIds.add(addedMaterialList.get(i).getMaterialId());
            materialQuantity.add(addedMaterialList.get(i).getQuantityId());
        }
        if(materialIds.size()!=0 && materialQuantity.size()!=0)
        {
            makefinalRequest(materialIds,materialQuantity);
        }
    }

    private void makefinalRequest(ArrayList<String> materialIds, ArrayList<String> materialQuantity) {

        HashMap<String, String> param = new HashMap<>();
        param.put("siteId", Sharedpref.getStringPreferences(MyPreferenceManager.Siteid));
        param.put("Build_Up_Area", getMyActivity().getSightkycpojo().getBuild_Up_Area());
        param.put("Construction_Period", getMyActivity().getSightkycpojo().getConstruction_Period());
        param.put("Completion_Date",getMyActivity().getSightkycpojo().getCompletion_Date());
        param.put("RCC_Consultant", getMyActivity().getSightkycpojo().getRCC_Consultant());
        param.put("Architect",getMyActivity().getSightkycpojo().getArchitect());
        param.put("ConstructionManagement", getMyActivity().getSightkycpojo().getConstructionManagement());
        param.put("Geo_tech_Invest", getMyActivity().getSightkycpojo().getGeo_tech_Invest());
        param.put("No_of_Buildings", getMyActivity().getSightkycpojo().getNo_of_Buildings());
        param.put("RCC", getMyActivity().getSightkycpojo().getRCC());
        param.put("Block_Work_Plaster", getMyActivity().getSightkycpojo().getBlock_Work_Plaster());
        param.put("Finishes'", getMyActivity().getSightkycpojo().getFinishes());
        param.put("proposed_building",getMyActivity().getSightkycpojo().getProposed_building());
        param.put("proposed_date", getMyActivity().getSightkycpojo().getProposed_date());
        param.put("under_const_building", getMyActivity().getSightkycpojo().getUnder_const_building());
        param.put("materialId", materialIds.toString().replaceAll("\\[|\\]", ""));
        param.put("materiallab", materialQuantity.toString().replaceAll("\\[|\\]", ""));

        CallWebservice.getWebservice(true, getActivity(), Request.Method.POST, IUrls.Update_Site, param, new VolleyResponseListener<Updateclient>() {
            @Override
            public void onResponse(Updateclient[] object, String message, String key) {

                Toast.makeText(getActivity(), "Site KYC Updated Successfully", Toast.LENGTH_SHORT).show();
                android.support.v4.app.Fragment fragment = null;
                fragment = new Fragmenthome();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            }

            @Override
            public void onError(String message) {
                Log.v("tag", message.toString());
                Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();

            }
        }, Updateclient[].class);



    }


    @Override
    public void onChangeAdapterView(boolean isChanged, MaterialDetailModel position) {
        if (isChanged) {
            Log.v(TAG, " deleted material position : " + position);
            addedMaterialList.remove(position);
            materialNQuantityAdapter.setArray(addedMaterialList);
        }
    }

    private boolean removeDuplicateMaterial(MaterialDetailModel materialDetailModel) {
        if (materialDetailModel != null) {
            int count = 0;
            for (int i = 0; i < addedMaterialList.size(); i++) {
                if (materialDetailModel.getMaterialId().equalsIgnoreCase(addedMaterialList.get(i).getMaterialId())) {
                    count++;
                }
            }
            if (count > 0) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }
    /*private boolean removeDuplicateMaterial(MaterialDetailModel materialDetailModel) {
        Log.v(TAG, "addedMaterialList : " + addedMaterialList.size());
        if (materialDetailModel != null) {
         *//*   for (MaterialDetailModel detailModel : addedMaterialList) {
                if (detailModel.getMaterialId().equalsIgnoreCase(materialDetailModel.getMaterialId())) {
                    return true;
                } else {
                    return false;
                }
            }*//*
            for (int i = 0; i < addedMaterialList.size(); i++) {
                if (materialDetailModel.getMaterialId().equals(addedMaterialList.get(i).getMaterialId())) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }*/

//    private void makeCheckOutRequest() {
//        HashMap<String, String> param = new HashMap<>();
//        param.put("siteId", String.valueOf(siteId));
//        param.put("driverId", SharedPreference.getInstanceProfileData(getActivity()).getUserId());
//        param.put("materialId", "");
//        param.put("quantity", "");
//        param.put("workStatus", "");
//        param.put("sign", "");
//
//        Log.v(TAG, "siteId : " + siteId);
//        Log.v(TAG, "driverId : " + "26");
//        Log.v(TAG, "materialId :" + "");
//        Log.v(TAG, "quantity : " + "");
//        Log.v(TAG, "workStatus : " + "");
//        Log.v(TAG, "sign : " + "");
//
//        RequestHandler.makeWebservice(true, getActivity(), Request.Method.POST, URLS.getInstance().getCheckOut, param, CheckInOUTModel[].class, new VolleyResponseListener<CheckInOUTModel>() {
//                    @Override
//                    public void onResponse(CheckInOUTModel[] object) {
//                        if (object[0] instanceof CheckInOUTModel) {
//                            CheckInOUTModel checkInOUTModel = (CheckInOUTModel) object[0];
//                            Log.v(TAG, " onResponse8 :" + checkInOUTModel.getResult());
//                            sendIntent();
//                          /*  Bundle bundle = new Bundle();
//                            bundle.putInt(Constants.CHECK_OUT_SITE_ID, siteId);
//                            Fragment mapFragment = new MapActivity();
//                            mapFragment.setArguments(bundle);
//                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mapFragment).addToBackStack(TAG).commit();*/
//                        }
//                    }
//
//
//                    @Override
//                    public void onError(String message) {
//                        Utility.errorDialog(message, getActivity());
//
//                    }
//                }
//        );
//    }

//    private void sendIntent() {
//        Log.v(TAG, "CheckInId : " + SharedPreference.getInstanceProfileData(getActivity()).getCheckIn());
//        if (SharedPreference.getInstanceProfileData(getActivity()).getCheckIn().equals("1")) {
//            getActivity().finish();
//        } else {
//            Intent intent = new Intent(getActivity(), MapActivity.class);
//            intent.putExtra(Constants.CHECK_OUT_SITE_ID, siteId);
//            getActivity().startActivity(intent);
//        }
//
//    }

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

