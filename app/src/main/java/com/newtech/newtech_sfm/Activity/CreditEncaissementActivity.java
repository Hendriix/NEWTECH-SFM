package com.newtech.newtech_sfm.Activity;

import android.app.Dialog;
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

import com.newtech.newtech_sfm.Configuration.EncaissementAdapter;
import com.newtech.newtech_sfm.Metier.Credit;
import com.newtech.newtech_sfm.Metier.Encaissement;
import com.newtech.newtech_sfm.Metier.User;
import com.newtech.newtech_sfm.Metier_Manager.ClientManager;
import com.newtech.newtech_sfm.Metier_Manager.CreditManager;
import com.newtech.newtech_sfm.Metier_Manager.EncaissementManager;
import com.newtech.newtech_sfm.Metier_Manager.ImpressionManager;
import com.newtech.newtech_sfm.Metier_Manager.UniteManager;
import com.newtech.newtech_sfm.Metier_Manager.UserManager;
import com.newtech.newtech_sfm.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by TONPC on 02/08/2017.
 */

public class CreditEncaissementActivity extends AppCompatActivity {

    ListView mListView;
    EncaissementAdapter encaissementAdapter;
    public static Credit credit;
    public static ArrayList<Encaissement> encaissements = new ArrayList<>();
    public static ArrayList<Encaissement> encaissementslocaux = new ArrayList<>();
    public static ArrayList<Credit> credits =  new ArrayList<>();
    public static String commande_source="";
    public static double valeur_credit = 0;
    public static double paye_credit = 0;
    public static double reste_credit = 0;
    public static ImpressionManager impressionManager;
    public static double payecommandelocal = 0;

    TextView Valeur;
    TextView Paye;
    TextView Reste;

    Button terminer_credit_encaissement;
    Button annuler_credit_encaissement;


    EncaissementManager encaissementManager;
    UniteManager uniteManager;
    UserManager userManager ;
    User utilisateur;
    ClientManager clientManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_encaissement);
        setTitle("ENCAISSEMENT CREDIT");

        encaissementManager =  new EncaissementManager(getApplicationContext());
        impressionManager = new ImpressionManager(getApplicationContext());
        userManager = new UserManager(getApplicationContext());
        uniteManager = new UniteManager(getApplicationContext());
        clientManager = new ClientManager(getApplicationContext());

        valeur_credit = encaissementManager.getNumberRounded(credit.getMONTANT_CREDIT());
        paye_credit = encaissementManager.getNumberRounded(credit.getMONTANT_ENCAISSE()+getSumEncaissement(encaissements));
        reste_credit = encaissementManager.getNumberRounded(valeur_credit-paye_credit);

        Valeur = (TextView) findViewById(R.id.valeur_credit);
        Paye = (TextView) findViewById(R.id.paye_credit);
        Reste = (TextView) findViewById(R.id.reste_credit);



        Log.d("encaissement", "onCreate: "+encaissements.toString());
        Log.d("encaissement", "onCreate: "+encaissements.size());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //commande_code = (TextView) findViewById(R.id.commandee_code);
        terminer_credit_encaissement = (Button) findViewById(R.id.terminer_credit_encaissement);
        annuler_credit_encaissement = (Button) findViewById(R.id.annuler_credit_encaissement);

        //commande_code.setText("COMMANDE_CODE : "+commandecode);
        Valeur.setText("VALEUR : "+valeur_credit+" DH.");
        Paye.setText("PAYEE : "+paye_credit+" DH.");
        Reste.setText("RESTE : "+reste_credit+" DH.");



        mListView = (ListView) findViewById(R.id.list_encaissement);
        encaissementAdapter=new EncaissementAdapter(this,encaissements);
        mListView.setAdapter(encaissementAdapter);


        terminer_credit_encaissement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"TERMINER",Toast.LENGTH_SHORT).show();

                if(encaissements.size()>0){

                        addEncaissement(encaissements,credit);

                        final Dialog dialog1 = new Dialog(CreditEncaissementActivity.this);
                        dialog1.setContentView(R.layout.alert_imprimante);
                        dialog1.setTitle("Impression");
                        dialog1.setCanceledOnTouchOutside(false);
                        Button print = (Button) dialog1.findViewById(R.id.btn_print);
                        Button done = (Button) dialog1.findViewById(R.id.done_print);
                        final TextView nbr_copies = (TextView) dialog1.findViewById(R.id.nbr_copies);


                        print.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Log.d("CommandeLignevalider", "onClick: "+commande.getCOMMANDE_CODE());
                                boolean printed=false;
                                String impression_text = "";
                                //impression_text= impressionManager.ImprimerCredit(credit,getApplicationContext());

                                /*for(int i=0;i<Integer.valueOf(nbr_copies.getText().toString());i++){

                                    printed= BlutoothConnctionService.imprimanteManager.printText(impression_text);
                                    ImprimanteManager.lastPrint=impression_text;
                                    Log.d("print", "onClick: "+impression_text.toString());

                                    if(printed==true){
                                        Log.d("printed", "onClick: "+"imprimeée");
                                        try {
                                            Impression impression = new Impression(getApplicationContext(),credit.getCOMMANDE_CODE(),impression_text,"NORMAL",1,"COMMANDE");
                                            Log.d("printed", "onClick: "+impression.toString());
                                            impressionManager.add(impression);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }else{
                                        Log.d("printed", "onClick: "+"non imprimee");
                                        try {
                                            Impression impression = new Impression(CreditEncaissementActivity.this,credit.getCOMMANDE_CODE(),impression_text,"STOCKEE",0,"COMMANDE");
                                            Log.d("printed", "onClick: "+impression.toString());
                                            impressionManager.add(impression);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                }*/

                            }
                        });

                        done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {



                                if(isNetworkAvailable()){
                                    Toast.makeText(getApplicationContext(),"Synchronisation en cours",Toast.LENGTH_SHORT).show();
                                    EncaissementManager.synchronisationEncaissement(getApplicationContext());
                                    CreditManager.synchronisationCredit(getApplicationContext());

                                }else{
                                    Toast.makeText(CreditEncaissementActivity.this,"Vérifier votre connexion internet puis synchroniser",Toast.LENGTH_LONG).show();

                                }

                                encaissements.clear();
                                credit=null;
                                dialog1.dismiss();
                                Intent i = new Intent(getApplicationContext(), CreditActivity.class);
                                i.putExtra("VISITERESULTAT_CODE",1);
                                startActivity(i);
                                finish();
                            }

                        });


                        dialog1.show();

                }else{

                    Toast.makeText(getApplicationContext(),"AUCUN ENCAISSEMENT POUR TERMINER",Toast.LENGTH_SHORT).show();
                }

            }

        });

        annuler_credit_encaissement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date_visite=df.format(Calendar.getInstance().getTime());
                credit=null;
                ViewCommandeActivity.commandeSource=null;
                encaissements.clear();
                encaissementslocaux.clear();
                Intent i = new Intent(getApplicationContext(), CreditActivity.class);
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
                    Intent intent = new Intent(this, CreditEncaissementTypeActivity.class);
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
                Intent i = new Intent(CreditEncaissementActivity.this, AuthActivity.class);
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

    private void addEncaissement(ArrayList<Encaissement> encaissements, Credit credit){

        EncaissementManager encaissementManager = new EncaissementManager(getApplicationContext());
        CreditManager creditManager = new CreditManager(getApplicationContext());
        double payement_credit = 0;
        double reste_credit = 0;

        for(int i=0;i<encaissements.size();i++){

                if(encaissements.get(i).getLOCAL()==1){
                    Log.d("Encaissement", "addEncaissement: encaissement "+encaissements.get(i).toString());
                    encaissementManager.add(encaissements.get(i));
                    payement_credit += encaissements.get(i).getMONTANT();
                }
        }
        reste_credit = credit.getMONTANT_CREDIT()-credit.getMONTANT_ENCAISSE()-payement_credit;
        payement_credit+= credit.getMONTANT_ENCAISSE();

        creditManager.updatePayementCredit(credit.getCREDIT_CODE(),payement_credit,reste_credit);

    }

    private double getSumEncaissement(ArrayList<Encaissement> encaissements){
        double somme = 0;
        for(int i=0; i<encaissements.size();i++){
            somme+=encaissements.get(i).getMONTANT();
        }
        return somme;
    }

    private double getSumCredit(ArrayList<Credit> credits){
        double somme = 0;
        for(int i=0; i<credits.size();i++){
            somme+=credits.get(i).getMONTANT_CREDIT();
        }
        return somme;
    }



}


