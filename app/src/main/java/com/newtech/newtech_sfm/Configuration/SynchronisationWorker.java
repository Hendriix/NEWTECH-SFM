package com.newtech.newtech_sfm.Configuration;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

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
import com.newtech.newtech_sfm.Metier_Manager.VisiteManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteResultatManager;
import com.newtech.newtech_sfm.R;

public class SynchronisationWorker extends Worker{

    public SynchronisationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        sendNotification("Synchronisation", "Synchronisation en cours...");

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

        StockPManager.synchronisationStockP(getApplicationContext());
        StockPLigneManager.synchronisationStockPLigne(getApplicationContext());

        //StockTransfertManager.synchronisationStockTransfert(getApplicationContext());

        StockManager.synchronisationStock(getApplicationContext());
        //StockLigneManager.synchronisationStockLigne(getApplicationContext());

        ParametreManager.synchronisationParametre(getApplicationContext());
        return Result.success();
    }

    public void sendNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.logosavola2);

        notificationManager.notify(1, notification.build());
    }
}
