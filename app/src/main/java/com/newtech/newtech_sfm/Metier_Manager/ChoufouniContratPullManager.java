package com.newtech.newtech_sfm.Metier_Manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
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
import com.newtech.newtech_sfm.Metier.ChoufouniContrat;
import com.newtech.newtech_sfm.Metier.ChoufouniContratPull;
import com.newtech.newtech_sfm.Metier.LogSync;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChoufouniContratPullManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_CHOUFOUNI_CONTRAT_PULL = "choufounicontratpull";


    public static final String
            KEY_CHOUFOUNI_CONTRAT_CODE = "CHOUFOUNI_CONTRAT_CODE",
            KEY_CHOUFOUNI_CODE= "CHOUFOUNI_CODE",
            KEY_DISTRIBUTEUR_CODE= "DISTRIBUTEUR_CODE",
            KEY_UTILISATEUR_CODE= "UTILISATEUR_CODE",
            KEY_CLIENT_CODE= "CLIENT_CODE",
            KEY_DATE_CONTRAT= "DATE_CONTRAT",
            KEY_TYPE_CODE= "TYPE_CODE",
            KEY_STATUT_CODE= "STATUT_CODE",
            KEY_CATEGORIE_CODE= "CATEGORIE_CODE",
            KEY_REMISE= "REMISE",
            KEY_SOLDE= "SOLDE",
            KEY_CREATEUR_CODE= "CREATEUR_CODE",
            KEY_DATE_CREATION= "DATE_CREATION",
            KEY_COMMENTAIRE= "COMMENTAIRE",
            KEY_VERSION= "VERSION",
            KEY_GPS_LATITUDE="GPS_LATITUDE",
            KEY_GPS_LONGITUDE="GPS_LONGITUDE",
            KEY_DISTANCE="DISTANCE";

    public static String CREATE_CHOUFOUNI_CONTRAT_PULL_TABLE = "CREATE TABLE " + TABLE_CHOUFOUNI_CONTRAT_PULL + " ("

            + KEY_CHOUFOUNI_CONTRAT_CODE  + " TEXT PRIMARY KEY,"
            + KEY_CHOUFOUNI_CODE + " TEXT,"
            + KEY_DISTRIBUTEUR_CODE + " TEXT,"
            + KEY_UTILISATEUR_CODE + " TEXT,"
            + KEY_CLIENT_CODE + " TEXT,"
            + KEY_DATE_CONTRAT + " TEXT,"
            + KEY_TYPE_CODE + " TEXT,"
            + KEY_STATUT_CODE + " TEXT,"
            + KEY_CATEGORIE_CODE + " TEXT,"
            + KEY_REMISE + " NUMERIC,"
            + KEY_SOLDE + " NUMERIC,"
            + KEY_CREATEUR_CODE + " TEXT,"
            + KEY_DATE_CREATION + " TEXT,"
            + KEY_COMMENTAIRE + " TEXT,"
            + KEY_VERSION + " TEXT,"
            + KEY_GPS_LATITUDE + " TEXT,"
            + KEY_GPS_LONGITUDE + " TEXT,"
            + KEY_DISTANCE + " TEXT"+ ")";

    public ChoufouniContratPullManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAMILLE);
            db.execSQL(CREATE_CHOUFOUNI_CONTRAT_PULL_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database CHOUFOUNICONTRATPULL tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHOUFOUNI_CONTRAT_PULL);
        // Create tables again
        onCreate(db);
    }

    public void add(ChoufouniContratPull choufouniContrat) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_CHOUFOUNI_CONTRAT_CODE,choufouniContrat.getCHOUFOUNI_CONTRAT_CODE());
        values.put(KEY_CHOUFOUNI_CODE,choufouniContrat.getCHOUFOUNI_CODE());
        values.put(KEY_DISTRIBUTEUR_CODE,choufouniContrat.getDISTRIBUTEUR_CODE());
        values.put(KEY_UTILISATEUR_CODE,choufouniContrat.getUTILISATEUR_CODE());
        values.put(KEY_CLIENT_CODE,choufouniContrat.getCLIENT_CODE());
        values.put(KEY_DATE_CONTRAT,choufouniContrat.getDATE_CONTRAT());
        values.put(KEY_TYPE_CODE,choufouniContrat.getTYPE_CODE());
        values.put(KEY_STATUT_CODE,choufouniContrat.getSTATUT_CODE());
        values.put(KEY_CATEGORIE_CODE,choufouniContrat.getCATEGORIE_CODE());
        values.put(KEY_REMISE,choufouniContrat.getREMISE());
        values.put(KEY_SOLDE,choufouniContrat.getSOLDE());
        values.put(KEY_CREATEUR_CODE,choufouniContrat.getCREATEUR_CODE());
        values.put(KEY_DATE_CREATION,choufouniContrat.getDATE_CREATION());
        values.put(KEY_COMMENTAIRE,choufouniContrat.getCOMMENTAIRE());
        values.put(KEY_VERSION,choufouniContrat.getVERSION());
        values.put(KEY_GPS_LATITUDE, choufouniContrat.getGPS_LATITUDE());
        values.put(KEY_GPS_LONGITUDE, choufouniContrat.getGPS_LONGITUDE());
        values.put(KEY_DISTANCE, choufouniContrat.getDISTANCE());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_CHOUFOUNI_CONTRAT_PULL, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d("STOCKDEMANDE MANAGER", "New stockdemande inserted into sqlite: " + id);

    }

    public ChoufouniContratPull get(String CHOUFOUNI_CONTRAT_CODE) {

        ChoufouniContratPull choufouniContrat = new ChoufouniContratPull();
        String selectQuery = "SELECT * FROM " + TABLE_CHOUFOUNI_CONTRAT_PULL+ " WHERE "+ KEY_CHOUFOUNI_CONTRAT_CODE +" = '"+CHOUFOUNI_CONTRAT_CODE+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            choufouniContrat.setCHOUFOUNI_CONTRAT_CODE(cursor.getString(0));
            choufouniContrat.setCHOUFOUNI_CODE(cursor.getString(1));
            choufouniContrat.setDISTRIBUTEUR_CODE(cursor.getString(2));
            choufouniContrat.setUTILISATEUR_CODE(cursor.getString(3));
            choufouniContrat.setCLIENT_CODE(cursor.getString(4));
            choufouniContrat.setDATE_CONTRAT(cursor.getString(5));
            choufouniContrat.setTYPE_CODE(cursor.getString(6));
            choufouniContrat.setSTATUT_CODE(cursor.getString(7));
            choufouniContrat.setCATEGORIE_CODE(cursor.getString(8));
            choufouniContrat.setREMISE(cursor.getDouble(9));
            choufouniContrat.setSOLDE(cursor.getDouble(10));
            choufouniContrat.setCREATEUR_CODE(cursor.getString(11));
            choufouniContrat.setDATE_CREATION(cursor.getString(12));
            choufouniContrat.setCOMMENTAIRE(cursor.getString(13));
            choufouniContrat.setVERSION(cursor.getString(14));
            choufouniContrat.setGPS_LATITUDE(cursor.getString(15));
            choufouniContrat.setGPS_LONGITUDE(cursor.getString(16));
            choufouniContrat.setDISTANCE(cursor.getInt(17));
        }

        cursor.close();
        db.close();
        Log.d("CHOUFOUNI CONTRAT M", "fetching ");
        return choufouniContrat;

    }

    public ArrayList<ChoufouniContratPull> getList() {
        ArrayList<ChoufouniContratPull> choufouniContrats = new ArrayList<ChoufouniContratPull>();

        String selectQuery = "SELECT * FROM " + TABLE_CHOUFOUNI_CONTRAT_PULL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                ChoufouniContratPull choufouniContrat = new ChoufouniContratPull();
                choufouniContrat.setCHOUFOUNI_CONTRAT_CODE(cursor.getString(0));
                choufouniContrat.setCHOUFOUNI_CODE(cursor.getString(1));
                choufouniContrat.setDISTRIBUTEUR_CODE(cursor.getString(2));
                choufouniContrat.setUTILISATEUR_CODE(cursor.getString(3));
                choufouniContrat.setCLIENT_CODE(cursor.getString(4));
                choufouniContrat.setDATE_CONTRAT(cursor.getString(5));
                choufouniContrat.setTYPE_CODE(cursor.getString(6));
                choufouniContrat.setSTATUT_CODE(cursor.getString(7));
                choufouniContrat.setCATEGORIE_CODE(cursor.getString(8));
                choufouniContrat.setREMISE(cursor.getDouble(9));
                choufouniContrat.setSOLDE(cursor.getDouble(10));
                choufouniContrat.setCREATEUR_CODE(cursor.getString(11));
                choufouniContrat.setDATE_CREATION(cursor.getString(12));
                choufouniContrat.setCOMMENTAIRE(cursor.getString(13));
                choufouniContrat.setVERSION(cursor.getString(14));
                choufouniContrat.setGPS_LATITUDE(cursor.getString(15));
                choufouniContrat.setGPS_LONGITUDE(cursor.getString(16));
                choufouniContrat.setDISTANCE(cursor.getInt(17));
                choufouniContrats.add(choufouniContrat);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version StockDemande from Sqlite: "+choufouniContrats.size());
        return choufouniContrats;
    }

    public ArrayList<ChoufouniContrat> getListChoufouniContrat() {
        ArrayList<ChoufouniContrat> choufouniContrats = new ArrayList<ChoufouniContrat>();

        String selectQuery = "SELECT * FROM " + TABLE_CHOUFOUNI_CONTRAT_PULL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                ChoufouniContrat choufouniContrat = new ChoufouniContrat();
                choufouniContrat.setCHOUFOUNI_CONTRAT_CODE(cursor.getString(0));
                choufouniContrat.setCHOUFOUNI_CODE(cursor.getString(1));
                choufouniContrat.setDISTRIBUTEUR_CODE(cursor.getString(2));
                choufouniContrat.setUTILISATEUR_CODE(cursor.getString(3));
                choufouniContrat.setCLIENT_CODE(cursor.getString(4));
                choufouniContrat.setDATE_CONTRAT(cursor.getString(5));
                choufouniContrat.setTYPE_CODE(cursor.getString(6));
                choufouniContrat.setSTATUT_CODE(cursor.getString(7));
                choufouniContrat.setCATEGORIE_CODE(cursor.getString(8));
                choufouniContrat.setREMISE(cursor.getDouble(9));
                choufouniContrat.setSOLDE(cursor.getDouble(10));
                choufouniContrat.setCREATEUR_CODE(cursor.getString(11));
                choufouniContrat.setDATE_CREATION(cursor.getString(12));
                choufouniContrat.setCOMMENTAIRE(cursor.getString(13));
                //choufouniContrat.setVERSION(cursor.getString(14));
                choufouniContrat.setGPS_LATITUDE(cursor.getString(15));
                choufouniContrat.setGPS_LONGITUDE(cursor.getString(16));
                choufouniContrat.setDISTANCE(cursor.getInt(17));

                choufouniContrats.add(choufouniContrat);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version StockDemande from Sqlite: "+choufouniContrats.size());
        return choufouniContrats;
    }

    public ArrayList<ChoufouniContrat> getListByClientCode(String client_code) {
        ArrayList<ChoufouniContrat> choufouniContrats = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CHOUFOUNI_CONTRAT_PULL +" WHERE "+KEY_CLIENT_CODE+" = '"+client_code+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                ChoufouniContrat choufouniContrat = new ChoufouniContrat();
                choufouniContrat.setCHOUFOUNI_CONTRAT_CODE(cursor.getString(0));
                choufouniContrat.setCHOUFOUNI_CODE(cursor.getString(1));
                choufouniContrat.setDISTRIBUTEUR_CODE(cursor.getString(2));
                choufouniContrat.setUTILISATEUR_CODE(cursor.getString(3));
                choufouniContrat.setCLIENT_CODE(cursor.getString(4));
                choufouniContrat.setDATE_CONTRAT(cursor.getString(5));
                choufouniContrat.setTYPE_CODE(cursor.getString(6));
                choufouniContrat.setSTATUT_CODE(cursor.getString(7));
                choufouniContrat.setCATEGORIE_CODE(cursor.getString(8));
                choufouniContrat.setREMISE(cursor.getDouble(9));
                choufouniContrat.setSOLDE(cursor.getDouble(10));
                choufouniContrat.setCREATEUR_CODE(cursor.getString(11));
                choufouniContrat.setDATE_CREATION(cursor.getString(12));
                choufouniContrat.setCOMMENTAIRE(cursor.getString(13));
                //choufouniContrat.setVERSION(cursor.getString(14));
                choufouniContrat.setGPS_LATITUDE(cursor.getString(15));
                choufouniContrat.setGPS_LONGITUDE(cursor.getString(16));
                choufouniContrat.setDISTANCE(cursor.getInt(17));
                Log.d(TAG, "getListNotInserted: "+choufouniContrats.toString());
                choufouniContrats.add(choufouniContrat);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();

        Log.d(TAG, "Nombre ChoufouniContrat from table choufounicontrat: "+choufouniContrats.size());
        return choufouniContrats;
    }

    public ArrayList<ChoufouniContratPull> getListNotInserted() {
        ArrayList<ChoufouniContratPull> choufouniContrats = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CHOUFOUNI_CONTRAT_PULL +" WHERE "+KEY_COMMENTAIRE+" = 'to_insert' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                ChoufouniContratPull choufouniContrat = new ChoufouniContratPull();
                choufouniContrat.setCHOUFOUNI_CONTRAT_CODE(cursor.getString(0));
                choufouniContrat.setCHOUFOUNI_CODE(cursor.getString(1));
                choufouniContrat.setDISTRIBUTEUR_CODE(cursor.getString(2));
                choufouniContrat.setUTILISATEUR_CODE(cursor.getString(3));
                choufouniContrat.setCLIENT_CODE(cursor.getString(4));
                choufouniContrat.setDATE_CONTRAT(cursor.getString(5));
                choufouniContrat.setTYPE_CODE(cursor.getString(6));
                choufouniContrat.setSTATUT_CODE(cursor.getString(7));
                choufouniContrat.setCATEGORIE_CODE(cursor.getString(8));
                choufouniContrat.setREMISE(cursor.getDouble(9));
                choufouniContrat.setSOLDE(cursor.getDouble(10));
                choufouniContrat.setCREATEUR_CODE(cursor.getString(11));
                choufouniContrat.setDATE_CREATION(cursor.getString(12));
                choufouniContrat.setCOMMENTAIRE(cursor.getString(13));
                choufouniContrat.setVERSION(cursor.getString(14));
                choufouniContrat.setGPS_LATITUDE(cursor.getString(15));
                choufouniContrat.setGPS_LONGITUDE(cursor.getString(16));
                choufouniContrat.setDISTANCE(cursor.getInt(17));

                Log.d(TAG, "getListNotInserted: "+choufouniContrats.toString());
                choufouniContrats.add(choufouniContrat);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();

        Log.d(TAG, "Nombre ChoufouniContrat from table choufounicontrat: "+choufouniContrats.size());
        return choufouniContrats;
    }

    public int delete(String choufouni_contrat_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CHOUFOUNI_CONTRAT_PULL,KEY_CHOUFOUNI_CONTRAT_CODE+"=?",new String[]{choufouni_contrat_code});
    }

    public void updateChoufouniContratPull(String choufouni_contrat_code,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_CHOUFOUNI_CONTRAT_PULL + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_CHOUFOUNI_CONTRAT_CODE +"= '"+choufouni_contrat_code+"'" ;
        db.execSQL(req);
        db.close();

        Log.d("commande", "updateCommande: "+req);
    }

    public void deleteChoufouniContratSynchronisee() {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefCmdAS = sdf.format(new Date());
        Log.d(TAG, "deleteChoufouniContratSynchronisee: "+DatefCmdAS);

        String Where = " "+KEY_COMMENTAIRE+"='inserted' and date("+KEY_DATE_CREATION+")!='"+DatefCmdAS+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_CHOUFOUNI_CONTRAT_PULL, Where, null);
        db.close();

    }

    public ArrayList<ChoufouniContratPull> getChoufouniContratPullCode_Version_List() {
        ArrayList<ChoufouniContratPull> choufounis = new ArrayList<>();

        String selectQuery = "SELECT "+ KEY_CHOUFOUNI_CODE+" , " +KEY_VERSION +  " FROM " + TABLE_CHOUFOUNI_CONTRAT_PULL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                ChoufouniContratPull choufouni = new ChoufouniContratPull();
                choufouni.setCHOUFOUNI_CONTRAT_CODE(cursor.getString(0));
                choufouni.setVERSION(cursor.getString(1));
                choufounis.add(choufouni);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version  Famille from Sqlite: ");
        return choufounis;
    }

    public static void synchronisationChoufouniContratPullContratPull(final Context context){

        String tag_string_req = "CHOUFOUNI_CONTRAT_PULL";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_CHOUFOUNI_CONTRAT_PULL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response);
                Log.d("ChoufouniContratPull", "onResponse: "+response);
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray ChoufouniContratPulls = jObj.getJSONArray("ChoufouniContratPulls");
                        //Toast.makeText(context, "nombre de ChoufouniContratPulls  "+ChoufouniContratPulls.length()  , Toast.LENGTH_SHORT).show();
                        if(ChoufouniContratPulls.length()>0) {
                            ChoufouniContratPullManager choufouniManager = new ChoufouniContratPullManager(context);
                            for (int i = 0; i < ChoufouniContratPulls.length(); i++) {
                                JSONObject unChoufouniContratPull = ChoufouniContratPulls.getJSONObject(i);
                                if (unChoufouniContratPull.getString("OPERATION").equals("DELETE")) {
                                    choufouniManager.delete(unChoufouniContratPull.getString(KEY_CHOUFOUNI_CODE));
                                    cptDelete++;
                                } else {
                                    choufouniManager.add(new ChoufouniContratPull(unChoufouniContratPull));
                                    cptInsert++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CHOUFOUNI_CONTRAT_PULL: Inserted: "+cptInsert +" Deleted: "+cptDelete ,"CHOUFOUNI_CONTRAT_PULL",1));

                        }

                        //logM.add("Familles:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncFamille");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(context, "Famille : "+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("Famille:NOK "+errorMsg ,"SyncFamille");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CHOUFOUNI_CONTRAT_PULL: Error: "+errorMsg ,"CHOUFOUNI_CONTRAT_PULL",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "Famille : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("Famille:NOK "+e.getMessage() ,"SyncFamille");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CHOUFOUNI_CONTRAT_PULL: Error: "+e.getMessage() ,"CHOUFOUNI_CONTRAT_PULL",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Famille : "+error.getMessage(), Toast.LENGTH_LONG).show();
                // LogSyncManager logM= new LogSyncManager(context);
                //logM.add("Famille:NOK "+error.getMessage() ,"SyncFamille");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CHOUFOUNI_CONTRAT_PULL: Error: "+error.getMessage() ,"CHOUFOUNI_CONTRAT_PULL",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                    String date=df1.format(new Date());


                    ChoufouniContratPullManager choufouniContratPullManager = new ChoufouniContratPullManager(context);
                    ArrayList<ChoufouniContratPull> choufouniContratPulls = new ArrayList<>();
                    choufouniContratPulls=choufouniContratPullManager.getList();

                    Log.d(TAG, "getParams: choufouniContratPulls  : "+choufouniContratPulls);

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    Log.d("uc cmdnc sync",gson.toJson(pref.getString("UTILISATEUR_CODE",null)));

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(choufouniContratPulls));
                }
                return arrayFinale;

            }
        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public Boolean exist(String choufouni_contrat_code){
        Boolean result=false;
        String selectQuery = "SELECT  * FROM "+TABLE_CHOUFOUNI_CONTRAT_PULL+" WHERE "+KEY_CHOUFOUNI_CONTRAT_CODE+"='"+choufouni_contrat_code+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if( cursor != null && cursor.getCount()>0 ) {
            result=true;
        }
        cursor.close();
        db.close();
        return result;
    }
}
