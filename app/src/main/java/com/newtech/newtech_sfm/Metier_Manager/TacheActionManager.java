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
import com.newtech.newtech_sfm.Metier.TacheAction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TONPC on 13/03/2017.
 */

public class TacheActionManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_TACHEACTION = "tacheaction";

    private static final String
            KEY_ID="ID",
            KEY_TACHEACTION_CODE="TACHEACTION_CODE",
            KEY_TACHEACTION_NOM="TACHEACTION_NOM",
            KEY_TACHE_CODE="TACHE_CODE",
            KEY_TYPE_CODE="TYPE_CODE",
            KEY_RANG="RANG",
            KEY_VERSION="VERSION"
            ;

    public static String CREATE_TACHEACTION_TABLE = "CREATE TABLE " + TABLE_TACHEACTION+ "("

            +KEY_ID + " INTEGER PRIMARY KEY ,"
            +KEY_TACHEACTION_CODE + " TEXT,"
            +KEY_TACHEACTION_NOM + " TEXT,"
            +KEY_TACHE_CODE + " TEXT,"
            +KEY_TYPE_CODE + " TEXT,"
            +KEY_RANG + " INTEGER,"
            +KEY_VERSION + " TEXT"
            + ")";

    public TacheActionManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_TOURNEE);
            db.execSQL(CREATE_TACHEACTION_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database tables tacheaction created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TACHEACTION);
        // Create tables again
        onCreate(db);

    }

    public void add(TacheAction tacheaction) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,tacheaction.getID());
        values.put(KEY_TACHEACTION_CODE, tacheaction.getTACHEACTION_CODE());
        values.put(KEY_TACHEACTION_NOM, tacheaction.getTACHEACTION_NOM());
        values.put(KEY_TACHE_CODE,tacheaction.getTACHE_CODE());
        values.put(KEY_TYPE_CODE,tacheaction.getTYPE_CODE());
        values.put(KEY_RANG,tacheaction.getRANG());
        values.put(KEY_VERSION,tacheaction.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_TACHEACTION, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d("TACHEACTION MANAGER", "New tache inserted into sqlite: " + id);
        Log.d("salim tache", "New tache inserted into sqlite: " + tacheaction.toString());
    }

    public int delete(String tacheactionCode,String version) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TACHEACTION,KEY_TACHEACTION_CODE+"=? AND "+KEY_VERSION+"=?",new String[]{tacheactionCode,version});
    }

    public ArrayList<TacheAction> getList() {
        ArrayList<TacheAction> listTacheAction = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_TACHEACTION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {

                TacheAction tacheaction = new TacheAction();
                tacheaction.setID(cursor.getInt(0));
                tacheaction.setTACHEACTION_CODE(cursor.getString(1));
                tacheaction.setTACHEACTION_NOM(cursor.getString(2));
                tacheaction.setTACHE_CODE(cursor.getString(3));
                tacheaction.setTYPE_CODE(cursor.getString(4));
                tacheaction.setRANG(cursor.getInt(5));
                tacheaction.setVERSION(cursor.getString(6));


                listTacheAction.add(tacheaction);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande from table tachaction: ");
        return listTacheAction;
    }

    public TacheAction get(String TACHEACTION_CODE) {

        String selectQuery = "SELECT * FROM " + TABLE_TACHEACTION + " WHERE "+ KEY_TACHEACTION_CODE +" = '"+TACHEACTION_CODE+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        TacheAction tacheAction = new TacheAction();

        if( cursor != null && cursor.moveToFirst() ) {
            tacheAction.setID(cursor.getInt(0));
            tacheAction.setTACHEACTION_CODE(cursor.getString(1));
            tacheAction.setTACHEACTION_NOM(cursor.getString(2));
            tacheAction.setTACHE_CODE(cursor.getString(3));
            tacheAction.setTYPE_CODE(cursor.getString(4));
            tacheAction.setRANG(cursor.getInt(5));
            tacheAction.setVERSION(cursor.getString(6));

        }

        cursor.close();
        db.close();
        return tacheAction;

    }

    public void deleteTachesActions() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_TACHEACTION, null, null);
        db.close();
        Log.d(TAG, "Deleted all tasks from sqlite");
    }


    public static void synchronisationTacheAction(final Context context){

        String tag_string_req = "TACHE_ACTION";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_TACHEACTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    Log.d("salim TACHEACTION",response);
                    JSONObject jObj = new JSONObject(response);

                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray TacheActions = jObj.getJSONArray("TacheActions");
                        //Toast.makeText(context, "nombre de TacheActions  "+TacheActions.length()  , Toast.LENGTH_SHORT).show();
                        if(TacheActions.length()>0) {
                            TacheActionManager tacheactionManager = new TacheActionManager(context);
                            for (int i = 0; i < TacheActions.length(); i++) {
                                JSONObject tacheaction = TacheActions.getJSONObject(i);
                                if (tacheaction.getString("OPERATION").equals("DELETE")) {
                                    tacheactionManager.delete(tacheaction.getString("TACHEACTION_CODE"),tacheaction.getString("VERSION"));
                                    cptDelete++;
                                } else {
                                    tacheactionManager.add(new TacheAction(tacheaction));
                                    cptInsert++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("TACHE_ACTION: Inserted: "+cptInsert +" Deleted: "+cptDelete ,"TACHE_ACTION",1));

                        }

                        //logM.add("TacheAction:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncTacheAction");
                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "tacheAction : "+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("TacheAction:NOK "+errorMsg ,"SyncTacheAction");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("TACHE_ACTION: Error: "+errorMsg ,"TACHE_ACTION",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "TacheAction : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("TacheAction:NOK "+e.getMessage() ,"SyncTacheAction");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("TACHE_ACTION: Error: "+e.getMessage() ,"TACHE_ACTION",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "TacheAction : "+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("TacheAction:NOK "+error.getMessage() ,"SyncTacheAction");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("TACHE_ACTION: Error: "+error.getMessage() ,"TACHE_ACTION",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //Map<String, String> params = new HashMap<String, String>();
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    TacheActionManager tachactionManager  = new TacheActionManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(tachactionManager.getList()));
                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
