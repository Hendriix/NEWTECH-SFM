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
import com.newtech.newtech_sfm.Metier.LivraisonLigne;
import com.newtech.newtech_sfm.Metier_Manager.ClientManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonLigneManager;
import com.newtech.newtech_sfm.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by TONPC on 07/04/2017.
 */

public class Commande_Livraison_Adapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Commande> commandeLists;

    private ArrayList<Commande> arrayList;
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private String tacheCode;


    public Commande_Livraison_Adapter(Activity activity, List<Commande> commandeLists) {
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
            convertView = inflater.inflate(R.layout.commande_ligne, null);

        TextView code_commande = (TextView) convertView.findViewById(R.id.code_commande);
        TextView client_nom = (TextView) convertView.findViewById(R.id.client_nom);
        TextView date_commande = (TextView) convertView.findViewById(R.id.date_commande);
        TextView quantite = (TextView) convertView.findViewById(R.id.quantite);
        TextView prix_commande = (TextView) convertView.findViewById(R.id.valeur_commande);
        TextView quantite_restante = (TextView) convertView.findViewById(R.id.quantite_restante);
        TextView valeur_restante = (TextView) convertView.findViewById(R.id.valeur_restante_commande);
        double quantite_livree = 0;
        double montant_ql = 0;

        double kg_commande = 0;
        double montant_net = 0;
        double kg_reste = 0;
        double montant_reste = 0;



        client_nom.setTextColor(Color.rgb(0, 191, 255));
        quantite.setTextColor(Color.rgb(0, 191, 255));
        prix_commande.setTextColor(Color.rgb(0, 191, 255));

        Commande commande = commandeLists.get(position);



        ClientManager client_manager= new ClientManager(convertView.getContext());
        Client client =client_manager.get(commande.getCLIENT_CODE());
        LivraisonLigneManager livraisonLigneManager= new LivraisonLigneManager(convertView.getContext());
        ArrayList<LivraisonLigne> livraisonLignes = livraisonLigneManager.getListByCmdCode(commande.getCOMMANDE_CODE());

        for(int i=0;i<livraisonLignes.size();i++){
            quantite_livree+=livraisonLignes.get(i).getKG_LIVREE();
            montant_ql+=livraisonLignes.get(i).getMONTANT_NET();
        }

        kg_commande = commande.getKG_COMMANDE();
        montant_net = commande.getMONTANT_NET();
        kg_reste = kg_commande - quantite_livree;
        montant_reste = getNumberRounded(montant_net - montant_ql);

        client_nom.setText(client.getCLIENT_NOM());
        code_commande.setText(commande.getCOMMANDE_CODE());
        date_commande.setText(commande.getDATE_COMMANDE());
        quantite.setText(String.valueOf((int) kg_commande)+":KG");
        prix_commande.setText("VALEUR :"+String.valueOf((float) montant_net));
        quantite_restante.setText("RESTE(Kg):"+String.valueOf((int) kg_reste));
        valeur_restante.setText("RESTE(MAD) :"+String.valueOf((float) montant_reste));

        if(commande.getCOMMANDESTATUT_CODE().equals("5")){
            Log.d(TAG, "getView: "+5);

            convertView.setBackgroundColor(Color.rgb(0,255,204));

        }else if(commande.getCOMMANDESTATUT_CODE().equals("4")){
            Log.d(TAG, "getView: "+4);

            convertView.setBackgroundColor(Color.rgb(255,255,90));

        }else if(commande.getCOMMANDESTATUT_CODE().equals("3")){
            Log.d(TAG, "getView: "+3);
            convertView.setBackgroundColor(Color.WHITE);

        }

        return convertView;
    }

    public void filter(String textfilter,Context context){
        ClientManager clientManager=new ClientManager(context);
        textfilter=textfilter.toLowerCase(Locale.getDefault());
        commandeLists.clear();
        if (textfilter.length() == 0) {
            commandeLists.addAll(arrayList);
        }
        else
        {
            for (Commande commande : arrayList)
            {

                if (commande.getCOMMANDE_CODE().toLowerCase(Locale.getDefault()).contains(textfilter))
                {
                    commandeLists.add(commande);
                }
            }
        }
        notifyDataSetChanged();

    }

    public double getNumberRounded(double nombre){

        double number = nombre;
        int decimalsToConsider = 2;
        BigDecimal bigDecimal = new BigDecimal(number);
        BigDecimal roundedWithScale = bigDecimal.setScale(decimalsToConsider, BigDecimal.ROUND_HALF_UP);
        //System.out.println("Rounded value with setting scale = "+roundedWithScale);


        return roundedWithScale.doubleValue();
    }
}
