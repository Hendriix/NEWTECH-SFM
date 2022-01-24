package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.ObjectifRow;
import com.newtech.newtech_sfm.R;

import java.util.List;

/**
 * Created by sferricha on 26/08/2016.
 */

public class ObjectifListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ObjectifRow> objectifrows;

    public ObjectifListAdapter(Activity activity, List<ObjectifRow> objectifrows) {
        this.activity = activity;
        this.objectifrows = objectifrows;
    }


    @Override
    public int getCount() {
        return objectifrows.size();
    }

    @Override
    public Object getItem(int location) {
        return objectifrows.get(location);
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
            convertView = inflater.inflate(R.layout.objectif_listview_row, null);


        TextView tv_famille = (TextView) convertView.findViewById(R.id.lobj_famille_tv);
        TextView tv_objectif = (TextView) convertView.findViewById(R.id.lobj_objectif_tv);
        TextView tv_realisation = (TextView) convertView.findViewById(R.id.lobj_realisation_tv);
        TextView tv_percent = (TextView) convertView.findViewById(R.id.lobj_percent_tv);
        CircularProgressBar circular = (CircularProgressBar) convertView.findViewById(R.id.lobj_progressBar1);





        // getting movie data for the row
        ObjectifRow obj = objectifrows.get(position);



        // tv_famille
        tv_famille.setText("Famille: " +  String.valueOf(obj.getFAMILLE_NOM()));

        // tv_objectif
        tv_objectif.setText("Objectif: " + Double.valueOf(obj.getObjectif()));

        // tv_realisation
        tv_realisation.setText("Realisation: " + Double.valueOf(obj.getRealisation()));

        // Percent
        tv_percent.setText("Percent: " + Double.valueOf(obj.getPercent()));

        //progressbar circular
        circular.setTitle((int) Double.valueOf(obj.getPercent()).doubleValue()+"%");
        circular.setSubTitle((int) Double.valueOf(obj.getPercent()).doubleValue()+"");
        circular.setProgress((int) Double.valueOf(obj.getPercent()).doubleValue());


        return convertView;
    }
}
