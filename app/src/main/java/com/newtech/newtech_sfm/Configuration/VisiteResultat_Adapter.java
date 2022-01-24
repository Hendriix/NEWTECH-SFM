package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.newtech.newtech_sfm.Metier.VisiteResultat;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by TONPC on 22/02/2017.
 */

public class VisiteResultat_Adapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<VisiteResultat> visiteResultat_list;
    private ArrayList<VisiteResultat> visiteResultat_arrayList;
    private int selectedIndex=-1;

    public VisiteResultat_Adapter(Activity activity, List<VisiteResultat> visiteResultat_list) {
        this.activity = activity;
        this.visiteResultat_list = visiteResultat_list;
        this.visiteResultat_arrayList=new ArrayList<VisiteResultat>();
        this.visiteResultat_arrayList.addAll(visiteResultat_list);
    }


    public void setSelectedIndex(int index){
        selectedIndex=index;
        //Log.d("salim", String.valueOf(selectedIndex));
    }

    public int getSelectedIndex(){
        return this.selectedIndex;
    }


    @Override
    public int getCount() {
        return visiteResultat_list.size();
    }

    @Override
    public Object getItem(int position) {
        return visiteResultat_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.visiteresultat_ligne, null);

            RadioButton vr_radioButton= (RadioButton) convertView.findViewById(R.id.vr_radioButton);
            //TextView vr_ligne_textView2= (TextView) convertView.findViewById(R.id.vr_ligne_textView2);
        //RadioButton vr_ligne_radioButton = (RadioButton) convertView.findViewById(R.id.vr_ligne_radioButton);


        VisiteResultat visiteResultat=visiteResultat_list.get(position);

        vr_radioButton.setText(visiteResultat.getVISITERESULTAT_NOM());

        if(selectedIndex==position){
            vr_radioButton.setChecked(true);
        }
        else{
            vr_radioButton.setChecked(false);
        }

        return convertView;
    }

}