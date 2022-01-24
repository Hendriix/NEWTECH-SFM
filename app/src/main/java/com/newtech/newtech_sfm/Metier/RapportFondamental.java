package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

public class RapportFondamental {

    private String UTILISATEUR_NOM;
    private String NB_VISITES;
    private String MOYENNE_VISITES;
    private String UNIVERS;
    private String NBC_VISITES;
    private String COUVERTURE;
    private String NBC_FACTURES;
    private String ACTIVATION;
    private String NBC_FACTURES_GPS;
    private String MOYENNE_FACTURES;

    private String NBSKUPF01;
    private String NBJ_TRAVAILLES;
    private String CF_AFIA;
    private String NBFAFIA01;

    private String NBFHALA01;
    private String NBFAFIASF01;
    private String NBFRIADZ01;
    private String NBFZAYTOUNI01;
    private String TAUX_GPS_FACTURES;


    public RapportFondamental() {
    }

    public RapportFondamental(String UTILISATEUR_NOM, String NB_VISITES, String MOYENNE_VISITES, String UNIVERS, String NBC_VISITES, String COUVERTURE, String NBC_FACTURES, String ACTIVATION, String NBC_FACTURES_GPS, String MOYENNE_FACTURES, String NBSKUPF01, String NBJ_TRAVAILLES, String CF_AFIA, String NBFAFIA01, String NBFHALA01, String NBFAFIASF01, String NBFRIADZ01, String NBFZAYTOUNI01, String TAUX_GPS_FACTURES) {
        this.UTILISATEUR_NOM = UTILISATEUR_NOM;
        this.NB_VISITES = NB_VISITES;
        this.MOYENNE_VISITES = MOYENNE_VISITES;
        this.UNIVERS = UNIVERS;
        this.NBC_VISITES = NBC_VISITES;
        this.COUVERTURE = COUVERTURE;
        this.NBC_FACTURES = NBC_FACTURES;
        this.ACTIVATION = ACTIVATION;
        this.NBC_FACTURES_GPS = NBC_FACTURES_GPS;
        this.MOYENNE_FACTURES = MOYENNE_FACTURES;
        this.NBSKUPF01 = NBSKUPF01;
        this.NBJ_TRAVAILLES = NBJ_TRAVAILLES;
        this.CF_AFIA = CF_AFIA;
        this.NBFAFIA01 = NBFAFIA01;
        this.NBFHALA01 = NBFHALA01;
        this.NBFAFIASF01 = NBFAFIASF01;
        this.NBFRIADZ01 = NBFRIADZ01;
        this.NBFZAYTOUNI01 = NBFZAYTOUNI01;
        this.TAUX_GPS_FACTURES = TAUX_GPS_FACTURES;
    }

    public RapportFondamental(JSONObject rapportFondamental) {

        try {

            this.UTILISATEUR_NOM = rapportFondamental.getString("UTILISATEUR_NOM");
            this.NB_VISITES = rapportFondamental.getString("NB_VISITES");
            this.MOYENNE_VISITES = rapportFondamental.getString("MOYENNE_VISITES");
            this.UNIVERS = rapportFondamental.getString("UNIVERS");
            this.NBC_VISITES = rapportFondamental.getString("NBC_VISITES");
            this.COUVERTURE = rapportFondamental.getString("COUVERTURE");
            this.NBC_FACTURES = rapportFondamental.getString("NBC_FACTURES");
            this.ACTIVATION = rapportFondamental.getString("ACTIVATION");
            this.NBC_FACTURES_GPS = rapportFondamental.getString("NBC_FACTURES_GPS");
            this.MOYENNE_FACTURES = rapportFondamental.getString("MOYENNE_FACTURES");
            this.NBSKUPF01 = rapportFondamental.getString("NBSKUPF01");
            this.NBJ_TRAVAILLES = rapportFondamental.getString("NBJ_TRAVAILLES");
            this.CF_AFIA = rapportFondamental.getString("CF_AFIA");
            this.NBFAFIA01 = rapportFondamental.getString("NBFAFIA01");
            this.NBFHALA01 = rapportFondamental.getString("NBFHALA01");
            this.NBFAFIASF01 = rapportFondamental.getString("NBFAFIASF01");
            this.NBFRIADZ01 = rapportFondamental.getString("NBFRIADZ01");
            this.NBFZAYTOUNI01 = rapportFondamental.getString("NBFZAYTOUNI01");
            this.TAUX_GPS_FACTURES = rapportFondamental.getString("TAUX_GPS_FACTURES");

            //Log.d("Client", "Client: "+rapportQualitatif.getString("LISTEPRIX_CODE"));

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public String getUTILISATEUR_NOM() {
        return UTILISATEUR_NOM;
    }

    public void setUTILISATEUR_NOM(String UTILISATEUR_NOM) {
        this.UTILISATEUR_NOM = UTILISATEUR_NOM;
    }

    public String getNB_VISITES() {
        return NB_VISITES;
    }

    public void setNB_VISITES(String NB_VISITES) {
        this.NB_VISITES = NB_VISITES;
    }

    public String getMOYENNE_VISITES() {
        return MOYENNE_VISITES;
    }

    public void setMOYENNE_VISITES(String MOYENNE_VISITES) {
        this.MOYENNE_VISITES = MOYENNE_VISITES;
    }

    public String getUNIVERS() {
        return UNIVERS;
    }

    public void setUNIVERS(String UNIVERS) {
        this.UNIVERS = UNIVERS;
    }

    public String getNBC_VISITES() {
        return NBC_VISITES;
    }

    public void setNBC_VISITES(String NBC_VISITES) {
        this.NBC_VISITES = NBC_VISITES;
    }

    public String getCOUVERTURE() {
        return COUVERTURE;
    }

    public void setCOUVERTURE(String COUVERTURE) {
        this.COUVERTURE = COUVERTURE;
    }

    public String getNBC_FACTURES() {
        return NBC_FACTURES;
    }

    public void setNBC_FACTURES(String NBC_FACTURES) {
        this.NBC_FACTURES = NBC_FACTURES;
    }

    public String getACTIVATION() {
        return ACTIVATION;
    }

    public void setACTIVATION(String ACTIVATION) {
        this.ACTIVATION = ACTIVATION;
    }

    public String getNBC_FACTURES_GPS() {
        return NBC_FACTURES_GPS;
    }

    public void setNBC_FACTURES_GPS(String NBC_FACTURES_GPS) {
        this.NBC_FACTURES_GPS = NBC_FACTURES_GPS;
    }

    public String getMOYENNE_FACTURES() {
        return MOYENNE_FACTURES;
    }

    public void setMOYENNE_FACTURES(String MOYENNE_FACTURES) {
        this.MOYENNE_FACTURES = MOYENNE_FACTURES;
    }

    public String getNBSKUPF01() {
        return NBSKUPF01;
    }

    public void setNBSKUPF01(String NBSKUPF01) {
        this.NBSKUPF01 = NBSKUPF01;
    }

    public String getNBJ_TRAVAILLES() {
        return NBJ_TRAVAILLES;
    }

    public void setNBJ_TRAVAILLES(String NBJ_TRAVAILLES) {
        this.NBJ_TRAVAILLES = NBJ_TRAVAILLES;
    }

    public String getCF_AFIA() {
        return CF_AFIA;
    }

    public void setCF_AFIA(String CF_AFIA) {
        this.CF_AFIA = CF_AFIA;
    }

    public String getNBFAFIA01() {
        return NBFAFIA01;
    }

    public void setNBFAFIA01(String NBFAFIA01) {
        this.NBFAFIA01 = NBFAFIA01;
    }

    public String getNBFHALA01() {
        return NBFHALA01;
    }

    public void setNBFHALA01(String NBFHALA01) {
        this.NBFHALA01 = NBFHALA01;
    }

    public String getNBFAFIASF01() {
        return NBFAFIASF01;
    }

    public void setNBFAFIASF01(String NBFAFIASF01) {
        this.NBFAFIASF01 = NBFAFIASF01;
    }

    public String getNBFRIADZ01() {
        return NBFRIADZ01;
    }

    public void setNBFRIADZ01(String NBFRIADZ01) {
        this.NBFRIADZ01 = NBFRIADZ01;
    }

    public String getNBFZAYTOUNI01() {
        return NBFZAYTOUNI01;
    }

    public void setNBFZAYTOUNI01(String NBFZAYTOUNI01) {
        this.NBFZAYTOUNI01 = NBFZAYTOUNI01;
    }

    public String getTAUX_GPS_FACTURES() {
        return TAUX_GPS_FACTURES;
    }

    public void setTAUX_GPS_FACTURES(String TAUX_GPS_FACTURES) {
        this.TAUX_GPS_FACTURES = TAUX_GPS_FACTURES;
    }

    @Override
    public String toString() {
        return "RapportFondamental{" +
                "UTILISATEUR_NOM='" + UTILISATEUR_NOM + '\'' +
                ", NB_VISITES='" + NB_VISITES + '\'' +
                ", MOYENNE_VISITES='" + MOYENNE_VISITES + '\'' +
                ", UNIVERS='" + UNIVERS + '\'' +
                ", NBC_VISITES='" + NBC_VISITES + '\'' +
                ", COUVERTURE='" + COUVERTURE + '\'' +
                ", NBC_FACTURES='" + NBC_FACTURES + '\'' +
                ", ACTIVATION='" + ACTIVATION + '\'' +
                ", NBC_FACTURES_GPS='" + NBC_FACTURES_GPS + '\'' +
                ", MOYENNE_FACTURES='" + MOYENNE_FACTURES + '\'' +
                ", NBSKUPF01='" + NBSKUPF01 + '\'' +
                ", NBJ_TRAVAILLES='" + NBJ_TRAVAILLES + '\'' +
                ", CF_AFIA='" + CF_AFIA + '\'' +
                ", NBFAFIA01='" + NBFAFIA01 + '\'' +
                ", NBFHALA01='" + NBFHALA01 + '\'' +
                ", NBFAFIASF01='" + NBFAFIASF01 + '\'' +
                ", NBFRIADZ01='" + NBFRIADZ01 + '\'' +
                ", NBFZAYTOUNI01='" + NBFZAYTOUNI01 + '\'' +
                ", TAUX_GPS_FACTURES='" + TAUX_GPS_FACTURES + '\'' +
                '}';
    }
}
