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
import com.newtech.newtech_sfm.Metier.CommandeGratuite;
import com.newtech.newtech_sfm.Metier.CommandePromotion;
import com.newtech.newtech_sfm.Metier.LogSync;
import com.newtech.newtech_sfm.Metier.Promotiongratuite;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stagiaireit2 on 02/08/2016.
 */
public class CommandeGratuiteManager  extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_COMMANDE_GRATUITE = "commandegratuite";
    private static final String

            KEY_GRATUITE_CODE="GRATUITE_CODE",
            KEY_COMMANDE_CODE="COMMANDE_CODE",
            KEY_PROMO_CODE="PROMO_CODE",
            KEY_ARTICLE_CODE="ARTICLE_CODE",
            KEY_QUANTITE="QUANTITE",
            KEY_VERSION="VERSION";

   public static String CREATE_TABLE_COMMANDE_GRATUITE = "CREATE TABLE " + TABLE_COMMANDE_GRATUITE+ "("
            +KEY_GRATUITE_CODE  + " TEXT ,"
            +KEY_COMMANDE_CODE  + " TEXT ,"
            +KEY_PROMO_CODE+ " TEXT ,"
            +KEY_ARTICLE_CODE+ " TEXT ,"
            +KEY_QUANTITE+ " NUMERIC ,"
            +KEY_VERSION  + " TEXT ,"
            +"PRIMARY KEY("+KEY_GRATUITE_CODE +","+KEY_COMMANDE_CODE+")"
            +");";
    public CommandeGratuiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE "+TABLE_COMMANDE_GRATUITE);
            db.execSQL(CREATE_TABLE_COMMANDE_GRATUITE);

        } catch (SQLException e) {
        }
        Log.d(TAG, "table CommandeGRATUITE created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMANDE_GRATUITE);
        onCreate(db);
    }

    public void add(CommandeGratuite commande) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_GRATUITE_CODE, commande.getGRATUITE_CODE());
        values.put(KEY_ARTICLE_CODE, commande.getARTICLE_CODE());
        values.put(KEY_COMMANDE_CODE, commande.getCOMMANDE_CODE());
        values.put(KEY_PROMO_CODE, commande.getPROMO_CODE());
        values.put(KEY_QUANTITE, commande.getQUANTITE());
        values.put(KEY_VERSION, commande.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_COMMANDE_GRATUITE, null, values,SQLiteDatabase.CONFLICT_REPLACE);

        db.close(); // Closing database connection
        Log.d(TAG, "nouvelle CommandeGRATUITE inseré dans la table CommandeGRATUITE: " + id);
    }

    //########################################################### GETLIST() #######################################################
    public ArrayList<CommandeGratuite> getList() {
        ArrayList<CommandeGratuite> listCommandeG = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDE_GRATUITE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeGratuite cmd = new CommandeGratuite();

                cmd.setGRATUITE_CODE(cursor.getString(0));
                cmd.setCOMMANDE_CODE(cursor.getString(1));
                cmd.setPROMO_CODE(cursor.getString(2));
                cmd.setARTICLE_CODE(cursor.getString(3));
                cmd.setQUANTITE(cursor.getDouble(4));
                cmd.setVERSION(cursor.getString(5));

                listCommandeG.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande from table CommandeLigne: ");
        return listCommandeG;
    }

    //########################################################### GETLIST() #######################################################
    public ArrayList<CommandeGratuite> getListNotInserted() {
        ArrayList<CommandeGratuite> listCommandeG = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDE_GRATUITE +" WHERE "+KEY_VERSION+" != 'inserted' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeGratuite cmd = new CommandeGratuite();

                cmd.setGRATUITE_CODE(cursor.getString(0));
                cmd.setCOMMANDE_CODE(cursor.getString(1));
                cmd.setPROMO_CODE(cursor.getString(2));
                cmd.setARTICLE_CODE(cursor.getString(3));
                cmd.setQUANTITE(cursor.getDouble(4));
                cmd.setVERSION(cursor.getString(5));

                listCommandeG.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande from table CommandeLigne: ");
        return listCommandeG;
    }
    //###############################################################################################################################

    //########################################################### GETLIST() #######################################################
    public ArrayList<CommandeGratuite> getListByClientCode(String client_code) {
        ArrayList<CommandeGratuite> listCommandeG = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDE_GRATUITE +" INNER JOIN commande ON commandegratuite."+KEY_COMMANDE_CODE+" = commande.COMMANDE_CODE WHERE commande.CLIENT_CODE ='"+client_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeGratuite cmd = new CommandeGratuite();

                cmd.setGRATUITE_CODE(cursor.getString(0));
                cmd.setCOMMANDE_CODE(cursor.getString(1));
                cmd.setPROMO_CODE(cursor.getString(2));
                cmd.setARTICLE_CODE(cursor.getString(3));
                cmd.setQUANTITE(cursor.getDouble(4));
                cmd.setVERSION(cursor.getString(5));

                listCommandeG.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande from table CommandeLigne: ");
        return listCommandeG;
    }
    //###############################################################################################################################

    public Cursor getGratuiteParArticle(){

        String selectQuery = "SELECT "+TABLE_COMMANDE_GRATUITE+"."+KEY_ARTICLE_CODE+",SUM("+TABLE_COMMANDE_GRATUITE+"."+KEY_QUANTITE+") AS QTE FROM "+TABLE_COMMANDE_GRATUITE+" GROUP BY "+TABLE_COMMANDE_GRATUITE+"."+KEY_ARTICLE_CODE+";";
        SQLiteDatabase db = this.getReadableDatabase();

        Log.d(TAG, "getGratuiteParArticle: "+selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }
    //#################################################### GETLISTCOMDCODE ##########################################################
    public ArrayList<CommandeGratuite> getListbyCmdCode(String commande_code) {
        ArrayList<CommandeGratuite> listCommandeG = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDE_GRATUITE +" WHERE "+KEY_COMMANDE_CODE+" = '"+commande_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeGratuite cmd = new CommandeGratuite();

                cmd.setGRATUITE_CODE(cursor.getString(0));
                cmd.setCOMMANDE_CODE(cursor.getString(1));
                cmd.setPROMO_CODE(cursor.getString(2));
                cmd.setARTICLE_CODE(cursor.getString(3));
                cmd.setQUANTITE(cursor.getDouble(4));
                cmd.setVERSION(cursor.getString(5));

                listCommandeG.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande from table CommandeLigne: ");
        return listCommandeG;
    }
    //###############################################################################################################################

    public void updateCommandeGratuite(String GRATUITE_CODE,String commande_code,String version){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_COMMANDE_GRATUITE + " SET "+KEY_VERSION  +"= '"+version+"' WHERE "+KEY_GRATUITE_CODE +"= '"+GRATUITE_CODE+"' AND "+KEY_COMMANDE_CODE +"= '"+commande_code+"'" ;
        db.execSQL(req);
    }

    //delete commande gratuite

    public void deleteCGSynchronisee() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefCmdAS = sdf.format(new Date());

        Log.d(TAG, "deleteCmdSynchronisee: "+DatefCmdAS);

        String Where = " "+KEY_VERSION+"='inserted' and commandegratuite.COMMANDE_CODE not in (select commande.COMMANDE_CODE from commande)";

        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_COMMANDE_GRATUITE, Where, null);
        db.close();
        Log.d(TAG, "Deleted all commandes gratuites verifiee from sqlite");
    }

    public int deleteByCmdCode(String commandeCode ) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_COMMANDE_GRATUITE,KEY_COMMANDE_CODE+"=?" ,new String[]{commandeCode});

    }

    //######## synchronisation.
    public static void synchronisationCommandeGratuite(final Context context){

        CommandeGratuiteManager commandeGratuiteManager = new CommandeGratuiteManager(context);
        commandeGratuiteManager.deleteCGSynchronisee();

        String tag_string_req = "COMMANDE_GRATUITE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_COMMANDE_GRATUITE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                Log.d("commandegratuite", "onResponse: "+response);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray commandegratuites = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre de commandegratuites  "+commandegratuites.length() , Toast.LENGTH_LONG).show();
                        // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les Commandegratuites : " +response);

                        if(commandegratuites.length()>0) {
                            CommandeGratuiteManager commandegratuiteManager = new CommandeGratuiteManager(context);
                            for (int i = 0; i < commandegratuites.length(); i++) {
                                JSONObject uneCommandegratuite = commandegratuites.getJSONObject(i);
                                if (uneCommandegratuite.getString("Statut").equals("true")) {
                                    commandegratuiteManager.updateCommandeGratuite((uneCommandegratuite.getString("GRATUITE_CODE")),uneCommandegratuite.getString("COMMANDE_CODE"),"inserted");
                                    cptInsert++;
                                }else{
                                    cptDelete++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE_GRATUITE: Inserted: "+cptInsert +" NotInserted: "+cptDelete ,"COMMANDE_GRATUITE",1));

                        }


                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "commandeG"+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE_GRATUITE: Error: "+errorMsg ,"COMMANDE_GRATUITE",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "commandeG"+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE_GRATUITE: Error: "+e.getMessage() ,"COMMANDE_GRATUITE",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "commandG"+error.getMessage(), Toast.LENGTH_LONG).show();
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE_GRATUITE: Error: "+error.getMessage() ,"COMMANDE_GRATUITE",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    CommandeGratuiteManager commandegratuiteManager  = new CommandeGratuiteManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableCommandeGratuite");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(commandegratuiteManager.getListNotInserted()));
                    Log.d("commandegratuite", "getParams: "+commandegratuiteManager.getListNotInserted().toString());
                }

                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public ArrayList<CommandeGratuite> getCmdGratuiteByCmdPromotion(ArrayList<CommandePromotion> commandePromotions,Commande commande,Context context){

        PromotiongratuiteManager promotiongratuiteManager = new PromotiongratuiteManager(context);
        ArrayList<CommandeGratuite> commandeGratuites = new ArrayList<CommandeGratuite>();

        if(commandePromotions.size()>0){
            for (CommandePromotion commandePromotion:commandePromotions){
                if(commandePromotion.getPROMO_MODECALCUL().equals("CA0016")&&commandePromotion.getCOMMANDE_CODE().equals(commande.getCOMMANDE_CODE())){
                    if(promotiongratuiteManager.exist(commandePromotion.getPROMO_CODE())){
                        Promotiongratuite promogratuite = promotiongratuiteManager.get(commandePromotion.getPROMO_CODE());
                        CommandeGratuite commandeGratuite=new CommandeGratuite();

                        commandeGratuite.setGRATUITE_CODE(commandePromotion.getGRATUITE_CODE());
                        commandeGratuite.setCOMMANDE_CODE(commande.getCOMMANDE_CODE());
                        commandeGratuite.setPROMO_CODE(commandePromotion.getPROMO_CODE());
                        commandeGratuite.setARTICLE_CODE(promogratuite.getVALEUR_GR());
                        if(commande.getCOMMANDETYPE_CODE() == "2"){
                            commandeGratuite.setQUANTITE(-commandePromotion.getVALEUR_PROMO());
                        }else{
                            commandeGratuite.setQUANTITE(commandePromotion.getVALEUR_PROMO());
                        }
                        commandeGratuite.setVERSION("to_insert");

                        commandeGratuites.add(commandeGratuite);
                    }
                }
            }
        }

        return commandeGratuites;
    }


}
