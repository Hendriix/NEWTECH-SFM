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
import com.newtech.newtech_sfm.Metier.Parametre;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stagiaireit2 on 08/08/2016.
 */
public class ParametreManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Article table name
    public static final String TABLE_PARAMETRE = "parametre";

    //table tournee column
    private static final String
    KEY_VARIABLE="VARIABLE",
    KEY_VALEUR="VALEUR",
    KEY_VERSION="VERSION";

    public static  String CREATE_PARAMETRE_TABLE = "CREATE TABLE " + TABLE_PARAMETRE + "("
            +KEY_VARIABLE + " TEXT PRIMARY KEY,"
            +KEY_VALEUR + " TEXT,"
            +KEY_VERSION + " TEXT "+ ")";

    public ParametreManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAMILLE);
            db.execSQL(CREATE_PARAMETRE_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database parametre tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARAMETRE);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing Articles details in database
     * */
    public void add(Parametre parametre) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_VARIABLE, parametre.getVARIABLE());
        values.put(KEY_VALEUR,parametre.getVALEUR());
        values.put(KEY_VERSION, parametre.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_PARAMETRE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New Parametres inserted into sqlite: " + id);
    }

    /**
     * Getting all tournee from database
     * */

    public Parametre get(String VARIABLE) {

        Parametre parametre = new Parametre();
        String selectQuery = "SELECT * FROM " + TABLE_PARAMETRE+ " WHERE "+ KEY_VARIABLE +" = '"+VARIABLE+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            parametre.setVARIABLE(cursor.getString(0));
            parametre.setVALEUR(cursor.getString(1));
            parametre.setVERSION(cursor.getString(2));
        }

        cursor.close();
        db.close();
        Log.d("PARAMETRE MANAGER", "fetching ");
        return parametre;

    }

    public ArrayList<Parametre> getList() {
        ArrayList<Parametre> parametres = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_PARAMETRE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Parametre parametre= new Parametre();
                parametre.setVARIABLE(cursor.getString(0));
                parametre.setVALEUR(cursor.getString(1));
                parametre.setVERSION(cursor.getString(2));

                parametres.add(parametre);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching all parametre from Sqlite: ");
        return parametres;
    }

    public ArrayList<Parametre> getParametreCode_Version_List() {
        ArrayList<Parametre> parametres = new ArrayList<>();

        String selectQuery = "SELECT "+ KEY_VARIABLE+" , " +KEY_VERSION +  " FROM " + TABLE_PARAMETRE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Parametre parametre = new Parametre();
                parametre.setVARIABLE(cursor.getString(0));
                parametre.setVERSION(cursor.getString(1));
                parametres.add(parametre);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version  parametres from Sqlite: ");
        return parametres;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteParametre() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_PARAMETRE, null, null);
        db.close();
        Log.d(TAG, "Deleted all parametre info from sqlite");
    }

    public int delete(String variable) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PARAMETRE,KEY_VARIABLE+"=?",new String[]{variable});
    }


    public static void synchronisationParametre(final Context context){

        String tag_string_req = "PARAMETRE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_PARAMETRE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Parametre", "onResponse: "+response);
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray Parametres = jObj.getJSONArray("Parametres");
                        //Toast.makeText(context, "nombre de Parametres  "+Parametres.length()  , Toast.LENGTH_SHORT).show();
                        if(Parametres.length()>0) {
                            ParametreManager parametreManager = new ParametreManager(context);
                            for (int i = 0; i < Parametres.length(); i++) {
                                JSONObject uneParametre = Parametres.getJSONObject(i);
                                if (uneParametre.getString("OPERATION").equals("DELETE")) {
                                    parametreManager.delete(uneParametre.getString(KEY_VARIABLE));
                                    cptDelete++;
                                } else {
                                    parametreManager.add(new Parametre(uneParametre));
                                    cptInsert++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PARAMETRE: Inserted: "+cptInsert +" Deleted: "+cptDelete ,"PARAMETRE",1));

                        }
                        //logM.add("Parametres:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncParametre");

                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(context, "Parametre : "+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("Parametre:NOK "+errorMsg ,"SyncParametre");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PARAMETRE: Error: "+errorMsg ,"PARAMETRE",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "Parametre : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("Parametre:NOK "+e.getMessage() ,"SyncParametre");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PARAMETRE: Error: "+e.getMessage() ,"PARAMETRE",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Parametre : "+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("Parametre:NOK "+error.getMessage() ,"SyncParametre");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PARAMETRE: Error: "+error.getMessage() ,"PARAMETRE",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    ParametreManager parametreManager = new ParametreManager(context);
                    List<Parametre> Parametres =parametreManager.getParametreCode_Version_List();
                    for (int i = 0; i < Parametres.size(); i++) {
                        Log.d("Parametre-@-version",Parametres.get(i).getVARIABLE()+"-@@-" +Parametres.get(i).getVERSION());
                        if(Parametres.get(i).getVARIABLE()!=null && Parametres.get(i).getVERSION() != null ) {
                            params.put(Parametres.get(i).getVARIABLE(), Parametres.get(i).getVERSION());
                        }
                    }
                }
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}


