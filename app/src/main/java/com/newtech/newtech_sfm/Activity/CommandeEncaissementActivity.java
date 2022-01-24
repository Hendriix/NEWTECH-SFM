package com.newtech.newtech_sfm.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.newtech.newtech_sfm.Configuration.Commande_Encaissement_Adapter;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier.CommandeLigneAEncaisser;
import com.newtech.newtech_sfm.Metier.CommandeNonCloturee;
import com.newtech.newtech_sfm.Metier.Encaissement;
import com.newtech.newtech_sfm.Metier_Manager.CommandeNonClotureeManager;
import com.newtech.newtech_sfm.Metier_Manager.EncaissementManager;
import com.newtech.newtech_sfm.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by TONPC on 22/08/2017.
 */

public class CommandeEncaissementActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{


    SearchView mSearchView;
    Commande_Encaissement_Adapter commande_encaissement_adapter;


    ArrayList<CommandeNonCloturee> commandeNonCloturees = new ArrayList<>();
    ArrayList<Commande> commandes = new ArrayList<>();

    ArrayList<CommandeLigneAEncaisser> commandeLigneAEncaissers = new ArrayList<>();
    ArrayList<CommandeLigne> commandeLignes = new ArrayList<>();

    ArrayList<Encaissement> encaissements = new ArrayList<>();

    EncaissementManager encaissementManager;

    ListView mListView1;
    SearchManager searchmanager;
    private ProgressBar mProgressBar;
    private CheckBox vaCheckBox1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commande_encaissement_activity);

        mSearchView =(SearchView) findViewById(R.id.commande_search);
        encaissementManager = new EncaissementManager(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_client);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchmanager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        mListView1 = (ListView) findViewById(R.id.commande_encaissement_listview1);

        CommandeNonClotureeManager commandeNonClotureeManager = new CommandeNonClotureeManager(getApplicationContext());
        commandeNonCloturees = commandeNonClotureeManager.getListAEByClientCode(ClientActivity.clientCourant.getCLIENT_CODE());


        for(int i=0;i<commandeNonCloturees.size();i++){
            commandes.add(new Commande(commandeNonCloturees.get(i)));
        }

        commande_encaissement_adapter = new Commande_Encaissement_Adapter(this,commandes);
        mListView1.setAdapter(commande_encaissement_adapter);
        mListView1.setTextFilterEnabled(true);
        setupSearchView();

        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                EncaissementActivity.commande=null;


                Commande commande =(Commande)commande_encaissement_adapter.getItem(position);
                //encaissements = encaissementManager.getListByCommandeCode(commande.getCOMMANDE_CODE());

                //EncaissementActivity.encaissements = encaissements;
                EncaissementActivity.commande = commande;
                EncaissementConsultationActivity.commande = commande;
                //EncaissementActivity.ListeCommandeLigne = commandeLignes;

                Intent intent = new Intent(getApplicationContext(),EncaissementConsultationActivity.class);
                startActivity(intent);
                finish();

            }
        });

        setTitle("COMMANDES A ENCAISSER");
        toolbar.setTitleTextColor(Color.WHITE);

    }


    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        //mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("SEARCH HERE");

    }

    public boolean onQueryTextChange(String newText) {

        String textfilter=mSearchView.getQuery().toString().toLowerCase(Locale.getDefault());
        //commande_encaissement_adapter.filter(textfilter,getApplicationContext());
        //Toast.makeText(this,textfilter, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
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
                Intent i = new Intent(CommandeEncaissementActivity.this, AuthActivity.class);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CommandeEncaissementActivity.this, ClientActivity.class);
        startActivity(i);
        finish();

    }

    public double getNumberRounded(double nombre){

        double number = nombre;
        int decimalsToConsider = 2;
        BigDecimal bigDecimal = new BigDecimal(number);
        BigDecimal roundedWithScale = bigDecimal.setScale(decimalsToConsider, BigDecimal.ROUND_HALF_UP);
        //System.out.println("Rounded value with setting scale = "+roundedWithScale);


        return roundedWithScale.doubleValue();
    }
}
