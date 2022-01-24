package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

public class RapportQualitatif {

    private String TOURNEE_NOM;
    private String CLIENT_PROGRAMMES;
    private String NOMBRE_VISITES;
    private String TAUX_COUVERTURE;
    private String NOMBRE_FACTURES;
    private String TAUX_SUCCES;
    private String MOYENNE_MINUTES;
    private String NOMBRE_SKU;
    private String TAUX_GPS_VISITES;
    private String TAUX_GPS_FACTURES;


    public RapportQualitatif() {
    }

    public RapportQualitatif(String TOURNEE_NOM, String CLIENT_PROGRAMMES, String NOMBRE_VISITES, String TAUX_COUVERTURE, String NOMBRE_FACTURES, String TAUX_SUCCES, String MOYENNE_MINUTES, String NOMBRE_SKU, String TAUX_GPS_VISITES, String TAUX_GPS_FACTURES) {
        this.TOURNEE_NOM = TOURNEE_NOM;
        this.CLIENT_PROGRAMMES = CLIENT_PROGRAMMES;
        this.NOMBRE_VISITES = NOMBRE_VISITES;
        this.TAUX_COUVERTURE = TAUX_COUVERTURE;
        this.NOMBRE_FACTURES = NOMBRE_FACTURES;
        this.TAUX_SUCCES = TAUX_SUCCES;
        this.MOYENNE_MINUTES = MOYENNE_MINUTES;
        this.NOMBRE_SKU = NOMBRE_SKU;
        this.TAUX_GPS_VISITES = TAUX_GPS_VISITES;
        this.TAUX_GPS_FACTURES = TAUX_GPS_FACTURES;
    }

    public RapportQualitatif(JSONObject rapportQualitatif) {

        try {
            this.TOURNEE_NOM = rapportQualitatif.getString("TOURNEE_NOM");
            this.CLIENT_PROGRAMMES = rapportQualitatif.getString("CLIENT_PROGRAMMES");
            this.NOMBRE_VISITES = rapportQualitatif.getString("NOMBRE_VISITES");
            this.TAUX_COUVERTURE = rapportQualitatif.getString("TAUX_COUVERTURE");
            this.NOMBRE_FACTURES = rapportQualitatif.getString("NOMBRE_FACTURES");
            this.TAUX_SUCCES = rapportQualitatif.getString("TAUX_SUCCES");
            this.MOYENNE_MINUTES = rapportQualitatif.getString("MOYENNE_MINUTES");
            this.NOMBRE_SKU = rapportQualitatif.getString("NOMBRE_SKU");
            this.TAUX_GPS_VISITES = rapportQualitatif.getString("TAUX_GPS_VISITES");
            this.TAUX_GPS_FACTURES = rapportQualitatif.getString("TAUX_GPS_FACTURES");

            //Log.d("Client", "Client: "+rapportQualitatif.getString("LISTEPRIX_CODE"));

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public String getCLIENT_PROGRAMMES() {
        return CLIENT_PROGRAMMES;
    }

    public void setCLIENT_PROGRAMMES(String CLIENT_PROGRAMMES) {
        this.CLIENT_PROGRAMMES = CLIENT_PROGRAMMES;
    }

    public String getNOMBRE_VISITES() {
        return NOMBRE_VISITES;
    }

    public void setNOMBRE_VISITES(String NOMBRE_VISITES) {
        this.NOMBRE_VISITES = NOMBRE_VISITES;
    }

    public String getTAUX_COUVERTURE() {
        return TAUX_COUVERTURE;
    }

    public void setTAUX_COUVERTURE(String TAUX_COUVERTURE) {
        this.TAUX_COUVERTURE = TAUX_COUVERTURE;
    }

    public String getNOMBRE_FACTURES() {
        return NOMBRE_FACTURES;
    }

    public void setNOMBRE_FACTURES(String NOMBRE_FACTURES) {
        this.NOMBRE_FACTURES = NOMBRE_FACTURES;
    }

    public String getTAUX_SUCCES() {
        return TAUX_SUCCES;
    }

    public void setTAUX_SUCCES(String TAUX_SUCCES) {
        this.TAUX_SUCCES = TAUX_SUCCES;
    }

    public String getMOYENNE_MINUTES() {
        return MOYENNE_MINUTES;
    }

    public void setMOYENNE_MINUTES(String MOYENNE_MINUTES) {
        this.MOYENNE_MINUTES = MOYENNE_MINUTES;
    }

    public String getNOMBRE_SKU() {
        return NOMBRE_SKU;
    }

    public void setNOMBRE_SKU(String NOMBRE_SKU) {
        this.NOMBRE_SKU = NOMBRE_SKU;
    }

    public String getTAUX_GPS_VISITES() {
        return TAUX_GPS_VISITES;
    }

    public void setTAUX_GPS_VISITES(String TAUX_GPS_VISITES) {
        this.TAUX_GPS_VISITES = TAUX_GPS_VISITES;
    }

    public String getTAUX_GPS_FACTURES() {
        return TAUX_GPS_FACTURES;
    }

    public void setTAUX_GPS_FACTURES(String TAUX_GPS_FACTURES) {
        this.TAUX_GPS_FACTURES = TAUX_GPS_FACTURES;
    }

    public String getTOURNEE_NOM() {
        return TOURNEE_NOM;
    }

    public void setTOURNEE_NOM(String TOURNEE_NOM) {
        this.TOURNEE_NOM = TOURNEE_NOM;
    }

    @Override
    public String toString() {
        return "RapportQualitatif{" +
                "TOURNEE_NOM='" + TOURNEE_NOM + '\'' +
                ", CLIENT_PROGRAMMES='" + CLIENT_PROGRAMMES + '\'' +
                ", NOMBRE_VISITES='" + NOMBRE_VISITES + '\'' +
                ", TAUX_COUVERTURE='" + TAUX_COUVERTURE + '\'' +
                ", NOMBRE_FACTURES='" + NOMBRE_FACTURES + '\'' +
                ", TAUX_SUCCES='" + TAUX_SUCCES + '\'' +
                ", MOYENNE_MINUTES='" + MOYENNE_MINUTES + '\'' +
                ", NOMBRE_SKU='" + NOMBRE_SKU + '\'' +
                ", TAUX_GPS_VISITES='" + TAUX_GPS_VISITES + '\'' +
                ", TAUX_GPS_FACTURES='" + TAUX_GPS_FACTURES + '\'' +
                '}';
    }
}
