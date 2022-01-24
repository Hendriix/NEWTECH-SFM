package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TONPC on 06/09/2017.
 */

public class UtilisateurUnique {

    String
    UTILISATEUR_CODE,
    UTILISATEUR_NOM,
    UTILISATEUR_UNIQUECODE,
    INACTIF,
    INACTIF_RAISON,
    VERSION;

    public UtilisateurUnique() {
    }

    public UtilisateurUnique(JSONObject utilisateur) {
        try {

            this.UTILISATEUR_CODE=utilisateur.getString("UTILISATEUR_CODE");
            this.UTILISATEUR_NOM=utilisateur.getString("UTILISATEUR_NOM");
            this.UTILISATEUR_UNIQUECODE=utilisateur.getString("UTILISATEUR_UNIQUECODE");
            this.INACTIF = utilisateur.getString("INACTIF");
            this.INACTIF_RAISON = utilisateur.getString("INACTIF_RAISON");
            this.VERSION = utilisateur.getString("VERSION");

        }catch(JSONException e){
            System.out.println(e.toString());
        }
    }

    public String getUTILISATEUR_CODE() {
        return UTILISATEUR_CODE;
    }

    public void setUTILISATEUR_CODE(String UTILISATEUR_CODE) {
        this.UTILISATEUR_CODE = UTILISATEUR_CODE;
    }

    public String getUTILISATEUR_NOM() {
        return UTILISATEUR_NOM;
    }

    public void setUTILISATEUR_NOM(String UTILISATEUR_NOM) {
        this.UTILISATEUR_NOM = UTILISATEUR_NOM;
    }

    public String getUTILISATEUR_UNIQUECODE() {
        return UTILISATEUR_UNIQUECODE;
    }

    public void setUTILISATEUR_UNIQUECODE(String UTILISATEUR_UNIQUECODE) {
        this.UTILISATEUR_UNIQUECODE = UTILISATEUR_UNIQUECODE;
    }

    public String getINACTIF() {
        return INACTIF;
    }

    public void setINACTIF(String INACTIF) {
        this.INACTIF = INACTIF;
    }

    public String getINACTIF_RAISON() {
        return INACTIF_RAISON;
    }

    public void setINACTIF_RAISON(String INACTIF_RAISON) {
        this.INACTIF_RAISON = INACTIF_RAISON;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    @Override
    public String toString() {
        return "UtilisateurUnique{" +
                "UTILISATEUR_CODE='" + UTILISATEUR_CODE + '\'' +
                ", UTILISATEUR_NOM='" + UTILISATEUR_NOM + '\'' +
                ", UTILISATEUR_UNIQUECODE='" + UTILISATEUR_UNIQUECODE + '\'' +
                ", INACTIF='" + INACTIF + '\'' +
                ", INACTIF_RAISON='" + INACTIF_RAISON + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }

}
