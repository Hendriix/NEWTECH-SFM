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
import com.newtech.newtech_sfm.Metier.Stock;
import com.newtech.newtech_sfm.Metier.StockLigne;
import com.newtech.newtech_sfm.Metier.StockTransfert;
import com.newtech.newtech_sfm.Metier.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_STOCK = "stock";

    public static final String
            KEY_STOCK_CODE = "STOCK_CODE",
            KEY_STOCK_NOM = "STOCK_NOM",
            KEY_CLIENT_CODE = "CLIENT_CODE",
            KEY_DATE_CREATION = "DATE_CREATION",
            KEY_DESCRIPTION = "DESCRIPTION",
            KEY_STATUT_CODE = "STATUT_CODE",
            KEY_TYPE_CODE = "TYPE_CODE",
            KEY_CATEGORIE_CODE = "CATEGORIE_CODE",
            KEY_COMMENTAIRE = "COMMENTAIRE",
            KEY_GERABLE = "GERABLE",
            KEY_VERSION = "VERSION";

    public static String CREATE_STOCK_TABLE = "CREATE TABLE " + TABLE_STOCK+ " ("

            +KEY_STOCK_CODE +" TEXT, "
            +KEY_STOCK_NOM +" TEXT, "
            +KEY_CLIENT_CODE +" TEXT, "
            +KEY_DATE_CREATION +" TEXT, "
            +KEY_DESCRIPTION +" TEXT, "
            +KEY_STATUT_CODE +" TEXT, "
            +KEY_TYPE_CODE +" TEXT, "
            +KEY_CATEGORIE_CODE +" TEXT, "
            +KEY_COMMENTAIRE +" TEXT, "
            +KEY_GERABLE +" NUMERIC, "
            +KEY_VERSION +" TEXT" + ")";

    public StockManager(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            db.execSQL(CREATE_STOCK_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database STOCK tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCK);
        // Create tables again
        onCreate(db);
    }

    public void add(Stock stock) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_STOCK_CODE,stock.getSTOCK_CODE());
        values.put(KEY_STOCK_NOM,stock.getSTOCK_NOM());
        values.put(KEY_CLIENT_CODE,stock.getCLIENT_CODE());
        values.put(KEY_DATE_CREATION,stock.getDATE_CREATION());
        values.put(KEY_DESCRIPTION,stock.getDESCRIPTION());
        values.put(KEY_STATUT_CODE,stock.getSTOCK_CODE());
        values.put(KEY_TYPE_CODE,stock.getTYPE_CODE());
        values.put(KEY_CATEGORIE_CODE,stock.getCATEGORIE_CODE());
        values.put(KEY_COMMENTAIRE,stock.getCOMMENTAIRE());
        values.put(KEY_GERABLE,stock.getGERABLE());
        values.put(KEY_VERSION,stock.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_STOCK, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d("STOCK MANAGER", "New stock inserted into sqlite: " + id);

    }

    public Stock get(String STOCK_CODE) {

        Stock stock = new Stock();
        String selectQuery = "SELECT * FROM " + TABLE_STOCK+ " WHERE "+ KEY_STATUT_CODE +" = '"+STOCK_CODE+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {

            stock.setSTOCK_CODE(cursor.getString(0));
            stock.setSTOCK_NOM(cursor.getString(1));
            stock.setCLIENT_CODE(cursor.getString(2));
            stock.setDATE_CREATION(cursor.getString(3));
            stock.setDESCRIPTION(cursor.getString(4));
            stock.setSTATUT_CODE(cursor.getString(5));
            stock.setTYPE_CODE(cursor.getString(6));
            stock.setCATEGORIE_CODE(cursor.getString(7));
            stock.setCOMMENTAIRE(cursor.getString(8));
            stock.setGERABLE(cursor.getInt(9));
            stock.setVERSION(cursor.getString(10));
        }

        cursor.close();
        db.close();
        Log.d("STOCK MANAGER", "fetching ");
        return stock;

    }

    public Stock getByUserCode(String UTILISATEUR_CODE) {

        Stock stock = new Stock();
        String selectQuery = "SELECT * FROM " + TABLE_STOCK+ " WHERE "+ KEY_CLIENT_CODE +" = '"+UTILISATEUR_CODE+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {

            stock.setSTOCK_CODE(cursor.getString(0));
            stock.setSTOCK_NOM(cursor.getString(1));
            stock.setCLIENT_CODE(cursor.getString(2));
            stock.setDATE_CREATION(cursor.getString(3));
            stock.setDESCRIPTION(cursor.getString(4));
            stock.setSTATUT_CODE(cursor.getString(5));
            stock.setTYPE_CODE(cursor.getString(6));
            stock.setCATEGORIE_CODE(cursor.getString(7));
            stock.setCOMMENTAIRE(cursor.getString(8));
            stock.setGERABLE(cursor.getInt(9));
            stock.setVERSION(cursor.getString(10));
        }

        cursor.close();
        db.close();
        Log.d("STOCK MANAGER", "fetching ");
        return stock;

    }

    public ArrayList<Stock> getList() {
        ArrayList<Stock> stocks = new ArrayList<Stock>();

        String selectQuery = "SELECT * FROM " + TABLE_STOCK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Stock stock = new Stock();
                stock.setSTOCK_CODE(cursor.getString(0));
                stock.setSTOCK_NOM(cursor.getString(1));
                stock.setCLIENT_CODE(cursor.getString(2));
                stock.setDATE_CREATION(cursor.getString(3));
                stock.setDESCRIPTION(cursor.getString(4));
                stock.setSTATUT_CODE(cursor.getString(5));
                stock.setTYPE_CODE(cursor.getString(6));
                stock.setCATEGORIE_CODE(cursor.getString(7));
                stock.setCOMMENTAIRE(cursor.getString(8));
                stock.setGERABLE(cursor.getInt(9));
                stock.setVERSION(cursor.getString(10));
                stocks.add(stock);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version Stock from Sqlite: "+stocks.size());
        return stocks;
    }

    public int delete(String stock_code,String version) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_STOCK,KEY_STOCK_CODE+"=? AND "+KEY_VERSION+"=?",new String[]{stock_code,version});
    }


    public void updateStock(Context context){

        StockTransfertManager stockTransfertManager = new StockTransfertManager(context);
        StockLigneManager stockLigneManager = new StockLigneManager(context);

        ArrayList<StockTransfert> stockTransferts;
        stockTransferts = stockTransfertManager.getListNonTransferes();

        Log.d(TAG, "updateStock: stockstransferts "+stockTransferts);

        for(int i=0;i<stockTransferts.size();i++){

            StockLigne stockLigne;


            String STOCKTRANSFERT_CODE = stockTransferts.get(i).getSTOCKTRANSFERT_CODE();
            String ARTICLE_CODE = stockTransferts.get(i).getARTICLE_CODE();
            String UNITE_CODE = stockTransferts.get(i).getUNITE_CODE();
            double QTE_TRANSFERT = stockTransferts.get(i).getQTE();

            double QTE_SL = 0;
            double SUM = 0;
            int updateStockTransfertResult = 0;

            stockLigne = stockLigneManager.getByAcUc(ARTICLE_CODE,UNITE_CODE);

            Log.d(TAG, "updateStock: stock ligne: "+stockLigne.toString());

            if(stockLigne.getARTICLE_CODE() == null || stockLigne.getUNITE_CODE() == null){

                //CREATION D UNE NOUVELLE STOCK LIGNE
                try {
                    StockLigne stockLigne1 = new StockLigne(stockTransferts.get(i),context);
                    stockLigneManager.add(stockLigne1);

                    Log.d(TAG, "updateStock: stockligne1 "+i+" "+stockLigne1);
                    stockTransfertManager.updateStatutStockTransfert(STOCKTRANSFERT_CODE,"20");

                    Log.d(TAG, "updateStock: stockupdate add "+updateStockTransfertResult);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }else{

                //MISE A JOUR DE LA LIGNE EXISTANTE
                QTE_SL = stockLigne.getQTE();
                SUM = QTE_TRANSFERT + QTE_SL;
                updateStockTransfertResult = stockLigneManager.updateStockLigneQte(ARTICLE_CODE,UNITE_CODE,SUM);
                Log.d(TAG, "updateStock: update "+updateStockTransfertResult);

                if(updateStockTransfertResult == 1){

                    stockTransfertManager.updateStatutStockTransfert(STOCKTRANSFERT_CODE,"20");
                    Log.d(TAG, "updateStock: stockupdate update "+updateStockTransfertResult);

                }else{
                    Log.d(TAG, "updateStock: stockupdate update error "+updateStockTransfertResult);
                    stockLigneManager.updateStockLigneQte(ARTICLE_CODE,UNITE_CODE,QTE_SL);
                }
            }

        }
    }

    public boolean checkGerable(String UTILISATEUR_CODE,Context context){

        UserManager userManager = new UserManager(context);
        User user = userManager.get(UTILISATEUR_CODE);
        boolean result = true;


        StockManager stockManager = new StockManager(context);
        Stock stock = stockManager.getByUserCode(UTILISATEUR_CODE);

        if(user.getPROFILE_CODE().equals("PF0010") || user.getPROFILE_CODE().equals("PF0001")){

            if(stock.getSTOCK_CODE()== "" || stock.getSTOCK_CODE()==null){
                result = false;

            }else{

                if(stock.getGERABLE()==0){
                    result = false;
                }
            }

            Log.d(TAG, "checkGerable: "+user.getPROFILE_CODE());
        }else{
            result=false;
        }

        return result;

    }

    public static void synchronisationStock(final Context context){

        String tag_string_req = "STOCK";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_STOCK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("stock", " receive onResponse: "+response);

                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    Log.d("stock response",response);
                    JSONObject jObj = new JSONObject(response);

                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray Stocks = jObj.getJSONArray("Stocks");
                        //Toast.makeText(context, "nombre de stock  "+Stocks.length()  , Toast.LENGTH_SHORT).show();
                        if(Stocks.length()>0) {
                            StockManager stockManager = new StockManager(context);
                            for (int i = 0; i < Stocks.length(); i++) {
                                JSONObject stock = Stocks.getJSONObject(i);
                                Log.d(TAG, "onResponse: json "+stock);
                                if (stock.getString("OPERATION").equals("DELETE")) {
                                    stockManager.delete(stock.getString("STOCK_CODE"),stock.getString("VERSION"));
                                    cptDelete++;
                                } else {
                                    stockManager.add(new Stock(stock));
                                    cptInsert++;
                                }
                            }

                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STOCK: Inserted: "+cptInsert +" Deleted: "+cptDelete ,"STOCK",1));

                        }

                        //logM.add("Stock:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncStock");
                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "Stock : "+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("Stock:NOK "+errorMsg ,"SyncStock");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STOCK: Error: "+errorMsg ,"STOCK",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "Stock : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("Stock:NOK "+e.getMessage() ,"SyncStock");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STOCK: Error: "+e.getMessage() ,"STOCK",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Stock : "+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("Stock:NOK "+error.getMessage() ,"SyncStock");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STOCK: Error: "+error.getMessage() ,"STOCK",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //Map<String, String> params = new HashMap<String, String>();
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    StockManager stockManager  = new StockManager(context);
                    List<Stock> stockS = stockManager.getList();

                    Log.d(TAG, "getParams: sent stock"+stockS);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    //Log.d("UC STOCKP SYNC",gson.toJson(pref.getString("UTILISATEUR_CODE",null)));

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(stockS));

                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
