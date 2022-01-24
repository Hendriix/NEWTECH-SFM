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
import com.newtech.newtech_sfm.Metier.CommandeNonClotureeLigne;
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
 * Created by stagiaireit2 on 02/08/2016.
 */
public class CommandeNonClotureeLigneManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_COMMANDENONCLOTUREE_LIGNE = "commandenonclotureeligne";

    private static final String
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
            KEY_UNITE="UNITE_CODE",
            KEY_COMMANDELIGNE_CODE="COMMANDELIGNE_CODE",
            KEY_SOURCE="SOURCE",
            KEY_VERSION="VERSION";


   public static String CREATE_COMMANDENONCLOTUREE_LIGNE_TABLE = "CREATE TABLE " + TABLE_COMMANDENONCLOTUREE_LIGNE+ "("

            +KEY_COMMANDELIGNE_CODE  + " NUMERIC ,"
            +KEY_COMMANDE_CODE  + " TEXT ,"
            +KEY_FACTURE_CODE  + " TEXT,"
            +KEY_FAMILLE_CODE  + " TEXT,"
            +KEY_ARTICLE_CODE  + " TEXT,"
            +KEY_ARTICLE_DESIGNATION  + " TEXT,"
            +KEY_ARTICLE_NBUS_PAR_UP  + " NUMERIC,"
            +KEY_ARTICLE_PRIX  + " NUMERIC,"
            +KEY_QTE_COMMANDEE  + " NUMERIC,"
            +KEY_QTE_LIVREE  + " NUMERIC,"
            +KEY_CAISSE_COMMANDEE  + " NUMERIC,"
            +KEY_CAISSE_LIVREE  + " NUMERIC,"
            +KEY_LITTRAGE_COMMANDEE  + " NUMERIC,"
            +KEY_LITTRAGE_LIVREE  + " NUMERIC,"
            +KEY_TONNAGE_COMMANDEE  + " NUMERIC,"
            +KEY_TONNAGE_LIVREE  + " NUMERIC,"
            +KEY_KG_COMMANDEE  + " NUMERIC,"
            +KEY_KG_LIVREE  + " NUMERIC,"
            +KEY_MONTANT_BRUT  + " NUMERIC,"
            +KEY_REMISE  + " NUMERIC,"
            +KEY_MONTANT_NET  + " NUMERIC,"
            +KEY_COMMENTAIRE  + " TEXT,"
            +KEY_CREATEUR_CODE  + " TEXT,"
            +KEY_DATE_CREATION  + " TEXT ,"
            +KEY_UNITE  + " TEXT ,"
           +KEY_SOURCE + " TEXT ,"
            +KEY_VERSION  + " TEXT  "+")";

    public CommandeNonClotureeLigneManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_COMMANDE_LIGNE);
            db.execSQL(CREATE_COMMANDENONCLOTUREE_LIGNE_TABLE);
        } catch (SQLException e) {
        }
        Log.d(TAG, "table Commandeligne a livrer created"+CREATE_COMMANDENONCLOTUREE_LIGNE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMANDENONCLOTUREE_LIGNE);
        onCreate(db);
    }

    public int delete(String commandeLigneCode ,String commandeCode ) {
        Log.d(TAG, "Deleted commandesLignes from sqlite : "+commandeLigneCode+" ; "+commandeCode);
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_COMMANDENONCLOTUREE_LIGNE,KEY_COMMANDELIGNE_CODE+"=?  AND "+KEY_COMMANDE_CODE+"=?" ,new String[]{commandeLigneCode,commandeCode});

    }

    public void deleteCmdSynchronisee() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefCmdAS = sdf.format(new Date());
        String Where = " "+KEY_COMMENTAIRE+"='commande verifiee' and date("+KEY_DATE_CREATION+")!='"+DatefCmdAS+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_COMMANDENONCLOTUREE_LIGNE, Where, null);
        db.close();
        Log.d(TAG, "Deleted all commandesLignes verifiee from sqlite");
    }

    public void deleteCommandesLignes() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_COMMANDENONCLOTUREE_LIGNE, null, null);
        db.close();
        Log.d(TAG, "Deleted all commandesLignes info from sqlite");
    }
    public void add(CommandeNonClotureeLigne commandeNonClotureeLigne) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(KEY_COMMANDELIGNE_CODE, commandeNonClotureeLigne.getCOMMANDELIGNE_CODE());
        values.put(KEY_COMMANDE_CODE, commandeNonClotureeLigne.getCOMMANDE_CODE());
        values.put(KEY_FACTURE_CODE, commandeNonClotureeLigne.getFACTURE_CODE());
        values.put(KEY_FAMILLE_CODE, commandeNonClotureeLigne.getFAMILLE_CODE());
        values.put(KEY_ARTICLE_CODE, commandeNonClotureeLigne.getARTICLE_CODE());
        values.put(KEY_ARTICLE_DESIGNATION, commandeNonClotureeLigne.getARTICLE_DESIGNATION());
        values.put(KEY_ARTICLE_NBUS_PAR_UP, commandeNonClotureeLigne.getARTICLE_NBUS_PAR_UP());
        values.put(KEY_ARTICLE_PRIX, commandeNonClotureeLigne.getARTICLE_PRIX());
        values.put(KEY_QTE_COMMANDEE, commandeNonClotureeLigne.getQTE_COMMANDEE());
        values.put(KEY_QTE_LIVREE, commandeNonClotureeLigne.getQTE_LIVREE());
        values.put(KEY_CAISSE_COMMANDEE, commandeNonClotureeLigne.getCAISSE_COMMANDEE());
        values.put(KEY_CAISSE_LIVREE, commandeNonClotureeLigne.getCAISSE_LIVREE());
        values.put(KEY_LITTRAGE_COMMANDEE, commandeNonClotureeLigne.getLITTRAGE_COMMANDEE());
        values.put(KEY_LITTRAGE_LIVREE, commandeNonClotureeLigne.getLITTRAGE_LIVREE());
        values.put(KEY_TONNAGE_COMMANDEE, commandeNonClotureeLigne.getTONNAGE_COMMANDEE());
        values.put(KEY_TONNAGE_LIVREE, commandeNonClotureeLigne.getTONNAGE_LIVREE());
        values.put(KEY_KG_COMMANDEE, commandeNonClotureeLigne.getKG_COMMANDEE());
        values.put(KEY_KG_LIVREE, commandeNonClotureeLigne.getKG_LIVREE());
        values.put(KEY_MONTANT_BRUT, getNumberRounded(commandeNonClotureeLigne.getMONTANT_BRUT()));
        values.put(KEY_REMISE, getNumberRounded(commandeNonClotureeLigne.getREMISE()));
        values.put(KEY_MONTANT_NET, getNumberRounded(commandeNonClotureeLigne.getMONTANT_NET()));
        values.put(KEY_COMMENTAIRE, commandeNonClotureeLigne.getCOMMENTAIRE());
        values.put(KEY_CREATEUR_CODE, commandeNonClotureeLigne.getCREATEUR_CODE());
        values.put(KEY_DATE_CREATION, commandeNonClotureeLigne.getDATE_CREATION());
        values.put(KEY_UNITE, commandeNonClotureeLigne.getUNITE_CODE());
        values.put(KEY_SOURCE, commandeNonClotureeLigne.getSOURCE());
        values.put(KEY_VERSION, commandeNonClotureeLigne.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_COMMANDENONCLOTUREE_LIGNE, null, values,SQLiteDatabase.CONFLICT_IGNORE);
        db.close(); // Closing database connection
        Log.d(TAG, "nouvelle commande non cloturee ligne inseré dans la table Commandelignealivrer: " + id);
        Log.d(TAG, "add: commande ligne"+values);
    }

    public void updateCommandeLigne(String commandeligneCode,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_COMMANDENONCLOTUREE_LIGNE + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_COMMANDE_CODE +"= '"+commandeligneCode+"'" ;
        db.execSQL(req);
    }


    public ArrayList<CommandeNonClotureeLigne> getList() {
        ArrayList<CommandeNonClotureeLigne> commandeNonClotureeLignes = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_COMMANDENONCLOTUREE_LIGNE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "nb sal CommandeNonClotureeLigne: "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeNonClotureeLigne commandeNonClotureeLigne = new CommandeNonClotureeLigne();

                commandeNonClotureeLigne.setCOMMANDELIGNE_CODE(cursor.getInt(0));
                commandeNonClotureeLigne.setCOMMANDE_CODE(cursor.getString(1));
                commandeNonClotureeLigne.setFACTURE_CODE(cursor.getString(2));
                commandeNonClotureeLigne.setFAMILLE_CODE(cursor.getString(3));
                commandeNonClotureeLigne.setARTICLE_CODE(cursor.getString(4));
                commandeNonClotureeLigne.setARTICLE_DESIGNATION(cursor.getString(5));
                commandeNonClotureeLigne.setARTICLE_NBUS_PAR_UP((cursor.getDouble(6)));
                commandeNonClotureeLigne.setARTICLE_PRIX(cursor.getDouble(7));
                commandeNonClotureeLigne.setQTE_COMMANDEE(cursor.getDouble(8));
                commandeNonClotureeLigne.setQTE_LIVREE(cursor.getDouble(9));
                commandeNonClotureeLigne.setCAISSE_COMMANDEE(cursor.getDouble(10));
                commandeNonClotureeLigne.setCAISSE_LIVREE(cursor.getDouble(11));
                commandeNonClotureeLigne.setLITTRAGE_COMMANDEE(cursor.getDouble(12));
                commandeNonClotureeLigne.setLITTRAGE_LIVREE(cursor.getDouble(13));
                commandeNonClotureeLigne.setTONNAGE_COMMANDEE(cursor.getDouble(14));
                commandeNonClotureeLigne.setTONNAGE_LIVREE(cursor.getDouble(15));
                commandeNonClotureeLigne.setKG_COMMANDEE(cursor.getDouble(16));
                commandeNonClotureeLigne.setKG_LIVREE(cursor.getDouble(17));
                commandeNonClotureeLigne.setMONTANT_BRUT(cursor.getDouble(18));
                commandeNonClotureeLigne.setREMISE(cursor.getDouble(19));
                commandeNonClotureeLigne.setMONTANT_NET(cursor.getDouble(20));
                commandeNonClotureeLigne.setCOMMENTAIRE(cursor.getString(21));
                commandeNonClotureeLigne.setCREATEUR_CODE(cursor.getString(22));
                commandeNonClotureeLigne.setDATE_CREATION(cursor.getString(23));
                commandeNonClotureeLigne.setUNITE_CODE(cursor.getString(24));
                commandeNonClotureeLigne.setSOURCE(cursor.getString(25));
                commandeNonClotureeLigne.setVERSION(cursor.getString(26));

                commandeNonClotureeLignes.add(commandeNonClotureeLigne);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande non cloturee ligne from table commandenonclotureeligne: ");
        return commandeNonClotureeLignes;
    }

    public ArrayList<CommandeNonClotureeLigne> getListByArticleCode(String article_code) {
        ArrayList<CommandeNonClotureeLigne> commandeNonClotureeLignes = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_COMMANDENONCLOTUREE_LIGNE+" WHERE "+KEY_ARTICLE_CODE +"= '"+article_code+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "nb sal Commandenonclotureeligne: "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeNonClotureeLigne commandeNonClotureeLigne = new CommandeNonClotureeLigne();

                commandeNonClotureeLigne.setCOMMANDELIGNE_CODE(cursor.getInt(0));
                commandeNonClotureeLigne.setCOMMANDE_CODE(cursor.getString(1));
                commandeNonClotureeLigne.setFACTURE_CODE(cursor.getString(2));
                commandeNonClotureeLigne.setFAMILLE_CODE(cursor.getString(3));
                commandeNonClotureeLigne.setARTICLE_CODE(cursor.getString(4));
                commandeNonClotureeLigne.setARTICLE_DESIGNATION(cursor.getString(5));
                commandeNonClotureeLigne.setARTICLE_NBUS_PAR_UP((cursor.getDouble(6)));
                commandeNonClotureeLigne.setARTICLE_PRIX(cursor.getDouble(7));
                commandeNonClotureeLigne.setQTE_COMMANDEE(cursor.getDouble(8));
                commandeNonClotureeLigne.setQTE_LIVREE(cursor.getDouble(9));
                commandeNonClotureeLigne.setCAISSE_COMMANDEE(cursor.getDouble(10));
                commandeNonClotureeLigne.setCAISSE_LIVREE(cursor.getDouble(11));
                commandeNonClotureeLigne.setLITTRAGE_COMMANDEE(cursor.getDouble(12));
                commandeNonClotureeLigne.setLITTRAGE_LIVREE(cursor.getDouble(13));
                commandeNonClotureeLigne.setTONNAGE_COMMANDEE(cursor.getDouble(14));
                commandeNonClotureeLigne.setTONNAGE_LIVREE(cursor.getDouble(15));
                commandeNonClotureeLigne.setKG_COMMANDEE(cursor.getDouble(16));
                commandeNonClotureeLigne.setKG_LIVREE(cursor.getDouble(17));
                commandeNonClotureeLigne.setMONTANT_BRUT(cursor.getDouble(18));
                commandeNonClotureeLigne.setREMISE(cursor.getDouble(19));
                commandeNonClotureeLigne.setMONTANT_NET(cursor.getDouble(20));
                commandeNonClotureeLigne.setCOMMENTAIRE(cursor.getString(21));
                commandeNonClotureeLigne.setCREATEUR_CODE(cursor.getString(22));
                commandeNonClotureeLigne.setDATE_CREATION(cursor.getString(23));
                commandeNonClotureeLigne.setUNITE_CODE(cursor.getString(24));
                commandeNonClotureeLigne.setSOURCE(cursor.getString(25));
                commandeNonClotureeLigne.setVERSION(cursor.getString(26));
                commandeNonClotureeLignes.add(commandeNonClotureeLigne);

            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande non cloturee ligne from table commandneonclotureeligne: ");
        return commandeNonClotureeLignes;
    }

    public ArrayList<CommandeNonClotureeLigne> getListByCommandeCode(String commande_code) {
        ArrayList<CommandeNonClotureeLigne> commandeNonClotureeLignes = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_COMMANDENONCLOTUREE_LIGNE +" WHERE "+KEY_COMMANDE_CODE +"= '"+commande_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "nb sal CommandeLigneALivree: "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeNonClotureeLigne commandeNonClotureeLigne = new CommandeNonClotureeLigne();

                commandeNonClotureeLigne.setCOMMANDELIGNE_CODE(cursor.getInt(0));
                commandeNonClotureeLigne.setCOMMANDE_CODE(cursor.getString(1));
                commandeNonClotureeLigne.setFACTURE_CODE(cursor.getString(2));
                commandeNonClotureeLigne.setFAMILLE_CODE(cursor.getString(3));
                commandeNonClotureeLigne.setARTICLE_CODE(cursor.getString(4));
                commandeNonClotureeLigne.setARTICLE_DESIGNATION(cursor.getString(5));
                commandeNonClotureeLigne.setARTICLE_NBUS_PAR_UP((cursor.getDouble(6)));
                commandeNonClotureeLigne.setARTICLE_PRIX(cursor.getDouble(7));
                commandeNonClotureeLigne.setQTE_COMMANDEE(cursor.getDouble(8));
                commandeNonClotureeLigne.setQTE_LIVREE(cursor.getDouble(9));
                commandeNonClotureeLigne.setCAISSE_COMMANDEE(cursor.getDouble(10));
                commandeNonClotureeLigne.setCAISSE_LIVREE(cursor.getDouble(11));
                commandeNonClotureeLigne.setLITTRAGE_COMMANDEE(cursor.getDouble(12));
                commandeNonClotureeLigne.setLITTRAGE_LIVREE(cursor.getDouble(13));
                commandeNonClotureeLigne.setTONNAGE_COMMANDEE(cursor.getDouble(14));
                commandeNonClotureeLigne.setTONNAGE_LIVREE(cursor.getDouble(15));
                commandeNonClotureeLigne.setKG_COMMANDEE(cursor.getDouble(16));
                commandeNonClotureeLigne.setKG_LIVREE(cursor.getDouble(17));
                commandeNonClotureeLigne.setMONTANT_BRUT(cursor.getDouble(18));
                commandeNonClotureeLigne.setREMISE(cursor.getDouble(19));
                commandeNonClotureeLigne.setMONTANT_NET(cursor.getDouble(20));
                commandeNonClotureeLigne.setCOMMENTAIRE(cursor.getString(21));
                commandeNonClotureeLigne.setCREATEUR_CODE(cursor.getString(22));
                commandeNonClotureeLigne.setDATE_CREATION(cursor.getString(23));
                commandeNonClotureeLigne.setUNITE_CODE(cursor.getString(24));
                commandeNonClotureeLigne.setSOURCE(cursor.getString(25));
                commandeNonClotureeLigne.setVERSION(cursor.getString(26));

                commandeNonClotureeLignes.add(commandeNonClotureeLigne);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande non cloturee ligne from table commandneonclotureeligne: "+commandeNonClotureeLignes);
        return commandeNonClotureeLignes;
    }

    public ArrayList<CommandeNonClotureeLigne> getListByCommandeCodeQl(String commande_code) {
        ArrayList<CommandeNonClotureeLigne> commandeNonClotureeLignes = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_COMMANDENONCLOTUREE_LIGNE +" WHERE "+KEY_COMMANDE_CODE +"= '"+commande_code+"' AND "+KEY_QTE_LIVREE+" < "+KEY_QTE_COMMANDEE + " AND "+KEY_QTE_LIVREE+">= 0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "nb sal CommandeNonClotureeLigne: "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeNonClotureeLigne commandeNonClotureeLigne = new CommandeNonClotureeLigne();

                commandeNonClotureeLigne.setCOMMANDELIGNE_CODE(cursor.getInt(0));
                commandeNonClotureeLigne.setCOMMANDE_CODE(cursor.getString(1));
                commandeNonClotureeLigne.setFACTURE_CODE(cursor.getString(2));
                commandeNonClotureeLigne.setFAMILLE_CODE(cursor.getString(3));
                commandeNonClotureeLigne.setARTICLE_CODE(cursor.getString(4));
                commandeNonClotureeLigne.setARTICLE_DESIGNATION(cursor.getString(5));
                commandeNonClotureeLigne.setARTICLE_NBUS_PAR_UP((cursor.getDouble(6)));
                commandeNonClotureeLigne.setARTICLE_PRIX(cursor.getDouble(7));
                commandeNonClotureeLigne.setQTE_COMMANDEE(cursor.getDouble(8));
                commandeNonClotureeLigne.setQTE_LIVREE(cursor.getDouble(9));
                commandeNonClotureeLigne.setCAISSE_COMMANDEE(cursor.getDouble(10));
                commandeNonClotureeLigne.setCAISSE_LIVREE(cursor.getDouble(11));
                commandeNonClotureeLigne.setLITTRAGE_COMMANDEE(cursor.getDouble(12));
                commandeNonClotureeLigne.setLITTRAGE_LIVREE(cursor.getDouble(13));
                commandeNonClotureeLigne.setTONNAGE_COMMANDEE(cursor.getDouble(14));
                commandeNonClotureeLigne.setTONNAGE_LIVREE(cursor.getDouble(15));
                commandeNonClotureeLigne.setKG_COMMANDEE(cursor.getDouble(16));
                commandeNonClotureeLigne.setKG_LIVREE(cursor.getDouble(17));
                commandeNonClotureeLigne.setMONTANT_BRUT(cursor.getDouble(18));
                commandeNonClotureeLigne.setREMISE(cursor.getDouble(19));
                commandeNonClotureeLigne.setMONTANT_NET(cursor.getDouble(20));
                commandeNonClotureeLigne.setCOMMENTAIRE(cursor.getString(21));
                commandeNonClotureeLigne.setCREATEUR_CODE(cursor.getString(22));
                commandeNonClotureeLigne.setDATE_CREATION(cursor.getString(23));
                commandeNonClotureeLigne.setUNITE_CODE(cursor.getString(24));
                commandeNonClotureeLigne.setSOURCE(cursor.getString(25));
                commandeNonClotureeLigne.setVERSION(cursor.getString(26));

                commandeNonClotureeLignes.add(commandeNonClotureeLigne);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande non cloturee ligne from table commandneonclotureeligne: "+commandeNonClotureeLignes);
        return commandeNonClotureeLignes;
    }

    public ArrayList<CommandeNonClotureeLigne> getListALByCommandeCodeQl(String commande_code) {
        ArrayList<CommandeNonClotureeLigne> commandeNonClotureeLignes = new ArrayList<>();

        String selectQuery = "SELECT * FROM commandenonclotureeal WHERE commandenonclotureeal.COMMANDE_CODE"+"= '"+commande_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "nb sal CommandeNonClotureeLigne: "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeNonClotureeLigne commandeNonClotureeLigne = new CommandeNonClotureeLigne();

                commandeNonClotureeLigne.setCOMMANDELIGNE_CODE(cursor.getInt(0));
                commandeNonClotureeLigne.setCOMMANDE_CODE(cursor.getString(1));
                commandeNonClotureeLigne.setFACTURE_CODE(cursor.getString(2));
                commandeNonClotureeLigne.setFAMILLE_CODE(cursor.getString(3));
                commandeNonClotureeLigne.setARTICLE_CODE(cursor.getString(4));
                commandeNonClotureeLigne.setARTICLE_DESIGNATION(cursor.getString(5));
                commandeNonClotureeLigne.setARTICLE_NBUS_PAR_UP((cursor.getDouble(6)));
                commandeNonClotureeLigne.setARTICLE_PRIX(cursor.getDouble(7));
                commandeNonClotureeLigne.setQTE_COMMANDEE(cursor.getDouble(8));
                commandeNonClotureeLigne.setQTE_LIVREE(cursor.getDouble(9));
                commandeNonClotureeLigne.setCAISSE_COMMANDEE(cursor.getDouble(10));
                commandeNonClotureeLigne.setCAISSE_LIVREE(cursor.getDouble(11));
                commandeNonClotureeLigne.setLITTRAGE_COMMANDEE(cursor.getDouble(12));
                commandeNonClotureeLigne.setLITTRAGE_LIVREE(cursor.getDouble(13));
                commandeNonClotureeLigne.setTONNAGE_COMMANDEE(cursor.getDouble(14));
                commandeNonClotureeLigne.setTONNAGE_LIVREE(cursor.getDouble(15));
                commandeNonClotureeLigne.setKG_COMMANDEE(cursor.getDouble(16));
                commandeNonClotureeLigne.setKG_LIVREE(cursor.getDouble(17));
                commandeNonClotureeLigne.setMONTANT_BRUT(cursor.getDouble(18));
                commandeNonClotureeLigne.setREMISE(cursor.getDouble(19));
                commandeNonClotureeLigne.setMONTANT_NET(cursor.getDouble(20));
                commandeNonClotureeLigne.setCOMMENTAIRE(cursor.getString(21));
                commandeNonClotureeLigne.setCREATEUR_CODE(cursor.getString(22));
                commandeNonClotureeLigne.setDATE_CREATION(cursor.getString(23));
                commandeNonClotureeLigne.setUNITE_CODE(cursor.getString(24));
                commandeNonClotureeLigne.setSOURCE(cursor.getString(25));
                commandeNonClotureeLigne.setVERSION(cursor.getString(26));

                commandeNonClotureeLignes.add(commandeNonClotureeLigne);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande non cloturee ligne from table commandneonclotureeligne: "+commandeNonClotureeLignes);
        return commandeNonClotureeLignes;
    }


    ///////////////////////////////////////////////SYNCHRONISATION COMMENDELIGNE A LIVRER/////////////////////////////////////////////

    public static void synchronisationCommandeNonClotureeLigne(final Context context){

        String tag_string_req = "COMMANDE_NC_LIGNE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_COMMANDENONCLOTUREE_LIGNE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ouvrir le logManager
                Log.d("CommandeNonClotureeL ", ""+response);
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray CommandeNonClotureeLigne = jObj.getJSONArray("CommandeNonClotureeLigne");
                        //Toast.makeText(context, "Nombre de CommandeNonClotureeLigne " +CommandeNonClotureeLigne.length(), Toast.LENGTH_SHORT).show();
                        int cptInsert = 0,cptDeleted = 0;
                        //Ajout/Suppression/modification des articles dans la base de données.
                        for (int i = 0; i < CommandeNonClotureeLigne.length(); i++) {
                            JSONObject uneCommandeNonClotureeLigne = CommandeNonClotureeLigne.getJSONObject(i);
                            CommandeNonClotureeLigneManager commandeNonClotureeLigneManager = new CommandeNonClotureeLigneManager(context);


                            if(uneCommandeNonClotureeLigne.getString("OPERATION").equals("DELETE")){
                                commandeNonClotureeLigneManager.delete(uneCommandeNonClotureeLigne.getString("COMMANDELIGNE_CODE"),uneCommandeNonClotureeLigne.getString("COMMANDE_CODE"));
                                cptDeleted++;
                            }else {
                                Log.d("onrcmdligne", "onResponse: comdligne"+uneCommandeNonClotureeLigne.toString());
                                commandeNonClotureeLigneManager.add(new CommandeNonClotureeLigne(uneCommandeNonClotureeLigne));
                                cptInsert++;
                            }

                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE_NC_LIGNE: Inserted: " + cptInsert + " Deleted: " + cptDeleted, "COMMANDE_NC_LIGNE", 1));

                        }

                        //logM.add("CommandeNonClotureeLigne:OK Insert:"+cptInsert +"Deleted:"+cptDeleted ,"SyncCommandeNonClotureeLigne");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE_NC_LIGNE: Error: " +errorMsg, "COMMANDE_NC_LIGNE", 0));

                        }
                        //logM.add("CommandeNonClotureeLigne :NOK Insert "+errorMsg ,"SyncCommandeNonClotureeLigne");
                        //Toast.makeText(context,"CommandeNonClotureeLigne:"+errorMsg, Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    //logM.add("CommandeNonClotureeLigne : NOK Insert "+e.getMessage() ,"SyncCommandeNonClotureeLigne");
                    e.printStackTrace();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE_NC_LIGNE: Error: " +e.getMessage(), "COMMANDE_NC_LIGNE", 0));

                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "CommandeNonClotureeLigne:"+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("CommandeNonClotureeLigne : NOK Inserted "+error.getMessage() ,"SyncCommandeNonClotureeLigne");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE_NC_LIGNE: Error: " +error.getMessage(), "COMMANDE_NC_LIGNE", 0));

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


                    CommandeNonClotureeLigneManager commandeNonClotureeLigneManager = new CommandeNonClotureeLigneManager(context);
                    ArrayList<CommandeNonClotureeLigne> commandeNonClotureeLignes = new ArrayList<>();
                    commandeNonClotureeLignes=commandeNonClotureeLigneManager.getList();

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(commandeNonClotureeLignes));
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


    public ArrayList<CommandeNonClotureeLigne> getListCmdNCL(String commande_code, Context context){

        int Nbrecmd = 0;//Nombre de commdes différentes
        CommandeLigneManager commandeLigneManager = new CommandeLigneManager(context);
        CommandeNonClotureeLigneManager commandeNonClotureeLigneManager = new CommandeNonClotureeLigneManager(context);

        ArrayList<CommandeNonClotureeLigne> commandeNonClotureeLignes;
        ArrayList<CommandeLigne> commandeLignes;
        ArrayList<CommandeNonClotureeLigne> commandesFinales = new ArrayList<>();

        commandeLignes = commandeLigneManager.getListByCommandeCode(commande_code);
        commandeNonClotureeLignes = commandeNonClotureeLigneManager.getListByCommandeCode(commande_code);

        Log.d(TAG, "getListCmd: "+commandeLignes.toString());
        Log.d(TAG, "getListCmd Size: "+commandeLignes.size());

        Log.d(TAG, "getListCmdNC: "+commandeNonClotureeLignes.toString());
        Log.d(TAG, "getListCmdNC Size: "+commandeNonClotureeLignes.size());

        if(commandeLignes.size()>0 && commandeNonClotureeLignes.size()>0){
            for(int i=0;i<=commandeLignes.size();i++){

                for(int j=0;j<=commandeNonClotureeLignes.size();j++){

                    if(commandeNonClotureeLignes.get(i).getCOMMANDE_CODE()==commandeLignes.get(j).getCOMMANDE_CODE() && commandeNonClotureeLignes.get(i).getCOMMANDELIGNE_CODE()==commandeLignes.get(j).getCOMMANDELIGNE_CODE()){
                        break;
                    }else{
                        Nbrecmd++;
                    }
                }

                if(Nbrecmd==commandeNonClotureeLignes.size()){
                    commandeNonClotureeLignes.add(new CommandeNonClotureeLigne(commandeLignes.get(i)));
                }
            }
        }else if(commandeLignes.size()>0 && commandeNonClotureeLignes.size()==0){

            for(int i=0;i<commandeLignes.size();i++){
                commandeNonClotureeLignes.add(new CommandeNonClotureeLigne(commandeLignes.get(i)));
            }

        }

        return commandeNonClotureeLignes;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public double getNumberRounded(double nombre){

        double number = nombre;
        int decimalsToConsider = 2;
        BigDecimal bigDecimal = new BigDecimal(number);
        BigDecimal roundedWithScale = bigDecimal.setScale(decimalsToConsider, BigDecimal.ROUND_HALF_UP);
        //System.out.println("Rounded value with setting scale = "+roundedWithScale);


        return roundedWithScale.doubleValue();
    }


}