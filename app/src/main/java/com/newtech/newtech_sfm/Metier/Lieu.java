package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by csaylani on 15/05/2018.
 */

public class Lieu {

    private String LIEU_CODE;
    private String LIEU_NOM;
    private String CLIENT_CODE;
    private String TYPE_CODE;
    private String STATUT_CODE;
    private String CATEGORIE_CODE;
    private String ADRESSE_NR;
    private String ADRESSE_RUE;
    private String ADRESSE_QUARTIER;
    private String GPS_LATITUDE;
    private String GPS_LONGITUDE;
    private String IMAGE;
    private String VERSION;

    public Lieu(String LIEU_CODE, String LIEU_NOM, String CLIENT_CODE, String TYPE_CODE, String STATUT_CODE, String CATEGORIE_CODE, String ADRESSE_NR, String ADRESSE_RUE, String ADRESSE_QUARTIER, String GPS_LATITUDE, String GPS_LONGITUDE, String IMAGE, String VERSION) {
        this.LIEU_CODE = LIEU_CODE;
        this.LIEU_NOM = LIEU_NOM;
        this.CLIENT_CODE = CLIENT_CODE;
        this.TYPE_CODE = TYPE_CODE;
        this.STATUT_CODE = STATUT_CODE;
        this.CATEGORIE_CODE = CATEGORIE_CODE;
        this.ADRESSE_NR = ADRESSE_NR;
        this.ADRESSE_RUE = ADRESSE_RUE;
        this.ADRESSE_QUARTIER = ADRESSE_QUARTIER;
        this.GPS_LATITUDE = GPS_LATITUDE;
        this.GPS_LONGITUDE = GPS_LONGITUDE;
        this.IMAGE = IMAGE;
        this.VERSION = VERSION;
    }

    public Lieu() {
    }

    public Lieu(JSONObject lieu) {
        try {

            this.LIEU_CODE  = lieu.getString("LIEU_CODE");
            this.LIEU_NOM  = lieu.getString("LIEU_NOM");
            this.CLIENT_CODE  = lieu.getString("CLIENT_CODE");
            this.TYPE_CODE  = lieu.getString("TYPE_CODE");
            this.STATUT_CODE  = lieu.getString("STATUT_CODE");
            this.CATEGORIE_CODE  = lieu.getString("CATEGORIE_CODE");
            this.ADRESSE_NR  = lieu.getString("ADRESSE_NR");
            this.ADRESSE_RUE  = lieu.getString("ADRESSE_RUE");
            this.ADRESSE_QUARTIER  = lieu.getString("ADRESSE_QUARTIER");
            this.GPS_LATITUDE  = lieu.getString("GPS_LATITUDE");
            this.GPS_LONGITUDE  = lieu.getString("GPS_LONGITUDE");
            this.IMAGE  = lieu.getString("IMAGE");
            this.VERSION  = lieu.getString("VERSION");
            

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Lieu(Lieu lieu) {

        this.LIEU_CODE  = lieu.getLIEU_CODE();
        this.LIEU_NOM  = lieu.getLIEU_NOM();
        this.CLIENT_CODE  = lieu.getCLIENT_CODE();
        this.TYPE_CODE  = lieu.getTYPE_CODE();
        this.STATUT_CODE  = lieu.getSTATUT_CODE();
        this.CATEGORIE_CODE  = lieu.getCATEGORIE_CODE();
        this.ADRESSE_NR  = lieu.getADRESSE_NR();
        this.ADRESSE_RUE  = lieu.getADRESSE_RUE();
        this.ADRESSE_QUARTIER  = lieu.getADRESSE_QUARTIER();
        this.GPS_LATITUDE  = lieu.getGPS_LATITUDE();
        this.GPS_LONGITUDE  = lieu.getGPS_LONGITUDE();
        this.IMAGE  = lieu.getIMAGE();
        this.VERSION  = lieu.getVERSION();
    }


    public String getLIEU_CODE() {
        return LIEU_CODE;
    }

    public void setLIEU_CODE(String LIEU_CODE) {
        this.LIEU_CODE = LIEU_CODE;
    }

    public String getLIEU_NOM() {
        return LIEU_NOM;
    }

    public void setLIEU_NOM(String LIEU_NOM) {
        this.LIEU_NOM = LIEU_NOM;
    }

    public String getCLIENT_CODE() {
        return CLIENT_CODE;
    }

    public void setCLIENT_CODE(String CLIENT_CODE) {
        this.CLIENT_CODE = CLIENT_CODE;
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

    public String getADRESSE_NR() {
        return ADRESSE_NR;
    }

    public void setADRESSE_NR(String ADRESSE_NR) {
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

    public String getGPS_LATITUDE() {
        return GPS_LATITUDE;
    }

    public void setGPS_LATITUDE(String GPS_LATITUDE) {
        this.GPS_LATITUDE = GPS_LATITUDE;
    }

    public String getGPS_LONGITUDE() {
        return GPS_LONGITUDE;
    }

    public void setGPS_LONGITUDE(String GPS_LONGITUDE) {
        this.GPS_LONGITUDE = GPS_LONGITUDE;
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

    @Override
    public String toString() {
        return "Lieu{" +
                "LIEU_CODE='" + LIEU_CODE + '\'' +
                ", LIEU_NOM='" + LIEU_NOM + '\'' +
                ", TYPE_CODE='" + TYPE_CODE + '\'' +
                ", STATUT_CODE='" + STATUT_CODE + '\'' +
                ", CATEGORIE_CODE='" + CATEGORIE_CODE + '\'' +
                ", ADRESSE_NR='" + ADRESSE_NR + '\'' +
                ", ADRESSE_RUE='" + ADRESSE_RUE + '\'' +
                ", ADRESSE_QUARTIER='" + ADRESSE_QUARTIER + '\'' +
                ", GPS_LATITUDE='" + GPS_LATITUDE + '\'' +
                ", GPS_LONGITUDE='" + GPS_LONGITUDE + '\'' +
                ", IMAGE='" + IMAGE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}
