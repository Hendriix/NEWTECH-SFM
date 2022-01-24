package com.newtech.newtech_sfm.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.newtech.newtech_sfm.Metier.Menu;
import com.newtech.newtech_sfm.R;


/**
 * Created by sferricha on 13/08/2016.
 */

public class RapportActivity extends AppCompatActivity {

    ListView mListView;
    Menu[] Rapports3={
            new Menu("M1","Vente Par Article", "R_VenteParArticle" ,""),
            new Menu("M2","Objectif/Realisation", "R_Objectif" ,""),
            new Menu("M3","Commandes", "R_AllCommande" ,""),
            new Menu("M5","Commandes lignes", "R_AllCommande2" ,""),
            new Menu("M6","Ventes Mensuelles", "RVenteActivity" ,""),
            new Menu("M7","Gratuites", "R_GratuitesActivity" ,""),
            new Menu("M8","Livraison", "R_AllLivraison" ,"")
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rapport);
        setTitle("RAPPORT");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView = (ListView) findViewById(R.id.listrapport);

        ArrayAdapter <Menu> mAdapterRapport = new ArrayAdapter<Menu>(RapportActivity.this,android.R.layout.simple_list_item_1, Rapports3);
        mListView.setAdapter(mAdapterRapport);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.d("menu1", "onItemClick: "+Rapports3[i]);

                Menu ClickedMenu=Rapports3[i];

                Log.d("menu2", "onItemClick: "+ClickedMenu);


                Class<?> className = null;
                try {
                    className = Class.forName(getApplicationContext().getPackageName()+".Activity."+ClickedMenu.getLIEN().toString());

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                Intent intent =new Intent(RapportActivity.this,className);
                startActivity(intent);

                //Toast.makeText(RapportActivity.this,ClickedMenu.getLIEN().toString()+getApplicationContext().getPackageName().toString() , Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            case R.id.logout:
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(RapportActivity.this, AuthActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                return true;

            case R.id.option2:
                Intent intt = new Intent(RapportActivity.this, PrintActivity2.class);
                intt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intt);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(RapportActivity.this, MenuActivity.class);

        startActivity(i);
        finish();
    }
}
