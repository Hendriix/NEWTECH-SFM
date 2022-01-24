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
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Activity.SyncV2Activity;
import com.newtech.newtech_sfm.Configuration.AppConfig;
import com.newtech.newtech_sfm.Configuration.AppController;
import com.newtech.newtech_sfm.Configuration.SQLiteHandler;
import com.newtech.newtech_sfm.Metier.LogSync;
import com.newtech.newtech_sfm.Metier.Tournee;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stagiaireit2 on 22/07/2016.
 */
public class TourneeManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Article table name
    public static final String TABLE_TOURNEE = "tournee";

//table tournee column
    private static final String
            KEY_TOURNEE_CODE ="TOURNEE_CODE",
            KEY_TOURNEE_NOM = "TOURNEE_NOM",
            KEY_DISTRIBUTEUR_CODE = "DISTRIBUTEUR_CODE",
            KEY_REGION_CODE = "REGION_CODE",
            KEY_ZONE_CODE = "ZONE_CODE",
            KEY_FREQUENCE_VISITE = "FREQUENCE_VISITE",
            KEY_VERSION = "VERSION";

    public static String CREATE_TOURNEE_TABLE = "CREATE TABLE " + TABLE_TOURNEE + "("
            +KEY_TOURNEE_CODE + " TEXT PRIMARY KEY,"
            + KEY_TOURNEE_NOM + " TEXT,"
            + KEY_DISTRIBUTEUR_CODE + " TEXT,"
            + KEY_REGION_CODE + " TEXT,"
            + KEY_ZONE_CODE + " TEXT,"
            +KEY_FREQUENCE_VISITE + " TEXT,"
            + KEY_VERSION + " TEXT" + ") ;";

    public TourneeManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_TOURNEE);
            db.execSQL(CREATE_TOURNEE_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database table tournee created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOURNEE);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing Articles details in database
     * */
    public void add(Tournee tournee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TOURNEE_CODE, tournee.getTOURNEE_CODE());
        values.put(KEY_TOURNEE_NOM,tournee.getTOURNEE_NOM());
        values.put(KEY_DISTRIBUTEUR_CODE, tournee.getDISTRIBUTEUR_CODE());
        values.put(KEY_REGION_CODE, tournee.getREGION_CODE());
        values.put(KEY_ZONE_CODE, tournee.getZONE_CODE());
        values.put(KEY_FREQUENCE_VISITE, tournee.getFREQUENCE_VISITE());
        values.put(KEY_VERSION, tournee.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_TOURNEE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New articles inserted into sqlite: " + id);
    }

    /**
     * Getting all tournee from database
     * */

    public ArrayList<Tournee> getList() {
        ArrayList<Tournee> listTournee = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_TOURNEE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Tournee cl = new Tournee();
                cl.setTOURNEE_CODE(cursor.getString(0));
                cl.setTOURNEE_NOM(cursor.getString(1));
                cl.setDISTRIBUTEUR_CODE(cursor.getString(2));
                cl.setREGION_CODE(cursor.getString(3));
                cl.setZONE_CODE(cursor.getString(4));
                cl.setFREQUENCE_VISITE(cursor.getInt(5));
                cl.setVERSION(cursor.getString(6));
                listTournee.add(cl);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version Tournees from Sqlite: ");
        return listTournee;
    }

    public Tournee  get( String TOURNEE_CODE) {

        String selectQuery = "SELECT * FROM " + TABLE_TOURNEE + " WHERE "+ KEY_TOURNEE_CODE +" = '"+TOURNEE_CODE+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Tournee tournee = new Tournee();

        if( cursor != null && cursor.moveToFirst() ) {
            tournee.setTOURNEE_CODE(cursor.getString(0));
            tournee.setTOURNEE_NOM(cursor.getString(1));
            tournee.setDISTRIBUTEUR_CODE(cursor.getString(2));
            tournee.setREGION_CODE(cursor.getString(3));
            tournee.setZONE_CODE(cursor.getString(4));
            tournee.setFREQUENCE_VISITE(cursor.getInt(5));
            tournee.setVERSION(cursor.getString(6));
        }

        cursor.close();
        db.close();
        return tournee;

    }

    public ArrayList<Tournee> getTourneeCode_Version_List() {
        ArrayList<Tournee> listTournee = new ArrayList<>();

        String selectQuery = "SELECT "+ KEY_TOURNEE_CODE+","+KEY_VERSION +  " FROM " + TABLE_TOURNEE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Tournee cl = new Tournee();
                cl.setTOURNEE_CODE(cursor.getString(0));
                cl.setVERSION(cursor.getString(1));
                listTournee.add(cl);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version Tournees from Sqlite: ");
        return listTournee;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteTournees() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_TOURNEE, null, null);
        db.close();
        Log.d(TAG, "Deleted all tournees info from sqlite");
    }

    public int delete(String tourneeCode,String version) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TOURNEE,KEY_TOURNEE_CODE+"=? AND "+KEY_VERSION+"=?",new String[]{tourneeCode,version});
    }

    public static void synchronisationTournee(final Context context){

        String tag_string_req = "TOURNEE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_TOURNEE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                Log.d("Tournee", "onResponse: "+response);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray Tournees = jObj.getJSONArray("Tournees");
                       //Toast.makeText(context, "nombre de Tournee  "+Tournees.length()  , Toast.LENGTH_SHORT).show();
                        if(Tournees.length()>0) {
                           TourneeManager tourneeManager = new TourneeManager(context);
                            for (int i = 0; i < Tournees.length(); i++) {
                                JSONObject tournee = Tournees.getJSONObject(i);
                                if (tournee.getString("OPERATION").equals("DELETE")) {
                                    tourneeManager.delete(tournee.getString("TOURNEE_CODE"),tournee.getString("VERSION"));
                                    cptInsert++;
                                } else {
                                    tourneeManager.add(new Tournee(tournee));cptDelete++;
                                  }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("TOURNEE: Inserted: "+cptInsert +" Deleted: "+cptDelete ,"TOURNEE",1));

                        }
                        //logM.add("Tournee:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncTournee");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(context, "Tournee : "+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("Tournee:NOK "+errorMsg ,"SyncTournee");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("TOURNEE: Error: "+errorMsg ,"TOURNEE",0));

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "Tournee : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("Tournee:NOK "+e.getMessage() ,"SyncTournee");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("TOURNEE: Error: "+e.getMessage() ,"TOURNEE",0));

                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Tournee : "+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("Tournee:NOK "+error.getMessage() ,"SyncTournee");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("TOURNEE: Error: "+error.getMessage() ,"TOURNEE",0));

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    Gson gson2 = new Gson();
                    String json2 = pref.getString("User", "");
                    Type type = new TypeToken<JSONObject>() {}.getType();
                     JSONObject user = gson2.fromJson(json2, type);
                    try {
                        params.put("UTILISATEUR_CODE", user.getString("UTILISATEUR_CODE"));
                    }catch (Exception e ){

                    }
                    TourneeManager tourneeManager  = new TourneeManager(context);
                    List<Tournee> listTournee=tourneeManager.getTourneeCode_Version_List();
                    for (int i = 0; i < listTournee.size(); i++) {
                        Log.d("test",listTournee.get(i).getTOURNEE_CODE()+"-@@-" +listTournee.get(i).getVERSION());
                        if(listTournee.get(i).getTOURNEE_CODE()!=null && listTournee.get(i).getVERSION() != null ) {
                            params.put(listTournee.get(i).getTOURNEE_CODE(), listTournee.get(i).getVERSION());
                        }
                    }
                }
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
