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
import com.newtech.newtech_sfm.Metier.Lieu;
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
public class LieuManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Article table name
    public static final String TABLE_LIEU = "lieu";

    //table tournee column
    private static final String
    KEY_LIEU_CODE = "LIEU_CODE",
    KEY_LIEU_NOM = "LIEU_NOM",
    KEY_CLIENT_CODE = "CLIENT_CODE",
    KEY_TYPE_CODE = "TYPE_CODE",
    KEY_STATUT_CODE = "STATUT_CODE",
    KEY_CATEGORIE_CODE = "CATEGORIE_CODE",
    KEY_ADRESSE_NR = "ADRESSE_NR",
    KEY_ADRESSE_RUE = "ADRESSE_RUE",
    KEY_ADRESSE_QUARTIER = "ADRESSE_QUARTIER",
    KEY_GPS_LATITUDE = "GPS_LATITUDE",
    KEY_GPS_LONGITUDE = "GPS_LONGITUDE",
    KEY_IMAGE = "IMAGE",
    KEY_VERSION = "VERSION";

    public static  String CREATE_LIEU_TABLE = "CREATE TABLE " + TABLE_LIEU + "("
            +KEY_LIEU_CODE + " TEXT PRIMARY KEY,"
            +KEY_LIEU_NOM + " TEXT,"
            +KEY_CLIENT_CODE + " TEXT,"
            +KEY_TYPE_CODE + " TEXT,"
            +KEY_STATUT_CODE + " TEXT,"
            +KEY_CATEGORIE_CODE + " TEXT,"
            +KEY_ADRESSE_NR + " TEXT,"
            +KEY_ADRESSE_RUE + " TEXT,"
            +KEY_ADRESSE_QUARTIER + " TEXT,"
            +KEY_GPS_LATITUDE + " TEXT,"
            +KEY_GPS_LONGITUDE + " TEXT,"
            +KEY_IMAGE + " TEXT,"
            +KEY_VERSION + " TEXT "+ ")";

    public LieuManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_LIEU);
            db.execSQL(CREATE_LIEU_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database LIEU tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIEU);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing Articles details in database
     * */
    public void add(Lieu lieu) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LIEU_CODE, lieu.getLIEU_CODE());
        values.put(KEY_LIEU_NOM,lieu.getLIEU_NOM());
        values.put(KEY_CLIENT_CODE,lieu.getCLIENT_CODE());
        values.put(KEY_TYPE_CODE, lieu.getTYPE_CODE());
        values.put(KEY_STATUT_CODE, lieu.getSTATUT_CODE());
        values.put(KEY_CATEGORIE_CODE, lieu.getCATEGORIE_CODE());
        values.put(KEY_ADRESSE_NR, lieu.getADRESSE_NR());
        values.put(KEY_ADRESSE_RUE, lieu.getADRESSE_RUE());
        values.put(KEY_ADRESSE_QUARTIER, lieu.getADRESSE_QUARTIER());
        values.put(KEY_GPS_LATITUDE, lieu.getGPS_LATITUDE());
        values.put(KEY_GPS_LONGITUDE, lieu.getGPS_LONGITUDE());
        values.put(KEY_IMAGE, lieu.getIMAGE());
        values.put(KEY_VERSION, lieu.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_LIEU, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New lieus inserted into sqlite: " + id);
    }

    public Lieu get( String LIEU_CODE) {

        String selectQuery = "SELECT * FROM " + TABLE_LIEU + " WHERE "+ KEY_LIEU_CODE +" = '"+LIEU_CODE+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Lieu lieu = new Lieu();

        if( cursor != null && cursor.moveToFirst() ) {

            lieu.setLIEU_CODE(cursor.getString(0));
            lieu.setLIEU_NOM(cursor.getString(1));
            lieu.setCLIENT_CODE(cursor.getString(2));
            lieu.setTYPE_CODE(cursor.getString(3));
            lieu.setSTATUT_CODE(cursor.getString(4));
            lieu.setCATEGORIE_CODE(cursor.getString(5));
            lieu.setADRESSE_NR(cursor.getString(6));
            lieu.setADRESSE_RUE(cursor.getString(7));
            lieu.setADRESSE_QUARTIER(cursor.getString(8));
            lieu.setGPS_LATITUDE(cursor.getString(9));
            lieu.setGPS_LONGITUDE(cursor.getString(10));
            lieu.setIMAGE(cursor.getString(11));
            lieu.setVERSION(cursor.getString(12));

        }

        cursor.close();
        db.close();
        return lieu;

    }

    /**
     * Getting all tournee from database
     * */

    public ArrayList<Lieu> getList() {
        ArrayList<Lieu> lieus= new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_LIEU;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Lieu lieu = new Lieu();
                lieu.setLIEU_CODE(cursor.getString(0));
                lieu.setLIEU_NOM(cursor.getString(1));
                lieu.setCLIENT_CODE(cursor.getString(2));
                lieu.setTYPE_CODE(cursor.getString(3));
                lieu.setSTATUT_CODE(cursor.getString(4));
                lieu.setCATEGORIE_CODE(cursor.getString(5));
                lieu.setADRESSE_NR(cursor.getString(6));
                lieu.setADRESSE_RUE(cursor.getString(7));
                lieu.setADRESSE_QUARTIER(cursor.getString(8));
                lieu.setGPS_LATITUDE(cursor.getString(9));
                lieu.setGPS_LONGITUDE(cursor.getString(10));
                lieu.setIMAGE(cursor.getString(11));
                lieu.setVERSION(cursor.getString(12));


                lieus.add(lieu);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching all lieu from Sqlite: ");
        return lieus;
    }

    public ArrayList<Lieu> getListByClientCode(String client_code) {
        ArrayList<Lieu> lieus= new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_LIEU +" WHERE "+ KEY_CLIENT_CODE +" = '"+client_code+"'";;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Lieu lieu = new Lieu();
                lieu.setLIEU_CODE(cursor.getString(0));
                lieu.setLIEU_NOM(cursor.getString(1));
                lieu.setCLIENT_CODE(cursor.getString(2));
                lieu.setTYPE_CODE(cursor.getString(3));
                lieu.setSTATUT_CODE(cursor.getString(4));
                lieu.setCATEGORIE_CODE(cursor.getString(5));
                lieu.setADRESSE_NR(cursor.getString(6));
                lieu.setADRESSE_RUE(cursor.getString(7));
                lieu.setADRESSE_QUARTIER(cursor.getString(8));
                lieu.setGPS_LATITUDE(cursor.getString(9));
                lieu.setGPS_LONGITUDE(cursor.getString(10));
                lieu.setIMAGE(cursor.getString(11));
                lieu.setVERSION(cursor.getString(12));


                lieus.add(lieu);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching all lieu from Sqlite: ");
        return lieus;
    }


    public ArrayList<Lieu> getLieuCode_Version_List() {
        ArrayList<Lieu> lieus = new ArrayList<>();

        String selectQuery = "SELECT "+ KEY_LIEU_CODE+" , " +KEY_VERSION +  " FROM " + TABLE_LIEU;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Lieu lieu = new Lieu();
                lieu.setLIEU_CODE(cursor.getString(0));
                lieu.setVERSION(cursor.getString(1));
                lieus.add(lieu);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version  lieus from Sqlite: ");
        return lieus;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteLieus() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LIEU, null, null);
        db.close();
        Log.d(TAG, "Deleted all Lieus info from sqlite");
    }

    public int delete(String lieuCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_LIEU,KEY_LIEU_CODE+"=?",new String[]{lieuCode});
    }


    public ArrayList<Lieu> getLieu_textList() {
        ArrayList<Lieu> lieus = new ArrayList<>();
            String selectQuery = "SELECT "+ KEY_LIEU_CODE+ " , "+ KEY_LIEU_NOM+  " FROM " + TABLE_LIEU;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if( cursor != null && cursor.moveToFirst() ) {
                do {
                    Lieu lieu = new Lieu();
                    lieu.setLIEU_CODE(cursor.getString(0));
                    lieu.setLIEU_NOM(cursor.getString(1));
                    lieus.add(lieu);
                }while(cursor.moveToNext());
            }
            //returner la listTournees;
            cursor.close();
            db.close();
            Log.d(TAG, "Fetching code/version lieus from Sqlite: ");
            return lieus;
        }


    public static void synchronisationLieu(final Context context){

        String tag_string_req = "LIEU";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_LIEU, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Lieu", "onResponse: "+response);
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray Lieus = jObj.getJSONArray("Lieus");
                        //Toast.makeText(context, "nombre de Lieus  "+Lieus.length()  , Toast.LENGTH_SHORT).show();
                        if(Lieus.length()>0) {
                            LieuManager lieuManager = new LieuManager(context);
                            for (int i = 0; i < Lieus.length(); i++) {
                                JSONObject uneLieu = Lieus.getJSONObject(i);
                                if (uneLieu.getString("OPERATION").equals("DELETE")) {
                                    lieuManager.delete(uneLieu.getString(KEY_LIEU_CODE));
                                    cptDelete++;
                                } else {
                                    lieuManager.add(new Lieu(uneLieu));cptInsert++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIEU: Inserted: "+cptInsert +" Deleted: "+cptDelete ,"LIEU",1));

                        }

                        //logM.add("Lieus:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncLieu");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(context, "Lieu : "+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("Lieu:NOK "+errorMsg ,"SyncLieu");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIEU: Error: "+errorMsg ,"LIEU",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "Lieu : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("Lieu:NOK "+e.getMessage() ,"SyncLieu");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIEU: Error: "+e.getMessage() ,"LIEU",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Lieu : "+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("Lieu:NOK "+error.getMessage() ,"SyncLieu");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIEU: Error: "+error.getMessage() ,"LIEU",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    LieuManager lieuManager = new LieuManager(context);
                    List<Lieu> lieus =lieuManager.getLieuCode_Version_List();
                    for (int i = 0; i < lieus.size(); i++) {
                        Log.d("test lieus-@-version",lieus.get(i).getLIEU_CODE()+"-@@-" +lieus.get(i).getVERSION());
                        if(lieus.get(i).getLIEU_CODE()!=null && lieus.get(i).getVERSION() != null ) {
                            params.put(lieus.get(i).getLIEU_CODE(), lieus.get(i).getVERSION());
                        }
                    }
                }
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}


