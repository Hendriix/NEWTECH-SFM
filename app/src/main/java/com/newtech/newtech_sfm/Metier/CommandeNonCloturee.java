package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

/**
 * Created by TONPC on 04/10/2017.
 */

public class CommandeNonCloturee extends Commande{

    public CommandeNonCloturee(JSONObject p) {
        super(p);
        try {
            this.COMMANDE_CODE=p.getString("COMMANDE_CODE");
            this.FACTURE_CODE=p.getString("FACTURE_CODE");
            this.FACTURECLIENT_CODE=p.getString("FACTURECLIENT_CODE");
            this.DATE_COMMANDE=p.getString("DATE_COMMANDE");
            this.DATE_LIVRAISON=p.getString("DATE_LIVRAISON");
            this.DATE_CREATION=p.getString("DATE_CREATION");
            this.PERIODE_CODE=p.getString("PERIODE_CODE");
            this.COMMANDETYPE_CODE=p.getString("COMMANDETYPE_CODE");
            this.COMMANDESTATUT_CODE=p.getString("COMMANDESTATUT_CODE");
            this.DISTRIBUTEUR_CODE=p.getString("DISTRIBUTEUR_CODE");
            this.VENDEUR_CODE=p.getString("VENDEUR_CODE");
            this.CLIENT_CODE=p.getString("CLIENT_CODE");
            this.CREATEUR_CODE=p.getString("CREATEUR_CODE");
            this.LIVREUR_CODE=p.getString("LIVREUR_CODE");
            this.REGION_CODE=p.getString("REGION_CODE");
            this.ZONE_CODE=p.getString("ZONE_CODE");
            this.SECTEUR_CODE=p.getString("SECTEUR_CODE");
            this.SOUSSECTEUR_CODE=p.getString("SOUSSECTEUR_CODE");
            this.TOURNEE_CODE=p.getString("TOURNEE_CODE");
            this.VISITE_CODE=p.getString("VISITE_CODE");
            this.STOCKDEPART_CODE=p.getString("STOCKDEPART_CODE");
            this.STOCKDESTINATION_CODE=p.getString("STOCKDESTINATION_CODE");
            this.DESTINATION_CODE=p.getString("DESTINATION_CODE");
            this.TS=p.getString("TS");
            this.COMMENTAIRE=p.getString("COMMENTAIRE");
            this.MONTANT_BRUT=getNumberRounded(p.getDouble("MONTANT_BRUT"));
            this.REMISE=getNumberRounded(p.getDouble("REMISE"));
            this.MONTANT_NET=getNumberRounded(p.getDouble("MONTANT_NET"));
            this.LITTRAGE_COMMANDE=p.getDouble("LITTRAGE_COMMANDE");
            this.TONNAGE_COMMANDE=p.getDouble("TONNAGE_COMMANDE");
            this.KG_COMMANDE=p.getDouble("KG_COMMANDE");
            this.VALEUR_COMMANDE=getNumberRounded(p.getDouble("VALEUR_COMMANDE"));
            this.PAIEMENT_CODE=p.getInt("PAIEMENT_CODE");
            this.NB_LIGNE=p.getInt("NB_LIGNE");
            this.CIRCUIT_CODE=p.getString("CIRCUIT_CODE");
            this.CHANNEL_CODE=p.getString("CHANNEL_CODE");
            this.STATUT_CODE=p.getString("STATUT_CODE");
            this.SOURCE=p.getString("SOURCE");
            this.VERSION=p.getString("VERSION");
            this.GPS_LATITUDE=p.getString("GPS_LATITUDE");
            this.GPS_LONGITUDE=p.getString("GPS_LONGITUDE");
            this.DISTANCE=p.getInt("DISTANCE");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public CommandeNonCloturee( Commande cmd) {
        try {
            this.COMMANDE_CODE=cmd.getCOMMANDE_CODE();
            this.FACTURE_CODE=cmd.getFACTURE_CODE();
            this.FACTURECLIENT_CODE=cmd.getFACTURECLIENT_CODE();
            this.DATE_COMMANDE=cmd.getDATE_COMMANDE();
            this.DATE_LIVRAISON=cmd.getDATE_LIVRAISON();
            this.DATE_CREATION=cmd.getDATE_CREATION();
            this.PERIODE_CODE=cmd.getPERIODE_CODE();
            this.COMMANDETYPE_CODE=cmd.getCOMMANDETYPE_CODE();
            this.COMMANDESTATUT_CODE=cmd.getCOMMANDESTATUT_CODE();
            this.DISTRIBUTEUR_CODE=cmd.getDISTRIBUTEUR_CODE();
            this.VENDEUR_CODE=cmd.getVENDEUR_CODE();
            this.CLIENT_CODE=cmd.getCLIENT_CODE();
            this.CREATEUR_CODE=cmd.getCREATEUR_CODE();
            this.LIVREUR_CODE=cmd.getLIVREUR_CODE();
            this.REGION_CODE=cmd.getREGION_CODE();
            this.ZONE_CODE=cmd.getZONE_CODE();
            this.SECTEUR_CODE=cmd.getSECTEUR_CODE();
            this.SOUSSECTEUR_CODE=cmd.getSOUSSECTEUR_CODE();
            this.TOURNEE_CODE=cmd.getTOURNEE_CODE();
            this.VISITE_CODE=cmd.getVISITE_CODE();
            this.STOCKDEPART_CODE=cmd.getSTOCKDEPART_CODE();
            this.STOCKDESTINATION_CODE=cmd.getSTOCKDESTINATION_CODE();
            this.DESTINATION_CODE=cmd.getDESTINATION_CODE();
            this.TS=cmd.getTS();
            this.CIRCUIT_CODE=cmd.getCIRCUIT_CODE();
            this.CHANNEL_CODE=cmd.getCHANNEL_CODE();
            this.COMMENTAIRE=cmd.getCOMMENTAIRE();
            this.MONTANT_BRUT=getNumberRounded(cmd.getMONTANT_BRUT());
            this.REMISE=getNumberRounded(cmd.getREMISE());
            this.MONTANT_NET=getNumberRounded(cmd.getMONTANT_NET());
            this.LITTRAGE_COMMANDE=cmd.getLITTRAGE_COMMANDE();
            this.TONNAGE_COMMANDE=cmd.getTONNAGE_COMMANDE();
            this.KG_COMMANDE=cmd.getKG_COMMANDE();
            this.VALEUR_COMMANDE=getNumberRounded(cmd.getVALEUR_COMMANDE());
            this.PAIEMENT_CODE=cmd.getPAIEMENT_CODE();
            this.NB_LIGNE=cmd.getNB_LIGNE();
            this.STATUT_CODE=cmd.getSTATUT_CODE();
            this.SOURCE=cmd.getSOURCE();
            this.VERSION=cmd.getVERSION();
            this.GPS_LATITUDE=cmd.getGPS_LATITUDE();
            this.GPS_LONGITUDE=cmd.getGPS_LONGITUDE();
            this.DISTANCE=cmd.getDISTANCE();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CommandeNonCloturee() {
    }

    @Override
    public String toString() {
        return "CommandeNonCloturee{" +
                "COMMANDE_CODE='" + COMMANDE_CODE + '\'' +
                ", FACTURE_CODE='" + FACTURE_CODE + '\'' +
                ", FACTURECLIENT_CODE='" + FACTURECLIENT_CODE + '\'' +
                ", DATE_COMMANDE='" + DATE_COMMANDE + '\'' +
                ", DATE_LIVRAISON='" + DATE_LIVRAISON + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", PERIODE_CODE='" + PERIODE_CODE + '\'' +
                ", COMMANDETYPE_CODE='" + COMMANDETYPE_CODE + '\'' +
                ", COMMANDESTATUT_CODE='" + COMMANDESTATUT_CODE + '\'' +
                ", DISTRIBUTEUR_CODE='" + DISTRIBUTEUR_CODE + '\'' +
                ", VENDEUR_CODE='" + VENDEUR_CODE + '\'' +
                ", CLIENT_CODE='" + CLIENT_CODE + '\'' +
                ", CREATEUR_CODE='" + CREATEUR_CODE + '\'' +
                ", LIVREUR_CODE='" + LIVREUR_CODE + '\'' +
                ", REGION_CODE='" + REGION_CODE + '\'' +
                ", ZONE_CODE='" + ZONE_CODE + '\'' +
                ", SECTEUR_CODE='" + SECTEUR_CODE + '\'' +
                ", SOUSSECTEUR_CODE='" + SOUSSECTEUR_CODE + '\'' +
                ", TOURNEE_CODE='" + TOURNEE_CODE + '\'' +
                ", VISITE_CODE='" + VISITE_CODE + '\'' +
                ", STOCKDEPART_CODE='" + STOCKDEPART_CODE + '\'' +
                ", STOCKDESTINATION_CODE='" + STOCKDESTINATION_CODE + '\'' +
                ", DESTINATION_CODE='" + DESTINATION_CODE + '\'' +
                ", TS='" + TS + '\'' +
                ", CIRCUIT_CODE='" + CIRCUIT_CODE + '\'' +
                ", CHANNEL_CODE='" + CHANNEL_CODE + '\'' +
                ", COMMENTAIRE='" + COMMENTAIRE + '\'' +
                ", LIEU_LIVRAISON='" + LIEU_LIVRAISON + '\'' +
                ", MONTANT_BRUT=" + MONTANT_BRUT +
                ", REMISE=" + REMISE +
                ", MONTANT_NET=" + MONTANT_NET +
                ", VALEUR_COMMANDE=" + VALEUR_COMMANDE +
                ", LITTRAGE_COMMANDE=" + LITTRAGE_COMMANDE +
                ", TONNAGE_COMMANDE=" + TONNAGE_COMMANDE +
                ", KG_COMMANDE=" + KG_COMMANDE +
                ", PAIEMENT_CODE=" + PAIEMENT_CODE +
                ", NB_LIGNE=" + NB_LIGNE +
                ", STATUT_CODE='" + STATUT_CODE + '\'' +
                ", SOURCE='" + SOURCE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                ", GPS_LATITUDE='" + GPS_LATITUDE + '\'' +
                ", GPS_LONGITUDE='" + GPS_LONGITUDE + '\'' +
                ", DISTANCE=" + DISTANCE +
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
