package com.newtech.newtech_sfm.AnnulationCommande;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.newtech.newtech_sfm.Activity.ClientActivity;
import com.newtech.newtech_sfm.Activity.ViewCommandeActivity;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier_Manager.CommandeLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.ImpressionManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

public class AnnulerCommandeActivity extends AppCompatActivity implements AnnulerCommandePresenter.AnnulerCommandeView, SearchView.OnQueryTextListener{

    private static final String TAG = AnnulerCommandeActivity.class.getName();

    TextView commande_empty_tv;
    ListView listView;

    AnnulerCommandeAdapter annulerCommandeAdapter;
    ArrayList<CommandeLigne> commandeLignes = new ArrayList<>();
    SearchView mSearchView;

    private AnnulerCommandePresenter presenter;
    private ProgressDialog progressDialog;
    public static String client_code;
    public static String visite_code;

    CommandeLigneManager commandeLigneManager;
    ImpressionManager impressionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commande_a_annuler);
        setTitle("FACTURES A ANNULER");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initProgressDialog();

        commandeLigneManager = new CommandeLigneManager(getApplicationContext());

        listView = (ListView) findViewById(R.id.listview1);
        View child = getLayoutInflater().inflate(R.layout.no_data_found, null);
        ((ViewGroup)listView.getParent()).addView(child);
        listView.setEmptyView(child);

        mSearchView= findViewById(R.id.search_client);

        setupSearchView();

        presenter = new AnnulerCommandePresenter(this);
        presenter.getCommandeAAnnuler(getApplicationContext(),client_code);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void showSuccess(ArrayList<Commande> commandes) {

        progressDialog.dismiss();

        annulerCommandeAdapter = new AnnulerCommandeAdapter(this,commandes);
        listView.setAdapter(annulerCommandeAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Commande commande = (Commande)annulerCommandeAdapter.getItem(position);
                commandeLignes = commandeLigneManager.getListByCommandeCode(commande.getCOMMANDE_CODE());
                ArrayList<CommandeLigne> commandeLignesAAList = new ArrayList<>();
                Commande commandeAa = new Commande();

                ViewCommandeActivity.commande = null;
                ViewCommandeActivity.ListeCommandeLigne.clear();

                commandeAa = new Commande(getApplicationContext(),commande,ClientActivity.clientCourant,visite_code);
                for(int i=0;i<commandeLignes.size();i++){
                    CommandeLigne commandeLigne = new CommandeLigne(commandeLignes.get(i),commandeAa);
                    commandeLignesAAList.add(commandeLigne);
                }
                ViewCommandeActivity.commande =  commandeAa;
                ViewCommandeActivity.ListeCommandeLigne = commandeLignesAAList;
                ViewCommandeActivity.commandeSource = "Annuler";

                Log.d(TAG, "onItemClick: commande : "+commande);
                Log.d(TAG, "onItemClick: avoir : "+commandeAa);

                Log.d(TAG, "onItemClick: commandelignes : "+commandeLignes);
                Log.d(TAG, "onItemClick: avoir : "+commandeLignesAAList);

                Intent intent = new Intent(AnnulerCommandeActivity.this, ViewCommandeActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    @Override
    public void showError(String message) {
        progressDialog.dismiss();
        showMessage(message);
    }

    @Override
    public void showEmpty(String message) {
        progressDialog.dismiss();
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    private void initProgressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Chargement en cours");
    }

    private void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, ClientActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
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
}
