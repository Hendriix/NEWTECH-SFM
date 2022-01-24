package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.newtech.newtech_sfm.Metier.Lieu;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by TONPC on 22/02/2017.
 */

public class LieuLivraison_Adapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<Lieu> lieus;
    private ArrayList<Lieu> lieuArrayList;
    private int selectedIndex=-1;

    public LieuLivraison_Adapter(Activity activity, List<Lieu> lieus) {
        this.activity = activity;
        this.lieus = lieus;
        this.lieuArrayList=new ArrayList<Lieu>();
        this.lieuArrayList.addAll(lieus);
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
        return lieus.size();
    }

    @Override
    public Object getItem(int position) {
        return lieus.get(position);
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
            convertView = inflater.inflate(R.layout.lieulivraison_ligne, null);

            RadioButton vr_radioButton= (RadioButton) convertView.findViewById(R.id.lieu_livraison_radioButton);
            //TextView vr_ligne_textView2= (TextView) convertView.findViewById(R.id.vr_ligne_textView2);
        //RadioButton vr_ligne_radioButton = (RadioButton) convertView.findViewById(R.id.vr_ligne_radioButton);


        Lieu lieu=lieus.get(position);

        vr_radioButton.setText(lieu.getLIEU_NOM());

        if(selectedIndex==position){
            vr_radioButton.setChecked(true);
        }
        else{
            vr_radioButton.setChecked(false);
        }

        return convertView;
    }

}