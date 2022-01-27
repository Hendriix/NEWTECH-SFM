package com.newtech.newtech_sfm.Configuration;

/**
 * Created by Mehdi on 22/06/2016.
 **/
public class AppConfig {


    //l'adresse des dans le serveur http://webapp.savola.com/OctopusMobile/WS/
    //private static String URL_SERVER= "https://webapp.savola.com/octopusnew/MB/WS/";

    private static String URL_SERVER = "https://77.95.216.47/octopusnew/MB/WS/";

    public static String URL_LOGIN_TEST = URL_SERVER + "login-exec_test.php";
    public static String URL_SYNC_UTILISATEURUNIQUE = URL_SERVER + "Sync_UtilisateurUnique.php";
    public static String URL_LOGIN = URL_SERVER + "login-exec.php";

    public static String URL_ARTICLE = URL_SERVER + "Get_Article.php?TD=Articles";
    public static String URL_SYNC_ARTICLE = URL_SERVER + "Sync_Article_test.php";
    public static String URL_SYNC_ARTICLE_PRIX = URL_SERVER + "sync_Articleprix.php";

    public static String URL_SYNC_CLIENT = URL_SERVER + "Sync_Client.php";
    public static String URL_SYNC_CLIENTN = URL_SERVER + "Sync_ClientN.php";
    public static String URL_SYNC_TOURNEE = URL_SERVER + "Sync_Tournee.php";

    public static String URL_SYNC_PROMOTION = URL_SERVER + "Sync_Promotion.php";
    public static String URL_SYNC_PROMOTION_AFFECTATION = URL_SERVER + "Sync_Promotionaffectation.php";
    public static String URL_SYNC_PROMOTION_GRATUITE = URL_SERVER + "Sync_Promotiongratuite.php";
    public static String URL_SYNC_PROMOTION_PALIER = URL_SERVER + "Sync_Promotionpalier.php";
    public static String URL_SYNC_PROMOTION_ARTICLE = URL_SERVER + "Sync_Promotionarticle.php";

    public static String URL_SYNC_COMMANDE = URL_SERVER + "Sync_Commande.php";
    public static String URL_SYNC_COMMANDE_LIGNE = URL_SERVER + "Sync_Commandeligne.php";

    public static String URL_SYNC_LIVRAISON = URL_SERVER + "Sync_Livraison.php";
    public static String URL_SYNC_LIVRAISON_LIGNE = URL_SERVER + "Sync_Livraisonligne.php";

    public static String URL_SYNC_FAMILLE = URL_SERVER + "sync_Famille.php";
    public static String URL_SYNC_VISITE = URL_SERVER + "Sync_Visite.php";
    public static String URL_SYNC_VISITERESULTAT = URL_SERVER + "Sync_VisiteResultat.php";

    public static String URL_SYNC_TACHE = URL_SERVER + "Sync_Tache.php";
    public static String URL_SYNC_TACHEPLANIFICATION = URL_SERVER + "Sync_TachePlanification.php";
    public static String URL_SYNC_TACHEACTION = URL_SERVER + "Sync_TacheAction.php";

    public static String URL_VIEW_OBJECTIF = URL_SERVER + "View_ObjectifByUtilisateur.php";
    public static String URL_VIEW_VENTE = URL_SERVER + "View_ObjectifByMoisUtilisateur.php";
    public static String URL_VIEW_VENTE_ARTICLE = URL_SERVER + "View_Vente_Article.php";

    public static String URL_SYNC_COMMANDE_PROMOTION = URL_SERVER + "Sync_Commandepromotion.php";
    public static String URL_SYNC_COMMANDE_GRATUITE = URL_SERVER + "Sync_Commandegratuite.php";

    public static String URL_SYNC_LIVRAISON_PROMOTION = URL_SERVER + "Sync_Livraisonpromotion.php";
    public static String URL_SYNC_LIVRAISON_GRATUITE = URL_SERVER + "Sync_Livraisongratuite.php";

    public static String URL_SYNC_COMMANDEALIVRER = URL_SERVER + "Sync_CommandeALivrer.php";
    public static String URL_SYNC_COMMANDE_LIGNEALIVRER = URL_SERVER + "Sync_CommandeLigneALivrer.php";

    public static String URL_SYNC_COMMANDENONCLOTUREE = URL_SERVER + "Sync_CommandeNonCloturee.php";
    public static String URL_SYNC_COMMANDENONCLOTUREE_LIGNE = URL_SERVER + "Sync_CommandeNonClotureeLigne.php";
    public static String URL_SYNC_COMMANDEAANNULER = URL_SERVER + "GetCommandeAAnnuler.php";
    //public static String URL_SYNC_COMMANDENONCLOTUREE_LIGNE = URL_SERVER+"Sync_CommandeNonClotureeLigne.php";


    public static String URL_SYNC_GPSTRACKER = URL_SERVER + "sync_Gpstracker.php";
    public static String URL_SYNC_DISTRIBUTEUR = URL_SERVER + "Sync_Distributeur.php";
    public static String URL_SYNC_ENCAISSEMENT = URL_SERVER + "Sync_Encaissement.php";

    public static String URL_SYNC_COMMANDEAENCAISSER = URL_SERVER + "Sync_CommandeAEncaisser.php";
    public static String URL_SYNC_COMMANDE_LIGNEAENCAISSER = URL_SERVER + "Sync_CommandeLigneAEncaisser.php";

    public static String URL_SYNC_LIVRAISON_PULL = URL_SERVER + "Sync_LivraisonPull.php";
    public static String URL_SYNC_LIVRAISON_LIGNE_PULL = URL_SERVER + "Sync_LivraisonLignePull.php";

    public static String URL_SYNC_ENCAISSEMENT_PULL = URL_SERVER + "Sync_EncaissementPull.php";

    public static String URL_SYNC_LISTEPRIX = URL_SERVER + "Sync_ListePrix.php";
    public static String URL_SYNC_LISTEPRIXLIGNE = URL_SERVER + "Sync_ListePrixLigne.php";

    public static String URL_SYNC_STOCKDEMANDE = URL_SERVER + "Sync_StockDemande.php";
    public static String URL_SYNC_STOCKDEMANDE_RECEPTIONNEE = URL_SERVER + "Sync_StockDemandeReceptionnee.php";
    public static String URL_SYNC_STOCKDEMANDE_LIGNE = URL_SERVER + "Sync_StockDemandeLigne.php";
    public static String URL_SYNC_STOCKDEMANDE_LIGNE_RECEPTIONNEE = URL_SERVER + "Sync_StockDemandeLigneReceptionnee.php";

    public static String URL_SYNC_CATEGORIE = URL_SERVER + "Sync_Categorie.php";
    public static String URL_SYNC_STATUT = URL_SERVER + "Sync_Statut.php";
    public static String URL_SYNC_TYPE = URL_SERVER + "Sync_Type.php";
    public static String URL_SYNC_CLASSE = URL_SERVER + "Sync_Classe.php";

    public static String URL_SYNC_UNITE = URL_SERVER + "Sync_Unite.php";
    public static String URL_SYNC_LIEU = URL_SERVER + "Sync_Lieu.php";

    public static String URL_SYNC_STOCKDEMANDE_PULL = URL_SERVER + "Sync_StockDemandePull.php";
    public static String URL_SYNC_STOCKDEMANDE_LIGNE_PULL = URL_SERVER + "Sync_StockDemandeLignePull.php";

    public static String URL_SYNC_STOCKP = URL_SERVER + "Sync_StockP_Push.php";
    public static String URL_SYNC_STOCKP_LIGNE = URL_SERVER + "Sync_StockPLigne_Push.php";

    public static String URL_SYNC_STOCKTRANSFERT = URL_SERVER + "Sync_StockTransfertUnite.php";

    public static String URL_SYNC_STOCK = URL_SERVER + "Sync_Stock.php";
    public static String URL_SYNC_STOCK_LIGNE = URL_SERVER + "Sync_StockLigne.php";

    public static String URL_SYNC_PARAMETRE = URL_SERVER + "Sync_Parametre.php";

    public static String URL_SYNC_CREDIT = URL_SERVER + "Sync_Credit.php";
    public static String URL_SYNC_CREDIT_PULL = URL_SERVER + "Sync_CreditPull.php";


    public static String URL_SYNC_VISIBILITE = URL_SERVER + "Sync_Visibilite.php";
    public static String URL_SYNC_VISIBILITE_LIGNE = URL_SERVER + "Sync_VisibiliteLigne.php";

    public static String URL_SYNC_CHOUFOUNI = URL_SERVER + "Sync_Choufouni.php";
    public static String URL_SYNC_CHOUFOUNI_CONTRAT = URL_SERVER + "Sync_ChoufouniContrat.php";
    public static String URL_SYNC_CHOUFOUNI_CONTRAT_IMAGE = URL_SERVER + "Sync_ChoufouniContratImage.php";
    public static String URL_SYNC_CHOUFOUNI_CONTRAT_PULL = URL_SERVER + "Sync_ChoufouniContratPull.php";

    public static String URL_SYNC_TABLEAU_BORD_CLIENT = URL_SERVER + "View_TBClient.php";

    public static String URL_GET_CONTRATCHOUFOUNI = URL_SERVER + "GetContratChoufouni.php";

    public static String URL_CANCEL_COMMANDE = URL_SERVER + "Cancel_Order.php";

    public static String URL_RAPPORT_QUALITATIF = URL_SERVER + "getDisciplineByUtilisateurCode.php";

    public static String URL_RAPPORT_FONDAMENTAL = URL_SERVER + "getFondamentalByUtilisateurCode.php";

    public static String URL_SYNC_CIRCUIT = URL_SERVER + "Get_Circuit.php";
    public static String URL_SYNC_DISTRIBUTEUR_BY_UC = URL_SERVER + "Get_Distributeur_ByUC.php";
    public static String URL_SYNC_UTILISATEUR_BY_DCCC = URL_SERVER + "Get_Utilisateur_ByDCC.php";
    public static String URL_SYNC_TOURNEE_BY_UC = URL_SERVER + "Get_Tournee_ByUC.php";
    public static String URL_SYNC_CLIENT_RECENSEMENT = URL_SERVER + "Get_Client.php";
    public static String URL_SYNC_CLIENT_BY_QR = URL_SERVER + "Get_Client_ByQr.php";

    public static String URL_REGISTER_DEVICE = URL_SERVER + "RegisterDevice.php";
    public static String URL_SYNC_LOGS = URL_SERVER + "Sync_Logs.php";

    public static String URL_SYNC_VISIBILITE_RAYON = URL_SERVER + "Sync_VisibiliteRayon.php";
    public static String URL_GET_RAPPORT_CHOUFOUNI = URL_SERVER + "Rapport_Choufouni.php";
    public static String URL_SYNC_QUESTIONNAIRE = URL_SERVER + "getListQuestionnaire.php";

    public static final String DATABASE_NAME = "db_sfm";
    public static final int DATABASE_VERSION = 20;

}
