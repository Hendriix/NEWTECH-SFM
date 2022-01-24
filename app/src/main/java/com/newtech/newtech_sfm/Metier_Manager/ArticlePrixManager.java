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
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Activity.SyncV2Activity;
import com.newtech.newtech_sfm.Configuration.AppConfig;
import com.newtech.newtech_sfm.Configuration.AppController;
import com.newtech.newtech_sfm.Configuration.SQLiteHandler;
import com.newtech.newtech_sfm.Metier.Articleprix;
import com.newtech.newtech_sfm.Metier.LogSync;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stagiaireit2 on 04/08/2016.
 */
public class ArticlePrixManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Article table name
    public static final String TABLE_ARTICLE_PRIX = "articlePrix";

    //table tournee column
    private static final String
    KEY_ARTICLEPRIX_CODE="ARTICLEPRIX_CODE",
    KEY_CIRCUIT_CODE="CIRCUIT_CODE",
    KEY_ARTICLE_CODE="ARTICLE_CODE",
    KEY_ARTICLE_UPPRIX="ARTICLE_UPPRIX",
    KEY_ARTICLE_USPRIX="RTICLE_USPRIX",
    KEY_DATE_DEBUT="DATE_DEBUT",
    KEY_DATE_FIN="DATE_FIN",
    KEY_DATE_CREATION="DATE_CREATION",
    KEY_CREATEUR_CODE="CREATEUR_CODE",
    KEY_INACTIF="INACTIF",
    KEY_RAISON_INACTIF="RAISON_INACTIF",
    KEY_VERSION="_VERSION",
    KEY_UNITE_CODE="UNITE_CODE",
    KEY_ARTICLE_PRIX="ARTICLE_PRIX";

    public static String CREATE_ARTICLE_PRIX_TABLE = "CREATE TABLE " + TABLE_ARTICLE_PRIX + "("

            + KEY_ARTICLEPRIX_CODE + " TEXT PRIMARY KEY,"
            + KEY_CIRCUIT_CODE + " TEXT ,"
            + KEY_ARTICLE_CODE +" TEXT ,"
            + KEY_ARTICLE_UPPRIX +" NUMERIC,"
            + KEY_ARTICLE_USPRIX +" NUMERIC,"
            + KEY_DATE_DEBUT +" TEXT ,"
            + KEY_DATE_FIN +" TEXT ,"
            + KEY_DATE_CREATION +" TEXT ,"
            + KEY_CREATEUR_CODE +" TEXT ,"
            + KEY_INACTIF +" TEXT ,"
            + KEY_RAISON_INACTIF +" TEXT ,"
            + KEY_VERSION +" TEXT ,"
            + KEY_UNITE_CODE +" TEXT ,"
            + KEY_ARTICLE_PRIX + " NUMERIC "+")";

    public ArticlePrixManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE_PRIX);
            db.execSQL(CREATE_ARTICLE_PRIX_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "TABLE ArticlePrix creer");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE_PRIX);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing Articles details in database
     * */
    public void add(Articleprix articleprix) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ARTICLEPRIX_CODE, articleprix.getARTICLEPRIX_CODE());
        values.put(KEY_CIRCUIT_CODE, articleprix.getCIRCUIT_CODE());
        values.put(KEY_ARTICLE_CODE, articleprix.getARTICLE_CODE());
        values.put(KEY_ARTICLE_UPPRIX, articleprix.getARTICLE_UPPRIX());
        values.put(KEY_ARTICLE_USPRIX, articleprix.getARTICLE_USPRIX());
        values.put(KEY_DATE_DEBUT, articleprix.getDATE_DEBUT());
        values.put(KEY_DATE_FIN, articleprix.getDATE_FIN());
        values.put(KEY_DATE_CREATION, articleprix.getDATE_CREATION());
        values.put(KEY_CREATEUR_CODE, articleprix.getCREATEUR_CODE());
        values.put(KEY_INACTIF, articleprix.getINACTIF());
        values.put(KEY_RAISON_INACTIF, articleprix.getRAISON_INACTIF());
        values.put(KEY_VERSION, articleprix.getVERSION());
        values.put(KEY_UNITE_CODE, articleprix.getUNITE_CODE());
        values.put(KEY_ARTICLE_PRIX, articleprix.getARTICLE_PRIX());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_ARTICLE_PRIX, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New articlesPrix inserted into sqlite: " + id);
    }

    /**
     * Getting all tournee from database
     * */

    public ArrayList<Articleprix> getList() {
        ArrayList<Articleprix> articleprixArrayList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_ARTICLE_PRIX;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Articleprix  articleprix = new Articleprix();
                articleprix.setARTICLEPRIX_CODE(cursor.getString(0));
                articleprix.setCIRCUIT_CODE(cursor.getString(1));
                articleprix.setARTICLE_CODE(cursor.getString(2));
                articleprix.setARTICLE_UPPRIX(3);
                articleprix.setARTICLE_USPRIX(4);
                articleprix.setDATE_DEBUT(cursor.getString(5));
                articleprix.setDATE_FIN(cursor.getString(6));
                articleprix.setDATE_CREATION(cursor.getString(7));
                articleprix.setCREATEUR_CODE(cursor.getString(8));
                articleprix.setINACTIF(cursor.getString(9));
                articleprix.setRAISON_INACTIF(cursor.getString(10));
                articleprix.setVERSION(cursor.getString(11));
                articleprix.setUNITE_CODE(cursor.getString(12));
                articleprix.setARTICLE_PRIX(cursor.getDouble(13));
                articleprixArrayList.add(articleprix);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching articlePrix from Sqlite: ");
        return articleprixArrayList;
    }

    public ArrayList<Articleprix> getArticlePrix_code_version() {
        ArrayList<Articleprix> articleprixArrayList = new ArrayList<>();

        String selectQuery = "SELECT "+ KEY_ARTICLEPRIX_CODE+","+KEY_VERSION +  " FROM " + TABLE_ARTICLE_PRIX;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Articleprix  articleprix = new Articleprix();
                articleprix.setARTICLEPRIX_CODE(cursor.getString(0));
                articleprix.setVERSION(cursor.getString(1));

                articleprixArrayList.add(articleprix);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version articlePrix from Sqlite: ");
        return articleprixArrayList;
    }


    public Articleprix getArticlePrixBy_Unite_ArticlePrix (String articlecode , String code_unite,String circuit_code,String DATE_LIVRAISON) {
        String selectQuery = "select *  from " + TABLE_ARTICLE_PRIX +
                " WHERE " + KEY_CIRCUIT_CODE + " = '"+circuit_code+"'"+
                " AND " + KEY_ARTICLE_CODE +  " = '"+articlecode+"'"+
                 " AND " + KEY_UNITE_CODE +  " = '"+code_unite+"'"+
               " AND " +     "  '"+DATE_LIVRAISON+"'"   +  " BETWEEN " + KEY_DATE_DEBUT + " AND " + KEY_DATE_FIN +
                " AND " + KEY_INACTIF + " = '0' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Articleprix articleprix = new Articleprix();
        if (cursor != null && cursor.moveToFirst() && cursor.getCount() == 1) {
            articleprix.setARTICLEPRIX_CODE(cursor.getString(0));
            articleprix.setCIRCUIT_CODE(cursor.getString(1));
            articleprix.setARTICLE_CODE(cursor.getString(2));
            articleprix.setARTICLE_UPPRIX(3);
            articleprix.setARTICLE_USPRIX(4);
            articleprix.setDATE_DEBUT(cursor.getString(5));
            articleprix.setDATE_FIN(cursor.getString(6));
            articleprix.setDATE_CREATION(cursor.getString(7));
            articleprix.setCREATEUR_CODE(cursor.getString(8));
            articleprix.setINACTIF(cursor.getString(9));
            articleprix.setRAISON_INACTIF(cursor.getString(10));
            articleprix.setVERSION(cursor.getString(11));
            articleprix.setUNITE_CODE(cursor.getString(12));
            articleprix.setARTICLE_PRIX(cursor.getDouble(13));
            cursor.close();
            db.close();
            Log.d(TAG, "--get ArtcileAprix : " + articleprix.getARTICLE_CODE());
        }
        return articleprix;
    }


    public void deleteArticlePrix() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_ARTICLE_PRIX, null, null);
        db.close();
        Log.d(TAG, "Deleted all articles prix info from sqlite");
    }

    public int delete(String articlePrixcode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ARTICLE_PRIX,KEY_ARTICLEPRIX_CODE+"=?",new String[]{articlePrixcode});
    }

    public static void synchronisationArticlePrix(final Context context){

        // Tag used to cancel the request
        String tag_string_req = "ARTICLEPRIX";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_ARTICLE_PRIX, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                Log.d("articleprix", "onResponse: "+response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error == 1) {
                        JSONArray articlePrix = jObj.getJSONArray("ArticlePrix");
                        //Toast.makeText(context, "Nombre de ArticlePrix : " + articlePrix.length(), Toast.LENGTH_SHORT).show();
                        Log.d("Prix recu du serveurs ", "--@@" + articlePrix);
                        int cptInsert = 0, cptDeleted = 0;

                        if(articlePrix.length()>0) {
                            ArticlePrixManager articlePrixManager = new ArticlePrixManager(context);
                            for (int i = 0; i < articlePrix.length(); i++) {
                                JSONObject unarticlePrix = articlePrix.getJSONObject(i);
                                if (unarticlePrix.getString("OPERATION").equals("DELETE")) {
                                    for (int j = 0; j < 2; j++)
                                   //Toast.makeText(context, "unarticlePrix a supprimer   "+unarticlePrix.getString("ARTICLEPRIX_CODE"), Toast.LENGTH_SHORT).show();
                                    cptDeleted++;
                                    articlePrixManager.delete(unarticlePrix.getString("ARTICLEPRIX_CODE"));
                                } else {
                                    articlePrixManager.add(new Articleprix(unarticlePrix));
                                    cptInsert++;
                                }
                             }
                         }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("ARTICLEPRIX : Inserted: "+cptInsert +" Deleted: "+cptDeleted ,"ARTICLEPRIX",1));

                        }
                        //logM.add("ArticlePrix : OK Inserted "+cptInsert +"Deleted "+cptDeleted ,"SyncArticlePrix");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("ARTICLEPRIX : Error: "+errorMsg ,"ARTICLEPRIX",0));

                        }

                        //Toast.makeText(context, "ArticlePrix :"+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("ArticlePrix : NOK Inserted "+errorMsg ,"SyncArticlePrix");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "ArticlePrix :"+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("ArticlePrix : NOK Inserted "+e.getMessage() ,"SyncArticlePrix");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("ARTICLEPRIX : Error: "+e.getMessage() ,"ARTICLEPRIX",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "ArticlePrix :"+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("ArticlePrix : NOK Inserted "+error.getMessage() ,"SyncArticlePrix");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("ARTICLEPRIX : Error: "+error.getMessage() ,"ARTICLEPRIX",0));

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    Gson gson2 = new Gson();
                    String json2 = pref.getString("User", "");
                    Type type = new TypeToken<JSONObject>() {}.getType();
                    JSONObject user = gson2.fromJson(json2, type);

                    try {
                        params.put("DISTRIBUTEUR_CODE", user.getString("DISTRIBUTEUR_CODE"));
                    }catch (Exception e ){

                    }

                    ArticlePrixManager articlePrixManager  = new ArticlePrixManager(context);
                    ArrayList<Articleprix> articleprixList=articlePrixManager.getArticlePrix_code_version();
                    for (int i = 0; i < articleprixList.size(); i++) {
                        if(articleprixList.get(i).getARTICLEPRIX_CODE()!=null && articleprixList.get(i).getVERSION() != null )
                            params.put(articleprixList.get(i).getARTICLEPRIX_CODE(), articleprixList.get(i).getVERSION());
                        Log.d("paramEnvoye au serveur",articleprixList.get(i).getARTICLEPRIX_CODE()+"--@@--"+articleprixList.get(i).getVERSION());
                        Log.d("articleprix", "getParams: "+articleprixList.toString());

                    }
                }
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /*public static void synchronisationArticlePrixTest(final Context context){

        // Tag used to cancel the request
        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_ARTICLE_PRIX, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                Log.d("articleprix", "onResponse: "+response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error == 1) {
                        JSONArray articlePrix = jObj.getJSONArray("ArticlePrix");
                        Toast.makeText(context, "Nombre de ArticlePrix : " + articlePrix.length(), Toast.LENGTH_SHORT).show();
                        Log.d("Prix recu du serveurs ", "--@@" + articlePrix);
                        int cptInsert = 0, cptDeleted = 0;

                        if(articlePrix.length()>0) {
                            ArticlePrixManager articlePrixManager = new ArticlePrixManager(context);
                            for (int i = 0; i < articlePrix.length(); i++) {
                                JSONObject unarticlePrix = articlePrix.getJSONObject(i);
                                if (unarticlePrix.getString("OPERATION").equals("DELETE")) {
                                    for (int j = 0; j < 2; j++)
                                        //Toast.makeText(context, "unarticlePrix a supprimer   "+unarticlePrix.getString("ARTICLEPRIX_CODE"), Toast.LENGTH_SHORT).show();
                                        cptDeleted++;
                                    articlePrixManager.delete(unarticlePrix.getString("ARTICLEPRIX_CODE"));
                                } else {
                                    articlePrixManager.add(new Articleprix(unarticlePrix));
                                    cptInsert++;
                                }
                            }
                        }
                        logM.add("ArticlePrix : OK Inserted "+cptInsert +"Deleted "+cptDeleted ,"SyncArticlePrix");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(context,
                                "ArticlePrix :"+errorMsg, Toast.LENGTH_LONG).show();
                        logM.add("ArticlePrix : NOK Inserted "+errorMsg ,"SyncArticlePrix");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "ArticlePrix :"+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    logM.add("ArticlePrix : NOK Inserted "+e.getMessage() ,"SyncArticlePrix");

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        "ArticlePrix :"+error.getMessage(), Toast.LENGTH_LONG).show();
                LogSyncManager logM= new LogSyncManager(context);
                logM.add("ArticlePrix : NOK Inserted "+error.getMessage() ,"SyncArticlePrix");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    Gson gson2 = new Gson();
                    String json2 = pref.getString("User", "");
                    Type type = new TypeToken<JSONObject>() {}.getType();
                    JSONObject user = gson2.fromJson(json2, type);
                    try {
                        params.put("UTILISATEUR_CODE", user.getString("UTILISATEUR_CODE"));
                    }catch (Exception e ){

                    }
                    ArticlePrixManager articlePrixManager = new ArticlePrixManager(context);
                    List<Articleprix> articleprixes =articlePrixManager.getList();

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("DISTRIBUTEUR_CODE",pref.getString("DISTRIBUTEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(articleprixes));

                }
                return arrayFinale;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }*/

}