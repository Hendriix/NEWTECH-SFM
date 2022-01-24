package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TONPC on 12/01/2018.
 */

public class ListePrixLigne {

    private int LISTEPRIXLIGNE_CODE;
    private String LISTEPRIX_CODE;
    private String ARTICLE_CODE;
    private String UNITE_CODE;
    private double ARTICLE_PRIX;
    private String VERSION;

    public ListePrixLigne(int LISTEPRIXLIGNE_CODE, String LISTEPRIX_CODE, String ARTICLE_CODE, String UNITE_CODE, double ARTICLE_PRIX, String VERSION) {
        this.LISTEPRIXLIGNE_CODE = LISTEPRIXLIGNE_CODE;
        this.LISTEPRIX_CODE = LISTEPRIX_CODE;
        this.ARTICLE_CODE = ARTICLE_CODE;
        this.UNITE_CODE = UNITE_CODE;
        this.ARTICLE_PRIX = ARTICLE_PRIX;
        this.VERSION = VERSION;
    }

    public ListePrixLigne() {
    }

    public ListePrixLigne(ListePrixLigne listePrixLigne) {

        this.LISTEPRIXLIGNE_CODE = listePrixLigne.getLISTEPRIXLIGNE_CODE();
        this.LISTEPRIX_CODE = listePrixLigne.getLISTEPRIX_CODE();
        this.ARTICLE_CODE = listePrixLigne.getARTICLE_CODE();
        this.UNITE_CODE = listePrixLigne.getUNITE_CODE();
        this.ARTICLE_PRIX = listePrixLigne.getARTICLE_PRIX();
        this.VERSION = listePrixLigne.getVERSION();

    }

    public ListePrixLigne(JSONObject listePrixLigne) {

        try {

            this.LISTEPRIXLIGNE_CODE = listePrixLigne.getInt("LISTEPRIXLIGNE_CODE");
            this.LISTEPRIX_CODE = listePrixLigne.getString("LISTEPRIX_CODE");
            this.ARTICLE_CODE = listePrixLigne.getString("ARTICLE_CODE");
            this.UNITE_CODE = listePrixLigne.getString("UNITE_CODE");
            this.ARTICLE_PRIX = listePrixLigne.getDouble("ARTICLE_PRIX");
            this.VERSION = listePrixLigne.getString("VERSION");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getLISTEPRIXLIGNE_CODE() {
        return LISTEPRIXLIGNE_CODE;
    }

    public void setLISTEPRIXLIGNE_CODE(int LISTEPRIXLIGNE_CODE) {
        this.LISTEPRIXLIGNE_CODE = LISTEPRIXLIGNE_CODE;
    }

    public String getLISTEPRIX_CODE() {
        return LISTEPRIX_CODE;
    }

    public void setLISTEPRIX_CODE(String LISTEPRIX_CODE) {
        this.LISTEPRIX_CODE = LISTEPRIX_CODE;
    }

    public String getARTICLE_CODE() {
        return ARTICLE_CODE;
    }

    public void setARTICLE_CODE(String ARTICLE_CODE) {
        this.ARTICLE_CODE = ARTICLE_CODE;
    }

    public String getUNITE_CODE() {
        return UNITE_CODE;
    }

    public void setUNITE_CODE(String UNITE_CODE) {
        this.UNITE_CODE = UNITE_CODE;
    }

    public double getARTICLE_PRIX() {
        return ARTICLE_PRIX;
    }

    public void setARTICLE_PRIX(double ARTICLE_PRIX) {
        this.ARTICLE_PRIX = ARTICLE_PRIX;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    @Override
    public String toString() {
        return "ListePrixLigne{" +
                "LISTEPRIXLIGNE_CODE='" + LISTEPRIXLIGNE_CODE + '\'' +
                ", LISTEPRIX_CODE='" + LISTEPRIX_CODE + '\'' +
                ", ARTICLE_CODE='" + ARTICLE_CODE + '\'' +
                ", UNITE_CODE='" + UNITE_CODE + '\'' +
                ", ARTICLE_PRIX=" + ARTICLE_PRIX +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}
