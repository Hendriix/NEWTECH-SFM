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
import com.newtech.newtech_sfm.Metier.Imprimante;

import java.util.ArrayList;

/**
 * Created by TONPC on 30/05/2017.
 */

public class ImprimanteMan extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Client table name
    public static final String TABLE_IMPRIMANTE = "imprimante";

    private static final String
            KEY_ID="ID",
            KEY_LIBELLE="LIBELLE",
            KEY_ADDMAC="ADDMAC",
            KEY_STATUT="STATUT"
                    ;

    public static  String CREATE_IMPRIMANTE_TABLE = "CREATE TABLE " + TABLE_IMPRIMANTE + " ("
            +KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            +KEY_LIBELLE + " TEXT ,"
            + KEY_ADDMAC + " TEXT,"
            + KEY_STATUT + " TEXT"
            + ")";

    public ImprimanteMan(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_CLIENT);
            db.execSQL(CREATE_IMPRIMANTE_TABLE);

        } catch (SQLException e) {
        }
        Log.d(TAG, "Database tables imprimante created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMPRIMANTE);
        // Create tables again
        onCreate(db);
    }

    public void add(Imprimante imprimante) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LIBELLE, imprimante.getLIBELLE());
        values.put(KEY_ADDMAC, imprimante.getADDMAC());
        values.put(KEY_STATUT, imprimante.getSTATUT());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_IMPRIMANTE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New imprimante inserted into sqlite: " + id);
    }

    public void deleteImprimante() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_IMPRIMANTE, null, null);
        db.close();
        Log.d(TAG, "Deleted all imprimante info from sqlite");
    }

    public Imprimante getByStatut(String statut){

        Imprimante imprimante = new Imprimante();
        String selectQuery = "SELECT  * FROM " + TABLE_IMPRIMANTE +" WHERE "+ KEY_STATUT +" = '"+statut+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {


                imprimante.setID(cursor.getInt(0));
                imprimante.setLIBELLE(cursor.getString(1));
                imprimante.setADDMAC(cursor.getString(2));
                imprimante.setSTATUT(cursor.getString(3));


        }

        cursor.close();
        db.close();
        return imprimante;
    }

    public int getNumberByStatut(String statut){

        int found=0;
        Imprimante imprimante = new Imprimante();
        String selectQuery = "SELECT  COUNT(*) as found FROM " + TABLE_IMPRIMANTE +" WHERE "+ KEY_STATUT +" = '"+statut+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            found=cursor.getInt(0);
        }
        return found;
    }

    public ArrayList<Imprimante> getList() {
        ArrayList<Imprimante> listImprimante = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_IMPRIMANTE ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Imprimante imprimante = new Imprimante();
                imprimante.setID(cursor.getInt(0));
                imprimante.setLIBELLE(cursor.getString(1));
                imprimante.setADDMAC(cursor.getString(2));
                imprimante.setSTATUT(cursor.getString(3));

                listImprimante.add(imprimante);
            }while(cursor.moveToNext());
        }
        //returner la gpstrackers;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Imprimantes from Sqlite: ");
        return listImprimante;
    }
}
