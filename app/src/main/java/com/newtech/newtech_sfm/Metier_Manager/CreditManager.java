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
import com.newtech.newtech_sfm.Metier.Credit;
import com.newtech.newtech_sfm.Metier.LogSync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TONPC on 02/08/2017.
 */

public class CreditManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_CREDIT = "credit";


    private static final String
            KEY_CREDIT_CODE = "CREDIT_CODE",
            KEY_COMMANDE_CODE = "COMMANDE_CODE",
            KEY_CLIENT_CODE = "CLIENT_CODE",
            KEY_CREDIT_DATE = "CREDIT_DATE",
            KEY_CREDIT_ECHEANCE = "CREDIT_ECHEANCE",
            KEY_MONTANT_CREDIT = "MONTANT_CREDIT",
            KEY_MONTANT_ENCAISSE = "MONTANT_ENCAISSE",
            KEY_RESTE = "RESTE",
            KEY_TYPE_CODE = "TYPE_CODE",
            KEY_STATUT_CODE = "STATUT_CODE",
            KEY_CATEGORIE_CODE = "CATEGORIE_CODE",
            KEY_DATE_CREATION = "DATE_CREATION",
            KEY_CREATEUR_CODE = "CREATEUR_CODE",
            KEY_COMMENTAIRE = "COMMENTAIRE",
            KEY_VERSION = "VERSION",
            KEY_SOURCE = "SOURCE";

    public static String CREATE_CREDIT_TABLE="CREATE TABLE " + TABLE_CREDIT + "("
            +KEY_CREDIT_CODE + " TEXT PRIMARY KEY,"
            +KEY_COMMANDE_CODE + " TEXT,"
            +KEY_CLIENT_CODE + " TEXT,"
            +KEY_CREDIT_DATE + " TEXT,"
            +KEY_CREDIT_ECHEANCE + " TEXT,"
            +KEY_MONTANT_CREDIT + " FLOAT,"
            +KEY_MONTANT_ENCAISSE + " FLOAT,"
            +KEY_RESTE + " FLOAT,"
            +KEY_TYPE_CODE + " TEXT,"
            +KEY_STATUT_CODE + " TEXT,"
            +KEY_CATEGORIE_CODE + " TEXT,"
            +KEY_DATE_CREATION + " TEXT,"
            +KEY_CREATEUR_CODE + " TEXT,"
            +KEY_COMMENTAIRE + " TEXT,"
            +KEY_VERSION + " TEXT,"
            +KEY_SOURCE + " TEXT" + ")";


    public CreditManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE);
            db.execSQL(CREATE_CREDIT_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREDIT);
        onCreate(db);
    }

    public void add(Credit credit) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CREDIT_CODE , credit.getCREDIT_CODE());
        values.put(KEY_COMMANDE_CODE , credit.getCOMMANDE_CODE());
        values.put(KEY_CLIENT_CODE , credit.getCLIENT_CODE());
        values.put(KEY_CREDIT_DATE , credit.getCREDIT_DATE());
        values.put(KEY_CREDIT_ECHEANCE , credit.getCREDIT_ECHEANCE());
        values.put(KEY_MONTANT_CREDIT , credit.getMONTANT_CREDIT());
        values.put(KEY_MONTANT_ENCAISSE , credit.getMONTANT_ENCAISSE());
        values.put(KEY_RESTE , credit.getRESTE());
        values.put(KEY_TYPE_CODE , credit.getTYPE_CODE());
        values.put(KEY_STATUT_CODE , credit.getSTATUT_CODE());
        values.put(KEY_CATEGORIE_CODE , credit.getCATEGORIE_CODE());
        values.put(KEY_DATE_CREATION , credit.getDATE_CREATION());
        values.put(KEY_CREATEUR_CODE , credit.getCREATEUR_CODE());
        values.put(KEY_COMMENTAIRE , credit.getCOMMENTAIRE());
        values.put(KEY_VERSION , credit.getVERSION());
        values.put(KEY_SOURCE , credit.getSOURCE());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_CREDIT, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New credit inserted into sqlite: " + id);
    }

    public Credit get(String credit_code) {
        Credit credit= new Credit();

        String selectQuery = "SELECT  * FROM " + TABLE_CREDIT+" WHERE "+KEY_CREDIT_CODE+"='"+credit_code+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if( cursor != null && cursor.moveToFirst() ) {
            credit.setCREDIT_CODE(cursor.getString(0));
            credit.setCOMMANDE_CODE(cursor.getString(1));
            credit.setCLIENT_CODE(cursor.getString(2));
            credit.setCREDIT_DATE(cursor.getString(3));
            credit.setCREDIT_ECHEANCE(cursor.getString(4));
            credit.setMONTANT_CREDIT(cursor.getDouble(5));
            credit.setMONTANT_ENCAISSE(cursor.getDouble(6));
            credit.setRESTE(cursor.getDouble(7));
            credit.setTYPE_CODE(cursor.getString(8));
            credit.setSTATUT_CODE(cursor.getString(9));
            credit.setCATEGORIE_CODE(cursor.getString(10));
            credit.setDATE_CREATION(cursor.getString(11));
            credit.setCREATEUR_CODE(cursor.getString(12));
            credit.setCOMMENTAIRE(cursor.getString(13));
            credit.setVERSION(cursor.getString(14));
            credit.setSOURCE(cursor.getString(15));

        }
        //retourner un credit;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching encaissmenents from Sqlite: ");
        return credit;
    }

    public ArrayList<Credit> getList() {
        ArrayList<Credit> credits = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CREDIT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Credit credit= new Credit();
                credit.setCREDIT_CODE(cursor.getString(0));
                credit.setCOMMANDE_CODE(cursor.getString(1));
                credit.setCLIENT_CODE(cursor.getString(2));
                credit.setCREDIT_DATE(cursor.getString(3));
                credit.setCREDIT_ECHEANCE(cursor.getString(4));
                credit.setMONTANT_CREDIT(cursor.getDouble(5));
                credit.setMONTANT_ENCAISSE(cursor.getDouble(6));
                credit.setRESTE(cursor.getDouble(7));
                credit.setTYPE_CODE(cursor.getString(8));
                credit.setSTATUT_CODE(cursor.getString(9));
                credit.setCATEGORIE_CODE(cursor.getString(10));
                credit.setDATE_CREATION(cursor.getString(11));
                credit.setCREATEUR_CODE(cursor.getString(12));
                credit.setCOMMENTAIRE(cursor.getString(13));
                credit.setVERSION(cursor.getString(14));
                credit.setSOURCE(cursor.getString(15));
                credits.add(credit);
            }while(cursor.moveToNext());
        }

        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching credit from Sqlite: ");
        return credits;
    }

    public ArrayList<Credit> getListCreditAEncaisser() {
        ArrayList<Credit> credits = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CREDIT + " WHERE "+KEY_RESTE+" != 0  AND "+KEY_COMMENTAIRE+" = 'inserted' ";;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Credit credit= new Credit();
                credit.setCREDIT_CODE(cursor.getString(0));
                credit.setCOMMANDE_CODE(cursor.getString(1));
                credit.setCLIENT_CODE(cursor.getString(2));
                credit.setCREDIT_DATE(cursor.getString(3));
                credit.setCREDIT_ECHEANCE(cursor.getString(4));
                credit.setMONTANT_CREDIT(cursor.getDouble(5));
                credit.setMONTANT_ENCAISSE(cursor.getDouble(6));
                credit.setRESTE(cursor.getDouble(7));
                credit.setTYPE_CODE(cursor.getString(8));
                credit.setSTATUT_CODE(cursor.getString(9));
                credit.setCATEGORIE_CODE(cursor.getString(10));
                credit.setDATE_CREATION(cursor.getString(11));
                credit.setCREATEUR_CODE(cursor.getString(12));
                credit.setCOMMENTAIRE(cursor.getString(13));
                credit.setVERSION(cursor.getString(14));
                credit.setSOURCE(cursor.getString(15));
                credits.add(credit);
            }while(cursor.moveToNext());
        }

        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching credit from Sqlite: ");
        return credits;
    }


    public ArrayList<Credit> getListNotInserted() {
        ArrayList<Credit> credits = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CREDIT +" WHERE "+KEY_COMMENTAIRE+" IN ('to_insert','to_update') ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Credit credit= new Credit();
                credit.setCREDIT_CODE(cursor.getString(0));
                credit.setCOMMANDE_CODE(cursor.getString(1));
                credit.setCLIENT_CODE(cursor.getString(2));
                credit.setCREDIT_DATE(cursor.getString(3));
                credit.setCREDIT_ECHEANCE(cursor.getString(4));
                credit.setMONTANT_CREDIT(cursor.getDouble(5));
                credit.setMONTANT_ENCAISSE(cursor.getDouble(6));
                credit.setRESTE(cursor.getDouble(7));
                credit.setTYPE_CODE(cursor.getString(8));
                credit.setSTATUT_CODE(cursor.getString(9));
                credit.setCATEGORIE_CODE(cursor.getString(10));
                credit.setDATE_CREATION(cursor.getString(11));
                credit.setCREATEUR_CODE(cursor.getString(12));
                credit.setCOMMENTAIRE(cursor.getString(13));
                credit.setVERSION(cursor.getString(14));
                credit.setSOURCE(cursor.getString(15));
                credits.add(credit);
            }while(cursor.moveToNext());
        }

        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching credit from Sqlite: ");
        return credits;
    }

    public ArrayList<Credit> getListLocal() {
        ArrayList<Credit> credits = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CREDIT +" WHERE "+KEY_SOURCE+" = '' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Credit credit= new Credit();
                credit.setCREDIT_CODE(cursor.getString(0));
                credit.setCOMMANDE_CODE(cursor.getString(1));
                credit.setCLIENT_CODE(cursor.getString(2));
                credit.setCREDIT_DATE(cursor.getString(3));
                credit.setCREDIT_ECHEANCE(cursor.getString(4));
                credit.setMONTANT_CREDIT(cursor.getDouble(5));
                credit.setMONTANT_ENCAISSE(cursor.getDouble(6));
                credit.setRESTE(cursor.getDouble(7));
                credit.setTYPE_CODE(cursor.getString(8));
                credit.setSTATUT_CODE(cursor.getString(9));
                credit.setCATEGORIE_CODE(cursor.getString(10));
                credit.setDATE_CREATION(cursor.getString(11));
                credit.setCREATEUR_CODE(cursor.getString(12));
                credit.setCOMMENTAIRE(cursor.getString(13));
                credit.setVERSION(cursor.getString(14));
                credit.setSOURCE(cursor.getString(15));
                credits.add(credit);
            }while(cursor.moveToNext());
        }

        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching credit from Sqlite: ");
        return credits;
    }

    public ArrayList<Credit> getListServeur() {
        ArrayList<Credit> credits = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CREDIT +" WHERE "+KEY_SOURCE+" != '' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Credit credit= new Credit();
                credit.setCREDIT_CODE(cursor.getString(0));
                credit.setCOMMANDE_CODE(cursor.getString(1));
                credit.setCLIENT_CODE(cursor.getString(2));
                credit.setCREDIT_DATE(cursor.getString(3));
                credit.setCREDIT_ECHEANCE(cursor.getString(4));
                credit.setMONTANT_CREDIT(cursor.getDouble(5));
                credit.setMONTANT_ENCAISSE(cursor.getDouble(6));
                credit.setRESTE(cursor.getDouble(7));
                credit.setTYPE_CODE(cursor.getString(8));
                credit.setSTATUT_CODE(cursor.getString(9));
                credit.setCATEGORIE_CODE(cursor.getString(10));
                credit.setDATE_CREATION(cursor.getString(11));
                credit.setCREATEUR_CODE(cursor.getString(12));
                credit.setCOMMENTAIRE(cursor.getString(13));
                credit.setVERSION(cursor.getString(14));
                credit.setSOURCE(cursor.getString(15));
                credits.add(credit);
            }while(cursor.moveToNext());
        }

        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching credit from Sqlite: ");
        return credits;
    }

    public ArrayList<Credit> getListCreditAEncaisse() {
        ArrayList<Credit> credits = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CREDIT +" WHERE "+KEY_SOURCE+" != ''  AND "+KEY_RESTE+">'"+0+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Credit credit= new Credit();
                credit.setCREDIT_CODE(cursor.getString(0));
                credit.setCOMMANDE_CODE(cursor.getString(1));
                credit.setCLIENT_CODE(cursor.getString(2));
                credit.setCREDIT_DATE(cursor.getString(3));
                credit.setCREDIT_ECHEANCE(cursor.getString(4));
                credit.setMONTANT_CREDIT(cursor.getDouble(5));
                credit.setMONTANT_ENCAISSE(cursor.getDouble(6));
                credit.setRESTE(cursor.getDouble(7));
                credit.setTYPE_CODE(cursor.getString(8));
                credit.setSTATUT_CODE(cursor.getString(9));
                credit.setCATEGORIE_CODE(cursor.getString(10));
                credit.setDATE_CREATION(cursor.getString(11));
                credit.setCREATEUR_CODE(cursor.getString(12));
                credit.setCOMMENTAIRE(cursor.getString(13));
                credit.setVERSION(cursor.getString(14));
                credit.setSOURCE(cursor.getString(15));
                credits.add(credit);
            }while(cursor.moveToNext());
        }

        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching credit from Sqlite: ");
        return credits;
    }



    public void CleanCreditDatabse(ArrayList<Credit> local_list, ArrayList<Credit> server_list, Context context){

        CreditManager creditManager = new CreditManager(context);

        for(int i =0 ; i < local_list.size(); i++){
            String CREDIT_CODE = local_list.get(i).getCREDIT_CODE();
            Log.d(TAG, "CleanCreditDatabse: CREDIT CODE "+CREDIT_CODE);

            for(int j = 0 ; j < server_list.size() ; j++){
                String SOURCE = server_list.get(j).getSOURCE();
                Log.d(TAG, "CleanCreditDatabse: SOURCE "+SOURCE);
                if(CREDIT_CODE.equals(SOURCE)){
                    creditManager.delete(CREDIT_CODE);
                }
            }
        }
    }


    public ArrayList<Credit> getListByCmdCode(String commande_code) {
        ArrayList<Credit> credits = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CREDIT +" WHERE "+KEY_COMMANDE_CODE+"='"+commande_code+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Credit credit= new Credit();
                credit.setCREDIT_CODE(cursor.getString(0));
                credit.setCOMMANDE_CODE(cursor.getString(1));
                credit.setCLIENT_CODE(cursor.getString(2));
                credit.setCREDIT_DATE(cursor.getString(3));
                credit.setCREDIT_ECHEANCE(cursor.getString(4));
                credit.setMONTANT_CREDIT(cursor.getDouble(5));
                credit.setMONTANT_ENCAISSE(cursor.getDouble(6));
                credit.setRESTE(cursor.getDouble(7));
                credit.setTYPE_CODE(cursor.getString(8));
                credit.setSTATUT_CODE(cursor.getString(9));
                credit.setCATEGORIE_CODE(cursor.getString(10));
                credit.setDATE_CREATION(cursor.getString(11));
                credit.setCREATEUR_CODE(cursor.getString(12));
                credit.setCOMMENTAIRE(cursor.getString(13));
                credit.setVERSION(cursor.getString(14));
                credit.setSOURCE(cursor.getString(15));
                credits.add(credit);
            }while(cursor.moveToNext());
        }

        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching credit from Sqlite: ");
        return credits;
    }

    public double getSumResteCredit() {
        double somme = 0;

        String selectQuery = "SELECT  SUM(credit.RESTE) FROM " + TABLE_CREDIT ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if( cursor != null && cursor.moveToFirst() ) {
            somme = cursor.getDouble(0);
        }
        //retourner un credit;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching sum from Sqlite: ");
        return somme;
    }

    public void delete(String creditcode) {
        String Where = " "+KEY_CREDIT_CODE +"= '"+creditcode+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CREDIT, Where, null);
        db.close();
    }

    public void updateCredit(String creditcode,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_CREDIT + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_CREDIT_CODE +"= '"+creditcode+"'" ;
        db.execSQL(req);
        db.close();

        Log.d("credit", "updateCredit: "+req);
    }

    public void updatePayementCredit(String creditcode,double paye, double reste){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_CREDIT + " SET "+KEY_MONTANT_ENCAISSE  +"= '"+paye+"',"+KEY_RESTE+"= '"+reste+"',"+KEY_COMMENTAIRE+"= 'to_update' WHERE "+KEY_CREDIT_CODE +"= '"+creditcode+"'" ;
        db.execSQL(req);
        db.close();

        Log.d("credit", "updateCredit: "+req);
    }


    public void deleteCreditSynchronisee() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DateCredit = sdf.format(new Date());

        String Where = " "+KEY_COMMENTAIRE+"='updated' AND date("+KEY_DATE_CREATION+")!='"+DateCredit+"' AND "+KEY_RESTE+" = 0 ";
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_CREDIT, Where, null);
        db.close();
        Log.d("credit", "Deleted all credits verifiee from sqlite");
        Log.d("credit", "deleteCreditSynchronisee: "+Where);
    }


    public static void synchronisationCredit(final Context context){

        final CreditManager creditManager = new CreditManager(context);
        creditManager.deleteCreditSynchronisee();

        String tag_string_req = "CREDIT";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_CREDIT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("credit", "onResponse: "+response);
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray credits = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre de credit  "+credits.length() , Toast.LENGTH_LONG).show();
                        // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les Credits : " +response);

                        if(credits.length()>0) {

                            for (int i = 0; i < credits.length(); i++) {
                                JSONObject unCredit = credits.getJSONObject(i);
                                if (unCredit.getString("Statut").equals("true")) {
                                    creditManager.updateCredit((unCredit.getString("CREDIT_CODE")),unCredit.getString("msg"));
                                    cptInsert++;
                                }else{
                                    cptDelete++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CREDIT: Inserted: "+cptInsert +" Deleted: "+cptDelete ,"CREDIT",1));

                        }
                        //logM.add("Credit:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncCredit");


                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "credit : "+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CREDIT: Error: "+errorMsg ,"CREDIT",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "credit : "+"Json error: " +"erreur applcation credit" + e.getMessage(), Toast.LENGTH_LONG).show();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CREDIT: Error: "+e.getMessage() ,"CREDIT",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "credit : "+error.getMessage(), Toast.LENGTH_LONG).show();
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CREDIT: Error: "+error.getMessage() ,"CREDIT",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableCredit");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(creditManager.getListNotInserted()));
                    Log.d("Creditsynch", "Liste: "+creditManager.getListNotInserted());

                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public static void synchronisationCreditPull(final Context context){

        String tag_string_req = "CREDIT_PULL";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_CREDIT_PULL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ouvrir le logManager
                Log.d("CreditPull ", ""+response);
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray Credit = jObj.getJSONArray("Credits");
                        //Toast.makeText(context, "Nombre de CreditPull " +Credit.length(), Toast.LENGTH_SHORT).show();
                        int cptInsert = 0,cptDeleted = 0;
                        //Ajout/Suppression/modification des articles dans la base de données.
                        for (int i = 0; i < Credit.length(); i++) {
                            JSONObject uneCredit = Credit.getJSONObject(i);
                            CreditManager creditManager = new CreditManager(context);

                            if(uneCredit.getString("OPERATION").equals("DELETE")){
                                creditManager.delete(uneCredit.getString("COMMANDE_CODE"));
                                cptDeleted++;
                            }else {
                                Log.d("CreditPull", "onResponse: CreditPull"+uneCredit.toString());
                                creditManager.add(new Credit(uneCredit));
                                cptInsert++;
                            }
                        }

                        CreditManager creditManager = new CreditManager(context);
                        creditManager.CleanCreditDatabse(creditManager.getListLocal(),creditManager.getListServeur(),context);

                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CREDIT_PULL: Inserted: "+cptInsert +" Deleted: "+cptDeleted ,"CREDIT_PULL",1));

                        }


                        //logM.add("CreditPull:OK Insert:"+cptInsert +"Deleted:"+cptDeleted ,"SyncuneCreditPull");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //logM.add("CreditPull :NOK Insert "+errorMsg ,"SyncCreditPull");
                        //Toast.makeText(context,"CreditPull:"+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CREDIT_PULL: Error: "+errorMsg ,"CREDIT_PULL",0));

                        }

                    }

                } catch (JSONException e) {
                    //logM.add("CreditPull : NOK Insert "+e.getMessage() ,"SyncCreditPull");
                    e.printStackTrace();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CREDIT_PULL: Error: "+e.getMessage() ,"CREDIT_PULL",0));

                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "CreditPull:"+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("CreditPull : NOK Inserted "+error.getMessage() ,"SyncCreditPull");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CREDIT_PULL: Error: "+error.getMessage() ,"CREDIT_PULL",0));

                }


            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //Map<String, String> params = new HashMap<String, String>();
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                    String date=df1.format(new Date());


                    CreditManager creditManager = new CreditManager(context);
                    ArrayList<Credit> credits = new ArrayList<>();
                    credits=creditManager.getList();

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(credits));
                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    public double getNumberRounded(double nombre){

        double number = nombre;
        int decimalsToConsider = 2;
        BigDecimal bigDecimal = new BigDecimal(number);
        BigDecimal roundedWithScale = bigDecimal.setScale(decimalsToConsider, BigDecimal.ROUND_HALF_UP);
        //System.out.println("Rounded value with setting scale = "+roundedWithScale);


        return roundedWithScale.doubleValue();
    }

}


