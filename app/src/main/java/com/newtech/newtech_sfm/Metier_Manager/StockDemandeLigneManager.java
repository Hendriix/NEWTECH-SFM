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
import com.newtech.newtech_sfm.Metier.LogSync;
import com.newtech.newtech_sfm.Metier.StockDemandeLigne;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by csaylani on 23/04/2018.
 */

public class StockDemandeLigneManager extends SQLiteOpenHelper{


    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_STOCKDEMANDE_LIGNE = "stockdemandeligne";


    public static final String
            KEY_DEMANDELIGNE_CODE ="DEMANDELIGNE_CODE",
            KEY_DEMANDE_CODE ="DEMANDE_CODE",
            KEY_FAMILLE_CODE ="FAMILLE_CODE",
            KEY_ARTICLE_CODE ="ARTICLE_CODE",
            KEY_ARTICLE_DESIGNATION ="ARTICLE_DESIGNATION",
            KEY_AR_NBUS_PAR_UP ="AR_NBUS_PAR_UP",
            KEY_ARTICLE_PRIX ="ARTICLE_PRIX",
            KEY_ARTICLE_KG ="ARTICLE_KG",
            KEY_UNITE_CODE ="UNITE_CODE",
            KEY_QTE_COMMANDEE ="QTE_COMMANDEE",
            KEY_QTE_APPROUVEE ="QTE_APPROUVEE",
            KEY_QTE_LIVREE ="QTE_LIVREE",
            KEY_QTE_RECEPTIONEE ="QTE_RECEPTIONEE",
            KEY_DATE_CREATION="DATE_CREATION",
            KEY_CREATEUR_CODE ="CREATEUR_CODE",
            KEY_COMMENTAIRE = "COMMENTAIRE",
            KEY_VERSION="VERSION";


    public static String CREATE_STOCKDEMANDE_LIGNE_TABLE = "CREATE TABLE " + TABLE_STOCKDEMANDE_LIGNE+ " ("

            +KEY_DEMANDELIGNE_CODE + " NUMERIC,"
            +KEY_DEMANDE_CODE + " TEXT,"
            +KEY_FAMILLE_CODE + " TEXT,"
            +KEY_ARTICLE_CODE + " TEXT,"
            +KEY_ARTICLE_DESIGNATION + " TEXT,"
            +KEY_AR_NBUS_PAR_UP + " NUMERIC,"
            +KEY_ARTICLE_PRIX + " NUMERIC,"
            +KEY_ARTICLE_KG + " NUMERIC,"
            +KEY_UNITE_CODE + " TEXT,"
            +KEY_QTE_COMMANDEE + " NUMERIC,"
            +KEY_QTE_APPROUVEE + " NUMERIC,"
            +KEY_QTE_LIVREE + " NUMERIC,"
            +KEY_QTE_RECEPTIONEE + " NUMERIC,"
            +KEY_CREATEUR_CODE + " TEXT,"
            +KEY_DATE_CREATION + " TEXT,"
            +KEY_COMMENTAIRE + " TEXT,"
            +KEY_VERSION + " TEXT " + ")";


    public StockDemandeLigneManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAMILLE);
            db.execSQL(CREATE_STOCKDEMANDE_LIGNE_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database STOCKDEMANDELIGNE tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCKDEMANDE_LIGNE);
        // Create tables again
        onCreate(db);
    }

    public void add(StockDemandeLigne stockDemandeLigne) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_DEMANDELIGNE_CODE,stockDemandeLigne.getDEMANDELIGNE_CODE());
        values.put(KEY_DEMANDE_CODE,stockDemandeLigne.getDEMANDE_CODE());
        values.put(KEY_FAMILLE_CODE,stockDemandeLigne.getFAMILLE_CODE());
        values.put(KEY_ARTICLE_CODE,stockDemandeLigne.getARTICLE_CODE());
        values.put(KEY_ARTICLE_DESIGNATION,stockDemandeLigne.getARTICLE_DESIGNATION());
        values.put(KEY_AR_NBUS_PAR_UP,stockDemandeLigne.getAR_NBUS_PAR_UP());
        values.put(KEY_ARTICLE_PRIX,stockDemandeLigne.getARTICLE_PRIX());
        values.put(KEY_ARTICLE_KG,stockDemandeLigne.getARTICLE_KG());
        values.put(KEY_UNITE_CODE,stockDemandeLigne.getUNITE_CODE());
        values.put(KEY_QTE_COMMANDEE,stockDemandeLigne.getQTE_COMMANDEE());
        values.put(KEY_QTE_APPROUVEE,stockDemandeLigne.getQTE_APPROUVEE());
        values.put(KEY_QTE_LIVREE,stockDemandeLigne.getQTE_LIVREE());
        values.put(KEY_QTE_RECEPTIONEE,stockDemandeLigne.getQTE_RECEPTIONEE());
        values.put(KEY_CREATEUR_CODE,stockDemandeLigne.getCREATEUR_CODE());
        values.put(KEY_DATE_CREATION,stockDemandeLigne.getDATE_CREATION());
        values.put(KEY_COMMENTAIRE,stockDemandeLigne.getCOMMENTAIRE());
        values.put(KEY_VERSION,stockDemandeLigne.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_STOCKDEMANDE_LIGNE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d("STOCKDEMANDE MANAGER", "New stockdemande inserted into sqlite: " + id);

    }

    public StockDemandeLigne get(String DEMANDE_CODE) {

        StockDemandeLigne stockDemandeLigne = new StockDemandeLigne();
        String selectQuery = "SELECT * FROM " + TABLE_STOCKDEMANDE_LIGNE+ " WHERE "+ KEY_DEMANDE_CODE +" = '"+DEMANDE_CODE+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            stockDemandeLigne.setDEMANDELIGNE_CODE(cursor.getInt(0));
            stockDemandeLigne.setDEMANDE_CODE(cursor.getString(1));
            stockDemandeLigne.setFAMILLE_CODE(cursor.getString(2));
            stockDemandeLigne.setARTICLE_CODE(cursor.getString(3));
            stockDemandeLigne.setARTICLE_DESIGNATION(cursor.getString(4));
            stockDemandeLigne.setAR_NBUS_PAR_UP(cursor.getInt(5));
            stockDemandeLigne.setARTICLE_PRIX(cursor.getDouble(6));
            stockDemandeLigne.setARTICLE_KG(cursor.getDouble(7));
            stockDemandeLigne.setUNITE_CODE(cursor.getString(8));
            stockDemandeLigne.setQTE_COMMANDEE(cursor.getInt(9));
            stockDemandeLigne.setQTE_APPROUVEE(cursor.getInt(10));
            stockDemandeLigne.setQTE_LIVREE(cursor.getInt(11));
            stockDemandeLigne.setQTE_RECEPTIONEE(cursor.getInt(12));
            stockDemandeLigne.setCREATEUR_CODE(cursor.getString(13));
            stockDemandeLigne.setDATE_CREATION(cursor.getString(14));
            stockDemandeLigne.setCOMMENTAIRE(cursor.getString(15));
            stockDemandeLigne.setVERSION(cursor.getString(16));
        }

        cursor.close();
        db.close();
        Log.d("SDL MANAGER", "fetching ");
        return stockDemandeLigne;

    }


    public ArrayList<StockDemandeLigne> getList() {
        ArrayList<StockDemandeLigne> stockDemandeLignes = new ArrayList<StockDemandeLigne>();

        String selectQuery = "SELECT * FROM " + TABLE_STOCKDEMANDE_LIGNE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                StockDemandeLigne stockDemandeLigne = new StockDemandeLigne();
                stockDemandeLigne.setDEMANDELIGNE_CODE(cursor.getInt(0));
                stockDemandeLigne.setDEMANDE_CODE(cursor.getString(1));
                stockDemandeLigne.setFAMILLE_CODE(cursor.getString(2));
                stockDemandeLigne.setARTICLE_CODE(cursor.getString(3));
                stockDemandeLigne.setARTICLE_DESIGNATION(cursor.getString(4));
                stockDemandeLigne.setAR_NBUS_PAR_UP(cursor.getInt(5));
                stockDemandeLigne.setARTICLE_PRIX(cursor.getDouble(6));
                stockDemandeLigne.setARTICLE_KG(cursor.getDouble(7));
                stockDemandeLigne.setUNITE_CODE(cursor.getString(8));
                stockDemandeLigne.setQTE_COMMANDEE(cursor.getInt(9));
                stockDemandeLigne.setQTE_APPROUVEE(cursor.getInt(10));
                stockDemandeLigne.setQTE_LIVREE(cursor.getInt(11));
                stockDemandeLigne.setQTE_RECEPTIONEE(cursor.getInt(12));
                stockDemandeLigne.setCREATEUR_CODE(cursor.getString(13));
                stockDemandeLigne.setDATE_CREATION(cursor.getString(14));
                stockDemandeLigne.setCOMMENTAIRE(cursor.getString(15));
                stockDemandeLigne.setVERSION(cursor.getString(16));
                stockDemandeLignes.add(stockDemandeLigne);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version StockDemandeLigne from Sqlite: "+stockDemandeLignes.size());
        return stockDemandeLignes;
    }

    public ArrayList<StockDemandeLigne> updateList(ArrayList<StockDemandeLigne> stockDemandeLignes) {


        ArrayList<StockDemandeLigne> stockDemandeLignes1 = new ArrayList<>();

        for(int i =0;i<stockDemandeLignes.size();i++){

            stockDemandeLignes.get(i).setQTE_RECEPTIONEE(stockDemandeLignes.get(i).getQTE_LIVREE());
            stockDemandeLignes1.add(stockDemandeLignes.get(i));

        }
        Log.d(TAG, "Fetching code/version StockDemandeLigne from Sqlite: "+stockDemandeLignes.size());
        return stockDemandeLignes;
    }

    public void updatestockDemandeLigne(StockDemandeLigne stockDemandeLigne){

            SQLiteDatabase db = this.getWritableDatabase();
            String req = "UPDATE " +TABLE_STOCKDEMANDE_LIGNE + " SET "+KEY_QTE_RECEPTIONEE  +"= '"+stockDemandeLigne.getQTE_LIVREE()+"' WHERE "+KEY_DEMANDE_CODE +"= '"+stockDemandeLigne.getDEMANDE_CODE()+"' AND "+KEY_DEMANDELIGNE_CODE+"= '"+stockDemandeLigne.getDEMANDELIGNE_CODE()+"'" ;
            db.execSQL(req);
            db.close();
            Log.d("StockDemandeLigne", "updateStockDemandeLigne: "+req);

    }


    public ArrayList<StockDemandeLigne> getListNotInserted() {
        ArrayList<StockDemandeLigne> stockDemandeLignes = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_STOCKDEMANDE_LIGNE +" WHERE "+KEY_COMMENTAIRE+" = 'to_insert' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                StockDemandeLigne stockDemandeLigne = new StockDemandeLigne();

                stockDemandeLigne.setDEMANDELIGNE_CODE(cursor.getInt(0));
                stockDemandeLigne.setDEMANDE_CODE(cursor.getString(1));
                stockDemandeLigne.setFAMILLE_CODE(cursor.getString(2));
                stockDemandeLigne.setARTICLE_CODE(cursor.getString(3));
                stockDemandeLigne.setARTICLE_DESIGNATION(cursor.getString(4));
                stockDemandeLigne.setAR_NBUS_PAR_UP(cursor.getInt(5));
                stockDemandeLigne.setARTICLE_PRIX(cursor.getDouble(6));
                stockDemandeLigne.setARTICLE_KG(cursor.getDouble(7));
                stockDemandeLigne.setUNITE_CODE(cursor.getString(8));
                stockDemandeLigne.setQTE_COMMANDEE(cursor.getInt(9));
                stockDemandeLigne.setQTE_APPROUVEE(cursor.getInt(10));
                stockDemandeLigne.setQTE_LIVREE(cursor.getInt(11));
                stockDemandeLigne.setQTE_RECEPTIONEE(cursor.getInt(12));
                stockDemandeLigne.setCREATEUR_CODE(cursor.getString(13));
                stockDemandeLigne.setDATE_CREATION(cursor.getString(14));
                stockDemandeLigne.setCOMMENTAIRE(cursor.getString(15));
                stockDemandeLigne.setVERSION(cursor.getString(16));

                Log.d(TAG, "getListNotInserted: "+stockDemandeLignes.toString());
                stockDemandeLignes.add(stockDemandeLigne);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching stockDemandeLignes from table StockDemandeLigne: ");
        Log.d(TAG, "Nombre stockDemandeLignes from table StockDemandeLigne: "+stockDemandeLignes.size());
        return stockDemandeLignes;
    }

    public ArrayList<StockDemandeLigne> getListInserted() {
        ArrayList<StockDemandeLigne> stockDemandeLignes = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_STOCKDEMANDE_LIGNE +" WHERE "+KEY_COMMENTAIRE+" = 'inserted' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                StockDemandeLigne stockDemandeLigne = new StockDemandeLigne();

                stockDemandeLigne.setDEMANDELIGNE_CODE(cursor.getInt(0));
                stockDemandeLigne.setDEMANDE_CODE(cursor.getString(1));
                stockDemandeLigne.setFAMILLE_CODE(cursor.getString(2));
                stockDemandeLigne.setARTICLE_CODE(cursor.getString(3));
                stockDemandeLigne.setARTICLE_DESIGNATION(cursor.getString(4));
                stockDemandeLigne.setAR_NBUS_PAR_UP(cursor.getInt(5));
                stockDemandeLigne.setARTICLE_PRIX(cursor.getDouble(6));
                stockDemandeLigne.setARTICLE_KG(cursor.getDouble(7));
                stockDemandeLigne.setUNITE_CODE(cursor.getString(8));
                stockDemandeLigne.setQTE_COMMANDEE(cursor.getInt(9));
                stockDemandeLigne.setQTE_APPROUVEE(cursor.getInt(10));
                stockDemandeLigne.setQTE_LIVREE(cursor.getInt(11));
                stockDemandeLigne.setQTE_RECEPTIONEE(cursor.getInt(12));
                stockDemandeLigne.setCREATEUR_CODE(cursor.getString(13));
                stockDemandeLigne.setDATE_CREATION(cursor.getString(14));
                stockDemandeLigne.setCOMMENTAIRE(cursor.getString(15));
                stockDemandeLigne.setVERSION(cursor.getString(16));

                Log.d(TAG, "getListInserted: "+stockDemandeLignes.toString());
                stockDemandeLignes.add(stockDemandeLigne);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching stockDemandeLignes from table StockDemandeLigne: ");
        Log.d(TAG, "Nombre stockDemandeLignes from table StockDemandeLigne: "+stockDemandeLignes.size());
        return stockDemandeLignes;
    }

    public ArrayList<StockDemandeLigne> getListReceptionnee() {
        ArrayList<StockDemandeLigne> stockDemandeLignes = new ArrayList<>();

        String selectQuery = "SELECT stockdemandeligne.* FROM stockdemandeligne left join (select distinct stockdemandeligne.DEMANDE_CODE,SUM(stockdemandeligne.QTE_RECEPTIONEE) as 'SQL'\n" +
                "\n" +
                "from stockdemandeligne group by stockdemandeligne.DEMANDE_CODE) AS TSQL on TSQL.DEMANDE_CODE=stockdemandeligne.DEMANDE_CODE WHERE IFNULL(TSQL.SQL,0) != 0 AND stockdemandeligne.COMMENTAIRE != 'receptionnee'";

        Log.d(TAG, "getListReceptionnee: "+selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                StockDemandeLigne stockDemandeLigne = new StockDemandeLigne();

                stockDemandeLigne.setDEMANDELIGNE_CODE(cursor.getInt(0));
                stockDemandeLigne.setDEMANDE_CODE(cursor.getString(1));
                stockDemandeLigne.setFAMILLE_CODE(cursor.getString(2));
                stockDemandeLigne.setARTICLE_CODE(cursor.getString(3));
                stockDemandeLigne.setARTICLE_DESIGNATION(cursor.getString(4));
                stockDemandeLigne.setAR_NBUS_PAR_UP(cursor.getInt(5));
                stockDemandeLigne.setARTICLE_PRIX(cursor.getDouble(6));
                stockDemandeLigne.setARTICLE_KG(cursor.getDouble(7));
                stockDemandeLigne.setUNITE_CODE(cursor.getString(8));
                stockDemandeLigne.setQTE_COMMANDEE(cursor.getInt(9));
                stockDemandeLigne.setQTE_APPROUVEE(cursor.getInt(10));
                stockDemandeLigne.setQTE_LIVREE(cursor.getInt(11));
                stockDemandeLigne.setQTE_RECEPTIONEE(cursor.getInt(12));
                stockDemandeLigne.setCREATEUR_CODE(cursor.getString(13));
                stockDemandeLigne.setDATE_CREATION(cursor.getString(14));
                stockDemandeLigne.setCOMMENTAIRE(cursor.getString(15));
                stockDemandeLigne.setVERSION(cursor.getString(16));

                Log.d(TAG, "getListInserted: "+stockDemandeLignes.toString());
                stockDemandeLignes.add(stockDemandeLigne);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching stockDemandeLignes from table StockDemandeLigne: ");
        Log.d(TAG, "Nombre stockDemandeLignes from table StockDemandeLigne: "+stockDemandeLignes.size());
        return stockDemandeLignes;
    }

    public ArrayList<StockDemandeLigne> getListByDemandeCode(String demande_code) {
        ArrayList<StockDemandeLigne> stockDemandeLignes = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_STOCKDEMANDE_LIGNE +" WHERE "+KEY_DEMANDE_CODE+" = '"+demande_code+"' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                StockDemandeLigne stockDemandeLigne = new StockDemandeLigne();

                stockDemandeLigne.setDEMANDELIGNE_CODE(cursor.getInt(0));
                stockDemandeLigne.setDEMANDE_CODE(cursor.getString(1));
                stockDemandeLigne.setFAMILLE_CODE(cursor.getString(2));
                stockDemandeLigne.setARTICLE_CODE(cursor.getString(3));
                stockDemandeLigne.setARTICLE_DESIGNATION(cursor.getString(4));
                stockDemandeLigne.setAR_NBUS_PAR_UP(cursor.getInt(5));
                stockDemandeLigne.setARTICLE_PRIX(cursor.getDouble(6));
                stockDemandeLigne.setARTICLE_KG(cursor.getDouble(7));
                stockDemandeLigne.setUNITE_CODE(cursor.getString(8));
                stockDemandeLigne.setQTE_COMMANDEE(cursor.getInt(9));
                stockDemandeLigne.setQTE_APPROUVEE(cursor.getInt(10));
                stockDemandeLigne.setQTE_LIVREE(cursor.getInt(11));
                stockDemandeLigne.setQTE_RECEPTIONEE(cursor.getInt(12));
                stockDemandeLigne.setCREATEUR_CODE(cursor.getString(13));
                stockDemandeLigne.setDATE_CREATION(cursor.getString(14));
                stockDemandeLigne.setCOMMENTAIRE(cursor.getString(15));
                stockDemandeLigne.setVERSION(cursor.getString(16));

                Log.d(TAG, "getListNotInserted: "+stockDemandeLignes.toString());
                stockDemandeLignes.add(stockDemandeLigne);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching stockDemandeLignes from table StockDemandeLigne: ");
        Log.d(TAG, "Nombre stockDemandeLignes from table StockDemandeLigne: "+stockDemandeLignes.size());
        return stockDemandeLignes;
    }

    public int delete(String stockdemande_code, String stockdemandeligne_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result;
        result = db.delete(TABLE_STOCKDEMANDE_LIGNE,KEY_DEMANDE_CODE+"=?  AND "+KEY_DEMANDELIGNE_CODE+"=?" ,new String[]{stockdemande_code,stockdemandeligne_code});
        db.close();
        return result;

    }

   /* public int deletePull(String stockdemande_code, String stockdemandeligne_code,String version) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_STOCKDEMANDE_LIGNE,KEY_DEMANDE_CODE+"=? AND "+KEY_DEMANDELIGNE_CODE+"=? AND "+KEY_VERSION+"=?",new String[]{stockdemande_code,stockdemandeligne_code,version});
        return result;

    }*/

    public int deletePull(String stockdemande_code, String stockdemandeligne_code, String version) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_STOCKDEMANDE_LIGNE,KEY_DEMANDE_CODE+"=? AND "+KEY_DEMANDELIGNE_CODE+"=? AND "+KEY_VERSION+"=? AND "+KEY_COMMENTAIRE+"=?",new String[]{stockdemande_code,stockdemandeligne_code,version,"inserted"});
        return result;

    }

    public void updateStockDemandeLigneReceptionnee(String stockdemande_code, String stockdemandeligne_code,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_STOCKDEMANDE_LIGNE + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_DEMANDE_CODE +"= '"+stockdemande_code+"' AND "+KEY_DEMANDELIGNE_CODE+"= '"+stockdemandeligne_code+"'" ;
        db.execSQL(req);
        db.close();
        Log.d("commandeligne", "updateCommandeligne: "+req);
    }


    public void updateStockDemandeLigne(String stockdemande_code, String stockdemandeligne_code,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_STOCKDEMANDE_LIGNE + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_DEMANDE_CODE +"= '"+stockdemande_code+"' AND "+KEY_DEMANDELIGNE_CODE+"= '"+stockdemandeligne_code+"'" ;
        db.execSQL(req);
        db.close();
        Log.d("commandeligne", "updateCommandeligne: "+req);
    }

    public void deleteStockDemandeLigneSynchronisee() {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefCmdAS = sdf.format(new Date());
        Log.d(TAG, "deleteStockDemandeLigneSynchronisee: "+DatefCmdAS);

        String Where = " "+KEY_COMMENTAIRE+"='receptionnee' and date("+KEY_DATE_CREATION+")!='"+DatefCmdAS+"' and stockdemandeligne."+KEY_DEMANDE_CODE +" in (SELECT stockdemande.DEMANDE_CODE FROM stockdemande where stockdemande.COMMENTAIRE = 'inserted' and stockdemande.STATUT_CODE = 34)";
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_STOCKDEMANDE_LIGNE, Where, null);
        db.close();
        Log.d("stockdemandelignes", "Deleted all stockdemandelignes verifiee from sqlite");
        Log.d("stockdemandelignes", "deletestockdemandelignesSynchronisee: "+Where);
    }


    public static void synchronisationStockDemandeLigne(final Context context){

        StockDemandeLigneManager stockDemandeLigneManager = new StockDemandeLigneManager(context);
        stockDemandeLigneManager.deleteStockDemandeLigneSynchronisee();

        String tag_string_req = "STOCK_DEMANDE_LIGNE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_STOCKDEMANDE_LIGNE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    Log.d(TAG, "onResponse: StockdemandeLigne"+ response);
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray stockDemandeLignes = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre de StockDemandeLignes  "+stockDemandeLignes.length() , Toast.LENGTH_LONG).show();
                        // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les StockDemandeLignes : " +response);

                        if(stockDemandeLignes.length()>0) {
                            StockDemandeLigneManager stockDemandeLigneManager = new StockDemandeLigneManager(context);
                            for (int i = 0; i < stockDemandeLignes.length(); i++) {
                                JSONObject unStockDemandeLigne = stockDemandeLignes.getJSONObject(i);
                                if (unStockDemandeLigne.getString("Statut").equals("true")) {
                                    stockDemandeLigneManager.updateStockDemandeLigne((unStockDemandeLigne.getString("DEMANDE_CODE")),unStockDemandeLigne.getString("DEMANDELIGNE_CODE"),"inserted");
                                    cptInsert++;
                                }else{
                                    cptDelete++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STOCK_DEMANDE_LIGNE: Inserted: "+cptInsert +" NotInserted: "+cptDelete ,"STOCK_DEMANDE_LIGNE",1));

                        }
                        //logM.add("StockDemandeLigne:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"StockDemandeLigne");


                    }else {
                        String errorMsg = jObj.getString("info");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STOCK_DEMANDE_LIGNE: Error: "+errorMsg ,"STOCK_DEMANDE_LIGNE",0));

                        }
                        //Toast.makeText(context, "StockDemandeLigne : "+errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STOCK_DEMANDE_LIGNE: Error: "+e.getMessage() ,"STOCK_DEMANDE_LIGNE",0));

                    }
                    //Toast.makeText(context, "StockDemandeLigne : "+"Json error: " +"erreur applcation StockDemandeLigne" + e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STOCK_DEMANDE_LIGNE: Error: "+error.getMessage() ,"STOCK_DEMANDE_LIGNE",0));

                }
                //Toast.makeText(context, "StockDemandeLigne : "+error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    StockDemandeLigneManager stockDemandeLigneManager = new StockDemandeLigneManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableStockDemandeLigne");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(stockDemandeLigneManager.getListNotInserted()));

                    Log.d("StockDemandeLignesynch", "Liste: "+stockDemandeLigneManager.getListNotInserted());
                    Log.d("StockDemandeLignesynch", "Listeall: "+stockDemandeLigneManager.getList());
                    Log.d(TAG, "StockDemandegetParams: listnotinserted"+stockDemandeLigneManager.getListNotInserted().size());
                    Log.d(TAG, "StockDemandegetParams: listnotinsertedall"+stockDemandeLigneManager.getList().size());
                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public static void synchronisationStockDemandeLignePull(final Context context){

        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_STOCKDEMANDE_LIGNE_PULL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("stockDemandepullLignes", "onResponse: "+response);

                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    Log.d("stockDemandeLignePull",response);
                    JSONObject jObj = new JSONObject(response);

                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray StockDemandeLignes = jObj.getJSONArray("StockDemandeLignesPull");
                        Toast.makeText(context, "nombre de stockDemandeLignePull  "+StockDemandeLignes.length()  , Toast.LENGTH_SHORT).show();
                        if(StockDemandeLignes.length()>0) {
                            StockDemandeLigneManager stockDemandeLigneManager = new StockDemandeLigneManager(context);
                            for (int i = 0; i < StockDemandeLignes.length(); i++) {
                                JSONObject stockDemandeLigne = StockDemandeLignes.getJSONObject(i);

                                if (stockDemandeLigne.getString("OPERATION").equals("DELETE")) {

                                    stockDemandeLigneManager.deletePull(stockDemandeLigne.getString("DEMANDE_CODE"),stockDemandeLigne.getString("DEMANDELIGNE_CODE"),stockDemandeLigne.getString("VERSION"));
                                    cptDelete++;
                                } else {
                                    stockDemandeLigneManager.add(new StockDemandeLigne(stockDemandeLigne));
                                    cptInsert++;
                                }
                            }

                        }
                        logM.add("StockDemandeLignePull:OK Insert:"+cptInsert +"Delete:"+cptDelete ,"SyncStockDemandeLignePull");
                    }else {
                        String errorMsg = jObj.getString("info");
                        Toast.makeText(context,
                                "StockDemandeLignePull : "+errorMsg, Toast.LENGTH_LONG).show();
                        logM.add("StockDemandeLignePull:NOK "+errorMsg ,"SyncStockDemandeLignePull");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "StockDemandeLignePull : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    logM.add("StockDemandeLignePull:NOK "+e.getMessage() ,"SyncStockDemandeLignePull");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        "StockDemandeLignePull : "+error.getMessage(), Toast.LENGTH_LONG).show();
                LogSyncManager logM= new LogSyncManager(context);
                logM.add("StockDemandeLignePull:NOK "+error.getMessage() ,"SyncStockDemandeLignePull");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //Map<String, String> params = new HashMap<String, String>();
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    StockDemandeLigneManager stockDemandeLigneManager  = new StockDemandeLigneManager(context);
                    List<StockDemandeLigne> stockDemandeLignes = stockDemandeLigneManager.getListInserted();

                    Log.d(TAG, "getParams: stockDemandepullLignes"+stockDemandeLignes);

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(stockDemandeLignes));


                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public static void synchronisationStockDemandeLigneReceptionnee(final Context context){

        StockDemandeLigneManager stockDemandeLigneManager = new StockDemandeLigneManager(context);
        stockDemandeLigneManager.deleteStockDemandeLigneSynchronisee();

        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_STOCKDEMANDE_LIGNE_RECEPTIONNEE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    Log.d(TAG, "onResponse: StockdemandeLignereception"+ response);
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray stockDemandeLignes = jObj.getJSONArray("result");
                        Toast.makeText(context, "Nombre de StockDemandeLignesreception  "+stockDemandeLignes.length() , Toast.LENGTH_LONG).show();
                        // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les StockDemandeLignes : " +response);

                        if(stockDemandeLignes.length()>0) {
                            StockDemandeLigneManager stockDemandeLigneManager = new StockDemandeLigneManager(context);
                            for (int i = 0; i < stockDemandeLignes.length(); i++) {
                                JSONObject unStockDemandeLigne = stockDemandeLignes.getJSONObject(i);
                                if (unStockDemandeLigne.getString("Statut").equals("true")) {
                                    stockDemandeLigneManager.updateStockDemandeLigneReceptionnee((unStockDemandeLigne.getString("DEMANDE_CODE")),unStockDemandeLigne.getString("DEMANDELIGNE_CODE"),"receptionnee");
                                }
                            }
                        }
                        logM.add("StockDemandeLigneReception:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"StockDemandeLigne");


                    }else {
                        String errorMsg = jObj.getString("info");
                        Toast.makeText(context,
                                "StockDemandeLigneReception : "+errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "StockDemandeLigneReception : "+"Json error: " +"erreur applcation StockDemandeLigne" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        "StockDemandeLigne : "+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    StockDemandeLigneManager stockDemandeLigneManager = new StockDemandeLigneManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableStockDemandeLigne");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(stockDemandeLigneManager.getListReceptionnee()));

                    Log.d("StockDemandeLignesynch", "Liste: "+stockDemandeLigneManager.getListReceptionnee());
                    Log.d("StockDemandeLignesynch", "Listeall: "+stockDemandeLigneManager.getList());
                    Log.d(TAG, "StockDemandegetParams: listnotinserted"+stockDemandeLigneManager.getListReceptionnee().size());
                    Log.d(TAG, "StockDemandegetParams: listnotinsertedall"+stockDemandeLigneManager.getList().size());
                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public ArrayList<StockDemandeLigne> fixSDLigneCode(ArrayList<StockDemandeLigne> stockDemandeLignes){

        ArrayList<StockDemandeLigne> stockDemandeLignes1 = stockDemandeLignes;

        for(int i=0 ; i<stockDemandeLignes1.size(); i++){

            stockDemandeLignes1.get(i).setDEMANDELIGNE_CODE(i+1);
        }

        return stockDemandeLignes1;
    }

}
