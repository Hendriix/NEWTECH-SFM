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
import com.newtech.newtech_sfm.Metier.Classe;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

public class DialogFiltreClasseAdapter extends BaseAdapter {

    private ArrayList<Classe> classeArrayList;
    private Activity activity;
    private LayoutInflater inflater;
    private static final String TAG = DialogFiltreClasseAdapter.class.getName();
    private boolean mChecked[];

    public DialogFiltreClasseAdapter(ArrayList<Classe> classeArrayList, Activity activity) {
        this.classeArrayList = classeArrayList;
        this.activity = activity;
        this.mChecked = new boolean[classeArrayList.size()];
    }

    public String getCheckedItems() {
        ArrayList<Classe> classes = new ArrayList<Classe>();
        String chaine_classe = "";
        int lastElementIndex = Common.findLastTrueValue(mChecked);

        for(int i=0;i<classeArrayList.size();i++) {
            if(mChecked[i]) {
                classes.add(classeArrayList.get(i));
                if(i == lastElementIndex){
                    chaine_classe = chaine_classe+"'"+classeArrayList.get(i).getCLASSE_CODE()+"'";
                }else{
                    chaine_classe = chaine_classe+"'"+classeArrayList.get(i).getCLASSE_CODE()+"',";
                }
            }
        }
        Log.d(TAG, "getCheckedItems: "+chaine_classe);

        return chaine_classe;
    }

    @Override
    public int getCount() {
        return classeArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return classeArrayList.get(position);
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

        Classe classe = classeArrayList.get(position);
        CheckBox title_checkbox = convertView.findViewById(R.id.title_cb);
        title_checkbox.setTag((Integer) position);
        title_checkbox.setChecked(mChecked[position]);
        title_checkbox.setOnCheckedChangeListener(mListener);
        title_checkbox.setText(classe.getCLASSE_NOM());

        return convertView;
    }

    CompoundButton.OnCheckedChangeListener mListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mChecked[(Integer)buttonView.getTag()] = isChecked;
        }
    };
}
