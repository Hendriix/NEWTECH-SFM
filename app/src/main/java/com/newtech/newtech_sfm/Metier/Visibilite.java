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

public class Visibilite {

    private String VISIBILITE_CODE;
    private String VISITE_CODE;
    private String CLIENT_CODE;
    private String TOURNEE_CODE;
    private String DATE_VISIBILITE;
    private String DISTRIBUTEUR_CODE;
    private String UTILISATEUR_CODE;
    private String CIRCUIT_CODE;
    private String TYPE_CODE;
    private String STATUT_CODE;
    private String CATEGORIE_CODE;
    private String DATE_CREATION;
    private String CREATEUR_CODE;
    private String COMMENTAIRE;
    private String VERSION;

    public Visibilite() {
    }

    public Visibilite(String VISIBILITE_CODE, String VISITE_CODE, String CLIENT_CODE, String TOURNEE_CODE, String DATE_VISIBILITE, String DISTRIBUTEUR_CODE, String UTILISATEUR_CODE, String CIRCUIT_CODE, String TYPE_CODE, String STATUT_CODE, String CATEGORIE_CODE, String DATE_CREATION, String CREATEUR_CODE, String COMMENTAIRE, String VERSION) {
        this.VISIBILITE_CODE = VISIBILITE_CODE;
        this.VISITE_CODE = VISITE_CODE;
        this.CLIENT_CODE = CLIENT_CODE;
        this.TOURNEE_CODE = TOURNEE_CODE;
        this.DATE_VISIBILITE = DATE_VISIBILITE;
        this.DISTRIBUTEUR_CODE = DISTRIBUTEUR_CODE;
        this.UTILISATEUR_CODE = UTILISATEUR_CODE;
        this.CIRCUIT_CODE = CIRCUIT_CODE;
        this.TYPE_CODE = TYPE_CODE;
        this.STATUT_CODE = STATUT_CODE;
        this.CATEGORIE_CODE = CATEGORIE_CODE;
        this.DATE_CREATION = DATE_CREATION;
        this.CREATEUR_CODE = CREATEUR_CODE;
        this.COMMENTAIRE = COMMENTAIRE;
        this.VERSION = VERSION;
    }

    public Visibilite(Client client, Context context, String VISITE_CODE, String VISIBILITE_CODE){
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        this.VISIBILITE_CODE = VISIBILITE_CODE;
        this.VISITE_CODE = VISITE_CODE;
        this.CLIENT_CODE = client.getCLIENT_CODE();
        this.TOURNEE_CODE = client.getTOURNEE_CODE();
        this.DATE_VISIBILITE = df.format(Calendar.getInstance().getTime());
        this.DISTRIBUTEUR_CODE = client.getDISTRIBUTEUR_CODE();
        try {
            this.UTILISATEUR_CODE = user.getString("UTILISATEUR_CODE");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.CIRCUIT_CODE = client.getCIRCUIT_CODE();
        this.TYPE_CODE = "DEFAULT";
        this.STATUT_CODE = "DEFAULT";
        this.CATEGORIE_CODE = "DEFAULT";
        this.DATE_CREATION = df.format(Calendar.getInstance().getTime());
        try {
            this.CREATEUR_CODE = user.getString("UTILISATEUR_CODE");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.COMMENTAIRE = "to_insert";
        this.VERSION = "non verifiee";
    }

    public String getVISIBILITE_CODE() {
        return VISIBILITE_CODE;
    }

    public void setVISIBILITE_CODE(String VISIBILITE_CODE) {
        this.VISIBILITE_CODE = VISIBILITE_CODE;
    }

    public String getVISITE_CODE() {
        return VISITE_CODE;
    }

    public void setVISITE_CODE(String VISITE_CODE) {
        this.VISITE_CODE = VISITE_CODE;
    }

    public String getCLIENT_CODE() {
        return CLIENT_CODE;
    }

    public void setCLIENT_CODE(String CLIENT_CODE) {
        this.CLIENT_CODE = CLIENT_CODE;
    }

    public String getTOURNEE_CODE() {
        return TOURNEE_CODE;
    }

    public void setTOURNEE_CODE(String TOURNEE_CODE) {
        this.TOURNEE_CODE = TOURNEE_CODE;
    }

    public String getDATE_VISIBILITE() {
        return DATE_VISIBILITE;
    }

    public void setDATE_VISIBILITE(String DATE_VISIBILITE) {
        this.DATE_VISIBILITE = DATE_VISIBILITE;
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

    public String getCIRCUIT_CODE() {
        return CIRCUIT_CODE;
    }

    public void setCIRCUIT_CODE(String CIRCUIT_CODE) {
        this.CIRCUIT_CODE = CIRCUIT_CODE;
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
        return "Visibilite{" +
                "VISIBILITE_CODE='" + VISIBILITE_CODE + '\'' +
                ", VISITE_CODE='" + VISITE_CODE + '\'' +
                ", CLIENT_CODE='" + CLIENT_CODE + '\'' +
                ", TOURNEE_CODE='" + TOURNEE_CODE + '\'' +
                ", DATE_VISIBILITE='" + DATE_VISIBILITE + '\'' +
                ", DISTRIBUTEUR_CODE='" + DISTRIBUTEUR_CODE + '\'' +
                ", UTILISATEUR_CODE='" + UTILISATEUR_CODE + '\'' +
                ", CIRCUIT_CODE='" + CIRCUIT_CODE + '\'' +
                ", TYPE_CODE='" + TYPE_CODE + '\'' +
                ", STATUT_CODE='" + STATUT_CODE + '\'' +
                ", CATEGORIE_CODE='" + CATEGORIE_CODE + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", CREATEUR_CODE='" + CREATEUR_CODE + '\'' +
                ", COMMENTAIRE='" + COMMENTAIRE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}
