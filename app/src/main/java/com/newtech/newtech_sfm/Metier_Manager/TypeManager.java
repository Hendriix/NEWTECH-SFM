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
import com.newtech.newtech_sfm.Metier.LogSync;
import com.newtech.newtech_sfm.Metier.Type;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stagiaireit2 on 08/08/2016.
 */
public class TypeManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Article table name
    public static final String TABLE_TYPE = "type";

    //table tournee column
    private static final String
    KEY_TYPE_CODE="TYPE_CODE",
    KEY_TYPE_NOM="TYPE_NOM",
    KEY_TYPE_CATEGORIE="TYPE_CATEGORIE",
    KEY_DESCRIPTION="DESCRIPTION",
    KEY_VERSION="VERSION";

    public static  String CREATE_TYPE_TABLE = "CREATE TABLE " + TABLE_TYPE + "("
            +KEY_TYPE_CODE + " TEXT PRIMARY KEY,"
            +KEY_TYPE_NOM + " TEXT,"
            +KEY_TYPE_CATEGORIE + " TEXT,"
            +KEY_DESCRIPTION + " TEXT,"
            +KEY_VERSION + " TEXT "+ ")";

    public TypeManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAMILLE);
            db.execSQL(CREATE_TYPE_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database FAMILLE tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPE);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing Articles details in database
     * */
    public void add(Type type) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE_CODE, type.getTYPE_CODE());
        values.put(KEY_TYPE_NOM,type.getTYPE_NOM());
        values.put(KEY_TYPE_CATEGORIE, type.getTYPE_CATEGORIE());
        values.put(KEY_DESCRIPTION, type.getDESCRIPTION());
        values.put(KEY_VERSION, type.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_TYPE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New types inserted into sqlite: " + id);
    }

    public Type get( String TYPE_CODE) {

        String selectQuery = "SELECT * FROM " + TABLE_TYPE + " WHERE "+ KEY_TYPE_CODE +" = '"+TYPE_CODE+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Type type = new Type();

        if( cursor != null && cursor.moveToFirst() ) {

            type.setTYPE_CODE(cursor.getString(0));
            type.setTYPE_NOM(cursor.getString(1));
            type.setTYPE_CATEGORIE(cursor.getString(2));
            type.setDESCRIPTION(cursor.getString(3));
            type.setVERSION(cursor.getString(4));

        }

        cursor.close();
        db.close();
        return type;

    }

    /**
     * Getting all tournee from database
     * */

    public ArrayList<Type> getList() {
        ArrayList<Type> types= new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_TYPE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Type type = new Type();
                type.setTYPE_CODE(cursor.getString(0));
                type.setTYPE_NOM(cursor.getString(1));
                type.setTYPE_CATEGORIE(cursor.getString(2));
                type.setDESCRIPTION(cursor.getString(3));
                type.setVERSION(cursor.getString(4));

                types.add(type);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching all type from Sqlite: ");
        return types;
    }

    public ArrayList<Type> getListByCatCode(String categorie_code) {
        ArrayList<Type> types= new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_TYPE + " WHERE "+ KEY_TYPE_CATEGORIE +" = '"+categorie_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Type type = new Type();
                type.setTYPE_CODE(cursor.getString(0));
                type.setTYPE_NOM(cursor.getString(1));
                type.setTYPE_CATEGORIE(cursor.getString(2));
                type.setDESCRIPTION(cursor.getString(3));
                type.setVERSION(cursor.getString(4));

                types.add(type);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching all type from Sqlite: ");
        return types;
    }

    public ArrayList<Type> getTypeCode_Version_List() {
        ArrayList<Type> types = new ArrayList<>();

        String selectQuery = "SELECT "+ KEY_TYPE_CODE+" , " +KEY_VERSION +  " FROM " + TABLE_TYPE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Type type = new Type();
                type.setTYPE_CODE(cursor.getString(0));
                type.setVERSION(cursor.getString(1));
                types.add(type);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version  types from Sqlite: ");
        return types;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteTypes() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_TYPE, null, null);
        db.close();
        Log.d(TAG, "Deleted all Types info from sqlite");
    }

    public int delete(String typeCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TYPE,KEY_TYPE_CODE+"=?",new String[]{typeCode});
    }


    public ArrayList<Type> getType_textList() {
        ArrayList<Type> types = new ArrayList<>();
            String selectQuery = "SELECT "+ KEY_TYPE_CODE+ " , "+ KEY_TYPE_NOM+  " FROM " + TABLE_TYPE;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if( cursor != null && cursor.moveToFirst() ) {
                do {
                    Type type = new Type();
                    type.setTYPE_CODE(cursor.getString(0));
                    type.setTYPE_NOM(cursor.getString(1));
                    types.add(type);
                }while(cursor.moveToNext());
            }
            //returner la listTournees;
            cursor.close();
            db.close();
            Log.d(TAG, "Fetching code/version types from Sqlite: ");
            return types;
        }


    public static void synchronisationType(final Context context){

        String tag_string_req = "TYPE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_TYPE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Type", "onResponse: "+response);
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray Types = jObj.getJSONArray("Types");
                        //Toast.makeText(context, "nombre de Types  "+Types.length()  , Toast.LENGTH_SHORT).show();
                        if(Types.length()>0) {
                            TypeManager typeManager = new TypeManager(context);
                            for (int i = 0; i < Types.length(); i++) {
                                JSONObject uneType = Types.getJSONObject(i);
                                if (uneType.getString("OPERATION").equals("DELETE")) {
                                    typeManager.delete(uneType.getString(KEY_TYPE_CODE));
                                    cptDelete++;
                                } else {
                                    typeManager.add(new Type(uneType));cptInsert++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("TYPE: Inserted: "+cptInsert +" Deleted: "+cptDelete ,"TYPE",1));

                        }

                        //logM.add("Types:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncType");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(context, "Type : "+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("Type:NOK "+errorMsg ,"SyncType");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("TYPE: Error: "+errorMsg ,"TYPE",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "Type : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("Type:NOK "+e.getMessage() ,"SyncType");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("TYPE: Error: "+e.getMessage() ,"TYPE",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Type : "+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("Type:NOK "+error.getMessage() ,"SyncType");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("TYPE: Error: "+error.getMessage() ,"TYPE",0));

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    TypeManager typeManager = new TypeManager(context);
                    List<Type> types =typeManager.getTypeCode_Version_List();
                    for (int i = 0; i < types.size(); i++) {
                        Log.d("test types-@-version",types.get(i).getTYPE_CODE()+"-@@-" +types.get(i).getVERSION());
                        if(types.get(i).getTYPE_CODE()!=null && types.get(i).getVERSION() != null ) {
                            params.put(types.get(i).getTYPE_CODE(), types.get(i).getVERSION());
                        }
                    }
                }
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}


