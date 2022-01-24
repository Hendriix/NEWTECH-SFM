package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Classe;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TONPC on 18/04/2017.
 */

public class Spinner_Classe_Adapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<Classe> classes;

    private ArrayList<Classe> arrayList;
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private String classecode;


    public Spinner_Classe_Adapter(Activity activity, int simple_spinner_item, List<Classe> classes ) {
        this.activity = activity;
        this.classes = classes;
        this.arrayList=new ArrayList<Classe>();
        this.arrayList.addAll(classes);
    }

    @Override
    public int getCount() {
        return classes.size();
    }

    @Override
    public Object getItem(int position) {
        return classes.get(position);
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
            convertView = inflater.inflate(R.layout.spinner_classe_ligne, null);


        TextView label = (TextView) convertView.findViewById(R.id.spinnertextclasse);

        Classe classe = classes.get(position);
        label.setText(classe.getCLASSE_NOM());

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

        Classe classe = classes.get(position);
        label.setText(classe.getCLASSE_NOM());

        return label;
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }
}
