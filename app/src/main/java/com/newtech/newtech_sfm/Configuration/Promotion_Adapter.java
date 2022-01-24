package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Promotion;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TONPC on 12/07/2017.
 */

public class Promotion_Adapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<Promotion> promotions;
    private ArrayList<Promotion> arrayList;


    public Promotion_Adapter(Activity activity, List<Promotion> promotions) {
        this.activity = activity;
        this.promotions = promotions;
        this.arrayList=new ArrayList<Promotion>();
        this.arrayList.addAll(promotions);
    }

    @Override
    public int getCount() {
        return promotions.size();
    }

    @Override
    public Object getItem(int position) {
        return promotions.get(position);
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
            convertView = inflater.inflate(R.layout.promotions_ligne, null);

        TextView promo_nom = (TextView) convertView.findViewById(R.id.promonom);
        TextView promo_base = (TextView) convertView.findViewById(R.id.promosbase);
        TextView promo_mode_calcule = (TextView) convertView.findViewById(R.id.promosmc);
        TextView promo_niveau = (TextView) convertView.findViewById(R.id.promosniveau);
        TextView promo_appliquee_sur = (TextView) convertView.findViewById(R.id.promoapplsur);
        TextView promo_date_debut = (TextView) convertView.findViewById(R.id.promosdatede);
        TextView promo_date_fin = (TextView) convertView.findViewById(R.id.promosdatefin);

        Promotion promotion = promotions.get(position);


        promo_nom.setText("Libelle: "+promotion.getPROMO_NOM());
        promo_base.setText("Base: "+promotion.getPROMO_BASE());
        promo_mode_calcule.setText("Mode calcul: "+promotion.getPROMO_MODECALCUL());
        promo_niveau.setText("Niveau: "+promotion.getPROMO_NIVEAU());
        promo_appliquee_sur.setText("Aplliquée sur: "+promotion.getPROMO_APPLIQUESUR());
        promo_date_debut.setText("Date début: "+promotion.getDATE_DEBUT());
        promo_date_fin.setText("Date fin: "+promotion.getDATE_FIN());

        return convertView;
    }
}
