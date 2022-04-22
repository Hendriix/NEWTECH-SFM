package com.newtech.newtech_sfm.Metier_Manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
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
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier.LivraisonLigne;
import com.newtech.newtech_sfm.Metier.LogSync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TONPC on 26/04/2017.
 */

public class LivraisonLigneManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_LIVRAISON_LIGNE = "livraisonligne";

    private static final String

            KEY_LIVRAISONLIGNE_CODE="LIVRAISONLIGNE_CODE",
            KEY_LIVRAISON_CODE="LIVRAISON_CODE",
            KEY_COMMANDELIGNE_CODE="COMMANDELIGNE_CODE",
            KEY_COMMANDE_CODE="COMMANDE_CODE",
            KEY_FACTURE_CODE="FACTURE_CODE",
            KEY_FAMILLE_CODE="FAMILLE_CODE",
            KEY_ARTICLE_CODE="ARTICLE_CODE",
            KEY_ARTICLE_DESIGNATION="ARTICLE_DESIGNATION",
            KEY_ARTICLE_NBUS_PAR_UP="ARTICLE_NBUS_PAR_UP",
            KEY_ARTICLE_PRIX="ARTICLE_PRIX",
            KEY_QTE_COMMANDEE="QTE_COMMANDEE",
            KEY_QTE_LIVREE="QTE_LIVREE",
            KEY_CAISSE_COMMANDEE="CAISSE_COMMANDEE",
            KEY_CAISSE_LIVREE="CAISSE_LIVREE",
            KEY_LITTRAGE_COMMANDEE="LITTRAGE_COMMANDEE",
            KEY_LITTRAGE_LIVREE="LITTRAGE_LIVREE",
            KEY_TONNAGE_COMMANDEE="TONNAGE_COMMANDEE",
            KEY_TONNAGE_LIVREE="TONNAGE_LIVREE",
            KEY_KG_COMMANDEE="KG_COMMANDEE",
            KEY_KG_LIVREE="KG_LIVREE",
            KEY_MONTANT_BRUT="MONTANT_BRUT",
            KEY_REMISE="REMISE",
            KEY_MONTANT_NET="MONTANT_NET",
            KEY_COMMENTAIRE="COMMENTAIRE",
            KEY_CREATEUR_CODE="CREATEUR_CODE",
            KEY_DATE_CREATION="DATE_CREATION",
            KEY_TS="TS",
            KEY_UNITE_CODE="UNITE_CODE",
            KEY_SOURCE="SOURCE",
            KEY_VERSION="VERSION";


    public static String CREATE_LIVRAISON_LIGNE_TABLE= "CREATE TABLE " + TABLE_LIVRAISON_LIGNE+ "("

            +KEY_LIVRAISONLIGNE_CODE  + " TEXT ,"
            +KEY_LIVRAISON_CODE  + " TEXT ,"
            +KEY_COMMANDELIGNE_CODE  + " TEXT ,"
            +KEY_COMMANDE_CODE  + " TEXT ,"
            +KEY_FACTURE_CODE  + " TEXT ,"
            +KEY_FAMILLE_CODE  + " TEXT ,"
            +KEY_ARTICLE_CODE  + " TEXT ,"
            +KEY_ARTICLE_DESIGNATION  + " TEXT ,"
            +KEY_ARTICLE_NBUS_PAR_UP  + " NUMBER ,"
            +KEY_ARTICLE_PRIX  + " NUMBER ,"
            +KEY_QTE_COMMANDEE  + " NUMBER ,"
            +KEY_QTE_LIVREE  + " NUMBER ,"
            +KEY_CAISSE_COMMANDEE  + " NUMERIC,"
            +KEY_CAISSE_LIVREE  + " NUMERIC,"
            +KEY_LITTRAGE_COMMANDEE  + " NUMBER ,"
            +KEY_LITTRAGE_LIVREE  + " NUMBER ,"
            +KEY_TONNAGE_COMMANDEE  + " NUMBER ,"
            +KEY_TONNAGE_LIVREE  + " NUMBER ,"
            +KEY_KG_COMMANDEE  + " NUMBER ,"
            +KEY_KG_LIVREE  + " NUMBER ,"
            +KEY_MONTANT_BRUT  + " NUMBER ,"
            +KEY_REMISE  + " NUMBER ,"
            +KEY_MONTANT_NET  + " NUMBER ,"
            +KEY_COMMENTAIRE  + " TEXT ,"
            +KEY_CREATEUR_CODE  + " TEXT ,"
            +KEY_DATE_CREATION  + " TEXT ,"
            +KEY_TS  + " TEXT ,"
            +KEY_UNITE_CODE  + " TEXT ,"
            +KEY_SOURCE + " TEXT ,"
            +KEY_VERSION  + " TEXT ,"
            +"PRIMARY KEY ("+KEY_LIVRAISON_CODE+","+KEY_LIVRAISONLIGNE_CODE+"))";


    public LivraisonLigneManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_COMMANDE_LIGNE);
            db.execSQL(CREATE_LIVRAISON_LIGNE_TABLE);
        } catch (SQLException e) {

        }
        Log.d(TAG, "table LivraisonLigne created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIVRAISON_LIGNE);
        onCreate(db);

    }

    public ArrayList<LivraisonLigne> getListALivrer(ArrayList<CommandeLigne> commandeLignes, ArrayList<LivraisonLigne> livraisonLignes,String livraison_code, Context context){

        ArrayList<LivraisonLigne> livraisonLigneTemp= new ArrayList<>();
        ArrayList<LivraisonLigne> livraisonLigneFinal= new ArrayList<>();
        LivraisonLigne livraisonLigne = new LivraisonLigne();

        String ARTICLE_CODE ="";
        String UNITE_CODE="";

        for(int i=0; i<commandeLignes.size();i++){

            ARTICLE_CODE=commandeLignes.get(i).getARTICLE_CODE();
            UNITE_CODE=commandeLignes.get(i).getUNITE_CODE();

            Log.d(TAG, "getListALivrer: commandeligne"+(i+1));

            Log.d(TAG, "getListALivrer cmdl: "+ARTICLE_CODE);
            Log.d(TAG, "getListALivrer cmdl: "+UNITE_CODE);

            Log.d(TAG, "getListALivrer: livraisonlignescmd"+livraisonLignes.size());

           if(livraisonLignes.size()>0){

                for(int j=0;j<livraisonLignes.size();j++){

                    Log.d(TAG, "getListALivrer: livraisonlignes"+ livraisonLignes);
                    if(livraisonLignes.get(j).getARTICLE_CODE().equals(ARTICLE_CODE) && livraisonLignes.get(j).getUNITE_CODE().equals(UNITE_CODE)){
                        Log.d(TAG, "getListALivrer livl: "+livraisonLignes.get(j).getARTICLE_CODE());
                        Log.d(TAG, "getListALivrer livl: "+livraisonLignes.get(j).getUNITE_CODE());
                        livraisonLigneTemp.add(livraisonLignes.get(j));
                    }
                }

               if(livraisonLigneTemp.size()>0){
                   Log.d(TAG, "getListALivrer livTemp >: "+livraisonLigneTemp.size());
                   Log.d(TAG, "getListALivrer livTemp >: "+ livraisonLigneTemp);
                   livraisonLigne = getLivraisonLigneALivrer(commandeLignes.get(i),livraisonLigneTemp,livraison_code,context);

               }else{
                   Log.d(TAG, "getListALivrer livTemp <: "+livraisonLigneTemp.size());
                   Log.d(TAG, "getListALivrer livTemp <: "+ livraisonLigneTemp);
                   livraisonLigne = new LivraisonLigne(commandeLignes.get(i),livraison_code,context);
               }

               if(livraisonLigne!=null){
                   livraisonLigneFinal.add(livraisonLigne);
               }

           }else{

               livraisonLigne = getLivraisonLigneALivrer(commandeLignes.get(i),livraisonLigneTemp,livraison_code,context);

               if(!livraisonLigne.equals(null)){
                   livraisonLigneFinal.add(livraisonLigne);
               }
           }

           livraisonLigneTemp.clear();
        }

        for(int i=0;i<livraisonLigneFinal.size();i++){
            livraisonLigneFinal.get(i).setLIVRAISONLIGNE_CODE(i+1);
        }

        return livraisonLigneFinal;
    }

    public ArrayList<LivraisonLigne> getListAL(ArrayList<CommandeLigne> commandeLignes,String livraison_code, Context context){

        ArrayList<LivraisonLigne> livraisonLigneTemp= new ArrayList<>();
        ArrayList<LivraisonLigne> livraisonLigneFinal= new ArrayList<>();
        LivraisonLigne livraisonLigne;

        for(int i=0; i<commandeLignes.size();i++){

                livraisonLigne = getLivraisonLigneAL(commandeLignes.get(i),livraison_code,context);

                if(livraisonLigne != null){
                    livraisonLigneFinal.add(livraisonLigne);
                }

            livraisonLigneTemp.clear();
        }

        for(int i=0;i<livraisonLigneFinal.size();i++){
            livraisonLigneFinal.get(i).setLIVRAISONLIGNE_CODE(i+1);
        }

        return livraisonLigneFinal;
    }


    public LivraisonLigne getLivraisonLigneALivrer(CommandeLigne commandeLigne, ArrayList<LivraisonLigne> livraisonLignes, String livraison_code, Context context){
        LivraisonLigne livraisonLigne = new LivraisonLigne();

        double QTE_COMMANDEE=0;
        double QTE_LIVREE=0;

        double CAISSE_COMMANDEE=0;
        double CAISSE_LIVREE=0;

        double LITTRAGE_COMMANDEE=0;
        double LITTRAGE_LIVREE=0;

        double TONNAGE_COMMANDEE=0;
        double TONNAGE_LIVREE=0;

        double KG_COMMANDEE=0;
        double KG_LIVREE=0;

        double MONTANT_BRUT=0;
        double REMISE=0;
        double MONTANT_NET=0;

        Log.d(TAG, "getLivraisonLigneALivrer: "+livraisonLignes.size());
        Log.d(TAG, "getLivraisonLigneALivrer: "+livraisonLignes.toString());
        if(livraisonLignes.size() > 0){



            for(int i=0; i<livraisonLignes.size();i++){

                QTE_COMMANDEE+=livraisonLignes.get(i).getQTE_COMMANDEE();
                QTE_LIVREE+=livraisonLignes.get(i).getQTE_LIVREE();
                CAISSE_COMMANDEE+=livraisonLignes.get(i).getCAISSE_COMMANDEE();
                CAISSE_LIVREE+=livraisonLignes.get(i).getCAISSE_LIVREE();
                LITTRAGE_COMMANDEE+=livraisonLignes.get(i).getLITTRAGE_COMMANDEE();
                LITTRAGE_LIVREE+=livraisonLignes.get(i).getLITTRAGE_LIVREE();
                TONNAGE_COMMANDEE+=livraisonLignes.get(i).getTONNAGE_COMMANDEE();
                TONNAGE_LIVREE+=livraisonLignes.get(i).getTONNAGE_LIVREE();
                KG_COMMANDEE+=livraisonLignes.get(i).getKG_COMMANDEE();
                KG_LIVREE+=livraisonLignes.get(i).getKG_LIVREE();
                MONTANT_BRUT+=livraisonLignes.get(i).getMONTANT_BRUT();
                REMISE+=livraisonLignes.get(i).getREMISE();
                MONTANT_NET+=livraisonLignes.get(i).getMONTANT_NET();

            }

            Log.d(TAG, "getLivraisonLigneALivrer 5: "+QTE_COMMANDEE);
            Log.d(TAG, "getLivraisonLigneALivrer 6: "+commandeLigne.getQTE_COMMANDEE());

            if(commandeLigne.getQTE_COMMANDEE()-QTE_COMMANDEE>0){
                livraisonLigne = new LivraisonLigne(livraison_code,commandeLigne,context);
                Log.d(TAG, "getLivraisonLigneALivrer1: "+livraisonLigne);
                /*livraisonLigne.setQTE_COMMANDEE(commandeLigne.getQTE_COMMANDEE()-QTE_COMMANDEE);
                livraisonLigne.setQTE_LIVREE(QTE_LIVREE);
                livraisonLigne.setCAISSE_COMMANDEE(commandeLigne.getCAISSE_COMMANDEE()-CAISSE_COMMANDEE);
                livraisonLigne.setCAISSE_LIVREE(CAISSE_LIVREE);
                livraisonLigne.setLITTRAGE_COMMANDEE(commandeLigne.getLITTRAGE_COMMANDEE()-LITTRAGE_COMMANDEE);
                livraisonLigne.setLITTRAGE_LIVREE(LITTRAGE_LIVREE);
                livraisonLigne.setTONNAGE_COMMANDEE(commandeLigne.getTONNAGE_COMMANDEE()-TONNAGE_COMMANDEE);
                livraisonLigne.setTONNAGE_LIVREE(TONNAGE_LIVREE);
                livraisonLigne.setKG_COMMANDEE(commandeLigne.getKG_COMMANDEE()-KG_COMMANDEE);
                livraisonLigne.setKG_LIVREE(KG_LIVREE);
                livraisonLigne.setMONTANT_BRUT(commandeLigne.getMONTANT_BRUT()-MONTANT_BRUT);
                livraisonLigne.setREMISE(commandeLigne.getREMISE()-REMISE);
                livraisonLigne.setMONTANT_NET(commandeLigne.getMONTANT_NET()-MONTANT_NET);*/

                livraisonLigne.setQTE_COMMANDEE(commandeLigne.getQTE_COMMANDEE()-QTE_COMMANDEE);
                livraisonLigne.setQTE_LIVREE(commandeLigne.getQTE_COMMANDEE()-QTE_COMMANDEE);
                livraisonLigne.setCAISSE_COMMANDEE(commandeLigne.getCAISSE_COMMANDEE()-CAISSE_COMMANDEE);
                livraisonLigne.setCAISSE_LIVREE(commandeLigne.getCAISSE_COMMANDEE()-CAISSE_COMMANDEE);
                livraisonLigne.setLITTRAGE_COMMANDEE(commandeLigne.getLITTRAGE_COMMANDEE()-LITTRAGE_COMMANDEE);
                livraisonLigne.setLITTRAGE_LIVREE(commandeLigne.getLITTRAGE_COMMANDEE()-LITTRAGE_COMMANDEE);
                livraisonLigne.setTONNAGE_COMMANDEE(commandeLigne.getTONNAGE_COMMANDEE()-TONNAGE_COMMANDEE);
                livraisonLigne.setTONNAGE_LIVREE(commandeLigne.getTONNAGE_COMMANDEE()-TONNAGE_COMMANDEE);
                livraisonLigne.setKG_COMMANDEE(commandeLigne.getKG_COMMANDEE()-KG_COMMANDEE);
                livraisonLigne.setKG_LIVREE(commandeLigne.getKG_COMMANDEE()-KG_COMMANDEE);
                livraisonLigne.setMONTANT_BRUT(commandeLigne.getMONTANT_BRUT()-MONTANT_BRUT);
                livraisonLigne.setREMISE(commandeLigne.getREMISE()-REMISE);
                livraisonLigne.setMONTANT_NET(commandeLigne.getMONTANT_NET()-MONTANT_NET);

                Log.d(TAG, "getLivraisonLigneALivrer2: "+livraisonLigne);

            }else{
                Log.d(TAG, "getLivraisonLigneALivrer3: "+livraisonLigne);
                livraisonLigne=null;
            }

        }else{

            livraisonLigne = new LivraisonLigne(commandeLigne,livraison_code,context);

        }

        return livraisonLigne;
    }

    public LivraisonLigne getLivraisonLigneAL(CommandeLigne commandeLigne, String livraison_code, Context context){
        LivraisonLigne livraisonLigne = null;

        if((commandeLigne.getQTE_COMMANDEE() - commandeLigne.getQTE_LIVREE()) != 0){
            livraisonLigne = new LivraisonLigne(commandeLigne,livraison_code,context);
        }

        return livraisonLigne;
    }

    public void add(LivraisonLigne livraisonLigne) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_LIVRAISONLIGNE_CODE, livraisonLigne.getLIVRAISONLIGNE_CODE());
        values.put(KEY_LIVRAISON_CODE , livraisonLigne.getLIVRAISON_CODE());
        values.put(KEY_COMMANDELIGNE_CODE , livraisonLigne.getCOMMANDELIGNE_CODE());
        values.put(KEY_COMMANDE_CODE , livraisonLigne.getCOMMANDE_CODE());
        values.put(KEY_FACTURE_CODE , livraisonLigne.getFACTURE_CODE());
        values.put(KEY_FAMILLE_CODE , livraisonLigne.getFAMILLE_CODE());
        values.put(KEY_ARTICLE_CODE , livraisonLigne.getARTICLE_CODE());
        values.put(KEY_ARTICLE_DESIGNATION , livraisonLigne.getARTICLE_DESIGNATION());
        values.put(KEY_ARTICLE_NBUS_PAR_UP , livraisonLigne.getARTICLE_NBUS_PAR_UP());
        values.put(KEY_ARTICLE_PRIX , livraisonLigne.getARTICLE_PRIX());
        values.put(KEY_QTE_COMMANDEE , livraisonLigne.getQTE_COMMANDEE());
        values.put(KEY_QTE_LIVREE , livraisonLigne.getQTE_COMMANDEE());
        values.put(KEY_CAISSE_COMMANDEE , livraisonLigne.getCAISSE_COMMANDEE());
        values.put(KEY_CAISSE_LIVREE , livraisonLigne.getCAISSE_COMMANDEE());
        values.put(KEY_LITTRAGE_COMMANDEE , livraisonLigne.getLITTRAGE_COMMANDEE());
        values.put(KEY_LITTRAGE_LIVREE , livraisonLigne.getLITTRAGE_COMMANDEE());
        values.put(KEY_TONNAGE_COMMANDEE , livraisonLigne.getTONNAGE_COMMANDEE());
        values.put(KEY_TONNAGE_LIVREE , livraisonLigne.getTONNAGE_COMMANDEE());
        values.put(KEY_KG_COMMANDEE , livraisonLigne.getKG_COMMANDEE());
        values.put(KEY_KG_LIVREE , livraisonLigne.getKG_COMMANDEE());
        values.put(KEY_MONTANT_BRUT , getNumberRounded(livraisonLigne.getMONTANT_BRUT()));
        values.put(KEY_REMISE , getNumberRounded(livraisonLigne.getREMISE()));
        values.put(KEY_MONTANT_NET , getNumberRounded(livraisonLigne.getMONTANT_NET()));
        values.put(KEY_COMMENTAIRE , livraisonLigne.getCOMMENTAIRE());
        values.put(KEY_CREATEUR_CODE , livraisonLigne.getCREATEUR_CODE());
        values.put(KEY_DATE_CREATION , livraisonLigne.getDATE_CREATION());
        values.put(KEY_TS , livraisonLigne.getTS());
        values.put(KEY_UNITE_CODE , livraisonLigne.getUNITE_CODE());
        values.put(KEY_SOURCE , livraisonLigne.getSOURCE());
        values.put(KEY_VERSION , livraisonLigne.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_LIVRAISON_LIGNE, null, values,SQLiteDatabase.CONFLICT_IGNORE);
        db.close(); // Closing database connection
        Log.d(TAG, "nouvelle livraison inserée dans la table LivraisonLigne: " + id);
    }

    public ArrayList<LivraisonLigne> getList() {
        ArrayList<LivraisonLigne> listLivraisonL = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_LIVRAISON_LIGNE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "Nombre de LivraisonLigne: "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                LivraisonLigne livraisonLigne = new LivraisonLigne();

                livraisonLigne.setLIVRAISONLIGNE_CODE(cursor.getInt(0));
                livraisonLigne.setLIVRAISON_CODE(cursor.getString(1));
                livraisonLigne.setCOMMANDELIGNE_CODE(cursor.getDouble(2));
                livraisonLigne.setCOMMANDE_CODE(cursor.getString(3));
                livraisonLigne.setFACTURE_CODE(cursor.getString(4));
                livraisonLigne.setFAMILLE_CODE(cursor.getString(5));
                livraisonLigne.setARTICLE_CODE(cursor.getString(6));
                livraisonLigne.setARTICLE_DESIGNATION(cursor.getString(7));
                livraisonLigne.setARTICLE_NBUS_PAR_UP(cursor.getDouble(8));
                livraisonLigne.setARTICLE_PRIX(cursor.getDouble(9));
                livraisonLigne.setQTE_COMMANDEE(cursor.getDouble(10));
                livraisonLigne.setQTE_LIVREE(cursor.getDouble(11));
                livraisonLigne.setCAISSE_COMMANDEE(cursor.getDouble(12));
                livraisonLigne.setCAISSE_LIVREE(cursor.getDouble(13));
                livraisonLigne.setLITTRAGE_COMMANDEE(cursor.getDouble(14));
                livraisonLigne.setLITTRAGE_LIVREE(cursor.getDouble(15));
                livraisonLigne.setTONNAGE_COMMANDEE(cursor.getDouble(16));
                livraisonLigne.setTONNAGE_LIVREE(cursor.getDouble(17));
                livraisonLigne.setKG_COMMANDEE(cursor.getDouble(18));
                livraisonLigne.setKG_LIVREE(cursor.getDouble(19));
                livraisonLigne.setMONTANT_BRUT(cursor.getDouble(20));
                livraisonLigne.setREMISE(cursor.getDouble(21));
                livraisonLigne.setMONTANT_NET(cursor.getDouble(22));
                livraisonLigne.setCOMMENTAIRE(cursor.getString(23));
                livraisonLigne.setCREATEUR_CODE(cursor.getString(24));
                livraisonLigne.setDATE_CREATION(cursor.getString(25));
                livraisonLigne.setTS(cursor.getString(26));
                livraisonLigne.setUNITE_CODE(cursor.getString(27));
                livraisonLigne.setSOURCE(cursor.getString(28));
                livraisonLigne.setVERSION(cursor.getString(29));

                listLivraisonL.add(livraisonLigne);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Livraison ligne from table LivraisonLigne: ");
        return listLivraisonL;
    }

    public ArrayList<LivraisonLigne> getListNotInserted() {
        ArrayList<LivraisonLigne> listLivraisonL = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_LIVRAISON_LIGNE +" WHERE "+KEY_COMMENTAIRE+" = 'to_insert' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "Nombre de LivraisonLigne: "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                LivraisonLigne livraisonLigne = new LivraisonLigne();

                livraisonLigne.setLIVRAISONLIGNE_CODE(cursor.getInt(0));
                livraisonLigne.setLIVRAISON_CODE(cursor.getString(1));
                livraisonLigne.setCOMMANDELIGNE_CODE(cursor.getDouble(2));
                livraisonLigne.setCOMMANDE_CODE(cursor.getString(3));
                livraisonLigne.setFACTURE_CODE(cursor.getString(4));
                livraisonLigne.setFAMILLE_CODE(cursor.getString(5));
                livraisonLigne.setARTICLE_CODE(cursor.getString(6));
                livraisonLigne.setARTICLE_DESIGNATION(cursor.getString(7));
                livraisonLigne.setARTICLE_NBUS_PAR_UP(cursor.getDouble(8));
                livraisonLigne.setARTICLE_PRIX(cursor.getDouble(9));
                livraisonLigne.setQTE_COMMANDEE(cursor.getDouble(10));
                livraisonLigne.setQTE_LIVREE(cursor.getDouble(11));
                livraisonLigne.setCAISSE_COMMANDEE(cursor.getDouble(12));
                livraisonLigne.setCAISSE_LIVREE(cursor.getDouble(13));
                livraisonLigne.setLITTRAGE_COMMANDEE(cursor.getDouble(14));
                livraisonLigne.setLITTRAGE_LIVREE(cursor.getDouble(15));
                livraisonLigne.setTONNAGE_COMMANDEE(cursor.getDouble(16));
                livraisonLigne.setTONNAGE_LIVREE(cursor.getDouble(17));
                livraisonLigne.setKG_COMMANDEE(cursor.getDouble(18));
                livraisonLigne.setKG_LIVREE(cursor.getDouble(19));
                livraisonLigne.setMONTANT_BRUT(cursor.getDouble(20));
                livraisonLigne.setREMISE(cursor.getDouble(21));
                livraisonLigne.setMONTANT_NET(cursor.getDouble(22));
                livraisonLigne.setCOMMENTAIRE(cursor.getString(23));
                livraisonLigne.setCREATEUR_CODE(cursor.getString(24));
                livraisonLigne.setDATE_CREATION(cursor.getString(25));
                livraisonLigne.setTS(cursor.getString(26));
                livraisonLigne.setUNITE_CODE(cursor.getString(27));
                livraisonLigne.setSOURCE(cursor.getString(28));
                livraisonLigne.setVERSION(cursor.getString(29));

                listLivraisonL.add(livraisonLigne);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Livraison ligne from table LivraisonLigne: ");
        return listLivraisonL;
    }

    public ArrayList<LivraisonLigne> getListByCmdCode(String commande_code) {
        ArrayList<LivraisonLigne> listLivraisonL = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_LIVRAISON_LIGNE +" WHERE "+KEY_COMMANDE_CODE +"= '"+commande_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "Nombre de LivraisonLigne: "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                LivraisonLigne livraisonLigne = new LivraisonLigne();

                livraisonLigne.setLIVRAISONLIGNE_CODE(cursor.getInt(0));
                livraisonLigne.setLIVRAISON_CODE(cursor.getString(1));
                livraisonLigne.setCOMMANDELIGNE_CODE(cursor.getDouble(2));
                livraisonLigne.setCOMMANDE_CODE(cursor.getString(3));
                livraisonLigne.setFACTURE_CODE(cursor.getString(4));
                livraisonLigne.setFAMILLE_CODE(cursor.getString(5));
                livraisonLigne.setARTICLE_CODE(cursor.getString(6));
                livraisonLigne.setARTICLE_DESIGNATION(cursor.getString(7));
                livraisonLigne.setARTICLE_NBUS_PAR_UP(cursor.getDouble(8));
                livraisonLigne.setARTICLE_PRIX(cursor.getDouble(9));
                livraisonLigne.setQTE_COMMANDEE(cursor.getDouble(10));
                livraisonLigne.setQTE_LIVREE(cursor.getDouble(11));
                livraisonLigne.setCAISSE_COMMANDEE(cursor.getDouble(12));
                livraisonLigne.setCAISSE_LIVREE(cursor.getDouble(13));
                livraisonLigne.setLITTRAGE_COMMANDEE(cursor.getDouble(14));
                livraisonLigne.setLITTRAGE_LIVREE(cursor.getDouble(15));
                livraisonLigne.setTONNAGE_COMMANDEE(cursor.getDouble(16));
                livraisonLigne.setTONNAGE_LIVREE(cursor.getDouble(17));
                livraisonLigne.setKG_COMMANDEE(cursor.getDouble(18));
                livraisonLigne.setKG_LIVREE(cursor.getDouble(19));
                livraisonLigne.setMONTANT_BRUT(cursor.getDouble(20));
                livraisonLigne.setREMISE(cursor.getDouble(21));
                livraisonLigne.setMONTANT_NET(cursor.getDouble(22));
                livraisonLigne.setCOMMENTAIRE(cursor.getString(23));
                livraisonLigne.setCREATEUR_CODE(cursor.getString(24));
                livraisonLigne.setDATE_CREATION(cursor.getString(25));
                livraisonLigne.setTS(cursor.getString(26));
                livraisonLigne.setUNITE_CODE(cursor.getString(27));
                livraisonLigne.setSOURCE(cursor.getString(28));
                livraisonLigne.setVERSION(cursor.getString(29));

                listLivraisonL.add(livraisonLigne);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Livraison ligne from table LivraisonLigne: "+listLivraisonL);
        return listLivraisonL;
    }

    public ArrayList<LivraisonLigne> getListByLivCode(String livraison_code) {
        ArrayList<LivraisonLigne> listLivraisonL = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_LIVRAISON_LIGNE +" WHERE "+KEY_LIVRAISON_CODE +"= '"+livraison_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "Nombre de LivraisonLigne: "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                LivraisonLigne livraisonLigne = new LivraisonLigne();

                livraisonLigne.setLIVRAISONLIGNE_CODE(cursor.getInt(0));
                livraisonLigne.setLIVRAISON_CODE(cursor.getString(1));
                livraisonLigne.setCOMMANDELIGNE_CODE(cursor.getDouble(2));
                livraisonLigne.setCOMMANDE_CODE(cursor.getString(3));
                livraisonLigne.setFACTURE_CODE(cursor.getString(4));
                livraisonLigne.setFAMILLE_CODE(cursor.getString(5));
                livraisonLigne.setARTICLE_CODE(cursor.getString(6));
                livraisonLigne.setARTICLE_DESIGNATION(cursor.getString(7));
                livraisonLigne.setARTICLE_NBUS_PAR_UP(cursor.getDouble(8));
                livraisonLigne.setARTICLE_PRIX(cursor.getDouble(9));
                livraisonLigne.setQTE_COMMANDEE(cursor.getDouble(10));
                livraisonLigne.setQTE_LIVREE(cursor.getDouble(11));
                livraisonLigne.setCAISSE_COMMANDEE(cursor.getDouble(12));
                livraisonLigne.setCAISSE_LIVREE(cursor.getDouble(13));
                livraisonLigne.setLITTRAGE_COMMANDEE(cursor.getDouble(14));
                livraisonLigne.setLITTRAGE_LIVREE(cursor.getDouble(15));
                livraisonLigne.setTONNAGE_COMMANDEE(cursor.getDouble(16));
                livraisonLigne.setTONNAGE_LIVREE(cursor.getDouble(17));
                livraisonLigne.setKG_COMMANDEE(cursor.getDouble(18));
                livraisonLigne.setKG_LIVREE(cursor.getDouble(19));
                livraisonLigne.setMONTANT_BRUT(cursor.getDouble(20));
                livraisonLigne.setREMISE(cursor.getDouble(21));
                livraisonLigne.setMONTANT_NET(cursor.getDouble(22));
                livraisonLigne.setCOMMENTAIRE(cursor.getString(23));
                livraisonLigne.setCREATEUR_CODE(cursor.getString(24));
                livraisonLigne.setDATE_CREATION(cursor.getString(25));
                livraisonLigne.setTS(cursor.getString(26));
                livraisonLigne.setUNITE_CODE(cursor.getString(27));
                livraisonLigne.setSOURCE(cursor.getString(28));
                livraisonLigne.setVERSION(cursor.getString(29));

                listLivraisonL.add(livraisonLigne);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Livraison ligne from table LivraisonLigne: "+listLivraisonL);
        return listLivraisonL;
    }

    public ArrayList<LivraisonLigne> getListByLivraisonCode(String livraison_code) {
        ArrayList<LivraisonLigne> listLivraisonL = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_LIVRAISON_LIGNE +" WHERE "+KEY_LIVRAISON_CODE +"= '"+livraison_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "Nombre de LivraisonLigne: "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                LivraisonLigne livraisonLigne = new LivraisonLigne();

                livraisonLigne.setLIVRAISONLIGNE_CODE(cursor.getInt(0));
                livraisonLigne.setLIVRAISON_CODE(cursor.getString(1));
                livraisonLigne.setCOMMANDELIGNE_CODE(cursor.getDouble(2));
                livraisonLigne.setCOMMANDE_CODE(cursor.getString(3));
                livraisonLigne.setFACTURE_CODE(cursor.getString(4));
                livraisonLigne.setFAMILLE_CODE(cursor.getString(5));
                livraisonLigne.setARTICLE_CODE(cursor.getString(6));
                livraisonLigne.setARTICLE_DESIGNATION(cursor.getString(7));
                livraisonLigne.setARTICLE_NBUS_PAR_UP(cursor.getDouble(8));
                livraisonLigne.setARTICLE_PRIX(cursor.getDouble(9));
                livraisonLigne.setQTE_COMMANDEE(cursor.getDouble(10));
                livraisonLigne.setQTE_LIVREE(cursor.getDouble(11));
                livraisonLigne.setCAISSE_COMMANDEE(cursor.getDouble(12));
                livraisonLigne.setCAISSE_LIVREE(cursor.getDouble(13));
                livraisonLigne.setLITTRAGE_COMMANDEE(cursor.getDouble(14));
                livraisonLigne.setLITTRAGE_LIVREE(cursor.getDouble(15));
                livraisonLigne.setTONNAGE_COMMANDEE(cursor.getDouble(16));
                livraisonLigne.setTONNAGE_LIVREE(cursor.getDouble(17));
                livraisonLigne.setKG_COMMANDEE(cursor.getDouble(18));
                livraisonLigne.setKG_LIVREE(cursor.getDouble(19));
                livraisonLigne.setMONTANT_BRUT(cursor.getDouble(20));
                livraisonLigne.setREMISE(cursor.getDouble(21));
                livraisonLigne.setMONTANT_NET(cursor.getDouble(22));
                livraisonLigne.setCOMMENTAIRE(cursor.getString(23));
                livraisonLigne.setCREATEUR_CODE(cursor.getString(24));
                livraisonLigne.setDATE_CREATION(cursor.getString(25));
                livraisonLigne.setTS(cursor.getString(26));
                livraisonLigne.setUNITE_CODE(cursor.getString(27));
                livraisonLigne.setSOURCE(cursor.getString(28));
                livraisonLigne.setVERSION(cursor.getString(29));

                listLivraisonL.add(livraisonLigne);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Livraison ligne from table LivraisonLigne: "+listLivraisonL);
        return listLivraisonL;
    }

    public int delete(String livraisonLigne_code ,String livraison_code ) {
        Log.d(TAG, "Deleted livraisonLignes from sqlite : "+livraisonLigne_code+" ; "+livraison_code);
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_LIVRAISON_LIGNE,KEY_LIVRAISONLIGNE_CODE+"=?  AND "+KEY_LIVRAISON_CODE+"=?" ,new String[]{livraisonLigne_code,livraison_code});

    }

    public int deleteByLivraisonCode(String livraisonCode ) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_LIVRAISON_LIGNE,KEY_LIVRAISON_CODE+"=?" ,new String[]{livraisonCode});

    }


    public void deletelivraisonLigneSynchronisee() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DateLivraisonL = sdf.format(new Date());
        String Where = " "+KEY_COMMENTAIRE+"='inserted' and "+KEY_COMMANDE_CODE+" not in (SELECT commandenoncloturee.COMMANDE_CODE FROM commandenoncloturee)";

        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_LIVRAISON_LIGNE, Where, null);
        db.close();
        Log.d(TAG, "Deleted all livraisonLignes verifiee from sqlite");
        Log.d("livraisonligne", "deletelivraisonLigneSynchronisee: "+Where);
    }

    public void deletelivraisonLignes() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LIVRAISON_LIGNE, null, null);
        db.close();
        Log.d(TAG, "Deleted all livraisonLignes info from sqlite");

    }

    public void updateLivraisonLigne(String livraison_code,double livraisonLigne_code,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_LIVRAISON_LIGNE + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_LIVRAISON_CODE +"= '"+livraison_code+"' AND "+KEY_LIVRAISONLIGNE_CODE +"= '"+livraisonLigne_code+"'" ;
        db.execSQL(req);
        db.close();
        Log.d("livraisonligne", "updateLivraisonLigne: "+req);

    }

    public static void synchronisationLivraisonLigne(final Context context){

        LivraisonLigneManager livraisonLigneManager = new LivraisonLigneManager(context);
        livraisonLigneManager.deletelivraisonLigneSynchronisee();

        String tag_string_req = "LIVRAISON_LIGNE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_LIVRAISON_LIGNE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("LivraisonLigne ", ""+response);
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray livraisonLignes = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre de livraisonLignes  "+livraisonLignes.length() , Toast.LENGTH_LONG).show();
                        // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les Livraison Lignes : " +livraisonLignes.toString());

                        if(livraisonLignes.length()>0) {
                            LivraisonLigneManager livraisonLigneManager = new LivraisonLigneManager(context);
                            for (int i = 0; i < livraisonLignes.length(); i++) {
                                JSONObject uneLivraisonLigne = livraisonLignes.getJSONObject(i);

                                if (uneLivraisonLigne.getString("Statut").equals("true")) {
                                    livraisonLigneManager.updateLivraisonLigne((uneLivraisonLigne.getString("LIVRAISON_CODE")),(uneLivraisonLigne.getDouble("LIVRAISONLIGNE_CODE")), "inserted");
                                    cptInsert++;
                                }else{
                                    cptDelete++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON_LIGNE: Insert: "+cptInsert +" NotInserted: "+cptDelete ,"LIVRAISON_LIGNE",1));

                        }
                        logM.add("LivraisonLigne :OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncLivraisonLigne");
                    }else{
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "livraisonLigne: "+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON_LIGNE: Error: "+errorMsg ,"LIVRAISON_LIGNE",0));

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON_LIGNE: Error: "+e.getMessage() ,"LIVRAISON_LIGNE",0));

                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "livraisonLigne: "+error.getMessage(), Toast.LENGTH_LONG).show();
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON_LIGNE: Error: "+error.getMessage() ,"LIVRAISON_LIGNE",0));

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    LivraisonLigneManager livraisonLigneManager  = new LivraisonLigneManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableLivraisonLigne");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(livraisonLigneManager.getListNotInserted()));
                    Log.d("LivraisonLignesend", "liste: "+livraisonLigneManager.getListNotInserted());
                }
                return arrayFinale;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


    }

    public static void synchronisationLivraisonLignePull(final Context context){

        String tag_string_req = "LIVRAISON_LIGNE_PULL";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_LIVRAISON_LIGNE_PULL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ouvrir le logManager
                Log.d("LivraisonLignePull ", ""+response);
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray LivraisonLigneLigne = jObj.getJSONArray("LivraisonLignes");
                        //Toast.makeText(context, "Nombre de LivraisonLignePull " +LivraisonLigneLigne.length(), Toast.LENGTH_SHORT).show();
                        int cptInsert = 0,cptDeleted = 0;
                        //Ajout/Suppression/modification des articles dans la base de données.
                        for (int i = 0; i < LivraisonLigneLigne.length(); i++) {
                            JSONObject uneLivraisonLigne = LivraisonLigneLigne.getJSONObject(i);
                            LivraisonLigneManager livraisonLigneManager = new LivraisonLigneManager(context);


                            if(uneLivraisonLigne.getString("OPERATION").equals("DELETE")){
                                livraisonLigneManager.delete(uneLivraisonLigne.getString("LIVRAISONLIGNE_CODE"),uneLivraisonLigne.getString("LIVRAISON_CODE"));
                                cptDeleted++;
                            }
                            else {
                                Log.d("LivraisonLignePull", "onResponse: LivraisonLignePull"+uneLivraisonLigne.toString());
                                Log.d("LivraisonLignePull", "onResponse: LivraisonLignePull added"+(new LivraisonLigne(uneLivraisonLigne).toString()));

                                livraisonLigneManager.add(new LivraisonLigne(uneLivraisonLigne));
                                cptInsert++;
                            }

                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON_LIGNE_PULL: Insert: "+cptInsert +" Deleted: "+cptDeleted ,"LIVRAISON_LIGNE_PULL",1));

                        }

                        //logM.add("LivraisonLignePull:OK Insert:"+cptInsert +"Deleted:"+cptDeleted ,"SyncLivraisonLignePull");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //logM.add("LivraisonLignePull :NOK Insert "+errorMsg ,"SyncLivraisonLignePull");
                        //Toast.makeText(context,"LivraisonLignePull:"+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON_LIGNE_PULL: Error: "+errorMsg ,"LIVRAISON_LIGNE_PULL",0));

                        }

                    }

                } catch (JSONException e) {
                    //logM.add("LivraisonLignePull : NOK Insert "+e.getMessage() ,"SyncLivraisonLignePull");
                    e.printStackTrace();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON_LIGNE_PULL: Error: "+e.getMessage() ,"LIVRAISON_LIGNE_PULL",0));

                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "LivraisonLignePull:"+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("LivraisonLignePull : NOK Inserted "+error.getMessage() ,"SyncLivraisonLignePull");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("LIVRAISON_LIGNE_PULL: Error: "+error.getMessage() ,"LIVRAISON_LIGNE_PULL",0));

                }


            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //Map<String, String> params = new HashMap<String, String>();
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                    String date=df1.format(new Date());


                    LivraisonLigneManager livraisonLigneManager = new LivraisonLigneManager(context);
                    ArrayList<LivraisonLigne> livraisonLignes = new ArrayList<>();
                    livraisonLignes=livraisonLigneManager.getList();

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(livraisonLignes));
                }
                return arrayFinale;
            }
        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    public double getNumberRounded(double nombre){

        double number = nombre;
        int decimalsToConsider = 2;
        BigDecimal bigDecimal = new BigDecimal(number);
        BigDecimal roundedWithScale = bigDecimal.setScale(decimalsToConsider, BigDecimal.ROUND_HALF_UP);
        //System.out.println("Rounded value with setting scale = "+roundedWithScale);


        return roundedWithScale.doubleValue();
    }

    public ArrayList<LivraisonLigne> fixLivraisonLigneCode(ArrayList<LivraisonLigne> livraisonLignes){

        ArrayList<LivraisonLigne> livraisonLigneArrayList = livraisonLignes;

        for(int i=0 ; i<livraisonLigneArrayList.size(); i++){

            livraisonLigneArrayList.get(i).setLIVRAISONLIGNE_CODE(i+1);
        }

        return livraisonLigneArrayList;
    }

}
