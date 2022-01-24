package com.newtech.newtech_sfm.Activity;

import static com.newtech.newtech_sfm.Activity.ClientActivity.clientCourant;

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
import com.newtech.newtech_sfm.Configuration.Panier_Adapter;
import com.newtech.newtech_sfm.Configuration.Spinner_Adapter;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.Articleprix;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier.Famille;
import com.newtech.newtech_sfm.Metier.Livraison;
import com.newtech.newtech_sfm.Metier.LivraisonLigne;
import com.newtech.newtech_sfm.Metier.Unite;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.Metier_Manager.ArticlePrixManager;
import com.newtech.newtech_sfm.Metier_Manager.FamilleManager;
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

public class LivraisonPanierActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    public static Panier_Adapter panier_adapter;

    public ArrayList<LivraisonLigne>  livraisonLignes = ViewLivraisonActivity.livraisonLignes;
    public Livraison livraison = ViewLivraisonActivity.livraison;

    public String commandeSource= ViewCommandeActivity.commandeSource;
    List<Article> article_list = new ArrayList<Article>();
    ListView mListView;
    public TextView total_panier;
    public TextView total_vente;
    public Button val;
    public float valeur_total_panier=0;

    LivraisonLigne livraisonLigne = new LivraisonLigne();
    float prixArticle=0;

    public static HashMap<String,List<Article>> listArticleParFamilleCode = new HashMap<String, List<Article>>();

    //final Context context=this.getApplicationContext();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalogue_article_panier);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_client);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final ArticleManager articleManager= new ArticleManager(getApplicationContext());
        final Spinner dropdown = (Spinner)findViewById(R.id.famille_spinner);
        mListView= (ListView) findViewById(R.id.panier_listview1);

        float prix_article=0;
        total_panier=(TextView) findViewById(R.id.total_panier);
        total_vente=(TextView) findViewById(R.id.total_vente);
        val = (Button) findViewById(R.id.validernewpanier);

        ArticlePrixManager articlePrixManager = new ArticlePrixManager(getApplicationContext());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        String date =df.format(Calendar.getInstance().getTime());

        if(livraisonLignes.size()>0){

            for(int i=0;i<livraisonLignes.size();i++){
                livraisonLigne=livraisonLignes.get(i);
                prix_article= (float) articlePrixManager.getArticlePrixBy_Unite_ArticlePrix(livraisonLigne.getARTICLE_CODE(),livraisonLigne.getUNITE_CODE(), clientCourant.getCIRCUIT_CODE(),date).getARTICLE_PRIX();
                valeur_total_panier+=prix_article*(livraisonLigne.getQTE_COMMANDEE());
            }

            total_panier.setText(String.format("%.1f",valeur_total_panier)+"DH");
            total_vente.setText(String.format("%.1f",valeur_total_panier)+"DH");

        }else{
            total_panier.setText(String.valueOf(0)+"DH");
            total_vente.setText(String.valueOf(0)+"DH");
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

                final  Panier_Adapter panierAdapter= new Panier_Adapter(LivraisonPanierActivity.this, listArticleCatalogue,getApplicationContext());

                mListView.setAdapter(panierAdapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        final Article article =(Article) panierAdapter.getItem(position);
                        UniteManager uniteManager = new UniteManager(getApplicationContext());
                        ArrayList<Unite> unites = new ArrayList<>();
                        unites = uniteManager.getListByArticleCode(article.getARTICLE_CODE());

                        /*ArrayList<String> items= new ArrayList<>() ;
                        items.add("CAISSE");
                        items.add("BOUTEILLE");*/


                        final Dialog dialog = new Dialog(view.getContext());
                        dialog.setContentView(R.layout.alert_dialog_panier);

                        final EditText quantite_vendue= (EditText) dialog.findViewById(R.id.quantite_vendue_panier);

                        final Spinner  dropdown_unite = (Spinner)dialog.findViewById(R.id.spinner_choix_unite);

                        final Spinner_Adapter spinnerAdapter = new Spinner_Adapter(LivraisonPanierActivity.this,unites);

                        dropdown_unite.setAdapter(spinnerAdapter);

                 dropdown_unite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                    String unite = spinnerAdapter.getItem(position).toString();

                    if(unite.equals("CAISSE")) {

                        quantite_vendue.getText().clear();
                        double caisse_commandee=0;

                        for (int i = 0; i < livraisonLignes.size(); i++) {

                            String unite_code=livraisonLignes.get(i).getUNITE_CODE();
                            String article_code=livraisonLignes.get(i).getARTICLE_CODE();


                            if (article_code.equals(article.getARTICLE_CODE()) && unite_code.equals("CAISSE")) {

                                caisse_commandee+=(livraisonLignes.get(i).getQTE_COMMANDEE());


                            }

                        }
                        quantite_vendue.setText(String.valueOf((int)caisse_commandee));
                    }else if(unite.equals("BOUTEILLE")){

                        quantite_vendue.getText().clear();
                        double bouteille_commandee=0;

                        for (int i = 0; i < livraisonLignes.size(); i++) {

                            String unite_code=livraisonLignes.get(i).getUNITE_CODE();
                            String article_code=livraisonLignes.get(i).getARTICLE_CODE();


                            if (article_code.equals(article.getARTICLE_CODE()) && unite_code.equals("BOUTEILLE")){

                                bouteille_commandee+=(livraisonLignes.get(i).getQTE_COMMANDEE());

                            }


                        }
                        quantite_vendue.setText(String.valueOf((int)bouteille_commandee));
                    }

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
                        Log.d("valider", "valeur: "+quantite_vendue.getText());

                        if(!String.valueOf(quantite_vendue.getText()).equals("")){
                            quantite_article = Integer.parseInt(String.valueOf(quantite_vendue.getText()));
                        }


                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
                        String dateLivraison = df.format(Calendar.getInstance().getTime());

                        ArticlePrixManager articlePrixManager = new ArticlePrixManager(getApplicationContext());
                        Articleprix articlePrix = articlePrixManager.getArticlePrixBy_Unite_ArticlePrix(article.getARTICLE_CODE(), dropdown_unite.getSelectedItem().toString(), clientCourant.getCIRCUIT_CODE(), dateLivraison);
                        double prixArticle = articlePrix.getARTICLE_PRIX();


                        LivraisonLigne livraisonLigne1= getLivraisonLigne(article,dropdown_unite.getSelectedItem().toString(),livraisonLignes);

                        if(commandeSource.equals("Livraison") && quantite_article>livraisonLigne1.getQTE_COMMANDEE()){

                            dialog.dismiss();
                            panierAdapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(),"Vous ne pouver pas dépasser le max du quantite à livrer" , Toast.LENGTH_LONG).show();

                        }else{
                            supprimerLivraisonLigne(article,dropdown_unite.getSelectedItem().toString(),livraisonLignes);



                            if(quantite_article!=0 && !String.valueOf(quantite_vendue.getText()).equals("")){

                                    ajouterLivraisonLigne(livraison.getCOMMANDE_CODE(),article,quantite_article,dropdown_unite.getSelectedItem().toString(),prixArticle,livraisonLignes);
                                    updateLivraison(livraison,livraisonLignes);
                                    //Log.d("newpanierautre", "onClick: "+commande.toString());


                            }

                            updateLivraison(livraison,livraisonLignes);

                            Log.d("newpanier", "onClick: "+livraison.toString());

                            total_panier.setText(String.valueOf(livraison.getVALEUR_COMMANDE())+"DH");
                            total_vente.setText(String.valueOf(livraison.getVALEUR_COMMANDE())+"DH");

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
        Toast.makeText(getApplicationContext(),"Merci de valider votre Panier" , Toast.LENGTH_LONG).show();

    }


    public void supprimerCommandeLigne(Article article, String unite, ArrayList<CommandeLigne> commandeLignes){

        for(int i=0;i<commandeLignes.size();i++){
            if(commandeLignes.get(i).getARTICLE_CODE().equals(article.getARTICLE_CODE()) && commandeLignes.get(i).getUNITE_CODE().equals(unite)){
                Log.d("valider", "livraisonLigne: "+livraisonLigne);
                commandeLignes.remove(i);
            }
        }
    }

    public CommandeLigne getCommandeLigne(Article article, String unite, ArrayList<CommandeLigne> commandeLignes){

        CommandeLigne commandeLigne = new CommandeLigne();

        for(int i=0;i<commandeLignes.size();i++){
            if(commandeLignes.get(i).getARTICLE_CODE().equals(article.getARTICLE_CODE()) && commandeLignes.get(i).getUNITE_CODE().equals(unite)){
                commandeLigne=commandeLignes.get(i);
               break;
            }
        }
        return commandeLigne;
    }

    public void ajouterCommandeLigne(String commande_code, Article article, float quantite, String unite, double prixArticle, ArrayList<CommandeLigne> commandeLignes){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        String dateLivraison = df.format(Calendar.getInstance().getTime());

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);


        int size= commandeLignes.size()+1;

        CommandeLigne commandeLigne = new CommandeLigne(commande_code,article,dateLivraison,quantite,unite,prixArticle,size,getApplicationContext());
        commandeLignes.add(commandeLigne);
    }

    public void updateCommande(Commande commande, ArrayList<CommandeLigne> commandeLignes){

        Log.d("newpanieravant", "updateCommande: "+commande.toString());

        double MONTANT_BRUT=0;
        double MONTANT_NET=0;
        double LITTRAGE_COMMANDE=0;
        double TONNAGE_COMMANDE=0;
        double KG_COMMANDE=0;
        double VALEUR_COMMANDE=0;

        for (int i = 0; i < commandeLignes.size(); i++) {

            MONTANT_BRUT+= commandeLignes.get(i).getMONTANT_BRUT();
            MONTANT_NET+= commandeLignes.get(i).getMONTANT_NET();
            LITTRAGE_COMMANDE+= commandeLignes.get(i).getLITTRAGE_COMMANDEE();
            TONNAGE_COMMANDE+= commandeLignes.get(i).getTONNAGE_COMMANDEE();
            KG_COMMANDE+= commandeLignes.get(i).getKG_COMMANDEE();

        }
        VALEUR_COMMANDE = MONTANT_NET;

        commande.setMONTANT_BRUT(MONTANT_BRUT);
        commande.setMONTANT_NET(MONTANT_NET);
        commande.setLITTRAGE_COMMANDE(LITTRAGE_COMMANDE);
        commande.setTONNAGE_COMMANDE(TONNAGE_COMMANDE);
        commande.setKG_COMMANDE(KG_COMMANDE);
        commande.setVALEUR_COMMANDE(VALEUR_COMMANDE);

        Log.d("newpanierapres", "updateCommande: "+commande.toString());

    }



    public void supprimerLivraisonLigne(Article article, String unite, ArrayList<LivraisonLigne> livraisonLignes){

        for(int i=0;i<livraisonLignes.size();i++){
            if(livraisonLignes.get(i).getARTICLE_CODE().equals(article.getARTICLE_CODE()) && livraisonLignes.get(i).getUNITE_CODE().equals(unite)){
                Log.d("valider", "livraisonLigne: "+livraisonLigne);
                livraisonLignes.remove(i);
            }
        }
    }

    public LivraisonLigne getLivraisonLigne(Article article, String unite, ArrayList<LivraisonLigne> livraisonLignes){

        LivraisonLigne livraisonLigne = new LivraisonLigne();

        for(int i=0;i<livraisonLignes.size();i++){
            if(livraisonLignes.get(i).getARTICLE_CODE().equals(article.getARTICLE_CODE()) && livraisonLignes.get(i).getUNITE_CODE().equals(unite)){
                livraisonLigne=livraisonLignes.get(i);
                break;
            }
        }
        return livraisonLigne;
    }

    public void ajouterLivraisonLigne(String commande_code, Article article, float quantite, String unite, double prixArticle, ArrayList<LivraisonLigne> livraisonLignes){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        String dateLivraison = df.format(Calendar.getInstance().getTime());

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);


        int size= livraisonLignes.size()+1;

        LivraisonLigne livraisonLigne = new LivraisonLigne(livraison,article,dateLivraison,quantite,unite,prixArticle,size,getApplicationContext());
        livraisonLignes.add(livraisonLigne);
    }

    public void updateLivraison(Livraison livraison, ArrayList<LivraisonLigne> livraisonLignes){

        Log.d("newpanieravant", "updateLivraison: "+livraison.toString());

        double MONTANT_BRUT=0;
        double MONTANT_NET=0;
        double LITTRAGE_COMMANDE=0;
        double TONNAGE_COMMANDE=0;
        double KG_COMMANDE=0;
        double VALEUR_COMMANDE=0;

        for (int i = 0; i < livraisonLignes.size(); i++) {

            MONTANT_BRUT+= livraisonLignes.get(i).getMONTANT_BRUT();
            MONTANT_NET+= livraisonLignes.get(i).getMONTANT_NET();
            LITTRAGE_COMMANDE+= livraisonLignes.get(i).getLITTRAGE_COMMANDEE();
            TONNAGE_COMMANDE+= livraisonLignes.get(i).getTONNAGE_COMMANDEE();
            KG_COMMANDE+= livraisonLignes.get(i).getKG_COMMANDEE();

        }
        VALEUR_COMMANDE = MONTANT_NET;

        livraison.setMONTANT_BRUT(MONTANT_BRUT);
        livraison.setMONTANT_NET(MONTANT_NET);
        livraison.setLITTRAGE_COMMANDE(LITTRAGE_COMMANDE);
        livraison.setTONNAGE_COMMANDE(TONNAGE_COMMANDE);
        livraison.setKG_COMMANDE(KG_COMMANDE);
        livraison.setVALEUR_COMMANDE(VALEUR_COMMANDE);

        Log.d("newlivraisonpanierapres", "updateLivraison: "+livraison.toString());

    }


}
