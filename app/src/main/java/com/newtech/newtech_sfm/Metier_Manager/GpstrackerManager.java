package com.newtech.newtech_sfm.Metier_Manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.newtech.newtech_sfm.Configuration.AppConfig;
import com.newtech.newtech_sfm.Configuration.AppController;
import com.newtech.newtech_sfm.Configuration.SQLiteHandler;
import com.newtech.newtech_sfm.Metier.Gpstracker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sferricha on 16/12/2016.
 */

public class GpstrackerManager extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Client table name
    public static final String TABLE_GPSTRACKER = "gpstracker";

    private static final String
            KEY_ID="ID",
            KEY_UTILISATEUR_CODE="UTILISATEUR_CODE",
            KEY_LATITUDE="LATITUDE",
            KEY_LONGITUDE="LONGITUDE",
            KEY_TS="TS",
            KEY_DESCRIPTION="DESCRIPTION",
            KEY_VERSION="VERSION"
    ;

    public static  String CREATE_GPSTRACKER_TABLE = "CREATE TABLE " + TABLE_GPSTRACKER + " ("
            +KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            +KEY_UTILISATEUR_CODE + " TEXT ,"
            + KEY_LATITUDE + " NUMERIC,"
            + KEY_LONGITUDE + " NUMERIC,"
            + KEY_TS + " TEXT,"
            + KEY_DESCRIPTION + " TEXT,"
            +KEY_VERSION + " TEXT "  + ")";

    public GpstrackerManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_CLIENT);
            db.execSQL(CREATE_GPSTRACKER_TABLE);

        } catch (SQLException e) {
        }
        Log.d(TAG, "Database tables gpstracker created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GPSTRACKER);
        // Create tables again
        onCreate(db);
    }

    public void add(Gpstracker gpstracker) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_UTILISATEUR_CODE, gpstracker.getUTILISATEUR_CODE());
        values.put(KEY_LATITUDE,gpstracker.getLATITUDE());
        values.put(KEY_LONGITUDE, gpstracker.getLONGITUDE());
        values.put(KEY_TS, gpstracker.getTS());
        values.put(KEY_DESCRIPTION, gpstracker.getDESCRIPTION());
        values.put(KEY_VERSION, gpstracker.getVERSION());
        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_GPSTRACKER, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New gpstracker inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public ArrayList<Gpstracker> getListbyVersion(String version) {
        ArrayList<Gpstracker> listGpstracker = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_GPSTRACKER + " WHERE VERSION='" + version + "' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Gpstracker gpstracker = new Gpstracker();
                gpstracker.setID(cursor.getInt(0));
                gpstracker.setUTILISATEUR_CODE(cursor.getString(1));
                gpstracker.setLATITUDE(cursor.getDouble(2));
                gpstracker.setLONGITUDE(cursor.getDouble(3));
                gpstracker.setTS(cursor.getString(4));
                gpstracker.setDESCRIPTION(cursor.getString(5));
                gpstracker.setVERSION(cursor.getString(6));

                listGpstracker.add(gpstracker);
            }while(cursor.moveToNext());
        }
        //returner la gpstrackers;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Gpstrackers from Sqlite: ");
        return listGpstracker;
    }


    public void updateVersionGpstracker(String id,String version){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_GPSTRACKER+ " SET "+KEY_VERSION  +"= '"+version+"' WHERE "+KEY_ID +"= '"+id+"'" ;
        db.execSQL(req);
    }

    public static void synchronisationGpstracker(final Context context){

        // Tag used to cancel the request
        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_GPSTRACKER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray gpstrackers = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre de gpstrackers  "+gpstrackers.length() , Toast.LENGTH_LONG).show();
                        // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les Gpstrackers : " +response);

                        if(gpstrackers.length()>0) {
                            GpstrackerManager gpstrackerManager = new GpstrackerManager(context);
                            for (int i = 0; i < gpstrackers.length(); i++) {
                                JSONObject unGpstracker = gpstrackers.getJSONObject(i);
                                if (unGpstracker.getString("Statut").equals("true")) {
                                    gpstrackerManager.updateVersionGpstracker((unGpstracker.getString("ID")),"Inserted");
                                }
                            }
                        }
                    }else {
                        String errorMsg = jObj.getString("info");
                        Toast.makeText(context,
                                "Gpstrackers : "+errorMsg, Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Gpstrackers Json error: " + e.getMessage()+response.toString(), Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    GpstrackerManager gpstrackerManager  = new GpstrackerManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableGpstrackers");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(gpstrackerManager.getListbyVersion("To_Insert")));

                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
