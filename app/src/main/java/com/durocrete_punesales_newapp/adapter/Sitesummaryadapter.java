package com.durocrete_punesales_newapp.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.durocrete_punesales_newapp.R;

import java.util.ArrayList;
import java.util.List;

import com.durocrete_punesales_newapp.Utillity.MyPreferenceManager;
import com.durocrete_punesales_newapp.model.Sitesummary;

/**
 * Created by root on 27/6/17.
 */

public class Sitesummaryadapter extends RecyclerView.Adapter<Sitesummaryadapter.MyViewHolder> {

    private List<Sitesummary> summarylist;
    private Context context;
    private int Alltotal = 0;
    MyPreferenceManager sharedpref;

    public Sitesummaryadapter(FragmentActivity activity, ArrayList<Sitesummary> sitesummarylist) {
        this.summarylist=sitesummarylist;
        this.context=activity;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvdate;
        private TextView tvintime;
        private TextView tvouttime;
        private TextView tvstatus;


        public MyViewHolder(View view) {
            super(view);
            tvdate = (TextView) view.findViewById(R.id.txtdate);
            tvintime = (TextView) view.findViewById(R.id.txtInTime);
            tvouttime = (TextView) view.findViewById(R.id.txtOutTime);
            tvstatus = (TextView) view.findViewById(R.id.txtstatus);
            sharedpref= new MyPreferenceManager(context);

        }


    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_task_summary1, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        Sitesummary summary = summarylist.get(position);

        holder.tvdate.setText(summary.getDate());
        holder.tvintime.setText(summary.getIn_time());
        sharedpref.setStringPreferences(MyPreferenceManager.check_In_time,summary.getIn_time());
        holder.tvouttime.setText(summary.getOut_time());
        sharedpref.setStringPreferences(MyPreferenceManager.check_out_time,summary.getOut_time());
        holder.tvstatus.setText(summary.getResponse());

    }

    @Override
    public int getItemCount() {
        return summarylist.size();

    }


}
