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
import com.newtech.newtech_sfm.Metier.TachePlanification;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TONPC on 13/03/2017.
 */

public class TachePlanificationManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_TACHEPLANIFICATION = "tacheplanification";

    public TachePlanificationManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //table visiteresultat column
    private static final String
            KEY_ID ="ID",
            KEY_TACHEPLANIFICATION_CODE ="TACHEPLANIFICATION_CODE",
            KEY_TACHE_CODE ="TACHE_CODE",
            KEY_TACHEPLANIFICATION_DATE = "TACHEPLANIFICATION_DATE",
            KEY_VERSION ="VERSION";

    public static String CREATE_TACHEPLANIFICATION_TABLE = "CREATE TABLE " + TABLE_TACHEPLANIFICATION + "("
            +KEY_ID + " INTEGER PRIMARY KEY,"
            +KEY_TACHEPLANIFICATION_CODE + " TEXT,"
            +KEY_TACHE_CODE + " TEXT,"
            +KEY_TACHEPLANIFICATION_DATE + " TEXT,"
            +KEY_VERSION + " TEXT "+")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_VISITETOURNEE);
            db.execSQL(CREATE_TACHEPLANIFICATION_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database table tacheplanification created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TACHEPLANIFICATION);
        // Create tables again
        onCreate(db);
    }

    //ADD TACHEPLANIFICATION TO THE TABLE

    public void add(TachePlanification tachePlanification) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,tachePlanification.getID());
        values.put(KEY_TACHEPLANIFICATION_CODE, tachePlanification.getTACHEPLANIFICATION_CODE());
        values.put(KEY_TACHE_CODE, tachePlanification.getTACHE_CODE());
        values.put(KEY_TACHEPLANIFICATION_DATE, tachePlanification.getTACHEPLANIFICATION_DATE());
        values.put(KEY_VERSION, tachePlanification.getVERSION());
        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_TACHEPLANIFICATION, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d("TACHEPLANIFICATION MANAGER", "New tache inserted into sqlite: " + id);
        Log.d("salim tache", "New tachePlanification inserted into sqlite: " + tachePlanification.toString());
    }

    public ArrayList<TachePlanification> getList() {
        ArrayList<TachePlanification> listTachePlanification = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_TACHEPLANIFICATION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                TachePlanification tachePlanification = new TachePlanification();
                tachePlanification.setID(cursor.getInt(0));
                tachePlanification.setTACHEPLANIFICATION_CODE(cursor.getString(1));
                tachePlanification.setTACHE_CODE(cursor.getString(2));
                tachePlanification.setTACHEPLANIFICATION_DATE(cursor.getString(3));
                tachePlanification.setVERSION(cursor.getString(4));
                listTachePlanification.add(tachePlanification);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version TachePlanification from Sqlite: ");
        return listTachePlanification;
    }

    public ArrayList<TachePlanification> getListplanByDate(String date) {
        ArrayList<TachePlanification> listTachePlanification = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_TACHEPLANIFICATION +" WHERE "+KEY_TACHEPLANIFICATION_DATE+"= '"+date+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                TachePlanification tachePlanification = new TachePlanification();
                tachePlanification.setID(cursor.getInt(0));
                tachePlanification.setTACHEPLANIFICATION_CODE(cursor.getString(1));
                tachePlanification.setTACHE_CODE(cursor.getString(2));
                tachePlanification.setTACHEPLANIFICATION_DATE(cursor.getString(3));
                tachePlanification.setVERSION(cursor.getString(4));
                listTachePlanification.add(tachePlanification);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version TachePlanification from Sqlite: ");
        return listTachePlanification;
    }

    public TachePlanification  get( String TACHEPLANIFICATION_CODE) {

        String selectQuery = "SELECT * FROM " + TABLE_TACHEPLANIFICATION + " WHERE "+ KEY_TACHEPLANIFICATION_CODE +" = '"+TACHEPLANIFICATION_CODE+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        TachePlanification tachePlanification = new TachePlanification();

        if( cursor != null && cursor.moveToFirst() ) {
            tachePlanification.setID(cursor.getInt(0));
            tachePlanification.setTACHEPLANIFICATION_CODE(cursor.getString(1));
            tachePlanification.setTACHE_CODE(cursor.getString(2));
            tachePlanification.setTACHEPLANIFICATION_DATE(cursor.getString(3));
            tachePlanification.setVERSION(cursor.getString(4));
        }

        cursor.close();
        db.close();
        return tachePlanification;

    }

    public int delete(String tacheplanificationcode,String version) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TACHEPLANIFICATION,KEY_TACHEPLANIFICATION_CODE+"=? AND "+KEY_VERSION+"=?",new String[]{tacheplanificationcode,version});
    }

    public static void synchronisationTachePlanification(final Context context){

        String tag_string_req = "TACHE_PLANIFICATION";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_TACHEPLANIFICATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    Log.d("salim tacheplanification",response);
                    JSONObject jObj = new JSONObject(response);

                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray TachePlanifications = jObj.getJSONArray("TachePlanifications");
                        //Toast.makeText(context, "nombre de TachePlanifications  "+TachePlanifications.length()  , Toast.LENGTH_SHORT).show();
                        if(TachePlanifications.length()>0) {
                            TachePlanificationManager tachePlanificationManager = new TachePlanificationManager(context);
                            for (int i = 0; i < TachePlanifications.length(); i++) {
                                JSONObject tacheplanification = TachePlanifications.getJSONObject(i);
                                if (tacheplanification.getString("OPERATION").equals("DELETE")) {
                                    tachePlanificationManager.delete(tacheplanification.getString("TACHEPLANIFICATION_CODE"),tacheplanification.getString("VERSION"));
                                    cptDelete++;
                                } else {
                                    tachePlanificationManager.add(new TachePlanification(tacheplanification));
                                    cptInsert++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("TACHE_PLANIFICATION: Inserted: "+cptInsert +" Deleted: "+cptDelete ,"TACHE_PLANIFICATION",1));

                        }

                        //logM.add("tacheplanification:OK Insert:"+cptInsert +"Delete:"+cptDelete ,"SyncTachePlanification");
                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "tacheplanification : "+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("tacheplanification:NOK "+errorMsg ,"SyncTachePlanification");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("TACHE_PLANIFICATION: Error: "+errorMsg ,"TACHE_PLANIFICATION",0));

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "TachePlanification : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("TachePlanification:NOK "+e.getMessage() ,"SyncTachePlanification");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("TACHE_PLANIFICATION: Error: "+e.getMessage() ,"TACHE_PLANIFICATION",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "TachePlanification : "+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("TachePlanification:NOK "+error.getMessage() ,"SyncTachePlanification");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("TACHE_PLANIFICATION: Error: "+error.getMessage() ,"TACHE_PLANIFICATION",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //Map<String, String> params = new HashMap<String, String>();
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    TachePlanificationManager tachePlanificationManager  = new TachePlanificationManager(context);
                    List<TachePlanification> tachePlanifications =tachePlanificationManager.getList();

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(tachePlanifications));

                    //Log.d("salim JSON TACHE PLANIFICATION",tachePlanifications.toString());
                    //Log.d("salim JSON TACHE PLANIFICATION",gson.toJson(tachePlanifications).toString());
                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


}
