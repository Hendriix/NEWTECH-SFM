package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Promotionarticle;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TONPC on 12/07/2017.
 */

public class PromotionArticle_Adapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<Promotionarticle> promotionarticles;
    private ArrayList<Promotionarticle> arrayList;


    public PromotionArticle_Adapter(Activity activity, List<Promotionarticle> promotionarticles) {
        this.activity = activity;
        this.promotionarticles = promotionarticles;
        this.arrayList=new ArrayList<Promotionarticle>();
        this.arrayList.addAll(promotionarticles);
    }

    @Override
    public int getCount() {
        return promotionarticles.size();
    }

    @Override
    public Object getItem(int position) {
        return promotionarticles.get(position);
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
            convertView = inflater.inflate(R.layout.promotionarticle_ligne, null);


        TextView promo_code = (TextView) convertView.findViewById(R.id.promocode);
        TextView categ_code = (TextView) convertView.findViewById(R.id.categcode);
        TextView type_code = (TextView) convertView.findViewById(R.id.typecode);
        TextView valeur_ar = (TextView) convertView.findViewById(R.id.valeurar);

        Promotionarticle promotionarticle = promotionarticles.get(position);

        promo_code.setText("Promotion code: "+promotionarticle.getPROMO_CODE());
        categ_code.setText("Categorie code: "+promotionarticle.getCATEGORIE_CODE());
        type_code.setText("Type code: "+promotionarticle.getTYPE_CODE());
        valeur_ar.setText("Valeur AR: "+promotionarticle.getVALEUR_AR());

        return convertView;
    }
}
