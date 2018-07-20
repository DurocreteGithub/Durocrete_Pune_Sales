package com.durocrete_punesales_newapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.durocrete_punesales_newapp.R;

import java.util.ArrayList;
import java.util.HashMap;

import com.durocrete_punesales_newapp.adapter.SiteallocationAdapter;
import com.durocrete_punesales_newapp.model.Siteallocation;
import com.durocrete_punesales_newapp.network.CallWebservice;
import com.durocrete_punesales_newapp.network.IUrls;
import com.durocrete_punesales_newapp.network.VolleyResponseListener;


/**
 * Created by ravi on 23/3/17.
 */
public class FragmentSearchSite extends Fragment  {

    private RecyclerView recyclerSites;
    ArrayList<Siteallocation> siteallocationArrayList;
    SiteallocationAdapter siteallocationAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_site, container, false);

        Initview(view);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", "69");


        CallWebservice.getWebservice(true, getActivity(), Request.Method.POST, IUrls.Site_allocation, hashMap, new VolleyResponseListener<Siteallocation>() {
            @Override
            public void onResponse(Siteallocation[] object, String message, String key) {

                if (object[0] instanceof Siteallocation) {
                    for (Siteallocation siteslist : object) {
                        siteallocationArrayList.add(siteslist);
                    }
                }
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                siteallocationAdapter = new SiteallocationAdapter(getActivity(), siteallocationArrayList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerSites.setLayoutManager(mLayoutManager);
                recyclerSites.setItemAnimator(new DefaultItemAnimator());
                recyclerSites.setAdapter(siteallocationAdapter);
            }

            @Override
            public void onError(String message) {
                Log.v("tag", message.toString());
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

        }, Siteallocation[].class);
        return view;
    }


    private void Initview(View view) {
        recyclerSites=(RecyclerView)view.findViewById(R.id.rv_sites);
        siteallocationArrayList=new ArrayList<>();

    }
}


