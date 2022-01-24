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
import com.newtech.newtech_sfm.Metier.Tournee;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

public class DialogFiltreTourneeAdapter extends BaseAdapter {

    private ArrayList<Tournee> tourneeArrayList;
    private Activity activity;
    private LayoutInflater inflater;
    private static final String TAG = DialogFiltreTourneeAdapter.class.getName();
    private boolean mChecked[];

    public DialogFiltreTourneeAdapter(ArrayList<Tournee> tourneeArrayList, Activity activity) {
        this.tourneeArrayList = tourneeArrayList;
        this.activity = activity;
        this.mChecked = new boolean[tourneeArrayList.size()];
    }

    public String getCheckedItems() {
        ArrayList<Tournee> tournees = new ArrayList<Tournee>();
        String chaine_tournee = "";
        int lastElementIndex = Common.findLastTrueValue(mChecked);

        for(int i=0;i<tourneeArrayList.size();i++) {
            if(mChecked[i]) {
                tournees.add(tourneeArrayList.get(i));
                if(i == lastElementIndex){
                    chaine_tournee = chaine_tournee+"'"+tourneeArrayList.get(i).getTOURNEE_CODE()+"'";
                }else{
                    chaine_tournee = chaine_tournee+"'"+tourneeArrayList.get(i).getTOURNEE_CODE()+"',";
                }
            }
        }
        Log.d(TAG, "getCheckedItems: "+chaine_tournee);

        return chaine_tournee;
    }

    @Override
    public int getCount() {
        return tourneeArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return tourneeArrayList.get(position);
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

        Tournee tournee = tourneeArrayList.get(position);
        CheckBox title_checkbox = convertView.findViewById(R.id.title_cb);
        title_checkbox.setTag((Integer) position);
        title_checkbox.setChecked(mChecked[position]);
        title_checkbox.setOnCheckedChangeListener(mListener);
        title_checkbox.setText(tournee.getTOURNEE_NOM());

        return convertView;
    }

   CompoundButton.OnCheckedChangeListener mListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mChecked[(Integer)buttonView.getTag()] = isChecked;
        }
    };

}
