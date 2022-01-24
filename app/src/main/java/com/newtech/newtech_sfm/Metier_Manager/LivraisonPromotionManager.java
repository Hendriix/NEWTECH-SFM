package com.newtech.newtech_sfm.Metier_Manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

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
import com.newtech.newtech_sfm.Metier.LivraisonPromotion;
import com.newtech.newtech_sfm.Metier.LogSync;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stagiaireit2 on 02/08/2016.
 */
public class LivraisonPromotionManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_LIVRAISON_PROMOTION = "livraisonpromotion";
    private static final String

    KEY_LIVRAISON_PROMO_CODE="LIVRAISON_PROMO_CODE",
    KEY_PROMO_CODE="PROMO_CODE",
    KEY_LIVRAISON_CODE="LIVRAISON_CODE",
    KEY_LIVRAISONLIGNE_CODE="LIVRAISONLIGNE_CODE",
    KEY_PROMO_NIVEAU="PROMO_NIVEAU",
    KEY_PROMO_MODECALCUL="PROMO_MODECALCUL",
    KEY_PROMO_APPLIQUESUR="PROMO_APPLIQUESUR",
    KEY_MONTANT_BRUTE_AV="MONTANT_BRUTE_AV",
    KEY_MONTANT_NET_AV="MONTANT_NET_AV",
    KEY_VALEUR_PROMO="VALEUR_PROMO",
    KEY_MONTANT_NET_AP="MONTANT_NET_AP",
    KEY_GRATUITE_CODE="GRATUITE_CODE",
    KEY_VERSION="VERSION";

    public static  String CREATE_TABLE_LIVRAISON_PROMOTION = "CREATE TABLE " + TABLE_LIVRAISON_PROMOTION+ "("
            +KEY_LIVRAISON_PROMO_CODE  + " TEXT PRIMARY KEY,"
            +KEY_PROMO_CODE  + " TEXT ,"
            +KEY_LIVRAISON_CODE  + " TEXT ,"
            +KEY_LIVRAISONLIGNE_CODE  + " NUMERIC ,"
            +KEY_PROMO_NIVEAU  + " TEXT ,"
            +KEY_PROMO_MODECALCUL  + " TEXT ,"
            +KEY_PROMO_APPLIQUESUR  + " TEXT ,"
            +KEY_MONTANT_BRUTE_AV  + " NUMERIC ,"
            +KEY_MONTANT_NET_AV  + " NUMERIC ,"
            +KEY_VALEUR_PROMO  + " NUMERIC ,"
            +KEY_MONTANT_NET_AP  + " NUMERIC ,"
            +KEY_GRATUITE_CODE  + " TEXT ,"
            +KEY_VERSION + " TEXT "
            +")";

    public LivraisonPromotionManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE);
            db.execSQL(CREATE_TABLE_LIVRAISON_PROMOTION);
        } catch (SQLException e) {
        }
        Log.d(TAG, "table LivraisonGRATUITE created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIVRAISON_PROMOTION);
        onCreate(db);
    }

    public void add(LivraisonPromotion livraison) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LIVRAISON_PROMO_CODE, livraison.getLIVRAISON_PROMO_CODE());
        values.put(KEY_PROMO_CODE, livraison.getPROMO_CODE());
        values.put(KEY_LIVRAISON_CODE, livraison.getLIVRAISON_CODE());
        values.put(KEY_LIVRAISONLIGNE_CODE, livraison.getLIVRAISONLIGNE_CODE());
        values.put(KEY_PROMO_NIVEAU, livraison.getPROMO_NIVEAU());
        values.put(KEY_PROMO_MODECALCUL, livraison.getPROMO_MODECALCUL());
        values.put(KEY_PROMO_APPLIQUESUR, livraison.getPROMO_APPLIQUESUR());
        values.put(KEY_MONTANT_BRUTE_AV, livraison.getMONTANT_BRUTE_AV());
        values.put(KEY_MONTANT_NET_AV, livraison.getMONTANT_NET_AV());
        values.put(KEY_VALEUR_PROMO, livraison.getVALEUR_PROMO());
        values.put(KEY_MONTANT_NET_AP, livraison.getMONTANT_NET_AP());
        values.put(KEY_GRATUITE_CODE, livraison.getGRATUITE_CODE());
        values.put(KEY_VERSION, livraison.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_LIVRAISON_PROMOTION, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d(TAG, "nouvelle LivraisonPromotionActivity inseré dans la table LivraisonPromotionActivity: " + id);
    }

    //############################################################# GETLIST () #############################################################
    public ArrayList<LivraisonPromotion> getList() {
        ArrayList<LivraisonPromotion> listLivraisonP = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LIVRAISON_PROMOTION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                LivraisonPromotion cmd = new LivraisonPromotion();

                cmd.setLIVRAISON_PROMO_CODE(cursor.getString(0));
                cmd.setPROMO_CODE(cursor.getString(1));
                cmd.setLIVRAISON_CODE(cursor.getString(2));
                cmd.setLIVRAISONLIGNE_CODE(cursor.getInt(3));
                cmd.setPROMO_NIVEAU(cursor.getString(4));
                cmd.setPROMO_MODECALCUL(cursor.getString(5));
                cmd.setPROMO_APPLIQUESUR(cursor.getString(6));
                cmd.setMONTANT_BRUTE_AV(cursor.getDouble(7));
                cmd.setMONTANT_NET_AV(cursor.getDouble(8));
                cmd.setVALEUR_PROMO(cursor.getDouble(9));
                cmd.setMONTANT_NET_AP(cursor.getDouble(10));
                cmd.setGRATUITE_CODE(cursor.getString(11));
                cmd.setVERSION(cursor.getString(12));

                listLivraisonP.add(cmd);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Livraison from table LivraisonPROMOTION ");
        return listLivraisonP;
    }

    //###################################################################################################################################

    //############################################################# GETLIST () #############################################################
    public ArrayList<LivraisonPromotion> getListNotInserted() {
        ArrayList<LivraisonPromotion> listLivraisonP = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LIVRAISON_PROMOTION +" WHERE "+KEY_VERSION+" != 'inserted' ";;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                LivraisonPromotion cmd = new LivraisonPromotion();

                cmd.setLIVRAISON_PROMO_CODE(cursor.getString(0));
                cmd.setPROMO_CODE(cursor.getString(1));
                cmd.setLIVRAISON_CODE(cursor.getString(2));
                cmd.setLIVRAISONLIGNE_CODE(cursor.getInt(3));
                cmd.setPROMO_NIVEAU(cursor.getString(4));
                cmd.setPROMO_MODECALCUL(cursor.getString(5));
                cmd.setPROMO_APPLIQUESUR(cursor.getString(6));
                cmd.setMONTANT_BRUTE_AV(cursor.getDouble(7));
                cmd.setMONTANT_NET_AV(cursor.getDouble(8));
                cmd.setVALEUR_PROMO(cursor.getDouble(9));
                cmd.setMONTANT_NET_AP(cursor.getDouble(10));
                cmd.setGRATUITE_CODE(cursor.getString(11));
                cmd.setVERSION(cursor.getString(12));

                listLivraisonP.add(cmd);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Livraison from table LivraisonPROMOTION ");
        return listLivraisonP;
    }
    //############################################################# GETLISTBYCLIENTCODE () #############################################################
    public ArrayList<LivraisonPromotion> getListByClienCode(String client_code) {
        ArrayList<LivraisonPromotion> listLivraisonP = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LIVRAISON_PROMOTION +" INNER JOIN livraison ON livraison.LIVRAISON_CODE = livraisonpromotion."+KEY_LIVRAISON_CODE+" WHERE livraison.CLIENT_CODE = '"+client_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                LivraisonPromotion cmd = new LivraisonPromotion();

                cmd.setLIVRAISON_PROMO_CODE(cursor.getString(0));
                cmd.setPROMO_CODE(cursor.getString(1));
                cmd.setLIVRAISON_CODE(cursor.getString(2));
                cmd.setLIVRAISONLIGNE_CODE(cursor.getInt(3));
                cmd.setPROMO_NIVEAU(cursor.getString(4));
                cmd.setPROMO_MODECALCUL(cursor.getString(5));
                cmd.setPROMO_APPLIQUESUR(cursor.getString(6));
                cmd.setMONTANT_BRUTE_AV(cursor.getDouble(7));
                cmd.setMONTANT_NET_AV(cursor.getDouble(8));
                cmd.setVALEUR_PROMO(cursor.getDouble(9));
                cmd.setMONTANT_NET_AP(cursor.getDouble(10));
                cmd.setGRATUITE_CODE(cursor.getString(11));
                cmd.setVERSION(cursor.getString(12));

                listLivraisonP.add(cmd);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Livraison from table LivraisonPROMOTION ");
        return listLivraisonP;
    }

    //###################################################################################################################################

    public void updateLivraisonPromotion(String LIVRAISON_PROMO_CODE,String version){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_LIVRAISON_PROMOTION + " SET "+KEY_VERSION  +"= '"+version+"' WHERE "+KEY_LIVRAISON_PROMO_CODE +"= '"+LIVRAISON_PROMO_CODE+"'" ;
        db.execSQL(req);
    }

    public int deleteByLivraisonCode(String livraisonCode ) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_LIVRAISON_PROMOTION,KEY_LIVRAISON_CODE+"=?" ,new String[]{livraisonCode});

    }

    public void deleteLivPromosynchronisee() {
        String Where = " "+KEY_VERSION+"='inserted' and livraisonpromotion.LIVRAISON_CODE not in (select livraison.LIVRAISON_CODE from livraison)";

        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_LIVRAISON_PROMOTION, Where, null);
        db.close();
        Log.d(TAG, "Deleted all livraisons promotions verifiee from sqlite");
    }
    //######## synchronisation.
    public static void synchronisationLivraisonPromotion(final Context context){

        LivraisonPromotionManager livraisonPromotionManager = new LivraisonPromotionManager(context);
        livraisonPromotionManager.deleteLivPromosynchronisee();

        String tag_string_req = "LIVRAISON_PROMOTION";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_LIVRAISON_PROMOTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                Log.d("livraisonpromotion", "onResponse: "+response);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray livraisonpromotions = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre de livraisonpromotions  "+livraisonpromotions.length() , Toast.LENGTH_LONG).show();
                        // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les Livraisonpromotions : " +response);

                        if(livraisonpromotions.length()>0) {
                            LivraisonPromotionManager livraisonpromotionManager = new LivraisonPromotionManager(context);
                            for (int i = 0; i < livraisonpromotions.length(); i++) {
                                JSONObject uneLivraisonpromotion = livraisonpromotions.getJSONObject(i);
                                if (uneLivraisonpromotion.getString("Statut").equals("true")) {
                                    livraisonpromotionManager.updateLivraisonPromotion((uneLivraisonpromotion.getString("LIVRAISON_PROMO_CODE")),"inserted");
                                    cptInsert++;
                                }else{
                                    cptDelete++;
                                }
                            }
                        }

                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON_PROMOTION: Inserted: "+cptInsert +" NotInserted: "+cptDelete ,"LIVRAISON_PROMOTION",1));

                        }


                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "livraisonP : "+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON_PROMOTION: Error: "+errorMsg ,"LIVRAISON_PROMOTION",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "livraisonP : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON_PROMOTION: Error: "+e.getMessage() ,"LIVRAISON_PROMOTION",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "livraisonP : "+error.getMessage(), Toast.LENGTH_LONG).show();
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON_PROMOTION: Error: "+error.getMessage() ,"LIVRAISON_PROMOTION",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    LivraisonPromotionManager livraisonpromotionManager  = new LivraisonPromotionManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableLivraisonPromotion");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(livraisonpromotionManager.getListNotInserted()));
                    Log.d("livraisonpromotion", "getParams: "+livraisonpromotionManager.getListNotInserted().toString());
                }

                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
