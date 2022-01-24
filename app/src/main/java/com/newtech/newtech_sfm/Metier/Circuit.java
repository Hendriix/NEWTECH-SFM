package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

public class Circuit {

    private String CIRCUIT_CODE;
    private String CIRCUIT_NOM;
    private String CIRCUIT_CATEGORIE;
    private String DESCRIPTION;


    public Circuit(String CIRCUIT_CATEGORIE) {
        this.CIRCUIT_CATEGORIE = CIRCUIT_CATEGORIE;
    }

    public Circuit(String CIRCUIT_CODE, String CIRCUIT_NOM, String CIRCUIT_CATEGORIE, String DESCRIPTION) {
        this.CIRCUIT_CODE = CIRCUIT_CODE;
        this.CIRCUIT_NOM = CIRCUIT_NOM;
        this.CIRCUIT_CATEGORIE = CIRCUIT_CATEGORIE;
        this.DESCRIPTION = DESCRIPTION;
    }

    public Circuit(JSONObject circuit) {
        try {

            this.CIRCUIT_CODE  = circuit.getString("CIRCUIT_CODE");
            this.CIRCUIT_NOM  = circuit.getString("CIRCUIT_NOM");
            this.CIRCUIT_CATEGORIE  = circuit.getString("CIRCUIT_CATEGORIE");
            this.DESCRIPTION  = circuit.getString("DESCRIPTION");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getCIRCUIT_CODE() {
        return CIRCUIT_CODE;
    }

    public void setCIRCUIT_CODE(String CIRCUIT_CODE) {
        this.CIRCUIT_CODE = CIRCUIT_CODE;
    }

    public String getCIRCUIT_NOM() {
        return CIRCUIT_NOM;
    }

    public void setCIRCUIT_NOM(String CIRCUIT_NOM) {
        this.CIRCUIT_NOM = CIRCUIT_NOM;
    }

    public String getCIRCUIT_CATEGORIE() {
        return CIRCUIT_CATEGORIE;
    }

    public void setCIRCUIT_CATEGORIE(String CIRCUIT_CATEGORIE) {
        this.CIRCUIT_CATEGORIE = CIRCUIT_CATEGORIE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    @Override
    public String toString() {
        return "Circuit{" +
                "CIRCUIT_CODE='" + CIRCUIT_CODE + '\'' +
                ", CIRCUIT_NOM='" + CIRCUIT_NOM + '\'' +
                ", CIRCUIT_CATEGORIE='" + CIRCUIT_CATEGORIE + '\'' +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                '}';
    }
}
