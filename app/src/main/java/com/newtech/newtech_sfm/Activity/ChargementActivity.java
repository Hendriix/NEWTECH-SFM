package com.newtech.newtech_sfm.Activity;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.newtech.newtech_sfm.Configuration.Demande_Adapter;
import com.newtech.newtech_sfm.Configuration.StockDemandeViewModel;
import com.newtech.newtech_sfm.Metier.StockDemande;
import com.newtech.newtech_sfm.Metier.StockDemandeLigne;
import com.newtech.newtech_sfm.Metier_Manager.StatutManager;
import com.newtech.newtech_sfm.Metier_Manager.StockDemandeLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.StockDemandeManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by csaylani on 12/04/2018.
 */

public class ChargementActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ListView StockDemandesList;
    ArrayList<StockDemande> stockDemandes;
    Demande_Adapter demande_adapter;
    StockDemandeManager stockDemandeManager;
    StockDemandeLigneManager stockDemandeLigneManager;
    StatutManager statutManager;
    ArrayList<StockDemandeLigne> stockDemandeLignes;

    public static String commandeSource;
    public static String commandeType;
    SearchView mSearchView;
    SwipeRefreshLayout pullToRefresh;
    SearchManager searchmanager;

    StockDemandeViewModel stockDemandeViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demande_chargement);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_client);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        stockDemandeManager = new StockDemandeManager(getApplicationContext());
        stockDemandeLigneManager = new StockDemandeLigneManager(getApplicationContext());
        statutManager = new StatutManager(getApplicationContext());

        searchmanager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView=(SearchView) findViewById(R.id.chargement_search_client);
        pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.pullToRefresh);

        stockDemandeViewModel = ViewModelProviders.of(this).get(StockDemandeViewModel.class);
        setTitle(stockDemandeManager.DemandeTitle(commandeSource));



        stockDemandes = stockDemandeManager.getListByTypeDemande(commandeSource);
        StockDemandesList = (ListView)findViewById(R.id.demande_listview);

        View child = getLayoutInflater().inflate(R.layout.no_data_found, null);
        ((ViewGroup)StockDemandesList.getParent()).addView(child);

        StockDemandesList.setEmptyView(child);

        pullToRefresh.setRefreshing(true);

        getListStockDemande();
        //demande_adapter = new Demande_Adapter(this, stockDemandes);

        StockDemandesList.setTextFilterEnabled(true);
        setupSearchView();

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //Toast.makeText(ChargementActivity.this,"PATIENTEZ",Toast.LENGTH_SHORT).show();
                //demande_adapter.notifyDataSetChanged();
                //pullToRefresh.setRefreshing(false);

                getListStockDemande();

            }
        });

        StockDemandesList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (StockDemandesList.getChildAt(0) != null) {
                    pullToRefresh.setEnabled(StockDemandesList.getFirstVisiblePosition() == 0 && StockDemandesList.getChildAt(0).getTop() == 0);
                }
            }
        });

        //StockDemandesList.setAdapter(demande_adapter);

        StockDemandesList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                StockDemande stockDemande =(StockDemande) demande_adapter.getItem(position);


                try{
                    String statut = statutManager.get(stockDemande.getSTATUT_CODE()).getSTATUT_NOM();
                    Toast.makeText(ChargementActivity.this,"STATUT :"+statut,Toast.LENGTH_LONG).show();
                }catch (NullPointerException e){
                    Toast.makeText(ChargementActivity.this,"STATUT :"+stockDemande.getSTATUT_CODE(),Toast.LENGTH_LONG).show();
                }

                if(stockDemande.getSTATUT_CODE().equals("32")){

                    stockDemandeLignes = stockDemandeLigneManager.getListByDemandeCode(stockDemande.getDEMANDE_CODE());

                    ViewReceptionChargementActivity.stockDemande=null;
                    ViewReceptionChargementActivity.stockDemandeLignes.clear();
                    ViewReceptionChargementActivity.commandeSource = null;
                    ViewReceptionChargementActivity.commandeType = null;


                    ViewReceptionChargementActivity.stockDemande=stockDemande;
                    ViewReceptionChargementActivity.stockDemandeLignes=stockDemandeLignes;
                    ViewReceptionChargementActivity.commandeSource = stockDemande.getTYPE_CODE();
                    ViewReceptionChargementActivity.commandeType = "Reception";

                    Intent intent = new Intent(getApplicationContext(),ViewReceptionChargementActivity.class);
                    intent.putExtra("DEMANDE_CODE",stockDemande.getDEMANDE_CODE());
                    startActivity(intent);
                    finish();


                }else{
                    Toast.makeText(ChargementActivity.this,"DEMANDE DOIT ETRE LIVREE POUR POUVOIR LA RECEPTIONNER",Toast.LENGTH_LONG).show();
                }

            }
        });

        StockDemandesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.d("Slow Down It Hurts", "onItemLongClick: ");
                ChargementPopUpActivity.demande_code = "";
                Intent intent = new Intent(ChargementActivity.this,ChargementPopUpActivity.class);
                StockDemande stockDemande =(StockDemande) demande_adapter.getItem(i);
                ChargementPopUpActivity.demande_code = stockDemande.getDEMANDE_CODE();
                startActivity(intent);
                //finish();
                return true;
            }
        });

        /*StockDemandesList.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                Toast.makeText(ChargementActivity.this,"Slow down it hurts",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(ChargementActivity.this,ChargementPopUpActivity.class);
                //startActivity(intent);
            }
        });*/

    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        //mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("SEARCH HERE");

    }

    public void getListStockDemande() {

        //logSyncManager.deleteLogs();
        if(isNetworkAvailable()) {

            StockDemandeManager.synchronisationStockDemandePull(getApplicationContext());
            StockDemandeLigneManager.synchronisationStockDemandeLignePull(getApplicationContext());

            //ArrayList<StockDemande> stockDemandes = new ArrayList<>();
            //stockDemandeViewModel.clearListMutableLiveData(logSyncs);
            // Create the observer which updates the UI.
            final Observer<ArrayList<StockDemande>> listObserver = new Observer<ArrayList<StockDemande>>() {
                @Override
                public void onChanged(@Nullable final ArrayList<StockDemande> stockDemandes1) {
                    // Update the UI, in this case, a TextView.
                    demande_adapter = new Demande_Adapter(ChargementActivity.this, stockDemandes1);
                    StockDemandesList.setAdapter(demande_adapter);
                    demande_adapter.notifyDataSetChanged();
                    pullToRefresh.setRefreshing(false);

                }
            };

            stockDemandeViewModel.getListStockDemande(commandeSource).observe(this, listObserver);

        }else{

            pullToRefresh.setRefreshing(false);
            demande_adapter = new Demande_Adapter(ChargementActivity.this, stockDemandes);
            StockDemandesList.setAdapter(demande_adapter);
            demande_adapter.notifyDataSetChanged();
            showCustomDialog();
            //Toast.makeText(SyncV2Activity.this, "Vérifier votre connexion internet puis synchroniser", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public boolean onQueryTextChange(String newText) {

        String textfilter=mSearchView.getQuery().toString().toLowerCase(Locale.getDefault());
        demande_adapter.filter(textfilter);
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

            case R.id.plus:
                    ViewChargementActivity.commandeSource=null;
                    ViewChargementActivity.stockDemande=null;
                    ViewChargementActivity.stockDemandeLignes.clear();

                    ViewChargementActivity.commandeSource=ChargementActivity.commandeSource;
                    Intent intent = new Intent(this, ViewChargementActivity.class);
                    startActivity(intent);
                    finish();

                    Toast.makeText(ChargementActivity.this,"CREATION CHARGEMENT",Toast.LENGTH_LONG).show();

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();

        Intent intent = new Intent(ChargementActivity.this,StockActivity.class);
        startActivity(intent);
        finish();


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.synchronisation_dialog, viewGroup, false);
        Button confirmer = (Button) dialogView.findViewById(R.id.confirmer);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);


        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"ANNULER", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

    }



}
