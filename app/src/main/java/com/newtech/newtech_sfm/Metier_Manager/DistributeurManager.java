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
import com.newtech.newtech_sfm.Metier.Distributeur;
import com.newtech.newtech_sfm.Metier.LogSync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TONPC on 28/07/2017.
 */

public class DistributeurManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_DISTRIBUTEUR = "distributeur";

    private static final String
            KEY_DISTRIBUTEUR_CODE = "DISTRIBUTEUR_CODE",
            KEY_DISTRIBUTEUR_NOM = "DISTRIBUTEUR_NOM",
            KEY_REGION_CODE = "REGION_CODE",
            KEY_ZONE_CODE = "ZONE_CODE",
            KEY_GERANT_NOM = "GERANT_NOM",
            KEY_GERANT_TELEPHONE = "GERANT_TELEPHONE",
            KEY_GERANT_FIXE = "GERANT_FIXE",
            KEY_GERANT_EMAIL = "GERANT_EMAIL",
            KEY_ADRESSE_NR = "ADRESSE_NR",
            KEY_ADRESSE_RUE = "ADRESSE_RUE",
            KEY_ADRESSE_QUARTIER = "ADRESSE_QUARTIER",
            KEY_DATE_CREATION = "DATE_CREATION",
            KEY_CREATEUR_CODE = "CREATEUR_CODE",
            KEY_INACTIF = "INACTIF",
            KEY_INACTIF_RAISON = "INACTIF_RAISON",
            KEY_CODAGE = "CODAGE",
            KEY_CHANNEL_CODE = "CHANNEL_CODE",
            KEY_RANG = "RANG",
            KEY_DISTRIBUTEUR_ENTETE = "DISTRIBUTEUR_ENTETE",
            KEY_DISTRIBUTEUR_PIED = "DISTRIBUTEUR_PIED",
            KEY_VERSION = "VERSION";

    public static String CREATE_DISTRIBUTEUR_TABLE="CREATE TABLE " + TABLE_DISTRIBUTEUR + "("
            +KEY_DISTRIBUTEUR_CODE + " TEXT PRIMARY KEY,"
            +KEY_DISTRIBUTEUR_NOM + " TEXT,"
            +KEY_REGION_CODE + " TEXT,"
            +KEY_ZONE_CODE + " TEXT,"
            +KEY_GERANT_NOM + " TEXT,"
            +KEY_GERANT_TELEPHONE + " TEXT,"
            +KEY_GERANT_FIXE + " TEXT,"
            +KEY_GERANT_EMAIL + " TEXT,"
            +KEY_ADRESSE_NR + " NUMERIC,"
            +KEY_ADRESSE_RUE + " TEXT,"
            +KEY_ADRESSE_QUARTIER + " TEXT,"
            +KEY_DATE_CREATION + " TEXT,"
            +KEY_CREATEUR_CODE + " TEXT,"
            +KEY_INACTIF + " NUMERIC,"
            +KEY_INACTIF_RAISON + " TEXT,"
            +KEY_CODAGE+ " TEXT,"
            +KEY_CHANNEL_CODE+ " TEXT,"
            +KEY_RANG + " NUMERIC,"
            +KEY_DISTRIBUTEUR_ENTETE+ " TEXT,"
            +KEY_DISTRIBUTEUR_PIED+ " TEXT,"
            +KEY_VERSION + " TEXT" + ")";


    public DistributeurManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE);
            db.execSQL(CREATE_DISTRIBUTEUR_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database tables created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISTRIBUTEUR);
        onCreate(db);

    }

    public void add(Distributeur distributeur) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DISTRIBUTEUR_CODE, distributeur.getDISTRIBUTEUR_CODE());
        values.put(KEY_DISTRIBUTEUR_NOM, distributeur.getDISTRIBUTEUR_NOM());
        values.put(KEY_REGION_CODE, distributeur.getREGION_CODE());
        values.put(KEY_ZONE_CODE, distributeur.getZONE_CODE());
        values.put(KEY_GERANT_NOM, distributeur.getGERANT_NOM());
        values.put(KEY_GERANT_TELEPHONE, distributeur.getGERANT_TELEPHONE());
        values.put(KEY_GERANT_FIXE, distributeur.getGERANT_FIXE());
        values.put(KEY_GERANT_EMAIL, distributeur.getGERANT_EMAIL());
        values.put(KEY_ADRESSE_NR, distributeur.getADRESSE_NR());
        values.put(KEY_ADRESSE_RUE, distributeur.getADRESSE_RUE());
        values.put(KEY_ADRESSE_QUARTIER, distributeur.getADRESSE_QUARTIER());
        values.put(KEY_DATE_CREATION, distributeur.getDATE_CREATION());
        values.put(KEY_CREATEUR_CODE, distributeur.getCREATEUR_CODE());
        values.put(KEY_INACTIF, distributeur.getINACTIF());
        values.put(KEY_INACTIF_RAISON, distributeur.getINACTIF_RAISON());
        values.put(KEY_CODAGE, distributeur.getCODAGE());
        values.put(KEY_CHANNEL_CODE,distributeur.getCHANNEL_CODE());
        values.put(KEY_RANG, distributeur.getRANG());
        values.put(KEY_DISTRIBUTEUR_ENTETE, distributeur.getDISTRIBUTEUR_ENTETE());
        values.put(KEY_DISTRIBUTEUR_PIED, distributeur.getDISTRIBUTEUR_PIED());
        values.put(KEY_VERSION, distributeur.getVERSION());


        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_DISTRIBUTEUR, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New distributeurs inserted into sqlite: " + id);
    }

    public Distributeur get(String distributeur_code) {
        Distributeur distributeur = new Distributeur();

        String selectQuery = "SELECT  * FROM " + TABLE_DISTRIBUTEUR+" WHERE "+KEY_DISTRIBUTEUR_CODE+"='"+distributeur_code+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if( cursor != null && cursor.moveToFirst() ) {
            distributeur.setDISTRIBUTEUR_CODE(cursor.getString(0));
            distributeur.setDISTRIBUTEUR_NOM(cursor.getString(1));
            distributeur.setREGION_CODE(cursor.getString(2));
            distributeur.setZONE_CODE(cursor.getString(3));
            distributeur.setGERANT_NOM(cursor.getString(4));
            distributeur.setGERANT_TELEPHONE(cursor.getString(5));
            distributeur.setGERANT_FIXE(cursor.getString(6));
            distributeur.setGERANT_EMAIL(cursor.getString(7));
            distributeur.setADRESSE_NR(cursor.getInt(8));
            distributeur.setADRESSE_RUE(cursor.getString(9));
            distributeur.setADRESSE_QUARTIER(cursor.getString(10));
            distributeur.setDATE_CREATION(cursor.getString(11));
            distributeur.setCREATEUR_CODE(cursor.getString(12));
            distributeur.setINACTIF(cursor.getInt(13));
            distributeur.setINACTIF_RAISON(cursor.getString(14));
            distributeur.setCODAGE(cursor.getString(15));
            distributeur.setCHANNEL_CODE(cursor.getString(16));
            distributeur.setRANG(cursor.getString(17));
            distributeur.setDISTRIBUTEUR_ENTETE(cursor.getString(18));
            distributeur.setDISTRIBUTEUR_PIED(cursor.getString(19));
            distributeur.setVERSION(cursor.getString(20));

        }
        //returner un distributeur;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching distributeurs from Sqlite: ");
        return distributeur;
    }

    public ArrayList<Distributeur> getList() {
        ArrayList<Distributeur> distributeurs = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_DISTRIBUTEUR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Distributeur distributeur = new Distributeur();
                distributeur.setDISTRIBUTEUR_CODE(cursor.getString(0));
                distributeur.setDISTRIBUTEUR_NOM(cursor.getString(1));
                distributeur.setREGION_CODE(cursor.getString(2));
                distributeur.setZONE_CODE(cursor.getString(3));
                distributeur.setGERANT_NOM(cursor.getString(4));
                distributeur.setGERANT_TELEPHONE(cursor.getString(5));
                distributeur.setGERANT_FIXE(cursor.getString(6));
                distributeur.setGERANT_EMAIL(cursor.getString(7));
                distributeur.setADRESSE_NR(cursor.getInt(8));
                distributeur.setADRESSE_RUE(cursor.getString(9));
                distributeur.setADRESSE_QUARTIER(cursor.getString(10));
                distributeur.setDATE_CREATION(cursor.getString(11));
                distributeur.setCREATEUR_CODE(cursor.getString(12));
                distributeur.setINACTIF(cursor.getInt(13));
                distributeur.setINACTIF_RAISON(cursor.getString(14));
                distributeur.setCODAGE(cursor.getString(15));
                distributeur.setCHANNEL_CODE(cursor.getString(16));
                distributeur.setRANG(cursor.getString(17));
                distributeur.setDISTRIBUTEUR_ENTETE(cursor.getString(18));
                distributeur.setDISTRIBUTEUR_PIED(cursor.getString(19));
                distributeur.setVERSION(cursor.getString(20));

                distributeurs.add(distributeur);
            }while(cursor.moveToNext());
        }

        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching distributeurs from Sqlite: ");
        return distributeurs;
    }

    public void deleteDistributeurs() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_DISTRIBUTEUR, null, null);
        db.close();
        Log.d(TAG, "Deleted all distributeurs info from sqlite");
    }

    public int delete(String distributeur_code,String version) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_DISTRIBUTEUR,KEY_DISTRIBUTEUR_CODE+"=? AND "+KEY_VERSION+"=?",new String[]{distributeur_code,version});
    }

    public static void synchronisationDistributeur(final Context context){

        String tag_string_req = "DISTRIBUTEUR";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_DISTRIBUTEUR, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("distributeur", "onResponse: "+response);
                //ouvrir le logManager
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray distributeurs = jObj.getJSONArray("Distributeurs");
                        //Toast.makeText(context, "Nombre de distributeurs " +distributeurs.length(), Toast.LENGTH_SHORT).show();
                        int cptInsert = 0,cptDeleted = 0;
                        //Ajout/Suppression/modification des distributeurs dans la base de données.
                        for (int i = 0; i < distributeurs.length(); i++) {
                            JSONObject unDistributeur = distributeurs.getJSONObject(i);
                            DistributeurManager distributeurManager = new DistributeurManager(context);
                            if(unDistributeur.getString("OPERATION").equals("DELETE")){
                                distributeurManager.delete(unDistributeur.getString("DISTRIBUTEUR_CODE"),unDistributeur.getString("VERSION"));
                                cptDeleted++;
                            }
                            else {
                                distributeurManager.add(new Distributeur(unDistributeur));
                                cptInsert++;
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("DISTRIBUTEUR: Inserted: "+cptInsert +" Deleted: "+cptDeleted ,"DISTRIBUTEUR",1));

                        }

                        //logM.add("DISTRIBUTEURS:OK Insert:"+cptInsert +"Deleted:"+cptDeleted ,"SyncDistributeur");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //logM.add("DISTRIBUTEUR:NOK Insert "+errorMsg ,"SyncDistributeur");
                        //Toast.makeText(context,"DISTRIBUTEUR:"+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("DISTRIBUTEUR: Error: "+errorMsg,"DISTRIBUTEUR",0));

                        }

                    }

                } catch (JSONException e) {
                    //logM.add("DISTRIBUTEURS : NOK Insert "+e.getMessage() ,"SyncDistributeur");
                    e.printStackTrace();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("DISTRIBUTEUR: Error: "+e.getMessage(),"DISTRIBUTEUR",0));

                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "DISTRIBUTEURS:"+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("DISTRIBUTEURS : NOK Inserted "+error.getMessage() ,"SyncDistributeur");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("DISTRIBUTEUR: Error: "+error.getMessage(),"DISTRIBUTEUR",0));

                }


            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    DistributeurManager distributeurManager  = new DistributeurManager(context);
                    List<Distributeur> distributeurs=distributeurManager.getList();

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    //Log.d("UTILISATEUR_CODE TACHE SYNC",gson.toJson(pref.getString("UTILISATEUR_CODE",null)));
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(distributeurs));
                    //Log.d("salim JSON",taches.toString());
                    //Log.d("salim JSON",gson.toJson(taches).toString());

                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
