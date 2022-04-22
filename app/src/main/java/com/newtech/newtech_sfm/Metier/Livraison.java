package com.newtech.newtech_sfm.Metier;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Metier_Manager.UserManager;
import com.newtech.newtech_sfm.Service.Gpstrackerservice;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by TONPC on 25/04/2017.
 */

public class Livraison {
    String
    LIVRAISON_CODE,
    LIVRAISON_DATE,
    COMMANDE_CODE,
    FACTURE_CODE,
    FACTURECLIENT_CODE,
    DATE_COMMANDE,
    DATE_LIVRAISON,
    DATE_CREATION,
    PERIODE_CODE,
    COMMANDETYPE_CODE,
    COMMANDESTATUT_CODE,
    DISTRIBUTEUR_CODE,
    VENDEUR_CODE,
    CLIENT_CODE,
    CREATEUR_CODE,
    LIVREUR_CODE,
    REGION_CODE,
    ZONE_CODE,
    SECTEUR_CODE,
    SOUSSECTEUR_CODE,
    TOURNEE_CODE,
    VISITE_CODE,
    STOCKDEPART_CODE,
    STOCKDESTINATION_CODE,
    DESTINATION_CODE,
    TS;
    double
    MONTANT_BRUT,
    REMISE,
    MONTANT_NET,
    VALEUR_COMMANDE,
    LITTRAGE_COMMANDE,
    TONNAGE_COMMANDE,
    KG_COMMANDE;
    String
    COMMENTAIRE;
    int PAIEMENT_CODE,
        NB_LIGNE;
    String
    CIRCUIT_CODE,
    CHANNEL_CODE,
    SOURCE,
    VERSION;
    String GPS_LATITUDE;
    String GPS_LONGITUDE;

    int DISTANCE;

    public Livraison(){

    }

    public Livraison( JSONObject l) {
        try {

            this.LIVRAISON_CODE=l.getString("LIVRAISON_CODE");
            this.LIVRAISON_DATE=l.getString("LIVRAISON_DATE");
            this.COMMANDE_CODE=l.getString("COMMANDE_CODE");
            this.FACTURE_CODE=l.getString("FACTURE_CODE");
            this.FACTURECLIENT_CODE=l.getString("FACTURECLIENT_CODE");
            this.DATE_COMMANDE=l.getString("DATE_COMMANDE");
            this.DATE_LIVRAISON=l.getString("DATE_LIVRAISON");
            this.DATE_CREATION=l.getString("DATE_CREATION");
            this.PERIODE_CODE=l.getString("PERIODE_CODE");
            this.COMMANDETYPE_CODE=l.getString("COMMANDETYPE_CODE");
            this.COMMANDESTATUT_CODE=l.getString("COMMANDESTATUT_CODE");
            this.DISTRIBUTEUR_CODE=l.getString("DISTRIBUTEUR_CODE");
            this.VENDEUR_CODE=l.getString("VENDEUR_CODE");
            this.CLIENT_CODE=l.getString("CLIENT_CODE");
            this.CREATEUR_CODE=l.getString("CREATEUR_CODE");
            this.LIVREUR_CODE=l.getString("LIVREUR_CODE");
            this.REGION_CODE=l.getString("REGION_CODE");
            this.ZONE_CODE=l.getString("ZONE_CODE");
            this.SECTEUR_CODE=l.getString("SECTEUR_CODE");
            this.SOUSSECTEUR_CODE=l.getString("SOUSSECTEUR_CODE");
            this.TOURNEE_CODE=l.getString("TOURNEE_CODE");
            this.VISITE_CODE=l.getString("VISITE_CODE");
            this.STOCKDEPART_CODE=l.getString("STOCKDEPART_CODE");
            this.STOCKDESTINATION_CODE=l.getString("STOCKDESTINATION_CODE");
            this.DESTINATION_CODE=l.getString("DESTINATION_CODE");
            this.TS=l.getString("TS");
            this.MONTANT_BRUT=getNumberRounded(l.getDouble("MONTANT_BRUT"));
            this.REMISE=getNumberRounded(l.getDouble("REMISE"));
            this.MONTANT_NET=getNumberRounded(l.getDouble("MONTANT_NET"));
            this.VALEUR_COMMANDE=getNumberRounded(l.getDouble("VALEUR_COMMANDE"));
            this.LITTRAGE_COMMANDE=l.getDouble("LITTRAGE_COMMANDE");
            this.TONNAGE_COMMANDE=l.getDouble("TONNAGE_COMMANDE");
            this.KG_COMMANDE=l.getDouble("KG_COMMANDE");
            this.COMMENTAIRE=l.getString("COMMENTAIRE");
            this.PAIEMENT_CODE=l.getInt("PAIEMENT_CODE");
            this.NB_LIGNE=l.getInt("NB_LIGNE");
            this.CIRCUIT_CODE=l.getString("CIRCUIT_CODE");
            this.SOURCE=l.getString("SOURCE");
            this.CHANNEL_CODE=l.getString("CHANNEL_CODE");
            this.VERSION=l.getString("VERSION");
            this.GPS_LATITUDE=l.getString("GPS_LATITUDE");
            this.GPS_LONGITUDE=l.getString("GPS_LONGITUDE");
            this.DISTANCE=l.getInt("DISTANCE");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Livraison(Commande commande,String livraison_code, Context context, Client client,double latitude,double longitude){

        int distance = 0;
        try{
            distance = getDistance(client,latitude,longitude);
        }catch(ArithmeticException a){
        }

        UserManager userManager = new UserManager(context);
        User user = userManager.get();
        try{
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            this.LIVRAISON_CODE=livraison_code;
            this.LIVRAISON_DATE=df.format(Calendar.getInstance().getTime());

            this.COMMANDE_CODE=commande.getCOMMANDE_CODE();
            this.FACTURE_CODE=commande.getFACTURE_CODE();
            this.FACTURECLIENT_CODE=commande.getFACTURECLIENT_CODE();
            this.DATE_COMMANDE=commande.getDATE_COMMANDE();
            this.DATE_LIVRAISON=df.format(Calendar.getInstance().getTime());

            this.DATE_CREATION=df.format(Calendar.getInstance().getTime());

            this.PERIODE_CODE=commande.getPERIODE_CODE();
            this.COMMANDETYPE_CODE=commande.getCOMMANDETYPE_CODE();
            this.COMMANDESTATUT_CODE=commande.getCOMMANDESTATUT_CODE();
            this.DISTRIBUTEUR_CODE=commande.getDISTRIBUTEUR_CODE();
            this.VENDEUR_CODE=commande.getVENDEUR_CODE();
            this.CLIENT_CODE=commande.getCLIENT_CODE();

            this.CREATEUR_CODE=user.getUTILISATEUR_CODE();

            this.LIVREUR_CODE=commande.getLIVREUR_CODE();

            this.REGION_CODE=commande.getREGION_CODE();
            this.ZONE_CODE=commande.getZONE_CODE();
            this.SECTEUR_CODE=commande.getSECTEUR_CODE();
            this.SOUSSECTEUR_CODE=commande.getSOUSSECTEUR_CODE();
            this.TOURNEE_CODE=commande.getTOURNEE_CODE();

            //this.VISITE_CODE=commande.getVISITE_CODE();

            this.STOCKDEPART_CODE=user.getSTOCK_CODE();
            this.STOCKDESTINATION_CODE=commande.getSTOCKDESTINATION_CODE();
            this.DESTINATION_CODE=commande.getDESTINATION_CODE();

            //this.TS=commande.getTS();

            this.MONTANT_BRUT=0;
            this.REMISE=0;
            this.MONTANT_NET=0;
            this.VALEUR_COMMANDE=0;
            this.LITTRAGE_COMMANDE=0;
            this.TONNAGE_COMMANDE=0;
            this.KG_COMMANDE=0;

            this.COMMENTAIRE="to_insert";
            this.PAIEMENT_CODE=commande.getPAIEMENT_CODE();
            this.NB_LIGNE=commande.getNB_LIGNE();
            this.CIRCUIT_CODE=commande.getCIRCUIT_CODE();
            this.SOURCE=commande.getSOURCE();
            this.CHANNEL_CODE=commande.getCHANNEL_CODE();

            this.VERSION="non_verifiee";

            this.GPS_LATITUDE = String.valueOf(latitude);
            this.GPS_LONGITUDE = String.valueOf(longitude);
            this.DISTANCE = distance;

        }catch (Exception e){

        }
    }

    public Livraison(Commande commande,String livraison_code){

        try{
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            this.LIVRAISON_CODE=livraison_code;
            this.LIVRAISON_DATE=df.format(Calendar.getInstance().getTime());

            this.COMMANDE_CODE=commande.getCOMMANDE_CODE();
            this.FACTURE_CODE=commande.getFACTURE_CODE();
            this.FACTURECLIENT_CODE=commande.getFACTURECLIENT_CODE();
            this.DATE_COMMANDE=commande.getDATE_COMMANDE();
            this.DATE_LIVRAISON=commande.getDATE_LIVRAISON();

            this.DATE_CREATION=df.format(Calendar.getInstance().getTime());

            this.PERIODE_CODE=commande.getPERIODE_CODE();
            this.COMMANDETYPE_CODE=commande.getCOMMANDETYPE_CODE();
            this.COMMANDESTATUT_CODE=commande.getCOMMANDESTATUT_CODE();
            this.DISTRIBUTEUR_CODE=commande.getDISTRIBUTEUR_CODE();
            this.VENDEUR_CODE=commande.getVENDEUR_CODE();
            this.CLIENT_CODE=commande.getCLIENT_CODE();

            this.CREATEUR_CODE=commande.getVENDEUR_CODE();

            this.LIVREUR_CODE=commande.getVENDEUR_CODE();

            this.REGION_CODE=commande.getREGION_CODE();
            this.ZONE_CODE=commande.getZONE_CODE();
            this.SECTEUR_CODE=commande.getSECTEUR_CODE();
            this.SOUSSECTEUR_CODE=commande.getSOUSSECTEUR_CODE();
            this.TOURNEE_CODE=commande.getTOURNEE_CODE();

            this.VISITE_CODE=commande.getVISITE_CODE();

            this.STOCKDEPART_CODE=commande.getSTOCKDEPART_CODE();
            this.STOCKDESTINATION_CODE=commande.getSTOCKDESTINATION_CODE();
            this.DESTINATION_CODE=commande.getDESTINATION_CODE();

            //this.TS=commande.getTS();

            this.MONTANT_BRUT=commande.getMONTANT_BRUT();
            this.REMISE=commande.getREMISE();
            this.MONTANT_NET=commande.getMONTANT_NET();
            this.VALEUR_COMMANDE=commande.getVALEUR_COMMANDE();
            this.LITTRAGE_COMMANDE=commande.getLITTRAGE_COMMANDE();
            this.TONNAGE_COMMANDE=commande.getTONNAGE_COMMANDE();
            this.KG_COMMANDE=commande.getKG_COMMANDE();

            this.COMMENTAIRE="to_insert";
            this.PAIEMENT_CODE=commande.getPAIEMENT_CODE();
            this.NB_LIGNE=commande.getNB_LIGNE();
            this.CIRCUIT_CODE=commande.getCIRCUIT_CODE();
            this.SOURCE=commande.getSOURCE();
            this.CHANNEL_CODE=commande.getCHANNEL_CODE();
            this.VERSION="non_verifiee";

            this.GPS_LATITUDE = String.valueOf(Gpstrackerservice.latitude);
            this.GPS_LONGITUDE = String.valueOf(Gpstrackerservice.longitude);

        }catch (Exception e){

        }
    }

    public Livraison (Context context , Livraison livraison, Client clientCourant, String visite_code){

        SimpleDateFormat df_code = new SimpleDateFormat("yyMMddHHmmss");
        String date_livraison=df_code.format(new java.util.Date());

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);

        DateFormat df_today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String LIVRAISON_CODE = clientCourant.getDISTRIBUTEUR_CODE()+livraison.getLIVREUR_CODE()+date_livraison;

        this.LIVRAISON_CODE=LIVRAISON_CODE;
        this.LIVRAISON_DATE=df_today.format(Calendar.getInstance().getTime());
        this.COMMANDE_CODE=livraison.getCOMMANDE_CODE();
        this.FACTURE_CODE=COMMANDE_CODE;
        this.FACTURECLIENT_CODE=COMMANDE_CODE;
        this.DATE_COMMANDE=livraison.getDATE_COMMANDE();
        this.DATE_LIVRAISON=df_today.format(Calendar.getInstance().getTime());
        this.DATE_CREATION=df_today.format(Calendar.getInstance().getTime());
        this.PERIODE_CODE=livraison.getPERIODE_CODE();
        this.COMMANDETYPE_CODE="2";
        this.COMMANDESTATUT_CODE="3";
        this.DISTRIBUTEUR_CODE=livraison.getDISTRIBUTEUR_CODE();
        this.VENDEUR_CODE=livraison.getVENDEUR_CODE();
        this.CLIENT_CODE=livraison.getCLIENT_CODE();
        this.CREATEUR_CODE=livraison.getCREATEUR_CODE();
        this.LIVREUR_CODE=livraison.getLIVREUR_CODE();
        this.REGION_CODE=livraison.getREGION_CODE();
        this.ZONE_CODE=livraison.getZONE_CODE();
        this.SECTEUR_CODE=livraison.getSECTEUR_CODE();
        this.SOUSSECTEUR_CODE=livraison.getSOUSSECTEUR_CODE();
        this.TOURNEE_CODE=livraison.getTOURNEE_CODE();
        this.VISITE_CODE=visite_code;
        this.STOCKDEPART_CODE=livraison.getSTOCKDEPART_CODE();
        this.STOCKDESTINATION_CODE=livraison.getSTOCKDESTINATION_CODE();
        this.DESTINATION_CODE=livraison.getDESTINATION_CODE();
        this.CIRCUIT_CODE=livraison.getCIRCUIT_CODE();
        this.CHANNEL_CODE=livraison.getCHANNEL_CODE();
        this.COMMENTAIRE = "to_insert";
        this.MONTANT_BRUT=-livraison.getMONTANT_BRUT();
        this.REMISE=livraison.getREMISE();
        this.MONTANT_NET=-livraison.getMONTANT_NET();
        this.LITTRAGE_COMMANDE=-livraison.getLITTRAGE_COMMANDE();
        this.TONNAGE_COMMANDE=-livraison.getTONNAGE_COMMANDE();
        this.KG_COMMANDE=-livraison.getKG_COMMANDE();
        this.VALEUR_COMMANDE=-livraison.getVALEUR_COMMANDE();
        this.PAIEMENT_CODE=livraison.getPAIEMENT_CODE();
        this.NB_LIGNE=livraison.getNB_LIGNE();
        this.SOURCE=livraison.getLIVRAISON_CODE();
        this.VERSION=livraison.getVERSION();
        this.GPS_LATITUDE=livraison.getGPS_LATITUDE();
        this.GPS_LONGITUDE=livraison.getGPS_LONGITUDE();
        this.DISTANCE=livraison.getDISTANCE();

    }

    public String getLIVRAISON_CODE() {
        return LIVRAISON_CODE;
    }

    public String getLIVRAISON_DATE() {
        return LIVRAISON_DATE;
    }

    public String getCOMMANDE_CODE() {
        return COMMANDE_CODE;
    }

    public String getFACTURE_CODE() {
        return FACTURE_CODE;
    }

    public String getFACTURECLIENT_CODE() {
        return FACTURECLIENT_CODE;
    }

    public String getDATE_COMMANDE() {
        return DATE_COMMANDE;
    }

    public String getDATE_LIVRAISON() {
        return DATE_LIVRAISON;
    }

    public String getDATE_CREATION() {
        return DATE_CREATION;
    }

    public String getPERIODE_CODE() {
        return PERIODE_CODE;
    }

    public String getCOMMANDETYPE_CODE() {
        return COMMANDETYPE_CODE;
    }

    public String getCOMMANDESTATUT_CODE() {
        return COMMANDESTATUT_CODE;
    }

    public String getDISTRIBUTEUR_CODE() {
        return DISTRIBUTEUR_CODE;
    }

    public String getVENDEUR_CODE() {
        return VENDEUR_CODE;
    }

    public String getCLIENT_CODE() {
        return CLIENT_CODE;
    }

    public String getCREATEUR_CODE() {
        return CREATEUR_CODE;
    }

    public String getLIVREUR_CODE() {
        return LIVREUR_CODE;
    }

    public String getREGION_CODE() {
        return REGION_CODE;
    }

    public String getZONE_CODE() {
        return ZONE_CODE;
    }

    public String getSECTEUR_CODE() {
        return SECTEUR_CODE;
    }

    public String getSOUSSECTEUR_CODE() {
        return SOUSSECTEUR_CODE;
    }

    public String getTOURNEE_CODE() {
        return TOURNEE_CODE;
    }

    public String getVISITE_CODE() {
        return VISITE_CODE;
    }

    public String getSTOCKDEPART_CODE() {
        return STOCKDEPART_CODE;
    }

    public String getSTOCKDESTINATION_CODE() {
        return STOCKDESTINATION_CODE;
    }

    public String getDESTINATION_CODE() {
        return DESTINATION_CODE;
    }

    public String getTS() {
        return TS;
    }

    public double getMONTANT_BRUT() {
        return MONTANT_BRUT;
    }

    public double getREMISE() {
        return REMISE;
    }

    public double getMONTANT_NET() {
        return MONTANT_NET;
    }

    public double getVALEUR_COMMANDE() {
        return VALEUR_COMMANDE;
    }

    public double getLITTRAGE_COMMANDE() {
        return LITTRAGE_COMMANDE;
    }

    public double getTONNAGE_COMMANDE() {
        return TONNAGE_COMMANDE;
    }

    public double getKG_COMMANDE() {
        return KG_COMMANDE;
    }

    public String getCOMMENTAIRE() {
        return COMMENTAIRE;
    }

    public int getPAIEMENT_CODE() {
        return PAIEMENT_CODE;
    }

    public String getCIRCUIT_CODE() {
        return CIRCUIT_CODE;
    }

    public String getCHANNEL_CODE() {
        return CHANNEL_CODE;
    }

    public void setLIVRAISON_CODE(String LIVRAISON_CODE) {
        this.LIVRAISON_CODE = LIVRAISON_CODE;
    }

    public void setLIVRAISON_DATE(String LIVRAISON_DATE) {
        this.LIVRAISON_DATE = LIVRAISON_DATE;
    }

    public void setCOMMANDE_CODE(String COMMANDE_CODE) {
        this.COMMANDE_CODE = COMMANDE_CODE;
    }

    public void setFACTURE_CODE(String FACTURE_CODE) {
        this.FACTURE_CODE = FACTURE_CODE;
    }

    public void setFACTURECLIENT_CODE(String FACTURECLIENT_CODE) {
        this.FACTURECLIENT_CODE = FACTURECLIENT_CODE;
    }

    public void setDATE_COMMANDE(String DATE_COMMANDE) {
        this.DATE_COMMANDE = DATE_COMMANDE;
    }

    public void setDATE_LIVRAISON(String DATE_LIVRAISON) {
        this.DATE_LIVRAISON = DATE_LIVRAISON;
    }

    public void setDATE_CREATION(String DATE_CREATION) {
        this.DATE_CREATION = DATE_CREATION;
    }

    public void setPERIODE_CODE(String PERIODE_CODE) {
        this.PERIODE_CODE = PERIODE_CODE;
    }

    public void setCOMMANDETYPE_CODE(String COMMANDETYPE_CODE) {
        this.COMMANDETYPE_CODE = COMMANDETYPE_CODE;
    }

    public void setCOMMANDESTATUT_CODE(String COMMANDESTATUT_CODE) {
        this.COMMANDESTATUT_CODE = COMMANDESTATUT_CODE;
    }

    public void setDISTRIBUTEUR_CODE(String DISTRIBUTEUR_CODE) {
        this.DISTRIBUTEUR_CODE = DISTRIBUTEUR_CODE;
    }

    public void setVENDEUR_CODE(String VENDEUR_CODE) {
        this.VENDEUR_CODE = VENDEUR_CODE;
    }

    public void setCLIENT_CODE(String CLIENT_CODE) {
        this.CLIENT_CODE = CLIENT_CODE;
    }

    public void setCREATEUR_CODE(String CREATEUR_CODE) {
        this.CREATEUR_CODE = CREATEUR_CODE;
    }

    public void setLIVREUR_CODE(String LIVREUR_CODE) {
        this.LIVREUR_CODE = LIVREUR_CODE;
    }

    public void setREGION_CODE(String REGION_CODE) {
        this.REGION_CODE = REGION_CODE;
    }

    public void setZONE_CODE(String ZONE_CODE) {
        this.ZONE_CODE = ZONE_CODE;
    }

    public void setSECTEUR_CODE(String SECTEUR_CODE) {
        this.SECTEUR_CODE = SECTEUR_CODE;
    }

    public void setSOUSSECTEUR_CODE(String SOUSSECTEUR_CODE) {
        this.SOUSSECTEUR_CODE = SOUSSECTEUR_CODE;
    }

    public void setTOURNEE_CODE(String TOURNEE_CODE) {
        this.TOURNEE_CODE = TOURNEE_CODE;
    }

    public void setVISITE_CODE(String VISITE_CODE) {
        this.VISITE_CODE = VISITE_CODE;
    }

    public void setSTOCKDEPART_CODE(String STOCKDEPART_CODE) {
        this.STOCKDEPART_CODE = STOCKDEPART_CODE;
    }

    public void setSTOCKDESTINATION_CODE(String STOCKDESTINATION_CODE) {
        this.STOCKDESTINATION_CODE = STOCKDESTINATION_CODE;
    }

    public void setDESTINATION_CODE(String DESTINATION_CODE) {
        this.DESTINATION_CODE = DESTINATION_CODE;
    }

    public void setTS(String TS) {
        this.TS = TS;
    }

    public void setMONTANT_BRUT(double MONTANT_BRUT) {
        this.MONTANT_BRUT = MONTANT_BRUT;
    }

    public void setREMISE(double REMISE) {
        this.REMISE = REMISE;
    }

    public void setMONTANT_NET(double MONTANT_NET) {
        this.MONTANT_NET = MONTANT_NET;
    }

    public void setVALEUR_COMMANDE(double VALEUR_COMMANDE) {
        this.VALEUR_COMMANDE = VALEUR_COMMANDE;
    }

    public void setLITTRAGE_COMMANDE(double LITTRAGE_COMMANDE) {
        this.LITTRAGE_COMMANDE = LITTRAGE_COMMANDE;
    }

    public void setTONNAGE_COMMANDE(double TONNAGE_COMMANDE) {
        this.TONNAGE_COMMANDE = TONNAGE_COMMANDE;
    }

    public void setKG_COMMANDE(double KG_COMMANDE) {
        this.KG_COMMANDE = KG_COMMANDE;
    }

    public void setCOMMENTAIRE(String COMMENTAIRE) {
        this.COMMENTAIRE = COMMENTAIRE;
    }

    public void setPAIEMENT_CODE(int PAIEMENT_CODE) {
        this.PAIEMENT_CODE = PAIEMENT_CODE;
    }

    public void setCIRCUIT_CODE(String CIRCUIT_CODE) {
        this.CIRCUIT_CODE = CIRCUIT_CODE;
    }

    public void setCHANNEL_CODE(String CHANNEL_CODE) {
        this.CHANNEL_CODE = CHANNEL_CODE;
    }

    public String getSOURCE() {
        return SOURCE;
    }

    public void setSOURCE(String SOURCE) {
        this.SOURCE = SOURCE;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public int getNB_LIGNE() {
        return NB_LIGNE;
    }

    public void setNB_LIGNE(int NB_LIGNE) {
        this.NB_LIGNE = NB_LIGNE;
    }

    public String getGPS_LATITUDE() {
        return GPS_LATITUDE;
    }

    public void setGPS_LATITUDE(String GPS_LATITUDE) {
        this.GPS_LATITUDE = GPS_LATITUDE;
    }

    public String getGPS_LONGITUDE() {
        return GPS_LONGITUDE;
    }

    public void setGPS_LONGITUDE(String GPS_LONGITUDE) {
        this.GPS_LONGITUDE = GPS_LONGITUDE;
    }

    public int getDISTANCE() {
        return DISTANCE;
    }

    public void setDISTANCE(int DISTANCE) {
        this.DISTANCE = DISTANCE;
    }

    @Override
    public String toString() {
        return "Livraison{" +
                "LIVRAISON_CODE='" + LIVRAISON_CODE + '\'' +
                ", LIVRAISON_DATE='" + LIVRAISON_DATE + '\'' +
                ", COMMANDE_CODE='" + COMMANDE_CODE + '\'' +
                ", FACTURE_CODE='" + FACTURE_CODE + '\'' +
                ", FACTURECLIENT_CODE='" + FACTURECLIENT_CODE + '\'' +
                ", DATE_COMMANDE='" + DATE_COMMANDE + '\'' +
                ", DATE_LIVRAISON='" + DATE_LIVRAISON + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", PERIODE_CODE='" + PERIODE_CODE + '\'' +
                ", COMMANDETYPE_CODE='" + COMMANDETYPE_CODE + '\'' +
                ", COMMANDESTATUT_CODE='" + COMMANDESTATUT_CODE + '\'' +
                ", DISTRIBUTEUR_CODE='" + DISTRIBUTEUR_CODE + '\'' +
                ", VENDEUR_CODE='" + VENDEUR_CODE + '\'' +
                ", CLIENT_CODE='" + CLIENT_CODE + '\'' +
                ", CREATEUR_CODE='" + CREATEUR_CODE + '\'' +
                ", LIVREUR_CODE='" + LIVREUR_CODE + '\'' +
                ", REGION_CODE='" + REGION_CODE + '\'' +
                ", ZONE_CODE='" + ZONE_CODE + '\'' +
                ", SECTEUR_CODE='" + SECTEUR_CODE + '\'' +
                ", SOUSSECTEUR_CODE='" + SOUSSECTEUR_CODE + '\'' +
                ", TOURNEE_CODE='" + TOURNEE_CODE + '\'' +
                ", VISITE_CODE='" + VISITE_CODE + '\'' +
                ", STOCKDEPART_CODE='" + STOCKDEPART_CODE + '\'' +
                ", STOCKDESTINATION_CODE='" + STOCKDESTINATION_CODE + '\'' +
                ", DESTINATION_CODE='" + DESTINATION_CODE + '\'' +
                ", TS='" + TS + '\'' +
                ", MONTANT_BRUT=" + MONTANT_BRUT +
                ", REMISE=" + REMISE +
                ", MONTANT_NET=" + MONTANT_NET +
                ", VALEUR_COMMANDE=" + VALEUR_COMMANDE +
                ", LITTRAGE_COMMANDE=" + LITTRAGE_COMMANDE +
                ", TONNAGE_COMMANDE=" + TONNAGE_COMMANDE +
                ", KG_COMMANDE=" + KG_COMMANDE +
                ", COMMENTAIRE='" + COMMENTAIRE + '\'' +
                ", PAIEMENT_CODE=" + PAIEMENT_CODE +
                ", NB_LIGNE=" + NB_LIGNE +
                ", CIRCUIT_CODE='" + CIRCUIT_CODE + '\'' +
                ", CHANNEL_CODE='" + CHANNEL_CODE + '\'' +
                ", SOURCE='" + SOURCE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                ", GPS_LATITUDE='" + GPS_LATITUDE + '\'' +
                ", GPS_LONGITUDE='" + GPS_LONGITUDE + '\'' +
                ", DISTANCE=" + DISTANCE +
                '}';
    }

    public double getNumberRounded(double nombre){

        double number = nombre;
        int decimalsToConsider = 2;
        BigDecimal bigDecimal = new BigDecimal(number);
        BigDecimal roundedWithScale = bigDecimal.setScale(decimalsToConsider, BigDecimal.ROUND_HALF_UP);
        //System.out.println("Rounded value with setting scale = "+roundedWithScale);


        return roundedWithScale.doubleValue();
    }

    public int getDistance(Client client,double latitude,double longitude){


        double positionLatitude = latitude;
        double positionLongitude = longitude;

        float [] distance = new float[1];
        distance[0]=0;

        double clientLatitue = Double.parseDouble(client.getGPS_LATITUDE().replace(",",".")) ;
        double clientLongitude = Double.parseDouble(client.getGPS_LONGITUDE().replace(",","."));

        Location.distanceBetween(positionLatitude,positionLongitude,clientLatitue,clientLongitude,distance);

        return (int)distance[0];
    }
}
