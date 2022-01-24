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
import com.newtech.newtech_sfm.Metier.Categorie;
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
public class CategorieManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Article table name
    public static final String TABLE_CATEGORIE = "categorie";

    //table tournee column
    private static final String
    KEY_CATEGORIE_CODE="CATEGORIE_CODE",
    KEY_CATEGORIE_NOM="CATEGORIE_NOM",
    KEY_CATEGORIE_CATEGORIE="CATEGORIE_CATEGORIE",
    KEY_DESCRIPTION="DESCRIPTION",
    KEY_VERSION="VERSION";

    public static  String CREATE_CATEGORIE_TABLE = "CREATE TABLE " + TABLE_CATEGORIE + "("
            +KEY_CATEGORIE_CODE + " TEXT PRIMARY KEY,"
            +KEY_CATEGORIE_NOM + " TEXT,"
            +KEY_CATEGORIE_CATEGORIE + " TEXT,"
            +KEY_DESCRIPTION + " TEXT,"
            +KEY_VERSION + " TEXT "+ ")";

    public CategorieManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAMILLE);
            db.execSQL(CREATE_CATEGORIE_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database FAMILLE tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIE);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing Articles details in database
     * */
    public void add(Categorie categorie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORIE_CODE, categorie.getCATEGORIE_CODE());
        values.put(KEY_CATEGORIE_NOM,categorie.getCATEGORIE_NOM());
        values.put(KEY_CATEGORIE_CATEGORIE, categorie.getCATEGORIE_CATEGORIE());
        values.put(KEY_DESCRIPTION, categorie.getDESCRIPTION());
        values.put(KEY_VERSION, categorie.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_CATEGORIE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New familles inserted into sqlite: " + id);
    }

    /**
     * Getting all tournee from database
     * */

    public ArrayList<Categorie> getList() {
        ArrayList<Categorie> categories= new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CATEGORIE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Categorie categorie = new Categorie();
                categorie.setCATEGORIE_CODE(cursor.getString(0));
                categorie.setCATEGORIE_NOM(cursor.getString(1));
                categorie.setCATEGORIE_CATEGORIE(cursor.getString(2));
                categorie.setDESCRIPTION(cursor.getString(3));
                categorie.setVERSION(cursor.getString(4));

                categories.add(categorie);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching all categorie from Sqlite: ");
        return categories;
    }

    public ArrayList<Categorie> getListByCateCode(String categorie_code) {
        ArrayList<Categorie> categories= new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CATEGORIE + " WHERE "+ KEY_CATEGORIE_CATEGORIE +" = '"+categorie_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Categorie categorie = new Categorie();
                categorie.setCATEGORIE_CODE(cursor.getString(0));
                categorie.setCATEGORIE_NOM(cursor.getString(1));
                categorie.setCATEGORIE_CATEGORIE(cursor.getString(2));
                categorie.setDESCRIPTION(cursor.getString(3));
                categorie.setVERSION(cursor.getString(4));

                categories.add(categorie);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching all categorie from Sqlite: ");
        return categories;
    }


    public ArrayList<Categorie> getCategorieCode_Version_List() {
        ArrayList<Categorie> categories = new ArrayList<>();

        String selectQuery = "SELECT "+ KEY_CATEGORIE_CODE+" , " +KEY_VERSION +  " FROM " + TABLE_CATEGORIE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Categorie categorie = new Categorie();
                categorie.setCATEGORIE_CODE(cursor.getString(0));
                categorie.setVERSION(cursor.getString(1));
                categories.add(categorie);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version  categories from Sqlite: ");
        return categories;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteCategories() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_CATEGORIE, null, null);
        db.close();
        Log.d(TAG, "Deleted all Famille info from sqlite");
    }

    public int delete(String catgorieCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CATEGORIE,KEY_CATEGORIE_CODE+"=?",new String[]{catgorieCode});
    }

    public Categorie get( String CATEGORIE_CODE) {

        String selectQuery = "SELECT * FROM " + TABLE_CATEGORIE + " WHERE "+ KEY_CATEGORIE_CODE +" = '"+CATEGORIE_CODE+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Categorie categorie = new Categorie();

        if( cursor != null && cursor.moveToFirst() ) {

            categorie.setCATEGORIE_CODE(cursor.getString(0));
            categorie.setCATEGORIE_NOM(cursor.getString(1));
            categorie.setCATEGORIE_CATEGORIE(cursor.getString(2));
            categorie.setDESCRIPTION(cursor.getString(3));
            categorie.setVERSION(cursor.getString(4));

        }

        cursor.close();
        db.close();
        return categorie;

    }


    public ArrayList<Categorie> getCategorie_textList() {
        ArrayList<Categorie> categories = new ArrayList<>();
            String selectQuery = "SELECT "+ KEY_CATEGORIE_CODE+ " , "+ KEY_CATEGORIE_NOM +  " FROM " + TABLE_CATEGORIE;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if( cursor != null && cursor.moveToFirst() ) {
                do {
                    Categorie categorie = new Categorie();
                    categorie.setCATEGORIE_CODE(cursor.getString(0));
                    categorie.setCATEGORIE_NOM(cursor.getString(1));
                    categories.add(categorie);
                }while(cursor.moveToNext());
            }
            //returner la listTournees;
            cursor.close();
            db.close();
            Log.d(TAG, "Fetching code/version categories from Sqlite: ");
            return categories;
        }


    public static void synchronisationCategorie(final Context context){

        String tag_string_req = "CATEGORIE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_CATEGORIE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Categorie", "onResponse: "+response);
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray Categories = jObj.getJSONArray("Categories");
                        //Toast.makeText(context, "nombre de Categories  "+Categories.length()  , Toast.LENGTH_SHORT).show();
                        if(Categories.length()>0) {
                            CategorieManager categorieManager = new CategorieManager(context);
                            for (int i = 0; i < Categories.length(); i++) {
                                JSONObject uneCategorie = Categories.getJSONObject(i);
                                if (uneCategorie.getString("OPERATION").equals("DELETE")) {
                                    categorieManager.delete(uneCategorie.getString(KEY_CATEGORIE_CODE));
                                    cptDelete++;
                                } else {
                                    categorieManager.add(new Categorie(uneCategorie));
                                    cptInsert++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CATEGORIE: Inserted: "+cptInsert +" Deleted: "+cptDelete ,"CATEGORIE",1));

                        }
                        //logM.add("Categories:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncCategorie");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(context, "Categorie : "+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("Categorie:NOK "+errorMsg ,"SyncCategorie");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CATEGORIE: Error: "+errorMsg ,"CATEGORIE",0));

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CATEGORIE: Error: "+e.getMessage() ,"CATEGORIE",0));

                    }
                    //Toast.makeText(context, "Categorie : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("Categorie:NOK "+e.getMessage() ,"SyncCategorie");

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Categorie : "+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("Categorie:NOK "+error.getMessage() ,"SyncCategorie");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CATEGORIE: Error: "+error.getMessage() ,"CATEGORIE",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    CategorieManager categorieManager = new CategorieManager(context);
                    List<Categorie> categories =categorieManager.getCategorieCode_Version_List();
                    for (int i = 0; i < categories.size(); i++) {
                        Log.d("test famille-@-version",categories.get(i).getCATEGORIE_CODE()+"-@@-" +categories.get(i).getVERSION());
                        if(categories.get(i).getCATEGORIE_CODE()!=null && categories.get(i).getVERSION() != null ) {
                            params.put(categories.get(i).getCATEGORIE_CODE(), categories.get(i).getVERSION());
                        }
                    }
                }
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}


