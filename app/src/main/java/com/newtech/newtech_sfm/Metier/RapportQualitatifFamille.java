package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

public class RapportQualitatifFamille {

    private String FAMILLE_LIBELLE;
    private String TONNAGE;
    private String NOMBRE_FACTURE;

    public RapportQualitatifFamille() {
    }

    public RapportQualitatifFamille(String FAMILLE_LIBELLE, String TONNAGE, String NOMBRE_FACTURE) {
        this.FAMILLE_LIBELLE = FAMILLE_LIBELLE;
        this.TONNAGE = TONNAGE;
        this.NOMBRE_FACTURE = NOMBRE_FACTURE;
    }

    public RapportQualitatifFamille(JSONObject rapportQualitatifFamille) {

        try {
            this.FAMILLE_LIBELLE = rapportQualitatifFamille.getString("FAMILLE_LIBELLE");
            this.TONNAGE = rapportQualitatifFamille.getString("TONNAGE");
            this.NOMBRE_FACTURE = rapportQualitatifFamille.getString("NOMBRE_FACTURE");

            //Log.d("Client", "Client: "+rapportQualitatif.getString("LISTEPRIX_CODE"));

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public String getFAMILLE_LIBELLE() {
        return FAMILLE_LIBELLE;
    }

    public void setFAMILLE_LIBELLE(String FAMILLE_LIBELLE) {
        this.FAMILLE_LIBELLE = FAMILLE_LIBELLE;
    }

    public String getTONNAGE() {
        return TONNAGE;
    }

    public void setTONNAGE(String TONNAGE) {
        this.TONNAGE = TONNAGE;
    }

    public String getNOMBRE_FACTURE() {
        return NOMBRE_FACTURE;
    }

    public void setNOMBRE_FACTURE(String NOMBRE_FACTURE) {
        this.NOMBRE_FACTURE = NOMBRE_FACTURE;
    }

    @Override
    public String toString() {
        return "RapportQualitatifFamille{" +
                "FAMILLE_LIBELLE='" + FAMILLE_LIBELLE + '\'' +
                ", TONNAGE='" + TONNAGE + '\'' +
                ", NOMBRE_FACTURE='" + NOMBRE_FACTURE + '\'' +
                '}';
    }
}
