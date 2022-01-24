package com.newtech.newtech_sfm.Metier_Manager;

import android.content.ContentValues;
import android.content.Context;
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
import com.newtech.newtech_sfm.Metier.ListePrix;
import com.newtech.newtech_sfm.Metier.LogSync;
import com.newtech.newtech_sfm.Metier.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TONPC on 15/01/2018.
 */

public class ListePrixManager extends SQLiteOpenHelper{

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_LISTEPRIX = "listeprix";


    private static final String
            KEY_LISTEPRIX_CODE = "LISTEPRIX_CODE",
            KEY_LISTEPRIX_NOM = "LISTEPRIX_NOM",
            KEY_DATE_DEBUT = "DATE_DEBUT",
            KEY_DATE_FIN = "DATE_FIN",
            KEY_INACTIF = "INACTIF",
            KEY_INACTIF_RAISON = "INACTIF_RAISON",
            KEY_TYPE_CODE = "TYPE_CODE",
            KEY_CATEGORIE_CODE = "CATEGORIE_CODE",
            KEY_VERSION = "VERSION";

    public static String CREATE_LISTEPRIX_TABLE="CREATE TABLE " + TABLE_LISTEPRIX + "("
            +KEY_LISTEPRIX_CODE + " TEXT PRIMARY KEY,"
            +KEY_LISTEPRIX_NOM + " TEXT,"
            +KEY_DATE_DEBUT + " TEXT,"
            +KEY_DATE_FIN + " TEXT,"
            +KEY_INACTIF + " NUMERIC,"
            +KEY_INACTIF_RAISON + " TEXT,"
            +KEY_TYPE_CODE+ " TEXT,"
            +KEY_CATEGORIE_CODE+ " TEXT,"
            +KEY_VERSION + " TEXT" + ")";


    public ListePrixManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try{
            db.execSQL(CREATE_LISTEPRIX_TABLE);
        }catch(SQLException e){

        }
        Log.d(TAG, "Database tables ListePrix created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTEPRIX);
        onCreate(db);
    }

    public void add(ListePrix listePrix) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LISTEPRIX_CODE,listePrix.getLISTEPRIX_CODE());
        values.put(KEY_LISTEPRIX_NOM,listePrix.getLISTEPRIX_NOM());
        values.put(KEY_DATE_DEBUT,listePrix.getDATE_DEBUT());
        values.put(KEY_DATE_FIN,listePrix.getDATE_FIN());
        values.put(KEY_INACTIF,listePrix.getINACTIF());
        values.put(KEY_INACTIF_RAISON,listePrix.getINACTIF_RAISON());
        values.put(KEY_TYPE_CODE,listePrix.getTYPE_CODE());
        values.put(KEY_CATEGORIE_CODE,listePrix.getCATEGORIE_CODE());
        values.put(KEY_VERSION,listePrix.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_LISTEPRIX, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New listeprix inserted into sqlite: " + id);
    }

    public ListePrix get(String listeprix_code) {
        ListePrix listePrix = new ListePrix();

        String selectQuery = "SELECT  * FROM " + TABLE_LISTEPRIX+" WHERE "+KEY_LISTEPRIX_CODE+"='"+listeprix_code+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if( cursor != null && cursor.moveToFirst() ) {
            listePrix.setLISTEPRIX_CODE(cursor.getString(0));
            listePrix.setLISTEPRIX_NOM(cursor.getString(1));
            listePrix.setDATE_DEBUT(cursor.getString(2));
            listePrix.setDATE_FIN(cursor.getString(3));
            listePrix.setINACTIF(cursor.getInt(4));
            listePrix.setINACTIF_RAISON(cursor.getString(5));
            listePrix.setTYPE_CODE(cursor.getString(6));
            listePrix.setCATEGORIE_CODE(cursor.getString(7));
            listePrix.setVERSION(cursor.getString(8));
        }
        //returner un distributeur;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching listeprix from Sqlite: ");
        return listePrix;
    }

    public ArrayList<ListePrix> getList() {
        ArrayList<ListePrix> listePrixArrayList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LISTEPRIX;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                ListePrix listePrix = new ListePrix();
                listePrix.setLISTEPRIX_CODE(cursor.getString(0));
                listePrix.setLISTEPRIX_NOM(cursor.getString(1));
                listePrix.setDATE_DEBUT(cursor.getString(2));
                listePrix.setDATE_FIN(cursor.getString(3));
                listePrix.setINACTIF(cursor.getInt(4));
                listePrix.setINACTIF_RAISON(cursor.getString(5));
                listePrix.setTYPE_CODE(cursor.getString(6));
                listePrix.setCATEGORIE_CODE(cursor.getString(7));
                listePrix.setVERSION(cursor.getString(8));

                listePrixArrayList.add(listePrix);
            }while(cursor.moveToNext());
        }

        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching listePrix from Sqlite: ");
        return listePrixArrayList;
    }

    public void deleteListesPrix() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LISTEPRIX, null, null);
        db.close();
        Log.d(TAG, "Deleted all listeprix info from sqlite");
    }

    public int delete(String listeprix_code,String version) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_LISTEPRIX,KEY_LISTEPRIX_CODE+"=? AND "+KEY_VERSION+"=?",new String[]{listeprix_code,version});
    }

    public static void synchronisationListePrix(final Context context){

        String tag_string_req = "LISTE_PRIX";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_LISTEPRIX, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("listeprix", "onResponse: "+response);
                //ouvrir le logManager
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray listesPrix = jObj.getJSONArray("ListePrix");
                        //Toast.makeText(context, "Nombre de ListePrix " +listesPrix.length(), Toast.LENGTH_SHORT).show();
                        int cptInsert = 0,cptDeleted = 0;
                        //Ajout/Suppression/modification des distributeurs dans la base de données.
                        for (int i = 0; i < listesPrix.length(); i++) {
                            JSONObject uneListePrix = listesPrix.getJSONObject(i);
                            ListePrixManager listePrixManager = new ListePrixManager(context);
                            if(uneListePrix.getString("OPERATION").equals("DELETE")){
                                listePrixManager.delete(uneListePrix.getString("LISTEPRIX_CODE"),uneListePrix.getString("VERSION"));
                                cptDeleted++;
                            }
                            else {
                                listePrixManager.add(new ListePrix(uneListePrix));
                                cptInsert++;
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LISTE_PRIX: Inserted: "+cptInsert +" Deleted: "+cptDeleted ,"LISTE_PRIX",1));

                        }

                        //logM.add("LISTEPRIX:OK Insert:"+cptInsert +"Deleted:"+cptDeleted ,"SyncListePrix");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //logM.add("LISTEPRIX:NOK Insert "+errorMsg ,"SyncListePrix");
                        //Toast.makeText(context,"LISTEPRIX:"+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LISTE_PRIX: Error: "+errorMsg ,"LISTE_PRIX",0));

                        }

                    }

                } catch (JSONException e) {
                    //logM.add("LISTEPRIX : NOK Insert "+e.getMessage() ,"SyncListePrix");
                    e.printStackTrace();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LISTE_PRIX: Error: "+e.getMessage() ,"LISTE_PRIX",0));

                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "LISTEPRIX:"+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("LISTEPRIX : NOK Inserted "+error.getMessage() ,"SyncListePrix");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LISTE_PRIX: Error: "+error.getMessage() ,"LISTE_PRIX",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> arrayFinale= new HashMap<>();

                UserManager userManager =  new UserManager(context);
                ListePrixManager listePrixManager = new ListePrixManager(context);

                ArrayList<ListePrix> listePrixArrayList = new ArrayList<>();
                User user = userManager.get();

                listePrixArrayList = listePrixManager.getList();
                HashMap<String,String > TabParams =new HashMap<>();
                TabParams.put("UTILISATEUR_CODE",user.getUTILISATEUR_CODE());

                final GsonBuilder builder = new GsonBuilder();
                final Gson gson = builder.create();

                arrayFinale.put("Params",gson.toJson(TabParams));
                arrayFinale.put("Data",gson.toJson(listePrixArrayList));

                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
