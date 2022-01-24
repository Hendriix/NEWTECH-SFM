package com.newtech.newtech_sfm.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.newtech.newtech_sfm.Configuration.Stock_Ligne_Adapter;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.StockLigne;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.Metier_Manager.ImpressionManager;
import com.newtech.newtech_sfm.Metier_Manager.StockLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.StockManager;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.Service.BlutoothConnctionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StockLigneActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private static final String TAG = StockLigneActivity.class.getName();
    SearchView mSearchView;
    Stock_Ligne_Adapter stock_ligne_adapter;
    List<Article> articles = new ArrayList<Article>();
    ListView SlListView;
    StockLigneManager stockLigneManager;
    ArticleManager articleManager;
    SwipeRefreshLayout pullToRefresh;
    ArrayList<StockLigne> stockLignes;
    Button imprimer_rs;
    ImpressionManager impressionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_ligne_panier);

        Toolbar toolbar = (Toolbar) findViewById(R.id.sl_toolbar);
        imprimer_rs = findViewById(R.id.print_btn);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        articleManager = new ArticleManager(getApplicationContext());
        stockLigneManager = new StockLigneManager(getApplicationContext());
        impressionManager = new ImpressionManager(getApplicationContext());

        stockLigneManager.updateStockLigneQteVersion(getApplicationContext());
        mSearchView=(SearchView) findViewById(R.id.tache_search);
        articles=articleManager.getListJoinStockLigne();
        stockLignes = stockLigneManager.getList();

        SlListView=(ListView) findViewById(R.id.stockligne_list);

        pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.pullToRefresh);

        View child = getLayoutInflater().inflate(R.layout.no_data_found, null);
        ((ViewGroup)SlListView.getParent()).addView(child);

        SlListView.setEmptyView(child);
        stock_ligne_adapter=new Stock_Ligne_Adapter(this,articles,getApplicationContext());
        setupSearchView();

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //Toast.makeText(StockLigneActivity.this,"PATIENTEZ",Toast.LENGTH_SHORT).show();
                //StockLigneManager.synchronisationStockLigne(getApplicationContext());
                if(isNetworkAvailable()){

                    pullToRefresh.setRefreshing(false);
                    StockManager.synchronisationStock(getApplicationContext());
                    stock_ligne_adapter.notifyDataSetChanged();

                }else{

                    pullToRefresh.setRefreshing(false);
                    //Toast.makeText(StockActivity.this,"Vérifier votre connexion internet puis synchroniser",Toast.LENGTH_LONG).show();
                    showCustomDialog();
                }

            }
        });

        SlListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (SlListView.getChildAt(0) != null) {
                    pullToRefresh.setEnabled(SlListView.getFirstVisiblePosition() == 0 && SlListView.getChildAt(0).getTop() == 0);
                }
            }
        });

        SlListView.setAdapter(stock_ligne_adapter);

        imprimer_rs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if(stockLignes.size() > 0){

                    final Dialog dialog1 = new Dialog(view.getContext());
                    dialog1.setContentView(R.layout.alert_imprimante);
                    dialog1.setTitle("IMPRESSION");
                    dialog1.setCanceledOnTouchOutside(false);
                    Button print = (Button) dialog1.findViewById(R.id.btn_print);
                    Button done = (Button) dialog1.findViewById(R.id.done_print);
                    final TextView nbr_copies = (TextView) dialog1.findViewById(R.id.nbr_copies);

                    print.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            //Toast.makeText(ViewChargementActivity.this,"IMPRIMER",Toast.LENGTH_LONG).show();

                            //Log.d("CommandeLignevalider", "onClick: "+stockDemande.getDEMANDE_CODE());
                            boolean printed=false;
                            String impression_text=impressionManager.ImprimerStockLignes(stockLignes,getApplicationContext());
                            Log.d(TAG, "onClick: "+impression_text);
                            for(int i=0;i<Integer.valueOf(nbr_copies.getText().toString());i++){

                                printed= BlutoothConnctionService.imprimanteManager.printText(impression_text);

                            }

                        }
                    });


                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();
                        }

                    });

                    dialog1.show();


                }else{
                    Toast.makeText(getApplicationContext(),"AUCUNE LIGNE A IMPRIMER" , Toast.LENGTH_LONG).show();
                }


            }
        });

        setTitle("STOCK LIGNES");

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
                Intent i = new Intent(StockLigneActivity.this, AuthActivity.class);
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

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        //mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("SEARCH HERE");

    }

    public boolean onQueryTextChange(String newText) {

        String textfilter=mSearchView.getQuery().toString().toLowerCase(Locale.getDefault());
        stock_ligne_adapter.filter(textfilter);
        //Toast.makeText(this,textfilter, Toast.LENGTH_LONG).show();
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(StockLigneActivity.this, StockActivity.class);
        startActivity(i);
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
