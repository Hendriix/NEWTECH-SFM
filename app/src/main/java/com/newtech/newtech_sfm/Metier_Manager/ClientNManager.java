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
import com.newtech.newtech_sfm.Metier.ClientN;
import com.newtech.newtech_sfm.Metier.LogSync;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sferricha on 14/11/2016.
 */

public class ClientNManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // ClientN table name
    public static final String TABLE_CLIENTN = "clientn";

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

    public static  String CREATE_CLIENTN_TABLE = "CREATE TABLE " + TABLE_CLIENTN + "("
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
    public ClientNManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_CLIENTN);
            db.execSQL(CREATE_CLIENTN_TABLE);

        } catch (SQLException e) {
        }
        Log.d(TAG, "Database tables created");
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTN);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing Articles details in database
     * */
    public void add(ClientN clientN) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CLIENT_CODE, clientN.getCLIENT_CODE());
        values.put(KEY_CLIENT_NOM,clientN.getCLIENT_NOM());
        values.put(KEY_CLIENT_TELEPHONE1, clientN.getCLIENT_TELEPHONE1());
        values.put(KEY_CLIENT_TELEPHONE2, clientN.getCLIENT_TELEPHONE2());
        values.put(KEY_STATUT_CODE, clientN.getSTATUT_CODE());
        values.put(KEY_DISTRIBUTEUR_CODE,clientN.getDISTRIBUTEUR_CODE());
        values.put(KEY_REGION_CODE,clientN.getREGION_CODE());
        values.put(KEY_ZONE_CODE, clientN.getZONE_CODE());
        values.put(KEY_VILLE_CODE, clientN.getVILLE_CODE());
        values.put(KEY_SECTEUR_CODE, clientN.getSECTEUR_CODE());
        values.put(KEY_SOUSSECTEUR_CODE, clientN.getSOUSSECTEUR_CODE());
        values.put(KEY_TOURNEE_CODE, clientN.getTOURNEE_CODE());
        values.put(KEY_ADRESSE_NR, clientN.getADRESSE_NR());
        values.put(KEY_ADRESSE_RUE, clientN.getADRESSE_RUE());
        values.put(KEY_ADRESSE_QUARTIER, clientN.getADRESSE_QUARTIER());
        values.put(KEY_TYPE_CODE, clientN.getTYPE_CODE());
        values.put(KEY_CATEGORIE_CODE, clientN.getCATEGORIE_CODE());
        values.put(KEY_GROUPE_CODE, clientN.getGROUPE_CODE());
        values.put(KEY_CLASSE_CODE, clientN.getCLASSE_CODE());
        values.put(KEY_CIRCUIT_CODE, clientN.getCIRCUIT_CODE());
        values.put(KEY_FAMILLE_CODE,clientN.getFAMILLE_CODE());
        values.put(KEY_RANG, clientN.getRANG());
        values.put(KEY_GPS_LATITUDE,clientN.getGPS_LATITUDE());
        values.put(KEY_GPS_LONGITUDE, clientN.getGPS_LONGITUDE());
        values.put(KEY_MODE_PAIEMENT, clientN.getMODE_PAIEMENT());
        values.put(KEY_POTENTIEL_TONNE, clientN.getPOTENTIEL_TONNE());
        values.put(KEY_FREQUENCE_VISITE, clientN.getFREQUENCE_VISITE());
        values.put(KEY_DATE_CREATION, clientN.getDATE_CREATION());
        values.put(KEY_CREATEUR_CODE, clientN.getCREATEUR_CODE());
        values.put(KEY_INACTIF, clientN.getINACTIF());
        values.put(KEY_INACTIF_RAISON, clientN.getINACTIF_RAISON());
        values.put(KEY_STOCK_CODE, clientN.getSTOCK_CODE());
        values.put(KEY_VERSION, clientN.getVERSION());
        values.put(KEY_IMAGE, clientN.getIMAGE());
        values.put(KEY_LISTEPRIX_CODE, clientN.getLISTEPRIX_CODE());
        values.put(KEY_QR_CODE, clientN.getQR_CODE());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_CLIENTN, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New ClientNs inserted into sqlite: " + id);
    }

    public ArrayList<ClientN> getClientCode_Version_List() {
        ArrayList<ClientN> listClientN = new ArrayList<>();

        String selectQuery = "SELECT "+ KEY_CLIENT_CODE+","+KEY_VERSION +  " FROM " + TABLE_CLIENTN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                ClientN cl = new ClientN();
                cl.setCLIENT_CODE(cursor.getString(0));
                cl.setVERSION(cursor.getString(1));
                listClientN.add(cl);
            }while(cursor.moveToNext());
        }
        //returner la listclientsN;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version ClientsN from Sqlite: ");
        return listClientN;
    }

    /**
     * Getting user data from database
     * */
    public ClientN get(String client_code) {

        ClientN clientN = new ClientN();
        String selectQuery = "SELECT  * FROM " + TABLE_CLIENTN +" WHERE "+KEY_CLIENT_CODE +" = '"+client_code +"' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {


            clientN.setCLIENT_CODE(cursor.getString(0));
            clientN.setCLIENT_NOM(cursor.getString(1));
            clientN.setCLIENT_TELEPHONE1(cursor.getString(2));
            clientN.setCLIENT_TELEPHONE2(cursor.getString(3));
            clientN.setSTATUT_CODE(cursor.getInt(4));
            clientN.setDISTRIBUTEUR_CODE(cursor.getString(5));
            clientN.setREGION_CODE(cursor.getString(6));
            clientN.setZONE_CODE(cursor.getString(7));
            clientN.setVILLE_CODE(cursor.getString(8));
            clientN.setSECTEUR_CODE(cursor.getString(9));
            clientN.setSOUSSECTEUR_CODE(cursor.getString(10));
            clientN.setTOURNEE_CODE(cursor.getString(11));
            clientN.setADRESSE_NR(cursor.getString(12));
            clientN.setADRESSE_RUE(cursor.getString(13));
            clientN.setADRESSE_QUARTIER(cursor.getString(14));
            clientN.setTYPE_CODE(cursor.getString(15));
            clientN.setCATEGORIE_CODE(cursor.getString(16));
            clientN.setGROUPE_CODE(cursor.getString(17));
            clientN.setCLASSE_CODE(cursor.getString(18));
            clientN.setCIRCUIT_CODE(cursor.getString(19));
            clientN.setFAMILLE_CODE(cursor.getString(20));
            clientN.setRANG(cursor.getInt(21));
            clientN.setGPS_LATITUDE(cursor.getString(22));
            clientN.setGPS_LONGITUDE(cursor.getString(23));
            clientN.setMODE_PAIEMENT(cursor.getString(24));
            clientN.setPOTENTIEL_TONNE(cursor.getString(25));
            clientN.setFREQUENCE_VISITE(cursor.getString(26));
            clientN.setDATE_CREATION(cursor.getString(27));
            clientN.setCREATEUR_CODE(cursor.getString(28));
            clientN.setINACTIF(cursor.getString(29));
            clientN.setINACTIF_RAISON(cursor.getString(30));
            clientN.setSTOCK_CODE(cursor.getString(31));
            clientN.setVERSION(cursor.getString(32));
            clientN.setIMAGE(cursor.getString(33));
            clientN.setLISTEPRIX_CODE(cursor.getString(34));
            clientN.setQR_CODE(cursor.getInt(35));


        }
        //returner la listclientsN;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching ClientN from Sqlite: ");
        return clientN;
    }
    /**
     * Getting user data from database
     * */
    public ArrayList<ClientN> getList() {
        ArrayList<ClientN> listClientN = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CLIENTN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                ClientN clN = new ClientN();
                clN.setCLIENT_CODE(cursor.getString(0));
                clN.setCLIENT_NOM(cursor.getString(1));
                clN.setCLIENT_TELEPHONE1(cursor.getString(2));
                clN.setCLIENT_TELEPHONE2(cursor.getString(3));
                clN.setSTATUT_CODE(cursor.getInt(4));
                clN.setDISTRIBUTEUR_CODE(cursor.getString(5));
                clN.setREGION_CODE(cursor.getString(6));
                clN.setZONE_CODE(cursor.getString(7));
                clN.setVILLE_CODE(cursor.getString(8));
                clN.setSECTEUR_CODE(cursor.getString(9));
                clN.setSOUSSECTEUR_CODE(cursor.getString(10));
                clN.setTOURNEE_CODE(cursor.getString(11));
                clN.setADRESSE_NR(cursor.getString(12));
                clN.setADRESSE_RUE(cursor.getString(13));
                clN.setADRESSE_QUARTIER(cursor.getString(14));
                clN.setTYPE_CODE(cursor.getString(15));
                clN.setCATEGORIE_CODE(cursor.getString(16));
                clN.setGROUPE_CODE(cursor.getString(17));
                clN.setCLASSE_CODE(cursor.getString(18));
                clN.setCIRCUIT_CODE(cursor.getString(19));
                clN.setFAMILLE_CODE(cursor.getString(20));
                clN.setRANG(cursor.getInt(21));
                clN.setGPS_LATITUDE(cursor.getString(22));
                clN.setGPS_LONGITUDE(cursor.getString(23));
                clN.setMODE_PAIEMENT(cursor.getString(24));
                clN.setPOTENTIEL_TONNE(cursor.getString(25));
                clN.setFREQUENCE_VISITE(cursor.getString(26));
                clN.setDATE_CREATION(cursor.getString(27));
                clN.setCREATEUR_CODE(cursor.getString(28));
                clN.setINACTIF(cursor.getString(29));
                clN.setINACTIF_RAISON(cursor.getString(30));
                clN.setSTOCK_CODE(cursor.getString(31));
                clN.setVERSION(cursor.getString(32));
                clN.setIMAGE(cursor.getString(33));
                clN.setLISTEPRIX_CODE(cursor.getString(34));
                clN.setQR_CODE(cursor.getInt(35));

                listClientN.add(clN);
            }while(cursor.moveToNext());
        }
        //returner la listclientsN;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching ClientsN from Sqlite: ");
        return listClientN;
    }
    /**
     * Getting user new data from database
     * */
    public ArrayList<ClientN> getListNew() {
        ArrayList<ClientN> listClientN = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CLIENTN +" WHERE VERSION ='To_Insert' OR VERSION ='To_Update' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                ClientN clN = new ClientN();
                clN.setCLIENT_CODE(cursor.getString(0));
                clN.setCLIENT_NOM(cursor.getString(1));
                clN.setCLIENT_TELEPHONE1(cursor.getString(2));
                clN.setCLIENT_TELEPHONE2(cursor.getString(3));
                clN.setSTATUT_CODE(cursor.getInt(4));
                clN.setDISTRIBUTEUR_CODE(cursor.getString(5));
                clN.setREGION_CODE(cursor.getString(6));
                clN.setZONE_CODE(cursor.getString(7));
                clN.setVILLE_CODE(cursor.getString(8));
                clN.setSECTEUR_CODE(cursor.getString(9));
                clN.setSOUSSECTEUR_CODE(cursor.getString(10));
                clN.setTOURNEE_CODE(cursor.getString(11));
                clN.setADRESSE_NR(cursor.getString(12));
                clN.setADRESSE_RUE(cursor.getString(13));
                clN.setADRESSE_QUARTIER(cursor.getString(14));
                clN.setTYPE_CODE(cursor.getString(15));
                clN.setCATEGORIE_CODE(cursor.getString(16));
                clN.setGROUPE_CODE(cursor.getString(17));
                clN.setCLASSE_CODE(cursor.getString(18));
                clN.setCIRCUIT_CODE(cursor.getString(19));
                clN.setFAMILLE_CODE(cursor.getString(20));
                clN.setRANG(cursor.getInt(21));
                clN.setGPS_LATITUDE(cursor.getString(22));
                clN.setGPS_LONGITUDE(cursor.getString(23));
                clN.setMODE_PAIEMENT(cursor.getString(24));
                clN.setPOTENTIEL_TONNE(cursor.getString(25));
                clN.setFREQUENCE_VISITE(cursor.getString(26));
                clN.setDATE_CREATION(cursor.getString(27));
                clN.setCREATEUR_CODE(cursor.getString(28));
                clN.setINACTIF(cursor.getString(29));
                clN.setINACTIF_RAISON(cursor.getString(30));
                clN.setSTOCK_CODE(cursor.getString(31));
                clN.setVERSION(cursor.getString(32));
                clN.setIMAGE(cursor.getString(33));
                clN.setLISTEPRIX_CODE(cursor.getString(34));
                clN.setQR_CODE(cursor.getInt(35));

                listClientN.add(clN);
            }while(cursor.moveToNext());
        }
        //returner la listclientsN new;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching ClientsN from Sqlite: ");
        return listClientN;
    }
    /**
     * Re crate database Delete all tables and create them again
     * */

    public ArrayList<ClientN> getListByTourneCode(String TourCode) {
        ArrayList<ClientN> listClientN = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CLIENTN +" WHERE "+KEY_TOURNEE_CODE +" = '"+TourCode +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                ClientN clN = new ClientN();
                clN.setCLIENT_CODE(cursor.getString(0));
                clN.setCLIENT_NOM(cursor.getString(1));
                clN.setCLIENT_TELEPHONE1(cursor.getString(2));
                clN.setCLIENT_TELEPHONE2(cursor.getString(3));
                clN.setSTATUT_CODE(cursor.getInt(4));
                clN.setDISTRIBUTEUR_CODE(cursor.getString(5));
                clN.setREGION_CODE(cursor.getString(6));
                clN.setZONE_CODE(cursor.getString(7));
                clN.setVILLE_CODE(cursor.getString(8));
                clN.setSECTEUR_CODE(cursor.getString(9));
                clN.setSOUSSECTEUR_CODE(cursor.getString(10));
                clN.setTOURNEE_CODE(cursor.getString(11));
                clN.setADRESSE_NR(cursor.getString(12));
                clN.setADRESSE_RUE(cursor.getString(13));
                clN.setADRESSE_QUARTIER(cursor.getString(14));
                clN.setTYPE_CODE(cursor.getString(15));
                clN.setCATEGORIE_CODE(cursor.getString(16));
                clN.setGROUPE_CODE(cursor.getString(17));
                clN.setCLASSE_CODE(cursor.getString(18));
                clN.setCIRCUIT_CODE(cursor.getString(19));
                clN.setFAMILLE_CODE(cursor.getString(20));
                clN.setRANG(cursor.getInt(21));
                clN.setGPS_LATITUDE(cursor.getString(22));
                clN.setGPS_LONGITUDE(cursor.getString(23));
                clN.setMODE_PAIEMENT(cursor.getString(24));
                clN.setPOTENTIEL_TONNE(cursor.getString(25));
                clN.setFREQUENCE_VISITE(cursor.getString(26));
                clN.setDATE_CREATION(cursor.getString(27));
                clN.setCREATEUR_CODE(cursor.getString(28));
                clN.setINACTIF(cursor.getString(29));
                clN.setINACTIF_RAISON(cursor.getString(30));
                clN.setSTOCK_CODE(cursor.getString(31));
                clN.setVERSION(cursor.getString(32));
                clN.setIMAGE(cursor.getString(33));
                clN.setLISTEPRIX_CODE(cursor.getString(34));
                clN.setQR_CODE(cursor.getInt(35));


                listClientN.add(clN);
            }while(cursor.moveToNext());
        }
        //returner la listclientsN;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching ClientsN from Sqlite: ");
        return listClientN;
    }


    public void deleteClients() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_CLIENTN, null, null);
        db.close();
        Log.d(TAG, "Deleted all ClientsN info from sqlite");
    }

    public void deleteClientSynchronisee() {

        String Where = " "+KEY_VERSION+"='inserted'";
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_CLIENTN, Where, null);
        db.close();
        Log.d("ClientN", "Deleted all clients verifiee from sqlite");
        Log.d("ClientN", "deleteClientSynchronise: "+Where);

    }

    public int delete(String clientCode) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CLIENTN,KEY_CLIENT_CODE+"=?",new String[]{clientCode});
    }

    public void updateVersionClientN(String clientCode,String version){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_CLIENTN+ " SET "+KEY_VERSION  +"= '"+version+"' WHERE "+KEY_CLIENT_CODE +"= '"+clientCode+"'" ;
        db.execSQL(req);
    }

    public static void synchronisationClientN(final Context context){

        ClientNManager clientNManager = new ClientNManager(context);
        clientNManager.deleteClientSynchronisee();

        // Tag used to cancel the request
        String tag_string_req = "CLIENTN";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_CLIENTN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                Log.d("clientN", "onResponse: "+response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        int cptInsert = 0;int cptDeleted = 0;
                        JSONArray clientns = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre de clientns  "+clientns.length() , Toast.LENGTH_LONG).show();
                        // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les ClientsN : " +response);

                        if(clientns.length()>0) {
                            ClientNManager clientNManager = new ClientNManager(context);
                            for (int i = 0; i < clientns.length(); i++) {
                                JSONObject unClientN = clientns.getJSONObject(i);
                                if (unClientN.getString("Statut").equals("true")) {
                                    clientNManager.updateVersionClientN((unClientN.getString("CLIENT_CODE")),"inserted");
                                    cptInsert++;

                                }else{
                                    cptDeleted++;
                                }
                            }

                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CLIENTN: Inserted: "+cptInsert +" NotInserted: "+cptDeleted ,"CLIENTN",1));

                        }

                        //logM.add("CLIENTN:OK Insert:"+cptInsert,"SyncClientN");
                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "ClientN : "+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CLIENTN: Error: "+errorMsg ,"CLIENTN",0));

                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "ClientN Json error: " +"erreur applcation clientn" + e.getMessage(), Toast.LENGTH_LONG).show();.
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CLIENTN: Error: "+e.getMessage() ,"CLIENTN",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("clientN", "onErrorResponse: "+error);
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CLIENTN: Error: "+error.getMessage() ,"CLIENTN",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);

                if( pref.getString("is_login", null).equals("ok")) {

                    ClientNManager clientNManager  = new ClientNManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableClientN");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(clientNManager.getListNew()));
                    //Log.d("clientN", "getParams: "+clientNManager.getListNew().toString());

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
}
