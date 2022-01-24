package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sferricha on 30/12/2016.
 */

public class Commande_Adapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Commande> commandeLists;
    private ArrayList<Commande> arrayList;


    public Commande_Adapter(Activity activity, List<Commande> commandeLists) {
        this.activity = activity;
        this.commandeLists = commandeLists;
        this.arrayList=new ArrayList<Commande>();
        this.arrayList.addAll(commandeLists);
    }

    @Override
    public int getCount() {
        return commandeLists.size();
    }

    @Override
    public Object getItem(int location) {
        return commandeLists.get(location);
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
            convertView = inflater.inflate(R.layout.commande_ad_ligne, null);


        TextView commande_code = (TextView) convertView.findViewById(R.id.cmdcodetv);
        TextView client_code = (TextView) convertView.findViewById(R.id.clientcodetv);
        TextView commande_date = (TextView) convertView.findViewById(R.id.cmddatetv);

        Commande commande = commandeLists.get(position);


        commande_code.setText("Commande code: "+commande.getCOMMANDE_CODE());
        client_code.setText("Client: "+commande.getCLIENT_CODE());
        commande_date.setText("Date commande: "+commande.getDATE_COMMANDE());

        return convertView;
    }


}
