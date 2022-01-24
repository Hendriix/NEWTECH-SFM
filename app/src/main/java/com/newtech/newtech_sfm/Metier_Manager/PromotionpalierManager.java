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
import com.newtech.newtech_sfm.Metier.Promotionpalier;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stagiaireit2 on 22/07/2016.
 */
public class PromotionpalierManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    // All Static variables

    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Article table name
    public static final String TABLE_PROMOTIONPALIER = "promotionpalier";

    // Promotion Table Columns names
    private static final String
            KEY_PROMO_ID="PROMO_ID",
            KEY_PROMO_CODE="PROMO_CODE",
            KEY_PROMO_BASE="PROMO_BASE",
            KEY_VALEUR_PBASE="VALEUR_PBASE",
            KEY_VALEUR_PROMO="VALEUR_PROMO",
            KEY_PROMO_CATEGORIE="PROMO_CATEGORIE",
            KEY_PROMO_GRATUITE="PROMO_GRATUITE",
            KEY_DATE_CREATION="DATE_CREATION",
            KEY_VERSION="VERSION";

    public static String CREATE_PROMOTIONPALIER_TABLE = "CREATE TABLE " + TABLE_PROMOTIONPALIER + "("
            +KEY_PROMO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            +KEY_PROMO_CODE + " TEXT,"
            + KEY_PROMO_BASE + " TEXT,"
            + KEY_VALEUR_PBASE + " NUMERIC,"
            + KEY_VALEUR_PROMO + " NUMERIC,"
            + KEY_PROMO_CATEGORIE + " TEXT,"
            + KEY_PROMO_GRATUITE + " TEXT,"
            + KEY_DATE_CREATION + " TEXT,"
            + KEY_VERSION + " TEXT" + ")";
    public PromotionpalierManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_PROMOTIONPALIER);
            db.execSQL(CREATE_PROMOTIONPALIER_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROMOTIONPALIER);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing Articles details in database
     * */
    public void add(Promotionpalier promotionpalier) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PROMO_CODE, promotionpalier.getPROMO_CODE());
        values.put(KEY_PROMO_BASE,promotionpalier.getPROMO_BASE());
        values.put(KEY_VALEUR_PBASE, promotionpalier.getVALEUR_PBASE());
        values.put(KEY_VALEUR_PROMO, promotionpalier.getVALEUR_PROMO());
        values.put(KEY_PROMO_CATEGORIE, promotionpalier.getPROMO_CATEGORIE());
        values.put(KEY_PROMO_GRATUITE, promotionpalier.getPROMO_GRATUITE());
        values.put(KEY_DATE_CREATION, promotionpalier.getDATE_CREATION());
        values.put(KEY_VERSION, promotionpalier.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_PROMOTIONPALIER, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New promotionpalier inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public ArrayList<Promotionpalier> getList() {
        ArrayList<Promotionpalier> listPromotionpaliers = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_PROMOTIONPALIER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Promotionpalier promotionpaliere = new Promotionpalier();

                promotionpaliere.setPROMO_ID(cursor.getInt(0));
                promotionpaliere.setPROMO_CODE(cursor.getString(1));
                promotionpaliere.setPROMO_BASE(cursor.getString(2));
                promotionpaliere.setVALEUR_PBASE(cursor.getString(3));
                promotionpaliere.setVALEUR_PROMO(cursor.getString(4));
                promotionpaliere.setPROMO_CATEGORIE(cursor.getString(5));
                promotionpaliere.setPROMO_GRATUITE(cursor.getString(6));
                promotionpaliere.setDATE_CREATION(cursor.getString(7));
                promotionpaliere.setVERSION(cursor.getString(8));

                listPromotionpaliers.add(promotionpaliere);
            }while(cursor.moveToNext());
        }

        //returner la listPromotionarticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching promotionpaliere from Sqlite: ");
        return listPromotionpaliers;
    }



    public ArrayList<Promotionpalier> getPromotionP_code_version() {
        ArrayList<Promotionpalier> listPromotionpaliers = new ArrayList<>();

        String selectQuery = "SELECT  " + KEY_PROMO_CODE+","+KEY_VERSION  +  " FROM "+ TABLE_PROMOTIONPALIER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Promotionpalier promotionpaliere = new Promotionpalier();

                promotionpaliere.setPROMO_CODE(cursor.getString(0));
                promotionpaliere.setVERSION(cursor.getString(1));
                listPromotionpaliers.add(promotionpaliere);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching promotionpaliere from Sqlite: ");
        return listPromotionpaliers;
    }

    //remise
    public ArrayList<Promotionpalier> getPromoPalierByVBase(String promo_code,String promo_base,Double promo_Vbase){
        ArrayList<Promotionpalier> list_Promotion_Palier = new ArrayList<Promotionpalier>();

        String selectQuery = "SELECT  * FROM " + TABLE_PROMOTIONPALIER + " WHERE " + KEY_PROMO_CODE + "='"+promo_code+
                "' and "+KEY_PROMO_BASE+"='"+promo_base+
                "' and '"+promo_Vbase+"'>="+KEY_VALEUR_PBASE+" ;";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d("Remise", "query : "+selectQuery.toString()+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Promotionpalier promotionpaliere = new Promotionpalier();

                promotionpaliere.setPROMO_ID(cursor.getInt(0));
                promotionpaliere.setPROMO_CODE(cursor.getString(1));
                promotionpaliere.setPROMO_BASE(cursor.getString(2));
                promotionpaliere.setVALEUR_PBASE(cursor.getString(3));
                promotionpaliere.setVALEUR_PROMO(cursor.getString(4));
                promotionpaliere.setPROMO_CATEGORIE(cursor.getString(5));
                promotionpaliere.setPROMO_GRATUITE(cursor.getString(6));
                promotionpaliere.setDATE_CREATION(cursor.getString(7));
                promotionpaliere.setVERSION(cursor.getString(8));

                list_Promotion_Palier.add(promotionpaliere);
            }while(cursor.moveToNext());
        }

        //returner la listPromotionarticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching remisepromotionpaliere from Sqlite: ");
        return list_Promotion_Palier;
    }

    public Promotionpalier getMaxPromoPalierByVBase(String promo_code,String promo_base,Double promo_Vbase){
        Promotionpalier promotionpalier = new Promotionpalier();

        String selectQuery = "SELECT * FROM " + TABLE_PROMOTIONPALIER + " WHERE "+KEY_PROMO_ID+" IN (SELECT MAX(PROMO_ID) FROM "+TABLE_PROMOTIONPALIER+
                " WHERE "+KEY_PROMO_CODE+"='"+promo_code+"' "+
                "and "+KEY_PROMO_BASE+"='"+promo_base+"' "+
                "and '"+promo_Vbase+"' >= "+KEY_VALEUR_PBASE+" "+
                ") ; ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d("Remise", "query"+selectQuery.toString()+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {


                promotionpalier.setPROMO_ID(cursor.getInt(0));
                promotionpalier.setPROMO_CODE(cursor.getString(1));
                promotionpalier.setPROMO_BASE(cursor.getString(2));
                promotionpalier.setVALEUR_PBASE(cursor.getString(3));
                promotionpalier.setVALEUR_PROMO(cursor.getString(4));
                promotionpalier.setPROMO_CATEGORIE(cursor.getString(5));
                promotionpalier.setPROMO_GRATUITE(cursor.getString(6));
                promotionpalier.setDATE_CREATION(cursor.getString(7));
                promotionpalier.setVERSION(cursor.getString(8));



        }

        //returner la listPromotionarticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching remise promotionpaliere MAX from Sqlite: "+cursor.getCount());
        return promotionpalier;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deletePromotionpalieres() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_PROMOTIONPALIER, null, null);
        db.close();
        Log.d(TAG, "Deleted all promotionpaliers info from sqlite");
    }

    /*public int delete(String promocode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PROMOTIONPALIER,KEY_PROMO_CODE+"=?",new String[]{promocode});
    }*/

    public int delete(String tacheCode,String version) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PROMOTIONPALIER,KEY_PROMO_CODE+"=? AND "+KEY_VERSION+"=?",new String[]{tacheCode,version});
    }

    public static void synchronisationPromotionPalier(final Context context){

        String tag_string_req = "PROMOTION_PALIER";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_PROMOTION_PALIER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray promotions_Paliers = jObj.getJSONArray("Promotionpaliers");
                        //Toast.makeText(context, "nombre de Promotionpaliers  "+promotions_Paliers.length() , Toast.LENGTH_SHORT).show();
                        if(promotions_Paliers.length()>0) {
                            PromotionpalierManager promotionpalierManager = new PromotionpalierManager(context);
                            for (int i = 0; i < promotions_Paliers.length(); i++) {
                                JSONObject Unepromotions_palier = promotions_Paliers.getJSONObject(i);
                                if (Unepromotions_palier.getString("OPERATION").equals("DELETE")) {
                                    promotionpalierManager.delete(Unepromotions_palier.getString("PROMO_CODE"),Unepromotions_palier.getString("VERSION"));
                                    cptDelete++;
                                } else {
                                    promotionpalierManager.add(new Promotionpalier(Unepromotions_palier));cptInsert++;
                                  }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PROMOTION_PALIER: Inserted:"+cptInsert +" Deleted: "+cptDelete ,"PROMOTION_PALIER",1));

                        }

                        //logM.add("PROMOTION_PALIER:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"PROMOTION_PALIER");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(context, "PromoPL :"+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("PROMOTION_PALIER:NOK Insert:"+errorMsg,"PROMOTION_PALIER");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PROMOTION_PALIER: Error: "+errorMsg ,"PROMOTION_PALIER",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "PromoPL :"+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("PROMOTION_PALIER:NOK Insert:"+e.getMessage(),"PROMOTION_PALIER");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PROMOTION_PALIER: Error: "+e.getMessage() ,"PROMOTION_PALIER",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "PromoPL :"+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("PROMOTION_PALIER:NOK Insert:"+error.getMessage(),"PROMOTION_PALIER");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PROMOTION_PALIER: Error: "+error.getMessage() ,"PROMOTION_PALIER",0));

                }


            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //Map<String, String> params = new HashMap<String, String>();
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    PromotionpalierManager promotionpalierManager = new PromotionpalierManager(context);
                    List<Promotionpalier> promotionpaliers = promotionpalierManager.getList();

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    Log.d("UTILISATEUR_CODE PROMOTION PALIER SYNC",gson.toJson(pref.getString("UTILISATEUR_CODE",null)));

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(promotionpaliers));
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
