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

import com.durocrete_punesales_newapp.model.Summary;

/**
 * Created by root on 27/6/17.
 */

public class Summaryadapter extends RecyclerView.Adapter<Summaryadapter.MyViewHolder> {

    private List<Summary> summarylist;
    private Context context;
    private int Alltotal = 0;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvclientname;
        private TextView tvintime;
        private TextView tvouttime;
        private TextView tvstatus;



        public MyViewHolder(View view) {
            super(view);
            tvclientname = (TextView) view.findViewById(R.id.txtClientName);
            tvintime = (TextView) view.findViewById(R.id.txtInTime);
            tvouttime = (TextView) view.findViewById(R.id.txtOutTime);
            tvstatus = (TextView) view.findViewById(R.id.txtStatus);

        }


    }

    public Summaryadapter(FragmentActivity activity, ArrayList<Summary> testlist) {
        this.summarylist = testlist;
        this.context = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_task_summary2, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        Summary summary = summarylist.get(position);

        holder.tvclientname.setText(summary.getSITE_Name_var());
        holder.tvintime.setText(summary.getIn_time());
        holder.tvouttime.setText(summary.getOut_time());
        holder.tvstatus.setText(summary.getResponse());
    }

    @Override
    public int getItemCount() {
        return summarylist.size();

    }


}
