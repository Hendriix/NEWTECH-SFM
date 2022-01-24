package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TONPC on 15/08/2017.
 */

public class CommandeLigneAEncaisser extends CommandeLigne{

    public CommandeLigneAEncaisser(JSONObject p) {
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
            this.MONTANT_BRUT=p.getDouble("MONTANT_BRUT");
            this.REMISE=p.getDouble("REMISE");
            this.MONTANT_NET=p.getDouble("MONTANT_NET");
            this.COMMENTAIRE=p.getString("COMMENTAIRE");
            this.CREATEUR_CODE=p.getString("CREATEUR_CODE");
            this.DATE_CREATION=p.getString("DATE_CREATION");
            this.UNITE_CODE=p.getString("UNITE_CODE");
            this.CAISSE_COMMANDEE=p.getDouble("CAISSE_COMMANDEE");
            this.CAISSE_LIVREE=p.getDouble("CAISSE_LIVREE");
            this.VERSION=p.getString("VERSION");
            this.COMMANDELIGNE_CODE=p.getInt("COMMANDELIGNE_CODE");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public CommandeLigneAEncaisser(){
        super();
    }
}
