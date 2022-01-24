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
import com.newtech.newtech_sfm.Metier.User;

import java.util.ArrayList;

/**
 * Created by sferricha on 10/01/2017.
 */

public class UserManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Article table name
    public static final String TABLE_UTILISATEUR = "utilisateur";

    //table utilisateur column
    private static final String
            KEY_ID ="ID",
            KEY_UTILISATEUR_CODE ="UTILISATEUR_CODE",
            KEY_UTILISATEUR_NOM = "UTILISATEUR_NOM",
            KEY_UTILISATEUR_TELEPHONE1 = "UTILISATEUR_TELEPHONE1",
            KEY_UTILISATEUR_TELEPHONE2 = "UTILISATEUR_TELEPHONE2",
            KEY_PROFILE_CODE = "PROFILE_CODE",
            KEY_UTILISATEUR_EMAIL = "UTILISATEUR_EMAIL",
            KEY_DISTRIBUTEUR_CODE = "DISTRIBUTEUR_CODE",
            KEY_INACTIF = "INACTIF",
            KEY_INACTIF_RAISON = "INACTIF_RAISON",
            KEY_UTILISATEURSUP_CODE = "UTILISATEURSUP_CODE",
            KEY_STOCK_CODE = "STOCK_CODE",
            KEY_STOCKSUP_CODE = "STOCKSUP_CODE",
            KEY_UTILISATEUR_DESCRIPTION = "UTILISATEUR_DESCRIPTION",
            KEY_DATE_ENTREE = "DATE_ENTREE",
            KEY_CREATEUR_CODE = "CREATEUR_CODE",
            KEY_PLAFOND = "PLAFOND",
            KEY_VERSION = "VERSION";

    public static String CREATE_UTILISATEUR_TABLE = "CREATE TABLE " + TABLE_UTILISATEUR + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_UTILISATEUR_CODE + " TEXT,"
            + KEY_UTILISATEUR_NOM + " TEXT,"
            + KEY_UTILISATEUR_TELEPHONE1 + " TEXT,"
            + KEY_UTILISATEUR_TELEPHONE2 + " TEXT,"
            + KEY_PROFILE_CODE + " TEXT,"
            + KEY_UTILISATEUR_EMAIL + " TEXT,"
            + KEY_DISTRIBUTEUR_CODE + " TEXT,"
            + KEY_INACTIF + " TEXT,"
            + KEY_INACTIF_RAISON + " TEXT,"
            + KEY_UTILISATEURSUP_CODE + " TEXT,"
            + KEY_STOCK_CODE + " TEXT,"
            + KEY_STOCKSUP_CODE + " TEXT,"
            + KEY_UTILISATEUR_DESCRIPTION + " TEXT,"
            + KEY_DATE_ENTREE + " TEXT,"
            + KEY_CREATEUR_CODE + " TEXT,"
            + KEY_PLAFOND + " FLOAT,"
            + KEY_VERSION + " TEXT" +") ;";



    public UserManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_TOURNEE);
            db.execSQL(CREATE_UTILISATEUR_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database tables utilisateur created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILISATEUR);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing Articles details in database
     * */
    public void add(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_UTILISATEUR_CODE,user.getUTILISATEUR_CODE());
        values.put(KEY_UTILISATEUR_NOM, user.getUTILISATEUR_NOM());
        values.put(KEY_UTILISATEUR_TELEPHONE1, user.getUTILISATEUR_TELEPHONE1());
        values.put(KEY_UTILISATEUR_TELEPHONE2, user.getUTILISATEUR_TELEPHONE2());
        values.put(KEY_PROFILE_CODE, user.getPROFILE_CODE());
        values.put(KEY_UTILISATEUR_EMAIL, user.getUTILISATEUR_EMAIL());
        values.put(KEY_DISTRIBUTEUR_CODE, user.getDISTRIBUTEUR_CODE());
        values.put(KEY_INACTIF, user.getINACTIF());
        values.put(KEY_INACTIF_RAISON, user.getINACTIF_RAISON());
        values.put(KEY_UTILISATEURSUP_CODE, user.getUTILISATEURSUP_CODE());
        values.put(KEY_STOCK_CODE, user.getSTOCK_CODE());
        values.put(KEY_STOCKSUP_CODE, user.getSTOCKSUP_CODE());
        values.put(KEY_UTILISATEUR_DESCRIPTION, user.getUTILISATEUR_DESCRIPTION());
        values.put(KEY_DATE_ENTREE, user.getDATE_ENTREE());
        values.put(KEY_CREATEUR_CODE, user.getCREATEUR_CODE());
        values.put(KEY_PLAFOND, user.getPLAFOND());
        values.put(KEY_VERSION, user.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_UTILISATEUR, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New utilisateur inserted into sqlite: " + id);
    }

    public ArrayList<User> getList() {
        ArrayList<User> listUser = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_UTILISATEUR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                User user = new User();

                user.setID(cursor.getString(0));
                user.setUTILISATEUR_CODE(cursor.getString(1));
                user.setUTILISATEUR_NOM(cursor.getString(2));
                user.setUTILISATEUR_TELEPHONE1(cursor.getString(3));
                user.setUTILISATEUR_TELEPHONE2(cursor.getString(4));
                user.setPROFILE_CODE(cursor.getString(5));
                user.setUTILISATEUR_EMAIL(cursor.getString(6));
                user.setDISTRIBUTEUR_CODE(cursor.getString(7));
                user.setINACTIF(cursor.getString(8));
                user.setINACTIF_RAISON(cursor.getString(9));
                user.setUTILISATEURSUP_CODE(cursor.getString(10));
                user.setSTOCK_CODE(cursor.getString(11));
                user.setSTOCKSUP_CODE(cursor.getString(12));
                user.setUTILISATEUR_DESCRIPTION(cursor.getString(13));
                user.setDATE_ENTREE(cursor.getString(14));
                user.setCREATEUR_CODE(cursor.getString(15));
                user.setPLAFOND(cursor.getDouble(16));
                user.setVERSION(cursor.getString(17));

                listUser.add(user);
            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching urilisateurs from Sqlite: ");
        return listUser;
    }

    public User  get( String utilisateur_code) {

        String selectQuery = "SELECT * FROM " + TABLE_UTILISATEUR + " WHERE "+ KEY_UTILISATEUR_CODE +" = '"+utilisateur_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        User user = new User();

        if( cursor != null && cursor.moveToFirst() ) {
            user.setID(cursor.getString(0));
            user.setUTILISATEUR_CODE(cursor.getString(1));
            user.setUTILISATEUR_NOM(cursor.getString(2));
            user.setUTILISATEUR_TELEPHONE1(cursor.getString(3));
            user.setUTILISATEUR_TELEPHONE2(cursor.getString(4));
            user.setPROFILE_CODE(cursor.getString(5));
            user.setUTILISATEUR_EMAIL(cursor.getString(6));
            user.setDISTRIBUTEUR_CODE(cursor.getString(7));
            user.setINACTIF(cursor.getString(8));
            user.setINACTIF_RAISON(cursor.getString(9));
            user.setUTILISATEURSUP_CODE(cursor.getString(10));
            user.setSTOCK_CODE(cursor.getString(11));
            user.setSTOCKSUP_CODE(cursor.getString(12));
            user.setUTILISATEUR_DESCRIPTION(cursor.getString(13));
            user.setDATE_ENTREE(cursor.getString(14));
            user.setCREATEUR_CODE(cursor.getString(15));
            user.setPLAFOND(cursor.getDouble(16));
            user.setVERSION(cursor.getString(17));
        }

        cursor.close();
        db.close();
        return user;

    }

    public User  get() {

        String selectQuery = "SELECT * FROM " + TABLE_UTILISATEUR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        User user = new User();

        if( cursor != null && cursor.moveToFirst() ) {

            user.setID(cursor.getString(0));
            user.setUTILISATEUR_CODE(cursor.getString(1));
            user.setUTILISATEUR_NOM(cursor.getString(2));
            user.setUTILISATEUR_TELEPHONE1(cursor.getString(3));
            user.setUTILISATEUR_TELEPHONE2(cursor.getString(4));
            user.setPROFILE_CODE(cursor.getString(5));
            user.setUTILISATEUR_EMAIL(cursor.getString(6));
            user.setDISTRIBUTEUR_CODE(cursor.getString(7));
            user.setINACTIF(cursor.getString(8));
            user.setINACTIF_RAISON(cursor.getString(9));
            user.setUTILISATEURSUP_CODE(cursor.getString(10));
            user.setSTOCK_CODE(cursor.getString(11));
            user.setSTOCKSUP_CODE(cursor.getString(12));
            user.setUTILISATEUR_DESCRIPTION(cursor.getString(13));
            user.setDATE_ENTREE(cursor.getString(14));
            user.setCREATEUR_CODE(cursor.getString(15));
            user.setPLAFOND(cursor.getDouble(16));
            user.setVERSION(cursor.getString(17));
        }

        cursor.close();
        db.close();
        return user;

    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUtilisateurs() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_UTILISATEUR, null, null);
        db.close();
        Log.d(TAG, "Deleted all utilisateurs info from sqlite");
    }

    public int delete(String utilisateur_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_UTILISATEUR,KEY_UTILISATEUR_CODE+"=?",new String[]{utilisateur_code});
    }


    public int count(){

        int compteur = 0;
        String selectQuery = "SELECT COUNT(*) FROM " + TABLE_UTILISATEUR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            compteur = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return compteur;
    }


}
