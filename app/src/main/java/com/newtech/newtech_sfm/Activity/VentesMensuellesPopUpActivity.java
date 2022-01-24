package com.newtech.newtech_sfm.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.newtech.newtech_sfm.Configuration.VentesMensuellesPopUp_Adapter;
import com.newtech.newtech_sfm.R;

import java.util.Map;

/**
 * Created by csaylani on 26/04/2018.
 */

public class VentesMensuellesPopUpActivity extends AppCompatActivity{

    ListView ventesMensuellesPopUpList;
    public static Map<String,String> realisations;
    VentesMensuellesPopUp_Adapter ventesMensuellesPopUp_adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ventesmensuellespopup_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //DisplayMetrics dm = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(dm);


        //int width = dm.widthPixels;
        //int height = dm.heightPixels;

        //getWindow().setLayout((int)(width*.8),(int)(height*.6));


        ventesMensuellesPopUpList = (ListView)findViewById(R.id.ventes_mensuelles_popup_listeview);

        View child = getLayoutInflater().inflate(R.layout.no_data_found, null);
        ((ViewGroup)ventesMensuellesPopUpList.getParent()).addView(child);

        ventesMensuellesPopUpList.setEmptyView(child);

        ventesMensuellesPopUp_adapter = new VentesMensuellesPopUp_Adapter(this, realisations,getApplicationContext());

        ventesMensuellesPopUpList.setAdapter(ventesMensuellesPopUp_adapter);

        setTitle("DETAILS JOURNALIERS");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
