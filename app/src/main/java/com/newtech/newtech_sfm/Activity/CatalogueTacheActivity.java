package com.newtech.newtech_sfm.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.newtech.newtech_sfm.Configuration.ListDataSave;
import com.newtech.newtech_sfm.Configuration.Tache_Adapter;
import com.newtech.newtech_sfm.Metier.Tache;
import com.newtech.newtech_sfm.Metier_Manager.TacheManager;
import com.newtech.newtech_sfm.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by TONPC on 13/03/2017.
 */

public class CatalogueTacheActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    SearchView mSearchView;
    Tache_Adapter tache_adapter;
    List<Tache> tache_list = new ArrayList<Tache>();
    //List<TachePlanification> tacheplanification_list=new ArrayList<>();
    ListView mListView1;
    ListDataSave listDataSave;
    TacheManager tacheManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tache_catalogue);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSearchView=(SearchView) findViewById(R.id.tache_search);

        tacheManager= new TacheManager(getApplicationContext());
        listDataSave = new ListDataSave(getApplicationContext(),"MyPref");
        //TachePlanificationManager tachePlanificationManager=new TachePlanificationManager(getApplicationContext());


        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        String date=df1.format(new Date());

        tache_list=tacheManager.getListByDate(date);

        //Toast.makeText(CatalogueTacheActivity.this,String.valueOf(tache_list.size()),
                //Toast.LENGTH_LONG).show();

        mListView1=(ListView) findViewById(R.id.tache_listview1);

        View child = getLayoutInflater().inflate(R.layout.no_data_found, null);
        ((ViewGroup)mListView1.getParent()).addView(child);

        mListView1.setEmptyView(child);

        tache_adapter=new Tache_Adapter(this,tache_list);
        mListView1.setAdapter(tache_adapter);
        mListView1.setTextFilterEnabled(true);
        setupSearchView();

        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();

                Tache tache = (Tache)tache_adapter.getItem(position); 
                listDataSave.remove("client_list");
                listDataSave.remove("tournee_code");
                listDataSave.remove("type_code");
                listDataSave.remove("classe_code");
                listDataSave.remove("categorie_code");

                if(tache.getTYPE_CODE().equals("LIVRAISON")){
                    Toast.makeText(CatalogueTacheActivity.this,"LIVRAISON",
                            Toast.LENGTH_LONG).show();

                    intent = new Intent(CatalogueTacheActivity.this, com.newtech.newtech_sfm.Livraison.LivraisonDateActivity.class);
                    //Log.d("afftectation valeur", "onItemClick: "+tache.getAFFECTATION_VALEUR());
                    intent.putExtra("TOURNEE_CODE",tache.getAFFECTATION_VALEUR());
                    com.newtech.newtech_sfm.Livraison.LivraisonDateActivity.activity_source="CatalogueTacheActivity";
                    com.newtech.newtech_sfm.Livraison.LivraisonDateActivity.tache_code=tache.getTACHE_CODE();
                    com.newtech.newtech_sfm.Livraison.LivraisonDateActivity.affectation_type=tache.getAFFECTATION_TYPE();
                    com.newtech.newtech_sfm.Livraison.LivraisonDateActivity.affectation_valeur=tache.getAFFECTATION_VALEUR();
                    com.newtech.newtech_sfm.Livraison.LivraisonDateActivity.commande_source="LIVRAISON";

                    startActivity(intent);
                    finish();

                }else if(tache.getTYPE_CODE().equals("ENCAISSEMENT")){
                    intent = new Intent(CatalogueTacheActivity.this, LivraisonDateActivity.class);

                    intent.putExtra("TOURNEE_CODE",tache.getAFFECTATION_VALEUR());
                    intent.putExtra("AFFECTATION_TYPE",tache.getAFFECTATION_TYPE());
                    LivraisonDateActivity.activity_source="CatalogueTacheActivity";
                    LivraisonDateActivity.tache_code=tache.getTACHE_CODE();
                    LivraisonDateActivity.affectation_type=tache.getAFFECTATION_TYPE();
                    LivraisonDateActivity.affectation_valeur=tache.getAFFECTATION_VALEUR();
                    LivraisonDateActivity.commande_source="ENCAISSEMENT";

                    startActivity(intent);
                    finish();

                }else if(tache.getTYPE_CODE().equals("VISITE")){
                    intent = new Intent(CatalogueTacheActivity.this, VisiteActivity.class);

                    VisiteActivity.commande_source="VISITE";
                    VisiteActivity.activity_source="CatalogueTacheActivity";
                    VisiteActivity.affectation_type=tache.getAFFECTATION_TYPE();
                    VisiteActivity.affectation_valeur=tache.getAFFECTATION_VALEUR();
                    VisiteActivity.tache_code=tache.getTACHE_CODE();
                    listDataSave.remove("client_list");
                    listDataSave.remove("tournee_code");
                    listDataSave.remove("type_code");
                    listDataSave.remove("classe_code");
                    listDataSave.remove("categorie_code");

                    

                }else{
                    Toast.makeText(CatalogueTacheActivity.this,"AUTRE",
                            Toast.LENGTH_LONG).show();
                }

                startActivity(intent);
                finish();


            }
        });

        setTitle("LES TACHES DU JOUR");
    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        //mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("SEARCH HERE");

    }

    public boolean onQueryTextChange(String newText) {

        String textfilter=mSearchView.getQuery().toString().toLowerCase(Locale.getDefault());
        tache_adapter.filter(textfilter);
        //Toast.makeText(this,textfilter, Toast.LENGTH_LONG).show();
        return true;
    }


    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            case R.id.add_client:
                Intent intent=new Intent(this,Client_Manager.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //finish();
                return true;
            case R.id.logout:
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(CatalogueTacheActivity.this, AuthActivity.class);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_client, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }

}
