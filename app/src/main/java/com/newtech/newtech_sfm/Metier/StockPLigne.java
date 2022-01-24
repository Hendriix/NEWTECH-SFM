package com.newtech.newtech_sfm.Metier;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Metier_Manager.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StockPLigne {


    private int STOCKLIGNE_CODE;
    private String STOCK_CODE;
    private String FAMILLE_CODE;
    private String ARTICLE_CODE;
    private String ARTICLE_DESIGNATION;
    private double ARTICLE_NBUS_PAR_UP;
    private double ARTICLE_PRIX;
    private String UNITE_CODE;
    private double QTE;
    private double LITTRAGE;
    private double TONNAGE;
    private String COMMENTAIRE;
    private String CREATEUR_CODE;
    private String DATE_CREATION;
    private String TS;
    private String VERSION;

    public StockPLigne(int STOCKLIGNE_CODE, String STOCK_CODE, String FAMILLE_CODE, String ARTICLE_CODE, String ARTICLE_DESIGNATION, double ARTICLE_NBUS_PAR_UP, double ARTICLE_PRIX, String UNITE_CODE, double QTE, double LITTRAGE, double TONNAGE, String COMMENTAIRE, String CREATEUR_CODE, String DATE_CREATION, String TS, String VERSION) {
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

    public StockPLigne(JSONObject stockPLigne) {

        try{

            this.STOCKLIGNE_CODE = stockPLigne.getInt("STOCKLIGNE_CODE");
            this.STOCK_CODE = stockPLigne.getString("STOCK_CODE");
            this.FAMILLE_CODE = stockPLigne.getString("FAMILLE_CODE");
            this.ARTICLE_CODE = stockPLigne.getString("ARTICLE_CODE");
            this.ARTICLE_DESIGNATION = stockPLigne.getString("ARTICLE_DESIGNATION");
            this.ARTICLE_NBUS_PAR_UP = stockPLigne.getDouble("ARTICLE_NBUS_PAR_UP");
            this.ARTICLE_PRIX = stockPLigne.getDouble("ARTICLE_PRIX");
            this.UNITE_CODE = stockPLigne.getString("UNITE_CODE");
            this.QTE = stockPLigne.getDouble("QTE");
            this.LITTRAGE = stockPLigne.getDouble("LITTRAGE");
            this.TONNAGE = stockPLigne.getDouble("TONNAGE");
            this.COMMENTAIRE = stockPLigne.getString("COMMENTAIRE");
            this.CREATEUR_CODE = stockPLigne.getString("CREATEUR_CODE");
            this.DATE_CREATION = stockPLigne.getString("DATE_CREATION");
            this.TS = stockPLigne.getString("TS");
            this.VERSION = stockPLigne.getString("VERSION");

        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public StockPLigne(){

    }

    public StockPLigne (Article article ,String unite_code,double quantite, Context context) throws JSONException {

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);

        UserManager userManager = new UserManager(context);
        User utilisateur = userManager.get(user.getString("UTILISATEUR_CODE"));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        this.STOCK_CODE = utilisateur.getSTOCK_CODE();
        this.FAMILLE_CODE = article.getFAMILLE_CODE();
        this.ARTICLE_CODE = article.getARTICLE_CODE();
        this.ARTICLE_DESIGNATION = article.getARTICLE_DESIGNATION1();
        this.ARTICLE_NBUS_PAR_UP = (int)article.getNBUS_PAR_UP();
        this.ARTICLE_PRIX = article.getARTICLE_PRIX();
        this.UNITE_CODE = unite_code;
        this.QTE = quantite;
        this.LITTRAGE = 0.0;
        this.TONNAGE = 0.0;
        this.COMMENTAIRE = "to_insert";
        this.CREATEUR_CODE = utilisateur.getUTILISATEUR_CODE();
        this.DATE_CREATION = df.format(Calendar.getInstance().getTime());
        this.TS = df.format(Calendar.getInstance().getTime());
        this.VERSION = "non_verifiee";

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

    public double getARTICLE_NBUS_PAR_UP() {
        return ARTICLE_NBUS_PAR_UP;
    }

    public void setARTICLE_NBUS_PAR_UP(double ARTICLE_NBUS_PAR_UP) {
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

    public double getQTE() {
        return QTE;
    }

    public void setQTE(double QTE) {
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
        return "StockPLigne{" +
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
