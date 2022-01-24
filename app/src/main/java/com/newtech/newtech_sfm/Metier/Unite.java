package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by csaylani on 15/05/2018.
 */

public class Unite {

    private String UNITE_CODE;
    private String UNITE_NOM;
    private String DESCRIPTION;
    private String TYPE_CODE;
    private String STATUT_CODE;
    private String CATEGORIE_CODE;
    private String INACTIF;
    private String INACTIF_RAISON;
    private String ARTICLE_CODE;
    private double FACTEUR_CONVERSION;
    private String IMAGE;
    private double LITTRAGE;
    private double POIDKG;
    private String VERSION;


    public Unite(String UNITE_CODE, String UNITE_NOM, String DESCRIPTION, String TYPE_CODE, String STATUT_CODE, String CATEGORIE_CODE, String INACTIF, String INACTIF_RAISON, String ARTICLE_CODE, double FACTEUR_CONVERSION, String IMAGE, double LITTRAGE, double POIDKG, String VERSION) {
        this.UNITE_CODE = UNITE_CODE;
        this.UNITE_NOM = UNITE_NOM;
        this.DESCRIPTION = DESCRIPTION;
        this.TYPE_CODE = TYPE_CODE;
        this.STATUT_CODE = STATUT_CODE;
        this.CATEGORIE_CODE = CATEGORIE_CODE;
        this.INACTIF = INACTIF;
        this.INACTIF_RAISON = INACTIF_RAISON;
        this.ARTICLE_CODE = ARTICLE_CODE;
        this.FACTEUR_CONVERSION = FACTEUR_CONVERSION;
        this.IMAGE = IMAGE;
        this.LITTRAGE = LITTRAGE;
        this.POIDKG = POIDKG;
        this.VERSION = VERSION;
    }

    public Unite() {
    }

    public Unite(JSONObject unite) {
        try {

            this.UNITE_CODE  = unite.getString("UNITE_CODE");
            this.UNITE_NOM  = unite.getString("UNITE_NOM");
            this.DESCRIPTION  = unite.getString("DESCRIPTION");
            this.TYPE_CODE  = unite.getString("TYPE_CODE");
            this.STATUT_CODE  = unite.getString("STATUT_CODE");
            this.CATEGORIE_CODE  = unite.getString("CATEGORIE_CODE");
            this.INACTIF  = unite.getString("INACTIF");
            this.INACTIF_RAISON  = unite.getString("INACTIF_RAISON");
            this.ARTICLE_CODE  = unite.getString("ARTICLE_CODE");
            this.FACTEUR_CONVERSION  = unite.getDouble("FACTEUR_CONVERSION");
            this.IMAGE  = unite.getString("IMAGE");
            this.LITTRAGE  = unite.getDouble("LITTRAGE");
            this.POIDKG  = unite.getDouble("POIDKG");
            this.VERSION  = unite.getString("VERSION");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Unite(Unite unite) {

        this.UNITE_CODE  = unite.getUNITE_CODE();
        this.UNITE_NOM  = unite.getUNITE_NOM();
        this.DESCRIPTION  = unite.getDESCRIPTION();
        this.TYPE_CODE  = unite.getTYPE_CODE();
        this.STATUT_CODE  = unite.getSTATUT_CODE();
        this.CATEGORIE_CODE  = unite.getCATEGORIE_CODE();
        this.INACTIF  = unite.getINACTIF();
        this.INACTIF_RAISON  = unite.getINACTIF_RAISON();
        this.ARTICLE_CODE  = unite.getARTICLE_CODE();
        this.FACTEUR_CONVERSION  = unite.getFACTEUR_CONVERSION();
        this.IMAGE  = unite.getIMAGE();
        this.LITTRAGE  = unite.getLITTRAGE();
        this.POIDKG  = unite.getPOIDKG();
        this.VERSION  = unite.getVERSION();

    }

    public String getUNITE_CODE() {
        return UNITE_CODE;
    }

    public void setUNITE_CODE(String UNITE_CODE) {
        this.UNITE_CODE = UNITE_CODE;
    }

    public String getUNITE_NOM() {
        return UNITE_NOM;
    }

    public void setUNITE_NOM(String UNITE_NOM) {
        this.UNITE_NOM = UNITE_NOM;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getINACTIF() {
        return INACTIF;
    }

    public void setINACTIF(String INACTIF) {
        this.INACTIF = INACTIF;
    }

    public String getINACTIF_RAISON() {
        return INACTIF_RAISON;
    }

    public void setINACTIF_RAISON(String INACTIF_RAISON) {
        this.INACTIF_RAISON = INACTIF_RAISON;
    }

    public String getARTICLE_CODE() {
        return ARTICLE_CODE;
    }

    public void setARTICLE_CODE(String ARTICLE_CODE) {
        this.ARTICLE_CODE = ARTICLE_CODE;
    }

    public double getFACTEUR_CONVERSION() {
        return FACTEUR_CONVERSION;
    }

    public void setFACTEUR_CONVERSION(double FACTEUR_CONVERSION) {
        this.FACTEUR_CONVERSION = FACTEUR_CONVERSION;
    }

    public String getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(String IMAGE) {
        this.IMAGE = IMAGE;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
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

    public double getLITTRAGE() {
        return LITTRAGE;
    }

    public void setLITTRAGE(double LITTRAGE) {
        this.LITTRAGE = LITTRAGE;
    }

    public double getPOIDKG() {
        return POIDKG;
    }

    public void setPOIDKG(double POIDKG) {
        this.POIDKG = POIDKG;
    }

    @Override
    public String toString() {
        return "Unite{" +
                "UNITE_CODE='" + UNITE_CODE + '\'' +
                ", UNITE_NOM='" + UNITE_NOM + '\'' +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                ", INACTIF=" + INACTIF +
                ", INACTIF_RAISON='" + INACTIF_RAISON + '\'' +
                ", ARTICLE_CODE='" + ARTICLE_CODE + '\'' +
                ", FACTEUR_CONVERSION=" + FACTEUR_CONVERSION +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}
