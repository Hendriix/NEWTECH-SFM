package com.newtech.newtech_sfm.AnnulerBC;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.newtech.newtech_sfm.AnnulationCommande.AnnulerCommandeAdapter;
import com.newtech.newtech_sfm.AnnulationCommande.AnnulerCommandePresenter;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier_Manager.CommandeLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.ImpressionManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

public class AnnulerBcActivity extends AppCompatActivity implements AnnulerCommandePresenter.AnnulerCommandeView, SearchView.OnQueryTextListener{

    private static final String TAG = AnnulerBcActivity.class.getName();

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
        setTitle("BONS DE COMMANDES A ANNULER");
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

                ViewBcAAnnulerActivity.commande = null;
                ViewBcAAnnulerActivity.ListeCommandeLigne.clear();

                commandeAa = new Commande(getApplicationContext(),commande,visite_code);
                for(int i=0;i<commandeLignes.size();i++){
                    CommandeLigne commandeLigne = new CommandeLigne(commandeLignes.get(i),commandeAa);
                    commandeLignesAAList.add(commandeLigne);
                }
                ViewBcAAnnulerActivity.commande =  commandeAa;
                ViewBcAAnnulerActivity.ListeCommandeLigne = commandeLignesAAList;
                ViewBcAAnnulerActivity.commandeSource = "Annuler";

                Intent intent = new Intent(AnnulerBcActivity.this, ViewBcAAnnulerActivity.class);
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
