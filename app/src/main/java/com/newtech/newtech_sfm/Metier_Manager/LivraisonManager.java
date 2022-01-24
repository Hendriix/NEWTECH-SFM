package com.newtech.newtech_sfm.Metier_Manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.newtech.newtech_sfm.Activity.SyncV2Activity;
import com.newtech.newtech_sfm.Configuration.AppConfig;
import com.newtech.newtech_sfm.Configuration.AppController;
import com.newtech.newtech_sfm.Configuration.SQLiteHandler;
import com.newtech.newtech_sfm.Metier.Livraison;
import com.newtech.newtech_sfm.Metier.LivraisonLigne;
import com.newtech.newtech_sfm.Metier.LogSync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TONPC on 25/04/2017.
 */

public class LivraisonManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_LIVRAISON = "livraison";

    private static final String
            KEY_LIVRAISON_CODE="LIVRAISON_CODE",
            KEY_LIVRAISON_DATE="LIVRAISON_DATE",
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
            KEY_PAIEMENT_CODE="PAIEMENT_CODE",
            KEY_NB_LIGNE="NB_LIGNE",
            KEY_CIRCUIT_CODE="CIRCUIT_CODE",
            KEY_CHANNEL_CODE="CHANNEL_CODE",
            KEY_SOURCE="SOURCE",
            KEY_VERSION="VERSION",
            KEY_GPS_LATITUDE="GPS_LATITUDE",
            KEY_GPS_LONGITUDE="GPS_LONGITUDE",
            KEY_DISTANCE="DISTANCE";

    public static String CREATE_LIVRAISON_TABLE = "CREATE TABLE " + TABLE_LIVRAISON + "("

            +KEY_LIVRAISON_CODE + " TEXT PRIMARY KEY ,"
            +KEY_LIVRAISON_DATE + " TEXT ,"
            +KEY_COMMANDE_CODE + " TEXT ,"
            +KEY_FACTURE_CODE+ " TEXT,"
            +KEY_FACTURECLIENT_CODE    + " TEXT,"
            +KEY_DATE_COMMANDE + " TEXT,"
            +KEY_DATE_LIVRAISON  + " TEXT,"
            +KEY_DATE_CREATION + " TEXT,"
            +KEY_PERIODE_CODE+ " TEXT,"
            +KEY_COMMANDETYPE_CODE     + " TEXT,"
            +KEY_COMMANDESTATUT_CODE    + " TEXT,"
            +KEY_DISTRIBUTEUR_CODE     + " TEXT,"
            +KEY_VENDEUR_CODE+ " TEXT,"
            +KEY_CLIENT_CODE+ " TEXT,"
            +KEY_CREATEUR_CODE + " TEXT,"
            +KEY_LIVREUR_CODE+ " TEXT,"
            +KEY_REGION_CODE+ " TEXT,"
            +KEY_ZONE_CODE+ " TEXT,"
            +KEY_SECTEUR_CODE+ " TEXT,"
            +KEY_SOUSSECTEUR_CODE   + " TEXT,"
            +KEY_TOURNEE_CODE+ " TEXT,"
            +KEY_VISITE_CODE+ " TEXT,"
            +KEY_STOCKDEPART_CODE  + " TEXT,"
            +KEY_STOCKDESTINATION_CODE + " TEXT,"
            +KEY_DESTINATION_CODE + " TEXT,"
            +KEY_TS + " TEXT,"
            +KEY_MONTANT_BRUT+ " NUMERIC ,"
            +KEY_REMISE + " NUMERIC ,"
            +KEY_MONTANT_NET + " NUMERIC,"
            +KEY_VALEUR_COMMANDE   + " NUMERIC,"
            +KEY_LITTRAGE_COMMANDE  + " NUMERIC,"
            +KEY_TONNAGE_COMMANDE  + " NUMERIC,"
            +KEY_KG_COMMANDE+ " NUMERIC,"
            +KEY_COMMENTAIRE+ " TEXT ,"
            +KEY_PAIEMENT_CODE + " NUMERIC,"
            +KEY_NB_LIGNE+ " NUMERIC,"
            +KEY_CIRCUIT_CODE+ " TEXT,"
            +KEY_CHANNEL_CODE+ " TEXT ,"
            +KEY_SOURCE + " TEXT ,"
            +KEY_VERSION+ " TEXT ,"
            +KEY_GPS_LATITUDE + " TEXT,"
            +KEY_GPS_LONGITUDE + " TEXT,"
            +KEY_DISTANCE + " NUMERIC"+ ")";

    public LivraisonManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE);
            db.execSQL(CREATE_LIVRAISON_TABLE);
        } catch (SQLException e) {
        }
        Log.d(TAG, "table livraison created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIVRAISON);
        onCreate(db);
    }

    public void add(Livraison livraison) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LIVRAISON_CODE,livraison.getLIVRAISON_CODE());
        values.put(KEY_LIVRAISON_DATE,livraison.getLIVRAISON_DATE());
        values.put(KEY_COMMANDE_CODE, livraison.getCOMMANDE_CODE());
        values.put(KEY_FACTURE_CODE, livraison.getFACTURE_CODE());
        values.put(KEY_FACTURECLIENT_CODE, livraison.getFACTURECLIENT_CODE());
        values.put(KEY_DATE_COMMANDE, livraison.getDATE_COMMANDE());
        values.put(KEY_DATE_LIVRAISON, livraison.getDATE_LIVRAISON());
        values.put(KEY_DATE_CREATION, livraison.getDATE_CREATION());
        values.put(KEY_PERIODE_CODE, livraison.getPERIODE_CODE());
        values.put(KEY_COMMANDETYPE_CODE, livraison.getCOMMANDETYPE_CODE());
        values.put(KEY_COMMANDESTATUT_CODE, livraison.getCOMMANDESTATUT_CODE());
        values.put(KEY_DISTRIBUTEUR_CODE, livraison.getDISTRIBUTEUR_CODE());
        values.put(KEY_VENDEUR_CODE, livraison.getVENDEUR_CODE());
        values.put(KEY_CLIENT_CODE, livraison.getCLIENT_CODE());
        values.put(KEY_CREATEUR_CODE, livraison.getCREATEUR_CODE());
        values.put(KEY_LIVREUR_CODE, livraison.getLIVREUR_CODE());
        values.put(KEY_REGION_CODE, livraison.getREGION_CODE());
        values.put(KEY_ZONE_CODE, livraison.getZONE_CODE());
        values.put(KEY_SECTEUR_CODE, livraison.getSECTEUR_CODE());
        values.put(KEY_SOUSSECTEUR_CODE, livraison.getSOUSSECTEUR_CODE());
        values.put(KEY_TOURNEE_CODE, livraison.getTOURNEE_CODE());
        values.put(KEY_VISITE_CODE, livraison.getVISITE_CODE());
        values.put(KEY_STOCKDEPART_CODE, livraison.getSTOCKDEPART_CODE());
        values.put(KEY_STOCKDESTINATION_CODE, livraison.getSTOCKDESTINATION_CODE());
        values.put(KEY_DESTINATION_CODE, livraison.getDESTINATION_CODE());
        values.put(KEY_TS,livraison.getTS());
        values.put(KEY_MONTANT_BRUT, getNumberRounded(livraison.getMONTANT_BRUT()));
        values.put(KEY_REMISE, getNumberRounded(livraison.getREMISE()));
        values.put(KEY_MONTANT_NET, getNumberRounded(livraison.getMONTANT_NET()));
        values.put(KEY_VALEUR_COMMANDE, getNumberRounded(livraison.getVALEUR_COMMANDE()));
        values.put(KEY_LITTRAGE_COMMANDE, livraison.getLITTRAGE_COMMANDE());
        values.put(KEY_TONNAGE_COMMANDE, livraison.getTONNAGE_COMMANDE());
        values.put(KEY_KG_COMMANDE, livraison.getKG_COMMANDE());
        values.put(KEY_COMMENTAIRE, livraison.getCOMMENTAIRE());
        values.put(KEY_PAIEMENT_CODE, livraison.getPAIEMENT_CODE());
        values.put(KEY_NB_LIGNE, livraison.getNB_LIGNE());
        values.put(KEY_CIRCUIT_CODE, livraison.getCIRCUIT_CODE());
        values.put(KEY_CHANNEL_CODE, livraison.getCHANNEL_CODE());
        values.put(KEY_SOURCE, livraison.getSOURCE());
        values.put(KEY_VERSION, livraison.getVERSION());
        values.put(KEY_GPS_LATITUDE, livraison.getGPS_LATITUDE());
        values.put(KEY_GPS_LONGITUDE, livraison.getGPS_LONGITUDE());
        values.put(KEY_DISTANCE, livraison.getDISTANCE());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_LIVRAISON, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d(TAG, "nouvelle livraison inseree dans la table Livraison: " + id);
    }

    public Livraison get(String livraison_code) {

        String selectQuery = "SELECT * FROM " + TABLE_LIVRAISON + " WHERE "+ KEY_LIVRAISON_CODE +" = '"+livraison_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Livraison livraison = new Livraison();

        if( cursor != null && cursor.moveToFirst() ) {
            livraison.setLIVRAISON_CODE(cursor.getString(0));
            livraison.setLIVRAISON_DATE(cursor.getString(1));
            livraison.setCOMMANDE_CODE(cursor.getString(2));
            livraison.setFACTURE_CODE(cursor.getString(3));
            livraison.setFACTURECLIENT_CODE(cursor.getString(4));
            livraison.setDATE_COMMANDE(cursor.getString(5));
            livraison.setDATE_LIVRAISON((cursor.getString(6)));
            livraison.setDATE_CREATION(cursor.getString(7));
            livraison.setPERIODE_CODE(cursor.getString(8));
            livraison.setCOMMANDETYPE_CODE(cursor.getString(9));
            livraison.setCOMMANDESTATUT_CODE(cursor.getString(10));
            livraison.setDISTRIBUTEUR_CODE(cursor.getString(11));
            livraison.setVENDEUR_CODE(cursor.getString(12));
            livraison.setCLIENT_CODE(cursor.getString(13));
            livraison.setCREATEUR_CODE(cursor.getString(14));
            livraison.setLIVREUR_CODE(cursor.getString(15));
            livraison.setREGION_CODE(cursor.getString(16));
            livraison.setZONE_CODE(cursor.getString(17));
            livraison.setSECTEUR_CODE(cursor.getString(18));
            livraison.setSOUSSECTEUR_CODE(cursor.getString(19));
            livraison.setTOURNEE_CODE(cursor.getString(20));
            livraison.setVISITE_CODE(cursor.getString(21));
            livraison.setSTOCKDEPART_CODE(cursor.getString(22));
            livraison.setSTOCKDESTINATION_CODE(cursor.getString(23));
            livraison.setDESTINATION_CODE(cursor.getString(24));
            livraison.setTS(cursor.getString(25));
            livraison.setMONTANT_BRUT(cursor.getDouble(26));
            livraison.setREMISE(cursor.getDouble(27));
            livraison.setMONTANT_NET(cursor.getDouble(28));
            livraison.setVALEUR_COMMANDE(cursor.getDouble(29));
            livraison.setLITTRAGE_COMMANDE(cursor.getDouble(30));
            livraison.setTONNAGE_COMMANDE(cursor.getDouble(31));
            livraison.setKG_COMMANDE(cursor.getDouble(32));
            livraison.setCOMMENTAIRE(cursor.getString(33));
            livraison.setPAIEMENT_CODE(cursor.getInt(34));
            livraison.setNB_LIGNE(cursor.getInt(35));
            livraison.setCIRCUIT_CODE(cursor.getString(36));
            livraison.setCHANNEL_CODE(cursor.getString(37));
            livraison.setSOURCE(cursor.getString(38));
            livraison.setVERSION(cursor.getString(39));
            livraison.setGPS_LATITUDE(cursor.getString(40));
            livraison.setGPS_LONGITUDE(cursor.getString(41));
            livraison.setDISTANCE(cursor.getInt(42));
        }

        cursor.close();
        db.close();
        return livraison;

    }


    public ArrayList<Livraison> getList() {
        ArrayList<Livraison> listLivraison = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LIVRAISON;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {

                Livraison livraison = new Livraison();
                livraison.setLIVRAISON_CODE(cursor.getString(0));
                livraison.setLIVRAISON_DATE(cursor.getString(1));
                livraison.setCOMMANDE_CODE(cursor.getString(2));
                livraison.setFACTURE_CODE(cursor.getString(3));
                livraison.setFACTURECLIENT_CODE(cursor.getString(4));
                livraison.setDATE_COMMANDE(cursor.getString(5));
                livraison.setDATE_LIVRAISON((cursor.getString(6)));
                livraison.setDATE_CREATION(cursor.getString(7));
                livraison.setPERIODE_CODE(cursor.getString(8));
                livraison.setCOMMANDETYPE_CODE(cursor.getString(9));
                livraison.setCOMMANDESTATUT_CODE(cursor.getString(10));
                livraison.setDISTRIBUTEUR_CODE(cursor.getString(11));
                livraison.setVENDEUR_CODE(cursor.getString(12));
                livraison.setCLIENT_CODE(cursor.getString(13));
                livraison.setCREATEUR_CODE(cursor.getString(14));
                livraison.setLIVREUR_CODE(cursor.getString(15));
                livraison.setREGION_CODE(cursor.getString(16));
                livraison.setZONE_CODE(cursor.getString(17));
                livraison.setSECTEUR_CODE(cursor.getString(18));
                livraison.setSOUSSECTEUR_CODE(cursor.getString(19));
                livraison.setTOURNEE_CODE(cursor.getString(20));
                livraison.setVISITE_CODE(cursor.getString(21));
                livraison.setSTOCKDEPART_CODE(cursor.getString(22));
                livraison.setSTOCKDESTINATION_CODE(cursor.getString(23));
                livraison.setDESTINATION_CODE(cursor.getString(24));
                livraison.setTS(cursor.getString(25));
                livraison.setMONTANT_BRUT(cursor.getDouble(26));
                livraison.setREMISE(cursor.getDouble(27));
                livraison.setMONTANT_NET(cursor.getDouble(28));
                livraison.setVALEUR_COMMANDE(cursor.getDouble(29));
                livraison.setLITTRAGE_COMMANDE(cursor.getDouble(30));
                livraison.setTONNAGE_COMMANDE(cursor.getDouble(31));
                livraison.setKG_COMMANDE(cursor.getDouble(32));
                livraison.setCOMMENTAIRE(cursor.getString(33));
                livraison.setPAIEMENT_CODE(cursor.getInt(34));
                livraison.setNB_LIGNE(cursor.getInt(35));
                livraison.setCIRCUIT_CODE(cursor.getString(36));
                livraison.setCHANNEL_CODE(cursor.getString(37));
                livraison.setSOURCE(cursor.getString(38));
                livraison.setVERSION(cursor.getString(39));
                livraison.setGPS_LATITUDE(cursor.getString(40));
                livraison.setGPS_LONGITUDE(cursor.getString(41));
                livraison.setDISTANCE(cursor.getInt(42));

                listLivraison.add(livraison);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Livraison from table livraison: ");
        return listLivraison;
    }

    public ArrayList<Livraison> getListByCC_CD(String client_code) {
        ArrayList<Livraison> listLivraison = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LIVRAISON +" WHERE livraison."+KEY_CLIENT_CODE +" = '"+client_code+"' " +
                /*"AND date(livraison."+KEY_DATE_COMMANDE+") = '"+date_commande+"' " +*/
                "AND "+KEY_COMMANDETYPE_CODE+" != '2' " +
                "AND livraison."+KEY_LIVRAISON_CODE+" NOT IN (SELECT DISTINCT(livraison."+KEY_SOURCE+") FROM "+TABLE_LIVRAISON +" WHERE livraison."+KEY_COMMANDETYPE_CODE+"= '2')";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "getListByCC_CD: "+selectQuery);
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Livraison livraison = new Livraison();
                livraison.setLIVRAISON_CODE(cursor.getString(0));
                livraison.setLIVRAISON_DATE(cursor.getString(1));
                livraison.setCOMMANDE_CODE(cursor.getString(2));
                livraison.setFACTURE_CODE(cursor.getString(3));
                livraison.setFACTURECLIENT_CODE(cursor.getString(4));
                livraison.setDATE_COMMANDE(cursor.getString(5));
                livraison.setDATE_LIVRAISON((cursor.getString(6)));
                livraison.setDATE_CREATION(cursor.getString(7));
                livraison.setPERIODE_CODE(cursor.getString(8));
                livraison.setCOMMANDETYPE_CODE(cursor.getString(9));
                livraison.setCOMMANDESTATUT_CODE(cursor.getString(10));
                livraison.setDISTRIBUTEUR_CODE(cursor.getString(11));
                livraison.setVENDEUR_CODE(cursor.getString(12));
                livraison.setCLIENT_CODE(cursor.getString(13));
                livraison.setCREATEUR_CODE(cursor.getString(14));
                livraison.setLIVREUR_CODE(cursor.getString(15));
                livraison.setREGION_CODE(cursor.getString(16));
                livraison.setZONE_CODE(cursor.getString(17));
                livraison.setSECTEUR_CODE(cursor.getString(18));
                livraison.setSOUSSECTEUR_CODE(cursor.getString(19));
                livraison.setTOURNEE_CODE(cursor.getString(20));
                livraison.setVISITE_CODE(cursor.getString(21));
                livraison.setSTOCKDEPART_CODE(cursor.getString(22));
                livraison.setSTOCKDESTINATION_CODE(cursor.getString(23));
                livraison.setDESTINATION_CODE(cursor.getString(24));
                livraison.setTS(cursor.getString(25));
                livraison.setMONTANT_BRUT(cursor.getDouble(26));
                livraison.setREMISE(cursor.getDouble(27));
                livraison.setMONTANT_NET(cursor.getDouble(28));
                livraison.setVALEUR_COMMANDE(cursor.getDouble(29));
                livraison.setLITTRAGE_COMMANDE(cursor.getDouble(30));
                livraison.setTONNAGE_COMMANDE(cursor.getDouble(31));
                livraison.setKG_COMMANDE(cursor.getDouble(32));
                livraison.setCOMMENTAIRE(cursor.getString(33));
                livraison.setPAIEMENT_CODE(cursor.getInt(34));
                livraison.setNB_LIGNE(cursor.getInt(35));
                livraison.setCIRCUIT_CODE(cursor.getString(36));
                livraison.setCHANNEL_CODE(cursor.getString(37));
                livraison.setSOURCE(cursor.getString(38));
                livraison.setVERSION(cursor.getString(39));
                livraison.setGPS_LATITUDE(cursor.getString(40));
                livraison.setGPS_LONGITUDE(cursor.getString(41));
                livraison.setDISTANCE(cursor.getInt(42));

                listLivraison.add(livraison);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Livraison from table Commande: ");
        return listLivraison;
    }


    public ArrayList<Livraison> getListNotInserted() {
        ArrayList<Livraison> listLivraison = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LIVRAISON +" WHERE "+KEY_COMMENTAIRE+" = 'to_insert' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {

                Livraison livraison = new Livraison();
                livraison.setLIVRAISON_CODE(cursor.getString(0));
                livraison.setLIVRAISON_DATE(cursor.getString(1));
                livraison.setCOMMANDE_CODE(cursor.getString(2));
                livraison.setFACTURE_CODE(cursor.getString(3));
                livraison.setFACTURECLIENT_CODE(cursor.getString(4));
                livraison.setDATE_COMMANDE(cursor.getString(5));
                livraison.setDATE_LIVRAISON((cursor.getString(6)));
                livraison.setDATE_CREATION(cursor.getString(7));
                livraison.setPERIODE_CODE(cursor.getString(8));
                livraison.setCOMMANDETYPE_CODE(cursor.getString(9));
                livraison.setCOMMANDESTATUT_CODE(cursor.getString(10));
                livraison.setDISTRIBUTEUR_CODE(cursor.getString(11));
                livraison.setVENDEUR_CODE(cursor.getString(12));
                livraison.setCLIENT_CODE(cursor.getString(13));
                livraison.setCREATEUR_CODE(cursor.getString(14));
                livraison.setLIVREUR_CODE(cursor.getString(15));
                livraison.setREGION_CODE(cursor.getString(16));
                livraison.setZONE_CODE(cursor.getString(17));
                livraison.setSECTEUR_CODE(cursor.getString(18));
                livraison.setSOUSSECTEUR_CODE(cursor.getString(19));
                livraison.setTOURNEE_CODE(cursor.getString(20));
                livraison.setVISITE_CODE(cursor.getString(21));
                livraison.setSTOCKDEPART_CODE(cursor.getString(22));
                livraison.setSTOCKDESTINATION_CODE(cursor.getString(23));
                livraison.setDESTINATION_CODE(cursor.getString(24));
                livraison.setTS(cursor.getString(25));
                livraison.setMONTANT_BRUT(cursor.getDouble(26));
                livraison.setREMISE(cursor.getDouble(27));
                livraison.setMONTANT_NET(cursor.getDouble(28));
                livraison.setVALEUR_COMMANDE(cursor.getDouble(29));
                livraison.setLITTRAGE_COMMANDE(cursor.getDouble(30));
                livraison.setTONNAGE_COMMANDE(cursor.getDouble(31));
                livraison.setKG_COMMANDE(cursor.getDouble(32));
                livraison.setCOMMENTAIRE(cursor.getString(33));
                livraison.setPAIEMENT_CODE(cursor.getInt(34));
                livraison.setNB_LIGNE(cursor.getInt(35));
                livraison.setCIRCUIT_CODE(cursor.getString(36));
                livraison.setCHANNEL_CODE(cursor.getString(37));
                livraison.setSOURCE(cursor.getString(38));
                livraison.setVERSION(cursor.getString(39));
                livraison.setGPS_LATITUDE(cursor.getString(40));
                livraison.setGPS_LONGITUDE(cursor.getString(41));
                livraison.setDISTANCE(cursor.getInt(42));

                listLivraison.add(livraison);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Livraison from table livraison: ");
        return listLivraison;
    }

    public Livraison getLivByCmdCode(String commande_code) {
        Livraison livraison = new Livraison();

        String selectQuery = "SELECT  * FROM " + TABLE_LIVRAISON +" WHERE "+ KEY_COMMANDE_CODE +" = '"+commande_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {

                livraison.setLIVRAISON_CODE(cursor.getString(0));
                livraison.setLIVRAISON_DATE(cursor.getString(1));
                livraison.setCOMMANDE_CODE(cursor.getString(2));
                livraison.setFACTURE_CODE(cursor.getString(3));
                livraison.setFACTURECLIENT_CODE(cursor.getString(4));
                livraison.setDATE_COMMANDE(cursor.getString(5));
                livraison.setDATE_LIVRAISON((cursor.getString(6)));
                livraison.setDATE_CREATION(cursor.getString(7));
                livraison.setPERIODE_CODE(cursor.getString(8));
                livraison.setCOMMANDETYPE_CODE(cursor.getString(9));
                livraison.setCOMMANDESTATUT_CODE(cursor.getString(10));
                livraison.setDISTRIBUTEUR_CODE(cursor.getString(11));
                livraison.setVENDEUR_CODE(cursor.getString(12));
                livraison.setCLIENT_CODE(cursor.getString(13));
                livraison.setCREATEUR_CODE(cursor.getString(14));
                livraison.setLIVREUR_CODE(cursor.getString(15));
                livraison.setREGION_CODE(cursor.getString(16));
                livraison.setZONE_CODE(cursor.getString(17));
                livraison.setSECTEUR_CODE(cursor.getString(18));
                livraison.setSOUSSECTEUR_CODE(cursor.getString(19));
                livraison.setTOURNEE_CODE(cursor.getString(20));
                livraison.setVISITE_CODE(cursor.getString(21));
                livraison.setSTOCKDEPART_CODE(cursor.getString(22));
                livraison.setSTOCKDESTINATION_CODE(cursor.getString(23));
                livraison.setDESTINATION_CODE(cursor.getString(24));
                livraison.setTS(cursor.getString(25));
                livraison.setMONTANT_BRUT(cursor.getDouble(26));
                livraison.setREMISE(cursor.getDouble(27));
                livraison.setMONTANT_NET(cursor.getDouble(28));
                livraison.setVALEUR_COMMANDE(cursor.getDouble(29));
                livraison.setLITTRAGE_COMMANDE(cursor.getDouble(30));
                livraison.setTONNAGE_COMMANDE(cursor.getDouble(31));
                livraison.setKG_COMMANDE(cursor.getDouble(32));
                livraison.setCOMMENTAIRE(cursor.getString(33));
                livraison.setPAIEMENT_CODE(cursor.getInt(34));
                livraison.setNB_LIGNE(cursor.getInt(35));
                livraison.setCIRCUIT_CODE(cursor.getString(36));
                livraison.setCHANNEL_CODE(cursor.getString(37));
                livraison.setSOURCE(cursor.getString(38));
                livraison.setVERSION(cursor.getString(39));
                livraison.setGPS_LATITUDE(cursor.getString(40));
                livraison.setGPS_LONGITUDE(cursor.getString(41));
                livraison.setDISTANCE(cursor.getInt(42));

        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Livraison from table livraison: ");
        return livraison;
    }


    public ArrayList<Livraison> getListByClientCode(String client_code) {
        ArrayList<Livraison> listLivraison = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LIVRAISON +" WHERE "+ KEY_CLIENT_CODE +" = '"+client_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {

                Livraison livraison = new Livraison();
                livraison.setLIVRAISON_CODE(cursor.getString(0));
                livraison.setLIVRAISON_DATE(cursor.getString(1));
                livraison.setCOMMANDE_CODE(cursor.getString(2));
                livraison.setFACTURE_CODE(cursor.getString(3));
                livraison.setFACTURECLIENT_CODE(cursor.getString(4));
                livraison.setDATE_COMMANDE(cursor.getString(5));
                livraison.setDATE_LIVRAISON((cursor.getString(6)));
                livraison.setDATE_CREATION(cursor.getString(7));
                livraison.setPERIODE_CODE(cursor.getString(8));
                livraison.setCOMMANDETYPE_CODE(cursor.getString(9));
                livraison.setCOMMANDESTATUT_CODE(cursor.getString(10));
                livraison.setDISTRIBUTEUR_CODE(cursor.getString(11));
                livraison.setVENDEUR_CODE(cursor.getString(12));
                livraison.setCLIENT_CODE(cursor.getString(13));
                livraison.setCREATEUR_CODE(cursor.getString(14));
                livraison.setLIVREUR_CODE(cursor.getString(15));
                livraison.setREGION_CODE(cursor.getString(16));
                livraison.setZONE_CODE(cursor.getString(17));
                livraison.setSECTEUR_CODE(cursor.getString(18));
                livraison.setSOUSSECTEUR_CODE(cursor.getString(19));
                livraison.setTOURNEE_CODE(cursor.getString(20));
                livraison.setVISITE_CODE(cursor.getString(21));
                livraison.setSTOCKDEPART_CODE(cursor.getString(22));
                livraison.setSTOCKDESTINATION_CODE(cursor.getString(23));
                livraison.setDESTINATION_CODE(cursor.getString(24));
                livraison.setTS(cursor.getString(25));
                livraison.setMONTANT_BRUT(cursor.getDouble(26));
                livraison.setREMISE(cursor.getDouble(27));
                livraison.setMONTANT_NET(cursor.getDouble(28));
                livraison.setVALEUR_COMMANDE(cursor.getDouble(29));
                livraison.setLITTRAGE_COMMANDE(cursor.getDouble(30));
                livraison.setTONNAGE_COMMANDE(cursor.getDouble(31));
                livraison.setKG_COMMANDE(cursor.getDouble(32));
                livraison.setCOMMENTAIRE(cursor.getString(33));
                livraison.setPAIEMENT_CODE(cursor.getInt(34));
                livraison.setNB_LIGNE(cursor.getInt(35));
                livraison.setCIRCUIT_CODE(cursor.getString(36));
                livraison.setCHANNEL_CODE(cursor.getString(37));
                livraison.setSOURCE(cursor.getString(38));
                livraison.setVERSION(cursor.getString(39));
                livraison.setGPS_LATITUDE(cursor.getString(40));
                livraison.setGPS_LONGITUDE(cursor.getString(41));
                livraison.setDISTANCE(cursor.getInt(42));

                listLivraison.add(livraison);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Livraison from table livraison: ");
        return listLivraison;
    }

    public ArrayList<Livraison> getListByCmdCode(String commande_code) {
        ArrayList<Livraison> listLivraison = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LIVRAISON +" WHERE "+ KEY_COMMANDE_CODE +" = '"+commande_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {

                Livraison livraison = new Livraison();
                livraison.setLIVRAISON_CODE(cursor.getString(0));
                livraison.setLIVRAISON_DATE(cursor.getString(1));
                livraison.setCOMMANDE_CODE(cursor.getString(2));
                livraison.setFACTURE_CODE(cursor.getString(3));
                livraison.setFACTURECLIENT_CODE(cursor.getString(4));
                livraison.setDATE_COMMANDE(cursor.getString(5));
                livraison.setDATE_LIVRAISON((cursor.getString(6)));
                livraison.setDATE_CREATION(cursor.getString(7));
                livraison.setPERIODE_CODE(cursor.getString(8));
                livraison.setCOMMANDETYPE_CODE(cursor.getString(9));
                livraison.setCOMMANDESTATUT_CODE(cursor.getString(10));
                livraison.setDISTRIBUTEUR_CODE(cursor.getString(11));
                livraison.setVENDEUR_CODE(cursor.getString(12));
                livraison.setCLIENT_CODE(cursor.getString(13));
                livraison.setCREATEUR_CODE(cursor.getString(14));
                livraison.setLIVREUR_CODE(cursor.getString(15));
                livraison.setREGION_CODE(cursor.getString(16));
                livraison.setZONE_CODE(cursor.getString(17));
                livraison.setSECTEUR_CODE(cursor.getString(18));
                livraison.setSOUSSECTEUR_CODE(cursor.getString(19));
                livraison.setTOURNEE_CODE(cursor.getString(20));
                livraison.setVISITE_CODE(cursor.getString(21));
                livraison.setSTOCKDEPART_CODE(cursor.getString(22));
                livraison.setSTOCKDESTINATION_CODE(cursor.getString(23));
                livraison.setDESTINATION_CODE(cursor.getString(24));
                livraison.setTS(cursor.getString(25));
                livraison.setMONTANT_BRUT(cursor.getDouble(26));
                livraison.setREMISE(cursor.getDouble(27));
                livraison.setMONTANT_NET(cursor.getDouble(28));
                livraison.setVALEUR_COMMANDE(cursor.getDouble(29));
                livraison.setLITTRAGE_COMMANDE(cursor.getDouble(30));
                livraison.setTONNAGE_COMMANDE(cursor.getDouble(31));
                livraison.setKG_COMMANDE(cursor.getDouble(32));
                livraison.setCOMMENTAIRE(cursor.getString(33));
                livraison.setPAIEMENT_CODE(cursor.getInt(34));
                livraison.setNB_LIGNE(cursor.getInt(35));
                livraison.setCIRCUIT_CODE(cursor.getString(36));
                livraison.setCHANNEL_CODE(cursor.getString(37));
                livraison.setSOURCE(cursor.getString(38));
                livraison.setVERSION(cursor.getString(39));
                livraison.setGPS_LATITUDE(cursor.getString(40));
                livraison.setGPS_LONGITUDE(cursor.getString(41));
                livraison.setDISTANCE(cursor.getInt(42));

                listLivraison.add(livraison);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Livraisons from table livraison: ");
        return listLivraison;
    }

    public ArrayList<Livraison> getListByVisiteCode(String visite_code) {
        ArrayList<Livraison> listLivraison = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LIVRAISON +" WHERE "+ KEY_VISITE_CODE +" = '"+visite_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {

                Livraison livraison = new Livraison();
                livraison.setLIVRAISON_CODE(cursor.getString(0));
                livraison.setLIVRAISON_DATE(cursor.getString(1));
                livraison.setCOMMANDE_CODE(cursor.getString(2));
                livraison.setFACTURE_CODE(cursor.getString(3));
                livraison.setFACTURECLIENT_CODE(cursor.getString(4));
                livraison.setDATE_COMMANDE(cursor.getString(5));
                livraison.setDATE_LIVRAISON((cursor.getString(6)));
                livraison.setDATE_CREATION(cursor.getString(7));
                livraison.setPERIODE_CODE(cursor.getString(8));
                livraison.setCOMMANDETYPE_CODE(cursor.getString(9));
                livraison.setCOMMANDESTATUT_CODE(cursor.getString(10));
                livraison.setDISTRIBUTEUR_CODE(cursor.getString(11));
                livraison.setVENDEUR_CODE(cursor.getString(12));
                livraison.setCLIENT_CODE(cursor.getString(13));
                livraison.setCREATEUR_CODE(cursor.getString(14));
                livraison.setLIVREUR_CODE(cursor.getString(15));
                livraison.setREGION_CODE(cursor.getString(16));
                livraison.setZONE_CODE(cursor.getString(17));
                livraison.setSECTEUR_CODE(cursor.getString(18));
                livraison.setSOUSSECTEUR_CODE(cursor.getString(19));
                livraison.setTOURNEE_CODE(cursor.getString(20));
                livraison.setVISITE_CODE(cursor.getString(21));
                livraison.setSTOCKDEPART_CODE(cursor.getString(22));
                livraison.setSTOCKDESTINATION_CODE(cursor.getString(23));
                livraison.setDESTINATION_CODE(cursor.getString(24));
                livraison.setTS(cursor.getString(25));
                livraison.setMONTANT_BRUT(cursor.getDouble(26));
                livraison.setREMISE(cursor.getDouble(27));
                livraison.setMONTANT_NET(cursor.getDouble(28));
                livraison.setVALEUR_COMMANDE(cursor.getDouble(29));
                livraison.setLITTRAGE_COMMANDE(cursor.getDouble(30));
                livraison.setTONNAGE_COMMANDE(cursor.getDouble(31));
                livraison.setKG_COMMANDE(cursor.getDouble(32));
                livraison.setCOMMENTAIRE(cursor.getString(33));
                livraison.setPAIEMENT_CODE(cursor.getInt(34));
                livraison.setNB_LIGNE(cursor.getInt(35));
                livraison.setCIRCUIT_CODE(cursor.getString(36));
                livraison.setCHANNEL_CODE(cursor.getString(37));
                livraison.setSOURCE(cursor.getString(38));
                livraison.setVERSION(cursor.getString(39));
                livraison.setGPS_LATITUDE(cursor.getString(40));
                livraison.setGPS_LONGITUDE(cursor.getString(41));
                livraison.setDISTANCE(cursor.getInt(42));

                listLivraison.add(livraison);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Livraisons by visite code from table livraison: ");
        return listLivraison;
    }

    public int delete(String livraison_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_LIVRAISON,KEY_LIVRAISON_CODE+"=?",new String[]{livraison_code});
    }

    public void updateLivraison(String livraison_code,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_LIVRAISON + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_LIVRAISON_CODE +"= '"+livraison_code+"'" ;
        db.execSQL(req);
        db.close();
        Log.d("livraison", "updateLivraison: "+req);
    }

    public void deleteLivraisonSynchronisee() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DateLivraison = sdf.format(new Date());
        String Where = " "+KEY_COMMENTAIRE+"='inserted' and "+KEY_COMMANDE_CODE+" not in (SELECT commandenoncloturee.COMMANDE_CODE FROM commandenoncloturee)";

        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_LIVRAISON, Where, null);
        db.close();
    }


    public static void synchronisationLivraison(final Context context){

        LivraisonManager livraisonManager = new LivraisonManager(context);
        livraisonManager.deleteLivraisonSynchronisee();

        String tag_string_req = "LIVRAISON";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_LIVRAISON, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Livraison ", ""+response);
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray livraisons = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre de livraisons  "+livraisons.length() , Toast.LENGTH_LONG).show();
                        // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les Livraisons : " +response);

                        if(livraisons.length()>0) {
                            LivraisonManager livraisonManager = new LivraisonManager(context);
                            for (int i = 0; i < livraisons.length(); i++) {
                                JSONObject uneLivraison = livraisons.getJSONObject(i);
                                if (uneLivraison.getString("Statut").equals("true")) {
                                    livraisonManager.updateLivraison((uneLivraison.getString("LIVRAISON_CODE")),"inserted");
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON: Inserted: "+cptInsert +" NotInserted: "+cptDelete ,"LIVRAISON",1));

                        }

                        //logM.add("Livraison:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncLivraison");
                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "livraison : "+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON: Error: "+errorMsg ,"LIVRAISON",0));

                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "livraison : "+"Json error: " +"erreur applcation livraison" + e.getMessage(), Toast.LENGTH_LONG).show();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON: Error: "+e.getMessage() ,"LIVRAISON",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "livraison : "+error.getMessage(), Toast.LENGTH_LONG).show();
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON: Error: "+error.getMessage() ,"LIVRAISON",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    LivraisonManager livraisonManager  = new LivraisonManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableLivraison");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(livraisonManager.getListNotInserted()));
                    Log.d("Livraisonsend", "liste: "+livraisonManager.getListNotInserted());

                }
                return arrayFinale;
            }
        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public static void synchronisationLivraisonPull(final Context context){

        String tag_string_req = "LIVRAISON_PULL";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_LIVRAISON_PULL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ouvrir le logManager
                Log.d("LivraisonPull ", ""+response);
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray Livraison = jObj.getJSONArray("Livraisons");
                        //Toast.makeText(context, "Nombre de LivraisonPull " +Livraison.length(), Toast.LENGTH_SHORT).show();
                        int cptInsert = 0,cptDeleted = 0;
                        //Ajout/Suppression/modification des articles dans la base de données.
                        for (int i = 0; i < Livraison.length(); i++) {
                            JSONObject uneLivraison = Livraison.getJSONObject(i);
                            LivraisonManager livraisonManager = new LivraisonManager(context);

                            if(uneLivraison.getString("OPERATION").equals("DELETE")){
                                livraisonManager.delete(uneLivraison.getString("LIVRAISON_CODE"));
                                cptDeleted++;
                            }else {
                                Log.d("LivraisonPull", "onResponse: LivraisonPull"+uneLivraison.toString());
                                livraisonManager.add(new Livraison(uneLivraison));
                                cptInsert++;
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON_PULL: Inserted: "+cptInsert +" Deleted: "+cptDeleted ,"LIVRAISON_PULL",1));

                        }

                        //logM.add("LivraisonPull:OK Insert:"+cptInsert +"Deleted:"+cptDeleted ,"SyncuneLivraisonPull");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //logM.add("LivraisonPull :NOK Insert "+errorMsg ,"SyncLivraisonPull");
                        //Toast.makeText(context,"LivraisonPull:"+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON_PULL: Error: "+errorMsg ,"LIVRAISON_PULL",0));

                        }
                    }

                } catch (JSONException e) {
                    //logM.add("LivraisonPull : NOK Insert "+e.getMessage() ,"SyncLivraisonPull");
                    e.printStackTrace();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON_PULL: Error: "+e.getMessage() ,"LIVRAISON_PULL",0));

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "LivraisonPull:"+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("LivraisonPull : NOK Inserted "+error.getMessage() ,"SyncLivraisonPull");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON_PULL: Error: "+error.getMessage() ,"LIVRAISON_PULL",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //Map<String, String> params = new HashMap<String, String>();
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                    String date=df1.format(new Date());


                    LivraisonManager livraisonManager = new LivraisonManager(context);
                    ArrayList<Livraison> livraisons = new ArrayList<>();
                    livraisons=livraisonManager.getList();

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(livraisons));
                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    public double getMontantNet(ArrayList<LivraisonLigne> livraisonLignes){
        double MN=0;

        if(livraisonLignes.size()>0){
            for(int i=0 ; i<livraisonLignes.size(); i++){
                MN+=livraisonLignes.get(i).getMONTANT_NET();
            }
        }
        return MN;
    }

    public double getNumberRounded(double nombre){

        double number = nombre;
        int decimalsToConsider = 2;
        BigDecimal bigDecimal = new BigDecimal(number);
        BigDecimal roundedWithScale = bigDecimal.setScale(decimalsToConsider, BigDecimal.ROUND_HALF_UP);
        //System.out.println("Rounded value with setting scale = "+roundedWithScale);


        return roundedWithScale.doubleValue();
    }

    public Livraison calcDroitTimbre(Livraison livraison){

        Livraison livraison1 = livraison;
        double montant_net = 0;

        montant_net = livraison1.getMONTANT_NET() + (livraison1.getMONTANT_NET()*(0.25/100));
        livraison1.setMONTANT_NET(montant_net);

        return livraison1;
    }

}
