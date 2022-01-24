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
import com.newtech.newtech_sfm.Metier.VisiteResultat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TONPC on 22/02/2017.
 */

public class VisiteResultatManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_VISITERESULTAT = "visiteresultat";


    public VisiteResultatManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //table visiteresultat column
    private static final String
            KEY_ID ="ID",
            KEY_VISITERESULTAT_CODE ="VISITERESULTAT_CODE",
            KEY_VISITERESULTAT_NOM = "VISITERESULTAT_NOM",
            KEY_RANG = "RANG",
            KEY_VERSION="VERSION";

    public static String CREATE_VISITERESULTAT_TABLE = "CREATE TABLE " + TABLE_VISITERESULTAT + "("
            +KEY_ID + " INTEGER PRIMARY KEY,"
            +KEY_VISITERESULTAT_CODE + " INTEGER,"
            + KEY_VISITERESULTAT_NOM + " TEXT,"
            + KEY_RANG + " INTEGER ,"
            +KEY_VERSION+ " TEXT " +");";


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_VISITETOURNEE);
            db.execSQL(CREATE_VISITERESULTAT_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database table visiteresultat created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VISITERESULTAT);
        // Create tables again
        onCreate(db);

    }

    //ADD VISITERESULTAT TO THE TABLE

    public void add(VisiteResultat visiteresultat) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_VISITERESULTAT_CODE, visiteresultat.getVISITERESULTAT_CODE());
        values.put(KEY_VISITERESULTAT_NOM, visiteresultat.getVISITERESULTAT_NOM());
        values.put(KEY_RANG, visiteresultat.getRANG());
        values.put(KEY_VERSION, visiteresultat.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_VISITERESULTAT, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New VISITERESULTAT inserted into sqlite: " + id);
    }

    public ArrayList<VisiteResultat> getList() {
        ArrayList<VisiteResultat> listVisiteResultat = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_VISITERESULTAT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                VisiteResultat vr = new VisiteResultat();
                vr.setID(cursor.getInt(0));
                vr.setVISITERESULTAT_CODE(cursor.getString(1));
                vr.setVISITERESULTAT_NOM(cursor.getString(2));
                vr.setRANG(cursor.getInt(3));
                vr.setVERSION(cursor.getString(4));
                listVisiteResultat.add(vr);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version VisiteResultat from Sqlite: ");
        return listVisiteResultat;
    }

    public ArrayList<VisiteResultat> getListNotOk() {
        ArrayList<VisiteResultat> listVisiteResultat = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_VISITERESULTAT +" WHERE "+ KEY_VISITERESULTAT_CODE +" != 1 "  ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                VisiteResultat vr = new VisiteResultat();
                vr.setID(cursor.getInt(0));
                vr.setVISITERESULTAT_CODE(cursor.getString(1));
                vr.setVISITERESULTAT_NOM(cursor.getString(2));
                vr.setRANG(cursor.getInt(3));
                listVisiteResultat.add(vr);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version VisiteResultat from Sqlite: ");
        return listVisiteResultat;
    }

    public VisiteResultat  get( String VISITERESULTAT_CODE) {

        String selectQuery = "SELECT * FROM " + TABLE_VISITERESULTAT + " WHERE "+ KEY_VISITERESULTAT_CODE +" = '"+VISITERESULTAT_CODE+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        VisiteResultat visiteresultat = new VisiteResultat();

        if( cursor != null && cursor.moveToFirst() ) {
            VisiteResultat vr = new VisiteResultat();
            visiteresultat.setID(cursor.getInt(0));
            visiteresultat.setVISITERESULTAT_CODE(cursor.getString(1));
            visiteresultat.setVISITERESULTAT_NOM(cursor.getString(2));
            visiteresultat.setRANG(cursor.getInt(3));
            vr.setVERSION(cursor.getString(4));
        }

        cursor.close();
        db.close();
        return visiteresultat;

    }


    public ArrayList<VisiteResultat> getvisiteResultatCode_Version_List() {
        ArrayList<VisiteResultat> listVisiteResultat = new ArrayList<>();

        String selectQuery = "SELECT "+ KEY_VISITERESULTAT_CODE+","+KEY_VERSION +  " FROM " + TABLE_VISITERESULTAT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                VisiteResultat vr = new VisiteResultat();
                vr.setID(cursor.getInt(0));
                vr.setVISITERESULTAT_CODE(cursor.getString(1));
                vr.setVISITERESULTAT_NOM(cursor.getString(2));
                vr.setRANG(cursor.getInt(3));
                vr.setVERSION(cursor.getString(4));
                listVisiteResultat.add(vr);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version VisiteResultats from Sqlite: ");
        return listVisiteResultat;
    }

    /*public int delete(String visiteresultatCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_VISITERESULTAT,KEY_VISITERESULTAT_CODE+"=?",new String[]{visiteresultatCode});
    }*/

    public int delete(String visiteresultatCode ,String version ) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_VISITERESULTAT,KEY_VISITERESULTAT_CODE+"=?  AND "+KEY_VERSION+"=?" ,new String[]{visiteresultatCode,version});

    }


    public static void synchronisationVisiteResultat(final Context context){

        String tag_string_req = "VISITE_RESULTAT";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_VISITERESULTAT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    Log.d("server visiteResultats",response);
                    JSONObject jObj = new JSONObject(response);

                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray VisiteResultats = jObj.getJSONArray("Visiteresultats");
                        //Toast.makeText(context, "nombre de VisiteResultats  "+VisiteResultats.length()  , Toast.LENGTH_SHORT).show();
                        if(VisiteResultats.length()>0) {
                            VisiteResultatManager visiteresultatManager = new VisiteResultatManager(context);
                            for (int i = 0; i < VisiteResultats.length(); i++) {
                                JSONObject visiteresultat = VisiteResultats.getJSONObject(i);
                                if (visiteresultat.getString("OPERATION").equals("DELETE")) {
                                    visiteresultatManager.delete(visiteresultat.getString("VISITERESULTAT_CODE"),visiteresultat.getString("VERSION"));
                                    cptDelete++;
                                } else {
                                    visiteresultatManager.add(new VisiteResultat(visiteresultat));
                                    cptInsert++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("VISITE_RESULTAT: Inserted: "+cptInsert +" Deleted: "+cptDelete ,"VISITE_RESULTAT",1));

                        }

                        //logM.add("VisiteResultat:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncVisiteResultat");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(context, "visiteResultat : "+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("VisiteResultat:NOK "+errorMsg ,"SyncVisiteResultat");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("VISITE_RESULTAT: Error: "+errorMsg,"VISITE_RESULTAT",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "VisiteResultat : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("VisiteResultat:NOK "+e.getMessage() ,"SyncVisiteResultat");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("VISITE_RESULTAT: Error: "+e.getMessage(),"VISITE_RESULTAT",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "VisiteResultat : "+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("VisiteResultat:NOK "+error.getMessage() ,"SyncVisiteResultat");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("VISITE_RESULTAT: Error: "+error.getMessage(),"VISITE_RESULTAT",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //Map<String, String> params = new HashMap<String, String>();
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    VisiteResultatManager visiteresultatManager  = new VisiteResultatManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableClientN");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(visiteresultatManager.getList()));
                    Log.d("mobile visiteResultats",visiteresultatManager.getList().toString());
                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
