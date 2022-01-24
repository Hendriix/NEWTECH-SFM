package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.Encaissement;
import com.newtech.newtech_sfm.Metier_Manager.ClientManager;
import com.newtech.newtech_sfm.Metier_Manager.EncaissementManager;
import com.newtech.newtech_sfm.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TONPC on 22/08/2017.
 */

public class Commande_Encaissement_Adapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<Commande> commandeLists;

    private ArrayList<Commande> arrayList;
    private static final String TAG = SQLiteHandler.class.getSimpleName();


    public Commande_Encaissement_Adapter(Activity activity, List<Commande> commandeLists) {
        this.activity = activity;
        this.commandeLists = commandeLists;
        this.arrayList=new ArrayList<Commande>();
        this.arrayList.addAll(commandeLists);
    }


    @Override
    public int getCount() {
        return commandeLists.size();
    }

    @Override
    public Object getItem(int position) {
        return commandeLists.get(position);
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
            convertView = inflater.inflate(R.layout.commande_encaissement_ligne, null);

        TextView code_commande = (TextView) convertView.findViewById(R.id.code_commande);
        TextView client_nom = (TextView) convertView.findViewById(R.id.client_nom);
        TextView date_commande = (TextView) convertView.findViewById(R.id.date_commande);
        TextView quantite = (TextView) convertView.findViewById(R.id.quantite);
        TextView prix_commande = (TextView) convertView.findViewById(R.id.prix_commande);
        TextView reste_commande = (TextView) convertView.findViewById(R.id.reste_commande);

        client_nom.setTextColor(Color.rgb(0, 191, 255));
        quantite.setTextColor(Color.rgb(0, 191, 255));
        prix_commande.setTextColor(Color.rgb(0, 191, 255));

        double valeur=0;
        double reste=0;

        Commande commande = commandeLists.get(position);

        ClientManager client_manager= new ClientManager(convertView.getContext());
        Client client =client_manager.get(commande.getCLIENT_CODE());

        EncaissementManager encaissementManager = new EncaissementManager(convertView.getContext());

        ArrayList<Encaissement> encaissements = new ArrayList<>();
        //encaissements = encaissementManager.getListByCommandeCode(commande.getCOMMANDE_CODE());
        encaissements = encaissementManager.getListByCmdCode(commande.getCOMMANDE_CODE());

        Log.d("encaissementAdapter", "getView: "+commande.getCOMMANDE_CODE());
        Log.d("encaissementAdapter", "getView: "+encaissements.toString());

        double sommeEncaissement = getSumEncaissement(encaissements);
        double resteEncaissement = commande.getMONTANT_NET()-sommeEncaissement;

        Log.d("encaissementAdapter", "getView: "+sommeEncaissement);
        Log.d("encaissementAdapter", "getView: "+resteEncaissement);

        valeur=getNumberRounded(commande.getMONTANT_NET());
        reste=getNumberRounded(commande.getMONTANT_NET()-sommeEncaissement);

        client_nom.setText(client.getCLIENT_NOM());
        code_commande.setText(commande.getCOMMANDE_CODE());
        date_commande.setText(commande.getDATE_COMMANDE());
        quantite.setText(String.valueOf((int) commande.getKG_COMMANDE())+":KG");
        prix_commande.setText("PRIX :"+String.valueOf(valeur));
        reste_commande.setText("RESTE :"+String.valueOf(reste));


        return convertView;
    }

    private double getSumEncaissement(ArrayList<Encaissement> encaissements){
        double somme = 0;
        for(int i=0; i<encaissements.size();i++){
            somme+=encaissements.get(i).getMONTANT();
        }
        return somme;
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
