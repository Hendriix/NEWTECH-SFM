package com.newtech.newtech_sfm.Metier;

import static java.lang.Math.abs;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by TONPC on 02/08/2017.
 */

public class Encaissement {

    private String ENCAISSEMENT_CODE;
    private String COMMANDE_CODE;
    private String CLIENT_CODE;
    private String CREDIT_DATE;
    private String CREDIT_ECHEANCE;
    private String ENCAISSEMENT_DATE;
    private String TYPE_CODE;
    private String STATUT_CODE;
    private String CATEGORIE_CODE;
    private String BANQUE;
    private String NUMERO_CHEQUE;
    private String NUMERO_COMPTE;
    private double MONTANT;
    private double MONTANT_ENCAISSE;
    private double RESTE;
    private String CREATEUR_CODE;
    private String DATE_CREATION;
    private String COMMENTAIRE;
    private int LOCAL;
    private String VERSION;
    private String SOURCE;
    private String IMAGE;


    public Encaissement(){

    }

    public Encaissement(JSONObject enc) {
        try {
            this.ENCAISSEMENT_CODE=enc.getString("ENCAISSEMENT_CODE");
            this.COMMANDE_CODE=enc.getString("COMMANDE_CODE");
            this.CLIENT_CODE =enc.getString("CLIENT_CODE");
            this.CREDIT_DATE =enc.getString("CREDIT_DATE");
            this.CREDIT_ECHEANCE =enc.getString("CREDIT_ECHEANCE");
            this.ENCAISSEMENT_DATE=enc.getString("ENCAISSEMENT_DATE");
            this.TYPE_CODE=enc.getString("TYPE_CODE");
            this.STATUT_CODE = enc.getString("STATUT_CODE");
            this.CATEGORIE_CODE =enc.getString("CATEGORIE_CODE");
            this.BANQUE=enc.getString("BANQUE");
            this.NUMERO_CHEQUE=enc.getString("NUMERO_CHEQUE");
            this.NUMERO_COMPTE=enc.getString("NUMERO_COMPTE");
            this.MONTANT=getNumberRounded(enc.getDouble("MONTANT"));
            this.MONTANT_ENCAISSE =getNumberRounded(enc.getDouble("MONTANT_ENCAISSE"));
            this.RESTE =getNumberRounded(enc.getDouble("RESTE"));
            this.CREATEUR_CODE=enc.getString("CREATEUR_CODE");
            this.DATE_CREATION=enc.getString("DATE_CREATION");
            this.COMMENTAIRE=enc.getString("COMMENTAIRE");
            this.LOCAL=enc.getInt("LOCAL");
            this.VERSION=enc.getString("VERSION");
            this.SOURCE =enc.getString("SOURCE");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Encaissement(Encaissement enc) {
        try {
            this.ENCAISSEMENT_CODE=enc.getENCAISSEMENT_CODE();
            this.COMMANDE_CODE=enc.getCOMMANDE_CODE();
            this.CLIENT_CODE = enc.getCLIENT_CODE();
            this.CREDIT_DATE = enc.getCREDIT_DATE();
            this.CREDIT_ECHEANCE = enc.getCREDIT_ECHEANCE();
            this.ENCAISSEMENT_DATE=enc.getENCAISSEMENT_DATE();
            this.TYPE_CODE=enc.getTYPE_CODE();
            this.STATUT_CODE=enc.getSTATUT_CODE();
            this.CATEGORIE_CODE= enc.getCATEGORIE_CODE();
            this.BANQUE=enc.getBANQUE();
            this.NUMERO_CHEQUE=enc.getNUMERO_CHEQUE();
            this.NUMERO_COMPTE=enc.getNUMERO_COMPTE();
            this.MONTANT=getNumberRounded(enc.getMONTANT());
            this.MONTANT_ENCAISSE = getNumberRounded(enc.getMONTANT_ENCAISSE());
            this.RESTE = getNumberRounded(enc.getRESTE());
            this.CREATEUR_CODE=enc.getCREATEUR_CODE();
            this.DATE_CREATION=enc.getDATE_CREATION();
            this.COMMENTAIRE=enc.getCOMMENTAIRE();
            this.LOCAL=enc.getLOCAL();
            this.VERSION=enc.getVERSION();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Encaissement(User utilisateur, Commande commande, String type_encaissement, double montant_encaissement, String banque) {

        SimpleDateFormat df2 = new SimpleDateFormat("yyMMddHHmmss");
        final String date_encaissement = df2.format(new java.util.Date());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String date_encaissement1=df.format(Calendar.getInstance().getTime());

        try {
            this.ENCAISSEMENT_CODE="ENC"+commande.getVENDEUR_CODE()+date_encaissement;
            this.COMMANDE_CODE=commande.getCOMMANDE_CODE();
            this.CLIENT_CODE = commande.getCLIENT_CODE();
            this.CREDIT_DATE = date_encaissement1;
            this.CREDIT_ECHEANCE = date_encaissement1;
            this.ENCAISSEMENT_DATE=date_encaissement1;
            this.TYPE_CODE=type_encaissement;
            this.STATUT_CODE = "DEFAULT";
            this.CATEGORIE_CODE = "CA0040";
            this.BANQUE= "DEFAULT";
            this.NUMERO_CHEQUE= "DEFAULT";
            this.NUMERO_COMPTE= "DEFAULT";
            this.MONTANT=getNumberRounded(montant_encaissement);
            this.MONTANT_ENCAISSE = this.MONTANT;
            this.RESTE = this.MONTANT-this.MONTANT_ENCAISSE;
            this.CREATEUR_CODE=utilisateur.getUTILISATEUR_CODE();
            this.DATE_CREATION=date_encaissement1;
            this.COMMENTAIRE="to_insert";
            this.LOCAL=1;
            this.VERSION="verifiee";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Encaissement(User utilisateur, Commande commande, String type_encaissement, double montant_encaissement) {

        SimpleDateFormat df2 = new SimpleDateFormat("yyMMddHHmmss");
        final String date_encaissement = df2.format(new java.util.Date());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String date_encaissement1=df.format(Calendar.getInstance().getTime());

        double montant = 0;

        montant = abs(montant_encaissement);

        if(commande.getCOMMANDETYPE_CODE() == "2"){

            montant = -montant;
        }

        try {
            this.ENCAISSEMENT_CODE="ENC"+commande.getVENDEUR_CODE()+date_encaissement;
            this.COMMANDE_CODE=commande.getCOMMANDE_CODE();
            this.CLIENT_CODE = commande.getCLIENT_CODE();
            this.CREDIT_DATE = date_encaissement1;
            this.CREDIT_ECHEANCE = date_encaissement1;
            this.ENCAISSEMENT_DATE=date_encaissement1;
            this.TYPE_CODE=type_encaissement;
            this.STATUT_CODE = "DEFAULT";
            this.CATEGORIE_CODE = "CA0040";
            this.BANQUE= "DEFAULT";
            this.NUMERO_CHEQUE= "DEFAULT";
            this.NUMERO_COMPTE= "DEFAULT";
            this.MONTANT=getNumberRounded(montant);
            this.MONTANT_ENCAISSE = this.MONTANT;
            this.RESTE = this.MONTANT-this.MONTANT_ENCAISSE;
            this.CREATEUR_CODE=utilisateur.getUTILISATEUR_CODE();
            this.DATE_CREATION=date_encaissement1;
            this.COMMENTAIRE="to_insert";
            this.LOCAL=1;
            this.VERSION="verifiee";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Encaissement(User utilisateur, Livraison livraison, String type_encaissement, double montant_encaissement) {

        SimpleDateFormat df2 = new SimpleDateFormat("yyMMddHHmmss");
        final String date_encaissement = df2.format(new java.util.Date());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String date_encaissement1=df.format(Calendar.getInstance().getTime());

        double montant = 0;

        montant = abs(montant_encaissement);

        if(livraison.getCOMMANDETYPE_CODE() == "2"){

            montant = -montant;
        }

        try {
            this.ENCAISSEMENT_CODE="ENC"+livraison.getVENDEUR_CODE()+date_encaissement;
            this.COMMANDE_CODE=livraison.getLIVRAISON_CODE();
            this.CLIENT_CODE = livraison.getCLIENT_CODE();
            this.CREDIT_DATE = date_encaissement1;
            this.CREDIT_ECHEANCE = date_encaissement1;
            this.ENCAISSEMENT_DATE=date_encaissement1;
            this.TYPE_CODE=type_encaissement;
            this.STATUT_CODE = "DEFAULT";
            this.CATEGORIE_CODE = "CA0040";
            this.BANQUE= "DEFAULT";
            this.NUMERO_CHEQUE= "DEFAULT";
            this.NUMERO_COMPTE= "DEFAULT";
            this.MONTANT=getNumberRounded(montant);
            this.MONTANT_ENCAISSE = this.MONTANT;
            this.RESTE = this.MONTANT-this.MONTANT_ENCAISSE;
            this.CREATEUR_CODE=utilisateur.getUTILISATEUR_CODE();
            this.DATE_CREATION=date_encaissement1;
            this.COMMENTAIRE="to_insert";
            this.LOCAL=1;
            this.VERSION="verifiee";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Encaissement(User utilisateur, Credit credit, String type_encaissement, double montant_encaissement) {

        SimpleDateFormat df2 = new SimpleDateFormat("yyMMddHHmmss");
        final String date_encaissement = df2.format(new java.util.Date());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String date_encaissement1=df.format(Calendar.getInstance().getTime());

        double montant = 0;

        montant = abs(montant_encaissement);

        try {
            this.ENCAISSEMENT_CODE="ENC"+utilisateur.getUTILISATEUR_CODE()+date_encaissement;
            this.COMMANDE_CODE=credit.getCOMMANDE_CODE();
            this.CLIENT_CODE = credit.getCLIENT_CODE();
            this.CREDIT_DATE = date_encaissement1;
            this.CREDIT_ECHEANCE = date_encaissement1;
            this.ENCAISSEMENT_DATE=date_encaissement1;
            this.TYPE_CODE=type_encaissement;
            this.STATUT_CODE = "DEFAULT";
            this.CATEGORIE_CODE = "CA0041";
            this.BANQUE= "DEFAULT";
            this.NUMERO_CHEQUE= "DEFAULT";
            this.NUMERO_COMPTE= "DEFAULT";
            this.MONTANT=getNumberRounded(montant);
            this.MONTANT_ENCAISSE = this.MONTANT;
            this.RESTE = this.MONTANT-this.MONTANT_ENCAISSE;
            this.CREATEUR_CODE=utilisateur.getUTILISATEUR_CODE();
            this.DATE_CREATION=date_encaissement1;
            this.COMMENTAIRE="to_insert";
            this.LOCAL=1;
            this.VERSION="verifiee";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////ENCAISSEMENT CREDIT//////////////////////////////////////////////
    public Encaissement(User utilisateur, Commande commande, String type_encaissement, double montant_encaissement, double montant_encaisse, String credit_echeance) {

        SimpleDateFormat df2 = new SimpleDateFormat("yyMMddHHmmss");
        final String date_encaissement = df2.format(new java.util.Date());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String date_encaissement1=df.format(Calendar.getInstance().getTime());

        double montant = 0;

        montant = abs(montant_encaissement);

        if(commande.getCOMMANDETYPE_CODE() == "2"){

            montant = -montant;
        }

        try {
            this.ENCAISSEMENT_CODE="ENC"+commande.getVENDEUR_CODE()+date_encaissement;
            this.COMMANDE_CODE=commande.getCOMMANDE_CODE();
            this.CLIENT_CODE = commande.getCLIENT_CODE();
            this.CREDIT_DATE = date_encaissement1;
            this.CREDIT_ECHEANCE = credit_echeance;
            this.ENCAISSEMENT_DATE=date_encaissement1;
            this.TYPE_CODE=type_encaissement;
            this.STATUT_CODE = "DEFAULT";
            this.CATEGORIE_CODE = "CA0040";
            this.BANQUE= "DEFAULT";
            this.NUMERO_CHEQUE= "DEFAULT";
            this.NUMERO_COMPTE= "DEFAULT";
            this.MONTANT=getNumberRounded(montant);
            this.MONTANT_ENCAISSE = montant_encaisse;
            this.RESTE = this.MONTANT-this.MONTANT_ENCAISSE;
            this.CREATEUR_CODE=utilisateur.getUTILISATEUR_CODE();
            this.DATE_CREATION=date_encaissement1;
            this.COMMENTAIRE="to_insert";
            this.LOCAL=1;
            this.VERSION="verifiee";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Encaissement(User utilisateur, Livraison livraison, String type_encaissement, double montant_encaissement, double montant_encaisse, String credit_echeance) {

        SimpleDateFormat df2 = new SimpleDateFormat("yyMMddHHmmss");
        final String date_encaissement = df2.format(new java.util.Date());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String date_encaissement1=df.format(Calendar.getInstance().getTime());

        double montant = 0;

        montant = abs(montant_encaissement);

        if(livraison.getCOMMANDETYPE_CODE() == "2"){

            montant = -montant;
        }

        try {
            this.ENCAISSEMENT_CODE="ENC"+livraison.getVENDEUR_CODE()+date_encaissement;
            this.COMMANDE_CODE=livraison.getLIVRAISON_CODE();
            this.CLIENT_CODE = livraison.getCLIENT_CODE();
            this.CREDIT_DATE = date_encaissement1;
            this.CREDIT_ECHEANCE = credit_echeance;
            this.ENCAISSEMENT_DATE=date_encaissement1;
            this.TYPE_CODE=type_encaissement;
            this.STATUT_CODE = "DEFAULT";
            this.CATEGORIE_CODE = "CA0040";
            this.BANQUE= "DEFAULT";
            this.NUMERO_CHEQUE= "DEFAULT";
            this.NUMERO_COMPTE= "DEFAULT";
            this.MONTANT=getNumberRounded(montant);
            this.MONTANT_ENCAISSE = montant_encaisse;
            this.RESTE = this.MONTANT-this.MONTANT_ENCAISSE;
            this.CREATEUR_CODE=utilisateur.getUTILISATEUR_CODE();
            this.DATE_CREATION=date_encaissement1;
            this.COMMENTAIRE="to_insert";
            this.LOCAL=1;
            this.VERSION="verifiee";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Encaissement(User utilisateur, Commande commande, String type_encaissement, double montant_encaissement, String banque, String numero_cheque, String numero_compte, String image) {

        SimpleDateFormat df2 = new SimpleDateFormat("yyMMddHHmmss");
        final String date_encaissement = df2.format(new java.util.Date());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String date_encaissement1=df.format(Calendar.getInstance().getTime());

        double montant = 0;

        montant = abs(montant_encaissement);

        if(commande.getCOMMANDETYPE_CODE() == "2"){

            montant = -montant;
        }


        try {
            this.ENCAISSEMENT_CODE="ENC"+commande.getVENDEUR_CODE()+date_encaissement;
            this.COMMANDE_CODE=commande.getCOMMANDE_CODE();
            this.CLIENT_CODE = commande.getCLIENT_CODE();
            this.CREDIT_DATE = date_encaissement1;
            this.CREDIT_ECHEANCE = date_encaissement1;
            this.ENCAISSEMENT_DATE=date_encaissement1;
            this.TYPE_CODE=type_encaissement;
            this.STATUT_CODE = "DEFAULT";
            this.CATEGORIE_CODE = "CA0040";
            this.BANQUE= banque;
            this.NUMERO_CHEQUE=numero_cheque;
            this.NUMERO_COMPTE=numero_compte;
            this.BANQUE=banque;
            this.MONTANT=getNumberRounded(montant);
            this.MONTANT_ENCAISSE = this.MONTANT;
            this.RESTE = this.MONTANT - this.MONTANT_ENCAISSE;
            this.CREATEUR_CODE=utilisateur.getUTILISATEUR_CODE();
            this.DATE_CREATION=date_encaissement1;
            this.COMMENTAIRE="to_insert";
            this.LOCAL=1;
            this.VERSION="verifiee";
            this.IMAGE= image;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Encaissement(User utilisateur, Livraison livraison, String type_encaissement, double montant_encaissement, String banque, String numero_cheque, String numero_compte) {

        SimpleDateFormat df2 = new SimpleDateFormat("yyMMddHHmmss");
        final String date_encaissement = df2.format(new java.util.Date());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String date_encaissement1=df.format(Calendar.getInstance().getTime());

        double montant = 0;

        montant = abs(montant_encaissement);

        if(livraison.getCOMMANDETYPE_CODE() == "2"){

            montant = -montant;
        }


        try {
            this.ENCAISSEMENT_CODE="ENC"+livraison.getVENDEUR_CODE()+date_encaissement;
            this.COMMANDE_CODE=livraison.getLIVRAISON_CODE();
            this.CLIENT_CODE = livraison.getCLIENT_CODE();
            this.CREDIT_DATE = date_encaissement1;
            this.CREDIT_ECHEANCE = date_encaissement1;
            this.ENCAISSEMENT_DATE=date_encaissement1;
            this.TYPE_CODE=type_encaissement;
            this.STATUT_CODE = "DEFAULT";
            this.CATEGORIE_CODE = "CA0040";
            this.BANQUE= banque;
            this.NUMERO_CHEQUE=numero_cheque;
            this.NUMERO_COMPTE=numero_compte;
            this.BANQUE=banque;
            this.MONTANT=getNumberRounded(montant);
            this.MONTANT_ENCAISSE = this.MONTANT;
            this.RESTE = this.MONTANT - this.MONTANT_ENCAISSE;
            this.CREATEUR_CODE=utilisateur.getUTILISATEUR_CODE();
            this.DATE_CREATION=date_encaissement1;
            this.COMMENTAIRE="to_insert";
            this.LOCAL=1;
            this.VERSION="verifiee";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Encaissement(User utilisateur, Credit credit, String type_encaissement, double montant_encaissement, String banque, String numero_cheque, String numero_compte) {

        SimpleDateFormat df2 = new SimpleDateFormat("yyMMddHHmmss");
        final String date_encaissement = df2.format(new java.util.Date());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String date_encaissement1=df.format(Calendar.getInstance().getTime());

        double montant = 0;

        montant = montant_encaissement;

        try {
            this.ENCAISSEMENT_CODE="ENC"+utilisateur.getUTILISATEUR_CODE()+date_encaissement;
            this.COMMANDE_CODE=credit.getCOMMANDE_CODE();
            this.CLIENT_CODE = credit.getCLIENT_CODE();
            this.CREDIT_DATE = date_encaissement1;
            this.CREDIT_ECHEANCE = date_encaissement1;
            this.ENCAISSEMENT_DATE=date_encaissement1;
            this.TYPE_CODE=type_encaissement;
            this.STATUT_CODE = "DEFAULT";
            this.CATEGORIE_CODE = "CA0041";
            this.BANQUE= "DEFAULT";
            this.NUMERO_CHEQUE=numero_cheque;
            this.NUMERO_COMPTE=numero_compte;
            this.BANQUE=banque;
            this.MONTANT=getNumberRounded(montant);
            this.MONTANT_ENCAISSE = this.MONTANT;
            this.RESTE = this.MONTANT - this.MONTANT_ENCAISSE;
            this.CREATEUR_CODE=utilisateur.getUTILISATEUR_CODE();
            this.DATE_CREATION=date_encaissement1;
            this.COMMENTAIRE="to_insert";
            this.LOCAL=1;
            this.VERSION="verifiee";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getENCAISSEMENT_CODE() {
        return ENCAISSEMENT_CODE;
    }

    public void setENCAISSEMENT_CODE(String ENCAISSEMENT_CODE) {
        this.ENCAISSEMENT_CODE = ENCAISSEMENT_CODE;
    }

    public String getCOMMANDE_CODE() {
        return COMMANDE_CODE;
    }

    public void setCOMMANDE_CODE(String COMMANDE_CODE) {
        this.COMMANDE_CODE = COMMANDE_CODE;
    }

    public String getENCAISSEMENT_DATE() {
        return ENCAISSEMENT_DATE;
    }

    public void setENCAISSEMENT_DATE(String ENCAISSEMENT_DATE) {
        this.ENCAISSEMENT_DATE = ENCAISSEMENT_DATE;
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

    public String getBANQUE() {
        return BANQUE;
    }

    public void setBANQUE(String BANQUE) {
        this.BANQUE = BANQUE;
    }

    public String getNUMERO_CHEQUE() {
        return NUMERO_CHEQUE;
    }

    public void setNUMERO_CHEQUE(String NUMERO_CHEQUE) {
        this.NUMERO_CHEQUE = NUMERO_CHEQUE;
    }

    public double getMONTANT() {
        return MONTANT;
    }

    public void setMONTANT(double MONTANT) {
        this.MONTANT = MONTANT;
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

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public int getLOCAL() {
        return LOCAL;
    }

    public void setLOCAL(int LOCAL) {
        this.LOCAL = LOCAL;
    }

    public String getNUMERO_COMPTE() {
        return NUMERO_COMPTE;
    }

    public void setNUMERO_COMPTE(String NUMERO_COMPTE) {
        this.NUMERO_COMPTE = NUMERO_COMPTE;
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

    public String getCATEGORIE_CODE() {
        return CATEGORIE_CODE;
    }

    public void setCATEGORIE_CODE(String CATEGORIE_CODE) {
        this.CATEGORIE_CODE = CATEGORIE_CODE;
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

    public String getSOURCE() {
        return SOURCE;
    }

    public void setSOURCE(String SOURCE) {
        this.SOURCE = SOURCE;
    }

    public String getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(String IMAGE) {
        this.IMAGE = IMAGE;
    }

    public double getNumberRounded(double nombre){

        double number = nombre;
        int decimalsToConsider = 2;
        BigDecimal bigDecimal = new BigDecimal(number);
        BigDecimal roundedWithScale = bigDecimal.setScale(decimalsToConsider, BigDecimal.ROUND_HALF_UP);
        //System.out.println("Rounded value with setting scale = "+roundedWithScale);


        return roundedWithScale.doubleValue();
    }

    @Override
    public String toString() {
        return "Encaissement{" +
                "ENCAISSEMENT_CODE='" + ENCAISSEMENT_CODE + '\'' +
                ", COMMANDE_CODE='" + COMMANDE_CODE + '\'' +
                ", CLIENT_CODE='" + CLIENT_CODE + '\'' +
                ", CREDIT_DATE='" + CREDIT_DATE + '\'' +
                ", CREDIT_ECHEANCE='" + CREDIT_ECHEANCE + '\'' +
                ", ENCAISSEMENT_DATE='" + ENCAISSEMENT_DATE + '\'' +
                ", TYPE_CODE='" + TYPE_CODE + '\'' +
                ", STATUT_CODE='" + STATUT_CODE + '\'' +
                ", CATEGORIE_CODE='" + CATEGORIE_CODE + '\'' +
                ", BANQUE='" + BANQUE + '\'' +
                ", NUMERO_CHEQUE='" + NUMERO_CHEQUE + '\'' +
                ", NUMERO_COMPTE='" + NUMERO_COMPTE + '\'' +
                ", MONTANT=" + MONTANT +
                ", MONTANT_ENCAISSE=" + MONTANT_ENCAISSE +
                ", RESTE=" + RESTE +
                ", CREATEUR_CODE='" + CREATEUR_CODE + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", COMMENTAIRE='" + COMMENTAIRE + '\'' +
                ", LOCAL=" + LOCAL +
                ", VERSION='" + VERSION + '\'' +
                ", SOURCE='" + SOURCE + '\'' +
                '}';
    }
}
