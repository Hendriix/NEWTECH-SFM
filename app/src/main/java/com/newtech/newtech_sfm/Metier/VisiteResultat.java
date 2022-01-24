package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TONPC on 22/02/2017.
 */

public class VisiteResultat {

    private int ID;
    private String VISITERESULTAT_CODE;
    private String VISITERESULTAT_NOM;
    private int RANG;
    private String VERSION;


    public VisiteResultat(JSONObject visiteresultat) {
        try {
            this.VISITERESULTAT_CODE = visiteresultat.getString("VISITERESULTAT_CODE");
            this.VISITERESULTAT_NOM = visiteresultat.getString("VISITERESULTAT_NOM");
            this.RANG = visiteresultat.getInt("RANG");
            this.VERSION = visiteresultat.getString("VERSION");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public VisiteResultat() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getVISITERESULTAT_CODE() {
        return VISITERESULTAT_CODE;
    }

    public String getVISITERESULTAT_NOM() {
        return VISITERESULTAT_NOM;
    }

    public int getRANG() {
        return RANG;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVISITERESULTAT_CODE(String VISITERESULTAT_CODE) {
        this.VISITERESULTAT_CODE = VISITERESULTAT_CODE;
    }

    public void setVISITERESULTAT_NOM(String VISITERESULTAT_NOM) {
        this.VISITERESULTAT_NOM = VISITERESULTAT_NOM;
    }

    public void setRANG(int RANG) {
        this.RANG = RANG;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    @Override
    public String toString() {
        return "VisiteResultat{" +
                "ID=" + ID +
                ", VISITERESULTAT_CODE='" + VISITERESULTAT_CODE + '\'' +
                ", VISITERESULTAT_NOM='" + VISITERESULTAT_NOM + '\'' +
                ", RANG=" + RANG +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}
