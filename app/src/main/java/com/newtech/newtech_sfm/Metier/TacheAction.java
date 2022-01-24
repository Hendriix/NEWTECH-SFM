package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TONPC on 10/03/2017.
 */

public class TacheAction {

    private int ID;
    private String TACHEACTION_CODE;
    private String TACHEACTION_NOM;
    private String TACHE_CODE;
    private String TYPE_CODE;
    private int RANG;
    private String VERSION;

    public TacheAction(){

    }

    public TacheAction(JSONObject p) {
        try {

            this.ID=p.getInt("ID");
            this.TACHEACTION_CODE=p.getString("TACHEACTION_CODE");
            this.TACHEACTION_NOM=p.getString("TACHEACTION_NOM");
            this.TACHE_CODE=p.getString("TACHE_CODE");
            this.TYPE_CODE=p.getString("TYPE_CODE");
            this.RANG=p.getInt("RANG");
            this.VERSION=p.getString("VERSION");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public TacheAction(TacheAction tacheAction){

        try{
            this.ID=tacheAction.getID();
            this.TACHEACTION_CODE=tacheAction.getTACHEACTION_CODE();
            this.TACHEACTION_NOM=tacheAction.getTACHEACTION_NOM();
            this.TACHE_CODE=tacheAction.getTACHE_CODE();
            this.TYPE_CODE=tacheAction.getTYPE_CODE();
            this.RANG=tacheAction.getRANG();
            this.VERSION=tacheAction.getVERSION();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getID() {
        return ID;
    }

    public String getTACHEACTION_CODE() {
        return TACHEACTION_CODE;
    }

    public String getTACHE_CODE() {
        return TACHE_CODE;
    }

    public int getRANG() {
        return RANG;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTACHEACTION_CODE(String TACHEACTION_CODE) {
        this.TACHEACTION_CODE = TACHEACTION_CODE;
    }

    public void setTACHE_CODE(String TACHE_CODE) {
        this.TACHE_CODE = TACHE_CODE;
    }

    public void setRANG(int RANG) {
        this.RANG = RANG;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public String getTYPE_CODE() {
        return TYPE_CODE;
    }

    public void setTYPE_CODE(String TYPE_CODE) {
        this.TYPE_CODE = TYPE_CODE;
    }

    public String getTACHEACTION_NOM() {
        return TACHEACTION_NOM;
    }

    public void setTACHEACTION_NOM(String TACHEACTION_NOM) {
        this.TACHEACTION_NOM = TACHEACTION_NOM;
    }

    @Override
    public String toString() {
        return "TacheAction{" +
                "ID=" + ID +
                ", TACHEACTION_CODE='" + TACHEACTION_CODE + "\n" +
                ", TACHEACTION_NOM='" + TACHEACTION_NOM + "\n" +
                ", TACHE_CODE='" + TACHE_CODE + "\n" +
                ", TYPE_CODE='" + TYPE_CODE + "\n" +
                ", RANG=" + RANG + "\n" +
                ", VERSION='" + VERSION + "\n" +
                '}';
    }
}
