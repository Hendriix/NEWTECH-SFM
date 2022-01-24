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
import com.newtech.newtech_sfm.Metier.StockPLigne;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StockPLigneManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_STOCKP_LIGNE = "stockpligne";

    public static final String

            KEY_STOCKLIGNE_CODE = "STOCKLIGNE_CODE",
            KEY_STOCK_CODE = "STOCK_CODE",
            KEY_FAMILLE_CODE = "FAMILLE_CODE",
            KEY_ARTICLE_CODE = "ARTICLE_CODE",
            KEY_ARTICLE_DESIGNATION = "ARTICLE_DESIGNATION",
            KEY_ARTICLE_NBUS_PAR_UP = "ARTICLE_NBUS_PAR_UP",
            KEY_ARTICLE_PRIX = "ARTICLE_PRIX",
            KEY_UNITE_CODE = "UNITE_CODE",
            KEY_QTE = "QTE",
            KEY_LITTRAGE = "LITTRAGE",
            KEY_TONNAGE = "TONNAGE",
            KEY_COMMENTAIRE = "COMMENTAIRE",
            KEY_CREATEUR_CODE = "CREATEUR_CODE",
            KEY_DATE_CREATION = "DATE_CREATION",
            KEY_TS = "TS",
            KEY_VERSION = "VERSION";

    public static String CREATE_STOCKP_LIGNE_TABLE = "CREATE TABLE " + TABLE_STOCKP_LIGNE+ " ("

            +KEY_STOCKLIGNE_CODE + " TEXT PRIMARY KEY,"
            +KEY_STOCK_CODE + " TEXT,"
            +KEY_FAMILLE_CODE + " TEXT,"
            +KEY_ARTICLE_CODE + " TEXT,"
            +KEY_ARTICLE_DESIGNATION + " TEXT,"
            +KEY_ARTICLE_NBUS_PAR_UP + " NUMERIC,"
            +KEY_ARTICLE_PRIX + " NUMERIC,"
            +KEY_UNITE_CODE + " TEXT,"
            +KEY_QTE + " NUMERIC,"
            +KEY_LITTRAGE + " NUMERIC,"
            +KEY_TONNAGE + " NUMERIC,"
            +KEY_COMMENTAIRE + " TEXT,"
            +KEY_CREATEUR_CODE + " TEXT,"
            +KEY_DATE_CREATION + " TEXT,"
            +KEY_TS + " TEXT,"
            +KEY_VERSION + " TEXT,"
            +"CONSTRAINT stockpligneunique UNIQUE ("+KEY_STOCKLIGNE_CODE+","+KEY_STOCK_CODE+")" +")";



    public StockPLigneManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAMILLE);
            db.execSQL(CREATE_STOCKP_LIGNE_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database STOCKPLIGNE tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCKP_LIGNE);
        // Create tables again
        onCreate(db);
    }

    public void add(StockPLigne stockPLigne) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_STOCKLIGNE_CODE,stockPLigne.getSTOCKLIGNE_CODE());
        values.put(KEY_STOCK_CODE,stockPLigne.getSTOCK_CODE());
        values.put(KEY_FAMILLE_CODE,stockPLigne.getFAMILLE_CODE());
        values.put(KEY_ARTICLE_CODE,stockPLigne.getARTICLE_CODE());
        values.put(KEY_ARTICLE_DESIGNATION,stockPLigne.getARTICLE_DESIGNATION());
        values.put(KEY_ARTICLE_NBUS_PAR_UP,stockPLigne.getARTICLE_NBUS_PAR_UP());
        values.put(KEY_ARTICLE_PRIX,stockPLigne.getARTICLE_PRIX());
        values.put(KEY_UNITE_CODE,stockPLigne.getUNITE_CODE());
        values.put(KEY_QTE,stockPLigne.getQTE());
        values.put(KEY_LITTRAGE,stockPLigne.getLITTRAGE());
        values.put(KEY_TONNAGE,stockPLigne.getTONNAGE());
        values.put(KEY_COMMENTAIRE,stockPLigne.getCOMMENTAIRE());
        values.put(KEY_CREATEUR_CODE,stockPLigne.getCREATEUR_CODE());
        values.put(KEY_DATE_CREATION,stockPLigne.getDATE_CREATION());
        values.put(KEY_TS,stockPLigne.getTS());
        values.put(KEY_VERSION,stockPLigne.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_STOCKP_LIGNE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d("stockPLigne MANAGER", "New stockPLigne inserted into sqlite: " + id);

    }

    public StockPLigne get(String STOCK_CODE) {

        StockPLigne stockPLigne = new StockPLigne();
        String selectQuery = "SELECT * FROM " + TABLE_STOCKP_LIGNE+ " WHERE "+ KEY_STOCK_CODE +" = '"+STOCK_CODE+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {

            stockPLigne.setSTOCKLIGNE_CODE(cursor.getInt(0));
            stockPLigne.setSTOCK_CODE(cursor.getString(1));
            stockPLigne.setFAMILLE_CODE(cursor.getString(2));
            stockPLigne.setARTICLE_CODE(cursor.getString(3));
            stockPLigne.setARTICLE_DESIGNATION(cursor.getString(4));
            stockPLigne.setARTICLE_NBUS_PAR_UP(cursor.getDouble(5));
            stockPLigne.setARTICLE_PRIX(cursor.getDouble(6));
            stockPLigne.setUNITE_CODE(cursor.getString(7));
            stockPLigne.setQTE(cursor.getDouble(8));
            stockPLigne.setLITTRAGE(cursor.getDouble(9));
            stockPLigne.setTONNAGE(cursor.getDouble(10));
            stockPLigne.setCOMMENTAIRE(cursor.getString(11));
            stockPLigne.setCREATEUR_CODE(cursor.getString(12));
            stockPLigne.setDATE_CREATION(cursor.getString(13));
            stockPLigne.setTS(cursor.getString(14));
            stockPLigne.setVERSION(cursor.getString(15));
        }

        cursor.close();
        db.close();
        Log.d("STOCKPLIGNE MANAGER", "fetching ");
        return stockPLigne;

    }

    public ArrayList<StockPLigne> getList() {
        ArrayList<StockPLigne> stockPLignes = new ArrayList<StockPLigne>();

        String selectQuery = "SELECT * FROM " + TABLE_STOCKP_LIGNE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                StockPLigne stockPLigne = new StockPLigne();
                stockPLigne.setSTOCKLIGNE_CODE(cursor.getInt(0));
                stockPLigne.setSTOCK_CODE(cursor.getString(1));
                stockPLigne.setFAMILLE_CODE(cursor.getString(2));
                stockPLigne.setARTICLE_CODE(cursor.getString(3));
                stockPLigne.setARTICLE_DESIGNATION(cursor.getString(4));
                stockPLigne.setARTICLE_NBUS_PAR_UP(cursor.getDouble(5));
                stockPLigne.setARTICLE_PRIX(cursor.getDouble(6));
                stockPLigne.setUNITE_CODE(cursor.getString(7));
                stockPLigne.setQTE(cursor.getDouble(8));
                stockPLigne.setLITTRAGE(cursor.getDouble(9));
                stockPLigne.setTONNAGE(cursor.getDouble(10));
                stockPLigne.setCOMMENTAIRE(cursor.getString(11));
                stockPLigne.setCREATEUR_CODE(cursor.getString(12));
                stockPLigne.setDATE_CREATION(cursor.getString(13));
                stockPLigne.setTS(cursor.getString(14));
                stockPLigne.setVERSION(cursor.getString(15));
                stockPLignes.add(stockPLigne);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Liste StockPLigne from Sqlite: "+stockPLignes.size());
        return stockPLignes;
    }

    public int delete(String STOCK_CODE,String STOCKLIGNE_CODE,String VERSION) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_STOCKP_LIGNE,KEY_STOCK_CODE+"=? AND "+KEY_STOCKLIGNE_CODE +" =? AND "+KEY_VERSION +" =?",new String[]{STOCK_CODE,STOCKLIGNE_CODE,VERSION});
        return result;

    }

    public void updateStockPLigne(String stockCode, String sotkcPLigneCode,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_STOCKP_LIGNE + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_STOCK_CODE +"= '"+stockCode+"' AND "+KEY_STOCKLIGNE_CODE +"= '"+sotkcPLigneCode+"'" ;
        db.execSQL(req);
        db.close();

        Log.d("visibilite", "updateVisibilite: "+req);
    }

    public void deleteStockPLigneSynchronisee() {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefCmdAS = sdf.format(new Date());
        Log.d(TAG, "deleteStockPLigneSynchronisee: "+DatefCmdAS);

        String Where = " "+KEY_COMMENTAIRE+"='inserted' and "+KEY_STOCK_CODE+" NOT IN (SELECT stockp.STOCK_CODE FROM stockp)";
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_STOCKP_LIGNE, Where, null);
        db.close();
        Log.d("stockPLigne", "Deleted all stockPLigne verifiee from sqlite");
        Log.d("stockPLigne", "deleteStockPLigneSynchronisee: "+Where);
    }

    public ArrayList<StockPLigne> getListNotInserted() {
        ArrayList<StockPLigne> stockPLignes = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_STOCKP_LIGNE +" WHERE "+KEY_COMMENTAIRE+" = 'to_insert' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                StockPLigne stockPLigne = new StockPLigne();
                stockPLigne.setSTOCKLIGNE_CODE(cursor.getInt(0));
                stockPLigne.setSTOCK_CODE(cursor.getString(1));
                stockPLigne.setFAMILLE_CODE(cursor.getString(2));
                stockPLigne.setARTICLE_CODE(cursor.getString(3));
                stockPLigne.setARTICLE_DESIGNATION(cursor.getString(4));
                stockPLigne.setARTICLE_NBUS_PAR_UP(cursor.getDouble(5));
                stockPLigne.setARTICLE_PRIX(cursor.getDouble(6));
                stockPLigne.setUNITE_CODE(cursor.getString(7));
                stockPLigne.setQTE(cursor.getDouble(8));
                stockPLigne.setLITTRAGE(cursor.getDouble(9));
                stockPLigne.setTONNAGE(cursor.getDouble(10));
                stockPLigne.setCOMMENTAIRE(cursor.getString(11));
                stockPLigne.setCREATEUR_CODE(cursor.getString(12));
                stockPLigne.setDATE_CREATION(cursor.getString(13));
                stockPLigne.setTS(cursor.getString(14));
                stockPLigne.setVERSION(cursor.getString(15));
                stockPLignes.add(stockPLigne);

                Log.d(TAG, "getListNotInserted: "+stockPLigne.toString());
                stockPLignes.add(stockPLigne);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching stockPLigne from table stockPLigne: ");
        return stockPLignes;
    }

    //SYNCHRONISATION PUSH
    public static void synchronisationStockPLigne(final Context context){

        StockPLigneManager stockPLigneManager = new StockPLigneManager(context);
        stockPLigneManager.deleteStockPLigneSynchronisee();

        String tag_string_req = "STOCKP_LIGNE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_STOCKP_LIGNE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                Log.d(TAG, "onResponse: "+response);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray stockPLignes = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre de stockPLignes  "+stockPLignes.length() , Toast.LENGTH_LONG).show();
                        // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les StockPLignes : " +response);

                        if(stockPLignes.length()>0) {
                            StockPLigneManager stockPLigneManager = new StockPLigneManager(context);
                            for (int i = 0; i < stockPLignes.length(); i++) {
                                JSONObject uneStockPLigne = stockPLignes.getJSONObject(i);
                                if (uneStockPLigne.getString("Statut").equals("true")) {
                                    stockPLigneManager.updateStockPLigne((uneStockPLigne.getString("STOCK_CODE")),(uneStockPLigne.getString("STOCKLIGNE_CODE")),"inserted");
                                    cptInsert++;
                                }else{
                                    cptDelete++;
                                }
                            }
                        }
                        //logM.add("StockPLigne:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncStockPLigne");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STOCKP_LIGNE: Inserted: "+cptInsert +" NotInserted: "+cptDelete ,"STOCKP_LIGNE",1));

                        }

                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "stockPLigne : "+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STOCKP_LIGNE: Error: "+errorMsg,"STOCKP_LIGNE",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "stockPLigne : "+"Json error: " +"erreur applcation stockPLigne" + e.getMessage(), Toast.LENGTH_LONG).show();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STOCKP_LIGNE: Error: "+e.getMessage(),"STOCKP_LIGNE",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "stockPLigne : "+error.getMessage(), Toast.LENGTH_LONG).show();
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STOCKP_LIGNE: Error: "+error.getMessage(),"STOCKP_LIGNE",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    StockPLigneManager stockPLigneManager  = new StockPLigneManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableArticle");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(stockPLigneManager.getListNotInserted()));
                    Log.d("StockPLignesynch", "Liste: "+stockPLigneManager.getListNotInserted());
                    Log.d(TAG, "getParams: listnotinserted"+stockPLigneManager.getListNotInserted().size());
                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
   

    public ArrayList<StockPLigne> fixStockPLigneCode(ArrayList<StockPLigne> stockPLignes){

        ArrayList<StockPLigne> stockPLignes1 = stockPLignes;

        for(int i=0 ; i<stockPLignes1.size(); i++){

            stockPLignes1.get(i).setSTOCKLIGNE_CODE(i+1);
        }

        return stockPLignes1;
    }

    //SYNCHRONISATION STOCKP
    /*public static void synchronisationStockPLigne(final Context context){

        StockPLigneManager stockPLigneManager = new StockPLigneManager(context);
        stockPLigneManager.deleteStockPLigneSynchronisee();

        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_STOCKP_LIGNE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("stockpligne", "onResponse: "+response);

                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    Log.d("stockpligne response",response);
                    JSONObject jObj = new JSONObject(response);

                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray StockPLignes = jObj.getJSONArray("StockPLignes");
                        Toast.makeText(context, "nombre de stockpligne  "+StockPLignes.length()  , Toast.LENGTH_SHORT).show();
                        if(StockPLignes.length()>0) {
                            StockPLigneManager stockPLigneManager = new StockPLigneManager(context);
                            for (int i = 0; i < StockPLignes.length(); i++) {
                                JSONObject stockPLigne = StockPLignes.getJSONObject(i);

                                if (stockPLigne.getString("OPERATION").equals("DELETE")) {
                                    stockPLigneManager.delete(stockPLigne.getString("STOCK_CODE"),stockPLigne.getString("STOCKLIGNE_CODE"),stockPLigne.getString("VERSION"));
                                    cptDelete++;
                                } else {
                                    stockPLigneManager.add(new StockPLigne(stockPLigne));
                                    cptInsert++;
                                }
                            }

                        }
                        logM.add("StockPLigne:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncStockPLigne");
                    }else {
                        String errorMsg = jObj.getString("info");
                        Toast.makeText(context,
                                "StockPLigne : "+errorMsg, Toast.LENGTH_LONG).show();
                        logM.add("StockPLigne:NOK "+errorMsg ,"SyncStockPLigne");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "StockPLigne : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    logM.add("StockPLigne:NOK "+e.getMessage() ,"SyncStockPLigne");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        "StockPLigne : "+error.getMessage(), Toast.LENGTH_LONG).show();
                LogSyncManager logM= new LogSyncManager(context);
                logM.add("StockPLigne:NOK "+error.getMessage() ,"SyncStockPLigne");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //Map<String, String> params = new HashMap<String, String>();
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    StockPLigneManager stockPLigneManager  = new StockPLigneManager(context);
                    List<StockPLigne> stockPLignes = stockPLigneManager.getList();

                    Log.d(TAG, "getParams: stockPLigne"+stockPLignes);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    //Log.d("UC STOCKP SYNC",gson.toJson(pref.getString("UTILISATEUR_CODE",null)));

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(stockPLignes));

                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }*/
    
    

}
