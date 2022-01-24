package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

public class Stock {

    private String STOCK_CODE;
    private String STOCK_NOM;
    private String CLIENT_CODE;
    private String DATE_CREATION;
    private String DESCRIPTION;
    private String STATUT_CODE;
    private String TYPE_CODE;
    private String CATEGORIE_CODE;
    private String COMMENTAIRE;
    private int GERABLE;
    private String VERSION;


    public Stock(String STOCK_CODE, String STOCK_NOM, String CLIENT_CODE, String DATE_CREATION, String DESCRIPTION, String STATUT_CODE, String TYPE_CODE, String CATEGORIE_CODE, String COMMENTAIRE, int GERABLE, String VERSION) {
        this.STOCK_CODE = STOCK_CODE;
        this.STOCK_NOM = STOCK_NOM;
        this.CLIENT_CODE = CLIENT_CODE;
        this.DATE_CREATION = DATE_CREATION;
        this.DESCRIPTION = DESCRIPTION;
        this.STATUT_CODE = STATUT_CODE;
        this.TYPE_CODE = TYPE_CODE;
        this.CATEGORIE_CODE = CATEGORIE_CODE;
        this.COMMENTAIRE = COMMENTAIRE;
        this.GERABLE = GERABLE;
        this.VERSION = VERSION;
    }

    public Stock() {
    }

    public Stock(JSONObject stock) {
        try {

            this.STOCK_CODE = stock.getString("STOCK_CODE");
            this.STOCK_NOM = stock.getString("STOCK_NOM");
            this.CLIENT_CODE = stock.getString("CLIENT_CODE");
            this.DATE_CREATION = stock.getString("DATE_CREATION");
            this.DESCRIPTION = stock.getString("DESCRIPTION");
            this.STATUT_CODE = stock.getString("STATUT_CODE");
            this.TYPE_CODE = stock.getString("TYPE_CODE");
            this.CATEGORIE_CODE = stock.getString("CATEGORIE_CODE");
            this.COMMENTAIRE = stock.getString("COMMENTAIRE");
            this.GERABLE = stock.getInt("GERABLE");
            this.VERSION = stock.getString("VERSION");

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public String getCOMMENTAIRE() {
        return COMMENTAIRE;
    }

    public void setCOMMENTAIRE(String COMMENTAIRE) {
        this.COMMENTAIRE = COMMENTAIRE;
    }

    public int getGERABLE() {
        return GERABLE;
    }

    public void setGERABLE(int GERABLE) {
        this.GERABLE = GERABLE;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "STOCK_CODE='" + STOCK_CODE + '\'' +
                ", STOCK_NOM='" + STOCK_NOM + '\'' +
                ", CLIENT_CODE='" + CLIENT_CODE + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                ", STATUT_CODE='" + STATUT_CODE + '\'' +
                ", TYPE_CODE='" + TYPE_CODE + '\'' +
                ", CATEGORIE_CODE='" + CATEGORIE_CODE + '\'' +
                ", COMMENTAIRE='" + COMMENTAIRE + '\'' +
                ", GERABLE='" + GERABLE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}
