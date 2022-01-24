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
import com.newtech.newtech_sfm.Metier.LogSync;
import com.newtech.newtech_sfm.Metier.Unite;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stagiaireit2 on 08/08/2016.
 */
public class UniteManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Article table name
    public static final String TABLE_UNITE = "unite";

    //table tournee column
    private static final String
    KEY_UNITE_CODE = "UNITE_CODE",
    KEY_UNITE_NOM = "UNITE_NOM",
    KEY_DESCRIPTION = "DESCRIPTION",
    KEY_TYPE_CODE = "TYPE_CODE",
    KEY_STATUT_CODE = "STATUT_CODE",
    KEY_CATEGORIE_CODE = "CATEGORIE_CODE",
    KEY_INACTIF = "INACTIF",
    KEY_INACTIF_RAISON = "INACTIF_RAISON",
    KEY_ARTICLE_CODE = "ARTICLE_CODE",
    KEY_FACTEUR_CONVERSION = "FACTEUR_CONVERSION",
    KEY_IMAGE = "IMAGE",
    KEY_LITTRAGE = "LITTRAGE",
    KEY_POIDKG = "POIDKG",
    KEY_VERSION = "VERSION";

    public static  String CREATE_UNITE_TABLE = "CREATE TABLE " + TABLE_UNITE + "("
            +KEY_UNITE_CODE + " TEXT ,"
            +KEY_UNITE_NOM + " TEXT,"
            +KEY_DESCRIPTION + " TEXT,"
            +KEY_TYPE_CODE + " TEXT,"
            +KEY_STATUT_CODE + " TEXT,"
            +KEY_CATEGORIE_CODE + " TEXT,"
            +KEY_INACTIF + " TEXT,"
            +KEY_INACTIF_RAISON + " TEXT,"
            +KEY_ARTICLE_CODE + " TEXT,"
            +KEY_FACTEUR_CONVERSION + " NUMERIC,"
            +KEY_IMAGE + " TEXT,"
            +KEY_LITTRAGE + " NUMERIC,"
            +KEY_POIDKG + " NUMERIC,"
            +KEY_VERSION + " TEXT,"
            +"PRIMARY KEY ("+KEY_UNITE_CODE+" , " +KEY_VERSION +")"+")";

    public UniteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_UNITE);
            db.execSQL(CREATE_UNITE_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database UNITE tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNITE);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing Articles details in database
     * */
    public void add(Unite unite) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_UNITE_CODE, unite.getUNITE_CODE());
        values.put(KEY_UNITE_NOM,unite.getUNITE_NOM());
        values.put(KEY_DESCRIPTION, unite.getDESCRIPTION());
        values.put(KEY_TYPE_CODE,unite.getTYPE_CODE());
        values.put(KEY_STATUT_CODE,unite.getSTATUT_CODE());
        values.put(KEY_CATEGORIE_CODE,unite.getCATEGORIE_CODE());
        values.put(KEY_INACTIF, unite.getINACTIF());
        values.put(KEY_INACTIF_RAISON, unite.getINACTIF_RAISON());
        values.put(KEY_ARTICLE_CODE, unite.getARTICLE_CODE());
        values.put(KEY_FACTEUR_CONVERSION, unite.getFACTEUR_CONVERSION());
        values.put(KEY_IMAGE, unite.getIMAGE());
        values.put(KEY_LITTRAGE, unite.getLITTRAGE());
        values.put(KEY_POIDKG, unite.getPOIDKG());
        values.put(KEY_VERSION, unite.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_UNITE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New unite inserted into sqlite: " + id);
    }

    public Unite get( String UNITE_CODE) {

        String selectQuery = "SELECT * FROM " + TABLE_UNITE + " WHERE "+ KEY_UNITE_CODE +" = '"+UNITE_CODE+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Unite unite = new Unite();

        if( cursor != null && cursor.moveToFirst() ) {

            unite.setUNITE_CODE(cursor.getString(0));
            unite.setUNITE_NOM(cursor.getString(1));
            unite.setDESCRIPTION(cursor.getString(2));
            unite.setTYPE_CODE(cursor.getString(3));
            unite.setSTATUT_CODE(cursor.getString(4));
            unite.setCATEGORIE_CODE(cursor.getString(5));
            unite.setINACTIF(cursor.getString(6));
            unite.setINACTIF_RAISON(cursor.getString(7));
            unite.setARTICLE_CODE(cursor.getString(8));
            unite.setFACTEUR_CONVERSION(cursor.getDouble(9));
            unite.setIMAGE(cursor.getString(10));
            unite.setLITTRAGE(cursor.getDouble(11));
            unite.setPOIDKG(cursor.getDouble(12));
            unite.setVERSION(cursor.getString(13));

        }

        cursor.close();
        db.close();
        return unite;

    }

    public Unite getMaxFc( String ARTICLE_CODE) {

        String selectQuery = "SELECT * FROM " + TABLE_UNITE + " WHERE "+ KEY_ARTICLE_CODE +" = '"+ARTICLE_CODE+"' AND "+ KEY_FACTEUR_CONVERSION +" IN (select MAX(unite.FACTEUR_CONVERSION) from unite WHERE unite.ARTICLE_CODE "+" = '"+ARTICLE_CODE+"')";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Unite unite = new Unite();

        if( cursor != null && cursor.moveToFirst() ) {

            unite.setUNITE_CODE(cursor.getString(0));
            unite.setUNITE_NOM(cursor.getString(1));
            unite.setDESCRIPTION(cursor.getString(2));
            unite.setTYPE_CODE(cursor.getString(3));
            unite.setSTATUT_CODE(cursor.getString(4));
            unite.setCATEGORIE_CODE(cursor.getString(5));
            unite.setINACTIF(cursor.getString(6));
            unite.setINACTIF_RAISON(cursor.getString(7));
            unite.setARTICLE_CODE(cursor.getString(8));
            unite.setFACTEUR_CONVERSION(cursor.getDouble(9));
            unite.setIMAGE(cursor.getString(10));
            unite.setLITTRAGE(cursor.getDouble(11));
            unite.setPOIDKG(cursor.getDouble(12));
            unite.setVERSION(cursor.getString(13));

        }

        cursor.close();
        db.close();
        return unite;

    }


    /**
     * Getting all tournee from database
     * */

    public ArrayList<Unite> getList() {
        ArrayList<Unite> unites= new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_UNITE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Unite unite = new Unite();
                unite.setUNITE_CODE(cursor.getString(0));
                unite.setUNITE_NOM(cursor.getString(1));
                unite.setDESCRIPTION(cursor.getString(2));
                unite.setTYPE_CODE(cursor.getString(3));
                unite.setSTATUT_CODE(cursor.getString(4));
                unite.setCATEGORIE_CODE(cursor.getString(5));
                unite.setINACTIF(cursor.getString(6));
                unite.setINACTIF_RAISON(cursor.getString(7));
                unite.setARTICLE_CODE(cursor.getString(8));
                unite.setFACTEUR_CONVERSION(cursor.getDouble(9));
                unite.setIMAGE(cursor.getString(10));
                unite.setLITTRAGE(cursor.getDouble(11));
                unite.setPOIDKG(cursor.getDouble(12));
                unite.setVERSION(cursor.getString(13));


                unites.add(unite);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching all unite from Sqlite: ");
        return unites;
    }

    public ArrayList<Unite> getListByArticleCode(String article_code) {
        ArrayList<Unite> unites= new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_UNITE + " WHERE "+KEY_ARTICLE_CODE+"='"+article_code+"';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Unite unite = new Unite();
                unite.setUNITE_CODE(cursor.getString(0));
                unite.setUNITE_NOM(cursor.getString(1));
                unite.setDESCRIPTION(cursor.getString(2));
                unite.setTYPE_CODE(cursor.getString(3));
                unite.setSTATUT_CODE(cursor.getString(4));
                unite.setCATEGORIE_CODE(cursor.getString(5));
                unite.setINACTIF(cursor.getString(6));
                unite.setINACTIF_RAISON(cursor.getString(7));
                unite.setARTICLE_CODE(cursor.getString(8));
                unite.setFACTEUR_CONVERSION(cursor.getDouble(9));
                unite.setIMAGE(cursor.getString(10));
                unite.setLITTRAGE(cursor.getDouble(11));
                unite.setPOIDKG(cursor.getDouble(12));
                unite.setVERSION(cursor.getString(13));


                unites.add(unite);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching all unite from Sqlite: ");
        return unites;
    }

    public ArrayList<Unite> getListTest() {
        ArrayList<Unite> unites= new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_UNITE + " WHERE "+KEY_UNITE_CODE+" >= "+"'47'"+" ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Unite unite = new Unite();
                unite.setUNITE_CODE(cursor.getString(0));
                unite.setUNITE_NOM(cursor.getString(1));
                unite.setDESCRIPTION(cursor.getString(2));
                unite.setTYPE_CODE(cursor.getString(3));
                unite.setSTATUT_CODE(cursor.getString(4));
                unite.setCATEGORIE_CODE(cursor.getString(5));
                unite.setINACTIF(cursor.getString(6));
                unite.setINACTIF_RAISON(cursor.getString(7));
                unite.setARTICLE_CODE(cursor.getString(8));
                unite.setFACTEUR_CONVERSION(cursor.getDouble(9));
                unite.setIMAGE(cursor.getString(10));
                unite.setLITTRAGE(cursor.getDouble(11));
                unite.setPOIDKG(cursor.getDouble(12));
                unite.setVERSION(cursor.getString(13));


                unites.add(unite);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching all unite from Sqlite: ");
        return unites;
    }


    public ArrayList<Unite> getListByACDESC(String article_code) {
        ArrayList<Unite> unites= new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_UNITE + " WHERE "+KEY_ARTICLE_CODE+"='"+article_code+"' ORDER BY "+ KEY_FACTEUR_CONVERSION + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Unite unite = new Unite();
                unite.setUNITE_CODE(cursor.getString(0));
                unite.setUNITE_NOM(cursor.getString(1));
                unite.setDESCRIPTION(cursor.getString(2));
                unite.setTYPE_CODE(cursor.getString(3));
                unite.setSTATUT_CODE(cursor.getString(4));
                unite.setCATEGORIE_CODE(cursor.getString(5));
                unite.setINACTIF(cursor.getString(6));
                unite.setINACTIF_RAISON(cursor.getString(7));
                unite.setARTICLE_CODE(cursor.getString(8));
                unite.setFACTEUR_CONVERSION(cursor.getDouble(9));
                unite.setIMAGE(cursor.getString(10));
                unite.setLITTRAGE(cursor.getDouble(11));
                unite.setPOIDKG(cursor.getDouble(12));
                unite.setVERSION(cursor.getString(13));


                unites.add(unite);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching all unite from Sqlite: ");
        return unites;
    }


    public ArrayList<Unite> getListByArticleCode(String article_code,String unite_code) {
        ArrayList<Unite> unites= new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_UNITE + " WHERE "+KEY_ARTICLE_CODE+"='"+article_code+"' AND "+ KEY_UNITE_CODE +" = '"+unite_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Unite unite = new Unite();
                unite.setUNITE_CODE(cursor.getString(0));
                unite.setUNITE_NOM(cursor.getString(1));
                unite.setDESCRIPTION(cursor.getString(2));
                unite.setTYPE_CODE(cursor.getString(3));
                unite.setSTATUT_CODE(cursor.getString(4));
                unite.setCATEGORIE_CODE(cursor.getString(5));
                unite.setINACTIF(cursor.getString(6));
                unite.setINACTIF_RAISON(cursor.getString(7));
                unite.setARTICLE_CODE(cursor.getString(8));
                unite.setFACTEUR_CONVERSION(cursor.getDouble(9));
                unite.setIMAGE(cursor.getString(10));
                unite.setLITTRAGE(cursor.getDouble(11));
                unite.setPOIDKG(cursor.getDouble(12));
                unite.setVERSION(cursor.getString(13));


                unites.add(unite);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching all unite from Sqlite: ");
        return unites;
    }


    public Unite getByACUC(String article_code, String unite_code){
        String selectQuery = "SELECT * FROM " + TABLE_UNITE + " WHERE "+ KEY_ARTICLE_CODE +" = '"+article_code+"' AND "+ KEY_UNITE_CODE +" = '"+unite_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Unite unite = new Unite();

        if( cursor != null && cursor.moveToFirst() ) {

            unite.setUNITE_CODE(cursor.getString(0));
            unite.setUNITE_NOM(cursor.getString(1));
            unite.setDESCRIPTION(cursor.getString(2));
            unite.setTYPE_CODE(cursor.getString(3));
            unite.setSTATUT_CODE(cursor.getString(4));
            unite.setCATEGORIE_CODE(cursor.getString(5));
            unite.setINACTIF(cursor.getString(6));
            unite.setINACTIF_RAISON(cursor.getString(7));
            unite.setARTICLE_CODE(cursor.getString(8));
            unite.setFACTEUR_CONVERSION(cursor.getDouble(9));
            unite.setIMAGE(cursor.getString(10));
            unite.setLITTRAGE(cursor.getDouble(11));
            unite.setPOIDKG(cursor.getDouble(12));
            unite.setVERSION(cursor.getString(13));


        }

        cursor.close();
        db.close();
        return unite;
    }

    public ArrayList<Unite> getUniteCode_Version_List() {
        ArrayList<Unite> unites = new ArrayList<>();

        String selectQuery = "SELECT "+ KEY_UNITE_CODE+" , " +KEY_VERSION +  " FROM " + TABLE_UNITE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Unite unite = new Unite();
                unite.setUNITE_CODE(cursor.getString(0));
                unite.setVERSION(cursor.getString(1));
                unites.add(unite);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version  unites from Sqlite: ");
        return unites;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUnites() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_UNITE, null, null);
        db.close();
        Log.d(TAG, "Deleted all Unites info from sqlite");
    }

    public int delete(String uniteCode,String version) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_UNITE,KEY_UNITE_CODE+"=? AND "+KEY_VERSION+"=?",new String[]{uniteCode,version});
    }


    public ArrayList<Unite> getUnite_textList() {
        ArrayList<Unite> unites = new ArrayList<>();
            String selectQuery = "SELECT "+ KEY_UNITE_CODE+ " , "+ KEY_UNITE_NOM+  " FROM " + TABLE_UNITE;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if( cursor != null && cursor.moveToFirst() ) {
                do {
                    Unite unite = new Unite();
                    unite.setUNITE_CODE(cursor.getString(0));
                    unite.setUNITE_NOM(cursor.getString(1));
                    unites.add(unite);
                }while(cursor.moveToNext());
            }
            //returner la listTournees;
            cursor.close();
            db.close();
            Log.d(TAG, "Fetching code/version unites from Sqlite: ");
            return unites;
    }


    public static void synchronisationUnite(final Context context){

        String tag_string_req = "UNITE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_UNITE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Unite", "onResponse: "+response);
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray Unites = jObj.getJSONArray("Unites");
                        //Toast.makeText(context, "nombre de Unites  "+Unites.length()  , Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onResponse: unites"+Unites.length());

                        if(Unites.length()>0) {
                            UniteManager uniteManager = new UniteManager(context);
                            for (int i = 0; i < Unites.length(); i++) {
                                JSONObject uneUnite = Unites.getJSONObject(i);

                                if (uneUnite.getString("OPERATION").equals("DELETE")) {
                                    Log.d(TAG, "onResponse: unites delete ");
                                    uniteManager.delete(uneUnite.getString("UNITE_CODE"),uneUnite.getString("VERSION"));
                                    cptDelete++;

                                } else {
                                    Log.d(TAG, "onResponse: unites add ");
                                    uniteManager.add(new Unite(uneUnite));
                                    cptInsert++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("UNITE: Inserted: "+cptInsert +" Deleted: "+cptDelete ,"UNITE",1));

                        }

                        //logM.add("Unites:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncUnite");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(context, "Unite : "+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("Unite:NOK "+errorMsg ,"SyncUnite");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("UNITE: Error: "+errorMsg ,"UNITE",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "Unite : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("Unite:NOK "+e.getMessage() ,"SyncUnite");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("UNITE: Error: "+e.getMessage() ,"UNITE",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Unite : "+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("Unite:NOK "+error.getMessage() ,"SyncUnite");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("UNITE: Error: "+error.getMessage() ,"UNITE",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    UniteManager uniteManager = new UniteManager(context);
                    List<Unite> unites =uniteManager.getUniteCode_Version_List();
                    for (int i = 0; i < unites.size(); i++) {
                        Log.d("test unites-@-version",unites.get(i).getUNITE_CODE()+"-@@-" +unites.get(i).getVERSION());

                        if(unites.get(i).getUNITE_CODE()!=null && unites.get(i).getVERSION() != null ) {
                            params.put(unites.get(i).getUNITE_CODE(), unites.get(i).getVERSION());
                        }
                    }
                }
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}


