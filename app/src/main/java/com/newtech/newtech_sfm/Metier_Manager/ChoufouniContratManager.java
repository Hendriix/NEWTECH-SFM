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
import com.newtech.newtech_sfm.Activity.SyncV2Activity;
import com.newtech.newtech_sfm.Configuration.AppConfig;
import com.newtech.newtech_sfm.Configuration.AppController;
import com.newtech.newtech_sfm.Configuration.SQLiteHandler;
import com.newtech.newtech_sfm.Metier.ChoufouniContrat;
import com.newtech.newtech_sfm.Metier.LogSync;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChoufouniContratManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_CHOUFOUNI_CONTRAT = "choufounicontrat";


    public static final String
            KEY_CHOUFOUNI_CONTRAT_CODE = "CHOUFOUNI_CONTRAT_CODE",
            KEY_CHOUFOUNI_CODE= "CHOUFOUNI_CODE",
            KEY_DISTRIBUTEUR_CODE= "DISTRIBUTEUR_CODE",
            KEY_UTILISATEUR_CODE= "UTILISATEUR_CODE",
            KEY_CLIENT_CODE= "CLIENT_CODE",
            KEY_DATE_CONTRAT= "DATE_CONTRAT",
            KEY_TYPE_CODE= "TYPE_CODE",
            KEY_STATUT_CODE= "STATUT_CODE",
            KEY_CATEGORIE_CODE= "CATEGORIE_CODE",
            KEY_REMISE= "REMISE",
            KEY_SOLDE= "SOLDE",
            KEY_CREATEUR_CODE= "CREATEUR_CODE",
            KEY_DATE_CREATION= "DATE_CREATION",
            KEY_COMMENTAIRE= "COMMENTAIRE",
            KEY_GPS_LATITUDE="GPS_LATITUDE",
            KEY_GPS_LONGITUDE="GPS_LONGITUDE",
            KEY_DISTANCE="DISTANCE";

    public static String CREATE_CHOUFOUNI_CONTRAT_TABLE = "CREATE TABLE " + TABLE_CHOUFOUNI_CONTRAT+ " ("

            + KEY_CHOUFOUNI_CONTRAT_CODE  + " TEXT PRIMARY KEY,"
            + KEY_CHOUFOUNI_CODE + " TEXT,"
            + KEY_DISTRIBUTEUR_CODE + " TEXT,"
            + KEY_UTILISATEUR_CODE + " TEXT,"
            + KEY_CLIENT_CODE + " TEXT,"
            + KEY_DATE_CONTRAT + " TEXT,"
            + KEY_TYPE_CODE + " TEXT,"
            + KEY_STATUT_CODE + " TEXT,"
            + KEY_CATEGORIE_CODE + " TEXT,"
            + KEY_REMISE + " NUMERIC,"
            + KEY_SOLDE + " NUMERIC,"
            + KEY_CREATEUR_CODE + " TEXT,"
            + KEY_DATE_CREATION + " TEXT,"
            + KEY_COMMENTAIRE + " TEXT,"
            + KEY_GPS_LATITUDE + " TEXT,"
            + KEY_GPS_LONGITUDE + " TEXT,"
            + KEY_DISTANCE + " NUMERIC"+ ")";

    public ChoufouniContratManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAMILLE);
            db.execSQL(CREATE_CHOUFOUNI_CONTRAT_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database CHOUFOUNICONTRAT tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHOUFOUNI_CONTRAT);
        // Create tables again
        onCreate(db);
    }

    public void add(ChoufouniContrat choufouniContrat) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_CHOUFOUNI_CONTRAT_CODE,choufouniContrat.getCHOUFOUNI_CONTRAT_CODE());
        values.put(KEY_CHOUFOUNI_CODE,choufouniContrat.getCHOUFOUNI_CODE());
        values.put(KEY_DISTRIBUTEUR_CODE,choufouniContrat.getDISTRIBUTEUR_CODE());
        values.put(KEY_UTILISATEUR_CODE,choufouniContrat.getUTILISATEUR_CODE());
        values.put(KEY_CLIENT_CODE,choufouniContrat.getCLIENT_CODE());
        values.put(KEY_DATE_CONTRAT,choufouniContrat.getDATE_CONTRAT());
        values.put(KEY_TYPE_CODE,choufouniContrat.getTYPE_CODE());
        values.put(KEY_STATUT_CODE,choufouniContrat.getSTATUT_CODE());
        values.put(KEY_CATEGORIE_CODE,choufouniContrat.getCATEGORIE_CODE());
        values.put(KEY_REMISE,choufouniContrat.getREMISE());
        values.put(KEY_SOLDE,choufouniContrat.getSOLDE());
        values.put(KEY_CREATEUR_CODE,choufouniContrat.getCREATEUR_CODE());
        values.put(KEY_DATE_CREATION,choufouniContrat.getDATE_CREATION());
        values.put(KEY_COMMENTAIRE,choufouniContrat.getCOMMENTAIRE());
        values.put(KEY_GPS_LATITUDE, choufouniContrat.getGPS_LATITUDE());
        values.put(KEY_GPS_LONGITUDE, choufouniContrat.getGPS_LONGITUDE());
        values.put(KEY_DISTANCE, choufouniContrat.getDISTANCE());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_CHOUFOUNI_CONTRAT, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d("STOCKDEMANDE MANAGER", "New stockdemande inserted into sqlite: " + id);

    }

    public ChoufouniContrat get(String CHOUFOUNI_CONTRAT_CODE) {

        ChoufouniContrat choufouniContrat = new ChoufouniContrat();
        String selectQuery = "SELECT * FROM " + TABLE_CHOUFOUNI_CONTRAT+ " WHERE "+ KEY_CHOUFOUNI_CONTRAT_CODE +" = '"+CHOUFOUNI_CONTRAT_CODE+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            choufouniContrat.setCHOUFOUNI_CONTRAT_CODE(cursor.getString(0));
            choufouniContrat.setCHOUFOUNI_CODE(cursor.getString(1));
            choufouniContrat.setDISTRIBUTEUR_CODE(cursor.getString(2));
            choufouniContrat.setUTILISATEUR_CODE(cursor.getString(3));
            choufouniContrat.setCLIENT_CODE(cursor.getString(4));
            choufouniContrat.setDATE_CONTRAT(cursor.getString(5));
            choufouniContrat.setTYPE_CODE(cursor.getString(6));
            choufouniContrat.setSTATUT_CODE(cursor.getString(7));
            choufouniContrat.setCATEGORIE_CODE(cursor.getString(8));
            choufouniContrat.setREMISE(cursor.getDouble(9));
            choufouniContrat.setSOLDE(cursor.getDouble(10));
            choufouniContrat.setCREATEUR_CODE(cursor.getString(11));
            choufouniContrat.setDATE_CREATION(cursor.getString(12));
            choufouniContrat.setCOMMENTAIRE(cursor.getString(13));
            choufouniContrat.setGPS_LATITUDE(cursor.getString(14));
            choufouniContrat.setGPS_LONGITUDE(cursor.getString(15));
            choufouniContrat.setDISTANCE(cursor.getInt(16));
        }

        cursor.close();
        db.close();
        Log.d("CC MANAGER", "fetching ");
        return choufouniContrat;

    }

    public ArrayList<ChoufouniContrat> getList() {
        ArrayList<ChoufouniContrat> choufouniContrats = new ArrayList<ChoufouniContrat>();

        String selectQuery = "SELECT * FROM " + TABLE_CHOUFOUNI_CONTRAT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                ChoufouniContrat choufouniContrat = new ChoufouniContrat();
                choufouniContrat.setCHOUFOUNI_CONTRAT_CODE(cursor.getString(0));
                choufouniContrat.setCHOUFOUNI_CODE(cursor.getString(1));
                choufouniContrat.setDISTRIBUTEUR_CODE(cursor.getString(2));
                choufouniContrat.setUTILISATEUR_CODE(cursor.getString(3));
                choufouniContrat.setCLIENT_CODE(cursor.getString(4));
                choufouniContrat.setDATE_CONTRAT(cursor.getString(5));
                choufouniContrat.setTYPE_CODE(cursor.getString(6));
                choufouniContrat.setSTATUT_CODE(cursor.getString(7));
                choufouniContrat.setCATEGORIE_CODE(cursor.getString(8));
                choufouniContrat.setREMISE(cursor.getDouble(9));
                choufouniContrat.setSOLDE(cursor.getDouble(10));
                choufouniContrat.setCREATEUR_CODE(cursor.getString(11));
                choufouniContrat.setDATE_CREATION(cursor.getString(12));
                choufouniContrat.setCOMMENTAIRE(cursor.getString(13));
                choufouniContrat.setGPS_LATITUDE(cursor.getString(14));
                choufouniContrat.setGPS_LONGITUDE(cursor.getString(15));
                choufouniContrat.setDISTANCE(cursor.getInt(16));
                choufouniContrats.add(choufouniContrat);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version choufouni contrat from Sqlite: "+choufouniContrats.size());
        return choufouniContrats;
    }

    public ArrayList<ChoufouniContrat> getListByClientCode(String client_code) {
        ArrayList<ChoufouniContrat> choufouniContrats = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CHOUFOUNI_CONTRAT +" WHERE "+KEY_CLIENT_CODE+" = '"+client_code+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                ChoufouniContrat choufouniContrat = new ChoufouniContrat();
                choufouniContrat.setCHOUFOUNI_CONTRAT_CODE(cursor.getString(0));
                choufouniContrat.setCHOUFOUNI_CODE(cursor.getString(1));
                choufouniContrat.setDISTRIBUTEUR_CODE(cursor.getString(2));
                choufouniContrat.setUTILISATEUR_CODE(cursor.getString(3));
                choufouniContrat.setCLIENT_CODE(cursor.getString(4));
                choufouniContrat.setDATE_CONTRAT(cursor.getString(5));
                choufouniContrat.setTYPE_CODE(cursor.getString(6));
                choufouniContrat.setSTATUT_CODE(cursor.getString(7));
                choufouniContrat.setCATEGORIE_CODE(cursor.getString(8));
                choufouniContrat.setREMISE(cursor.getDouble(9));
                choufouniContrat.setSOLDE(cursor.getDouble(10));
                choufouniContrat.setCREATEUR_CODE(cursor.getString(11));
                choufouniContrat.setDATE_CREATION(cursor.getString(12));
                choufouniContrat.setCOMMENTAIRE(cursor.getString(13));
                choufouniContrat.setGPS_LATITUDE(cursor.getString(14));
                choufouniContrat.setGPS_LONGITUDE(cursor.getString(15));
                choufouniContrat.setDISTANCE(cursor.getInt(16));
                Log.d(TAG, "getListNotInserted: "+choufouniContrats.toString());
                choufouniContrats.add(choufouniContrat);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();

        Log.d(TAG, "Nombre ChoufouniContrat from table choufounicontrat: "+choufouniContrats.size());
        return choufouniContrats;
    }

    public ArrayList<ChoufouniContrat> getListNotInserted() {
        ArrayList<ChoufouniContrat> choufouniContrats = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CHOUFOUNI_CONTRAT +" WHERE "+KEY_COMMENTAIRE+" = 'to_insert' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                ChoufouniContrat choufouniContrat = new ChoufouniContrat();
                choufouniContrat.setCHOUFOUNI_CONTRAT_CODE(cursor.getString(0));
                choufouniContrat.setCHOUFOUNI_CODE(cursor.getString(1));
                choufouniContrat.setDISTRIBUTEUR_CODE(cursor.getString(2));
                choufouniContrat.setUTILISATEUR_CODE(cursor.getString(3));
                choufouniContrat.setCLIENT_CODE(cursor.getString(4));
                choufouniContrat.setDATE_CONTRAT(cursor.getString(5));
                choufouniContrat.setTYPE_CODE(cursor.getString(6));
                choufouniContrat.setSTATUT_CODE(cursor.getString(7));
                choufouniContrat.setCATEGORIE_CODE(cursor.getString(8));
                choufouniContrat.setREMISE(cursor.getDouble(9));
                choufouniContrat.setSOLDE(cursor.getDouble(10));
                choufouniContrat.setCREATEUR_CODE(cursor.getString(11));
                choufouniContrat.setDATE_CREATION(cursor.getString(12));
                choufouniContrat.setCOMMENTAIRE(cursor.getString(13));
                choufouniContrat.setGPS_LATITUDE(cursor.getString(14));
                choufouniContrat.setGPS_LONGITUDE(cursor.getString(15));
                choufouniContrat.setDISTANCE(cursor.getInt(16));
                Log.d(TAG, "getListNotInserted: "+choufouniContrats.toString());
                choufouniContrats.add(choufouniContrat);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();

        Log.d(TAG, "Nombre ChoufouniContrat from table choufounicontrat: "+choufouniContrats.size());
        return choufouniContrats;
    }

    public int delete(String choufouni_contrat_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CHOUFOUNI_CONTRAT,KEY_CHOUFOUNI_CONTRAT_CODE+"=?",new String[]{choufouni_contrat_code});
    }

    public void updateChoufouniContrat(String choufouni_contrat_code,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_CHOUFOUNI_CONTRAT + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_CHOUFOUNI_CONTRAT_CODE +"= '"+choufouni_contrat_code+"'" ;
        db.execSQL(req);
        db.close();

        Log.d("commande", "updateCommande: "+req);
    }

    public void deleteChoufouniContratSynchronisee() {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefCmdAS = sdf.format(new Date());
        Log.d(TAG, "deleteChoufouniContratSynchronisee: "+DatefCmdAS);

        String Where = " "+KEY_COMMENTAIRE+"='inserted' and date("+KEY_DATE_CREATION+")!='"+DatefCmdAS+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_CHOUFOUNI_CONTRAT, Where, null);
        db.close();

    }

    public static void synchronisationChoufouniContrat(final Context context){

        ChoufouniContratManager choufouniContratManager = new ChoufouniContratManager(context);
        choufouniContratManager.deleteChoufouniContratSynchronisee();

        String tag_string_req = "CHOUFOUNI_CONTRAT";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_CHOUFOUNI_CONTRAT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    Log.d(TAG, "onResponse: ChoufouniContrat"+ response);
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray ChoufouniContrats = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre de ChoufouniContrat  "+ChoufouniContrats.length() , Toast.LENGTH_LONG).show();
                        // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les ChoufouniContrats : " +response);

                        if(ChoufouniContrats.length()>0) {
                            ChoufouniContratManager ChoufouniContratManager = new ChoufouniContratManager(context);
                            for (int i = 0; i < ChoufouniContrats.length(); i++) {
                                JSONObject unChoufouniContrat = ChoufouniContrats.getJSONObject(i);
                                if (unChoufouniContrat.getString("Statut").equals("true")) {
                                    ChoufouniContratManager.updateChoufouniContrat((unChoufouniContrat.getString("CHOUFOUNI_CONTRAT_CODE")),"inserted");
                                    cptInsert++;
                                }else{
                                    cptDelete++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CHOUFOUNI_CONTRAT_CODE: Inserted: "+cptInsert +" NotInserted: "+cptDelete ,"CHOUFOUNI_CONTRAT",1));

                        }

                        //logM.add("ChoufouniContrat:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncChoufouniContrat");


                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "ChoufouniContrat : "+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CHOUFOUNI_CONTRAT_CODE: Error: "+errorMsg ,"CHOUFOUNI_CONTRAT",0));

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CHOUFOUNI_CONTRAT_CODE: Error: "+e.getMessage() ,"CHOUFOUNI_CONTRAT",0));

                    }
                    //Toast.makeText(context, "ChoufouniContrat : "+"Json error: " +"erreur applcation ChoufouniContrat" + e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "ChoufouniContrat : "+error.getMessage(), Toast.LENGTH_LONG).show();
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CHOUFOUNI_CONTRAT_CODE: Error: "+error.getMessage() ,"CHOUFOUNI_CONTRAT",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    ChoufouniContratManager ChoufouniContratManager  = new ChoufouniContratManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableChoufouniContrat");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(ChoufouniContratManager.getListNotInserted()));

                    Log.d("ChoufouniContratsynch", "Liste: "+ChoufouniContratManager.getListNotInserted());
                    Log.d(TAG, "getParams: listnotinserted "+ChoufouniContratManager.getListNotInserted().size());
                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public Boolean exist(String choufouni_contrat_code){
        Boolean result=false;
        String selectQuery = "SELECT  * FROM "+TABLE_CHOUFOUNI_CONTRAT+" WHERE "+KEY_CHOUFOUNI_CONTRAT_CODE+"='"+choufouni_contrat_code+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if( cursor != null && cursor.getCount()>0 ) {
            result=true;
        }
        cursor.close();
        db.close();
        return result;
    }

    public boolean getChoufouniContrat(Context context, String client_code){

        ChoufouniContratPullManager choufouniContratPullManager = new ChoufouniContratPullManager(context);
        ChoufouniContratManager choufouniContratManager = new ChoufouniContratManager(context);
        ArrayList<ChoufouniContrat> choufouniContratArrayList = new ArrayList<>();
        ArrayList<ChoufouniContrat> choufouniContratPullArrayList = new ArrayList<>();

        choufouniContratArrayList = choufouniContratManager.getListByClientCode(client_code);
        Log.d(TAG, "getChoufouniContrat: "+choufouniContratArrayList.size());
        Log.d(TAG, "getChoufouniContrat: "+choufouniContratArrayList.toString());

        choufouniContratPullArrayList = choufouniContratPullManager.getListByClientCode(client_code);
        Log.d(TAG, "getChoufouniContrat PULL : "+choufouniContratPullArrayList.size());
        Log.d(TAG, "getChoufouniContrat PULL : "+choufouniContratPullArrayList.toString());

        for(int i=0 ; i<choufouniContratPullArrayList.size();i++){
            choufouniContratArrayList.add(choufouniContratPullArrayList.get(i));
        }

        Log.d(TAG, "getChoufouniContrat: "+choufouniContratArrayList.size());
        Log.d(TAG, "getChoufouniContrat: "+choufouniContratArrayList.toString());

        if(choufouniContratArrayList.size() > 0 ){
            return true;
        }else{
            return false;
        }
    }
}
