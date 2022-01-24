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
import com.newtech.newtech_sfm.Configuration.AppConfig;
import com.newtech.newtech_sfm.Configuration.AppController;
import com.newtech.newtech_sfm.Configuration.SQLiteHandler;
import com.newtech.newtech_sfm.Metier.StockDemandeLigne;
import com.newtech.newtech_sfm.Metier.StockTransfert;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StockTransfertManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_STOCKTRANSFERT = "stocktransfert";

    public static final String
            KEY_STOCKTRANSFERT_CODE = "STOCKTRANSFERT_CODE",
            KEY_STOCKTRANSFERT_DATE = "STOCKTRANSFERT_DATE",
            KEY_ARTICLE_CODE = "ARTICLE_CODE",
            KEY_UNITE_CODE = "UNITE_CODE",
            KEY_QTE = "QTE",
            KEY_STOCK = "STOCK",
            KEY_STOCK_SOURCE = "STOCK_SOURCE",
            KEY_TYPE_CODE = "TYPE_CODE",
            KEY_STATUT_CODE = "STATUT_CODE",
            KEY_CATEGORIE_CODE = "CATEGORIE_CODE",
            KEY_SOURCE = "SOURCE",
            KEY_DATE_CREATION = "DATE_CREATION",
            KEY_CREATEUR_CODE = "CREATEUR_CODE",
            KEY_SOURCE_CODE = "SOURCE_CODE",
            KEY_COMMENTAIRE = "COMMENTAIRE",
            KEY_VERSION = "VERSION";

    public static String CREATE_STOCKTRANSFERT_TABLE = "CREATE TABLE " + TABLE_STOCKTRANSFERT + "("

            +KEY_STOCKTRANSFERT_CODE +" TEXT PRIMARY KEY,"
            +KEY_STOCKTRANSFERT_DATE +" TEXT,"
            +KEY_ARTICLE_CODE +" TEXT,"
            +KEY_UNITE_CODE +" TEXT,"
            +KEY_QTE +" NUMERIC,"
            +KEY_STOCK +" TEXT,"
            +KEY_STOCK_SOURCE +" TEXT,"
            +KEY_TYPE_CODE +" TEXT,"
            +KEY_STATUT_CODE +" TEXT,"
            +KEY_CATEGORIE_CODE +" TEXT,"
            +KEY_SOURCE +" TEXT,"
            +KEY_DATE_CREATION +" TEXT,"
            +KEY_CREATEUR_CODE +" TEXT,"
            +KEY_SOURCE_CODE +" TEXT,"
            +KEY_COMMENTAIRE +" TEXT,"
            +KEY_VERSION +" TEXT " + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAMILLE);
            db.execSQL(CREATE_STOCKTRANSFERT_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database STOCKTRANSFERT tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCKTRANSFERT);
        // Create tables again
        onCreate(db);
    }

    public StockTransfertManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void add(StockTransfert stockTransfert) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_STOCKTRANSFERT_CODE,stockTransfert.getSTOCKTRANSFERT_CODE());
        values.put(KEY_STOCKTRANSFERT_DATE,stockTransfert.getSTOCKTRANSFERT_DATE());
        values.put(KEY_ARTICLE_CODE,stockTransfert.getARTICLE_CODE());
        values.put(KEY_UNITE_CODE,stockTransfert.getUNITE_CODE());
        values.put(KEY_QTE,stockTransfert.getQTE());
        values.put(KEY_STOCK,stockTransfert.getSTOCK());
        values.put(KEY_STOCK_SOURCE,stockTransfert.getSOURCE());
        values.put(KEY_TYPE_CODE,stockTransfert.getTYPE_CODE());
        values.put(KEY_STATUT_CODE,stockTransfert.getSTATUT_CODE());
        values.put(KEY_CATEGORIE_CODE,stockTransfert.getCATEGORIE_CODE());
        values.put(KEY_SOURCE,stockTransfert.getSOURCE());
        values.put(KEY_DATE_CREATION,stockTransfert.getDATE_CREATION());
        values.put(KEY_CREATEUR_CODE,stockTransfert.getCREATEUR_CODE());
        values.put(KEY_SOURCE_CODE,stockTransfert.getSOURCE_CODE());
        values.put(KEY_COMMENTAIRE,stockTransfert.getCOMMENTAIRE());
        values.put(KEY_VERSION,stockTransfert.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_STOCKTRANSFERT, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d("stockTransfert MANAGER", "New stockTransfert inserted into sqlite: " + id);

    }

    public StockTransfert get(String STOCKTRANSFERT_CODE) {

        StockTransfert stockTransfert = new StockTransfert();
        String selectQuery = "SELECT * FROM " + TABLE_STOCKTRANSFERT+ " WHERE "+ KEY_STOCKTRANSFERT_CODE +" = '"+STOCKTRANSFERT_CODE+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {

            stockTransfert.setSTOCKTRANSFERT_CODE(cursor.getString(0));
            stockTransfert.setSTOCKTRANSFERT_DATE(cursor.getString(1));
            stockTransfert.setARTICLE_CODE(cursor.getString(2));
            stockTransfert.setUNITE_CODE(cursor.getString(3));
            stockTransfert.setQTE(cursor.getInt(4));
            stockTransfert.setSTOCK(cursor.getString(5));
            stockTransfert.setSTOCK_SOURCE(cursor.getString(6));
            stockTransfert.setTYPE_CODE(cursor.getString(7));
            stockTransfert.setSTATUT_CODE(cursor.getString(8));
            stockTransfert.setCATEGORIE_CODE(cursor.getString(9));
            stockTransfert.setSOURCE(cursor.getString(10));
            stockTransfert.setDATE_CREATION(cursor.getString(11));
            stockTransfert.setCREATEUR_CODE(cursor.getString(12));
            stockTransfert.setSOURCE_CODE(cursor.getString(13));
            stockTransfert.setCOMMENTAIRE(cursor.getString(14));
            stockTransfert.setVERSION(cursor.getString(15));
        }

        cursor.close();
        db.close();
        Log.d("STOCKTRANSFERT MANAGER", "fetching ");
        return stockTransfert;

    }

    public ArrayList<StockTransfert> getList() {
        ArrayList<StockTransfert> stockTransferts = new ArrayList<StockTransfert>();

        String selectQuery = "SELECT * FROM " + TABLE_STOCKTRANSFERT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {

                StockTransfert stockTransfert = new StockTransfert();
                stockTransfert.setSTOCKTRANSFERT_CODE(cursor.getString(0));
                stockTransfert.setSTOCKTRANSFERT_DATE(cursor.getString(1));
                stockTransfert.setARTICLE_CODE(cursor.getString(2));
                stockTransfert.setUNITE_CODE(cursor.getString(3));
                stockTransfert.setQTE(cursor.getInt(4));
                stockTransfert.setSTOCK(cursor.getString(5));
                stockTransfert.setSTOCK_SOURCE(cursor.getString(6));
                stockTransfert.setTYPE_CODE(cursor.getString(7));
                stockTransfert.setSTATUT_CODE(cursor.getString(8));
                stockTransfert.setCATEGORIE_CODE(cursor.getString(9));
                stockTransfert.setSOURCE(cursor.getString(10));
                stockTransfert.setDATE_CREATION(cursor.getString(11));
                stockTransfert.setCREATEUR_CODE(cursor.getString(12));
                stockTransfert.setSOURCE_CODE(cursor.getString(13));
                stockTransfert.setCOMMENTAIRE(cursor.getString(14));
                stockTransfert.setVERSION(cursor.getString(15));

                stockTransferts.add(stockTransfert);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Liste StockTransfert from Sqlite: "+stockTransferts.size());
        return stockTransferts;
    }

    public ArrayList<StockTransfert> getListNonTransferes() {
        ArrayList<StockTransfert> stockTransferts = new ArrayList<StockTransfert>();

        String selectQuery = "SELECT * FROM " + TABLE_STOCKTRANSFERT + " WHERE "+KEY_STATUT_CODE +" = '18' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {

                StockTransfert stockTransfert = new StockTransfert();
                stockTransfert.setSTOCKTRANSFERT_CODE(cursor.getString(0));
                stockTransfert.setSTOCKTRANSFERT_DATE(cursor.getString(1));
                stockTransfert.setARTICLE_CODE(cursor.getString(2));
                stockTransfert.setUNITE_CODE(cursor.getString(3));
                stockTransfert.setQTE(cursor.getInt(4));
                stockTransfert.setSTOCK(cursor.getString(5));
                stockTransfert.setSTOCK_SOURCE(cursor.getString(6));
                stockTransfert.setTYPE_CODE(cursor.getString(7));
                stockTransfert.setSTATUT_CODE(cursor.getString(8));
                stockTransfert.setCATEGORIE_CODE(cursor.getString(9));
                stockTransfert.setSOURCE(cursor.getString(10));
                stockTransfert.setDATE_CREATION(cursor.getString(11));
                stockTransfert.setCREATEUR_CODE(cursor.getString(12));
                stockTransfert.setSOURCE_CODE(cursor.getString(13));
                stockTransfert.setCOMMENTAIRE(cursor.getString(14));
                stockTransfert.setVERSION(cursor.getString(15));

                stockTransferts.add(stockTransfert);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Liste StockTransfert from Sqlite: "+stockTransferts.size());
        return stockTransferts;
    }

    public void stockTransfertsFromStockDemandeLigne(ArrayList<StockDemandeLigne> stockDemandeLignes){


    }

    public ArrayList<StockTransfert> getListNotInserted() {
        ArrayList<StockTransfert> stockTransferts = new ArrayList<StockTransfert>();

        String selectQuery = "SELECT  * FROM " + TABLE_STOCKTRANSFERT +" WHERE "+KEY_COMMENTAIRE+" = 'to_insert' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                StockTransfert stockTransfert = new StockTransfert();
                stockTransfert.setSTOCKTRANSFERT_CODE(cursor.getString(0));
                stockTransfert.setSTOCKTRANSFERT_DATE(cursor.getString(1));
                stockTransfert.setARTICLE_CODE(cursor.getString(2));
                stockTransfert.setUNITE_CODE(cursor.getString(3));
                stockTransfert.setQTE(cursor.getInt(4));
                stockTransfert.setSTOCK(cursor.getString(5));
                stockTransfert.setSTOCK_SOURCE(cursor.getString(6));
                stockTransfert.setTYPE_CODE(cursor.getString(7));
                stockTransfert.setSTATUT_CODE(cursor.getString(8));
                stockTransfert.setCATEGORIE_CODE(cursor.getString(9));
                stockTransfert.setSOURCE(cursor.getString(10));
                stockTransfert.setDATE_CREATION(cursor.getString(11));
                stockTransfert.setCREATEUR_CODE(cursor.getString(12));
                stockTransfert.setSOURCE_CODE(cursor.getString(13));
                stockTransfert.setCOMMENTAIRE(cursor.getString(14));
                stockTransfert.setVERSION(cursor.getString(15));
                stockTransferts.add(stockTransfert);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Liste StockTransfert from Sqlite: "+stockTransferts.size());
        return stockTransferts;
    }

    public int delete(String STOCKTRANSFERT_CODE,String VERSION) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_STOCKTRANSFERT,KEY_STOCKTRANSFERT_CODE+"=? AND "+KEY_VERSION +" =?",new String[]{STOCKTRANSFERT_CODE,VERSION});
        return result;

    }

    public int deleteByCmdCode(String commandeCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_STOCKTRANSFERT,KEY_SOURCE_CODE+"=?",new String[]{commandeCode});
    }

    public void deleteStockTransfertSynchronisee() {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefCmdAS = sdf.format(new Date());
        Log.d(TAG, "deleteStockTransfertSynchronisee: "+DatefCmdAS);

        String Where = " "+KEY_COMMENTAIRE+"='inserted' and date("+KEY_DATE_CREATION+")!='"+DatefCmdAS+"'";
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d(TAG, "deleteStockTransfertSynchronisee: "+Where);
        // Delete All Rows verified
        db.delete(TABLE_STOCKTRANSFERT, Where, null);
        db.close();
    }

    public void updateStockTransfert(String STOCKTRANSFERT_CODE,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_STOCKTRANSFERT + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_STOCKTRANSFERT_CODE +"= '"+STOCKTRANSFERT_CODE+"'" ;
        db.execSQL(req);
        db.close();

        Log.d("stockTransfert", "updateStockTransfert: "+req);
    }

    public void updateStatutStockTransfert(String STOCKTRANSFERT_CODE,String statut){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_STOCKTRANSFERT + " SET "+KEY_STATUT_CODE  +"= '"+statut+"' WHERE "+KEY_STOCKTRANSFERT_CODE +"= '"+STOCKTRANSFERT_CODE+"'" ;
        db.execSQL(req);
        db.close();

        Log.d(TAG, "updateStatutStockTransfert: statut updated to 20");
    }

    public static void synchronisationStockTransfert(final Context context){

        StockTransfertManager stockTransfertManager = new StockTransfertManager(context);
        stockTransfertManager.deleteStockTransfertSynchronisee();

        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_STOCKTRANSFERT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    Log.d(TAG, "onResponse: stocktransfert"+response);

                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray stockTransferts = jObj.getJSONArray("result");
                        Toast.makeText(context, "Nombre de stockTransferts  "+stockTransferts.length() , Toast.LENGTH_LONG).show();
                        // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les StockTransferts : " +response);

                        if(stockTransferts.length()>0) {
                            StockTransfertManager stockTransfertManager = new StockTransfertManager(context);
                            for (int i = 0; i < stockTransferts.length(); i++) {
                                JSONObject uneStockTransfert = stockTransferts.getJSONObject(i);
                                if (uneStockTransfert.getString("Statut").equals("true")) {
                                    stockTransfertManager.updateStockTransfert((uneStockTransfert.getString("COMMANDE_CODE")),"inserted");
                                }
                            }
                        }
                        logM.add("StockTransfert:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncStockTransfert");


                    }else {
                        String errorMsg = jObj.getString("info");
                        Toast.makeText(context,
                                "stockTransfert : "+errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "stockTransfert : "+"Json error: " +"erreur applcation stockTransfert" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"stockTransfert : "+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    StockTransfertManager stockTransfertManager  = new StockTransfertManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableArticle");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(stockTransfertManager.getListNotInserted()));
                    Log.d("StockTransfertsynch", "Liste: "+stockTransfertManager.getListNotInserted());
                    Log.d(TAG, "getParams: listnotinserted"+stockTransfertManager.getListNotInserted().size());
                }
                return arrayFinale;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
