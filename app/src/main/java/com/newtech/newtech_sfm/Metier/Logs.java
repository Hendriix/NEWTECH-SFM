package com.newtech.newtech_sfm.Metier;


import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logs {

    private int ID;
    private String IMEI;
    private String UTILISATEUR_CODE;
    private String APPLICATION_VERSION;
    private String APPLICATION_VERSIONCODE;
    private String DATE_CREATION;
    private String DATE_LOG;
    private String TYPE_CODE;
    private String CATEGORIE_CODE;
    private String STATUT_CODE;
    private String LOG_TAG;
    private String LOG_TEXT;

    public Logs() {
    }

    public Logs(Context context,String type_code){

        String DeviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;


        try {

            this.IMEI=DeviceId;
            this.UTILISATEUR_CODE= user.getString("UTILISATEUR_CODE");
            this.APPLICATION_VERSION=versionName;
            this.APPLICATION_VERSIONCODE=String.valueOf(versionCode);
            this.DATE_CREATION = df.format(Calendar.getInstance().getTime());
            this.DATE_LOG = df.format(Calendar.getInstance().getTime());
            this.TYPE_CODE=type_code;
            this.CATEGORIE_CODE="CATEGORIE";
            this.STATUT_CODE="STATUT";
            this.LOG_TAG="CONNECTION";
            this.LOG_TEXT="TEXT";


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getUTILISATEUR_CODE() {
        return UTILISATEUR_CODE;
    }

    public void setUTILISATEUR_CODE(String UTILISATEUR_CODE) {
        this.UTILISATEUR_CODE = UTILISATEUR_CODE;
    }

    public String getAPPLICATION_VERSION() {
        return APPLICATION_VERSION;
    }

    public void setAPPLICATION_VERSION(String APPLICATION_VERSION) {
        this.APPLICATION_VERSION = APPLICATION_VERSION;
    }

    public String getAPPLICATION_VERSIONCODE() {
        return APPLICATION_VERSIONCODE;
    }

    public void setAPPLICATION_VERSIONCODE(String APPLICATION_VERSIONCODE) {
        this.APPLICATION_VERSIONCODE = APPLICATION_VERSIONCODE;
    }

    public String getDATE_CREATION() {
        return DATE_CREATION;
    }

    public void setDATE_CREATION(String DATE_CREATION) {
        this.DATE_CREATION = DATE_CREATION;
    }

    public String getDATE_LOG() {
        return DATE_LOG;
    }

    public void setDATE_LOG(String DATE_LOG) {
        this.DATE_LOG = DATE_LOG;
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

    public String getLOG_TAG() {
        return LOG_TAG;
    }

    public void setLOG_TAG(String LOG_TAG) {
        this.LOG_TAG = LOG_TAG;
    }

    public String getLOG_TEXT() {
        return LOG_TEXT;
    }

    public void setLOG_TEXT(String LOG_TEXT) {
        this.LOG_TEXT = LOG_TEXT;
    }

    @Override
    public String toString() {
        return "Log{" +
                "ID=" + ID +
                ", IMEI='" + IMEI + '\'' +
                ", UTILISATEUR_CODE='" + UTILISATEUR_CODE + '\'' +
                ", APPLICATION_VERSION='" + APPLICATION_VERSION + '\'' +
                ", APPLICATION_VERSIONCODE='" + APPLICATION_VERSIONCODE + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", DATE_LOG='" + DATE_LOG + '\'' +
                ", TYPE_CODE='" + TYPE_CODE + '\'' +
                ", CATEGORIE_CODE='" + CATEGORIE_CODE + '\'' +
                ", STATUT_CODE='" + STATUT_CODE + '\'' +
                ", LOG_TAG='" + LOG_TAG + '\'' +
                ", LOG_TEXT='" + LOG_TEXT + '\'' +
                '}';
    }
}
