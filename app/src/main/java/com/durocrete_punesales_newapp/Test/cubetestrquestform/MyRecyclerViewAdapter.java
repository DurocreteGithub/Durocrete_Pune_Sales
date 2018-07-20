package com.durocrete_punesales_newapp.Test.cubetestrquestform;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.durocrete_punesales_newapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 27/10/17.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Cubeset> Allcubelist = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private int no;

    String test_schedule;
    int test_schedule_no;


    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, int i) {
        this.mInflater = LayoutInflater.from(context);
        this.no = i;
        initialiselist();
    }

    private void initialiselist() {
        for (int i = 0; i < no; i++) {
            Allcubelist.add(new Cubeset());
        }
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.horizontal_recycleview_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public List<Cubeset> Allcubelist() {
        return this.Allcubelist;
    }


    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {



        Log.d("tag1", "" + position);
        final int position1 = position + 1;

        holder.row_layout.setTag(position);
        holder.setno.setText("" + position1 + "/" + "" + no);

        holder.txtInTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                test_schedule_no = position;

                if (position == 3) {
                    holder.linearotherinput.setVisibility(View.VISIBLE);
                    test_schedule = holder.txtother.getText().toString().trim();
                } else {
                    holder.linearotherinput.setVisibility(View.GONE);
                    test_schedule = parent.getItemAtPosition(position).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (test_schedule_no == 0) {
                    Toast.makeText(v.getContext(), "Please Select Test_schedule", Toast.LENGTH_SHORT).show();

                } else if (test_schedule_no == 3 && holder.txtother.getText().toString().trim().length() == 0) {
                    holder.txtother.setError("Please Enter Input");
                    holder.txtother.requestFocus();

                } else if (holder.idmark1.getText().toString().trim().length() == 0) {
                    holder.idmark1.setError("Please Enter IdMark1");
                    holder.idmark1.requestFocus();

                } else if (holder.idmark2.getText().toString().trim().length() == 0) {
                    holder.idmark2.setError("Please Enter IdMark2");
                    holder.idmark2.requestFocus();

                } else if (holder.idmark3.getText().toString().trim().length() == 0) {
                    holder.idmark3.setError("Please Enter IdMark3");
                    holder.idmark3.requestFocus();

                } else {

                    Cubeset cubeset = new Cubeset();
                    cubeset.setSchedule(test_schedule);
                    cubeset.setId_no("" + position);
                    cubeset.setIdmark1(holder.idmark1.getText().toString().trim());
                    cubeset.setIdmark2(holder.idmark2.getText().toString().trim());
                    cubeset.setIdmark3(holder.idmark3.getText().toString().trim());
                    Allcubelist.set(position, cubeset);
                    Toast.makeText(v.getContext(), "Saved Succesfully", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    // total number of rows
    @Override
    public int getItemCount() {
        return no;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView setno;
        private Spinner txtInTime;
        private EditText txtother;
        private EditText idmark1;
        private EditText idmark2;
        private EditText idmark3;
        private Button btnSave;
        private LinearLayout linearotherinput, row_layout;


        public ViewHolder(View itemView) {
            super(itemView);
            setno = (TextView) itemView.findViewById(R.id.setno);
            txtInTime = (Spinner) itemView.findViewById(R.id.txtInTime);
            txtother = (EditText) itemView.findViewById(R.id.txtother);
            idmark1 = (EditText) itemView.findViewById(R.id.idmark1);
            idmark2 = (EditText) itemView.findViewById(R.id.idmark2);
            idmark3 = (EditText) itemView.findViewById(R.id.idmark3);
            btnSave = (Button) itemView.findViewById(R.id.save);
            linearotherinput = (LinearLayout) itemView.findViewById(R.id.linearotherinput);
            row_layout = (LinearLayout) itemView.findViewById(R.id.row_layout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

