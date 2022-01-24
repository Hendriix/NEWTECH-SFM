package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Categorie;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TONPC on 18/04/2017.
 */

public class Spinner_Categorie_Adapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<Categorie> categories;

    private ArrayList<Categorie> arrayList;
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private String categoriecode;


    public Spinner_Categorie_Adapter(Activity activity,int simple_spinner_item, List<Categorie> categories ) {
        this.activity = activity;
        this.categories = categories;
        this.arrayList=new ArrayList<Categorie>();
        this.arrayList.addAll(categories);
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
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
            convertView = inflater.inflate(R.layout.spinner_categorie_ligne, null);


        TextView label = (TextView) convertView.findViewById(R.id.spinnertextcategorie);

        Categorie categorie = categories.get(position);
        label.setText(categorie.getCATEGORIE_NOM());

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.simple_spinner_dropdown_item, null);

        //TextView label=new TextView(activity);

        CheckedTextView label = (CheckedTextView) convertView.findViewById(R.id.spinnertext1);

        Categorie categorie = categories.get(position);
        label.setText(categorie.getCATEGORIE_NOM());

        return label;
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }
}
