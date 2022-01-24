package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.LogSync;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by TONPC on 04/08/2017.
 */

public class SynchronisationAdapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<LogSync> logSyncs;
    private ArrayList<LogSync> arrayList;


    public SynchronisationAdapter(Activity activity, List<LogSync> logSyncs) {
        this.activity = activity;
        this.logSyncs = logSyncs;
        this.arrayList=new ArrayList<LogSync>();
        this.arrayList.addAll(logSyncs);
    }


    @Override
    public int getCount() {
        return logSyncs.size();
    }

    @Override
    public Object getItem(int position) {
        return logSyncs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.synchronisation_item, null);

        TextView synchronisation_date = (TextView) convertView.findViewById(R.id.date_synchronisation);
        TextView synchronisation_type = (TextView) convertView.findViewById(R.id.type_synchronisation);
        TextView synchronisation_message = (TextView) convertView.findViewById(R.id.message_syncronisation);

        LogSync logSync = logSyncs.get(position);


        synchronisation_date.setText(logSync.getDate());
        synchronisation_type.setText(logSync.getType());
        synchronisation_message.setText(logSync.getMsg());

        if(logSync.getSuccess() == 0){
            convertView.setBackgroundColor(Color.rgb(255, 51, 51));
        }else{
            convertView.setBackgroundColor(Color.rgb(255, 255, 255));
        }

        return convertView;
    }


    public void filter(String textfilter){
        textfilter=textfilter.toLowerCase(Locale.getDefault());
        logSyncs.clear();
        if (textfilter.length() == 0) {
            logSyncs.addAll(arrayList);
        }
        else
        {
            for (LogSync logSync : arrayList)
            {
                if (logSync.getType().toLowerCase(Locale.getDefault()).contains(textfilter))
                {
                    logSyncs.add(logSync);
                }
            }
        }
        notifyDataSetChanged();

    }
}
