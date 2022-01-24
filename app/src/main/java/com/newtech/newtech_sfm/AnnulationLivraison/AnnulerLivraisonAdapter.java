package com.newtech.newtech_sfm.AnnulationLivraison;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.Livraison;
import com.newtech.newtech_sfm.Metier.Tournee;
import com.newtech.newtech_sfm.Metier_Manager.ClientManager;
import com.newtech.newtech_sfm.Metier_Manager.TourneeManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

public class AnnulerLivraisonAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Livraison> livraisonLists;
    private ArrayList<Livraison> arrayList;
    ClientManager clientManager;
    TourneeManager tourneeManager;


    public AnnulerLivraisonAdapter(Activity activity, List<Livraison> livraisonLists) {
        this.activity = activity;
        this.livraisonLists = livraisonLists;
        this.arrayList=new ArrayList<Livraison>();
        this.arrayList.addAll(livraisonLists);
        clientManager = new ClientManager(activity.getApplicationContext());
        tourneeManager = new TourneeManager(activity.getApplicationContext());
    }

    @Override
    public int getCount() {
        return livraisonLists.size();
    }

    @Override
    public Object getItem(int location) {
        return livraisonLists.get(location);
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
            convertView = inflater.inflate(R.layout.annuler_commande_item, null);


        TextView commande_code = (TextView) convertView.findViewById(R.id.cmdcodetv);
        TextView client_code = (TextView) convertView.findViewById(R.id.clientcodetv);
        TextView commande_date = (TextView) convertView.findViewById(R.id.cmddatetv);
        TextView tournee_code = (TextView) convertView.findViewById(R.id.tourneecodetv);
        TextView montant_cmd = (TextView) convertView.findViewById(R.id.montanttv);


        Livraison livraison = livraisonLists.get(position);

        if(livraison.getCLIENT_CODE() != null){
            Client client = clientManager.get(livraison.getCLIENT_CODE());

            if(client!=null){
                client_code.setText(client.getCLIENT_NOM());
            }else{
                client_code.setText(livraison.getCLIENT_CODE());
            }
        }else{
            client_code.setText(livraison.getCLIENT_CODE());

        }

        if(livraison.getTOURNEE_CODE() != null){
            Tournee tournee = tourneeManager.get(livraison.getTOURNEE_CODE());

            if(tournee!=null){
                tournee_code.setText(tournee.getTOURNEE_NOM());
            }else{
                tournee_code.setText(livraison.getTOURNEE_CODE());
            }
        }else{
            tournee_code.setText(livraison.getTOURNEE_CODE());

        }
        commande_code.setText(livraison.getLIVRAISON_CODE());
        commande_date.setText(livraison.getDATE_COMMANDE());

        montant_cmd.setText(String.valueOf(livraison.getMONTANT_NET())+" DH");

        return convertView;
    }
}