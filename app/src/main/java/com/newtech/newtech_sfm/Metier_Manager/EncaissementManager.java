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
import com.newtech.newtech_sfm.Metier.Encaissement;
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

public class EncaissementManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_ENCAISSEMENT = "encaissement";


    private static final String
            KEY_ENCAISSEMENT_CODE = "ENCAISSEMENT_CODE",
            KEY_COMMANDE_CODE = "COMMANDE_CODE",
            KEY_CLIENT_CODE = "CLIENT_CODE",
            KEY_CREDIT_DATE = "CREDIT_DATE",
            KEY_CREDIT_ECHEANCE = "CREDIT_ECHEANCE",
            KEY_ENCAISSEMENT_DATE = "ENCAISSEMENT_DATE",
            KEY_TYPE_CODE = "TYPE_CODE",
            KEY_STATUT_CODE = "STATUT_CODE",
            KEY_CATEGORIE_CODE = "CATEGORIE_CODE",
            KEY_BANQUE = "BANQUE",
            KEY_NUMERO_CHEQUE = "NUMERO_CHEQUE",
            KEY_NUMERO_COMPTE = "NUMERO_COMPTE",
            KEY_MONTANT= "MONTANT",
            KEY_MONTANT_ENCAISSE = "MONTANT_ENCAISSE",
            KEY_RESTE = "RESTE",
            KEY_CREATEUR_CODE = "CREATEUR_CODE",
            KEY_DATE_CREATION = "DATE_CREATION",
            KEY_COMMENTAIRE = "COMMENTAIRE",
            KEY_LOCAL = "LOCAL",
            KEY_VERSION = "VERSION",
            KEY_SOURCE = "SOURCE",
            KEY_IMAGE = "IMAGE";

    public static String CREATE_ENCAISSEMENT_TABLE="CREATE TABLE " + TABLE_ENCAISSEMENT + "("
            +KEY_ENCAISSEMENT_CODE + " TEXT PRIMARY KEY,"
            +KEY_COMMANDE_CODE + " TEXT,"
            +KEY_CLIENT_CODE + " TEXT,"
            +KEY_CREDIT_DATE + " TEXT,"
            +KEY_CREDIT_ECHEANCE + " TEXT,"
            +KEY_ENCAISSEMENT_DATE + " TEXT,"
            +KEY_TYPE_CODE + " TEXT,"
            +KEY_STATUT_CODE + " TEXT,"
            +KEY_CATEGORIE_CODE + " TEXT,"
            +KEY_BANQUE + " TEXT,"
            +KEY_NUMERO_CHEQUE + " TEXT,"
            +KEY_NUMERO_COMPTE + " TEXT,"
            +KEY_MONTANT + " FLOAT,"
            +KEY_MONTANT_ENCAISSE + " FLOAT,"
            +KEY_RESTE + " FLOAT,"
            +KEY_CREATEUR_CODE + " TEXT,"
            +KEY_DATE_CREATION + " TEXT,"
            +KEY_COMMENTAIRE + " TEXT,"
            +KEY_LOCAL  + " NUMERIC,"
            +KEY_VERSION + " TEXT,"
            +KEY_SOURCE + " TEXT ,"
            +KEY_IMAGE + " TEXT "+")";


    public EncaissementManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE);
            db.execSQL(CREATE_ENCAISSEMENT_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENCAISSEMENT);
        onCreate(db);
    }

    public void add(Encaissement encaissement) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ENCAISSEMENT_CODE, encaissement.getENCAISSEMENT_CODE());
        values.put(KEY_COMMANDE_CODE, encaissement.getCOMMANDE_CODE());
        values.put(KEY_CLIENT_CODE , encaissement.getCLIENT_CODE());
        values.put(KEY_CREDIT_DATE , encaissement.getCREDIT_DATE());
        values.put(KEY_CREDIT_ECHEANCE , encaissement.getCREDIT_ECHEANCE());
        values.put(KEY_ENCAISSEMENT_DATE, encaissement.getENCAISSEMENT_DATE());
        values.put(KEY_TYPE_CODE, encaissement.getTYPE_CODE());
        values.put(KEY_STATUT_CODE, encaissement.getSTATUT_CODE());
        values.put(KEY_CATEGORIE_CODE , encaissement.getCATEGORIE_CODE());
        values.put(KEY_BANQUE, encaissement.getBANQUE());
        values.put(KEY_NUMERO_CHEQUE, encaissement.getNUMERO_CHEQUE());
        values.put(KEY_NUMERO_COMPTE, encaissement.getNUMERO_COMPTE());
        values.put(KEY_MONTANT, getNumberRounded(encaissement.getMONTANT()));
        values.put(KEY_MONTANT_ENCAISSE , encaissement.getMONTANT_ENCAISSE());
        values.put(KEY_RESTE , encaissement.getRESTE());
        values.put(KEY_CREATEUR_CODE, encaissement.getCREATEUR_CODE());
        values.put(KEY_DATE_CREATION, encaissement.getDATE_CREATION());
        values.put(KEY_COMMENTAIRE, encaissement.getCOMMENTAIRE());
        values.put(KEY_LOCAL,0);
        values.put(KEY_VERSION, encaissement.getVERSION());
        values.put(KEY_SOURCE , encaissement.getSOURCE());
        values.put(KEY_IMAGE , encaissement.getIMAGE());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_ENCAISSEMENT, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New encaissement inserted into sqlite: " + id);
    }

    public Encaissement get(String encaissement_code) {
        Encaissement encaissement= new Encaissement();

        String selectQuery = "SELECT  * FROM " + TABLE_ENCAISSEMENT+" WHERE "+KEY_ENCAISSEMENT_CODE+"='"+encaissement_code+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if( cursor != null && cursor.moveToFirst() ) {
            encaissement.setENCAISSEMENT_CODE(cursor.getString(0));
            encaissement.setCOMMANDE_CODE(cursor.getString(1));
            encaissement.setCLIENT_CODE(cursor.getString(2));
            encaissement.setCREDIT_DATE(cursor.getString(3));
            encaissement.setCREDIT_ECHEANCE(cursor.getString(4));
            encaissement.setENCAISSEMENT_DATE(cursor.getString(5));
            encaissement.setTYPE_CODE(cursor.getString(6));
            encaissement.setSTATUT_CODE(cursor.getString(7));
            encaissement.setCATEGORIE_CODE(cursor.getString(8));
            encaissement.setBANQUE(cursor.getString(9));
            encaissement.setNUMERO_CHEQUE(cursor.getString(10));
            encaissement.setNUMERO_COMPTE(cursor.getString(11));
            encaissement.setMONTANT(cursor.getFloat(12));
            encaissement.setMONTANT_ENCAISSE(cursor.getDouble(13));
            encaissement.setRESTE(cursor.getDouble(14));
            encaissement.setCREATEUR_CODE(cursor.getString(15));
            encaissement.setDATE_CREATION(cursor.getString(16));
            encaissement.setCOMMENTAIRE(cursor.getString(17));
            encaissement.setLOCAL(cursor.getInt(18));
            encaissement.setVERSION(cursor.getString(19));
            encaissement.setSOURCE(cursor.getString(20));
            encaissement.setIMAGE(cursor.getString(21));

        }
        //retourner un encaissement;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching encaissmenents from Sqlite: ");
        return encaissement;
    }

    public ArrayList<Encaissement> getList() {
        ArrayList<Encaissement> encaissements = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_ENCAISSEMENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Encaissement encaissement= new Encaissement();
                encaissement.setENCAISSEMENT_CODE(cursor.getString(0));
                encaissement.setCOMMANDE_CODE(cursor.getString(1));
                encaissement.setCLIENT_CODE(cursor.getString(2));
                encaissement.setCREDIT_DATE(cursor.getString(3));
                encaissement.setCREDIT_ECHEANCE(cursor.getString(4));
                encaissement.setENCAISSEMENT_DATE(cursor.getString(5));
                encaissement.setTYPE_CODE(cursor.getString(6));
                encaissement.setSTATUT_CODE(cursor.getString(7));
                encaissement.setCATEGORIE_CODE(cursor.getString(8));
                encaissement.setBANQUE(cursor.getString(9));
                encaissement.setNUMERO_CHEQUE(cursor.getString(10));
                encaissement.setNUMERO_COMPTE(cursor.getString(11));
                encaissement.setMONTANT(cursor.getFloat(12));
                encaissement.setMONTANT_ENCAISSE(cursor.getDouble(13));
                encaissement.setRESTE(cursor.getDouble(14));
                encaissement.setCREATEUR_CODE(cursor.getString(15));
                encaissement.setDATE_CREATION(cursor.getString(16));
                encaissement.setCOMMENTAIRE(cursor.getString(17));
                encaissement.setLOCAL(cursor.getInt(18));
                encaissement.setVERSION(cursor.getString(19));
                encaissement.setSOURCE(cursor.getString(20));
                encaissement.setIMAGE(cursor.getString(21));
                encaissements.add(encaissement);
            }while(cursor.moveToNext());
        }

        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching encaissement from Sqlite: ");
        return encaissements;
    }

    public ArrayList<Encaissement> getListNotInserted() {
        ArrayList<Encaissement> encaissements = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_ENCAISSEMENT +" WHERE "+KEY_COMMENTAIRE+" = 'to_insert' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Encaissement encaissement= new Encaissement();
                encaissement.setENCAISSEMENT_CODE(cursor.getString(0));
                encaissement.setCOMMANDE_CODE(cursor.getString(1));
                encaissement.setCLIENT_CODE(cursor.getString(2));
                encaissement.setCREDIT_DATE(cursor.getString(3));
                encaissement.setCREDIT_ECHEANCE(cursor.getString(4));
                encaissement.setENCAISSEMENT_DATE(cursor.getString(5));
                encaissement.setTYPE_CODE(cursor.getString(6));
                encaissement.setSTATUT_CODE(cursor.getString(7));
                encaissement.setCATEGORIE_CODE(cursor.getString(8));
                encaissement.setBANQUE(cursor.getString(9));
                encaissement.setNUMERO_CHEQUE(cursor.getString(10));
                encaissement.setNUMERO_COMPTE(cursor.getString(11));
                encaissement.setMONTANT(cursor.getFloat(12));
                encaissement.setMONTANT_ENCAISSE(cursor.getDouble(13));
                encaissement.setRESTE(cursor.getDouble(14));
                encaissement.setCREATEUR_CODE(cursor.getString(15));
                encaissement.setDATE_CREATION(cursor.getString(16));
                encaissement.setCOMMENTAIRE(cursor.getString(17));
                encaissement.setLOCAL(cursor.getInt(18));
                encaissement.setVERSION(cursor.getString(19));
                encaissement.setSOURCE(cursor.getString(20));
                encaissement.setIMAGE(cursor.getString(21));
                encaissements.add(encaissement);
            }while(cursor.moveToNext());
        }

        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching encaissement from Sqlite: ");
        return encaissements;
    }

    public ArrayList<Encaissement> getListByCmdCode(String commande_code) {
        ArrayList<Encaissement> encaissements = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_ENCAISSEMENT +" WHERE "+KEY_COMMANDE_CODE+"='"+commande_code+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Encaissement encaissement= new Encaissement();
                encaissement.setENCAISSEMENT_CODE(cursor.getString(0));
                encaissement.setCOMMANDE_CODE(cursor.getString(1));
                encaissement.setCLIENT_CODE(cursor.getString(2));
                encaissement.setCREDIT_DATE(cursor.getString(3));
                encaissement.setCREDIT_ECHEANCE(cursor.getString(4));
                encaissement.setENCAISSEMENT_DATE(cursor.getString(5));
                encaissement.setTYPE_CODE(cursor.getString(6));
                encaissement.setSTATUT_CODE(cursor.getString(7));
                encaissement.setCATEGORIE_CODE(cursor.getString(8));
                encaissement.setBANQUE(cursor.getString(9));
                encaissement.setNUMERO_CHEQUE(cursor.getString(10));
                encaissement.setNUMERO_COMPTE(cursor.getString(11));
                encaissement.setMONTANT(cursor.getFloat(12));
                encaissement.setMONTANT_ENCAISSE(cursor.getDouble(13));
                encaissement.setRESTE(cursor.getDouble(14));
                encaissement.setCREATEUR_CODE(cursor.getString(15));
                encaissement.setDATE_CREATION(cursor.getString(16));
                encaissement.setCOMMENTAIRE(cursor.getString(17));
                encaissement.setLOCAL(cursor.getInt(18));
                encaissement.setVERSION(cursor.getString(19));
                encaissement.setSOURCE(cursor.getString(20));
                encaissement.setIMAGE(cursor.getString(21));
                encaissements.add(encaissement);
            }while(cursor.moveToNext());
        }

        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching encaissement from Sqlite: ");
        return encaissements;
    }

    public double getSumEncByCmdCode(String commande_code) {
        double somme = 0;

        String selectQuery = "SELECT  SUM(encaissement.MONTANT_ENCAISSEMENT) FROM " + TABLE_ENCAISSEMENT+" WHERE "+KEY_COMMANDE_CODE+"='"+commande_code+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if( cursor != null && cursor.moveToFirst() ) {
            somme = cursor.getDouble(0);
        }
        //retourner un encaissement;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching sum from Sqlite: ");
        return somme;
    }

    public double getSumEncByCcTC(String commande_code, String Type) {
        double somme = 0;

        String selectQuery = "SELECT  SUM(encaissement.MONTANT) FROM " + TABLE_ENCAISSEMENT+" WHERE "+KEY_COMMANDE_CODE+"='"+commande_code+"' AND "+KEY_TYPE_CODE+"='"+Type+"' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if( cursor != null && cursor.moveToFirst() ) {
            somme = cursor.getDouble(0);
        }
        //retourner un encaissement;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching sum from Sqlite: ");
        return somme;
    }

    public double calcDroitTimbre(double valeur){

        double montant_net = 0;

        montant_net = valeur*(0.25/100);

        return montant_net;
    }

    public int delete(String encaissementcode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ENCAISSEMENT,KEY_ENCAISSEMENT_CODE+"=?",new String[]{encaissementcode});
    }

    public void updateEncaissement(String encaissementcode,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_ENCAISSEMENT + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_ENCAISSEMENT_CODE +"= '"+encaissementcode+"'" ;
        db.execSQL(req);
        db.close();

        Log.d("encaissement", "updateEncaissement: "+req);
    }

    public void deleteEncaissementSynchronisee() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DateEncaissement = sdf.format(new Date());

        String Where = " "+KEY_COMMENTAIRE+"='inserted' and date("+KEY_ENCAISSEMENT_DATE+")!='"+DateEncaissement+"' ";
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_ENCAISSEMENT, Where, null);
        db.close();
        Log.d("encaissement", "Deleted all encaissements verifiee from sqlite");
        Log.d("encaissement", "deleteEncaissementSynchronisee: "+Where);
    }


    public static void synchronisationEncaissement(final Context context){

        final EncaissementManager encaissementManager = new EncaissementManager(context);
        encaissementManager.deleteEncaissementSynchronisee();

        String tag_string_req = "ENCAISSEMENT";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_ENCAISSEMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("encaissement", "onResponse: "+response);
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray encaissements = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre d encaissement  "+encaissements.length() , Toast.LENGTH_LONG).show();
                        // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les Encaissements : " +response);

                        if(encaissements.length()>0) {

                            for (int i = 0; i < encaissements.length(); i++) {
                                JSONObject unEncaissement = encaissements.getJSONObject(i);
                                if (unEncaissement.getString("Statut").equals("true")) {
                                    encaissementManager.updateEncaissement((unEncaissement.getString("ENCAISSEMENT_CODE")),"inserted");
                                    cptInsert++;

                                }else{

                                    cptDelete++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("ENCAISSEMENT: Inserted: "+cptInsert +" NotInserted: "+cptDelete ,"ENCAISSEMENT",1));

                        }

                        //logM.add("Encaissement:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncEncaissement");


                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "encaissement : "+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("ENCAISSEMENT: Error: "+errorMsg ,"ENCAISSEMENT",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("ENCAISSEMENT: Error: "+e.getMessage() ,"ENCAISSEMENT",0));

                    }
                    //Toast.makeText(context, "encaissement : "+"Json error: " +"erreur applcation encaissement" + e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "encaissement : "+error.getMessage(), Toast.LENGTH_LONG).show();
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("ENCAISSEMENT: Error: "+error.getMessage() ,"ENCAISSEMENT",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableEncaissement");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(encaissementManager.getListNotInserted()));
                    Log.d("Encaissementsynch", "Liste: "+encaissementManager.getListNotInserted());

                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public static void synchronisationEncaissementPull(final Context context){

        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_ENCAISSEMENT_PULL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ouvrir le logManager
                Log.d("EncaissementPull ", ""+response);
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray Encaissement = jObj.getJSONArray("Encaissements");
                        Toast.makeText(context, "Nombre de EncaissementPull " +Encaissement.length(), Toast.LENGTH_SHORT).show();
                        int cptInsert = 0,cptDeleted = 0;
                        //Ajout/Suppression/modification des articles dans la base de données.
                        for (int i = 0; i < Encaissement.length(); i++) {
                            JSONObject uneEncaissement = Encaissement.getJSONObject(i);
                            EncaissementManager encaissementManager = new EncaissementManager(context);

                            if(uneEncaissement.getString("OPERATION").equals("DELETE")){
                                encaissementManager.delete(uneEncaissement.getString("COMMANDE_CODE"));
                                cptDeleted++;
                            }else {
                                Log.d("EncaissementPull", "onResponse: EncaissementPull"+uneEncaissement.toString());
                                encaissementManager.add(new Encaissement(uneEncaissement));
                                cptInsert++;
                            }
                        }
                        logM.add("EncaissementPull:OK Insert:"+cptInsert +"Deleted:"+cptDeleted ,"SyncuneEncaissementPull");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        logM.add("EncaissementPull :NOK Insert "+errorMsg ,"SyncEncaissementPull");
                        Toast.makeText(context,"EncaissementPull:"+errorMsg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    logM.add("EncaissementPull : NOK Insert "+e.getMessage() ,"SyncEncaissementPull");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "EncaissementPull:"+error.getMessage(), Toast.LENGTH_LONG).show();
                LogSyncManager logM= new LogSyncManager(context);
                logM.add("EncaissementPull : NOK Inserted "+error.getMessage() ,"SyncEncaissementPull");

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


                    EncaissementManager encaissementManager = new EncaissementManager(context);
                    ArrayList<Encaissement> encaissements = new ArrayList<>();
                    encaissements=encaissementManager.getList();

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(encaissements));
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


