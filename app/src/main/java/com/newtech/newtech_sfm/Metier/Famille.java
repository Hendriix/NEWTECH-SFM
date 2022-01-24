package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by stagiaireit2 on 08/08/2016.
 */
public class Famille {

   String
    FAMILLE_CODE;
    String FAMILLE_NOM;
    String FAMILLE_CATEGORIE;
    String DESCRIPTION;
    String INACTIF;
    String INACTIF_RAISON;
    String RANG;
    String VERSION;

    public String getVERSION() {
        return VERSION;
    }

    public String getRANG() {
        return RANG;
    }

    public void setRANG(String RANG) {
        this.RANG = RANG;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    String FAMILLE_COULEUR;

    public Famille() {
    }

    public Famille(JSONObject famille) {

            try {
                this.FAMILLE_CODE = famille.getString("FAMILLE_CODE");
                this.FAMILLE_NOM = famille.getString("FAMILLE_NOM");
                this.FAMILLE_CATEGORIE = famille.getString("FAMILLE_CATEGORIE");
                this.DESCRIPTION = famille.getString("DESCRIPTION");
                this.INACTIF = famille.getString("INACTIF");
                this.INACTIF_RAISON = famille.getString("INACTIF_RAISON");
                this.RANG= famille.getString("RANG");
                this.FAMILLE_COULEUR = famille.getString("FAMILLE_COULEUR");
                this.VERSION = famille.getString("VERSION");

            }catch(JSONException e){
                System.out.println(e.toString());
            }
    }
    public String getFAMILLE_CODE() {
        return FAMILLE_CODE;
    }

    public String getFAMILLE_NOM() {
        return FAMILLE_NOM;
    }

    public String getFAMILLE_CATEGORIE() {
        return FAMILLE_CATEGORIE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public String getINACTIF() {
        return INACTIF;
    }

    public String getINACTIF_RAISON() {
        return INACTIF_RAISON;
    }

    public String getFAMILLE_COULEUR() {
        return FAMILLE_COULEUR;
    }

    public void setFAMILLE_CODE(String FAMILLE_CODE) {
        this.FAMILLE_CODE = FAMILLE_CODE;
    }

    public void setFAMILLE_NOM(String FAMILLE_NOM) {
        this.FAMILLE_NOM = FAMILLE_NOM;
    }

    public void setFAMILLE_CATEGORIE(String FAMILLE_CATEGORIE) {
        this.FAMILLE_CATEGORIE = FAMILLE_CATEGORIE;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public void setINACTIF(String INACTIF) {
        this.INACTIF = INACTIF;
    }

    public void setINACTIF_RAISON(String INACTIF_RAISON) {
        this.INACTIF_RAISON = INACTIF_RAISON;
    }

    public void setFAMILLE_COULEUR(String FAMILLE_COULEUR) {
        this.FAMILLE_COULEUR = FAMILLE_COULEUR;
    }


    @Override
    public String toString() {
        return "Famille{" +
                "FAMILLE_CODE='" + FAMILLE_CODE + '\'' +
                ", FAMILLE_NOM='" + FAMILLE_NOM + '\'' +
                ", FAMILLE_CATEGORIE='" + FAMILLE_CATEGORIE + '\'' +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                ", INACTIF='" + INACTIF + '\'' +
                ", INACTIF_RAISON='" + INACTIF_RAISON + '\'' +
                ", RANG='" + RANG + '\'' +
                ", VERSION='" + VERSION + '\'' +
                ", FAMILLE_COULEUR='" + FAMILLE_COULEUR + '\'' +
                '}';
    }
}
