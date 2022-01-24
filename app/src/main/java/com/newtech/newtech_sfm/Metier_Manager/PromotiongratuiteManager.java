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
import com.newtech.newtech_sfm.Metier.Promotiongratuite;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stagiaireit2 on 22/07/2016.
 */
public class PromotiongratuiteManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    // All Static variables

    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Article table name
    public static final String TABLE_PROMOTIONGRATUITE = "promotiongratuite";

    // Promotion Table Columns names
    private static final String
            KEY_ID = "ID",
            KEY_PROMO_CODE="PROMO_CODE",
            KEY_CATEGORIE_CODE="CATEGORIE_CODE",
            KEY_TYPE_CODE="TYPE_CODE",
            KEY_VALEUR_GR="VALEUR_GR",
            KEY_DATE_CREATION="DATE_CREATION",
            KEY_VERSION="VERSION";

    public static   String CREATE_PROMOTIONGRATUITE_TABLE = "CREATE TABLE " + TABLE_PROMOTIONGRATUITE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_PROMO_CODE + " TEXT,"
            + KEY_CATEGORIE_CODE + " TEXT,"
            + KEY_TYPE_CODE + " TEXT,"
            + KEY_VALEUR_GR + " TEXT,"
            + KEY_DATE_CREATION + " TEXT,"
            + KEY_VERSION + " TEXT" + ")";
    public PromotiongratuiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_PROMOTIONGRATUITE);
            db.execSQL(CREATE_PROMOTIONGRATUITE_TABLE);
        } catch (SQLException e) {

        }
        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROMOTIONGRATUITE);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing Articles details in database
     * */
    public void add(Promotiongratuite promotiongratuite) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PROMO_CODE, promotiongratuite.getPROMO_CODE());
        values.put(KEY_CATEGORIE_CODE,promotiongratuite.getCATEGORIE_CODE());
        values.put(KEY_TYPE_CODE, promotiongratuite.getTYPE_CODE());
        values.put(KEY_VALEUR_GR, promotiongratuite.getVALEUR_GR());
        values.put(KEY_DATE_CREATION, promotiongratuite.getDATE_CREATION());
        values.put(KEY_VERSION, promotiongratuite.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_PROMOTIONGRATUITE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New promotions inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public Promotiongratuite get(String promo_code) {
        Promotiongratuite promotiongratuite = new Promotiongratuite();

        String selectQuery = "SELECT  * FROM " + TABLE_PROMOTIONGRATUITE+" WHERE "+KEY_PROMO_CODE+"='"+promo_code+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {

                promotiongratuite.setPROMO_CODE(cursor.getString(1));
                promotiongratuite.setCATEGORIE_CODE(cursor.getString(2));
                promotiongratuite.setTYPE_CODE(cursor.getString(3));
                promotiongratuite.setVALEUR_GR(cursor.getString(4));
                promotiongratuite.setDATE_CREATION(cursor.getString(5));
                promotiongratuite.setVERSION(cursor.getString(6));

        }

        //returner la listPromotionarticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Promotiongratuite 1 from Sqlite: ");

        return promotiongratuite;
    }

    public ArrayList<Promotiongratuite> getList() {
        ArrayList<Promotiongratuite> listPromotiongratuites = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_PROMOTIONGRATUITE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Promotiongratuite promotiongratuite = new Promotiongratuite();

                promotiongratuite.setPROMO_CODE(cursor.getString(1));
                promotiongratuite.setCATEGORIE_CODE(cursor.getString(2));
                promotiongratuite.setTYPE_CODE(cursor.getString(3));
                promotiongratuite.setVALEUR_GR(cursor.getString(4));
                promotiongratuite.setDATE_CREATION(cursor.getString(5));
                promotiongratuite.setVERSION(cursor.getString(6));

                listPromotiongratuites.add(promotiongratuite);
            }while(cursor.moveToNext());
        }

        //returner la listPromotionarticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Promotiongratuites from Sqlite: ");
        return listPromotiongratuites;
    }

    public ArrayList<Promotiongratuite> getPromo_Code_Version_list() {
        ArrayList<Promotiongratuite> listPromotiongratuites = new ArrayList<>();

        String selectQuery = "SELECT  " + KEY_PROMO_CODE+","+KEY_VERSION  +  " FROM "  +TABLE_PROMOTIONGRATUITE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Promotiongratuite promotiongratuite = new Promotiongratuite();

                promotiongratuite.setPROMO_CODE(cursor.getString(0));
                promotiongratuite.setVERSION(cursor.getString(1));

                listPromotiongratuites.add(promotiongratuite);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Promotiongratuites from Sqlite: ");
        return listPromotiongratuites;
    }



    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deletePromotiongratuites() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_PROMOTIONGRATUITE, null, null);
        db.close();
        Log.d(TAG, "Deleted all promotiongratuites info from sqlite");
    }

    /*public int delete(String promocode) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PROMOTIONGRATUITE,KEY_PROMO_CODE+"=?",new String[]{promocode});
    }*/

    public int delete(String tacheCode,String version) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PROMOTIONGRATUITE,KEY_PROMO_CODE+"=? AND "+KEY_VERSION+"=?",new String[]{tacheCode,version});
    }

    public Boolean exist(String promo_code){
       Boolean result=false;
        String selectQuery = "SELECT  * FROM "+TABLE_PROMOTIONGRATUITE+" WHERE "+KEY_PROMO_CODE+"='"+promo_code+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if( cursor != null && cursor.getCount()>0 ) {
            result=true;
        }
        cursor.close();
        db.close();
        return result;
    }

    public static void synchronisationPromotionGratuite(final Context context){

        String tag_string_req = "PROMOTION_GRATUITE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_PROMOTION_GRATUITE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                Log.d("promotions", "onResponse: "+response);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray promotions_gratuite = jObj.getJSONArray("Promotiongratuites");
                       //Toast.makeText(context, "nombre de promotions_gratuite  "+promotions_gratuite.length(), Toast.LENGTH_SHORT).show();
                        if(promotions_gratuite.length()>0) {
                            PromotiongratuiteManager promotiongratuiteManager = new PromotiongratuiteManager(context);
                            for (int i = 0; i < promotions_gratuite.length(); i++) {
                                JSONObject Unepromotions_gratuite = promotions_gratuite.getJSONObject(i);
                                if (Unepromotions_gratuite.getString("OPERATION").equals("DELETE")) {
                                    promotiongratuiteManager.delete(Unepromotions_gratuite.getString("PROMO_CODE"),Unepromotions_gratuite.getString("VERSION"));
                                    cptDelete++;
                                } else {promotiongratuiteManager.add(new Promotiongratuite(Unepromotions_gratuite));
                                        cptInsert++;
                                    }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PROMOTION_GRATUITE: Inserted: "+cptInsert +" Deleted: "+cptDelete ,"PROMOTION_GRATUITE",1));

                        }

                        //logM.add("PROMOTION_GRATUITE:OK Insert:"+cptInsert +"Deleted:"+cptDelete ,"PROMOTION_GRATUITE");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(context,"PromoGR : "+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("PROMOTION_GRATUITE:OK Insert:"+errorMsg,"PROMOTION_GRATUITE");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PROMOTION_GRATUITE: Error:"+errorMsg ,"PROMOTION_GRATUITE",0));

                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "PromoGR : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("PROMOTION_GRATUITE:OK Insert:"+e.getMessage() ,"PROMOTION_GRATUITE");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PROMOTION_GRATUITE: Error:"+e.getMessage() ,"PROMOTION_GRATUITE",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "PromoGR : "+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("PROMOTION_GRATUITE:OK Insert:"+error.getMessage() ,"PROMOTION_GRATUITE");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PROMOTION_GRATUITE: Error:"+error.getMessage() ,"PROMOTION_GRATUITE",0));

                }


            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //Map<String, String> params = new HashMap<String, String>();
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    PromotiongratuiteManager promotiongratuiteManager= new PromotiongratuiteManager(context);
                    List<Promotiongratuite> promotiongratuites = promotiongratuiteManager.getList();

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    Log.d("UTILISATEUR_CODE PROMOTION GRATUITE SYNC",gson.toJson(pref.getString("UTILISATEUR_CODE",null)));

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(promotiongratuites));
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
