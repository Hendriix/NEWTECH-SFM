package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Credit {

    private String CREDIT_CODE;
    private String COMMANDE_CODE;
    private String CLIENT_CODE;
    private String CREDIT_DATE;
    private String CREDIT_ECHEANCE;
    private double MONTANT_CREDIT;
    private double MONTANT_ENCAISSE;
    private double RESTE;
    private String TYPE_CODE;
    private String STATUT_CODE;
    private String CATEGORIE_CODE;
    private String DATE_CREATION;
    private String CREATEUR_CODE;
    private String COMMENTAIRE;
    private String VERSION;
    private String SOURCE;

    public Credit() {
    }

    public Credit(String CREDIT_CODE, String COMMANDE_CODE, String CLIENT_CODE, String CREDIT_DATE, String CREDIT_ECHEANCE, double MONTANT_CREDIT, double MONTANT_ENCAISSE, double RESTE, String TYPE_CODE, String STATUT_CODE, String CATEGORIE_CODE, String DATE_CREATION, String CREATEUR_CODE, String COMMENTAIRE, String VERSION, String SOURCE) {
        this.CREDIT_CODE = CREDIT_CODE;
        this.COMMANDE_CODE = COMMANDE_CODE;
        this.CLIENT_CODE = CLIENT_CODE;
        this.CREDIT_DATE = CREDIT_DATE;
        this.CREDIT_ECHEANCE = CREDIT_ECHEANCE;
        this.MONTANT_CREDIT = MONTANT_CREDIT;
        this.MONTANT_ENCAISSE = MONTANT_ENCAISSE;
        this.RESTE = RESTE;
        this.TYPE_CODE = TYPE_CODE;
        this.STATUT_CODE = STATUT_CODE;
        this.CATEGORIE_CODE = CATEGORIE_CODE;
        this.DATE_CREATION = DATE_CREATION;
        this.CREATEUR_CODE = CREATEUR_CODE;
        this.COMMENTAIRE = COMMENTAIRE;
        this.VERSION = VERSION;
        this.SOURCE = SOURCE;
    }

    public Credit(User utilisateur, Commande commande, String type_encaissement, double montant_encaissement) {

        SimpleDateFormat df2 = new SimpleDateFormat("yyMMddHHmmss");
        final String date_encaissement = df2.format(new java.util.Date());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String date_encaissement1=df.format(Calendar.getInstance().getTime());

        double montant = 0;

        montant = montant_encaissement;

        if(commande.getCOMMANDETYPE_CODE() == "2"){

            montant = -montant_encaissement;
        }

        try {
            this.CREDIT_CODE = "CRE"+commande.getVENDEUR_CODE()+date_encaissement;
            this.COMMANDE_CODE = commande.getCOMMANDE_CODE();
            this.CLIENT_CODE = commande.getCLIENT_CODE();
            this.CREDIT_DATE = date_encaissement1;
            this.CREDIT_ECHEANCE = date_encaissement1;
            this.MONTANT_CREDIT = montant;
            this.MONTANT_ENCAISSE = 0;
            this.RESTE = this.MONTANT_CREDIT-this.MONTANT_ENCAISSE;
            this.TYPE_CODE = type_encaissement;
            this.STATUT_CODE = "DEFAULT";
            this.CATEGORIE_CODE = "DEFAULT";
            this.DATE_CREATION = date_encaissement1;
            this.CREATEUR_CODE = utilisateur.getUTILISATEUR_CODE();
            this.COMMENTAIRE = "to_insert";
            this.VERSION = "verifiee";
            this.SOURCE = "";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Credit(Encaissement encaissement){

        SimpleDateFormat df2 = new SimpleDateFormat("yyMMddHHmmssSSS");
        String date_encaissement = df2.format(new java.util.Date());

        this.CREDIT_CODE = "CRE"+encaissement.getCOMMANDE_CODE()+date_encaissement;
        this.COMMANDE_CODE = encaissement.getCOMMANDE_CODE();
        this.CLIENT_CODE = encaissement.getCLIENT_CODE();
        this.CREDIT_DATE = encaissement.getCREDIT_DATE();
        this.CREDIT_ECHEANCE = encaissement.getCREDIT_ECHEANCE();
        this.MONTANT_CREDIT = encaissement.getMONTANT();
        this.MONTANT_ENCAISSE = encaissement.getMONTANT_ENCAISSE();
        this.RESTE = encaissement.getRESTE();
        this.TYPE_CODE = encaissement.getTYPE_CODE();
        this.STATUT_CODE = encaissement.getSTATUT_CODE();
        this.CATEGORIE_CODE = encaissement.getCATEGORIE_CODE();
        this.DATE_CREATION = encaissement.getDATE_CREATION();
        this.CREATEUR_CODE = encaissement.getCREATEUR_CODE();
        this.COMMENTAIRE = encaissement.getCOMMENTAIRE();
        this.VERSION = encaissement.getVERSION();
        this.SOURCE = "";
    }

    public Credit(JSONObject credit) {
        try {

            this.CREDIT_CODE =credit.getString("CREDIT_CODE");
            this.COMMANDE_CODE =credit.getString("COMMANDE_CODE");
            this.CLIENT_CODE =credit.getString("CLIENT_CODE");
            this.CREDIT_DATE =credit.getString("CREDIT_DATE");
            this.CREDIT_ECHEANCE =credit.getString("CREDIT_ECHEANCE");
            this.MONTANT_CREDIT =credit.getDouble("MONTANT_CREDIT");
            this.MONTANT_ENCAISSE =credit.getDouble("MONTANT_ENCAISSE");
            this.RESTE =credit.getDouble("RESTE");
            this.TYPE_CODE =credit.getString("TYPE_CODE");
            this.STATUT_CODE =credit.getString("STATUT_CODE");
            this.CATEGORIE_CODE =credit.getString("CATEGORIE_CODE");
            this.DATE_CREATION =credit.getString("DATE_CREATION");
            this.CREATEUR_CODE =credit.getString("CREATEUR_CODE");
            this.COMMENTAIRE =credit.getString("COMMENTAIRE");
            this.VERSION =credit.getString("VERSION");
            this.SOURCE =credit.getString("SOURCE");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getCREDIT_CODE() {
        return CREDIT_CODE;
    }

    public void setCREDIT_CODE(String CREDIT_CODE) {
        this.CREDIT_CODE = CREDIT_CODE;
    }

    public String getCOMMANDE_CODE() {
        return COMMANDE_CODE;
    }

    public void setCOMMANDE_CODE(String COMMANDE_CODE) {
        this.COMMANDE_CODE = COMMANDE_CODE;
    }

    public String getCLIENT_CODE() {
        return CLIENT_CODE;
    }

    public void setCLIENT_CODE(String CLIENT_CODE) {
        this.CLIENT_CODE = CLIENT_CODE;
    }

    public String getCREDIT_DATE() {
        return CREDIT_DATE;
    }

    public void setCREDIT_DATE(String CREDIT_DATE) {
        this.CREDIT_DATE = CREDIT_DATE;
    }

    public String getCREDIT_ECHEANCE() {
        return CREDIT_ECHEANCE;
    }

    public void setCREDIT_ECHEANCE(String CREDIT_ECHEANCE) {
        this.CREDIT_ECHEANCE = CREDIT_ECHEANCE;
    }

    public double getMONTANT_CREDIT() {
        return MONTANT_CREDIT;
    }

    public void setMONTANT_CREDIT(double MONTANT_CREDIT) {
        this.MONTANT_CREDIT = MONTANT_CREDIT;
    }

    public double getMONTANT_ENCAISSE() {
        return MONTANT_ENCAISSE;
    }

    public void setMONTANT_ENCAISSE(double MONTANT_ENCAISSE) {
        this.MONTANT_ENCAISSE = MONTANT_ENCAISSE;
    }

    public double getRESTE() {
        return RESTE;
    }

    public void setRESTE(double RESTE) {
        this.RESTE = RESTE;
    }

    public String getTYPE_CODE() {
        return TYPE_CODE;
    }

    public void setTYPE_CODE(String TYPE_CODE) {
        this.TYPE_CODE = TYPE_CODE;
    }

    public String getSTATUT_CODE() {
        return STATUT_CODE;
    }

    public void setSTATUT_CODE(String STATUT_CODE) {
        this.STATUT_CODE = STATUT_CODE;
    }

    public String getCATEGORIE_CODE() {
        return CATEGORIE_CODE;
    }

    public void setCATEGORIE_CODE(String CATEGORIE_CODE) {
        this.CATEGORIE_CODE = CATEGORIE_CODE;
    }

    public String getDATE_CREATION() {
        return DATE_CREATION;
    }

    public void setDATE_CREATION(String DATE_CREATION) {
        this.DATE_CREATION = DATE_CREATION;
    }

    public String getCREATEUR_CODE() {
        return CREATEUR_CODE;
    }

    public void setCREATEUR_CODE(String CREATEUR_CODE) {
        this.CREATEUR_CODE = CREATEUR_CODE;
    }

    public String getCOMMENTAIRE() {
        return COMMENTAIRE;
    }

    public void setCOMMENTAIRE(String COMMENTAIRE) {
        this.COMMENTAIRE = COMMENTAIRE;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public String getSOURCE() {
        return SOURCE;
    }

    public void setSOURCE(String SOURCE) {
        this.SOURCE = SOURCE;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "CREDIT_CODE='" + CREDIT_CODE + '\'' +
                ", COMMANDE_CODE='" + COMMANDE_CODE + '\'' +
                ", CLIENT_CODE='" + CLIENT_CODE + '\'' +
                ", CREDIT_DATE='" + CREDIT_DATE + '\'' +
                ", CREDIT_ECHEANCE='" + CREDIT_ECHEANCE + '\'' +
                ", MONTANT_CREDIT=" + MONTANT_CREDIT +
                ", MONTANT_ENCAISSE=" + MONTANT_ENCAISSE +
                ", RESTE=" + RESTE +
                ", TYPE_CODE=" + TYPE_CODE +
                ", STATUT_CODE='" + STATUT_CODE + '\'' +
                ", CATEGORIE_CODE='" + CATEGORIE_CODE + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", CREATEUR_CODE='" + CREATEUR_CODE + '\'' +
                ", COMMENTAIRE='" + COMMENTAIRE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                ", SOURCE='" + SOURCE + '\'' +
                '}';
    }
}
