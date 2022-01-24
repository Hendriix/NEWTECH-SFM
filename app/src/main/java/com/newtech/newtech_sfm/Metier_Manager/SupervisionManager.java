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
import com.newtech.newtech_sfm.Metier.Supervision;

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
public class SupervisionManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_SUPERVISION = "supervision";



    private static final String

            KEY_ID="ID",
            KEY_UTILISATEUR_CODE="UTILISATEUR_CODE",
            KEY_IMEI="IMEI",
            KEY_DATE_ACTION="DATE_ACTION",
            KEY_VERSION_CODE = "VERSION_CODE",
            KEY_VERSION_NOM = "VERSION_NOM",
            KEY_IP = "IP",
            KEY_CONNECTED= "CONNECTED",
            KEY_PRINTER_CONNECTED= "PRINTER_CONNECTED",
            KEY_LAST_COMMANDE= "LAST_COMMANDE",
            KEY_LAST_VISITE= "LAST_VISITE",
            KEY_COMMENTAIRE= "COMMENTAIRE"

            ;

    public static String CREATE_SUPERVISION_TABLE = "CREATE TABLE " + TABLE_SUPERVISION+ " ("

            +KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +KEY_UTILISATEUR_CODE +" TEXT,"
            +KEY_IMEI +" TEXT,"
            +KEY_DATE_ACTION +" TEXT,"
            +KEY_VERSION_CODE +" TEXT,"
            +KEY_VERSION_NOM +" TEXT,"
            +KEY_IP +" TEXT,"
            +KEY_CONNECTED +" INTEGER,"
            +KEY_PRINTER_CONNECTED +" TEXT,"
            +KEY_LAST_COMMANDE +" TEXT,"
            +KEY_LAST_VISITE +" INTEGER,"
            +KEY_COMMENTAIRE +" TEXT"+ ")";

    public SupervisionManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE);
            db.execSQL(CREATE_SUPERVISION_TABLE);
        } catch (SQLException e) {
        }
        Log.d(TAG, "table Supervision created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUPERVISION);
        onCreate(db);
    }

    public void add(Supervision supervision) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "add: "+supervision.toString());
        ContentValues values = new ContentValues();
        values.put(KEY_UTILISATEUR_CODE,supervision.getUTILISATEUR_CODE() );
        values.put(KEY_IMEI, supervision.getIMEI());
        values.put(KEY_DATE_ACTION, supervision.getDATE_ACTION());
        values.put(KEY_VERSION_CODE, supervision.getVERSION_CODE());
        values.put(KEY_VERSION_NOM, supervision.getVERSION_NOM());
        values.put(KEY_IP, supervision.getIP());
        values.put(KEY_CONNECTED, supervision.getCONNECTED());
        values.put(KEY_PRINTER_CONNECTED, supervision.getPRINTER_CONNECTED());
        values.put(KEY_LAST_COMMANDE, supervision.getLAST_COMMANDE());
        values.put(KEY_LAST_VISITE, supervision.getLAST_VISITE());
        values.put(KEY_COMMENTAIRE, supervision.getCOMMENTAIRE());


        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_SUPERVISION, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database supervision
        Log.d(TAG, "nouvelle supervision inseré dans la table Supervision: " + id);
    }

    public int delete(String imei, String ts) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_SUPERVISION,KEY_IMEI+"=?",new String[]{imei});
    }

    public void updateSupervision(String imei,String ts,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_SUPERVISION + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_IMEI +"= '"+imei+"'" ;
        db.execSQL(req);
        db.close();

        Log.d("Supervision", "updateSupervision: "+req);
    }

    public void deleteSupervisionSynchronisee() {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefCmdAS = sdf.format(new Date());
        Log.d(TAG, "deleteSupervisionSynchronisee: "+DatefCmdAS);

        String Where = " "+KEY_COMMENTAIRE+"='inserted' and date("+KEY_DATE_ACTION+")!='"+DatefCmdAS+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_SUPERVISION, Where, null);
        db.close();
        Log.d("supervision", "Deleted all supervisions verifiee from sqlite");
        Log.d("supervision", "deleteSupervisionSynchronisee: "+Where);
    }


    public ArrayList<Supervision> getList() {
            ArrayList<Supervision> supervisions = new ArrayList<>();

            String selectQuery = "SELECT  * FROM " + TABLE_SUPERVISION;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if( cursor != null && cursor.moveToFirst() ) {
                do {
                    Supervision supervision = new Supervision();
                    supervision.setID(cursor.getInt(0));
                    supervision.setUTILISATEUR_CODE(cursor.getString(1));
                    supervision.setIMEI(cursor.getString(2));
                    supervision.setDATE_ACTION(cursor.getString(3));
                    supervision.setVERSION_CODE((cursor.getString(4)));
                    supervision.setVERSION_NOM((cursor.getString(5)));
                    supervision.setIP((cursor.getString(6)));
                    supervision.setCONNECTED((cursor.getInt(7)));
                    supervision.setPRINTER_CONNECTED((cursor.getInt(8)));
                    supervision.setLAST_COMMANDE((cursor.getString(9)));
                    supervision.setLAST_VISITE((cursor.getString(10)));
                    supervision.setCOMMENTAIRE((cursor.getString(11)));

                    supervisions.add(supervision);
                }while(cursor.moveToNext());
            }
            //returner la listArticles;
            cursor.close();
        db.close();
        Log.d(TAG, "Fetching Supervision from table Supervision: ");
        Log.d(TAG, "Nombre Supervision from table Supervision: "+supervisions.size());
        return supervisions;
    }

    public ArrayList<Supervision> getListNotInserted() {
        ArrayList<Supervision> supervisions = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_SUPERVISION +" WHERE "+KEY_COMMENTAIRE+" = 'to_insert' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Supervision supervision = new Supervision();
                supervision.setID(cursor.getInt(0));
                supervision.setUTILISATEUR_CODE(cursor.getString(1));
                supervision.setIMEI(cursor.getString(2));
                supervision.setDATE_ACTION(cursor.getString(3));
                supervision.setVERSION_CODE((cursor.getString(4)));
                supervision.setVERSION_NOM((cursor.getString(5)));
                supervision.setIP((cursor.getString(6)));
                supervision.setCONNECTED((cursor.getInt(7)));
                supervision.setPRINTER_CONNECTED((cursor.getInt(8)));
                supervision.setLAST_COMMANDE((cursor.getString(9)));
                supervision.setLAST_VISITE((cursor.getString(10)));
                supervision.setCOMMENTAIRE((cursor.getString(11)));

                Log.d(TAG, "getListNotInserted: "+supervision.toString());
                supervisions.add(supervision);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Supervision from table Supervision: ");
        Log.d(TAG, "Nombre Supervision from table Supervision: "+supervisions.size());
        return supervisions;
    }

    public void deleteSupervisions() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_SUPERVISION, null, null);
        db.close();
        Log.d(TAG, "Deleted all supervisions info from sqlite");
    }

    public static void synchronisationSupervision(final Context context){

        final SupervisionManager supervisionManager = new SupervisionManager(context);
        supervisionManager.deleteSupervisionSynchronisee();

        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_COMMANDE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                  JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray supervisions = jObj.getJSONArray("result");
                        Toast.makeText(context, "Nombre de supervisions  "+supervisions.length() , Toast.LENGTH_LONG).show();
                       // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les supervisions : " +response);

                        if(supervisions.length()>0) {
                            SupervisionManager supervisionManager = new SupervisionManager(context);
                            for (int i = 0; i < supervisions.length(); i++) {
                                JSONObject uneSupervision = supervisions.getJSONObject(i);
                                if (uneSupervision.getString("Statut").equals("true")) {
                                    supervisionManager.updateSupervision(uneSupervision.getString("IMEI"),uneSupervision.getString("DATE_ACTION"),"inserted");
                                }
                            }
                        }
                        logM.add("Supervision:OK Insert:"+cptInsert +"Delete:"+cptDelete ,"SynchSupervision");


                    }else {
                        String errorMsg = jObj.getString("info");
                        Toast.makeText(context,
                                "supervision : "+errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "supervision : "+"Json error: " +"erreur applcation supervision" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        "supervision : "+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    SupervisionManager supervisionManager  = new SupervisionManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    //TabParams.put("Table_Name","tableArticle");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    //arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(supervisionManager.getListNotInserted()));
                    Log.d("SupervisionSynch", "Liste: "+supervisionManager.getListNotInserted());
                    Log.d(TAG, "getParams: listnotinserted"+supervisionManager.getListNotInserted().size());
                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
