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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.newtech.newtech_sfm.Configuration.ListDataSave;
import com.newtech.newtech_sfm.Configuration.Tourne_Adapter;
import com.newtech.newtech_sfm.Metier.Tournee;
import com.newtech.newtech_sfm.Metier_Manager.TourneeManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by TONPC on 20/09/2017.
 */

public class TourneeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{


    SearchView mSearchView;
    Tourne_Adapter tourne_adapter;
    List<Tournee> tourneeList = new ArrayList<Tournee>();
    ListView mListView1;
    TourneeManager tourneeManager;
    ListDataSave listDataSave;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tourne_catalogue);
        tourneeManager = new TourneeManager(getApplicationContext());
        listDataSave = new ListDataSave(getApplicationContext(),"MyPref");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSearchView=(SearchView) findViewById(R.id.tournee_search);
        mListView1=(ListView) findViewById(R.id.tourne_listview1);

        tourneeList = tourneeManager.getList();


        tourne_adapter=new Tourne_Adapter(this,tourneeList);
        mListView1.setAdapter(tourne_adapter);

        View child = getLayoutInflater().inflate(R.layout.no_data_found, null);
        ((ViewGroup)mListView1.getParent()).addView(child);

        mListView1.setEmptyView(child);
        //mListView1.setTextFilterEnabled(true);

        mListView1.setTextFilterEnabled(true);
        setupSearchView();

        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              Tournee tournee = tourneeList.get(position);
              Intent intent = new Intent(TourneeActivity.this, VisiteActivity.class);
              VisiteActivity.commande_source="VISITE";
              VisiteActivity.activity_source="TourneeActivity";
              VisiteActivity.affectation_valeur=tournee.getTOURNEE_CODE();
              listDataSave.remove("client_list");
              listDataSave.remove("tournee_code");
              listDataSave.remove("type_code");
              listDataSave.remove("classe_code");
              listDataSave.remove("categorie_code");
              //intent.putExtra("TOURNEE_CODE",tournee.getTOURNEE_CODE());
              startActivity(intent);
              finish();

              }
          }

        );

        setTitle("LES TOURNEES");
    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        //mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("SEARCH HERE");

    }

    public boolean onQueryTextChange(String newText) {

        String textfilter=mSearchView.getQuery().toString().toLowerCase(Locale.getDefault());
        tourne_adapter.filter(textfilter);
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
                Intent i = new Intent(TourneeActivity.this, AuthActivity.class);
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
