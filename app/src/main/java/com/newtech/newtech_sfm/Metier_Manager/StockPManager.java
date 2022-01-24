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
import com.newtech.newtech_sfm.Metier.StockP;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StockPManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_STOCKP = "stockp";

    public static final String
            KEY_STOCK_CODE = "STOCK_CODE",
            KEY_STOCK_NOM = "STOCK_NOM",
            KEY_STOCK_DATE = "STOCK_DATE",
            KEY_CLIENT_CODE = "CLIENT_CODE",
            KEY_DATE_CREATION = "DATE_CREATION",
            KEY_DESCRIPTION = "DESCRIPTION",
            KEY_STATUT_CODE = "STATUT_CODE",
            KEY_TYPE_CODE = "TYPE_CODE",
            KEY_CATEGORIE_CODE = "CATEGORIE_CODE",
            KEY_CREATEUR_CODE = "CREATEUR_CODE",
            KEY_TS = "TS",
            KEY_COMMENTAIRE = "COMMENTAIRE",
            KEY_VERSION="VERSION";


    public static String CREATE_STOCKP_TABLE = "CREATE TABLE " + TABLE_STOCKP+ " ("

            +KEY_STOCK_CODE + " TEXT PRIMARY KEY,"
            +KEY_STOCK_NOM + " TEXT,"
            +KEY_STOCK_DATE + " TEXT,"
            +KEY_CLIENT_CODE + " TEXT,"
            +KEY_DATE_CREATION + " TEXT,"
            +KEY_DESCRIPTION + " TEXT,"
            +KEY_STATUT_CODE + " TEXT,"
            +KEY_TYPE_CODE + " TEXT,"
            +KEY_CATEGORIE_CODE + " TEXT,"
            +KEY_CREATEUR_CODE + " TEXT,"
            +KEY_TS + " TEXT,"
            +KEY_COMMENTAIRE + " TEXT,"
            +KEY_VERSION + " TEXT " + ")";

    public StockPManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAMILLE);
            db.execSQL(CREATE_STOCKP_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database STOCKP tables created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCKP);
        // Create tables again
        onCreate(db);
    }


    public void add(StockP stockP) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_STOCK_CODE,stockP.getSTOCK_CODE());
        values.put(KEY_STOCK_NOM,stockP.getSTOCK_NOM());
        values.put(KEY_STOCK_DATE,stockP.getSTOCK_DATE());
        values.put(KEY_CLIENT_CODE,stockP.getCLIENT_CODE());
        values.put(KEY_DATE_CREATION,stockP.getDATE_CREATION());
        values.put(KEY_DESCRIPTION,stockP.getDESCRIPTION());
        values.put(KEY_STATUT_CODE,stockP.getSTATUT_CODE());
        values.put(KEY_TYPE_CODE,stockP.getTYPE_CODE());
        values.put(KEY_CATEGORIE_CODE,stockP.getCATEGORIE_CODE());
        values.put(KEY_CREATEUR_CODE,stockP.getCREATEUR_CODE());
        values.put(KEY_TS,stockP.getTS());
        values.put(KEY_COMMENTAIRE,stockP.getCOMMENTAIRE());
        values.put(KEY_VERSION,stockP.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_STOCKP, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d("stockP MANAGER", "New stockP inserted into sqlite: " + id);

    }

    public StockP get(String STOCK_CODE) {

        StockP stockP = new StockP();
        String selectQuery = "SELECT * FROM " + TABLE_STOCKP+ " WHERE "+ KEY_STOCK_CODE +" = '"+STOCK_CODE+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            stockP.setSTOCK_CODE(cursor.getString(0));
            stockP.setSTOCK_NOM(cursor.getString(1));
            stockP.setSTOCK_DATE(cursor.getString(2));
            stockP.setCLIENT_CODE(cursor.getString(3));
            stockP.setDATE_CREATION(cursor.getString(4));
            stockP.setDESCRIPTION(cursor.getString(5));
            stockP.setSTATUT_CODE(cursor.getString(6));
            stockP.setTYPE_CODE(cursor.getString(7));
            stockP.setCATEGORIE_CODE(cursor.getString(8));
            stockP.setCREATEUR_CODE(cursor.getString(9));
            stockP.setTS(cursor.getString(10));
            stockP.setCOMMENTAIRE(cursor.getString(11));
            stockP.setVERSION(cursor.getString(12));
        }

        cursor.close();
        db.close();
        Log.d("STOCKP MANAGER", "fetching ");
        return stockP;

    }

    public ArrayList<StockP> getList() {
        ArrayList<StockP> stockPS = new ArrayList<StockP>();

        String selectQuery = "SELECT * FROM " + TABLE_STOCKP;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                StockP stockP = new StockP();
                stockP.setSTOCK_CODE(cursor.getString(0));
                stockP.setSTOCK_NOM(cursor.getString(1));
                stockP.setSTOCK_DATE(cursor.getString(2));
                stockP.setCLIENT_CODE(cursor.getString(3));
                stockP.setDATE_CREATION(cursor.getString(4));
                stockP.setDESCRIPTION(cursor.getString(5));
                stockP.setSTATUT_CODE(cursor.getString(6));
                stockP.setTYPE_CODE(cursor.getString(7));
                stockP.setCATEGORIE_CODE(cursor.getString(8));
                stockP.setCREATEUR_CODE(cursor.getString(9));
                stockP.setTS(cursor.getString(10));
                stockP.setCOMMENTAIRE(cursor.getString(11));
                stockP.setVERSION(cursor.getString(12));
                stockPS.add(stockP);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Liste StockP from Sqlite: "+stockPS.size());
        return stockPS;
    }

    public ArrayList<StockP> getListNotInserted() {
        ArrayList<StockP> stockPS = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_STOCKP +" WHERE "+KEY_COMMENTAIRE+" = 'to_insert' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                StockP stockP = new StockP();
                stockP.setSTOCK_CODE(cursor.getString(0));
                stockP.setSTOCK_NOM(cursor.getString(1));
                stockP.setSTOCK_DATE(cursor.getString(2));
                stockP.setCLIENT_CODE(cursor.getString(3));
                stockP.setDATE_CREATION(cursor.getString(4));
                stockP.setDESCRIPTION(cursor.getString(5));
                stockP.setSTATUT_CODE(cursor.getString(6));
                stockP.setTYPE_CODE(cursor.getString(7));
                stockP.setCATEGORIE_CODE(cursor.getString(8));
                stockP.setCREATEUR_CODE(cursor.getString(9));
                stockP.setTS(cursor.getString(10));
                stockP.setCOMMENTAIRE(cursor.getString(11));
                stockP.setVERSION(cursor.getString(12));
                stockPS.add(stockP);

                Log.d(TAG, "getListNotInserted: "+stockP.toString());
                stockPS.add(stockP);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching stockP from table stockP: ");
        return stockPS;
    }

    public int delete(String STOCK_CODE,String VERSION) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_STOCKP,KEY_STOCK_CODE+"=? AND "+KEY_VERSION +" =?",new String[]{STOCK_CODE,VERSION});
        return result;

    }

    public void updateStockP(String stockCode,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_STOCKP + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_STOCK_CODE +"= '"+stockCode+"'" ;
        db.execSQL(req);
        db.close();

        Log.d("visibilite", "updateVisibilite: "+req);
    }

    public void deleteStockPSynchronisee() {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefCmdAS = sdf.format(new Date());
        Log.d(TAG, "deleteStockPLigneSynchronisee: "+DatefCmdAS);

        String Where = " "+KEY_COMMENTAIRE+"='inserted' and "+KEY_STOCK_CODE+" NOT IN (SELECT stockp.STOCK_CODE FROM stockp)";
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_STOCKP, Where, null);
        db.close();
        Log.d("StockP", "Deleted all StockP verifiee from sqlite");
        Log.d("StockP", "deleteStockPSynchronisee: "+Where);
    }

    //SYNCHRONISATION PUSH
    public static void synchronisationStockP(final Context context){

        StockPManager stockPManager = new StockPManager(context);
        stockPManager.deleteStockPSynchronisee();

        String tag_string_req = "STOCKP";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_STOCKP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                Log.d(TAG, "onResponse: "+response);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray stockPs = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre de stockPs  "+stockPs.length() , Toast.LENGTH_LONG).show();
                        // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les StockPs : " +response);

                        if(stockPs.length()>0) {
                            StockPManager stockPManager = new StockPManager(context);
                            for (int i = 0; i < stockPs.length(); i++) {
                                JSONObject uneStockP = stockPs.getJSONObject(i);
                                if (uneStockP.getString("Statut").equals("true")) {
                                    stockPManager.updateStockP((uneStockP.getString("STOCK_CODE")),"inserted");
                                    cptInsert++;
                                }else{
                                    cptDelete++;
                                }
                            }
                        }
                        //logM.add("StockP:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncStockP");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STOCKP: Inserted: "+cptInsert +" NotInserted: "+cptDelete ,"STOCKP",1));

                        }


                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "stockP : "+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STOCKP: Error: "+errorMsg ,"STOCKP",0));

                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "stockP : "+"Json error: " +"erreur applcation stockP" + e.getMessage(), Toast.LENGTH_LONG).show();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STOCKP: Error: "+e.getMessage() ,"STOCKP",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "stockP : "+error.getMessage(), Toast.LENGTH_LONG).show();
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STOCKP: Error: "+error.getMessage() ,"STOCKP",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    StockPManager stockPManager  = new StockPManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableArticle");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(stockPManager.getListNotInserted()));
                    Log.d("StockPsynch", "Liste: "+stockPManager.getListNotInserted());
                    Log.d(TAG, "getParams: listnotinserted"+stockPManager.getListNotInserted().size());
                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    

    /*//SYNCHRONISATION STOCKP
    public static void synchronisationStockP(final Context context){

        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_STOCKP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("stockp", "onResponse: "+response);

                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    Log.d("stockp response",response);
                    JSONObject jObj = new JSONObject(response);

                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray StockPs = jObj.getJSONArray("StockPs");
                        Toast.makeText(context, "nombre de stockp  "+StockPs.length()  , Toast.LENGTH_SHORT).show();
                        if(StockPs.length()>0) {
                            StockPManager stockPManager = new StockPManager(context);
                            for (int i = 0; i < StockPs.length(); i++) {
                                JSONObject stockP = StockPs.getJSONObject(i);

                                if (stockP.getString("OPERATION").equals("DELETE")) {
                                    stockPManager.delete(stockP.getString("STOCK_CODE"),stockP.getString("VERSION"));
                                    cptDelete++;
                                } else {
                                    stockPManager.add(new StockP(stockP));
                                    cptInsert++;
                                }
                            }

                        }
                        logM.add("StockP:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncStockP");
                    }else {
                        String errorMsg = jObj.getString("info");
                        Toast.makeText(context,
                                "StockP : "+errorMsg, Toast.LENGTH_LONG).show();
                        logM.add("StockP:NOK "+errorMsg ,"SyncStockP");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "StockP : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    logM.add("StockP:NOK "+e.getMessage() ,"SyncStockP");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        "StockP : "+error.getMessage(), Toast.LENGTH_LONG).show();
                LogSyncManager logM= new LogSyncManager(context);
                logM.add("StockP:NOK "+error.getMessage() ,"SyncStockP");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //Map<String, String> params = new HashMap<String, String>();
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    StockPManager stockPManager  = new StockPManager(context);
                    List<StockP> stockPS = stockPManager.getList();

                    Log.d(TAG, "getParams: stockP"+stockPS);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    //Log.d("UC STOCKP SYNC",gson.toJson(pref.getString("UTILISATEUR_CODE",null)));

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(stockPS));

                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }*/
}
