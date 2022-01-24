package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Tournee;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sferricha on 25/10/2016.
 */

public class Tournee_Adapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Tournee> tourneeLists;
    private ArrayList<Tournee> arrayList;

    public Tournee_Adapter(Activity activity, int simple_spinner_item, List<Tournee> tourneeLists) {
        this.activity = activity;
        this.tourneeLists = tourneeLists;
        this.arrayList=new ArrayList<Tournee>();
        this.arrayList.addAll(tourneeLists);
    }

    @Override
    public int getCount() {
        return tourneeLists.size();
    }

    @Override
    public Object getItem(int location) {
        return tourneeLists.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.simple_spinner_item, null);

        //TextView label=new TextView(activity);

        TextView label = (TextView) convertView.findViewById(R.id.spinnertext1);

        Tournee tournee = tourneeLists.get(position);
        label.setText(tournee.getTOURNEE_NOM());

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.simple_spinner_dropdown_item, null);

        //TextView label=new TextView(activity);

        CheckedTextView label = (CheckedTextView) convertView.findViewById(R.id.spinnertext1);

        Tournee tournee = tourneeLists.get(position);
        label.setText(tournee.getTOURNEE_NOM());

        return label;
    }


}
