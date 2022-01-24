package com.newtech.newtech_sfm.Metier_Manager;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.content.ContextCompat;

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
import com.newtech.newtech_sfm.Metier.UtilisateurUnique;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TONPC on 06/09/2017.
 */

public class UtilisateurUniqueManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Article table name
    public static final String TABLE_UTILISATEURUNIQUE = "utilisateurunique";

    public UtilisateurUniqueManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //table utilisateur unique column
    private static final String
            KEY_UTILISATEUR_CODE = "UTILISATEUR_CODE",
            KEY_UTILISATEUR_NOM = "UTILISATEUR_NOM",
            KEY_UTILISATEUR_UNIQUECODE = "UTILISATEUR_UNIQUECODE",
            KEY_INACTIF = "INACTIF",
            KEY_INACTIF_RAISON = "INACTIF_RAISON",
            KEY_VERSION = "VERSION";

    public static String CREATE_UTILISATEURUNIQUE_TABLE = "CREATE TABLE " + TABLE_UTILISATEURUNIQUE + "("
            + KEY_UTILISATEUR_CODE + " TEXT PRIMARY KEY,"
            + KEY_UTILISATEUR_NOM + " TEXT,"
            + KEY_UTILISATEUR_UNIQUECODE + " TEXT,"
            + KEY_INACTIF + " TEXT,"
            + KEY_INACTIF_RAISON + " TEXT,"
            + KEY_VERSION + " TEXT" + ") ;";


    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_UTILISATEURUNIQUE);
            db.execSQL(CREATE_UTILISATEURUNIQUE_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database tables utilisateurunique created");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILISATEURUNIQUE);
        // Create tables again
        onCreate(db);

    }

    public void add(UtilisateurUnique utilisateurUnique) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_UTILISATEUR_CODE, utilisateurUnique.getUTILISATEUR_CODE());
        values.put(KEY_UTILISATEUR_NOM, utilisateurUnique.getUTILISATEUR_NOM());
        values.put(KEY_UTILISATEUR_UNIQUECODE, utilisateurUnique.getUTILISATEUR_UNIQUECODE());
        values.put(KEY_INACTIF, utilisateurUnique.getINACTIF());
        values.put(KEY_INACTIF_RAISON, utilisateurUnique.getINACTIF_RAISON());
        values.put(KEY_VERSION, utilisateurUnique.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_UTILISATEURUNIQUE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New utilisateurunique inserted into sqlite: " + id);
    }

    public ArrayList<UtilisateurUnique> getList() {
        ArrayList<UtilisateurUnique> utilisateurUniques = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_UTILISATEURUNIQUE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                UtilisateurUnique utilisateurUnique = new UtilisateurUnique();

                utilisateurUnique.setUTILISATEUR_CODE(cursor.getString(0));
                utilisateurUnique.setUTILISATEUR_NOM(cursor.getString(1));
                utilisateurUnique.setUTILISATEUR_UNIQUECODE(cursor.getString(2));
                utilisateurUnique.setINACTIF(cursor.getString(3));
                utilisateurUnique.setINACTIF_RAISON(cursor.getString(4));
                utilisateurUnique.setVERSION(cursor.getString(5));

                utilisateurUniques.add(utilisateurUnique);
            } while (cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching urilisateuruniques from Sqlite: ");
        return utilisateurUniques;
    }

    public UtilisateurUnique get() {

        UtilisateurUnique utilisateurUnique = new UtilisateurUnique();
        String selectQuery = "SELECT * FROM " + TABLE_UTILISATEURUNIQUE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {


            utilisateurUnique.setUTILISATEUR_CODE(cursor.getString(0));
            utilisateurUnique.setUTILISATEUR_NOM(cursor.getString(1));
            utilisateurUnique.setUTILISATEUR_UNIQUECODE(cursor.getString(2));
            utilisateurUnique.setINACTIF(cursor.getString(3));
            utilisateurUnique.setINACTIF_RAISON(cursor.getString(4));
            utilisateurUnique.setVERSION(cursor.getString(5));

        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching urilisateurunique from Sqlite: ");
        return utilisateurUnique;
    }


    public UtilisateurUnique get(String utilisateur_code) {

        String selectQuery = "SELECT * FROM " + TABLE_UTILISATEURUNIQUE + " WHERE " + KEY_UTILISATEUR_CODE + " = '" + utilisateur_code + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        UtilisateurUnique utilisateurUnique = new UtilisateurUnique();

        if (cursor != null && cursor.moveToFirst()) {

            utilisateurUnique.setUTILISATEUR_CODE(cursor.getString(0));
            utilisateurUnique.setUTILISATEUR_NOM(cursor.getString(1));
            utilisateurUnique.setUTILISATEUR_UNIQUECODE(cursor.getString(2));
            utilisateurUnique.setINACTIF(cursor.getString(3));
            utilisateurUnique.setINACTIF_RAISON(cursor.getString(4));
            utilisateurUnique.setVERSION(cursor.getString(5));
        }

        cursor.close();
        db.close();
        return utilisateurUnique;

    }

    public void deleteUtilisateurUniques() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_UTILISATEURUNIQUE, null, null);
        db.close();
        Log.d(TAG, "Deleted all utilisateurUniques info from sqlite");
    }

    public int delete(String utilisateur_code, String version) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_UTILISATEURUNIQUE, KEY_UTILISATEUR_CODE + "=? AND " + KEY_VERSION + "=?", new String[]{version});
    }


    public int count() {

        int compteur = 0;
        String selectQuery = "SELECT COUNT(*) FROM " + TABLE_UTILISATEURUNIQUE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            compteur = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return compteur;
    }

    public static void synchronisationUtilisateurUnique(final Context context) {

        String tag_string_req = "UTILISATEUR_UNIQUE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_UTILISATEURUNIQUE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM = new LogSyncManager(context);
                Log.d("UtilisateurUnique", "onResponse: " + response);
                int cptInsert = 0, cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error == 1) {
                        JSONArray UtilisateurUnique = jObj.getJSONArray("utilisateurunique");
                        // Toast.makeText(context, "nombre d'UtilisateurUnique "+UtilisateurUnique.length()  , Toast.LENGTH_SHORT).show();
                        if (UtilisateurUnique.length() > 0) {
                            UtilisateurUniqueManager utilisateurUniqueManager = new UtilisateurUniqueManager(context);
                            for (int i = 0; i < UtilisateurUnique.length(); i++) {
                                JSONObject utilisateurUnique = UtilisateurUnique.getJSONObject(i);
                                if (utilisateurUnique.getString("OPERATION").equals("DELETE")) {
                                    utilisateurUniqueManager.delete(utilisateurUnique.getString("UTILISATEUR_CODE"), utilisateurUnique.getString("VERSION"));
                                    cptDelete++;
                                    Log.d("utilisateurUnique", "onResponse: deleted");
                                } else {
                                    utilisateurUniqueManager.add(new UtilisateurUnique(utilisateurUnique));
                                    cptInsert++;
                                    Log.d("utilisateurUnique", "onResponse: inserted");
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("UTILISATEUR_UNIQUE: Inserted: " + cptInsert + " Deleted: " + cptDelete, "UTILISATEUR_UNIQUE", 1));

                        }

                        //logM.add("UtilisateurUnique:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncUtilisateurUnique");
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(context,"UtilisateurUnique : "+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("UtilisateurUnique:NOK "+errorMsg ,"SyncUtilisateurUnique");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("UTILISATEUR_UNIQUE: Error: " + errorMsg, "UTILISATEUR_UNIQUE", 0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "UtilisateurUnique : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("UtilisateurUnique:NOK "+e.getMessage() ,"SyncUtilisateurUnique");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("UTILISATEUR_UNIQUE: Error: " + e.getMessage(), "UTILISATEUR_UNIQUE", 0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "UtilisateurUnique : "+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("UtilisateurUnique:NOK "+error.getMessage() ,"SyncUtilisateurUnique");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("UTILISATEUR_UNIQUE: Error: " + error.getMessage(), "UTILISATEUR_UNIQUE", 0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                HashMap<String, String> arrayFinale = new HashMap<>();
                if (pref.getString("is_login", null).equals("ok")) {
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
                    String imei = "";
                    String uid = "";

                    uid = Settings.Secure.getString(context.getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {

                        if (ContextCompat.checkSelfPermission(context,
                                Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                            imei = telephonyManager.getDeviceId();

                        }

                    }



                    UtilisateurUniqueManager utilisateurUniqueManager = new UtilisateurUniqueManager(context);
                    ArrayList<UtilisateurUnique> utilisateurUniques = utilisateurUniqueManager.getList();

                    Log.d("UtilisateurUnique", "getParams: "+imei.toString());
                    HashMap<String,String > TabParams =new HashMap<>();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        TabParams.put("IMEI", uid);
                    } else {
                        TabParams.put("IMEI", imei);
                    }

                    TabParams.put("IMEI", imei);
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    //Log.d("UTILISATEUR_CODE TACHE SYNC",gson.toJson(pref.getString("UTILISATEUR_CODE",null)));
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(utilisateurUniques));
                }

                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
