package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by stagiaireit2 on 02/08/2016.
 */
public class LivraisonGratuite {

    String
    GRATUITE_CODE,
    LIVRAISON_CODE,
    PROMO_CODE,
    VERSION,
    ARTICLE_CODE;
    double
    QUANTITE;

    public LivraisonGratuite() {
    }


    public LivraisonGratuite(JSONObject p) {
        try {
            this.GRATUITE_CODE=p.getString("GRATUITE_CODE");
            this.LIVRAISON_CODE=p.getString("LIVRAISON_CODE");
            this.PROMO_CODE=p.getString("PROMO_CODE");
            this.ARTICLE_CODE=p.getString("ARTICLE_CODE");
            this.QUANTITE=p.getDouble("QUANTITE");
            this.VERSION=p.getString("VERSION");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getGRATUITE_CODE() {
        return GRATUITE_CODE;
    }

    public String getLIVRAISON_CODE() {
        return LIVRAISON_CODE;
    }

    public String getPROMO_CODE() {
        return PROMO_CODE;
    }

    public String getARTICLE_CODE() {
        return ARTICLE_CODE;
    }

    public double getQUANTITE() {
        return QUANTITE;
    }

    public String getVERSION() {
        return VERSION;
    }


    public void setGRATUITE_CODE(String GRATUITE_CODE) {
        this.GRATUITE_CODE = GRATUITE_CODE;
    }

    public void setLIVRAISON_CODE(String LIVRAISON_CODE) {
        this.LIVRAISON_CODE = LIVRAISON_CODE;
    }

    public void setPROMO_CODE(String PROMO_CODE) {
        this.PROMO_CODE = PROMO_CODE;
    }

    public void setARTICLE_CODE(String ARTICLE_CODE) {
        this.ARTICLE_CODE = ARTICLE_CODE;
    }

    public void setQUANTITE(double QUANTITE) {
        this.QUANTITE = QUANTITE;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    @Override
    public String toString() {
        return "LivraisonGratuite{" +
                "GRATUITE_CODE='" + GRATUITE_CODE + '\'' +
                ", LIVRAISON_CODE='" + LIVRAISON_CODE + '\'' +
                ", PROMO_CODE='" + PROMO_CODE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                ", ARTICLE_CODE='" + ARTICLE_CODE + '\'' +
                ", QUANTITE=" + QUANTITE +
                '}';
    }
}
