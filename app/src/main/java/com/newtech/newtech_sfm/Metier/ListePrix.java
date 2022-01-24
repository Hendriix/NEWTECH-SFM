package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TONPC on 12/01/2018.
 */

public class ListePrix {

    private String LISTEPRIX_CODE;
    private String LISTEPRIX_NOM;
    private String DATE_DEBUT;
    private String DATE_FIN;
    private int INACTIF;
    private String INACTIF_RAISON;
    private String TYPE_CODE;
    private String CATEGORIE_CODE;
    private String VERSION;


    public ListePrix() {
    }

    public ListePrix(ListePrix listePrix) {

        this.LISTEPRIX_CODE = listePrix.getLISTEPRIX_CODE();
        this.LISTEPRIX_NOM = listePrix.getLISTEPRIX_NOM();
        this.DATE_DEBUT = listePrix.getDATE_DEBUT();
        this.DATE_FIN = listePrix.getDATE_FIN();
        this.INACTIF = listePrix.getINACTIF();
        this.INACTIF_RAISON = listePrix.getINACTIF_RAISON();
        this.TYPE_CODE = listePrix.getTYPE_CODE();
        this.CATEGORIE_CODE = listePrix.getCATEGORIE_CODE();
        this.VERSION = listePrix.getVERSION();

    }

    public ListePrix(JSONObject listeprix) {
        try {
            this.LISTEPRIX_CODE = listeprix.getString("LISTEPRIX_CODE");
            this.LISTEPRIX_NOM = listeprix.getString("LISTEPRIX_NOM");
            this.DATE_DEBUT = listeprix.getString("DATE_DEBUT");
            this.DATE_FIN = listeprix.getString("DATE_FIN");
            this.INACTIF = listeprix.getInt("INACTIF");
            this.INACTIF_RAISON = listeprix.getString("INACTIF_RAISON");
            this.TYPE_CODE = listeprix.getString("TYPE_CODE");
            this.CATEGORIE_CODE = listeprix.getString("CATEGORIE_CODE");
            this.VERSION = listeprix.getString("VERSION");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ListePrix(String LISTEPRIX_CODE, String LISTEPRIX_NOM, String DATE_DEBUT, String DATE_FIN, int INACTIF, String INACTIF_RAISON, String TYPE_CODE, String CATEGORIE_CODE, String VERSION) {
        this.LISTEPRIX_CODE = LISTEPRIX_CODE;
        this.LISTEPRIX_NOM = LISTEPRIX_NOM;
        this.DATE_DEBUT = DATE_DEBUT;
        this.DATE_FIN = DATE_FIN;
        this.INACTIF = INACTIF;
        this.INACTIF_RAISON = INACTIF_RAISON;
        this.TYPE_CODE = TYPE_CODE;
        this.CATEGORIE_CODE = CATEGORIE_CODE;
        this.VERSION = VERSION;
    }

    public String getLISTEPRIX_CODE() {
        return LISTEPRIX_CODE;
    }

    public void setLISTEPRIX_CODE(String LISTEPRIX_CODE) {
        this.LISTEPRIX_CODE = LISTEPRIX_CODE;
    }

    public String getLISTEPRIX_NOM() {
        return LISTEPRIX_NOM;
    }

    public void setLISTEPRIX_NOM(String LISTEPRIX_NOM) {
        this.LISTEPRIX_NOM = LISTEPRIX_NOM;
    }

    public String getDATE_DEBUT() {
        return DATE_DEBUT;
    }

    public void setDATE_DEBUT(String DATE_DEBUT) {
        this.DATE_DEBUT = DATE_DEBUT;
    }

    public String getDATE_FIN() {
        return DATE_FIN;
    }

    public void setDATE_FIN(String DATE_FIN) {
        this.DATE_FIN = DATE_FIN;
    }

    public int getINACTIF() {
        return INACTIF;
    }

    public void setINACTIF(int INACTIF) {
        this.INACTIF = INACTIF;
    }

    public String getINACTIF_RAISON() {
        return INACTIF_RAISON;
    }

    public void setINACTIF_RAISON(String INACTIF_RAISON) {
        this.INACTIF_RAISON = INACTIF_RAISON;
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

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    @Override
    public String toString() {
        return "ListePrix{" +
                "LISTEPRIX_CODE='" + LISTEPRIX_CODE + '\'' +
                ", LISTEPRIX_NOM='" + LISTEPRIX_NOM + '\'' +
                ", DATE_DEBUT='" + DATE_DEBUT + '\'' +
                ", DATE_FIN='" + DATE_FIN + '\'' +
                ", INACTIF=" + INACTIF +
                ", INACTIF_RAISON='" + INACTIF_RAISON + '\'' +
                ", TYPE_CODE='" + TYPE_CODE + '\'' +
                ", CATEGORIE_CODE='" + CATEGORIE_CODE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}
