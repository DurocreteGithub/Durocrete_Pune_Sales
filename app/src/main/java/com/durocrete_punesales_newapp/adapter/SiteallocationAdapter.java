package com.durocrete_punesales_newapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.durocrete_punesales_newapp.R;

import java.util.ArrayList;
import java.util.List;

import com.durocrete_punesales_newapp.Utillity.MyPreferenceManager;
import com.durocrete_punesales_newapp.activity.MainActivity;
import com.durocrete_punesales_newapp.model.SiteDetailModel;
import com.durocrete_punesales_newapp.model.Siteallocation;
import com.durocrete_punesales_newapp.model.Sitesummary;

/**
 * Created by root on 27/6/17.
 */

public class SiteallocationAdapter extends RecyclerView.Adapter<SiteallocationAdapter.MyViewHolder> {

    private List<Siteallocation> siteallocationList;
    private Context context;
    MyPreferenceManager sharedpref;
    Sitesummary sitesummary;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvclientname;
        private TextView tvsitename;
        private LinearLayout row_layout;




        public MyViewHolder(View view) {
            super(view);
            tvclientname = (TextView) view.findViewById(R.id.txtClientName);
            tvsitename = (TextView) view.findViewById(R.id.txtInTime);
            row_layout = (LinearLayout) view.findViewById(R.id.row_layout);
            sharedpref = new MyPreferenceManager(context);
            sitesummary = new Sitesummary();

        }


    }

    public SiteallocationAdapter(FragmentActivity activity, ArrayList<Siteallocation> testlist) {
        this.siteallocationList = testlist;
        this.context = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R .layout.row_show_sides, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        Siteallocation siteallocation = siteallocationList.get(position);
        holder.tvclientname.setText(siteallocation.getCL_Name_var());
        holder.tvsitename.setText(siteallocation.getSITE_Name_var());
        SiteDetailModel siteDetailModel = new SiteDetailModel();

        if (sharedpref.getBooleanPreferences(MyPreferenceManager.done_bit) && sharedpref.getStringPreferences(MyPreferenceManager.Siteid).equals(siteallocationList.get(position).getSITE_Id()))
        {
            holder.row_layout.setBackgroundColor(Color.GREEN);
        }
        else
        {
            holder.row_layout.setBackgroundColor(Color.WHITE);
        }

        holder.row_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!sharedpref.getBooleanPreferences(MyPreferenceManager.done_bit) || sharedpref.getStringPreferences(MyPreferenceManager.Siteid).equals(siteallocationList.get(position).getSITE_Id())) {
                    Intent intent = new Intent(context, MainActivity.class);
                    sharedpref.setStringPreferences(MyPreferenceManager.Siteid, siteallocationList.get(position).getSITE_Id());
                    sharedpref.setStringPreferences(MyPreferenceManager.Site_name, siteallocationList.get(position).getSITE_Name_var());
                    context.startActivity(intent);
                    ((Activity) context).finish();

                } else {
                    Toast.makeText(context, "Please Check Out First", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }




    @Override
    public int getItemCount() {
        return siteallocationList.size();
    }


}
