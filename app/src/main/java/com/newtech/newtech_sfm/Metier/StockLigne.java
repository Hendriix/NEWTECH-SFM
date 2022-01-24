package com.newtech.newtech_sfm.Metier;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.Metier_Manager.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class StockLigne {

    private int STOCKLIGNE_CODE;
    private String STOCK_CODE;
    private String FAMILLE_CODE;
    private String ARTICLE_CODE;
    private String ARTICLE_DESIGNATION;
    private int ARTICLE_NBUS_PAR_UP;
    private double ARTICLE_PRIX;
    private String UNITE_CODE;
    private int QTE;
    private double LITTRAGE;
    private double TONNAGE;
    private String COMMENTAIRE;
    private String CREATEUR_CODE;
    private String DATE_CREATION;
    private String TS;
    private String VERSION;

    public StockLigne(int STOCKLIGNE_CODE, String STOCK_CODE, String FAMILLE_CODE, String ARTICLE_CODE, String ARTICLE_DESIGNATION, int ARTICLE_NBUS_PAR_UP, double ARTICLE_PRIX, String UNITE_CODE, int QTE, double LITTRAGE, double TONNAGE, String COMMENTAIRE, String CREATEUR_CODE, String DATE_CREATION, String TS, String VERSION) {
        this.STOCKLIGNE_CODE = STOCKLIGNE_CODE;
        this.STOCK_CODE = STOCK_CODE;
        this.FAMILLE_CODE = FAMILLE_CODE;
        this.ARTICLE_CODE = ARTICLE_CODE;
        this.ARTICLE_DESIGNATION = ARTICLE_DESIGNATION;
        this.ARTICLE_NBUS_PAR_UP = ARTICLE_NBUS_PAR_UP;
        this.ARTICLE_PRIX = ARTICLE_PRIX;
        this.UNITE_CODE = UNITE_CODE;
        this.QTE = QTE;
        this.LITTRAGE = LITTRAGE;
        this.TONNAGE = TONNAGE;
        this.COMMENTAIRE = COMMENTAIRE;
        this.CREATEUR_CODE = CREATEUR_CODE;
        this.DATE_CREATION = DATE_CREATION;
        this.TS = TS;
        this.VERSION = VERSION;
    }

    public StockLigne() {
    }

    public StockLigne(JSONObject stockLigne) {
        try {

            this.STOCKLIGNE_CODE = stockLigne.getInt("STOCKLIGNE_CODE");
            this.STOCK_CODE = stockLigne.getString("STOCK_CODE");
            this.FAMILLE_CODE = stockLigne.getString("FAMILLE_CODE");
            this.ARTICLE_CODE = stockLigne.getString("ARTICLE_CODE");
            this.ARTICLE_DESIGNATION = stockLigne.getString("ARTICLE_DESIGNATION");
            this.ARTICLE_NBUS_PAR_UP = stockLigne.getInt("ARTICLE_NBUS_PAR_UP");
            this.ARTICLE_PRIX = stockLigne.getDouble("ARTICLE_PRIX");
            this.UNITE_CODE = stockLigne.getString("UNITE_CODE");
            this.QTE = stockLigne.getInt("QTE");
            this.LITTRAGE = stockLigne.getDouble("LITTRAGE");
            this.TONNAGE = stockLigne.getDouble("TONNAGE");
            this.COMMENTAIRE = stockLigne.getString("COMMENTAIRE");
            this.CREATEUR_CODE = stockLigne.getString("CREATEUR_CODE");
            this.DATE_CREATION = stockLigne.getString("DATE_CREATION");
            this.TS = stockLigne.getString("TS");
            this.VERSION = stockLigne.getString("VERSION");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public StockLigne (StockTransfert stockTransfert, Context context) throws JSONException {
        ArticleManager articleManager = new ArticleManager(context);
        Article article = new Article();
        article = articleManager.get(stockTransfert.getARTICLE_CODE());

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);

        UserManager userManager = new UserManager(context);
        User utilisateur = userManager.get(user.getString("UTILISATEUR_CODE"));


        this.STOCK_CODE = utilisateur.getSTOCK_CODE();
        this.FAMILLE_CODE = article.getFAMILLE_CODE();
        this.ARTICLE_CODE = stockTransfert.getARTICLE_CODE();
        this.ARTICLE_DESIGNATION = article.getARTICLE_DESIGNATION1();
        this.ARTICLE_NBUS_PAR_UP = (int)article.getNBUS_PAR_UP();
        this.ARTICLE_PRIX = article.getARTICLE_PRIX();
        this.UNITE_CODE = stockTransfert.getUNITE_CODE();
        this.QTE = stockTransfert.getQTE();
        this.LITTRAGE = 0.0;
        this.TONNAGE = 0.0;
        this.COMMENTAIRE = "to_insert";
        this.CREATEUR_CODE = stockTransfert.getCREATEUR_CODE();
        this.DATE_CREATION = stockTransfert.getDATE_CREATION();
        this.TS = stockTransfert.getDATE_CREATION();
        this.VERSION = stockTransfert.getVERSION();

    }

    public int getSTOCKLIGNE_CODE() {
        return STOCKLIGNE_CODE;
    }

    public void setSTOCKLIGNE_CODE(int STOCKLIGNE_CODE) {
        this.STOCKLIGNE_CODE = STOCKLIGNE_CODE;
    }

    public String getSTOCK_CODE() {
        return STOCK_CODE;
    }

    public void setSTOCK_CODE(String STOCK_CODE) {
        this.STOCK_CODE = STOCK_CODE;
    }

    public String getFAMILLE_CODE() {
        return FAMILLE_CODE;
    }

    public void setFAMILLE_CODE(String FAMILLE_CODE) {
        this.FAMILLE_CODE = FAMILLE_CODE;
    }

    public String getARTICLE_CODE() {
        return ARTICLE_CODE;
    }

    public void setARTICLE_CODE(String ARTICLE_CODE) {
        this.ARTICLE_CODE = ARTICLE_CODE;
    }

    public String getARTICLE_DESIGNATION() {
        return ARTICLE_DESIGNATION;
    }

    public void setARTICLE_DESIGNATION(String ARTICLE_DESIGNATION) {
        this.ARTICLE_DESIGNATION = ARTICLE_DESIGNATION;
    }

    public int getARTICLE_NBUS_PAR_UP() {
        return ARTICLE_NBUS_PAR_UP;
    }

    public void setARTICLE_NBUS_PAR_UP(int ARTICLE_NBUS_PAR_UP) {
        this.ARTICLE_NBUS_PAR_UP = ARTICLE_NBUS_PAR_UP;
    }

    public double getARTICLE_PRIX() {
        return ARTICLE_PRIX;
    }

    public void setARTICLE_PRIX(double ARTICLE_PRIX) {
        this.ARTICLE_PRIX = ARTICLE_PRIX;
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

    public double getLITTRAGE() {
        return LITTRAGE;
    }

    public void setLITTRAGE(double LITTRAGE) {
        this.LITTRAGE = LITTRAGE;
    }

    public double getTONNAGE() {
        return TONNAGE;
    }

    public void setTONNAGE(double TONNAGE) {
        this.TONNAGE = TONNAGE;
    }

    public String getCOMMENTAIRE() {
        return COMMENTAIRE;
    }

    public void setCOMMENTAIRE(String COMMENTAIRE) {
        this.COMMENTAIRE = COMMENTAIRE;
    }

    public String getCREATEUR_CODE() {
        return CREATEUR_CODE;
    }

    public void setCREATEUR_CODE(String CREATEUR_CODE) {
        this.CREATEUR_CODE = CREATEUR_CODE;
    }

    public String getDATE_CREATION() {
        return DATE_CREATION;
    }

    public void setDATE_CREATION(String DATE_CREATION) {
        this.DATE_CREATION = DATE_CREATION;
    }

    public String getTS() {
        return TS;
    }

    public void setTS(String TS) {
        this.TS = TS;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    @Override
    public String toString() {
        return "StockLigne{" +
                "STOCKLIGNE_CODE=" + STOCKLIGNE_CODE +
                ", STOCK_CODE='" + STOCK_CODE + '\'' +
                ", FAMILLE_CODE='" + FAMILLE_CODE + '\'' +
                ", ARTICLE_CODE='" + ARTICLE_CODE + '\'' +
                ", ARTICLE_DESIGNATION='" + ARTICLE_DESIGNATION + '\'' +
                ", ARTICLE_NBUS_PAR_UP=" + ARTICLE_NBUS_PAR_UP +
                ", ARTICLE_PRIX=" + ARTICLE_PRIX +
                ", UNITE_CODE='" + UNITE_CODE + '\'' +
                ", QTE=" + QTE +
                ", LITTRAGE=" + LITTRAGE +
                ", TONNAGE=" + TONNAGE +
                ", COMMENTAIRE='" + COMMENTAIRE + '\'' +
                ", CREATEUR_CODE='" + CREATEUR_CODE + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", TS='" + TS + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}
