package com.newtech.newtech_sfm.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.newtech.newtech_sfm.Configuration.LieuLivraison_Adapter;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.Lieu;
import com.newtech.newtech_sfm.Metier_Manager.ClientManager;
import com.newtech.newtech_sfm.Metier_Manager.LieuManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TONPC on 22/02/2017.
 */

    public class LieuLivraisonActivity extends AppCompatActivity {

    ListView llivListView;
    LieuLivraison_Adapter lieuLivraison_adapter;
    List<Lieu> lieuList = new ArrayList<Lieu>();
    Button llivButton;

    private String lieulivraisonResult;
    public Commande commande= ViewCommandeActivity.commande;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lieu_livraison_activity);

        final String client_Code=ClientActivity.clientCourant.getCLIENT_CODE();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_visiteresultat);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LieuManager lieuManager = new LieuManager(getApplicationContext());
        lieuList = lieuManager.getListByClientCode(client_Code);



        llivListView = (ListView) findViewById(R.id.llivraison_listView);
        llivButton = (Button) findViewById(R.id.lieu_livraison_buttonvalider);


        if(lieuList.size()==0){

            View child = getLayoutInflater().inflate(R.layout.llieu_no_data_found, null);
            ((ViewGroup)llivListView.getParent()).addView(child);
            llivButton.setVisibility(View.GONE);
            llivListView.setEmptyView(child);

        }

        lieuLivraison_adapter = new LieuLivraison_Adapter(this,lieuList);


        ClientManager client_Manager = new ClientManager(getApplicationContext());
        ClientActivity.clientCourant=client_Manager.get(client_Code);


        llivListView.setAdapter(lieuLivraison_adapter);


        llivListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                lieuLivraison_adapter.setSelectedIndex(position);
                lieuLivraison_adapter.notifyDataSetChanged();



                Lieu lieu =(Lieu) lieuLivraison_adapter.getItem(position);
                lieulivraisonResult = lieu.getLIEU_CODE();


            }
        });

        llivButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if(lieuList.size()>0){

                    if(lieuLivraison_adapter.getSelectedIndex()!= -1)
                    {
                        commande.setLIEU_LIVRAISON(lieulivraisonResult);
                        Toast.makeText(LieuLivraisonActivity.this,"LIEU "+lieulivraisonResult,
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),ViewCommandeActivity.class);
                        startActivity(intent);
                        finish();


                    }else{
                        Toast.makeText(LieuLivraisonActivity.this,"MERCI DE CHOISIR UN RESULTAT",
                                Toast.LENGTH_LONG).show();
                    }

                }else{

                    Intent intent = new Intent(getApplicationContext(),ViewCommandeActivity.class);
                    startActivity(intent);
                    finish();

                }





            }
        });

        setTitle("LIEU LIVRAISON");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, ViewCommandeActivity.class);
        startActivity(i);
        finish();
    }
}
