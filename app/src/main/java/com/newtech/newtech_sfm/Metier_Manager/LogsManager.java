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
import com.newtech.newtech_sfm.Metier.LogSync;
import com.newtech.newtech_sfm.Metier.Logs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LogsManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_LOG = "logs";

    public LogsManager(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String
            KEY_ID="ID",
            KEY_IMEI="IMEI",
            KEY_UTILISATEUR_CODE="UTILISATEUR_CODE",
            KEY_APPLICATION_VERSION="APPLICATION_VERSION",
            KEY_APPLICATION_VERSIONCODE="APPLICATION_VERSIONCODE",
            KEY_DATE_CREATION="DATE_CREATION",
            KEY_DATE_LOG="DATE_LOG",
            KEY_TYPE_CODE="TYPE_CODE",
            KEY_CATEGORIE_CODE="CATEGORIE_CODE",
            KEY_STATUT_CODE="STATUT_CODE",
            KEY_LOG_TAG="LOG_TAG",
            KEY_LOG_TEXT="LOG_TEXT";

    public static String CREATE_LOG_TABLE = "CREATE TABLE " + TABLE_LOG + "("
            +KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            +KEY_IMEI+ " TEXT,"
            +KEY_UTILISATEUR_CODE    + " TEXT,"
            +KEY_APPLICATION_VERSION + " TEXT,"
            +KEY_APPLICATION_VERSIONCODE  + " TEXT,"
            +KEY_DATE_CREATION + " TEXT,"
            +KEY_DATE_LOG+ " TEXT,"
            +KEY_TYPE_CODE + " TEXT,"
            +KEY_CATEGORIE_CODE + " TEXT,"
            +KEY_STATUT_CODE + " TEXT,"
            +KEY_LOG_TAG + " TEXT,"
            +KEY_LOG_TEXT + " TEXT"+ ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE);
            db.execSQL(CREATE_LOG_TABLE);
        } catch (SQLException e) {
        }
        Log.d(TAG, "table Log created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOG);
        onCreate(db);
    }

    public void add(Logs logs) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "add: "+logs.toString());
        ContentValues values = new ContentValues();
        //values.put(KEY_ID, logs.getID());
        values.put(KEY_IMEI, logs.getIMEI());
        values.put(KEY_UTILISATEUR_CODE, logs.getUTILISATEUR_CODE());
        values.put(KEY_APPLICATION_VERSION, logs.getAPPLICATION_VERSION());
        values.put(KEY_APPLICATION_VERSIONCODE, logs.getAPPLICATION_VERSIONCODE());
        values.put(KEY_DATE_CREATION, logs.getDATE_CREATION());
        values.put(KEY_DATE_LOG, logs.getDATE_LOG());
        values.put(KEY_TYPE_CODE, logs.getTYPE_CODE());
        values.put(KEY_CATEGORIE_CODE, logs.getCATEGORIE_CODE());
        values.put(KEY_STATUT_CODE, logs.getSTATUT_CODE());
        values.put(KEY_LOG_TAG, logs.getLOG_TAG());
        values.put(KEY_LOG_TEXT, logs.getLOG_TEXT());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_LOG, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d(TAG, "nouvelle logs inseré dans la table logs: " + id);
    }

    public int delete(String logs_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_LOG,KEY_ID+"=?",new String[]{logs_id});
    }

    public ArrayList<Logs> getList() {
        ArrayList<Logs> logsArrayList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LOG;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Logs logs = new Logs();
                logs.setID(cursor.getInt(0));
                logs.setIMEI(cursor.getString(1));
                logs.setUTILISATEUR_CODE(cursor.getString(2));
                logs.setAPPLICATION_VERSION(cursor.getString(3));
                logs.setAPPLICATION_VERSIONCODE((cursor.getString(4)));
                logs.setDATE_CREATION(cursor.getString(5));
                logs.setDATE_LOG(cursor.getString(6));
                logs.setTYPE_CODE(cursor.getString(7));
                logs.setCATEGORIE_CODE(cursor.getString(8));
                logs.setSTATUT_CODE(cursor.getString(9));
                logs.setLOG_TAG(cursor.getString(10));
                logs.setLOG_TEXT(cursor.getString(11));

                logsArrayList.add(logs);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande from table Logs: ");
        Log.d(TAG, "Nombre Commande from table logs: "+logsArrayList.size());
        return logsArrayList;
    }

    public static void synchronisationLogs(final Context context){

        /*CommandeManager commandeManager = new CommandeManager(context);
        commandeManager.deleteCmdSynchronisee();*/

        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_LOGS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray logs = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre de commandes  "+commandes.length() , Toast.LENGTH_LONG).show();
                        // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les logs : " +response);

                        if(logs.length()>0) {
                            LogsManager logsManager = new LogsManager(context);
                            for (int i = 0; i < logs.length(); i++) {
                                JSONObject unLog = logs.getJSONObject(i);
                                if (unLog.getString("statut").equals("true")) {
                                    logsManager.delete(unLog.getString("ID"));
                                    
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){

                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LOGS: Inserted: "+cptInsert +" NotInserted: "+cptDelete ,"LOGS",1));

                        }

                        //logM.add("LOGS:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"LOGS");


                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "commande1 : "+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LOGS : Error: "+errorMsg ,"LOGS",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "onResponse: "+e.getMessage());
                    if(SyncV2Activity.logSyncViewModel != null){

                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LOGS : Error: "+e.getMessage() ,"LOGS",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: "+error.getMessage());
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LOGS : Error: "+error.getMessage() ,"LOGS",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    LogsManager logsManager = new LogsManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Data",gson.toJson(logsManager.getList()));
                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
