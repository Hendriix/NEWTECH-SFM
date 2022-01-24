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
import com.newtech.newtech_sfm.Metier.Livraison;
import com.newtech.newtech_sfm.Metier.LivraisonGratuite;
import com.newtech.newtech_sfm.Metier.LivraisonPromotion;
import com.newtech.newtech_sfm.Metier.LogSync;
import com.newtech.newtech_sfm.Metier.Promotiongratuite;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stagiaireit2 on 02/08/2016.
 */
public class LivraisonGratuiteManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_LIVRAISON_GRATUITE = "livraisongratuite";
    private static final String

            KEY_GRATUITE_CODE="GRATUITE_CODE",
            KEY_LIVRAISON_CODE="LIVRAISON_CODE",
            KEY_PROMO_CODE="PROMO_CODE",
            KEY_ARTICLE_CODE="ARTICLE_CODE",
            KEY_QUANTITE="QUANTITE",
            KEY_VERSION="VERSION";

   public static String CREATE_TABLE_LIVRAISON_GRATUITE = "CREATE TABLE " + TABLE_LIVRAISON_GRATUITE+ "("
            +KEY_GRATUITE_CODE  + " TEXT ,"
            +KEY_LIVRAISON_CODE  + " TEXT ,"
            +KEY_PROMO_CODE+ " TEXT ,"
            +KEY_ARTICLE_CODE+ " TEXT ,"
            +KEY_QUANTITE+ " NUMERIC ,"
            +KEY_VERSION  + " TEXT ,"
            +"PRIMARY KEY("+KEY_GRATUITE_CODE +","+KEY_LIVRAISON_CODE+")"
            +");";
    public LivraisonGratuiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE "+TABLE_LIVRAISON_GRATUITE);
            db.execSQL(CREATE_TABLE_LIVRAISON_GRATUITE);

        } catch (SQLException e) {
        }
        Log.d(TAG, "table LivraisonGRATUITE created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIVRAISON_GRATUITE);
        onCreate(db);
    }

    public void add(LivraisonGratuite livraison) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_GRATUITE_CODE, livraison.getGRATUITE_CODE());
        values.put(KEY_ARTICLE_CODE, livraison.getARTICLE_CODE());
        values.put(KEY_LIVRAISON_CODE, livraison.getLIVRAISON_CODE());
        values.put(KEY_PROMO_CODE, livraison.getPROMO_CODE());
        values.put(KEY_QUANTITE, livraison.getQUANTITE());
        values.put(KEY_VERSION, livraison.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_LIVRAISON_GRATUITE, null, values,SQLiteDatabase.CONFLICT_REPLACE);

        db.close(); // Closing database connection
        Log.d(TAG, "nouvelle LivraisonGRATUITE inseré dans la table LivraisonGRATUITE: " + id);
    }

    //########################################################### GETLIST() #######################################################
    public ArrayList<LivraisonGratuite> getList() {
        ArrayList<LivraisonGratuite> listLivraisonG = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LIVRAISON_GRATUITE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                LivraisonGratuite cmd = new LivraisonGratuite();

                cmd.setGRATUITE_CODE(cursor.getString(0));
                cmd.setLIVRAISON_CODE(cursor.getString(1));
                cmd.setPROMO_CODE(cursor.getString(2));
                cmd.setARTICLE_CODE(cursor.getString(3));
                cmd.setQUANTITE(cursor.getDouble(4));
                cmd.setVERSION(cursor.getString(5));

                listLivraisonG.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Livraison from table LivraisonLigne: ");
        return listLivraisonG;
    }
    //###############################################################################################################################

    //########################################################### GETLIST() #######################################################
    public ArrayList<LivraisonGratuite> getListByClientCode(String client_code) {
        ArrayList<LivraisonGratuite> listLivraisonG = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LIVRAISON_GRATUITE +" INNER JOIN livraison ON livraisongratuite."+KEY_LIVRAISON_CODE+" = livraison.LIVRAISON_CODE WHERE livraison.CLIENT_CODE ='"+client_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                LivraisonGratuite cmd = new LivraisonGratuite();

                cmd.setGRATUITE_CODE(cursor.getString(0));
                cmd.setLIVRAISON_CODE(cursor.getString(1));
                cmd.setPROMO_CODE(cursor.getString(2));
                cmd.setARTICLE_CODE(cursor.getString(3));
                cmd.setQUANTITE(cursor.getDouble(4));
                cmd.setVERSION(cursor.getString(5));

                listLivraisonG.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Livraison from table LivraisonLigne: ");
        return listLivraisonG;
    }
    //###############################################################################################################################

    public ArrayList<LivraisonGratuite> getListNotInserted() {
        ArrayList<LivraisonGratuite> listLivraisonG = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LIVRAISON_GRATUITE +" WHERE "+KEY_VERSION+" != 'inserted' ";;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                LivraisonGratuite cmd = new LivraisonGratuite();

                cmd.setGRATUITE_CODE(cursor.getString(0));
                cmd.setLIVRAISON_CODE(cursor.getString(1));
                cmd.setPROMO_CODE(cursor.getString(2));
                cmd.setARTICLE_CODE(cursor.getString(3));
                cmd.setQUANTITE(cursor.getDouble(4));
                cmd.setVERSION(cursor.getString(5));

                listLivraisonG.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Livraison gratuites from table livraisongratuites: ");
        return listLivraisonG;
    }


    //#################################################### GETLISTCOMDCODE ##########################################################
    public ArrayList<LivraisonGratuite> getListbyLivCode(String livraison_code) {
        ArrayList<LivraisonGratuite> listLivraisonG = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LIVRAISON_GRATUITE +" WHERE "+KEY_LIVRAISON_CODE+" = '"+livraison_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                LivraisonGratuite cmd = new LivraisonGratuite();

                cmd.setGRATUITE_CODE(cursor.getString(0));
                cmd.setLIVRAISON_CODE(cursor.getString(1));
                cmd.setPROMO_CODE(cursor.getString(2));
                cmd.setARTICLE_CODE(cursor.getString(3));
                cmd.setQUANTITE(cursor.getDouble(4));
                cmd.setVERSION(cursor.getString(5));

                listLivraisonG.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Livraison from table LivraisonLigne: ");
        return listLivraisonG;
    }
    //###############################################################################################################################

    public void updateLivraisonGratuite(String GRATUITE_CODE,String livraison_code,String version){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_LIVRAISON_GRATUITE + " SET "+KEY_VERSION  +"= '"+version+"' WHERE "+KEY_GRATUITE_CODE +"= '"+GRATUITE_CODE+"' AND "+KEY_LIVRAISON_CODE +"= '"+livraison_code+"'" ;
        db.execSQL(req);
    }

    public int deleteByLivraisonCode(String livraisonCode ) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_LIVRAISON_GRATUITE,KEY_LIVRAISON_CODE+"=?" ,new String[]{livraisonCode});

    }


    public void deleteLivGSynchronisee() {

        String Where = " "+KEY_VERSION+"='inserted' and livraisongratuite.LIVRAISON_CODE not in (select livraison.LIVRAISON_CODE from livraison)";

        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_LIVRAISON_GRATUITE, Where, null);
        db.close();
        Log.d(TAG, "Deleted all livraisons gratuites verifiee from sqlite");
    }
    //######## synchronisation.
    public static void synchronisationLivraisonGratuite(final Context context){

        LivraisonGratuiteManager livraisonGratuiteManager = new LivraisonGratuiteManager(context);
        livraisonGratuiteManager.deleteLivGSynchronisee();

        String tag_string_req = "LIVRAISON_GRATUITE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_LIVRAISON_GRATUITE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                Log.d("livraisongratuite", "onResponse: "+response);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray livraisongratuites = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre de livraisongratuites  "+livraisongratuites.length() , Toast.LENGTH_LONG).show();
                        // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les Livraisongratuites : " +response);

                        if(livraisongratuites.length()>0) {
                            LivraisonGratuiteManager livraisongratuiteManager = new LivraisonGratuiteManager(context);
                            for (int i = 0; i < livraisongratuites.length(); i++) {
                                JSONObject uneLivraisongratuite = livraisongratuites.getJSONObject(i);
                                if (uneLivraisongratuite.getString("Statut").equals("true")) {
                                    livraisongratuiteManager.updateLivraisonGratuite((uneLivraisongratuite.getString("GRATUITE_CODE")),uneLivraisongratuite.getString("LIVRAISON_CODE"),"inserted");
                                    cptInsert++;
                                }else{
                                    cptDelete++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON_GRATUITE: Inserted: "+cptInsert +" NotInserted: "+cptDelete ,"LIVRAISON_GRATUITE",1));

                        }


                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "livraisonG"+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON_GRATUITE: Error: "+errorMsg ,"LIVRAISON_GRATUITE",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "livraisonG"+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON_GRATUITE: Error: "+e.getMessage() ,"LIVRAISON_GRATUITE",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "livraisonG"+error.getMessage(), Toast.LENGTH_LONG).show();
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON_GRATUITE: Error: "+error.getMessage() ,"LIVRAISON_GRATUITE",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    LivraisonGratuiteManager livraisongratuiteManager  = new LivraisonGratuiteManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableLivraisonGratuite");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(livraisongratuiteManager.getListNotInserted()));
                    Log.d("livraisongratuite", "getParams: "+livraisongratuiteManager.getListNotInserted().toString());
                }

                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }



    public ArrayList<LivraisonGratuite> getLivraisonGratuiteByCmdPromotion(ArrayList<LivraisonPromotion> livraisonPromotions, Livraison livraison, Context context){

        PromotiongratuiteManager promotiongratuiteManager = new PromotiongratuiteManager(context);
        ArrayList<LivraisonGratuite> livraisonGratuites = new ArrayList<LivraisonGratuite>();

        if(livraisonPromotions.size()>0){
            for (LivraisonPromotion livraisonPromotion:livraisonPromotions){
                if(livraisonPromotion.getPROMO_MODECALCUL().equals("CA0016")&&livraisonPromotion.getLIVRAISON_CODE().equals(livraison.getLIVRAISON_CODE())){
                    if(promotiongratuiteManager.exist(livraisonPromotion.getPROMO_CODE())){
                        Promotiongratuite promotiongratuite = promotiongratuiteManager.get(livraisonPromotion.getPROMO_CODE());
                        LivraisonGratuite livraisonGratuite=new LivraisonGratuite();

                        livraisonGratuite.setGRATUITE_CODE(livraisonPromotion.getGRATUITE_CODE());
                        livraisonGratuite.setLIVRAISON_CODE(livraison.getCOMMANDE_CODE());
                        livraisonGratuite.setPROMO_CODE(livraisonPromotion.getPROMO_CODE());
                        livraisonGratuite.setARTICLE_CODE(promotiongratuite.getVALEUR_GR());
                        livraisonGratuite.setQUANTITE(livraisonPromotion.getVALEUR_PROMO());
                        livraisonGratuite.setVERSION("to_insert");

                        livraisonGratuites.add(livraisonGratuite);
                    }
                }
            }
        }

        return livraisonGratuites;
    }

    //#################################################### GETLISTCOMDCODE ##########################################################
    public ArrayList<LivraisonGratuite> getListbyLivraisonCode(String livraison_code) {
        ArrayList<LivraisonGratuite> livraisonGratuites = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LIVRAISON_GRATUITE +" WHERE "+KEY_LIVRAISON_CODE+" = '"+livraison_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                LivraisonGratuite livraisonGratuite = new LivraisonGratuite();

                livraisonGratuite.setGRATUITE_CODE(cursor.getString(0));
                livraisonGratuite.setLIVRAISON_CODE(cursor.getString(1));
                livraisonGratuite.setPROMO_CODE(cursor.getString(2));
                livraisonGratuite.setARTICLE_CODE(cursor.getString(3));
                livraisonGratuite.setQUANTITE(cursor.getDouble(4));
                livraisonGratuite.setVERSION(cursor.getString(5));

                livraisonGratuites.add(livraisonGratuite);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande from table LivraisonGratuites: ");
        return livraisonGratuites;
    }
}
