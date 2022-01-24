package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.StockDemandeLigne;
import com.newtech.newtech_sfm.Metier.Unite;
import com.newtech.newtech_sfm.Metier_Manager.UniteManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by csaylani on 26/04/2018.
 */

public class ChargementPopUp_Adapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<StockDemandeLigne> stockDemandeLignes;
    private ArrayList<StockDemandeLigne> arrayList;
    Context context;


    public ChargementPopUp_Adapter(Activity activity, List<StockDemandeLigne> stockDemandeLignes, Context context) {
        this.activity = activity;
        this.stockDemandeLignes = stockDemandeLignes;
        this.arrayList=new ArrayList<StockDemandeLigne>();
        this.arrayList.addAll(stockDemandeLignes);
        this.context = context;
    }



    @Override
    public int getCount() {
        return stockDemandeLignes.size();
    }

    @Override
    public StockDemandeLigne getItem(int i) {
        return stockDemandeLignes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {


        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.chargementpopup_ligne, null);


        ImageView article_image = (ImageView) convertView.findViewById(R.id.image_chargement_article);
        TextView article_code = (TextView) convertView.findViewById(R.id.chargement_article_code);
        TextView article_unite = (TextView) convertView.findViewById(R.id.chargement_unite);
        TextView quantite_commandee = (TextView) convertView.findViewById(R.id.chargement_commandee);
        TextView quantite_approuvee = (TextView) convertView.findViewById(R.id.chargement_approuvee);
        TextView quantite_livree = (TextView) convertView.findViewById(R.id.chargement_livree);
        TextView quantite_receptionnee= (TextView) convertView.findViewById(R.id.chargement_receptionee);

        if(getImageId(convertView.getContext(), getItem(i).getARTICLE_CODE().toLowerCase())>0){
            article_image.setImageResource(getImageId(convertView.getContext(), getItem(i).getARTICLE_CODE().toLowerCase()));

        }else{
            article_image.setImageResource(getImageId(convertView.getContext(),"bouteille_inconnu2"));

        }
        article_code.setText(getItem(i).getARTICLE_CODE());
        UniteManager uniteManager = new UniteManager(context);
        Unite unite = uniteManager.get(getItem(i).getUNITE_CODE());

        article_unite.setText(unite.getUNITE_NOM());
        quantite_commandee.setText(String.valueOf((int) getItem(i).getQTE_COMMANDEE()));
        quantite_approuvee.setText(String.valueOf((int) getItem(i).getQTE_APPROUVEE()));
        quantite_livree.setText(String.valueOf((int) getItem(i).getQTE_LIVREE()));
        quantite_receptionnee.setText(String.valueOf((int) getItem(i).getQTE_RECEPTIONEE()));

        return convertView;
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }
}
