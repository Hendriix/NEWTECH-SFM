package com.newtech.newtech_sfm.Metier;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
import com.newtech.newtech_sfm.Metier_Manager.CommandeAEncaisserManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeALivreeManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeGratuiteManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeLigneAEncaisserManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeLigneALivrerManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeNonClotureeLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeNonClotureeManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandePromotionManager;
import com.newtech.newtech_sfm.Metier_Manager.ConnectionManager;
import com.newtech.newtech_sfm.Metier_Manager.CreditManager;
import com.newtech.newtech_sfm.Metier_Manager.DistributeurManager;
import com.newtech.newtech_sfm.Metier_Manager.EncaissementManager;
import com.newtech.newtech_sfm.Metier_Manager.FamilleManager;
import com.newtech.newtech_sfm.Metier_Manager.GpstrackerManager;
import com.newtech.newtech_sfm.Metier_Manager.ImpressionManager;
import com.newtech.newtech_sfm.Metier_Manager.ImprimanteMan;
import com.newtech.newtech_sfm.Metier_Manager.JournalManager;
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
import com.newtech.newtech_sfm.Metier_Manager.StockLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.StockManager;
import com.newtech.newtech_sfm.Metier_Manager.StockPLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.StockPManager;
import com.newtech.newtech_sfm.Metier_Manager.StockTransfertManager;
import com.newtech.newtech_sfm.Metier_Manager.SupervisionManager;
import com.newtech.newtech_sfm.Metier_Manager.TacheActionManager;
import com.newtech.newtech_sfm.Metier_Manager.TacheManager;
import com.newtech.newtech_sfm.Metier_Manager.TachePlanificationManager;
import com.newtech.newtech_sfm.Metier_Manager.TourneeManager;
import com.newtech.newtech_sfm.Metier_Manager.TypeManager;
import com.newtech.newtech_sfm.Metier_Manager.UniteManager;
import com.newtech.newtech_sfm.Metier_Manager.UserManager;
import com.newtech.newtech_sfm.Metier_Manager.UtilisateurUniqueManager;
import com.newtech.newtech_sfm.Metier_Manager.VisibiliteLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.VisibiliteManager;
import com.newtech.newtech_sfm.Metier_Manager.VisibiliteRayonManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteResultatManager;

/**
 * Created by sferricha on 28/07/2016.
 */

public class MyDataBase extends SQLiteOpenHelper {


    public MyDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {

            db.execSQL(GpstrackerManager.CREATE_GPSTRACKER_TABLE);
            db.execSQL(LogSyncManager.CREATE_LOG_TABLE);
            db.execSQL(TourneeManager.CREATE_TOURNEE_TABLE);
            db.execSQL(ArticleManager.CREATE_ARTICLE_TABLE);
            db.execSQL(ClientManager.CREATE_CLIENT_TABLE);
            db.execSQL(ClientNManager.CREATE_CLIENTN_TABLE);
            db.execSQL(PromotionaffectationManager.CREATE_PROMOTION_AFF_TABLE);
            db.execSQL(PromotionarticleManager.CREATE_PROMOTIONARTICLE_TABLE);
            db.execSQL(PromotiongratuiteManager.CREATE_PROMOTIONGRATUITE_TABLE);
            db.execSQL(PromotionManager.CREATE_PROMOTION_TABLE);
            db.execSQL(PromotionpalierManager.CREATE_PROMOTIONPALIER_TABLE);
            db.execSQL(FamilleManager.CREATE_FAMILLE_TABLE);
            db.execSQL(CommandePromotionManager.CREATE_COMMANDE_TABLE);
            db.execSQL(CommandeManager.CREATE_COMMANDE_TABLE);
            db.execSQL(ArticlePrixManager.CREATE_ARTICLE_PRIX_TABLE);
            db.execSQL(CommandeLigneManager.CREATE_COMMANDE_LIGNE_TABLE);
            db.execSQL(CommandeGratuiteManager.CREATE_TABLE_COMMANDE_GRATUITE);
            db.execSQL(VisiteManager.CREATE_VISITE_TABLE);
            db.execSQL(VisiteResultatManager.CREATE_VISITERESULTAT_TABLE);
            db.execSQL(TacheManager.CREATE_TACHE_TABLE);
            db.execSQL(TacheActionManager.CREATE_TACHEACTION_TABLE);
            db.execSQL(TachePlanificationManager.CREATE_TACHEPLANIFICATION_TABLE);
            db.execSQL(LivraisonManager.CREATE_LIVRAISON_TABLE);
            db.execSQL(LivraisonLigneManager.CREATE_LIVRAISON_LIGNE_TABLE);
            db.execSQL(CommandeALivreeManager.CREATE_COMMANDEALIVRER_TABLE);
            db.execSQL(CommandeLigneALivrerManager.CREATE_COMMANDE_LIGNEALIVRER_TABLE);
            db.execSQL(ImprimanteMan.CREATE_IMPRIMANTE_TABLE);
            db.execSQL(ImpressionManager.CREATE_IMPRESSION_TABLE);
            db.execSQL(DistributeurManager.CREATE_DISTRIBUTEUR_TABLE);
            db.execSQL(EncaissementManager.CREATE_ENCAISSEMENT_TABLE);
            db.execSQL(CommandeAEncaisserManager.CREATE_COMMANDEAENCAISSER_TABLE);
            db.execSQL(CommandeLigneAEncaisserManager.CREATE_COMMANDE_LIGNEAENCAISSER_TABLE);
            db.execSQL(UtilisateurUniqueManager.CREATE_UTILISATEURUNIQUE_TABLE);
            db.execSQL(UserManager.CREATE_UTILISATEUR_TABLE);
            db.execSQL(CommandeNonClotureeManager.CREATE_COMMANDENONCLOTUREE_TABLE );
            db.execSQL(CommandeNonClotureeLigneManager.CREATE_COMMANDENONCLOTUREE_LIGNE_TABLE);
            db.execSQL(CommandeNonClotureeManager.CREATE_VIEW_COMMANDENONCLOTUREE_AL);
            db.execSQL(CommandeNonClotureeManager.CREATE_VIEW_COMMANDENONCLOTUREE_AE);
            db.execSQL(ListePrixManager.CREATE_LISTEPRIX_TABLE);
            db.execSQL(ListePrixLigneManager.CREATE_LISTEPRIXLIGNE_TABLE);
            db.execSQL(StockDemandeManager.CREATE_STOCKDEMANDE_TABLE);
            db.execSQL(StockDemandeLigneManager.CREATE_STOCKDEMANDE_LIGNE_TABLE);
            db.execSQL(LivraisonGratuiteManager.CREATE_TABLE_LIVRAISON_GRATUITE);
            db.execSQL(LivraisonPromotionManager.CREATE_TABLE_LIVRAISON_PROMOTION);
            db.execSQL(CategorieManager.CREATE_CATEGORIE_TABLE);
            db.execSQL(StatutManager.CREATE_STATUT_TABLE);
            db.execSQL(TypeManager.CREATE_TYPE_TABLE);
            db.execSQL(ClasseManager.CREATE_CLASSE_TABLE);
            db.execSQL(UniteManager.CREATE_UNITE_TABLE);
            db.execSQL(LieuManager.CREATE_LIEU_TABLE);
            db.execSQL(StockPManager.CREATE_STOCKP_TABLE);
            db.execSQL(StockPLigneManager.CREATE_STOCKP_LIGNE_TABLE);
            db.execSQL(StockTransfertManager.CREATE_STOCKTRANSFERT_TABLE);
            db.execSQL(StockManager.CREATE_STOCK_TABLE);
            db.execSQL(StockLigneManager.CREATE_STOCKLIGNE_TABLE);
            db.execSQL(ParametreManager.CREATE_PARAMETRE_TABLE);
            db.execSQL(JournalManager.CREATE_JOURNAL_TABLE);
            db.execSQL(ConnectionManager.CREATE_CONNECTION_TABLE);
            db.execSQL(SupervisionManager.CREATE_SUPERVISION_TABLE);
            db.execSQL(CreditManager.CREATE_CREDIT_TABLE);
            db.execSQL(VisibiliteManager.CREATE_VISIBILITE_TABLE);
            db.execSQL(VisibiliteLigneManager.CREATE_VISIBILITE_LIGNE_TABLE);
            db.execSQL(VisibiliteRayonManager.CREATE_VISIBILITE_RAYON_TABLE);

            db.execSQL(ChoufouniManager.CREATE_CHOUFOUNI_TABLE);
            db.execSQL(ChoufouniContratManager.CREATE_CHOUFOUNI_CONTRAT_TABLE);
            db.execSQL(ChoufouniContratImageManager.CREATE_CHOUFOUNI_IMAGE_TABLE);
            db.execSQL(ChoufouniContratPullManager.CREATE_CHOUFOUNI_CONTRAT_PULL_TABLE);
            db.execSQL(LogsManager.CREATE_LOG_TABLE);

            Log.d("database", "onCreate: "+GpstrackerManager.CREATE_GPSTRACKER_TABLE);
            Log.d("database", "onCreate: "+LogSyncManager.CREATE_LOG_TABLE);
            Log.d("database", "onCreate: "+TourneeManager.CREATE_TOURNEE_TABLE);
            Log.d("database", "onCreate: "+ArticleManager.CREATE_ARTICLE_TABLE);
            Log.d("database", "onCreate: "+ClientManager.CREATE_CLIENT_TABLE);
            Log.d("database", "onCreate: "+ClientNManager.CREATE_CLIENTN_TABLE);
            Log.d("database", "onCreate: "+PromotionaffectationManager.CREATE_PROMOTION_AFF_TABLE);
            Log.d("database", "onCreate: "+PromotionarticleManager.CREATE_PROMOTIONARTICLE_TABLE);
            Log.d("database", "onCreate: "+PromotiongratuiteManager.CREATE_PROMOTIONGRATUITE_TABLE);
            Log.d("database", "onCreate: "+PromotionManager.CREATE_PROMOTION_TABLE);
            Log.d("database", "onCreate: "+PromotionpalierManager.CREATE_PROMOTIONPALIER_TABLE);
            Log.d("database", "onCreate: "+FamilleManager.CREATE_FAMILLE_TABLE);
            Log.d("database", "onCreate: "+CommandePromotionManager.CREATE_COMMANDE_TABLE);
            Log.d("database", "onCreate: "+CommandeManager.CREATE_COMMANDE_TABLE);
            Log.d("database", "onCreate: "+ArticlePrixManager.CREATE_ARTICLE_PRIX_TABLE);
            Log.d("database", "onCreate: "+CommandeLigneManager.CREATE_COMMANDE_LIGNE_TABLE);
            Log.d("database", "onCreate: "+CommandeGratuiteManager.CREATE_TABLE_COMMANDE_GRATUITE);
            Log.d("database", "onCreate: "+VisiteManager.CREATE_VISITE_TABLE);
            Log.d("database", "onCreate: "+VisiteResultatManager.CREATE_VISITERESULTAT_TABLE);
            Log.d("database", "onCreate: "+TacheManager.CREATE_TACHE_TABLE);
            Log.d("database", "onCreate: "+TacheActionManager.CREATE_TACHEACTION_TABLE);
            Log.d("database", "onCreate: "+TachePlanificationManager.CREATE_TACHEPLANIFICATION_TABLE);
            Log.d("database", "onCreate: "+LivraisonManager.CREATE_LIVRAISON_TABLE);
            Log.d("database", "onCreate: "+LivraisonLigneManager.CREATE_LIVRAISON_LIGNE_TABLE);
            Log.d("database", "onCreate: "+CommandeALivreeManager.CREATE_COMMANDEALIVRER_TABLE);
            Log.d("database", "onCreate: "+CommandeLigneALivrerManager.CREATE_COMMANDE_LIGNEALIVRER_TABLE);
            Log.d("database", "onCreate: "+ImprimanteMan.CREATE_IMPRIMANTE_TABLE);
            Log.d("database", "onCreate: "+ImpressionManager.CREATE_IMPRESSION_TABLE);
            Log.d("database", "onCreate: "+DistributeurManager.CREATE_DISTRIBUTEUR_TABLE);
            Log.d("database", "onCreate: "+EncaissementManager.CREATE_ENCAISSEMENT_TABLE);
            Log.d("database", "onCreate: "+CommandeAEncaisserManager.CREATE_COMMANDEAENCAISSER_TABLE);
            Log.d("database", "onCreate: "+CommandeLigneAEncaisserManager.CREATE_COMMANDE_LIGNEAENCAISSER_TABLE);
            Log.d("database", "onCreate: "+UtilisateurUniqueManager.CREATE_UTILISATEURUNIQUE_TABLE);
            Log.d("database", "onCreate: "+UserManager.CREATE_UTILISATEUR_TABLE);
            Log.d("database", "onCreate: "+CommandeNonClotureeManager.CREATE_COMMANDENONCLOTUREE_TABLE );
            Log.d("database", "onCreate: "+CommandeNonClotureeLigneManager.CREATE_COMMANDENONCLOTUREE_LIGNE_TABLE);
            Log.d("database", "onCreate: "+CommandeNonClotureeManager.CREATE_VIEW_COMMANDENONCLOTUREE_AL);
            Log.d("database", "onCreate: "+CommandeNonClotureeManager.CREATE_VIEW_COMMANDENONCLOTUREE_AE);
            Log.d("database", "onCreate: "+ListePrixManager.CREATE_LISTEPRIX_TABLE);
            Log.d("database", "onCreate: "+ListePrixLigneManager.CREATE_LISTEPRIXLIGNE_TABLE);
            Log.d("database", "onCreate: "+StockDemandeManager.CREATE_STOCKDEMANDE_TABLE);
            Log.d("database", "onCreate: "+StockDemandeLigneManager.CREATE_STOCKDEMANDE_LIGNE_TABLE);
            Log.d("database", "onCreate: "+LivraisonGratuiteManager.CREATE_TABLE_LIVRAISON_GRATUITE);
            Log.d("database", "onCreate: "+LivraisonPromotionManager.CREATE_TABLE_LIVRAISON_PROMOTION);
            Log.d("database", "onCreate: "+CategorieManager.CREATE_CATEGORIE_TABLE);
            Log.d("database", "onCreate: "+StatutManager.CREATE_STATUT_TABLE);
            Log.d("database", "onCreate: "+TypeManager.CREATE_TYPE_TABLE);
            Log.d("database", "onCreate: "+ClasseManager.CREATE_CLASSE_TABLE);
            Log.d("database", "onCreate: "+UniteManager.CREATE_UNITE_TABLE);
            Log.d("database", "onCreate: "+LieuManager.CREATE_LIEU_TABLE);
            Log.d("database", "onCreate: "+StockPManager.CREATE_STOCKP_TABLE);
            Log.d("database", "onCreate: "+StockPLigneManager.CREATE_STOCKP_LIGNE_TABLE);
            Log.d("database", "onCreate: "+StockTransfertManager.CREATE_STOCKTRANSFERT_TABLE);
            Log.d("database", "onCreate: "+StockManager.CREATE_STOCK_TABLE);
            Log.d("database", "onCreate: "+StockLigneManager.CREATE_STOCKLIGNE_TABLE);
            Log.d("database", "onCreate: "+ParametreManager.CREATE_PARAMETRE_TABLE);
            Log.d("database", "onCreate: "+JournalManager.CREATE_JOURNAL_TABLE);
            Log.d("database", "onCreate: "+ConnectionManager.CREATE_CONNECTION_TABLE);
            Log.d("database", "onCreate: "+SupervisionManager.CREATE_SUPERVISION_TABLE);
            Log.d("database", "onCreate: "+CreditManager.CREATE_CREDIT_TABLE);
            Log.d("database", "onCreate: "+VisibiliteManager.CREATE_VISIBILITE_TABLE);
            Log.d("database", "onCreate: "+VisibiliteLigneManager.CREATE_VISIBILITE_LIGNE_TABLE);
            Log.d("database", "onCreate: "+VisibiliteRayonManager.CREATE_VISIBILITE_RAYON_TABLE);

            Log.d("database", "onCreate: "+ChoufouniManager.CREATE_CHOUFOUNI_TABLE);
            Log.d("database", "onCreate: "+ChoufouniContratManager.CREATE_CHOUFOUNI_CONTRAT_TABLE);
            Log.d("database", "onCreate: "+ChoufouniContratImageManager.CREATE_CHOUFOUNI_IMAGE_TABLE);
            Log.d("database", "onCreate: "+ChoufouniContratPullManager.CREATE_CHOUFOUNI_CONTRAT_PULL_TABLE);
            Log.d("database", "onCreate: "+LogsManager.CREATE_LOG_TABLE);

        } catch (SQLException e) {
            Log.d("database", e.toString());
        }
        Log.d("database", "Database tables created from mydatabase");
    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {

            db.execSQL("DROP TABLE IF EXISTS '"+GpstrackerManager.TABLE_GPSTRACKER+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+LogSyncManager.TABLE_LOG+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+TourneeManager.TABLE_TOURNEE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ArticleManager.TABLE_ARTICLE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ClientManager.TABLE_CLIENT+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ClientNManager.TABLE_CLIENTN+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+PromotionaffectationManager.TABLE_PROMOTIONAFFECTATION+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+PromotionarticleManager.TABLE_PROMOTIONARTICLE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+PromotiongratuiteManager.TABLE_PROMOTIONGRATUITE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+PromotionManager.TABLE_PROMOTION+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+PromotionpalierManager.TABLE_PROMOTIONPALIER+"' ;");
            db.execSQL("DROP TABLE IF EXISTS '"+FamilleManager.TABLE_FAMILLE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+CommandePromotionManager.TABLE_COMMANDE_PROMOTION+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+CommandeManager.TABLE_COMMANDE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ArticlePrixManager.TABLE_ARTICLE_PRIX+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+CommandeLigneManager.TABLE_COMMANDE_LIGNE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+CommandeGratuiteManager.TABLE_COMMANDE_GRATUITE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+VisiteManager.TABLE_VISITE+"' ;");
            db.execSQL("DROP TABLE IF EXISTS '"+VisiteResultatManager.TABLE_VISITERESULTAT+"' ;");
            db.execSQL("DROP TABLE IF EXISTS '"+TacheManager.TABLE_TACHE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+TacheActionManager.TABLE_TACHEACTION+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+TachePlanificationManager.TABLE_TACHEPLANIFICATION+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+LivraisonManager.TABLE_LIVRAISON+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+LivraisonLigneManager.TABLE_LIVRAISON_LIGNE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ CommandeALivreeManager.TABLE_COMMANDEALIVRER+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ CommandeLigneALivrerManager.TABLE_COMMANDE_LIGNEALIVRER+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ ImprimanteMan.TABLE_IMPRIMANTE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ ImpressionManager.TABLE_IMPRESSION+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ DistributeurManager.TABLE_DISTRIBUTEUR+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ EncaissementManager.TABLE_ENCAISSEMENT+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ CommandeAEncaisserManager.TABLE_COMMANDEAENCAISSER+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ CommandeLigneAEncaisserManager.TABLE_COMMANDE_LIGNEAENCAISSER+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ UtilisateurUniqueManager.TABLE_UTILISATEURUNIQUE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ UserManager.TABLE_UTILISATEUR+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ CommandeNonClotureeManager.TABLE_COMMANDENONCLOTUREE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ CommandeNonClotureeLigneManager.TABLE_COMMANDENONCLOTUREE_LIGNE+"'");
            db.execSQL("DROP VIEW '"+ CommandeNonClotureeManager.VIEW_COMMANDENONCLOTUREEAL+"'");
            db.execSQL("DROP VIEW '"+ CommandeNonClotureeManager.VIEW_COMMANDENONCLOTUREEAE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ ListePrixManager.TABLE_LISTEPRIX+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ ListePrixLigneManager.TABLE_LISTEPRIXLIGNE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ StockDemandeManager.TABLE_STOCKDEMANDE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ StockDemandeLigneManager.TABLE_STOCKDEMANDE_LIGNE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ LivraisonGratuiteManager.TABLE_LIVRAISON_GRATUITE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ LivraisonPromotionManager.TABLE_LIVRAISON_PROMOTION+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ CategorieManager.TABLE_CATEGORIE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ StatutManager.TABLE_STATUT+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ TypeManager.TABLE_TYPE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ ClasseManager.TABLE_CLASSE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ UniteManager.TABLE_UNITE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ LieuManager.TABLE_LIEU+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ StockPManager.TABLE_STOCKP+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ StockPLigneManager.TABLE_STOCKP_LIGNE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ StockTransfertManager.TABLE_STOCKTRANSFERT+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ StockManager.TABLE_STOCK+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ StockLigneManager.TABLE_STOCK_LIGNE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ ParametreManager.TABLE_PARAMETRE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ JournalManager.TABLE_JOURNAL+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ ConnectionManager.TABLE_CONNECTION+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ SupervisionManager.TABLE_SUPERVISION+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ CreditManager.TABLE_CREDIT+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ VisibiliteManager.TABLE_VISIBILITE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ VisibiliteLigneManager.TABLE_VISIBILITE_LIGNE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ VisibiliteRayonManager.TABLE_VISIBILITE_RAYON+"'");

            db.execSQL("DROP TABLE IF EXISTS '"+ ChoufouniManager.TABLE_CHOUFOUNI+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ ChoufouniContratManager.TABLE_CHOUFOUNI_CONTRAT+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ ChoufouniContratImageManager.TABLE_CHOUFOUNI_IMAGE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ ChoufouniContratPullManager.TABLE_CHOUFOUNI_CONTRAT_PULL+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ LogsManager.TABLE_LOG+"'");

            Log.d("database", "onUpdate: "+GpstrackerManager.CREATE_GPSTRACKER_TABLE);
            Log.d("database", "onUpdate: "+LogSyncManager.CREATE_LOG_TABLE);
            Log.d("database", "onUpdate: "+TourneeManager.CREATE_TOURNEE_TABLE);
            Log.d("database", "onUpdate: "+ArticleManager.CREATE_ARTICLE_TABLE);
            Log.d("database", "onUpdate: "+ClientManager.CREATE_CLIENT_TABLE);
            Log.d("database", "onUpdate: "+ClientNManager.CREATE_CLIENTN_TABLE);
            Log.d("database", "onUpdate: "+PromotionaffectationManager.CREATE_PROMOTION_AFF_TABLE);
            Log.d("database", "onUpdate: "+PromotionarticleManager.CREATE_PROMOTIONARTICLE_TABLE);
            Log.d("database", "onUpdate: "+PromotiongratuiteManager.CREATE_PROMOTIONGRATUITE_TABLE);
            Log.d("database", "onUpdate: "+PromotionManager.CREATE_PROMOTION_TABLE);
            Log.d("database", "onUpdate: "+PromotionpalierManager.CREATE_PROMOTIONPALIER_TABLE);
            Log.d("database", "onUpdate: "+FamilleManager.CREATE_FAMILLE_TABLE);
            Log.d("database", "onUpdate: "+CommandePromotionManager.CREATE_COMMANDE_TABLE);
            Log.d("database", "onUpdate: "+CommandeManager.CREATE_COMMANDE_TABLE);
            Log.d("database", "onUpdate: "+ArticlePrixManager.CREATE_ARTICLE_PRIX_TABLE);
            Log.d("database", "onUpdate: "+CommandeLigneManager.CREATE_COMMANDE_LIGNE_TABLE);
            Log.d("database", "onUpdate: "+CommandeGratuiteManager.CREATE_TABLE_COMMANDE_GRATUITE);
            Log.d("database", "onUpdate: "+VisiteManager.CREATE_VISITE_TABLE);
            Log.d("database", "onUpdate: "+VisiteResultatManager.CREATE_VISITERESULTAT_TABLE);
            Log.d("database", "onUpdate: "+TacheManager.CREATE_TACHE_TABLE);
            Log.d("database", "onUpdate: "+TacheActionManager.CREATE_TACHEACTION_TABLE);
            Log.d("database", "onUpdate: "+TachePlanificationManager.CREATE_TACHEPLANIFICATION_TABLE);
            Log.d("database", "onUpdate: "+LivraisonManager.CREATE_LIVRAISON_TABLE);
            Log.d("database", "onUpdate: "+LivraisonLigneManager.CREATE_LIVRAISON_LIGNE_TABLE);
            Log.d("database", "onUpdate: "+CommandeALivreeManager.CREATE_COMMANDEALIVRER_TABLE);
            Log.d("database", "onUpdate: "+CommandeLigneALivrerManager.CREATE_COMMANDE_LIGNEALIVRER_TABLE);
            Log.d("database", "onUpdate: "+ImprimanteMan.CREATE_IMPRIMANTE_TABLE);
            Log.d("database", "onUpdate: "+ImpressionManager.CREATE_IMPRESSION_TABLE);
            Log.d("database", "onUpdate: "+DistributeurManager.CREATE_DISTRIBUTEUR_TABLE);
            Log.d("database", "onUpdate: "+EncaissementManager.CREATE_ENCAISSEMENT_TABLE);
            Log.d("database", "onUpdate: "+CommandeAEncaisserManager.CREATE_COMMANDEAENCAISSER_TABLE);
            Log.d("database", "onUpdate: "+CommandeLigneAEncaisserManager.CREATE_COMMANDE_LIGNEAENCAISSER_TABLE);
            Log.d("database", "onUpdate: "+UtilisateurUniqueManager.CREATE_UTILISATEURUNIQUE_TABLE);
            Log.d("database", "onUpdate: "+UserManager.CREATE_UTILISATEUR_TABLE);
            Log.d("database", "onUpdate: "+CommandeNonClotureeManager.CREATE_COMMANDENONCLOTUREE_TABLE );
            Log.d("database", "onUpdate: "+CommandeNonClotureeLigneManager.CREATE_COMMANDENONCLOTUREE_LIGNE_TABLE);
            Log.d("database", "onUpdate: "+CommandeNonClotureeManager.CREATE_VIEW_COMMANDENONCLOTUREE_AL);
            Log.d("database", "onUpdate: "+CommandeNonClotureeManager.CREATE_VIEW_COMMANDENONCLOTUREE_AE);
            Log.d("database", "onUpdate: "+ListePrixManager.CREATE_LISTEPRIX_TABLE);
            Log.d("database", "onUpdate: "+ListePrixLigneManager.CREATE_LISTEPRIXLIGNE_TABLE);
            Log.d("database", "onUpdate: "+StockDemandeManager.CREATE_STOCKDEMANDE_TABLE);
            Log.d("database", "onUpdate: "+StockDemandeLigneManager.CREATE_STOCKDEMANDE_LIGNE_TABLE);
            Log.d("database", "onUpdate: "+LivraisonGratuiteManager.CREATE_TABLE_LIVRAISON_GRATUITE);
            Log.d("database", "onUpdate: "+LivraisonPromotionManager.CREATE_TABLE_LIVRAISON_PROMOTION);
            Log.d("database", "onUpdate: "+CategorieManager.CREATE_CATEGORIE_TABLE);
            Log.d("database", "onUpdate: "+StatutManager.CREATE_STATUT_TABLE);
            Log.d("database", "onUpdate: "+TypeManager.CREATE_TYPE_TABLE);
            Log.d("database", "onUpdate: "+ClasseManager.CREATE_CLASSE_TABLE);
            Log.d("database", "onUpdate: "+UniteManager.CREATE_UNITE_TABLE);
            Log.d("database", "onUpdate: "+LieuManager.CREATE_LIEU_TABLE);
            Log.d("database", "onUpdate: "+StockPManager.CREATE_STOCKP_TABLE);
            Log.d("database", "onUpdate: "+StockPLigneManager.CREATE_STOCKP_LIGNE_TABLE);
            Log.d("database", "onUpdate: "+StockTransfertManager.CREATE_STOCKTRANSFERT_TABLE);
            Log.d("database", "onUpdate: "+StockManager.CREATE_STOCK_TABLE);
            Log.d("database", "onUpdate: "+StockLigneManager.CREATE_STOCKLIGNE_TABLE);
            Log.d("database", "onUpdate: "+ParametreManager.CREATE_PARAMETRE_TABLE);
            Log.d("database", "onUpdate: "+JournalManager.CREATE_JOURNAL_TABLE);
            Log.d("database", "onUpdate: "+ConnectionManager.CREATE_CONNECTION_TABLE);
            Log.d("database", "onUpdate: "+SupervisionManager.CREATE_SUPERVISION_TABLE);
            Log.d("database", "onUpdate: "+CreditManager.CREATE_CREDIT_TABLE);
            Log.d("database", "onUpdate: "+VisibiliteManager.CREATE_VISIBILITE_TABLE);
            Log.d("database", "onUpdate: "+VisibiliteLigneManager.CREATE_VISIBILITE_LIGNE_TABLE);
            Log.d("database", "onUpdate: "+VisibiliteRayonManager.CREATE_VISIBILITE_RAYON_TABLE);

            Log.d("database", "onUpdate: "+ ChoufouniManager.CREATE_CHOUFOUNI_TABLE+"'");
            Log.d("database", "onUpdate: "+ ChoufouniContratManager.CREATE_CHOUFOUNI_CONTRAT_TABLE+"'");
            Log.d("database", "onUpdate: "+ ChoufouniContratImageManager.CREATE_CHOUFOUNI_IMAGE_TABLE+"'");
            Log.d("database", "onUpdate: "+ ChoufouniContratPullManager.CREATE_CHOUFOUNI_CONTRAT_PULL_TABLE+"'");
            Log.d("database", "onUpdate: "+ LogsManager.CREATE_LOG_TABLE+"'");

            this.onCreate(db);

            Log.d("database", "Database tables upgraded on mydatabase");
        } catch (SQLException e) {
            Log.d("database", e.toString());
        }


    }

    public void onClear(SQLiteDatabase db) {

        try {

            db.execSQL("DROP TABLE IF EXISTS '"+GpstrackerManager.TABLE_GPSTRACKER+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+LogSyncManager.TABLE_LOG+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+TourneeManager.TABLE_TOURNEE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ArticleManager.TABLE_ARTICLE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ClientManager.TABLE_CLIENT+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ClientNManager.TABLE_CLIENTN+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+PromotionaffectationManager.TABLE_PROMOTIONAFFECTATION+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+PromotionarticleManager.TABLE_PROMOTIONARTICLE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+PromotiongratuiteManager.TABLE_PROMOTIONGRATUITE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+PromotionManager.TABLE_PROMOTION+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+PromotionpalierManager.TABLE_PROMOTIONPALIER+"' ;");
            db.execSQL("DROP TABLE IF EXISTS '"+FamilleManager.TABLE_FAMILLE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+CommandePromotionManager.TABLE_COMMANDE_PROMOTION+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+CommandeManager.TABLE_COMMANDE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ArticlePrixManager.TABLE_ARTICLE_PRIX+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+CommandeLigneManager.TABLE_COMMANDE_LIGNE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+CommandeGratuiteManager.TABLE_COMMANDE_GRATUITE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+VisiteManager.TABLE_VISITE+"' ;");
            db.execSQL("DROP TABLE IF EXISTS '"+VisiteResultatManager.TABLE_VISITERESULTAT+"' ;");
            db.execSQL("DROP TABLE IF EXISTS '"+TacheManager.TABLE_TACHE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+TacheActionManager.TABLE_TACHEACTION+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+TachePlanificationManager.TABLE_TACHEPLANIFICATION+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+LivraisonManager.TABLE_LIVRAISON+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+LivraisonLigneManager.TABLE_LIVRAISON_LIGNE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ CommandeALivreeManager.TABLE_COMMANDEALIVRER+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ CommandeLigneALivrerManager.TABLE_COMMANDE_LIGNEALIVRER+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ ImprimanteMan.TABLE_IMPRIMANTE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ ImpressionManager.TABLE_IMPRESSION+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ DistributeurManager.TABLE_DISTRIBUTEUR+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ EncaissementManager.TABLE_ENCAISSEMENT+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ CommandeAEncaisserManager.TABLE_COMMANDEAENCAISSER+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ CommandeLigneAEncaisserManager.TABLE_COMMANDE_LIGNEAENCAISSER+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ UtilisateurUniqueManager.TABLE_UTILISATEURUNIQUE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ UserManager.TABLE_UTILISATEUR+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ CommandeNonClotureeManager.TABLE_COMMANDENONCLOTUREE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ CommandeNonClotureeLigneManager.TABLE_COMMANDENONCLOTUREE_LIGNE+"'");
            db.execSQL("DROP VIEW '"+ CommandeNonClotureeManager.VIEW_COMMANDENONCLOTUREEAL+"'");
            db.execSQL("DROP VIEW '"+ CommandeNonClotureeManager.VIEW_COMMANDENONCLOTUREEAE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ ListePrixManager.TABLE_LISTEPRIX+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ ListePrixLigneManager.TABLE_LISTEPRIXLIGNE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ StockDemandeManager.TABLE_STOCKDEMANDE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ StockDemandeLigneManager.TABLE_STOCKDEMANDE_LIGNE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ LivraisonGratuiteManager.TABLE_LIVRAISON_GRATUITE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ LivraisonPromotionManager.TABLE_LIVRAISON_PROMOTION+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ CategorieManager.TABLE_CATEGORIE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ StatutManager.TABLE_STATUT+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ TypeManager.TABLE_TYPE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ ClasseManager.TABLE_CLASSE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ UniteManager.TABLE_UNITE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ LieuManager.TABLE_LIEU+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ StockPManager.TABLE_STOCKP+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ StockPLigneManager.TABLE_STOCKP_LIGNE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ StockTransfertManager.TABLE_STOCKTRANSFERT+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ StockManager.TABLE_STOCK+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ StockLigneManager.TABLE_STOCK_LIGNE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ ParametreManager.TABLE_PARAMETRE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ JournalManager.TABLE_JOURNAL+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ ConnectionManager.TABLE_CONNECTION+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ SupervisionManager.TABLE_SUPERVISION+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ CreditManager.TABLE_CREDIT+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ VisibiliteManager.TABLE_VISIBILITE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ VisibiliteLigneManager.TABLE_VISIBILITE_LIGNE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ VisibiliteRayonManager.TABLE_VISIBILITE_RAYON+"'");

            db.execSQL("DROP TABLE IF EXISTS '"+ ChoufouniManager.TABLE_CHOUFOUNI+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ ChoufouniContratManager.TABLE_CHOUFOUNI_CONTRAT+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ ChoufouniContratImageManager.TABLE_CHOUFOUNI_IMAGE+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ ChoufouniContratPullManager.TABLE_CHOUFOUNI_CONTRAT_PULL+"'");
            db.execSQL("DROP TABLE IF EXISTS '"+ LogsManager.TABLE_LOG+"'");

            Log.d("database", "onClear: "+GpstrackerManager.CREATE_GPSTRACKER_TABLE);
            Log.d("database", "onClear: "+LogSyncManager.CREATE_LOG_TABLE);
            Log.d("database", "onClear: "+TourneeManager.CREATE_TOURNEE_TABLE);
            Log.d("database", "onClear: "+ArticleManager.CREATE_ARTICLE_TABLE);
            Log.d("database", "onClear: "+ClientManager.CREATE_CLIENT_TABLE);
            Log.d("database", "onClear: "+ClientNManager.CREATE_CLIENTN_TABLE);
            Log.d("database", "onClear: "+PromotionaffectationManager.CREATE_PROMOTION_AFF_TABLE);
            Log.d("database", "onClear: "+PromotionarticleManager.CREATE_PROMOTIONARTICLE_TABLE);
            Log.d("database", "onClear: "+PromotiongratuiteManager.CREATE_PROMOTIONGRATUITE_TABLE);
            Log.d("database", "onClear: "+PromotionManager.CREATE_PROMOTION_TABLE);
            Log.d("database", "onClear: "+PromotionpalierManager.CREATE_PROMOTIONPALIER_TABLE);
            Log.d("database", "onClear: "+FamilleManager.CREATE_FAMILLE_TABLE);
            Log.d("database", "onClear: "+CommandePromotionManager.CREATE_COMMANDE_TABLE);
            Log.d("database", "onClear: "+CommandeManager.CREATE_COMMANDE_TABLE);
            Log.d("database", "onClear: "+ArticlePrixManager.CREATE_ARTICLE_PRIX_TABLE);
            Log.d("database", "onClear: "+CommandeLigneManager.CREATE_COMMANDE_LIGNE_TABLE);
            Log.d("database", "onClear: "+CommandeGratuiteManager.CREATE_TABLE_COMMANDE_GRATUITE);
            Log.d("database", "onClear: "+VisiteManager.CREATE_VISITE_TABLE);
            Log.d("database", "onClear: "+VisiteResultatManager.CREATE_VISITERESULTAT_TABLE);
            Log.d("database", "onClear: "+TacheManager.CREATE_TACHE_TABLE);
            Log.d("database", "onClear: "+TacheActionManager.CREATE_TACHEACTION_TABLE);
            Log.d("database", "onClear: "+TachePlanificationManager.CREATE_TACHEPLANIFICATION_TABLE);
            Log.d("database", "onClear: "+LivraisonManager.CREATE_LIVRAISON_TABLE);
            Log.d("database", "onClear: "+LivraisonLigneManager.CREATE_LIVRAISON_LIGNE_TABLE);
            Log.d("database", "onClear: "+CommandeALivreeManager.CREATE_COMMANDEALIVRER_TABLE);
            Log.d("database", "onClear: "+CommandeLigneALivrerManager.CREATE_COMMANDE_LIGNEALIVRER_TABLE);
            Log.d("database", "onClear: "+ImprimanteMan.CREATE_IMPRIMANTE_TABLE);
            Log.d("database", "onClear: "+ImpressionManager.CREATE_IMPRESSION_TABLE);
            Log.d("database", "onClear: "+DistributeurManager.CREATE_DISTRIBUTEUR_TABLE);
            Log.d("database", "onClear: "+EncaissementManager.CREATE_ENCAISSEMENT_TABLE);
            Log.d("database", "onClear: "+CommandeAEncaisserManager.CREATE_COMMANDEAENCAISSER_TABLE);
            Log.d("database", "onClear: "+CommandeLigneAEncaisserManager.CREATE_COMMANDE_LIGNEAENCAISSER_TABLE);
            Log.d("database", "onClear: "+UtilisateurUniqueManager.CREATE_UTILISATEURUNIQUE_TABLE);
            Log.d("database", "onClear: "+UserManager.CREATE_UTILISATEUR_TABLE);
            Log.d("database", "onClear: "+CommandeNonClotureeManager.CREATE_COMMANDENONCLOTUREE_TABLE );
            Log.d("database", "onClear: "+CommandeNonClotureeLigneManager.CREATE_COMMANDENONCLOTUREE_LIGNE_TABLE);
            Log.d("database", "onClear: "+CommandeNonClotureeManager.CREATE_VIEW_COMMANDENONCLOTUREE_AL);
            Log.d("database", "onClear: "+CommandeNonClotureeManager.CREATE_VIEW_COMMANDENONCLOTUREE_AE);
            Log.d("database", "onClear: "+ListePrixManager.CREATE_LISTEPRIX_TABLE);
            Log.d("database", "onClear: "+ListePrixLigneManager.CREATE_LISTEPRIXLIGNE_TABLE);
            Log.d("database", "onClear: "+StockDemandeManager.CREATE_STOCKDEMANDE_TABLE);
            Log.d("database", "onClear: "+StockDemandeLigneManager.CREATE_STOCKDEMANDE_LIGNE_TABLE);
            Log.d("database", "onClear: "+LivraisonGratuiteManager.CREATE_TABLE_LIVRAISON_GRATUITE);
            Log.d("database", "onClear: "+LivraisonPromotionManager.CREATE_TABLE_LIVRAISON_PROMOTION);
            Log.d("database", "onClear: "+CategorieManager.CREATE_CATEGORIE_TABLE);
            Log.d("database", "onClear: "+StatutManager.CREATE_STATUT_TABLE);
            Log.d("database", "onClear: "+TypeManager.CREATE_TYPE_TABLE);
            Log.d("database", "onClear: "+ClasseManager.CREATE_CLASSE_TABLE);
            Log.d("database", "onClear: "+UniteManager.CREATE_UNITE_TABLE);
            Log.d("database", "onClear: "+LieuManager.CREATE_LIEU_TABLE);
            Log.d("database", "onClear: "+StockPManager.CREATE_STOCKP_TABLE);
            Log.d("database", "onClear: "+StockPLigneManager.CREATE_STOCKP_LIGNE_TABLE);
            Log.d("database", "onClear: "+StockTransfertManager.CREATE_STOCKTRANSFERT_TABLE);
            Log.d("database", "onClear: "+StockManager.CREATE_STOCK_TABLE);
            Log.d("database", "onClear: "+StockLigneManager.CREATE_STOCKLIGNE_TABLE);
            Log.d("database", "onClear: "+ParametreManager.CREATE_PARAMETRE_TABLE);
            Log.d("database", "onClear: "+JournalManager.CREATE_JOURNAL_TABLE);
            Log.d("database", "onClear: "+ConnectionManager.CREATE_CONNECTION_TABLE);
            Log.d("database", "onClear: "+SupervisionManager.CREATE_SUPERVISION_TABLE);
            Log.d("database", "onClear: "+CreditManager.CREATE_CREDIT_TABLE);
            Log.d("database", "onClear: "+VisibiliteManager.CREATE_VISIBILITE_TABLE);
            Log.d("database", "onClear: "+VisibiliteLigneManager.CREATE_VISIBILITE_LIGNE_TABLE);
            Log.d("database", "onClear: "+VisibiliteRayonManager.CREATE_VISIBILITE_RAYON_TABLE);

            Log.d("database", "onClear: "+ ChoufouniManager.CREATE_CHOUFOUNI_TABLE+"'");
            Log.d("database", "onClear: "+ ChoufouniContratManager.CREATE_CHOUFOUNI_CONTRAT_TABLE+"'");
            Log.d("database", "onClear: "+ ChoufouniContratImageManager.CREATE_CHOUFOUNI_IMAGE_TABLE+"'");
            Log.d("database", "onClear: "+ ChoufouniContratPullManager.CREATE_CHOUFOUNI_CONTRAT_PULL_TABLE+"'");
            Log.d("database", "onClear: "+ LogsManager.CREATE_LOG_TABLE+"'");

            this.onCreate(db);

            Log.d("database", "Database tables upgraded on mydatabase");
        } catch (SQLException e) {
            Log.d("database", e.toString());
        }


    }


}
