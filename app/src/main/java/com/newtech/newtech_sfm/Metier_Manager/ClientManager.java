package com.newtech.newtech_sfm.Metier_Manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Activity.SyncV2Activity;
import com.newtech.newtech_sfm.Configuration.AppConfig;
import com.newtech.newtech_sfm.Configuration.AppController;
import com.newtech.newtech_sfm.Configuration.SQLiteHandler;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.LogSync;
import com.newtech.newtech_sfm.Metier.Parametre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stagiaireit2 on 19/07/2016.
 */
public class ClientManager extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Client table name
    public static final String TABLE_CLIENT = "client";


    // Login Table Columns names
    private static final String
        KEY_CLIENT_CODE="CLIENT_CODE",
        KEY_CLIENT_NOM="CLIENT_NOM",
        KEY_CLIENT_TELEPHONE1="CLIENT_TELEPHONE1",
        KEY_CLIENT_TELEPHONE2="CLIENT_TELEPHONE2",
        KEY_STATUT_CODE="STATUT_CODE",
        KEY_DISTRIBUTEUR_CODE="DISTRIBUTEUR_CODE",
        KEY_REGION_CODE="REGION_CODE",
        KEY_ZONE_CODE="ZONE_CODE",
        KEY_VILLE_CODE="VILLE_CODE",
        KEY_SECTEUR_CODE="SECTEUR_CODE",
        KEY_SOUSSECTEUR_CODE="SOUSSECTEUR_CODE",
        KEY_TOURNEE_CODE="TOURNEE_CODE",
        KEY_ADRESSE_NR = "ADRESSE_NR",
        KEY_ADRESSE_RUE = "ADRESSE_RUE",
        KEY_ADRESSE_QUARTIER = "ADRESSE_QUARTIER",
        KEY_TYPE_CODE="TYPE_CODE",
        KEY_CATEGORIE_CODE ="CATEGORIE_CODE",
        KEY_GROUPE_CODE="GROUPE_CODE",
        KEY_CLASSE_CODE="CLASSE_CODE",
        KEY_CIRCUIT_CODE = "CIRCUIT_CODE",
        KEY_FAMILLE_CODE="FAMILLE_CODE",
        KEY_RANG="RANG",
        KEY_GPS_LATITUDE="GPS_LATITUDE",
        KEY_GPS_LONGITUDE="GPS_LONGITUDE",
        KEY_MODE_PAIEMENT="MODE_PAIEMENT",
        KEY_POTENTIEL_TONNE="POTENTIEL_TONNE",
        KEY_FREQUENCE_VISITE = "FREQUENCE_VISITE",
        KEY_DATE_CREATION = "DATE_CREATION",
        KEY_CREATEUR_CODE = "CREATEUR_CODE",
        KEY_INACTIF = "INACTIF",
        KEY_INACTIF_RAISON="INACTIF_RAISON",
        KEY_STOCK_CODE = "STOCK_CODE",
        KEY_VERSION="VERSION",
        KEY_IMAGE="IMAGE",
        KEY_LISTEPRIX_CODE="LISTEPRIX_CODE",
        KEY_QR_CODE="QR_CODE";


public static  String CREATE_CLIENT_TABLE = "CREATE TABLE " + TABLE_CLIENT + "("
        +KEY_CLIENT_CODE + " TEXT PRIMARY KEY,"
        + KEY_CLIENT_NOM + " TEXT,"
        + KEY_CLIENT_TELEPHONE1 + " TEXT,"
        + KEY_CLIENT_TELEPHONE2 + " TEXT,"
        + KEY_STATUT_CODE + " NUMERIC,"
        +KEY_DISTRIBUTEUR_CODE + " TEXT,"
        +KEY_REGION_CODE + " TEXT,"
        +KEY_ZONE_CODE + " TEXT,"
        +KEY_VILLE_CODE + " TEXT,"
        +KEY_SECTEUR_CODE + " TEXT,"
        +KEY_SOUSSECTEUR_CODE + " TEXT,"
        +KEY_TOURNEE_CODE + " TEXT,"
        +KEY_ADRESSE_NR + " TEXT,"
        +KEY_ADRESSE_RUE + " TEXT,"
        +KEY_ADRESSE_QUARTIER + " TEXT,"
        +KEY_TYPE_CODE + " TEXT,"
        +KEY_CATEGORIE_CODE + " TEXT,"
        +KEY_GROUPE_CODE + " TEXT,"
        +KEY_CLASSE_CODE + " TEXT,"
        +KEY_CIRCUIT_CODE + " TEXT,"
        +KEY_FAMILLE_CODE + " TEXT,"
        +KEY_RANG + " NUMERIC,"
        +KEY_GPS_LATITUDE + " TEXT,"
        +KEY_GPS_LONGITUDE + " TEXT,"
        +KEY_MODE_PAIEMENT + " TEXT,"
        +KEY_POTENTIEL_TONNE + " TEXT,"
        +KEY_FREQUENCE_VISITE + " TEXT,"
        +KEY_DATE_CREATION + " TEXT,"
        +KEY_CREATEUR_CODE + " TEXT,"
        +KEY_INACTIF + " TEXT,"
        +KEY_INACTIF_RAISON + " TEXT,"
        +KEY_STOCK_CODE + " TEXT,"
        +KEY_VERSION + " TEXT,"
        +KEY_IMAGE + " TEXT,"
        +KEY_LISTEPRIX_CODE + " TEXT,"
        +KEY_QR_CODE+ " INTEGER" +")";

    public ClientManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_CLIENT);
            db.execSQL(CREATE_CLIENT_TABLE);
            Log.d("Client", "onCreate: "+CREATE_CLIENT_TABLE);

        } catch (SQLException e) {
        }
        Log.d(TAG, "Database tables created");
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing Articles details in database
     * */
    public void add(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CLIENT_CODE, client.getCLIENT_CODE());
        values.put(KEY_CLIENT_NOM,client.getCLIENT_NOM());
        values.put(KEY_CLIENT_TELEPHONE1, client.getCLIENT_TELEPHONE1());
        values.put(KEY_CLIENT_TELEPHONE2, client.getCLIENT_TELEPHONE2());
        values.put(KEY_STATUT_CODE, client.getSTATUT_CODE());
        values.put(KEY_DISTRIBUTEUR_CODE,client.getDISTRIBUTEUR_CODE());
        values.put(KEY_REGION_CODE,client.getREGION_CODE());
        values.put(KEY_ZONE_CODE, client.getZONE_CODE());
        values.put(KEY_VILLE_CODE, client.getVILLE_CODE());
        values.put(KEY_SECTEUR_CODE, client.getSECTEUR_CODE());
        values.put(KEY_SOUSSECTEUR_CODE, client.getSOUSSECTEUR_CODE());
        values.put(KEY_TOURNEE_CODE, client.getTOURNEE_CODE());
        values.put(KEY_ADRESSE_NR, client.getADRESSE_NR());
        values.put(KEY_ADRESSE_RUE, client.getADRESSE_RUE());
        values.put(KEY_ADRESSE_QUARTIER, client.getADRESSE_QUARTIER());
        values.put(KEY_TYPE_CODE, client.getTYPE_CODE());
        values.put(KEY_CATEGORIE_CODE, client.getCATEGORIE_CODE());
        values.put(KEY_GROUPE_CODE, client.getCATEGORIE_CODE());
        values.put(KEY_CLASSE_CODE, client.getCLASSE_CODE());
        values.put(KEY_CIRCUIT_CODE, client.getCIRCUIT_CODE());
        values.put(KEY_FAMILLE_CODE,client.getFAMILLE_CODE());
        values.put(KEY_RANG, client.getRANG());
        values.put(KEY_GPS_LATITUDE,client.getGPS_LATITUDE());
        values.put(KEY_GPS_LONGITUDE, client.getGPS_LONGITUDE());
        values.put(KEY_MODE_PAIEMENT, client.getMODE_PAIEMENT());
        values.put(KEY_POTENTIEL_TONNE, client.getPOTENTIEL_TONNE());
        values.put(KEY_FREQUENCE_VISITE, client.getFREQUENCE_VISITE());
        values.put(KEY_DATE_CREATION, client.getDATE_CREATION());
        values.put(KEY_CREATEUR_CODE, client.getCREATEUR_CODE());
        values.put(KEY_INACTIF, client.getINACTIF());
        values.put(KEY_INACTIF_RAISON, client.getINACTIF_RAISON());
        values.put(KEY_STOCK_CODE, client.getSTOCK_CODE());
        values.put(KEY_VERSION, client.getVERSION());
        values.put(KEY_IMAGE, client.getIMAGE());
        values.put(KEY_LISTEPRIX_CODE, client.getLISTEPRIX_CODE());
        values.put(KEY_QR_CODE, client.getQR_CODE());

        //values.put(KEY_LISTEPRIX_CODE, "LISTEPRIX11");
        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_CLIENT, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New Clients inserted into sqlite: " + id);
        Log.d("Client", "add: "+client.getLISTEPRIX_CODE());
    }

    public ArrayList<Client> getClientCode_Version_List() {
        ArrayList<Client> listClient = new ArrayList<>();

        String selectQuery = "SELECT "+ KEY_CLIENT_CODE+","+KEY_VERSION +  " FROM " + TABLE_CLIENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Client cl = new Client();
                cl.setCLIENT_CODE(cursor.getString(0));
                cl.setVERSION(cursor.getString(1));
                listClient.add(cl);
            }while(cursor.moveToNext());
        }
        //returner la listclients;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version Clients from Sqlite: ");
        return listClient;
    }

    /**
     * Getting user data from database
     * */
    public Client get(String client_code) {

        Client client = new Client();
        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT +" WHERE "+KEY_CLIENT_CODE +" = '"+client_code +"' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {


            client.setCLIENT_CODE(cursor.getString(0));
            client.setCLIENT_NOM(cursor.getString(1));
            client.setCLIENT_TELEPHONE1(cursor.getString(2));
            client.setCLIENT_TELEPHONE2(cursor.getString(3));
            client.setSTATUT_CODE(cursor.getInt(4));
            client.setDISTRIBUTEUR_CODE(cursor.getString(5));
            client.setREGION_CODE(cursor.getString(6));
            client.setZONE_CODE(cursor.getString(7));
            client.setVILLE_CODE(cursor.getString(8));
            client.setSECTEUR_CODE(cursor.getString(9));
            client.setSOUSSECTEUR_CODE(cursor.getString(10));
            client.setTOURNEE_CODE(cursor.getString(11));
            client.setADRESSE_NR(cursor.getString(12));
            client.setADRESSE_RUE(cursor.getString(13));
            client.setADRESSE_QUARTIER(cursor.getString(14));
            client.setTYPE_CODE(cursor.getString(15));
            client.setCATEGORIE_CODE(cursor.getString(16));
            client.setGROUPE_CODE(cursor.getString(17));
            client.setCLASSE_CODE(cursor.getString(18));
            client.setCIRCUIT_CODE(cursor.getString(19));
            client.setFAMILLE_CODE(cursor.getString(20));
            client.setRANG(cursor.getInt(21));
            client.setGPS_LATITUDE(cursor.getString(22));
            client.setGPS_LONGITUDE(cursor.getString(23));
            client.setMODE_PAIEMENT(cursor.getString(24));
            client.setPOTENTIEL_TONNE(cursor.getString(25));
            client.setFREQUENCE_VISITE(cursor.getString(26));
            client.setDATE_CREATION(cursor.getString(27));
            client.setINACTIF(cursor.getString(29));
            client.setINACTIF_RAISON(cursor.getString(30));
            client.setSTOCK_CODE(cursor.getString(31));
            client.setVERSION(cursor.getString(32));
            client.setIMAGE(cursor.getString(33));
            client.setLISTEPRIX_CODE(cursor.getString(34));
            client.setQR_CODE(cursor.getInt(35));

        }
        //returner la listclients;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Client from Sqlite: ");
        return client;
    }

    public Client getByQr(int qr_code) {

        Client client = new Client();
        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT +" WHERE "+KEY_QR_CODE +" = "+qr_code;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {


            client.setCLIENT_CODE(cursor.getString(0));
            client.setCLIENT_NOM(cursor.getString(1));
            client.setCLIENT_TELEPHONE1(cursor.getString(2));
            client.setCLIENT_TELEPHONE2(cursor.getString(3));
            client.setSTATUT_CODE(cursor.getInt(4));
            client.setDISTRIBUTEUR_CODE(cursor.getString(5));
            client.setREGION_CODE(cursor.getString(6));
            client.setZONE_CODE(cursor.getString(7));
            client.setVILLE_CODE(cursor.getString(8));
            client.setSECTEUR_CODE(cursor.getString(9));
            client.setSOUSSECTEUR_CODE(cursor.getString(10));
            client.setTOURNEE_CODE(cursor.getString(11));
            client.setADRESSE_NR(cursor.getString(12));
            client.setADRESSE_RUE(cursor.getString(13));
            client.setADRESSE_QUARTIER(cursor.getString(14));
            client.setTYPE_CODE(cursor.getString(15));
            client.setCATEGORIE_CODE(cursor.getString(16));
            client.setGROUPE_CODE(cursor.getString(17));
            client.setCLASSE_CODE(cursor.getString(18));
            client.setCIRCUIT_CODE(cursor.getString(19));
            client.setFAMILLE_CODE(cursor.getString(20));
            client.setRANG(cursor.getInt(21));
            client.setGPS_LATITUDE(cursor.getString(22));
            client.setGPS_LONGITUDE(cursor.getString(23));
            client.setMODE_PAIEMENT(cursor.getString(24));
            client.setPOTENTIEL_TONNE(cursor.getString(25));
            client.setFREQUENCE_VISITE(cursor.getString(26));
            client.setDATE_CREATION(cursor.getString(27));
            client.setINACTIF(cursor.getString(29));
            client.setINACTIF_RAISON(cursor.getString(30));
            client.setSTOCK_CODE(cursor.getString(31));
            client.setVERSION(cursor.getString(32));
            client.setIMAGE(cursor.getString(33));
            client.setLISTEPRIX_CODE(cursor.getString(34));
            client.setQR_CODE(cursor.getInt(35));

        }
        //returner la listclients;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Client from Sqlite: ");
        return client;
    }

    public Client getByCcTc(String client_code,String tournee_code) {

        Client client = new Client();
        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT +" WHERE "+KEY_CLIENT_CODE +" = '"+client_code +"' AND "+KEY_TOURNEE_CODE +" = '"+tournee_code +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {


            client.setCLIENT_CODE(cursor.getString(0));
            client.setCLIENT_NOM(cursor.getString(1));
            client.setCLIENT_TELEPHONE1(cursor.getString(2));
            client.setCLIENT_TELEPHONE2(cursor.getString(3));
            client.setSTATUT_CODE(cursor.getInt(4));
            client.setDISTRIBUTEUR_CODE(cursor.getString(5));
            client.setREGION_CODE(cursor.getString(6));
            client.setZONE_CODE(cursor.getString(7));
            client.setVILLE_CODE(cursor.getString(8));
            client.setSECTEUR_CODE(cursor.getString(9));
            client.setSOUSSECTEUR_CODE(cursor.getString(10));
            client.setTOURNEE_CODE(cursor.getString(11));
            client.setADRESSE_NR(cursor.getString(12));
            client.setADRESSE_RUE(cursor.getString(13));
            client.setADRESSE_QUARTIER(cursor.getString(14));
            client.setTYPE_CODE(cursor.getString(15));
            client.setCATEGORIE_CODE(cursor.getString(16));
            client.setGROUPE_CODE(cursor.getString(17));
            client.setCLASSE_CODE(cursor.getString(18));
            client.setCIRCUIT_CODE(cursor.getString(19));
            client.setFAMILLE_CODE(cursor.getString(20));
            client.setRANG(cursor.getInt(21));
            client.setGPS_LATITUDE(cursor.getString(22));
            client.setGPS_LONGITUDE(cursor.getString(23));
            client.setMODE_PAIEMENT(cursor.getString(24));
            client.setPOTENTIEL_TONNE(cursor.getString(25));
            client.setFREQUENCE_VISITE(cursor.getString(26));
            client.setDATE_CREATION(cursor.getString(27));
            client.setINACTIF(cursor.getString(29));
            client.setINACTIF_RAISON(cursor.getString(30));
            client.setSTOCK_CODE(cursor.getString(31));
            client.setVERSION(cursor.getString(32));
            client.setIMAGE(cursor.getString(33));
            client.setLISTEPRIX_CODE(cursor.getString(34));
            client.setQR_CODE(cursor.getInt(35));

        }
        //returner la listclients;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Client from Sqlite: ");
        return client;
    }

    public Client getByCcQr(int qr_code,String tournee_code) {

        Client client = new Client();
        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT +" WHERE "+KEY_QR_CODE +" = " +qr_code+" AND "+KEY_TOURNEE_CODE +" = '"+tournee_code +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {


            client.setCLIENT_CODE(cursor.getString(0));
            client.setCLIENT_NOM(cursor.getString(1));
            client.setCLIENT_TELEPHONE1(cursor.getString(2));
            client.setCLIENT_TELEPHONE2(cursor.getString(3));
            client.setSTATUT_CODE(cursor.getInt(4));
            client.setDISTRIBUTEUR_CODE(cursor.getString(5));
            client.setREGION_CODE(cursor.getString(6));
            client.setZONE_CODE(cursor.getString(7));
            client.setVILLE_CODE(cursor.getString(8));
            client.setSECTEUR_CODE(cursor.getString(9));
            client.setSOUSSECTEUR_CODE(cursor.getString(10));
            client.setTOURNEE_CODE(cursor.getString(11));
            client.setADRESSE_NR(cursor.getString(12));
            client.setADRESSE_RUE(cursor.getString(13));
            client.setADRESSE_QUARTIER(cursor.getString(14));
            client.setTYPE_CODE(cursor.getString(15));
            client.setCATEGORIE_CODE(cursor.getString(16));
            client.setGROUPE_CODE(cursor.getString(17));
            client.setCLASSE_CODE(cursor.getString(18));
            client.setCIRCUIT_CODE(cursor.getString(19));
            client.setFAMILLE_CODE(cursor.getString(20));
            client.setRANG(cursor.getInt(21));
            client.setGPS_LATITUDE(cursor.getString(22));
            client.setGPS_LONGITUDE(cursor.getString(23));
            client.setMODE_PAIEMENT(cursor.getString(24));
            client.setPOTENTIEL_TONNE(cursor.getString(25));
            client.setFREQUENCE_VISITE(cursor.getString(26));
            client.setDATE_CREATION(cursor.getString(27));
            client.setINACTIF(cursor.getString(29));
            client.setINACTIF_RAISON(cursor.getString(30));
            client.setSTOCK_CODE(cursor.getString(31));
            client.setVERSION(cursor.getString(32));
            client.setIMAGE(cursor.getString(33));
            client.setLISTEPRIX_CODE(cursor.getString(34));
            client.setQR_CODE(cursor.getInt(35));

        }
        //returner la listclients;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Client from Sqlite: ");
        return client;
    }

    /**
     * Getting user data from database
     * */
    public ArrayList<Client> getList() {
        ArrayList<Client> listClient = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Client cl = new Client();
                cl.setCLIENT_CODE(cursor.getString(0));
                cl.setCLIENT_NOM(cursor.getString(1));
                cl.setCLIENT_TELEPHONE1(cursor.getString(2));
                cl.setCLIENT_TELEPHONE2(cursor.getString(3));
                cl.setSTATUT_CODE(cursor.getInt(4));
                cl.setDISTRIBUTEUR_CODE(cursor.getString(5));
                cl.setREGION_CODE(cursor.getString(6));
                cl.setZONE_CODE(cursor.getString(7));
                cl.setVILLE_CODE(cursor.getString(8));
                cl.setSECTEUR_CODE(cursor.getString(9));
                cl.setSOUSSECTEUR_CODE(cursor.getString(10));
                cl.setTOURNEE_CODE(cursor.getString(11));
                cl.setADRESSE_NR(cursor.getString(12));
                cl.setADRESSE_RUE(cursor.getString(13));
                cl.setADRESSE_QUARTIER(cursor.getString(14));
                cl.setTYPE_CODE(cursor.getString(15));
                cl.setCATEGORIE_CODE(cursor.getString(16));
                cl.setGROUPE_CODE(cursor.getString(17));
                cl.setCLASSE_CODE(cursor.getString(18));
                cl.setCIRCUIT_CODE(cursor.getString(19));
                cl.setFAMILLE_CODE(cursor.getString(20));
                cl.setRANG(cursor.getInt(21));
                cl.setGPS_LATITUDE(cursor.getString(22));
                cl.setGPS_LONGITUDE(cursor.getString(23));
                cl.setMODE_PAIEMENT(cursor.getString(24));
                cl.setPOTENTIEL_TONNE(cursor.getString(25));
                cl.setFREQUENCE_VISITE(cursor.getString(26));
                cl.setDATE_CREATION(cursor.getString(27));
                cl.setINACTIF(cursor.getString(29));
                cl.setINACTIF_RAISON(cursor.getString(30));
                cl.setSTOCK_CODE(cursor.getString(31));
                cl.setVERSION(cursor.getString(32));
                cl.setIMAGE(cursor.getString(33));
                cl.setLISTEPRIX_CODE(cursor.getString(34));
                cl.setQR_CODE(cursor.getInt(35));
                Log.d("Client", "Fetching Clients from Sqlite: listeprix"+cursor.getString(34));
                listClient.add(cl);
            }while(cursor.moveToNext());
        }
        //returner la listclients;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Clients from Sqlite: ");

        return listClient;
    }


    /*
     * Re crate database Delete all tables and create them again
     */
    public ArrayList<Client> getListByTourneCode(String TourCode) {
        ArrayList<Client> listClient = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT +" WHERE "+KEY_TOURNEE_CODE +" = '"+TourCode +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Client cl = new Client();
                cl.setCLIENT_CODE(cursor.getString(0));
                cl.setCLIENT_NOM(cursor.getString(1));
                cl.setCLIENT_TELEPHONE1(cursor.getString(2));
                cl.setCLIENT_TELEPHONE2(cursor.getString(3));
                cl.setSTATUT_CODE(cursor.getInt(4));
                cl.setDISTRIBUTEUR_CODE(cursor.getString(5));
                cl.setREGION_CODE(cursor.getString(6));
                cl.setZONE_CODE(cursor.getString(7));
                cl.setVILLE_CODE(cursor.getString(8));
                cl.setSECTEUR_CODE(cursor.getString(9));
                cl.setSOUSSECTEUR_CODE(cursor.getString(10));
                cl.setTOURNEE_CODE(cursor.getString(11));
                cl.setADRESSE_NR(cursor.getString(12));
                cl.setADRESSE_RUE(cursor.getString(13));
                cl.setADRESSE_QUARTIER(cursor.getString(14));
                cl.setTYPE_CODE(cursor.getString(15));
                cl.setCATEGORIE_CODE(cursor.getString(16));
                cl.setGROUPE_CODE(cursor.getString(17));
                cl.setCLASSE_CODE(cursor.getString(18));
                cl.setCIRCUIT_CODE(cursor.getString(19));
                cl.setFAMILLE_CODE(cursor.getString(20));
                cl.setRANG(cursor.getInt(21));
                cl.setGPS_LATITUDE(cursor.getString(22));
                cl.setGPS_LONGITUDE(cursor.getString(23));
                cl.setMODE_PAIEMENT(cursor.getString(24));
                cl.setPOTENTIEL_TONNE(cursor.getString(25));
                cl.setFREQUENCE_VISITE(cursor.getString(26));
                cl.setDATE_CREATION(cursor.getString(27));
                cl.setINACTIF(cursor.getString(29));
                cl.setINACTIF_RAISON(cursor.getString(30));
                cl.setSTOCK_CODE(cursor.getString(31));
                cl.setVERSION(cursor.getString(32));
                cl.setIMAGE(cursor.getString(33));
                cl.setLISTEPRIX_CODE(cursor.getString(34));
                cl.setQR_CODE(cursor.getInt(35));


                listClient.add(cl);
            }while(cursor.moveToNext());
        }
        //returner la listclients;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Clients from Sqlite: ");
        return listClient;
    }

    public ArrayList<Client> getListFiltered(String chaine_tournee,String chaine_type,String chaine_classe, String chaine_categorie) {
        ArrayList<Client> listClient = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT +" WHERE "+ KEY_CLIENT_CODE +" != ''";
        String selectTournee = " AND "+KEY_TOURNEE_CODE+" IN ("+chaine_tournee+")";
        String selectType = " AND "+KEY_TYPE_CODE+" IN ("+chaine_type+")";
        String selectClasse = " AND "+KEY_CLASSE_CODE+" IN ("+chaine_classe+")";
        String selectCategorie = "AND "+KEY_CATEGORIE_CODE+" IN ("+chaine_categorie+")";

        if(chaine_tournee != "" && chaine_tournee != "tous"){
            selectQuery+=selectTournee;
        }
        if(chaine_type != ""){
            selectQuery+=selectType;
        }
        if(chaine_classe != ""){
            selectQuery+=selectClasse;
        }
        if(chaine_categorie != ""){
            selectQuery+=selectCategorie;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Client cl = new Client();
                cl.setCLIENT_CODE(cursor.getString(0));
                cl.setCLIENT_NOM(cursor.getString(1));
                cl.setCLIENT_TELEPHONE1(cursor.getString(2));
                cl.setCLIENT_TELEPHONE2(cursor.getString(3));
                cl.setSTATUT_CODE(cursor.getInt(4));
                cl.setDISTRIBUTEUR_CODE(cursor.getString(5));
                cl.setREGION_CODE(cursor.getString(6));
                cl.setZONE_CODE(cursor.getString(7));
                cl.setVILLE_CODE(cursor.getString(8));
                cl.setSECTEUR_CODE(cursor.getString(9));
                cl.setSOUSSECTEUR_CODE(cursor.getString(10));
                cl.setTOURNEE_CODE(cursor.getString(11));
                cl.setADRESSE_NR(cursor.getString(12));
                cl.setADRESSE_RUE(cursor.getString(13));
                cl.setADRESSE_QUARTIER(cursor.getString(14));
                cl.setTYPE_CODE(cursor.getString(15));
                cl.setCATEGORIE_CODE(cursor.getString(16));
                cl.setGROUPE_CODE(cursor.getString(17));
                cl.setCLASSE_CODE(cursor.getString(18));
                cl.setCIRCUIT_CODE(cursor.getString(19));
                cl.setFAMILLE_CODE(cursor.getString(20));
                cl.setRANG(cursor.getInt(21));
                cl.setGPS_LATITUDE(cursor.getString(22));
                cl.setGPS_LONGITUDE(cursor.getString(23));
                cl.setMODE_PAIEMENT(cursor.getString(24));
                cl.setPOTENTIEL_TONNE(cursor.getString(25));
                cl.setFREQUENCE_VISITE(cursor.getString(26));
                cl.setDATE_CREATION(cursor.getString(27));
                cl.setINACTIF(cursor.getString(29));
                cl.setINACTIF_RAISON(cursor.getString(30));
                cl.setSTOCK_CODE(cursor.getString(31));
                cl.setVERSION(cursor.getString(32));
                cl.setIMAGE(cursor.getString(33));
                cl.setLISTEPRIX_CODE(cursor.getString(34));
                cl.setQR_CODE(cursor.getInt(35));


                listClient.add(cl);
            }while(cursor.moveToNext());
        }
        //returner la listclients;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Clients from Sqlite: ");
        return listClient;
    }

    public ArrayList<Client> getListFilteredWithoutVisite(String chaine_tournee,String chaine_type,String chaine_classe, String chaine_categorie,String date_visite) {
        ArrayList<Client> listClient = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT +" WHERE "+ KEY_CLIENT_CODE +" != ''";
        String selectTournee = " AND "+KEY_TOURNEE_CODE+" IN ("+chaine_tournee+")";
        String selectType = " AND "+KEY_TYPE_CODE+" IN ("+chaine_type+")";
        String selectClasse = " AND "+KEY_CLASSE_CODE+" IN ("+chaine_classe+")";
        String selectCategorie = "AND "+KEY_CATEGORIE_CODE+" IN ("+chaine_categorie+")";

        if(chaine_tournee != "" && chaine_tournee != "tous"){
            selectQuery+=selectTournee;
        }
        if(chaine_type != ""){
            selectQuery+=selectType;
        }
        if(chaine_classe != ""){
            selectQuery+=selectClasse;
        }
        if(chaine_categorie != ""){
            selectQuery+=selectCategorie;
        }

        selectQuery += "AND " + KEY_CLIENT_CODE +" NOT IN (SELECT CLIENT_CODE FROM visite WHERE DATE_VISITE = '"+date_visite+"' AND VISITE_RESULTAT != 0)";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Client cl = new Client();
                cl.setCLIENT_CODE(cursor.getString(0));
                cl.setCLIENT_NOM(cursor.getString(1));
                cl.setCLIENT_TELEPHONE1(cursor.getString(2));
                cl.setCLIENT_TELEPHONE2(cursor.getString(3));
                cl.setSTATUT_CODE(cursor.getInt(4));
                cl.setDISTRIBUTEUR_CODE(cursor.getString(5));
                cl.setREGION_CODE(cursor.getString(6));
                cl.setZONE_CODE(cursor.getString(7));
                cl.setVILLE_CODE(cursor.getString(8));
                cl.setSECTEUR_CODE(cursor.getString(9));
                cl.setSOUSSECTEUR_CODE(cursor.getString(10));
                cl.setTOURNEE_CODE(cursor.getString(11));
                cl.setADRESSE_NR(cursor.getString(12));
                cl.setADRESSE_RUE(cursor.getString(13));
                cl.setADRESSE_QUARTIER(cursor.getString(14));
                cl.setTYPE_CODE(cursor.getString(15));
                cl.setCATEGORIE_CODE(cursor.getString(16));
                cl.setGROUPE_CODE(cursor.getString(17));
                cl.setCLASSE_CODE(cursor.getString(18));
                cl.setCIRCUIT_CODE(cursor.getString(19));
                cl.setFAMILLE_CODE(cursor.getString(20));
                cl.setRANG(cursor.getInt(21));
                cl.setGPS_LATITUDE(cursor.getString(22));
                cl.setGPS_LONGITUDE(cursor.getString(23));
                cl.setMODE_PAIEMENT(cursor.getString(24));
                cl.setPOTENTIEL_TONNE(cursor.getString(25));
                cl.setFREQUENCE_VISITE(cursor.getString(26));
                cl.setDATE_CREATION(cursor.getString(27));
                cl.setINACTIF(cursor.getString(29));
                cl.setINACTIF_RAISON(cursor.getString(30));
                cl.setSTOCK_CODE(cursor.getString(31));
                cl.setVERSION(cursor.getString(32));
                cl.setIMAGE(cursor.getString(33));
                cl.setLISTEPRIX_CODE(cursor.getString(34));
                cl.setQR_CODE(cursor.getInt(35));


                listClient.add(cl);
            }while(cursor.moveToNext());
        }
        //returner la listclients;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Clients from Sqlite: ");
        return listClient;
    }

    public ArrayList<Client> getListByCmdNCAL() {
        ArrayList<Client> listClient = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT +" INNER JOIN commandenonclotureeal ON commandenonclotureeal.CLIENT_CODE = client.CLIENT_CODE group by client.CLIENT_CODE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Client cl = new Client();
                cl.setCLIENT_CODE(cursor.getString(0));
                cl.setCLIENT_NOM(cursor.getString(1));
                cl.setCLIENT_TELEPHONE1(cursor.getString(2));
                cl.setCLIENT_TELEPHONE2(cursor.getString(3));
                cl.setSTATUT_CODE(cursor.getInt(4));
                cl.setDISTRIBUTEUR_CODE(cursor.getString(5));
                cl.setREGION_CODE(cursor.getString(6));
                cl.setZONE_CODE(cursor.getString(7));
                cl.setVILLE_CODE(cursor.getString(8));
                cl.setSECTEUR_CODE(cursor.getString(9));
                cl.setSOUSSECTEUR_CODE(cursor.getString(10));
                cl.setTOURNEE_CODE(cursor.getString(11));
                cl.setADRESSE_NR(cursor.getString(12));
                cl.setADRESSE_RUE(cursor.getString(13));
                cl.setADRESSE_QUARTIER(cursor.getString(14));
                cl.setTYPE_CODE(cursor.getString(15));
                cl.setCATEGORIE_CODE(cursor.getString(16));
                cl.setGROUPE_CODE(cursor.getString(17));
                cl.setCLASSE_CODE(cursor.getString(18));
                cl.setCIRCUIT_CODE(cursor.getString(19));
                cl.setFAMILLE_CODE(cursor.getString(20));
                cl.setRANG(cursor.getInt(21));
                cl.setGPS_LATITUDE(cursor.getString(22));
                cl.setGPS_LONGITUDE(cursor.getString(23));
                cl.setMODE_PAIEMENT(cursor.getString(24));
                cl.setPOTENTIEL_TONNE(cursor.getString(25));
                cl.setFREQUENCE_VISITE(cursor.getString(26));
                cl.setDATE_CREATION(cursor.getString(27));
                cl.setINACTIF(cursor.getString(29));
                cl.setINACTIF_RAISON(cursor.getString(30));
                cl.setSTOCK_CODE(cursor.getString(31));
                cl.setVERSION(cursor.getString(32));
                cl.setIMAGE(cursor.getString(33));
                cl.setLISTEPRIX_CODE(cursor.getString(34));
                cl.setQR_CODE(cursor.getInt(35));


                listClient.add(cl);
            }while(cursor.moveToNext());
        }
        //returner la listclients;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Clients from Sqlite: "+listClient);
        Log.d(TAG, "getListClientByCmdNCAL: "+selectQuery);
        return listClient;
    }

    public ArrayList<Client> getListClNLByVC(String vendeur_code) { //La liste des clients avec des commande non cloturées à livrer par vendeur code
        ArrayList<Client> listClient = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT +" INNER JOIN commandenonclotureeal ON commandenonclotureeal.CLIENT_CODE = client.CLIENT_CODE WHERE commandenonclotureeal.VENDEUR_CODE "+" = '"+vendeur_code+"' group by client.CLIENT_CODE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Client cl = new Client();
                cl.setCLIENT_CODE(cursor.getString(0));
                cl.setCLIENT_NOM(cursor.getString(1));
                cl.setCLIENT_TELEPHONE1(cursor.getString(2));
                cl.setCLIENT_TELEPHONE2(cursor.getString(3));
                cl.setSTATUT_CODE(cursor.getInt(4));
                cl.setDISTRIBUTEUR_CODE(cursor.getString(5));
                cl.setREGION_CODE(cursor.getString(6));
                cl.setZONE_CODE(cursor.getString(7));
                cl.setVILLE_CODE(cursor.getString(8));
                cl.setSECTEUR_CODE(cursor.getString(9));
                cl.setSOUSSECTEUR_CODE(cursor.getString(10));
                cl.setTOURNEE_CODE(cursor.getString(11));
                cl.setADRESSE_NR(cursor.getString(12));
                cl.setADRESSE_RUE(cursor.getString(13));
                cl.setADRESSE_QUARTIER(cursor.getString(14));
                cl.setTYPE_CODE(cursor.getString(15));
                cl.setCATEGORIE_CODE(cursor.getString(16));
                cl.setGROUPE_CODE(cursor.getString(17));
                cl.setCLASSE_CODE(cursor.getString(18));
                cl.setCIRCUIT_CODE(cursor.getString(19));
                cl.setFAMILLE_CODE(cursor.getString(20));
                cl.setRANG(cursor.getInt(21));
                cl.setGPS_LATITUDE(cursor.getString(22));
                cl.setGPS_LONGITUDE(cursor.getString(23));
                cl.setMODE_PAIEMENT(cursor.getString(24));
                cl.setPOTENTIEL_TONNE(cursor.getString(25));
                cl.setFREQUENCE_VISITE(cursor.getString(26));
                cl.setDATE_CREATION(cursor.getString(27));
                cl.setINACTIF(cursor.getString(29));
                cl.setINACTIF_RAISON(cursor.getString(30));
                cl.setSTOCK_CODE(cursor.getString(31));
                cl.setVERSION(cursor.getString(32));
                cl.setIMAGE(cursor.getString(33));
                cl.setLISTEPRIX_CODE(cursor.getString(34));
                cl.setQR_CODE(cursor.getInt(35));


                listClient.add(cl);
            }while(cursor.moveToNext());
        }
        //returner la listclients;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Clients from Sqlite: "+listClient);
        Log.d(TAG, "getListClNLByVC: "+selectQuery);
        return listClient;
    }

    public ArrayList<Client> getListClNLByVCD(String vendeur_code, String date) { //La liste des clients avec des commande non cloturées à livrer par vendeur code
        ArrayList<Client> listClient = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT +" INNER JOIN commandenonclotureeal ON commandenonclotureeal.CLIENT_CODE = client.CLIENT_CODE WHERE commandenonclotureeal.VENDEUR_CODE "+" = '"+vendeur_code+"' and commandenonclotureeal.DATE_COMMANDE "+" = '"+date+"' group by client.CLIENT_CODE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Client cl = new Client();
                cl.setCLIENT_CODE(cursor.getString(0));
                cl.setCLIENT_NOM(cursor.getString(1));
                cl.setCLIENT_TELEPHONE1(cursor.getString(2));
                cl.setCLIENT_TELEPHONE2(cursor.getString(3));
                cl.setSTATUT_CODE(cursor.getInt(4));
                cl.setDISTRIBUTEUR_CODE(cursor.getString(5));
                cl.setREGION_CODE(cursor.getString(6));
                cl.setZONE_CODE(cursor.getString(7));
                cl.setVILLE_CODE(cursor.getString(8));
                cl.setSECTEUR_CODE(cursor.getString(9));
                cl.setSOUSSECTEUR_CODE(cursor.getString(10));
                cl.setTOURNEE_CODE(cursor.getString(11));
                cl.setADRESSE_NR(cursor.getString(12));
                cl.setADRESSE_RUE(cursor.getString(13));
                cl.setADRESSE_QUARTIER(cursor.getString(14));
                cl.setTYPE_CODE(cursor.getString(15));
                cl.setCATEGORIE_CODE(cursor.getString(16));
                cl.setGROUPE_CODE(cursor.getString(17));
                cl.setCLASSE_CODE(cursor.getString(18));
                cl.setCIRCUIT_CODE(cursor.getString(19));
                cl.setFAMILLE_CODE(cursor.getString(20));
                cl.setRANG(cursor.getInt(21));
                cl.setGPS_LATITUDE(cursor.getString(22));
                cl.setGPS_LONGITUDE(cursor.getString(23));
                cl.setMODE_PAIEMENT(cursor.getString(24));
                cl.setPOTENTIEL_TONNE(cursor.getString(25));
                cl.setFREQUENCE_VISITE(cursor.getString(26));
                cl.setDATE_CREATION(cursor.getString(27));
                cl.setINACTIF(cursor.getString(29));
                cl.setINACTIF_RAISON(cursor.getString(30));
                cl.setSTOCK_CODE(cursor.getString(31));
                cl.setVERSION(cursor.getString(32));
                cl.setIMAGE(cursor.getString(33));
                cl.setLISTEPRIX_CODE(cursor.getString(34));
                cl.setQR_CODE(cursor.getInt(35));


                listClient.add(cl);
            }while(cursor.moveToNext());
        }
        //returner la listclients;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Clients from Sqlite: "+listClient);
        Log.d(TAG, "getListClNLByVC: "+selectQuery);
        return listClient;
    }


    public ArrayList<Client> getListClNLByTCD(String tournee_code, String date) { //La liste des clients avec des commande non cloturées à livrer par tournee code
        ArrayList<Client> listClient = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT +" INNER JOIN commandenonclotureeal ON commandenonclotureeal.CLIENT_CODE = client.CLIENT_CODE where commandenonclotureeal.TOURNEE_CODE "+" = '"+tournee_code+"' and commandenonclotureeal.DATE_COMMANDE "+" = '"+date+"' group by client.CLIENT_CODE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Client cl = new Client();
                cl.setCLIENT_CODE(cursor.getString(0));
                cl.setCLIENT_NOM(cursor.getString(1));
                cl.setCLIENT_TELEPHONE1(cursor.getString(2));
                cl.setCLIENT_TELEPHONE2(cursor.getString(3));
                cl.setSTATUT_CODE(cursor.getInt(4));
                cl.setDISTRIBUTEUR_CODE(cursor.getString(5));
                cl.setREGION_CODE(cursor.getString(6));
                cl.setZONE_CODE(cursor.getString(7));
                cl.setVILLE_CODE(cursor.getString(8));
                cl.setSECTEUR_CODE(cursor.getString(9));
                cl.setSOUSSECTEUR_CODE(cursor.getString(10));
                cl.setTOURNEE_CODE(cursor.getString(11));
                cl.setADRESSE_NR(cursor.getString(12));
                cl.setADRESSE_RUE(cursor.getString(13));
                cl.setADRESSE_QUARTIER(cursor.getString(14));
                cl.setTYPE_CODE(cursor.getString(15));
                cl.setCATEGORIE_CODE(cursor.getString(16));
                cl.setGROUPE_CODE(cursor.getString(17));
                cl.setCLASSE_CODE(cursor.getString(18));
                cl.setCIRCUIT_CODE(cursor.getString(19));
                cl.setFAMILLE_CODE(cursor.getString(20));
                cl.setRANG(cursor.getInt(21));
                cl.setGPS_LATITUDE(cursor.getString(22));
                cl.setGPS_LONGITUDE(cursor.getString(23));
                cl.setMODE_PAIEMENT(cursor.getString(24));
                cl.setPOTENTIEL_TONNE(cursor.getString(25));
                cl.setFREQUENCE_VISITE(cursor.getString(26));
                cl.setDATE_CREATION(cursor.getString(27));
                cl.setINACTIF(cursor.getString(29));
                cl.setINACTIF_RAISON(cursor.getString(30));
                cl.setSTOCK_CODE(cursor.getString(31));
                cl.setVERSION(cursor.getString(32));
                cl.setIMAGE(cursor.getString(33));
                cl.setLISTEPRIX_CODE(cursor.getString(34));
                cl.setQR_CODE(cursor.getInt(35));


                listClient.add(cl);
            }while(cursor.moveToNext());
        }
        //returner la listclients;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Clients from Sqlite: "+listClient);
        Log.d(TAG, "getListClNLByTC: "+selectQuery);
        return listClient;
    }

    public ArrayList<Client> getListClNLByTC(String tournee_code) { //La liste des clients avec des commande non cloturées à livrer par tournee code
        ArrayList<Client> listClient = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT +" INNER JOIN commandenonclotureeal ON commandenonclotureeal.CLIENT_CODE = client.CLIENT_CODE where commandenonclotureeal.TOURNEE_CODE "+" = '"+tournee_code+"' group by client.CLIENT_CODE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Client cl = new Client();
                cl.setCLIENT_CODE(cursor.getString(0));
                cl.setCLIENT_NOM(cursor.getString(1));
                cl.setCLIENT_TELEPHONE1(cursor.getString(2));
                cl.setCLIENT_TELEPHONE2(cursor.getString(3));
                cl.setSTATUT_CODE(cursor.getInt(4));
                cl.setDISTRIBUTEUR_CODE(cursor.getString(5));
                cl.setREGION_CODE(cursor.getString(6));
                cl.setZONE_CODE(cursor.getString(7));
                cl.setVILLE_CODE(cursor.getString(8));
                cl.setSECTEUR_CODE(cursor.getString(9));
                cl.setSOUSSECTEUR_CODE(cursor.getString(10));
                cl.setTOURNEE_CODE(cursor.getString(11));
                cl.setADRESSE_NR(cursor.getString(12));
                cl.setADRESSE_RUE(cursor.getString(13));
                cl.setADRESSE_QUARTIER(cursor.getString(14));
                cl.setTYPE_CODE(cursor.getString(15));
                cl.setCATEGORIE_CODE(cursor.getString(16));
                cl.setGROUPE_CODE(cursor.getString(17));
                cl.setCLASSE_CODE(cursor.getString(18));
                cl.setCIRCUIT_CODE(cursor.getString(19));
                cl.setFAMILLE_CODE(cursor.getString(20));
                cl.setRANG(cursor.getInt(21));
                cl.setGPS_LATITUDE(cursor.getString(22));
                cl.setGPS_LONGITUDE(cursor.getString(23));
                cl.setMODE_PAIEMENT(cursor.getString(24));
                cl.setPOTENTIEL_TONNE(cursor.getString(25));
                cl.setFREQUENCE_VISITE(cursor.getString(26));
                cl.setDATE_CREATION(cursor.getString(27));
                cl.setINACTIF(cursor.getString(29));
                cl.setINACTIF_RAISON(cursor.getString(30));
                cl.setSTOCK_CODE(cursor.getString(31));
                cl.setVERSION(cursor.getString(32));
                cl.setIMAGE(cursor.getString(33));
                cl.setLISTEPRIX_CODE(cursor.getString(34));
                cl.setQR_CODE(cursor.getInt(35));


                listClient.add(cl);
            }while(cursor.moveToNext());
        }
        //returner la listclients;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Clients from Sqlite: "+listClient);
        Log.d(TAG, "getListClNLByTC: "+selectQuery);
        return listClient;
    }

    public ArrayList<Client> getListClNEByVC(String vendeur_code) { //La liste des clients avec des commande non cloturées à encaisser par vendeur code
        ArrayList<Client> listClient = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT +" INNER JOIN commandenonclotureeae ON commandenonclotureeae.CLIENT_CODE = client.CLIENT_CODE where commandenonclotureeae.VENDEUR_CODE "+" = '"+vendeur_code+"' group by client.CLIENT_CODE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Client cl = new Client();
                cl.setCLIENT_CODE(cursor.getString(0));
                cl.setCLIENT_NOM(cursor.getString(1));
                cl.setCLIENT_TELEPHONE1(cursor.getString(2));
                cl.setCLIENT_TELEPHONE2(cursor.getString(3));
                cl.setSTATUT_CODE(cursor.getInt(4));
                cl.setDISTRIBUTEUR_CODE(cursor.getString(5));
                cl.setREGION_CODE(cursor.getString(6));
                cl.setZONE_CODE(cursor.getString(7));
                cl.setVILLE_CODE(cursor.getString(8));
                cl.setSECTEUR_CODE(cursor.getString(9));
                cl.setSOUSSECTEUR_CODE(cursor.getString(10));
                cl.setTOURNEE_CODE(cursor.getString(11));
                cl.setADRESSE_NR(cursor.getString(12));
                cl.setADRESSE_RUE(cursor.getString(13));
                cl.setADRESSE_QUARTIER(cursor.getString(14));
                cl.setTYPE_CODE(cursor.getString(15));
                cl.setCATEGORIE_CODE(cursor.getString(16));
                cl.setGROUPE_CODE(cursor.getString(17));
                cl.setCLASSE_CODE(cursor.getString(18));
                cl.setCIRCUIT_CODE(cursor.getString(19));
                cl.setFAMILLE_CODE(cursor.getString(20));
                cl.setRANG(cursor.getInt(21));
                cl.setGPS_LATITUDE(cursor.getString(22));
                cl.setGPS_LONGITUDE(cursor.getString(23));
                cl.setMODE_PAIEMENT(cursor.getString(24));
                cl.setPOTENTIEL_TONNE(cursor.getString(25));
                cl.setFREQUENCE_VISITE(cursor.getString(26));
                cl.setDATE_CREATION(cursor.getString(27));
                cl.setINACTIF(cursor.getString(29));
                cl.setINACTIF_RAISON(cursor.getString(30));
                cl.setSTOCK_CODE(cursor.getString(31));
                cl.setVERSION(cursor.getString(32));
                cl.setIMAGE(cursor.getString(33));
                cl.setLISTEPRIX_CODE(cursor.getString(34));
                cl.setQR_CODE(cursor.getInt(35));


                listClient.add(cl);
            }while(cursor.moveToNext());
        }
        //returner la listclients;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Clients from Sqlite: "+listClient);
        Log.d(TAG, "getListClNEByVC: "+selectQuery);
        return listClient;
    }

    public ArrayList<Client> getListClNEByTC(String tournee_code) { //La liste des clients avec des commande non cloturées à encaisser par tournee code
        ArrayList<Client> listClient = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT +" INNER JOIN commandenonclotureeae ON commandenonclotureeae.CLIENT_CODE = client.CLIENT_CODE where commandenonclotureeae.TOURNEE_CODE "+" = '"+tournee_code+"' group by client.CLIENT_CODE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Client cl = new Client();
                cl.setCLIENT_CODE(cursor.getString(0));
                cl.setCLIENT_NOM(cursor.getString(1));
                cl.setCLIENT_TELEPHONE1(cursor.getString(2));
                cl.setCLIENT_TELEPHONE2(cursor.getString(3));
                cl.setSTATUT_CODE(cursor.getInt(4));
                cl.setDISTRIBUTEUR_CODE(cursor.getString(5));
                cl.setREGION_CODE(cursor.getString(6));
                cl.setZONE_CODE(cursor.getString(7));
                cl.setVILLE_CODE(cursor.getString(8));
                cl.setSECTEUR_CODE(cursor.getString(9));
                cl.setSOUSSECTEUR_CODE(cursor.getString(10));
                cl.setTOURNEE_CODE(cursor.getString(11));
                cl.setADRESSE_NR(cursor.getString(12));
                cl.setADRESSE_RUE(cursor.getString(13));
                cl.setADRESSE_QUARTIER(cursor.getString(14));
                cl.setTYPE_CODE(cursor.getString(15));
                cl.setCATEGORIE_CODE(cursor.getString(16));
                cl.setGROUPE_CODE(cursor.getString(17));
                cl.setCLASSE_CODE(cursor.getString(18));
                cl.setCIRCUIT_CODE(cursor.getString(19));
                cl.setFAMILLE_CODE(cursor.getString(20));
                cl.setRANG(cursor.getInt(21));
                cl.setGPS_LATITUDE(cursor.getString(22));
                cl.setGPS_LONGITUDE(cursor.getString(23));
                cl.setMODE_PAIEMENT(cursor.getString(24));
                cl.setPOTENTIEL_TONNE(cursor.getString(25));
                cl.setFREQUENCE_VISITE(cursor.getString(26));
                cl.setDATE_CREATION(cursor.getString(27));
                cl.setINACTIF(cursor.getString(29));
                cl.setINACTIF_RAISON(cursor.getString(30));
                cl.setSTOCK_CODE(cursor.getString(31));
                cl.setVERSION(cursor.getString(32));
                cl.setIMAGE(cursor.getString(33));
                cl.setLISTEPRIX_CODE(cursor.getString(34));
                cl.setQR_CODE(cursor.getInt(35));

                listClient.add(cl);
            }while(cursor.moveToNext());
        }
        //returner la listclients;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Clients from Sqlite: "+listClient);
        Log.d(TAG, "getListClNEByTC: "+selectQuery);
        return listClient;
    }

    public void deleteClients() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_CLIENT, null, null);
        db.close();
        Log.d(TAG, "Deleted all Clients info from sqlite");
    }

    public int delete(String clientCode) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CLIENT,KEY_CLIENT_CODE+"=?",new String[]{clientCode});
    }

    public static void synchronisationClient(final Context context){

        // Tag used to cancel the request
        String tag_string_req = "CLIENT";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_CLIENT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("client", "onResponse: "+response);
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray clients = jObj.getJSONArray("Clients");
                        // Toast.makeText(context, "Nombre de Clients  "+clients.length() , Toast.LENGTH_SHORT).show();
                        Log.d("Clients duserveur ","---"+clients.toString());
                        int cptInsert = 0,cptDeleted = 0;
                        if(clients.length()>0) {

                            //Ajout/Suppression/modification des clients dans la base de données.
                            ClientManager clientManager = new ClientManager(context);
                                for (int i = 0; i < clients.length(); i++) {
                                    JSONObject unClient = clients.getJSONObject(i);
                                    Log.d("Client", "onResponse: "+unClient);
                                    Log.d("Client", "onResponse: listprix1 "+unClient.getString("LISTEPRIX_CODE"));
                                    if (unClient.getString("OPERATION").equals("DELETE")) {
                                            //Toast.makeText(context, "Client a supprimer   "+unClient.getString("CLIENT_CODE"), Toast.LENGTH_LONG).show();
                                            cptDeleted++;
                                            clientManager.delete(unClient.getString("CLIENT_CODE"));
                                    } else {
                                        Log.d("Client", "onResponse: listprix2 "+unClient.getString("LISTEPRIX_CODE"));
                                        Log.d("Client", "onResponse: Json "+new Client(unClient));
                                        clientManager.add(new Client(unClient));
                                        cptInsert++;
                                    }
                                }
                            }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CLIENT: Inserted: "+cptInsert +" Deleted: "+cptDeleted ,"CLIENT",1));

                        }


                        //logM.add("CLIENTS : OK Inserted "+cptInsert +"Deleted "+cptDeleted ,"SyncClients");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(context, "CLIENTS :"+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("CLIENTS : NOK Inserted error"+errorMsg ,"SyncClients");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CLIENT: Error: "+errorMsg ,"CLIENT",0));

                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "CLIENTS :"+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("CLIENTS : NOK Inserted JsonErr"+e.getMessage() ,"SyncClients");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CLIENT: Error: "+e.getMessage() ,"CLIENT",0));

                    }


                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "CLIENTS :"+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("CLIENTS : NOK Inserted Onrsponse"+error.getMessage() ,"SyncClients");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CLIENT: Error: "+error.getMessage() ,"CLIENT",0));

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if(pref.getString("is_login", null) != null) {
                    if (pref.getString("is_login", null).equals("ok")) {

                        Gson gson2 = new Gson();
                        String json2 = pref.getString("User", "");
                        Type type = new TypeToken<JSONObject>() {}.getType();
                        JSONObject user = gson2.fromJson(json2, type);
                        try {
                            Log.d(TAG, "getParams: "+user.getString("UTILISATEUR_CODE"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            params.put("UTILISATEUR_CODE", user.getString("UTILISATEUR_CODE"));
                        }catch (Exception e ){

                        }
                        ClientManager clientManager = new ClientManager(context);
                        List<Client> listClients = clientManager.getClientCode_Version_List();
                        for (int i = 0; i < listClients.size(); i++) {
                            Log.d("client@@version","--"+listClients.get(i).getCLIENT_CODE()+"@@"+listClients.get(i).getVERSION());
                            if (listClients.get(i).getCLIENT_CODE() != null && listClients.get(i).getVERSION() != null)
                                params.put(listClients.get(i).getCLIENT_CODE(), listClients.get(i).getVERSION());
                        }
                    }
                }

                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    ////////////////////////////////////GET THE NUMBER OF VISITES WITH DISTINCTS CLIENT NAME//////////////////////////////
    public int GetClientNombreByTourneeCode(String tournee_code) {

        String selectQuery = "SELECT * FROM " + TABLE_CLIENT + " WHERE "+ KEY_TOURNEE_CODE +" = '"+tournee_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int vcr=cursor.getCount();
        cursor.close();
        db.close();
        return vcr;

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////GET THE NUMBER OF VISITES WITH DISTINCTS CLIENT NAME//////////////////////////////
    public int GetClientNombreByLivraison() {

        String selectQuery = "SELECT DISTINCT(commandenonclotureeal.CLIENT_CODE) FROM commandenonclotureeal";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int vcr=cursor.getCount();
        cursor.close();
        db.close();
        return vcr;

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////GET CLIENT WITH DONE VISITS////////////////////////////////////////////////////
    public ArrayList<Client> getListWithVisite(String tournee_code,String date_visite) {
        ArrayList<Client> listClient = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT + " WHERE "+KEY_TOURNEE_CODE +" = '"+tournee_code+"' AND " + KEY_CLIENT_CODE +" NOT IN (SELECT CLIENT_CODE FROM visite WHERE TOURNEE_CODE = '"+tournee_code+"' AND DATE_VISITE = '"+date_visite+"')";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Client cl = new Client();
                cl.setCLIENT_CODE(cursor.getString(0));
                cl.setCLIENT_NOM(cursor.getString(1));
                cl.setCLIENT_TELEPHONE1(cursor.getString(2));
                cl.setCLIENT_TELEPHONE2(cursor.getString(3));
                cl.setSTATUT_CODE(cursor.getInt(4));
                cl.setDISTRIBUTEUR_CODE(cursor.getString(5));
                cl.setREGION_CODE(cursor.getString(6));
                cl.setZONE_CODE(cursor.getString(7));
                cl.setVILLE_CODE(cursor.getString(8));
                cl.setSECTEUR_CODE(cursor.getString(9));
                cl.setSOUSSECTEUR_CODE(cursor.getString(10));
                cl.setTOURNEE_CODE(cursor.getString(11));
                cl.setADRESSE_NR(cursor.getString(12));
                cl.setADRESSE_RUE(cursor.getString(13));
                cl.setADRESSE_QUARTIER(cursor.getString(14));
                cl.setTYPE_CODE(cursor.getString(15));
                cl.setCATEGORIE_CODE(cursor.getString(16));
                cl.setGROUPE_CODE(cursor.getString(17));
                cl.setCLASSE_CODE(cursor.getString(18));
                cl.setCIRCUIT_CODE(cursor.getString(19));
                cl.setFAMILLE_CODE(cursor.getString(20));
                cl.setRANG(cursor.getInt(21));
                cl.setGPS_LATITUDE(cursor.getString(22));
                cl.setGPS_LONGITUDE(cursor.getString(23));
                cl.setMODE_PAIEMENT(cursor.getString(24));
                cl.setPOTENTIEL_TONNE(cursor.getString(25));
                cl.setFREQUENCE_VISITE(cursor.getString(26));
                cl.setDATE_CREATION(cursor.getString(27));
                cl.setINACTIF(cursor.getString(29));
                cl.setINACTIF_RAISON(cursor.getString(30));
                cl.setSTOCK_CODE(cursor.getString(31));
                cl.setVERSION(cursor.getString(32));
                cl.setIMAGE(cursor.getString(33));
                cl.setLISTEPRIX_CODE(cursor.getString(34));
                cl.setQR_CODE(cursor.getInt(35));

                listClient.add(cl);
            }while(cursor.moveToNext());
        }
        //returner la listclients;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Clients from Sqlite: ");
        Log.d(TAG, "getListWithVisite: "+selectQuery+" Nombre Clients "+listClient.size());
        return listClient;
    }

    public ArrayList<Client> getListWithoutVisiteLivraison() {
        ArrayList<Client> listClient = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT + " WHERE " + KEY_CLIENT_CODE +" NOT IN (SELECT CLIENT_CODE FROM visite WHERE  VISITE_RESULTAT != 0)";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Client cl = new Client();
                cl.setCLIENT_CODE(cursor.getString(0));
                cl.setCLIENT_NOM(cursor.getString(1));
                cl.setCLIENT_TELEPHONE1(cursor.getString(2));
                cl.setCLIENT_TELEPHONE2(cursor.getString(3));
                cl.setSTATUT_CODE(cursor.getInt(4));
                cl.setDISTRIBUTEUR_CODE(cursor.getString(5));
                cl.setREGION_CODE(cursor.getString(6));
                cl.setZONE_CODE(cursor.getString(7));
                cl.setVILLE_CODE(cursor.getString(8));
                cl.setSECTEUR_CODE(cursor.getString(9));
                cl.setSOUSSECTEUR_CODE(cursor.getString(10));
                cl.setTOURNEE_CODE(cursor.getString(11));
                cl.setADRESSE_NR(cursor.getString(12));
                cl.setADRESSE_RUE(cursor.getString(13));
                cl.setADRESSE_QUARTIER(cursor.getString(14));
                cl.setTYPE_CODE(cursor.getString(15));
                cl.setCATEGORIE_CODE(cursor.getString(16));
                cl.setGROUPE_CODE(cursor.getString(17));
                cl.setCLASSE_CODE(cursor.getString(18));
                cl.setCIRCUIT_CODE(cursor.getString(19));
                cl.setFAMILLE_CODE(cursor.getString(20));
                cl.setRANG(cursor.getInt(21));
                cl.setGPS_LATITUDE(cursor.getString(22));
                cl.setGPS_LONGITUDE(cursor.getString(23));
                cl.setMODE_PAIEMENT(cursor.getString(24));
                cl.setPOTENTIEL_TONNE(cursor.getString(25));
                cl.setFREQUENCE_VISITE(cursor.getString(26));
                cl.setDATE_CREATION(cursor.getString(27));
                cl.setINACTIF(cursor.getString(29));
                cl.setINACTIF_RAISON(cursor.getString(30));
                cl.setSTOCK_CODE(cursor.getString(31));
                cl.setVERSION(cursor.getString(32));
                cl.setIMAGE(cursor.getString(33));
                cl.setLISTEPRIX_CODE(cursor.getString(34));
                cl.setQR_CODE(cursor.getInt(35));

                listClient.add(cl);
            }while(cursor.moveToNext());
        }
        //returner la listclients;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Clients from Sqlite: ");
        Log.d(TAG, "getListWithVisite: "+selectQuery+" Nombre Clients "+listClient.size());
        return listClient;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<Client> getListWithoutVisiteCmdaL(String date_visite) {
        ArrayList<Client> listClient = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT + " INNER JOIN commandealivrer on commandealivrer.CLIENT_CODE = client.CLIENT_CODE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Client cl = new Client();
                cl.setCLIENT_CODE(cursor.getString(0));
                cl.setCLIENT_NOM(cursor.getString(1));
                cl.setCLIENT_TELEPHONE1(cursor.getString(2));
                cl.setCLIENT_TELEPHONE2(cursor.getString(3));
                cl.setSTATUT_CODE(cursor.getInt(4));
                cl.setDISTRIBUTEUR_CODE(cursor.getString(5));
                cl.setREGION_CODE(cursor.getString(6));
                cl.setZONE_CODE(cursor.getString(7));
                cl.setVILLE_CODE(cursor.getString(8));
                cl.setSECTEUR_CODE(cursor.getString(9));
                cl.setSOUSSECTEUR_CODE(cursor.getString(10));
                cl.setTOURNEE_CODE(cursor.getString(11));
                cl.setADRESSE_NR(cursor.getString(12));
                cl.setADRESSE_RUE(cursor.getString(13));
                cl.setADRESSE_QUARTIER(cursor.getString(14));
                cl.setTYPE_CODE(cursor.getString(15));
                cl.setCATEGORIE_CODE(cursor.getString(16));
                cl.setGROUPE_CODE(cursor.getString(17));
                cl.setCLASSE_CODE(cursor.getString(18));
                cl.setCIRCUIT_CODE(cursor.getString(19));
                cl.setFAMILLE_CODE(cursor.getString(20));
                cl.setRANG(cursor.getInt(21));
                cl.setGPS_LATITUDE(cursor.getString(22));
                cl.setGPS_LONGITUDE(cursor.getString(23));
                cl.setMODE_PAIEMENT(cursor.getString(24));
                cl.setPOTENTIEL_TONNE(cursor.getString(25));
                cl.setFREQUENCE_VISITE(cursor.getString(26));
                cl.setDATE_CREATION(cursor.getString(27));
                cl.setINACTIF(cursor.getString(29));
                cl.setINACTIF_RAISON(cursor.getString(30));
                cl.setSTOCK_CODE(cursor.getString(31));
                cl.setVERSION(cursor.getString(32));
                cl.setIMAGE(cursor.getString(33));
                cl.setLISTEPRIX_CODE(cursor.getString(34));
                cl.setQR_CODE(cursor.getInt(35));

                listClient.add(cl);
            }while(cursor.moveToNext());
        }
        //returner la listclients;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Clients from Sqlite: ");
        Log.d(TAG, "getListWithVisite: "+selectQuery+" Nombre Clients "+listClient.size());
        return listClient;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<Client> getClientProches(List<Client> clients,double latitude, double longitude, Context context){

        ParametreManager parametreManager = new ParametreManager(context);
        Parametre parametre = parametreManager.get("GPSPROCHE");
        int distance_gps = 50;
        ArrayList<Client> clientProches = new ArrayList<>();
        double positionLatitude = latitude;
        double positionLongitude = longitude;

        Log.d(TAG, "getClientProches: latitude "+positionLatitude);
        Log.d(TAG, "getClientProches: longitude "+positionLongitude);

        float [] distance = new float[1];

        for(int i=0;i<clients.size();i++){

            distance[0]=0;

            double clientLatitue = Double.parseDouble(clients.get(i).getGPS_LATITUDE().replace(",",".")) ;
            double clientLongitude = Double.parseDouble(clients.get(i).getGPS_LONGITUDE().replace(",","."));

            Location.distanceBetween(positionLatitude,positionLongitude,clientLatitue,clientLongitude,distance);

            try{

                if(parametre.getVALEUR()== null || parametre.getVALEUR().length() == 0){
                    distance_gps = 20;
                }else{
                    distance_gps =Integer.parseInt(parametre.getVALEUR());
                }
                if(distance[0]<=distance_gps){
                    clientProches.add(clients.get(i));
                }

            }catch (NullPointerException e){
                Log.d(TAG, "getClientProches: "+e.getMessage());
            }

        }

        return clientProches;
    }
}

