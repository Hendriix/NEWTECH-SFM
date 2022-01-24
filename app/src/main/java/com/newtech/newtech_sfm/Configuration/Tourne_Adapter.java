package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Tournee;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by TONPC on 20/09/2017.
 */

public class Tourne_Adapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<Tournee> tourneeList;

    private ArrayList<Tournee> arrayList;
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private String tacheCode;


    public Tourne_Adapter(Activity activity, List<Tournee> tourneeList) {
        this.activity = activity;
        this.tourneeList = tourneeList;
        this.arrayList=new ArrayList<Tournee>();
        this.arrayList.addAll(tourneeList);
    }


    @Override
    public int getCount() {
        return tourneeList.size();
    }

    @Override
    public Object getItem(int position) {
        return tourneeList.get(position);
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
            convertView = inflater.inflate(R.layout.list_cell3, null);


        TextView tournee_nom = (TextView)convertView.findViewById(R.id.person_name);
        TextView tournee_code = (TextView)convertView.findViewById(R.id.person_age);
        ImageView tourneePhoto = (ImageView)convertView.findViewById(R.id.person_photo);


        tourneePhoto.setImageResource(R.drawable.icone_route);

        Tournee tournee = tourneeList.get(position);

        tournee_nom.setText(tournee.getTOURNEE_NOM());
        tournee_code.setText(tournee.getTOURNEE_CODE());

        return convertView;

    }

    public void filter(String textfilter){
        textfilter=textfilter.toLowerCase(Locale.getDefault());
        tourneeList.clear();
        if (textfilter.length() == 0) {
            tourneeList.addAll(arrayList);
        }
        else
        {
            for (Tournee tournee : arrayList)
            {
                if (tournee.getTOURNEE_NOM().toLowerCase(Locale.getDefault()).contains(textfilter))
                {
                    tourneeList.add(tournee);
                }
            }
        }
        notifyDataSetChanged();

    }
}
