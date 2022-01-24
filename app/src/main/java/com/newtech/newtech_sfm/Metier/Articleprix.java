package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by stagiaireit2 on 04/08/2016.
 */
public class Articleprix {

    String
    ARTICLEPRIX_CODE,
    CIRCUIT_CODE,
    ARTICLE_CODE,
    DATE_DEBUT,
    DATE_FIN,
    DATE_CREATION,
    CREATEUR_CODE,
    INACTIF,
    RAISON_INACTIF,
    VERSION,
    UNITE_CODE;
    double
    ARTICLE_UPPRIX,
    ARTICLE_USPRIX,
    ARTICLE_PRIX;

    public Articleprix() {
    }

    public Articleprix(JSONObject articleprix) {
        try {

        this.ARTICLEPRIX_CODE = articleprix.getString("ARTICLEPRIX_CODE");
        this.CIRCUIT_CODE = articleprix.getString("CIRCUIT_CODE");
        this.ARTICLE_CODE = articleprix.getString("ARTICLE_CODE");
        this.DATE_DEBUT = articleprix.getString("DATE_DEBUT");
        this.DATE_FIN = articleprix.getString("DATE_FIN");
        this.DATE_CREATION = articleprix.getString("DATE_CREATION");
        this.CREATEUR_CODE = articleprix.getString("CREATEUR_CODE");
        this.INACTIF = articleprix.getString("INACTIF");
        this.RAISON_INACTIF = articleprix.getString("RAISON_INACTIF");
        this.VERSION = articleprix.getString("VERSION");
        this.UNITE_CODE = articleprix.getString("UNITE_CODE");
        this.ARTICLE_UPPRIX = articleprix.getDouble("ARTICLE_UPPRIX");
        this.ARTICLE_USPRIX = articleprix.getDouble("ARTICLE_USPRIX");
        this.ARTICLE_PRIX = articleprix.getDouble("ARTICLE_PRIX");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getARTICLEPRIX_CODE() {
        return ARTICLEPRIX_CODE;
    }

    public String getCIRCUIT_CODE() {
        return CIRCUIT_CODE;
    }

    public String getARTICLE_CODE() {
        return ARTICLE_CODE;
    }

    public String getDATE_DEBUT() {
        return DATE_DEBUT;
    }

    public String getDATE_FIN() {
        return DATE_FIN;
    }

    public String getDATE_CREATION() {
        return DATE_CREATION;
    }

    public String getCREATEUR_CODE() {
        return CREATEUR_CODE;
    }

    public String getINACTIF() {
        return INACTIF;
    }

    public String getRAISON_INACTIF() {
        return RAISON_INACTIF;
    }

    public String getVERSION() {
        return VERSION;
    }

    public String getUNITE_CODE() {
        return UNITE_CODE;
    }

    public double getARTICLE_UPPRIX() {
        return ARTICLE_UPPRIX;
    }

    public double getARTICLE_USPRIX() {
        return ARTICLE_USPRIX;
    }

    public double getARTICLE_PRIX() {
        return ARTICLE_PRIX;
    }

    public void setARTICLEPRIX_CODE(String ARTICLEPRIX_CODE) {
        this.ARTICLEPRIX_CODE = ARTICLEPRIX_CODE;
    }

    public void setCIRCUIT_CODE(String CIRCUIT_CODE) {
        this.CIRCUIT_CODE = CIRCUIT_CODE;
    }

    public void setARTICLE_CODE(String ARTICLE_CODE) {
        this.ARTICLE_CODE = ARTICLE_CODE;
    }

    public void setDATE_DEBUT(String DATE_DEBUT) {
        this.DATE_DEBUT = DATE_DEBUT;
    }

    public void setDATE_FIN(String DATE_FIN) {
        this.DATE_FIN = DATE_FIN;
    }

    public void setDATE_CREATION(String DATE_CREATION) {
        this.DATE_CREATION = DATE_CREATION;
    }

    public void setCREATEUR_CODE(String CREATEUR_CODE) {
        this.CREATEUR_CODE = CREATEUR_CODE;
    }

    public void setINACTIF(String INACTIF) {
        this.INACTIF = INACTIF;
    }

    public void setRAISON_INACTIF(String RAISON_INACTIF) {
        this.RAISON_INACTIF = RAISON_INACTIF;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public void setUNITE_CODE(String UNITE_CODE) {
        this.UNITE_CODE = UNITE_CODE;
    }

    public void setARTICLE_UPPRIX(double ARTICLE_UPPRIX) {
        this.ARTICLE_UPPRIX = ARTICLE_UPPRIX;
    }

    public void setARTICLE_USPRIX(double ARTICLE_USPRIX) {
        this.ARTICLE_USPRIX = ARTICLE_USPRIX;
    }

    public void setARTICLE_PRIX(double ARTICLE_PRIX) {
        this.ARTICLE_PRIX = ARTICLE_PRIX;
    }




    public String toString3() {
        return "Articleprix{" +
                "ARTICLE_CODE=" + ARTICLE_CODE +"\n"+
                "UNITE_CODE=" + UNITE_CODE +"\n"+
                "CIRCUIT_CODE=" + CIRCUIT_CODE +"\n"+

                '}';
    }

    @Override
    public String toString() {
        return "Articleprix{" +
                "ARTICLEPRIX_CODE='" + ARTICLEPRIX_CODE + '\'' +
                ", CIRCUIT_CODE='" + CIRCUIT_CODE + '\'' +
                ", ARTICLE_CODE='" + ARTICLE_CODE + '\'' +
                ", DATE_DEBUT='" + DATE_DEBUT + '\'' +
                ", DATE_FIN='" + DATE_FIN + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", CREATEUR_CODE='" + CREATEUR_CODE + '\'' +
                ", INACTIF='" + INACTIF + '\'' +
                ", RAISON_INACTIF='" + RAISON_INACTIF + '\'' +
                ", VERSION='" + VERSION + '\'' +
                ", UNITE_CODE='" + UNITE_CODE + '\'' +
                ", ARTICLE_UPPRIX=" + ARTICLE_UPPRIX +
                ", ARTICLE_USPRIX=" + ARTICLE_USPRIX +
                ", ARTICLE_PRIX=" + ARTICLE_PRIX +
                '}';
    }
}
