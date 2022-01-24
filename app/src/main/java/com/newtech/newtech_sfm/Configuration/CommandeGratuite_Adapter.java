package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.CommandeGratuite;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TONPC on 14/07/2017.
 */

public class CommandeGratuite_Adapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<CommandeGratuite> commandeGratuites;
    private ArrayList<CommandeGratuite> arrayList;


    public CommandeGratuite_Adapter(Activity activity, List<CommandeGratuite> commandeGratuites) {
        this.activity = activity;
        this.commandeGratuites = commandeGratuites;
        this.arrayList=new ArrayList<CommandeGratuite>();
        this.arrayList.addAll(commandeGratuites);
    }

    @Override
    public int getCount() {
        return commandeGratuites.size();
    }

    @Override
    public Object getItem(int position) {
        return commandeGratuites.get(position);
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
            convertView = inflater.inflate(R.layout.commandegratuite_ligne, null);

        TextView gratuite_code = (TextView) convertView.findViewById(R.id.gratuite_code);
        TextView commande_code = (TextView) convertView.findViewById(R.id.commande_code);
        TextView promo_code = (TextView) convertView.findViewById(R.id.promo_code);
        TextView article_code = (TextView) convertView.findViewById(R.id.article_code);
        TextView gratuite_quantite = (TextView) convertView.findViewById(R.id.gratuite_quantite);

        CommandeGratuite commandeGratuite = commandeGratuites.get(position);

        gratuite_code.setText("Gratuite code: "+commandeGratuite.getGRATUITE_CODE());
        commande_code.setText("Commande code: "+commandeGratuite.getCOMMANDE_CODE());
        promo_code.setText("Promotion code: "+commandeGratuite.getPROMO_CODE());
        article_code.setText("Article code: "+commandeGratuite.getARTICLE_CODE());
        gratuite_quantite.setText("Gratuite quantite: "+commandeGratuite.getQUANTITE());


        return convertView;
    }
}
