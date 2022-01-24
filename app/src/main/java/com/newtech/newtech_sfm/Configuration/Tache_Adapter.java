package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Tache;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by TONPC on 13/03/2017.
 */

public class Tache_Adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Tache> tacheLists;

    private ArrayList<Tache> arrayList;
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private String tacheCode;


    public Tache_Adapter(Activity activity, List<Tache> tacheLists) {
        this.activity = activity;
        this.tacheLists = tacheLists;
        this.arrayList=new ArrayList<Tache>();
        this.arrayList.addAll(tacheLists);
    }

    @Override
    public int getCount() {
        return tacheLists.size();
    }

    @Override
    public Object getItem(int position) {
        return tacheLists.get(position);
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
            convertView = inflater.inflate(R.layout.tache_catalogue_ligne, null);

        TextView tache_nom = (TextView) convertView.findViewById(R.id.tache_nom);
        TextView tache_code = (TextView) convertView.findViewById(R.id.tache_code);
        ImageView tache_image = (ImageView) convertView.findViewById(R.id.tache_image);
        tache_image.setImageResource(R.drawable.task22);

        Tache tache = tacheLists.get(position);

        tache_nom.setText(tache.getTACHE_NOM());
        tache_code.setText(tache.getTACHE_CODE());

        return convertView;
    }

    public void filter(String textfilter){
        textfilter=textfilter.toLowerCase(Locale.getDefault());
        tacheLists.clear();
        if (textfilter.length() == 0) {
            tacheLists.addAll(arrayList);
        }
        else
        {
            for (Tache tache : arrayList)
            {
                if (tache.getTACHE_NOM().toLowerCase(Locale.getDefault()).contains(textfilter))
                {
                    tacheLists.add(tache);
                }
            }
        }
        notifyDataSetChanged();

    }
}
