package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.Tournee;
import com.newtech.newtech_sfm.Metier.VenteRow;
import com.newtech.newtech_sfm.Metier.Visite;
import com.newtech.newtech_sfm.Metier.VisiteResultat;
import com.newtech.newtech_sfm.Metier_Manager.ClientManager;
import com.newtech.newtech_sfm.Metier_Manager.TourneeManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteResultatManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TONPC on 13/03/2017.
 */

public class VisitesJournalieres_Adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Visite> visites;
    Context context;
    private ArrayList<VenteRow> arrayList;
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    ClientManager clientManager;
    TourneeManager tourneeManager;
    VisiteResultatManager visiteResultatManager;


    public VisitesJournalieres_Adapter(Activity activity, List<Visite> venteList) {
        this.activity = activity;
        this.visites = venteList;
        this.context = activity.getApplicationContext();
        clientManager = new ClientManager(context);
        tourneeManager = new TourneeManager(context);
        visiteResultatManager = new VisiteResultatManager(context);
    }

    @Override
    public int getCount() {
        return visites.size();
    }

    @Override
    public Object getItem(int location) {
        return visites.get(location);
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
            convertView = inflater.inflate(R.layout.visites_journalieres_ligne, null);

        //TableLayout ventes_mensuelles_details_table= (TableLayout)convertView.findViewById(R.id.ventes_mensuelles_ligne_tablelayout);

        //TableRow ligne = new TableRow(context);
        TextView commande_code = (TextView) convertView.findViewById(R.id.cmdcodetv);
        TextView client_code = (TextView) convertView.findViewById(R.id.clientcodetv);
        TextView commande_date = (TextView) convertView.findViewById(R.id.cmddatetv);
        TextView commande_tournee = (TextView) convertView.findViewById(R.id.tourneecodetv);
        TextView commande_montant = (TextView) convertView.findViewById(R.id.montanttv);

        Visite visite = visites.get(position);
        if(visite.getCLIENT_CODE() != null){
            Client client = clientManager.get(visite.getCLIENT_CODE());

            if(client!=null){
                client_code.setText(client.getCLIENT_NOM());
            }else{
                client_code.setText(visite.getCLIENT_CODE());
            }
        }else{
            client_code.setText(visite.getCLIENT_CODE());

        }

        if(visite.getCLIENT_CODE() != null){
            VisiteResultat visiteResultat = visiteResultatManager.get(String.valueOf(visite.getVISITE_RESULTAT()));

            if(visiteResultat!=null){
                commande_montant.setText(visiteResultat.getVISITERESULTAT_NOM());
            }else{
                commande_montant.setText(visite.getVISITE_RESULTAT());
            }
        }else{
            commande_montant.setText(visite.getVISITE_RESULTAT());

        }

        if(visite.getTOURNEE_CODE() != null){
            Tournee tournee = tourneeManager.get(visite.getTOURNEE_CODE());

            if(tournee!=null){
                commande_tournee.setText(tournee.getTOURNEE_NOM());
            }else{
                commande_tournee.setText(visite.getTOURNEE_CODE());
            }
        }else{
            commande_tournee.setText(visite.getTOURNEE_CODE());

        }

        commande_code.setText(visite.getVISITE_CODE());
        commande_date.setText(visite.getDATE_VISITE());

        return convertView;
    }

    private static class ViewHolder{

    }
}
