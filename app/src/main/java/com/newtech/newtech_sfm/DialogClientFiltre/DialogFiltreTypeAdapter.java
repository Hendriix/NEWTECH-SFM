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
import com.newtech.newtech_sfm.Metier.Type;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

public class DialogFiltreTypeAdapter extends BaseAdapter {

    private ArrayList<Type> typeArrayList;
    private Activity activity;
    private LayoutInflater inflater;
    private static final String TAG = DialogFiltreTypeAdapter.class.getName();
    private boolean mChecked[];

    public DialogFiltreTypeAdapter(ArrayList<Type> typeArrayList, Activity activity) {
        this.typeArrayList = typeArrayList;
        this.activity = activity;
        this.mChecked = new boolean[typeArrayList.size()];
    }

    public String getCheckedItems() {
        ArrayList<Type> types = new ArrayList<Type>();
        String chaine_type = "";
        int lastElementIndex = Common.findLastTrueValue(mChecked);

        for(int i=0;i<typeArrayList.size();i++) {
            if(mChecked[i]) {
                types.add(typeArrayList.get(i));
                if(i == lastElementIndex){
                    chaine_type = chaine_type+"'"+typeArrayList.get(i).getTYPE_CODE()+"'";
                }else{
                    chaine_type = chaine_type+"'"+typeArrayList.get(i).getTYPE_CODE()+"',";
                }
            }
        }
        Log.d(TAG, "getCheckedItems: "+chaine_type);

        return chaine_type;
    }

    @Override
    public int getCount() {
        return typeArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return typeArrayList.get(position);
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

        Type type = typeArrayList.get(position);
        CheckBox title_checkbox = convertView.findViewById(R.id.title_cb);
        title_checkbox.setTag((Integer) position);
        title_checkbox.setChecked(mChecked[position]);
        title_checkbox.setOnCheckedChangeListener(mListener);
        title_checkbox.setText(type.getTYPE_NOM());

        return convertView;
    }

    CompoundButton.OnCheckedChangeListener mListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mChecked[(Integer)buttonView.getTag()] = isChecked;
        }
    };
}
