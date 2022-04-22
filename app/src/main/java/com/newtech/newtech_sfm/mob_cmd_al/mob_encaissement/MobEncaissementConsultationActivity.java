package com.newtech.newtech_sfm.mob_cmd_al.mob_encaissement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.newtech.newtech_sfm.Activity.AuthActivity;
import com.newtech.newtech_sfm.Activity.CommandeEncaissementActivity;
import com.newtech.newtech_sfm.Activity.EncaissementActivity;
import com.newtech.newtech_sfm.Activity.PrintActivity2;
import com.newtech.newtech_sfm.Configuration.EncaissementConsultationAdapter;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier.Encaissement;
import com.newtech.newtech_sfm.Metier_Manager.EncaissementManager;
import com.newtech.newtech_sfm.Metier_Manager.ImpressionManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

/**
 * Created by TONPC on 02/08/2017.
 */

public class MobEncaissementConsultationActivity extends AppCompatActivity {

    ListView mListView;
    EncaissementConsultationAdapter encaissementConsultationAdapter;

    public static Commande commande = new Commande();
    public static  ArrayList<CommandeLigne> ListeCommandeLigne = new ArrayList<>();
    public static ImpressionManager impressionManager;
    public static ArrayList<Encaissement> encaissements = new ArrayList<>();
    public static String commande_source="";

    public static double payecommande = 0;
    public static double restecommande = 0;
    public static double valeurcommande = 0;

    TextView commande_code;
    TextView valeur_commande;
    TextView paye_commande;
    TextView reste_commande;

    Button terminer_encaissement;
    Button annuler_encaissement;


    EncaissementManager encaissementManager ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encaissement);
        setTitle("ENCAISSEMENT");

        encaissementManager = new EncaissementManager(getApplicationContext());

        encaissements = encaissementManager.getListByCmdCode(commande.getCOMMANDE_CODE());
        //encaissements= encaissementManager.getList();

        String commandecode= commande.getCOMMANDE_CODE();
        valeurcommande = encaissementManager.getNumberRounded(commande.getVALEUR_COMMANDE());

        payecommande = encaissementManager.getNumberRounded(getSumEncaissement(encaissements));
        restecommande =encaissementManager.getNumberRounded(valeurcommande-payecommande);


        Log.d("encaissement", "onCreate: "+valeurcommande);
        Log.d("encaissement", "onCreate: "+payecommande);
        Log.d("encaissement", "onCreate: "+restecommande);

        impressionManager=new ImpressionManager(getApplicationContext());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //commande_code = (TextView) findViewById(R.id.commandee_code);
        valeur_commande = (TextView) findViewById(R.id.valeur_commande);
        paye_commande = (TextView) findViewById(R.id.paye_commande);
        reste_commande = (TextView) findViewById(R.id.reste_commande);


        terminer_encaissement = (Button) findViewById(R.id.terminer_encaissement);
        annuler_encaissement = (Button) findViewById(R.id.annuler_encaissement);

        //commande_code.setText("COMMANDE_CODE : "+commandecode);
        valeur_commande.setText("VALEUR COMMANDE : "+valeurcommande+" DH.");
        paye_commande.setText("VALEUR PAYEE : "+payecommande+" DH.");
        reste_commande.setText("VALEUR RESTANTE : "+restecommande+" DH.");

        mListView = (ListView) findViewById(R.id.list_encaissement);
        encaissementConsultationAdapter=new EncaissementConsultationAdapter(this,encaissements);
        mListView.setAdapter(encaissementConsultationAdapter);


        terminer_encaissement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"TERMINER",Toast.LENGTH_SHORT).show();

                if(encaissements.size()>0){
                    addEncaissement(encaissements);
                }


                if(isNetworkAvailable()){
                    Toast.makeText(getApplicationContext(),"Synchronisation en cours",Toast.LENGTH_SHORT).show();
                    EncaissementManager.synchronisationEncaissement(getApplicationContext());

                }

                encaissements.clear();
                ListeCommandeLigne.clear();
                commande=null;

                Intent i = new Intent(getApplicationContext(), CommandeEncaissementActivity.class);
                i.putExtra("VISITERESULTAT_CODE",1);
                startActivity(i);
                finish();

            }

        });

        annuler_encaissement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"ANNULER",Toast.LENGTH_SHORT).show();
                encaissements.clear();
                ListeCommandeLigne.clear();
                commande=null;
                Intent i = new Intent(getApplicationContext(), CommandeEncaissementActivity.class);
                i.putExtra("VISITERESULTAT_CODE",1);
                startActivity(i);
                finish();

            }

        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        //Toast.makeText(getApplicationContext(),"ON PAUSE",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //Toast.makeText(getApplicationContext(),"ON POSTE RESUME",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_panier, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.plus:
                    Intent intent = new Intent(this, EncaissementActivity.class);
                    //EncaissementActivity.encaissements = encaissements;
                    startActivity(intent);
                    finish();
                    return true;

            case android.R.id.home:
                this.onBackPressed();
                return true;
            case R.id.logout:
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(MobEncaissementConsultationActivity.this, AuthActivity.class);
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void addEncaissement(ArrayList<Encaissement> encaissements){

        EncaissementManager encaissementManager = new EncaissementManager(getApplicationContext());
        for(int i=0;i<encaissements.size();i++){

            if(encaissements.get(i).getLOCAL()==1){
                encaissementManager.add(encaissements.get(i));
            }

        }
    }

    private double getSumEncaissement(ArrayList<Encaissement> encaissements){
        double somme = 0;
        for(int i=0; i<encaissements.size();i++){
            somme+=encaissements.get(i).getMONTANT();
        }
        return somme;
    }
}


