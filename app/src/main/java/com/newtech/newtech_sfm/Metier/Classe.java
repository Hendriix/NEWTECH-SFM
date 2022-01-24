package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by csaylani on 15/05/2018.
 */

public class Classe {

    private String CLASSE_CODE;
    private String CLASSE_NOM;
    private String CLASSE_CATEGORIE;
    private String DESCRIPTION;
    private String VERSION;

    public Classe(String CLASSE_CODE, String CLASSE_NOM, String CLASSE_CATEGORIE, String DESCRIPTION, String VERSION) {
        this.CLASSE_CODE = CLASSE_CODE;
        this.CLASSE_NOM = CLASSE_NOM;
        this.CLASSE_CATEGORIE = CLASSE_CATEGORIE;
        this.DESCRIPTION = DESCRIPTION;
        this.VERSION = VERSION;
    }

    public Classe() {
    }

    public Classe(JSONObject classe) {
        try {

            this.CLASSE_CODE  = classe.getString("CLASSE_CODE");
            this.CLASSE_NOM  = classe.getString("CLASSE_NOM");
            this.CLASSE_CATEGORIE  = classe.getString("CLASSE_CATEGORIE");
            this.DESCRIPTION  = classe.getString("DESCRIPTION");
            this.VERSION  = classe.getString("VERSION");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Classe(Classe classe) {

            this.CLASSE_CODE  = classe.getCLASSE_CODE();
            this.CLASSE_NOM  = classe.getCLASSE_NOM();
            this.CLASSE_CATEGORIE  = classe.getCLASSE_CATEGORIE();
            this.DESCRIPTION  = classe.getDESCRIPTION();
            this.VERSION  = classe.getVERSION();
    }


    public String getCLASSE_CODE() {
        return CLASSE_CODE;
    }

    public void setCLASSE_CODE(String CLASSE_CODE) {
        this.CLASSE_CODE = CLASSE_CODE;
    }

    public String getCLASSE_NOM() {
        return CLASSE_NOM;
    }

    public void setCLASSE_NOM(String CLASSE_NOM) {
        this.CLASSE_NOM = CLASSE_NOM;
    }

    public String getCLASSE_CATEGORIE() {
        return CLASSE_CATEGORIE;
    }

    public void setCLASSE_CATEGORIE(String CLASSE_CATEGORIE) {
        this.CLASSE_CATEGORIE = CLASSE_CATEGORIE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "CLASSE_CODE='" + CLASSE_CODE + '\'' +
                ", CLASSE_NOM='" + CLASSE_NOM + '\'' +
                ", CLASSE_CATEGORIE='" + CLASSE_CATEGORIE + '\'' +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }


}
