package com.newtech.newtech_sfm.Metier_Manager;

import static java.lang.StrictMath.abs;

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
import com.newtech.newtech_sfm.Metier.StockDemande;
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
 * Created by csaylani on 19/04/2018.
 */

public class StockDemandeManager extends SQLiteOpenHelper{

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_STOCKDEMANDE = "stockdemande";


    public static final String
            KEY_DEMANDE_CODE ="DEMANDE_CODE",
            KEY_DEMANDE_DATE ="DEMANDE_DATE",
            KEY_DISTRIBUTEUR_CODE="DISTRIBUTEUR_CODE",
            KEY_UTILISATEUR_CODE ="UTILISATEUR_CODE",
            KEY_TYPE_CODE ="TYPE_CODE",
            KEY_STATUT_CODE ="STATUT_CODE",
            KEY_CATEGORIE_CODE ="CATEGORIE_CODE",
            KEY_CREATEUR_CODE ="CREATEUR_CODE",
            KEY_DATE_CREATION ="DATE_CREATION",
            KEY_COMMENTAIRE ="COMMENTAIRE",
            KEY_VERSION="VERSION";


    public static String CREATE_STOCKDEMANDE_TABLE = "CREATE TABLE " + TABLE_STOCKDEMANDE+ " ("

            +KEY_DEMANDE_CODE + " TEXT PRIMARY KEY,"
            +KEY_DEMANDE_DATE + " TEXT,"
            +KEY_DISTRIBUTEUR_CODE + " TEXT,"
            +KEY_UTILISATEUR_CODE + " TEXT,"
            +KEY_TYPE_CODE + " TEXT,"
            +KEY_STATUT_CODE + " TEXT,"
            +KEY_CATEGORIE_CODE + " TEXT,"
            +KEY_CREATEUR_CODE + " TEXT,"
            +KEY_DATE_CREATION + " TEXT,"
            +KEY_COMMENTAIRE + " TEXT,"
            +KEY_VERSION + " TEXT " + ")";


    public StockDemandeManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAMILLE);
            db.execSQL(CREATE_STOCKDEMANDE_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database STOCKDEMANDE tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCKDEMANDE);
        // Create tables again
        onCreate(db);
    }

    public void add(StockDemande stockDemande) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_DEMANDE_CODE,stockDemande.getDEMANDE_CODE());
        values.put(KEY_DEMANDE_DATE,stockDemande.getDEMANDE_DATE());
        values.put(KEY_DISTRIBUTEUR_CODE,stockDemande.getDISTRIBUTEUR_CODE());
        values.put(KEY_UTILISATEUR_CODE,stockDemande.getUTILISATEUR_CODE());
        values.put(KEY_TYPE_CODE,stockDemande.getTYPE_CODE());
        values.put(KEY_STATUT_CODE,stockDemande.getSTATUT_CODE());
        values.put(KEY_CATEGORIE_CODE,stockDemande.getCATEGORIE_CODE());
        values.put(KEY_CREATEUR_CODE,stockDemande.getCREATEUR_CODE());
        values.put(KEY_DATE_CREATION,stockDemande.getDATE_CREATION());
        values.put(KEY_COMMENTAIRE,stockDemande.getCOMMENTAIRE());
        values.put(KEY_VERSION,stockDemande.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_STOCKDEMANDE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d("STOCKDEMANDE MANAGER", "New stockdemande inserted into sqlite: " + id);

    }

    public StockDemande get(String DEMANDE_CODE) {

        StockDemande stockDemande = new StockDemande();
        String selectQuery = "SELECT * FROM " + TABLE_STOCKDEMANDE+ " WHERE "+ KEY_DEMANDE_CODE +" = '"+DEMANDE_CODE+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            stockDemande.setDEMANDE_CODE(cursor.getString(0));
            stockDemande.setDEMANDE_DATE(cursor.getString(1));
            stockDemande.setDISTRIBUTEUR_CODE(cursor.getString(2));
            stockDemande.setUTILISATEUR_CODE(cursor.getString(3));
            stockDemande.setTYPE_CODE(cursor.getString(4));
            stockDemande.setSTATUT_CODE(cursor.getString(5));
            stockDemande.setCATEGORIE_CODE(cursor.getString(6));
            stockDemande.setCREATEUR_CODE(cursor.getString(7));
            stockDemande.setDATE_CREATION(cursor.getString(8));
            stockDemande.setCOMMENTAIRE(cursor.getString(9));
            stockDemande.setVERSION(cursor.getString(10));
        }

        cursor.close();
        db.close();
        Log.d("STOCKDEMANDE MANAGER", "fetching ");
        return stockDemande;

    }


    public ArrayList<StockDemande> getList() {
        ArrayList<StockDemande> stockDemandes = new ArrayList<StockDemande>();

        String selectQuery = "SELECT * FROM " + TABLE_STOCKDEMANDE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                StockDemande stockDemande = new StockDemande();
                stockDemande.setDEMANDE_CODE(cursor.getString(0));
                stockDemande.setDEMANDE_DATE(cursor.getString(1));
                stockDemande.setDISTRIBUTEUR_CODE(cursor.getString(2));
                stockDemande.setUTILISATEUR_CODE(cursor.getString(3));
                stockDemande.setTYPE_CODE(cursor.getString(4));
                stockDemande.setSTATUT_CODE(cursor.getString(5));
                stockDemande.setCATEGORIE_CODE(cursor.getString(6));
                stockDemande.setCREATEUR_CODE(cursor.getString(7));
                stockDemande.setDATE_CREATION(cursor.getString(8));
                stockDemande.setCOMMENTAIRE(cursor.getString(9));
                stockDemande.setVERSION(cursor.getString(10));
                stockDemandes.add(stockDemande);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version StockDemande from Sqlite: "+stockDemandes.size());
        return stockDemandes;
    }

    public ArrayList<StockDemande> getListByTypeDemande(String type_demande) {
        ArrayList<StockDemande> stockDemandes = new ArrayList<StockDemande>();

        String selectQuery = "SELECT * FROM " + TABLE_STOCKDEMANDE +" WHERE "+KEY_TYPE_CODE+" = '"+type_demande+"' ORDER BY "+KEY_DEMANDE_CODE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                StockDemande stockDemande = new StockDemande();
                stockDemande.setDEMANDE_CODE(cursor.getString(0));
                stockDemande.setDEMANDE_DATE(cursor.getString(1));
                stockDemande.setDISTRIBUTEUR_CODE(cursor.getString(2));
                stockDemande.setUTILISATEUR_CODE(cursor.getString(3));
                stockDemande.setTYPE_CODE(cursor.getString(4));
                stockDemande.setSTATUT_CODE(cursor.getString(5));
                stockDemande.setCATEGORIE_CODE(cursor.getString(6));
                stockDemande.setCREATEUR_CODE(cursor.getString(7));
                stockDemande.setDATE_CREATION(cursor.getString(8));
                stockDemande.setCOMMENTAIRE(cursor.getString(9));
                stockDemande.setVERSION(cursor.getString(10));
                stockDemandes.add(stockDemande);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version StockDemande from Sqlite: "+stockDemandes.size());
        return stockDemandes;
    }

    public ArrayList<StockDemande> getListNotInserted() {
        ArrayList<StockDemande> stockDemandes = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_STOCKDEMANDE +" WHERE "+KEY_COMMENTAIRE+" = 'to_insert' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                StockDemande stockDemande = new StockDemande();

                stockDemande.setDEMANDE_CODE(cursor.getString(0));
                stockDemande.setDEMANDE_DATE(cursor.getString(1));
                stockDemande.setDISTRIBUTEUR_CODE(cursor.getString(2));
                stockDemande.setUTILISATEUR_CODE(cursor.getString(3));
                stockDemande.setTYPE_CODE(cursor.getString(4));
                stockDemande.setSTATUT_CODE(cursor.getString(5));
                stockDemande.setCATEGORIE_CODE(cursor.getString(6));
                stockDemande.setCREATEUR_CODE(cursor.getString(7));
                stockDemande.setDATE_CREATION(cursor.getString(8));
                stockDemande.setCOMMENTAIRE(cursor.getString(9));
                stockDemande.setVERSION(cursor.getString(10));


                Log.d(TAG, "getListNotInserted: "+stockDemandes.toString());
                stockDemandes.add(stockDemande);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Stockdemande from table StockDemande: ");
        Log.d(TAG, "Nombre StockDemande from table stockdemande: "+stockDemandes.size());
        return stockDemandes;
    }

    public ArrayList<StockDemande> getListInserted() {
        ArrayList<StockDemande> stockDemandes = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_STOCKDEMANDE +" WHERE "+KEY_COMMENTAIRE+" = 'inserted' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                StockDemande stockDemande = new StockDemande();

                stockDemande.setDEMANDE_CODE(cursor.getString(0));
                stockDemande.setDEMANDE_DATE(cursor.getString(1));
                stockDemande.setDISTRIBUTEUR_CODE(cursor.getString(2));
                stockDemande.setUTILISATEUR_CODE(cursor.getString(3));
                stockDemande.setTYPE_CODE(cursor.getString(4));
                stockDemande.setSTATUT_CODE(cursor.getString(5));
                stockDemande.setCATEGORIE_CODE(cursor.getString(6));
                stockDemande.setCREATEUR_CODE(cursor.getString(7));
                stockDemande.setDATE_CREATION(cursor.getString(8));
                stockDemande.setCOMMENTAIRE(cursor.getString(9));
                stockDemande.setVERSION(cursor.getString(10));


                Log.d(TAG, "getListInserted: "+stockDemandes.toString());
                stockDemandes.add(stockDemande);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Stockdemande from table StockDemande: ");
        Log.d(TAG, "Nombre StockDemande from table stockdemande: "+stockDemandes.size());
        return stockDemandes;
    }

    public ArrayList<StockDemande> getListReceptionnee() {
        ArrayList<StockDemande> stockDemandes = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_STOCKDEMANDE +" WHERE "+KEY_STATUT_CODE+" = '33' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                StockDemande stockDemande = new StockDemande();

                stockDemande.setDEMANDE_CODE(cursor.getString(0));
                stockDemande.setDEMANDE_DATE(cursor.getString(1));
                stockDemande.setDISTRIBUTEUR_CODE(cursor.getString(2));
                stockDemande.setUTILISATEUR_CODE(cursor.getString(3));
                stockDemande.setTYPE_CODE(cursor.getString(4));
                stockDemande.setSTATUT_CODE(cursor.getString(5));
                stockDemande.setCATEGORIE_CODE(cursor.getString(6));
                stockDemande.setCREATEUR_CODE(cursor.getString(7));
                stockDemande.setDATE_CREATION(cursor.getString(8));
                stockDemande.setCOMMENTAIRE(cursor.getString(9));
                stockDemande.setVERSION(cursor.getString(10));


                Log.d(TAG, "getListInserted: "+stockDemandes.toString());
                stockDemandes.add(stockDemande);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Stockdemande from table StockDemande: ");
        Log.d(TAG, "Nombre StockDemande from table stockdemande: "+stockDemandes.size());
        return stockDemandes;
    }

    public int delete(String stockdemande_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_STOCKDEMANDE,KEY_DEMANDE_CODE+"=?",new String[]{stockdemande_code});
        return result;

    }

    public int deletePull(String stockdemande_code, String version) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_STOCKDEMANDE,KEY_DEMANDE_CODE+"=? AND "+KEY_COMMENTAIRE+"=? AND "+KEY_VERSION+"=?",new String[]{stockdemande_code,"inserted",version});
        return result;

    }

    public void updateStockDemande(String stockdemande_code,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_STOCKDEMANDE + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_DEMANDE_CODE +"= '"+stockdemande_code+"'" ;
        db.execSQL(req);
        db.close();
        Log.d("stockdemande", "updateStockDemande: "+req);
    }

    public void updateStockDemandeStatut(String stockdemande_code,String statut_code){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_STOCKDEMANDE + " SET "+KEY_STATUT_CODE  +"= '"+statut_code+"' WHERE "+KEY_DEMANDE_CODE +"= '"+stockdemande_code+"'" ;
        db.execSQL(req);
        db.close();
        Log.d("stockdemande", "updateStockDemande: "+req);
    }

    public void updateStockDemandeStatut(StockDemande stockDemande, Context context){

        double QTE_LIVREE = 0;
        double QTE_RECEPTIONNEE = 0;

        StockDemandeLigneManager stockDemandeLigneManager = new StockDemandeLigneManager(context);
        ArrayList<StockDemandeLigne> stockDemandeLignes;

        String DEMANDE_CODE = stockDemande.getDEMANDE_CODE();

        stockDemandeLignes = stockDemandeLigneManager.getListByDemandeCode(DEMANDE_CODE);

        for (StockDemandeLigne stockDemandeLigne : stockDemandeLignes
             ) {
            Log.d(TAG, "updateStckDemandeStatut: LIV be"+stockDemandeLigne.getQTE_LIVREE());
            Log.d(TAG, "updateStckDemandeStatut: REC be"+stockDemandeLigne.getQTE_RECEPTIONEE());
        }

        Log.d(TAG, "updateStckDemandeStatut: LIVREE be"+QTE_LIVREE);
        Log.d(TAG, "updateStckDemandeStatut: RECETIONNEE be"+QTE_RECEPTIONNEE);

        String selectQuery = "SELECT SUM(stockdemandeligne.QTE_LIVREE) as SQTEL, " +
                                    "SUM(stockdemandeligne.QTE_RECEPTIONEE) as SQTER " +
                                    "from stockdemandeligne " +
                                    "where stockdemandeligne.DEMANDE_CODE "+"='"+DEMANDE_CODE+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                QTE_LIVREE += cursor.getDouble(0);
                QTE_RECEPTIONNEE += cursor.getDouble(1);

            }while(cursor.moveToNext());
        }

        QTE_LIVREE = abs(QTE_LIVREE);
        QTE_RECEPTIONNEE = abs(QTE_RECEPTIONNEE);

        cursor.close();
        db.close();

        Log.d(TAG, "updateStckDemandeStatut: LIVREE after"+QTE_LIVREE);
        Log.d(TAG, "updateStckDemandeStatut: RECETIONNEE after"+QTE_RECEPTIONNEE);

        if(QTE_LIVREE>0){

            if(QTE_LIVREE == QTE_RECEPTIONNEE){
                SQLiteDatabase db1 = this.getWritableDatabase();
                String req = "UPDATE " +TABLE_STOCKDEMANDE + " SET "+KEY_STATUT_CODE  +"= '"+"33"+"' WHERE "+KEY_DEMANDE_CODE +"= '"+DEMANDE_CODE+"'" ;
                db1.execSQL(req);
                db1.close();
            }
        }

        stockDemandeLignes = stockDemandeLigneManager.getListByDemandeCode(DEMANDE_CODE);

        for (StockDemandeLigne stockDemandeLigne : stockDemandeLignes) {
            Log.d(TAG, "updateStckDemandeStatut: LIV after"+stockDemandeLigne.getQTE_LIVREE());
            Log.d(TAG, "updateStckDemandeStatut: REC after"+stockDemandeLigne.getQTE_RECEPTIONEE());
        }



    }

   /* public void deleteStockDemandeSynchronisee() {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefCmdAS = sdf.format(new Date());
        Log.d(TAG, "deleteCmdSynchronisee: "+DatefCmdAS);

        String Where = " "+KEY_COMMENTAIRE+"='inserted' and date("+KEY_DATE_CREATION+")!='"+DatefCmdAS+"' and "+KEY_STATUT_CODE +"= 'CLOTUREE'";
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_STOCKDEMANDE, Where, null);
        db.close();
        Log.d("stockdemande", "Deleted all stockdemandes verifiee from sqlite");
        Log.d("stockdemande", "deletestockdemandeSynchronisee: "+Where);
    }*/

    public void deleteStockDemandeSynchronisee() {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefCmdAS = sdf.format(new Date());
        Log.d(TAG, "deleteCmdSynchronisee: "+DatefCmdAS);

        String Where = " "+KEY_COMMENTAIRE+"='inserted' and date("+KEY_DATE_CREATION+")!='"+DatefCmdAS+"'  AND "+KEY_STATUT_CODE+" = '34' ";
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_STOCKDEMANDE, Where, null);
        db.close();
        Log.d("stockdemande", "Deleted all stockdemandes verifiee from sqlite");
        Log.d("stockdemande", "deletestockdemandeSynchronisee: "+Where);
    }

    public static void synchronisationStockDemande(final Context context){

        StockDemandeManager stockDemandeManager = new StockDemandeManager(context);
        //stockDemandeManager.deleteStockDemandeSynchronisee();

        String tag_string_req = "STOCK_DEMANDE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_STOCKDEMANDE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    Log.d(TAG, "onResponse: Stockdemande"+ response);
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray stockdemandes = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre de stockdemande  "+stockdemandes.length() , Toast.LENGTH_LONG).show();
                        // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les stockdemandes : " +response);

                        if(stockdemandes.length()>0) {
                            StockDemandeManager stockDemandeManager = new StockDemandeManager(context);
                            for (int i = 0; i < stockdemandes.length(); i++) {
                                JSONObject unStockDemande = stockdemandes.getJSONObject(i);
                                if (unStockDemande.getString("Statut").equals("true")) {
                                    stockDemandeManager.updateStockDemande((unStockDemande.getString("DEMANDE_CODE")),"inserted");
                                    cptInsert++;
                                }else{
                                    cptDelete++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STOCK_DEMANDE: Inserted: "+cptInsert +" NotInserted: "+cptDelete ,"STOCK_DEMANDE",1));

                        }

                        //logM.add("StockDemande:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncStockDemande");


                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "StockDemande : "+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STOCK_DEMANDE: Error: "+errorMsg ,"STOCK_DEMANDE",0));

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STOCK_DEMANDE: Error: "+e.getMessage() ,"STOCK_DEMANDE",0));

                    }
                    //Toast.makeText(context, "StockDemande : "+"Json error: " +"erreur applcation StockDemande" + e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "stockdemande : "+error.getMessage(), Toast.LENGTH_LONG).show();
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("STOCK_DEMANDE: Error: "+error.getMessage() ,"STOCK_DEMANDE",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    StockDemandeManager stockDemandeManager  = new StockDemandeManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableStockDemande");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(stockDemandeManager.getListNotInserted()));

                    Log.d("StockDemandesynch", "Liste: "+stockDemandeManager.getListNotInserted());
                    Log.d(TAG, "getParams: listnotinserted "+stockDemandeManager.getListNotInserted().size());
                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public static void synchronisationStockDemandePull(final Context context){

        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_STOCKDEMANDE_PULL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("stockDemandepull", "onResponse: "+response);

                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    Log.d("stockDemandePull",response);
                    JSONObject jObj = new JSONObject(response);

                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray StockDemandes = jObj.getJSONArray("StockDemandesPull");
                        //Toast.makeText(context, "nombre de StockDemandePull  "+StockDemandes.length()  , Toast.LENGTH_SHORT).show();
                        if(StockDemandes.length()>0) {
                            StockDemandeManager stockDemandeManager = new StockDemandeManager(context);
                            for (int i = 0; i < StockDemandes.length(); i++) {
                                JSONObject stockDemande = StockDemandes.getJSONObject(i);

                                if (stockDemande.getString("OPERATION").equals("DELETE")) {

                                    stockDemandeManager.deletePull(stockDemande.getString("DEMANDE_CODE"),stockDemande.getString("VERSION"));
                                    cptDelete++;

                                } else {

                                    stockDemandeManager.add(new StockDemande(stockDemande));
                                    cptInsert++;
                                }
                            }

                        }
                        logM.add("StockDemandePull:OK Insert:"+cptInsert +"Delete:"+cptDelete ,"SyncStockDemandePull");
                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "StockDemandePull : "+errorMsg, Toast.LENGTH_LONG).show();
                        logM.add("StockDemandePull:NOK "+errorMsg ,"SyncStockDemandePull");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "StockDemandePull : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    logM.add("StockDemandePull:NOK "+e.getMessage() ,"SyncStockDemandePull");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"StockDemandePull : "+error.getMessage(), Toast.LENGTH_LONG).show();
                LogSyncManager logM= new LogSyncManager(context);
                logM.add("StockDemandePull:NOK "+error.getMessage() ,"SyncStockDemandePull");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //Map<String, String> params = new HashMap<String, String>();
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    StockDemandeManager stockDemandeManager  = new StockDemandeManager(context);
                    List<StockDemande> stockDemandes = stockDemandeManager.getListInserted();

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    Log.d(TAG, "getParams: stockDemandepull"+stockDemandeManager.getListInserted());

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(stockDemandes));


                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    public static void synchronisationStockDemandeReceptionnee(final Context context){

        StockDemandeManager stockDemandeManager = new StockDemandeManager(context);
        stockDemandeManager.deleteStockDemandeSynchronisee();

        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_STOCKDEMANDE_RECEPTIONNEE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    Log.d(TAG, "onResponse: StockdemandeReception"+ response);
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray stockdemandes = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre de stockdemandereception  "+stockdemandes.length() , Toast.LENGTH_LONG).show();
                        // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les stockdemandes : " +response);

                        if(stockdemandes.length()>0) {
                            StockDemandeManager stockDemandeManager = new StockDemandeManager(context);
                            for (int i = 0; i < stockdemandes.length(); i++) {
                                JSONObject unStockDemande = stockdemandes.getJSONObject(i);
                                if (unStockDemande.getString("Statut").equals("true")) {
                                    stockDemandeManager.updateStockDemandeStatut((unStockDemande.getString("DEMANDE_CODE")),"34");
                                }
                            }
                        }
                        logM.add("StockDemandeReception:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncStockDemande");


                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context,"StockDemande : "+errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "StockDemandeReception : "+"Json error: " +"erreur applcation StockDemande" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"stockdemande : "+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    StockDemandeManager stockDemandeManager  = new StockDemandeManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableStockDemande");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(stockDemandeManager.getListReceptionnee()));

                    Log.d("StockDemandesynch", "ListeReceptionnee : "+stockDemandeManager.getListReceptionnee());
                    Log.d(TAG, "getParams: listreceptionnee "+stockDemandeManager.getListReceptionnee().size());
                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    public String DemandeTitle(String commande_source){

        String titre = "DEMANDE";

        if(commande_source.equals("TP0024")){

            titre = "CHARGEMENT";

        }else if(commande_source.equals("TP0025")){

            titre = "DECHARGEMENT";
        }

        return titre;
    }
}
