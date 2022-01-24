package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

/**
 * Created by TONPC on 09/05/2017.
 */

public class CommandeNonClotureeLigne extends CommandeLigne{

    public CommandeNonClotureeLigne(JSONObject p) {
        super(p);
        try {
            this.COMMANDE_CODE=p.getString("COMMANDE_CODE");
            this.FACTURE_CODE=p.getString("FACTURE_CODE");
            this.FAMILLE_CODE=p.getString("FAMILLE_CODE");
            this.ARTICLE_CODE=p.getString("ARTICLE_CODE");
            this.ARTICLE_DESIGNATION=p.getString("ARTICLE_DESIGNATION");
            this.ARTICLE_NBUS_PAR_UP=p.getDouble("ARTICLE_NBUS_PAR_UP");
            this.ARTICLE_PRIX=p.getDouble("ARTICLE_PRIX");
            this.QTE_COMMANDEE=p.getDouble("QTE_COMMANDEE");
            this.QTE_LIVREE=p.getDouble("QTE_LIVREE");
            this.LITTRAGE_COMMANDEE=p.getDouble("LITTRAGE_COMMANDEE");
            this.LITTRAGE_LIVREE=p.getDouble("LITTRAGE_LIVREE");
            this.TONNAGE_COMMANDEE=p.getDouble("TONNAGE_COMMANDEE");
            this.TONNAGE_LIVREE=p.getDouble("TONNAGE_LIVREE");
            this.KG_COMMANDEE=p.getDouble("KG_COMMANDEE");
            this.KG_LIVREE=p.getDouble("KG_LIVREE");
            this.MONTANT_BRUT=getNumberRounded(p.getDouble("MONTANT_BRUT"));
            this.REMISE=getNumberRounded(p.getDouble("REMISE"));
            this.MONTANT_NET=getNumberRounded(p.getDouble("MONTANT_NET"));
            this.COMMENTAIRE=p.getString("COMMENTAIRE");
            this.CREATEUR_CODE=p.getString("CREATEUR_CODE");
            this.DATE_CREATION=p.getString("DATE_CREATION");
            this.UNITE_CODE=p.getString("UNITE_CODE");
            this.CAISSE_COMMANDEE=p.getDouble("CAISSE_COMMANDEE");
            this.CAISSE_LIVREE=p.getDouble("CAISSE_LIVREE");
            this.VERSION=p.getString("VERSION");
            this.SOURCE=p.getString("SOURCE");
            this.COMMANDELIGNE_CODE=p.getInt("COMMANDELIGNE_CODE");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public CommandeNonClotureeLigne() {
        super();
    }


    public CommandeNonClotureeLigne( CommandeLigne cmdNCL) {
        try {
            this.COMMANDE_CODE=cmdNCL.getCOMMANDE_CODE();
            this.FACTURE_CODE=cmdNCL.getFACTURE_CODE();
            this.FAMILLE_CODE=cmdNCL.getFAMILLE_CODE();
            this.ARTICLE_CODE=cmdNCL.getARTICLE_CODE();
            this.ARTICLE_DESIGNATION=cmdNCL.getARTICLE_DESIGNATION();
            this.ARTICLE_NBUS_PAR_UP=cmdNCL.getARTICLE_NBUS_PAR_UP();
            this.ARTICLE_PRIX=cmdNCL.getARTICLE_PRIX();
            this.QTE_COMMANDEE=cmdNCL.getQTE_COMMANDEE()-cmdNCL.getQTE_LIVREE();
            this.QTE_LIVREE=cmdNCL.getQTE_LIVREE();
            this.CAISSE_COMMANDEE=cmdNCL.getCAISSE_COMMANDEE()-cmdNCL.getCAISSE_LIVREE();
            this.CAISSE_LIVREE=cmdNCL.getCAISSE_LIVREE();
            this.LITTRAGE_COMMANDEE=cmdNCL.getLITTRAGE_COMMANDEE()-cmdNCL.getLITTRAGE_LIVREE();
            this.LITTRAGE_LIVREE=cmdNCL.getLITTRAGE_LIVREE();
            this.TONNAGE_COMMANDEE=cmdNCL.getTONNAGE_COMMANDEE()-cmdNCL.getTONNAGE_LIVREE();
            this.TONNAGE_LIVREE=cmdNCL.getTONNAGE_LIVREE();
            this.KG_COMMANDEE=cmdNCL.getKG_COMMANDEE()-cmdNCL.getKG_LIVREE();
            this.KG_LIVREE=cmdNCL.getKG_LIVREE();
            this.MONTANT_BRUT=cmdNCL.getMONTANT_BRUT();
            this.REMISE=cmdNCL.getREMISE();
            this.MONTANT_NET=cmdNCL.getMONTANT_NET();
            this.COMMENTAIRE=cmdNCL.getCOMMENTAIRE();
            this.CREATEUR_CODE=cmdNCL.getCREATEUR_CODE();
            this.DATE_CREATION=cmdNCL.getDATE_CREATION();
            this.UNITE_CODE=cmdNCL.getUNITE_CODE();
            this.SOURCE=cmdNCL.getSOURCE();
            this.VERSION=cmdNCL.getVERSION();
            this.COMMANDELIGNE_CODE=cmdNCL.getCOMMANDELIGNE_CODE();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "CommandeNonClotureeLigne{" +
                "COMMANDELIGNE_CODE=" + COMMANDELIGNE_CODE +
                ", COMMANDE_CODE='" + COMMANDE_CODE + '\'' +
                ", FACTURE_CODE='" + FACTURE_CODE + '\'' +
                ", FAMILLE_CODE='" + FAMILLE_CODE + '\'' +
                ", ARTICLE_CODE='" + ARTICLE_CODE + '\'' +
                ", UNITE_CODE='" + UNITE_CODE + '\'' +
                ", ARTICLE_DESIGNATION='" + ARTICLE_DESIGNATION + '\'' +
                ", ARTICLE_NBUS_PAR_UP=" + ARTICLE_NBUS_PAR_UP +
                ", ARTICLE_PRIX=" + ARTICLE_PRIX +
                ", QTE_COMMANDEE=" + QTE_COMMANDEE +
                ", QTE_LIVREE=" + QTE_LIVREE +
                ", CAISSE_COMMANDEE=" + CAISSE_COMMANDEE +
                ", CAISSE_LIVREE=" + CAISSE_LIVREE +
                ", LITTRAGE_COMMANDEE=" + LITTRAGE_COMMANDEE +
                ", LITTRAGE_LIVREE=" + LITTRAGE_LIVREE +
                ", TONNAGE_COMMANDEE=" + TONNAGE_COMMANDEE +
                ", TONNAGE_LIVREE=" + TONNAGE_LIVREE +
                ", KG_COMMANDEE=" + KG_COMMANDEE +
                ", KG_LIVREE=" + KG_LIVREE +
                ", MONTANT_BRUT=" + MONTANT_BRUT +
                ", REMISE=" + REMISE +
                ", MONTANT_NET=" + MONTANT_NET +
                ", COMMENTAIRE='" + COMMENTAIRE + '\'' +
                ", CREATEUR_CODE='" + CREATEUR_CODE + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", SOURCE='" + SOURCE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }

    public double getNumberRounded(double nombre){

        double number = nombre;
        int decimalsToConsider = 2;
        BigDecimal bigDecimal = new BigDecimal(number);
        BigDecimal roundedWithScale = bigDecimal.setScale(decimalsToConsider, BigDecimal.ROUND_HALF_UP);
        //System.out.println("Rounded value with setting scale = "+roundedWithScale);


        return roundedWithScale.doubleValue();
    }
}
