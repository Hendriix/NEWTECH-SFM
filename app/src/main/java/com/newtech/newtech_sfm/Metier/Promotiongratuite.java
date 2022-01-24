package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by stagiaireit2 on 22/07/2016.
 */
public class Promotiongratuite {
    private int ID;
    private String PROMO_CODE;
    private String CATEGORIE_CODE;
    private String TYPE_CODE;
    private String VALEUR_GR;
    private String DATE_CREATION;
    private String VERSION;

    public Promotiongratuite() {
    }

    public Promotiongratuite(JSONObject promotiongratuite) {
        try {
            this.PROMO_CODE = promotiongratuite.getString("PROMO_CODE");
            this.CATEGORIE_CODE = promotiongratuite.getString("CATEGORIE_CODE");
            this.TYPE_CODE = promotiongratuite.getString("TYPE_CODE");
            this.VALEUR_GR = promotiongratuite.getString("VALEUR_GR");
            this.DATE_CREATION = promotiongratuite.getString("DATE_CREATION");
            this.VERSION =promotiongratuite.getString("VERSION");
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
    public String getVALEUR_GR() {
        return VALEUR_GR;
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
    public void setVALEUR_GR(String VALEUR_GR) {
        this.VALEUR_GR = VALEUR_GR;
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
        return
                "PROMO_CODE|" + PROMO_CODE  +
                        "@CATEGORIE_CODE|" + CATEGORIE_CODE  +
                        "@TYPE_CODE|" + TYPE_CODE  +
                        "@VALEUR_GR|" + VALEUR_GR  +
                        "@DATE_CREATION|" + DATE_CREATION +
                        "@VERSION|" + VERSION;
    }

}
