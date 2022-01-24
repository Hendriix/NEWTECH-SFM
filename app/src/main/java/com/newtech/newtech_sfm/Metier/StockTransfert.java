package com.newtech.newtech_sfm.Metier;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Metier_Manager.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

public class StockTransfert {

    private String STOCKTRANSFERT_CODE;
    private String STOCKTRANSFERT_DATE;
    private String ARTICLE_CODE;
    private String UNITE_CODE;
    private int QTE;
    private String STOCK;
    private String STOCK_SOURCE;
    private String TYPE_CODE;
    private String STATUT_CODE;
    private String CATEGORIE_CODE;
    private String SOURCE;
    private String DATE_CREATION;
    private String CREATEUR_CODE;
    private String SOURCE_CODE;
    private String COMMENTAIRE;
    private String VERSION;

    public StockTransfert(String STOCKTRANSFERT_CODE, String STOCKTRANSFERT_DATE, String ARTICLE_CODE, String UNITE_CODE, int QTE, String STOCK, String STOCK_SOURCE, String TYPE_CODE, String STATUT_CODE, String CATEGORIE_CODE, String SOURCE, String DATE_CREATION, String CREATEUR_CODE, String SOURCE_CODE, String COMMENTAIRE, String VERSION) {
        this.STOCKTRANSFERT_CODE = STOCKTRANSFERT_CODE;
        this.STOCKTRANSFERT_DATE = STOCKTRANSFERT_DATE;
        this.ARTICLE_CODE = ARTICLE_CODE;
        this.UNITE_CODE = UNITE_CODE;
        this.QTE = QTE;
        this.STOCK = STOCK;
        this.STOCK_SOURCE = STOCK_SOURCE;
        this.TYPE_CODE = TYPE_CODE;
        this.STATUT_CODE = STATUT_CODE;
        this.CATEGORIE_CODE = CATEGORIE_CODE;
        this.SOURCE = SOURCE;
        this.DATE_CREATION = DATE_CREATION;
        this.CREATEUR_CODE = CREATEUR_CODE;
        this.SOURCE_CODE = SOURCE_CODE;
        this.COMMENTAIRE = COMMENTAIRE;
        this.VERSION = VERSION;
    }

    public StockTransfert(StockDemande stockDemande, StockDemandeLigne stockDemandeLigne, Context context) throws JSONException {

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);

        UserManager userManager = new UserManager(context);
        User utilisateur = userManager.get(user.getString("UTILISATEUR_CODE"));

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String stransfert_date =df.format(new java.util.Date());


        this.STOCKTRANSFERT_CODE = stockDemandeLigne.getDEMANDE_CODE()+stockDemandeLigne.getDEMANDELIGNE_CODE();
        this.STOCKTRANSFERT_DATE = stransfert_date;
        this.ARTICLE_CODE = stockDemandeLigne.getARTICLE_CODE();
        this.UNITE_CODE = stockDemandeLigne.getUNITE_CODE();
        this.QTE = stockDemandeLigne.getQTE_RECEPTIONEE();
        this.STOCK = utilisateur.getSTOCK_CODE();
        this.STOCK_SOURCE = utilisateur.getSTOCKSUP_CODE();
        this.TYPE_CODE = "Vente";
        this.STATUT_CODE = "18";
        this.CATEGORIE_CODE = "DEFAULT";
        this.SOURCE = stockDemande.getTYPE_CODE();
        this.DATE_CREATION = stransfert_date;
        this.CREATEUR_CODE = utilisateur.getUTILISATEUR_CODE();
        this.SOURCE_CODE = stockDemandeLigne.getDEMANDE_CODE();
        this.COMMENTAIRE = "to_insert";
        this.VERSION = stockDemandeLigne.getVERSION();

    }

    public StockTransfert(Commande commande, CommandeLigne commandeLigne, Context context) throws JSONException {

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);

        UserManager userManager = new UserManager(context);
        User utilisateur = userManager.get(user.getString("UTILISATEUR_CODE"));

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String stransfert_date =df.format(new java.util.Date());


        this.STOCKTRANSFERT_CODE = commandeLigne.getCOMMANDE_CODE()+commandeLigne.getCOMMANDELIGNE_CODE();
        this.STOCKTRANSFERT_DATE = stransfert_date;
        this.ARTICLE_CODE = commandeLigne.getARTICLE_CODE();
        this.UNITE_CODE = commandeLigne.getUNITE_CODE();
        this.QTE = (int)-commandeLigne.getQTE_LIVREE();
        this.STOCK = utilisateur.getSTOCK_CODE();
        this.STOCK_SOURCE = utilisateur.getSTOCKSUP_CODE();
        this.TYPE_CODE = "Commande";
        this.STATUT_CODE = "18";
        this.CATEGORIE_CODE = "DEFAULT";
        this.SOURCE = commande.getCOMMANDETYPE_CODE();
        this.DATE_CREATION = stransfert_date;
        this.CREATEUR_CODE = utilisateur.getUTILISATEUR_CODE();
        this.SOURCE_CODE = commande.getCOMMANDE_CODE();
        this.COMMENTAIRE = "to_insert";
        this.VERSION = commande.getVERSION();

    }

    public StockTransfert(Livraison livraison, LivraisonLigne livraisonLigne, Context context) throws JSONException {

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);

        UserManager userManager = new UserManager(context);
        User utilisateur = userManager.get(user.getString("UTILISATEUR_CODE"));

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String stransfert_date =df.format(new java.util.Date());


        this.STOCKTRANSFERT_CODE = livraisonLigne.getLIVRAISON_CODE()+livraisonLigne.getLIVRAISONLIGNE_CODE();
        this.STOCKTRANSFERT_DATE = stransfert_date;
        this.ARTICLE_CODE = livraisonLigne.getARTICLE_CODE();
        this.UNITE_CODE = livraisonLigne.getUNITE_CODE();
        this.QTE = (int)-livraisonLigne.getQTE_LIVREE();
        this.STOCK = utilisateur.getSTOCK_CODE();
        this.STOCK_SOURCE = utilisateur.getSTOCKSUP_CODE();
        this.TYPE_CODE = "Livraison";
        this.STATUT_CODE = "18";
        this.CATEGORIE_CODE = "DEFAULT";
        this.SOURCE = livraison.getCOMMANDETYPE_CODE();
        this.DATE_CREATION = stransfert_date;
        this.CREATEUR_CODE = utilisateur.getUTILISATEUR_CODE();
        this.SOURCE_CODE = livraison.getLIVRAISON_CODE();
        this.COMMENTAIRE = "to_insert";
        this.VERSION = livraison.getVERSION();

    }
    public StockTransfert() {
    }

    public String getSTOCKTRANSFERT_CODE() {
        return STOCKTRANSFERT_CODE;
    }

    public void setSTOCKTRANSFERT_CODE(String STOCKTRANSFERT_CODE) {
        this.STOCKTRANSFERT_CODE = STOCKTRANSFERT_CODE;
    }

    public String getSTOCKTRANSFERT_DATE() {
        return STOCKTRANSFERT_DATE;
    }

    public void setSTOCKTRANSFERT_DATE(String STOCKTRANSFERT_DATE) {
        this.STOCKTRANSFERT_DATE = STOCKTRANSFERT_DATE;
    }

    public String getARTICLE_CODE() {
        return ARTICLE_CODE;
    }

    public void setARTICLE_CODE(String ARTICLE_CODE) {
        this.ARTICLE_CODE = ARTICLE_CODE;
    }

    public String getUNITE_CODE() {
        return UNITE_CODE;
    }

    public void setUNITE_CODE(String UNITE_CODE) {
        this.UNITE_CODE = UNITE_CODE;
    }

    public int getQTE() {
        return QTE;
    }

    public void setQTE(int QTE) {
        this.QTE = QTE;
    }

    public String getSTOCK() {
        return STOCK;
    }

    public void setSTOCK(String STOCK) {
        this.STOCK = STOCK;
    }

    public String getSTOCK_SOURCE() {
        return STOCK_SOURCE;
    }

    public void setSTOCK_SOURCE(String STOCK_SOURCE) {
        this.STOCK_SOURCE = STOCK_SOURCE;
    }

    public String getTYPE_CODE() {
        return TYPE_CODE;
    }

    public void setTYPE_CODE(String TYPE_CODE) {
        this.TYPE_CODE = TYPE_CODE;
    }

    public String getSTATUT_CODE() {
        return STATUT_CODE;
    }

    public void setSTATUT_CODE(String STATUT_CODE) {
        this.STATUT_CODE = STATUT_CODE;
    }

    public String getCATEGORIE_CODE() {
        return CATEGORIE_CODE;
    }

    public void setCATEGORIE_CODE(String CATEGORIE_CODE) {
        this.CATEGORIE_CODE = CATEGORIE_CODE;
    }

    public String getSOURCE() {
        return SOURCE;
    }

    public void setSOURCE(String SOURCE) {
        this.SOURCE = SOURCE;
    }

    public String getDATE_CREATION() {
        return DATE_CREATION;
    }

    public void setDATE_CREATION(String DATE_CREATION) {
        this.DATE_CREATION = DATE_CREATION;
    }

    public String getCREATEUR_CODE() {
        return CREATEUR_CODE;
    }

    public void setCREATEUR_CODE(String CREATEUR_CODE) {
        this.CREATEUR_CODE = CREATEUR_CODE;
    }

    public String getSOURCE_CODE() {
        return SOURCE_CODE;
    }

    public void setSOURCE_CODE(String SOURCE_CODE) {
        this.SOURCE_CODE = SOURCE_CODE;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public String getCOMMENTAIRE() {
        return COMMENTAIRE;
    }

    public void setCOMMENTAIRE(String COMMENTAIRE) {
        this.COMMENTAIRE = COMMENTAIRE;
    }

    @Override
    public String toString() {
        return "StockTransfert{" +
                "STOCKTRANSFERT_CODE='" + STOCKTRANSFERT_CODE + '\'' +
                ", STOCKTRANSFERT_DATE='" + STOCKTRANSFERT_DATE + '\'' +
                ", ARTICLE_CODE='" + ARTICLE_CODE + '\'' +
                ", UNITE_CODE='" + UNITE_CODE + '\'' +
                ", QTE=" + QTE +
                ", STOCK='" + STOCK + '\'' +
                ", STOCK_SOURCE='" + STOCK_SOURCE + '\'' +
                ", TYPE_CODE='" + TYPE_CODE + '\'' +
                ", STATUT_CODE='" + STATUT_CODE + '\'' +
                ", CATEGORIE_CODE='" + CATEGORIE_CODE + '\'' +
                ", SOURCE='" + SOURCE + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", CREATEUR_CODE='" + CREATEUR_CODE + '\'' +
                ", SOURCE_CODE='" + SOURCE_CODE + '\'' +
                ", COMMENTAIRE='" + COMMENTAIRE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}
