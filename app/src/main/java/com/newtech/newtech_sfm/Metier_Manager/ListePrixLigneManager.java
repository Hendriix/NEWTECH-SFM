package com.newtech.newtech_sfm.Metier_Manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
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
import com.newtech.newtech_sfm.Metier.ListePrixLigne;
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

public class ListePrixLigneManager extends SQLiteOpenHelper{

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_LISTEPRIXLIGNE = "listeprixligne";


    private static final String
            KEY_LISTEPRIXLIGNE_CODE = "LISTEPRIXLIGNE_CODE",
            KEY_LISTEPRIX_CODE = "LISTEPRIX_CODE",
            KEY_ARTICLE_CODE = "ARTICLE_CODE",
            KEY_UNITE_CODE = "UNITE_CODE",
            KEY_ARTICLE_PRIX = "ARTICLE_PRIX",
            KEY_VERSION = "VERSION";

    public static String CREATE_LISTEPRIXLIGNE_TABLE="CREATE TABLE " + TABLE_LISTEPRIXLIGNE + "("
            +KEY_LISTEPRIXLIGNE_CODE + " NUMERIC ,"
            +KEY_LISTEPRIX_CODE + " TEXT,"
            +KEY_ARTICLE_CODE + " TEXT,"
            +KEY_UNITE_CODE + " TEXT,"
            +KEY_ARTICLE_PRIX + " NUMERIC,"
            +KEY_VERSION + " TEXT" + ")";


    public ListePrixLigneManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try{
            db.execSQL(CREATE_LISTEPRIXLIGNE_TABLE);

        }catch(SQLException e){

        }
        Log.d(TAG, "Database tables ListePrixLigne created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTEPRIXLIGNE);
        onCreate(db);
    }

    public void add(ListePrixLigne listePrixLigne) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LISTEPRIXLIGNE_CODE,listePrixLigne.getLISTEPRIXLIGNE_CODE());
        values.put(KEY_LISTEPRIX_CODE,listePrixLigne.getLISTEPRIX_CODE());
        values.put(KEY_ARTICLE_CODE,listePrixLigne.getARTICLE_CODE());
        values.put(KEY_UNITE_CODE,listePrixLigne.getUNITE_CODE());
        values.put(KEY_ARTICLE_PRIX,listePrixLigne.getARTICLE_PRIX());
        values.put(KEY_VERSION,listePrixLigne.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_LISTEPRIXLIGNE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New listeprixLigne inserted into sqlite: "+ id);
    }



    public ArrayList<ListePrixLigne> getList(){
        ArrayList<ListePrixLigne> listePrixLignes = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LISTEPRIXLIGNE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                ListePrixLigne listePrixLigne = new ListePrixLigne();
                listePrixLigne.setLISTEPRIXLIGNE_CODE(cursor.getInt(0));
                listePrixLigne.setLISTEPRIX_CODE(cursor.getString(1));
                listePrixLigne.setARTICLE_CODE(cursor.getString(2));
                listePrixLigne.setUNITE_CODE(cursor.getString(3));
                listePrixLigne.setARTICLE_PRIX(cursor.getInt(4));
                listePrixLigne.setVERSION(cursor.getString(5));

                listePrixLignes.add(listePrixLigne);
            }while(cursor.moveToNext());
        }

        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching listePrix from Sqlite: ");
        return listePrixLignes;
    }

    public ArrayList<ListePrixLigne> get(String listeprix_code){
        ArrayList<ListePrixLigne> listePrixLignes = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LISTEPRIXLIGNE +" WHERE "+KEY_LISTEPRIX_CODE+"='"+listeprix_code+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                ListePrixLigne listePrixLigne = new ListePrixLigne();
                listePrixLigne.setLISTEPRIXLIGNE_CODE(cursor.getInt(0));
                listePrixLigne.setLISTEPRIX_CODE(cursor.getString(1));
                listePrixLigne.setARTICLE_CODE(cursor.getString(2));
                listePrixLigne.setUNITE_CODE(cursor.getString(3));
                listePrixLigne.setARTICLE_PRIX(cursor.getInt(4));
                listePrixLigne.setVERSION(cursor.getString(5));

                listePrixLignes.add(listePrixLigne);
            }while(cursor.moveToNext());
        }

        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching listePrix from Sqlite: ");
        return listePrixLignes;
    }


    public ListePrixLigne getListPrixLigneByLPCACUC(String listeprix_code,String article_code, String unite_code){

        ListePrixLigne listePrixLigne = new ListePrixLigne();

        String selectQuery = "SELECT  * FROM " + TABLE_LISTEPRIXLIGNE+" WHERE "+KEY_LISTEPRIX_CODE+"='"+listeprix_code+"' AND " +KEY_ARTICLE_CODE+ "='"+article_code+"' AND " +KEY_UNITE_CODE+ "='"+unite_code+"';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if( cursor != null && cursor.moveToFirst() ) {
            listePrixLigne.setLISTEPRIXLIGNE_CODE(cursor.getInt(0));
            listePrixLigne.setLISTEPRIX_CODE(cursor.getString(1));
            listePrixLigne.setARTICLE_CODE(cursor.getString(2));
            listePrixLigne.setUNITE_CODE(cursor.getString(3));
            listePrixLigne.setARTICLE_PRIX(cursor.getDouble(4));
            listePrixLigne.setVERSION(cursor.getString(5));
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching listeprixLigne from Sqlite: ");
        return listePrixLigne;
    }

    public ListePrixLigne getPrixLigneByLPCACUC(String listeprix_code,String article_code){

        ListePrixLigne listePrixLigne = new ListePrixLigne();

        String selectQuery = "SELECT * FROM " + TABLE_LISTEPRIXLIGNE+" WHERE "+KEY_ARTICLE_PRIX+"" +
                " IN (SELECT MAX(listeprixligne.ARTICLE_PRIX) FROM listeprixligne WHERE listeprixligne.ARTICLE_CODE"+"='"+article_code+"' AND listeprixligne.LISTEPRIX_CODE"+"='"+listeprix_code+"') AND "+KEY_LISTEPRIX_CODE+ "='"+listeprix_code+"';";

        Log.d(TAG, "getPrixLigneByLPCACUC: "+selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if( cursor != null && cursor.moveToFirst() ) {
            listePrixLigne.setLISTEPRIXLIGNE_CODE(cursor.getInt(0));
            listePrixLigne.setLISTEPRIX_CODE(cursor.getString(1));
            listePrixLigne.setARTICLE_CODE(cursor.getString(2));
            listePrixLigne.setUNITE_CODE(cursor.getString(3));
            listePrixLigne.setARTICLE_PRIX(cursor.getDouble(4));
            listePrixLigne.setVERSION(cursor.getString(5));
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching listeprixLigne from Sqlite: ");
        return listePrixLigne;
    }

    public void deleteListePrixlignes() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LISTEPRIXLIGNE, null, null);
        db.close();
        Log.d(TAG, "Deleted all listeprixlignes info from sqlite");
    }

    public int delete(String listeprix_code, String listprixligne_code,String version) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_LISTEPRIXLIGNE,KEY_LISTEPRIX_CODE+"=?  AND "+KEY_LISTEPRIXLIGNE_CODE+"=? AND "+KEY_VERSION+"=?",new String[]{listeprix_code,listprixligne_code,version});
    }


    public static void synchronisationListePrixLigne(final Context context){

        String tag_string_req = "LISTE_PRIX_LIGNE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_LISTEPRIXLIGNE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("listeprixligne", "onResponse: "+response);
                //ouvrir le logManager
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray listePrixLignes = jObj.getJSONArray("ListePrixLignes");
                        //Toast.makeText(context, "Nombre de ListePrixLigne " +listePrixLignes.length(), Toast.LENGTH_SHORT).show();
                        int cptInsert = 0,cptDeleted = 0;
                        //Ajout/Suppression/modification des distributeurs dans la base de données.
                        for (int i = 0; i < listePrixLignes.length(); i++) {
                            JSONObject uneListePrixLigne = listePrixLignes.getJSONObject(i);
                            ListePrixLigneManager listePrixLigneManager = new ListePrixLigneManager(context);
                            if(uneListePrixLigne.getString("OPERATION").equals("DELETE")){
                                listePrixLigneManager.delete(uneListePrixLigne.getString("LISTEPRIX_CODE"),uneListePrixLigne.getString("LISTEPRIXLIGNE_CODE"),uneListePrixLigne.getString("VERSION"));
                                cptDeleted++;
                            }
                            else {
                                listePrixLigneManager.add(new ListePrixLigne(uneListePrixLigne));
                                cptInsert++;
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LISTE_PRIX_LIGNE: Inserted: "+cptInsert +" Deleted: "+cptDeleted ,"LISTE_PRIX_LIGNE",1));

                        }

                        //logM.add("LISTEPRIXLIGNES:OK Insert:"+cptInsert +"Deleted:"+cptDeleted ,"SyncListePrixLigne");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //logM.add("LISTEPRIXLIGNES:NOK Insert "+errorMsg ,"SyncListePrixLigne");
                        //Toast.makeText(context,"LISTEPRIXLIGNES:"+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LISTE_PRIX_LIGNE: Error: "+errorMsg ,"LISTE_PRIX_LIGNE",0));

                        }

                    }

                } catch (JSONException e) {
                    //logM.add("LISTEPRIXLIGNES : NOK Insert "+e.getMessage() ,"SyncListePrixLigne");
                    e.printStackTrace();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LISTE_PRIX_LIGNE: Error: "+e.getMessage() ,"LISTE_PRIX_LIGNE",0));

                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "LISTEPRIXLIGNES:"+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("LISTEPRIXLIGNES : NOK Inserted "+error.getMessage() ,"SyncListePrixLigne");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LISTE_PRIX_LIGNE: Error: "+error.getMessage() ,"LISTE_PRIX_LIGNE",0));

                }


            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> arrayFinale= new HashMap<>();

                UserManager userManager =  new UserManager(context);
                ListePrixLigneManager listePrixLigneManager = new ListePrixLigneManager(context);

                ArrayList<ListePrixLigne> listePrixLignes = new ArrayList<>();
                User user = userManager.get();

                listePrixLignes = listePrixLigneManager.getList();
                HashMap<String,String > TabParams =new HashMap<>();
                TabParams.put("UTILISATEUR_CODE",user.getUTILISATEUR_CODE());

                final GsonBuilder builder = new GsonBuilder();
                final Gson gson = builder.create();

                arrayFinale.put("Params",gson.toJson(TabParams));
                arrayFinale.put("Data",gson.toJson(listePrixLignes));

                return arrayFinale;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


}
