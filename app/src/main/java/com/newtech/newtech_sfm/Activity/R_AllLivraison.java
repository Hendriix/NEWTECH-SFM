package com.newtech.newtech_sfm.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.newtech.newtech_sfm.Configuration.RLivraison_Adapter;
import com.newtech.newtech_sfm.Metier.Livraison;
import com.newtech.newtech_sfm.Metier.LivraisonLigne;
import com.newtech.newtech_sfm.Metier_Manager.ImpressionManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonManager;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.Service.BlutoothConnctionService;

import java.util.ArrayList;

/**
 * Created by TONPC on 11/07/2017.
 */

public class R_AllLivraison extends AppCompatActivity{

    ListView listView;
    ArrayList<Livraison> listLivraison = new ArrayList<>();
    RLivraison_Adapter livraison_adapter;
    ArrayList<LivraisonLigne> livraisonLignes = new ArrayList<>();
    LivraisonLigneManager livraisonLigneManager;
    ImpressionManager impressionManager;
    String impression_text = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_alllivraison);
        setTitle("LES LIVRAISONS");

        impressionManager = new ImpressionManager(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listview1);

        LivraisonManager livraisonManager = new LivraisonManager(getApplicationContext());
        livraisonLigneManager= new LivraisonLigneManager(getApplicationContext());

        listLivraison=livraisonManager.getList();
        livraison_adapter = new RLivraison_Adapter(this,listLivraison);
        listView.setAdapter(livraison_adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Livraison livraison = (Livraison)livraison_adapter.getItem(position);
                livraisonLignes = livraisonLigneManager.getListByLivraisonCode(livraison.getLIVRAISON_CODE());

                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.alert_imprimante_all_commande);
                dialog.setTitle("Impression");
                dialog.setCanceledOnTouchOutside(false);
                Button print = (Button) dialog.findViewById(R.id.imprimer);
                Button done = (Button) dialog.findViewById(R.id.terminer);
                final TextView nbr_copies = (TextView) dialog.findViewById(R.id.nbr_copies);

                impression_text=impressionManager.ImprimerLivraison(livraison,getApplicationContext());
                print.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i=0;i<Integer.valueOf(nbr_copies.getText().toString());i++){

                            BlutoothConnctionService.imprimanteManager.printText(impression_text);
                            //impressionManager.printText(CommandeManager.ImprimerLivraison(livraison,livraisonLignes,getApplicationContext()));
                        }


                    }
                });

                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        livraisonLignes.clear();
                        dialog.dismiss();
                    }

                });

                dialog.show();

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
                Intent i = new Intent(R_AllLivraison.this, AuthActivity.class);
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
