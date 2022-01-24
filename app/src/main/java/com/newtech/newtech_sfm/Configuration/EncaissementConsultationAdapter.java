package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newtech.newtech_sfm.Activity.EncaissementActivity;
import com.newtech.newtech_sfm.Metier.Encaissement;
import com.newtech.newtech_sfm.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TONPC on 04/08/2017.
 */

public class EncaissementConsultationAdapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<Encaissement> encaissements;
    private ArrayList<Encaissement> arrayList;


    public EncaissementConsultationAdapter(Activity activity, List<Encaissement> encaissements) {
        this.activity = activity;
        this.encaissements = encaissements;
        this.arrayList=new ArrayList<Encaissement>();
        this.arrayList.addAll(encaissements);
    }

    @Override
    public int getCount() {
        return encaissements.size();
    }

    @Override
    public Object getItem(int position) {
        return encaissements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.encaissement_ligne, null);

        double montant =0;
        //TextView encaissement_code = (TextView) convertView.findViewById(R.id.encaissement_code);
        //TextView commande_code = (TextView) convertView.findViewById(R.id.commande_code);
        TextView encaissement_type = (TextView) convertView.findViewById(R.id.encaissement_type);
        TextView encaissement_montant = (TextView) convertView.findViewById(R.id.encaissement_montant);

        ImageView delete_encaissement = (ImageView) convertView.findViewById(R.id.delete_encaissement);



        final Encaissement encaissement = encaissements.get(position);

        montant=getNumberRounded(encaissement.getMONTANT());

        //encaissement_code.setText(encaissement.getENCAISSEMENT_CODE());
        //commande_code.setText(encaissement.getCOMMANDE_CODE());
        encaissement_type.setText(encaissement.getTYPE_CODE());
        encaissement_montant.setText(String.valueOf(montant)+" MAD");
        return convertView;
    }

    private void getSumEncaissement(List<Encaissement> encaissements){

        double somme = 0;
        for(int i=0; i<encaissements.size();i++){
            somme+=encaissements.get(i).getMONTANT();
        }
        EncaissementActivity.payecommande = somme;
        EncaissementActivity.restecommande = EncaissementActivity.commande.getVALEUR_COMMANDE() - somme;

        Log.d("encaissement paye", "getSumEncaissement: "+EncaissementActivity.payecommande);
        Log.d("encaissement reste", "getSumEncaissement: "+EncaissementActivity.restecommande);
    }

    private double getNumberRounded(double nombre){

        double number = nombre;
        int decimalsToConsider = 2;
        BigDecimal bigDecimal = new BigDecimal(number);
        BigDecimal roundedWithScale = bigDecimal.setScale(decimalsToConsider, BigDecimal.ROUND_HALF_UP);
        //System.out.println("Rounded value with setting scale = "+roundedWithScale);


        return roundedWithScale.doubleValue();
    }


}
