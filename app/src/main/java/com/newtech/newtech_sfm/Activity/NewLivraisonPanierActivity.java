package com.newtech.newtech_sfm.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Configuration.LivraisonPanier_Adapter;
import com.newtech.newtech_sfm.Configuration.Spinner_Adapter;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier.Famille;
import com.newtech.newtech_sfm.Metier.ListePrixLigne;
import com.newtech.newtech_sfm.Metier.Livraison;
import com.newtech.newtech_sfm.Metier.LivraisonLigne;
import com.newtech.newtech_sfm.Metier.Unite;
import com.newtech.newtech_sfm.Metier.User;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.Metier_Manager.ArticlePrixManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeManager;
import com.newtech.newtech_sfm.Metier_Manager.FamilleManager;
import com.newtech.newtech_sfm.Metier_Manager.ImpressionManager;
import com.newtech.newtech_sfm.Metier_Manager.ListePrixLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonManager;
import com.newtech.newtech_sfm.Metier_Manager.StockLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.StockManager;
import com.newtech.newtech_sfm.Metier_Manager.UniteManager;
import com.newtech.newtech_sfm.Metier_Manager.UserManager;
import com.newtech.newtech_sfm.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by TONPC on 14/04/2017.
 */

public class NewLivraisonPanierActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = NewLivraisonPanierActivity.class.getName();

    public ArrayList<CommandeLigne> commandeLignes = ViewLivraisonActivity.ListeCommandeLigne;
    public Commande commande = ViewLivraisonActivity.commande;
    public Livraison livraison = ViewLivraisonActivity.livraison;
    public String commandeSource = ViewLivraisonActivity.commandeSource;
    LivraisonLigne livraisonLigne = new LivraisonLigne();


    public ArrayList<LivraisonLigne> livraisonLignes = ViewLivraisonActivity.livraisonLignes;

    Client clientCourant = ClientActivity.clientCourant;
    public static ImpressionManager impressionManager;

    Unite unite;
    CommandeManager commandeManager;
    LivraisonManager livraisonManager;
    LivraisonLigneManager livraisonLigneManager;

    StockLigneManager stockLigneManager;
    StockManager stockManager;

    UserManager userManager;
    User utilisateur;

    ListView mListView;
    public TextView total_panier;
    public TextView total_vente;
    public Button val;
    EditText prix_defaut;

    EditText quantite_vendue;

    Spinner dropdown_unite;

    Spinner_Adapter spinnerAdapter;

    public float valeur_total_panier = 0;

    public static HashMap<String, List<Article>> listArticleParFamilleCode = new HashMap<String, List<Article>>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        Log.d("NLP", "onCreate: " + livraisonLignes.toString());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalogue_article_panier);

        userManager = new UserManager(getApplicationContext());
        impressionManager = new ImpressionManager(getApplicationContext());
        commandeManager = new CommandeManager(getApplicationContext());
        livraisonManager = new LivraisonManager(getApplicationContext());
        livraisonLigneManager = new LivraisonLigneManager(getApplicationContext());
        stockLigneManager = new StockLigneManager(getApplicationContext());
        stockManager = new StockManager(getApplicationContext());

        utilisateur = userManager.get();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_client);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final ArticleManager articleManager = new ArticleManager(getApplicationContext());
        final ListePrixLigneManager listePrixLigneManager = new ListePrixLigneManager(getApplicationContext());

        final Spinner dropdown = (Spinner) findViewById(R.id.famille_spinner);
        mListView = (ListView) findViewById(R.id.panier_listview1);

        float prix_article = 0;
        total_panier = (TextView) findViewById(R.id.total_panier);
        total_vente = (TextView) findViewById(R.id.total_vente);
        val = (Button) findViewById(R.id.validernewpanier);

        ArticlePrixManager articlePrixManager = new ArticlePrixManager(getApplicationContext());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        String date = df.format(Calendar.getInstance().getTime());

        if (livraisonLignes.size() > 0) {

            for (int i = 0; i < livraisonLignes.size(); i++) {
                livraisonLigne = livraisonLignes.get(i);
                //prix_article= (float) articlePrixManager.getArticlePrixBy_Unite_ArticlePrix(livraisonLigne.getARTICLE_CODE(),livraisonLigne.getUNITE_CODE(), clientCourant.getCIRCUIT_CODE(),date).getARTICLE_PRIX();
                prix_article = (float) listePrixLigneManager.getListPrixLigneByLPCACUC(clientCourant.getLISTEPRIX_CODE(), livraisonLigne.getARTICLE_CODE(), livraisonLigne.getUNITE_CODE()).getARTICLE_PRIX();
                valeur_total_panier += prix_article * (livraisonLigne.getQTE_COMMANDEE());
            }

            total_panier.setText(String.format("%.1f", valeur_total_panier) + "DH");
            total_vente.setText(String.format("%.1f", valeur_total_panier) + "DH");

        } else {
            total_panier.setText(String.valueOf(0) + "DH");
            total_vente.setText(String.valueOf(0) + "DH");
        }


        ArrayList<String> items = new ArrayList<>();
        FamilleManager familleManager = new FamilleManager(getApplicationContext());
        ArrayList<Famille> itemsFamille = familleManager.getFamille_textList();


        for (int j = 0; j < itemsFamille.size(); j++) {
            items.add(itemsFamille.get(j).getFAMILLE_NOM());
            listArticleParFamilleCode.put(itemsFamille.get(j).getFAMILLE_NOM(), articleManager.getListByFamilleCode(itemsFamille.get(j).getFAMILLE_CODE()));
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String familleCode = parent.getItemAtPosition(position).toString();

                List<Article> listArticleCatalogue = new ArrayList<Article>();


                if (!listArticleParFamilleCode.containsKey(familleCode)) {

                    listArticleCatalogue = articleManager.getListByFamilleCode(familleCode);
                } else {
                    listArticleCatalogue = listArticleParFamilleCode.get(familleCode);
                }


                final LivraisonPanier_Adapter panierAdapter = new LivraisonPanier_Adapter(NewLivraisonPanierActivity.this, listArticleCatalogue, livraisonLignes, clientCourant, getApplicationContext());

                mListView.setAdapter(panierAdapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        final Article article = (Article) panierAdapter.getItem(position);
                        UniteManager uniteManager = new UniteManager(getApplicationContext());
                        ArrayList<Unite> unites = new ArrayList<>();
                        unites = uniteManager.getListByArticleCode(article.getARTICLE_CODE());

                        /*ArrayList<String> items= new ArrayList<>() ;
                        items.add("CAISSE");
                        items.add("BOUTEILLE");*/


                        final Dialog dialog = new Dialog(view.getContext());
                        dialog.setContentView(R.layout.alert_dialog_panier);

                        quantite_vendue = (EditText) dialog.findViewById(R.id.quantite_vendue_panier);

                        prix_defaut = (EditText) dialog.findViewById(R.id.prix_par_defaut);

                        prix_defaut.setEnabled(false);

                        dropdown_unite = (Spinner) dialog.findViewById(R.id.spinner_choix_unite);

                        spinnerAdapter = new Spinner_Adapter(NewLivraisonPanierActivity.this, unites);

                        dropdown_unite.setAdapter(spinnerAdapter);

                        dropdown_unite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                                unite = spinnerAdapter.getItem(position);
                                ListePrixLigne listePrixLigne = listePrixLigneManager.getListPrixLigneByLPCACUC(clientCourant.getLISTEPRIX_CODE(), article.getARTICLE_CODE(), unite.getUNITE_CODE());

                                double quantite_commandee = 0;

                                quantite_vendue.getText().clear();
                                prix_defaut.getText().clear();

                                LivraisonLigne livraisonLigne1 = getLivraisonLigne(article, unite.getUNITE_CODE(), livraisonLignes);

                                quantite_vendue.setText(String.valueOf((int) livraisonLigne1.getQTE_COMMANDEE()));
                                prix_defaut.setText(String.valueOf((float) livraisonLigne1.getARTICLE_PRIX()));


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }

                        });


                        Button annuler_panier = (Button) dialog.findViewById(R.id.annuler_panier);
                        Button valider_panier = (Button) dialog.findViewById(R.id.valider_panier);


                        dialog.setTitle(article.getARTICLE_DESIGNATION1());

                        annuler_panier.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialog.dismiss();
                            }
                        });


                        valider_panier.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                float quantite_article = 0;
                                double prixDefault = 0;

                                String unite_code = "";

                                //unite_code = unite.getUNITE_CODE();
                                Log.d("unite", "onClick: " + unite_code);

                                if (unite == null) {

                                    Toast.makeText(getApplicationContext(), "Merci de Choisir une unité", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();

                                    Log.d("unite1", "onClick: " + unite_code);

                                } else {

                                    unite_code = unite.getUNITE_CODE();
                                    Log.d("unite2", "onClick: " + unite_code);

                                }

                                Log.d("valider", "valeur: " + quantite_vendue.getText());

                                if (!String.valueOf(quantite_vendue.getText()).equals("")) {
                                    quantite_article = Integer.parseInt(String.valueOf(quantite_vendue.getText()));
                                }


                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
                                String dateLivraison = df.format(Calendar.getInstance().getTime());

                                ListePrixLigne listePrixLigne = listePrixLigneManager.getListPrixLigneByLPCACUC(clientCourant.getLISTEPRIX_CODE(), article.getARTICLE_CODE(), unite_code);


                                if (listePrixLigne != null) {
                                    prixDefault = listePrixLigne.getARTICLE_PRIX();
                                }


                        /*ArticlePrixManager articlePrixManager = new ArticlePrixManager(getApplicationContext());
                        Articleprix articlePrix = articlePrixManager.getArticlePrixBy_Unite_ArticlePrix(article.getARTICLE_CODE(), unite_code, clientCourant.getCIRCUIT_CODE(), dateLivraison);
                        double prixArticle = articlePrix.getARTICLE_PRIX();*/


                                LivraisonLigne livraisonLigne1 = getLivraisonLigne(article, unite_code, livraisonLignes);

                                if (commandeSource.equals("Livraison") && quantite_article > livraisonLigne1.getQTE_COMMANDEE()) {

                                    dialog.dismiss();
                                    panierAdapter.notifyDataSetChanged();
                                    Toast.makeText(getApplicationContext(), "Vous ne pouver pas dépasser le max du quantite à livrer", Toast.LENGTH_LONG).show();

                                } else {
                                    supprimerLivraisonLigne(article, unite_code, livraisonLignes);

                                    if (quantite_article != 0 && !String.valueOf(quantite_vendue.getText()).equals("")) {

                                        ajouterLivraisonLigne(livraison, article, quantite_article, unite_code, prixDefault, livraisonLignes);
                                        updateLivraison(livraison, livraisonLignes);
                                        //Log.d("newpanierautre", "onClick: "+commande.toString());


                                    }

                                    updateLivraison(livraison, livraisonLignes);

                                    Log.d("newpanier", "onClick: " + livraison.toString());

                                    total_panier.setText(String.valueOf(livraison.getVALEUR_COMMANDE()) + "DH");
                                    total_vente.setText(String.valueOf(livraison.getVALEUR_COMMANDE()) + "DH");

                                    unite = null;

                                    dialog.dismiss();

                                    panierAdapter.notifyDataSetChanged();

                                }

                            }

                        });

                        dialog.show();
                    }
                });

                val.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        //val.setClickable(false);
                        ArrayList<String> articles = new ArrayList<>();
                        boolean stock_suffisant = true;

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                        Gson gson2 = new Gson();
                        String json2 = pref.getString("User", "");
                        Type type = new TypeToken<JSONObject>() {
                        }.getType();
                        final JSONObject user = gson2.fromJson(json2, type);

                        for (int i = 0; i < livraisonLignes.size(); i++) {

                            if (!articles.contains(livraisonLignes.get(i).getARTICLE_CODE())) {
                                articles.add(livraisonLignes.get(i).getARTICLE_CODE());
                            }
                        }

                        try {
                            if (stockManager.checkGerable(user.getString("UTILISATEUR_CODE"), getApplicationContext())) {
                                for (int i = 0; i < articles.size(); i++) {

                                    if (stockLigneManager.checkLivraisonStockLigneQteVersion(livraisonLignes, articles.get(i), getApplicationContext()) == false) {
                                        //Toast.makeText(getApplicationContext(),"Stock Insuffisant" , Toast.LENGTH_LONG).show();
                                        stock_suffisant = false;
                                        break;
                                    }
                                }
                            } else {
                                Log.d(TAG, "onClick: stock ingérable");
                                showMessage("STOCK INGERABLE");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (stock_suffisant == false) {
                            Log.d(TAG, "onClick: stock insuffisant");
                            showMessage("STOCK INSUFFISANT");
                        } else {
                            Log.d(TAG, "onClick: stock suffisant");
                            showMessage("STOCK SUFFISANT");
                        }

                        Intent i = new Intent(getApplicationContext(), ViewLivraisonActivity.class);
                        startActivity(i);
                        finish();


                    }
                });

            }


            public void onNothingSelected(AdapterView<?> parent) {
            }


        });


        setTitle("SELECTION DES ARTICLES");
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
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Merci de valider votre Panier", Toast.LENGTH_LONG).show();

    }


    public void supprimerCommandeLigne(Article article, String unite, ArrayList<CommandeLigne> commandeLignes) {

        for (int i = 0; i < commandeLignes.size(); i++) {
            if (commandeLignes.get(i).getARTICLE_CODE().equals(article.getARTICLE_CODE()) && commandeLignes.get(i).getUNITE_CODE().equals(unite)) {
                //Log.d("valider", "commandeligne: "+commandeLigne);
                commandeLignes.remove(i);
            }
        }
    }

    public CommandeLigne getCommandeLigne(Article article, String unite, ArrayList<CommandeLigne> commandeLignes) {

        CommandeLigne commandeLigne = new CommandeLigne();

        for (int i = 0; i < commandeLignes.size(); i++) {
            if (commandeLignes.get(i).getARTICLE_CODE().equals(article.getARTICLE_CODE()) && commandeLignes.get(i).getUNITE_CODE().equals(unite)) {
                commandeLigne = commandeLignes.get(i);
                break;
            }
        }
        return commandeLigne;
    }

    public void ajouterCommandeLigne(String commande_code, Article article, float quantite, String unite, double prixArticle, ArrayList<CommandeLigne> commandeLignes) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        String dateLivraison = df.format(Calendar.getInstance().getTime());

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {
        }.getType();
        final JSONObject user = gson2.fromJson(json2, type);


        int size = commandeLignes.size() + 1;

        CommandeLigne commandeLigne = new CommandeLigne(commande_code, article, dateLivraison, quantite, unite, prixArticle, size, getApplicationContext());
        commandeLignes.add(commandeLigne);
    }

    public void updateCommande(Commande commande, ArrayList<CommandeLigne> commandeLignes) {

        Log.d("newpanieravant", "updateCommande: " + commande.toString());

        double MONTANT_BRUT = 0;
        double MONTANT_NET = 0;
        double LITTRAGE_COMMANDE = 0;
        double TONNAGE_COMMANDE = 0;
        double KG_COMMANDE = 0;
        double VALEUR_COMMANDE = 0;

        for (int i = 0; i < commandeLignes.size(); i++) {

            MONTANT_BRUT += commandeLignes.get(i).getMONTANT_BRUT();
            MONTANT_NET += commandeLignes.get(i).getMONTANT_NET();
            LITTRAGE_COMMANDE += commandeLignes.get(i).getLITTRAGE_COMMANDEE();
            TONNAGE_COMMANDE += commandeLignes.get(i).getTONNAGE_COMMANDEE();
            KG_COMMANDE += commandeLignes.get(i).getKG_COMMANDEE();

        }
        VALEUR_COMMANDE = MONTANT_NET;

        commande.setMONTANT_BRUT(MONTANT_BRUT);
        commande.setMONTANT_NET(MONTANT_NET);
        commande.setLITTRAGE_COMMANDE(LITTRAGE_COMMANDE);
        commande.setTONNAGE_COMMANDE(TONNAGE_COMMANDE);
        commande.setKG_COMMANDE(KG_COMMANDE);
        commande.setVALEUR_COMMANDE(VALEUR_COMMANDE);

        Log.d("newpanierapres", "updateCommande: " + commande.toString());

    }

    public void supprimerLivraisonLigne(Article article, String unite, ArrayList<LivraisonLigne> livraisonLignes) {

        Log.d("livraison", "getLivraisonLigne: " + livraisonLignes.size());
        for (int i = 0; i < livraisonLignes.size(); i++) {
            if (livraisonLignes.get(i).getARTICLE_CODE().equals(article.getARTICLE_CODE()) && livraisonLignes.get(i).getUNITE_CODE().equals(unite)) {
                //Log.d("valider", "commandeligne: "+commandeLigne);
                livraisonLignes.remove(i);
            }
        }
    }

    public LivraisonLigne getLivraisonLigne(Article article, String unite, ArrayList<LivraisonLigne> livraisonLignes) {

        LivraisonLigne livraisonLigne = new LivraisonLigne();


        for (int i = 0; i < livraisonLignes.size(); i++) {
            if (livraisonLignes.get(i).getARTICLE_CODE().equals(article.getARTICLE_CODE()) && livraisonLignes.get(i).getUNITE_CODE().equals(unite)) {
                livraisonLigne = livraisonLignes.get(i);
                break;
            }
        }
        return livraisonLigne;
    }

    public void ajouterLivraisonLigne(Livraison livraison, Article article, float quantite, String unite, double prixArticle, ArrayList<LivraisonLigne> livraisonLignes) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        String dateLivraison = df.format(Calendar.getInstance().getTime());

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {
        }.getType();
        final JSONObject user = gson2.fromJson(json2, type);


        int size = livraisonLignes.size() + 1;

        LivraisonLigne livraisonLigne = new LivraisonLigne(livraison, article, dateLivraison, quantite, unite, prixArticle, size, getApplicationContext());
        livraisonLignes.add(livraisonLigne);
    }

    public void updateLivraison(Livraison livraison, ArrayList<LivraisonLigne> livraisonLignes) {

        Log.d("newpanieravant", "updateCommande: " + commande.toString());

        double MONTANT_BRUT = 0;
        double MONTANT_NET = 0;
        double LITTRAGE_COMMANDE = 0;
        double TONNAGE_COMMANDE = 0;
        double KG_COMMANDE = 0;
        double VALEUR_COMMANDE = 0;

        for (int i = 0; i < livraisonLignes.size(); i++) {

            MONTANT_BRUT += livraisonLignes.get(i).getMONTANT_BRUT();
            MONTANT_NET += livraisonLignes.get(i).getMONTANT_NET();
            LITTRAGE_COMMANDE += livraisonLignes.get(i).getLITTRAGE_COMMANDEE();
            TONNAGE_COMMANDE += livraisonLignes.get(i).getTONNAGE_COMMANDEE();
            KG_COMMANDE += livraisonLignes.get(i).getKG_COMMANDEE();

        }
        VALEUR_COMMANDE = MONTANT_NET;

        livraison.setMONTANT_BRUT(MONTANT_BRUT);
        livraison.setMONTANT_NET(MONTANT_NET);
        livraison.setLITTRAGE_COMMANDE(LITTRAGE_COMMANDE);
        livraison.setTONNAGE_COMMANDE(TONNAGE_COMMANDE);
        livraison.setKG_COMMANDE(KG_COMMANDE);
        livraison.setVALEUR_COMMANDE(VALEUR_COMMANDE);

        ViewLivraisonActivity.livraison = livraison;

        Log.d("newpanierapres", "updateCommande: " + livraison.toString());

    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


}
