package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by csaylani on 15/05/2018.
 */

public class Type {

    private String TYPE_CODE;
    private String TYPE_NOM;
    private String TYPE_CATEGORIE;
    private String DESCRIPTION;
    private String VERSION;

    public Type(String TYPE_CODE, String TYPE_NOM, String TYPE_CATEGORIE, String DESCRIPTION, String VERSION) {
        this.TYPE_CODE = TYPE_CODE;
        this.TYPE_NOM = TYPE_NOM;
        this.TYPE_CATEGORIE = TYPE_CATEGORIE;
        this.DESCRIPTION = DESCRIPTION;
        this.VERSION = VERSION;
    }

    public Type() {
    }

    public Type(JSONObject type) {
        try {

            this.TYPE_CODE  = type.getString("TYPE_CODE");
            this.TYPE_NOM  = type.getString("TYPE_NOM");
            this.TYPE_CATEGORIE  = type.getString("TYPE_CATEGORIE");
            this.DESCRIPTION  = type.getString("DESCRIPTION");
            this.VERSION  = type.getString("VERSION");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Type(Type type) {

            this.TYPE_CODE  = type.getTYPE_CODE();
            this.TYPE_NOM  = type.getTYPE_NOM();
            this.TYPE_CATEGORIE  = type.getTYPE_CATEGORIE();
            this.DESCRIPTION  = type.getDESCRIPTION();
            this.VERSION  = type.getVERSION();
    }


    public String getTYPE_CODE() {
        return TYPE_CODE;
    }

    public void setTYPE_CODE(String TYPE_CODE) {
        this.TYPE_CODE = TYPE_CODE;
    }

    public String getTYPE_NOM() {
        return TYPE_NOM;
    }

    public void setTYPE_NOM(String TYPE_NOM) {
        this.TYPE_NOM = TYPE_NOM;
    }

    public String getTYPE_CATEGORIE() {
        return TYPE_CATEGORIE;
    }

    public void setTYPE_CATEGORIE(String TYPE_CATEGORIE) {
        this.TYPE_CATEGORIE = TYPE_CATEGORIE;
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
        return "Type{" +
                "TYPE_CODE='" + TYPE_CODE + '\'' +
                ", TYPE_NOM='" + TYPE_NOM + '\'' +
                ", TYPE_CATEGORIE='" + TYPE_CATEGORIE + '\'' +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}
