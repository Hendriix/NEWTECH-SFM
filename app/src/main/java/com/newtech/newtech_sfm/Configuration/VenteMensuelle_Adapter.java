package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.VenteRow;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TONPC on 13/03/2017.
 */

public class VenteMensuelle_Adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<VenteRow> venteList;
    Context context;
    private ArrayList<VenteRow> arrayList;
    private static final String TAG = SQLiteHandler.class.getSimpleName();


    public VenteMensuelle_Adapter(Activity activity, List<VenteRow> venteList) {
        this.activity = activity;
        this.venteList = venteList;
        this.context = activity.getApplicationContext();
    }

    @Override
    public int getCount() {
        return venteList.size();
    }

    @Override
    public Object getItem(int location) {
        return venteList.get(location);
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
            convertView = inflater.inflate(R.layout.vente_kilogramme_ligne, null);

        //TableLayout ventes_mensuelles_details_table= (TableLayout)convertView.findViewById(R.id.ventes_mensuelles_ligne_tablelayout);

        //TableRow ligne = new TableRow(context);


        TextView date = (TextView)convertView.findViewById(R.id.vm_date);
        TextView tonnage = (TextView)convertView.findViewById(R.id.vm_tonnage);

        VenteRow vente = venteList.get(position);

        date.setText(vente.getDATE_VENTE());
        tonnage.setText(String.valueOf(vente.getQUANTITE_VENDU()));

        return convertView;
    }

    private static class ViewHolder{

    }
}
