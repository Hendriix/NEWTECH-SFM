package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by stagiaireit2 on 22/07/2016.
 */
public class Promotionarticle {

    private int ID;
    private String PROMO_CODE;
    private String CATEGORIE_CODE;
    private String TYPE_CODE;
    private String VALEUR_AR;
    private String DATE_CREATION;
    private String VERSION;

    public Promotionarticle(JSONObject promotionarticle) {
        try {
            //this.ID = promotionarticle.getInt("ID");
            this.PROMO_CODE = promotionarticle.getString("PROMO_CODE");
            this.CATEGORIE_CODE = promotionarticle.getString("CATEGORIE_CODE");
            this.TYPE_CODE = promotionarticle.getString("TYPE_CODE");
            this.VALEUR_AR = promotionarticle.getString("VALEUR_AR");
            this.DATE_CREATION = promotionarticle.getString("DATE_CREATION");
            this.VERSION =promotionarticle.getString("VERSION");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Promotionarticle(Promotionarticle promotionarticle) {

        //this.ID = promotionarticle.getID();
        this.PROMO_CODE = promotionarticle.getPROMO_CODE();
        this.CATEGORIE_CODE = promotionarticle.getCATEGORIE_CODE();
        this.TYPE_CODE = promotionarticle.getTYPE_CODE();
        this.VALEUR_AR = promotionarticle.getVALEUR_AR();
        this.DATE_CREATION = promotionarticle.getDATE_CREATION();
        this.VERSION=promotionarticle.getVERSION();
    }

    public Promotionarticle() {
    }

    public String getPROMO_CODE() {
        return PROMO_CODE;
    }
    public String getCATEGORIE_CODE() {
        return CATEGORIE_CODE;
    }
    public String getTYPE_CODE() {
        return TYPE_CODE;
    }
    public String getVALEUR_AR() {
        return VALEUR_AR;
    }
    public String getDATE_CREATION() {
        return DATE_CREATION;
    }
    public String getVERSION() {
        return VERSION;
    }


    public void setPROMO_CODE(String PROMO_CODE) {
        this.PROMO_CODE = PROMO_CODE;
    }
    public void setCATEGORIE_CODE(String CATEGORIE_CODE) {
        this.CATEGORIE_CODE = CATEGORIE_CODE;
    }
    public void setTYPE_CODE(String TYPE_CODE) {
        this.TYPE_CODE = TYPE_CODE;
    }
    public void setVALEUR_AR(String VALEUR_AR) {
        this.VALEUR_AR = VALEUR_AR;
    }
    public void setDATE_CREATION(String DATE_CREATION) {
        this.DATE_CREATION = DATE_CREATION;
    }
    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Promotionarticle{" +
                "ID=" + ID +
                ", PROMO_CODE='" + PROMO_CODE + '\'' +
                ", CATEGORIE_CODE='" + CATEGORIE_CODE + '\'' +
                ", TYPE_CODE='" + TYPE_CODE + '\'' +
                ", VALEUR_AR='" + VALEUR_AR + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}
