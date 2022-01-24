package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

public class ChoufouniContrat {

    private String CHOUFOUNI_CONTRAT_CODE;
    private String CHOUFOUNI_CODE;
    private String DISTRIBUTEUR_CODE;
    private String UTILISATEUR_CODE;
    private String CLIENT_CODE;
    private String DATE_CONTRAT;
    private String TYPE_CODE;
    private String STATUT_CODE;
    private String CATEGORIE_CODE;
    private double REMISE;
    private double SOLDE;
    private String CREATEUR_CODE;
    private String DATE_CREATION;
    private String COMMENTAIRE;
    private String GPS_LATITUDE;
    private String GPS_LONGITUDE;
    private int DISTANCE;

    public ChoufouniContrat() {
    }

    public ChoufouniContrat(ChoufouniContratPull choufouniContratPull){

        this.CHOUFOUNI_CONTRAT_CODE = choufouniContratPull.getCHOUFOUNI_CONTRAT_CODE();
        this.CHOUFOUNI_CODE = choufouniContratPull.getCHOUFOUNI_CODE();
        this.DISTRIBUTEUR_CODE = choufouniContratPull.getDISTRIBUTEUR_CODE();
        this.UTILISATEUR_CODE = choufouniContratPull.getUTILISATEUR_CODE();
        this.CLIENT_CODE = choufouniContratPull.getCLIENT_CODE();
        this.DATE_CONTRAT = choufouniContratPull.getDATE_CONTRAT();
        this.TYPE_CODE = choufouniContratPull.getTYPE_CODE();
        this.STATUT_CODE = choufouniContratPull.getSTATUT_CODE();
        this.CATEGORIE_CODE = choufouniContratPull.getCATEGORIE_CODE();
        this.REMISE = choufouniContratPull.getREMISE();
        this.SOLDE = choufouniContratPull.getSOLDE();
        this.CREATEUR_CODE = choufouniContratPull.getCREATEUR_CODE();
        this.DATE_CREATION = choufouniContratPull.getDATE_CREATION();
        this.COMMENTAIRE = choufouniContratPull.getCOMMENTAIRE();
        this.GPS_LATITUDE = choufouniContratPull.getGPS_LATITUDE();
        this.GPS_LONGITUDE = choufouniContratPull.getGPS_LONGITUDE();
        this.DISTANCE = choufouniContratPull.getDISTANCE();
    }

    public ChoufouniContrat(String CHOUFOUNI_CONTRAT_CODE, String CHOUFOUNI_CODE, String DISTRIBUTEUR_CODE, String UTILISATEUR_CODE, String CLIENT_CODE, String DATE_CONTRAT, String TYPE_CODE, String STATUT_CODE, String CATEGORIE_CODE, double REMISE, double SOLDE, String CREATEUR_CODE, String DATE_CREATION, String COMMENTAIRE, String GPS_LATITUDE, String GPS_LONGITUDE, int DISTANCE) {
        this.CHOUFOUNI_CONTRAT_CODE = CHOUFOUNI_CONTRAT_CODE;
        this.CHOUFOUNI_CODE = CHOUFOUNI_CODE;
        this.DISTRIBUTEUR_CODE = DISTRIBUTEUR_CODE;
        this.UTILISATEUR_CODE = UTILISATEUR_CODE;
        this.CLIENT_CODE = CLIENT_CODE;
        this.DATE_CONTRAT = DATE_CONTRAT;
        this.TYPE_CODE = TYPE_CODE;
        this.STATUT_CODE = STATUT_CODE;
        this.CATEGORIE_CODE = CATEGORIE_CODE;
        this.REMISE = REMISE;
        this.SOLDE = SOLDE;
        this.CREATEUR_CODE = CREATEUR_CODE;
        this.DATE_CREATION = DATE_CREATION;
        this.COMMENTAIRE = COMMENTAIRE;
        this.GPS_LATITUDE = GPS_LATITUDE;
        this.GPS_LONGITUDE = GPS_LONGITUDE;
        this.DISTANCE = DISTANCE;
    }

    public ChoufouniContrat(JSONObject choufouniContrat) {

        try {

        this.CHOUFOUNI_CONTRAT_CODE = choufouniContrat.getString("CHOUFOUNI_CONTRAT_CODE");
        this.CHOUFOUNI_CODE = choufouniContrat.getString("CHOUFOUNI_CODE");
        this.DISTRIBUTEUR_CODE = choufouniContrat.getString("DISTRIBUTEUR_CODE");
        this.UTILISATEUR_CODE = choufouniContrat.getString("UTILISATEUR_CODE");
        this.CLIENT_CODE = choufouniContrat.getString("CLIENT_CODE");
        this.DATE_CONTRAT = choufouniContrat.getString("DATE_CONTRAT");
        this.TYPE_CODE = choufouniContrat.getString("TYPE_CODE");
        this.STATUT_CODE = choufouniContrat.getString("STATUT_CODE");
        this.CATEGORIE_CODE = choufouniContrat.getString("CATEGORIE_CODE");
        this.REMISE = choufouniContrat.getDouble("REMISE");
        this.SOLDE = choufouniContrat.getDouble("SOLDE");
        this.CREATEUR_CODE = choufouniContrat.getString("CREATEUR_CODE");
        this.DATE_CREATION = choufouniContrat.getString("DATE_CREATION");
        this.COMMENTAIRE = choufouniContrat.getString("COMMENTAIRE");
        this.GPS_LATITUDE=choufouniContrat.getString("GPS_LATITUDE");
        this.GPS_LONGITUDE=choufouniContrat.getString("GPS_LONGITUDE");
        this.DISTANCE=choufouniContrat.getInt("DISTANCE");

        }catch(JSONException e){
            System.out.println(e.toString());
        }
    }

    public String getCHOUFOUNI_CODE() {
        return CHOUFOUNI_CODE;
    }

    public void setCHOUFOUNI_CODE(String CHOUFOUNI_CODE) {
        this.CHOUFOUNI_CODE = CHOUFOUNI_CODE;
    }

    public String getDISTRIBUTEUR_CODE() {
        return DISTRIBUTEUR_CODE;
    }

    public void setDISTRIBUTEUR_CODE(String DISTRIBUTEUR_CODE) {
        this.DISTRIBUTEUR_CODE = DISTRIBUTEUR_CODE;
    }

    public String getUTILISATEUR_CODE() {
        return UTILISATEUR_CODE;
    }

    public void setUTILISATEUR_CODE(String UTILISATEUR_CODE) {
        this.UTILISATEUR_CODE = UTILISATEUR_CODE;
    }

    public String getCLIENT_CODE() {
        return CLIENT_CODE;
    }

    public void setCLIENT_CODE(String CLIENT_CODE) {
        this.CLIENT_CODE = CLIENT_CODE;
    }

    public String getDATE_CONTRAT() {
        return DATE_CONTRAT;
    }

    public void setDATE_CONTRAT(String DATE_CONTRAT) {
        this.DATE_CONTRAT = DATE_CONTRAT;
    }

    public String getTYPE_CODE() {
        return TYPE_CODE;
    }

    public void setTYPE_CODE(String TYPE_CODE) {
        this.TYPE_CODE = TYPE_CODE;
    }

    public String getSTATUT_CODE() {
        return STATUT_CODE;
    }

    public void setSTATUT_CODE(String STATUT_CODE) {
        this.STATUT_CODE = STATUT_CODE;
    }

    public String getCATEGORIE_CODE() {
        return CATEGORIE_CODE;
    }

    public void setCATEGORIE_CODE(String CATEGORIE_CODE) {
        this.CATEGORIE_CODE = CATEGORIE_CODE;
    }

    public double getREMISE() {
        return REMISE;
    }

    public void setREMISE(double REMISE) {
        this.REMISE = REMISE;
    }

    public double getSOLDE() {
        return SOLDE;
    }

    public void setSOLDE(double SOLDE) {
        this.SOLDE = SOLDE;
    }

    public String getCREATEUR_CODE() {
        return CREATEUR_CODE;
    }

    public void setCREATEUR_CODE(String CREATEUR_CODE) {
        this.CREATEUR_CODE = CREATEUR_CODE;
    }

    public String getDATE_CREATION() {
        return DATE_CREATION;
    }

    public void setDATE_CREATION(String DATE_CREATION) {
        this.DATE_CREATION = DATE_CREATION;
    }

    public String getCHOUFOUNI_CONTRAT_CODE() {
        return CHOUFOUNI_CONTRAT_CODE;
    }

    public void setCHOUFOUNI_CONTRAT_CODE(String CHOUFOUNI_CONTRAT_CODE) {
        this.CHOUFOUNI_CONTRAT_CODE = CHOUFOUNI_CONTRAT_CODE;
    }

    public String getCOMMENTAIRE() {
        return COMMENTAIRE;
    }

    public void setCOMMENTAIRE(String COMMENTAIRE) {
        this.COMMENTAIRE = COMMENTAIRE;
    }

    public String getGPS_LATITUDE() {
        return GPS_LATITUDE;
    }

    public void setGPS_LATITUDE(String GPS_LATITUDE) {
        this.GPS_LATITUDE = GPS_LATITUDE;
    }

    public String getGPS_LONGITUDE() {
        return GPS_LONGITUDE;
    }

    public void setGPS_LONGITUDE(String GPS_LONGITUDE) {
        this.GPS_LONGITUDE = GPS_LONGITUDE;
    }

    public int getDISTANCE() {
        return DISTANCE;
    }

    public void setDISTANCE(int DISTANCE) {
        this.DISTANCE = DISTANCE;
    }

    @Override
    public String toString() {
        return "ChoufouniContrat{" +
                "CHOUFOUNI_CONTRAT_CODE='" + CHOUFOUNI_CONTRAT_CODE + '\'' +
                ", CHOUFOUNI_CODE='" + CHOUFOUNI_CODE + '\'' +
                ", DISTRIBUTEUR_CODE='" + DISTRIBUTEUR_CODE + '\'' +
                ", UTILISATEUR_CODE='" + UTILISATEUR_CODE + '\'' +
                ", CLIENT_CODE='" + CLIENT_CODE + '\'' +
                ", DATE_CONTRAT='" + DATE_CONTRAT + '\'' +
                ", TYPE_CODE='" + TYPE_CODE + '\'' +
                ", STATUT_CODE='" + STATUT_CODE + '\'' +
                ", CATEGORIE_CODE='" + CATEGORIE_CODE + '\'' +
                ", REMISE=" + REMISE +
                ", SOLDE=" + SOLDE +
                ", CREATEUR_CODE='" + CREATEUR_CODE + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", COMMENTAIRE='" + COMMENTAIRE + '\'' +
                ", GPS_LATITUDE='" + GPS_LATITUDE + '\'' +
                ", GPS_LONGITUDE='" + GPS_LONGITUDE + '\'' +
                ", DISTANCE=" + DISTANCE +
                '}';
    }
}
