package com.newtech.newtech_sfm.Metier;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TONPC on 28/03/2017.
 */

public class VenteRow {
    private String DATE_VENTE;
    private float QUANTITE_VENDU;
    private String DESCRIPTION;
    private Map<String, String> DETAILS = new HashMap<String, String>();

    public VenteRow(String DATE_VENTE, float QUANTITE_VENDU, String DESCRIPTION) {
        this.DATE_VENTE = DATE_VENTE;
        this.QUANTITE_VENDU = QUANTITE_VENDU;
        this.DESCRIPTION = DESCRIPTION;
    }

    public VenteRow(){

    }

    public String getDATE_VENTE() {
        return DATE_VENTE;
    }

    public void setDATE_VENTE(String DATE_VENTE) {
        this.DATE_VENTE = DATE_VENTE;
    }

    public float getQUANTITE_VENDU() {
        return QUANTITE_VENDU;
    }

    public void setQUANTITE_VENDU(float QUANTITE_VENDU) {
        this.QUANTITE_VENDU = QUANTITE_VENDU;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public Map<String, String> getDETAILS() {
        return DETAILS;
    }

    public void setDETAILS(Map<String, String> DETAILS) {
        this.DETAILS = DETAILS;
    }

    @Override
    public String toString() {
        return "VenteRow{" +
                "DATE_VENTE='" + DATE_VENTE + '\'' +
                ", QUANTITE_VENDU=" + QUANTITE_VENDU +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                ", DETAILS=" + DETAILS +
                '}';
    }
}
