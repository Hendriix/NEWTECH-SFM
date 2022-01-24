package com.newtech.newtech_sfm.DialogUtilisateur;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.User;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

public class DialogUtilisateurAdapter extends BaseAdapter {

    private ArrayList<User> utilisateurArrayList;
    private Activity activity;
    private LayoutInflater inflater;
    private static final String TAG = DialogUtilisateurAdapter.class.getName();

    public DialogUtilisateurAdapter(ArrayList<User> utilisateurArrayList, Activity activity) {
        this.utilisateurArrayList = utilisateurArrayList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return utilisateurArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return utilisateurArrayList.get(position);
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

        circuit_nom.setText(utilisateurArrayList.get(position).getUTILISATEUR_NOM());

        return convertView;
    }
}
