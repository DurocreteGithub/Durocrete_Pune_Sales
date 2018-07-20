package com.durocrete_punesales_newapp.Test.steelrequestform;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.durocrete_punesales_newapp.R;
import com.durocrete_punesales_newapp.Utillity.ListviewheightClass;
import com.durocrete_punesales_newapp.Utillity.MyPreferenceManager;
import com.durocrete_punesales_newapp.activity.MainActivity;
import com.durocrete_punesales_newapp.Test.Testrequestform;
import com.durocrete_punesales_newapp.Test.cubetestrquestform.Cubeset;
import com.durocrete_punesales_newapp.model.Materiallist;
import com.durocrete_punesales_newapp.model.Testlist;
import com.durocrete_punesales_newapp.network.CallWebservice;
import com.durocrete_punesales_newapp.network.IUrls;
import com.durocrete_punesales_newapp.network.VolleyResponseListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by root on 19/5/17.
 */

public class Fragmentsteeltestrequest extends Fragment {


    private Button btnnextsample;
    private Button btnSumbitSample;
    MainActivity mainActivity;
    MyPreferenceManager sharedPref;
    Steelrequestform steelrequestform;
    private List<Materiallist> Allrequestmateriallists;
    private List<Cubeset> Allcubesetlist;
    private ArrayAdapter<Materiallist> requestmateriallistadapter;
    String selectedmaterialId;
    String selectedmaterialname;
    String selectedgradename;
    Steel_MyRecyclerViewAdapter Steeladapter;
    RecyclerView rvAnimals;
    private SteelTestlistadapter steeltestlistadapter;
    private List<Testlist> Alltestlist;
    private ListView lvtestlist;
    List<Steelrequestform> steelrequestformList = new ArrayList<>();
    RecyclerView recyclerView;
    private Spinner no_of_sets;
    String no_of_sets_quantity;
    Testrequestform testrequestform;
    CheckBox chkAll;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentsteelstrequest, container, false);

        Initview(view);

        fetchtestlist(sharedPref.getStringPreferences(MyPreferenceManager.materialId));


        btnnextsample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitnextSample();
            }
        });

        chkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < Alltestlist.size(); i++) {
                        Alltestlist.get(i).setIschecked(true);
                    }
                } else {
                    for (int i = 0; i < Alltestlist.size(); i++) {
                        Alltestlist.get(i).setIschecked(false);
                    }
                }
                steeltestlistadapter.notifyDataSetChanged();

            }
    });


        no_of_sets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

    {
        @Override
        public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){
        no_of_sets_quantity = parent.getItemAtPosition(position).toString();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        Steeladapter = new Steel_MyRecyclerViewAdapter(getActivity(), position + 1);
        recyclerView.setAdapter(Steeladapter);
    }

        @Override
        public void onNothingSelected (AdapterView < ? > parent){

    }
    });

        return view;
}

    private void fetchtestlist(String selectedmaterialId) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("materialId", selectedmaterialId);

        Alltestlist = new ArrayList<Testlist>();

        CallWebservice.getWebservice(true, mainActivity, Request.Method.POST, IUrls.GET_Tests, hashMap, new VolleyResponseListener<Testlist>() {
            @Override
            public void onResponse(Testlist[] object, String s, String key) {

                if (object[0] instanceof Testlist) {
                    for (Testlist testobject : object) {
                        Alltestlist.add(testobject);
                    }
                }

//                        testlistadapter = new ArrayAdapter<Testlist>(getActivity(),
//                                android.R.layout.simple_list_item_multiple_choice, Alltestlist);

                steeltestlistadapter = new SteelTestlistadapter(getActivity(), Alltestlist);
                lvtestlist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                lvtestlist.setAdapter(steeltestlistadapter);
                ListviewheightClass.setListViewHeightBasedOnChildrenDrawer(lvtestlist);

            }

            @Override
            public void onError(String message) {
                Log.v("tag", message.toString());

            }
        }, Testlist[].class);


    }


    private void submitnextSample() {


        int selectedmaterialidvalue = Integer.parseInt(sharedPref.getStringPreferences(MyPreferenceManager.materialId));

        if (selectedmaterialidvalue == 0) {
            Toast.makeText(mainActivity, "Please Select Material First", Toast.LENGTH_SHORT).show();

        } else {
            steelrequestform = new Steelrequestform();

            ArrayList<Steelset> selectedItems = new ArrayList<Steelset>(Steeladapter.AllSteellist());


            ArrayList<Testlist> selectedItems1 = new ArrayList<Testlist>();
            if (Steeladapter.AllSteellist().size() != 0) {
                for (int i = 0; i < Alltestlist.size(); i++) {
                    if (steeltestlistadapter.Alltestlist().get(i).ischecked(true)) {
                        selectedItems1.add(steeltestlistadapter.Alltestlist().get(i));

                    }
                }
            }

            if (valid(selectedItems) == 0) {
                no_of_sets.setSelection(0);
                Steeladapter.AllSteellist().clear();
                Steeladapter.initialiselist();

                steelrequestform.setNo_of_sets(no_of_sets_quantity);
                steelrequestform.setSets(selectedItems);
                steelrequestform.setTests(selectedItems1);
                steelrequestform.setMaterial_id(sharedPref.getStringPreferences(MyPreferenceManager.materialId));
                steelrequestform.setEnquiry_id(sharedPref.getStringPreferences(MyPreferenceManager.enquiryId));


                steelrequestformList.add(steelrequestform);

                testrequestform = new Testrequestform();
                testrequestform.setSteeltestrequestformlist(steelrequestformList);

                getMyActivity().getTestrequestformList().add(testrequestform);

                Toast.makeText(mainActivity, "Successfully added Sample ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mainActivity, "Please Fill information of Set" + valid(selectedItems), Toast.LENGTH_SHORT).show();

            }
        }

    }

    private int valid(ArrayList<Steelset> selectedItems) {
        int val = 0;
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i).getIsfilled() == false) {
                val = i + 1;
                break;
            }
        }
        return val;
    }


    private void Initview(View view) {


        btnnextsample = (Button) view.findViewById(R.id.btnnextsample);
        mainActivity = (MainActivity) getActivity();
        sharedPref = new MyPreferenceManager(getActivity());
        lvtestlist = (ListView) view.findViewById(R.id.lvmaterialtestlist);
        no_of_sets = (Spinner) view.findViewById(R.id.cubetestname);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvAnimals);
        chkAll = (CheckBox) view.findViewById(R.id.chkAll);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Test Request Form");
    }

    public MainActivity getMyActivity() {
        return (MainActivity) getActivity();
    }
}
