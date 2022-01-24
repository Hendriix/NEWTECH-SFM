package com.newtech.newtech_sfm.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.newtech.newtech_sfm.Configuration.Commande_Adapter;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier_Manager.CommandeLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeManager;
import com.newtech.newtech_sfm.Metier_Manager.ImpressionManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;


/**
 * Created by sferricha on 30/12/2016.
 */

public class R_AllCommande2 extends AppCompatActivity {


    ListView listView;
    ArrayList<Commande> listcommande = new ArrayList<>();
    Commande_Adapter commande_adapter;
    ArrayList<CommandeLigne> commandeLignes = new ArrayList<>();

    CommandeLigneManager commandeLigneManager;
    ImpressionManager impressionManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_allcommande);
        setTitle("LES COMMANDES");
        commandeLigneManager = new CommandeLigneManager(getApplicationContext());
        impressionManager = new ImpressionManager(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listview1);

        CommandeManager commandeManager = new CommandeManager(getApplicationContext());
        listcommande=commandeManager.getList();
        commande_adapter = new Commande_Adapter(this,listcommande);
        listView.setAdapter(commande_adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Commande commande = (Commande)commande_adapter.getItem(position);

                R_AllCommandeLigne.commande_code="";

                Intent intent = new Intent(R_AllCommande2.this, R_AllCommandeLigne.class);
                R_AllCommandeLigne.commande_code=commande.getCOMMANDE_CODE();
                startActivity(intent);
                finish();


            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                Intent i = new Intent(R_AllCommande2.this, AuthActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                return true;

            case R.id.option2:
                Intent intt = new Intent(this, PrintActivity2.class);
                intt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intt);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
