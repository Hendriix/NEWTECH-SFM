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
import com.newtech.newtech_sfm.Metier.Visibilite;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stagiaireit2 on 02/08/2016.
 */
public class VisibiliteManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_VISIBILITE = "visibilite";



    private static final String
            KEY_VISIBILITE_CODE = "VISIBILITE_CODE",
            KEY_VISITE_CODE = "VISITE_CODE",
            KEY_CLIENT_CODE = "CLIENT_CODE",
            KEY_TOURNEE_CODE = "TOURNEE_CODE",
            KEY_DATE_VISIBILITE = "DATE_VISIBILITE",
            KEY_DISTRIBUTEUR_CODE = "DISTRIBUTEUR_CODE",
            KEY_UTILISATEUR_CODE = "UTILISATEUR_CODE",
            KEY_CIRCUIT_CODE = "CIRCUIT_CODE",
            KEY_TYPE_CODE = "TYPE_CODE",
            KEY_STATUT_CODE = "STATUT_CODE",
            KEY_CATEGORIE_CODE = "CATEGORIE_CODE",
            KEY_DATE_CREATION = "DATE_CREATION",
            KEY_CREATEUR_CODE = "CREATEUR_CODE",
            KEY_COMMENTAIRE = "COMMENTAIRE",
            KEY_VERSION = "VERSION";

    public static String CREATE_VISIBILITE_TABLE = "CREATE TABLE " + TABLE_VISIBILITE + "("
            + KEY_VISIBILITE_CODE +" TEXT PRIMARY KEY, "
            + KEY_VISITE_CODE +" TEXT, "
            + KEY_CLIENT_CODE +" TEXT, "
            + KEY_TOURNEE_CODE +" TEXT, "
            + KEY_DATE_VISIBILITE +" TEXT,"
            + KEY_DISTRIBUTEUR_CODE +" TEXT,"
            + KEY_UTILISATEUR_CODE +" TEXT,"
            + KEY_CIRCUIT_CODE +" TEXT,"
            + KEY_TYPE_CODE +" TEXT,"
            + KEY_STATUT_CODE +" TEXT,"
            + KEY_CATEGORIE_CODE +" TEXT,"
            + KEY_DATE_CREATION +" TEXT,"
            + KEY_CREATEUR_CODE +" TEXT,"
            + KEY_COMMENTAIRE +" TEXT,"
            + KEY_VERSION +" TEXT "+")";

    public VisibiliteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE);
            db.execSQL(CREATE_VISIBILITE_TABLE);
        } catch (SQLException e) {
        }
        Log.d(TAG, "table Visibilite created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VISIBILITE);
        onCreate(db);
    }

    public void add(Visibilite visibilite) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "add: "+visibilite.toString());
        ContentValues values = new ContentValues();
        values.put(KEY_VISIBILITE_CODE,visibilite.getVISIBILITE_CODE());
        values.put(KEY_VISITE_CODE,visibilite.getVISITE_CODE());
        values.put(KEY_CLIENT_CODE,visibilite.getCLIENT_CODE());
        values.put(KEY_TOURNEE_CODE,visibilite.getTOURNEE_CODE());
        values.put(KEY_DATE_VISIBILITE,visibilite.getDATE_VISIBILITE());
        values.put(KEY_DISTRIBUTEUR_CODE,visibilite.getDISTRIBUTEUR_CODE());
        values.put(KEY_UTILISATEUR_CODE,visibilite.getUTILISATEUR_CODE());
        values.put(KEY_CIRCUIT_CODE,visibilite.getCIRCUIT_CODE());
        values.put(KEY_TYPE_CODE,visibilite.getTYPE_CODE());
        values.put(KEY_STATUT_CODE,visibilite.getSTATUT_CODE());
        values.put(KEY_CATEGORIE_CODE,visibilite.getCATEGORIE_CODE());
        values.put(KEY_DATE_CREATION,visibilite.getDATE_CREATION());
        values.put(KEY_CREATEUR_CODE,visibilite.getCREATEUR_CODE());
        values.put(KEY_COMMENTAIRE,visibilite.getCOMMENTAIRE());
        values.put(KEY_VERSION,visibilite.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_VISIBILITE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d(TAG, "nouvelle visibilite inseré dans la table Visibilite: " + id);
    }

    public int delete(String visibiliteCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_VISIBILITE,KEY_VISIBILITE_CODE+"=?",new String[]{visibiliteCode});
    }

    public void updateVisibilite(String visibiliteCode,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_VISIBILITE + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_VISIBILITE_CODE +"= '"+visibiliteCode+"'" ;
        db.execSQL(req);
        db.close();

        Log.d("visibilite", "updateVisibilite: "+req);
    }

    public void deleteVisibiliteSynchronisee() {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefCmdAS = sdf.format(new Date());
        Log.d(TAG, "deleteCmdSynchronisee: "+DatefCmdAS);

        String Where = " "+KEY_COMMENTAIRE+"='inserted' and date("+KEY_DATE_CREATION+")!='"+DatefCmdAS+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_VISIBILITE, Where, null);
        db.close();
        Log.d("visibilite", "Deleted all visibilites verifiee from sqlite");
        Log.d("visibilite", "deleteVisibiliteSynchronisee: "+Where);
    }


    public ArrayList<Visibilite> getList() {

            ArrayList<Visibilite> visibilites = new ArrayList<>();

            String selectQuery = "SELECT  * FROM " + TABLE_VISIBILITE;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if( cursor != null && cursor.moveToFirst() ) {
                do {

                    Visibilite visibilite = new Visibilite();
                    visibilite.setVISIBILITE_CODE(cursor.getString(0));
                    visibilite.setVISITE_CODE(cursor.getString(1));
                    visibilite.setCLIENT_CODE(cursor.getString(2));
                    visibilite.setTOURNEE_CODE(cursor.getString(3));
                    visibilite.setDATE_VISIBILITE(cursor.getString(4));
                    visibilite.setDISTRIBUTEUR_CODE(cursor.getString(5));
                    visibilite.setUTILISATEUR_CODE(cursor.getString(6));
                    visibilite.setCIRCUIT_CODE(cursor.getString(7));
                    visibilite.setTYPE_CODE(cursor.getString(8));
                    visibilite.setSTATUT_CODE(cursor.getString(9));
                    visibilite.setCATEGORIE_CODE(cursor.getString(10));
                    visibilite.setDATE_CREATION(cursor.getString(11));
                    visibilite.setCREATEUR_CODE(cursor.getString(12));
                    visibilite.setCOMMENTAIRE(cursor.getString(13));
                    visibilite.setVERSION(cursor.getString(14));

                    visibilites.add(visibilite);
                }while(cursor.moveToNext());
            }
            //returner la listArticles;
            cursor.close();
        db.close();
        Log.d(TAG, "Fetching Visibilite from table Visibilite: ");
        Log.d(TAG, "Nombre Visibilite from table visibilite: "+visibilites.size());
        return visibilites;
    }

    public ArrayList<Visibilite> getListNotInserted() {
        ArrayList<Visibilite> visibilites = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_VISIBILITE +" WHERE "+KEY_COMMENTAIRE+" = 'to_insert' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Visibilite visibilite = new Visibilite();
                visibilite.setVISIBILITE_CODE(cursor.getString(0));
                visibilite.setVISITE_CODE(cursor.getString(1));
                visibilite.setCLIENT_CODE(cursor.getString(2));
                visibilite.setTOURNEE_CODE(cursor.getString(3));
                visibilite.setDATE_VISIBILITE(cursor.getString(4));
                visibilite.setDISTRIBUTEUR_CODE(cursor.getString(5));
                visibilite.setUTILISATEUR_CODE(cursor.getString(6));
                visibilite.setCIRCUIT_CODE(cursor.getString(7));
                visibilite.setTYPE_CODE(cursor.getString(8));
                visibilite.setSTATUT_CODE(cursor.getString(9));
                visibilite.setCATEGORIE_CODE(cursor.getString(10));
                visibilite.setDATE_CREATION(cursor.getString(11));
                visibilite.setCREATEUR_CODE(cursor.getString(12));
                visibilite.setCOMMENTAIRE(cursor.getString(13));
                visibilite.setVERSION(cursor.getString(14));

                Log.d(TAG, "getListNotInserted: "+visibilite.toString());
                visibilites.add(visibilite);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Visibilite from table Visibilite: ");
        Log.d(TAG, "Nombre Visibilite from table visibilite: "+visibilites.size());
        return visibilites;
    }

    public ArrayList<Visibilite> getListByClientCode(String client_code) {
        ArrayList<Visibilite> visibilites = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_VISIBILITE +" WHERE "+ KEY_CLIENT_CODE +" = '"+client_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Visibilite visibilite = new Visibilite();
                visibilite.setVISIBILITE_CODE(cursor.getString(0));
                visibilite.setVISITE_CODE(cursor.getString(1));
                visibilite.setCLIENT_CODE(cursor.getString(2));
                visibilite.setTOURNEE_CODE(cursor.getString(3));
                visibilite.setDATE_VISIBILITE(cursor.getString(4));
                visibilite.setDISTRIBUTEUR_CODE(cursor.getString(5));
                visibilite.setUTILISATEUR_CODE(cursor.getString(6));
                visibilite.setCIRCUIT_CODE(cursor.getString(7));
                visibilite.setTYPE_CODE(cursor.getString(8));
                visibilite.setSTATUT_CODE(cursor.getString(9));
                visibilite.setCATEGORIE_CODE(cursor.getString(10));
                visibilite.setDATE_CREATION(cursor.getString(11));
                visibilite.setCREATEUR_CODE(cursor.getString(12));
                visibilite.setCOMMENTAIRE(cursor.getString(13));
                visibilite.setVERSION(cursor.getString(14));

                visibilites.add(visibilite);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Visibilite from table Visibilite: ");
        return visibilites;
    }

    public ArrayList<Visibilite> getListByDate(String date) {
        ArrayList<Visibilite> visibilites = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_VISIBILITE +" WHERE date("+KEY_DATE_VISIBILITE+") = '"+date+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Visibilite visibilite = new Visibilite();
                visibilite.setVISIBILITE_CODE(cursor.getString(0));
                visibilite.setVISITE_CODE(cursor.getString(1));
                visibilite.setCLIENT_CODE(cursor.getString(2));
                visibilite.setTOURNEE_CODE(cursor.getString(3));
                visibilite.setDATE_VISIBILITE(cursor.getString(4));
                visibilite.setDISTRIBUTEUR_CODE(cursor.getString(5));
                visibilite.setUTILISATEUR_CODE(cursor.getString(6));
                visibilite.setCIRCUIT_CODE(cursor.getString(7));
                visibilite.setTYPE_CODE(cursor.getString(8));
                visibilite.setSTATUT_CODE(cursor.getString(9));
                visibilite.setCATEGORIE_CODE(cursor.getString(10));
                visibilite.setDATE_CREATION(cursor.getString(11));
                visibilite.setCREATEUR_CODE(cursor.getString(12));
                visibilite.setCOMMENTAIRE(cursor.getString(13));
                visibilite.setVERSION(cursor.getString(14));

                visibilites.add(visibilite);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Visibilite from table Visibilite: ");
        return visibilites;
    }


    public void deleteVisibilites() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_VISIBILITE, null, null);
        db.close();
        Log.d(TAG, "Deleted all visibilites info from sqlite");
    }

    public static void synchronisationVisibilite(final Context context){

        VisibiliteManager visibiliteManager = new VisibiliteManager(context);
        visibiliteManager.deleteVisibiliteSynchronisee();

        String tag_string_req = "VISIBILITE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_VISIBILITE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                Log.d(TAG, "onResponse: "+response);
                try {
                  JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray visibilites = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre de visibilites  "+visibilites.length() , Toast.LENGTH_LONG).show();
                       // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les Visibilites : " +response);

                        if(visibilites.length()>0) {
                            VisibiliteManager visibiliteManager = new VisibiliteManager(context);
                            for (int i = 0; i < visibilites.length(); i++) {
                                JSONObject uneVisibilite = visibilites.getJSONObject(i);
                                if (uneVisibilite.getString("Statut").equals("true")) {
                                    visibiliteManager.updateVisibilite((uneVisibilite.getString("VISIBILITE_CODE")),"inserted");
                                    cptInsert++;
                                }else{
                                    cptDelete++;
                                }
                            }
                        }
                        //logM.add("Visibilite:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncVisibilite");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("VISIBILITE: Inserted: "+cptInsert +" NotInserted: "+cptDelete ,"VISIBILITE",1));

                        }



                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "visibilite : "+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("VISIBILITE: Error: "+errorMsg ,"VISIBILITE",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "visibilite : "+"Json error: " +"erreur applcation visibilite" + e.getMessage(), Toast.LENGTH_LONG).show();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("VISIBILITE: Error: "+e.getMessage() ,"VISIBILITE",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "visibilite : "+error.getMessage(), Toast.LENGTH_LONG).show();
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("VISIBILITE: Error: "+error.getMessage() ,"VISIBILITE",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    VisibiliteManager visibiliteManager  = new VisibiliteManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableArticle");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(visibiliteManager.getListNotInserted()));
                    Log.d("Visibilitesynch", "Liste: "+visibiliteManager.getListNotInserted());
                    Log.d(TAG, "getParams: listnotinserted"+visibiliteManager.getListNotInserted().size());
                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
