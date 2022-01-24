package com.newtech.newtech_sfm.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.newtech.newtech_sfm.Configuration.Credit_Adapter;
import com.newtech.newtech_sfm.Metier.Credit;
import com.newtech.newtech_sfm.Metier_Manager.CreditManager;
import com.newtech.newtech_sfm.Metier_Manager.StatutManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by csaylani on 12/04/2018.
 */

public class CreditActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ListView CreditList;
    ArrayList<Credit> credits;
    Credit_Adapter credit_adapter;
    CreditManager creditManager;
    
    StatutManager statutManager;
    

    public static String commandeSource;
    public static String commandeType;
    
    SearchView mSearchView;
    SwipeRefreshLayout pullToRefresh;

    SearchManager searchmanager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_activity);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_client);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        creditManager = new CreditManager(getApplicationContext());
        statutManager = new StatutManager(getApplicationContext());

        searchmanager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView=(SearchView) findViewById(R.id.credit_search);


        setTitle("ENCAISSEMENT CREDIT");



        //credits = creditManager.getListCreditAEncaisser();
        credits = creditManager.getListCreditAEncaisse();

        CreditList = (ListView)findViewById(R.id.credit_listview);
        View child = getLayoutInflater().inflate(R.layout.no_data_found, null);
        ((ViewGroup)CreditList.getParent()).addView(child);

        CreditList.setEmptyView(child);

        credit_adapter = new Credit_Adapter(this, credits);

        CreditList.setTextFilterEnabled(true);
        setupSearchView();

        CreditList.setAdapter(credit_adapter);

        CreditList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CreditEncaissementActivity.credit=null;
                Credit credit =(Credit) credit_adapter.getItem(position);
                CreditEncaissementActivity.credit = credit;

                Intent intent = new Intent(getApplicationContext(),CreditEncaissementActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        //mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("SEARCH HERE");

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public boolean onQueryTextChange(String newText) {

        String textfilter=mSearchView.getQuery().toString().toLowerCase(Locale.getDefault());
        credit_adapter.filter(textfilter);
        //Toast.makeText(this,textfilter, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_panier, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                this.onBackPressed();
                return true;

                    //Toast.makeText(ChargementActivity.this,"CREATION CHARGEMENT",Toast.LENGTH_LONG).show();

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();

        Intent intent = new Intent(CreditActivity.this,MenuActivity.class);
        startActivity(intent);
        finish();


    }


}
