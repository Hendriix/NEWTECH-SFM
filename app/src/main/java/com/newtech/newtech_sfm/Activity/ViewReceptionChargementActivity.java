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
import com.newtech.newtech_sfm.Configuration.ImprimanteManager;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.Impression;
import com.newtech.newtech_sfm.Metier.StockDemande;
import com.newtech.newtech_sfm.Metier.StockDemandeLigne;
import com.newtech.newtech_sfm.Metier.StockTransfert;
import com.newtech.newtech_sfm.Metier.Unite;
import com.newtech.newtech_sfm.Metier.User;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.Metier_Manager.ImpressionManager;
import com.newtech.newtech_sfm.Metier_Manager.StockDemandeLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.StockDemandeManager;
import com.newtech.newtech_sfm.Metier_Manager.StockManager;
import com.newtech.newtech_sfm.Metier_Manager.StockTransfertManager;
import com.newtech.newtech_sfm.Metier_Manager.UniteManager;
import com.newtech.newtech_sfm.Metier_Manager.UserManager;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.Service.BlutoothConnctionService;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by TONPC on 19/04/2017.
 */

public class ViewReceptionChargementActivity extends AppCompatActivity{

    public static String commandeSource;
    public static String commandeType;

    public static StockDemande stockDemande = new StockDemande();
    public static  ArrayList<StockDemandeLigne> stockDemandeLignes = new ArrayList<StockDemandeLigne>();
    public static String activite_source;
    public static ImpressionManager impressionManager;
    StockDemandeManager stockDemandeManager;
    UniteManager uniteManager;
    ArticleManager articleManager;
    StockManager stockManager;

    UserManager userManager ;
    User utilisateur;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        userManager = new UserManager(getApplicationContext());
        impressionManager = new ImpressionManager(getApplicationContext());
        stockDemandeManager = new StockDemandeManager(getApplicationContext());
        uniteManager = new UniteManager(getApplicationContext());
        stockManager = new StockManager(getApplicationContext());
        articleManager = new ArticleManager(getApplicationContext());
        utilisateur = userManager.get();

        if(stockDemande!=null){

            Log.d("ViewChargement Activity", "onCreate: "+stockDemande.toString());
            Log.d("ViewChargement Activity", "onCreate: "+stockDemandeLignes.toString());

        }else{

            Log.d("ViewChargement Activity", "onCreate: stockDemande null");
            Log.d("ViewChargement Activity", "onCreate: "+stockDemandeLignes.toString());

        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.chargement_panier);


        TableLayout tableVente= (TableLayout)findViewById(R.id.tableLayout1);
        final Button valider =(Button)findViewById(R.id.btn_valider1);
        Button  annuler =(Button)findViewById(R.id.btn_annuler1);
        TextView panier_nbr_total =(TextView) findViewById(R.id.nbr_panier_total1);
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
        String STOCKDEMANDE_CODE = VENDEUR_CODE+date_commande;
        //String commande_typecode = "1";

        if(stockDemande==null){
            stockDemande= new StockDemande(STOCKDEMANDE_CODE,commandeSource,getApplicationContext());
        }

        if(stockDemandeLignes!=null){

            for(int i=0;i<stockDemandeLignes.size();i++){

                StockDemandeLigne stockDemandeLigne = stockDemandeLignes.get(i);

                TableRow ligne1 = new TableRow(this);

                TextView ligne_code = new TextView(this);
                TextView art_code = new TextView(this);
                TextView unite = new TextView(this);
                TextView qte_commandee = new TextView(this);
                TextView qte_approuvee = new TextView(this);
                TextView qte_livree = new TextView(this);
                TextView qte_receptionnee = new TextView(this);

                //Log.d("StockDemandeLignes", "onCreate: "+stockDemandeLignes.get(i).toString());

                Unite unite1 = uniteManager.get(stockDemandeLigne.getUNITE_CODE());
                Article article = articleManager.get(stockDemandeLigne.getARTICLE_CODE());

                ligne_code.setEllipsize(TextUtils.TruncateAt.END);
                unite.setEllipsize(TextUtils.TruncateAt.END);

                ligne_code.setGravity(Gravity.CENTER);
                art_code.setGravity(Gravity.CENTER);
                unite.setGravity(Gravity.CENTER);
                qte_commandee.setGravity(Gravity.CENTER);
                qte_approuvee.setGravity(Gravity.CENTER);
                qte_livree.setGravity(Gravity.CENTER);
                qte_receptionnee.setGravity(Gravity.CENTER);


                ligne_code.setTextSize(15);
                art_code.setTextSize(15);
                unite.setTextSize(15);
                qte_commandee.setTextSize(15);
                qte_approuvee.setTextSize(15);
                qte_livree.setTextSize(15);
                qte_receptionnee.setTextSize(15);

                ligne_code.setBackgroundResource(R.drawable.cell_shape);
                art_code.setBackgroundResource(R.drawable.cell_shape);
                unite.setBackgroundResource(R.drawable.cell_shape);
                qte_commandee.setBackgroundResource(R.drawable.cell_shape);
                qte_approuvee.setBackgroundResource(R.drawable.cell_shape);
                qte_livree.setBackgroundResource(R.drawable.cell_shape);
                qte_receptionnee.setBackgroundResource(R.drawable.cell_shape);



                ligne_code.setText(String.valueOf(stockDemandeLigne.getDEMANDELIGNE_CODE()));
                try{
                    art_code.setText(article.getARTICLE_DESIGNATION1());

                }catch(NullPointerException e){
                    art_code.setText(stockDemandeLigne.getARTICLE_CODE());

                }
                unite.setText(unite1.getUNITE_NOM());
                qte_commandee.setText(String.valueOf(stockDemandeLigne.getQTE_COMMANDEE()));
                qte_approuvee.setText(String.valueOf(stockDemandeLigne.getQTE_APPROUVEE()));
                qte_livree.setText(String.valueOf(stockDemandeLigne.getQTE_LIVREE()));
                qte_receptionnee.setText(String.valueOf(stockDemandeLigne.getQTE_RECEPTIONEE()));


                ligne1.addView(ligne_code);
                ligne1.addView(art_code);
                ligne1.addView(unite);
                ligne1.addView(qte_commandee);
                ligne1.addView(qte_approuvee);
                ligne1.addView(qte_livree);
                ligne1.addView(qte_receptionnee);

                tableVente.addView(ligne1);

                //panierTotal+=ListeCommandeLigne.get(i).getARTICLE_PRIX()*ListeCommandeLigne.get(i).getQTE_COMMANDEE();
            }
        }

        //panierTotal=commandeManager.getMontantNet(ListeCommandeLigne);

        //panier_nbr_total.setText(String.valueOf(new DecimalFormat("##.##").format(panierTotal)));

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                valider.setClickable(false);
                StockDemandeManager stockDemandeManager = new StockDemandeManager(getApplicationContext());
                StockTransfertManager stockTransfertManager = new StockTransfertManager(getApplicationContext());


                final StockDemandeLigneManager stockDemandeLigneManager = new StockDemandeLigneManager(getApplicationContext());


                if((stockDemande!=null && commandeSource.equals("TP0024") && commandeType.equals("Reception") && stockDemandeLignes.isEmpty()==false) || (stockDemande!=null && commandeSource.equals("TP0025") && commandeType.equals("Reception") && stockDemandeLignes.isEmpty()==false)){

                    stockDemandeLignes = stockDemandeLigneManager.updateList(ViewReceptionChargementActivity.stockDemandeLignes);

                    for(int i=0;i<stockDemandeLignes.size();i++){
                        stockDemandeLigneManager.updatestockDemandeLigne(stockDemandeLignes.get(i));
                    }

                    stockDemandeManager.updateStockDemandeStatut(stockDemande,getApplicationContext());

                    for(int i=0;i<stockDemandeLignes.size();i++){

                        try {
                            StockTransfert stockTransfert = new StockTransfert(stockDemande,stockDemandeLignes.get(i),getApplicationContext());

                            Log.d("stockTransfert", "onClick: "+ i +stockTransfert.toString());
                            stockTransfertManager.add(stockTransfert);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    stockManager.updateStock(getApplicationContext());


                    Log.d("stockTransferts", "onClick: "+stockTransfertManager.getList().toString());

                    //stockDemandeManager.add(stockDemande);

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

                            Toast.makeText(ViewReceptionChargementActivity.this,"IMPRIMER",Toast.LENGTH_LONG).show();

                            Log.d("CommandeLignevalider", "onClick: "+stockDemande.getDEMANDE_CODE());
			                        boolean printed=false;
			                        String impression_text=impressionManager.ImprimerStockDemande(stockDemande,getApplicationContext());

			                        for(int i=0;i<Integer.valueOf(nbr_copies.getText().toString());i++){

			                            printed= BlutoothConnctionService.imprimanteManager.printText(impression_text);
			                            ImprimanteManager.lastPrint=impression_text;
			                            Log.d("print", "onClick: "+impression_text.toString());

			                            if(printed==true){
			                                Log.d("printed", "onClick: "+"imprimeée");
			                                try {
			                                    Impression impression = new Impression(getApplicationContext(),stockDemande.getDEMANDE_CODE(),impression_text,"NORMAL",1,"STOCKDEMANDE");
			                                    impressionManager.add(impression);
			                                } catch (JSONException e) {
			                                    e.printStackTrace();
			                                }

			                            }else{
			                                Log.d("printed", "onClick: "+"non imprimee");
			                                try {
			                                    Impression impression = new Impression(getApplicationContext(),stockDemande.getDEMANDE_CODE(),impression_text,"STOCKEE",0,"STOCKDEMANDE");
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

                            if(isNetworkAvailable()){

                                StockDemandeManager.synchronisationStockDemandeReceptionnee(getApplicationContext());
                                StockDemandeLigneManager.synchronisationStockDemandeLigneReceptionnee(getApplicationContext());

                            }else{
                                Toast.makeText(ViewReceptionChargementActivity.this,"Vérifier votre connexion internet puis synchroniser",Toast.LENGTH_LONG).show();

                            }
                            stockDemandeLignes.clear();
                            stockDemande=null;
                            commandeSource=null;
                            dialog1.dismiss();
                            Intent i = new Intent(view.getContext(), ChargementActivity.class);
                            startActivity(i);
                            finish();
                        }

                    });

                    dialog1.show();


                }else if(stockDemande!=null && stockDemandeLignes.isEmpty()==true){
                    Toast.makeText(getApplicationContext(),"Finaliser votre demande ou appuyer sur annuler pour terminer l'opération" , Toast.LENGTH_LONG).show();
                }


            }
        });


        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                stockDemandeLignes.clear();
                stockDemande=null;
                Intent i = new Intent(view.getContext(), ChargementActivity.class);
                startActivity(i);
                finish();
            }
        });



        setTitle("RECEPTION CHARGEMENT");

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

                if(stockDemande==null){
                    new AlertDialog.Builder(ViewReceptionChargementActivity.this)
                            .setTitle("ALERTE STOCKDEMANDE")
                            .setMessage("l'OBJET STOCKDEMANDE EST NULL")
                            .setPositiveButton("ANNULLER", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            })
                            .show();

                }else{
                    ReceptionChargementPanierActivity.commandeType = null;
                    ReceptionChargementPanierActivity.commandeType = commandeType;

                    Intent intent = new Intent(this, ReceptionChargementPanierActivity.class);
                    startActivity(intent);
                    finish();


                    Toast.makeText(ViewReceptionChargementActivity.this,"CREATION CHARGEMENT",Toast.LENGTH_LONG).show();
                    return true;
                }


        }
        return super.onOptionsItemSelected(item);
    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Finaliser votre demande ou appuyer sur annuler pour terminer l'opération" , Toast.LENGTH_LONG).show();
    }

}
