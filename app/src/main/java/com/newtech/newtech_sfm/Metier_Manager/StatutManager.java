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
import com.newtech.newtech_sfm.Activity.SyncV2Activity;
import com.newtech.newtech_sfm.Configuration.AppConfig;
import com.newtech.newtech_sfm.Configuration.AppController;
import com.newtech.newtech_sfm.Configuration.SQLiteHandler;
import com.newtech.newtech_sfm.Metier.LogSync;
import com.newtech.newtech_sfm.Metier.Statut;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stagiaireit2 on 08/08/2016.
 */
public class StatutManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Article table name
    public static final String TABLE_STATUT = "statut";

    //table tournee column
    private static final String
    KEY_STATUT_CODE="STATUT_CODE",
    KEY_STATUT_NOM="STATUT_NOM",
    KEY_STATUT_CATEGORIE="STATUT_CATEGORIE",
    KEY_DESCRIPTION="DESCRIPTION",
    KEY_RANG="RANG",
    KEY_VERSION="VERSION";

    public static  String CREATE_STATUT_TABLE = "CREATE TABLE " + TABLE_STATUT + "("
            +KEY_STATUT_CODE + " TEXT PRIMARY KEY,"
            +KEY_STATUT_NOM + " TEXT,"
            +KEY_STATUT_CATEGORIE + " TEXT,"
            +KEY_DESCRIPTION + " TEXT,"
            +KEY_RANG + " NUMERIC,"
            +KEY_VERSION + " TEXT "+ ")";

    public StatutManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAMILLE);
            db.execSQL(CREATE_STATUT_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database statut tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATUT);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing Articles details in database
     * */
    public void add(Statut statut) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STATUT_CODE, statut.getSTATUT_CODE());
        values.put(KEY_STATUT_NOM,statut.getSTATUT_NOM());
        values.put(KEY_STATUT_CATEGORIE, statut.getSTATUT_CATEGORIE());
        values.put(KEY_DESCRIPTION, statut.getDESCRIPTION());
        values.put(KEY_RANG, statut.getRANG());
        values.put(KEY_VERSION, statut.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_STATUT, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New statuts inserted into sqlite: " + id);
    }

    /**
     * Getting all tournee from database
     * */

    public Statut get(String STATUT_CODE) {

        Statut statut = new Statut();
        String selectQuery = "SELECT * FROM " + TABLE_STATUT+ " WHERE "+ KEY_STATUT_CODE +" = '"+STATUT_CODE+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            statut.setSTATUT_CODE(cursor.getString(0));
            statut.setSTATUT_NOM(cursor.getString(1));
            statut.setSTATUT_CATEGORIE(cursor.getString(2));
            statut.setDESCRIPTION(cursor.getString(3));
            statut.setRANG(cursor.getInt(4));
            statut.setVERSION(cursor.getString(5));
        }

        cursor.close();
        db.close();
        Log.d("STATUT MANAGER", "fetching ");
        return statut;

    }

    public ArrayList<Statut> getList() {
        ArrayList<Statut> statuts = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_STATUT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Statut statut= new Statut();
                statut.setSTATUT_CODE(cursor.getString(0));
                statut.setSTATUT_NOM(cursor.getString(1));
                statut.setSTATUT_CATEGORIE(cursor.getString(2));
                statut.setDESCRIPTION(cursor.getString(3));
                statut.setRANG(cursor.getInt(4));
                statut.setVERSION(cursor.getString(5));

                statuts.add(statut);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching all statut from Sqlite: ");
        return statuts;
    }

    public ArrayList<Statut> getStatutCode_Version_List() {
        ArrayList<Statut> statuts = new ArrayList<>();

        String selectQuery = "SELECT "+ KEY_STATUT_CODE+" , " +KEY_VERSION +  " FROM " + TABLE_STATUT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Statut statut = new Statut();
                statut.setSTATUT_CODE(cursor.getString(0));
                statut.setVERSION(cursor.getString(1));
                statuts.add(statut);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version  statuts from Sqlite: ");
        return statuts;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteStatuts() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_STATUT, null, null);
        db.close();
        Log.d(TAG, "Deleted all statut info from sqlite");
    }

    public int delete(String statutCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_STATUT,KEY_STATUT_CODE+"=?",new String[]{statutCode});
    }


    public ArrayList<Statut> getStatut_textList() {
        ArrayList<Statut> statuts = new ArrayList<>();
            String selectQuery = "SELECT "+ KEY_STATUT_CODE+ " , "+ KEY_STATUT_NOM +  " FROM " + TABLE_STATUT;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if( cursor != null && cursor.moveToFirst() ) {
                do {
                    Statut statut = new Statut();
                    statut.setSTATUT_CODE(cursor.getString(0));
                    statut.setSTATUT_NOM(cursor.getString(1));
                    statuts.add(statut);
                }while(cursor.moveToNext());
            }
            //returner la listTournees;
            cursor.close();
            db.close();
            Log.d(TAG, "Fetching code/version statuts from Sqlite: ");
            return statuts;
        }


    public static void synchronisationStatut(final Context context){

        String tag_string_req = "STATUT";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_STATUT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Statut", "onResponse: "+response);
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray Statuts = jObj.getJSONArray("Statuts");
                        //Toast.makeText(context, "nombre de Statuts  "+Statuts.length()  , Toast.LENGTH_SHORT).show();
                        if(Statuts.length()>0) {
                            StatutManager statutManager = new StatutManager(context);
                            for (int i = 0; i < Statuts.length(); i++) {
                                JSONObject uneStatut = Statuts.getJSONObject(i);
                                if (uneStatut.getString("OPERATION").equals("DELETE")) {
                                    statutManager.delete(uneStatut.getString(KEY_STATUT_CODE));
                                    cptDelete++;
                                } else {
                                    statutManager.add(new Statut(uneStatut));cptInsert++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STATUT: Inserted: "+cptInsert +" Deleted: "+cptDelete ,"STATUT",1));

                        }
                        //logM.add("Statuts:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncStatut");

                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(context, "Statut : "+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("Statut:NOK "+errorMsg ,"SyncStatut");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STATUT: Error: "+errorMsg ,"STATUT",0));

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "Statut : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("Statut:NOK "+e.getMessage() ,"SyncStatut");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STATUT: Error: "+e.getMessage() ,"STATUT",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Statut : "+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("Statut:NOK "+error.getMessage() ,"SyncStatut");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STATUT: Error: "+error.getMessage() ,"STATUT",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    StatutManager statutManager = new StatutManager(context);
                    List<Statut> statuts =statutManager.getStatutCode_Version_List();
                    for (int i = 0; i < statuts.size(); i++) {
                        Log.d("test statut-@-version",statuts.get(i).getSTATUT_CODE()+"-@@-" +statuts.get(i).getVERSION());
                        if(statuts.get(i).getSTATUT_CODE()!=null && statuts.get(i).getVERSION() != null ) {
                            params.put(statuts.get(i).getSTATUT_CODE(), statuts.get(i).getVERSION());
                        }
                    }
                }
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}


