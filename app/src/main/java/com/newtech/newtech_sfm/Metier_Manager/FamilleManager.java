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
import com.newtech.newtech_sfm.Metier.Famille;
import com.newtech.newtech_sfm.Metier.LogSync;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stagiaireit2 on 08/08/2016.
 */
public class FamilleManager  extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Article table name
    public static final String TABLE_FAMILLE = "famille";

    //table tournee column
    private static final String
    KEY_FAMILLE_CODE="FAMILLE_CODE",
    KEY_FAMILLE_NOM="FAMILLE_NOM",
    KEY_FAMILLE_CATEGORIE="FAMILLE_CATEGORIE",
    KEY_DESCRIPTION="DESCRIPTION",
    KEY_INACTIF="INACTIF",
    KEY_INACTIF_RAISON="INACTIF_RAISON",
    KEY_FAMILLE_COULEUR="FAMILLE_COULEUR",
    KEY_RANG="RANG",
    KEY_VERSION="VERSION";
public static  String CREATE_FAMILLE_TABLE = "CREATE TABLE " + TABLE_FAMILLE + "("
        +KEY_FAMILLE_CODE + " TEXT PRIMARY KEY,"
        + KEY_FAMILLE_NOM + " TEXT,"
        + KEY_FAMILLE_CATEGORIE + " TEXT,"
        + KEY_DESCRIPTION + " TEXT,"
        + KEY_INACTIF + " TEXT,"
        +KEY_INACTIF_RAISON + " TEXT,"
        +KEY_VERSION + " TEXT,"
        +KEY_RANG + " NUMERIC,"
        + KEY_FAMILLE_COULEUR + " TEXT" + ")";
    public FamilleManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAMILLE);
            db.execSQL(CREATE_FAMILLE_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database FAMILLE tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAMILLE);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing Articles details in database
     * */
    public void add(Famille famille) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FAMILLE_CODE, famille.getFAMILLE_CODE());
        values.put(KEY_FAMILLE_NOM,famille.getFAMILLE_NOM());
        values.put(KEY_FAMILLE_CATEGORIE, famille.getFAMILLE_CATEGORIE());
        values.put(KEY_DESCRIPTION, famille.getDESCRIPTION());
        values.put(KEY_INACTIF, famille.getINACTIF());
        values.put(KEY_INACTIF_RAISON, famille.getINACTIF_RAISON());
        values.put(KEY_VERSION, famille.getVERSION());
        values.put(KEY_RANG, famille.getRANG());
        values.put(KEY_FAMILLE_COULEUR, famille.getFAMILLE_COULEUR());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_FAMILLE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New familles inserted into sqlite: " + id);
    }

    /**
     * Getting all tournee from database
     * */

    public Famille get(String famille_code) {
        Famille famille = new Famille();

        String selectQuery = "SELECT  * FROM " + TABLE_FAMILLE+" WHERE "+KEY_FAMILLE_CODE+"='"+famille_code+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if( cursor != null && cursor.moveToFirst() ) {

            famille.setFAMILLE_CODE(cursor.getString(0));
            famille.setFAMILLE_NOM(cursor.getString(1));
            famille.setFAMILLE_CATEGORIE(cursor.getString(2));
            famille.setDESCRIPTION(cursor.getString(3));
            famille.setINACTIF(cursor.getString(4));
            famille.setINACTIF_RAISON(cursor.getString(5));
            famille.setVERSION(cursor.getString(6));
            famille.setRANG(cursor.getString(7));
            famille.setFAMILLE_COULEUR(cursor.getString(8));

        }
        //returner un distributeur;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching distributeurs from Sqlite: ");
        return famille;
    }

    public ArrayList<Famille> getList() {
        ArrayList<Famille> listFamilles = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_FAMILLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Famille famille = new Famille();
                famille.setFAMILLE_CODE(cursor.getString(0));
                famille.setFAMILLE_NOM(cursor.getString(1));
                famille.setFAMILLE_CATEGORIE(cursor.getString(2));
                famille.setDESCRIPTION(cursor.getString(3));
                famille.setINACTIF(cursor.getString(4));
                famille.setINACTIF_RAISON(cursor.getString(5));
                famille.setVERSION(cursor.getString(6));
                famille.setRANG(cursor.getString(7));
                famille.setFAMILLE_COULEUR(cursor.getString(8));

                listFamilles.add(famille);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching all famille from Sqlite: ");
        return listFamilles;
    }

    public ArrayList<Famille> getFamilleCode_Version_List() {
        ArrayList<Famille> listFamille = new ArrayList<>();

        String selectQuery = "SELECT "+ KEY_FAMILLE_CODE+" , " +KEY_VERSION +  " FROM " + TABLE_FAMILLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Famille famille = new Famille();
                famille.setFAMILLE_CODE(cursor.getString(0));
                famille.setVERSION(cursor.getString(1));
                listFamille.add(famille);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version  Famille from Sqlite: ");
        return listFamille;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteFamilles() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_FAMILLE, null, null);
        db.close();
        Log.d(TAG, "Deleted all Famille info from sqlite");
    }

    public int delete(String familleCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_FAMILLE,KEY_FAMILLE_CODE+"=?",new String[]{familleCode});
    }


    public ArrayList<Famille> getFamille_textList() {
        ArrayList<Famille> listFamille = new ArrayList<>();
            String selectQuery = "SELECT "+ KEY_FAMILLE_CODE+ " , "+ KEY_FAMILLE_NOM +  " FROM " + TABLE_FAMILLE + " ORDER BY "+ KEY_RANG;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if( cursor != null && cursor.moveToFirst() ) {
                do {
                    Famille famille = new Famille();
                    famille.setFAMILLE_CODE(cursor.getString(0));
                    famille.setFAMILLE_NOM(cursor.getString(1));
                    listFamille.add(famille);
                }while(cursor.moveToNext());
            }
            //returner la listTournees;
            cursor.close();
            db.close();
            Log.d(TAG, "Fetching code/version  Famille from Sqlite: ");
            return listFamille;
    }


    public static void synchronisationFamille(final Context context){

        String tag_string_req = "FAMILLE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_FAMILLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("famille", "onResponse: "+response);
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray Familles = jObj.getJSONArray("Familles");
                        //Toast.makeText(context, "nombre de Familles  "+Familles.length()  , Toast.LENGTH_SHORT).show();
                        if(Familles.length()>0) {
                            FamilleManager familleManager = new FamilleManager(context);
                            for (int i = 0; i < Familles.length(); i++) {
                                JSONObject uneFamille = Familles.getJSONObject(i);
                                if (uneFamille.getString("OPERATION").equals("DELETE")) {
                                    familleManager.delete(uneFamille.getString(KEY_FAMILLE_CODE));
                                    cptDelete++;
                                } else {
                                    familleManager.add(new Famille(uneFamille));
                                    cptInsert++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("FAMILLE: Inserted: "+cptInsert +" Deleted: "+cptDelete ,"FAMILLE",1));

                        }

                        //logM.add("Familles:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncFamille");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(context, "Famille : "+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("Famille:NOK "+errorMsg ,"SyncFamille");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("FAMILLE: Error: "+errorMsg ,"FAMILLE",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "Famille : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("Famille:NOK "+e.getMessage() ,"SyncFamille");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("FAMILLE: Error: "+e.getMessage() ,"FAMILLE",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Famille : "+error.getMessage(), Toast.LENGTH_LONG).show();
               // LogSyncManager logM= new LogSyncManager(context);
                //logM.add("Famille:NOK "+error.getMessage() ,"SyncFamille");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("FAMILLE: Error: "+error.getMessage() ,"FAMILLE",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    FamilleManager familleManager = new FamilleManager(context);
                    List<Famille> listFamille=familleManager.getFamilleCode_Version_List();
                    for (int i = 0; i < listFamille.size(); i++) {
                        Log.d("test famille-@-version",listFamille.get(i).getFAMILLE_CODE()+"-@@-" +listFamille.get(i).getVERSION());
                        if(listFamille.get(i).getFAMILLE_CODE()!=null && listFamille.get(i).getVERSION() != null ) {
                            params.put(listFamille.get(i).getFAMILLE_CODE(), listFamille.get(i).getVERSION());
                        }
                    }
                }
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}


