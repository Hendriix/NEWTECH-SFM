package com.newtech.newtech_sfm.Metier;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by csaylani on 17/04/2018.
 */

public class StockDemande {


    private String DEMANDE_CODE;
    private String DEMANDE_DATE;
    private String DISTRIBUTEUR_CODE;
    private String UTILISATEUR_CODE;
    private String TYPE_CODE;
    private String STATUT_CODE;
    private String CATEGORIE_CODE;
    private String CREATEUR_CODE;
    private String DATE_CREATION;
    private String COMMENTAIRE;
    private String VERSION;




    public StockDemande() {
    }

    public StockDemande(JSONObject p) {
        try {

            this.DEMANDE_CODE=p.getString("DEMANDE_CODE");
            this.DEMANDE_DATE=p.getString("DEMANDE_DATE");
            this.DISTRIBUTEUR_CODE=p.getString("DISTRIBUTEUR_CODE");
            this.UTILISATEUR_CODE=p.getString("UTILISATEUR_CODE");
            this.TYPE_CODE=p.getString("TYPE_CODE");
            this.STATUT_CODE=p.getString("STATUT_CODE");
            this.CATEGORIE_CODE=p.getString("CATEGORIE_CODE");
            this.CREATEUR_CODE=p.getString("CREATEUR_CODE");
            this.DATE_CREATION=p.getString("DATE_CREATION");
            this.COMMENTAIRE=p.getString("COMMENTAIRE");
            this.VERSION=p.getString("VERSION");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public StockDemande(String stockdemande_code, String type_demande, Context context){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);

        this.DEMANDE_CODE = stockdemande_code;
        this.DEMANDE_DATE = df.format(Calendar.getInstance().getTime());
        try {
            this.DISTRIBUTEUR_CODE = user.getString("DISTRIBUTEUR_CODE");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.UTILISATEUR_CODE = user.getString("UTILISATEUR_CODE");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.TYPE_CODE = type_demande;
        this.STATUT_CODE = "29";
        this.CATEGORIE_CODE = "CA0073";
        try {
            this.CREATEUR_CODE = user.getString("UTILISATEUR_CODE");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.DATE_CREATION = df.format(Calendar.getInstance().getTime());
        this.COMMENTAIRE="to_insert";
        this.VERSION = "non_verifiee";


    }


    public String getDEMANDE_CODE() {
        return DEMANDE_CODE;
    }

    public void setDEMANDE_CODE(String DEMANDE_CODE) {
        this.DEMANDE_CODE = DEMANDE_CODE;
    }

    public String getDEMANDE_DATE() {
        return DEMANDE_DATE;
    }

    public void setDEMANDE_DATE(String DEMANDE_DATE) {
        this.DEMANDE_DATE = DEMANDE_DATE;
    }

    public String getDISTRIBUTEUR_CODE() {
        return DISTRIBUTEUR_CODE;
    }

    public void setDISTRIBUTEUR_CODE(String DISTRIBUTEUR_CODE) {
        this.DISTRIBUTEUR_CODE = DISTRIBUTEUR_CODE;
    }

    public String getUTILISATEUR_CODE() {
        return UTILISATEUR_CODE;
    }

    public void setUTILISATEUR_CODE(String UTILISATEUR_CODE) {
        this.UTILISATEUR_CODE = UTILISATEUR_CODE;
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
        return "StockDemande{" +
                "DEMANDE_CODE='" + DEMANDE_CODE + '\'' +
                ", DEMANDE_DATE='" + DEMANDE_DATE + '\'' +
                ", DISTRIBUTEUR_CODE='" + DISTRIBUTEUR_CODE + '\'' +
                ", UTILISATEUR_CODE='" + UTILISATEUR_CODE + '\'' +
                ", TYPE_CODE='" + TYPE_CODE + '\'' +
                ", STATUT_CODE='" + STATUT_CODE + '\'' +
                ", CATEGORIE_CODE='" + CATEGORIE_CODE + '\'' +
                ", CREATEUR_CODE='" + CREATEUR_CODE + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", COMMENTAIRE='" + COMMENTAIRE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}
