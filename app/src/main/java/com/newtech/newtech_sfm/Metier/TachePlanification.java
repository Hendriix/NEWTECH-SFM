package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TONPC on 10/03/2017.
 */

public class TachePlanification {

    private int ID;
    private String TACHEPLANIFICATION_CODE;
    private String TACHE_CODE;
    private String TACHEPLANIFICATION_DATE;
    private String VERSION;

    public TachePlanification(){

    }

    public TachePlanification(JSONObject p) {
        try {
            this.ID=p.getInt("ID");
            this.TACHEPLANIFICATION_CODE=p.getString("TACHEPLANIFICATION_CODE");
            this.TACHE_CODE=p.getString("TACHE_CODE");
            this.TACHEPLANIFICATION_DATE=p.getString("TACHEPLANIFICATION_DATE");
            this.VERSION=p.getString("VERSION");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public TachePlanification(TachePlanification tachePlanification){

        try{
            this.ID=tachePlanification.getID();
            this.TACHEPLANIFICATION_CODE=tachePlanification.getTACHEPLANIFICATION_CODE();
            this.TACHE_CODE=tachePlanification.getTACHE_CODE();
            this.TACHEPLANIFICATION_DATE=tachePlanification.getTACHEPLANIFICATION_DATE();
            this.VERSION=tachePlanification.getVERSION();

        }catch(Exception e){

            e.printStackTrace();

        }
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTACHE_CODE() {
        return TACHE_CODE;
    }

    public void setTACHE_CODE(String TACHE_CODE) {
        this.TACHE_CODE = TACHE_CODE;
    }

    public String getTACHEPLANIFICATION_DATE() {
        return TACHEPLANIFICATION_DATE;
    }

    public void setTACHEPLANIFICATION_DATE(String TACHEPLANIFICATION_DATE) {
        this.TACHEPLANIFICATION_DATE = TACHEPLANIFICATION_DATE;
    }

    public String getTACHEPLANIFICATION_CODE() {
        return TACHEPLANIFICATION_CODE;
    }

    public void setTACHEPLANIFICATION_CODE(String TACHEPLANIFICATION_CODE) {
        this.TACHEPLANIFICATION_CODE = TACHEPLANIFICATION_CODE;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    @Override
    public String toString() {
        return "TachePlanification{" +
                "ID=" + ID +
                ", TACHEPLANIFICATION_CODE='" + TACHEPLANIFICATION_CODE + '\'' +
                ", TACHE_CODE='" + TACHE_CODE + '\'' +
                ", TACHEPLANIFICATION_DATE='" + TACHEPLANIFICATION_DATE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}
