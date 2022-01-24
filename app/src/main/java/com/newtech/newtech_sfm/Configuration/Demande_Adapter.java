package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.StockDemande;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by csaylani on 17/04/2018.
 */

public class Demande_Adapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<StockDemande> stockDemandes;
    private ArrayList<StockDemande> arrayList;


    public Demande_Adapter(Activity activity, List<StockDemande> stockDemandes) {
        this.activity = activity;
        this.stockDemandes = stockDemandes;
        this.arrayList=new ArrayList<StockDemande>();
        this.arrayList.addAll(stockDemandes);
    }


    @Override
    public int getCount() {
        return stockDemandes.size();
    }

    @Override
    public Object getItem(int position) {
        return stockDemandes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = inflater.inflate(R.layout.demande_ligne, null);


        ImageView imageView = (ImageView) view.findViewById(R.id.stockDemande_image);


        TextView titre1 = (TextView) view.findViewById(R.id.title1);
        TextView titre2 = (TextView) view.findViewById(R.id.title2);
        TextView titre3 = (TextView) view.findViewById(R.id.title3);

        StockDemande stockDemande = stockDemandes.get(position);

        if(stockDemande.getTYPE_CODE().equals("Chargement")){

            imageView.setImageResource(R.drawable.charge);

        }else if(stockDemande.getTYPE_CODE().equals("Dechargement")){

            imageView.setImageResource(R.drawable.decharge);

        }else{
            imageView.setImageResource(R.drawable.viewstock);

        }
        titre1.setText(stockDemande.getDEMANDE_CODE());
        titre2.setText(stockDemande.getSTATUT_CODE());
        titre3.setText(stockDemande.getDEMANDE_DATE());

        String  couleur = stockDemande.getSTATUT_CODE();

        Log.d("Demande Adapter", "couleur code "+couleur);

        if(couleur.equals("29"))
        {
            Log.d("Demande Adapter R", "couleur code "+couleur);
            view.setBackgroundColor(Color.rgb(255, 77, 77));

        }else if(couleur.equals("30")){
            Log.d("Demande Adapter Y", "couleur code "+couleur);
            view.setBackgroundColor(Color.rgb(255, 255, 0));

        }else if(couleur.equals("31")){
            Log.d("Demande Adapter O", "couleur code "+couleur);
            view.setBackgroundColor(Color.rgb(255, 102, 0));

        }else if(couleur.equals("32")){
            Log.d("Demande Adapter G", "couleur code "+couleur);
            view.setBackgroundColor(Color.rgb(26, 255, 163));

        }else if(couleur.equals("33")){
            Log.d("Demande Adapter M", "couleur code "+couleur);
            view.setBackgroundColor(Color.rgb(51, 204, 255));

        }else{
            Log.d("Demande Adapter W", "couleur code "+couleur);
            view.setBackgroundColor(Color.WHITE);
        }

        return view;


    }

    public void filter(String textfilter){
        textfilter=textfilter.toLowerCase(Locale.getDefault());
        stockDemandes.clear();
        if (textfilter.length() == 0) {
            stockDemandes.addAll(arrayList);
        }
        else
        {
            for (StockDemande stockDemande : arrayList)
            {
                if (stockDemande.getDEMANDE_DATE().toLowerCase(Locale.getDefault()).contains(textfilter) || stockDemande.getSTATUT_CODE().toLowerCase(Locale.getDefault()).contains(textfilter))
                {
                    stockDemandes.add(stockDemande);
                }
            }
        }
        notifyDataSetChanged();

    }
}
