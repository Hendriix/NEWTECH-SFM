package com.newtech.newtech_sfm.AnnulationLivraison;

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
import com.newtech.newtech_sfm.Activity.ViewLivraisonActivity;
import com.newtech.newtech_sfm.Metier.Livraison;
import com.newtech.newtech_sfm.Metier.LivraisonLigne;
import com.newtech.newtech_sfm.Metier_Manager.ImpressionManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonLigneManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

public class AnnulerLivraisonActivity extends AppCompatActivity implements AnnulerLivraisonPresenter.AnnulerLivraisonView, SearchView.OnQueryTextListener{

    private static final String TAG = AnnulerLivraisonActivity.class.getName();

    TextView commande_empty_tv;
    ListView listView;

    AnnulerLivraisonAdapter annulerLivraisonAdapter;
    ArrayList<LivraisonLigne> livraisonLignes = new ArrayList<>();
    SearchView mSearchView;

    private AnnulerLivraisonPresenter presenter;
    private ProgressDialog progressDialog;
    public static String client_code;
    public static String visite_code;

    LivraisonLigneManager livraisonLigneManager;
    ImpressionManager impressionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commande_a_annuler);
        setTitle("LIVRAISON A ANNULER");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initProgressDialog();

        livraisonLigneManager = new LivraisonLigneManager(getApplicationContext());

        listView = (ListView) findViewById(R.id.listview1);
        View child = getLayoutInflater().inflate(R.layout.no_data_found, null);
        ((ViewGroup)listView.getParent()).addView(child);
        listView.setEmptyView(child);

        mSearchView= findViewById(R.id.search_client);

        setupSearchView();

        presenter = new AnnulerLivraisonPresenter(this);
        presenter.getLivraisonAAnnuler(getApplicationContext(),client_code);
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
    public void showSuccess(ArrayList<Livraison> livraisons) {

        progressDialog.dismiss();

        annulerLivraisonAdapter = new AnnulerLivraisonAdapter(this,livraisons);
        listView.setAdapter(annulerLivraisonAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Livraison livraison = (Livraison) annulerLivraisonAdapter.getItem(position);
                livraisonLignes = livraisonLigneManager.getListByLivraisonCode(livraison.getLIVRAISON_CODE());
                ArrayList<LivraisonLigne> livraisonLignesAa = new ArrayList<>();
                Livraison livraisonAa = new Livraison();

                ViewLivraisonActivity.commande = null;
                ViewLivraisonActivity.ListeCommandeLigne.clear();
                ViewLivraisonActivity.livraison = null;
                ViewLivraisonActivity.livraisonLignes.clear();

                livraisonAa = new Livraison(getApplicationContext(),livraison,ClientActivity.clientCourant,visite_code);
                for(int i=0;i<livraisonLignes.size();i++){
                    LivraisonLigne livraisonLigne = new LivraisonLigne(livraisonLignes.get(i),livraisonAa);
                    livraisonLignesAa.add(livraisonLigne);
                }
                ViewLivraisonActivity.livraison =  livraisonAa;
                ViewLivraisonActivity.livraisonLignes = livraisonLignesAa;
                ViewLivraisonActivity.commandeSource = "Annuler";

                Log.d(TAG, "onItemClick: commande : "+livraison);
                Log.d(TAG, "onItemClick: avoir : "+livraisonAa);

                Log.d(TAG, "onItemClick: commandelignes : "+livraisonLignes);
                Log.d(TAG, "onItemClick: avoir : "+livraisonLignesAa);

                Intent intent = new Intent(AnnulerLivraisonActivity.this, ViewLivraisonActivity.class);
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
