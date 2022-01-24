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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.newtech.newtech_sfm.Configuration.Commande_Ligne_Livraison_Adapter;
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier_Manager.CommandeLigneManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TONPC on 07/04/2017.
 */

public class CommandeLigneActivity extends AppCompatActivity {

    SearchView mSearchView;
    Commande_Ligne_Livraison_Adapter commande_Ligne_livraison_adapter;
    List<CommandeLigne> commandeLigne_list = new ArrayList<>();
    ListView mListView1;
    SearchManager searchmanager;
    public static String commande_code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commandeligne_panier_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_client);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchmanager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        mSearchView=(SearchView) findViewById(R.id.commandeligne_search);

        mListView1 = (ListView) findViewById(R.id.commandeligne_listview1);
        Toast.makeText(getApplicationContext(),"COMMANDE CODE"+commande_code , Toast.LENGTH_LONG).show();

        CommandeLigneManager commandeLigneManager = new CommandeLigneManager(getApplicationContext());
        commandeLigne_list= commandeLigneManager.getListByCommandeCode(commande_code);


        commande_Ligne_livraison_adapter = new Commande_Ligne_Livraison_Adapter(this,commandeLigne_list);
        mListView1.setAdapter(commande_Ligne_livraison_adapter);
        mListView1.setTextFilterEnabled(true);
        //setupSearchView();

        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommandeLigne commandeligne = (CommandeLigne)commande_Ligne_livraison_adapter.getItem(position);

            }
        });

        setTitle("COMMANDELIGNE A LIVRER");
        toolbar.setTitleTextColor(Color.WHITE);

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
                Intent i = new Intent(CommandeLigneActivity.this, AuthActivity.class);
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

    public void onCheckboxClicked(View view) {
        Toast.makeText(getApplicationContext(),"CHECKED" , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CommandeLigneActivity.this, CommandeActivity.class);
        startActivity(i);
        finish();

    }
}
