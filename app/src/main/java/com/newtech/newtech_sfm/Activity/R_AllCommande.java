package com.newtech.newtech_sfm.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
import com.newtech.newtech_sfm.Service.BlutoothConnctionService;

import java.util.ArrayList;


/**
 * Created by sferricha on 30/12/2016.
 */

public class R_AllCommande extends AppCompatActivity {


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

                final Commande commande = (Commande)commande_adapter.getItem(position);
                commandeLignes = commandeLigneManager.getListByCommandeCode(commande.getCOMMANDE_CODE());

                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.alert_imprimante_all_commande);
                dialog.setTitle("Impression");
                dialog.setCanceledOnTouchOutside(false);
                Button print = (Button) dialog.findViewById(R.id.imprimer);
                Button done = (Button) dialog.findViewById(R.id.terminer);
                final TextView nbr_copies = (TextView) dialog.findViewById(R.id.nbr_copies);

                print.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i=0;i<Integer.valueOf(nbr_copies.getText().toString());i++){

                            String impression_text=impressionManager.ImprimerCommande(commande,getApplicationContext());
                            Log.d("print", "onClick rapport: "+impression_text.toString());
                            BlutoothConnctionService.imprimanteManager.printText(impression_text);


                        }

                    }
                });


                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        commandeLignes.clear();
                        dialog.dismiss();
                    }

                });

                dialog.show();
                //Toast.makeText(getApplicationContext(),commande.toString() , Toast.LENGTH_LONG).show();
                //Log.d("commande", "onItemClick: "+commande.toString());

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
                Intent i = new Intent(R_AllCommande.this, AuthActivity.class);
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
