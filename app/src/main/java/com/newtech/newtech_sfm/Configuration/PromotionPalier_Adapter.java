package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Promotionpalier;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TONPC on 12/07/2017.
 */

public class PromotionPalier_Adapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Promotionpalier> promotionpaliers;
    private ArrayList<Promotionpalier> arrayList;


    public PromotionPalier_Adapter(Activity activity, List<Promotionpalier> promotionpaliers) {
        this.activity = activity;
        this.promotionpaliers = promotionpaliers;
        this.arrayList=new ArrayList<Promotionpalier>();
        this.arrayList.addAll(promotionpaliers);
    }


    @Override
    public int getCount() {
        return promotionpaliers.size();
    }

    @Override
    public Object getItem(int position) {
        return promotionpaliers.get(position);
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
            convertView = inflater.inflate(R.layout.promotionpalier_ligne, null);

        TextView promo_code = (TextView) convertView.findViewById(R.id.promocode);
        TextView promo_base = (TextView) convertView.findViewById(R.id.promobase);
        TextView val_promo_base = (TextView) convertView.findViewById(R.id.valpromobase);
        TextView promo_gratuite = (TextView) convertView.findViewById(R.id.promogratuite);
        TextView promo_date = (TextView) convertView.findViewById(R.id.promodate);

        Promotionpalier promotionPalier = promotionpaliers.get(position);

        promo_code.setText("Promotion code: "+promotionPalier.getPROMO_CODE());
        promo_base.setText("Promotion base: "+promotionPalier.getPROMO_BASE());
        val_promo_base.setText("Valeur base: "+promotionPalier.getVALEUR_PBASE());
        promo_gratuite.setText("Promotion gratuite: "+promotionPalier.getPROMO_GRATUITE());
        promo_date.setText("Promotion date: "+promotionPalier.getDATE_CREATION());

        return convertView;
    }
}
