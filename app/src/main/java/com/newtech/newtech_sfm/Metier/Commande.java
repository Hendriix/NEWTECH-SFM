package com.newtech.newtech_sfm.Metier;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Activity.ClientActivity;
import com.newtech.newtech_sfm.Service.Gpstrackerservice;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by stagiaireit2 on 02/08/2016.
 */
public class Commande implements Parcelable {
    String
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
    TS,
    CIRCUIT_CODE,
    CHANNEL_CODE,
    COMMENTAIRE,
    LIEU_LIVRAISON;
    double
    MONTANT_BRUT,
    REMISE,
    MONTANT_NET,
    VALEUR_COMMANDE,
    LITTRAGE_COMMANDE,
    TONNAGE_COMMANDE,
    KG_COMMANDE;
   int
       PAIEMENT_CODE,
       NB_LIGNE;
    String
    STATUT_CODE,
    SOURCE,
    VERSION;
    String GPS_LATITUDE;
    String GPS_LONGITUDE;
    int DISTANCE;

    public Commande() {
    }

    public Commande( JSONObject p) {
        try {
            this.COMMANDE_CODE=p.getString("COMMANDE_CODE");
            this.FACTURE_CODE=p.getString("FACTURE_CODE");
            this.FACTURECLIENT_CODE=p.getString("FACTURECLIENT_CODE");
            this.DATE_COMMANDE=p.getString("DATE_COMMANDE");
            this.DATE_LIVRAISON=p.getString("DATE_LIVRAISON");
            this.DATE_CREATION=p.getString("DATE_CREATION");
            this.PERIODE_CODE=p.getString("PERIODE_CODE");
            this.COMMANDETYPE_CODE=p.getString("COMMANDETYPE_CODE");
            this.COMMANDESTATUT_CODE=p.getString("COMMANDESTATUT_CODE");
            this.DISTRIBUTEUR_CODE=p.getString("DISTRIBUTEUR_CODE");
            this.VENDEUR_CODE=p.getString("VENDEUR_CODE");
            this.CLIENT_CODE=p.getString("CLIENT_CODE");
            this.CREATEUR_CODE=p.getString("CREATEUR_CODE");
            this.LIVREUR_CODE=p.getString("LIVREUR_CODE");
            this.REGION_CODE=p.getString("REGION_CODE");
            this.ZONE_CODE=p.getString("ZONE_CODE");
            this.SECTEUR_CODE=p.getString("SECTEUR_CODE");
            this.SOUSSECTEUR_CODE=p.getString("SOUSSECTEUR_CODE");
            this.TOURNEE_CODE=p.getString("TOURNEE_CODE");
            this.VISITE_CODE=p.getString("VISITE_CODE");
            this.STOCKDEPART_CODE=p.getString("STOCKDEPART_CODE");
            this.STOCKDESTINATION_CODE=p.getString("STOCKDESTINATION_CODE");
            this.DESTINATION_CODE=p.getString("DESTINATION_CODE");
            this.TS=p.getString("TS");
            this.CIRCUIT_CODE=p.getString("CIRCUIT_CODE");
            this.CHANNEL_CODE=p.getString("CHANNEL_CODE");
            this.COMMENTAIRE=p.getString("COMMENTAIRE");
            this.LIEU_LIVRAISON=p.getString("LIEU_LIVRAISON");
            this.MONTANT_BRUT=getNumberRounded(p.getDouble("MONTANT_BRUT"));
            this.REMISE=getNumberRounded(p.getDouble("REMISE"));
            this.MONTANT_NET=getNumberRounded(p.getDouble("MONTANT_NET"));
            this.LITTRAGE_COMMANDE=p.getDouble("LITTRAGE_COMMANDE");
            this.TONNAGE_COMMANDE=p.getDouble("TONNAGE_COMMANDE");
            this.KG_COMMANDE=p.getDouble("KG_COMMANDE");
            this.VALEUR_COMMANDE=getNumberRounded(p.getDouble("VALEUR_COMMANDE"));
            this.PAIEMENT_CODE=p.getInt("PAIEMENT_CODE");
            this.NB_LIGNE=p.getInt("NB_LIGNE");
            this.STATUT_CODE=p.getString("STATUT_CODE");
            this.SOURCE=p.getString("SOURCE");
            this.VERSION=p.getString("VERSION");
            this.GPS_LATITUDE=p.getString("GPS_LATITUDE");
            this.GPS_LONGITUDE=p.getString("GPS_LONGITUDE");
            this.DISTANCE=p.getInt("DISTANCE");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public Commande( CommandeNonCloturee cmdNC) {
        try {
            this.COMMANDE_CODE=cmdNC.getCOMMANDE_CODE();
            this.FACTURE_CODE=cmdNC.getFACTURE_CODE();
            this.FACTURECLIENT_CODE=cmdNC.getFACTURECLIENT_CODE();
            this.DATE_COMMANDE=cmdNC.getDATE_COMMANDE();
            this.DATE_LIVRAISON=cmdNC.getDATE_LIVRAISON();
            this.DATE_CREATION=cmdNC.getDATE_CREATION();
            this.PERIODE_CODE=cmdNC.getPERIODE_CODE();
            this.COMMANDETYPE_CODE=cmdNC.getCOMMANDETYPE_CODE();
            this.COMMANDESTATUT_CODE=cmdNC.getCOMMANDESTATUT_CODE();
            this.DISTRIBUTEUR_CODE=cmdNC.getDISTRIBUTEUR_CODE();
            this.VENDEUR_CODE=cmdNC.getVENDEUR_CODE();
            this.CLIENT_CODE=cmdNC.getCLIENT_CODE();
            this.CREATEUR_CODE=cmdNC.getCREATEUR_CODE();
            this.LIVREUR_CODE=cmdNC.getLIVREUR_CODE();
            this.REGION_CODE=cmdNC.getREGION_CODE();
            this.ZONE_CODE=cmdNC.getZONE_CODE();
            this.SECTEUR_CODE=cmdNC.getSECTEUR_CODE();
            this.SOUSSECTEUR_CODE=cmdNC.getSOUSSECTEUR_CODE();
            this.TOURNEE_CODE=cmdNC.getTOURNEE_CODE();
            this.VISITE_CODE=cmdNC.getVISITE_CODE();
            this.STOCKDEPART_CODE=cmdNC.getSTOCKDEPART_CODE();
            this.STOCKDESTINATION_CODE=cmdNC.getSTOCKDESTINATION_CODE();
            this.DESTINATION_CODE=cmdNC.getDESTINATION_CODE();
            this.TS=cmdNC.getTS();
            this.CIRCUIT_CODE=cmdNC.getCIRCUIT_CODE();
            this.CHANNEL_CODE=cmdNC.getCHANNEL_CODE();
            this.COMMENTAIRE=cmdNC.getCOMMENTAIRE();
            this.LIEU_LIVRAISON=cmdNC.getLIEU_LIVRAISON();
            this.MONTANT_BRUT=cmdNC.getMONTANT_BRUT();
            this.REMISE=cmdNC.getREMISE();
            this.MONTANT_NET=cmdNC.getMONTANT_NET();
            this.LITTRAGE_COMMANDE=cmdNC.getLITTRAGE_COMMANDE();
            this.TONNAGE_COMMANDE=cmdNC.getTONNAGE_COMMANDE();
            this.KG_COMMANDE=cmdNC.getKG_COMMANDE();
            this.VALEUR_COMMANDE=cmdNC.getVALEUR_COMMANDE();
            this.PAIEMENT_CODE=cmdNC.getPAIEMENT_CODE();
            this.NB_LIGNE=cmdNC.getNB_LIGNE();
            this.STATUT_CODE=cmdNC.getSTATUT_CODE();
            this.SOURCE=cmdNC.getSOURCE();
            this.VERSION=cmdNC.getVERSION();
            this.GPS_LATITUDE=cmdNC.getGPS_LATITUDE();
            this.GPS_LONGITUDE=cmdNC.getGPS_LONGITUDE();
            this.DISTANCE=cmdNC.getDISTANCE();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Commande( CommandeAEncaisser cmdAE) {
        try {
            this.COMMANDE_CODE=cmdAE.getCOMMANDE_CODE();
            this.FACTURE_CODE=cmdAE.getFACTURE_CODE();
            this.FACTURECLIENT_CODE=cmdAE.getFACTURECLIENT_CODE();
            this.DATE_COMMANDE=cmdAE.getDATE_COMMANDE();
            this.DATE_LIVRAISON=cmdAE.getDATE_LIVRAISON();
            this.DATE_CREATION=cmdAE.getDATE_CREATION();
            this.PERIODE_CODE=cmdAE.getPERIODE_CODE();
            this.COMMANDETYPE_CODE=cmdAE.getCOMMANDETYPE_CODE();
            this.COMMANDESTATUT_CODE=cmdAE.getCOMMANDESTATUT_CODE();
            this.DISTRIBUTEUR_CODE=cmdAE.getDISTRIBUTEUR_CODE();
            this.VENDEUR_CODE=cmdAE.getVENDEUR_CODE();
            this.CLIENT_CODE=cmdAE.getCLIENT_CODE();
            this.CREATEUR_CODE=cmdAE.getCREATEUR_CODE();
            this.LIVREUR_CODE=cmdAE.getLIVREUR_CODE();
            this.REGION_CODE=cmdAE.getREGION_CODE();
            this.ZONE_CODE=cmdAE.getZONE_CODE();
            this.SECTEUR_CODE=cmdAE.getSECTEUR_CODE();
            this.SOUSSECTEUR_CODE=cmdAE.getSOUSSECTEUR_CODE();
            this.TOURNEE_CODE=cmdAE.getTOURNEE_CODE();
            this.VISITE_CODE=cmdAE.getVISITE_CODE();
            this.STOCKDEPART_CODE=cmdAE.getSTOCKDEPART_CODE();
            this.STOCKDESTINATION_CODE=cmdAE.getSTOCKDESTINATION_CODE();
            this.DESTINATION_CODE=cmdAE.getDESTINATION_CODE();
            this.CIRCUIT_CODE=cmdAE.getCIRCUIT_CODE();
            this.CHANNEL_CODE=cmdAE.getCHANNEL_CODE();
            this.COMMENTAIRE=cmdAE.getCOMMENTAIRE();
            this.LIEU_LIVRAISON=cmdAE.getLIEU_LIVRAISON();
            this.MONTANT_BRUT=cmdAE.getMONTANT_BRUT();
            this.REMISE=cmdAE.getREMISE();
            this.MONTANT_NET=cmdAE.getMONTANT_NET();
            this.LITTRAGE_COMMANDE=cmdAE.getLITTRAGE_COMMANDE();
            this.TONNAGE_COMMANDE=cmdAE.getTONNAGE_COMMANDE();
            this.KG_COMMANDE=cmdAE.getKG_COMMANDE();
            this.VALEUR_COMMANDE=cmdAE.getVALEUR_COMMANDE();
            this.PAIEMENT_CODE=cmdAE.getPAIEMENT_CODE();
            this.NB_LIGNE=cmdAE.getNB_LIGNE();
            this.STATUT_CODE=cmdAE.getSTATUT_CODE();
            this.SOURCE=cmdAE.getSOURCE();
            this.VERSION=cmdAE.getVERSION();
            this.GPS_LATITUDE=cmdAE.getGPS_LATITUDE();
            this.GPS_LONGITUDE=cmdAE.getGPS_LONGITUDE();
            this.DISTANCE=cmdAE.getDISTANCE();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Commande(String COMMANDE_CODE,String visite_code, Context context, ArrayList<CommandeLigne> list, String commande_typecode) {

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.COMMANDE_CODE = COMMANDE_CODE;
            this.FACTURE_CODE = COMMANDE_CODE;
            this.FACTURECLIENT_CODE = COMMANDE_CODE;
            this.DATE_COMMANDE = df.format(Calendar.getInstance().getTime());
            this.DATE_LIVRAISON = DATE_COMMANDE;
            this.DATE_CREATION = DATE_COMMANDE;
            this.PERIODE_CODE = "PR0003";
            this.COMMANDETYPE_CODE = commande_typecode;
            this.COMMANDESTATUT_CODE = "5";
            this.DISTRIBUTEUR_CODE = ClientActivity.clientCourant.getDISTRIBUTEUR_CODE();
            this.VENDEUR_CODE = user.getString("UTILISATEUR_CODE");
            this.CLIENT_CODE = ClientActivity.clientCourant.getCLIENT_CODE();
            this.CREATEUR_CODE = user.getString("UTILISATEUR_CODE");;
            this.LIVREUR_CODE = user.getString("UTILISATEUR_CODE");;
            this.REGION_CODE = ClientActivity.clientCourant.getREGION_CODE();
            this.ZONE_CODE = ClientActivity.clientCourant.getZONE_CODE();
            this.SECTEUR_CODE = ClientActivity.clientCourant.getSECTEUR_CODE();
            this.SOUSSECTEUR_CODE = ClientActivity.clientCourant.getSOUSSECTEUR_CODE();
            this.TOURNEE_CODE = ClientActivity.clientCourant.getTOURNEE_CODE();
            this.VISITE_CODE = visite_code;
            this.STOCKDEPART_CODE = user.getString("STOCK_CODE");;
            this.STOCKDESTINATION_CODE = ClientActivity.clientCourant.getSTOCK_CODE();
            this.DESTINATION_CODE = "default";
            this.CIRCUIT_CODE = ClientActivity.clientCourant.getCIRCUIT_CODE();
            this.CHANNEL_CODE = "CH0001";
            this.COMMENTAIRE = "to_insert";
            this.REMISE = 0;
            this.PAIEMENT_CODE = 0;
            this.NB_LIGNE = 0;

            for (int i = 0; i < list.size(); i++) {
                this.MONTANT_BRUT += getNumberRounded(list.get(i).getMONTANT_BRUT());
                this.MONTANT_NET += getNumberRounded(list.get(i).getMONTANT_NET());
                this.LITTRAGE_COMMANDE += list.get(i).getLITTRAGE_COMMANDEE();
                this.TONNAGE_COMMANDE += list.get(i).getTONNAGE_COMMANDEE();
                this.KG_COMMANDE += list.get(i).getKG_COMMANDEE();
            }
            this.VALEUR_COMMANDE = getNumberRounded(MONTANT_NET);
            this.SOURCE=COMMANDE_CODE;
            this.GPS_LATITUDE = String.valueOf(Gpstrackerservice.latitude);
            this.GPS_LONGITUDE = String.valueOf(Gpstrackerservice.longitude);


        } catch (Exception e) {

        }
    }

    public Commande(String COMMANDE_CODE,String visite_code, Context context, String commande_typecode, String commande_statutcode, Client client,double latitude,double longitude) {

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);
        int distance = 0;
        try{
            distance = getDistance(client,latitude,longitude);
        }catch(ArithmeticException a){
        }


        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.COMMANDE_CODE = COMMANDE_CODE;
            this.FACTURE_CODE = COMMANDE_CODE;
            this.FACTURECLIENT_CODE = COMMANDE_CODE;
            this.DATE_COMMANDE = df.format(Calendar.getInstance().getTime());
            this.DATE_LIVRAISON = DATE_COMMANDE;
            this.DATE_CREATION = DATE_COMMANDE;
            this.PERIODE_CODE = "PR0003";
            this.COMMANDETYPE_CODE = commande_typecode;
            this.COMMANDESTATUT_CODE = commande_statutcode;
            this.DISTRIBUTEUR_CODE = client.getDISTRIBUTEUR_CODE();
            this.VENDEUR_CODE = user.getString("UTILISATEUR_CODE");
            this.CLIENT_CODE = client.getCLIENT_CODE();
            this.CREATEUR_CODE = user.getString("UTILISATEUR_CODE");
            this.LIVREUR_CODE = user.getString("UTILISATEUR_CODE");
            this.REGION_CODE = client.getREGION_CODE();
            this.ZONE_CODE = client.getZONE_CODE();
            this.SECTEUR_CODE = client.getSECTEUR_CODE();
            this.SOUSSECTEUR_CODE = client.getSOUSSECTEUR_CODE();
            this.TOURNEE_CODE = client.getTOURNEE_CODE();
            this.VISITE_CODE = visite_code;
            this.STOCKDEPART_CODE = user.getString("STOCK_CODE");
            this.STOCKDESTINATION_CODE = client.getSTOCK_CODE();
            this.DESTINATION_CODE = "default";
            this.CIRCUIT_CODE = client.getCIRCUIT_CODE();
            this.CHANNEL_CODE = "CH0001";
            this.COMMENTAIRE = "to_insert";
            this.REMISE = 0;
            this.PAIEMENT_CODE = 0;
            this.NB_LIGNE = 0;
            this.STATUT_CODE="NON_CLOTURE";
            this.VERSION="non verifiee";
            this.SOURCE=COMMANDE_CODE;
            this.GPS_LATITUDE = String.valueOf(latitude);
            this.GPS_LONGITUDE = String.valueOf(longitude);
            this.DISTANCE = distance;

        } catch (Exception e) {

        }
    }

    public Commande (Context context , Commande commande, Client clientCourant, String visite_code){

        SimpleDateFormat df_code = new SimpleDateFormat("yyMMddHHmmss");
        String date_commande=df_code.format(new java.util.Date());

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);

        DateFormat df_today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String COMMANDE_CODE = clientCourant.getDISTRIBUTEUR_CODE()+commande.getVENDEUR_CODE()+date_commande;

        this.COMMANDE_CODE=COMMANDE_CODE;
        this.FACTURE_CODE=COMMANDE_CODE;
        this.FACTURECLIENT_CODE=COMMANDE_CODE;
        this.DATE_COMMANDE=commande.getDATE_COMMANDE();
        this.DATE_LIVRAISON=commande.getDATE_LIVRAISON();
        this.DATE_CREATION=df_today.format(Calendar.getInstance().getTime());
        this.PERIODE_CODE=commande.getPERIODE_CODE();
        this.COMMANDETYPE_CODE="2";
        this.COMMANDESTATUT_CODE="3";
        this.DISTRIBUTEUR_CODE=commande.getDISTRIBUTEUR_CODE();
        this.VENDEUR_CODE=commande.getVENDEUR_CODE();
        this.CLIENT_CODE=commande.getCLIENT_CODE();
        this.CREATEUR_CODE=commande.getCREATEUR_CODE();
        this.LIVREUR_CODE=commande.getLIVREUR_CODE();
        this.REGION_CODE=commande.getREGION_CODE();
        this.ZONE_CODE=commande.getZONE_CODE();
        this.SECTEUR_CODE=commande.getSECTEUR_CODE();
        this.SOUSSECTEUR_CODE=commande.getSOUSSECTEUR_CODE();
        this.TOURNEE_CODE=commande.getTOURNEE_CODE();
        this.VISITE_CODE=visite_code;
        this.STOCKDEPART_CODE=commande.getSTOCKDEPART_CODE();
        this.STOCKDESTINATION_CODE=commande.getSTOCKDESTINATION_CODE();
        this.DESTINATION_CODE=commande.getDESTINATION_CODE();
        this.CIRCUIT_CODE=commande.getCIRCUIT_CODE();
        this.CHANNEL_CODE=commande.getCHANNEL_CODE();
        this.COMMENTAIRE = "to_insert";
        this.LIEU_LIVRAISON=commande.getLIEU_LIVRAISON();
        this.MONTANT_BRUT=-commande.getMONTANT_BRUT();
        this.REMISE=commande.getREMISE();
        this.MONTANT_NET=-commande.getMONTANT_NET();
        this.LITTRAGE_COMMANDE=-commande.getLITTRAGE_COMMANDE();
        this.TONNAGE_COMMANDE=-commande.getTONNAGE_COMMANDE();
        this.KG_COMMANDE=-commande.getKG_COMMANDE();
        this.VALEUR_COMMANDE=-commande.getVALEUR_COMMANDE();
        this.PAIEMENT_CODE=commande.getPAIEMENT_CODE();
        this.NB_LIGNE=commande.getNB_LIGNE();
        this.STATUT_CODE=commande.getSTATUT_CODE();
        this.SOURCE=commande.getCOMMANDE_CODE();
        this.VERSION=commande.getVERSION();
        this.GPS_LATITUDE=commande.getGPS_LATITUDE();
        this.GPS_LONGITUDE=commande.getGPS_LONGITUDE();
        this.DISTANCE=commande.getDISTANCE();

    }

    public Commande (Context context , Commande commande, String visite_code){

        SimpleDateFormat df_code = new SimpleDateFormat("yyMMddHHmmss");
        String date_commande=df_code.format(new java.util.Date());

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);

        DateFormat df_today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        this.COMMANDE_CODE=commande.getCOMMANDE_CODE();
        this.FACTURE_CODE=commande.getCOMMANDE_CODE();
        this.FACTURECLIENT_CODE=COMMANDE_CODE;
        this.DATE_COMMANDE=commande.getDATE_COMMANDE();
        this.DATE_LIVRAISON=commande.getDATE_LIVRAISON();
        this.DATE_CREATION=df_today.format(Calendar.getInstance().getTime());
        this.PERIODE_CODE=commande.getPERIODE_CODE();
        this.COMMANDETYPE_CODE="2";
        this.COMMANDESTATUT_CODE="3";
        this.DISTRIBUTEUR_CODE=commande.getDISTRIBUTEUR_CODE();
        this.VENDEUR_CODE=commande.getVENDEUR_CODE();
        this.CLIENT_CODE=commande.getCLIENT_CODE();
        this.CREATEUR_CODE=commande.getCREATEUR_CODE();
        this.LIVREUR_CODE=commande.getLIVREUR_CODE();
        this.REGION_CODE=commande.getREGION_CODE();
        this.ZONE_CODE=commande.getZONE_CODE();
        this.SECTEUR_CODE=commande.getSECTEUR_CODE();
        this.SOUSSECTEUR_CODE=commande.getSOUSSECTEUR_CODE();
        this.TOURNEE_CODE=commande.getTOURNEE_CODE();
        this.VISITE_CODE=visite_code;
        this.STOCKDEPART_CODE=commande.getSTOCKDEPART_CODE();
        this.STOCKDESTINATION_CODE=commande.getSTOCKDESTINATION_CODE();
        this.DESTINATION_CODE=commande.getDESTINATION_CODE();
        this.CIRCUIT_CODE=commande.getCIRCUIT_CODE();
        this.CHANNEL_CODE=commande.getCHANNEL_CODE();
        this.COMMENTAIRE = "to_insert";
        this.LIEU_LIVRAISON=commande.getLIEU_LIVRAISON();
        this.MONTANT_BRUT=-commande.getMONTANT_BRUT();
        this.REMISE=commande.getREMISE();
        this.MONTANT_NET=-commande.getMONTANT_NET();
        this.LITTRAGE_COMMANDE=-commande.getLITTRAGE_COMMANDE();
        this.TONNAGE_COMMANDE=-commande.getTONNAGE_COMMANDE();
        this.KG_COMMANDE=-commande.getKG_COMMANDE();
        this.VALEUR_COMMANDE=-commande.getVALEUR_COMMANDE();
        this.PAIEMENT_CODE=commande.getPAIEMENT_CODE();
        this.NB_LIGNE=commande.getNB_LIGNE();
        this.STATUT_CODE=commande.getSTATUT_CODE();
        this.SOURCE=commande.getCOMMANDE_CODE();
        this.VERSION=commande.getVERSION();
        this.GPS_LATITUDE=commande.getGPS_LATITUDE();
        this.GPS_LONGITUDE=commande.getGPS_LONGITUDE();
        this.DISTANCE=commande.getDISTANCE();

    }

    public Commande (Commande commande){

        this.COMMANDE_CODE=commande.getCOMMANDE_CODE();
        this.FACTURE_CODE=commande.getFACTURE_CODE();
        this.FACTURECLIENT_CODE=commande.getFACTURECLIENT_CODE();
        this.DATE_COMMANDE=commande.getDATE_COMMANDE();
        this.DATE_LIVRAISON=commande.getDATE_LIVRAISON();
        this.DATE_CREATION=commande.getDATE_CREATION();
        this.PERIODE_CODE=commande.getPERIODE_CODE();
        this.COMMANDETYPE_CODE=commande.getCOMMANDETYPE_CODE();
        this.COMMANDESTATUT_CODE=commande.getCOMMANDESTATUT_CODE();
        this.DISTRIBUTEUR_CODE=commande.getDISTRIBUTEUR_CODE();
        this.VENDEUR_CODE=commande.getVENDEUR_CODE();
        this.CLIENT_CODE=commande.getCLIENT_CODE();
        this.CREATEUR_CODE=commande.getCREATEUR_CODE();
        this.LIVREUR_CODE=commande.getLIVREUR_CODE();
        this.REGION_CODE=commande.getREGION_CODE();
        this.ZONE_CODE=commande.getZONE_CODE();
        this.SECTEUR_CODE=commande.getSECTEUR_CODE();
        this.SOUSSECTEUR_CODE=commande.getSOUSSECTEUR_CODE();
        this.TOURNEE_CODE=commande.getTOURNEE_CODE();
        this.VISITE_CODE=commande.getVISITE_CODE();
        this.STOCKDEPART_CODE=commande.getSTOCKDEPART_CODE();
        this.STOCKDESTINATION_CODE=commande.getSTOCKDESTINATION_CODE();
        this.DESTINATION_CODE=commande.getDESTINATION_CODE();
        this.CIRCUIT_CODE=commande.getCIRCUIT_CODE();
        this.CHANNEL_CODE=commande.getCHANNEL_CODE();
        this.COMMENTAIRE=commande.getCOMMENTAIRE();
        this.LIEU_LIVRAISON=commande.getLIEU_LIVRAISON();
        this.MONTANT_BRUT=-commande.getMONTANT_BRUT();
        this.REMISE=-commande.getREMISE();
        this.MONTANT_NET=-commande.getMONTANT_NET();
        this.LITTRAGE_COMMANDE=-commande.getLITTRAGE_COMMANDE();
        this.TONNAGE_COMMANDE=-commande.getTONNAGE_COMMANDE();
        this.KG_COMMANDE=-commande.getKG_COMMANDE();
        this.VALEUR_COMMANDE=-commande.getVALEUR_COMMANDE();
        this.PAIEMENT_CODE=commande.getPAIEMENT_CODE();
        this.NB_LIGNE=commande.getNB_LIGNE();
        this.STATUT_CODE=commande.getSTATUT_CODE();
        this.SOURCE=commande.getSOURCE();
        this.VERSION=commande.getVERSION();
        this.GPS_LATITUDE=commande.getGPS_LATITUDE();
        this.GPS_LONGITUDE=commande.getGPS_LONGITUDE();
        this.DISTANCE=commande.getDISTANCE();

    }

    protected Commande(Parcel in) {
        COMMANDE_CODE = in.readString();
        FACTURE_CODE = in.readString();
        FACTURECLIENT_CODE = in.readString();
        DATE_COMMANDE = in.readString();
        DATE_LIVRAISON = in.readString();
        DATE_CREATION = in.readString();
        PERIODE_CODE = in.readString();
        COMMANDETYPE_CODE = in.readString();
        COMMANDESTATUT_CODE = in.readString();
        DISTRIBUTEUR_CODE = in.readString();
        VENDEUR_CODE = in.readString();
        CLIENT_CODE = in.readString();
        CREATEUR_CODE = in.readString();
        LIVREUR_CODE = in.readString();
        REGION_CODE = in.readString();
        ZONE_CODE = in.readString();
        SECTEUR_CODE = in.readString();
        SOUSSECTEUR_CODE = in.readString();
        TOURNEE_CODE = in.readString();
        VISITE_CODE = in.readString();
        STOCKDEPART_CODE = in.readString();
        STOCKDESTINATION_CODE = in.readString();
        DESTINATION_CODE = in.readString();
        TS = in.readString();
        CIRCUIT_CODE = in.readString();
        CHANNEL_CODE = in.readString();
        COMMENTAIRE = in.readString();
        LIEU_LIVRAISON = in.readString();
        MONTANT_BRUT = in.readDouble();
        REMISE = in.readDouble();
        MONTANT_NET = in.readDouble();
        VALEUR_COMMANDE = in.readDouble();
        LITTRAGE_COMMANDE = in.readDouble();
        TONNAGE_COMMANDE = in.readDouble();
        KG_COMMANDE = in.readDouble();
        PAIEMENT_CODE = in.readInt();
        NB_LIGNE = in.readInt();
        STATUT_CODE = in.readString();
        SOURCE = in.readString();
        VERSION = in.readString();
        GPS_LATITUDE = in.readString();
        GPS_LONGITUDE = in.readString();
        DISTANCE = in.readInt();
    }

    public static final Creator<Commande> CREATOR = new Creator<Commande>() {
        @Override
        public Commande createFromParcel(Parcel in) {
            return new Commande(in);
        }

        @Override
        public Commande[] newArray(int size) {
            return new Commande[size];
        }
    };

    public String getCOMMANDE_CODE() {
        return COMMANDE_CODE;
    }

    public void setCOMMANDE_CODE(String COMMANDE_CODE) {
        this.COMMANDE_CODE = COMMANDE_CODE;
    }

    public String getFACTURE_CODE() {
        return FACTURE_CODE;
    }

    public void setFACTURE_CODE(String FACTURE_CODE) {
        this.FACTURE_CODE = FACTURE_CODE;
    }

    public String getFACTURECLIENT_CODE() {
        return FACTURECLIENT_CODE;
    }

    public void setFACTURECLIENT_CODE(String FACTURECLIENT_CODE) {
        this.FACTURECLIENT_CODE = FACTURECLIENT_CODE;
    }

    public String getDATE_COMMANDE() {
        return DATE_COMMANDE;
    }

    public void setDATE_COMMANDE(String DATE_COMMANDE) {
        this.DATE_COMMANDE = DATE_COMMANDE;
    }

    public String getDATE_LIVRAISON() {
        return DATE_LIVRAISON;
    }

    public void setDATE_LIVRAISON(String DATE_LIVRAISON) {
        this.DATE_LIVRAISON = DATE_LIVRAISON;
    }

    public String getDATE_CREATION() {
        return DATE_CREATION;
    }

    public void setDATE_CREATION(String DATE_CREATION) {
        this.DATE_CREATION = DATE_CREATION;
    }

    public String getPERIODE_CODE() {
        return PERIODE_CODE;
    }

    public void setPERIODE_CODE(String PERIODE_CODE) {
        this.PERIODE_CODE = PERIODE_CODE;
    }

    public String getCOMMANDETYPE_CODE() {
        return COMMANDETYPE_CODE;
    }

    public void setCOMMANDETYPE_CODE(String COMMANDETYPE_CODE) {
        this.COMMANDETYPE_CODE = COMMANDETYPE_CODE;
    }

    public String getCOMMANDESTATUT_CODE() {
        return COMMANDESTATUT_CODE;
    }

    public void setCOMMANDESTATUT_CODE(String COMMANDESTATUT_CODE) {
        this.COMMANDESTATUT_CODE = COMMANDESTATUT_CODE;
    }

    public String getDISTRIBUTEUR_CODE() {
        return DISTRIBUTEUR_CODE;
    }

    public void setDISTRIBUTEUR_CODE(String DISTRIBUTEUR_CODE) {
        this.DISTRIBUTEUR_CODE = DISTRIBUTEUR_CODE;
    }

    public String getVENDEUR_CODE() {
        return VENDEUR_CODE;
    }

    public void setVENDEUR_CODE(String VENDEUR_CODE) {
        this.VENDEUR_CODE = VENDEUR_CODE;
    }

    public String getCLIENT_CODE() {
        return CLIENT_CODE;
    }

    public void setCLIENT_CODE(String CLIENT_CODE) {
        this.CLIENT_CODE = CLIENT_CODE;
    }

    public String getCREATEUR_CODE() {
        return CREATEUR_CODE;
    }

    public void setCREATEUR_CODE(String CREATEUR_CODE) {
        this.CREATEUR_CODE = CREATEUR_CODE;
    }

    public String getLIVREUR_CODE() {
        return LIVREUR_CODE;
    }

    public void setLIVREUR_CODE(String LIVREUR_CODE) {
        this.LIVREUR_CODE = LIVREUR_CODE;
    }

    public String getREGION_CODE() {
        return REGION_CODE;
    }

    public void setREGION_CODE(String REGION_CODE) {
        this.REGION_CODE = REGION_CODE;
    }

    public String getZONE_CODE() {
        return ZONE_CODE;
    }

    public void setZONE_CODE(String ZONE_CODE) {
        this.ZONE_CODE = ZONE_CODE;
    }

    public String getSECTEUR_CODE() {
        return SECTEUR_CODE;
    }

    public void setSECTEUR_CODE(String SECTEUR_CODE) {
        this.SECTEUR_CODE = SECTEUR_CODE;
    }

    public String getSOUSSECTEUR_CODE() {
        return SOUSSECTEUR_CODE;
    }

    public void setSOUSSECTEUR_CODE(String SOUSSECTEUR_CODE) {
        this.SOUSSECTEUR_CODE = SOUSSECTEUR_CODE;
    }

    public String getTOURNEE_CODE() {
        return TOURNEE_CODE;
    }

    public void setTOURNEE_CODE(String TOURNEE_CODE) {
        this.TOURNEE_CODE = TOURNEE_CODE;
    }

    public String getVISITE_CODE() {
        return VISITE_CODE;
    }

    public void setVISITE_CODE(String VISITE_CODE) {
        this.VISITE_CODE = VISITE_CODE;
    }

    public String getSTOCKDEPART_CODE() {
        return STOCKDEPART_CODE;
    }

    public void setSTOCKDEPART_CODE(String STOCKDEPART_CODE) {
        this.STOCKDEPART_CODE = STOCKDEPART_CODE;
    }

    public String getSTOCKDESTINATION_CODE() {
        return STOCKDESTINATION_CODE;
    }

    public void setSTOCKDESTINATION_CODE(String STOCKDESTINATION_CODE) {
        this.STOCKDESTINATION_CODE = STOCKDESTINATION_CODE;
    }

    public String getDESTINATION_CODE() {
        return DESTINATION_CODE;
    }

    public void setDESTINATION_CODE(String DESTINATION_CODE) {
        this.DESTINATION_CODE = DESTINATION_CODE;
    }

    public String getTS() {
        return TS;
    }

    public void setTS(String TS) {
        this.TS = TS;
    }

    public String getCIRCUIT_CODE() {
        return CIRCUIT_CODE;
    }

    public void setCIRCUIT_CODE(String CIRCUIT_CODE) {
        this.CIRCUIT_CODE = CIRCUIT_CODE;
    }

    public String getCHANNEL_CODE() {
        return CHANNEL_CODE;
    }

    public void setCHANNEL_CODE(String CHANNEL_CODE) {
        this.CHANNEL_CODE = CHANNEL_CODE;
    }

    public String getCOMMENTAIRE() {
        return COMMENTAIRE;
    }

    public void setCOMMENTAIRE(String COMMENTAIRE) {
        this.COMMENTAIRE = COMMENTAIRE;
    }

    public double getMONTANT_BRUT() {
        return MONTANT_BRUT;
    }

    public void setMONTANT_BRUT(double MONTANT_BRUT) {
        this.MONTANT_BRUT = MONTANT_BRUT;
    }

    public double getREMISE() {
        return REMISE;
    }

    public void setREMISE(double REMISE) {
        this.REMISE = REMISE;
    }

    public double getMONTANT_NET() {
        return MONTANT_NET;
    }

    public void setMONTANT_NET(double MONTANT_NET) {
        this.MONTANT_NET = MONTANT_NET;
    }

    public double getVALEUR_COMMANDE() {
        return VALEUR_COMMANDE;
    }

    public void setVALEUR_COMMANDE(double VALEUR_COMMANDE) {
        this.VALEUR_COMMANDE = VALEUR_COMMANDE;
    }

    public double getLITTRAGE_COMMANDE() {
        return LITTRAGE_COMMANDE;
    }

    public void setLITTRAGE_COMMANDE(double LITTRAGE_COMMANDE) {
        this.LITTRAGE_COMMANDE = LITTRAGE_COMMANDE;
    }

    public double getTONNAGE_COMMANDE() {
        return TONNAGE_COMMANDE;
    }

    public void setTONNAGE_COMMANDE(double TONNAGE_COMMANDE) {
        this.TONNAGE_COMMANDE = TONNAGE_COMMANDE;
    }

    public double getKG_COMMANDE() {
        return KG_COMMANDE;
    }

    public void setKG_COMMANDE(double KG_COMMANDE) {
        this.KG_COMMANDE = KG_COMMANDE;
    }

    public int getPAIEMENT_CODE() {
        return PAIEMENT_CODE;
    }

    public void setPAIEMENT_CODE(int PAIEMENT_CODE) {
        this.PAIEMENT_CODE = PAIEMENT_CODE;
    }

    public String getSTATUT_CODE() {
        return STATUT_CODE;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setSTATUT_CODE(String STATUT_CODE) {
        this.STATUT_CODE = STATUT_CODE;
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

    public String getLIEU_LIVRAISON() {
        return LIEU_LIVRAISON;
    }

    public void setLIEU_LIVRAISON(String LIEU_LIVRAISON) {
        this.LIEU_LIVRAISON = LIEU_LIVRAISON;
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
        return "Commande{" +
                "COMMANDE_CODE='" + COMMANDE_CODE + '\'' +
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
                ", CIRCUIT_CODE='" + CIRCUIT_CODE + '\'' +
                ", CHANNEL_CODE='" + CHANNEL_CODE + '\'' +
                ", COMMENTAIRE='" + COMMENTAIRE + '\'' +
                ", LIEU_LIVRAISON='" + LIEU_LIVRAISON + '\'' +
                ", MONTANT_BRUT=" + MONTANT_BRUT +
                ", REMISE=" + REMISE +
                ", MONTANT_NET=" + MONTANT_NET +
                ", VALEUR_COMMANDE=" + VALEUR_COMMANDE +
                ", LITTRAGE_COMMANDE=" + LITTRAGE_COMMANDE +
                ", TONNAGE_COMMANDE=" + TONNAGE_COMMANDE +
                ", KG_COMMANDE=" + KG_COMMANDE +
                ", PAIEMENT_CODE=" + PAIEMENT_CODE +
                ", NB_LIGNE=" + NB_LIGNE +
                ", STATUT_CODE='" + STATUT_CODE + '\'' +
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(COMMANDE_CODE);
        dest.writeString(FACTURE_CODE);
        dest.writeString(FACTURECLIENT_CODE);
        dest.writeString(DATE_COMMANDE);
        dest.writeString(DATE_LIVRAISON);
        dest.writeString(DATE_CREATION);
        dest.writeString(PERIODE_CODE);
        dest.writeString(COMMANDETYPE_CODE);
        dest.writeString(COMMANDESTATUT_CODE);
        dest.writeString(DISTRIBUTEUR_CODE);
        dest.writeString(VENDEUR_CODE);
        dest.writeString(CLIENT_CODE);
        dest.writeString(CREATEUR_CODE);
        dest.writeString(LIVREUR_CODE);
        dest.writeString(REGION_CODE);
        dest.writeString(ZONE_CODE);
        dest.writeString(SECTEUR_CODE);
        dest.writeString(SOUSSECTEUR_CODE);
        dest.writeString(TOURNEE_CODE);
        dest.writeString(VISITE_CODE);
        dest.writeString(STOCKDEPART_CODE);
        dest.writeString(STOCKDESTINATION_CODE);
        dest.writeString(DESTINATION_CODE);
        dest.writeString(TS);
        dest.writeString(CIRCUIT_CODE);
        dest.writeString(CHANNEL_CODE);
        dest.writeString(COMMENTAIRE);
        dest.writeString(LIEU_LIVRAISON);
        dest.writeDouble(MONTANT_BRUT);
        dest.writeDouble(REMISE);
        dest.writeDouble(MONTANT_NET);
        dest.writeDouble(VALEUR_COMMANDE);
        dest.writeDouble(LITTRAGE_COMMANDE);
        dest.writeDouble(TONNAGE_COMMANDE);
        dest.writeDouble(KG_COMMANDE);
        dest.writeInt(PAIEMENT_CODE);
        dest.writeInt(NB_LIGNE);
        dest.writeString(STATUT_CODE);
        dest.writeString(SOURCE);
        dest.writeString(VERSION);
        dest.writeString(GPS_LATITUDE);
        dest.writeString(GPS_LONGITUDE);
        dest.writeInt(DISTANCE);
    }


    public int getDistance(Client client,double latitude, double longitude){


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
