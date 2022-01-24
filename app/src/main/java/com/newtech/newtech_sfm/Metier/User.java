package com.newtech.newtech_sfm.Metier;

import com.newtech.newtech_sfm.Metier_Manager.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by stagiaireit2 on 30/06/2016.
 */
public class User {

    String  ID,
            UTILISATEUR_CODE,
            UTILISATEUR_NOM,
            UTILISATEUR_TELEPHONE1,
            UTILISATEUR_TELEPHONE2,
            PROFILE_CODE,
            UTILISATEUR_EMAIL,
            DISTRIBUTEUR_CODE,
            INACTIF,
            INACTIF_RAISON,
            UTILISATEURSUP_CODE,
            STOCK_CODE,
            STOCKSUP_CODE,
            UTILISATEUR_DESCRIPTION,
            DATE_ENTREE,
            CREATEUR_CODE,
            VERSION;

    double PLAFOND;

    public UserManager userManager;

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public User(JSONObject user) {

        try {
            this.UTILISATEUR_CODE =user.getString("UTILISATEUR_CODE");
            this.UTILISATEUR_NOM = user.getString("UTILISATEUR_NOM");
            this.UTILISATEUR_TELEPHONE1 = user.getString("UTILISATEUR_TELEPHONE1");
            this.UTILISATEUR_TELEPHONE2 = user.getString("UTILISATEUR_TELEPHONE2");
            this.PROFILE_CODE = user.getString("PROFILE_CODE");
            this.UTILISATEUR_EMAIL = user.getString("UTILISATEUR_EMAIL");
            this.DISTRIBUTEUR_CODE = user.getString("DISTRIBUTEUR_CODE");
            this.INACTIF = user.getString("INACTIF");
            this.INACTIF_RAISON = user.getString("INACTIF_RAISON");
            this.UTILISATEURSUP_CODE = user.getString("UTILISATEURSUP_CODE");
            this.STOCK_CODE = user.getString("STOCK_CODE");
            this.STOCKSUP_CODE = user.getString("STOCKSUP_CODE");
            this.UTILISATEUR_DESCRIPTION = user.getString("UTILISATEUR_DESCRIPTION");
            this.DATE_ENTREE = user.getString("DATE_ENTREE");
            this.CREATEUR_CODE = user.getString("CREATEUR_CODE");
            this.VERSION= user.getString("VERSION");
            this.PLAFOND=user.getDouble("PLAFOND");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public User() {

    }

    public void setUTILISATEUR_CODE(String UTILISATEUR_CODE) {
        this.UTILISATEUR_CODE = UTILISATEUR_CODE;
    }

    public void setUTILISATEUR_NOM(String UTILISATEUR_NOM) {
        this.UTILISATEUR_NOM = UTILISATEUR_NOM;
    }

    public void setUTILISATEUR_TELEPHONE1(String UTILISATEUR_TELEPHONE1) {
        this.UTILISATEUR_TELEPHONE1 = UTILISATEUR_TELEPHONE1;
    }

    public void setUTILISATEUR_TELEPHONE2(String UTILISATEUR_TELEPHONE2) {
        this.UTILISATEUR_TELEPHONE2 = UTILISATEUR_TELEPHONE2;
    }

    public void setPROFILE_CODE(String PROFILE_CODE) {
        this.PROFILE_CODE = PROFILE_CODE;
    }

    public void setUTILISATEUR_EMAIL(String UTILISATEUR_EMAIL) {
        this.UTILISATEUR_EMAIL = UTILISATEUR_EMAIL;
    }

    public void setDISTRIBUTEUR_CODE(String DISTRIBUTEUR_CODE) {
        this.DISTRIBUTEUR_CODE = DISTRIBUTEUR_CODE;
    }

    public void setINACTIF(String INACTIF) {
        this.INACTIF = INACTIF;
    }

    public void setINACTIF_RAISON(String INACTIF_RAISON) {
        this.INACTIF_RAISON = INACTIF_RAISON;
    }

    public void setUTILISATEURSUP_CODE(String UTILISATEURSUP_CODE) {
        this.UTILISATEURSUP_CODE = UTILISATEURSUP_CODE;
    }

    public void setSTOCK_CODE(String STOCK_CODE) {
        this.STOCK_CODE = STOCK_CODE;
    }

    public void setSTOCKSUP_CODE(String STOCKSUP_CODE) {
        this.STOCKSUP_CODE = STOCKSUP_CODE;
    }

    public void setUTILISATEUR_DESCRIPTION(String UTILISATEUR_DESCRIPTION) {
        this.UTILISATEUR_DESCRIPTION = UTILISATEUR_DESCRIPTION;
    }

    public void setDATE_ENTREE(String DATE_ENTREE) {
        this.DATE_ENTREE = DATE_ENTREE;
    }

    public void setCREATEUR_CODE(String CREATEUR_CODE) {
        this.CREATEUR_CODE = CREATEUR_CODE;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setVERSION(String VERSION){this.VERSION = VERSION;}



    public String getUTILISATEUR_CODE() {
        return UTILISATEUR_CODE;
    }

    public String getUTILISATEUR_NOM() {
        return UTILISATEUR_NOM;
    }

    public String getUTILISATEUR_TELEPHONE1() {
        return UTILISATEUR_TELEPHONE1;
    }

    public String getUTILISATEUR_TELEPHONE2() {
        return UTILISATEUR_TELEPHONE2;
    }

    public String getPROFILE_CODE() {
        return PROFILE_CODE;
    }

    public String getUTILISATEUR_EMAIL() {
        return UTILISATEUR_EMAIL;
    }

    public String getDISTRIBUTEUR_CODE() {
        return DISTRIBUTEUR_CODE;
    }

    public String getINACTIF() {
        return INACTIF;
    }

    public String getINACTIF_RAISON() {
        return INACTIF_RAISON;
    }

    public String getUTILISATEURSUP_CODE() {
        return UTILISATEURSUP_CODE;
    }

    public String getSTOCK_CODE() {
        return STOCK_CODE;
    }

    public String getSTOCKSUP_CODE() {
        return STOCKSUP_CODE;
    }

    public String getUTILISATEUR_DESCRIPTION() {
        return UTILISATEUR_DESCRIPTION;
    }

    public String getDATE_ENTREE() {
        return DATE_ENTREE;
    }

    public String getCREATEUR_CODE() {
        return CREATEUR_CODE;
    }

    public String getID() {
        return ID;
    }

    public String getVERSION(){return VERSION;}

    public double getPLAFOND() {
        return PLAFOND;
    }

    public void setPLAFOND(double PLAFOND) {
        this.PLAFOND = PLAFOND;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID='" + ID + '\'' +
                ", UTILISATEUR_CODE='" + UTILISATEUR_CODE + '\'' +
                ", UTILISATEUR_NOM='" + UTILISATEUR_NOM + '\'' +
                ", UTILISATEUR_TELEPHONE1='" + UTILISATEUR_TELEPHONE1 + '\'' +
                ", UTILISATEUR_TELEPHONE2='" + UTILISATEUR_TELEPHONE2 + '\'' +
                ", PROFILE_CODE='" + PROFILE_CODE + '\'' +
                ", UTILISATEUR_EMAIL='" + UTILISATEUR_EMAIL + '\'' +
                ", DISTRIBUTEUR_CODE='" + DISTRIBUTEUR_CODE + '\'' +
                ", INACTIF='" + INACTIF + '\'' +
                ", INACTIF_RAISON='" + INACTIF_RAISON + '\'' +
                ", UTILISATEURSUP_CODE='" + UTILISATEURSUP_CODE + '\'' +
                ", STOCK_CODE='" + STOCK_CODE + '\'' +
                ", STOCKSUP_CODE='" + STOCKSUP_CODE + '\'' +
                ", UTILISATEUR_DESCRIPTION='" + UTILISATEUR_DESCRIPTION + '\'' +
                ", DATE_ENTREE='" + DATE_ENTREE + '\'' +
                ", CREATEUR_CODE='" + CREATEUR_CODE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                ", userManager=" + userManager +
                '}';
    }
}
