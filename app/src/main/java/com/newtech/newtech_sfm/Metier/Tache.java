package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TONPC on 10/03/2017.
 */

public class Tache {

    private int ID;
    private String TACHE_CODE;
    private String TACHE_NOM;
    private String TACHE_DATE;
    private int FREQUENCE;
    private String UTILISATEUR_CODE;
    private String AFFECTATION_TYPE;
    private String AFFECTATION_VALEUR;
    private String TYPE_CODE;
    private String STATUT_CODE;
    private String CATEGORIE_CODE;
    private String DATE_CREATION;
    private String CREATEUR_CODE;
    private int RANG;
    private int PROGRESSION;
    private String VERSION;

    public Tache(){

    }

    public Tache(JSONObject p) {
        try {

            this.ID=p.getInt("ID");
            this.TACHE_CODE=p.getString("TACHE_CODE");
            this.TACHE_NOM=p.getString("TACHE_NOM");
            this.TACHE_DATE=p.getString("TACHE_DATE");
            this.FREQUENCE=p.getInt("FREQUENCE");
            this.UTILISATEUR_CODE=p.getString("UTILISATEUR_CODE");
            this.AFFECTATION_TYPE=p.getString("AFFECTATION_TYPE");
            this.AFFECTATION_VALEUR=p.getString("AFFECTATION_VALEUR");
            this.TYPE_CODE=p.getString("TYPE_CODE");
            this.STATUT_CODE=p.getString("STATUT_CODE");
            this.CATEGORIE_CODE=p.getString("CATEGORIE_CODE");
            this.DATE_CREATION=p.getString("DATE_CREATION");
            this.CREATEUR_CODE=p.getString("CREATEUR_CODE");
            this.RANG=p.getInt("RANG");
            this.PROGRESSION=p.getInt("PROGRESSION");
            this.VERSION=p.getString("VERSION");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Tache(Tache tache){
        try {

            this.ID=tache.getID();
            this.TACHE_CODE=tache.getTACHE_CODE();
            this.TACHE_NOM=tache.getTACHE_NOM();
            this.TACHE_DATE=tache.getTACHE_DATE();
            this.FREQUENCE=tache.getFREQUENCE();
            this.UTILISATEUR_CODE=tache.getUTILISATEUR_CODE();
            this.AFFECTATION_TYPE=tache.getAFFECTATION_TYPE();
            this.AFFECTATION_VALEUR=tache.getAFFECTATION_VALEUR();
            this.TYPE_CODE=tache.getTYPE_CODE();
            this.STATUT_CODE=tache.getSTATUT_CODE();
            this.CATEGORIE_CODE=tache.getCATEGORIE_CODE();
            this.DATE_CREATION=tache.getDATE_CREATION();
            this.CREATEUR_CODE=tache.getCREATEUR_CODE();
            this.RANG=tache.getRANG();
            this.PROGRESSION=tache.getPROGRESSION();
            this.VERSION=tache.getVERSION();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getID() {
        return ID;
    }

    public String getTACHE_CODE() {
        return TACHE_CODE;
    }

    public String getTACHE_NOM() {
        return TACHE_NOM;
    }

    public String getTACHE_DATE() {
        return TACHE_DATE;
    }

    public int getFREQUENCE() {
        return FREQUENCE;
    }

    public String getUTILISATEUR_CODE() {
        return UTILISATEUR_CODE;
    }

    public String getAFFECTATION_TYPE() {
        return AFFECTATION_TYPE;
    }

    public String getAFFECTATION_VALEUR() {
        return AFFECTATION_VALEUR;
    }

    public String getTYPE_CODE() {
        return TYPE_CODE;
    }

    public String getSTATUT_CODE() {
        return STATUT_CODE;
    }

    public String getCATEGORIE_CODE() {
        return CATEGORIE_CODE;
    }

    public String getDATE_CREATION() {
        return DATE_CREATION;
    }

    public String getCREATEUR_CODE() {
        return CREATEUR_CODE;
    }

    public int getRANG() {
        return RANG;
    }

    public int getPROGRESSION() {
        return PROGRESSION;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTACHE_CODE(String TACHE_CODE) {
        this.TACHE_CODE = TACHE_CODE;
    }

    public void setTACHE_NOM(String TACHE_NOM) {
        this.TACHE_NOM = TACHE_NOM;
    }

    public void setTACHE_DATE(String TACHE_DATE) {
        this.TACHE_DATE = TACHE_DATE;
    }

    public void setFREQUENCE(int FREQUENCE) {
        this.FREQUENCE = FREQUENCE;
    }

    public void setUTILISATEUR_CODE(String UTILISATEUR_CODE) {
        this.UTILISATEUR_CODE = UTILISATEUR_CODE;
    }

    public void setAFFECTATION_TYPE(String AFFECTATION_TYPE) {
        this.AFFECTATION_TYPE = AFFECTATION_TYPE;
    }

    public void setAFFECTATION_VALEUR(String AFFECTATION_VALEUR) {
        this.AFFECTATION_VALEUR = AFFECTATION_VALEUR;
    }

    public void setTYPE_CODE(String TYPE_CODE) {
        this.TYPE_CODE = TYPE_CODE;
    }

    public void setSTATUT_CODE(String STATUT_CODE) {
        this.STATUT_CODE = STATUT_CODE;
    }

    public void setCATEGORIE_CODE(String CATEGORIE_CODE) {
        this.CATEGORIE_CODE = CATEGORIE_CODE;
    }

    public void setDATE_CREATION(String DATE_CREATION) {
        this.DATE_CREATION = DATE_CREATION;
    }

    public void setCREATEUR_CODE(String CREATEUR_CODE) {
        this.CREATEUR_CODE = CREATEUR_CODE;
    }

    public void setRANG(int RANG) {
        this.RANG = RANG;
    }

    public void setPROGRESSION(int PROGRESSION) {
        this.PROGRESSION = PROGRESSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    @Override
    public String toString() {
        return "Tache{" +
                "ID='" + ID + "\n" +
                ", TACHE_CODE='" + TACHE_CODE + "\n" +
                ", TACHE_NOM='" + TACHE_NOM + "\n" +
                ", TACHE_DATE='" + TACHE_DATE + "\n" +
                ", FREQUENCE=" + FREQUENCE + "\n" +
                ", UTILISATEUR_CODE='" + UTILISATEUR_CODE + "\n" +
                ", AFFECTATION_TYPE='" + AFFECTATION_TYPE + "\n" +
                ", AFFECTATION_VALEUR='" + AFFECTATION_VALEUR + "\n" +
                ", TYPE_CODE='" + TYPE_CODE + "\n" +
                ", STATUT_CODE='" + STATUT_CODE + "\n"+
                ", CATEGORIE_CODE='" + CATEGORIE_CODE + "\n" +
                ", DATE_CREATION='" + DATE_CREATION + "\n" +
                ", CREATEUR_CODE='" + CREATEUR_CODE + "\n" +
                ", RANG=" + RANG + "\n" +
                ", PROGRESSION=" + PROGRESSION + "\n" +
                ", VERSION='" + VERSION + "\n" +
                '}';
    }
}
