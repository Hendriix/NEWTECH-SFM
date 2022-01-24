package com.newtech.newtech_sfm.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.newtech.newtech_sfm.Configuration.CommandeGratuite_Adapter;
import com.newtech.newtech_sfm.Metier.CommandeGratuite;
import com.newtech.newtech_sfm.Metier_Manager.CommandeGratuiteManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

/**
 * Created by TONPC on 14/07/2017.
 */

public class CommandeGratuiteActivity extends AppCompatActivity {

    ListView mListView;
    CommandeGratuite_Adapter commandeGratuite_adapter;
    ArrayList<CommandeGratuite> commandeGratuites= new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.commandegratuite);
        setTitle("COMMANDE GRATUITE");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView = (ListView) findViewById(R.id.listcommandegrat);

        CommandeGratuiteManager commandeGratuiteManager = new CommandeGratuiteManager(getApplicationContext());

        commandeGratuites = commandeGratuiteManager.getListByClientCode(ClientActivity.clientCourant.getCLIENT_CODE());
       // Toast.makeText(CommandeGratuiteActivity.this,"list size"+commandeGratuites.size(),
                //Toast.LENGTH_LONG).show();

        commandeGratuite_adapter=new CommandeGratuite_Adapter(this,commandeGratuites);
        mListView.setAdapter(commandeGratuite_adapter);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
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
                Intent i = new Intent(CommandeGratuiteActivity.this, AuthActivity.class);
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
