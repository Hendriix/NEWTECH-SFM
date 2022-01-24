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
import com.newtech.newtech_sfm.Metier.Tache;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TONPC on 10/03/2017.
 */

public class TacheManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_TACHE = "tache";

    public static final String
        KEY_ID ="ID",
        KEY_TACHE_CODE="TACHE_CODE",
        KEY_TACHE_NOM="TACHE_NOM",
        KEY_TACHE_DATE="TACHE_DATE",
        KEY_FREQUENCE="FREQUENCE",
        KEY_UTILISATEUR_CODE="UTILISATEUR_CODE",
        KEY_AFFECTATION_TYPE="AFFECTATION_TYPE",
        KEY_AFFECTATION_VALEUR="AFFECTATION_VALEUR",
        KEY_TYPE_CODE="TYPE_CODE",
        KEY_STATUT_CODE="STATUT_CODE",
        KEY_CATEGORIE_CODE="CATEGORIE_CODE",
        KEY_DATE_CREATION="DATE_CREATION",
        KEY_CREATEUR_CODE="CREATEUR_CODE",
        KEY_RANG="RANG",
        KEY_PROGRESSION="PROGRESSION",
        KEY_VERSION="VERSION";


    public static String CREATE_TACHE_TABLE = "CREATE TABLE " + TABLE_TACHE+ " ("
            +KEY_ID + " INTEGER PRIMARY KEY ,"
            +KEY_TACHE_CODE + " TEXT,"
            +KEY_TACHE_NOM + " TEXT,"
            +KEY_TACHE_DATE + " TEXT,"
            +KEY_FREQUENCE + " INTEGER,"
            +KEY_UTILISATEUR_CODE + " TEXT,"
            +KEY_AFFECTATION_TYPE + " TEXT,"
            +KEY_AFFECTATION_VALEUR + " TEXT,"
            +KEY_TYPE_CODE + " TEXT,"
            +KEY_STATUT_CODE + " TEXT,"
            +KEY_CATEGORIE_CODE + " TEXT,"
            +KEY_DATE_CREATION + " TEXT,"
            +KEY_CREATEUR_CODE + " TEXT,"
            +KEY_RANG + " INTEGER,"
            +KEY_PROGRESSION + " INTEGER,"
            +KEY_VERSION + " TEXT " + ")";

    public TacheManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_TOURNEE);
            db.execSQL(CREATE_TACHE_TABLE);

        } catch (SQLException e) {

            Log.d("TACHE MANAGER", e.toString());

        }
        Log.d("TACHE MANAGER", "Database tables tache created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TACHE);
        // Create tables again
        onCreate(db);

    }

    //ADD TACHE INTO THE DATABASE

    public void add(Tache tache) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID,tache.getID());
        values.put(KEY_TACHE_CODE,tache.getTACHE_CODE());
        values.put(KEY_TACHE_NOM,tache.getTACHE_NOM());
        values.put(KEY_TACHE_DATE,tache.getTACHE_DATE());
        values.put(KEY_FREQUENCE,tache.getFREQUENCE());
        values.put(KEY_UTILISATEUR_CODE,tache.getUTILISATEUR_CODE());
        values.put(KEY_AFFECTATION_TYPE,tache.getAFFECTATION_TYPE());
        values.put(KEY_AFFECTATION_VALEUR,tache.getAFFECTATION_VALEUR());
        values.put(KEY_TYPE_CODE,tache.getTYPE_CODE());
        values.put(KEY_STATUT_CODE,tache.getSTATUT_CODE());
        values.put(KEY_CATEGORIE_CODE,tache.getCATEGORIE_CODE());
        values.put(KEY_DATE_CREATION,tache.getDATE_CREATION());
        values.put(KEY_CREATEUR_CODE,tache.getCREATEUR_CODE());
        values.put(KEY_RANG,tache.getRANG());
        values.put(KEY_PROGRESSION,tache.getPROGRESSION());
        values.put(KEY_VERSION,tache.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_TACHE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d("TACHE MANAGER", "New tache inserted into sqlite: " + id);
        //Log.d("salim tache", "New tache inserted into sqlite: " + tache.toString());
    }

    //GET TASK
    public Tache get(String TACHE_CODE) {

        Tache tache = new Tache();
        String selectQuery = "SELECT * FROM " + TABLE_TACHE+ " WHERE "+ KEY_TACHE_CODE +" = '"+TACHE_CODE+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            tache.setID(cursor.getInt(0));
            tache.setTACHE_CODE(cursor.getString(1));
            tache.setTACHE_NOM(cursor.getString(2));
            tache.setTACHE_DATE(cursor.getString(3));
            tache.setFREQUENCE(cursor.getInt(4));
            tache.setUTILISATEUR_CODE(cursor.getString(5));
            tache.setAFFECTATION_TYPE(cursor.getString(6));
            tache.setAFFECTATION_VALEUR(cursor.getString(7));
            tache.setTYPE_CODE(cursor.getString(8));
            tache.setSTATUT_CODE(cursor.getString(9));
            tache.setCATEGORIE_CODE(cursor.getString(10));
            tache.setDATE_CREATION(cursor.getString(11));
            tache.setCREATEUR_CODE(cursor.getString(12));
            tache.setRANG(cursor.getInt(13));
            tache.setPROGRESSION(cursor.getInt(14));
            tache.setVERSION(cursor.getString(15));
        }

        cursor.close();
        db.close();
        Log.d("TACHE MANAGER", "fetching ");
        return tache;

    }

    //GET LIST TASK

    public ArrayList<Tache> getList() {
        ArrayList<Tache> listTache = new ArrayList<Tache>();

        String selectQuery = "SELECT * FROM " + TABLE_TACHE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Tache tache = new Tache();
                tache.setID(cursor.getInt(0));
                tache.setTACHE_CODE(cursor.getString(1));
                tache.setTACHE_NOM(cursor.getString(2));
                tache.setTACHE_DATE(cursor.getString(3));
                tache.setFREQUENCE(cursor.getInt(4));
                tache.setUTILISATEUR_CODE(cursor.getString(5));
                tache.setAFFECTATION_TYPE(cursor.getString(6));
                tache.setAFFECTATION_VALEUR(cursor.getString(7));
                tache.setTYPE_CODE(cursor.getString(8));
                tache.setSTATUT_CODE(cursor.getString(9));
                tache.setCATEGORIE_CODE(cursor.getString(10));
                tache.setDATE_CREATION(cursor.getString(11));
                tache.setCREATEUR_CODE(cursor.getString(12));
                tache.setRANG(cursor.getInt(13));
                tache.setPROGRESSION(cursor.getInt(14));
                tache.setVERSION(cursor.getString(15));
                listTache.add(tache);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version Tache from Sqlite: "+listTache.size());
        return listTache;
    }

    //GET LIST TASK by date

    public ArrayList<Tache> getListByDate(String date) {
        ArrayList<Tache> listTache = new ArrayList<Tache>();

        String selectQuery = "SELECT * FROM " + TABLE_TACHE +" INNER JOIN tacheplanification ON tacheplanification.TACHE_CODE = tache."+KEY_TACHE_CODE +
                " WHERE tacheplanification.TACHEPLANIFICATION_DATE = '"+date+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Tache tache = new Tache();
                tache.setID(cursor.getInt(0));
                tache.setTACHE_CODE(cursor.getString(1));
                tache.setTACHE_NOM(cursor.getString(2));
                tache.setTACHE_DATE(cursor.getString(3));
                tache.setFREQUENCE(cursor.getInt(4));
                tache.setUTILISATEUR_CODE(cursor.getString(5));
                tache.setAFFECTATION_TYPE(cursor.getString(6));
                tache.setAFFECTATION_VALEUR(cursor.getString(7));
                tache.setTYPE_CODE(cursor.getString(8));
                tache.setSTATUT_CODE(cursor.getString(9));
                tache.setCATEGORIE_CODE(cursor.getString(10));
                tache.setDATE_CREATION(cursor.getString(11));
                tache.setCREATEUR_CODE(cursor.getString(12));
                tache.setRANG(cursor.getInt(13));
                tache.setPROGRESSION(cursor.getInt(14));
                tache.setVERSION(cursor.getString(15));
                listTache.add(tache);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version Tache from Sqlite: "+listTache.size());
        return listTache;
    }

    public Tache getByDate(String date) {
        Tache tache = new Tache();

        String selectQuery = "SELECT * FROM " + TABLE_TACHE +" INNER JOIN tacheplanification ON tacheplanification.TACHE_CODE = tache."+KEY_TACHE_CODE +
                " WHERE tacheplanification.TACHEPLANIFICATION_DATE = '"+date+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {

                tache.setID(cursor.getInt(0));
                tache.setTACHE_CODE(cursor.getString(1));
                tache.setTACHE_NOM(cursor.getString(2));
                tache.setTACHE_DATE(cursor.getString(3));
                tache.setFREQUENCE(cursor.getInt(4));
                tache.setUTILISATEUR_CODE(cursor.getString(5));
                tache.setAFFECTATION_TYPE(cursor.getString(6));
                tache.setAFFECTATION_VALEUR(cursor.getString(7));
                tache.setTYPE_CODE(cursor.getString(8));
                tache.setSTATUT_CODE(cursor.getString(9));
                tache.setCATEGORIE_CODE(cursor.getString(10));
                tache.setDATE_CREATION(cursor.getString(11));
                tache.setCREATEUR_CODE(cursor.getString(12));
                tache.setRANG(cursor.getInt(13));
                tache.setPROGRESSION(cursor.getInt(14));
                tache.setVERSION(cursor.getString(15));
        }
        //returner la listTournees;
        cursor.close();
        db.close();
       // Log.d(TAG, "Fetching code/version Tache from Sqlite: "+listTache.size());
        return tache;
    }

    //GET TASKS VERSION CODE LIST
    public ArrayList<Tache> gettacheCode_Version_List() {
        ArrayList<Tache> listTache = new ArrayList<>();

        String selectQuery = "SELECT "+ KEY_TACHE_CODE+","+KEY_VERSION +  " FROM " + TABLE_TACHE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Tache tache = new Tache();
                tache.setID(cursor.getInt(0));
                tache.setTACHE_CODE(cursor.getString(1));
                tache.setTACHE_NOM(cursor.getString(2));
                tache.setTACHE_DATE(cursor.getString(3));
                tache.setFREQUENCE(cursor.getInt(4));
                tache.setUTILISATEUR_CODE(cursor.getString(5));
                tache.setAFFECTATION_TYPE(cursor.getString(6));
                tache.setAFFECTATION_VALEUR(cursor.getString(7));
                tache.setTYPE_CODE(cursor.getString(8));
                tache.setSTATUT_CODE(cursor.getString(9));
                tache.setCATEGORIE_CODE(cursor.getString(10));
                tache.setDATE_CREATION(cursor.getString(11));
                tache.setCREATEUR_CODE(cursor.getString(12));
                tache.setRANG(cursor.getInt(13));
                tache.setPROGRESSION(cursor.getInt(14));
                tache.setVERSION(cursor.getString(15));
                listTache.add(tache);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version Taches from Sqlite: ");
        return listTache;
    }

    //DELETE TASK
    public int delete(String tacheCode,String version) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TACHE,KEY_TACHE_CODE+"=? AND "+KEY_VERSION+"=?",new String[]{tacheCode,version});
    }

    //UPDATE Task STATUT
    public void updateCommande(String tacheCode,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_TACHE + " SET "+KEY_STATUT_CODE  +"= '"+msg+"' WHERE "+KEY_TACHE_CODE +"= '"+tacheCode+"'" ;
        db.execSQL(req);
    }

    //SYNCHRONISATION TACHE
    public static void synchronisationTache(final Context context){

        String tag_string_req = "TACHE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_TACHE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("tache", "onResponse: "+response);

                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    Log.d("salim response",response);
                    JSONObject jObj = new JSONObject(response);

                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray Taches = jObj.getJSONArray("Taches");
                        //Toast.makeText(context, "nombre de Taches  "+Taches.length()  , Toast.LENGTH_SHORT).show();
                        if(Taches.length()>0) {
                            TacheManager tacheManager = new TacheManager(context);
                            for (int i = 0; i < Taches.length(); i++) {
                                JSONObject tache = Taches.getJSONObject(i);

                                if (tache.getString("OPERATION").equals("DELETE")) {
                                    tacheManager.delete(tache.getString("TACHE_CODE"),tache.getString("VERSION"));
                                    cptDelete++;
                                } else {
                                    tacheManager.add(new Tache(tache));
                                    cptInsert++;
                                }
                            }

                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("TACHE: Inserted: "+cptInsert +" Deleted: "+cptDelete ,"TACHE",1));

                        }

                        //logM.add("Tache:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncTache");
                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "Tache : "+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("Tache:NOK "+errorMsg ,"SyncTache");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("TACHE: Error: "+errorMsg ,"TACHE",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "Tache : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("Tache:NOK "+e.getMessage() ,"SyncTache");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("TACHE: Error: "+e.getMessage() ,"TACHE",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Tache : "+error.getMessage(), Toast.LENGTH_LONG).show();
               // LogSyncManager logM= new LogSyncManager(context);
                //logM.add("Tache:NOK "+error.getMessage() ,"SyncTache");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("TACHE: Error: "+error.getMessage() ,"TACHE",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //Map<String, String> params = new HashMap<String, String>();
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    TacheManager tacheManager  = new TacheManager(context);
                    List<Tache> taches=tacheManager.getList();

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    Log.d("UC TACHE SYNC",gson.toJson(pref.getString("UTILISATEUR_CODE",null)));

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(taches));
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
