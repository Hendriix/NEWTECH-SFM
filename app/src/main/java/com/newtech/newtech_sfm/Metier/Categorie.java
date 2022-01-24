package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by csaylani on 15/05/2018.
 */

public class Categorie {

    private String CATEGORIE_CODE;
    private String CATEGORIE_NOM;
    private String CATEGORIE_CATEGORIE;
    private String DESCRIPTION;
    private String VERSION;

    public Categorie(String CATEGORIE_CODE, String CATEGORIE_NOM, String CATEGORIE_CATEGORIE, String DESCRIPTION, String VERSION) {
        this.CATEGORIE_CODE = CATEGORIE_CODE;
        this.CATEGORIE_NOM = CATEGORIE_NOM;
        this.CATEGORIE_CATEGORIE = CATEGORIE_CATEGORIE;
        this.DESCRIPTION = DESCRIPTION;
        this.VERSION = VERSION;
    }

    public Categorie() {
    }

    public Categorie(JSONObject categorie) {
        try {

            this.CATEGORIE_CODE  = categorie.getString("CATEGORIE_CODE");
            this.CATEGORIE_NOM  = categorie.getString("CATEGORIE_NOM");
            this.CATEGORIE_CATEGORIE  = categorie.getString("CATEGORIE_CATEGORIE");
            this.DESCRIPTION  = categorie.getString("DESCRIPTION");
            this.VERSION  = categorie.getString("VERSION");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Categorie(Categorie categorie) {

            this.CATEGORIE_CODE  = categorie.getCATEGORIE_CODE();
            this.CATEGORIE_NOM  = categorie.getCATEGORIE_NOM();
            this.CATEGORIE_CATEGORIE  = categorie.getCATEGORIE_CATEGORIE();
            this.DESCRIPTION  = categorie.getDESCRIPTION();
            this.VERSION  = categorie.getVERSION();
    }


    public String getCATEGORIE_CODE() {
        return CATEGORIE_CODE;
    }

    public void setCATEGORIE_CODE(String CATEGORIE_CODE) {
        this.CATEGORIE_CODE = CATEGORIE_CODE;
    }

    public String getCATEGORIE_NOM() {
        return CATEGORIE_NOM;
    }

    public void setCATEGORIE_NOM(String CATEGORIE_NOM) {
        this.CATEGORIE_NOM = CATEGORIE_NOM;
    }

    public String getCATEGORIE_CATEGORIE() {
        return CATEGORIE_CATEGORIE;
    }

    public void setCATEGORIE_CATEGORIE(String CATEGORIE_CATEGORIE) {
        this.CATEGORIE_CATEGORIE = CATEGORIE_CATEGORIE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "CATEGORIE_CODE='" + CATEGORIE_CODE + '\'' +
                ", CATEGORIE_NOM='" + CATEGORIE_NOM + '\'' +
                ", CATEGORIE_CATEGORIE='" + CATEGORIE_CATEGORIE + '\'' +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}
