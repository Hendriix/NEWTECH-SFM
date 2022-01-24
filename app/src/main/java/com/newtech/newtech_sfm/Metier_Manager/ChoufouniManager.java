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
import com.newtech.newtech_sfm.Activity.SyncV2Activity;
import com.newtech.newtech_sfm.Configuration.AppConfig;
import com.newtech.newtech_sfm.Configuration.AppController;
import com.newtech.newtech_sfm.Configuration.SQLiteHandler;
import com.newtech.newtech_sfm.Metier.Choufouni;
import com.newtech.newtech_sfm.Metier.LogSync;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChoufouniManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Article table name
    public static final String TABLE_CHOUFOUNI = "choufouni";

    private static final String
    KEY_CHOUFOUNI_CODE = "CHOUFOUNI_CODE",
    KEY_CHOUFOUNI_NOM = "CHOUFOUNI_NOM",
    KEY_DATE_DEBUT = "DATE_DEBUT",
    KEY_DATE_FIN = "DATE_FIN",
    KEY_TYPE_CODE = "TYPE_CODE",
    KEY_STATUT_CODE = "STATUT_CODE",
    KEY_CATEGORIE_CODE = "CATEGORIE_CODE",
    KEY_CREATEUR_CODE = "CREATEUR_CODE",
    KEY_DATE_CREATION = "DATE_CREATION",
    KEY_INACTIF = "INACTIF",
    KEY_INACTIF_RAISON = "INACTIF_RAISON",
    KEY_VERSION = "VERSION";

    public static  String CREATE_CHOUFOUNI_TABLE = "CREATE TABLE " + TABLE_CHOUFOUNI + "("

            + KEY_CHOUFOUNI_CODE + " TEXT,"
            + KEY_CHOUFOUNI_NOM + " TEXT,"
            + KEY_DATE_DEBUT + " TEXT,"
            + KEY_DATE_FIN + " TEXT,"
            + KEY_TYPE_CODE + " TEXT,"
            + KEY_STATUT_CODE + " TEXT,"
            + KEY_CATEGORIE_CODE + " TEXT,"
            + KEY_CREATEUR_CODE + " TEXT,"
            + KEY_DATE_CREATION + " TEXT,"
            + KEY_INACTIF + " NUMERIC,"
            + KEY_INACTIF_RAISON + " TEXT,"
            + KEY_VERSION + " TEXT" + ")";


    public ChoufouniManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAMILLE);
            db.execSQL(CREATE_CHOUFOUNI_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database CHOUFOUNI tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHOUFOUNI);
        onCreate(db);
    }

    public void add(Choufouni choufouni) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CHOUFOUNI_CODE, choufouni.getCHOUFOUNI_CODE());
        values.put(KEY_CHOUFOUNI_NOM, choufouni.getCHOUFOUNI_NOM());
        values.put(KEY_DATE_DEBUT, choufouni.getDATE_DEBUT());
        values.put(KEY_DATE_FIN, choufouni.getDATE_FIN());
        values.put(KEY_TYPE_CODE, choufouni.getTYPE_CODE());
        values.put(KEY_STATUT_CODE, choufouni.getSTATUT_CODE());
        values.put(KEY_CATEGORIE_CODE, choufouni.getCATEGORIE_CODE());
        values.put(KEY_CREATEUR_CODE, choufouni.getCREATEUR_CODE());
        values.put(KEY_DATE_CREATION, choufouni.getDATE_CREATION());
        values.put(KEY_INACTIF, choufouni.getINACTIF());
        values.put(KEY_INACTIF_RAISON, choufouni.getINACTIF_RAISON());
        values.put(KEY_VERSION, choufouni.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_CHOUFOUNI, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New choufouni inserted into sqlite: " + id);
    }

    public Choufouni get(String choufouni_code) {
        Choufouni choufouni = new Choufouni();

        String selectQuery = "SELECT  * FROM " + TABLE_CHOUFOUNI+" WHERE "+KEY_CHOUFOUNI_CODE+"='"+choufouni_code+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if( cursor != null && cursor.moveToFirst() ) {
            choufouni.setCHOUFOUNI_CODE(cursor.getString(0));
            choufouni.setCHOUFOUNI_NOM(cursor.getString(1));
            choufouni.setDATE_DEBUT(cursor.getString(2));
            choufouni.setDATE_FIN(cursor.getString(3));
            choufouni.setTYPE_CODE(cursor.getString(4));
            choufouni.setSTATUT_CODE(cursor.getString(5));
            choufouni.setCATEGORIE_CODE(cursor.getString(6));
            choufouni.setCREATEUR_CODE(cursor.getString(7));
            choufouni.setDATE_CREATION(cursor.getString(8));
            choufouni.setINACTIF(cursor.getDouble(9));
            choufouni.setINACTIF_RAISON(cursor.getString(10));
            choufouni.setVERSION(cursor.getString(11));
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching article from Sqlite: ");
        return choufouni;
    }

    public ArrayList<Choufouni> getList() {
        ArrayList<Choufouni> choufounis = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CHOUFOUNI;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Choufouni choufouni = new Choufouni();
                choufouni.setCHOUFOUNI_CODE(cursor.getString(0));
                choufouni.setCHOUFOUNI_NOM(cursor.getString(1));
                choufouni.setDATE_DEBUT(cursor.getString(2));
                choufouni.setDATE_FIN(cursor.getString(3));
                choufouni.setTYPE_CODE(cursor.getString(4));
                choufouni.setSTATUT_CODE(cursor.getString(5));
                choufouni.setCATEGORIE_CODE(cursor.getString(6));
                choufouni.setCREATEUR_CODE(cursor.getString(7));
                choufouni.setDATE_CREATION(cursor.getString(8));
                choufouni.setINACTIF(cursor.getDouble(9));
                choufouni.setINACTIF_RAISON(cursor.getString(10));
                choufouni.setVERSION(cursor.getString(11));
                choufounis.add(choufouni);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching all famille from Sqlite: ");
        return choufounis;
    }

    public ArrayList<Choufouni> getListActif() {
        ArrayList<Choufouni> choufounis = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CHOUFOUNI +" WHERE "+KEY_INACTIF+"='"+0+"' ;";;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Choufouni choufouni = new Choufouni();
                choufouni.setCHOUFOUNI_CODE(cursor.getString(0));
                choufouni.setCHOUFOUNI_NOM(cursor.getString(1));
                choufouni.setDATE_DEBUT(cursor.getString(2));
                choufouni.setDATE_FIN(cursor.getString(3));
                choufouni.setTYPE_CODE(cursor.getString(4));
                choufouni.setSTATUT_CODE(cursor.getString(5));
                choufouni.setCATEGORIE_CODE(cursor.getString(6));
                choufouni.setCREATEUR_CODE(cursor.getString(7));
                choufouni.setDATE_CREATION(cursor.getString(8));
                choufouni.setINACTIF(cursor.getDouble(9));
                choufouni.setINACTIF_RAISON(cursor.getString(10));
                choufouni.setVERSION(cursor.getString(11));
                choufounis.add(choufouni);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching all famille from Sqlite: ");
        return choufounis;
    }

    public void deleteChoufounis() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_CHOUFOUNI, null, null);
        db.close();
        Log.d(TAG, "Deleted all choufouni info from sqlite");
    }

    public int delete(String choufouni_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CHOUFOUNI,KEY_CHOUFOUNI_CODE+"=?",new String[]{choufouni_code});
    }

    public ArrayList<Choufouni> getChoufouniCode_Version_List() {
        ArrayList<Choufouni> choufounis = new ArrayList<>();

        String selectQuery = "SELECT "+ KEY_CHOUFOUNI_CODE+" , " +KEY_VERSION +  " FROM " + TABLE_CHOUFOUNI;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Choufouni choufouni = new Choufouni();
                choufouni.setCHOUFOUNI_CODE(cursor.getString(0));
                choufouni.setVERSION(cursor.getString(1));
                choufounis.add(choufouni);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version  Famille from Sqlite: ");
        return choufounis;
    }

    public static void synchronisationChoufouni(final Context context){

        String tag_string_req = "CHOUFOUNI";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_CHOUFOUNI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response);
                Log.d("famille", "onResponse: "+response);
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray Choufounis = jObj.getJSONArray("Choufounis");
                        //Toast.makeText(context, "nombre de Choufounis  "+Choufounis.length()  , Toast.LENGTH_SHORT).show();
                        if(Choufounis.length()>0) {
                            ChoufouniManager choufouniManager = new ChoufouniManager(context);
                            for (int i = 0; i < Choufounis.length(); i++) {
                                JSONObject unChoufouni = Choufounis.getJSONObject(i);
                                if (unChoufouni.getString("OPERATION").equals("DELETE")) {
                                    choufouniManager.delete(unChoufouni.getString(KEY_CHOUFOUNI_CODE));
                                    cptDelete++;
                                } else {
                                    choufouniManager.add(new Choufouni(unChoufouni));
                                    cptInsert++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CHOUFOUNI: Inserted: "+cptInsert +" Deleted: "+cptDelete ,"CHOUFOUNI",1));

                        }

                        //logM.add("Familles:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncFamille");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(context, "Famille : "+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("Famille:NOK "+errorMsg ,"SyncFamille");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CHOUFOUNI: Error: "+errorMsg ,"CHOUFOUNI",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "Famille : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("Famille:NOK "+e.getMessage() ,"SyncFamille");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CHOUFOUNI: Error: "+e.getMessage() ,"CHOUFOUNI",0));

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
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CHOUFOUNI: Error: "+error.getMessage() ,"CHOUFOUNI",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    ChoufouniManager choufouniManager = new ChoufouniManager(context);
                    List<Choufouni> choufounis = choufouniManager.getChoufouniCode_Version_List();
                    for (int i = 0; i < choufounis.size(); i++) {
                        Log.d("test famille-@-version",choufounis.get(i).getCHOUFOUNI_CODE()+"-@@-" +choufounis.get(i).getVERSION());
                        if(choufounis.get(i).getCHOUFOUNI_CODE()!=null && choufounis.get(i).getVERSION() != null ) {
                            params.put(choufounis.get(i).getCHOUFOUNI_CODE(), choufounis.get(i).getVERSION());
                        }
                    }
                }
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
