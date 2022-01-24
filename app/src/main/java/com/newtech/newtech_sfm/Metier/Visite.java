package com.newtech.newtech_sfm.Metier;

import android.location.Location;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by TONPC on 13/02/2017.
 */

public class Visite {

    private int ID;
    private String VISITE_CODE;
    private String DISTRIBUTEUR_CODE;
    private String UTILISATEUR_CODE;
    private String CLIENT_CODE;
    private String TOURNEE_CODE;
    private String DATE_DEBUT;
    private String DATE_FIN;
    private String DATE_VISITE;
    private String GPS_LATITUDE;
    private String GPS_LONGITUDE;
    private String TYPE_CODE;
    private String STATUT_CODE;
    private String CATEGORIE_CODE;
    private String TACHE_CODE;
    private String CREATEUR_CODE;
    private String DATE_CREATION;
    private String VERSION;
    private int VISITE_RESULTAT;
    private int DISTANCE;
    private String VISITE_SOURCE;

    public Visite(){
        super();
    }


    public Visite( JSONObject p) {
        try {
            this.VISITE_CODE=p.getString("VISITE_CODE");
            this.DISTRIBUTEUR_CODE=p.getString("DISTRIBUTEUR_CODE");
            this.UTILISATEUR_CODE=p.getString("UTILISATEUR_CODE");
            this.CLIENT_CODE=p.getString("CLIENT_CODE");
            this.TOURNEE_CODE=p.getString("TOURNEE_CODE");
            this.DATE_DEBUT=p.getString("DATE_DEBUT");
            this.DATE_FIN=p.getString("DATE_FIN");
            this.DATE_VISITE=p.getString("DATE_VISITE");
            this.GPS_LATITUDE=p.getString("GPS_LATITUDE");
            this.GPS_LONGITUDE=p.getString("GPS_LONGITUDE");
            this.TYPE_CODE=p.getString("TYPE_CODE");
            this.STATUT_CODE=p.getString("STATUT_CODE");
            this.CATEGORIE_CODE=p.getString("CATEGORIE_CODE");
            this.TACHE_CODE=p.getString("TACHE_CODE");
            this.CREATEUR_CODE=p.getString("CREATEUR_CODE");
            this.DATE_CREATION=p.getString("DATE_CREATION");
            this.VERSION=p.getString("VERSION");
            this.VISITE_RESULTAT=p.getInt("VISITE_RESULTAT");
            this.DISTANCE=p.getInt("DISTANCE");
            this.VISITE_SOURCE=p.getString("VISITE_SOURCE");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Visite(String visite_code, String distributeur_code, String utilisateur_code,Client client,String tache_code,double latitude,double longitude,String visite_source) {



        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_visite = df.format(Calendar.getInstance().getTime());

        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        String date = df1.format(new Date());

        int distance = 0;
        try{
            distance = getDistance(client,latitude,longitude);
        }catch(ArithmeticException a){
        }



        this.VISITE_CODE = visite_code;
        this.DISTRIBUTEUR_CODE = distributeur_code;
        this.UTILISATEUR_CODE = utilisateur_code;
        this.CLIENT_CODE = client.getCLIENT_CODE();
        this.TOURNEE_CODE = client.getTOURNEE_CODE();
        this.DATE_DEBUT = date_visite;
        //this.DATE_FIN=;
        this.DATE_VISITE = date;
        this.GPS_LATITUDE = String.valueOf(latitude);
        this.GPS_LONGITUDE = String.valueOf(longitude);
        this.TYPE_CODE = "DEFAULT";
        this.STATUT_CODE = "to_insert";
        this.CATEGORIE_CODE = "DEFAULT";
        this.TACHE_CODE = tache_code;
        this.CREATEUR_CODE = utilisateur_code;
        this.DATE_CREATION = date_visite;
        this.VERSION = "non verifiee";
        this.DISTANCE = distance;
        this.VISITE_SOURCE = visite_source;

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    public int getVISITE_RESULTAT() {
        return VISITE_RESULTAT;
    }

    public void setVISITE_RESULTAT(int VISITE_RESULTAT) {
        this.VISITE_RESULTAT = VISITE_RESULTAT;
    }

    public String getVISITE_CODE() {
        return VISITE_CODE;
    }

    public void setVISITE_CODE(String VISITE_CODE) {
        this.VISITE_CODE = VISITE_CODE;
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

    public String getTOURNEE_CODE() {
        return TOURNEE_CODE;
    }

    public void setTOURNEE_CODE(String TOURNEE_CODE) {
        this.TOURNEE_CODE = TOURNEE_CODE;
    }

    public String getDATE_DEBUT() {
        return DATE_DEBUT;
    }

    public void setDATE_DEBUT(String DATE_DEBUT) {
        this.DATE_DEBUT = DATE_DEBUT;
    }

    public String getDATE_FIN() {
        return DATE_FIN;
    }

    public void setDATE_FIN(String DATE_FIN) {
        this.DATE_FIN = DATE_FIN;
    }

    public String getDATE_VISITE() {
        return DATE_VISITE;
    }

    public void setDATE_VISITE(String DATE_VISITE) {
        this.DATE_VISITE = DATE_VISITE;
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

    public String getTACHE_CODE() {
        return TACHE_CODE;
    }

    public void setTACHE_CODE(String TACHE_CODE) {
        this.TACHE_CODE = TACHE_CODE;
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

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public String getVISITE_SOURCE() {
        return VISITE_SOURCE;
    }

    public void setVISITE_SOURCE(String VISITE_SOURCE) {
        this.VISITE_SOURCE = VISITE_SOURCE;
    }

    public int getDISTANCE() {
        return DISTANCE;
    }

    public void setDISTANCE(int DISTANCE) {
        this.DISTANCE = DISTANCE;
    }

    @Override
    public String toString() {
        return "Visite{" +
                "ID=" + ID +
                ", VISITE_CODE='" + VISITE_CODE + '\'' +
                ", DISTRIBUTEUR_CODE='" + DISTRIBUTEUR_CODE + '\'' +
                ", UTILISATEUR_CODE='" + UTILISATEUR_CODE + '\'' +
                ", CLIENT_CODE='" + CLIENT_CODE + '\'' +
                ", TOURNEE_CODE='" + TOURNEE_CODE + '\'' +
                ", DATE_DEBUT='" + DATE_DEBUT + '\'' +
                ", DATE_FIN='" + DATE_FIN + '\'' +
                ", DATE_VISITE='" + DATE_VISITE + '\'' +
                ", GPS_LATITUDE='" + GPS_LATITUDE + '\'' +
                ", GPS_LONGITUDE='" + GPS_LONGITUDE + '\'' +
                ", TYPE_CODE='" + TYPE_CODE + '\'' +
                ", STATUT_CODE='" + STATUT_CODE + '\'' +
                ", CATEGORIE_CODE='" + CATEGORIE_CODE + '\'' +
                ", TACHE_CODE='" + TACHE_CODE + '\'' +
                ", CREATEUR_CODE='" + CREATEUR_CODE + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", VERSION='" + VERSION + '\'' +
                ", VISITE_RESULTAT=" + VISITE_RESULTAT +
                ", DISTANCE=" + DISTANCE +
                ", VISITE_SOURCE='" + VISITE_SOURCE + '\'' +
                '}';
    }

    public int getDistance(Client client,double latitude, double longitude){


        double positionLatitude = latitude;
        double positionLongitude = longitude;

        float [] distance = new float[1];
        distance[0]=0;

        double clientLatitue = Double.parseDouble(client.getGPS_LATITUDE().replace(",",".")) ;
        double clientLongitude = Double.parseDouble(client.getGPS_LONGITUDE().replace(",","."));

        Location.distanceBetween(positionLatitude,positionLongitude,clientLatitue,clientLongitude,distance);

        return (int)distance[0];
    }
}
