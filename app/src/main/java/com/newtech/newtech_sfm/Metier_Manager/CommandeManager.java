package com.newtech.newtech_sfm.Metier_Manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Activity.ClientActivity;
import com.newtech.newtech_sfm.Activity.SyncV2Activity;
import com.newtech.newtech_sfm.Configuration.AppConfig;
import com.newtech.newtech_sfm.Configuration.AppController;
import com.newtech.newtech_sfm.Configuration.SQLiteHandler;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.CommandeALivrer;
import com.newtech.newtech_sfm.Metier.CommandeGratuite;
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier.CommandeLigneALivrer;
import com.newtech.newtech_sfm.Metier.CommandeNonCloturee;
import com.newtech.newtech_sfm.Metier.CommandeNonClotureeLigne;
import com.newtech.newtech_sfm.Metier.CommandePromotion;
import com.newtech.newtech_sfm.Metier.Livraison;
import com.newtech.newtech_sfm.Metier.LivraisonLigne;
import com.newtech.newtech_sfm.Metier.LogSync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stagiaireit2 on 02/08/2016.
 */
public class CommandeManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_COMMANDE = "commande";

    Client clientCourant=ClientActivity.clientCourant;

    private static final String
            KEY_COMMANDE_CODE="COMMANDE_CODE",
            KEY_FACTURE_CODE="FACTURE_CODE",
            KEY_FACTURECLIENT_CODE="FACTURECLIENT_CODE",
            KEY_DATE_COMMANDE="DATE_COMMANDE",
            KEY_DATE_LIVRAISON="DATE_LIVRAISON",
            KEY_DATE_CREATION="DATE_CREATION",
            KEY_PERIODE_CODE="PERIODE_CODE",
            KEY_COMMANDETYPE_CODE="COMMANDETYPE_CODE",
            KEY_COMMANDESTATUT_CODE="COMMANDESTATUT_CODE",
            KEY_DISTRIBUTEUR_CODE="DISTRIBUTEUR_CODE",
            KEY_VENDEUR_CODE="VENDEUR_CODE",
            KEY_CLIENT_CODE="CLIENT_CODE",
            KEY_CREATEUR_CODE="CREATEUR_CODE",
            KEY_LIVREUR_CODE="LIVREUR_CODE",
            KEY_REGION_CODE="REGION_CODE",
            KEY_ZONE_CODE="ZONE_CODE",
            KEY_SECTEUR_CODE="SECTEUR_CODE",
            KEY_SOUSSECTEUR_CODE="SOUSSECTEUR_CODE",
            KEY_TOURNEE_CODE="TOURNEE_CODE",
            KEY_VISITE_CODE="VISITE_CODE",
            KEY_STOCKDEPART_CODE="STOCKDEPART_CODE",
            KEY_STOCKDESTINATION_CODE="STOCKDESTINATION_CODE",
            KEY_DESTINATION_CODE="DESTINATION_CODE",
            KEY_TS="TS",
            KEY_MONTANT_BRUT="MONTANT_BRUT",
            KEY_REMISE="REMISE",
            KEY_MONTANT_NET="MONTANT_NET",
            KEY_VALEUR_COMMANDE="VALEUR_COMMANDE",
            KEY_LITTRAGE_COMMANDE="LITTRAGE_COMMANDE",
            KEY_TONNAGE_COMMANDE="TONNAGE_COMMANDE",
            KEY_KG_COMMANDE="KG_COMMANDE",
            KEY_COMMENTAIRE="COMMENTAIRE",
            KEY_LIEU_LIVRAISON="LIEU_LIVRAISON",
            KEY_PAIEMENT_CODE="PAIEMENT_CODE",
            KEY_NB_LIGNE="NB_LIGNE",
            KEY_CIRCUIT_CODE="CIRCUIT_CODE",
            KEY_CHANNEL_CODE="CHANNEL_CODE",
            KEY_STATUT_CODE="STATUT_CODE",
            KEY_SOURCE="SOURCE",
            KEY_VERSION="VERSION",
            KEY_GPS_LATITUDE="GPS_LATITUDE",
            KEY_GPS_LONGITUDE="GPS_LONGITUDE",
            KEY_DISTANCE="DISTANCE";

    public static String CREATE_COMMANDE_TABLE = "CREATE TABLE " + TABLE_COMMANDE + "("
            +KEY_COMMANDE_CODE + " TEXT PRIMARY KEY ,"
            +KEY_FACTURE_CODE+ " TEXT,"
            +KEY_FACTURECLIENT_CODE    + " TEXT,"
            +KEY_DATE_COMMANDE + " TEXT,"
            +KEY_DATE_LIVRAISON  + " TEXT,"
            +KEY_DATE_CREATION + " TEXT,"
            +KEY_PERIODE_CODE+ " TEXT,"
            +KEY_COMMANDETYPE_CODE + " TEXT,"
            +KEY_COMMANDESTATUT_CODE + " TEXT,"
            +KEY_DISTRIBUTEUR_CODE + " TEXT,"
            +KEY_VENDEUR_CODE+ " TEXT,"
            +KEY_CLIENT_CODE+ " TEXT,"
            +KEY_CREATEUR_CODE + " TEXT,"
            +KEY_LIVREUR_CODE+ " TEXT,"
            +KEY_REGION_CODE+ " TEXT,"
            +KEY_ZONE_CODE+ " TEXT,"
            +KEY_SECTEUR_CODE+ " TEXT,"
            +KEY_SOUSSECTEUR_CODE+ " TEXT,"
            +KEY_TOURNEE_CODE+ " TEXT,"
            +KEY_VISITE_CODE+ " TEXT,"
            +KEY_STOCKDEPART_CODE+ " TEXT,"
            +KEY_STOCKDESTINATION_CODE+ " TEXT,"
            +KEY_DESTINATION_CODE+ " TEXT,"
            +KEY_TS+ " TEXT,"
            +KEY_MONTANT_BRUT+ " NUMERIC ,"
            +KEY_REMISE + " NUMERIC ,"
            +KEY_MONTANT_NET+ " NUMERIC,"
            +KEY_VALEUR_COMMANDE+ " NUMERIC,"
            +KEY_LITTRAGE_COMMANDE+ " NUMERIC,"
            +KEY_TONNAGE_COMMANDE+ " NUMERIC,"
            +KEY_KG_COMMANDE+ " NUMERIC,"
            +KEY_COMMENTAIRE+ " TEXT,"
            +KEY_LIEU_LIVRAISON+ " TEXT,"
            +KEY_PAIEMENT_CODE + " NUMERIC,"
            +KEY_NB_LIGNE + " NUMERIC,"
            +KEY_CIRCUIT_CODE+ " TEXT,"
            +KEY_CHANNEL_CODE+ " TEXT,"
            +KEY_STATUT_CODE+ " TEXT,"
            +KEY_SOURCE + " TEXT ,"
            +KEY_VERSION+ " TEXT ,"
            +KEY_GPS_LATITUDE + " TEXT,"
            +KEY_GPS_LONGITUDE + " TEXT,"
            +KEY_DISTANCE + " NUMERIC"+ ")";

    public CommandeManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE);
            db.execSQL(CREATE_COMMANDE_TABLE);
        } catch (SQLException e) {
        }
        Log.d(TAG, "table Commande created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMANDE);
        onCreate(db);
    }

    public void add(Commande commande) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "add: "+commande.toString());
        ContentValues values = new ContentValues();
        values.put(KEY_COMMANDE_CODE, commande.getCOMMANDE_CODE());
        values.put(KEY_FACTURE_CODE, commande.getFACTURE_CODE());
        values.put(KEY_FACTURECLIENT_CODE, commande.getFACTURECLIENT_CODE());
        values.put(KEY_DATE_COMMANDE, commande.getDATE_COMMANDE());
        values.put(KEY_DATE_LIVRAISON, commande.getDATE_LIVRAISON());
        values.put(KEY_DATE_CREATION, commande.getDATE_CREATION());
        values.put(KEY_PERIODE_CODE, commande.getPERIODE_CODE());
        values.put(KEY_COMMANDETYPE_CODE, commande.getCOMMANDETYPE_CODE());
        values.put(KEY_COMMANDESTATUT_CODE, commande.getCOMMANDESTATUT_CODE());
        values.put(KEY_DISTRIBUTEUR_CODE, commande.getDISTRIBUTEUR_CODE());
        values.put(KEY_VENDEUR_CODE, commande.getVENDEUR_CODE());
        values.put(KEY_CLIENT_CODE, commande.getCLIENT_CODE());
        values.put(KEY_CREATEUR_CODE, commande.getCREATEUR_CODE());
        values.put(KEY_LIVREUR_CODE, commande.getLIVREUR_CODE());
        values.put(KEY_REGION_CODE, commande.getREGION_CODE());
        values.put(KEY_ZONE_CODE, commande.getZONE_CODE());
        values.put(KEY_SECTEUR_CODE, commande.getSECTEUR_CODE());
        values.put(KEY_SOUSSECTEUR_CODE, commande.getSOUSSECTEUR_CODE());
        values.put(KEY_TOURNEE_CODE, commande.getTOURNEE_CODE());
        values.put(KEY_VISITE_CODE, commande.getVISITE_CODE());
        values.put(KEY_STOCKDEPART_CODE, commande.getSTOCKDEPART_CODE());
        values.put(KEY_STOCKDESTINATION_CODE, commande.getSTOCKDESTINATION_CODE());
        values.put(KEY_DESTINATION_CODE, commande.getDESTINATION_CODE());
        values.put(KEY_TS, commande.getTS());
        values.put(KEY_MONTANT_BRUT, getNumberRounded(commande.getMONTANT_BRUT()));
        values.put(KEY_REMISE, getNumberRounded(commande.getREMISE()));
        values.put(KEY_MONTANT_NET, getNumberRounded(commande.getMONTANT_NET()));
        values.put(KEY_VALEUR_COMMANDE, getNumberRounded(commande.getVALEUR_COMMANDE()));
        values.put(KEY_LITTRAGE_COMMANDE, commande.getLITTRAGE_COMMANDE());
        values.put(KEY_TONNAGE_COMMANDE, commande.getTONNAGE_COMMANDE());
        values.put(KEY_KG_COMMANDE, commande.getKG_COMMANDE());
        values.put(KEY_COMMENTAIRE, commande.getCOMMENTAIRE());
        values.put(KEY_LIEU_LIVRAISON, commande.getLIEU_LIVRAISON());
        values.put(KEY_PAIEMENT_CODE, commande.getPAIEMENT_CODE());
        values.put(KEY_NB_LIGNE, commande.getNB_LIGNE());
        values.put(KEY_CIRCUIT_CODE, commande.getCIRCUIT_CODE());
        values.put(KEY_CHANNEL_CODE, commande.getCHANNEL_CODE());
        values.put(KEY_STATUT_CODE, commande.getSTATUT_CODE());
        values.put(KEY_SOURCE, commande.getSOURCE());
        values.put(KEY_VERSION, commande.getVERSION());
        values.put(KEY_GPS_LATITUDE, commande.getGPS_LATITUDE());
        values.put(KEY_GPS_LONGITUDE, commande.getGPS_LONGITUDE());
        values.put(KEY_DISTANCE, commande.getDISTANCE());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_COMMANDE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d(TAG, "nouvelle commande inseré dans la table Commande: " + id);
    }

    public int delete(String commandeCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_COMMANDE,KEY_COMMANDE_CODE+"=?",new String[]{commandeCode});
    }

    public void updateCommande(String commandeCode,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_COMMANDE + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_COMMANDE_CODE +"= '"+commandeCode+"'" ;
        db.execSQL(req);
        db.close();

        Log.d("commande", "updateCommande: "+req);
    }

    public void deleteCmdSynchronisee() {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefCmdAS = sdf.format(new Date());
        Log.d(TAG, "deleteCmdSynchronisee: "+DatefCmdAS);

        String Where = " "+KEY_COMMENTAIRE+"='inserted' and date("+KEY_DATE_CREATION+")!='"+DatefCmdAS+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_COMMANDE, Where, null);
        db.close();
        Log.d("commande", "Deleted all commandes verifiee from sqlite");
        Log.d("commande", "deleteCmdSynchronisee: "+Where);
    }


    public ArrayList<Commande> getList() {
            ArrayList<Commande> listCommande = new ArrayList<>();

            String selectQuery = "SELECT  * FROM " + TABLE_COMMANDE;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if( cursor != null && cursor.moveToFirst() ) {
                do {
                    Commande cmd = new Commande();
                    cmd.setCOMMANDE_CODE(cursor.getString(0));
                    cmd.setFACTURE_CODE(cursor.getString(1));
                    cmd.setFACTURECLIENT_CODE(cursor.getString(2));
                    cmd.setDATE_COMMANDE(cursor.getString(3));
                    cmd.setDATE_LIVRAISON((cursor.getString(4)));
                    cmd.setDATE_CREATION(cursor.getString(5));
                    cmd.setPERIODE_CODE(cursor.getString(6));
                    cmd.setCOMMANDETYPE_CODE(cursor.getString(7));
                    cmd.setCOMMANDESTATUT_CODE(cursor.getString(8));
                    cmd.setDISTRIBUTEUR_CODE(cursor.getString(9));
                    cmd.setVENDEUR_CODE(cursor.getString(10));
                    cmd.setCLIENT_CODE(cursor.getString(11));
                    cmd.setCREATEUR_CODE(cursor.getString(12));
                    cmd.setLIVREUR_CODE(cursor.getString(13));
                    cmd.setREGION_CODE(cursor.getString(14));
                    cmd.setZONE_CODE(cursor.getString(15));
                    cmd.setSECTEUR_CODE(cursor.getString(16));
                    cmd.setSOUSSECTEUR_CODE(cursor.getString(17));
                    cmd.setTOURNEE_CODE(cursor.getString(18));
                    cmd.setVISITE_CODE(cursor.getString(19));
                    cmd.setSTOCKDEPART_CODE(cursor.getString(20));
                    cmd.setSTOCKDESTINATION_CODE(cursor.getString(21));
                    cmd.setDESTINATION_CODE(cursor.getString(22));
                    cmd.setTS(cursor.getString(23));
                    cmd.setMONTANT_BRUT(cursor.getDouble(24));
                    cmd.setREMISE(cursor.getDouble(25));
                    cmd.setMONTANT_NET(cursor.getDouble(26));
                    cmd.setVALEUR_COMMANDE(cursor.getDouble(27));
                    cmd.setLITTRAGE_COMMANDE(cursor.getDouble(28));
                    cmd.setTONNAGE_COMMANDE(cursor.getDouble(29));
                    cmd.setKG_COMMANDE(cursor.getDouble(30));
                    cmd.setCOMMENTAIRE(cursor.getString(31));
                    cmd.setLIEU_LIVRAISON(cursor.getString(32));
                    cmd.setPAIEMENT_CODE(cursor.getInt(33));
                    cmd.setNB_LIGNE(cursor.getInt(34));
                    cmd.setCIRCUIT_CODE(cursor.getString(35));
                    cmd.setCHANNEL_CODE(cursor.getString(36));
                    cmd.setSTATUT_CODE(cursor.getString(37));
                    cmd.setSOURCE(cursor.getString(38));
                    cmd.setVERSION(cursor.getString(39));
                    cmd.setGPS_LATITUDE(cursor.getString(40));
                    cmd.setGPS_LONGITUDE(cursor.getString(41));
                    cmd.setDISTANCE(cursor.getInt(42));

                    listCommande.add(cmd);
                }while(cursor.moveToNext());
            }
            //returner la listArticles;
            cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande from table Commande: ");
        Log.d(TAG, "Nombre Commande from table commande: "+listCommande.size());
        return listCommande;
    }

    public ArrayList<Commande> getListNotInserted() {
        ArrayList<Commande> listCommande = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDE +" WHERE "+KEY_COMMENTAIRE+" = 'to_insert' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Commande cmd = new Commande();

                cmd.setCOMMANDE_CODE(cursor.getString(0));
                cmd.setFACTURE_CODE(cursor.getString(1));
                cmd.setFACTURECLIENT_CODE(cursor.getString(2));
                cmd.setDATE_COMMANDE(cursor.getString(3));
                cmd.setDATE_LIVRAISON((cursor.getString(4)));
                cmd.setDATE_CREATION(cursor.getString(5));
                cmd.setPERIODE_CODE(cursor.getString(6));
                cmd.setCOMMANDETYPE_CODE(cursor.getString(7));
                cmd.setCOMMANDESTATUT_CODE(cursor.getString(8));
                cmd.setDISTRIBUTEUR_CODE(cursor.getString(9));
                cmd.setVENDEUR_CODE(cursor.getString(10));
                cmd.setCLIENT_CODE(cursor.getString(11));
                cmd.setCREATEUR_CODE(cursor.getString(12));
                cmd.setLIVREUR_CODE(cursor.getString(13));
                cmd.setREGION_CODE(cursor.getString(14));
                cmd.setZONE_CODE(cursor.getString(15));
                cmd.setSECTEUR_CODE(cursor.getString(16));
                cmd.setSOUSSECTEUR_CODE(cursor.getString(17));
                cmd.setTOURNEE_CODE(cursor.getString(18));
                cmd.setVISITE_CODE(cursor.getString(19));
                cmd.setSTOCKDEPART_CODE(cursor.getString(20));
                cmd.setSTOCKDESTINATION_CODE(cursor.getString(21));
                cmd.setDESTINATION_CODE(cursor.getString(22));
                cmd.setTS(cursor.getString(23));
                cmd.setMONTANT_BRUT(cursor.getDouble(24));
                cmd.setREMISE(cursor.getDouble(25));
                cmd.setMONTANT_NET(cursor.getDouble(26));
                cmd.setVALEUR_COMMANDE(cursor.getDouble(27));
                cmd.setLITTRAGE_COMMANDE(cursor.getDouble(28));
                cmd.setTONNAGE_COMMANDE(cursor.getDouble(29));
                cmd.setKG_COMMANDE(cursor.getDouble(30));
                cmd.setCOMMENTAIRE(cursor.getString(31));
                cmd.setLIEU_LIVRAISON(cursor.getString(32));
                cmd.setPAIEMENT_CODE(cursor.getInt(33));
                cmd.setNB_LIGNE(cursor.getInt(34));
                cmd.setCIRCUIT_CODE(cursor.getString(35));
                cmd.setCHANNEL_CODE(cursor.getString(36));
                cmd.setSTATUT_CODE(cursor.getString(37));
                cmd.setSOURCE(cursor.getString(38));
                cmd.setVERSION(cursor.getString(39));
                cmd.setGPS_LATITUDE(cursor.getString(40));
                cmd.setGPS_LONGITUDE(cursor.getString(41));
                cmd.setDISTANCE(cursor.getInt(42));

                Log.d(TAG, "getListNotInserted: "+cmd.toString());
                listCommande.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande from table Commande: ");
        Log.d(TAG, "Nombre Commande from table commande: "+listCommande.size());
        return listCommande;
    }

    public ArrayList<Commande> getListByClientCode(String client_code) {
        ArrayList<Commande> listCommande = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDE +" WHERE "+ KEY_CLIENT_CODE +" = '"+client_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Commande cmd = new Commande();
                cmd.setCOMMANDE_CODE(cursor.getString(0));
                cmd.setFACTURE_CODE(cursor.getString(1));
                cmd.setFACTURECLIENT_CODE(cursor.getString(2));
                cmd.setDATE_COMMANDE(cursor.getString(3));
                cmd.setDATE_LIVRAISON((cursor.getString(4)));
                cmd.setDATE_CREATION(cursor.getString(5));
                cmd.setPERIODE_CODE(cursor.getString(6));
                cmd.setCOMMANDETYPE_CODE(cursor.getString(7));
                cmd.setCOMMANDESTATUT_CODE(cursor.getString(8));
                cmd.setDISTRIBUTEUR_CODE(cursor.getString(9));
                cmd.setVENDEUR_CODE(cursor.getString(10));
                cmd.setCLIENT_CODE(cursor.getString(11));
                cmd.setCREATEUR_CODE(cursor.getString(12));
                cmd.setLIVREUR_CODE(cursor.getString(13));
                cmd.setREGION_CODE(cursor.getString(14));
                cmd.setZONE_CODE(cursor.getString(15));
                cmd.setSECTEUR_CODE(cursor.getString(16));
                cmd.setSOUSSECTEUR_CODE(cursor.getString(17));
                cmd.setTOURNEE_CODE(cursor.getString(18));
                cmd.setVISITE_CODE(cursor.getString(19));
                cmd.setSTOCKDEPART_CODE(cursor.getString(20));
                cmd.setSTOCKDESTINATION_CODE(cursor.getString(21));
                cmd.setDESTINATION_CODE(cursor.getString(22));
                cmd.setTS(cursor.getString(23));
                cmd.setMONTANT_BRUT(cursor.getDouble(24));
                cmd.setREMISE(cursor.getDouble(25));
                cmd.setMONTANT_NET(cursor.getDouble(26));
                cmd.setVALEUR_COMMANDE(cursor.getDouble(27));
                cmd.setLITTRAGE_COMMANDE(cursor.getDouble(28));
                cmd.setTONNAGE_COMMANDE(cursor.getDouble(29));
                cmd.setKG_COMMANDE(cursor.getDouble(30));
                cmd.setCOMMENTAIRE(cursor.getString(31));
                cmd.setLIEU_LIVRAISON(cursor.getString(32));
                cmd.setPAIEMENT_CODE(cursor.getInt(33));
                cmd.setNB_LIGNE(cursor.getInt(34));
                cmd.setCIRCUIT_CODE(cursor.getString(35));
                cmd.setCHANNEL_CODE(cursor.getString(36));
                cmd.setSTATUT_CODE(cursor.getString(37));
                cmd.setSOURCE(cursor.getString(38));
                cmd.setVERSION(cursor.getString(39));
                cmd.setGPS_LATITUDE(cursor.getString(40));
                cmd.setGPS_LONGITUDE(cursor.getString(41));
                cmd.setDISTANCE(cursor.getInt(42));

                listCommande.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande from table Commande: ");
        return listCommande;
    }

    public ArrayList<Commande> getListByCC_DC(String client_code, String date_commande) {
        ArrayList<Commande> listCommande = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDE +" WHERE commande."+KEY_CLIENT_CODE +" = '"+client_code+"' " +
                "AND date(commande."+KEY_DATE_COMMANDE+") = '"+date_commande+"' " ;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "getListByCC_CD: "+selectQuery);
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Commande cmd = new Commande();
                cmd.setCOMMANDE_CODE(cursor.getString(0));
                cmd.setFACTURE_CODE(cursor.getString(1));
                cmd.setFACTURECLIENT_CODE(cursor.getString(2));
                cmd.setDATE_COMMANDE(cursor.getString(3));
                cmd.setDATE_LIVRAISON((cursor.getString(4)));
                cmd.setDATE_CREATION(cursor.getString(5));
                cmd.setPERIODE_CODE(cursor.getString(6));
                cmd.setCOMMANDETYPE_CODE(cursor.getString(7));
                cmd.setCOMMANDESTATUT_CODE(cursor.getString(8));
                cmd.setDISTRIBUTEUR_CODE(cursor.getString(9));
                cmd.setVENDEUR_CODE(cursor.getString(10));
                cmd.setCLIENT_CODE(cursor.getString(11));
                cmd.setCREATEUR_CODE(cursor.getString(12));
                cmd.setLIVREUR_CODE(cursor.getString(13));
                cmd.setREGION_CODE(cursor.getString(14));
                cmd.setZONE_CODE(cursor.getString(15));
                cmd.setSECTEUR_CODE(cursor.getString(16));
                cmd.setSOUSSECTEUR_CODE(cursor.getString(17));
                cmd.setTOURNEE_CODE(cursor.getString(18));
                cmd.setVISITE_CODE(cursor.getString(19));
                cmd.setSTOCKDEPART_CODE(cursor.getString(20));
                cmd.setSTOCKDESTINATION_CODE(cursor.getString(21));
                cmd.setDESTINATION_CODE(cursor.getString(22));
                cmd.setTS(cursor.getString(23));
                cmd.setMONTANT_BRUT(cursor.getDouble(24));
                cmd.setREMISE(cursor.getDouble(25));
                cmd.setMONTANT_NET(cursor.getDouble(26));
                cmd.setVALEUR_COMMANDE(cursor.getDouble(27));
                cmd.setLITTRAGE_COMMANDE(cursor.getDouble(28));
                cmd.setTONNAGE_COMMANDE(cursor.getDouble(29));
                cmd.setKG_COMMANDE(cursor.getDouble(30));
                cmd.setCOMMENTAIRE(cursor.getString(31));
                cmd.setLIEU_LIVRAISON(cursor.getString(32));
                cmd.setPAIEMENT_CODE(cursor.getInt(33));
                cmd.setNB_LIGNE(cursor.getInt(34));
                cmd.setCIRCUIT_CODE(cursor.getString(35));
                cmd.setCHANNEL_CODE(cursor.getString(36));
                cmd.setSTATUT_CODE(cursor.getString(37));
                cmd.setSOURCE(cursor.getString(38));
                cmd.setVERSION(cursor.getString(39));
                cmd.setGPS_LATITUDE(cursor.getString(40));
                cmd.setGPS_LONGITUDE(cursor.getString(41));
                cmd.setDISTANCE(cursor.getInt(42));

                listCommande.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande from table Commande: ");
        return listCommande;
    }

    public ArrayList<Commande> getListByCC_CD(String client_code, String date_commande) {
        ArrayList<Commande> listCommande = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDE +" WHERE commande."+KEY_CLIENT_CODE +" = '"+client_code+"' " +
                "AND date(commande."+KEY_DATE_COMMANDE+") = '"+date_commande+"' " +
                "AND "+KEY_COMMANDETYPE_CODE+" != '2' " +
                "AND commande."+KEY_COMMANDE_CODE+" NOT IN (SELECT DISTINCT(commande."+KEY_SOURCE+") FROM "+TABLE_COMMANDE +" WHERE commande."+KEY_COMMANDETYPE_CODE+"= '2')";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "getListByCC_CD: "+selectQuery);
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Commande cmd = new Commande();
                cmd.setCOMMANDE_CODE(cursor.getString(0));
                cmd.setFACTURE_CODE(cursor.getString(1));
                cmd.setFACTURECLIENT_CODE(cursor.getString(2));
                cmd.setDATE_COMMANDE(cursor.getString(3));
                cmd.setDATE_LIVRAISON((cursor.getString(4)));
                cmd.setDATE_CREATION(cursor.getString(5));
                cmd.setPERIODE_CODE(cursor.getString(6));
                cmd.setCOMMANDETYPE_CODE(cursor.getString(7));
                cmd.setCOMMANDESTATUT_CODE(cursor.getString(8));
                cmd.setDISTRIBUTEUR_CODE(cursor.getString(9));
                cmd.setVENDEUR_CODE(cursor.getString(10));
                cmd.setCLIENT_CODE(cursor.getString(11));
                cmd.setCREATEUR_CODE(cursor.getString(12));
                cmd.setLIVREUR_CODE(cursor.getString(13));
                cmd.setREGION_CODE(cursor.getString(14));
                cmd.setZONE_CODE(cursor.getString(15));
                cmd.setSECTEUR_CODE(cursor.getString(16));
                cmd.setSOUSSECTEUR_CODE(cursor.getString(17));
                cmd.setTOURNEE_CODE(cursor.getString(18));
                cmd.setVISITE_CODE(cursor.getString(19));
                cmd.setSTOCKDEPART_CODE(cursor.getString(20));
                cmd.setSTOCKDESTINATION_CODE(cursor.getString(21));
                cmd.setDESTINATION_CODE(cursor.getString(22));
                cmd.setTS(cursor.getString(23));
                cmd.setMONTANT_BRUT(cursor.getDouble(24));
                cmd.setREMISE(cursor.getDouble(25));
                cmd.setMONTANT_NET(cursor.getDouble(26));
                cmd.setVALEUR_COMMANDE(cursor.getDouble(27));
                cmd.setLITTRAGE_COMMANDE(cursor.getDouble(28));
                cmd.setTONNAGE_COMMANDE(cursor.getDouble(29));
                cmd.setKG_COMMANDE(cursor.getDouble(30));
                cmd.setCOMMENTAIRE(cursor.getString(31));
                cmd.setLIEU_LIVRAISON(cursor.getString(32));
                cmd.setPAIEMENT_CODE(cursor.getInt(33));
                cmd.setNB_LIGNE(cursor.getInt(34));
                cmd.setCIRCUIT_CODE(cursor.getString(35));
                cmd.setCHANNEL_CODE(cursor.getString(36));
                cmd.setSTATUT_CODE(cursor.getString(37));
                cmd.setSOURCE(cursor.getString(38));
                cmd.setVERSION(cursor.getString(39));
                cmd.setGPS_LATITUDE(cursor.getString(40));
                cmd.setGPS_LONGITUDE(cursor.getString(41));
                cmd.setDISTANCE(cursor.getInt(42));

                listCommande.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande from table Commande: ");
        return listCommande;
    }

    public ArrayList<Commande> getListByVisiteCode(String visite_code) {
        ArrayList<Commande> listCommande = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDE +" WHERE "+ KEY_VISITE_CODE +" = '"+visite_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Commande cmd = new Commande();
                cmd.setCOMMANDE_CODE(cursor.getString(0));
                cmd.setFACTURE_CODE(cursor.getString(1));
                cmd.setFACTURECLIENT_CODE(cursor.getString(2));
                cmd.setDATE_COMMANDE(cursor.getString(3));
                cmd.setDATE_LIVRAISON((cursor.getString(4)));
                cmd.setDATE_CREATION(cursor.getString(5));
                cmd.setPERIODE_CODE(cursor.getString(6));
                cmd.setCOMMANDETYPE_CODE(cursor.getString(7));
                cmd.setCOMMANDESTATUT_CODE(cursor.getString(8));
                cmd.setDISTRIBUTEUR_CODE(cursor.getString(9));
                cmd.setVENDEUR_CODE(cursor.getString(10));
                cmd.setCLIENT_CODE(cursor.getString(11));
                cmd.setCREATEUR_CODE(cursor.getString(12));
                cmd.setLIVREUR_CODE(cursor.getString(13));
                cmd.setREGION_CODE(cursor.getString(14));
                cmd.setZONE_CODE(cursor.getString(15));
                cmd.setSECTEUR_CODE(cursor.getString(16));
                cmd.setSOUSSECTEUR_CODE(cursor.getString(17));
                cmd.setTOURNEE_CODE(cursor.getString(18));
                cmd.setVISITE_CODE(cursor.getString(19));
                cmd.setSTOCKDEPART_CODE(cursor.getString(20));
                cmd.setSTOCKDESTINATION_CODE(cursor.getString(21));
                cmd.setDESTINATION_CODE(cursor.getString(22));
                cmd.setTS(cursor.getString(23));
                cmd.setMONTANT_BRUT(cursor.getDouble(24));
                cmd.setREMISE(cursor.getDouble(25));
                cmd.setMONTANT_NET(cursor.getDouble(26));
                cmd.setVALEUR_COMMANDE(cursor.getDouble(27));
                cmd.setLITTRAGE_COMMANDE(cursor.getDouble(28));
                cmd.setTONNAGE_COMMANDE(cursor.getDouble(29));
                cmd.setKG_COMMANDE(cursor.getDouble(30));
                cmd.setCOMMENTAIRE(cursor.getString(31));
                cmd.setLIEU_LIVRAISON(cursor.getString(32));
                cmd.setPAIEMENT_CODE(cursor.getInt(33));
                cmd.setNB_LIGNE(cursor.getInt(34));
                cmd.setCIRCUIT_CODE(cursor.getString(35));
                cmd.setCHANNEL_CODE(cursor.getString(36));
                cmd.setSTATUT_CODE(cursor.getString(37));
                cmd.setSOURCE(cursor.getString(38));
                cmd.setVERSION(cursor.getString(39));
                cmd.setGPS_LATITUDE(cursor.getString(40));
                cmd.setGPS_LONGITUDE(cursor.getString(41));
                cmd.setDISTANCE(cursor.getInt(42));

                listCommande.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande from table Commande: ");
        return listCommande;
    }

    public ArrayList<Commande> getListByDate(String date) {
        ArrayList<Commande> listCommande = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDE +" WHERE date("+KEY_DATE_COMMANDE+") = '"+date+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Commande cmd = new Commande();
                cmd.setCOMMANDE_CODE(cursor.getString(0));
                cmd.setFACTURE_CODE(cursor.getString(1));
                cmd.setFACTURECLIENT_CODE(cursor.getString(2));
                cmd.setDATE_COMMANDE(cursor.getString(3));
                cmd.setDATE_LIVRAISON((cursor.getString(4)));
                cmd.setDATE_CREATION(cursor.getString(5));
                cmd.setPERIODE_CODE(cursor.getString(6));
                cmd.setCOMMANDETYPE_CODE(cursor.getString(7));
                cmd.setCOMMANDESTATUT_CODE(cursor.getString(8));
                cmd.setDISTRIBUTEUR_CODE(cursor.getString(9));
                cmd.setVENDEUR_CODE(cursor.getString(10));
                cmd.setCLIENT_CODE(cursor.getString(11));
                cmd.setCREATEUR_CODE(cursor.getString(12));
                cmd.setLIVREUR_CODE(cursor.getString(13));
                cmd.setREGION_CODE(cursor.getString(14));
                cmd.setZONE_CODE(cursor.getString(15));
                cmd.setSECTEUR_CODE(cursor.getString(16));
                cmd.setSOUSSECTEUR_CODE(cursor.getString(17));
                cmd.setTOURNEE_CODE(cursor.getString(18));
                cmd.setVISITE_CODE(cursor.getString(19));
                cmd.setSTOCKDEPART_CODE(cursor.getString(20));
                cmd.setSTOCKDESTINATION_CODE(cursor.getString(21));
                cmd.setDESTINATION_CODE(cursor.getString(22));
                cmd.setTS(cursor.getString(23));
                cmd.setMONTANT_BRUT(cursor.getDouble(24));
                cmd.setREMISE(cursor.getDouble(25));
                cmd.setMONTANT_NET(cursor.getDouble(26));
                cmd.setVALEUR_COMMANDE(cursor.getDouble(27));
                cmd.setLITTRAGE_COMMANDE(cursor.getDouble(28));
                cmd.setTONNAGE_COMMANDE(cursor.getDouble(29));
                cmd.setKG_COMMANDE(cursor.getDouble(30));
                cmd.setCOMMENTAIRE(cursor.getString(31));
                cmd.setLIEU_LIVRAISON(cursor.getString(32));
                cmd.setPAIEMENT_CODE(cursor.getInt(33));
                cmd.setNB_LIGNE(cursor.getInt(34));
                cmd.setCIRCUIT_CODE(cursor.getString(35));
                cmd.setCHANNEL_CODE(cursor.getString(36));
                cmd.setSTATUT_CODE(cursor.getString(37));
                cmd.setSOURCE(cursor.getString(38));
                cmd.setVERSION(cursor.getString(39));
                cmd.setGPS_LATITUDE(cursor.getString(40));
                cmd.setGPS_LONGITUDE(cursor.getString(41));
                cmd.setDISTANCE(cursor.getInt(42));

                listCommande.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande from table Commande: ");
        return listCommande;
    }


    public void deleteCommandes() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_COMMANDE, null, null);
        db.close();
        Log.d(TAG, "Deleted all commandes info from sqlite");
    }

    public static void synchronisationCommande(final Context context){

        CommandeManager commandeManager = new CommandeManager(context);
        commandeManager.deleteCmdSynchronisee();

        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_COMMANDE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                  JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray commandes = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre de commandes  "+commandes.length() , Toast.LENGTH_LONG).show();
                       // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les Commandes : " +response);

                        if(commandes.length()>0) {
                            CommandeManager commandeManager = new CommandeManager(context);
                            for (int i = 0; i < commandes.length(); i++) {
                                JSONObject uneCommande = commandes.getJSONObject(i);
                                if (uneCommande.getString("Statut").equals("true")) {
                                    commandeManager.updateCommande((uneCommande.getString("COMMANDE_CODE")),"inserted");
                                    cptInsert++;
                                }else{
                                    cptDelete++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){

                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE: Inserted: "+cptInsert +" NotInserted: "+cptDelete ,"COMMANDE",1));

                        }

                        //logM.add("COMMANDE:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"COMMANDE");


                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "commande1 : "+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE : Error: "+errorMsg ,"COMMANDE",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "commande2 : "+"Json error: " +"erreur applcation commande" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(context, "commande2.1 : "+"response error: " +response.toString(), Toast.LENGTH_LONG).show();
                    if(SyncV2Activity.logSyncViewModel != null){

                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE : Error: "+e.getMessage() ,"COMMANDE",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"commande3 : "+error.getMessage(), Toast.LENGTH_LONG).show();
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE : Error: "+error.getMessage() ,"COMMANDE",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    CommandeManager commandeManager  = new CommandeManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableArticle");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(commandeManager.getListNotInserted()));
                    Log.d("Commandesynch", "Liste: "+commandeManager.getListNotInserted());
                    Log.d(TAG, "getParams: listnotinserted"+commandeManager.getListNotInserted().size());
                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    ///////////////////////////////////////////////SYNCHRONISATION COMMANDELIVRAISON/////////////////////////////////////////////
   /* public static void synchronisationCommandeLivraison(final Context context){

        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_ARTICLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ouvrir le logManager
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray articles = jObj.getJSONArray("Articles");
                        Toast.makeText(context, "Nombre d'articles " +articles.length(), Toast.LENGTH_SHORT).show();
                        int cptInsert = 0,cptDeleted = 0;
                        //Ajout/Suppression/modification des articles dans la base de données.
                        for (int i = 0; i < articles.length(); i++) {
                            JSONObject unArticle = articles.getJSONObject(i);
                            ArticleManager articleManager = new ArticleManager(context);
                            if(unArticle.getString("OPERATION").equals("DELETE")){
                                articleManager.delete(unArticle.getString("ARTICLE_CODE"));
                                cptDeleted++;
                            }
                            else {
                                articleManager.add(new Article(unArticle));
                                cptInsert++;
                            }
                        }
                        logM.add("ARTICLES:OK Insert:"+cptInsert +"Deleted:"+cptDeleted ,"SyncArticles");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        logM.add("ARTICLES:NOK Insert "+errorMsg ,"SyncArticles");
                        Toast.makeText(context,"ARTICLES:"+errorMsg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    logM.add("ARTICLES : NOK Insert "+e.getMessage() ,"SyncArticles");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "ARTICLES:"+error.getMessage(), Toast.LENGTH_LONG).show();
                LogSyncManager logM= new LogSyncManager(context);
                logM.add("ARTICLES : NOK Inserted "+error.getMessage() ,"SyncArticles");

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    CommandeManager commandeManager  = new CommandeManager(context);
                    for (int i = 0; i < commandeManager.getList().size(); i++) {
                        if(commandeManager.getList().get(i).getCOMMANDE_CODE()!=null && commandeManager.getList().get(i).getVERSION() != null )
                            params.put(commandeManager.getList().get(i).getCOMMANDE_CODE(), commandeManager.getList().get(i).getVERSION());
                    }
                }
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }*/
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    static public String ImprimerCommande(Commande commande, ArrayList<CommandeLigne> lisCommandeLigne,Context context) {

        String entete="";
        DecimalFormat formatDD = new DecimalFormat("#.00");
        DecimalFormat formatD = new DecimalFormat("#.0");
        TourneeManager tourneeManager = new TourneeManager(context);
        CommandeGratuiteManager commandeGratuiteManager = new CommandeGratuiteManager(context);
        ArticleManager articleManager=new ArticleManager(context);

        ArrayList<CommandeGratuite> list_commande_gratuite = new ArrayList<CommandeGratuite>();
        list_commande_gratuite=commandeGratuiteManager.getListbyCmdCode(commande.getCOMMANDE_CODE());


                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                Gson gson2 = new Gson();
                String json2 = pref.getString("User", "");
                Type type = new TypeToken<JSONObject>() {}.getType();
                final JSONObject user = gson2.fromJson(json2, type);
                String User_Name="";
                String User_tel="";
                String User_distributeur="";

                 try {
                     User_Name= user.getString("UTILISATEUR_NOM");
                     User_tel= user.getString("UTILISATEUR_TELEPHONE1");
                     User_distributeur = user.getString("DISTRIBUTEUR_CODE");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Client client_commande = new Client();
                ClientManager clientManager = new ClientManager(context);
                DistributeurManager distributeurManager = new DistributeurManager(context);
                client_commande= clientManager.get(commande.getCLIENT_CODE());

                if(User_distributeur==null){
                    User_distributeur="DISTRIBUTEUR";
                }else{
                    entete = distributeurManager.get(User_distributeur).getDISTRIBUTEUR_ENTETE();
                    Log.d("ENTETE ", " entete "+entete);

                    if(entete==null){
                        entete = "ENTETE";
                    }
                }
                if(User_tel==null){
                    User_tel="0606060606";
                }
                if(User_Name==null){
                    User_Name="UTILISATEUR";
                }

                Double TTC_BRUT = 0.0;
                Double REMISE = 0.0;
                Double TTC_APRES_REMISE = 0.0,TVA = 0.0,NET_PAYER = 0.0;


                //String chaine1 = entete.substring(0,54);
                // chaine2 = entete.substring(54,95);
                //String chaine3 = entete.substring(96);

                String message ="                     STE.TRAD & DIS   \r\n" +
                        "w(----------------------------------------------------\r\n" +
                        entete+"\r\n" +
                        //chaine2+"\r\n" +
                        //chaine3+"\r\n" +
                        "w(----------------------------------------------------\r\n" +
                        "Facture Nr:"+commande.getCOMMANDE_CODE()+"\r\n" +
                        "Date:"+commande.getDATE_LIVRAISON() +"\r\n" +
                        "Client:"+ commande.getCLIENT_CODE()+" " + client_commande.getCLIENT_NOM()+" TEL:"+client_commande.getCLIENT_TELEPHONE1()+"\r\n" +
                        "Adresse:"+client_commande.getADRESSE_RUE()+"\r\n" +
                        "Tournee:"+ tourneeManager.get(commande.getTOURNEE_CODE()).getTOURNEE_NOM()+"\n"+
                        "Vendeur:"+User_Name+"    TEL:"+User_tel+"\n" +
                        "----------------------------------------------------\n" +
                        "Produit          |U|QTE |PU   |Mt.Brut|Remise|Mt.Net\r\n" +
                        "\u001Bw(----------------------------------------------------\r\n";
                for(int i=0;i<lisCommandeLigne.size();i++) {
                    Double QTE =lisCommandeLigne.get(i).getQTE_COMMANDEE();

                    message += Nchaine(lisCommandeLigne.get(i).getARTICLE_DESIGNATION(),17) + "|"
                            +NchaineL(String.valueOf(lisCommandeLigne.get(i).getUNITE_CODE()),1)+"|"
                            +NchaineL(String.valueOf(QTE.intValue()),4)+"|"
                            +NchaineL(String.valueOf(formatD.format(lisCommandeLigne.get(i).getARTICLE_PRIX())),5)+"|"
                            +NchaineL(String.valueOf(formatD.format(lisCommandeLigne.get(i).getMONTANT_BRUT())),7)+"|"
                            +NchaineL(String.valueOf(formatD.format(lisCommandeLigne.get(i).getREMISE())),6)+"|"+
                             NchaineL(String.valueOf(formatD.format(lisCommandeLigne.get(i).getMONTANT_NET())),6) + "\r\n";
                    TTC_BRUT+=lisCommandeLigne.get(i).getMONTANT_BRUT();
                    REMISE+=lisCommandeLigne.get(i).getREMISE();
                    NET_PAYER+=lisCommandeLigne.get(i).getMONTANT_NET();
                }

                    String message2= "\u001Bw(----------------------------------------------------\r\n" +
                        "       TOTAL TTC Brut         :"+NchaineL(String.valueOf(formatDD.format(commande.getMONTANT_BRUT())),8)+"\r\n" +
                        "       Remise                 :"+NchaineL(String.valueOf(formatDD.format(commande.getREMISE())),8)+"\r\n" +
                        "       TOTAL TTC apres Remise :"+NchaineL(String.valueOf(formatDD.format(commande.getMONTANT_NET())),8)+"\r\n" +
                        "       dont TVA               :"+NchaineL(String.valueOf(formatDD.format(TVA)),8)+"\r\n" +
                        "            NET A payer       :"+NchaineL(String.valueOf(formatDD.format(commande.getMONTANT_NET())),8)+"\r\n" +
                        "\u001Bw(----------------------------------------------------\r\n\r\n";

                //Gratuitee
                    String message3="\u001Bw(\"Gratuite:\r\n" +
                        "Article             |QTE\r\n" +
                        "\u001Bw(-----------------------------\r\n";
                for(int i=0;i<list_commande_gratuite.size();i++){
                    Article article = new Article();
                    article=articleManager.get(list_commande_gratuite.get(i).getARTICLE_CODE());
                    message3+=Nchaine(article.getARTICLE_DESIGNATION1(),20) + "|"+NchaineL(String.valueOf(list_commande_gratuite.get(i).getQUANTITE()),4) + "\r\n";
                }


                message+=message2+message3+"\r\n\r\n\r\n\r\n";

        return message;
    }

    //////////////////////////////////////////////////////////IMPRIMER LIVRAISON///////////////////////////////////////////////////
    static public String ImprimerLivraison(Livraison livraison, ArrayList<LivraisonLigne> listLivraisonLignes, Context context) {

        DecimalFormat formatDD = new DecimalFormat("#.00");
        DecimalFormat formatD = new DecimalFormat("#.0");
        TourneeManager tourneeManager = new TourneeManager(context);
        CommandeGratuiteManager commandeGratuiteManager = new CommandeGratuiteManager(context);
        ArticleManager articleManager=new ArticleManager(context);

        Client client_livree = new Client();
        ClientManager clientManager = new ClientManager(context);
        DistributeurManager distributeurManager = new DistributeurManager(context);
        client_livree= clientManager.get(livraison.getCLIENT_CODE());

        ArrayList<CommandeGratuite> list_commande_gratuite = new ArrayList<CommandeGratuite>();
        list_commande_gratuite=commandeGratuiteManager.getList();

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);
        String User_Name="";
        String User_tel="";
        String User_distributeur="";

        try {
            User_Name= user.getString("UTILISATEUR_NOM");
            User_tel= user.getString("UTILISATEUR_TELEPHONE1");
            User_distributeur = user.getString("DISTRIBUTEUR_CODE");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(User_Name==null){
            User_Name="UTILISATEUR";
        }else{
            String entete = distributeurManager.get(User_distributeur).getDISTRIBUTEUR_ENTETE();
            Log.d("ENTETE ", " entete "+entete);

            if(entete==null){
                entete = "ENTETE";
            }
        }
        if(User_tel==null){
            User_tel="0606060606";
        }
        if(User_distributeur==null){
            User_distributeur="DISTRIBUTEUR";
        }



        Double TTC_BRUT = 0.0;
        Double REMISE = 0.0;
        Double TTC_APRES_REMISE = 0.0,TVA = 0.0,NET_PAYER = 0.0;

        String message ="                     STE.TRAD & DIS   \r\n" +
                "w(----------------------------------------------------\r\n" +
                "SIEGE: Rue Marseille, Res.Lamadrague, 2etage,"+"\r\n"+
                "N4-Tanger.\r\n" +
                "DEPOT: Av.Harroun Rachid, N108 Tanger.\r\n" +
                "TEL: 05 39 93 63 16.\r\n" +
                "w(----------------------------------------------------\r\n" +
                "Facture Nr:"+livraison.getLIVRAISON_CODE()+"\r\n" +
                "Date:"+livraison.getDATE_LIVRAISON() +"\r\n" +
                "Client:"+ livraison.getCLIENT_CODE()+" " + client_livree.getCLIENT_NOM()+" TEL:"+client_livree.getCLIENT_TELEPHONE1()+"\r\n" +
                "Adresse:"+client_livree.getADRESSE_RUE()+"\r\n" +
                //"Tournee:"+ tourneeManager.get(client_livree.getTOURNEE_CODE()).getTOURNEE_NOM()+"\n"+
                "Vendeur:"+livraison.getVENDEUR_CODE()+"\n" +
                "Livreur:"+User_Name+"    TEL:"+User_tel+"\n" +
                "----------------------------------------------------\n" +
                "Produit          |U|QTE |PU   |Mt.Brut|Remise|Mt.Net\r\n" +
                "\u001Bw(----------------------------------------------------\r\n";
        for(int i=0;i<listLivraisonLignes.size();i++) {
            Double QTE =listLivraisonLignes.get(i).getQTE_COMMANDEE();

            message += Nchaine(listLivraisonLignes.get(i).getARTICLE_DESIGNATION(),17) + "|"
                    +NchaineL(String.valueOf(listLivraisonLignes.get(i).getUNITE_CODE()),1)+"|"
                    +NchaineL(String.valueOf(QTE.intValue()),4)+"|"
                    +NchaineL(String.valueOf(formatD.format(listLivraisonLignes.get(i).getARTICLE_PRIX())),5)+"|"
                    +NchaineL(String.valueOf(formatD.format(listLivraisonLignes.get(i).getMONTANT_BRUT())),7)+"|"
                    +NchaineL(String.valueOf(formatD.format(listLivraisonLignes.get(i).getREMISE())),6)+"|"+
                    NchaineL(String.valueOf(formatD.format(listLivraisonLignes.get(i).getMONTANT_NET())),6) + "\r\n";
            TTC_BRUT+=listLivraisonLignes.get(i).getMONTANT_BRUT();
            REMISE+=listLivraisonLignes.get(i).getREMISE();
            NET_PAYER+=listLivraisonLignes.get(i).getMONTANT_NET();
        }

        String message2= "\u001Bw(----------------------------------------------------\r\n" +
                "       TOTAL TTC Brut         :"+NchaineL(String.valueOf(formatDD.format(livraison.getMONTANT_BRUT())),8)+"\r\n" +
                "       Remise                 :"+NchaineL(String.valueOf(formatDD.format(livraison.getREMISE())),8)+"\r\n" +
                "       TOTAL TTC apres Remise :"+NchaineL(String.valueOf(formatDD.format(livraison.getMONTANT_NET())),8)+"\r\n" +
                "       dont TVA               :"+NchaineL(String.valueOf(formatDD.format(TVA)),8)+"\r\n" +
                "            NET A payer       :"+NchaineL(String.valueOf(formatDD.format(livraison.getMONTANT_NET())),8)+"\r\n" +
                "\u001Bw(----------------------------------------------------\r\n\r\n";

        //Gratuitee
        String message3="\u001Bw(\"Gratuite:\r\n" +
                "Article             |QTE\r\n" +
                "\u001Bw(-----------------------------\r\n";
        for(int i=0;i<list_commande_gratuite.size();i++){
            Article article = new Article();
            article=articleManager.get(list_commande_gratuite.get(i).getARTICLE_CODE());
            message3+=Nchaine(article.getARTICLE_DESIGNATION1(),20) + "|"+NchaineL(String.valueOf(list_commande_gratuite.get(i).getQUANTITE()),4) + "\r\n";
        }


        message+=message2+message3+"\r\n\r\n\r\n\r\n";

        return message;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    static String Nchaine(String machaine, int size){
        int taille = machaine.length();
        String spaces="";
        if(size<taille) return machaine.substring(0,size);
        for(int i=0;i<size-taille;i++)
            spaces+=" ";
        return machaine+spaces;
    }

    static String NchaineL(String machaine, int size){
        int taille = machaine.length();
        String spaces="";
        if(size<taille) return machaine.substring(0,size);
        for(int i=0;i<size-taille;i++)
            spaces+=" ";
        return spaces+machaine;
    }

    //RETURN VISTE BY CODE
    public int CheckCommandeByVisiteCode(String visite_code) {

        String selectQuery = "SELECT * FROM " + TABLE_COMMANDE + " WHERE "+ KEY_VISITE_CODE +" = '"+visite_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int vcr=cursor.getCount();
        cursor.close();
        db.close();
        return vcr;

    }

    //public int CheckCmdALivrerByVisiteCode(String visite);

    public void UpdateCmdALL_QL(String commande_code,double commandeligne_code,String article_code,String unite_code) {

        String selectQuery = "UPDATE commandelignealivrer SET commandelignealivrer.QTE_LIVREE = (SELECT SUM(livraisonligne.QTE_LIVREE) FROM livraisonligne" +
                " WHERE livraisonligne.COMMANDE_CODE = '"+commande_code+"' AND livraisonligne.COMMANDELIGNE_CODE = '"+1 +"' " +
                "AND livraisonligne.ARTICLE_CODE = '"+article_code+"' AND livraisonligne.UNITE_CODE = '"+unite_code+"') WHERE commandelignealivrer.COMMANDE_CODE = '"+commande_code+"' " +
                "AND commandelignealivrer.COMMANDELIGNE_CODE = '"+1 +"' " +
                "AND commandelignealivrer.ARTICLE_CODE = '"+article_code+"' AND commandelignealivrer.UNITE_CODE = '"+unite_code+"' ;" ;
        Log.d("update", "UpdateCmdALL_QL: "+selectQuery.toString());

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.close();
        db.close();

    }

    public double getSumQtLivree(String commande_code,double commandeligne_code,String article_code,String unite_code){
        double qte_livree=0;
        String selectQuery ="SELECT SUM(livraisonligne.QTE_COMMANDEE) AS QTE, " +
                "SUM (livraisonligne.LITTRAGE_COMMANDEE) AS LIT," +
                "SUM (livraisonligne.TONNAGE_COMMANDEE) AS TON," +
                "SUM (livraisonligne.KG_COMMANDEE) AS KG," +
                "SUM (livraisonligne.MONTANT_NET) AS MN " +
                "FROM livraisonligne WHERE livraisonligne.COMMANDE_CODE = '"+commande_code+"' AND livraisonligne.COMMANDELIGNE_CODE ='"+commandeligne_code+"'" +
                " AND livraisonligne.ARTICLE_CODE ='"+article_code+"' AND livraisonligne.UNITE_CODE ='"+unite_code+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if ( cursor.moveToFirst() ) {
            qte_livree=cursor.getInt(0);
        } else {
            qte_livree=0;
        }

        cursor.close();
        db.close();

        return qte_livree;
    }

    public void updatecmdlal(String commande_code,double commandeligne_code,String article_code,String unite_code){
        double QTE=0;
        double LIT=0;
        double TON=0;
        double KG=0;
        double MN=0;
        double MB=0;

        String selectQuery ="SELECT SUM(livraisonligne.QTE_COMMANDEE) AS QTE, " +
                "SUM (livraisonligne.LITTRAGE_COMMANDEE) AS LIT," +
                "SUM (livraisonligne.TONNAGE_COMMANDEE) AS TON," +
                "SUM (livraisonligne.KG_COMMANDEE) AS KG," +
                "SUM (livraisonligne.MONTANT_NET) AS MN," +
                "SUM (livraisonligne.MONTANT_BRUT) AS MB " +
                "FROM livraisonligne WHERE livraisonligne.COMMANDE_CODE = '"+commande_code+"' " +
                " AND livraisonligne.ARTICLE_CODE ='"+article_code+"' AND livraisonligne.UNITE_CODE ='"+unite_code+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if ( cursor.moveToFirst() ) {

            QTE=cursor.getInt(0);
            LIT=cursor.getDouble(1);
            TON=cursor.getDouble(2);
            KG=cursor.getDouble(3);
            MN=cursor.getDouble(4);
            MB=cursor.getDouble(5);


        } else {

            QTE=0;
            LIT=0;
            TON=0;
            KG=0;
            MN=0;
            MB=0;
        }

        cursor.close();
        db.close();

        Log.d("values", "updatecmdal: "+QTE +LIT +TON +KG +MN +MB);

        if(QTE>0 && LIT>0 && TON>0 && KG>0 && MN>0 && MB>0){

            String selectQuery1="UPDATE commandenonclotureeligne SET QTE_LIVREE = '"+QTE+"' ," +
                    "LITTRAGE_LIVREE ='"+LIT+"' ," +
                    "TONNAGE_LIVREE ='"+TON+"'," +
                    "KG_LIVREE ='"+KG+"' ," +
                    "MONTANT_BRUT ='"+MB+"'," +
                    "MONTANT_NET ='"+MN+"' WHERE commandenonclotureeligne.COMMANDE_CODE ='"+commande_code+"' AND commandenonclotureeligne.ARTICLE_CODE = '"+article_code+"' AND commandenonclotureeligne.UNITE_CODE = '"+unite_code+"';" ;

            Log.d("qlquery", "updatecmdal: "+selectQuery1);
            SQLiteDatabase db1 = this.getReadableDatabase();
            db1.execSQL(selectQuery1);
            db1.close();
        }

    }


    public void ModifierStatutCommandeALivrer(String commande_code,Context context){

        CommandeALivreeManager commandeALivreeManager = new CommandeALivreeManager(context);
        CommandeALivrer commandeALivrer = commandeALivreeManager.getCmdALivrerByCmdCode(commande_code);

        CommandeLigneALivrerManager commandeLigneALivrerManager = new CommandeLigneALivrerManager(context);
        ArrayList<CommandeLigneALivrer> commandeLigneALivrers = commandeLigneALivrerManager.getListByCommandeCode(commande_code);

        double kg_livree=0;

        for(int i=0;i<commandeLigneALivrers.size();i++){
            kg_livree+=commandeLigneALivrers.get(i).getKG_LIVREE();
        }

        /*Log.d("livraisonavant", "ModifierStatutCommandeALivrer: "+kg_livree);
        Log.d("livraisonavant", "ModifierStatutCommandeALivrer: "+Math.round(kg_livree));
        Log.d("livraisonavant", "ModifierStatutCommandeALivrer: "+commandeALivrer.getKG_COMMANDE());*/

        if(kg_livree>=commandeALivrer.getKG_COMMANDE()){

            Log.d("livraisontot", "ModifierStatutCommandeALivrer: "+kg_livree);
            Log.d("livraisontot", "ModifierStatutCommandeALivrer: "+Math.round(kg_livree));
            Log.d("livraisontot", "ModifierStatutCommandeALivrer: "+commandeALivrer.getKG_COMMANDE());

            String selectQuery = "UPDATE commandealivrer SET COMMANDESTATUT_CODE = 5 WHERE commandealivrer.COMMANDE_CODE = "+commande_code+" ";
            SQLiteDatabase db = this.getReadableDatabase();
            db.execSQL(selectQuery);
            db.close();

        }else if(kg_livree<commandeALivrer.getKG_COMMANDE() && kg_livree>0){

            Log.d("livraisonpar", "ModifierStatutCommandeALivrer: "+kg_livree);
            Log.d("livraisonpar", "ModifierStatutCommandeALivrer: "+Math.round(kg_livree));
            Log.d("livraisonpar", "ModifierStatutCommandeALivrer: "+commandeALivrer.getKG_COMMANDE());

            String selectQuery = "UPDATE commandealivrer SET COMMANDESTATUT_CODE = 4 WHERE commandealivrer.COMMANDE_CODE = "+commande_code+" ";
            SQLiteDatabase db = this.getReadableDatabase();
            db.execSQL(selectQuery);
            db.close();
        }else{

            Log.d("livraisonapres", "ModifierStatutCommandeALivrer: "+kg_livree);
            Log.d("livraisonapres", "ModifierStatutCommandeALivrer: "+Math.round(kg_livree));
            Log.d("livraisonapres", "ModifierStatutCommandeALivrer: "+commandeALivrer.getKG_COMMANDE());
        }

    }

    public void ModifierStatutCommandeNonCloturee(String commande_code,Context context){

        CommandeNonClotureeManager commandeNonClotureeManager = new CommandeNonClotureeManager(context);
        CommandeNonCloturee commandeNonCloturee = commandeNonClotureeManager.getCmdNonClotureeByCmdCode(commande_code);

        CommandeNonClotureeLigneManager commandeNonClotureeLigneManager = new CommandeNonClotureeLigneManager(context);
        ArrayList<CommandeNonClotureeLigne> commandeNonClotureeLignes = commandeNonClotureeLigneManager.getListByCommandeCode(commande_code);

        double kg_livree=0;

        for(int i=0;i<commandeNonClotureeLignes.size();i++){
            kg_livree+=commandeNonClotureeLignes.get(i).getKG_LIVREE();
        }

        /*Log.d("livraisonavant", "ModifierStatutCommandeALivrer: "+kg_livree);
        Log.d("livraisonavant", "ModifierStatutCommandeALivrer: "+Math.round(kg_livree));
        Log.d("livraisonavant", "ModifierStatutCommandeALivrer: "+commandeALivrer.getKG_COMMANDE());*/

        if(kg_livree>=commandeNonCloturee.getKG_COMMANDE()){

            Log.d("livraisontot", "ModifierStatutCommandeALivrer: "+kg_livree);
            Log.d("livraisontot", "ModifierStatutCommandeALivrer: "+Math.round(kg_livree));
            Log.d("livraisontot", "ModifierStatutCommandeALivrer: "+commandeNonCloturee.getKG_COMMANDE());

            String selectQuery = "UPDATE commandenoncloturee SET COMMANDESTATUT_CODE = 5 WHERE commandenoncloturee.COMMANDE_CODE = "+commande_code+" ";
            SQLiteDatabase db = this.getReadableDatabase();
            db.execSQL(selectQuery);
            db.close();

        }else if(kg_livree<commandeNonCloturee.getKG_COMMANDE() && kg_livree>0){

            Log.d("livraisonpar", "ModifierStatutCommandeALivrer: "+kg_livree);
            Log.d("livraisonpar", "ModifierStatutCommandeALivrer: "+Math.round(kg_livree));
            Log.d("livraisonpar", "ModifierStatutCommandeALivrer: "+commandeNonCloturee.getKG_COMMANDE());

            String selectQuery = "UPDATE commandenoncloturee SET COMMANDESTATUT_CODE = 4 WHERE commandenoncloturee.COMMANDE_CODE = "+commande_code+" ";
            SQLiteDatabase db = this.getReadableDatabase();
            db.execSQL(selectQuery);
            db.close();
        }else{

            Log.d("livraisonapres", "ModifierStatutCommandeALivrer: "+kg_livree);
            Log.d("livraisonapres", "ModifierStatutCommandeALivrer: "+Math.round(kg_livree));
            Log.d("livraisonapres", "ModifierStatutCommandeALivrer: "+commandeNonCloturee.getKG_COMMANDE());
        }

    }

    public void ModifierStatutCommandeNonCloturee(Commande commande ,ArrayList<CommandeLigne> commandeLignes){


        double kg_livree=0;

        for(int i=0;i<commandeLignes.size();i++){
            kg_livree+=commandeLignes.get(i).getKG_LIVREE();
        }

        /*Log.d("livraisonavant", "ModifierStatutCommandeALivrer: "+kg_livree);
        Log.d("livraisonavant", "ModifierStatutCommandeALivrer: "+Math.round(kg_livree));
        Log.d("livraisonavant", "ModifierStatutCommandeALivrer: "+commandeALivrer.getKG_COMMANDE());*/

        if(kg_livree>=commande.getKG_COMMANDE()){

            Log.d("livraisontot", "ModifierStatutCommandeALivrer: "+kg_livree);
            Log.d("livraisontot", "ModifierStatutCommandeALivrer: "+Math.round(kg_livree));
            Log.d("livraisontot", "ModifierStatutCommandeALivrer: "+commande.getKG_COMMANDE());

            String selectQuery = "UPDATE commandenoncloturee SET COMMANDESTATUT_CODE = 5 WHERE commandenoncloturee.COMMANDE_CODE = "+commande.getCOMMANDE_CODE()+" ";
            String selectQuery1 = "UPDATE commande SET COMMANDESTATUT_CODE = 5 WHERE commande.COMMANDE_CODE = "+commande.getCOMMANDE_CODE()+" ";

            SQLiteDatabase db = this.getReadableDatabase();
            db.execSQL(selectQuery);
            db.execSQL(selectQuery1);
            db.close();

        }else if(kg_livree<commande.getKG_COMMANDE() && kg_livree>0){

            Log.d("livraisonpar", "ModifierStatutCommandeALivrer: "+kg_livree);
            Log.d("livraisonpar", "ModifierStatutCommandeALivrer: "+Math.round(kg_livree));
            Log.d("livraisonpar", "ModifierStatutCommandeALivrer: "+commande.getKG_COMMANDE());

            String selectQuery = "UPDATE commandenoncloturee SET COMMANDESTATUT_CODE = 4 WHERE commandenoncloturee.COMMANDE_CODE = "+commande.getCOMMANDE_CODE()+" ";
            String selectQuery1 = "UPDATE commande SET COMMANDESTATUT_CODE = 4 WHERE commande.COMMANDE_CODE = "+commande.getCOMMANDE_CODE()+" ";

            SQLiteDatabase db = this.getReadableDatabase();
            db.execSQL(selectQuery);
            db.execSQL(selectQuery1);
            db.close();
        }else{

            Log.d("livraisonapres", "ModifierStatutCommandeALivrer: "+kg_livree);
            Log.d("livraisonapres", "ModifierStatutCommandeALivrer: "+Math.round(kg_livree));
            Log.d("livraisonapres", "ModifierStatutCommandeALivrer: "+commande.getKG_COMMANDE());
        }

    }

    public double getMontantNet(ArrayList<CommandeLigne> commandeLignes){
        double MN=0;

        if(commandeLignes.size()>0){
            for(int i=0 ; i<commandeLignes.size(); i++){
                MN+=commandeLignes.get(i).getMONTANT_NET();
            }
        }


        return MN;
    }

    public int getClientLivree(String client_code, Context context){

        ArrayList<CommandeNonCloturee> commandeNonCloturees = new ArrayList<>();
        CommandeNonClotureeManager commandeNonClotureeManager = new CommandeNonClotureeManager(context);
        commandeNonCloturees=commandeNonClotureeManager.getListByClientCode(client_code);

        int compt=0; //Le nombre de commandes livrees commandestatutcode = 5
        int nbrCmd=commandeNonCloturees.size(); //nombre des commandes du client

        for(int i=0;i<commandeNonCloturees.size();i++){
            if(commandeNonCloturees.get(i).getCOMMANDESTATUT_CODE().equals("5")){
                Log.d("livraison1", "getClientLivree: "+commandeNonCloturees.get(i).getCOMMANDESTATUT_CODE().toString());
                compt+=1;
            }
        }

        if(compt==nbrCmd && compt>0){

            return 10;

        }else if(nbrCmd>compt && compt>0){

            return 9;

        }else{

            return 0;

        }

    }



    public ArrayList<Commande> getListCmdNC(String client_code,Context context){

        int Nbrecmd = 0;//Nombre de commdes différentes
        CommandeManager commandeManager = new CommandeManager(context);
        CommandeNonClotureeManager commandeNonClotureeManager = new CommandeNonClotureeManager(context);

        ArrayList<CommandeNonCloturee> commandeNonCloturees = new ArrayList<>();
        ArrayList<Commande> commandes= new ArrayList<>();
        ArrayList<Commande> commandesFinales = new ArrayList<>();

        commandes = commandeManager.getListByClientCode(client_code);
        commandeNonCloturees = commandeNonClotureeManager.getListByClientCode(client_code);

        for(int i=0;i<=commandeNonCloturees.size();i++){

            for(int j=0;j<=commandes.size();j++){

                if(commandeNonCloturees.get(i).getCOMMANDE_CODE()==commandes.get(j).getCOMMANDE_CODE()){
                    break;
                }else{
                    Nbrecmd++;
                }
            }

            if(Nbrecmd==commandes.size()){
                commandes.add(new Commande(commandeNonCloturees.get(i)));
            }
        }
        return commandes;
    }

    public double getNumberRounded(double nombre){

        double number = nombre;
        int decimalsToConsider = 2;
        BigDecimal bigDecimal = new BigDecimal(number);
        BigDecimal roundedWithScale = bigDecimal.setScale(decimalsToConsider, BigDecimal.ROUND_HALF_UP);
        //System.out.println("Rounded value with setting scale = "+roundedWithScale);


        return roundedWithScale.doubleValue();
    }

    public Commande getCmdByCmdPromotion(ArrayList<CommandePromotion> commandePromotions, Commande commande, Context context){

        Commande commande1 = commande;


        return commande1;
    }

    public Commande calcDroitTimbre(Commande commande){

        Commande commande1 = commande;
        double montant_net = 0;

        montant_net = commande1.getMONTANT_NET() + (commande1.getMONTANT_NET()*(0.25/100));
        commande1.setMONTANT_NET(montant_net);

        return commande1;
    }


}
