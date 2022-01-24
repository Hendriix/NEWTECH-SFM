package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by stagiaireit2 on 22/07/2016.
 */
public class Promotionpalier {
    private int PROMO_ID;
    private String PROMO_CODE;
    private String PROMO_BASE;
    private String VALEUR_PBASE;
    private String VALEUR_PROMO;
    private String PROMO_CATEGORIE;
    private String PROMO_GRATUITE;
    private String DATE_CREATION;
    private String VERSION;


    public Promotionpalier() {
    }

    public Promotionpalier(JSONObject promotionpalier) {
        try {
            this.PROMO_CODE = promotionpalier.getString("PROMO_CODE");
            this.PROMO_BASE = promotionpalier.getString("PROMO_BASE");
            this.VALEUR_PBASE = promotionpalier.getString("VALEUR_PBASE");
            this.VALEUR_PROMO = promotionpalier.getString("VALEUR_PROMO");
            this.PROMO_CATEGORIE = promotionpalier.getString("PROMO_CATEGORIE");
            this.PROMO_GRATUITE = promotionpalier.getString("PROMO_GRATUITE");
            this.DATE_CREATION = promotionpalier.getString("DATE_CREATION");
            this.VERSION =promotionpalier.getString("VERSION");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getPROMO_CODE() {
        return PROMO_CODE;
    }
    public String getPROMO_BASE() {
        return PROMO_BASE;
    }
    public String getVALEUR_PBASE() {
        return VALEUR_PBASE;
    }
    public String getVALEUR_PROMO() {
        return VALEUR_PROMO;
    }
    public String getPROMO_CATEGORIE() {
        return PROMO_CATEGORIE;
    }
    public String getPROMO_GRATUITE() {
        return PROMO_GRATUITE;
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
    public void setPROMO_BASE(String PROMO_BASE) {
        this.PROMO_BASE = PROMO_BASE;
    }
    public void setVALEUR_PBASE(String VALEUR_PBASE) {
        this.VALEUR_PBASE = VALEUR_PBASE;
    }
    public void setVALEUR_PROMO(String VALEUR_PROMO) {
        this.VALEUR_PROMO = VALEUR_PROMO;
    }
    public void setPROMO_CATEGORIE(String PROMO_CATEGORIE) {
        this.PROMO_CATEGORIE = PROMO_CATEGORIE;
    }
    public void setPROMO_GRATUITE(String PROMO_GRATUITE) {
        this.PROMO_GRATUITE = PROMO_GRATUITE;
    }
    public void setDATE_CREATION(String DATE_CREATION) {
        this.DATE_CREATION = DATE_CREATION;
    }
    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public int getPROMO_ID() {
        return PROMO_ID;
    }

    public void setPROMO_ID(int PROMO_ID) {
        this.PROMO_ID = PROMO_ID;
    }

    @Override
    public String toString() {
        return
                "PROMO_ID|" + PROMO_ID  +
                        "\n@PROMO_CODE :" + PROMO_CODE  +
                        "\n@PROMO_BASE :" + PROMO_BASE  +
                        "\n@VALEUR_PBASE :" + VALEUR_PBASE  +
                        "\n@VALEUR_PROMO :" + VALEUR_PROMO  +
                        "\n@PROMO_CATEGORIE :" + PROMO_CATEGORIE  +
                        "\n@PROMO_GRATUITE:" + PROMO_GRATUITE  +
                        "\n@DATE_CREATION:" + DATE_CREATION +
                        "\n@VERSION :" + VERSION;
    }

}
