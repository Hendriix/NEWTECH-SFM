package com.newtech.newtech_sfm.Configuration;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.newtech.newtech_sfm.Metier.LogSync;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.Metier_Manager.ArticlePrixManager;
import com.newtech.newtech_sfm.Metier_Manager.CategorieManager;
import com.newtech.newtech_sfm.Metier_Manager.ChoufouniContratImageManager;
import com.newtech.newtech_sfm.Metier_Manager.ChoufouniContratManager;
import com.newtech.newtech_sfm.Metier_Manager.ChoufouniContratPullManager;
import com.newtech.newtech_sfm.Metier_Manager.ChoufouniManager;
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
import com.newtech.newtech_sfm.Metier_Manager.LogsManager;
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
import com.newtech.newtech_sfm.Metier_Manager.VisibiliteRayonManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteResultatManager;

import java.util.ArrayList;
import java.util.List;

public class LogSyncViewModel extends AndroidViewModel {

    private static final String TAG = LogSyncViewModel.class.getSimpleName();

    private List<LogSync> logSyncList = new ArrayList<>();
    private MutableLiveData<List<LogSync>> listMutableLiveData;

    LogSyncManager logSyncManager;

    public LogSyncViewModel(@NonNull Application application) {
        super(application);
        logSyncManager = new LogSyncManager(getApplication());
    }


    public LiveData<List<LogSync>> getLogSyncList() {

        listMutableLiveData = new MutableLiveData<>();
        loadLogSyncs();

        return listMutableLiveData;
    }

    public void setListMutableLiveData(LogSync logSync) {
        Log.d(TAG, "setListMutableLiveData: " + logSync);
        logSyncList.add(logSync);
        this.listMutableLiveData.setValue(logSyncList);
    }

    public void clearListMutableLiveData(List<LogSync> logSyncs) {

        if (logSyncList.size() > 0) {
            logSyncList = logSyncs;
            this.listMutableLiveData.setValue(logSyncList);
        }


    }

    private void loadLogSyncs() {

        ArticleManager.synchronisationArticle(getApplication());
        ArticlePrixManager.synchronisationArticlePrix(getApplication());
        TourneeManager.synchronisationTournee(getApplication());
        PromotionManager.synchronisationPromotion(getApplication());
        PromotionaffectationManager.synchronisationPromotionAffectation(getApplication());
        PromotiongratuiteManager.synchronisationPromotionGratuite(getApplication());
        PromotionpalierManager.synchronisationPromotionPalier(getApplication());
        PromotionarticleManager.synchronisationPromArticleAffectation(getApplication());
        CommandeManager.synchronisationCommande(getApplication());
        CommandeLigneManager.synchronisationCommandeLigne(getApplication());
        CommandeGratuiteManager.synchronisationCommandeGratuite(getApplication());
        CommandePromotionManager.synchronisationCommandePromotion(getApplication());
        ClientManager.synchronisationClient(getApplication());
        ClientNManager.synchronisationClientN(getApplication());
        FamilleManager.synchronisationFamille(getApplication());
        VisiteResultatManager.synchronisationVisiteResultat(getApplication());
        VisiteManager.synchronisationVisite(getApplication());

        TacheManager.synchronisationTache(getApplication());
        TacheActionManager.synchronisationTacheAction(getApplication());
        TachePlanificationManager.synchronisationTachePlanification(getApplication());
        LivraisonManager.synchronisationLivraison(getApplication());
        LivraisonLigneManager.synchronisationLivraisonLigne(getApplication());
        //CommandeALivreeManager.synchronisationCommandeALivrer(getApplication());
        //CommandeLigneALivrerManager.synchronisationCommandeLigneALivrer(getApplication());
        DistributeurManager.synchronisationDistributeur(getApplication());
        EncaissementManager.synchronisationEncaissement(getApplication());


        UtilisateurUniqueManager.synchronisationUtilisateurUnique(getApplication());
        CommandeNonClotureeManager.synchronisationCommandeNonCloturee(getApplication());
        CommandeNonClotureeLigneManager.synchronisationCommandeNonClotureeLigne(getApplication());
        LivraisonManager.synchronisationLivraisonPull(getApplication());
        LivraisonLigneManager.synchronisationLivraisonLignePull(getApplication());
        //EncaissementManager.synchronisationEncaissementPull(getApplication());

        ListePrixManager.synchronisationListePrix(getApplication());
        ListePrixLigneManager.synchronisationListePrixLigne(getApplication());
        StockDemandeManager.synchronisationStockDemande(getApplication());
        StockDemandeLigneManager.synchronisationStockDemandeLigne(getApplication());
        CategorieManager.synchronisationCategorie(getApplication());
        StatutManager.synchronisationStatut(getApplication());
        TypeManager.synchronisationType(getApplication());
        ClasseManager.synchronisationClasse(getApplication());
        UniteManager.synchronisationUnite(getApplication());
        LieuManager.synchronisationLieu(getApplication());
        LivraisonPromotionManager.synchronisationLivraisonPromotion(getApplication());
        LivraisonGratuiteManager.synchronisationLivraisonGratuite(getApplication());
        //StockPManager.synchronisationStockP(getApplication());
        //StockPLigneManager.synchronisationStockPLigne(getApplication());
        //StockTransfertManager.synchronisationStockTransfert(getApplication());
        StockManager.synchronisationStock(getApplication());
        //StockLigneManager.synchronisationStockLigne(getApplication());
        ParametreManager.synchronisationParametre(getApplication());
        CreditManager.synchronisationCredit(getApplication());
        CreditManager.synchronisationCreditPull(getApplication());
        VisibiliteManager.synchronisationVisibilite(getApplication());
        VisibiliteLigneManager.synchronisationVisibiliteLigne(getApplication());
        VisibiliteRayonManager.Companion.synchronisationVisibiliteRayon(getApplication());
        StockPManager.synchronisationStockP(getApplication());
        StockPLigneManager.synchronisationStockPLigne(getApplication());

        ChoufouniManager.synchronisationChoufouni(getApplication());
        ChoufouniContratManager.synchronisationChoufouniContrat(getApplication());
        ChoufouniContratImageManager.synchronisationChoufouniContratImage(getApplication());
        ChoufouniContratPullManager.synchronisationChoufouniContratPullContratPull(getApplication());

        LogsManager.synchronisationLogs(getApplication());

        //VisibiliteRayonManager.Companion.synchronisationVisibiliteRayon(getApplication());


    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "on cleared called");
    }

}
