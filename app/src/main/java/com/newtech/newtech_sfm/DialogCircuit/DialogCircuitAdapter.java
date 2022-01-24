package com.newtech.newtech_sfm.DialogCircuit;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Circuit;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

public class DialogCircuitAdapter extends BaseAdapter {

    private ArrayList<Circuit> circuitArrayList;
    private Activity activity;
    private LayoutInflater inflater;
    private static final String TAG = DialogCircuitAdapter.class.getName();

    public DialogCircuitAdapter(ArrayList<Circuit> circuitArrayList,Activity activity) {
        this.circuitArrayList = circuitArrayList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return circuitArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return circuitArrayList.get(position);
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

        circuit_nom.setText(circuitArrayList.get(position).getCIRCUIT_NOM());

        return convertView;
    }
}
