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
import com.newtech.newtech_sfm.Metier.Classe;
import com.newtech.newtech_sfm.Metier.LogSync;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stagiaireit2 on 08/08/2016.
 */
public class ClasseManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Article table name
    public static final String TABLE_CLASSE = "classe";

    //table tournee column
    private static final String
    KEY_CLASSE_CODE="CLASSE_CODE",
    KEY_CLASSE_NOM="CLASSE_NOM",
    KEY_CLASSE_CATEGORIE="CLASSE_CATEGORIE",
    KEY_DESCRIPTION="DESCRIPTION",
    KEY_VERSION="VERSION";

    public static  String CREATE_CLASSE_TABLE = "CREATE TABLE " + TABLE_CLASSE + "("
            +KEY_CLASSE_CODE + " TEXT PRIMARY KEY,"
            +KEY_CLASSE_NOM + " TEXT,"
            +KEY_CLASSE_CATEGORIE + " TEXT,"
            +KEY_DESCRIPTION + " TEXT,"
            +KEY_VERSION + " TEXT "+ ")";

    public ClasseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAMILLE);
            db.execSQL(CREATE_CLASSE_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database FAMILLE tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSE);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing Articles details in database
     * */
    public void add(Classe classe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CLASSE_CODE, classe.getCLASSE_CODE());
        values.put(KEY_CLASSE_NOM,classe.getCLASSE_NOM());
        values.put(KEY_CLASSE_CATEGORIE, classe.getCLASSE_CATEGORIE());
        values.put(KEY_DESCRIPTION, classe.getDESCRIPTION());
        values.put(KEY_VERSION, classe.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_CLASSE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New classes inserted into sqlite: " + id);
    }

    public Classe get( String CLASSE_CODE) {

        String selectQuery = "SELECT * FROM " + TABLE_CLASSE + " WHERE "+ KEY_CLASSE_CODE +" = '"+CLASSE_CODE+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Classe classe = new Classe();

        if( cursor != null && cursor.moveToFirst() ) {

            classe.setCLASSE_CODE(cursor.getString(0));
            classe.setCLASSE_NOM(cursor.getString(1));
            classe.setCLASSE_CATEGORIE(cursor.getString(2));
            classe.setDESCRIPTION(cursor.getString(3));
            classe.setVERSION(cursor.getString(4));

        }

        cursor.close();
        db.close();
        return classe;

    }

    /**
     * Getting all tournee from database
     * */

    public ArrayList<Classe> getList() {
        ArrayList<Classe> classes= new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CLASSE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Classe classe = new Classe();
                classe.setCLASSE_CODE(cursor.getString(0));
                classe.setCLASSE_NOM(cursor.getString(1));
                classe.setCLASSE_CATEGORIE(cursor.getString(2));
                classe.setDESCRIPTION(cursor.getString(3));
                classe.setVERSION(cursor.getString(4));

                classes.add(classe);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching all classe from Sqlite: ");
        return classes;
    }

    public ArrayList<Classe> getListByCateCode(String categorie_code) {
        ArrayList<Classe> classes= new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CLASSE + " WHERE "+ KEY_CLASSE_CATEGORIE +" = '"+categorie_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Classe classe = new Classe();
                classe.setCLASSE_CODE(cursor.getString(0));
                classe.setCLASSE_NOM(cursor.getString(1));
                classe.setCLASSE_CATEGORIE(cursor.getString(2));
                classe.setDESCRIPTION(cursor.getString(3));
                classe.setVERSION(cursor.getString(4));

                classes.add(classe);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching all classe from Sqlite: ");
        return classes;
    }


    public ArrayList<Classe> getClasseCode_Version_List() {
        ArrayList<Classe> classes = new ArrayList<>();

        String selectQuery = "SELECT "+ KEY_CLASSE_CODE+" , " +KEY_VERSION +  " FROM " + TABLE_CLASSE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Classe classe = new Classe();
                classe.setCLASSE_CODE(cursor.getString(0));
                classe.setVERSION(cursor.getString(1));
                classes.add(classe);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version  classes from Sqlite: ");
        return classes;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteClasses() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_CLASSE, null, null);
        db.close();
        Log.d(TAG, "Deleted all Famille info from sqlite");
    }

    public int delete(String catgorieCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CLASSE,KEY_CLASSE_CODE+"=?",new String[]{catgorieCode});
    }


    public ArrayList<Classe> getClasse_textList() {
        ArrayList<Classe> classes = new ArrayList<>();
            String selectQuery = "SELECT "+ KEY_CLASSE_CODE+ " , "+ KEY_CLASSE_NOM +  " FROM " + TABLE_CLASSE;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if( cursor != null && cursor.moveToFirst() ) {
                do {
                    Classe classe = new Classe();
                    classe.setCLASSE_CODE(cursor.getString(0));
                    classe.setCLASSE_NOM(cursor.getString(1));
                    classes.add(classe);
                }while(cursor.moveToNext());
            }
            //returner la listTournees;
            cursor.close();
            db.close();
            Log.d(TAG, "Fetching code/version classes from Sqlite: ");
            return classes;
        }


    public static void synchronisationClasse(final Context context){

        String tag_string_req = "CLASSE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_CLASSE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Classe", "onResponse: "+response);
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray Classes = jObj.getJSONArray("Classes");
                        //Toast.makeText(context, "nombre de Classes  "+Classes.length()  , Toast.LENGTH_SHORT).show();
                        if(Classes.length()>0) {
                            ClasseManager classeManager = new ClasseManager(context);
                            for (int i = 0; i < Classes.length(); i++) {
                                JSONObject uneClasse = Classes.getJSONObject(i);
                                if (uneClasse.getString("OPERATION").equals("DELETE")) {
                                    classeManager.delete(uneClasse.getString(KEY_CLASSE_CODE));
                                    cptDelete++;
                                } else {
                                    classeManager.add(new Classe(uneClasse));cptInsert++;
                                }
                            }
                        }
                        //logM.add("Classes:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncClasse");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CLASSE: Inserted: "+cptInsert +" Deleted: "+cptDelete ,"CLASSE",1));

                        }

                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(context, "Classe : "+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("Classe:NOK "+errorMsg ,"SyncClasse");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CLASSE: Error: "+errorMsg,"CLASSE",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "Classe : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("Classe:NOK "+e.getMessage() ,"SyncClasse");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CLASSE: Error: "+e.getMessage(),"CLASSE",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Classe : "+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("Classe:NOK "+error.getMessage() ,"SyncClasse");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CLASSE: Error: "+error.getMessage(),"CLASSE",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    ClasseManager classeManager = new ClasseManager(context);
                    List<Classe> classes =classeManager.getClasseCode_Version_List();
                    for (int i = 0; i < classes.size(); i++) {
                        Log.d("test classe-@-version",classes.get(i).getCLASSE_CODE()+"-@@-" +classes.get(i).getVERSION());
                        if(classes.get(i).getCLASSE_CODE()!=null && classes.get(i).getVERSION() != null ) {
                            params.put(classes.get(i).getCLASSE_CODE(), classes.get(i).getVERSION());
                        }
                    }
                }
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}


