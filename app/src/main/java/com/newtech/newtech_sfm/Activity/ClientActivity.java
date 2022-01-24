package com.newtech.newtech_sfm.Activity;

import android.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.AnnulationCommande.AnnulerCommandeActivity;
import com.newtech.newtech_sfm.AnnulationLivraison.AnnulerLivraisonActivity;
import com.newtech.newtech_sfm.AnnulerBC.AnnulerBcActivity;
import com.newtech.newtech_sfm.ContratImage.ContratImageActivity;
import com.newtech.newtech_sfm.Livraison.LivraisonDateActivity;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.Parametre;
import com.newtech.newtech_sfm.Metier.User;
import com.newtech.newtech_sfm.Metier.Visite;
import com.newtech.newtech_sfm.Metier_Manager.ChoufouniContratManager;
import com.newtech.newtech_sfm.Metier_Manager.ClientManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeManager;
import com.newtech.newtech_sfm.Metier_Manager.EncaissementManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonManager;
import com.newtech.newtech_sfm.Metier_Manager.ParametreManager;
import com.newtech.newtech_sfm.Metier_Manager.UserManager;
import com.newtech.newtech_sfm.Metier_Manager.UtilisateurUniqueManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteManager;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.TableauBordClient.TableauBordClientActivity;
import com.newtech.newtech_sfm.merchandising.MerchandisingActivity;
import com.newtech.newtech_sfm.superviseur.QuestionnaireActivity;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by stagiaireit2 on 27/07/2016.
 */
public class ClientActivity extends AppCompatActivity {

    private static final String TAG = ClientActivity.class.getName();
    public static Client clientCourant=new Client();
    public static Visite visiteCourante=new Visite();
    public static String activity_source="";
    public static String commande_source="";

    public static Double gps_latitude = 0.0;
    public static Double gps_longitude = 0.0;

    public static String visite_code="";
    public static String utilisateur_code="";
    public static String tournee_code="";
    public static String distributeur_code="";
    public static String visite_source="";


    ChoufouniContratManager choufouniContratManager;
    UtilisateurUniqueManager utilisateurUniqueManager;
    ParametreManager parametreManager;
    UserManager userManager;
    Parametre parametre;

    private ImageView btn_info;
    private ImageView btn_edit;
    private ImageView btn_rapport;
    private Button btn_cmd;
    private Button btn_livraison;
    private Button btn_promotion;
    private Button btn_avoir;
    private Button btn_annuler;
    private Button btn_encaissement;
    private Button btn_facturation;
    private Button btn_referencement_visibilite;
    private Button btn_relever_stock;
    private Button btn_choufouni;
    private Button btn_contrat_image;
    private Button btn_questionnaire;


    private String client_code="";
    private float insertVisiteResponse;
    private int vc;
    public String tache_code="";

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_activity);

        utilisateurUniqueManager = new UtilisateurUniqueManager(this);
        choufouniContratManager = new ChoufouniContratManager(this);
        parametreManager = new ParametreManager(this);
        userManager = new UserManager(this);
        user = userManager.get();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_client);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        parametre = parametreManager.get("AVOIR");



        btn_info = (ImageView)findViewById(R.id.btn_info);
        btn_edit=(ImageView)findViewById(R.id.btn_edit);
        btn_rapport=(ImageView)findViewById(R.id.btn_rapport);

        btn_cmd=(Button)findViewById(R.id.btn_cmd);
        btn_facturation=(Button)findViewById(R.id.btn_facturation);
        btn_livraison=(Button)findViewById(R.id.btn_livraison);
        btn_avoir=(Button)findViewById(R.id.btn_avoir);
        btn_annuler=(Button)findViewById(R.id.btn_annuler);
        btn_promotion=(Button)findViewById(R.id.btn_promotion);
        btn_encaissement=(Button)findViewById(R.id.btn_encaissement);
        btn_referencement_visibilite=(Button)findViewById(R.id.btn_referencement_visibilite);
        btn_relever_stock=(Button)findViewById(R.id.btn_relever_stock);
        btn_choufouni=(Button)findViewById(R.id.btn_choufouni);
        btn_contrat_image=(Button)findViewById(R.id.btn_contrat_image);
        btn_questionnaire=(Button)findViewById(R.id.btn_questionnaire);

        /////////////////////////////////////////CACHER BOUTTON NON FONCTIONNELS//////////////////////////////////////

        try{
            if(parametre.getVALEUR().equals("0")){
                btn_avoir.setVisibility(View.GONE);
            }
        }catch(NullPointerException e){
            Log.d(TAG, "onCreate: ");
        }


        if(user.getPROFILE_CODE().equals("PF0001")){

            btn_cmd.setVisibility(View.GONE);
            btn_livraison.setVisibility(View.GONE);
            btn_avoir.setVisibility(View.GONE);

        }else if(user.getPROFILE_CODE().equals("PF0010")){

            btn_cmd.setVisibility(View.GONE);
            btn_avoir.setVisibility(View.GONE);
            btn_facturation.setVisibility(View.GONE);
            //btn_referencement_visibilite.setVisibility(View.GONE);
            btn_choufouni.setVisibility(View.GONE);
            btn_contrat_image.setVisibility(View.GONE);

        }else if(user.getPROFILE_CODE().equals("PF0012") || user.getPROFILE_CODE().equals("PF0006")){

            btn_cmd.setVisibility(View.GONE);
            btn_facturation.setVisibility(View.GONE);
            btn_livraison.setVisibility(View.GONE);
            btn_avoir.setVisibility(View.GONE);
            btn_promotion.setVisibility(View.GONE);
            btn_encaissement.setVisibility(View.GONE);
            btn_choufouni.setVisibility(View.GONE);
            btn_contrat_image.setVisibility(View.GONE);
            btn_annuler.setVisibility(View.GONE);


        }else if(user.getPROFILE_CODE().equals("PF0011") || user.getPROFILE_CODE().equals("PF0002")){
;;
            btn_facturation.setVisibility(View.GONE);
            btn_livraison.setVisibility(View.GONE);
            btn_avoir.setVisibility(View.GONE);
            //btn_referencement_visibilite.setVisibility(View.GONE);
            btn_encaissement.setVisibility(View.GONE);
            //btn_choufouni.setVisibility(View.GONE);
            //btn_contrat_image.setVisibility(View.GONE);


        }

        btn_relever_stock.setVisibility(View.GONE);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////GET DATA FROM INTENT///////////////////////////////////////////////

        Intent intent = getIntent();
        ClientManager clientmanager= new ClientManager(getApplicationContext());

        //tournee_code=VisiteActivity.clientCourant.getTOURNEE_CODE();
        vc=intent.getIntExtra("VISITERESULTAT_CODE",0);

        if(vc == 1 || vc == -1){
            btn_cmd.setVisibility(View.GONE);
            btn_annuler.setVisibility(View.GONE);
            btn_facturation.setVisibility(View.GONE);
            btn_avoir.setVisibility(View.GONE);
            btn_livraison.setVisibility(View.GONE);
        }

        ////////////////////////////////////FIN GET DATA FROM INTENT/////////////////////////////////////////////////


        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref", 0);
        if( pref1.getString("is_login", null).equals("ok")) {
            try{
                Gson gson2 = new Gson();
                String json2 = pref1.getString("User", "");
                Type type = new TypeToken<JSONObject>() {}.getType();
                final JSONObject user = gson2.fromJson(json2, type);
                utilisateur_code =user.getString("UTILISATEUR_CODE");
                distributeur_code=user.getString("DISTRIBUTEUR_CODE");
            }
            catch (Exception e){
            }
        }

        if(visiteCourante==null){

            SimpleDateFormat df2 = new SimpleDateFormat("yyMMddHHmmss");
            String date_visite2 = df2.format(new java.util.Date());

            VisiteManager visiteManager=new VisiteManager(getApplicationContext());

            visite_code=distributeur_code+utilisateur_code+date_visite2;

            Visite nouvelleVisite = new Visite(visite_code,distributeur_code,utilisateur_code,clientCourant,VisiteActivity.tache_code,gps_latitude,gps_longitude,visite_source);
            visiteManager.add(nouvelleVisite);
            visiteCourante=nouvelleVisite;

        }

        if(visiteCourante.getVISITE_RESULTAT() != 0){

            btn_cmd.setVisibility(View.GONE);
            btn_avoir.setVisibility(View.GONE);
            btn_facturation.setVisibility(View.GONE);

        }


        if(btn_promotion!=null){
            btn_promotion.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    //Toast.makeText(ClientActivity.this,"A ACTIVER",
                            //Toast.LENGTH_LONG).show();

                    Intent i = new Intent(ClientActivity.this, PromotionActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }


        if(btn_livraison!=null){
            btn_livraison.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    //Toast.makeText(ClientActivity.this,"A ACTIVER",
                           // Toast.LENGTH_LONG).show();
                ViewLivraisonActivity.visite_code=visite_code;
                ViewCommandeActivity.commandeSource=null;
                ViewCommandeActivity.commandeSource="Livraison";
                Intent i = new Intent(ClientActivity.this, CommandeActivity.class);
                startActivity(i);
                finish();
                }
            });
        }

        if(btn_info!=null){
            btn_info.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            AlertDialog show = new AlertDialog.Builder(ClientActivity.this)
                                    .setTitle("npm")
                                    .setMessage(ClientActivity.clientCourant.toString() )
                                    .setNeutralButton("OK", null)
                                    .show();
                        }
            });
        }

        if(btn_cmd!=null) {
            btn_cmd.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    ViewCommandeActivity.commandeSource=null;
                    ViewCommandeActivity.commande=null;
                    ViewCommandeActivity.ListeCommandeLigne.clear();
                    ViewCommandeActivity.visite_code=visite_code;
                    ViewCommandeActivity.commandeSource="Commande";
                    Intent i = new Intent(ClientActivity.this, ViewCommandeActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }

        if(btn_avoir!=null) {
            btn_avoir.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    ViewCommandeActivity.commandeSource=null;
                    ViewCommandeActivity.commande=null;
                    ViewCommandeActivity.ListeCommandeLigne.clear();
                    ViewCommandeActivity.visite_code=visite_code;
                    ViewCommandeActivity.commandeSource="Avoir";
                    Intent i = new Intent(ClientActivity.this, ViewCommandeActivity.class);
                    i.putExtra("VISITE_CODE",visiteCourante.getVISITE_CODE());
                    startActivity(i);
                    finish();
                }
            });
        }

        if(btn_annuler!=null) {
            btn_annuler.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    Intent intent = new Intent();
                    if(user.getPROFILE_CODE().equals("PF0010")){
                         intent = new Intent(ClientActivity.this, AnnulerLivraisonActivity.class);
                        AnnulerLivraisonActivity.client_code = clientCourant.getCLIENT_CODE();
                        AnnulerLivraisonActivity.visite_code = visite_code;
                    }else if(user.getPROFILE_CODE().equals("PF0002") || user.getPROFILE_CODE().equals("PF0011") ){
                         intent = new Intent(ClientActivity.this, AnnulerBcActivity.class);
                        AnnulerBcActivity.client_code = clientCourant.getCLIENT_CODE();
                        AnnulerBcActivity.visite_code = visite_code;
                    }else{
                         intent = new Intent(ClientActivity.this, AnnulerCommandeActivity.class);
                        AnnulerCommandeActivity.client_code = clientCourant.getCLIENT_CODE();
                        AnnulerCommandeActivity.visite_code = visite_code;
                    }
                    startActivity(intent);
                    finish();

                }
            });
        }

        if(btn_edit!=null) {
            btn_edit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent i = new Intent(ClientActivity.this, Client_Manager.class);
                    i.putExtra("CLIENT_CODE",clientCourant.getCLIENT_CODE());
                    i.putExtra("FROM","CLIENT");
                    i.putExtra("SOURCE","ClientActivity");
                    startActivity(i);
                    finish();
                }
            });
        }

        if(btn_rapport!=null) {
            btn_rapport.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent i = new Intent(ClientActivity.this, TableauBordClientActivity.class);
                    TableauBordClientActivity.client_code = clientCourant.getCLIENT_CODE();
                    TableauBordClientActivity.source = "ClientActivity";
                    startActivity(i);
                    //finish();
                }
            });
        }

        if(btn_encaissement!=null) {

            btn_encaissement.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    //Toast.makeText(ClientActivity.this,"A ACTIVER",
                           // Toast.LENGTH_LONG).show();

                    Intent i = new Intent(ClientActivity.this, CommandeEncaissementActivity.class);
                    EncaissementActivity.commande_source="Encaissement";
                    startActivity(i);
                    finish();
                }
            });
        }

         if(btn_facturation!=null) {
             btn_facturation.setOnClickListener(new View.OnClickListener() {
                 public void onClick(View view) {
                     //Toast.makeText(ClientActivity.this,"A ACTIVER",
                     //Toast.LENGTH_LONG).show();

                     Intent i = new Intent(ClientActivity.this, ViewCommandeActivity.class);
                     ViewCommandeActivity.commandeSource = null;
                     ViewCommandeActivity.commande = null;
                     ViewCommandeActivity.ListeCommandeLigne.clear();
                     ViewCommandeActivity.visite_code = visite_code;
                     ViewCommandeActivity.commandeSource = "Facturation";
                     startActivity(i);
                     finish();
                 }
             });
         }

        if(btn_referencement_visibilite!=null){
            btn_referencement_visibilite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ClientActivity.this, MerchandisingActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        if(btn_relever_stock!=null){
            btn_relever_stock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ClientActivity.this, ReleveStockActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        if(btn_choufouni!=null){
            btn_choufouni.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ClientActivity.this, ChoufouniContratActivity.class);
                    intent.putExtra("CLIENT_CODE",clientCourant.getCLIENT_CODE());
                    intent.putExtra("FROM","CLIENT");
                    startActivity(intent);
                    finish();
                }
            });
        }

        if(btn_contrat_image!=null){
            btn_contrat_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ClientActivity.this, ContratImageActivity.class);
                    intent.putExtra("CLIENT_CODE",clientCourant.getCLIENT_CODE());
                    intent.putExtra("FROM","CLIENT");
                    startActivity(intent);
                    finish();
                }
            });
        }

        if(btn_questionnaire!=null){
            btn_questionnaire.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ClientActivity.this, QuestionnaireActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }


       setTitle(clientCourant.getCLIENT_NOM());

        if(choufouniContratManager.getChoufouniContrat(this,clientCourant.getCLIENT_CODE())){
            toolbar.setLogo(R.drawable.medal_icone);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            case R.id.add_client:
                Intent intent=new Intent(this,Client_Manager.class);
                startActivity(intent);
                finish();
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        CommandeManager commande_manager=new CommandeManager(getApplicationContext());
        VisiteManager visite_Manager= new VisiteManager(getApplicationContext());
        LivraisonManager livraisonManager = new LivraisonManager(getApplicationContext());
        EncaissementManager encaissementManager = new EncaissementManager(getApplicationContext());


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_visite=df.format(Calendar.getInstance().getTime());
        int vrc = commande_manager.CheckCommandeByVisiteCode(visiteCourante.getVISITE_CODE());
        int vrm = visite_Manager.count(visiteCourante.getVISITE_CODE());
        int vrcal = livraisonManager.getListByVisiteCode(visiteCourante.getVISITE_CODE()).size();


        if(commande_source.equals("VISITE")){

            if(vrc<=0 ){

                if(vrm>0){

                    visite_Manager.updateVisite_VRDF(visiteCourante.getVISITE_CODE(),vc,date_visite);

                    if(isNetworkAvailable()){
                        visite_Manager.synchronisationVisite(getApplicationContext());
                    }else{
                        Toast.makeText(ClientActivity.this,"Vérifier votre connexion internet puis synchroniser",Toast.LENGTH_LONG).show();

                    }

                    Intent intent=new Intent(getApplicationContext(),VisiteActivity.class);
                    intent.putExtra("TOURNEE_CODE",ClientActivity.clientCourant.getTOURNEE_CODE());
                    intent.putExtra("CLIENT_CODE",ClientActivity.clientCourant.getCLIENT_CODE());

                    ClientActivity.commande_source=null;
                    visite_code=null;
                    distributeur_code=null;
                    utilisateur_code=null;
                    tournee_code=null;
                    visiteCourante=null;
                    startActivity(intent);
                    finish();

                }else{

                    Intent intent = new Intent(this, VisiteResultatActivity.class);
                    startActivity(intent);
                    finish();
                }


            }else{

                visite_Manager.updateVisite_VRDF(visiteCourante.getVISITE_CODE(),vc,date_visite);

                if(isNetworkAvailable()){
                    visite_Manager.synchronisationVisite(getApplicationContext());
                }else{
                    Toast.makeText(ClientActivity.this,"Vérifier votre connexion internet puis synchroniser",Toast.LENGTH_LONG).show();

                }

                Intent intent=new Intent(getApplicationContext(),VisiteActivity.class);
                intent.putExtra("TOURNEE_CODE",ClientActivity.clientCourant.getTOURNEE_CODE());
                intent.putExtra("CLIENT_CODE",ClientActivity.clientCourant.getCLIENT_CODE());

                ClientActivity.commande_source=null;
                visite_code=null;
                distributeur_code=null;
                utilisateur_code=null;
                tournee_code=null;
                visiteCourante=null;
                startActivity(intent);
                finish();
            }

        }else if(commande_source.equals("LIVRAISON")){

            Log.d(TAG, "onBackPressed: "+visiteCourante.toString());
            //Log.d("livraison1", "onBackPressed: "+commande_source.toString());

            if(vrcal>0){

                //Log.d("livraison1", "onBackPressed: "+String.valueOf(vrcal));
                visite_Manager.updateVisite_VRDF(visiteCourante.getVISITE_CODE(),vc,date_visite);


                if(isNetworkAvailable()){
                    visite_Manager.synchronisationVisite(getApplicationContext());
                }else{
                    Toast.makeText(ClientActivity.this,"Vérifier votre connexion internet puis synchroniser",Toast.LENGTH_LONG).show();

                }

                Intent intent=new Intent(getApplicationContext(), LivraisonDateActivity.class);
                intent.putExtra("TOURNEE_CODE",ClientActivity.clientCourant.getTOURNEE_CODE());
                intent.putExtra("CLIENT_CODE",ClientActivity.clientCourant.getCLIENT_CODE());

                ClientActivity.commande_source=null;
                visite_code=null;
                distributeur_code=null;
                utilisateur_code=null;
                tournee_code=null;
                visiteCourante=null;
                startActivity(intent);
                finish();

            }else{
                //Log.d("livraison1", "onBackPressed: "+String.valueOf(vrcal));

                Intent intent = new Intent(this, VisiteResultatActivity.class);
                startActivity(intent);
                finish();
            }

        }else if(commande_source.equals("ENCAISSEMENT")) {
            Intent intent = new Intent(getApplicationContext(), VisiteActivity.class);

            ClientActivity.commande_source = null;
            visite_code = null;
            distributeur_code = null;
            utilisateur_code = null;
            tournee_code = null;
            visiteCourante = null;
            startActivity(intent);
            finish();
        }else{

        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}