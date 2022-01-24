package com.newtech.newtech_sfm.Activity;

import static com.newtech.newtech_sfm.Activity.ClientActivity.clientCourant;
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
import com.newtech.newtech_sfm.Configuration.Panier_Adapter;
import com.newtech.newtech_sfm.Configuration.Spinner_Adapter;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier.Famille;
import com.newtech.newtech_sfm.Metier.ListePrixLigne;
import com.newtech.newtech_sfm.Metier.Unite;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.Metier_Manager.FamilleManager;
import com.newtech.newtech_sfm.Metier_Manager.ListePrixLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.StockLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.StockManager;
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

public class NewPanierActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    public static Panier_Adapter panier_adapter;
    public ArrayList<CommandeLigne>  commandeLignes = ViewCommandeActivity.ListeCommandeLigne;

    public Commande commande= ViewCommandeActivity.commande;
    public String commandeSource= ViewCommandeActivity.commandeSource;

    List<Article> article_list = new ArrayList<Article>();

    ListView mListView;
    public TextView total_panier;
    public TextView total_vente;
    public Button val;
    public float valeur_total_panier=0;
    EditText quantite_vendue;
    EditText prix_defaut;
    EditText prix_modifie;
    Spinner  dropdown_unite ;
    Spinner_Adapter spinnerAdapter;

    Unite unite;
    CommandeLigne commandeLigne = new CommandeLigne();

    float prixArticle=0;

    public static HashMap<String,List<Article>> listArticleParFamilleCode = new HashMap<String, List<Article>>();

    StockLigneManager stockLigneManager;
    StockManager stockManager;
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
        final ListePrixLigneManager listePrixLigneManager = new ListePrixLigneManager(getApplicationContext());

        stockLigneManager = new StockLigneManager(getApplicationContext());
        stockManager = new StockManager(getApplicationContext());
        stockManager.updateStock(getApplicationContext());

        final Spinner dropdown = (Spinner)findViewById(R.id.famille_spinner);
        mListView= (ListView) findViewById(R.id.panier_listview1);

        float prix_article=0;
        total_panier=(TextView) findViewById(R.id.total_panier);
        total_vente=(TextView) findViewById(R.id.total_vente);
        val = (Button) findViewById(R.id.validernewpanier);

        //ArticlePrixManager articlePrixManager = new ArticlePrixManager(getApplicationContext());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        String date =df.format(Calendar.getInstance().getTime());

        if(commandeLignes.size()>0){

            for(int i=0;i<commandeLignes.size();i++){
                commandeLigne=commandeLignes.get(i);
                //prix_article= (float) articlePrixManager.getArticlePrixBy_Unite_ArticlePrix(commandeLigne.getARTICLE_CODE(),commandeLigne.getUNITE_CODE(), clientCourant.getCIRCUIT_CODE(),date).getARTICLE_PRIX();
                //prix_article= (float) listePrixLigneManager.getListePrixLigneByLPCACUC(clientCourant.getLISTEPRIX_CODE(),commandeLigne.getARTICLE_CODE(),commandeLigne.getUNITE_CODE()).getARTICLE_PRIX();
                prix_article =(float) commandeLigne.getARTICLE_PRIX();
                valeur_total_panier+=prix_article*(commandeLigne.getQTE_COMMANDEE());
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

                final  Panier_Adapter panierAdapter= new Panier_Adapter(NewPanierActivity.this, listArticleCatalogue,getApplicationContext());

                mListView.setAdapter(panierAdapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        final Article article =(Article) panierAdapter.getItem(position);
                        UniteManager uniteManager = new UniteManager(getApplicationContext());
                        ArrayList<Unite> unites = new ArrayList<>();
                        unites = uniteManager.getListByArticleCode(article.getARTICLE_CODE());

                        Log.d("unites", "onItemClick: "+unites.toString());

                       /* ArrayList<String> items= new ArrayList<>() ;
                        items.add("CAISSE");
                        items.add("BOUTEILLE");*/

                        final Dialog dialog = new Dialog(view.getContext());
                        dialog.setContentView(R.layout.alert_dialog_panier);

                        quantite_vendue= (EditText) dialog.findViewById(R.id.quantite_vendue_panier);

                        prix_defaut= (EditText) dialog.findViewById(R.id.prix_par_defaut);

                        //prix_modifie= (EditText) dialog.findViewById(R.id.prix_modifie);

                        dropdown_unite = (Spinner)dialog.findViewById(R.id.spinner_choix_unite);

                        prix_defaut.setEnabled(false);

                        spinnerAdapter = new Spinner_Adapter(NewPanierActivity.this,unites);

                        dropdown_unite.setAdapter(spinnerAdapter);

                 dropdown_unite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {


                        int quantite_commandee=0;
                        double prixModifie=0;

                        unite = spinnerAdapter.getItem(position);
                        ListePrixLigne listePrixLigne = listePrixLigneManager.getListPrixLigneByLPCACUC(clientCourant.getLISTEPRIX_CODE(), article.getARTICLE_CODE(),unite.getUNITE_CODE());

                        //Log.d("listprixligne", "onItemSelected: "+listePrixLigne.toString());
                        //Log.d("unite", "onItemSelected: "+unite.toString());

                        quantite_vendue.getText().clear();
                        prix_defaut.getText().clear();

                        CommandeLigne commandeLigne1= getCommandeLigne(article,unite.getUNITE_CODE(),commandeLignes);
                        Log.d("NewPanierActivity", "onItemSelected: "+commandeLigne1.toString());
                        if(commandeLigne1.getQTE_COMMANDEE()>0){

                            quantite_vendue.setText(String.valueOf((int)commandeLigne1.getQTE_COMMANDEE()));
                        }

                        prix_defaut.setText(String.valueOf((float)listePrixLigne.getARTICLE_PRIX()));

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

                        if(checkValidation()){

                            float quantite_article=0;
                            double prixDefault=0;
                            double prixArticle=0;
                            String unite_code="";

                            //unite_code = unite.getUNITE_CODE();
                            //Log.d("unite", "onClick: "+unite_code);

                            if(unite==null){

                                Toast.makeText(getApplicationContext(),"Merci de Choisir une unité" , Toast.LENGTH_LONG).show();
                                dialog.dismiss();

                                //Log.d("unite1", "onClick: "+unite_code);

                            }else{

                                unite_code=unite.getUNITE_CODE();
                                //Log.d("unite2", "onClick: "+unite_code);

                            }

                            //Log.d("valider", "valeur: "+quantite_vendue.getText());

                            if(!String.valueOf(quantite_vendue.getText()).equals("")){
                                quantite_article = Integer.parseInt(String.valueOf(quantite_vendue.getText()));
                            }

                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
                            String dateLivraison = df.format(Calendar.getInstance().getTime());


                            ListePrixLigne listePrixLigne = listePrixLigneManager.getListPrixLigneByLPCACUC(clientCourant.getLISTEPRIX_CODE(), article.getARTICLE_CODE(),unite_code);


                            if(listePrixLigne!=null){
                                prixDefault = listePrixLigne.getARTICLE_PRIX();
                            }


                            CommandeLigne commandeLigne1= getCommandeLigne(article,unite_code,commandeLignes);

                           //Log.d("article", "onClick: "+article.toString());
                            if(commandeSource.equals("Livraison") && quantite_article>commandeLigne1.getQTE_COMMANDEE()){

                                dialog.dismiss();
                                panierAdapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(),"Vous ne pouver pas dépasser le max du quantite à livrer" , Toast.LENGTH_LONG).show();

                            }else{
                                supprimerCommandeLigne(article,unite_code,commandeLignes);

                                if(quantite_article!=0 && !String.valueOf(quantite_vendue.getText()).equals("") && unite!=null){

                                    ajouterCommandeLigne(commande.getCOMMANDE_CODE(),commandeSource,article,quantite_article,unite_code,prixDefault,commandeLignes);

                                    if(stockLigneManager.checkQteMinVersion(commandeLignes,article.getARTICLE_CODE(),getApplicationContext())== false){

                                        supprimerCommandeLigne(article,unite_code,commandeLignes);
                                        Toast.makeText(getApplicationContext(),"Il faut respecter la quantité minimale" , Toast.LENGTH_LONG).show();

                                    }else{

                                        if(stockManager.checkGerable(commande.getVENDEUR_CODE(),getApplicationContext())){

                                            //Log.d("stockgerable", "onClick: stock gerable");

                                            if(stockLigneManager.checkStockLigneQteVersion(commandeLignes,article.getARTICLE_CODE(),getApplicationContext())== false){

                                                supprimerCommandeLigne(article,unite_code,commandeLignes);
                                                Toast.makeText(getApplicationContext(),"Stock Insuffisant" , Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    }

                                    updateCommande(commande,commandeLignes);


                                }

                                //updateCommande(commande,commandeLignes);



                                total_panier.setText(String.valueOf(commande.getVALEUR_COMMANDE())+"DH");
                                total_vente.setText(String.valueOf(commande.getVALEUR_COMMANDE())+"DH");

                                unite = null;

                                dialog.dismiss();

                                panierAdapter.notifyDataSetChanged();

                            }

                        }
                    }


                });

                        dialog.show();
                    }
                });

                val.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        /*if(commandeLignes.size()>0){

                            Intent i = new Intent(getApplicationContext(), LieuLivraisonActivity.class);
                            startActivity(i);
                            finish();

                        }else{

                            Intent i = new Intent(getApplicationContext(), ViewCommandeActivity.class);
                            startActivity(i);
                            finish();
                        }*/

                        Intent i = new Intent(getApplicationContext(), ViewCommandeActivity.class);
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


    /*public void supprimerCommandeLigne(Article article, String unite, ArrayList<CommandeLigne> commandeLignes){

        for(int i=0;i<commandeLignes.size();i++){
            if(commandeLignes.get(i).getARTICLE_CODE().equals(article.getARTICLE_CODE()) && commandeLignes.get(i).getUNITE_CODE().equals(unite)){
                Log.d("valider", "commandeligne: "+commandeLigne);
                commandeLignes.remove(i);
            }
        }
    }*/

    public void supprimerCommandeLigne(Article article, String unite, ArrayList<CommandeLigne> commandeLignes){

        ArrayList<CommandeLigne> commandeLignes1 = commandeLignes;


        for(int i=0;i<commandeLignes1.size();i++){
            if(commandeLignes1.get(i).getARTICLE_CODE().equals(article.getARTICLE_CODE()) && commandeLignes1.get(i).getUNITE_CODE().equals(unite)){
                //Log.d("valider", "commandeligne: "+commandeLigne);
                commandeLignes1.remove(i);
            }
        }

        ViewCommandeActivity.ListeCommandeLigne = commandeLignes1;

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

    public void ajouterCommandeLigne(String commande_code,String commandeSource, Article article, float quantite, String unite,double prixDefault, ArrayList<CommandeLigne> commandeLignes){

        ArrayList<CommandeLigne> commandeLignes1 = commandeLignes;
        CommandeLigne commandeLigne = new CommandeLigne();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        String dateLivraison = df.format(Calendar.getInstance().getTime());

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);


        int size= commandeLignes1.size()+1;

        if(commandeSource.equals("Avoir")){

            commandeLigne = new CommandeLigne(commande_code,article,-abs(quantite),unite,prixDefault,size,getApplicationContext());

        }else if(commandeSource.equals("Commande")){

            commandeLigne = new CommandeLigne(commande_code,article,dateLivraison,abs(quantite),unite,prixDefault,size,getApplicationContext());

        }else if(commandeSource.equals("Facturation")){

            commandeLigne = new CommandeLigne(commande_code,article,abs(quantite),unite,prixDefault,size,getApplicationContext());

        }
        commandeLignes1.add(commandeLigne);

        ViewCommandeActivity.ListeCommandeLigne=commandeLignes1;
    }

    public void updateCommande(Commande commande, ArrayList<CommandeLigne> commandeLignes){

        Commande commande1 = commande;

        //Log.d("newpanieravant", "updateCommande: "+commande.toString());

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

        commande1.setMONTANT_BRUT(MONTANT_BRUT);
        commande1.setMONTANT_NET(MONTANT_NET);
        commande1.setLITTRAGE_COMMANDE(LITTRAGE_COMMANDE);
        commande1.setTONNAGE_COMMANDE(TONNAGE_COMMANDE);
        commande1.setKG_COMMANDE(KG_COMMANDE);
        commande1.setVALEUR_COMMANDE(VALEUR_COMMANDE);

        Log.d("newpanierapres", "updateCommande: "+commande1.toString());

        ViewCommandeActivity.commande = commande1;


    }

    private boolean checkValidation() {
        boolean result = true;

        return result;
    }

}
