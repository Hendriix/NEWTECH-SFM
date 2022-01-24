package com.newtech.newtech_sfm.Metier_Manager;

import android.content.ContentValues;
import android.content.Context;
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
import com.newtech.newtech_sfm.Metier.Visite;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TONPC on 13/02/2017.
 */

public class VisiteManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Client table name
    public static final String TABLE_VISITE = "visite";


    public VisiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Visite Table Columns names
    private static final String
            KEY_ID ="ID",
            KEY_VISITE_CODE="VISITE_CODE",
            KEY_DISTRIBUTEUR_CODE="DISTRIBUTEUR_CODE",
            KEY_UTILISATEUR_CODE="UTILISATEUR_CODE",
            KEY_CLIENT_CODE="CLIENT_CODE",
            KEY_TOURNEE_CODE="TOURNEE_CODE",
            KEY_DATE_DEBUT="DATE_DEBUT",
            KEY_DATE_FIN="DATE_FIN",
            KEY_DATE_VISITE="DATE_VISITE",
            KEY_GPS_LATITUDE="GPS_LATITUDE",
            KEY_GPS_LONGITUDE="GPS_LONGITUDE",
            KEY_TYPE_CODE="TYPE_CODE",
            KEY_STATUT_CODE="STATUT_CODE",
            KEY_CATEGORIE_CODE="CATEGORIE_CODE",
            KEY_TACHE_CODE="TACHE_CODE",
            KEY_CREATEUR_CODE="CREATEUR_CODE",
            KEY_DATE_CREATION="DATE_CREATION",
            KEY_VERSION="VERSION",
            KEY_VISITE_RESULTAT="VISITE_RESULTAT",
            KEY_DISTANCE="DISTANCE",
            KEY_VISITE_SOURCE="VISITE_SOURCE";



    public static String CREATE_VISITE_TABLE = "CREATE TABLE " + TABLE_VISITE + "("
            +KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            +KEY_VISITE_CODE + " TEXT,"
            +KEY_DISTRIBUTEUR_CODE + " TEXT,"
            +KEY_UTILISATEUR_CODE + " TEXT,"
            +KEY_CLIENT_CODE + " TEXT,"
            +KEY_TOURNEE_CODE + " TEXT,"
            +KEY_DATE_DEBUT + " TEXT,"
            +KEY_DATE_FIN + " TEXT,"
            +KEY_DATE_VISITE + " TEXT,"
            +KEY_GPS_LATITUDE + " TEXT,"
            +KEY_GPS_LONGITUDE + " TEXT,"
            +KEY_TYPE_CODE + " TEXT,"
            +KEY_STATUT_CODE + " TEXT,"
            +KEY_CATEGORIE_CODE + " TEXT,"
            +KEY_TACHE_CODE + " TEXT,"
            +KEY_CREATEUR_CODE + " TEXT,"
            +KEY_DATE_CREATION + " TEXT,"
            +KEY_VERSION+ " TEXT,"
            +KEY_VISITE_RESULTAT+ " INTEGER,"
            +KEY_DISTANCE+ " INTEGER,"
            +KEY_VISITE_SOURCE+ " TEXT"+ ");";


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_TOURNEE);
            db.execSQL(CREATE_VISITE_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database tables visite created");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VISITE);
        // Create tables again
        onCreate(db);

    }

    public int getVisiteChecked(String client_code,String date_visite){
        Integer result =0;
        ArrayList<Visite> listVisite = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_VISITE +" WHERE "+
                KEY_CLIENT_CODE+"='"+client_code+"' AND date("+KEY_DATE_VISITE+")='"+date_visite+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Visite visite = new Visite();
                visite.setID(cursor.getInt(0));
                visite.setVISITE_CODE(cursor.getString(1));
                visite.setDISTRIBUTEUR_CODE(cursor.getString(2));
                visite.setUTILISATEUR_CODE(cursor.getString(3));
                visite.setCLIENT_CODE(cursor.getString(4));
                visite.setTOURNEE_CODE(cursor.getString(5));
                visite.setDATE_DEBUT(cursor.getString(6));
                visite.setDATE_FIN(cursor.getString(7));
                visite.setDATE_VISITE(cursor.getString(8));
                visite.setGPS_LATITUDE(cursor.getString(9));
                visite.setGPS_LONGITUDE(cursor.getString(10));
                visite.setTYPE_CODE(cursor.getString(11));
                visite.setSTATUT_CODE(cursor.getString(12));
                visite.setCATEGORIE_CODE(cursor.getString(13));
                visite.setTACHE_CODE(cursor.getString(14));
                visite.setCREATEUR_CODE(cursor.getString(15));
                visite.setDATE_CREATION(cursor.getString(16));
                visite.setVERSION(cursor.getString(17));
                visite.setVISITE_RESULTAT(cursor.getInt(18));
                visite.setDISTANCE(cursor.getInt(19));
                visite.setVISITE_SOURCE(cursor.getString(20));

                listVisite.add(visite);
                //Log.d(TAG, "getVisiteChecked1: " +visite.getDATE_VISITE()+"  "+date_visite);
            }while(cursor.moveToNext());
        }
        //returner la listVisites;
        cursor.close();
        db.close();

        //Log.d(TAG, "getVisiteChecked2: " +selectQuery+"  "+listVisite.size());

        if(listVisite.size()>0){
            for(int i=0;i<listVisite.size();i++){

                if(listVisite.get(i).getVISITE_RESULTAT()==1){
                    result=1;
                   break;
                }else if(listVisite.get(i).getVISITE_RESULTAT()==7){
                    result=7;

                }
                else if(listVisite.get(i).getVISITE_RESULTAT()!=0 && listVisite.get(i).getVISITE_RESULTAT()!=1 && listVisite.get(i).getVISITE_RESULTAT()!=7){
                    result=2;

                }
            }
        }
        else{
            //Log.d(TAG, "getVisiteChecked: aucune visite trouvé");
        }

        return result;
    }

    public int getVisiteCheckedCmdAL(String client_code,String date_visite){
        Integer result =0;
        ArrayList<Visite> listVisite = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_VISITE +" WHERE "+
                KEY_CLIENT_CODE+"='"+client_code+"' AND date("+KEY_DATE_VISITE+")='"+date_visite+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Visite visite = new Visite();
                visite.setID(cursor.getInt(0));
                visite.setVISITE_CODE(cursor.getString(1));
                visite.setDISTRIBUTEUR_CODE(cursor.getString(2));
                visite.setUTILISATEUR_CODE(cursor.getString(3));
                visite.setCLIENT_CODE(cursor.getString(4));
                visite.setTOURNEE_CODE(cursor.getString(5));
                visite.setDATE_DEBUT(cursor.getString(6));
                visite.setDATE_FIN(cursor.getString(7));
                visite.setDATE_VISITE(cursor.getString(8));
                visite.setGPS_LATITUDE(cursor.getString(9));
                visite.setGPS_LONGITUDE(cursor.getString(10));
                visite.setTYPE_CODE(cursor.getString(11));
                visite.setSTATUT_CODE(cursor.getString(12));
                visite.setCATEGORIE_CODE(cursor.getString(13));
                visite.setTACHE_CODE(cursor.getString(14));
                visite.setCREATEUR_CODE(cursor.getString(15));
                visite.setDATE_CREATION(cursor.getString(16));
                visite.setVERSION(cursor.getString(17));
                visite.setVISITE_RESULTAT(cursor.getInt(18));
                visite.setDISTANCE(cursor.getInt(19));
                visite.setVISITE_SOURCE(cursor.getString(20));

                listVisite.add(visite);
                //Log.d(TAG, "getVisiteChecked1: " +visite.getDATE_VISITE()+"  "+date_visite);
            }while(cursor.moveToNext());
        }
        //returner la listVisites;
        cursor.close();
        db.close();

        //Log.d(TAG, "getVisiteChecked2: " +selectQuery+"  "+listVisite.size());

        if(listVisite.size()>0){
            for(int i=0;i<listVisite.size();i++){

                if(listVisite.get(i).getVISITE_RESULTAT()==1){
                    result=1;
                    break;
                }else if(listVisite.get(i).getVISITE_RESULTAT()==7){
                    result=7;

                }else if(listVisite.get(i).getVISITE_RESULTAT()!=0 && listVisite.get(i).getVISITE_RESULTAT()!=1 && listVisite.get(i).getVISITE_RESULTAT()!=7){
                    result=2;

                }
            }
        }
        else{
            //Log.d(TAG, "getVisiteChecked: aucune visite trouvé");
        }

        return result;
    }


    //ADD VISITE INTO THE DATABASE

    public void add(Visite visite) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_VISITE_CODE,visite.getVISITE_CODE());
        values.put(KEY_DISTRIBUTEUR_CODE,visite.getDISTRIBUTEUR_CODE());
        values.put(KEY_UTILISATEUR_CODE,visite.getUTILISATEUR_CODE());
        values.put(KEY_CLIENT_CODE,visite.getCLIENT_CODE());
        values.put(KEY_TOURNEE_CODE,visite.getTOURNEE_CODE());
        values.put(KEY_DATE_DEBUT,visite.getDATE_DEBUT());
        values.put(KEY_DATE_FIN,visite.getDATE_FIN());
        values.put(KEY_DATE_VISITE,visite.getDATE_VISITE());
        values.put(KEY_GPS_LATITUDE,visite.getGPS_LATITUDE());
        values.put(KEY_GPS_LONGITUDE,visite.getGPS_LONGITUDE());
        values.put(KEY_TYPE_CODE,visite.getTYPE_CODE());
        values.put(KEY_STATUT_CODE,visite.getSTATUT_CODE());
        values.put(KEY_CATEGORIE_CODE,visite.getCATEGORIE_CODE());
        values.put(KEY_TACHE_CODE,visite.getTACHE_CODE());
        values.put(KEY_CREATEUR_CODE,visite.getCREATEUR_CODE());
        values.put(KEY_DATE_CREATION,visite.getDATE_CREATION());
        values.put(KEY_VERSION,visite.getVERSION());
        values.put(KEY_VISITE_RESULTAT,visite.getVISITE_RESULTAT());
        values.put(KEY_VISITE_SOURCE,visite.getVISITE_SOURCE());
        values.put(KEY_DISTANCE,visite.getDISTANCE());



        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_VISITE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d(TAG, "New visite inserted into sqlite: " + id);
    }


    public ArrayList<Visite> getList() {
        ArrayList<Visite> listVisite = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_VISITE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Visite visite = new Visite();
                visite.setID(cursor.getInt(0));
                visite.setVISITE_CODE(cursor.getString(1));
                visite.setDISTRIBUTEUR_CODE(cursor.getString(2));
                visite.setUTILISATEUR_CODE(cursor.getString(3));
                visite.setCLIENT_CODE(cursor.getString(4));
                visite.setTOURNEE_CODE(cursor.getString(5));
                visite.setDATE_DEBUT(cursor.getString(6));
                visite.setDATE_FIN(cursor.getString(7));
                visite.setDATE_VISITE(cursor.getString(8));
                visite.setGPS_LATITUDE(cursor.getString(9));
                visite.setGPS_LONGITUDE(cursor.getString(10));
                visite.setTYPE_CODE(cursor.getString(11));
                visite.setSTATUT_CODE(cursor.getString(12));
                visite.setCATEGORIE_CODE(cursor.getString(13));
                visite.setTACHE_CODE(cursor.getString(14));
                visite.setCREATEUR_CODE(cursor.getString(15));
                visite.setDATE_CREATION(cursor.getString(16));
                visite.setVERSION(cursor.getString(17));
                visite.setVISITE_RESULTAT(cursor.getInt(18));
                visite.setDISTANCE(cursor.getInt(19));
                visite.setVISITE_SOURCE(cursor.getString(20));

                listVisite.add(visite);
            }while(cursor.moveToNext());
        }
        //returner la listVisites;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching visites from Sqlite: ");
        return listVisite;
    }

    public ArrayList<Visite> getListNonVerifie() {
        ArrayList<Visite> listVisite = new ArrayList<>();
        String statut="non verifiee";

        String selectQuery = "SELECT * FROM " + TABLE_VISITE +" WHERE "+KEY_STATUT_CODE+"!='visite verifiee' OR "+KEY_STATUT_CODE+" IS NULL ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Visite visite = new Visite();
                visite.setID(cursor.getInt(0));
                visite.setVISITE_CODE(cursor.getString(1));
                visite.setDISTRIBUTEUR_CODE(cursor.getString(2));
                visite.setUTILISATEUR_CODE(cursor.getString(3));
                visite.setCLIENT_CODE(cursor.getString(4));
                visite.setTOURNEE_CODE(cursor.getString(5));
                visite.setDATE_DEBUT(cursor.getString(6));
                visite.setDATE_FIN(cursor.getString(7));
                visite.setDATE_VISITE(cursor.getString(8));
                visite.setGPS_LATITUDE(cursor.getString(9));
                visite.setGPS_LONGITUDE(cursor.getString(10));
                visite.setTYPE_CODE(cursor.getString(11));
                visite.setSTATUT_CODE(cursor.getString(12));
                visite.setCATEGORIE_CODE(cursor.getString(13));
                visite.setTACHE_CODE(cursor.getString(14));
                visite.setCREATEUR_CODE(cursor.getString(15));
                visite.setDATE_CREATION(cursor.getString(16));
                visite.setVERSION(cursor.getString(17));
                visite.setVISITE_RESULTAT(cursor.getInt(18));
                visite.setDISTANCE(cursor.getInt(19));
                visite.setVISITE_SOURCE(cursor.getString(20));

                listVisite.add(visite);
            }while(cursor.moveToNext());
        }
        //returner la listVisites;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching visites Non vérifiés from Sqlite:  "+listVisite.size());
        Log.d(TAG, "Liste non vérifiée:  "+selectQuery);
        return listVisite;
    }

    public ArrayList<Visite> getListNotInserted() {
        ArrayList<Visite> listVisite = new ArrayList<>();
        String statut="non verifiee";

        String selectQuery = "SELECT * FROM " + TABLE_VISITE +" WHERE "+KEY_STATUT_CODE+"='to_insert';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Visite visite = new Visite();
                visite.setID(cursor.getInt(0));
                visite.setVISITE_CODE(cursor.getString(1));
                visite.setDISTRIBUTEUR_CODE(cursor.getString(2));
                visite.setUTILISATEUR_CODE(cursor.getString(3));
                visite.setCLIENT_CODE(cursor.getString(4));
                visite.setTOURNEE_CODE(cursor.getString(5));
                visite.setDATE_DEBUT(cursor.getString(6));
                visite.setDATE_FIN(cursor.getString(7));
                visite.setDATE_VISITE(cursor.getString(8));
                visite.setGPS_LATITUDE(cursor.getString(9));
                visite.setGPS_LONGITUDE(cursor.getString(10));
                visite.setTYPE_CODE(cursor.getString(11));
                visite.setSTATUT_CODE(cursor.getString(12));
                visite.setCATEGORIE_CODE(cursor.getString(13));
                visite.setTACHE_CODE(cursor.getString(14));
                visite.setCREATEUR_CODE(cursor.getString(15));
                visite.setDATE_CREATION(cursor.getString(16));
                visite.setVERSION(cursor.getString(17));
                visite.setVISITE_RESULTAT(cursor.getInt(18));
                visite.setDISTANCE(cursor.getInt(19));
                visite.setVISITE_SOURCE(cursor.getString(20));

                listVisite.add(visite);
            }while(cursor.moveToNext());
        }
        //returner la listVisites;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching visites Non vérifiés from Sqlite:  "+listVisite.size());
        Log.d(TAG, "Liste non vérifiée:  "+selectQuery);
        return listVisite;
    }

    //RETURN VISTE BY CODE
    public Visite  get(String VISITE_CODE) {

        String selectQuery = "SELECT * FROM " + TABLE_VISITE + " WHERE "+ KEY_VISITE_CODE +" = '"+VISITE_CODE+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Visite visite = new Visite();

        if( cursor != null && cursor.moveToFirst() ) {

            visite.setID(cursor.getInt(0));
            visite.setVISITE_CODE(cursor.getString(1));
            visite.setDISTRIBUTEUR_CODE(cursor.getString(2));
            visite.setUTILISATEUR_CODE(cursor.getString(3));
            visite.setCLIENT_CODE(cursor.getString(4));
            visite.setTOURNEE_CODE(cursor.getString(5));
            visite.setDATE_DEBUT(cursor.getString(6));
            visite.setDATE_FIN(cursor.getString(7));
            visite.setDATE_VISITE(cursor.getString(8));
            visite.setGPS_LATITUDE(cursor.getString(9));
            visite.setGPS_LONGITUDE(cursor.getString(10));
            visite.setTYPE_CODE(cursor.getString(11));
            visite.setSTATUT_CODE(cursor.getString(12));
            visite.setCATEGORIE_CODE(cursor.getString(13));
            visite.setTACHE_CODE(cursor.getString(14));
            visite.setCREATEUR_CODE(cursor.getString(15));
            visite.setDATE_CREATION(cursor.getString(16));
            visite.setVERSION(cursor.getString(17));
            visite.setVISITE_RESULTAT(cursor.getInt(18));
            visite.setDISTANCE(cursor.getInt(19));
            visite.setVISITE_SOURCE(cursor.getString(20));
        }

        cursor.close();
        db.close();
        return visite;

    }

    public int  count(String VISITE_CODE) {

        String selectQuery = "SELECT * FROM " + TABLE_VISITE + " WHERE "+ KEY_VISITE_CODE +" = '"+VISITE_CODE+"' AND "+ KEY_VISITE_RESULTAT +" IN ('"+1+"','"+-1+"')";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int vcr=cursor.getCount();
        cursor.close();
        db.close();
        return vcr;

    }

    //GET LIST WITH CLIENT CODE
    public Visite  getByClientCode( String CLIENT_CODE) {

        String selectQuery = "SELECT * FROM " + TABLE_VISITE + " WHERE "+ KEY_CLIENT_CODE +" = '"+CLIENT_CODE+"' AND " + KEY_VISITE_CODE +" LIKE '%"+CLIENT_CODE+"%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Visite visite = new Visite();

        if( cursor != null && cursor.moveToFirst() ) {

            visite.setID(cursor.getInt(0));
            visite.setVISITE_CODE(cursor.getString(1));
            visite.setDISTRIBUTEUR_CODE(cursor.getString(2));
            visite.setUTILISATEUR_CODE(cursor.getString(3));
            visite.setCLIENT_CODE(cursor.getString(4));
            visite.setTOURNEE_CODE(cursor.getString(5));
            visite.setDATE_DEBUT(cursor.getString(6));
            visite.setDATE_FIN(cursor.getString(7));
            visite.setDATE_VISITE(cursor.getString(8));
            visite.setGPS_LATITUDE(cursor.getString(9));
            visite.setGPS_LONGITUDE(cursor.getString(10));
            visite.setTYPE_CODE(cursor.getString(11));
            visite.setSTATUT_CODE(cursor.getString(12));
            visite.setCATEGORIE_CODE(cursor.getString(13));
            visite.setTACHE_CODE(cursor.getString(14));
            visite.setCREATEUR_CODE(cursor.getString(15));
            visite.setDATE_CREATION(cursor.getString(16));
            visite.setVERSION(cursor.getString(17));
            visite.setVISITE_RESULTAT(cursor.getInt(18));
            visite.setDISTANCE(cursor.getInt(19));
            visite.setVISITE_SOURCE(cursor.getString(20));
        }

        cursor.close();
        db.close();
        return visite;

    }


    public ArrayList<Visite> getvisiteCode_Version_List() {
        ArrayList<Visite> listVisite = new ArrayList<>();

        String selectQuery = "SELECT "+ KEY_VISITE_CODE+","+KEY_VERSION +  " FROM " + TABLE_VISITE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Visite v = new Visite();
                v.setID(cursor.getInt(0));
                v.setVISITE_CODE(cursor.getString(1));
                v.setDISTRIBUTEUR_CODE(cursor.getString(2));
                v.setUTILISATEUR_CODE(cursor.getString(3));
                v.setCLIENT_CODE(cursor.getString(4));
                v.setTOURNEE_CODE(cursor.getString(5));
                v.setDATE_DEBUT(cursor.getString(6));
                v.setDATE_FIN(cursor.getString(7));
                v.setDATE_VISITE(cursor.getString(8));
                v.setGPS_LATITUDE(cursor.getString(9));
                v.setGPS_LONGITUDE(cursor.getString(10));
                v.setTYPE_CODE(cursor.getString(11));
                v.setSTATUT_CODE(cursor.getString(12));
                v.setCATEGORIE_CODE(cursor.getString(13));
                v.setTACHE_CODE(cursor.getString(14));
                v.setCREATEUR_CODE(cursor.getString(15));
                v.setDATE_CREATION(cursor.getString(16));
                v.setVERSION(cursor.getString(17));
                v.setVISITE_RESULTAT(cursor.getInt(18));
                v.setDISTANCE(cursor.getInt(19));
                v.setVISITE_SOURCE(cursor.getString(20));
                listVisite.add(v);

            }while(cursor.moveToNext());
        }
        //returner la listTournees;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version Visites from Sqlite: ");
        return listVisite;
    }

    /////////////////////////////GET THE NUMBER OF VISITES WITH DISTINCTS CLIENT NAME///////////////////////////////////////
    public int GetVisiteNombreByTourneeDate(String tournee_code, String date_visite) {

        String selectQuery = "SELECT COUNT (DISTINCT "+KEY_CLIENT_CODE+") FROM " + TABLE_VISITE + " WHERE "+ KEY_TOURNEE_CODE +" = '"+tournee_code+"' AND date("+KEY_DATE_VISITE+")='"+date_visite+"' AND "+KEY_VISITE_RESULTAT+"!= 0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int vcr;
        if(cursor!=null&&cursor.moveToFirst()){
            cursor.moveToFirst();
            vcr=cursor.getInt(0);
        }else{
            vcr=0;
        }

        cursor.close();
        db.close();
        Log.d(TAG, "GetVisiteNombreByTourneeDate:  "+selectQuery + "Number "+vcr);
        return vcr;

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////GET THE NUMBER OF VISITES WITH DISTINCTS CLIENT NAME///////////////////////////////////////
    public int GetVisiteNombreByDate(String date_visite) {

        String selectQuery = "SELECT COUNT (DISTINCT "+KEY_CLIENT_CODE+") FROM " + TABLE_VISITE + " WHERE date("+KEY_DATE_VISITE+")='"+date_visite+"' AND "+KEY_VISITE_RESULTAT+"!= 0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int vcr;
        if(cursor!=null&&cursor.moveToFirst()){
            cursor.moveToFirst();
            vcr=cursor.getInt(0);
        }else{
            vcr=0;
        }

        cursor.close();
        db.close();
        Log.d(TAG, "GetVisiteNombreByTourneeDate:  "+selectQuery + "Number "+vcr);
        return vcr;

    }

    ////////////////////////////////////GET THE NUMBER OF VISITES WITH DISTINCTS CLIENT NAME///////////////////////////////////////
    public int GetVisiteNombre() {

        String selectQuery = "SELECT COUNT (DISTINCT "+KEY_CLIENT_CODE+") FROM " + TABLE_VISITE + " WHERE "+KEY_VISITE_RESULTAT+"!= 0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int vcr;
        if(cursor!=null&&cursor.moveToFirst()){
            cursor.moveToFirst();
            vcr=cursor.getInt(0);
        }else{
            vcr=0;
        }

        cursor.close();
        db.close();
        Log.d(TAG, "GetVisiteNombreByTourneeDate:  "+selectQuery + "Number "+vcr);
        return vcr;

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //DELETE VISITE BY CODE
    public int delete(String visite_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_VISITE,KEY_VISITE_CODE+"=?",new String[]{visite_code});
    }

    public void updateVisite_VRDF(String visiteCode,int visiteresultcode, String datefin){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_VISITE + " SET "+KEY_VISITE_RESULTAT +"='"+visiteresultcode+"',"+KEY_DATE_FIN+"='"+datefin+"' WHERE "+KEY_VISITE_CODE +"= '"+visiteCode+"'" ;
        db.execSQL(req);
        db.close();
        Log.d(TAG, "Visite Updated "+visiteresultcode+" , "+datefin+" , "+req);
    }

    //UPDATE VISITE
    public void updateVisite(String visiteCode,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_VISITE + " SET "+KEY_STATUT_CODE+"= '"+msg+"' WHERE "+KEY_VISITE_CODE +"= '"+visiteCode+"' ;" ;
        db.execSQL(req);
    }

    //DELETE VISITE SYNCHRONISE
    public void deleteVisiteSynchronisee() {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DateVisiteAS = sdf.format(new Date());

        Log.d(TAG, "deleteVisiteSynchronisee: "+DateVisiteAS);

        String Where = " "+KEY_STATUT_CODE+"='inserted' and date("+KEY_DATE_CREATION+")!='"+DateVisiteAS+"' ";
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_VISITE, Where, null);
        db.close();
        Log.d(TAG, "Deleted all visites verifiee from sqlite");
        Log.d("VISITE SYNCHRONISEE "," "+Where);
    }


    public static void synchronisationVisite(final Context context){

        VisiteManager visiteManager = new VisiteManager(context);
        visiteManager.deleteVisiteSynchronisee();

        String tag_string_req = "VISITE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_VISITE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                Log.d("REPSERVEUR1 ",response);

                try {
                    Log.d("REPSERVEUR2 ",response);
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray visites = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre de visites  "+visites.length() , Toast.LENGTH_LONG).show();
                        // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les Visites : " +response);

                        if(visites.length()>0) {
                            VisiteManager visiteManager = new VisiteManager(context);
                            for (int i = 0; i < visites.length(); i++) {
                                JSONObject uneVisite = visites.getJSONObject(i);
                                Log.d("STATUT CODE", "onResponse: "+uneVisite.getString("Statut"));
                                if (uneVisite.getString("Statut").equals("true")) {
                                    cptInsert++;
                                    visiteManager.updateVisite((uneVisite.getString("VISITE_CODE")),"inserted");
                                }else{
                                    cptDelete++;
                                    Log.d("visite","erreur ajout visite" +uneVisite.getString("VISITE_CODE"));
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("VISITE: Inserted: "+cptInsert +" NotInserted: "+cptDelete ,"VISITE",1));

                        }

                        //logM.add("Visite:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncVisite");
                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context,"visite  error if error!=1: "+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("VISITE: Error: "+errorMsg ,"VISITE",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "visite : "+"Json error: " +"erreur applcation visite" + e.getMessage(), Toast.LENGTH_LONG).show();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("VISITE: Error: "+e.getMessage() ,"VISITE",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "visite OnErrorResponse : "+error.getMessage(), Toast.LENGTH_LONG).show();
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("VISITE: Error: "+error.getMessage() ,"VISITE",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();


                    VisiteManager visiteManager  = new VisiteManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableVisite");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(visiteManager.getListNotInserted()));
                    //Log.d(TAG, "getParams: listnotinserted"+visiteManager.getListNotInserted().size());
                    //Log.d(TAG, "getParams: array finale"+arrayFinale.size());

                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public ArrayList<Visite> getListActifVisites() {
        ArrayList<Visite> listVisite = new ArrayList<>();
        String statut="non verifiee";

        String selectQuery = "SELECT * FROM " + TABLE_VISITE +" WHERE "+KEY_VISITE_RESULTAT+"!='0';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Visite visite = new Visite();
                visite.setID(cursor.getInt(0));
                visite.setVISITE_CODE(cursor.getString(1));
                visite.setDISTRIBUTEUR_CODE(cursor.getString(2));
                visite.setUTILISATEUR_CODE(cursor.getString(3));
                visite.setCLIENT_CODE(cursor.getString(4));
                visite.setTOURNEE_CODE(cursor.getString(5));
                visite.setDATE_DEBUT(cursor.getString(6));
                visite.setDATE_FIN(cursor.getString(7));
                visite.setDATE_VISITE(cursor.getString(8));
                visite.setGPS_LATITUDE(cursor.getString(9));
                visite.setGPS_LONGITUDE(cursor.getString(10));
                visite.setTYPE_CODE(cursor.getString(11));
                visite.setSTATUT_CODE(cursor.getString(12));
                visite.setCATEGORIE_CODE(cursor.getString(13));
                visite.setTACHE_CODE(cursor.getString(14));
                visite.setCREATEUR_CODE(cursor.getString(15));
                visite.setDATE_CREATION(cursor.getString(16));
                visite.setVERSION(cursor.getString(17));
                visite.setVISITE_RESULTAT(cursor.getInt(18));
                visite.setDISTANCE(cursor.getInt(19));
                visite.setVISITE_SOURCE(cursor.getString(20));

                listVisite.add(visite);
            }while(cursor.moveToNext());
        }
        //returner la listVisites;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching visites Non vérifiés from Sqlite:  "+listVisite.size());
        Log.d(TAG, "Liste non vérifiée:  "+selectQuery);
        return listVisite;
    }


}

