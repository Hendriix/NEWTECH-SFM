package com.newtech.newtech_sfm.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.newtech.newtech_sfm.Configuration.Commande_Livraison_Adapter;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier.CommandeNonCloturee;
import com.newtech.newtech_sfm.Metier.CommandeNonClotureeLigne;
import com.newtech.newtech_sfm.Metier.Livraison;
import com.newtech.newtech_sfm.Metier.LivraisonLigne;
import com.newtech.newtech_sfm.Metier.User;
import com.newtech.newtech_sfm.Metier_Manager.CommandeNonClotureeLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeNonClotureeManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonManager;
import com.newtech.newtech_sfm.Metier_Manager.UserManager;
import com.newtech.newtech_sfm.R;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by TONPC on 07/04/2017.
 */

public class CommandeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    SearchView mSearchView;
    Commande_Livraison_Adapter commande_livraison_adapter;
    String livraison_code ="";

    List<CommandeNonCloturee> commandeNonCloturees = new ArrayList<CommandeNonCloturee>();
    List<Commande> commandes = new ArrayList<Commande>();
    ArrayList<Livraison> livraisons = new ArrayList<>();
    Client client = new Client();
    User user;
    Livraison livraison;

    ArrayList<CommandeNonClotureeLigne> commandeNonClotureeLignes = new ArrayList<CommandeNonClotureeLigne>();
    ArrayList<CommandeLigne> commandeLignes = new ArrayList<CommandeLigne>();
    ArrayList<LivraisonLigne> livraisonLignes = new ArrayList<>();
    ArrayList<LivraisonLigne> livraisonLignesAL = new ArrayList<>();




    LivraisonManager livraisonManager;
    LivraisonLigneManager livraisonLigneManager;
    CommandeNonClotureeManager commandeNonClotureeManager;
    CommandeNonClotureeLigneManager commandeNonClotureeLigneManager;
    UserManager userManager;
    ArrayList<Livraison> livraison_list = new ArrayList<Livraison>();


    ListView mListView1;
    SearchManager searchmanager;
    private ProgressBar mProgressBar;
    private CheckBox vaCheckBox1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commande_activity);

        livraisonManager= new LivraisonManager(getApplicationContext());
        livraisonLigneManager = new LivraisonLigneManager(getApplicationContext());
        commandeNonClotureeManager = new CommandeNonClotureeManager(getApplicationContext());
        commandeNonClotureeLigneManager = new CommandeNonClotureeLigneManager(getApplicationContext());
        userManager = new UserManager(getApplicationContext());
        client = ClientActivity.clientCourant;


        livraison_code = "livraison_code";

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_client);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchmanager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView=(SearchView) findViewById(R.id.commande_search);
        mProgressBar = (ProgressBar) findViewById(R.id.va_progressBar1);
        vaCheckBox1=(CheckBox) findViewById(R.id.va_checkBox1);
        mListView1 = (ListView) findViewById(R.id.commande_listview1);


        user= userManager.get();
        livraison_list=livraisonManager.getList();
        //commandeALivrer_list=commandeALivreeManager.getListByCmdSC("5");//Les Commande Non livrees ou livrees partiellement
        commandeNonCloturees=commandeNonClotureeManager.getListALByClientCode(ClientActivity.clientCourant.getCLIENT_CODE());

        for(int i=0;i<commandeNonCloturees.size();i++){
            commandes.add(new Commande(commandeNonCloturees.get(i)));
        }


        commande_livraison_adapter = new Commande_Livraison_Adapter(this,commandes);
        mListView1.setAdapter(commande_livraison_adapter);
        mListView1.setTextFilterEnabled(true);
        setupSearchView();

        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ViewLivraisonActivity.commande=null;
                ViewLivraisonActivity.ListeCommandeLigne.clear();

                ViewLivraisonActivity.livraisonLignes.clear();
                ViewLivraisonActivity.livraison = null;
                ViewLivraisonActivity.commandeSource = null;

                Commande commande =(Commande)commande_livraison_adapter.getItem(position);
                livraisons = livraisonManager.getListByCmdCode(commande.getCOMMANDE_CODE());
                livraisonLignes = livraisonLigneManager.getListByCmdCode(commande.getCOMMANDE_CODE());

                SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
                String date_Cmdlivraison=df.format(new java.util.Date());
                String LIVRAISON_CODE=client.getDISTRIBUTEUR_CODE()+user.getUTILISATEUR_CODE()+date_Cmdlivraison;

                livraison = new Livraison(commande,LIVRAISON_CODE,getApplicationContext(),ClientActivity.clientCourant,ClientActivity.gps_latitude,ClientActivity.gps_longitude);

                if(commande.getCOMMANDESTATUT_CODE().equals("5")){
                    Toast.makeText(getApplicationContext(),"CETTE COMMANDE EST LIVREE",Toast.LENGTH_SHORT).show();

                }else{

                    //Toast.makeText(getApplicationContext(),"JE SUIS UNE COMMANDE HOHO" , Toast.LENGTH_LONG).show();
                    commandeNonClotureeLignes = commandeNonClotureeLigneManager.getListByCommandeCode(commande.getCOMMANDE_CODE());

                    for(int i=0;i<commandeNonClotureeLignes.size();i++){
                        commandeLignes.add(new CommandeLigne(commandeNonClotureeLignes.get(i)));
                    }

                    livraisonLignesAL=livraisonLigneManager.getListALivrer(commandeLignes,livraisonLignes,LIVRAISON_CODE,getApplicationContext());

                    ViewLivraisonActivity.commande=commande;
                    ViewLivraisonActivity.ListeCommandeLigne = commandeLignes;

                    ViewLivraisonActivity.livraisonLignes = livraisonLignesAL;
                    ViewLivraisonActivity.livraison = livraison;
                    ViewLivraisonActivity.commandeSource = "Livraison";

                    Intent intent = new Intent(getApplicationContext(),ViewLivraisonActivity.class);
                    intent.putExtra("COMMANDE_CODE",commande.getCOMMANDE_CODE());
                    startActivity(intent);
                    finish();
                }

            }
        });

        setTitle("COMMANDES A LIVRER");
        toolbar.setTitleTextColor(Color.WHITE);

    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        //mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("SEARCH HERE");

    }

    public boolean onQueryTextChange(String newText) {

        String textfilter=mSearchView.getQuery().toString().toLowerCase(Locale.getDefault());
        commande_livraison_adapter.filter(textfilter,getApplicationContext());
        //Toast.makeText(this,textfilter, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
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
                Intent i = new Intent(CommandeActivity.this, AuthActivity.class);
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





    public void onCheckboxClicked(View view) {
        Toast.makeText(getApplicationContext(),"CHECKED" , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CommandeActivity.this, ClientActivity.class);
        startActivity(i);
        finish();

    }

    public double getNumberRounded(double nombre){

        double number = nombre;
        int decimalsToConsider = 2;
        BigDecimal bigDecimal = new BigDecimal(number);
        BigDecimal roundedWithScale = bigDecimal.setScale(decimalsToConsider, BigDecimal.ROUND_HALF_UP);
        //System.out.println("Rounded value with setting scale = "+roundedWithScale);


        return roundedWithScale.doubleValue();
    }
}
