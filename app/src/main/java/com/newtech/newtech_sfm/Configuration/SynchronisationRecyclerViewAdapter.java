package com.newtech.newtech_sfm.Configuration;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.newtech.newtech_sfm.Metier.LogSync;
import com.newtech.newtech_sfm.R;

import java.util.List;


/**
 * Created by User on 2/12/2018.
 */

public class SynchronisationRecyclerViewAdapter extends RecyclerView.Adapter<SynchronisationRecyclerViewAdapter.ViewHolder> {



    private List<LogSync> logSyncs;
    private static final String TAG = SQLiteHandler.class.getSimpleName();


    public SynchronisationRecyclerViewAdapter(List<LogSync> logSyncs) {
        Log.d(TAG, "SynchronisationRecyclerViewAdapter: okok");
        this.logSyncs = logSyncs;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.synchronisation_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Log.d(TAG, "onBindViewHolder: okkok ");
        
        holder.synchronisation_date.setText(String.valueOf(logSyncs.get(position).getDate()));
        holder.synchronisation_message.setText(String.valueOf(logSyncs.get(position).getMsg()));
        holder.synchronisation_type.setText(String.valueOf(logSyncs.get(position).getType()));

    }


    @Override
    public int getItemCount() {
        return logSyncs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView synchronisation_date;
        TextView synchronisation_message;
        TextView synchronisation_type;

        public ViewHolder(View itemView) {
            super(itemView);
            synchronisation_date =(TextView) itemView.findViewById(R.id.date_synchronisation);
            synchronisation_message =(TextView) itemView.findViewById(R.id.message_syncronisation);
            synchronisation_type =(TextView) itemView.findViewById(R.id.type_synchronisation);
        }
    }

}
