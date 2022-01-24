package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Livraison;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TONPC on 11/07/2017.
 */

public class RLivraison_Adapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<Livraison> livraisonLists;
    private ArrayList<Livraison> arrayList;


    public RLivraison_Adapter(Activity activity, List<Livraison> livraisonLists) {
        this.activity = activity;
        this.livraisonLists = livraisonLists;
        this.arrayList=new ArrayList<Livraison>();
        this.arrayList.addAll(livraisonLists);
    }





    @Override
    public int getCount() {
        return livraisonLists.size();
    }

    @Override
    public Object getItem(int position) {
        return livraisonLists.get(position);
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
            convertView = inflater.inflate(R.layout.rlivraison_ligne, null);

        TextView facturenum =(TextView) convertView.findViewById(R.id.facturenum);
        TextView facturedate =(TextView) convertView.findViewById(R.id.facturedate);
        TextView factureclient =(TextView) convertView.findViewById(R.id.factureclient);
        TextView facturevendeur =(TextView) convertView.findViewById(R.id.facturevendeur);

        Livraison livraison = livraisonLists.get(position);

        facturenum.setText("N°Facture: "+livraison.getFACTURE_CODE());
        facturedate.setText("Date facture: "+livraison.getLIVRAISON_DATE());
        factureclient.setText("Client: "+livraison.getCLIENT_CODE());
        facturevendeur.setText("Vendeur: "+livraison.getVENDEUR_CODE());

        return convertView;
    }
}
