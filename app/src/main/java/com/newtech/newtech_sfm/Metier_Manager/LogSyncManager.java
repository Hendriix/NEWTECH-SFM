package com.newtech.newtech_sfm.Metier_Manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.newtech.newtech_sfm.Configuration.AppConfig;
import com.newtech.newtech_sfm.Configuration.SQLiteHandler;
import com.newtech.newtech_sfm.Metier.LogSync;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by stagiaireit2 on 26/07/2016.
 */
public class LogSyncManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_LOG = "LogSync";
    // Login Table Columns names
    private static final String

            KEY_DATE="DATE",
            KEY_MESSAGE = "MESSAGE",
            KEY_TYPE = "TYPE";
    public static  String CREATE_LOG_TABLE = "CREATE TABLE " + TABLE_LOG + "("
            +KEY_DATE + " TEXT ,"
            + KEY_MESSAGE + " TEXT,"
            + KEY_TYPE + " TEXT" + ")";
    public LogSyncManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_LOG);
            db.execSQL(CREATE_LOG_TABLE);
        } catch (SQLException e) {
        }
        Log.d(TAG, "Database table log created");
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOG);
        // Create tables again
        onCreate(db);
    }
    /**
     * Storing Articles details in database
     * */
    public void add(String msg , String type) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        DateFormat df = new SimpleDateFormat("HH:mm:ss ");
        String date = df.format(Calendar.getInstance().getTime());

        values.put(KEY_DATE,date);
        values.put(KEY_MESSAGE, msg);
        values.put(KEY_TYPE, type);


        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_LOG, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New logSync inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public ArrayList<LogSync> getList() {
        ArrayList<LogSync> listLog = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LOG;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                LogSync log = new LogSync();
                log.setDate(cursor.getString(0));
                log.setMsg(cursor.getString(1));
                log.setType(cursor.getString(2));

                listLog.add(log);
            }while(cursor.moveToNext());
        }

        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "extraction des Log de Synchronisation de la base de donnees: ");
        return listLog;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteLogs() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOG, null, null);
        db.close();
        Log.d(TAG, "Deleted all log info from sqlite");
    }

}
