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
import com.newtech.newtech_sfm.Metier.Connection;

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
public class ConnectionManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_CONNECTION = "connection";



    private static final String

            KEY_ID="ID",
            KEY_UTILISATEUR_CODE="UTILISATEUR_CODE",
            KEY_IMEI="IMEI",
            KEY_DATE_ACTION="DATE_ACTION",
            KEY_VERSION_CODE = "VERSION_CODE",
            KEY_VERSION_NOM = "VERSION_NOM",
            KEY_CONNECTED = "CONNECTED",
            KEY_COMMENTAIRE= "COMMENTAIRE"

            ;

    public static String CREATE_CONNECTION_TABLE = "CREATE TABLE " + TABLE_CONNECTION+ " ("

            +KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +KEY_UTILISATEUR_CODE +" TEXT,"
            +KEY_IMEI +" TEXT,"
            +KEY_DATE_ACTION +" TEXT,"
            +KEY_VERSION_CODE +" TEXT,"
            +KEY_VERSION_NOM +" TEXT,"
            +KEY_CONNECTED +" INTEGER,"
            +KEY_COMMENTAIRE +" TEXT"+ ")";

    public ConnectionManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE);
            db.execSQL(CREATE_CONNECTION_TABLE);
        } catch (SQLException e) {
        }
        Log.d(TAG, "table Connection created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONNECTION);
        onCreate(db);
    }

    public void add(Connection connection) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "add: "+connection.toString());
        ContentValues values = new ContentValues();
        values.put(KEY_UTILISATEUR_CODE,connection.getUTILISATEUR_CODE() );
        values.put(KEY_IMEI, connection.getIMEI());
        values.put(KEY_DATE_ACTION, connection.getDATE_ACTION());
        values.put(KEY_VERSION_CODE, connection.getVERSION_CODE());
        values.put(KEY_VERSION_NOM, connection.getVERSION_NOM());
        values.put(KEY_CONNECTED, connection.getCONNECTED());
        values.put(KEY_COMMENTAIRE, connection.getCOMMENTAIRE());


        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_CONNECTION, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d(TAG, "nouvelle connection inseré dans la table Connection: " + id);
    }

    public int delete(String imei, String ts) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CONNECTION,KEY_IMEI+"=?",new String[]{imei});
    }

    public void updateConnection(String imei,String ts,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_CONNECTION + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_IMEI +"= '"+imei+"'" ;
        db.execSQL(req);
        db.close();

        Log.d("Connection", "updateConnection: "+req);
    }

    public void deleteConnectionSynchronisee() {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefCmdAS = sdf.format(new Date());
        Log.d(TAG, "deleteConnectionSynchronisee: "+DatefCmdAS);

        String Where = " "+KEY_COMMENTAIRE+"='inserted' and date("+KEY_DATE_ACTION+")!='"+DatefCmdAS+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_CONNECTION, Where, null);
        db.close();
        Log.d("connection", "Deleted all connections verifiee from sqlite");
        Log.d("connection", "deleteConnectionSynchronisee: "+Where);
    }


    public ArrayList<Connection> getList() {
            ArrayList<Connection> connections = new ArrayList<>();

            String selectQuery = "SELECT  * FROM " + TABLE_CONNECTION;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if( cursor != null && cursor.moveToFirst() ) {
                do {
                    Connection connection = new Connection();
                    connection.setID(cursor.getInt(0));
                    connection.setUTILISATEUR_CODE(cursor.getString(1));
                    connection.setIMEI(cursor.getString(2));
                    connection.setDATE_ACTION(cursor.getString(3));
                    connection.setVERSION_CODE((cursor.getString(4)));
                    connection.setVERSION_NOM((cursor.getString(5)));
                    connection.setCONNECTED((cursor.getInt(6)));
                    connection.setCOMMENTAIRE((cursor.getString(7)));

                    connections.add(connection);
                }while(cursor.moveToNext());
            }
            //returner la listArticles;
            cursor.close();
        db.close();
        Log.d(TAG, "Fetching Connection from table Connection: ");
        Log.d(TAG, "Nombre Connection from table Connection: "+connections.size());
        return connections;
    }

    public ArrayList<Connection> getListNotInserted() {
        ArrayList<Connection> connections = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CONNECTION +" WHERE "+KEY_COMMENTAIRE+" = 'to_insert' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Connection connection = new Connection();
                connection.setID(cursor.getInt(0));
                connection.setUTILISATEUR_CODE(cursor.getString(1));
                connection.setIMEI(cursor.getString(2));
                connection.setDATE_ACTION(cursor.getString(3));
                connection.setVERSION_CODE((cursor.getString(4)));
                connection.setVERSION_NOM((cursor.getString(5)));
                connection.setCONNECTED((cursor.getInt(6)));
                connection.setCOMMENTAIRE((cursor.getString(7)));

                Log.d(TAG, "getListNotInserted: "+connection.toString());
                connections.add(connection);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Connection from table Connection: ");
        Log.d(TAG, "Nombre Connection from table Connection: "+connections.size());
        return connections;
    }

    public void deleteConnections() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_CONNECTION, null, null);
        db.close();
        Log.d(TAG, "Deleted all connections info from sqlite");
    }

    public static void synchronisationConnection(final Context context){

        final ConnectionManager connectionManager = new ConnectionManager(context);
        connectionManager.deleteConnectionSynchronisee();

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
                        JSONArray connections = jObj.getJSONArray("result");
                        Toast.makeText(context, "Nombre de connections  "+connections.length() , Toast.LENGTH_LONG).show();
                       // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les connections : " +response);

                        if(connections.length()>0) {
                            ConnectionManager connectionManager = new ConnectionManager(context);
                            for (int i = 0; i < connections.length(); i++) {
                                JSONObject uneConnection = connections.getJSONObject(i);
                                if (uneConnection.getString("Statut").equals("true")) {
                                    connectionManager.updateConnection(uneConnection.getString("IMEI"),uneConnection.getString("DATE_ACTION"),"inserted");
                                }
                            }
                        }
                        logM.add("Connection:OK Insert:"+cptInsert +"Delete:"+cptDelete ,"SynchConnection");


                    }else {
                        String errorMsg = jObj.getString("info");
                        Toast.makeText(context,
                                "connection : "+errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "connection : "+"Json error: " +"erreur applcation connection" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        "connection : "+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    ConnectionManager connectionManager  = new ConnectionManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    //TabParams.put("Table_Name","tableArticle");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    //arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(connectionManager.getListNotInserted()));
                    Log.d("ConnectionSynch", "Liste: "+connectionManager.getListNotInserted());
                    Log.d(TAG, "getParams: listnotinserted"+connectionManager.getListNotInserted().size());
                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
