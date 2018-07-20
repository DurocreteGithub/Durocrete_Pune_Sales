package com.durocrete_punesales_newapp.Test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.durocrete_punesales_newapp.R;
import com.durocrete_punesales_newapp.Test.cubetestrquestform.CubeTestrequestform;
import com.durocrete_punesales_newapp.Test.cubetestrquestform.Cube_MyRecyclerViewAdapter;
import com.durocrete_punesales_newapp.Test.cubetestrquestform.Fragmenttestrequest;
import com.durocrete_punesales_newapp.Test.mixdesigntestrequest.Fragmentmixdesignrequest;
import com.durocrete_punesales_newapp.Test.othertestrequest.Fragmentothertestrequest;
import com.durocrete_punesales_newapp.Test.steelrequestform.Fragmentsteeltestrequest;
import com.durocrete_punesales_newapp.Test.watertestrequest.Fragmentwatertestrequest;
import com.durocrete_punesales_newapp.Utillity.MyPreferenceManager;
import com.durocrete_punesales_newapp.Utillity.Utility;
import com.durocrete_punesales_newapp.activity.MainActivity;
import com.durocrete_punesales_newapp.fragments.FragmenttermsCondition;
import com.durocrete_punesales_newapp.Test.cubetestrquestform.Cubeset;
import com.durocrete_punesales_newapp.model.Materiallist;
import com.durocrete_punesales_newapp.network.CallWebservice;
import com.durocrete_punesales_newapp.network.IUrls;
import com.durocrete_punesales_newapp.network.VolleyResponseListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by root on 19/5/17.
 */

public class Fragmenttestrequest1 extends Fragment {

    private Spinner spselectmaterial;
    private Button btnSumbitSample;
    MainActivity mainActivity;
    MyPreferenceManager sharedPref;
    CubeTestrequestform cubeTestrequestform;
    private List<Materiallist> Allrequestmateriallists;
    private List<Cubeset> Allcubesetlist;
    private ArrayAdapter<Materiallist> requestmateriallistadapter;
    String selectedmaterialId;
    String selectedmaterialname;
    String selectedgradename;
    Cube_MyRecyclerViewAdapter adapter;
    RecyclerView rvAnimals;
    RecyclerView recyclerView;
    String no_of_sets_quantity;
    int y;
    Fragment fragment = null;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentalltestrequest, container, false);

        Initview(view);

        FetchMateriallist();


        btnSumbitSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (y == 0) {
                    Toast.makeText(mainActivity, "Please Select Material First", Toast.LENGTH_SHORT).show();
                } else if (y != 0 && getMyActivity().getTestrequestformList().size() == 0) {
                    Toast.makeText(mainActivity, "Please Fill Test Request Form.", Toast.LENGTH_SHORT).show();
                } else

                {
                    Utility.hideSoftKeyboard(getActivity());
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new FragmenttermsCondition()).addToBackStack(null);
                    ft.commit();
                }
            }

    });

        spselectmaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

    {
        @Override
        public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){

        if (position != 0) {
            selectedmaterialId = Allrequestmateriallists.get(position).getMaterialId();
            selectedmaterialname = parent.getItemAtPosition(position).toString();
            y = Integer.parseInt(selectedmaterialId);
            sharedPref.setStringPreferences(MyPreferenceManager.materialId, selectedmaterialId);
            selection(y);
        }
       else {
            selectedmaterialId = Allrequestmateriallists.get(position).getMaterialId();
            selectedmaterialname = parent.getItemAtPosition(position).toString();
            y = Integer.parseInt(selectedmaterialId);
            sharedPref.setStringPreferences(MyPreferenceManager.materialId, selectedmaterialId);
            selection(y);
        }
    }

        @Override
        public void onNothingSelected (AdapterView < ? > parent){

    }
    });

        return view;
}

    private void selection(int y) {

        switch (y) {
            case 0:
                fragment = new Fragmentothertestrequest();
                break;
            case 1:
                fragment = new Fragmentothertestrequest();
                break;
            case 2:
                fragment = new Fragmentothertestrequest();
                break;
            case 3:
                fragment = new Fragmentothertestrequest();
                break;
            case 4:
                fragment = new Fragmentothertestrequest();
                break;
            case 5:
                fragment = new Fragmentothertestrequest();
                break;
            case 6:
                fragment = new Fragmentothertestrequest();
                break;
            case 7:
                fragment = new Fragmenttestrequest();
                break;
            case 8:
                fragment = new Fragmentothertestrequest();
                break;
            case 9:
                fragment = new Fragmentothertestrequest();
                break;
            case 10:
                fragment = new Fragmentmixdesignrequest();
                break;
            case 11:
                fragment = new Fragmentothertestrequest();
                break;
            case 12:
                fragment = new Fragmentothertestrequest();
                break;
            case 13:
                fragment = new Fragmentothertestrequest();
                break;
            case 14:
                fragment = new Fragmentothertestrequest();
                break;
            case 15:
                fragment = new Fragmentothertestrequest();
                break;
            case 16:
                fragment = new Fragmentsteeltestrequest();
                break;
            case 17:
                fragment = new Fragmentothertestrequest();
                break;

            case 18:
                fragment = new Fragmentothertestrequest();
                break;

            case 19:
                fragment = new Fragmentwatertestrequest();
                break;

            case 20:
                fragment = new Fragmentothertestrequest();
                break;

            case 21:
                fragment = new Fragmentothertestrequest();
                break;

            case 22:
                fragment = new Fragmentothertestrequest();
                break;

        }

        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.container, fragment);
            ft.commit();
        }


    }


    private void FetchMateriallist() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("", "");

        Allrequestmateriallists = new ArrayList<>();


        CallWebservice.getWebservice(true, mainActivity, Request.Method.POST, IUrls.GET_Material, hashMap, new VolleyResponseListener<Materiallist>() {
            @Override
            public void onResponse(Materiallist[] object, String s, String key) {

                if (object[0] instanceof Materiallist) {
                    for (Materiallist materialobject : object) {
                        Allrequestmateriallists.add(materialobject);
                    }
                }

                requestmateriallistadapter = new ArrayAdapter<Materiallist>(getActivity(),
                        android.R.layout.simple_spinner_item, Allrequestmateriallists);
                spselectmaterial.setAdapter(requestmateriallistadapter);

            }


            @Override
            public void onError(String message) {
                Log.v("tag", message.toString());

            }
        }, Materiallist[].class);

    }


    private void Initview(View view) {

        spselectmaterial = (Spinner) view.findViewById(R.id.spselectmaterial);
        btnSumbitSample = (Button) view.findViewById(R.id.btnSubmitSample);
        mainActivity = (MainActivity) getActivity();
        sharedPref = new MyPreferenceManager(getActivity());


    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Test Request Form");


    }

    public MainActivity getMyActivity() {
        return (MainActivity) getActivity();
    }
}
