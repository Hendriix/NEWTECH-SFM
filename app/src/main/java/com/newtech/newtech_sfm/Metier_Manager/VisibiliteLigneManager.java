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
import com.newtech.newtech_sfm.Metier.VisibiliteLigne;

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
public class VisibiliteLigneManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_VISIBILITE_LIGNE = "visibiliteligne";



    private static final String
            KEY_VISIBILITE_CODE = "VISIBILITE_CODE",
            KEY_VISIBILITE_LIGNE_CODE = "VISIBILITELIGNE_CODE",
            KEY_ARTICLE_CODE = "ARTICLE_CODE",
            KEY_FAMILLE_CODE = "FAMILLE_CODE",
            KEY_VISIBILITE = "VISIBILITE",
            KEY_REFERENCEMENT = "REFERENCEMENT",
            KEY_PROMOTION = "PROMOTION",
            KEY_ROTATION = "ROTATION",
            KEY_POSITION = "POSITION",
            KEY_PRIX = "PRIX",
            KEY_STOCK_RAYON = "STOCK_RAYON",
            KEY_RUPTURE_RAYON = "RUPTURE_RAYON",
            KEY_STOCK_MAGASIN = "STOCK_MAGASIN",
            KEY_RUPTURE_MAGASIN = "RUPTURE_MAGASIN",
            KEY_COMMENTAIRE = "COMMENTAIRE",
            KEY_COMMENTAIRE_LIGNE = "COMMENTAIRE_LIGNE",
            KEY_VERSION = "VERSION";

    public static String CREATE_VISIBILITE_LIGNE_TABLE = "CREATE TABLE " + TABLE_VISIBILITE_LIGNE + "("
            + KEY_VISIBILITE_CODE +" TEXT, "
            + KEY_VISIBILITE_LIGNE_CODE +" NUMERIC, "
            + KEY_ARTICLE_CODE +" TEXT, "
            + KEY_FAMILLE_CODE +" TEXT, "
            + KEY_VISIBILITE +" NUMERIC, "
            + KEY_REFERENCEMENT +" NUMERIC, "
            + KEY_PROMOTION + " TEXT,"
            + KEY_ROTATION + " TEXT,"
            + KEY_POSITION + " NUMERIC,"
            + KEY_PRIX + " NUMERIC,"
            + KEY_STOCK_RAYON + " NUMERIC,"
            + KEY_RUPTURE_RAYON + " NUMERIC,"
            + KEY_STOCK_MAGASIN + " NUMERIC,"
            + KEY_RUPTURE_MAGASIN + " NUMERIC,"
            + KEY_COMMENTAIRE +" TEXT, "
            + KEY_COMMENTAIRE_LIGNE +" TEXT, "
            + KEY_VERSION +" TEXT "+")";

    public VisibiliteLigneManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE);
            db.execSQL(CREATE_VISIBILITE_LIGNE_TABLE);
        } catch (SQLException e) {
        }
        Log.d(TAG, "table VisibiliteLigne created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VISIBILITE_LIGNE);
        onCreate(db);
    }

    public void add(VisibiliteLigne visibiliteLigne) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "add: "+visibiliteLigne.toString());
        ContentValues values = new ContentValues();
        values.put(KEY_VISIBILITE_CODE,visibiliteLigne.getVISIBILITE_CODE());
        values.put(KEY_VISIBILITE_LIGNE_CODE,visibiliteLigne.getVISIBILITE_LIGNE_CODE());
        values.put(KEY_ARTICLE_CODE,visibiliteLigne.getARTICLE_CODE());
        values.put(KEY_FAMILLE_CODE,visibiliteLigne.getFAMILLE_CODE());
        values.put(KEY_VISIBILITE,visibiliteLigne.getVISIBILITE());
        values.put(KEY_REFERENCEMENT,visibiliteLigne.getREFERENCEMENT());
        values.put(KEY_PROMOTION,visibiliteLigne.getPROMOTION());
        values.put(KEY_ROTATION,visibiliteLigne.getROTATION());
        values.put(KEY_POSITION,visibiliteLigne.getPOSITION());
        values.put(KEY_PRIX,visibiliteLigne.getPRIX());
        values.put(KEY_STOCK_RAYON,visibiliteLigne.getSTOCK_RAYON());
        values.put(KEY_RUPTURE_RAYON,visibiliteLigne.getRUPTURE_RAYON());
        values.put(KEY_STOCK_MAGASIN,visibiliteLigne.getSTOCK_MAGASIN());
        values.put(KEY_RUPTURE_MAGASIN,visibiliteLigne.getRUPTURE_MAGASIN());
        values.put(KEY_COMMENTAIRE,visibiliteLigne.getCOMMENTAIRE());
        values.put(KEY_COMMENTAIRE_LIGNE,visibiliteLigne.getCOMMENTAIRE_LIGNE());
        values.put(KEY_VERSION,visibiliteLigne.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_VISIBILITE_LIGNE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d(TAG, "nouvelle visibiliteLigne inseré dans la table VisibiliteLigne: " + id);
    }

    public int delete(String visibiliteLigneCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_VISIBILITE_LIGNE,KEY_VISIBILITE_LIGNE_CODE+"=?",new String[]{visibiliteLigneCode});
    }

    public void updateVisibiliteLigne(String visibiliteCode,String visibiliteLigneCode,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_VISIBILITE_LIGNE + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_VISIBILITE_LIGNE_CODE +"= '"+visibiliteLigneCode+"' AND "+KEY_VISIBILITE_CODE +"= '"+visibiliteCode+"'" ;
        db.execSQL(req);
        db.close();

        Log.d("visibiliteLigne", "updateVisibiliteLigne: "+req);
    }

    public void deleteVisibiliteLigneSynchronisee() {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefVisibiliteLigneAS = sdf.format(new Date());
        Log.d(TAG, "deleteVisibiliteLigneSynchronisee: "+DatefVisibiliteLigneAS);

        String Where = " "+KEY_COMMENTAIRE+"='inserted' ";
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_VISIBILITE_LIGNE, Where, null);
        db.close();
        Log.d("visibiliteLigne", "Deleted all visibiliteLignes verifiee from sqlite");
        Log.d("visibiliteLigne", "deleteVisibiliteLigneSynchronisee: "+Where);
    }


    public ArrayList<VisibiliteLigne> getList() {

            ArrayList<VisibiliteLigne> visibiliteLignes = new ArrayList<>();

            String selectQuery = "SELECT  * FROM " + TABLE_VISIBILITE_LIGNE;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if( cursor != null && cursor.moveToFirst() ) {
                do {

                    VisibiliteLigne visibiliteLigne = new VisibiliteLigne();
                    visibiliteLigne.setVISIBILITE_CODE(cursor.getString(0));
                    visibiliteLigne.setVISIBILITE_LIGNE_CODE(cursor.getInt(1));
                    visibiliteLigne.setARTICLE_CODE(cursor.getString(2));
                    visibiliteLigne.setFAMILLE_CODE(cursor.getString(3));
                    visibiliteLigne.setVISIBILITE(cursor.getInt(4));
                    visibiliteLigne.setREFERENCEMENT(cursor.getInt(5));
                    visibiliteLigne.setPROMOTION(cursor.getString(6));
                    visibiliteLigne.setROTATION(cursor.getString(7));
                    visibiliteLigne.setPOSITION(cursor.getInt(8));
                    visibiliteLigne.setPRIX(cursor.getDouble(9));
                    visibiliteLigne.setSTOCK_RAYON(cursor.getDouble(10));
                    visibiliteLigne.setRUPTURE_RAYON(cursor.getInt(11));
                    visibiliteLigne.setSTOCK_MAGASIN(cursor.getDouble(12));
                    visibiliteLigne.setRUPTURE_MAGASIN(cursor.getInt(13));
                    visibiliteLigne.setCOMMENTAIRE(cursor.getString(14));
                    visibiliteLigne.setCOMMENTAIRE_LIGNE(cursor.getString(15));
                    visibiliteLigne.setVERSION(cursor.getString(16));

                    visibiliteLignes.add(visibiliteLigne);
                }while(cursor.moveToNext());
            }
            //returner la listArticles;
            cursor.close();
        db.close();
        Log.d(TAG, "Fetching VisibiliteLigne from table VisibiliteLigne: ");
        Log.d(TAG, "Nombre VisibiliteLigne from table visibiliteLigne: "+visibiliteLignes.size());
        return visibiliteLignes;
    }

    public ArrayList<VisibiliteLigne> getListNotInserted() {
        ArrayList<VisibiliteLigne> visibiliteLignes = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_VISIBILITE_LIGNE +" WHERE "+KEY_COMMENTAIRE+" = 'to_insert' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                VisibiliteLigne visibiliteLigne = new VisibiliteLigne();

                visibiliteLigne.setVISIBILITE_CODE(cursor.getString(0));
                visibiliteLigne.setVISIBILITE_LIGNE_CODE(cursor.getInt(1));
                visibiliteLigne.setARTICLE_CODE(cursor.getString(2));
                visibiliteLigne.setFAMILLE_CODE(cursor.getString(3));
                visibiliteLigne.setVISIBILITE(cursor.getInt(4));
                visibiliteLigne.setREFERENCEMENT(cursor.getInt(5));
                visibiliteLigne.setPROMOTION(cursor.getString(6));
                visibiliteLigne.setROTATION(cursor.getString(7));
                visibiliteLigne.setPOSITION(cursor.getInt(8));
                visibiliteLigne.setPRIX(cursor.getDouble(9));
                visibiliteLigne.setSTOCK_RAYON(cursor.getDouble(10));
                visibiliteLigne.setRUPTURE_RAYON(cursor.getInt(11));
                visibiliteLigne.setSTOCK_MAGASIN(cursor.getDouble(12));
                visibiliteLigne.setRUPTURE_MAGASIN(cursor.getInt(13));
                visibiliteLigne.setCOMMENTAIRE(cursor.getString(14));
                visibiliteLigne.setCOMMENTAIRE_LIGNE(cursor.getString(15));
                visibiliteLigne.setVERSION(cursor.getString(16));

                Log.d(TAG, "getListNotInserted: "+visibiliteLigne.toString());
                visibiliteLignes.add(visibiliteLigne);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching VisibiliteLigne from table VisibiliteLigne: ");
        Log.d(TAG, "Nombre VisibiliteLigne from table visibiliteLigne: "+visibiliteLignes.size());
        return visibiliteLignes;
    }


    public void deleteVisibiliteLignes() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_VISIBILITE_LIGNE, null, null);
        db.close();
        Log.d(TAG, "Deleted all visibiliteLignes info from sqlite");
    }

    public static void synchronisationVisibiliteLigne(final Context context){

        VisibiliteLigneManager visibiliteLigneManager = new VisibiliteLigneManager(context);
        visibiliteLigneManager.deleteVisibiliteLigneSynchronisee();

        String tag_string_req = "VISBILITE_LIGNE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_VISIBILITE_LIGNE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                Log.d(TAG, "onResponse: "+response);
                int cptInsert = 0,cptDelete = 0;
                try {
                  JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray visibiliteLignes = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre de visibiliteLignes  "+visibiliteLignes.length() , Toast.LENGTH_LONG).show();
                       // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les VisibiliteLignes : " +response);

                        if(visibiliteLignes.length()>0) {
                            VisibiliteLigneManager visibiliteLigneManager = new VisibiliteLigneManager(context);
                            for (int i = 0; i < visibiliteLignes.length(); i++) {
                                JSONObject uneVisibiliteLigne = visibiliteLignes.getJSONObject(i);
                                if (uneVisibiliteLigne.getString("Statut").equals("true")) {
                                    visibiliteLigneManager.updateVisibiliteLigne((uneVisibiliteLigne.getString("VISIBILITE_CODE")),(uneVisibiliteLigne.getString("VISIBILITE_LIGNE_CODE")),"inserted");
                                    cptInsert++;
                                }else{
                                    cptDelete++;
                                }
                            }
                        }

                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("VISBILITE_LIGNE: Inserted: "+cptInsert +" NotInserted: "+cptDelete ,"VISBILITE_LIGNE",1));

                        }


                        //logM.add("VisibiliteLigne:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncVisibiliteLigne");


                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "visibiliteLigne : "+errorMsg, Toast.LENGTH_LONG).show();

                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("VISBILITE_LIGNE: Error: "+errorMsg ,"VISBILITE_LIGNE",0));

                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "visibiliteLigne : "+"Json error: " +"erreur applcation visibiliteLigne" + e.getMessage(), Toast.LENGTH_LONG).show();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("VISBILITE_LIGNE: Error: "+e.getMessage() ,"VISBILITE_LIGNE",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "visibiliteLigne : "+error.getMessage(), Toast.LENGTH_LONG).show();
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("VISBILITE_LIGNE: Error: "+error.getMessage() ,"VISBILITE_LIGNE",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    VisibiliteLigneManager visibiliteLigneManager  = new VisibiliteLigneManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableArticle");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(visibiliteLigneManager.getListNotInserted()));
                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    public boolean checkIfEmpty(ArrayList<VisibiliteLigne> visibiliteLignes){
        Boolean result = true;
        for(int i=0; i<visibiliteLignes.size();i++){

            if(getBooleanFromInt(visibiliteLignes.get(i).getVISIBILITE())== true || getBooleanFromInt(visibiliteLignes.get(i).getREFERENCEMENT()) == true){
                result = false;
                break;
            }
        }
        return result;
    }

    public boolean getBooleanFromInt(int value){
        boolean checked = false;

        if(value == 1){
            checked = true;
        }

        return checked;
    }




}
