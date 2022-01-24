package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TONPC on 15/08/2017.
 */

public class CommandeAEncaisser extends Commande{

    public CommandeAEncaisser(JSONObject p) {
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
            this.COMMENTAIRE=p.getString("COMMENTAIRE");
            this.MONTANT_BRUT=p.getDouble("MONTANT_BRUT");
            this.REMISE=p.getDouble("REMISE");
            this.MONTANT_NET=p.getDouble("MONTANT_NET");
            this.LITTRAGE_COMMANDE=p.getDouble("LITTRAGE_COMMANDE");
            this.TONNAGE_COMMANDE=p.getDouble("TONNAGE_COMMANDE");
            this.KG_COMMANDE=p.getDouble("KG_COMMANDE");
            this.VALEUR_COMMANDE=p.getDouble("VALEUR_COMMANDE");
            this.PAIEMENT_CODE=p.getInt("PAIEMENT_CODE");
            this.NB_LIGNE=p.getInt("NB_LIGNE");
            this.CIRCUIT_CODE=p.getString("CIRCUIT_CODE");
            this.CHANNEL_CODE=p.getString("CHANNEL_CODE");
            this.STATUT_CODE=p.getString("STATUT_CODE");
            this.VERSION=p.getString("VERSION");
            this.GPS_LATITUDE=p.getString("GPS_LATITUDE");
            this.GPS_LONGITUDE=p.getString("GPS_LONGITUDE");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public CommandeAEncaisser() {
        super();
    }
}
