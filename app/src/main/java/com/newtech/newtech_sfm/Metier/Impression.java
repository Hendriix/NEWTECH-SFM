package com.newtech.newtech_sfm.Metier;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

/**
 * Created by TONPC on 02/06/2017.
 */

public class Impression {

    private Integer ID;
    private String IMPRESSION_CODE;
    private String IMPRESSION_TEXT;
    private Integer STATUT_CODE;
    private String TYPE_CODE;
    private String CATEGORIE_CODE;
    private String CREATEUR_CODE;
    private String DATE_CREATION;
    private String COMMENTAIRE;


    public Impression(Integer ID, String IMPRESSION_CODE, String IMPRESSION_TEXT, Integer STATUT_CODE, String TYPE_CODE, String CATEGORIE_CODE, String CREATEUR_CODE, String DATE_CREATION, String COMMENTAIRE) {
        this.ID = ID;
        this.IMPRESSION_CODE = IMPRESSION_CODE;
        this.IMPRESSION_TEXT = IMPRESSION_TEXT;
        this.STATUT_CODE = STATUT_CODE;
        this.TYPE_CODE = TYPE_CODE;
        this.CATEGORIE_CODE = CATEGORIE_CODE;
        this.CREATEUR_CODE = CREATEUR_CODE;
        this.DATE_CREATION = DATE_CREATION;
        this.COMMENTAIRE = COMMENTAIRE;
    }

    public Impression(Context context,String impression_code,String impression_text,String type_code,Integer statut_code,String categorie_code) throws JSONException {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);
        SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");

        this.IMPRESSION_CODE=impression_code;
        this.IMPRESSION_TEXT=impression_text;
        this.TYPE_CODE=type_code;
        this.STATUT_CODE=statut_code;
        this.CATEGORIE_CODE=categorie_code;
        this.CREATEUR_CODE=user.getString("UTILISATEUR_CODE");
        this.DATE_CREATION=df.format(new java.util.Date());
        this.COMMENTAIRE="Impression";

    }

    public Impression() {
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getIMPRESSION_CODE() {
        return IMPRESSION_CODE;
    }

    public void setIMPRESSION_CODE(String IMPRESSION_CODE) {
        this.IMPRESSION_CODE = IMPRESSION_CODE;
    }

    public String getIMPRESSION_TEXT() {
        return IMPRESSION_TEXT;
    }

    public void setIMPRESSION_TEXT(String IMPRESSION_TEXT) {
        this.IMPRESSION_TEXT = IMPRESSION_TEXT;
    }

    public Integer getSTATUT_CODE() {
        return STATUT_CODE;
    }

    public void setSTATUT_CODE(Integer STATUT_CODE) {
        this.STATUT_CODE = STATUT_CODE;
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

    public String getCOMMENTAIRE() {
        return COMMENTAIRE;
    }

    public void setCOMMENTAIRE(String COMMENTAIRE) {
        this.COMMENTAIRE = COMMENTAIRE;
    }

    @Override
    public String toString() {
        return "Impression{" +
                "ID=" + ID +
                ", IMPRESSION_CODE='" + IMPRESSION_CODE + '\'' +
                ", IMPRESSION_TEXT='" + IMPRESSION_TEXT + '\'' +
                ", STATUT_CODE='" + STATUT_CODE + '\'' +
                ", TYPE_CODE='" + TYPE_CODE + '\'' +
                ", CATEGORIE_CODE='" + CATEGORIE_CODE + '\'' +
                ", CREATEUR_CODE='" + CREATEUR_CODE + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", COMMENTAIRE='" + COMMENTAIRE + '\'' +
                '}';
    }
}
