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
import com.newtech.newtech_sfm.AnnulationCommande.AnnulerCommandeActivity;
import com.newtech.newtech_sfm.Configuration.ImprimanteManager;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.CommandeGratuite;
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier.CommandePromotion;
import com.newtech.newtech_sfm.Metier.Encaissement;
import com.newtech.newtech_sfm.Metier.Impression;
import com.newtech.newtech_sfm.Metier.StockTransfert;
import com.newtech.newtech_sfm.Metier.Unite;
import com.newtech.newtech_sfm.Metier.User;
import com.newtech.newtech_sfm.Metier_Manager.CommandeGratuiteManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandePromotionManager;
import com.newtech.newtech_sfm.Metier_Manager.ImpressionManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonManager;
import com.newtech.newtech_sfm.Metier_Manager.PromotionManager;
import com.newtech.newtech_sfm.Metier_Manager.PromotiongratuiteManager;
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

public class ViewCommandeActivity extends AppCompatActivity{

    private static final String TAG = ViewCommandeActivity.class.getName();
    public static String commandeSource;
    public static String visite_code;
    public static Commande commande= new Commande();
    public static  ArrayList<CommandeLigne> ListeCommandeLigne = new ArrayList<CommandeLigne>();

    public ArrayList<CommandePromotion> tab_PromosAP = new ArrayList<CommandePromotion>();
    public ArrayList<CommandePromotion>promo_AP=new ArrayList<CommandePromotion>();
    public ArrayList<CommandeGratuite> tab_CMDG = new ArrayList<CommandeGratuite>();

    Client clientCourant = ClientActivity.clientCourant;
    public static String activite_source;
    public static ImpressionManager impressionManager;
    CommandeManager commandeManager;
    CommandeLigneManager commandeLigneManager;
    UniteManager uniteManager;

    CommandePromotionManager commandePromotionManager;
    CommandeGratuiteManager commandeGratuiteManager;
    PromotionManager promo_Manager;
    PromotiongratuiteManager promo_Gratuite_Manager;
    StockManager stockManager;
    StockTransfertManager stockTransfertManager;
    UserManager userManager ;
    User utilisateur;
    VisiteManager visiteManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        userManager = new UserManager(getApplicationContext());
        impressionManager = new ImpressionManager(getApplicationContext());
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


        utilisateur = userManager.get();

        if(commande!=null){
            Log.d("ViewCommande Activity", "onCreate: "+commande.toString());
            Log.d("ViewCommande Activity", "onCreate: "+ListeCommandeLigne.toString());
        }else{
            Log.d("ViewCommande Activity", "onCreate: commande null");
            Log.d("ViewCommande Activity", "onCreate: "+ListeCommandeLigne.toString());
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_panier);


        TableLayout tableVente= (TableLayout)findViewById(R.id.tableLayout1);
        TableLayout tableVente2= (TableLayout)findViewById(R.id.tableLayout2);

        final Button valider =(Button)findViewById(R.id.btn_valider1);
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

        String VENDEUR_CODE="";

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref", 0);
        if( pref1.getString("is_login", null).equals("ok")) {
            try{
                Gson gson2 = new Gson();
                String json2 = pref1.getString("User", "");
                Type type = new TypeToken<JSONObject>() {}.getType();
                final JSONObject user = gson2.fromJson(json2, type);
                VENDEUR_CODE =user.getString("UTILISATEUR_CODE");}
            catch (Exception e){
            }
        }
        String COMMANDE_CODE = clientCourant.getDISTRIBUTEUR_CODE()+VENDEUR_CODE+date_commande;

        String commande_typecode = "";
        String commande_statutcode= "";

        if(commandeSource.equals("Avoir") || commandeSource.equals("Annuler")){
            commande_typecode = "2";
            commande_statutcode= "3";

        }else if(commandeSource.equals("Facturation")){
            commande_typecode = "1";
            commande_statutcode= "3";

        }else if(commandeSource.equals("Commande")){
            commande_typecode = "1";
            commande_statutcode= "1";
        }


        if(commande==null){
            commande= new Commande(COMMANDE_CODE,visite_code,getApplicationContext(),commande_typecode,commande_statutcode, ClientActivity.clientCourant,ClientActivity.gps_latitude,ClientActivity.gps_longitude);
        }

        Log.d(TAG, "onCreate: "+commande.toString());

        if(ListeCommandeLigne!=null){

            ListeCommandeLigne = commandeLigneManager.fixCmdLigneCode(ListeCommandeLigne);


            for(int i=0;i<ListeCommandeLigne.size();i++){

                TableRow ligne1 = new TableRow(this);

                TextView ligne_code = new TextView(this);
                TextView art_code = new TextView(this);
                TextView unite = new TextView(this);
                TextView qte = new TextView(this);
                TextView remise1= new TextView(this);
                TextView total = new TextView(this);

                Unite unite1 = uniteManager.get(ListeCommandeLigne.get(i).getUNITE_CODE());

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


                ligne_code.setText(String.valueOf(ListeCommandeLigne.get(i).getCOMMANDELIGNE_CODE()));
                art_code.setText(ListeCommandeLigne.get(i).getARTICLE_CODE());
                unite.setText(unite1.getUNITE_NOM());
                qte.setText(String.valueOf(ListeCommandeLigne.get(i).getQTE_COMMANDEE()));
                remise1.setText("0");
                //total.setText(String.valueOf(new DecimalFormat("##.##").format(ListeCommandeLigne.get(i).getARTICLE_PRIX()*ListeCommandeLigne.get(i).getQTE_COMMANDEE())));
                total.setText(String.format("%.1f",ListeCommandeLigne.get(i).getMONTANT_BRUT()));

                ligne1.addView(ligne_code);
                ligne1.addView(art_code);
                ligne1.addView(unite);
                ligne1.addView(qte);
                ligne1.addView(remise1);
                ligne1.addView(total);

                tableVente.addView(ligne1);

                //panierTotal+=ListeCommandeLigne.get(i).getARTICLE_PRIX()*ListeCommandeLigne.get(i).getQTE_COMMANDEE();
            }
        }

        panierTotal=commandeManager.getMontantNet(ListeCommandeLigne);

        panier_nbr_total.setText(String.valueOf(new DecimalFormat("##.##").format(panierTotal)));

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



        promo_AP=promo_Manager.GetPromoAP_CMD_QC(commande,ListeCommandeLigne,getApplicationContext());

        //############################################################ REMISE LIGNE ######################################################################
        Double tRemise=0.0;
        for (int i = 0; i < ListeCommandeLigne.size(); i++) {
            Double remise_LCMD=0.0;
            if(promo_AP.size()>0){
                for (CommandePromotion unPromoAP:promo_AP){
                    if(unPromoAP.getPROMO_MODECALCUL().equals("CA0015")&&unPromoAP.getPROMO_NIVEAU().equals("CA0018")&&unPromoAP.getCOMMANDE_CODE().equals(commande.getCOMMANDE_CODE())&&unPromoAP.getCOMMANDELIGNE_CODE()==(ListeCommandeLigne.get(i).getCOMMANDELIGNE_CODE())){
                        remise_LCMD+=unPromoAP.getVALEUR_PROMO();
                        tab_PromosAP.add(unPromoAP);
                    }
                }
            }
            tRemise+=remise_LCMD;
            ListeCommandeLigne.get(i).setREMISE(tRemise);
            ListeCommandeLigne.get(i).setMONTANT_NET(ListeCommandeLigne.get(i).getMONTANT_BRUT()-tRemise);
        }

        //################################################################################################################################################

        //################################################################ COMMANDE PROMOTION #############################################################

        //#################################################################################################################################################

        if(promo_AP.size()>0){
            for (CommandePromotion unPromoAP:promo_AP){
                if(unPromoAP.getPROMO_MODECALCUL().equals("CA0016")&&unPromoAP.getPROMO_NIVEAU().equals("CA0017")&&unPromoAP.getCOMMANDE_CODE().equals(commande.getCOMMANDE_CODE())){
                    tab_PromosAP.add(unPromoAP);
                }
            }
        }

        //##################################################################### REMISE COMMANDE ##########################################################
        Double remise_CMD=0.0;
        if(promo_AP.size()>0){
            for (CommandePromotion unPromoAP:promo_AP){
                if(unPromoAP.getPROMO_MODECALCUL().equals("CA0015")&&unPromoAP.getPROMO_NIVEAU().equals("CA0017")&&unPromoAP.getCOMMANDE_CODE().equals(commande.getCOMMANDE_CODE())){
                    remise_CMD+=unPromoAP.getVALEUR_PROMO();
                    tab_PromosAP.add(unPromoAP);
                }
            }
        }

        tRemise+=remise_CMD;
        //commande.setREMISE(tRemise);
        //commande.setMONTANT_NET(commande.getMONTANT_NET()-tRemise);

        if(!commandeSource.equals("Annuler")){

            if(commande.getCOMMANDETYPE_CODE().equals("2")){
                tRemise = -tRemise;
                commande.setREMISE(tRemise);
                commande.setMONTANT_NET(commande.getMONTANT_NET()-tRemise);
            }else{
                commande.setREMISE(tRemise);
                commande.setMONTANT_NET(commande.getMONTANT_NET()-tRemise);
            }
        }



        //commande = commandeManager.calcDroitTimbre(commande);

        //Log.d("remise", "onClick: "+remise_CMD);
        //Log.d("tremise", "onClick: "+tRemise);
        //Log.d("cremise", "onClick: "+commande.getMONTANT_NET());

        //################################################################################################################################################


        //##################################################################### REMISE GRATUITE ##########################################################

        tab_CMDG = commandeGratuiteManager.getCmdGratuiteByCmdPromotion(promo_AP,commande,getApplicationContext());

        if(tab_CMDG!=null){

            //ListeCommandeLigne = commandeLigneManager.fixCmdLigneCode(ListeCommandeLigne);


            for(int i=0;i<tab_CMDG.size();i++){

                TableRow ligne1 = new TableRow(this);

                TextView art_code = new TextView(this);
                TextView qte = new TextView(this);


                art_code.setEllipsize(TextUtils.TruncateAt.END);
                qte.setEllipsize(TextUtils.TruncateAt.END);

                art_code.setGravity(Gravity.CENTER);
                qte.setGravity(Gravity.CENTER);

                art_code.setTextSize(15);
                qte.setTextSize(15);

                art_code.setBackgroundResource(R.drawable.cell_shape);
                qte.setBackgroundResource(R.drawable.cell_shape);

                art_code.setText(tab_CMDG.get(i).getARTICLE_CODE());
                qte.setText(String.valueOf(tab_CMDG.get(i).getQUANTITE()));


                ligne1.addView(art_code);
                ligne1.addView(qte);

                tableVente2.addView(ligne1);

                //panierTotal+=ListeCommandeLigne.get(i).getARTICLE_PRIX()*ListeCommandeLigne.get(i).getQTE_COMMANDEE();
            }
        }

        panier_nbr_remise.setText(String.valueOf(new DecimalFormat("##.##").format(commande.getREMISE())));
        panier_nbr_net.setText(String.valueOf(new DecimalFormat("##.##").format(commande.getMONTANT_NET())));

        //################################################################# FIN REMISE ###################################################################


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ListeCommandeLigne.clear();
                commande=null;

                if(!commandeSource.equals("Annuler")){
                    Intent i = new Intent(view.getContext(), ClientActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(view.getContext(), AnnulerCommandeActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                //Log.d("commande lieu livraison", "onClick: "+commande.toString());
                valider.setClickable(false);

                if((commande!=null && commandeSource.equals("Commande") && ListeCommandeLigne.isEmpty()==false) || (commande!=null && (commandeSource.equals("Avoir") || commandeSource.equals("Annuler")) && ListeCommandeLigne.isEmpty()==false) || (commande!=null && commandeSource.equals("Facturation") && ListeCommandeLigne.isEmpty()==false)){


                    addCmdLivraison(commande, ListeCommandeLigne);


                    //Log.d("COMMANDE TEST", "onClick: "+commande.toString());
                    //Log.d("COMMANDELIGNE TEST", "onClick: "+ListeCommandeLigne.toString());

                    //Log.d("promo code before", "onClick: "+tab_PromosAP.toString());


                    if(tab_PromosAP.size()>0){
                        for (CommandePromotion unPromoAP:tab_PromosAP){
                            commandePromotionManager.add(unPromoAP);
                        }
                    }

                    if(tab_CMDG.size()>0){

                        for (CommandeGratuite unCMDG:tab_CMDG){
                            commandeGratuiteManager.add(unCMDG);
                            //Log.d("COMMANDE GRATUITE ", "GRATUITE: "+unCMDG);
                        }

                    }
                    //Log.d("promotioncommande after", "onClick: "+tab_PromosAP.toString());

                    final Dialog dialog = new Dialog(view.getContext());
                    dialog.setContentView(R.layout.alert_encaissement);
                    dialog.setTitle("ENCAISSEMENT");
                    dialog.setCanceledOnTouchOutside(false);
                    Button oui = (Button) dialog.findViewById(R.id.btn_oui);
                    Button non = (Button) dialog.findViewById(R.id.btn_non);
                    final TextView alertencaissement = (TextView) dialog.findViewById(R.id.alertencaissement);

                    final Dialog dialog1 = new Dialog(view.getContext());
                    dialog1.setContentView(R.layout.alert_imprimante);
                    dialog1.setTitle("IMPRESSION");
                    dialog1.setCanceledOnTouchOutside(false);
                    Button print = (Button) dialog1.findViewById(R.id.btn_print);
                    Button done = (Button) dialog1.findViewById(R.id.done_print);
                    final TextView nbr_copies = (TextView) dialog1.findViewById(R.id.nbr_copies);

                    print.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Log.d("CommandeLignevalider", "onClick: "+commande.getCOMMANDE_CODE());
                        boolean printed=false;
                        String impression_text=impressionManager.ImprimerCommande(commande,getApplicationContext());
                        for(int i=0;i<Integer.valueOf(nbr_copies.getText().toString());i++){

                            printed=BlutoothConnctionService.imprimanteManager.printText(impression_text);
                            ImprimanteManager.lastPrint=impression_text;
                            //Log.d("print", "onClick: "+impression_text.toString());

                            if(printed==true){
                                //Log.d("printed", "onClick: "+"imprimeée");
                                try {
                                    Impression impression = new Impression(getApplicationContext(),commande.getCOMMANDE_CODE(),impression_text,"NORMAL",1,"COMMANDE");
                                    impressionManager.add(impression);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }else{
                                //Log.d("printed", "onClick: "+"non imprimee");
                                try {
                                    Impression impression = new Impression(getApplicationContext(),commande.getCOMMANDE_CODE(),impression_text,"STOCKEE",0,"COMMANDE");
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

                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String date_visite=df.format(Calendar.getInstance().getTime());

                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                            Gson gson2 = new Gson();
                            String json2 = pref.getString("User", "");
                            Type type = new TypeToken<JSONObject>() {}.getType();
                            final JSONObject user = gson2.fromJson(json2, type);

                            try {
                                if(stockManager.checkGerable(user.getString("UTILISATEUR_CODE"),getApplicationContext())) {
                                    if (commandeSource.equals("Avoir") || commandeSource.equals("Annuler")) {

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

                                    stockManager.updateStock(getApplicationContext());

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            visiteManager.updateVisite_VRDF(ClientActivity.visiteCourante.getVISITE_CODE(),1,date_visite);



                            if(isNetworkAvailable()){
                                Toast.makeText(getApplicationContext(),"Synchronisation en cours",Toast.LENGTH_SHORT).show();

                                CommandeManager.synchronisationCommande(getApplicationContext());
                                CommandeLigneManager.synchronisationCommandeLigne(getApplicationContext());
                                VisiteManager.synchronisationVisite(getApplicationContext());
                                //Log.d("commandesource avant", "onClick: "+commandeSource);

                                if(commandeSource.equals("Facturation")){
                                    //Log.d("commandesource apres", "onClick: "+commandeSource);
                                    LivraisonManager.synchronisationLivraison(getApplicationContext());
                                    LivraisonLigneManager.synchronisationLivraisonLigne(getApplicationContext());
                                }


                                CommandePromotionManager.synchronisationCommandePromotion(getApplicationContext());
                                CommandeGratuiteManager.synchronisationCommandeGratuite(getApplicationContext());

                            }

                            ListeCommandeLigne.clear();
                            commande=null;

                            dialog1.dismiss();
                            Intent i = new Intent(view.getContext(), ClientActivity.class);
                            if(commandeSource.equals("Annuler")){
                                i.putExtra("VISITERESULTAT_CODE",-1);
                            }else{
                                i.putExtra("VISITERESULTAT_CODE",1);
                            }
                            commandeSource=null;
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
                            EncaissementActivity.encaissements = null;
                            EncaissementActivity.encaissements = new ArrayList<Encaissement>();
                            EncaissementActivity.encaissementslocaux = null;
                            EncaissementActivity.encaissementslocaux = new ArrayList<Encaissement>();
                            EncaissementActivity.commande_source = "Facturation";

                            startActivity(i);
                            finish();
                        }

                    });

                    non.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //dialog.dismiss();
                            //dialog1.show();

                            if(commandeSource.equals("Facturation")){
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String date_visite=df.format(Calendar.getInstance().getTime());

                                visiteManager.updateVisite_VRDF(ClientActivity.visiteCourante.getVISITE_CODE(),0,date_visite);

                                commandeManager.delete(commande.getCOMMANDE_CODE());
                                commandeLigneManager.deleteByCmdCode(commande.getCOMMANDE_CODE());
                                commandeGratuiteManager.deleteByCmdCode(commande.getCOMMANDE_CODE());
                                commandePromotionManager.deleteByCmdCode(commande.getCOMMANDE_CODE());
                                stockTransfertManager.deleteByCmdCode(commande.getCOMMANDE_CODE());

                                dialog.dismiss();

                                ListeCommandeLigne.clear();
                                commande=null;
                                commandeSource=null;

                                Intent i = new Intent(view.getContext(), ClientActivity.class);
                                //i.putExtra("VISITERESULTAT_CODE",1);
                                startActivity(i);
                                finish();

                            }else{

                                dialog.dismiss();
                                dialog1.show();
                            }

                        }

                    });

                    if(commandeSource.equals("Avoir") || commandeSource.equals("Annuler") || commandeSource.equals("Commande")){
                        dialog1.show();
                    }else{
                        dialog.show();
                    }
                    //dialog1.show();
                    //dialog.show();

                }else if(commande!=null && ListeCommandeLigne.isEmpty()==true){
                    Toast.makeText(getApplicationContext(),"Finaliser votre commande ou appuyer sur annuler pour terminer l'opération" , Toast.LENGTH_LONG).show();
                }


            }
        });

        setTitle("PANIER COMMANDES");

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

                if(commande==null){
                    new AlertDialog.Builder(ViewCommandeActivity.this)
                            .setTitle("ALERTE COMMANDE")
                            .setMessage("l'OBJET COMMANDE EST NULL")
                            .setPositiveButton("ANNULLER", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            })
                            .show();

                }else{

                    if(!commandeSource.equals("Annuler")){
                        Intent intent = new Intent(this, NewPanierActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    }

                }


        }
        return super.onOptionsItemSelected(item);
    }

    public void updateCommande(Commande commande, ArrayList<CommandeLigne> commandeLignes){

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

        commande.setMONTANT_BRUT(MONTANT_BRUT);
        commande.setMONTANT_NET(MONTANT_NET);
        commande.setLITTRAGE_COMMANDE(LITTRAGE_COMMANDE);
        commande.setTONNAGE_COMMANDE(TONNAGE_COMMANDE);
        commande.setKG_COMMANDE(KG_COMMANDE);
        commande.setVALEUR_COMMANDE(VALEUR_COMMANDE);

        //Log.d("newpanierapres", "updateCommande: "+commande.toString());
        ViewCommandeActivity.commande = commande;

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

    public void addCmdLivraison(Commande commande, ArrayList<CommandeLigne> ListeCommandeLigne){

        Log.d("addCommandeLivraison", "addCmdLivraison: "+ListeCommandeLigne.size());
        Log.d("addCommandeLivraison", "addCmdLivraison: "+ListeCommandeLigne);

        SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");

        CommandeManager commandeManager = new CommandeManager(getApplicationContext());
        CommandeLigneManager commandeLigneManager = new CommandeLigneManager(getApplicationContext());

        for(int i=0;i<ListeCommandeLigne.size();i++){
            commandeLigneManager.add(ListeCommandeLigne.get(i));
        }
        commande.setNB_LIGNE(ListeCommandeLigne.size());
        commandeManager.add(commande);
    }
}
