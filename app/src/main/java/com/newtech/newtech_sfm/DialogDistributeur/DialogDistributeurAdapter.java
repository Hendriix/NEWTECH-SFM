package com.newtech.newtech_sfm.DialogDistributeur;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Distributeur;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

public class DialogDistributeurAdapter extends BaseAdapter {

    private ArrayList<Distributeur> distributeurArrayList;
    private Activity activity;
    private LayoutInflater inflater;
    private static final String TAG = DialogDistributeurAdapter.class.getName();

    public DialogDistributeurAdapter(ArrayList<Distributeur> distributeurArrayList, Activity activity) {
        this.distributeurArrayList = distributeurArrayList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return distributeurArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return distributeurArrayList.get(position);
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
            convertView = inflater.inflate(R.layout.circuit_ligne, null);

        TextView circuit_nom = convertView.findViewById(R.id.cnom_tv);

        circuit_nom.setText(distributeurArrayList.get(position).getDISTRIBUTEUR_NOM());

        return convertView;
    }
}
