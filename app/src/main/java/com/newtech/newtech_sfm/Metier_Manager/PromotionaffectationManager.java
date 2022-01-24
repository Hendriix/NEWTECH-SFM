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
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.LogSync;
import com.newtech.newtech_sfm.Metier.Promotionaffectation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stagiaireit2 on 22/07/2016.
 */
public class PromotionaffectationManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    // All Static variables

    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Article table name
    public static final String TABLE_PROMOTIONAFFECTATION = "promotionaffectation";

    // Promotion Table Columns names
    private static final String
            KEY_ID="ID",
            KEY_PROMO_CODE="PROMO_CODE",
            KEY_CATEGORIE_CODE="CATEGORIE_CODE",
            KEY_TYPE_CODE="TYPE_CODE",
            KEY_VALEUR_AF="VALEUR_AF",
            KEY_DATE_CREATION="DATE_CREATION",
            KEY_VERSION="VERSION";

    public static String CREATE_PROMOTION_AFF_TABLE = "CREATE TABLE " + TABLE_PROMOTIONAFFECTATION + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_PROMO_CODE + " TEXT,"
            + KEY_CATEGORIE_CODE + " TEXT,"
            + KEY_TYPE_CODE + " TEXT,"
            + KEY_VALEUR_AF + " TEXT,"
            + KEY_DATE_CREATION + " TEXT,"
            + KEY_VERSION + " TEXT" + ")";

    public PromotionaffectationManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_PROMOTIONAFFECTATION);
            db.execSQL(CREATE_PROMOTION_AFF_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROMOTIONAFFECTATION);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing Articles details in database
     * */
    public void add(Promotionaffectation promotionaffectation) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PROMO_CODE, promotionaffectation.getPROMO_CODE());
        values.put(KEY_CATEGORIE_CODE,promotionaffectation.getCATEGORIE_CODE());
        values.put(KEY_TYPE_CODE, promotionaffectation.getTYPE_CODE());
        values.put(KEY_VALEUR_AF, promotionaffectation.getVALEUR_AF());
        values.put(KEY_DATE_CREATION, promotionaffectation.getDATE_CREATION());
        values.put(KEY_VERSION, promotionaffectation.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_PROMOTIONAFFECTATION, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New articles inserted into sqlite: " + id);
    }

    /**
 * Getting promotionffectation data from database
     * */
    public Promotionaffectation get(String promo_code) {
        Promotionaffectation promotionaffectation = new Promotionaffectation();

        String selectQuery = "SELECT  * FROM " + TABLE_PROMOTIONAFFECTATION +" WHERE "+KEY_PROMO_CODE +" = '"+promo_code +"' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {

            promotionaffectation.setPROMO_CODE(cursor.getString(1));
            promotionaffectation.setCATEGORIE_CODE(cursor.getString(2));
            promotionaffectation.setTYPE_CODE(cursor.getString(3));
            promotionaffectation.setVALEUR_AF(cursor.getString(4));
            promotionaffectation.setDATE_CREATION(cursor.getString(5));
            promotionaffectation.setVERSION(cursor.getString(6));

        }


        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Promotionaffectation from Sqlite: ");
        return promotionaffectation;
    }

    public ArrayList<Promotionaffectation> getList() {
        ArrayList<Promotionaffectation> listPromotionaffectations = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_PROMOTIONAFFECTATION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Promotionaffectation promotionaffectation = new Promotionaffectation();
                promotionaffectation.setPROMO_CODE(cursor.getString(1));
                promotionaffectation.setCATEGORIE_CODE(cursor.getString(2));
                promotionaffectation.setTYPE_CODE(cursor.getString(3));
                promotionaffectation.setVALEUR_AF(cursor.getString(4));
                promotionaffectation.setDATE_CREATION(cursor.getString(5));
                promotionaffectation.setVERSION(cursor.getString(6));

                listPromotionaffectations.add(promotionaffectation);
            }while(cursor.moveToNext());
        }

        //returner la listPromotions;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Promotionaffectations from Sqlite: ");
        return listPromotionaffectations;
    }


    public ArrayList<Promotionaffectation> getPromotionAffectationCode_Version_List() {
        ArrayList<Promotionaffectation> listPromotion_aff = new ArrayList<>();
        String selectQuery = "SELECT " + KEY_PROMO_CODE + "," + KEY_VERSION + " FROM " + TABLE_PROMOTIONAFFECTATION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Promotionaffectation pr = new Promotionaffectation() ;
                pr.setPROMO_CODE(cursor.getString(0));
                pr.setVERSION(cursor.getString(1));
                listPromotion_aff.add(pr);
            } while (cursor.moveToNext());
        }
        return listPromotion_aff;
    }

    /**
     * Getting promotionffectation data from database
     * */
    public Boolean check_Affectation(String promo_code,Client client) {

        Promotionaffectation promoAF = this.get(promo_code);
        Boolean result = false;

       switch (promoAF.getCATEGORIE_CODE()){
           case "CA0007":
               if(promoAF.getVALEUR_AF().equals(client.getCLIENT_CODE())){ result=true;}
               break;
           case "CA0008":
               if(promoAF.getVALEUR_AF().equals(client.getFAMILLE_CODE())){ result=true;}
               break;
           case "CA0009":
               if(promoAF.getVALEUR_AF().equals(client.getDISTRIBUTEUR_CODE())){ result=true;}
               break;
           case "CA0010":
               if(promoAF.getVALEUR_AF().equals(client.getVILLE_CODE())){ result=true;}
               break;
           default:
               result=false;
       }
        Log.d("Remise", "check affectation = "+promoAF.getCATEGORIE_CODE()+" : "+promoAF.getVALEUR_AF()+" == "+client.getDISTRIBUTEUR_CODE()+" result : "+result.toString());
        Log.d("promotion", "check_Affectation: "+ result);
       return result;
    }


    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deletePromotionaffectations() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_PROMOTIONAFFECTATION, null, null);
        db.close();
        Log.d(TAG, "Deleted all promotionAFFECTATIONs info from sqlite");
    }

    /*public int delete(String promocode) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PROMOTIONAFFECTATION,KEY_PROMO_CODE+"=?",new String[]{promocode});
    }*/

    public int delete(String tacheCode,String version) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PROMOTIONAFFECTATION,KEY_PROMO_CODE+"=? AND "+KEY_VERSION+"=?",new String[]{tacheCode,version});
    }

    public static void synchronisationPromotionAffectation(final Context context){

        String tag_string_req = "PROMOTION_AFFECTATION";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_PROMOTION_AFFECTATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDeleted = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray promotions_affectations = jObj.getJSONArray("Promotionaffectations");
                         //Toast.makeText(context, "Nombre de Promo_affect  "+promotions_affectations.length(), Toast.LENGTH_SHORT).show();
                        if(promotions_affectations.length()>0) {
                            PromotionaffectationManager promotionaffectationManager = new PromotionaffectationManager(context);
                            for (int i = 0; i < promotions_affectations.length(); i++) {
                                JSONObject Unepromotions_affectations = promotions_affectations.getJSONObject(i);
                                if (Unepromotions_affectations.getString("OPERATION").equals("DELETE")) {
                                    promotionaffectationManager.delete(Unepromotions_affectations.getString("PROMO_CODE"),Unepromotions_affectations.getString("VERSION"));
                                    cptDeleted++;
                                } else{
                                    promotionaffectationManager.add(new Promotionaffectation(Unepromotions_affectations));
                                    cptInsert++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PROMOTION AFFECTATION: Inserted: "+cptInsert +" Deleted: "+cptDeleted ,"PROMOTION AFFECTATION",1));

                        }

                        //logM.add("PROMOTION AFFECTATION:OK Insert:"+cptInsert +"Delet:"+cptDeleted ,"PROMOTION AFFECTATION");

                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(context, "PromoAF : "+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("PROMOTION AFFECTATION:NOK Insert:"+errorMsg ,"PROMOTION AFFECTATION");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PROMOTION AFFECTATION: Error: "+errorMsg,"PROMOTION AFFECTATION",0));

                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "PromoAF : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("PROMOTION AFFECTATION:NOK Insert:"+e.getMessage() ,"PROMOTION AFFECTATION");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PROMOTION AFFECTATION: Error: "+e.getMessage(),"PROMOTION AFFECTATION",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "PromoAF : "+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("PROMOTION AFFECTATION:NOK Insert:"+error.getMessage() ,"PROMOTION AFFECTATION");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PROMOTION AFFECTATION: Error: "+error.getMessage(),"PROMOTION AFFECTATION",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //Map<String, String> params = new HashMap<String, String>();
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    PromotionaffectationManager promotionaffectationManager = new PromotionaffectationManager(context);
                    List<Promotionaffectation> promotionaffectations = promotionaffectationManager.getList();

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    Log.d("UTILISATEUR_CODE PROMOTION AFFECTATION SYNC",gson.toJson(pref.getString("UTILISATEUR_CODE",null)));

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(promotionaffectations));
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
