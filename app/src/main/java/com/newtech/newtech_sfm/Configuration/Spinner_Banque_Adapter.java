package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TONPC on 18/04/2017.
 */

public class Spinner_Banque_Adapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<String> uniteLists;

    private ArrayList<String> arrayList;
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private String tacheCode;


    public Spinner_Banque_Adapter(Activity activity, List<String> uniteLists) {
        this.activity = activity;
        this.uniteLists = uniteLists;
        this.arrayList=new ArrayList<String>();
        this.arrayList.addAll(uniteLists);
    }
    @Override
    public int getCount() {
        return uniteLists.size();
    }

    @Override
    public Object getItem(int position) {
        return uniteLists.get(position);
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
            convertView = inflater.inflate(R.layout.spinner_banque_ligne, null);


        TextView banque_nom = (TextView) convertView.findViewById(R.id.banque_nom);

        banque_nom.setText(uniteLists.get(position));

        return convertView;
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }
}
