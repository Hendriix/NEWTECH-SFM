package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by stagiaireit2 on 02/08/2016.
 */
public class CommandePromotion {

   String
    COMMANDE_PROMO_CODE,
    PROMO_CODE,
    COMMANDE_CODE;
    int
    COMMANDELIGNE_CODE;
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

    public CommandePromotion() {
    }

    public CommandePromotion( JSONObject p) {
        try {
            this.COMMANDE_PROMO_CODE=p.getString("COMMANDE_PROMO_CODE");
            this.PROMO_CODE=p.getString("PROMO_CODE");
            this.COMMANDE_CODE=p.getString("COMMANDE_CODE");
            this.COMMANDELIGNE_CODE=p.getInt("COMMANDELIGNE_CODE");
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


    public String getCOMMANDE_PROMO_CODE() {
        return COMMANDE_PROMO_CODE;
    }

    public String getPROMO_CODE() {
        return PROMO_CODE;
    }

    public String getCOMMANDE_CODE() {
        return COMMANDE_CODE;
    }

    public int getCOMMANDELIGNE_CODE() {
        return COMMANDELIGNE_CODE;
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



    public void setCOMMANDE_PROMO_CODE(String COMMANDE_PROMO_CODE) {
        this.COMMANDE_PROMO_CODE = COMMANDE_PROMO_CODE;
    }

    public void setPROMO_CODE(String PROMO_CODE) {
        this.PROMO_CODE = PROMO_CODE;
    }

    public void setCOMMANDE_CODE(String COMMANDE_CODE) {
        this.COMMANDE_CODE = COMMANDE_CODE;
    }

    public void setCOMMANDELIGNE_CODE(int COMMANDELIGNE_CODE) {
        this.COMMANDELIGNE_CODE = COMMANDELIGNE_CODE;
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
        return "CommandePromotionActivity{" +
                "COMMANDE_PROMO_CODE='" + COMMANDE_PROMO_CODE + '\'' +
                ", PROMO_CODE='" + PROMO_CODE + '\'' +
                ", COMMANDE_CODE='" + COMMANDE_CODE + '\'' +
                ", COMMANDELIGNE_CODE=" + COMMANDELIGNE_CODE +
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

