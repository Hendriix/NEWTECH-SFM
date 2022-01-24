package com.newtech.newtech_sfm.Configuration;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.newtech.newtech_sfm.Metier.LogSync;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

public class SynchRecyclerAdapter extends RecyclerView.Adapter<SynchRecyclerAdapter.MyViewHolder> {

    private ArrayList<LogSync> logSyncs = new ArrayList<LogSync>();

    public SynchRecyclerAdapter(ArrayList<LogSync> logSyncs) {

        this.logSyncs = logSyncs;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.synchronisation_ligne,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.synchronisation_date.setText(logSyncs.get(position).getDate());
        holder.synchronisation_type.setText(logSyncs.get(position).getType());
        holder.synchronisation_message.setText(logSyncs.get(position).getMsg());
    }


    @Override
    public int getItemCount() {

        return logSyncs.size();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView synchronisation_date;
        TextView synchronisation_type;
        TextView synchronisation_message;


        public MyViewHolder(View itemView) {
            super(itemView);
            synchronisation_date = (TextView) itemView.findViewById(R.id.synchronisation_date);
            synchronisation_type = (TextView) itemView.findViewById(R.id.synchronisation_type);
            synchronisation_message = (TextView) itemView.findViewById(R.id.synchronisation_message);
        }
    }
}
