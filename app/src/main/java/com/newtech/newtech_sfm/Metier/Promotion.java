package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by stagiaireit2 on 22/07/2016.
 */
public class Promotion {

    private int ID;
    private String PROMO_CODE;
    private String PROMO_NOM;
    private String PROMO_DESCRIPTION1;
    private String PROMO_CATEGORIE;
    private String PROMO_TYPE;
    private String PROMO_STATUT;
    private String DATE_DEBUT;
    private String DATE_FIN;
    private String PERIODE_CODE;
    private int CODE_PRIORITAIRE;
    private String PROMO_BASE;
    private String PROMO_MODECALCUL;
    private String PROMO_NIVEAU;
    private String PROMO_APPLIQUESUR;
    private String PROMO_REPETITIONPALIER;
    private int INACTIF;
    private String RAISON_INACTIF;
    private String CREATEUR_CODE;
    private String DATE_CREATION;
    private String TS; //a supprimer apres
    private String COMMENTAIRE;
    private String VERSION;

    public Promotion() {
    }

    public Promotion( JSONObject p) {
        try {
            this.PROMO_CODE=p.getString("PROMO_CODE");
            this.PROMO_NOM=p.getString("PROMO_NOM");
            this.PROMO_DESCRIPTION1=p.getString("PROMO_DESCRIPTION1");
            this.PROMO_CATEGORIE=p.getString("PROMO_CATEGORIE");
            this.PROMO_TYPE=p.getString("PROMO_TYPE");
            this.PROMO_STATUT=p.getString("PROMO_STATUT");
            this.DATE_DEBUT=p.getString("DATE_DEBUT");
            this.DATE_FIN=p.getString("DATE_FIN");
            this.PERIODE_CODE=p.getString("PERIODE_CODE");
            this.CODE_PRIORITAIRE=p.getInt("CODE_PRIORITAIRE");
            this.PROMO_BASE=p.getString("PROMO_BASE");
            this.PROMO_MODECALCUL=p.getString("PROMO_MODECALCUL");
            this.PROMO_NIVEAU=p.getString("PROMO_NIVEAU");
            this.PROMO_APPLIQUESUR=p.getString("PROMO_APPLIQUESUR");
            this.PROMO_REPETITIONPALIER=p.getString("PROMO_REPETITIONPALIER");
            this.INACTIF=p.getInt("INACTIF");
            this.RAISON_INACTIF=p.getString("RAISON_INACTIF");
            this.CREATEUR_CODE=p.getString("CREATEUR_CODE");
            this.DATE_CREATION=p.getString("DATE_CREATION");
            this.TS=p.getString("TS");
            this.COMMENTAIRE=p.getString("COMMENTAIRE");
            this.VERSION=p.getString("VERSION");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//getters

    public String getPROMO_CODE() {return PROMO_CODE;}
    public String getPROMO_NOM() {return PROMO_NOM;}
    public String getPROMO_DESCRIPTION1(){return PROMO_DESCRIPTION1;}
    public String getPROMO_CATEGORIE(){return PROMO_CATEGORIE;}
    public String getPROMO_TYPE(){return PROMO_TYPE;}
    public String getPROMO_STATUT(){return PROMO_STATUT;}
    public String getDATE_DEBUT(){return DATE_DEBUT;}
    public String getDATE_FIN(){return DATE_FIN;}
    public String getPERIODE_CODE(){return PERIODE_CODE;}
    public int getCODE_PRIORITAIRE(){return CODE_PRIORITAIRE;}
    public String getPROMO_BASE(){return PROMO_BASE;}
    public String getPROMO_MODECALCUL(){return PROMO_MODECALCUL;}
    public String getPROMO_NIVEAU(){return PROMO_NIVEAU;}
    public String getPROMO_APPLIQUESUR(){return PROMO_APPLIQUESUR;}
    public String getPROMO_REPETITIONPALIER(){return PROMO_REPETITIONPALIER;}
    public int getINACTIF(){return INACTIF;}
    public String getRAISON_INACTIF(){return RAISON_INACTIF;}
    public String getCREATEUR_CODE(){return CREATEUR_CODE;}
    public String getDATE_CREATION(){return DATE_CREATION;}
    public String getTS(){return TS;}
    public String getCOMMENTAIRE(){return COMMENTAIRE;}
    public String getVERSION(){ return VERSION;}

//Setters

    public void setPROMO_CODE(String PROMO_CODE){this.PROMO_CODE = PROMO_CODE;}
    public void setPROMO_NOM(String PROMO_NOM){this.PROMO_NOM = PROMO_NOM;}
    public void setPROMO_DESCRIPTION1(String PROMO_DESCRIPTION1){this.PROMO_DESCRIPTION1 = PROMO_DESCRIPTION1;}
    public void setPROMO_CATEGORIE(String PROMO_CATEGORIE){this.PROMO_CATEGORIE = PROMO_CATEGORIE;}
    public void setPROMO_TYPE(String PROMO_TYPE){this.PROMO_TYPE = PROMO_TYPE;}
    public void setPROMO_STATUT(String PROMO_STATUT){this.PROMO_STATUT = PROMO_STATUT;}
    public void setDATE_DEBUT(String DATE_DEBUT){this.DATE_DEBUT = DATE_DEBUT;}
    public void setDATE_FIN(String DATE_FIN){this.DATE_FIN = DATE_FIN;}
    public void setPERIODE_CODE(String PERIODE_CODE){this.PERIODE_CODE = PERIODE_CODE;}
    public void setCODE_PRIORITAIRE(int CODE_PRIORITAIRE){this.CODE_PRIORITAIRE = CODE_PRIORITAIRE;}
    public void setPROMO_BASE(String PROMO_BASE){this.PROMO_BASE = PROMO_BASE;}
    public void setPROMO_MODECALCUL(String PROMO_MODECALCUL){this.PROMO_MODECALCUL = PROMO_MODECALCUL;}
    public void setPROMO_NIVEAU(String PROMO_NIVEAU){this.PROMO_NIVEAU = PROMO_NIVEAU;}
    public void setPROMO_APPLIQUESUR(String PROMO_APPLIQUESUR){this.PROMO_APPLIQUESUR = PROMO_APPLIQUESUR;}
    public void setPROMO_REPETITIONPALIER(String PROMO_REPETITIONPALIER){this.PROMO_REPETITIONPALIER = PROMO_REPETITIONPALIER;}
    public void setINACTIF(int INACTIF){this.INACTIF = INACTIF;}
    public void setRAISON_INACTIF(String RAISON_INACTIF){this.RAISON_INACTIF = RAISON_INACTIF;}
    public void setCREATEUR_CODE(String CREATEUR_CODE){this.CREATEUR_CODE = CREATEUR_CODE;}
    public void setDATE_CREATION(String DATE_CREATION){this.DATE_CREATION = DATE_CREATION;}
    public void setTS(String TS){this.TS = TS;}
    public void setCOMMENTAIRE(String COMMENTAIRE){this.COMMENTAIRE = COMMENTAIRE;}
    public void setVERSION(String VERSION){this.VERSION = VERSION;}

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "ID=" + ID +
                ", PROMO_CODE='" + PROMO_CODE + '\'' +
                ", PROMO_NOM='" + PROMO_NOM + '\'' +
                ", PROMO_DESCRIPTION1='" + PROMO_DESCRIPTION1 + '\'' +
                ", PROMO_CATEGORIE='" + PROMO_CATEGORIE + '\'' +
                ", PROMO_TYPE='" + PROMO_TYPE + '\'' +
                ", PROMO_STATUT='" + PROMO_STATUT + '\'' +
                ", DATE_DEBUT='" + DATE_DEBUT + '\'' +
                ", DATE_FIN='" + DATE_FIN + '\'' +
                ", PERIODE_CODE='" + PERIODE_CODE + '\'' +
                ", CODE_PRIORITAIRE=" + CODE_PRIORITAIRE +
                ", PROMO_BASE='" + PROMO_BASE + '\'' +
                ", PROMO_MODECALCUL='" + PROMO_MODECALCUL + '\'' +
                ", PROMO_NIVEAU='" + PROMO_NIVEAU + '\'' +
                ", PROMO_APPLIQUESUR='" + PROMO_APPLIQUESUR + '\'' +
                ", PROMO_REPETITIONPALIER='" + PROMO_REPETITIONPALIER + '\'' +
                ", INACTIF=" + INACTIF +
                ", RAISON_INACTIF='" + RAISON_INACTIF + '\'' +
                ", CREATEUR_CODE='" + CREATEUR_CODE + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", TS='" + TS + '\'' +
                ", COMMENTAIRE='" + COMMENTAIRE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}