package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.Credit;
import com.newtech.newtech_sfm.Metier_Manager.ClientManager;
import com.newtech.newtech_sfm.R;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by csaylani on 17/04/2018.
 */

public class Credit_Adapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<Credit> credits;
    private ArrayList<Credit> arrayList;
    SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");


    public Credit_Adapter(Activity activity, List<Credit> credits) {
        this.activity = activity;
        this.credits = credits;
        this.arrayList=new ArrayList<Credit>();
        this.arrayList.addAll(credits);
    }


    @Override
    public int getCount() {
        return credits.size();
    }

    @Override
    public Object getItem(int position) {
        return credits.get(position);
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
            view = inflater.inflate(R.layout.credit_ligne, null);

        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        TextView client_nom = (TextView) view.findViewById(R.id.client_nom);
        TextView code_commande = (TextView) view.findViewById(R.id.code_commande);
        TextView date_credit = (TextView) view.findViewById(R.id.date_credit);
        TextView date_echeance = (TextView) view.findViewById(R.id.date_echeance);
        TextView valeur_credit = (TextView) view.findViewById(R.id.valeur_credit);
        TextView valeur_payee = (TextView) view.findViewById(R.id.valeur_payee);
        TextView reste_credit = (TextView) view.findViewById(R.id.reste_credit);

        double valeur=0;
        double paye=0;
        double reste=0;
        String date_debut="";
        String date_fin="";

        client_nom.setTextColor(Color.rgb(0, 191, 255));
        valeur_credit.setTextColor(Color.rgb(0, 191, 255));
        valeur_payee.setTextColor(Color.rgb(0, 191, 255));
        reste_credit.setTextColor(Color.rgb(0, 191, 255));

        Credit credit = credits.get(position);
        ClientManager client_manager= new ClientManager(view.getContext());
        Client client =client_manager.get(credit.getCLIENT_CODE());

        valeur=getNumberRounded(credit.getMONTANT_CREDIT());
        paye=getNumberRounded(credit.getMONTANT_ENCAISSE());
        reste=getNumberRounded(credit.getRESTE());
        date_debut = credit.getCREDIT_DATE();
        date_fin = credit.getCREDIT_ECHEANCE();


        client_nom.setText(client.getCLIENT_NOM());
        code_commande.setText(credit.getCOMMANDE_CODE());
        date_credit.setText("DATE: "+date_debut);
        date_echeance.setText("ECHEANCE: "+date_fin);
        valeur_credit.setText("VALEUR: "+String.valueOf(valeur));
        valeur_payee.setText("PAYE: "+String.valueOf(paye));
        reste_credit.setText("RESTE: "+String.valueOf(reste));

        return view;

    }

    public void filter(String textfilter){
        textfilter=textfilter.toLowerCase(Locale.getDefault());
        credits.clear();
        if (textfilter.length() == 0) {
            credits.addAll(arrayList);
        }
        else
        {
            for (Credit credit : arrayList)
            {
                if (credit.getCLIENT_CODE().toLowerCase(Locale.getDefault()).contains(textfilter) || credit.getSTATUT_CODE().toLowerCase(Locale.getDefault()).contains(textfilter))
                {
                    credits.add(credit);
                }
            }
        }
        notifyDataSetChanged();

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
