package com.newtech.newtech_sfm.Metier;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Metier_Manager.UniteManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by csaylani on 19/04/2018.
 */

public class StockDemandeLigne {


    private int DEMANDELIGNE_CODE;
    private String DEMANDE_CODE;
    private String FAMILLE_CODE;
    private String ARTICLE_CODE;
    private String ARTICLE_DESIGNATION;
    private int AR_NBUS_PAR_UP;
    private double ARTICLE_PRIX;
    private double ARTICLE_KG;
    private String UNITE_CODE;
    private int QTE_COMMANDEE;
    private int QTE_APPROUVEE;
    private int QTE_LIVREE;
    private int QTE_RECEPTIONEE;
    private String DATE_CREATION;
    private String CREATEUR_CODE;
    private String COMMENTAIRE;
    private String VERSION;


    public StockDemandeLigne(int DEMANDELIGNE_CODE, String DEMANDE_CODE, String FAMILLE_CODE, String ARTICLE_CODE, String ARTICLE_DESIGNATION, int AR_NBUS_PAR_UP, double ARTICLE_PRIX, double ARTICLE_KG, String UNITE_CODE, int QTE_COMMANDEE, int QTE_APPROUVEE, int QTE_LIVREE, int QTE_RECEPTIONEE, String DATE_CREATION, String CREATEUR_CODE, String COMMENTAIRE, String VERSION) {
        this.DEMANDELIGNE_CODE = DEMANDELIGNE_CODE;
        this.DEMANDE_CODE = DEMANDE_CODE;
        this.FAMILLE_CODE = FAMILLE_CODE;
        this.ARTICLE_CODE = ARTICLE_CODE;
        this.ARTICLE_DESIGNATION = ARTICLE_DESIGNATION;
        this.AR_NBUS_PAR_UP = AR_NBUS_PAR_UP;
        this.ARTICLE_PRIX = ARTICLE_PRIX;
        this.ARTICLE_KG = ARTICLE_KG;
        this.UNITE_CODE = UNITE_CODE;
        this.QTE_COMMANDEE = QTE_COMMANDEE;
        this.QTE_APPROUVEE = QTE_APPROUVEE;
        this.QTE_LIVREE = QTE_LIVREE;
        this.QTE_RECEPTIONEE = QTE_RECEPTIONEE;
        this.DATE_CREATION = DATE_CREATION;
        this.CREATEUR_CODE = CREATEUR_CODE;
        this.COMMENTAIRE = COMMENTAIRE;
        this.VERSION = VERSION;
    }

    public StockDemandeLigne() {
    }


    public StockDemandeLigne(int size, String stockdemande_code, Article article, String date, int quantite, String unite, double prix, Context context){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        UniteManager uniteManager = new UniteManager(context);
        Unite unite1 = uniteManager.get(unite);

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);

        this.DEMANDELIGNE_CODE =size ;
        this.DEMANDE_CODE = stockdemande_code;
        this.FAMILLE_CODE = article.getFAMILLE_CODE();
        this.ARTICLE_CODE = article.getARTICLE_CODE();
        this.ARTICLE_DESIGNATION = article.getARTICLE_DESIGNATION1();
        this.AR_NBUS_PAR_UP = (int)article.getNBUS_PAR_UP();
        this.ARTICLE_PRIX = prix;
        try{
            this.ARTICLE_KG = unite1.getPOIDKG();
        }catch(NullPointerException e){
            this.ARTICLE_KG = article.getPOIDKG_UP();
        }
        this.UNITE_CODE = unite;
        this.QTE_COMMANDEE = quantite;
        this.QTE_APPROUVEE = 0;
        this.QTE_LIVREE = 0;
        this.QTE_RECEPTIONEE = 0;
        this.DATE_CREATION = df.format(Calendar.getInstance().getTime());
        try {
            this.CREATEUR_CODE = user.getString("UTILISATEUR_CODE");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.COMMENTAIRE="to_insert";
        this.VERSION = "non_verifiee";
    }

    public StockDemandeLigne(int size, String stockdemande_code, Article article, String date,  String unite ,int quantite, double prix, Context context){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        UniteManager uniteManager = new UniteManager(context);
        Unite unite1 = uniteManager.get(unite);

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);

        this.DEMANDELIGNE_CODE =size ;
        this.DEMANDE_CODE = stockdemande_code;
        this.FAMILLE_CODE = article.getFAMILLE_CODE();
        this.ARTICLE_CODE = article.getARTICLE_CODE();
        this.ARTICLE_DESIGNATION = article.getARTICLE_DESIGNATION1();
        this.AR_NBUS_PAR_UP = (int)article.getNBUS_PAR_UP();
        this.ARTICLE_PRIX = prix;
        try{
            this.ARTICLE_KG = unite1.getPOIDKG();
        }catch(NullPointerException e){
            this.ARTICLE_KG = article.getPOIDKG_UP();
        }
        this.UNITE_CODE = unite;
        this.QTE_COMMANDEE = quantite;
        this.QTE_APPROUVEE = 0;
        this.QTE_LIVREE = 0;
        this.QTE_RECEPTIONEE = 0;
        this.DATE_CREATION = df.format(Calendar.getInstance().getTime());
        try {
            this.CREATEUR_CODE = user.getString("UTILISATEUR_CODE");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.COMMENTAIRE="to_insert";
        this.VERSION = "non_verifiee";
    }

    public StockDemandeLigne(JSONObject p) {
        try {

            this.DEMANDELIGNE_CODE =p.getInt("DEMANDELIGNE_CODE");
            this.DEMANDE_CODE = p.getString("DEMANDE_CODE");
            this.FAMILLE_CODE = p.getString("FAMILLE_CODE");
            this.ARTICLE_CODE = p.getString("ARTICLE_CODE");
            this.ARTICLE_DESIGNATION = p.getString("ARTICLE_DESIGNATION");
            this.AR_NBUS_PAR_UP = p.getInt("AR_NBUS_PAR_UP");
            this.ARTICLE_PRIX = p.getDouble("ARTICLE_PRIX");
            this.ARTICLE_KG = p.getDouble("ARTICLE_KG");
            this.UNITE_CODE = p.getString("UNITE_CODE");
            this.QTE_COMMANDEE = p.getInt("QTE_COMMANDEE");
            this.QTE_APPROUVEE = p.getInt("QTE_APPROUVEE");
            this.QTE_LIVREE = p.getInt("QTE_LIVREE");
            this.QTE_RECEPTIONEE = p.getInt("QTE_RECEPTIONEE");
            this.DATE_CREATION = p.getString("DATE_CREATION");
            this.CREATEUR_CODE = p.getString("CREATEUR_CODE");
            this.COMMENTAIRE = p.getString("COMMENTAIRE");
            this.VERSION = p.getString("VERSION");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public int getDEMANDELIGNE_CODE() {
        return DEMANDELIGNE_CODE;
    }

    public void setDEMANDELIGNE_CODE(int DEMANDELIGNE_CODE) {
        this.DEMANDELIGNE_CODE = DEMANDELIGNE_CODE;
    }

    public String getDEMANDE_CODE() {
        return DEMANDE_CODE;
    }

    public void setDEMANDE_CODE(String DEMANDE_CODE) {
        this.DEMANDE_CODE = DEMANDE_CODE;
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

    public int getAR_NBUS_PAR_UP() {
        return AR_NBUS_PAR_UP;
    }

    public void setAR_NBUS_PAR_UP(int AR_NBUS_PAR_UP) {
        this.AR_NBUS_PAR_UP = AR_NBUS_PAR_UP;
    }

    public double getARTICLE_PRIX() {
        return ARTICLE_PRIX;
    }

    public void setARTICLE_PRIX(double ARTICLE_PRIX) {
        this.ARTICLE_PRIX = ARTICLE_PRIX;
    }

    public double getARTICLE_KG() {
        return ARTICLE_KG;
    }

    public void setARTICLE_KG(double ARTICLE_KG) {
        this.ARTICLE_KG = ARTICLE_KG;
    }

    public String getUNITE_CODE() {
        return UNITE_CODE;
    }

    public void setUNITE_CODE(String UNITE_CODE) {
        this.UNITE_CODE = UNITE_CODE;
    }

    public int getQTE_COMMANDEE() {
        return QTE_COMMANDEE;
    }

    public void setQTE_COMMANDEE(int QTE_COMMANDEE) {
        this.QTE_COMMANDEE = QTE_COMMANDEE;
    }

    public int getQTE_APPROUVEE() {
        return QTE_APPROUVEE;
    }

    public void setQTE_APPROUVEE(int QTE_APPROUVEE) {
        this.QTE_APPROUVEE = QTE_APPROUVEE;
    }

    public int getQTE_LIVREE() {
        return QTE_LIVREE;
    }

    public void setQTE_LIVREE(int QTE_LIVREE) {
        this.QTE_LIVREE = QTE_LIVREE;
    }

    public int getQTE_RECEPTIONEE() {
        return QTE_RECEPTIONEE;
    }

    public void setQTE_RECEPTIONEE(int QTE_RECEPTIONEE) {
        this.QTE_RECEPTIONEE = QTE_RECEPTIONEE;
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

    public String getCOMMENTAIRE() {
        return COMMENTAIRE;
    }

    public void setCOMMENTAIRE(String COMMENTAIRE) {
        this.COMMENTAIRE = COMMENTAIRE;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    @Override
    public String toString() {
        return "StockDemandeLigne{" +
                "DEMANDELIGNE_CODE=" + DEMANDELIGNE_CODE +
                ", DEMANDE_CODE='" + DEMANDE_CODE + '\'' +
                ", FAMILLE_CODE='" + FAMILLE_CODE + '\'' +
                ", ARTICLE_CODE='" + ARTICLE_CODE + '\'' +
                ", ARTICLE_DESIGNATION='" + ARTICLE_DESIGNATION + '\'' +
                ", AR_NBUS_PAR_UP=" + AR_NBUS_PAR_UP +
                ", ARTICLE_PRIX=" + ARTICLE_PRIX +
                ", ARTICLE_KG=" + ARTICLE_KG +
                ", UNITE_CODE='" + UNITE_CODE + '\'' +
                ", QTE_COMMANDEE=" + QTE_COMMANDEE +
                ", QTE_APPROUVEE=" + QTE_APPROUVEE +
                ", QTE_LIVREE=" + QTE_LIVREE +
                ", QTE_RECEPTIONEE=" + QTE_RECEPTIONEE +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", CREATEUR_CODE='" + CREATEUR_CODE + '\'' +
                ", COMMENTAIRE='" + COMMENTAIRE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}
