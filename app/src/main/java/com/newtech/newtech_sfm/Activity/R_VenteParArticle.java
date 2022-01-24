package com.newtech.newtech_sfm.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Metier.Impression;
import com.newtech.newtech_sfm.Metier.Unite;
import com.newtech.newtech_sfm.Metier_Manager.CommandeLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.ImpressionManager;
import com.newtech.newtech_sfm.Metier_Manager.UniteManager;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.Service.BlutoothConnctionService;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sferricha on 13/08/2016.
 */

public class R_VenteParArticle extends AppCompatActivity {


    public static ImpressionManager impressionManager;

    TableLayout tablelayout1;
    Button mBtnPrint;
    String IMPRESSION_CODE;
    UniteManager uniteManager;
    private String rapportText = "w(  Rapport : -- Vente Par Article Par Date --  \r\n"+
            "----------------------------------------------------\r\n" +
            "ARTICLE               UNITE             QUANTITE \r\n" +
            "----------------------------------------------------\r\n";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_ventepararticle);
        setTitle("VENTE PAR ARTICLE");

        impressionManager=new ImpressionManager(getApplicationContext());
        uniteManager=new UniteManager(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tablelayout1 = (TableLayout) findViewById(R.id.tablelayout1);

        CommandeLigneManager commandeLigneManager = new CommandeLigneManager(getApplicationContext());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date=sdf.format(new Date()).toString();

        ArrayList<CommandeLigneManager.VenteArticle> ListeCommandeLigne;
        ArrayList<CommandeLigneManager.VenteArticle> ListeCommandeLigne1;

        ListeCommandeLigne = commandeLigneManager.getRealisationArticleParDateUnite(date);
 


        double nbr_unite=0;
        double nbr_quantite=0;

        Log.d("ListeCommandeLigne", " "+ListeCommandeLigne.toString());

        if(ListeCommandeLigne!=null){

            for(int i=0;i<ListeCommandeLigne.size();i++){

                String article_code=ListeCommandeLigne.get(i).getARTICLE_CODE();
                String unite_code=ListeCommandeLigne.get(i).getUNITE_CODE();

                nbr_unite=0;
                nbr_quantite=0;
                Unite unite_ligne = uniteManager.get(unite_code);

                TableRow ligne1 = new TableRow(this);

                TextView art_code = new TextView(this);
                TextView unite = new TextView(this);
                TextView quantite = new TextView(this);



                Log.d("RAPPORT VA", " "+ListeCommandeLigne.get(i).getARTICLE_CODE()+" QTE CAISSE"+ unite_code);
                Log.d("RAPPORT VA", " "+ListeCommandeLigne.get(i).getARTICLE_CODE()+" QTE "+nbr_quantite);

                nbr_quantite = ListeCommandeLigne.get(i).getQUANTITE_COMMANDEE();

                art_code.setTextSize(20);
                unite.setTextSize(15);
                quantite.setTextSize(15);

                art_code.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT,1));
                unite.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT,1));
                quantite.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT,1));

                art_code.setGravity(Gravity.LEFT);
                unite.setGravity(Gravity.CENTER);
                quantite.setGravity(Gravity.RIGHT);

                art_code.setText(ListeCommandeLigne.get(i).getARTICLE_CODE());
                unite.setText(String.valueOf(unite_ligne.getUNITE_NOM()));
                quantite.setText(String.valueOf(nbr_quantite));

                ligne1.addView(art_code);
                ligne1.addView(unite);
                ligne1.addView(quantite);

                tablelayout1.addView(ligne1);

                rapportText+= Nchaine(ListeCommandeLigne.get(i).getARTICLE_CODE(),20)+"    "+Nchaine(unite_ligne.getUNITE_NOM(),15)+"   "+Nchaine(String.valueOf(nbr_quantite),15)+" \r\n";
            }

            rapportText+= "\r\n\r\n\r\n\r\n";
        }else{

            Toast.makeText(getApplicationContext(),"Un probléme est survenu merci d'essayer ultérieurement" , Toast.LENGTH_LONG).show();

        }

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
        IMPRESSION_CODE = VENDEUR_CODE+date_commande;

        mBtnPrint = (Button) findViewById(R.id.buttonprint);

        mBtnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean printed=false;

                Toast.makeText(getApplicationContext(),"rapp"+rapportText,Toast.LENGTH_SHORT).show();

                printed=BlutoothConnctionService.imprimanteManager.printText(rapportText);
                //ImprimanteManager.lastPrint=rapportText;

                if(printed==true){
                    Log.d("printed", "onClick: "+"imprimeée");
                    try {
                        Impression impression = new Impression(getApplicationContext(),IMPRESSION_CODE,rapportText,"NORMAL",1,"RAPPORT");
                        impressionManager.add(impression);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    Log.d("printed", "onClick: "+"non imprimee");
                    try {
                        Impression impression = new Impression(getApplicationContext(),IMPRESSION_CODE,rapportText,"STOCKEE",0,"RAPPORT");
                        impressionManager.add(impression);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                BlutoothConnctionService.imprimanteManager.printText(rapportText);
            }
        });

    }


    static String Nchaine(String machaine, int size){
        int taille = machaine.length();
        String spaces="";
        if(size<taille) return machaine.substring(0,size);
        for(int i=0;i<size-taille;i++)
            spaces+=" ";
        return machaine+spaces;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            case R.id.logout:
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(R_VenteParArticle.this, AuthActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                return true;

            case R.id.option2:
                Intent intt = new Intent(this, PrintActivity2.class);
                intt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intt);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
