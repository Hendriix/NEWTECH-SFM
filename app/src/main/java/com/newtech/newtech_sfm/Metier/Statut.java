package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by csaylani on 15/05/2018.
 */

public class Statut {

    private String STATUT_CODE;
    private String STATUT_NOM;
    private String STATUT_CATEGORIE;
    private String DESCRIPTION;
    private int RANG;
    private String VERSION;

    public Statut(String STATUT_CODE, String STATUT_NOM, String STATUT_CATEGORIE, String DESCRIPTION, int RANG, String VERSION) {
        this.STATUT_CODE = STATUT_CODE;
        this.STATUT_NOM = STATUT_NOM;
        this.STATUT_CATEGORIE = STATUT_CATEGORIE;
        this.DESCRIPTION = DESCRIPTION;
        this.RANG = RANG;
        this.VERSION = VERSION;
    }

    public Statut() {
    }

    public Statut(JSONObject statut) {
        try {

            this.STATUT_CODE  = statut.getString("STATUT_CODE");
            this.STATUT_NOM  = statut.getString("STATUT_NOM");
            this.STATUT_CATEGORIE  = statut.getString("STATUT_CATEGORIE");
            this.DESCRIPTION  = statut.getString("DESCRIPTION");
            this.RANG  = statut.getInt("RANG");
            this.VERSION  = statut.getString("VERSION");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Statut(Statut statut) {

            this.STATUT_CODE  = statut.getSTATUT_CODE();
            this.STATUT_NOM  = statut.getSTATUT_NOM();
            this.STATUT_CATEGORIE  = statut.getSTATUT_CATEGORIE();
            this.DESCRIPTION  = statut.getDESCRIPTION();
            this.RANG  = statut.getRANG();
            this.VERSION  = statut.getVERSION();
    }


    public String getSTATUT_CODE() {
        return STATUT_CODE;
    }

    public void setSTATUT_CODE(String STATUT_CODE) {
        this.STATUT_CODE = STATUT_CODE;
    }

    public String getSTATUT_NOM() {
        return STATUT_NOM;
    }

    public void setSTATUT_NOM(String STATUT_NOM) {
        this.STATUT_NOM = STATUT_NOM;
    }

    public String getSTATUT_CATEGORIE() {
        return STATUT_CATEGORIE;
    }

    public void setSTATUT_CATEGORIE(String STATUT_CATEGORIE) {
        this.STATUT_CATEGORIE = STATUT_CATEGORIE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public int getRANG() {
        return RANG;
    }

    public void setRANG(int RANG) {
        this.RANG = RANG;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    @Override
    public String toString() {
        return "Statut{" +
                "STATUT_CODE='" + STATUT_CODE + '\'' +
                ", STATUT_NOM='" + STATUT_NOM + '\'' +
                ", STATUT_CATEGORIE='" + STATUT_CATEGORIE + '\'' +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                ", RANG=" + RANG +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}
