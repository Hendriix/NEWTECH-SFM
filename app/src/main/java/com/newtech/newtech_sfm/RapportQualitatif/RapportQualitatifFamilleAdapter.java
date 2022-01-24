package com.newtech.newtech_sfm.RapportQualitatif;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.RapportQualitatifFamille;
import com.newtech.newtech_sfm.R;

import java.util.List;

/**
 * Created by TONPC on 13/03/2017.
 */

public class RapportQualitatifFamilleAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<RapportQualitatifFamille> rapportQualitatifFamilles;
    Context context;


    public RapportQualitatifFamilleAdapter(Activity activity, List<RapportQualitatifFamille> rapportQualitatifFamilles) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.rapportQualitatifFamilles = rapportQualitatifFamilles;
    }

    @Override
    public int getCount() {
        return rapportQualitatifFamilles.size();
    }

    @Override
    public Object getItem(int location) {
        return rapportQualitatifFamilles.get(location);
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
            convertView = inflater.inflate(R.layout.rapport_qualitatif_famille_ligne, null);

        TextView labelle = (TextView) convertView.findViewById(R.id.label_tv);
        TextView tonnage = (TextView) convertView.findViewById(R.id.tonnage_tv);
        TextView nbr_factures = (TextView) convertView.findViewById(R.id.nbr_factures_tv);

        RapportQualitatifFamille rapportQualitatifFamille = rapportQualitatifFamilles.get(position);

        labelle.setText(String.valueOf(rapportQualitatifFamille.getFAMILLE_LIBELLE()));
        tonnage.setText(String.valueOf(rapportQualitatifFamille.getTONNAGE()));
        nbr_factures.setText(String.valueOf(rapportQualitatifFamille.getNOMBRE_FACTURE()));

        return convertView;
    }

    private static class ViewHolder{

    }
}
