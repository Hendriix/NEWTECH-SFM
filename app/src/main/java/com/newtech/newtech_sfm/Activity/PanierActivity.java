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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Configuration.ImprimanteManager;
import com.newtech.newtech_sfm.Configuration.RVAdapter;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.CommandeGratuite;
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier.CommandePromotion;
import com.newtech.newtech_sfm.Metier.Promotiongratuite;
import com.newtech.newtech_sfm.Metier_Manager.CommandeGratuiteManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandePromotionManager;
import com.newtech.newtech_sfm.Metier_Manager.PromotionManager;
import com.newtech.newtech_sfm.Metier_Manager.PromotiongratuiteManager;
import com.newtech.newtech_sfm.R;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by stagiaireit2 on 28/07/2016.
 */
public class PanierActivity extends AppCompatActivity {

    private String visite_Code;
    private String tournee_Code;
    Client clientCourant = ClientActivity.clientCourant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panier);


        Intent intentClient=getIntent();

        final String visite_code=intentClient.getStringExtra("VISITE_CODE");


        visite_Code=visite_code;



        TableLayout tableVente= (TableLayout)findViewById(R.id.tableLayout);
        Button  valider =(Button)findViewById(R.id.btn_valider);
        Button  annuler =(Button)findViewById(R.id.btn_annuler);
        TextView panier_nbr_total =(TextView) findViewById(R.id.nbr_panier_total);
        double panierTotal=0;
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Panier", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("MonPanier", "");
        Type type = new TypeToken<ArrayList<CommandeLigne>>(){}.getType();
        final ArrayList<CommandeLigne> list = gson2.fromJson(json2, type);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);

        //afficher le bouton retour
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(list!=null){
            for(int i=0;i<list.size();i++){
                TableRow ligne1 = new TableRow(this);
                TextView art_code = new TextView(this);
                TextView unite = new TextView(this);
                TextView qte = new TextView(this);
                TextView total = new TextView(this);

                art_code.setTextSize(20);
                unite.setTextSize(15);
                qte.setTextSize(15);
                total.setTextSize(20);

                art_code.setText(list.get(i).getARTICLE_CODE());
                unite.setText(list.get(i).getUNITE_CODE());
                qte.setText(String.valueOf(list.get(i).getQTE_COMMANDEE()));
                total.setText(String.valueOf(new DecimalFormat("##.###").format(list.get(i).getARTICLE_PRIX()*list.get(i).getQTE_COMMANDEE())));

                ligne1.addView(art_code);
                ligne1.addView(unite);
                ligne1.addView(qte);
                ligne1.addView(total);

                tableVente.addView(ligne1);

                panierTotal+=list.get(i).getARTICLE_PRIX()*list.get(i).getQTE_COMMANDEE();
            }
        }

        panier_nbr_total.setText(String.valueOf(new DecimalFormat("##.###").format(panierTotal)));

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
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

                CommandeLigneManager commandeLigneManager = new CommandeLigneManager(getApplicationContext());
                if(list!=null) {
                    for (int i = 0; i < list.size(); i++) {

                        list.get(i).setCOMMANDE_CODE(COMMANDE_CODE);
                        list.get(i).setFACTURE_CODE(COMMANDE_CODE);

                    }
                    String commande_typecode="1";
                    final Commande commande= new Commande(COMMANDE_CODE,ClientActivity.visiteCourante.getVISITE_CODE(),getApplicationContext(),list,commande_typecode);
                    CommandeManager commandeManager = new CommandeManager(getApplicationContext());
                    CommandePromotionManager commandePromotionManager = new CommandePromotionManager(getApplicationContext());
                    CommandeGratuiteManager commandeGratuiteManager = new CommandeGratuiteManager(getApplicationContext());



                    //###########################################"  Debut  Remise


                    PromotionManager promo_Manager = new PromotionManager(getApplicationContext());
                    PromotiongratuiteManager promo_Gratuite_Manager = new PromotiongratuiteManager(getApplicationContext());

                    ArrayList<CommandePromotion> tab_PromosAP = new ArrayList<CommandePromotion>();
                    ArrayList<CommandePromotion>promo_AP=new ArrayList<CommandePromotion>();
                    Double tRemise=0.0;

                    promo_AP=promo_Manager.GetPromoAP_CMD_QC(commande,list,getApplicationContext());
                    Log.d("Remise", "Panier:Remise Appliquée = "+promo_AP.size());
                    //########################"  Remise Ligne
                    for (int i = 0; i < list.size(); i++) {
                        Double remise_LCMD=0.0;
                        if(promo_AP.size()>0){
                            for (CommandePromotion unPromoAP:promo_AP){
                                if(unPromoAP.getPROMO_MODECALCUL().equals("CA0015")&&unPromoAP.getPROMO_NIVEAU().equals("CA0018")&&unPromoAP.getCOMMANDE_CODE().equals(commande.getCOMMANDE_CODE())&&unPromoAP.getCOMMANDELIGNE_CODE()==(list.get(i).getCOMMANDELIGNE_CODE())){
                                    remise_LCMD+=unPromoAP.getVALEUR_PROMO();
                                    tab_PromosAP.add(unPromoAP);
                                }
                            }
                        }
                        tRemise+=remise_LCMD;
                        list.get(i).setREMISE(tRemise);
                        list.get(i).setMONTANT_NET(list.get(i).getMONTANT_NET()-tRemise);
                    }
                    //########################"  Remise commande
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
                    commande.setREMISE(tRemise);
                    commande.setMONTANT_NET(commande.getMONTANT_NET()-tRemise);

                    //########################"  Remise Gratuite
                    ArrayList<CommandeGratuite> tab_CMDG = new ArrayList<CommandeGratuite>();
                    if(promo_AP.size()>0){
                        for (CommandePromotion unPromoAP:promo_AP){
                            if(unPromoAP.getPROMO_MODECALCUL().equals("CA0016")&&unPromoAP.getCOMMANDE_CODE().equals(commande.getCOMMANDE_CODE())){
                                if(promo_Gratuite_Manager.exist(unPromoAP.getPROMO_CODE())){
                                    Promotiongratuite promogratuite = promo_Gratuite_Manager.get(unPromoAP.getPROMO_CODE());
                                    CommandeGratuite commandeGratuite=new CommandeGratuite();

                                    commandeGratuite.setGRATUITE_CODE(unPromoAP.getGRATUITE_CODE());
                                    commandeGratuite.setCOMMANDE_CODE(commande.getCOMMANDE_CODE());
                                    commandeGratuite.setPROMO_CODE(unPromoAP.getPROMO_CODE());
                                    commandeGratuite.setARTICLE_CODE(promogratuite.getVALEUR_GR());
                                    commandeGratuite.setQUANTITE(unPromoAP.getVALEUR_PROMO());

                                    tab_CMDG.add(commandeGratuite);
                                }
                            }
                        }
                    }

                    //############################################ Fin Remise   ##########################################



                    for (int i = 0; i < list.size(); i++) {
                        commandeLigneManager.add(list.get(i));
                    }

                    commandeManager.add(commande);
                    Toast.makeText(getApplicationContext(),"Commande enregistrée  ",Toast.LENGTH_SHORT).show();

                    // ###### insertion Promotion
                    if(tab_PromosAP.size()>0){
                        for (CommandePromotion unPromoAP:tab_PromosAP){
                            commandePromotionManager.add(unPromoAP);
                        }
                    }
                    if(tab_CMDG.size()>0){
                        for (CommandeGratuite unCMDG:tab_CMDG){
                           commandeGratuiteManager.add(unCMDG);
                        }
                    }
                    // ###### fin insertion Gratuitee

                    final Dialog dialog = new Dialog(view.getContext());
                    dialog.setContentView(R.layout.alert_imprimante);
                    dialog.setTitle("Impression");
                    Button print = (Button) dialog.findViewById(R.id.btn_print);
                    Button done = (Button) dialog.findViewById(R.id.done_print);
                    final TextView nbr_copies = (TextView) dialog.findViewById(R.id.nbr_copies);

                    print.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                         for(int i=0;i<Integer.valueOf(nbr_copies.getText().toString());i++)

                            MainActivity.imprimanteManager.printText(CommandeManager.ImprimerCommande(commande,list,view.getContext()));
                            ImprimanteManager.lastPrint=CommandeManager.ImprimerCommande(commande,list,view.getContext());
                        }
                    });


                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent i = new Intent(view.getContext(), ClientActivity.class);
                            //i.putExtra("TOURNEE_CODE",tournee_Code);
                            //i.putExtra("CLIENT_CODE",client_Code);
                            //i.putExtra("VISITE_CODE",visite_Code);
                            i.putExtra("VISITERESULTAT_CODE",1);
                            startActivity(i);
                            finish();
                        }

                    });
                    dialog.show();
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("Panier", 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear();
                    editor.commit();
                    RVAdapter.list_comande.clear();

                    SelectArticleActivity.listArticleParFamilleCode.clear();
                    if(isNetworkAvailable()){
                        Toast.makeText(getApplicationContext(),"Synchronisation en cours",Toast.LENGTH_SHORT).show();
                        CommandeManager.synchronisationCommande(getApplicationContext());
                        CommandeLigneManager.synchronisationCommandeLigne(getApplicationContext());
                        CommandePromotionManager.synchronisationCommandePromotion(getApplicationContext());
                        CommandeGratuiteManager.synchronisationCommandeGratuite(getApplicationContext());
                    }else{
                        Toast.makeText(PanierActivity.this,"Vérifier votre connexion internet puis synchroniser",Toast.LENGTH_LONG).show();

                    }
                }
            }
        });

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("Panier", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                RVAdapter.list_comande.clear();
                Toast.makeText(getApplicationContext(),"commande annulée",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(view.getContext(), ClientActivity.class);
                //i.putExtra("TOURNEE_CODE",tournee_code);
                //i.putExtra("CLIENT_CODE",client_code);
                startActivity(i);
                finish();
            }
        });


        setTitle("Panier");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_panier, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.logout:
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(this, AuthActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"finaliser votre commande",Toast.LENGTH_SHORT).show();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
