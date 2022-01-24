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
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.CommandePromotion;
import com.newtech.newtech_sfm.Metier.LogSync;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stagiaireit2 on 02/08/2016.
 */
public class CommandePromotionManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_COMMANDE_PROMOTION = "commandepromotion";
    private static final String

    KEY_COMMANDE_PROMO_CODE="COMMANDE_PROMO_CODE",
    KEY_PROMO_CODE="PROMO_CODE",
    KEY_COMMANDE_CODE="COMMANDE_CODE",
    KEY_COMMANDELIGNE_CODE="COMMANDELIGNE_CODE",
    KEY_PROMO_NIVEAU="PROMO_NIVEAU",
    KEY_PROMO_MODECALCUL="PROMO_MODECALCUL",
    KEY_PROMO_APPLIQUESUR="PROMO_APPLIQUESUR",
    KEY_MONTANT_BRUTE_AV="MONTANT_BRUTE_AV",
    KEY_MONTANT_NET_AV="MONTANT_NET_AV",
    KEY_VALEUR_PROMO="VALEUR_PROMO",
    KEY_MONTANT_NET_AP="MONTANT_NET_AP",
    KEY_GRATUITE_CODE="GRATUITE_CODE",
    KEY_VERSION="VERSION";

    public static  String CREATE_COMMANDE_TABLE = "CREATE TABLE " + TABLE_COMMANDE_PROMOTION+ "("
            +KEY_COMMANDE_PROMO_CODE  + " TEXT PRIMARY KEY,"
            +KEY_PROMO_CODE  + " TEXT ,"
            +KEY_COMMANDE_CODE  + " TEXT ,"
            +KEY_COMMANDELIGNE_CODE  + " NUMERIC ,"
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

    public CommandePromotionManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE);
            db.execSQL(CREATE_COMMANDE_TABLE);
        } catch (SQLException e) {
        }
        Log.d(TAG, "table CommandeGRATUITE created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMANDE_PROMOTION);
        onCreate(db);
    }

    public void add(CommandePromotion commande) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_COMMANDE_PROMO_CODE, commande.getCOMMANDE_PROMO_CODE());
        values.put(KEY_PROMO_CODE, commande.getPROMO_CODE());
        values.put(KEY_COMMANDE_CODE, commande.getCOMMANDE_CODE());
        values.put(KEY_COMMANDELIGNE_CODE, commande.getCOMMANDELIGNE_CODE());
        values.put(KEY_PROMO_NIVEAU, commande.getPROMO_NIVEAU());
        values.put(KEY_PROMO_MODECALCUL, commande.getPROMO_MODECALCUL());
        values.put(KEY_PROMO_APPLIQUESUR, commande.getPROMO_APPLIQUESUR());
        values.put(KEY_MONTANT_BRUTE_AV, commande.getMONTANT_BRUTE_AV());
        values.put(KEY_MONTANT_NET_AV, commande.getMONTANT_NET_AV());
        values.put(KEY_VALEUR_PROMO, commande.getVALEUR_PROMO());
        values.put(KEY_MONTANT_NET_AP, commande.getMONTANT_NET_AP());
        values.put(KEY_GRATUITE_CODE, commande.getGRATUITE_CODE());
        values.put(KEY_VERSION, commande.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_COMMANDE_PROMOTION, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d(TAG, "nouvelle CommandePromotionActivity inseré dans la table CommandePromotionActivity: " + id);
    }

    //############################################################# GETLIST () #############################################################
    public ArrayList<CommandePromotion> getList() {
        ArrayList<CommandePromotion> listCommandeP = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDE_PROMOTION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandePromotion cmd = new CommandePromotion();

                cmd.setCOMMANDE_PROMO_CODE(cursor.getString(0));
                cmd.setPROMO_CODE(cursor.getString(1));
                cmd.setCOMMANDE_CODE(cursor.getString(2));
                cmd.setCOMMANDELIGNE_CODE(cursor.getInt(3));
                cmd.setPROMO_NIVEAU(cursor.getString(4));
                cmd.setPROMO_MODECALCUL(cursor.getString(5));
                cmd.setPROMO_APPLIQUESUR(cursor.getString(6));
                cmd.setMONTANT_BRUTE_AV(cursor.getDouble(7));
                cmd.setMONTANT_NET_AV(cursor.getDouble(8));
                cmd.setVALEUR_PROMO(cursor.getDouble(9));
                cmd.setMONTANT_NET_AP(cursor.getDouble(10));
                cmd.setGRATUITE_CODE(cursor.getString(11));
                cmd.setVERSION(cursor.getString(12));

                listCommandeP.add(cmd);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande from table CommandePROMOTION ");
        return listCommandeP;
    }

    //############################################################# GETLIST () #############################################################
    public ArrayList<CommandePromotion> getListNotInserted() {
        ArrayList<CommandePromotion> listCommandeP = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDE_PROMOTION +" WHERE "+KEY_VERSION+" != 'inserted' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandePromotion cmd = new CommandePromotion();

                cmd.setCOMMANDE_PROMO_CODE(cursor.getString(0));
                cmd.setPROMO_CODE(cursor.getString(1));
                cmd.setCOMMANDE_CODE(cursor.getString(2));
                cmd.setCOMMANDELIGNE_CODE(cursor.getInt(3));
                cmd.setPROMO_NIVEAU(cursor.getString(4));
                cmd.setPROMO_MODECALCUL(cursor.getString(5));
                cmd.setPROMO_APPLIQUESUR(cursor.getString(6));
                cmd.setMONTANT_BRUTE_AV(cursor.getDouble(7));
                cmd.setMONTANT_NET_AV(cursor.getDouble(8));
                cmd.setVALEUR_PROMO(cursor.getDouble(9));
                cmd.setMONTANT_NET_AP(cursor.getDouble(10));
                cmd.setGRATUITE_CODE(cursor.getString(11));
                cmd.setVERSION(cursor.getString(12));

                listCommandeP.add(cmd);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande from table CommandePROMOTION ");
        return listCommandeP;
    }
    //###################################################################################################################################

    //############################################################# GETLISTBYCLIENTCODE () #############################################################
    public ArrayList<CommandePromotion> getListByClienCode(String client_code) {
        ArrayList<CommandePromotion> listCommandeP = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDE_PROMOTION +" INNER JOIN commande ON commande.COMMANDE_CODE = commandepromotion."+KEY_COMMANDE_CODE+" WHERE commande.CLIENT_CODE = '"+client_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandePromotion cmd = new CommandePromotion();

                cmd.setCOMMANDE_PROMO_CODE(cursor.getString(0));
                cmd.setPROMO_CODE(cursor.getString(1));
                cmd.setCOMMANDE_CODE(cursor.getString(2));
                cmd.setCOMMANDELIGNE_CODE(cursor.getInt(3));
                cmd.setPROMO_NIVEAU(cursor.getString(4));
                cmd.setPROMO_MODECALCUL(cursor.getString(5));
                cmd.setPROMO_APPLIQUESUR(cursor.getString(6));
                cmd.setMONTANT_BRUTE_AV(cursor.getDouble(7));
                cmd.setMONTANT_NET_AV(cursor.getDouble(8));
                cmd.setVALEUR_PROMO(cursor.getDouble(9));
                cmd.setMONTANT_NET_AP(cursor.getDouble(10));
                cmd.setGRATUITE_CODE(cursor.getString(11));
                cmd.setVERSION(cursor.getString(12));

                listCommandeP.add(cmd);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande from table CommandePROMOTION ");
        return listCommandeP;
    }

    //###################################################################################################################################

    public void updateCommandePromotion(String COMMANDE_PROMO_CODE,String version){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_COMMANDE_PROMOTION + " SET "+KEY_VERSION  +"= '"+version+"' WHERE "+KEY_COMMANDE_PROMO_CODE +"= '"+COMMANDE_PROMO_CODE+"'" ;
        db.execSQL(req);
    }

    public int deleteByCmdCode(String commandeCode ) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_COMMANDE_PROMOTION,KEY_COMMANDE_CODE+"=?" ,new String[]{commandeCode});

    }

    //delete commande gratuite

    public void deleteCPSynchronisee() {

        String Where = " "+KEY_VERSION+"='inserted' and commandepromotion.COMMANDE_CODE not in (select commande.COMMANDE_CODE from commande)";

        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_COMMANDE_PROMOTION, Where, null);
        db.close();
        Log.d(TAG, "Deleted all commande promotions verifiee from sqlite");
    }

    //######## synchronisation.
    public static void synchronisationCommandePromotion(final Context context){

        CommandePromotionManager commandePromotionManager = new CommandePromotionManager(context);
        commandePromotionManager.deleteCPSynchronisee();
        String tag_string_req = "COMMANDE_PROMOTION";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_COMMANDE_PROMOTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                Log.d("commandepromotion", "onResponse: "+response);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray commandepromotions = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre de commandepromotions  "+commandepromotions.length() , Toast.LENGTH_LONG).show();
                        // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les Commandepromotions : " +response);

                        if(commandepromotions.length()>0) {
                            CommandePromotionManager commandepromotionManager = new CommandePromotionManager(context);
                            for (int i = 0; i < commandepromotions.length(); i++) {
                                JSONObject uneCommandepromotion = commandepromotions.getJSONObject(i);
                                if (uneCommandepromotion.getString("Statut").equals("true")) {
                                    commandepromotionManager.updateCommandePromotion((uneCommandepromotion.getString("COMMANDE_PROMO_CODE")),"inserted");
                                    cptInsert++;
                                }else{
                                    cptDelete++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE_PROMOTION: Inserted: "+cptInsert +" NotInserted: "+cptDelete ,"COMMANDE_PROMOTION",1));

                        }


                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "commandeP : "+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE_PROMOTION: Error: "+errorMsg,"COMMANDE_PROMOTION",0));

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "commandeP : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE_PROMOTION: Error: "+e.getMessage(),"COMMANDE_PROMOTION",0));

                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(context, "commandeP : "+error.getMessage(), Toast.LENGTH_LONG).show();
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE_PROMOTION: Error: "+error.getMessage(),"COMMANDE_PROMOTION",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    CommandePromotionManager commandepromotionManager  = new CommandePromotionManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableCommandePromotion");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(commandepromotionManager.getListNotInserted()));
                    Log.d("commandepromotion", "getParams: "+commandepromotionManager.getListNotInserted().toString());
                }

                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    public ArrayList<CommandePromotion> getCmdPromotionByCmdPromotion(ArrayList<CommandePromotion> commandePromotions, Commande commande){

        ArrayList<CommandePromotion> commandePromotions1 = new ArrayList<>();

        return commandePromotions1;

    }

}
