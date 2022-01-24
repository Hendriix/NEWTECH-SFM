package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.CommandePromotion;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TONPC on 14/07/2017.
 */

public class CommandePromotion_Adapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<CommandePromotion> commandePromotions;
    private ArrayList<CommandePromotion> arrayList;


    public CommandePromotion_Adapter(Activity activity, List<CommandePromotion> commandePromotions) {
        this.activity = activity;
        this.commandePromotions = commandePromotions;
        this.arrayList=new ArrayList<CommandePromotion>();
        this.arrayList.addAll(commandePromotions);
    }

    @Override
    public int getCount() {
        return commandePromotions.size();
    }

    @Override
    public Object getItem(int position) {
        return commandePromotions.get(position);
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
            convertView = inflater.inflate(R.layout.commandepromotion_ligne, null);

        TextView promo_code = (TextView) convertView.findViewById(R.id.promo_code);
        TextView commande_code = (TextView) convertView.findViewById(R.id.commande_code);
        TextView comandeligne_code = (TextView) convertView.findViewById(R.id.commandeligne_code);
        TextView montantnetavant = (TextView) convertView.findViewById(R.id.montantnetavant);
        TextView valeur_promo = (TextView) convertView.findViewById(R.id.valeur_promo);
        TextView montantnetapres = (TextView) convertView.findViewById(R.id.montantnetapres);
        TextView promo_niveau = (TextView) convertView.findViewById(R.id.promoniveau);
        TextView mode_calcul = (TextView) convertView.findViewById(R.id.modecalc);
        TextView appliquer_sur = (TextView) convertView.findViewById(R.id.appliquersur);

        CommandePromotion commandePromotion = commandePromotions.get(position);

        promo_code.setText("Promotion code: "+commandePromotion.getPROMO_CODE());
        commande_code.setText("Commande code: "+commandePromotion.getCOMMANDE_CODE());
        comandeligne_code.setText("Commandeligne code: "+commandePromotion.getCOMMANDELIGNE_CODE());
        montantnetavant.setText("Net avant: "+commandePromotion.getMONTANT_NET_AV());
        valeur_promo.setText("Valeur promo: "+commandePromotion.getVALEUR_PROMO());
        montantnetapres.setText("Net apres: "+commandePromotion.getMONTANT_NET_AP());
        promo_niveau.setText("Promotion niveau: "+commandePromotion.getPROMO_NIVEAU());
        mode_calcul.setText("Mode calcule: "+commandePromotion.getPROMO_MODECALCUL());
        appliquer_sur.setText("Appliquer sur: "+commandePromotion.getPROMO_APPLIQUESUR());

        return convertView;
    }
}
