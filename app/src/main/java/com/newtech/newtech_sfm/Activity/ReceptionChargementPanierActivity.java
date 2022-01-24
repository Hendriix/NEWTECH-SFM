package com.newtech.newtech_sfm.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.newtech.newtech_sfm.Configuration.Panier_Adapter;
import com.newtech.newtech_sfm.Configuration.ReceptionChargementPanier_Adapter;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.Famille;
import com.newtech.newtech_sfm.Metier.StockDemande;
import com.newtech.newtech_sfm.Metier.StockDemandeLigne;
import com.newtech.newtech_sfm.Metier.Unite;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.Metier_Manager.FamilleManager;
import com.newtech.newtech_sfm.Metier_Manager.ListePrixLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.StockDemandeLigneManager;
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

public class ReceptionChargementPanierActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    public static Panier_Adapter panier_adapter;
    public ArrayList<StockDemandeLigne>  stockDemandeLignes = ViewReceptionChargementActivity.stockDemandeLignes;

    public StockDemande stockDemande = ViewReceptionChargementActivity.stockDemande;
    public String commandeSource= ViewReceptionChargementActivity.commandeSource;
    public static String commandeType;

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
        setContentView(R.layout.catalogue_article_reception_chargement);

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

       /* if(stockDemandeLignes.size()>0){

            for(int i=0;i<stockDemandeLignes.size();i++){
                stockDemandeLigne =stockDemandeLignes.get(i);
                //prix_article= (float) articlePrixManager.getArticlePrixBy_Unite_ArticlePrix(commandeLigne.getARTICLE_CODE(),commandeLigne.getUNITE_CODE(), clientCourant.getCIRCUIT_CODE(),date).getARTICLE_PRIX();
                prix_article= (float) listePrixLigneManager.getListPrixLigneByLPCACUC(clientCourant.getLISTEPRIX_CODE(),stockDemandeLigne.getARTICLE_CODE(),stockDemandeLigne.getUNITE_CODE()).getARTICLE_PRIX();
                valeur_total_panier+=prix_article*(stockDemandeLigne.getQTE_COMMANDEE());
            }

            //total_panier.setText(String.format("%.1f",valeur_total_panier)+"DH");
            //total_vente.setText(String.format("%.1f",valeur_total_panier)+"DH");

        }else{
            //total_panier.setText(String.valueOf(0)+"DH");
            //total_vente.setText(String.valueOf(0)+"DH");
        }*/

        valeur_total_panier = 0;
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

                final ReceptionChargementPanier_Adapter receptionChargementPanierAdapter= new ReceptionChargementPanier_Adapter(ReceptionChargementPanierActivity.this, listArticleCatalogue,getApplicationContext());

                mListView.setAdapter(receptionChargementPanierAdapter);
                /*mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        final Article article =(Article) receptionChargementPanierAdapter.getItem(position);
                        UniteManager uniteManager = new UniteManager(getApplicationContext());
                        ArrayList<Unite> unites = new ArrayList<>();
                        unites = uniteManager.getListByArticleCode(article.getARTICLE_CODE());



                        final Dialog dialog = new Dialog(view.getContext());
                        dialog.setContentView(R.layout.alert_dialog_stock);

                        final EditText quantite_vendue= (EditText) dialog.findViewById(R.id.quantite_vendue_panier);

                        final Spinner  dropdown_unite = (Spinner)dialog.findViewById(R.id.spinner_choix_unite);

                        final Spinner_Adapter spinnerAdapter = new Spinner_Adapter(ReceptionChargementPanierActivity.this,unites);

                        dropdown_unite.setAdapter(spinnerAdapter);

                        dropdown_unite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                                double quantite_commandee=0;
                                double prixModifie=0;

                                unite = spinnerAdapter.getItem(position);
                                ListePrixLigne listePrixLigne = listePrixLigneManager.getListPrixLigneByLPCACUC(clientCourant.getLISTEPRIX_CODE(), article.getARTICLE_CODE(),unite.getUNITE_CODE());

                                Log.d("listprixligne", "onItemSelected: "+listePrixLigne.toString());
                                Log.d("unite", "onItemSelected: "+unite.toString());

                                quantite_vendue.getText().clear();
                                //prix_defaut.getText().clear();

                                StockDemandeLigne stockDemandeLigne1= getStockDemandeLigne(article,unite.getUNITE_CODE(),stockDemandeLignes);
                                quantite_vendue.setText(String.valueOf((int)stockDemandeLigne1.getQTE_COMMANDEE()));

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

                                float quantite_article=0;
                                double prixArticle=0;
                                Log.d("valider", "valeur: "+quantite_vendue.getText());

                                if(!String.valueOf(quantite_vendue.getText()).equals("")){
                                    quantite_article = Integer.parseInt(String.valueOf(quantite_vendue.getText()));
                                }


                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
                                String dateLivraison = df.format(Calendar.getInstance().getTime());

                                prixArticle = 0;

                                StockDemandeLigne stockDemandeLigne1= getStockDemandeLigne(article,unite.getUNITE_CODE(),stockDemandeLignes);

                                if(commandeType.equals("Reception") && quantite_article>stockDemandeLigne1.getQTE_LIVREE()){

                                    dialog.dismiss();
                                    receptionChargementPanierAdapter.notifyDataSetChanged();
                                    Toast.makeText(getApplicationContext(),"Vous ne pouver pas dépasser le max du quantite à Réceptionner" , Toast.LENGTH_LONG).show();

                                }else{


                                    supprimerStockDemandeLigne(article,unite.getUNITE_CODE(),stockDemandeLignes);

                                    if(quantite_article!=0 && !String.valueOf(quantite_vendue.getText()).equals("")){
                                        if(commandeSource.equals("TP0025")){

                                            float quantite=-quantite_article;
                                            ajouterStockDemandeLigne(stockDemande.getDEMANDE_CODE(),article,quantite,unite.getUNITE_CODE(),prixArticle,stockDemandeLignes);
                                            //updateCommande(commande,commandeLignes);
                                            //Log.d("newpanieravoir", "onClick: "+commande.toString());
                                        }else{

                                            ajouterStockDemandeLigne(stockDemande.getDEMANDE_CODE(),article,quantite_article,unite.getUNITE_CODE(),prixArticle,stockDemandeLignes);
                                            //updateCommande(commande,commandeLignes);
                                            //Log.d("newpanierautre", "onClick: "+commande.toString());
                                        }

                                    }

                                    //updateCommande(commande,commandeLignes);

                                    Log.d("newpanier", "onClick: "+stockDemande.toString());

                                    //total_panier.setText(String.valueOf(commande.getVALEUR_COMMANDE())+"DH");
                                    //total_vente.setText(String.valueOf(commande.getVALEUR_COMMANDE())+"DH");

                                    dialog.dismiss();

                                    receptionChargementPanierAdapter.notifyDataSetChanged();

                                }

                            }

                        });

                        dialog.show();
                    }
                });*/

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getApplicationContext(),"Vous ne pouver pas modifier les quantités livrées" , Toast.LENGTH_LONG).show();
                    }
                });

                val.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        StockDemandeLigneManager stockDemandeLigneManager = new StockDemandeLigneManager(getApplicationContext());
                        ViewReceptionChargementActivity.stockDemandeLignes = stockDemandeLigneManager.updateList(ViewReceptionChargementActivity.stockDemandeLignes);

                        Intent i = new Intent(getApplicationContext(), ViewReceptionChargementActivity.class);
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

        for(int i=0;i<stockDemandeLignes.size();i++){
            if(stockDemandeLignes.get(i).getARTICLE_CODE().equals(article.getARTICLE_CODE()) && stockDemandeLignes.get(i).getUNITE_CODE().equals(unite)){
                Log.d("valider", "commandeligne: "+stockDemandeLigne);
                stockDemandeLignes.remove(i);
            }
        }
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

    public void ajouterStockDemandeLigne(String stockdemande_code, Article article, int quantite, String unite, double prixArticle, ArrayList<StockDemandeLigne> stockDemandeLignes){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        String dateLivraison = df.format(Calendar.getInstance().getTime());

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);


        int size= stockDemandeLignes.size()+1;

        StockDemandeLigne stockDemandeLigne = new StockDemandeLigne(size,stockdemande_code,article,dateLivraison,quantite,unite,prixArticle,getApplicationContext());

        Log.d("StockDemandeLignes", "ajouterStockDemandeLigne: "+stockDemandeLigne.toString());
        stockDemandeLignes.add(stockDemandeLigne);
    }



}
