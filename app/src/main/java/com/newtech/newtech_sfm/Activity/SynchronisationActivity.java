package com.newtech.newtech_sfm.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.Metier_Manager.ArticlePrixManager;
import com.newtech.newtech_sfm.Metier_Manager.CategorieManager;
import com.newtech.newtech_sfm.Metier_Manager.ClasseManager;
import com.newtech.newtech_sfm.Metier_Manager.ClientManager;
import com.newtech.newtech_sfm.Metier_Manager.ClientNManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeGratuiteManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeNonClotureeLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeNonClotureeManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandePromotionManager;
import com.newtech.newtech_sfm.Metier_Manager.CreditManager;
import com.newtech.newtech_sfm.Metier_Manager.DistributeurManager;
import com.newtech.newtech_sfm.Metier_Manager.EncaissementManager;
import com.newtech.newtech_sfm.Metier_Manager.FamilleManager;
import com.newtech.newtech_sfm.Metier_Manager.LieuManager;
import com.newtech.newtech_sfm.Metier_Manager.ListePrixLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.ListePrixManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonGratuiteManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonPromotionManager;
import com.newtech.newtech_sfm.Metier_Manager.LogSyncManager;
import com.newtech.newtech_sfm.Metier_Manager.ParametreManager;
import com.newtech.newtech_sfm.Metier_Manager.PromotionManager;
import com.newtech.newtech_sfm.Metier_Manager.PromotionaffectationManager;
import com.newtech.newtech_sfm.Metier_Manager.PromotionarticleManager;
import com.newtech.newtech_sfm.Metier_Manager.PromotiongratuiteManager;
import com.newtech.newtech_sfm.Metier_Manager.PromotionpalierManager;
import com.newtech.newtech_sfm.Metier_Manager.StatutManager;
import com.newtech.newtech_sfm.Metier_Manager.StockDemandeLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.StockDemandeManager;
import com.newtech.newtech_sfm.Metier_Manager.StockManager;
import com.newtech.newtech_sfm.Metier_Manager.StockPLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.StockPManager;
import com.newtech.newtech_sfm.Metier_Manager.TacheActionManager;
import com.newtech.newtech_sfm.Metier_Manager.TacheManager;
import com.newtech.newtech_sfm.Metier_Manager.TachePlanificationManager;
import com.newtech.newtech_sfm.Metier_Manager.TourneeManager;
import com.newtech.newtech_sfm.Metier_Manager.TypeManager;
import com.newtech.newtech_sfm.Metier_Manager.UniteManager;
import com.newtech.newtech_sfm.Metier_Manager.UtilisateurUniqueManager;
import com.newtech.newtech_sfm.Metier_Manager.VisibiliteLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.VisibiliteManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteResultatManager;
import com.newtech.newtech_sfm.R;

/**
 * Created by stagiaireit2 on 18/07/2016.
 */
public class SynchronisationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.synchronisation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final  TextView logSynch = (TextView)findViewById(R.id.log_text);
        final Button btn_sync=(Button)findViewById(R.id.button_synchro);
        //logSynch.setCursorVisible(false);
        final LogSyncManager log= new LogSyncManager(getApplicationContext());


        if(log.getList().size()>=12) {
            for (int i = log.getList().size() - 12; i < log.getList().size(); i++)
                logSynch.append(log.getList().get(i).toString() );
        }

        btn_sync.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if(isNetworkAvailable()){
                    ArticleManager.synchronisationArticle(getApplicationContext());
                    ArticlePrixManager.synchronisationArticlePrix(getApplicationContext());

                    TourneeManager.synchronisationTournee(getApplicationContext());

                    PromotionManager.synchronisationPromotion(getApplicationContext());
                    PromotionaffectationManager.synchronisationPromotionAffectation(getApplicationContext());
                    PromotiongratuiteManager.synchronisationPromotionGratuite(getApplicationContext());
                    PromotionpalierManager.synchronisationPromotionPalier(getApplicationContext());
                    PromotionarticleManager.synchronisationPromArticleAffectation(getApplicationContext());


                    CommandeManager.synchronisationCommande(getApplicationContext());
                    CommandeLigneManager.synchronisationCommandeLigne(getApplicationContext());
                    CommandeGratuiteManager.synchronisationCommandeGratuite(getApplicationContext());
                    CommandePromotionManager.synchronisationCommandePromotion(getApplicationContext());
                    //CommandePromotionManager.synchronisationCommandePromotion(getApplicationContext());

                    ClientManager.synchronisationClient(getApplicationContext());
                    ClientNManager.synchronisationClientN(getApplicationContext());
                    FamilleManager.synchronisationFamille(getApplicationContext());


                    VisiteResultatManager.synchronisationVisiteResultat(getApplicationContext());
                    VisiteManager.synchronisationVisite(getApplicationContext());

                    TacheManager.synchronisationTache(getApplicationContext());
                    TacheActionManager.synchronisationTacheAction(getApplicationContext());
                    TachePlanificationManager.synchronisationTachePlanification(getApplicationContext());

                    LivraisonManager.synchronisationLivraison(getApplicationContext());
                    LivraisonLigneManager.synchronisationLivraisonLigne(getApplicationContext());

                    //CommandeALivreeManager.synchronisationCommandeALivrer(getApplicationContext());
                    //CommandeLigneALivrerManager.synchronisationCommandeLigneALivrer(getApplicationContext());

                    DistributeurManager.synchronisationDistributeur(getApplicationContext());
                    EncaissementManager.synchronisationEncaissement(getApplicationContext());
                    UtilisateurUniqueManager.synchronisationUtilisateurUnique(getApplicationContext());

                    CommandeNonClotureeManager.synchronisationCommandeNonCloturee(getApplicationContext());
                    CommandeNonClotureeLigneManager.synchronisationCommandeNonClotureeLigne(getApplicationContext());

                    LivraisonManager.synchronisationLivraisonPull(getApplicationContext());
                    LivraisonLigneManager.synchronisationLivraisonLignePull(getApplicationContext());

                    //EncaissementManager.synchronisationEncaissementPull(getApplicationContext());

                    ListePrixManager.synchronisationListePrix(getApplicationContext());
                    ListePrixLigneManager.synchronisationListePrixLigne(getApplicationContext());

                    StockDemandeManager.synchronisationStockDemande(getApplicationContext());
                    StockDemandeLigneManager.synchronisationStockDemandeLigne(getApplicationContext());

                    CategorieManager.synchronisationCategorie(getApplicationContext());
                    StatutManager.synchronisationStatut(getApplicationContext());
                    TypeManager.synchronisationType(getApplicationContext());
                    ClasseManager.synchronisationClasse(getApplicationContext());

                    UniteManager.synchronisationUnite(getApplicationContext());
                    LieuManager.synchronisationLieu(getApplicationContext());

                    LivraisonPromotionManager.synchronisationLivraisonPromotion(getApplicationContext());
                    LivraisonGratuiteManager.synchronisationLivraisonGratuite(getApplicationContext());

                    //StockPManager.synchronisationStockP(getApplicationContext());
                    //StockPLigneManager.synchronisationStockPLigne(getApplicationContext());

                    //StockTransfertManager.synchronisationStockTransfert(getApplicationContext());

                    StockManager.synchronisationStock(getApplicationContext());
                    //StockLigneManager.synchronisationStockLigne(getApplicationContext());

                    ParametreManager.synchronisationParametre(getApplicationContext());

                    CreditManager.synchronisationCredit(getApplicationContext());
                    CreditManager.synchronisationCreditPull(getApplicationContext());

                    VisibiliteManager.synchronisationVisibilite(getApplicationContext());
                    VisibiliteLigneManager.synchronisationVisibiliteLigne(getApplicationContext());

                    StockPManager.synchronisationStockP(getApplicationContext());
                    StockPLigneManager.synchronisationStockPLigne(getApplicationContext());



                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }else{
                    Toast.makeText(SynchronisationActivity.this,"Vérifier votre connexion internet puis synchroniser",Toast.LENGTH_LONG).show();
                }


            }
        });

        setTitle("SYNCHRONISATION");
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
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
                Intent i = new Intent(SynchronisationActivity.this, AuthActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                return true;

            case R.id.option2:
                Intent intt = new Intent(SynchronisationActivity.this, PrintActivity2.class);
                intt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intt);
                finish();
                return true;
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
        super.onBackPressed();
        Intent i = new Intent(SynchronisationActivity.this, MenuActivity.class);
        startActivity(i);
        finish();
    }
}
