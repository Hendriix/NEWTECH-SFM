package com.newtech.newtech_sfm.DialogTournee;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Tournee;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

public class DialogTourneeAdapter extends BaseAdapter {

    private ArrayList<Tournee> tourneeArrayList;
    private Activity activity;
    private LayoutInflater inflater;
    private static final String TAG = DialogTourneeAdapter.class.getName();

    public DialogTourneeAdapter(ArrayList<Tournee> tourneeArrayList, Activity activity) {
        this.tourneeArrayList = tourneeArrayList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return tourneeArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return tourneeArrayList.get(position);
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

        circuit_nom.setText(tourneeArrayList.get(position).getTOURNEE_NOM());

        return convertView;
    }
}
