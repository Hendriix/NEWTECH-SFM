package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by stagiaireit2 on 02/08/2016.
 */
public class LivraisonPromotion {

   String
    LIVRAISON_PROMO_CODE,
    PROMO_CODE,
    LIVRAISON_CODE;
    int
    LIVRAISONLIGNE_CODE;
    String
    PROMO_NIVEAU,
    PROMO_MODECALCUL,
    PROMO_APPLIQUESUR;
    double
    MONTANT_BRUTE_AV,
    MONTANT_NET_AV,
    VALEUR_PROMO,
    MONTANT_NET_AP;
    String
    GRATUITE_CODE,
    VERSION;

    public LivraisonPromotion() {
    }

    public LivraisonPromotion(JSONObject p) {
        try {
            this.LIVRAISON_PROMO_CODE=p.getString("LIVRAISON_PROMO_CODE");
            this.PROMO_CODE=p.getString("PROMO_CODE");
            this.LIVRAISON_CODE=p.getString("LIVRAISON_CODE");
            this.LIVRAISONLIGNE_CODE=p.getInt("LIVRAISONLIGNE_CODE");
            this.PROMO_NIVEAU=p.getString("PROMO_NIVEAU");
            this.PROMO_MODECALCUL=p.getString("PROMO_MODECALCUL");
            this.PROMO_APPLIQUESUR=p.getString("PROMO_APPLIQUESUR");
            this.MONTANT_BRUTE_AV=p.getDouble("MONTANT_BRUTE_AV");
            this.MONTANT_NET_AV=p.getDouble("MONTANT_NET_AV");
            this.VALEUR_PROMO=p.getDouble("VALEUR_PROMO");
            this.MONTANT_NET_AP=p.getDouble("MONTANT_NET_AP");
            this.GRATUITE_CODE=p.getString("GRATUITE_CODE");
            this.GRATUITE_CODE=p.getString("VERSION");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getLIVRAISON_PROMO_CODE() {
        return LIVRAISON_PROMO_CODE;
    }

    public String getPROMO_CODE() {
        return PROMO_CODE;
    }

    public String getLIVRAISON_CODE() {
        return LIVRAISON_CODE;
    }

    public int getLIVRAISONLIGNE_CODE() {
        return LIVRAISONLIGNE_CODE;
    }

    public String getPROMO_NIVEAU() {
        return PROMO_NIVEAU;
    }

    public String getPROMO_MODECALCUL() {
        return PROMO_MODECALCUL;
    }

    public String getPROMO_APPLIQUESUR() {
        return PROMO_APPLIQUESUR;
    }

    public double getMONTANT_BRUTE_AV() {
        return MONTANT_BRUTE_AV;
    }

    public double getMONTANT_NET_AV() {
        return MONTANT_NET_AV;
    }

    public double getVALEUR_PROMO() {
        return VALEUR_PROMO;
    }

    public double getMONTANT_NET_AP() {
        return MONTANT_NET_AP;
    }

    public String getGRATUITE_CODE() {
        return GRATUITE_CODE;
    }

    public String getVERSION() {
        return VERSION;
    }



    public void setLIVRAISON_PROMO_CODE(String LIVRAISON_PROMO_CODE) {
        this.LIVRAISON_PROMO_CODE = LIVRAISON_PROMO_CODE;
    }

    public void setPROMO_CODE(String PROMO_CODE) {
        this.PROMO_CODE = PROMO_CODE;
    }

    public void setLIVRAISON_CODE(String LIVRAISON_CODE) {
        this.LIVRAISON_CODE = LIVRAISON_CODE;
    }

    public void setLIVRAISONLIGNE_CODE(int LIVRAISONLIGNE_CODE) {
        this.LIVRAISONLIGNE_CODE = LIVRAISONLIGNE_CODE;
    }

    public void setPROMO_NIVEAU(String PROMO_NIVEAU) {
        this.PROMO_NIVEAU = PROMO_NIVEAU;
    }

    public void setPROMO_MODECALCUL(String PROMO_MODECALCUL) {
        this.PROMO_MODECALCUL = PROMO_MODECALCUL;
    }

    public void setPROMO_APPLIQUESUR(String PROMO_APPLIQUESUR) {
        this.PROMO_APPLIQUESUR = PROMO_APPLIQUESUR;
    }

    public void setMONTANT_BRUTE_AV(double MONTANT_BRUTE_AV) {
        this.MONTANT_BRUTE_AV = MONTANT_BRUTE_AV;
    }

    public void setMONTANT_NET_AV(double MONTANT_NET_AV) {
        this.MONTANT_NET_AV = MONTANT_NET_AV;
    }

    public void setVALEUR_PROMO(double VALEUR_PROMO) {
        this.VALEUR_PROMO = VALEUR_PROMO;
    }

    public void setMONTANT_NET_AP(double MONTANT_NET_AP) {
        this.MONTANT_NET_AP = MONTANT_NET_AP;
    }

    public void setGRATUITE_CODE(String GRATUITE_CODE) {
        this.GRATUITE_CODE = GRATUITE_CODE;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    @Override
    public String toString() {
        return "LivraisonPromotion{" +
                "LIVRAISON_PROMO_CODE='" + LIVRAISON_PROMO_CODE + '\'' +
                ", PROMO_CODE='" + PROMO_CODE + '\'' +
                ", LIVRAISON_CODE='" + LIVRAISON_CODE + '\'' +
                ", LIVRAISONLIGNE_CODE=" + LIVRAISONLIGNE_CODE +
                ", PROMO_NIVEAU='" + PROMO_NIVEAU + '\'' +
                ", PROMO_MODECALCUL='" + PROMO_MODECALCUL + '\'' +
                ", PROMO_APPLIQUESUR='" + PROMO_APPLIQUESUR + '\'' +
                ", MONTANT_BRUTE_AV=" + MONTANT_BRUTE_AV +
                ", MONTANT_NET_AV=" + MONTANT_NET_AV +
                ", VALEUR_PROMO=" + VALEUR_PROMO +
                ", MONTANT_NET_AP=" + MONTANT_NET_AP +
                ", GRATUITE_CODE='" + GRATUITE_CODE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}

