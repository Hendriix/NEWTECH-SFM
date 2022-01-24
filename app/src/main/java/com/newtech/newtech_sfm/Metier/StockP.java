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

public class StockP {

    private String STOCK_CODE;
    private String STOCK_NOM;
    private String STOCK_DATE;
    private String CLIENT_CODE;
    private String DATE_CREATION;
    private String DESCRIPTION;
    private String STATUT_CODE;
    private String TYPE_CODE;
    private String CATEGORIE_CODE;
    private String CREATEUR_CODE;
    private String TS;
    private String COMMENTAIRE;
    private String VERSION;

    public StockP(String STOCK_CODE, String STOCK_NOM, String STOCK_DATE, String CLIENT_CODE, String DATE_CREATION, String DESCRIPTION, String STATUT_CODE, String TYPE_CODE, String CATEGORIE_CODE, String CREATEUR_CODE, String TS, String COMMENTAIRE, String VERSION) {
        this.STOCK_CODE = STOCK_CODE;
        this.STOCK_NOM = STOCK_NOM;
        this.STOCK_DATE = STOCK_DATE;
        this.CLIENT_CODE = CLIENT_CODE;
        this.DATE_CREATION = DATE_CREATION;
        this.DESCRIPTION = DESCRIPTION;
        this.STATUT_CODE = STATUT_CODE;
        this.TYPE_CODE = TYPE_CODE;
        this.CATEGORIE_CODE = CATEGORIE_CODE;
        this.CREATEUR_CODE = CREATEUR_CODE;
        this.TS = TS;
        this.COMMENTAIRE = COMMENTAIRE;
        this.VERSION = VERSION;
    }

    public StockP() {
    }

    public StockP(JSONObject stockP) {
        try {

            this.STOCK_CODE = stockP.getString("STOCK_CODE");
            this.STOCK_NOM = stockP.getString("STOCK_NOM");
            this.STOCK_DATE = stockP.getString("STOCK_DATE");
            this.CLIENT_CODE = stockP.getString("CLIENT_CODE");
            this.DATE_CREATION = stockP.getString("DATE_CREATION");
            this.DESCRIPTION = stockP.getString("DESCRIPTION");
            this.STATUT_CODE = stockP.getString("STATUT_CODE");
            this.TYPE_CODE = stockP.getString("TYPE_CODE");
            this.CATEGORIE_CODE = stockP.getString("CATEGORIE_CODE");
            this.CREATEUR_CODE = stockP.getString("CREATEUR_CODE");
            this.TS = stockP.getString("TS");
            this.COMMENTAIRE = stockP.getString("COMMENTAIRE");
            this.VERSION = stockP.getString("VERSION");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public StockP(Client client, Context context , String STOCKP_CODE) throws JSONException {

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);

        UserManager userManager = new UserManager(context);
        User utilisateur = userManager.get(user.getString("UTILISATEUR_CODE"));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        this.STOCK_CODE = STOCKP_CODE;
        this.STOCK_NOM = "ST"+client.getCLIENT_NOM();
        this.STOCK_DATE = df.format(Calendar.getInstance().getTime());
        this.CLIENT_CODE = client.getCLIENT_CODE();
        this.DATE_CREATION = df.format(Calendar.getInstance().getTime());;
        this.DESCRIPTION = "DEFAULT";
        this.STATUT_CODE = "DEFAULT";
        this.TYPE_CODE = "DEFAULT";
        this.CATEGORIE_CODE = "DEFAULT";
        this.CREATEUR_CODE = utilisateur.getUTILISATEUR_CODE();
        this.TS = df.format(Calendar.getInstance().getTime());
        this.COMMENTAIRE = "to_insert";
        this.VERSION = "non_verifiee";

    }


    public String getSTOCK_CODE() {
        return STOCK_CODE;
    }

    public void setSTOCK_CODE(String STOCK_CODE) {
        this.STOCK_CODE = STOCK_CODE;
    }

    public String getSTOCK_NOM() {
        return STOCK_NOM;
    }

    public void setSTOCK_NOM(String STOCK_NOM) {
        this.STOCK_NOM = STOCK_NOM;
    }

    public String getSTOCK_DATE() {
        return STOCK_DATE;
    }

    public void setSTOCK_DATE(String STOCK_DATE) {
        this.STOCK_DATE = STOCK_DATE;
    }

    public String getCLIENT_CODE() {
        return CLIENT_CODE;
    }

    public void setCLIENT_CODE(String CLIENT_CODE) {
        this.CLIENT_CODE = CLIENT_CODE;
    }

    public String getDATE_CREATION() {
        return DATE_CREATION;
    }

    public void setDATE_CREATION(String DATE_CREATION) {
        this.DATE_CREATION = DATE_CREATION;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getSTATUT_CODE() {
        return STATUT_CODE;
    }

    public void setSTATUT_CODE(String STATUT_CODE) {
        this.STATUT_CODE = STATUT_CODE;
    }

    public String getTYPE_CODE() {
        return TYPE_CODE;
    }

    public void setTYPE_CODE(String TYPE_CODE) {
        this.TYPE_CODE = TYPE_CODE;
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

    public String getCOMMENTAIRE() {
        return COMMENTAIRE;
    }

    public void setCOMMENTAIRE(String COMMENTAIRE) {
        this.COMMENTAIRE = COMMENTAIRE;
    }

    @Override
    public String toString() {
        return "StockP{" +
                "STOCK_CODE='" + STOCK_CODE + '\'' +
                ", STOCK_NOM='" + STOCK_NOM + '\'' +
                ", STOCK_DATE='" + STOCK_DATE + '\'' +
                ", CLIENT_CODE='" + CLIENT_CODE + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                ", STATUT_CODE='" + STATUT_CODE + '\'' +
                ", TYPE_CODE='" + TYPE_CODE + '\'' +
                ", CATEGORIE_CODE='" + CATEGORIE_CODE + '\'' +
                ", CREATEUR_CODE='" + CREATEUR_CODE + '\'' +
                ", TS='" + TS + '\'' +
                ", COMMENTAIRE='" + COMMENTAIRE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}
