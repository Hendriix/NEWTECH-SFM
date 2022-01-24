package com.newtech.newtech_sfm.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.newtech.newtech_sfm.R;

/**
 * Created by stagiaireit2 on 26/07/2016.
 */
public class CatalogueTourneeActivity extends AppCompatActivity {
    //private String utilisateur_code="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalogue_article_view);

        //GET UTILISATEUR CODE FROM MENUACTIVITY


        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TourneeManager tourneeManager = new TourneeManager(getApplicationContext());
        //RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RVAdapter_Tournees adapter = new RVAdapter_Tournees(tourneeManager.getList());
        //adapter.setUtilisateur_code(utilisateur_code);
        rv.setAdapter(adapter);
        setTitle("LES TOURNEES");*/

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
                Intent i = new Intent(CatalogueTourneeActivity.this, AuthActivity.class);
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
        Intent i = new Intent(CatalogueTourneeActivity.this, MenuActivity.class);
        startActivity(i);
        finish();
    }

}
