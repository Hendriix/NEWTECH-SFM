package com.newtech.newtech_sfm.Metier;

import com.newtech.newtech_sfm.Metier_Manager.TourneeManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by stagiaireit2 on 21/07/2016.
 */
public class Tournee {

    private String  ID;
    private String  TOURNEE_CODE;
    private String TOURNEE_NOM;
    private String DISTRIBUTEUR_CODE;
    private String REGION_CODE;
    private String ZONE_CODE;
    int FREQUENCE_VISITE;
    private String VERSION;
    public TourneeManager tourneeManager;

    public TourneeManager getTourneeManager() {
        return tourneeManager;
    }

    public void setTourneeManager(TourneeManager tourneeManager) {
        this.tourneeManager = tourneeManager;
    }

    public Tournee() {
    }

    public Tournee(JSONObject tournee) {
        try {
            this.TOURNEE_CODE = tournee.getString("TOURNEE_CODE");
            this.TOURNEE_NOM = tournee.getString("TOURNEE_NOM");
            this.DISTRIBUTEUR_CODE = tournee.getString("DISTRIBUTEUR_CODE");
            this.REGION_CODE = tournee.getString("REGION_CODE");
            this.ZONE_CODE = tournee.getString("ZONE_CODE");
            this.FREQUENCE_VISITE = tournee.getInt("FREQUENCE_VISITE");
            this.VERSION = tournee.getString("VERSION");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public String getID() {
        return ID;
    }
    public String getTOURNEE_CODE() {
        return TOURNEE_CODE;
    }
    public String getTOURNEE_NOM() {
        return TOURNEE_NOM;
    }
    public String getDISTRIBUTEUR_CODE() {
        return DISTRIBUTEUR_CODE;
    }
    public String getREGION_CODE() {
        return REGION_CODE;
    }
    public String getZONE_CODE() {
        return ZONE_CODE;
    }
    public int getFREQUENCE_VISITE() {
        return FREQUENCE_VISITE;
    }
    public String getVERSION() {
        return VERSION;
    }



    public void setID(String ID) {
        this.ID = ID;
    }
    public void setTOURNEE_CODE(String TOURNEE_CODE) {
        this.TOURNEE_CODE = TOURNEE_CODE;
    }
    public void setTOURNEE_NOM(String TOURNEE_NOM) {
        this.TOURNEE_NOM = TOURNEE_NOM;
    }
    public void setDISTRIBUTEUR_CODE(String DISTRIBUTEUR_CODE) {
        this.DISTRIBUTEUR_CODE = DISTRIBUTEUR_CODE;
    }
    public void setREGION_CODE(String REGION_CODE) {
        this.REGION_CODE = REGION_CODE;
    }
    public void setZONE_CODE(String ZONE_CODE) {
        this.ZONE_CODE = ZONE_CODE;
    }
    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }
    public void setFREQUENCE_VISITE(int FREQUENCE_VISITE) {
        this.FREQUENCE_VISITE = FREQUENCE_VISITE;
    }


    @Override

    public String toString(){
       return
           "TOURNEE_CODE :" + TOURNEE_CODE  +
           "\nTOURNEE_NOM :" + TOURNEE_NOM  +
           "\nDISTRIBUTEUR_CODE :" + DISTRIBUTEUR_CODE  +
           "\nREGION_CODE :" + REGION_CODE  +
           "\nZONE_CODE :" + ZONE_CODE  +
           "\nFREQUENCE_VISITE :" + FREQUENCE_VISITE  +
           "\n VERSION :" + VERSION  ;
    }
}
