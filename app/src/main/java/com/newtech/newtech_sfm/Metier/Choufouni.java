package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

public class Choufouni {

    private String CHOUFOUNI_CODE;
    private String CHOUFOUNI_NOM;
    private String DATE_DEBUT;
    private String DATE_FIN;
    private String TYPE_CODE;
    private String STATUT_CODE;
    private String CATEGORIE_CODE;
    private String CREATEUR_CODE;
    private String DATE_CREATION;
    private double INACTIF;
    private String INACTIF_RAISON;
    private String VERSION;

    public Choufouni() {
    }

    public Choufouni(String CHOUFOUNI_CODE, String COUFOUNI_NOM, String DATE_DEBUT, String DATE_FIN, String TYPE_CODE, String STATUT_CODE, String CATEGORIE_CODE, String CREATEUR_CODE, String DATE_CREATION, double INACTIF, String INACTIF_RAISON, String VERSION) {
        this.CHOUFOUNI_CODE = CHOUFOUNI_CODE;
        this.CHOUFOUNI_NOM = COUFOUNI_NOM;
        this.DATE_DEBUT = DATE_DEBUT;
        this.DATE_FIN = DATE_FIN;
        this.TYPE_CODE = TYPE_CODE;
        this.STATUT_CODE = STATUT_CODE;
        this.CATEGORIE_CODE = CATEGORIE_CODE;
        this.CREATEUR_CODE = CREATEUR_CODE;
        this.DATE_CREATION = DATE_CREATION;
        this.INACTIF = INACTIF;
        this.INACTIF_RAISON = INACTIF_RAISON;
        this.VERSION = VERSION;
    }

    public Choufouni(Choufouni choufouni) {

        this.CHOUFOUNI_CODE = choufouni.getCHOUFOUNI_CODE();
        this.CHOUFOUNI_NOM = choufouni.getCHOUFOUNI_NOM();
        this.DATE_DEBUT = choufouni.getDATE_DEBUT();
        this.DATE_FIN = choufouni.getDATE_FIN();
        this.TYPE_CODE = choufouni.getTYPE_CODE();
        this.STATUT_CODE = choufouni.getSTATUT_CODE();
        this.CATEGORIE_CODE = choufouni.getCATEGORIE_CODE();
        this.CREATEUR_CODE = choufouni.getCREATEUR_CODE();
        this.DATE_CREATION = choufouni.getDATE_CREATION();
        this.INACTIF = choufouni.getINACTIF();
        this.INACTIF_RAISON = choufouni.getINACTIF_RAISON();
        this.VERSION = choufouni.getVERSION();
    }

    public Choufouni(JSONObject choufouni) {

        try {
            this.CHOUFOUNI_CODE = choufouni.getString("CHOUFOUNI_CODE");
            this.CHOUFOUNI_NOM = choufouni.getString("CHOUFOUNI_NOM");
            this.DATE_DEBUT = choufouni.getString("DATE_DEBUT");
            this.DATE_FIN = choufouni.getString("DATE_FIN");
            this.TYPE_CODE = choufouni.getString("TYPE_CODE");
            this.STATUT_CODE = choufouni.getString("STATUT_CODE");
            this.CATEGORIE_CODE = choufouni.getString("CATEGORIE_CODE");
            this.CREATEUR_CODE = choufouni.getString("CREATEUR_CODE");
            this.DATE_CREATION = choufouni.getString("DATE_CREATION");
            this.INACTIF = choufouni.getDouble("INACTIF");
            this.INACTIF_RAISON = choufouni.getString("INACTIF_RAISON");
            this.VERSION = choufouni.getString("VERSION");

        }catch(JSONException e){
            System.out.println(e.toString());
        }
    }

    public String getCHOUFOUNI_CODE() {
        return CHOUFOUNI_CODE;
    }

    public void setCHOUFOUNI_CODE(String CHOUFOUNI_CODE) {
        this.CHOUFOUNI_CODE = CHOUFOUNI_CODE;
    }

    public String getCHOUFOUNI_NOM() {
        return CHOUFOUNI_NOM;
    }

    public void setCHOUFOUNI_NOM(String COUFOUNI_NOM) {
        this.CHOUFOUNI_NOM = COUFOUNI_NOM;
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

    public double getINACTIF() {
        return INACTIF;
    }

    public void setINACTIF(double INACTIF) {
        this.INACTIF = INACTIF;
    }

    public String getINACTIF_RAISON() {
        return INACTIF_RAISON;
    }

    public void setINACTIF_RAISON(String INACTIF_RAISON) {
        this.INACTIF_RAISON = INACTIF_RAISON;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }
}
