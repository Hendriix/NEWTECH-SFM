package com.newtech.newtech_sfm.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.AnnulationLivraison.AnnulerLivraisonActivity;
import com.newtech.newtech_sfm.Configuration.ImprimanteManager;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier.Encaissement;
import com.newtech.newtech_sfm.Metier.Impression;
import com.newtech.newtech_sfm.Metier.Livraison;
import com.newtech.newtech_sfm.Metier.LivraisonGratuite;
import com.newtech.newtech_sfm.Metier.LivraisonLigne;
import com.newtech.newtech_sfm.Metier.LivraisonPromotion;
import com.newtech.newtech_sfm.Metier.StockTransfert;
import com.newtech.newtech_sfm.Metier.Unite;
import com.newtech.newtech_sfm.Metier.User;
import com.newtech.newtech_sfm.Metier_Manager.CommandeManager;
import com.newtech.newtech_sfm.Metier_Manager.ImpressionManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonGratuiteManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonPromotionManager;
import com.newtech.newtech_sfm.Metier_Manager.PromotionManager;
import com.newtech.newtech_sfm.Metier_Manager.PromotiongratuiteManager;
import com.newtech.newtech_sfm.Metier_Manager.StockLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.StockManager;
import com.newtech.newtech_sfm.Metier_Manager.StockTransfertManager;
import com.newtech.newtech_sfm.Metier_Manager.UniteManager;
import com.newtech.newtech_sfm.Metier_Manager.UserManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteManager;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.Service.BlutoothConnctionService;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by TONPC on 19/04/2017.
 */

public class ViewLivraisonActivity extends AppCompatActivity{

    private static final String TAG = ViewLivraisonActivity.class.getName();
    public static String commandeSource;
    public static String visite_code;

    public static Commande commande= new Commande();
    public static  ArrayList<CommandeLigne> ListeCommandeLigne = new ArrayList<CommandeLigne>();

    public static Livraison livraison= new Livraison();
    public static  ArrayList<LivraisonLigne> livraisonLignes = new ArrayList<>();
    public ArrayList<LivraisonGratuite> tab_LIVG = new ArrayList<LivraisonGratuite>();
    public ArrayList<LivraisonPromotion> tab_PromosAP = new ArrayList<LivraisonPromotion>();
    public ArrayList<LivraisonPromotion>promo_AP=new ArrayList<LivraisonPromotion>();

    LivraisonLigneManager livraisonLigneManager;
    LivraisonManager livraisonManager;

    VisiteManager visiteManager;
    LivraisonPromotionManager livraisonPromotionManager;
    LivraisonGratuiteManager livraisonGratuiteManager;

    PromotionManager promo_Manager;
    PromotiongratuiteManager promo_Gratuite_Manager;

    Client clientCourant = ClientActivity.clientCourant;
    public static String activite_source;

    public static ImpressionManager impressionManager;
    CommandeManager commandeManager;
    UniteManager uniteManager;
    StockLigneManager stockLigneManager;
    StockManager stockManager;
    StockTransfertManager stockTransfertManager;


    UserManager userManager ;
    User utilisateur;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        userManager = new UserManager(getApplicationContext());
        impressionManager = new ImpressionManager(getApplicationContext());
        commandeManager = new CommandeManager(getApplicationContext());

        uniteManager = new UniteManager(getApplicationContext());
        livraisonLigneManager = new LivraisonLigneManager(getApplicationContext());
        livraisonManager = new LivraisonManager(getApplicationContext());

        livraisonPromotionManager = new LivraisonPromotionManager(getApplicationContext());
        livraisonGratuiteManager = new LivraisonGratuiteManager(getApplicationContext());

        promo_Manager = new PromotionManager(getApplicationContext());
        promo_Gratuite_Manager = new PromotiongratuiteManager(getApplicationContext());

        stockLigneManager = new StockLigneManager(getApplicationContext());
        stockManager = new StockManager(getApplicationContext());
        stockTransfertManager =  new StockTransfertManager(getApplicationContext());

        visiteManager = new VisiteManager(getApplicationContext());


        utilisateur = userManager.get();

        if(livraison!=null){
            Log.d("ViewLivraison Activity", "onCreate: "+livraison.toString());
            Log.d("ViewLivraison Activity", "onCreate: "+livraisonLignes.toString());
        }else{
            Log.d("ViewLivraison Activity", "onCreate: livraison null");
            Log.d("ViewLivraison Activity", "onCreate: "+livraisonLignes.toString());
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_panier);

        TableLayout tableVente= (TableLayout)findViewById(R.id.tableLayout1);
        TableLayout tableVente2= (TableLayout)findViewById(R.id.tableLayout2);


        Button valider =(Button)findViewById(R.id.btn_valider1);
        Button  annuler =(Button)findViewById(R.id.btn_annuler1);

        TextView panier_nbr_total =(TextView) findViewById(R.id.nbr_panier_total1);
        TextView panier_nbr_remise =(TextView) findViewById(R.id.nbr_panier_remise1);
        TextView panier_nbr_net =(TextView) findViewById(R.id.nbr_panier_net1);

        double panierTotal=0;


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);

        SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
        String date_commande=df.format(new java.util.Date());

        String VENDEUR_CODE=utilisateur.getUTILISATEUR_CODE();

        if(livraisonLignes!=null){

            livraisonLignes = livraisonLigneManager.fixLivraisonLigneCode(livraisonLignes);

            Log.d(TAG, "onCreate: LIV 1 : "+livraisonLignes);

            for(int i=0;i<livraisonLignes.size();i++){

                TableRow ligne1 = new TableRow(this);
                TextView ligne_code = new TextView(this);
                TextView art_code = new TextView(this);
                TextView unite = new TextView(this);
                TextView qte = new TextView(this);
                TextView remise1= new TextView(this);
                TextView total = new TextView(this);

                Unite unite1 = uniteManager.get(livraisonLignes.get(i).getUNITE_CODE());

                ligne_code.setEllipsize(TextUtils.TruncateAt.END);
                unite.setEllipsize(TextUtils.TruncateAt.END);

                ligne_code.setGravity(Gravity.CENTER);
                art_code.setGravity(Gravity.CENTER);
                unite.setGravity(Gravity.CENTER);
                qte.setGravity(Gravity.CENTER);
                remise1.setGravity(Gravity.CENTER);
                total.setGravity(Gravity.CENTER);


                ligne_code.setTextSize(15);
                art_code.setTextSize(15);
                unite.setTextSize(15);
                qte.setTextSize(15);
                total.setTextSize(15);

                ligne_code.setBackgroundResource(R.drawable.cell_shape);
                art_code.setBackgroundResource(R.drawable.cell_shape);
                unite.setBackgroundResource(R.drawable.cell_shape);
                qte.setBackgroundResource(R.drawable.cell_shape);
                remise1.setBackgroundResource(R.drawable.cell_shape);
                total.setBackgroundResource(R.drawable.cell_shape);

                ligne_code.setText(String.valueOf(livraisonLignes.get(i).getLIVRAISONLIGNE_CODE()));
                art_code.setText(livraisonLignes.get(i).getARTICLE_CODE());
                unite.setText(unite1.getUNITE_NOM());
                qte.setText(String.valueOf(livraisonLignes.get(i).getQTE_COMMANDEE()));
                remise1.setText("0");
                total.setText(String.format("%.1f",livraisonLignes.get(i).getMONTANT_NET()));

                ligne1.addView(ligne_code);
                ligne1.addView(art_code);
                ligne1.addView(unite);
                ligne1.addView(qte);
                ligne1.addView(remise1);
                ligne1.addView(total);

                tableVente.addView(ligne1);
            }
        }

        panierTotal=livraisonManager.getMontantNet(livraisonLignes);

        panier_nbr_total.setText(String.valueOf(new DecimalFormat("##.##").format(panierTotal)));

        double MONTANT_BRUT = 0;
        double REMISE = 0;
        double MONTANT_NET = 0;
        double VALEUR_COMMANDEE = 0;
        double LITTRAGE_COMMANDEE = 0;
        double TONNAGE_COMMANDEE = 0;
        double KG_COMMANDEE = 0;
        int livraisonlignes_size = 0;

        //livraison = new Livraison(commande,livraison.getLIVRAISON_CODE(),getApplicationContext());

        livraisonlignes_size = livraisonLigneManager.getListByLivCode(livraison.getLIVRAISON_CODE()).size();

        for(int i=0;i<livraisonLignes.size();i++){

            MONTANT_BRUT += livraisonLignes.get(i).getMONTANT_BRUT();
            REMISE += livraisonLignes.get(i).getREMISE();
            MONTANT_NET += livraisonLignes.get(i).getMONTANT_NET();
            VALEUR_COMMANDEE += livraisonLignes.get(i).getMONTANT_NET();
            LITTRAGE_COMMANDEE += livraisonLignes.get(i).getLITTRAGE_COMMANDEE();
            TONNAGE_COMMANDEE += livraisonLignes.get(i).getTONNAGE_COMMANDEE();
            KG_COMMANDEE += livraisonLignes.get(i).getKG_COMMANDEE();
            livraisonLignes.get(i).setLIVRAISON_CODE(livraison.getLIVRAISON_CODE());
            livraisonLignes.get(i).setLIVRAISONLIGNE_CODE(livraisonlignes_size+i+1);
        }

        if(!commandeSource.equals("Annuler")){

            livraison.setMONTANT_BRUT(MONTANT_BRUT);
            livraison.setREMISE(REMISE);
            livraison.setMONTANT_NET(MONTANT_NET);
        }

        livraison.setMONTANT_BRUT(MONTANT_BRUT);
        livraison.setREMISE(REMISE);
        livraison.setMONTANT_NET(MONTANT_NET);
        livraison.setVALEUR_COMMANDE(VALEUR_COMMANDEE);
        livraison.setLITTRAGE_COMMANDE(LITTRAGE_COMMANDEE);
        livraison.setTONNAGE_COMMANDE(TONNAGE_COMMANDEE);
        livraison.setKG_COMMANDE(KG_COMMANDEE);
        livraison.setVISITE_CODE(visite_code);

        Double tRemise=0.0;

        promo_AP=promo_Manager.GetPromoAP_LIV_QC(livraison,livraisonLignes,getApplicationContext());
        Log.d("Remise", "Panier:Remise Appliquée = "+promo_AP.size());
        Log.d("Remise", "Panier:Remise Appliquée = "+promo_AP.toString());

        //############################################################ REMISE LIGNE ######################################################################

        for (int i = 0; i < livraisonLignes.size(); i++) {
            Double remise_LLIV=0.0;
            if(promo_AP.size()>0){
                for (LivraisonPromotion unPromoAP:promo_AP){
                    if(unPromoAP.getPROMO_MODECALCUL().equals("CA0015")&&unPromoAP.getPROMO_NIVEAU().equals("CA0018")&&unPromoAP.getLIVRAISON_CODE().equals(livraison.getLIVRAISON_CODE())&&unPromoAP.getLIVRAISONLIGNE_CODE()==(livraisonLignes.get(i).getLIVRAISONLIGNE_CODE())){
                        remise_LLIV+=unPromoAP.getVALEUR_PROMO();
                        tab_PromosAP.add(unPromoAP);
                    }
                }
            }
            tRemise+=remise_LLIV;
            livraisonLignes.get(i).setREMISE(tRemise);
            livraisonLignes.get(i).setMONTANT_NET(livraisonLignes.get(i).getMONTANT_BRUT()-tRemise);
        }

        //################################################################################################################################################

        //################################################################ LIVRAISON PROMOTION #############################################################

        //#################################################################################################################################################

        if(promo_AP.size()>0){
            for (LivraisonPromotion unPromoAP:promo_AP){
                if(unPromoAP.getPROMO_MODECALCUL().equals("CA0016")&&unPromoAP.getPROMO_NIVEAU().equals("CA0017")&&unPromoAP.getLIVRAISON_CODE().equals(livraison.getLIVRAISON_CODE())){
                    tab_PromosAP.add(unPromoAP);
                }
            }
        }

        //##################################################################### REMISE LIVRAISON ##########################################################
        Double remise_LIV=0.0;
        if(promo_AP.size()>0){
            for (LivraisonPromotion unPromoAP:promo_AP){
                if(unPromoAP.getPROMO_MODECALCUL().equals("CA0015")&&unPromoAP.getPROMO_NIVEAU().equals("CA0017")&&unPromoAP.getLIVRAISON_CODE().equals(livraison.getLIVRAISON_CODE())){
                    remise_LIV+=unPromoAP.getVALEUR_PROMO();
                    tab_PromosAP.add(unPromoAP);
                }
            }
        }
        /////////////////////////////////////////PARTIE REMISE A REVOIR////////////////////////////////////////////
        tRemise+=remise_LIV;

        if(!commandeSource.equals("Annuler")){
            livraison.setREMISE(tRemise);
            livraison.setMONTANT_NET(livraison.getMONTANT_BRUT()-tRemise);
        }else{
            //livraison.setREMISE(-tRemise);
            livraison.setMONTANT_NET(livraison.getMONTANT_BRUT()+livraison.getREMISE());
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////

        Log.d("remise", "onClick: "+remise_LIV);
        Log.d("tremise", "onClick: "+tRemise);
        Log.d("cremise", "onClick: "+livraison.getMONTANT_NET());

        //################################################################################################################################################

        tab_LIVG = livraisonGratuiteManager.getLivraisonGratuiteByCmdPromotion(promo_AP,livraison,getApplicationContext());

        if(tab_LIVG!=null){
            for(int i=0;i<tab_LIVG.size();i++){

                TableRow ligne1 = new TableRow(this);

                TextView art_code = new TextView(this);
                TextView qte = new TextView(this);


                art_code.setEllipsize(TextUtils.TruncateAt.END);
                qte.setGravity(Gravity.CENTER);

                art_code.setTextSize(15);
                qte.setTextSize(15);


                art_code.setText(tab_LIVG.get(i).getARTICLE_CODE());
                qte.setText(String.valueOf(tab_LIVG.get(i).getQUANTITE()));


                ligne1.addView(art_code);
                ligne1.addView(qte);

                tableVente2.addView(ligne1);
            }
        }

        panier_nbr_remise.setText(String.valueOf(new DecimalFormat("##.##").format(livraison.getREMISE())));
        panier_nbr_net.setText(String.valueOf(new DecimalFormat("##.##").format(livraison.getMONTANT_NET())));

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                Intent i = new Intent();
                if(commandeSource.equals("Annuler")){
                     i = new Intent(view.getContext(), AnnulerLivraisonActivity.class);
                }else{
                     i = new Intent(view.getContext(), CommandeActivity.class);
                }

                ListeCommandeLigne.clear();
                commande=null;
                livraisonLignes.clear();
                livraison=null;
                commandeSource = null;

                startActivity(i);
                finish();


            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                valider.setClickable(false);
                ArrayList<String> articles = new ArrayList<>();
                boolean stock_suffisant = true;

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date_visite=df.format(Calendar.getInstance().getTime());

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                Gson gson2 = new Gson();
                String json2 = pref.getString("User", "");
                Type type = new TypeToken<JSONObject>() {}.getType();
                final JSONObject user = gson2.fromJson(json2, type);



                if(!commandeSource.equals("Annuler")){

                    for(int i= 0; i<livraisonLignes.size();i++){

                        if(!articles.contains(livraisonLignes.get(i).getARTICLE_CODE())){
                            articles.add(livraisonLignes.get(i).getARTICLE_CODE());
                        }
                    }

                    try {
                        if(stockManager.checkGerable(user.getString("UTILISATEUR_CODE"),getApplicationContext())){
                            for(int i=0;i<articles.size();i++){

                                if(stockLigneManager.checkLivraisonStockLigneQteVersion(livraisonLignes,articles.get(i),getApplicationContext())== false){
                                    //Toast.makeText(getApplicationContext(),"Stock Insuffisant" , Toast.LENGTH_LONG).show();
                                    stock_suffisant = false;
                                    break;
                                }
                            }
                        }else{
                            Log.d(TAG, "onClick: stock ingérable");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }






                if(stock_suffisant == false){
                    showMessage("STOCK INSUFFISANT");
                }else{
                    Log.d(TAG, "onClick: stock suffisant");
                    if((livraison!=null && (commandeSource.equals("Livraison") || commandeSource.equals("Annuler"))  && livraisonLignes.isEmpty()==false) ){

                        if(tab_PromosAP.size()>0){

                            for (LivraisonPromotion unPromoAP:tab_PromosAP){

                                unPromoAP.setVERSION("to_insert");
                                livraisonPromotionManager.add(unPromoAP);
                            }
                        }

                        if(tab_LIVG.size()>0){

                            for (LivraisonGratuite unLIVG:tab_LIVG){
                                livraisonGratuiteManager.add(unLIVG);
                                Log.d("LIVRAISON GRATUITE ", "GRATUITE: "+unLIVG);
                            }

                        }
                        //################################################################# FIN REMISE ###################################################################

                        addCmdLivraison(livraison,livraisonLignes);

                        final Dialog dialog = new Dialog(view.getContext());
                        dialog.setContentView(R.layout.alert_encaissement);
                        dialog.setTitle("ENCAISSEMENT");
                        dialog.setCanceledOnTouchOutside(false);
                        Button oui = (Button) dialog.findViewById(R.id.btn_oui);
                        Button non = (Button) dialog.findViewById(R.id.btn_non);
                        final TextView alertencaissement = (TextView) dialog.findViewById(R.id.alertencaissement);

                        final Dialog dialog1 = new Dialog(view.getContext());
                        dialog1.setContentView(R.layout.alert_imprimante);
                        dialog1.setTitle("Impression");
                        dialog1.setCanceledOnTouchOutside(false);
                        Button print = (Button) dialog1.findViewById(R.id.btn_print);
                        Button done = (Button) dialog1.findViewById(R.id.done_print);
                        final TextView nbr_copies = (TextView) dialog1.findViewById(R.id.nbr_copies);

                        print.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                boolean printed=false;
                                String impression_text=impressionManager.ImprimerLivraison(livraison,getApplicationContext());
                                for(int i=0;i<Integer.valueOf(nbr_copies.getText().toString());i++){

                                    printed=BlutoothConnctionService.imprimanteManager.printText(impression_text);
                                    ImprimanteManager.lastPrint=impression_text;
                                    Log.d("print", "onClick: "+impression_text.toString());

                                    if(printed==true){
                                        Log.d("printed", "onClick: "+"imprimeée");
                                        try {
                                            Impression impression = new Impression(getApplicationContext(),livraison.getLIVRAISON_CODE(),impression_text,"NORMAL",1,"LIVRAISON");
                                            impressionManager.add(impression);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }else{
                                        Log.d("printed", "onClick: "+"non imprimee");
                                        try {
                                            Impression impression = new Impression(getApplicationContext(),livraison.getLIVRAISON_CODE(),impression_text,"STOCKEE",0,"LIVRAISON");
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


                                try {
                                    if(stockManager.checkGerable(user.getString("UTILISATEUR_CODE"),getApplicationContext())) {
                                        if (commandeSource.equals("Annuler") || commandeSource.equals("Livraison")) {

                                            for (int i = 0; i < ViewLivraisonActivity.livraisonLignes.size(); i++) {

                                                try {
                                                    StockTransfert stockTransfert = new StockTransfert(livraison, ViewLivraisonActivity.livraisonLignes.get(i), getApplicationContext());

                                                    //Log.d("stockTransfert", "onClick: "+ i +stockTransfert.toString());
                                                    stockTransfertManager.add(stockTransfert);

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }


                                            }
                                        }

                                        stockManager.updateStock(getApplicationContext());

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if(commandeSource.equals("Annuler")){
                                    visiteManager.updateVisite_VRDF(ClientActivity.visiteCourante.getVISITE_CODE(),-1,date_visite);

                                }else{
                                    visiteManager.updateVisite_VRDF(ClientActivity.visiteCourante.getVISITE_CODE(),1,date_visite);

                                }



                                if(isNetworkAvailable()){
                                    Toast.makeText(getApplicationContext(),"Synchronisation en cours",Toast.LENGTH_SHORT).show();

                                    LivraisonManager.synchronisationLivraison(getApplicationContext());
                                    LivraisonLigneManager.synchronisationLivraisonLigne(getApplicationContext());
                                    LivraisonGratuiteManager.synchronisationLivraisonGratuite(getApplicationContext());
                                    LivraisonPromotionManager.synchronisationLivraisonPromotion(getApplicationContext());

                                }

                                livraisonLignes.clear();
                                commande=null;
                                ListeCommandeLigne.clear();
                                livraison=null;

                                dialog1.dismiss();
                                Intent i = new Intent(view.getContext(), ClientActivity.class);

                                if(commandeSource.equals("Annuler")){
                                    i.putExtra("VISITERESULTAT_CODE",-1);
                                }else{
                                    i.putExtra("VISITERESULTAT_CODE",1);
                                }
                                startActivity(i);
                                finish();
                            }

                        });

                        oui.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                //Toast.makeText(getApplicationContext(),"ENCAISSEMENT HONEY",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(view.getContext(), EncaissementActivity.class);

                                EncaissementActivity.commande = null;
                                EncaissementActivity.commande = commande;

                                EncaissementActivity.commande_source = null;
                                EncaissementActivity.commande_source = "Livraison";

                                EncaissementActivity.livraison = null;
                                EncaissementActivity.encaissements = null;
                                EncaissementActivity.encaissements = new ArrayList<Encaissement>();
                                EncaissementActivity.encaissementslocaux = null;
                                EncaissementActivity.encaissementslocaux = new ArrayList<Encaissement>();
                                //EncaissementActivity.livraisonLignes.clear();

                                EncaissementActivity.livraison = livraison;
                                EncaissementActivity.livraisonLignes = livraisonLignes;

                                startActivity(i);
                                finish();
                            }

                        });

                        non.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                dialog1.show();
                            }

                        });

                        if(commandeSource.equals("Annuler")){
                            dialog1.show();
                        }else{
                            dialog.show();
                        }

                    }else if(livraison!=null && livraisonLignes.isEmpty()==true){
                        Toast.makeText(getApplicationContext(),"Finaliser votre livraison ou appuyer sur annuler pour terminer l'opération" , Toast.LENGTH_LONG).show();
                    }
                }


            }
        });

        setTitle("PANIER LIVRAISON");

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

                if(livraison==null){
                    new AlertDialog.Builder(ViewLivraisonActivity.this)
                            .setTitle("ALERTE COMMANDE")
                            .setMessage("l'OBJET COMMANDE EST NULL")
                            .setPositiveButton("ANNULLER", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            })
                            .show();

                }else{

                    if(!commandeSource.equals("Annuler")){
                        Intent intent = new Intent(this, NewLivraisonPanierActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    }

                }


        }
        return super.onOptionsItemSelected(item);
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Finaliser votre commande ou appuyer sur annuler pour terminer l'opération" , Toast.LENGTH_LONG).show();
    }

    public void addCmdLivraison(Livraison livraison, ArrayList<LivraisonLigne> livraisonLignes){

        Log.d("addCommandeLivraison", "addCmdLivraison: "+ListeCommandeLigne.size());
        Log.d("addCommandeLivraison", "addCmdLivraison: "+ListeCommandeLigne);

        SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");

        LivraisonManager livraisonManager = new LivraisonManager(getApplicationContext());
        LivraisonLigneManager livraisonLigneManager = new LivraisonLigneManager(getApplicationContext());

        for(int i=0;i<livraisonLignes.size();i++){
            livraisonLigneManager.add(livraisonLignes.get(i));
        }
        livraison.setNB_LIGNE(livraisonLignes.size());
        livraisonManager.add(livraison);
    }

    private void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
