package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Choufouni;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TONPC on 18/04/2017.
 */

public class Spinner_Choufouni_Adapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<Choufouni> choufounis;

    private ArrayList<Choufouni> arrayList;
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private String typecode;


    public Spinner_Choufouni_Adapter(Activity activity, int simple_spinner_item, List<Choufouni> choufounis ) {
        this.activity = activity;
        this.choufounis = choufounis;
        this.arrayList=new ArrayList<Choufouni>();
        this.arrayList.addAll(choufounis);
    }

    @Override
    public int getCount() {
        return choufounis.size();
    }

    @Override
    public Object getItem(int position) {
        return choufounis.get(position);
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
            convertView = inflater.inflate(R.layout.spinner_choufouni_ligne, null);


        TextView label = (TextView) convertView.findViewById(R.id.spinnertexttype);

        Choufouni choufouni = choufounis.get(position);
        label.setText(choufouni.getCHOUFOUNI_NOM());

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

        Choufouni choufouni = choufounis.get(position);
        label.setText(choufouni.getCHOUFOUNI_NOM());

        return label;
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }
}
