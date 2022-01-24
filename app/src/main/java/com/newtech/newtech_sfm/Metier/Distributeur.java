package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TONPC on 28/07/2017.
 */

public class Distributeur {

    private String DISTRIBUTEUR_CODE;
    private String DISTRIBUTEUR_NOM;
    private String REGION_CODE;
    private String ZONE_CODE;
    private String GERANT_NOM;
    private String GERANT_TELEPHONE;
    private String GERANT_FIXE;
    private String GERANT_EMAIL;
    private int ADRESSE_NR;
    private String ADRESSE_RUE;
    private String ADRESSE_QUARTIER;
    private String DATE_CREATION;
    private String CREATEUR_CODE;
    private int INACTIF;
    private String INACTIF_RAISON;
    private String CODAGE;
    private String CHANNEL_CODE;
    private String RANG;
    private String DISTRIBUTEUR_ENTETE;
    private String DISTRIBUTEUR_PIED;
    private String VERSION;

    public Distributeur(){

    }


    public Distributeur(Distributeur distributeur) {

        this.DISTRIBUTEUR_CODE = distributeur.getDISTRIBUTEUR_CODE();
        this.DISTRIBUTEUR_NOM = distributeur.getDISTRIBUTEUR_NOM();
        this.REGION_CODE = distributeur.getREGION_CODE();
        this.ZONE_CODE = distributeur.getZONE_CODE();
        this.GERANT_NOM = distributeur.getGERANT_NOM();
        this.GERANT_TELEPHONE = distributeur.getGERANT_TELEPHONE();
        this.GERANT_FIXE = distributeur.getGERANT_FIXE();
        this.GERANT_EMAIL = distributeur.getGERANT_EMAIL();
        this.ADRESSE_NR = distributeur.getADRESSE_NR();
        this.ADRESSE_RUE = distributeur.getADRESSE_RUE();
        this.ADRESSE_QUARTIER = distributeur.getADRESSE_QUARTIER();
        this.DATE_CREATION = distributeur.getDATE_CREATION();
        this.CREATEUR_CODE = distributeur.getCREATEUR_CODE();
        this.INACTIF = distributeur.getINACTIF();
        this.INACTIF_RAISON = distributeur.getINACTIF_RAISON();
        this.CODAGE = distributeur.getCODAGE();
        this.CHANNEL_CODE = distributeur.getCHANNEL_CODE();
        this.RANG = distributeur.getRANG();
        this.DISTRIBUTEUR_ENTETE=distributeur.getDISTRIBUTEUR_ENTETE();
        this.DISTRIBUTEUR_PIED=distributeur.getDISTRIBUTEUR_PIED();
        this.VERSION=distributeur.getVERSION();
    }

    public Distributeur(JSONObject distributeur) {
        try {
            this.DISTRIBUTEUR_CODE = distributeur.getString("DISTRIBUTEUR_CODE");
            this.DISTRIBUTEUR_NOM = distributeur.getString("DISTRIBUTEUR_NOM");
            this.REGION_CODE = distributeur.getString("REGION_CODE");
            this.ZONE_CODE = distributeur.getString("ZONE_CODE");
            this.GERANT_NOM = distributeur.getString("GERANT_NOM");
            this.GERANT_TELEPHONE = distributeur.getString("GERANT_TELEPHONE");
            this.GERANT_FIXE = distributeur.getString("GERANT_FIXE");
            this.GERANT_EMAIL = distributeur.getString("GERANT_EMAIL");
            this.ADRESSE_NR = distributeur.getInt("ADRESSE_NR");
            this.ADRESSE_RUE = distributeur.getString("ADRESSE_RUE");
            this.ADRESSE_QUARTIER = distributeur.getString("ADRESSE_QUARTIER");
            this.DATE_CREATION = distributeur.getString("DATE_CREATION");
            this.CREATEUR_CODE = distributeur.getString("CREATEUR_CODE");
            this.INACTIF = distributeur.getInt("INACTIF");
            this.INACTIF_RAISON = distributeur.getString("INACTIF_RAISON");
            this.CODAGE = distributeur.getString("CODAGE");
            this.CHANNEL_CODE = distributeur.getString("CHANNEL_CODE");
            this.RANG = distributeur.getString("RANG");
            this.DISTRIBUTEUR_ENTETE=distributeur.getString("DISTRIBUTEUR_ENTETE");
            this.DISTRIBUTEUR_PIED=distributeur.getString("DISTRIBUTEUR_PIED");
            this.VERSION=distributeur.getString("VERSION");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getDISTRIBUTEUR_CODE() {
        return DISTRIBUTEUR_CODE;
    }

    public void setDISTRIBUTEUR_CODE(String DISTRIBUTEUR_CODE) {
        this.DISTRIBUTEUR_CODE = DISTRIBUTEUR_CODE;
    }

    public String getDISTRIBUTEUR_NOM() {
        return DISTRIBUTEUR_NOM;
    }

    public void setDISTRIBUTEUR_NOM(String DISTRIBUTEUR_NOM) {
        this.DISTRIBUTEUR_NOM = DISTRIBUTEUR_NOM;
    }

    public String getREGION_CODE() {
        return REGION_CODE;
    }

    public void setREGION_CODE(String REGION_CODE) {
        this.REGION_CODE = REGION_CODE;
    }

    public String getZONE_CODE() {
        return ZONE_CODE;
    }

    public void setZONE_CODE(String ZONE_CODE) {
        this.ZONE_CODE = ZONE_CODE;
    }

    public String getGERANT_NOM() {
        return GERANT_NOM;
    }

    public void setGERANT_NOM(String GERANT_NOM) {
        this.GERANT_NOM = GERANT_NOM;
    }

    public String getGERANT_TELEPHONE() {
        return GERANT_TELEPHONE;
    }

    public void setGERANT_TELEPHONE(String GERANT_TELEPHONE) {
        this.GERANT_TELEPHONE = GERANT_TELEPHONE;
    }

    public String getGERANT_FIXE() {
        return GERANT_FIXE;
    }

    public void setGERANT_FIXE(String GERANT_FIXE) {
        this.GERANT_FIXE = GERANT_FIXE;
    }

    public String getGERANT_EMAIL() {
        return GERANT_EMAIL;
    }

    public void setGERANT_EMAIL(String GERANT_EMAIL) {
        this.GERANT_EMAIL = GERANT_EMAIL;
    }

    public int getADRESSE_NR() {
        return ADRESSE_NR;
    }

    public void setADRESSE_NR(int ADRESSE_NR) {
        this.ADRESSE_NR = ADRESSE_NR;
    }

    public String getADRESSE_RUE() {
        return ADRESSE_RUE;
    }

    public void setADRESSE_RUE(String ADRESSE_RUE) {
        this.ADRESSE_RUE = ADRESSE_RUE;
    }

    public String getADRESSE_QUARTIER() {
        return ADRESSE_QUARTIER;
    }

    public void setADRESSE_QUARTIER(String ADRESSE_QUARTIER) {
        this.ADRESSE_QUARTIER = ADRESSE_QUARTIER;
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

    public String getCODAGE() {
        return CODAGE;
    }

    public void setCODAGE(String CODAGE) {
        this.CODAGE = CODAGE;
    }

    public String getCHANNEL_CODE() {
        return CHANNEL_CODE;
    }

    public void setCHANNEL_CODE(String CHANNEL_CODE) {
        this.CHANNEL_CODE = CHANNEL_CODE;
    }

    public String getRANG() {
        return RANG;
    }

    public void setRANG(String RANG) {
        this.RANG = RANG;
    }

    public String getDISTRIBUTEUR_ENTETE() {
        return DISTRIBUTEUR_ENTETE;
    }

    public void setDISTRIBUTEUR_ENTETE(String DISTRIBUTEUR_ENTETE) {
        this.DISTRIBUTEUR_ENTETE = DISTRIBUTEUR_ENTETE;
    }

    public String getDISTRIBUTEUR_PIED() {
        return DISTRIBUTEUR_PIED;
    }

    public void setDISTRIBUTEUR_PIED(String DISTRIBUTEUR_PIED) {
        this.DISTRIBUTEUR_PIED = DISTRIBUTEUR_PIED;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    @Override
    public String toString() {
        return "Distributeur{" +
                "DISTRIBUTEUR_CODE='" + DISTRIBUTEUR_CODE + '\'' +
                ", DISTRIBUTEUR_NOM='" + DISTRIBUTEUR_NOM + '\'' +
                ", REGION_CODE='" + REGION_CODE + '\'' +
                ", ZONE_CODE='" + ZONE_CODE + '\'' +
                ", GERANT_NOM='" + GERANT_NOM + '\'' +
                ", GERANT_TELEPHONE='" + GERANT_TELEPHONE + '\'' +
                ", GERANT_FIXE='" + GERANT_FIXE + '\'' +
                ", GERANT_EMAIL='" + GERANT_EMAIL + '\'' +
                ", ADRESSE_NR=" + ADRESSE_NR +
                ", ADRESSE_RUE='" + ADRESSE_RUE + '\'' +
                ", ADRESSE_QUARTIER='" + ADRESSE_QUARTIER + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", CREATEUR_CODE='" + CREATEUR_CODE + '\'' +
                ", INACTIF=" + INACTIF +
                ", INACTIF_RAISON='" + INACTIF_RAISON + '\'' +
                ", CODAGE='" + CODAGE + '\'' +
                ", CHANNEL_CODE='" + CHANNEL_CODE + '\'' +
                ", RANG='" + RANG + '\'' +
                ", DISTRIBUTEUR_ENTETE='" + DISTRIBUTEUR_ENTETE + '\'' +
                ", DISTRIBUTEUR_PIED='" + DISTRIBUTEUR_PIED + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}
