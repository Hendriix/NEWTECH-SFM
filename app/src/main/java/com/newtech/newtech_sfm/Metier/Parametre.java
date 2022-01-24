package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

public class Parametre {

    private String VARIABLE;
    private String VALEUR;
    private String VERSION;


    public Parametre(String VARIABLE, String VALEUR, String VERSION) {
        this.VARIABLE = VARIABLE;
        this.VALEUR = VALEUR;
        this.VERSION = VERSION;
    }

    public Parametre() {
    }

    public Parametre(JSONObject parametre) {
        try {

            this.VARIABLE  = parametre.getString("VARIABLE");
            this.VALEUR  = parametre.getString("VALEUR");
            this.VERSION  = parametre.getString("VERSION");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getVARIABLE() {
        return VARIABLE;
    }

    public void setVARIABLE(String VARIABLE) {
        this.VARIABLE = VARIABLE;
    }

    public String getVALEUR() {
        return VALEUR;
    }

    public void setVALEUR(String VALEUR) {
        this.VALEUR = VALEUR;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    @Override
    public String toString() {
        return "Parametre{" +
                "VARIABLE='" + VARIABLE + '\'' +
                ", VALEUR='" + VALEUR + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}
