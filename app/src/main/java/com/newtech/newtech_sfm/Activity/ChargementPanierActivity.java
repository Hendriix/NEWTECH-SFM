package com.newtech.newtech_sfm.Activity;

import static java.lang.StrictMath.abs;

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
import com.newtech.newtech_sfm.Configuration.ChargementPanier_Adapter;
import com.newtech.newtech_sfm.Configuration.Panier_Adapter;
import com.newtech.newtech_sfm.Configuration.Spinner_Adapter;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.Famille;
import com.newtech.newtech_sfm.Metier.StockDemande;
import com.newtech.newtech_sfm.Metier.StockDemandeLigne;
import com.newtech.newtech_sfm.Metier.Unite;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.Metier_Manager.FamilleManager;
import com.newtech.newtech_sfm.Metier_Manager.ListePrixLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.UniteManager;
import com.newtech.newtech_sfm.R;

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

public class ChargementPanierActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    public static Panier_Adapter panier_adapter;
    public ArrayList<StockDemandeLigne>  stockDemandeLignes = ViewChargementActivity.stockDemandeLignes;

    public StockDemande stockDemande = ViewChargementActivity.stockDemande;
    public String commandeSource= ViewChargementActivity.commandeSource;

    List<Article> article_list = new ArrayList<Article>();

    ListView mListView;
    public TextView total_panier;
    public TextView total_vente;
    public Button val;
    public float valeur_total_panier=0;

    Unite unite;
    StockDemandeLigne stockDemandeLigne = new StockDemandeLigne();

    float prixArticle=0;

    public static HashMap<String,List<Article>> listArticleParFamilleCode = new HashMap<String, List<Article>>();

    //final Context context=this.getApplicationContext();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalogue_article_panier_chargement);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_client);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ArticleManager articleManager= new ArticleManager(getApplicationContext());
        final ListePrixLigneManager listePrixLigneManager = new ListePrixLigneManager(getApplicationContext());

        final Spinner dropdown = (Spinner)findViewById(R.id.famille_spinner);
        mListView= (ListView) findViewById(R.id.panier_listview1);

        float prix_article=0;
        //total_panier=(TextView) findViewById(R.id.total_panier);
        //total_vente=(TextView) findViewById(R.id.total_vente);
        val = (Button) findViewById(R.id.validernewpanier);

        //ArticlePrixManager articlePrixManager = new ArticlePrixManager(getApplicationContext());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        String date =df.format(Calendar.getInstance().getTime());

        if(stockDemandeLignes.size()>0){

            for(int i=0;i<stockDemandeLignes.size();i++){
                stockDemandeLigne =stockDemandeLignes.get(i);
                //prix_article= (float) articlePrixManager.getArticlePrixBy_Unite_ArticlePrix(commandeLigne.getARTICLE_CODE(),commandeLigne.getUNITE_CODE(), clientCourant.getCIRCUIT_CODE(),date).getARTICLE_PRIX();
                //prix_article= (float) listePrixLigneManager.getListPrixLigneByLPCACUC(clientCourant.getLISTEPRIX_CODE(),stockDemandeLigne.getARTICLE_CODE(),stockDemandeLigne.getUNITE_CODE()).getARTICLE_PRIX();
                prix_article = 0;
                valeur_total_panier+=prix_article*(stockDemandeLigne.getQTE_COMMANDEE());
            }

            //total_panier.setText(String.format("%.1f",valeur_total_panier)+"DH");
            //total_vente.setText(String.format("%.1f",valeur_total_panier)+"DH");

        }else{
            //total_panier.setText(String.valueOf(0)+"DH");
            //total_vente.setText(String.valueOf(0)+"DH");
        }

        ArrayList<String> items= new ArrayList<>() ;
        FamilleManager familleManager = new FamilleManager(getApplicationContext());
        ArrayList<Famille> itemsFamille=  familleManager.getFamille_textList();

        for(int j=0;j<itemsFamille.size();j++){
            items.add(itemsFamille.get(j).getFAMILLE_NOM());
            listArticleParFamilleCode.put(itemsFamille.get(j).getFAMILLE_NOM(),articleManager.getListByFamilleCode(itemsFamille.get(j).getFAMILLE_CODE()));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,items );
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String familleCode = parent.getItemAtPosition(position).toString();

                List<Article> listArticleCatalogue = new ArrayList<Article>();

                if(!listArticleParFamilleCode.containsKey(familleCode)) {

                    listArticleCatalogue=articleManager.getListByFamilleCode(familleCode);
                }
                else {
                    listArticleCatalogue= listArticleParFamilleCode.get(familleCode);
                }

                final ChargementPanier_Adapter chargementPanierAdapter= new ChargementPanier_Adapter(ChargementPanierActivity.this, listArticleCatalogue,getApplicationContext());

                mListView.setAdapter(chargementPanierAdapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        final Article article =(Article) chargementPanierAdapter.getItem(position);
                        UniteManager uniteManager = new UniteManager(getApplicationContext());
                        ArrayList<Unite> unites = new ArrayList<>();
                        unites = uniteManager.getListByArticleCode(article.getARTICLE_CODE());

                        /*ArrayList<String> items= new ArrayList<>() ;
                        items.add("CAISSE");
                        items.add("BOUTEILLE");*/

                        final Dialog dialog = new Dialog(view.getContext());
                        dialog.setContentView(R.layout.alert_dialog_stock);

                        final EditText quantite_vendue= (EditText) dialog.findViewById(R.id.quantite_vendue_panier);

                        final Spinner  dropdown_unite = (Spinner)dialog.findViewById(R.id.spinner_choix_unite);

                        final Spinner_Adapter spinnerAdapter = new Spinner_Adapter(ChargementPanierActivity.this,unites);

                        dropdown_unite.setAdapter(spinnerAdapter);

                        dropdown_unite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                                double quantite_commandee=0;
                                double prixModifie=0;

                                unite = spinnerAdapter.getItem(position);
                                //ListePrixLigne listePrixLigne = listePrixLigneManager.getListPrixLigneByLPCACUC(clientCourant.getLISTEPRIX_CODE(), article.getARTICLE_CODE(),unite.getUNITE_CODE());

                                //Log.d("listprixligne", "onItemSelected: "+listePrixLigne.toString());
                                Log.d("unite", "onItemSelected: "+unite.toString());

                                quantite_vendue.getText().clear();
                                //prix_defaut.getText().clear();



                                StockDemandeLigne stockDemandeLigne1= getStockDemandeLigne(article,unite.getUNITE_CODE(),stockDemandeLignes);
                                if(stockDemandeLigne1.getQTE_COMMANDEE()>0){
                                    quantite_vendue.setText(String.valueOf((int)stockDemandeLigne1.getQTE_COMMANDEE()));
                                }

                                //quantite_vendue.setText(String.valueOf((int)stockDemandeLigne1.getQTE_COMMANDEE()));

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }

                        });

                        Button annuler_panier= (Button) dialog.findViewById(R.id.annuler_panier);
                        Button valider_panier= (Button) dialog.findViewById(R.id.valider_panier);


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

                                int quantite_article=0;
                                double prixArticle=0;
                                String unite_code="";
                                Log.d("valider", "valeur: "+quantite_vendue.getText());

                                if(unite==null){

                                    Toast.makeText(getApplicationContext(),"Merci de Choisir une unité" , Toast.LENGTH_LONG).show();
                                    dialog.dismiss();

                                    //Log.d("unite1", "onClick: "+unite_code);

                                }else{

                                    unite_code=unite.getUNITE_CODE();
                                    //Log.d("unite2", "onClick: "+unite_code);

                                }
                                if(!String.valueOf(quantite_vendue.getText()).equals("")){
                                    quantite_article = Integer.parseInt(String.valueOf(quantite_vendue.getText()));
                                }


                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
                                String dateLivraison = df.format(Calendar.getInstance().getTime());

                                // ArticlePrixManager articlePrixManager = new ArticlePrixManager(getApplicationContext());

                                //Articleprix articlePrix = articlePrixManager.getArticlePrixBy_Unite_ArticlePrix(article.getARTICLE_CODE(), dropdown_unite.getSelectedItem().toString(), clientCourant.getCIRCUIT_CODE(), dateLivraison);

                                //ListePrixLigne listePrixLigne = listePrixLigneManager.getListPrixLigneByLPCACUC(clientCourant.getLISTEPRIX_CODE(), article.getARTICLE_CODE(),dropdown_unite.getSelectedItem().toString());
                                //double prixArticle = articlePrix.getARTICLE_PRIX();

                                /*if(listePrixLigne!=null){
                                    prixArticle = listePrixLigne.getARTICLE_PRIX();
                                }*/

                                prixArticle = 0;

                                StockDemandeLigne stockDemandeLigne1= getStockDemandeLigne(article,unite_code,stockDemandeLignes);

                                if(commandeSource.equals("Livraison") && quantite_article>stockDemandeLigne1.getQTE_COMMANDEE()){

                                    dialog.dismiss();
                                    chargementPanierAdapter.notifyDataSetChanged();
                                    Toast.makeText(getApplicationContext(),"Vous ne pouver pas dépasser le max du quantite à livrer" , Toast.LENGTH_LONG).show();

                                }else{
                                    supprimerStockDemandeLigne(article,unite_code,stockDemandeLignes);

                                    if(quantite_article!=0 && !String.valueOf(quantite_vendue.getText()).equals("") && unite!=null){
                                        if(commandeSource.equals("Avoir")){
                                            int quantite=-quantite_article;
                                            ajouterStockDemandeLigne(stockDemande,article,quantite,unite_code,prixArticle,stockDemandeLignes);
                                            //updateCommande(commande,commandeLignes);
                                            //Log.d("newpanieravoir", "onClick: "+commande.toString());
                                        }else{
                                            ajouterStockDemandeLigne(stockDemande,article,quantite_article,unite_code,prixArticle,stockDemandeLignes);
                                            //updateCommande(commande,commandeLignes);
                                            //Log.d("newpanierautre", "onClick: "+commande.toString());
                                        }

                                    }

                                    //updateCommande(commande,commandeLignes);

                                    Log.d("newpanier", "onClick: "+stockDemande.toString());

                                    //total_panier.setText(String.valueOf(commande.getVALEUR_COMMANDE())+"DH");
                                    //total_vente.setText(String.valueOf(commande.getVALEUR_COMMANDE())+"DH");

                                    dialog.dismiss();

                                    chargementPanierAdapter.notifyDataSetChanged();

                                }

                            }

                        });

                        dialog.show();
                    }
                });

                val.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), ViewChargementActivity.class);
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
        Toast.makeText(getApplicationContext(),"Merci de valider votre Panier" , Toast.LENGTH_LONG).show();

    }


    public void supprimerStockDemandeLigne(Article article, String unite, ArrayList<StockDemandeLigne> stockDemandeLignes){

        ArrayList<StockDemandeLigne> stockDemandeLignes1 = stockDemandeLignes;

        for(int i=0;i<stockDemandeLignes1.size();i++){
            if(stockDemandeLignes1.get(i).getARTICLE_CODE().equals(article.getARTICLE_CODE()) && stockDemandeLignes1.get(i).getUNITE_CODE().equals(unite)){
                Log.d("valider", "commandeligne: "+stockDemandeLigne);
                stockDemandeLignes1.remove(i);
            }
        }

        ViewChargementActivity.stockDemandeLignes = stockDemandeLignes1;
    }

    public StockDemandeLigne getStockDemandeLigne(Article article, String unite, ArrayList<StockDemandeLigne> stockDemandeLignes){

        StockDemandeLigne stockDemandeLigne = new StockDemandeLigne();

        for(int i=0;i<stockDemandeLignes.size();i++){
            if(stockDemandeLignes.get(i).getARTICLE_CODE().equals(article.getARTICLE_CODE()) && stockDemandeLignes.get(i).getUNITE_CODE().equals(unite)){
                stockDemandeLigne=stockDemandeLignes.get(i);
                break;
            }
        }
        return stockDemandeLigne;
    }

    public void ajouterStockDemandeLigne(StockDemande stockDemande, Article article, int quantite, String unite, double prixArticle, ArrayList<StockDemandeLigne> stockDemandeLignes){

        ArrayList<StockDemandeLigne> stockDemandeLignes1 = stockDemandeLignes;
        StockDemandeLigne stockDemandeLigne = new StockDemandeLigne();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        String dateLivraison = df.format(Calendar.getInstance().getTime());

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);


        int size= stockDemandeLignes1.size()+1;

        if(stockDemande.getTYPE_CODE().equals("TP0025")){

             stockDemandeLigne = new StockDemandeLigne(size,stockDemande.getDEMANDE_CODE(),article,dateLivraison,-abs(quantite),unite,prixArticle,getApplicationContext());

        }else{
             stockDemandeLigne = new StockDemandeLigne(size,stockDemande.getDEMANDE_CODE(),article,dateLivraison,quantite,unite,prixArticle,getApplicationContext());

        }

        Log.d("StockDemandeLignes", "ajouterStockDemandeLigne: "+stockDemandeLigne.toString());
        stockDemandeLignes1.add(stockDemandeLigne);

        ViewChargementActivity.stockDemandeLignes = stockDemandeLignes1;
    }



}
