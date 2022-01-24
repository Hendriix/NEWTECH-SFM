package com.newtech.newtech_sfm.Metier;

public class ChoufouniContratImage {

    private String CHOUFOUNI_CONTRAT_CODE;
    private String IMAGE_CODE;
    private String IMAGE;
    private String TYPE_CODE;
    private String CATEGORIE_CODE;
    private String STATUT_CODE;
    private String COMMENTAIRE;
    private String DATE_CREATION;
    private String CREATEUR_CODE;
    private String VERSION;
    private String GPS_LATITUDE;
    private String GPS_LONGITUDE;
    private int DISTANCE;


    public ChoufouniContratImage(String CHOUFOUNI_CONTRAT_CODE, String IMAGE_CODE, String IMAGE, String TYPE_CODE, String CATEGORIE_CODE, String STATUT_CODE, String COMMENTAIRE, String DATE_CREATION, String CREATEUR_CODE, String VERSION, String GPS_LATITUDE, String GPS_LONGITUDE, int DISTANCE) {
        this.CHOUFOUNI_CONTRAT_CODE = CHOUFOUNI_CONTRAT_CODE;
        this.IMAGE_CODE = IMAGE_CODE;
        this.IMAGE = IMAGE;
        this.TYPE_CODE = TYPE_CODE;
        this.CATEGORIE_CODE = CATEGORIE_CODE;
        this.STATUT_CODE = STATUT_CODE;
        this.COMMENTAIRE = COMMENTAIRE;
        this.DATE_CREATION = DATE_CREATION;
        this.CREATEUR_CODE = CREATEUR_CODE;
        this.VERSION = VERSION;
        this.GPS_LATITUDE = GPS_LATITUDE;
        this.GPS_LONGITUDE = GPS_LONGITUDE;
        this.DISTANCE = DISTANCE;
    }

    public ChoufouniContratImage() {
    }

    public String getCHOUFOUNI_CONTRAT_CODE() {
        return CHOUFOUNI_CONTRAT_CODE;
    }

    public void setCHOUFOUNI_CONTRAT_CODE(String CHOUFOUNI_CONTRAT_CODE) {
        this.CHOUFOUNI_CONTRAT_CODE = CHOUFOUNI_CONTRAT_CODE;
    }

    public String getIMAGE_CODE() {
        return IMAGE_CODE;
    }

    public void setIMAGE_CODE(String IMAGE_CODE) {
        this.IMAGE_CODE = IMAGE_CODE;
    }

    public String getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(String IMAGE) {
        this.IMAGE = IMAGE;
    }

    public String getTYPE_CODE() {
        return TYPE_CODE;
    }

    public void setTYPE_CODE(String TYPE_CODE) {
        this.TYPE_CODE = TYPE_CODE;
    }

    public String getCATEGORIE_CODE() {
        return CATEGORIE_CODE;
    }

    public void setCATEGORIE_CODE(String CATEGORIE_CODE) {
        this.CATEGORIE_CODE = CATEGORIE_CODE;
    }

    public String getSTATUT_CODE() {
        return STATUT_CODE;
    }

    public void setSTATUT_CODE(String STATUT_CODE) {
        this.STATUT_CODE = STATUT_CODE;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public String getCOMMENTAIRE() {
        return COMMENTAIRE;
    }

    public void setCOMMENTAIRE(String COMMENTAIRE) {
        this.COMMENTAIRE = COMMENTAIRE;
    }

    public String getDATE_CREATION() {
        return DATE_CREATION;
    }

    public void setDATE_CREATION(String DATE_CREATION) {
        this.DATE_CREATION = DATE_CREATION;
    }

    public String getCREATEUR_CODE() {
        return CREATEUR_CODE;
    }

    public void setCREATEUR_CODE(String CREATEUR_CODE) {
        this.CREATEUR_CODE = CREATEUR_CODE;
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
        return "ChoufouniContratImage{" +
                "CHOUFOUNI_CONTRAT_CODE='" + CHOUFOUNI_CONTRAT_CODE + '\'' +
                ", IMAGE_CODE='" + IMAGE_CODE + '\'' +
                ", IMAGE='" + IMAGE + '\'' +
                ", TYPE_CODE='" + TYPE_CODE + '\'' +
                ", CATEGORIE_CODE='" + CATEGORIE_CODE + '\'' +
                ", STATUT_CODE='" + STATUT_CODE + '\'' +
                ", COMMENTAIRE='" + COMMENTAIRE + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", CREATEUR_CODE='" + CREATEUR_CODE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                ", GPS_LATITUDE='" + GPS_LATITUDE + '\'' +
                ", GPS_LONGITUDE='" + GPS_LONGITUDE + '\'' +
                ", DISTANCE=" + DISTANCE +
                '}';
    }
}
