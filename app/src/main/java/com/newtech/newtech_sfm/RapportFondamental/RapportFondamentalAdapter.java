package com.newtech.newtech_sfm.RapportFondamental;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.Configuration.SQLiteHandler;
import com.newtech.newtech_sfm.Metier.RapportQualitatif;
import com.newtech.newtech_sfm.R;

import java.util.List;

/**
 * Created by TONPC on 13/03/2017.
 */

public class RapportFondamentalAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<RapportQualitatif> rapportQualitatifs;
    Context context;
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    public RapportFondamentalAdapter(Activity activity, List<RapportQualitatif> rapportQualitatifs) {
        this.activity = activity;
        this.rapportQualitatifs = rapportQualitatifs;
        this.context = activity.getApplicationContext();
    }

    @Override
    public int getCount() {
        return rapportQualitatifs.size();
    }

    @Override
    public Object getItem(int location) {
        return rapportQualitatifs.get(location);
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
            convertView = inflater.inflate(R.layout.rapport_qualitatif_ligne, null);

        //TableLayout ventes_mensuelles_details_table= (TableLayout)convertView.findViewById(R.id.ventes_mensuelles_ligne_tablelayout);

        //TableRow ligne = new TableRow(context);
        TextView tournee_tv = (TextView) convertView.findViewById(R.id.tournee_tv);
        TextView cp_tv = (TextView) convertView.findViewById(R.id.cp_tv);
        TextView nbv_tv = (TextView) convertView.findViewById(R.id.nbv_tv);
        TextView tc_tv = (TextView) convertView.findViewById(R.id.tc_tv);
        TextView nbf_tv = (TextView) convertView.findViewById(R.id.nbf_tv);
        TextView ts_tv = (TextView) convertView.findViewById(R.id.ts_tv);
        TextView mm_tv = (TextView) convertView.findViewById(R.id.mm_tv);
        TextView nbsku_tv = (TextView) convertView.findViewById(R.id.nbsku_tv);
        TextView tgv_tv = (TextView) convertView.findViewById(R.id.tgv_tv);
        TextView tgf_tv = (TextView) convertView.findViewById(R.id.tgf_tv);

        RapportQualitatif rapportQualitatif = rapportQualitatifs.get(position);

        tournee_tv.setText(rapportQualitatif.getTOURNEE_NOM());
        cp_tv.setText(rapportQualitatif.getCLIENT_PROGRAMMES());
        nbv_tv.setText(rapportQualitatif.getNOMBRE_VISITES());
        tc_tv.setText(rapportQualitatif.getTAUX_COUVERTURE());
        nbf_tv.setText(rapportQualitatif.getNOMBRE_FACTURES());
        ts_tv.setText(rapportQualitatif.getTAUX_SUCCES());
        mm_tv.setText(rapportQualitatif.getMOYENNE_MINUTES());
        nbsku_tv.setText(rapportQualitatif.getNOMBRE_SKU());
        tgv_tv.setText(rapportQualitatif.getTAUX_GPS_VISITES());
        tgf_tv.setText(rapportQualitatif.getTAUX_GPS_FACTURES());


        return convertView;
    }

    private static class ViewHolder{

    }
}
