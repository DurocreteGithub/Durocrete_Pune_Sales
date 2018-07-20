package com.durocrete_punesales_newapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.durocrete_punesales_newapp.R;

import java.util.ArrayList;
import java.util.HashMap;

import com.durocrete_punesales_newapp.Utillity.MyPreferenceManager;
import com.durocrete_punesales_newapp.activity.MainActivity;
import com.durocrete_punesales_newapp.adapter.Sitesummaryadapter;
import com.durocrete_punesales_newapp.model.Sitesummary;
import com.durocrete_punesales_newapp.network.CallWebservice;
import com.durocrete_punesales_newapp.network.IUrls;
import com.durocrete_punesales_newapp.network.VolleyResponseListener;

/**
 * Created by root on 1/7/17.
 */

public class Sitevisitdetail extends Fragment {

    ArrayList<Sitesummary> Sitesummarylist;
    RecyclerView summaryrecycler;
    Sitesummaryadapter summaryadapter;
    MyPreferenceManager Sharedpref;
    MainActivity mainActivity;
    TextView nodata;
    private TextView clientname, sitename;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sitesummary, container, false);


        summaryrecycler = (RecyclerView) view.findViewById(R.id.summaryrecycle);
        Sharedpref = new MyPreferenceManager(getActivity());
        mainActivity = (MainActivity) getActivity();
        nodata = (TextView) view.findViewById(R.id.nodata);
        clientname = (TextView) view.findViewById(R.id.tvclientname);
        sitename = (TextView) view.findViewById(R.id.tvsitename);


        Sitesummarylist = new ArrayList<>();


        HashMap<String, String> hashMap1 = new HashMap<>();
        hashMap1.put("siteId", Sharedpref.getStringPreferences(MyPreferenceManager.Siteid));


        CallWebservice.getWebservice(true, getActivity(), Request.Method.POST, IUrls.site_history, hashMap1, new VolleyResponseListener<Sitesummary>() {
            @Override
            public void onResponse(Sitesummary[] object, String message, String key) {
                if (object[0] instanceof Sitesummary) {
                    for (Sitesummary routeObject : object) {
                        Sitesummarylist.add(routeObject);
                    }

                    if (Sitesummarylist.size() != 0) {
                        clientname.setText(Sitesummarylist.get(0).getClientName());
                        sitename.setText(Sharedpref.getStringPreferences(MyPreferenceManager.Site_name));
                        summaryadapter = new Sitesummaryadapter(getActivity(), Sitesummarylist);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        summaryrecycler.setLayoutManager(mLayoutManager);
                        summaryrecycler.setItemAnimator(new DefaultItemAnimator());
                        summaryrecycler.setAdapter(summaryadapter);
                    }
                }


            }


            @Override
            public void onError(String message) {
                clientname.setText(message);
                sitename.setText(Sharedpref.getStringPreferences(MyPreferenceManager.Site_name));
                summaryrecycler.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
                nodata.setText("No Data Available ");


            }
        }, Sitesummary[].class);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Site Visit Log");
    }

    public MainActivity getMyActivity() {
        return (MainActivity) getActivity();
    }


}


