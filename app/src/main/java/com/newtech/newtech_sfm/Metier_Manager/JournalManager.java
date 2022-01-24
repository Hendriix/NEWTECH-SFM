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
import com.newtech.newtech_sfm.Metier.Journal;

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
public class JournalManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_JOURNAL = "journal";



    private static final String

            KEY_ID="ID",
            KEY_IMEI="FACTURE_CODE",
            KEY_TYPE="FACTURECLIENT_CODE",
            KEY_LOG_TEXT="DATE_COMMANDE",
            KEY_TS = "TS",
            KEY_COMMENTAIRE = "COMMENTAIRE"

            ;

    public static String CREATE_JOURNAL_TABLE = "CREATE TABLE " + TABLE_JOURNAL+ " ("

            +KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +KEY_IMEI +" TEXT,"
            +KEY_TYPE +" TEXT,"
            +KEY_LOG_TEXT +" TEXT,"
            +KEY_TS +" TEXT,"
            +KEY_COMMENTAIRE +" TEXT"+ ")";

    public JournalManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE);
            db.execSQL(CREATE_JOURNAL_TABLE);
        } catch (SQLException e) {
        }
        Log.d(TAG, "table Log created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOURNAL);
        onCreate(db);
    }

    public void add(Journal journal) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "add: "+journal.toString());
        ContentValues values = new ContentValues();
        values.put(KEY_IMEI,journal.getIMEI() );
        values.put(KEY_TYPE, journal.getTYPE());
        values.put(KEY_LOG_TEXT, journal.getLOG_TEXT());
        values.put(KEY_TS, journal.getTS());
        values.put(KEY_COMMENTAIRE, journal.getCOMMENTAIRE());


        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_JOURNAL, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d(TAG, "nouvelle commande inseré dans la table Commande: " + id);
    }

    public int delete(String imei, String ts) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_JOURNAL,KEY_IMEI+"=?",new String[]{imei});
    }

    public void updateJournal(String imei,String ts,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_JOURNAL + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_IMEI +"= '"+imei+"'" ;
        db.execSQL(req);
        db.close();

        Log.d("commande", "updateCommande: "+req);
    }

    public void deleteJournalSynchronisee() {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefCmdAS = sdf.format(new Date());
        Log.d(TAG, "deleteCmdSynchronisee: "+DatefCmdAS);

        String Where = " "+KEY_COMMENTAIRE+"='inserted' and date("+KEY_TS+")!='"+DatefCmdAS+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_JOURNAL, Where, null);
        db.close();
        Log.d("journal", "Deleted all journaux verifiee from sqlite");
        Log.d("journal", "deleteJournalSynchronisee: "+Where);
    }


    public ArrayList<Journal> getList() {
            ArrayList<Journal> journals = new ArrayList<>();

            String selectQuery = "SELECT  * FROM " + TABLE_JOURNAL;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if( cursor != null && cursor.moveToFirst() ) {
                do {
                    Journal journal = new Journal();
                    journal.setID(cursor.getInt(0));
                    journal.setIMEI(cursor.getString(1));
                    journal.setTYPE(cursor.getString(2));
                    journal.setLOG_TEXT(cursor.getString(3));
                    journal.setTS((cursor.getString(4)));
                    journal.setCOMMENTAIRE((cursor.getString(5)));

                    journals.add(journal);
                }while(cursor.moveToNext());
            }
            //returner la listArticles;
            cursor.close();
        db.close();
        Log.d(TAG, "Fetching Journal from table Journal: ");
        Log.d(TAG, "Nombre Journal from table Journal: "+journals.size());
        return journals;
    }

    public ArrayList<Journal> getListNotInserted() {
        ArrayList<Journal> journals = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_JOURNAL +" WHERE "+KEY_COMMENTAIRE+" = 'to_insert' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Journal journal = new Journal();
                journal.setID(cursor.getInt(0));
                journal.setIMEI(cursor.getString(1));
                journal.setTYPE(cursor.getString(2));
                journal.setLOG_TEXT(cursor.getString(3));
                journal.setTS((cursor.getString(4)));
                journal.setCOMMENTAIRE((cursor.getString(5)));

                Log.d(TAG, "getListNotInserted: "+journal.toString());
                journals.add(journal);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Journal from table Journal: ");
        Log.d(TAG, "Nombre Journal from table Journal: "+journals.size());
        return journals;
    }

    public void deleteJournals() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_JOURNAL, null, null);
        db.close();
        Log.d(TAG, "Deleted all journals info from sqlite");
    }

    public static void synchronisationJournal(final Context context){

        JournalManager journalManager = new JournalManager(context);
        journalManager.deleteJournalSynchronisee();

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
                        JSONArray journaux = jObj.getJSONArray("result");
                        Toast.makeText(context, "Nombre de journals  "+journaux.length() , Toast.LENGTH_LONG).show();
                       // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les journaux : " +response);

                        if(journaux.length()>0) {
                            JournalManager journalManager = new JournalManager(context);
                            for (int i = 0; i < journaux.length(); i++) {
                                JSONObject unjournal = journaux.getJSONObject(i);
                                if (unjournal.getString("Statut").equals("true")) {
                                    journalManager.updateJournal((unjournal.getString("IMEI")),unjournal.getString("TS"),"inserted");
                                }
                            }
                        }
                        logM.add("Journal:OK Insert:"+cptInsert +"Delete:"+cptDelete ,"SynchJournal");


                    }else {
                        String errorMsg = jObj.getString("info");
                        Toast.makeText(context,
                                "journal : "+errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "journal : "+"Json error: " +"erreur applcation journal" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        "journal : "+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    JournalManager journalManager  = new JournalManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    //TabParams.put("Table_Name","tableArticle");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    //arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(journalManager.getListNotInserted()));
                    Log.d("JournalSynch", "Liste: "+journalManager.getListNotInserted());
                    Log.d(TAG, "getParams: listnotinserted"+journalManager.getListNotInserted().size());
                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
