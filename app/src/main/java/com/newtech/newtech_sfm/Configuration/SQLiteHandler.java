package com.newtech.newtech_sfm.Configuration;

/**
 * Created by stagiaireit2 on 05/07/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.newtech.newtech_sfm.Metier.Article;

import java.util.ArrayList;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;

    // Login table name
    private static final String TABLE_ARTICLE = "article";

    // Login Table Columns names
    private static final String
            KEY_ID ="ID",
            KEY_ARTICLE_CODE ="ARTICLE_CODE",
            KEY_ARTICLE_DESIGNATION1 = "ARTICLE_DESIGNATION1",
            KEY_ARTICLE_DESIGNATION2 = "ARTICLE_DESIGNATION2",
            KEY_ARTICLE_DESIGNATION3 = "ARTICLE_DESIGNATION3",
            KEY_ARTICLE_SKU = "ARTICLE_SKU",
            KEY_LITTRAGE_UP = "LITTRAGE_UP",
            KEY_POIDKG_UP = "POIDKG_UP",
            KEY_LITTRAGE_US = "LITTRAGE_US",
            KEY_POIDKG_US = "POIDKG_US",
            KEY_NBUS_PAR_UP = "NBUS_PAR_UP",
            KEY_CATEGORIE_CODE = "CATEGORIE_CODE",
            KEY_TYPE_CODE = "TYPE_CODE",
            KEY_FAMILLE_CODE = "FAMILLE_CODE",
            KEY_DATE_CREATION = "DATE_CREATION",
            KEY_CREATEUR_CODE = "CREATEUR_CODE",
            KEY_INACTIF = "INACTIF",
            KEY_INACTIF_RAISON = "INACTIF_RAISON",
            KEY_RANG = "RANG",
            KEY_VERSION = "VERSION";


    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
       String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_ARTICLE + "("
                +KEY_ARTICLE_CODE + " TEXT PRIMARY KEY,"
                + KEY_ARTICLE_DESIGNATION1 + " TEXT,"
                + KEY_ARTICLE_DESIGNATION2 + " TEXT,"
                + KEY_ARTICLE_DESIGNATION3 + " TEXT,"
                + KEY_ARTICLE_SKU + " NUMERIC,"
                +KEY_LITTRAGE_UP + " NUMERIC,"
                +KEY_POIDKG_UP + " NUMERIC,"
                +KEY_LITTRAGE_US + " NUMERIC,"
                +KEY_POIDKG_US + " NUMERIC,"
                +KEY_NBUS_PAR_UP + " NUMERIC,"
                +KEY_CATEGORIE_CODE + " TEXT,"
                +KEY_TYPE_CODE + " TEXT,"
                +KEY_FAMILLE_CODE + " TEXT,"
                +KEY_DATE_CREATION + " NUMERIC,"
                +KEY_CREATEUR_CODE + " TEXT,"
                +KEY_INACTIF + " NUMERIC,"
                +KEY_INACTIF_RAISON + " TEXT,"
                +KEY_RANG + " NUMERIC,"
                + KEY_VERSION + " TEXT" + ")";


        try {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE);
            db.execSQL(CREATE_LOGIN_TABLE);

        } catch (SQLException e) {

        }

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addArticle(Article article) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ARTICLE_CODE, article.getARTICLE_CODE());
        values.put(KEY_ARTICLE_DESIGNATION1,article.getARTICLE_DESIGNATION1());
        values.put(KEY_ARTICLE_DESIGNATION2, article.getARTICLE_DESIGNATION2());
        values.put(KEY_ARTICLE_DESIGNATION3, article.getARTICLE_DESIGNATION3());
        values.put(KEY_ARTICLE_SKU, article.getARTICLE_SKU());
        values.put(KEY_LITTRAGE_UP, article.getLITTRAGE_UP());
        values.put(KEY_POIDKG_UP, article.getPOIDKG_UP());
        values.put(KEY_LITTRAGE_US, article.getLITTRAGE_US());
        values.put(KEY_POIDKG_US, article.getPOIDKG_US());
        values.put(KEY_NBUS_PAR_UP, article.getNBUS_PAR_UP());
        values.put(KEY_CATEGORIE_CODE, article.getCATEGORIE_CODE());
        values.put(KEY_TYPE_CODE, article.getTYPE_CODE());
        values.put(KEY_FAMILLE_CODE, article.getFAMILLE_CODE());
        values.put(KEY_DATE_CREATION, article.getDATE_CREATION());
        values.put(KEY_CREATEUR_CODE, article.getCREATEUR_CODE());
        values.put(KEY_INACTIF, article.getINACTIF());
        values.put(KEY_INACTIF_RAISON, article.getINACTIF_RAISON());
        values.put(KEY_RANG, article.getRANG());
        values.put(KEY_VERSION, article.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_ARTICLE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New articles inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public  ArrayList<Article> getArticleDetails() {
        ArrayList<Article> listArticles = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_ARTICLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

                if( cursor != null && cursor.moveToFirst() ) {
                    do {
                        Article ar = new Article();
                        ar.setARTICLE_CODE(cursor.getString(0));
                        ar.setARTICLE_DESIGNATION1(cursor.getString(1));
                        ar.setARTICLE_DESIGNATION2(cursor.getString(2));
                        ar.setARTICLE_DESIGNATION3(cursor.getString(3));
                        ar.setARTICLE_SKU((cursor.getDouble(4)));
                        ar.setLITTRAGE_UP(cursor.getDouble(5));
                        ar.setPOIDKG_UP(cursor.getDouble(6));
                        ar.setLITTRAGE_US(cursor.getDouble(7));
                        ar.setPOIDKG_US(cursor.getDouble(8));
                        ar.setNBUS_PAR_UP(cursor.getDouble(9));
                        ar.setCATEGORIE_CODE(cursor.getString(10));
                        ar.setTYPE_CODE(cursor.getString(11));
                        ar.setFAMILLE_CODE(cursor.getString(12));
                        ar.setDATE_CREATION(cursor.getString(13));
                        ar.setCREATEUR_CODE(cursor.getString(14));
                        ar.setINACTIF(cursor.getDouble(15));
                        ar.setINACTIF_RAISON(cursor.getString(16));
                        ar.setRANG(cursor.getInt(17));
                        ar.setVERSION(cursor.getString(18));

                        listArticles.add(ar);
                    }while(cursor.moveToNext());
                }

        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching articles from Sqlite: ");
        return listArticles;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteArticles() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_ARTICLE, null, null);
        db.close();
        Log.d(TAG, "Deleted all articles info from sqlite");
    }

    public int deleteArticle(String articleCode) {

        SQLiteDatabase db = this.getWritableDatabase();
       return db.delete(TABLE_ARTICLE,KEY_ARTICLE_CODE+"=?",new String[]{articleCode});
    }


}
