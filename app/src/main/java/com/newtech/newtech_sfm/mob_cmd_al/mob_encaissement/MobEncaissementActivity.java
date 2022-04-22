package com.newtech.newtech_sfm.mob_cmd_al.mob_encaissement;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.newtech.newtech_sfm.Activity.ClientActivity;
import com.newtech.newtech_sfm.Activity.EncaissementConsultationActivity;
import com.newtech.newtech_sfm.Activity.EncaissementTypeActivity;
import com.newtech.newtech_sfm.Activity.ViewCommandeActivity;
import com.newtech.newtech_sfm.Configuration.EncaissementAdapter;
import com.newtech.newtech_sfm.Configuration.ImprimanteManager;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.Credit;
import com.newtech.newtech_sfm.Metier.Encaissement;
import com.newtech.newtech_sfm.Metier.Impression;
import com.newtech.newtech_sfm.Metier.Livraison;
import com.newtech.newtech_sfm.Metier.LivraisonLigne;
import com.newtech.newtech_sfm.Metier.StockTransfert;
import com.newtech.newtech_sfm.Metier.User;
import com.newtech.newtech_sfm.Metier_Manager.ClientManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeGratuiteManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandePromotionManager;
import com.newtech.newtech_sfm.Metier_Manager.CreditManager;
import com.newtech.newtech_sfm.Metier_Manager.EncaissementManager;
import com.newtech.newtech_sfm.Metier_Manager.ImpressionManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonGratuiteManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonPromotionManager;
import com.newtech.newtech_sfm.Metier_Manager.PromotionManager;
import com.newtech.newtech_sfm.Metier_Manager.PromotiongratuiteManager;
import com.newtech.newtech_sfm.Metier_Manager.StockManager;
import com.newtech.newtech_sfm.Metier_Manager.StockTransfertManager;
import com.newtech.newtech_sfm.Metier_Manager.UniteManager;
import com.newtech.newtech_sfm.Metier_Manager.UserManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteManager;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.Service.BlutoothConnctionService;
import com.newtech.newtech_sfm.mob_cmd_al.mob_livraison_panier.ViewLivraisonActivity;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by TONPC on 02/08/2017.
 */

public class MobEncaissementActivity extends AppCompatActivity {

    private static final String TAG = MobEncaissementActivity.class.getName();
    ListView mListView;
    EncaissementAdapter encaissementAdapter;

    public static Commande commande;
    public static Livraison livraison = new Livraison();
    public static ArrayList<LivraisonLigne> livraisonLignes = new ArrayList<>();
    //public static  ArrayList<CommandeLigne> ListeCommandeLigne = new ArrayList<>();

    public static ArrayList<Encaissement> encaissements = new ArrayList<>();
    public static ArrayList<Encaissement> encaissementslocaux = new ArrayList<>();
    public static ArrayList<Credit> credits = new ArrayList<>();
    public static String commande_source = "";

    public static double payecommande = 0;
    public static double restecommande = 0;
    public static double valeurcommande = 0;

    public static double payelivraison = 0;
    public static double restelivraison = 0;
    public static double valeurlivraison = 0;

    public static double payecommandelocal = 0;

    TextView commande_code;
    TextView valeur_commande;
    TextView paye_commande;
    TextView reste_commande;
    TextView valeur_livraison;
    TextView paye_livraison;
    TextView reste_livraison;

    Button terminer_encaissement;
    Button annuler_encaissement;

    public static ImpressionManager impressionManager;
    EncaissementManager encaissementManager;
    LivraisonManager livraisonManager;
    LivraisonLigneManager livraisonLigneManager;
    LivraisonPromotionManager livraisonPromotionManager;
    LivraisonGratuiteManager livraisonGratuiteManager;

    CommandeManager commandeManager;
    CommandeLigneManager commandeLigneManager;
    UniteManager uniteManager;
    CommandePromotionManager commandePromotionManager;
    CommandeGratuiteManager commandeGratuiteManager;
    PromotionManager promo_Manager;
    PromotiongratuiteManager promo_Gratuite_Manager;
    StockManager stockManager;
    StockTransfertManager stockTransfertManager;
    UserManager userManager;
    User utilisateur;
    VisiteManager visiteManager;
    ClientManager clientManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encaissement);
        setTitle("ENCAISSEMENT");

        encaissementManager = new EncaissementManager(getApplicationContext());
        livraisonManager = new LivraisonManager(getApplicationContext());
        livraisonLigneManager = new LivraisonLigneManager(getApplicationContext());
        livraisonPromotionManager = new LivraisonPromotionManager(getApplicationContext());
        livraisonGratuiteManager = new LivraisonGratuiteManager(getApplicationContext());
        impressionManager = new ImpressionManager(getApplicationContext());

        userManager = new UserManager(getApplicationContext());

        commandeManager = new CommandeManager(getApplicationContext());
        uniteManager = new UniteManager(getApplicationContext());
        commandeLigneManager = new CommandeLigneManager(getApplicationContext());
        commandePromotionManager = new CommandePromotionManager(getApplicationContext());
        commandeGratuiteManager = new CommandeGratuiteManager(getApplicationContext());
        promo_Manager = new PromotionManager(getApplicationContext());
        promo_Gratuite_Manager = new PromotiongratuiteManager(getApplicationContext());
        stockManager = new StockManager(getApplicationContext());
        stockTransfertManager = new StockTransfertManager(getApplicationContext());
        visiteManager = new VisiteManager(getApplicationContext());
        clientManager = new ClientManager(getApplicationContext());

        encaissementslocaux = encaissementManager.getListByCmdCode(commande.getCOMMANDE_CODE());
        payecommandelocal = encaissementManager.getNumberRounded(getSumEncaissement(encaissementslocaux));

        if (commande_source.equals("Livraison")) {

            valeurlivraison = encaissementManager.getNumberRounded(livraisonManager.get(livraison.getLIVRAISON_CODE()).getMONTANT_NET());
            payelivraison = encaissementManager.getNumberRounded(getSumEncaissement(encaissements));
            restelivraison = encaissementManager.getNumberRounded(valeurlivraison - payelivraison);

            valeur_livraison = (TextView) findViewById(R.id.valeur_livraison);
            paye_livraison = (TextView) findViewById(R.id.paye_livraison);
            reste_livraison = (TextView) findViewById(R.id.reste_livraison);

            valeur_livraison.setText("VAL LIV : " + valeurlivraison + " DH.");
            paye_livraison.setText("VAL PAYEE : " + payelivraison + " DH.");
            reste_livraison.setText("VAL RESTANTE : " + restelivraison + " DH.");
        }

        valeurcommande = encaissementManager.getNumberRounded(commande.getMONTANT_NET());
        payecommande = encaissementManager.getNumberRounded(payecommandelocal + getSumEncaissement(encaissements) + getSumCredit(credits));
        restecommande = encaissementManager.getNumberRounded(valeurcommande - payecommande);

        valeur_commande = (TextView) findViewById(R.id.valeur_commande);
        paye_commande = (TextView) findViewById(R.id.paye_commande);
        reste_commande = (TextView) findViewById(R.id.reste_commande);

        Log.d("encaissement", "onCreate: " + encaissements.toString());
        Log.d("encaissement", "onCreate: " + encaissements.size());

        Log.d(TAG, "onCreate: LIV 2 : " + livraisonLignes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //commande_code = (TextView) findViewById(R.id.commandee_code);
        terminer_encaissement = (Button) findViewById(R.id.terminer_encaissement);
        annuler_encaissement = (Button) findViewById(R.id.annuler_encaissement);

        //commande_code.setText("COMMANDE_CODE : "+commandecode);
        valeur_commande.setText("VAL CMDE : " + valeurcommande + " DH.");
        paye_commande.setText("VAL PAYEE : " + payecommande + " DH.");
        reste_commande.setText("VAL RESTANTE : " + restecommande + " DH.");


        mListView = (ListView) findViewById(R.id.list_encaissement);

        View child = getLayoutInflater().inflate(R.layout.no_data_found, null);
        ((ViewGroup) mListView.getParent()).addView(child);

        mListView.setEmptyView(child);

        encaissementAdapter = new EncaissementAdapter(this, encaissements);
        mListView.setAdapter(encaissementAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Dialog dialog = new Dialog(MobEncaissementActivity.this);

                dialog.setContentView(R.layout.alert_delete_encaissement);
                dialog.setTitle("ENCAISSEMENT");
                dialog.setCanceledOnTouchOutside(false);
                Button oui = (Button) dialog.findViewById(R.id.btn_oui);
                Button non = (Button) dialog.findViewById(R.id.btn_non);

                oui.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        encaissements.remove(i); //or some other task
                        encaissementAdapter.notifyDataSetChanged();

                        if (commande_source.equals("Livraison")) {

                            valeurlivraison = encaissementManager.getNumberRounded(livraisonManager.get(livraison.getLIVRAISON_CODE()).getMONTANT_NET());
                            payelivraison = encaissementManager.getNumberRounded(getSumEncaissement(encaissements));
                            restelivraison = encaissementManager.getNumberRounded(valeurlivraison - payelivraison);

                            valeur_livraison = (TextView) findViewById(R.id.valeur_livraison);
                            paye_livraison = (TextView) findViewById(R.id.paye_livraison);
                            reste_livraison = (TextView) findViewById(R.id.reste_livraison);

                            valeur_livraison.setText("VAL LIV : " + valeurlivraison + " DH.");
                            paye_livraison.setText("VAL PAYEE : " + payelivraison + " DH.");
                            reste_livraison.setText("VAL RESTANTE : " + restelivraison + " DH.");
                        }

                        valeurcommande = encaissementManager.getNumberRounded(commande.getMONTANT_NET());
                        payecommande = encaissementManager.getNumberRounded(payecommandelocal + getSumEncaissement(encaissements) + getSumCredit(credits));
                        restecommande = encaissementManager.getNumberRounded(valeurcommande - payecommande);

                        valeur_commande.setText("VAL CMDE : " + valeurcommande + " DH.");
                        paye_commande.setText("VAL PAYEE : " + payecommande + " DH.");
                        reste_commande.setText("VAL RESTANTE : " + restecommande + " DH.");

                        dialog.dismiss();

                    }

                });

                non.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }

                });

                dialog.show();

            }
        });

        terminer_encaissement.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "TERMINER", Toast.LENGTH_SHORT).show();

            //stockManager.updateStock(getApplicationContext());
            //clientManager.updateClientRemiseLastUpdateValeur(commande,getApplicationContext());

            if (encaissements.size() > 0) {
                addEncaissement(encaissements);


                if (commande_source.equals("Livraison")) {

                    for (int i = 0; i < livraisonLignes.size(); i++) {

                        Log.d(TAG, "onClick: LIV 3 " + livraisonLignes.get(i));

                        try {
                            StockTransfert stockTransfert = new StockTransfert(livraison, livraisonLignes.get(i), getApplicationContext());

                            Log.d("stockTransfert", "onClick: " + i + stockTransfert.toString());
                            stockTransfertManager.add(stockTransfert);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                } else {
                    for (int i = 0; i < ViewCommandeActivity.ListeCommandeLigne.size(); i++) {

                        try {
                            StockTransfert stockTransfert = new StockTransfert(commande, ViewCommandeActivity.ListeCommandeLigne.get(i), getApplicationContext());

                            //Log.d("stockTransfert", "onClick: "+ i +stockTransfert.toString());
                            stockTransfertManager.add(stockTransfert);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }


                if (commande_source.equals("Encaissement")) {
                    encaissements.clear();
                    Intent i = new Intent(getApplicationContext(), EncaissementConsultationActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    final Dialog dialog1 = new Dialog(MobEncaissementActivity.this);
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
                            boolean printed = false;
                            String impression_text = "";
                            if (commande_source.equals("Livraison")) {
                                impression_text = impressionManager.ImprimerLivraison(livraison, getApplicationContext());
                            } else {
                                impression_text = impressionManager.ImprimerCommande(commande, getApplicationContext());
                            }
                            for (int i = 0; i < Integer.valueOf(nbr_copies.getText().toString()); i++) {

                                printed = BlutoothConnctionService.imprimanteManager.printText(impression_text);
                                ImprimanteManager.lastPrint = impression_text;
                                Log.d("print", "onClick: " + impression_text.toString());

                                if (printed == true) {
                                    //Log.d("printed", "onClick: "+"imprimeée");
                                    try {
                                        Impression impression = new Impression(getApplicationContext(), commande.getCOMMANDE_CODE(), impression_text, "NORMAL", 1, "COMMANDE");
                                        impressionManager.add(impression);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    //Log.d("printed", "onClick: "+"non imprimee");
                                    try {
                                        Impression impression = new Impression(getApplicationContext(), commande.getCOMMANDE_CODE(), impression_text, "STOCKEE", 0, "COMMANDE");
                                        impressionManager.add(impression);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }


                        }


                    });

                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            stockManager.updateStock(getApplicationContext());

                            if (isNetworkAvailable()) {
                                Toast.makeText(getApplicationContext(), "Synchronisation en cours", Toast.LENGTH_SHORT).show();

                                CommandeManager.synchronisationCommande(getApplicationContext());
                                CommandeLigneManager.synchronisationCommandeLigne(getApplicationContext());
                                CommandePromotionManager.synchronisationCommandePromotion(getApplicationContext());
                                CommandeGratuiteManager.synchronisationCommandeGratuite(getApplicationContext());
                                LivraisonManager.synchronisationLivraison(getApplicationContext());
                                LivraisonLigneManager.synchronisationLivraisonLigne(getApplicationContext());
                                EncaissementManager.synchronisationEncaissement(getApplicationContext());
                                CreditManager.synchronisationCredit(getApplicationContext());

                            } else {
                                Toast.makeText(MobEncaissementActivity.this, "Vérifier votre connexion internet puis synchroniser", Toast.LENGTH_LONG).show();

                            }

                            ViewCommandeActivity.ListeCommandeLigne.clear();
                            ViewCommandeActivity.commandeSource = null;
                            ViewCommandeActivity.commande = null;

                            ViewLivraisonActivity.ListeCommandeLigne.clear();
                            ViewLivraisonActivity.commande = null;
                            ViewLivraisonActivity.commandeSource = null;

                            encaissements.clear();
                            encaissementslocaux.clear();
                            commande = null;
                            dialog1.dismiss();
                            Intent i = new Intent(getApplicationContext(), ClientActivity.class);
                            i.putExtra("VISITERESULTAT_CODE", 1);
                            startActivity(i);
                            finish();
                        }

                    });

                    dialog1.show();
                }
            } else {

                Toast.makeText(getApplicationContext(), "AUCUN ENCAISSEMENT POUR TERMINER", Toast.LENGTH_SHORT).show();
            }


        });

        annuler_encaissement.setOnClickListener(v -> {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date_visite = df.format(Calendar.getInstance().getTime());

            visiteManager.updateVisite_VRDF(ClientActivity.visiteCourante.getVISITE_CODE(), 0, date_visite);

            if (commande_source.equals("Livraison")) {
                livraisonManager.delete(livraison.getLIVRAISON_CODE());
                livraisonLigneManager.deleteByLivraisonCode(livraison.getLIVRAISON_CODE());
                livraisonGratuiteManager.deleteByLivraisonCode(livraison.getLIVRAISON_CODE());
                livraisonPromotionManager.deleteByLivraisonCode(livraison.getLIVRAISON_CODE());
                stockTransfertManager.deleteByCmdCode(livraison.getLIVRAISON_CODE());

                ViewLivraisonActivity.ListeCommandeLigne.clear();
                ViewLivraisonActivity.livraisonLignes.clear();
                livraisonLignes.clear();
                livraison = null;
                commande = null;
                ViewLivraisonActivity.commandeSource = null;

            } else {
                commandeManager.delete(commande.getCOMMANDE_CODE());
                commandeLigneManager.deleteByCmdCode(commande.getCOMMANDE_CODE());
                commandeGratuiteManager.deleteByCmdCode(commande.getCOMMANDE_CODE());
                commandePromotionManager.deleteByCmdCode(commande.getCOMMANDE_CODE());
                stockTransfertManager.deleteByCmdCode(commande.getCOMMANDE_CODE());

                ViewCommandeActivity.ListeCommandeLigne.clear();
                commande = null;
                ViewCommandeActivity.commandeSource = null;
            }

            encaissements.clear();
            encaissementslocaux.clear();
            Intent i = new Intent(getApplicationContext(), ClientActivity.class);
            i.putExtra("VISITERESULTAT_CODE", 0);
            startActivity(i);
            finish();


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

                EncaissementTypeActivity.commande_source = null;
                EncaissementTypeActivity.commande_source = MobEncaissementActivity.commande_source;
                Intent intent = new Intent(this, EncaissementTypeActivity.class);
                startActivity(intent);
                //finish();
                return true;

            case android.R.id.home:
                return true;
            case R.id.logout:
                return true;

            case R.id.option2:
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

    private void addEncaissement(ArrayList<Encaissement> encaissements) {

        EncaissementManager encaissementManager = new EncaissementManager(getApplicationContext());
        CreditManager creditManager = new CreditManager(getApplicationContext());

        for (int i = 0; i < encaissements.size(); i++) {
            if (encaissements.get(i).getTYPE_CODE().equals("CREDIT")) {
                Credit credit = new Credit(encaissements.get(i));
                Log.d("Encaissement", "addEncaissement: credit " + credit.toString());
                creditManager.add(credit);

            } else {
                if (encaissements.get(i).getLOCAL() == 1) {
                    Log.d("Encaissement", "addEncaissement: encaissement " + encaissements.get(i).toString());
                    encaissementManager.add(encaissements.get(i));
                }
            }
        }
    }

    private double getSumEncaissement(ArrayList<Encaissement> encaissements) {
        double somme = 0;
        for (int i = 0; i < encaissements.size(); i++) {
            somme += encaissements.get(i).getMONTANT();
        }
        return somme;
    }

    private double getSumCredit(ArrayList<Credit> credits) {
        double somme = 0;
        for (int i = 0; i < credits.size(); i++) {
            somme += credits.get(i).getMONTANT_CREDIT();
        }
        return somme;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(getApplicationContext(), "Merci de terminer votre opération", Toast.LENGTH_SHORT).show();
    }
}


