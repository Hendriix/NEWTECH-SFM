package com.newtech.newtech_sfm.Activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.newtech.newtech_sfm.Configuration.ChargementPopUp_Adapter;
import com.newtech.newtech_sfm.Metier.StockDemandeLigne;
import com.newtech.newtech_sfm.Metier_Manager.StockDemandeLigneManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

/**
 * Created by csaylani on 26/04/2018.
 */

public class ChargementPopUpActivity extends AppCompatActivity {

    ListView chargementPopUpList;
    ArrayList<StockDemandeLigne> stockDemandeLignes;
    ChargementPopUp_Adapter chargementPopUp_Adapter;
    StockDemandeLigneManager stockDemandeLigneManager;
    public static String commandeSource;
    public static String demande_code;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chargementpopup_activity);

        stockDemandeLigneManager = new StockDemandeLigneManager(getApplicationContext());


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        stockDemandeLignes = stockDemandeLigneManager.getListByDemandeCode(demande_code);

        chargementPopUpList = (ListView)findViewById(R.id.chargementpopup_listview);

        chargementPopUp_Adapter = new ChargementPopUp_Adapter(this, stockDemandeLignes,getApplicationContext());

        chargementPopUpList.setAdapter(chargementPopUp_Adapter);


    }
}
