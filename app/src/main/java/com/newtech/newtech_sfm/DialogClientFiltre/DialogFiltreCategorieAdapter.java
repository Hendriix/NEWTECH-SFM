package com.newtech.newtech_sfm.DialogClientFiltre;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.newtech.newtech_sfm.Configuration.Common;
import com.newtech.newtech_sfm.Metier.Categorie;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

public class DialogFiltreCategorieAdapter extends BaseAdapter {

    private ArrayList<Categorie> categorieArrayList;
    private Activity activity;
    private LayoutInflater inflater;
    private static final String TAG = DialogFiltreCategorieAdapter.class.getName();
    private boolean mChecked[];

    public DialogFiltreCategorieAdapter(ArrayList<Categorie> categorieArrayList, Activity activity) {
        this.categorieArrayList = categorieArrayList;
        this.activity = activity;
        this.mChecked = new boolean[categorieArrayList.size()];
    }

    public String getCheckedItems() {
        ArrayList<Categorie> categories = new ArrayList<Categorie>();
        String chaine_categorie = "";
        int lastElementIndex = Common.findLastTrueValue(mChecked);

        for(int i=0;i<categorieArrayList.size();i++) {
            if(mChecked[i]) {
                categories.add(categorieArrayList.get(i));
                if(i == lastElementIndex){
                    chaine_categorie = chaine_categorie+"'"+categorieArrayList.get(i).getCATEGORIE_CODE()+"'";
                }else{
                    chaine_categorie = chaine_categorie+"'"+categorieArrayList.get(i).getCATEGORIE_CODE()+"',";
                }
            }
        }
        Log.d(TAG, "getCheckedItems: "+chaine_categorie);

        return chaine_categorie;
    }

    @Override
    public int getCount() {
        return categorieArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return categorieArrayList.get(position);
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
            convertView = inflater.inflate(R.layout.multi_filtre_client_ligne, null);

        Categorie categorie = categorieArrayList.get(position);
        CheckBox title_checkbox = convertView.findViewById(R.id.title_cb);
        title_checkbox.setTag((Integer) position);
        title_checkbox.setChecked(mChecked[position]);
        title_checkbox.setOnCheckedChangeListener(mListener);
        title_checkbox.setText(categorie.getCATEGORIE_NOM());

        return convertView;
    }

    CompoundButton.OnCheckedChangeListener mListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mChecked[(Integer)buttonView.getTag()] = isChecked;
        }
    };
}
