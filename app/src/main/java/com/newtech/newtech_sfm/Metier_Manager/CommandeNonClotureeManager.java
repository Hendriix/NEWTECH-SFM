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
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.CommandeNonCloturee;
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
 * Created by TONPC on 04/10/2017.
 */

public class CommandeNonClotureeManager extends SQLiteOpenHelper{

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_COMMANDENONCLOTUREE = "commandenoncloturee";
    public static final String VIEW_COMMANDENONCLOTUREEAL = "commandenonclotureeal";
    public static final String VIEW_COMMANDENONCLOTUREEAE = "commandenonclotureeae";


    private static final String
            KEY_COMMANDE_CODE="COMMANDE_CODE",
            KEY_FACTURE_CODE="FACTURE_CODE",
            KEY_FACTURECLIENT_CODE="FACTURECLIENT_CODE",
            KEY_DATE_COMMANDE="DATE_COMMANDE",
            KEY_DATE_LIVRAISON="DATE_LIVRAISON",
            KEY_DATE_CREATION="DATE_CREATION",
            KEY_PERIODE_CODE="PERIODE_CODE",
            KEY_COMMANDETYPE_CODE="COMMANDETYPE_CODE",
            KEY_COMMANDESTATUT_CODE="COMMANDESTATUT_CODE",
            KEY_DISTRIBUTEUR_CODE="DISTRIBUTEUR_CODE",
            KEY_VENDEUR_CODE="VENDEUR_CODE",
            KEY_CLIENT_CODE="CLIENT_CODE",
            KEY_CREATEUR_CODE="CREATEUR_CODE",
            KEY_LIVREUR_CODE="LIVREUR_CODE",
            KEY_REGION_CODE="REGION_CODE",
            KEY_ZONE_CODE="ZONE_CODE",
            KEY_SECTEUR_CODE="SECTEUR_CODE",
            KEY_SOUSSECTEUR_CODE="SOUSSECTEUR_CODE",
            KEY_TOURNEE_CODE="TOURNEE_CODE",
            KEY_VISITE_CODE="VISITE_CODE",
            KEY_STOCKDEPART_CODE="STOCKDEPART_CODE",
            KEY_STOCKDESTINATION_CODE="STOCKDESTINATION_CODE",
            KEY_DESTINATION_CODE="DESTINATION_CODE",
            KEY_TS="TS",
            KEY_MONTANT_BRUT="MONTANT_BRUT",
            KEY_REMISE="REMISE",
            KEY_MONTANT_NET="MONTANT_NET",
            KEY_VALEUR_COMMANDE="VALEUR_COMMANDE",
            KEY_LITTRAGE_COMMANDE="LITTRAGE_COMMANDE",
            KEY_TONNAGE_COMMANDE="TONNAGE_COMMANDE",
            KEY_KG_COMMANDE="KG_COMMANDE",
            KEY_COMMENTAIRE="COMMENTAIRE",
            KEY_PAIEMENT_CODE="PAIEMENT_CODE",
            KEY_NB_LIGNE="NB_LIGNE",
            KEY_CIRCUIT_CODE="CIRCUIT_CODE",
            KEY_CHANNEL_CODE="CHANNEL_CODE",
            KEY_STATUT_CODE="STATUT_CODE",
            KEY_SOURCE="SOURCE",
            KEY_VERSION="VERSION",
            KEY_GPS_LATITUDE="GPS_LATITUDE",
            KEY_GPS_LONGITUDE="GPS_LONGITUDE",
            KEY_DISTANCE="DISTANCE";

    public static String CREATE_COMMANDENONCLOTUREE_TABLE = "CREATE TABLE " + TABLE_COMMANDENONCLOTUREE + "("
            +KEY_COMMANDE_CODE + " TEXT PRIMARY KEY ,"
            +KEY_FACTURE_CODE+ " TEXT,"
            +KEY_FACTURECLIENT_CODE  + " TEXT,"
            +KEY_DATE_COMMANDE + " TEXT,"
            +KEY_DATE_LIVRAISON  + " TEXT,"
            +KEY_DATE_CREATION + " TEXT,"
            +KEY_PERIODE_CODE+ " TEXT,"
            +KEY_COMMANDETYPE_CODE  + " TEXT,"
            +KEY_COMMANDESTATUT_CODE + " TEXT,"
            +KEY_DISTRIBUTEUR_CODE  + " TEXT,"
            +KEY_VENDEUR_CODE+ " TEXT,"
            +KEY_CLIENT_CODE+ " TEXT,"
            +KEY_CREATEUR_CODE + " TEXT,"
            +KEY_LIVREUR_CODE+ " TEXT,"
            +KEY_REGION_CODE+ " TEXT,"
            +KEY_ZONE_CODE+ " TEXT,"
            +KEY_SECTEUR_CODE+ " TEXT,"
            +KEY_SOUSSECTEUR_CODE    + " TEXT,"
            +KEY_TOURNEE_CODE+ " TEXT,"
            +KEY_VISITE_CODE+ " TEXT,"
            +KEY_STOCKDEPART_CODE  + " TEXT,"
            +KEY_STOCKDESTINATION_CODE  + " TEXT,"
            +KEY_DESTINATION_CODE + " TEXT,"
            +KEY_TS + " TEXT,"
            +KEY_MONTANT_BRUT+ " NUMERIC ,"
            +KEY_REMISE + " NUMERIC ,"
            +KEY_MONTANT_NET+ " NUMERIC,"
            +KEY_VALEUR_COMMANDE   + " NUMERIC,"
            +KEY_LITTRAGE_COMMANDE  + " NUMERIC,"
            +KEY_TONNAGE_COMMANDE  + " NUMERIC,"
            +KEY_KG_COMMANDE+ " NUMERIC,"
            +KEY_COMMENTAIRE+ " TEXT,"
            +KEY_PAIEMENT_CODE + " NUMERIC,"
            +KEY_NB_LIGNE + " NUMERIC,"
            +KEY_CIRCUIT_CODE+ " TEXT,"
            +KEY_CHANNEL_CODE+ " TEXT,"
            +KEY_STATUT_CODE+ " TEXT,"
            +KEY_SOURCE+" TEXT,"
            +KEY_VERSION+ " TEXT ,"
            +KEY_GPS_LATITUDE + " TEXT,"
            +KEY_GPS_LONGITUDE + " TEXT,"
            +KEY_DISTANCE + " NUMERIC"+ ")";

    public static String CREATE_VIEW_COMMANDENONCLOTUREE_AL = "CREATE VIEW " + VIEW_COMMANDENONCLOTUREEAL + "" +
            " AS " +
            "SELECT commandenoncloturee.*" +
            " FROM commandenoncloturee" +
            " left join (select distinct livraisonligne.COMMANDE_CODE,SUM(livraisonligne.QTE_LIVREE) as 'SQL'" +
            " from livraisonligne" +
            " group by livraisonligne.COMMANDE_CODE" +
            " ) AS TSQL" +
            " on TSQL.COMMANDE_CODE=commandenoncloturee.COMMANDE_CODE " +
            " inner join (select distinct commandenonclotureeligne.COMMANDE_CODE,SUM(commandenonclotureeligne.QTE_COMMANDEE) as 'SQC'" +
            " from commandenonclotureeligne" +
            " group by commandenonclotureeligne.COMMANDE_CODE" +
            " ) AS TSQC" +
            " on TSQC.COMMANDE_CODE=commandenoncloturee.COMMANDE_CODE " +
            " WHERE TSQC.SQC > ifnull(TSQL.SQL,0)" +"";

    public static String CREATE_VIEW_COMMANDENONCLOTUREE_AE = "CREATE VIEW " + VIEW_COMMANDENONCLOTUREEAE + "" +
            " AS " +
            "SELECT commandenoncloturee.*" +
            " FROM commandenoncloturee" +
            " left join (select encaissement.COMMANDE_CODE,SUM(encaissement.MONTANT) as 'SE'" +
            " from encaissement" +
            " group by encaissement.COMMANDE_CODE" +
            " ) AS TSE" +
            " on TSE.COMMANDE_CODE = commandenoncloturee.COMMANDE_CODE" +
            " WHERE commandenoncloturee.VALEUR_COMMANDE > ifnull(TSE.SE,0)" +"";
            ;

    public CommandeNonClotureeManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE);
            db.execSQL(CREATE_VIEW_COMMANDENONCLOTUREE_AL);
            db.execSQL(CREATE_VIEW_COMMANDENONCLOTUREE_AE);
            db.execSQL(CREATE_COMMANDENONCLOTUREE_TABLE);

        } catch (SQLException e) {

        }

        Log.d(TAG, "table CommandeNonCloturee created");
        Log.d(TAG, "onCreate commande non cloturee AL View: "+CREATE_VIEW_COMMANDENONCLOTUREE_AL);
        Log.d(TAG, "onCreate commande non cloturee AE View: "+CREATE_VIEW_COMMANDENONCLOTUREE_AE);
    }

    public ArrayList<CommandeNonCloturee> getListView(){
        ArrayList<CommandeNonCloturee> commandeNonCloturees = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + VIEW_COMMANDENONCLOTUREEAL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeNonCloturee commandeNonCloturee = new CommandeNonCloturee();

                commandeNonCloturee.setCOMMANDE_CODE(cursor.getString(0));
                commandeNonCloturee.setFACTURE_CODE(cursor.getString(1));
                commandeNonCloturee.setFACTURECLIENT_CODE(cursor.getString(2));
                commandeNonCloturee.setDATE_COMMANDE(cursor.getString(3));
                commandeNonCloturee.setDATE_LIVRAISON((cursor.getString(4)));
                commandeNonCloturee.setDATE_CREATION(cursor.getString(5));
                commandeNonCloturee.setPERIODE_CODE(cursor.getString(6));
                commandeNonCloturee.setCOMMANDETYPE_CODE(cursor.getString(7));
                commandeNonCloturee.setCOMMANDESTATUT_CODE(cursor.getString(8));
                commandeNonCloturee.setDISTRIBUTEUR_CODE(cursor.getString(9));
                commandeNonCloturee.setVENDEUR_CODE(cursor.getString(10));
                commandeNonCloturee.setCLIENT_CODE(cursor.getString(11));
                commandeNonCloturee.setCREATEUR_CODE(cursor.getString(12));
                commandeNonCloturee.setLIVREUR_CODE(cursor.getString(13));
                commandeNonCloturee.setREGION_CODE(cursor.getString(14));
                commandeNonCloturee.setZONE_CODE(cursor.getString(15));
                commandeNonCloturee.setSECTEUR_CODE(cursor.getString(16));
                commandeNonCloturee.setSOUSSECTEUR_CODE(cursor.getString(17));
                commandeNonCloturee.setTOURNEE_CODE(cursor.getString(18));
                commandeNonCloturee.setVISITE_CODE(cursor.getString(19));
                commandeNonCloturee.setSTOCKDEPART_CODE(cursor.getString(20));
                commandeNonCloturee.setSTOCKDESTINATION_CODE(cursor.getString(21));
                commandeNonCloturee.setDESTINATION_CODE(cursor.getString(22));
                commandeNonCloturee.setTS(cursor.getString(23));
                commandeNonCloturee.setMONTANT_BRUT(cursor.getDouble(24));
                commandeNonCloturee.setREMISE(cursor.getDouble(25));
                commandeNonCloturee.setMONTANT_NET(cursor.getDouble(26));
                commandeNonCloturee.setVALEUR_COMMANDE(cursor.getDouble(27));
                commandeNonCloturee.setLITTRAGE_COMMANDE(cursor.getDouble(28));
                commandeNonCloturee.setTONNAGE_COMMANDE(cursor.getDouble(29));
                commandeNonCloturee.setKG_COMMANDE(cursor.getDouble(30));
                commandeNonCloturee.setCOMMENTAIRE(cursor.getString(31));
                commandeNonCloturee.setPAIEMENT_CODE(cursor.getInt(32));
                commandeNonCloturee.setNB_LIGNE(cursor.getInt(33));
                commandeNonCloturee.setCIRCUIT_CODE(cursor.getString(34));
                commandeNonCloturee.setCHANNEL_CODE(cursor.getString(35));
                commandeNonCloturee.setSTATUT_CODE(cursor.getString(36));
                commandeNonCloturee.setSOURCE(cursor.getString(37));
                commandeNonCloturee.setVERSION(cursor.getString(38));
                commandeNonCloturee.setGPS_LATITUDE(cursor.getString(39));
                commandeNonCloturee.setGPS_LONGITUDE(cursor.getString(40));
                commandeNonCloturee.setDISTANCE(cursor.getInt(41));

                commandeNonCloturees.add(commandeNonCloturee);
            }while(cursor.moveToNext());
        }

        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching commande non cloturee from view commandenonclotureeal: ");

        return commandeNonCloturees;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMANDENONCLOTUREE);
        db.execSQL("DROP VIEW " + VIEW_COMMANDENONCLOTUREEAL);
        db.execSQL("DROP VIEW " + VIEW_COMMANDENONCLOTUREEAE);
        onCreate(db);
    }

    public void add(CommandeNonCloturee commandeNonCloturee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COMMANDE_CODE, commandeNonCloturee.getCOMMANDE_CODE());
        values.put(KEY_FACTURE_CODE, commandeNonCloturee.getFACTURE_CODE());
        values.put(KEY_FACTURECLIENT_CODE, commandeNonCloturee.getFACTURECLIENT_CODE());
        values.put(KEY_DATE_COMMANDE, commandeNonCloturee.getDATE_COMMANDE());
        values.put(KEY_DATE_LIVRAISON, commandeNonCloturee.getDATE_LIVRAISON());
        values.put(KEY_DATE_CREATION, commandeNonCloturee.getDATE_CREATION());
        values.put(KEY_PERIODE_CODE, commandeNonCloturee.getPERIODE_CODE());
        values.put(KEY_COMMANDETYPE_CODE, commandeNonCloturee.getCOMMANDETYPE_CODE());
        values.put(KEY_COMMANDESTATUT_CODE, commandeNonCloturee.getCOMMANDESTATUT_CODE());
        values.put(KEY_DISTRIBUTEUR_CODE, commandeNonCloturee.getDISTRIBUTEUR_CODE());
        values.put(KEY_VENDEUR_CODE, commandeNonCloturee.getVENDEUR_CODE());
        values.put(KEY_CLIENT_CODE, commandeNonCloturee.getCLIENT_CODE());
        values.put(KEY_CREATEUR_CODE, commandeNonCloturee.getCREATEUR_CODE());
        values.put(KEY_LIVREUR_CODE, commandeNonCloturee.getLIVREUR_CODE());
        values.put(KEY_REGION_CODE, commandeNonCloturee.getREGION_CODE());
        values.put(KEY_ZONE_CODE, commandeNonCloturee.getZONE_CODE());
        values.put(KEY_SECTEUR_CODE, commandeNonCloturee.getSECTEUR_CODE());
        values.put(KEY_SOUSSECTEUR_CODE, commandeNonCloturee.getSOUSSECTEUR_CODE());
        values.put(KEY_TOURNEE_CODE, commandeNonCloturee.getTOURNEE_CODE());
        values.put(KEY_VISITE_CODE, commandeNonCloturee.getVISITE_CODE());
        values.put(KEY_STOCKDEPART_CODE, commandeNonCloturee.getSTOCKDEPART_CODE());
        values.put(KEY_STOCKDESTINATION_CODE, commandeNonCloturee.getSTOCKDESTINATION_CODE());
        values.put(KEY_DESTINATION_CODE, commandeNonCloturee.getDESTINATION_CODE());
        values.put(KEY_TS, commandeNonCloturee.getTS());
        values.put(KEY_MONTANT_BRUT, getNumberRounded(commandeNonCloturee.getMONTANT_BRUT()));
        values.put(KEY_REMISE, getNumberRounded(commandeNonCloturee.getREMISE()));
        values.put(KEY_MONTANT_NET, getNumberRounded(commandeNonCloturee.getMONTANT_NET()));
        values.put(KEY_VALEUR_COMMANDE, getNumberRounded(commandeNonCloturee.getVALEUR_COMMANDE()));
        values.put(KEY_LITTRAGE_COMMANDE, commandeNonCloturee.getLITTRAGE_COMMANDE());
        values.put(KEY_TONNAGE_COMMANDE, commandeNonCloturee.getTONNAGE_COMMANDE());
        values.put(KEY_KG_COMMANDE, commandeNonCloturee.getKG_COMMANDE());
        values.put(KEY_COMMENTAIRE, commandeNonCloturee.getCOMMENTAIRE());
        values.put(KEY_PAIEMENT_CODE, commandeNonCloturee.getPAIEMENT_CODE());
        values.put(KEY_NB_LIGNE, commandeNonCloturee.getNB_LIGNE());
        values.put(KEY_CIRCUIT_CODE, commandeNonCloturee.getCIRCUIT_CODE());
        values.put(KEY_CHANNEL_CODE, commandeNonCloturee.getCHANNEL_CODE());
        values.put(KEY_STATUT_CODE, commandeNonCloturee.getSTATUT_CODE());
        values.put(KEY_SOURCE, commandeNonCloturee.getSOURCE());
        values.put(KEY_VERSION, commandeNonCloturee.getVERSION());
        values.put(KEY_GPS_LATITUDE, commandeNonCloturee.getGPS_LATITUDE());
        values.put(KEY_GPS_LONGITUDE, commandeNonCloturee.getGPS_LONGITUDE());
        values.put(KEY_DISTANCE, commandeNonCloturee.getDISTANCE());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_COMMANDENONCLOTUREE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d(TAG, "nouvelle commande Non Cloturee inserée dans la table Commande: " + id);
    }

    public int delete(String commandeCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_COMMANDENONCLOTUREE,KEY_COMMANDE_CODE+"=?",new String[]{commandeCode});
    }

    public int deleteByCcVersion(String commande_code,String version_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_COMMANDENONCLOTUREE,KEY_COMMANDE_CODE+"=?"+" AND "+KEY_VERSION+"=?",new String[]{commande_code,version_code});
    }


    public void updateCommande(String commandeCode,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_COMMANDENONCLOTUREE + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_COMMANDE_CODE +"= '"+commandeCode+"'" ;
        db.execSQL(req);
    }

    public void deleteCmdSynchronisee() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefCmdAS = sdf.format(new Date());
        String Where = " "+KEY_COMMENTAIRE+"='commande verifiee' and date("+KEY_DATE_CREATION+")<>date('"+DatefCmdAS+"') ";

        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_COMMANDENONCLOTUREE, Where, null);
        db.close();
        Log.d(TAG, "Deleted all commandes verifiee from sqlite");
        Log.d(TAG, "deleteCmdSynchronisee: "+Where);
    }

    public ArrayList<CommandeNonCloturee> getList() {
        ArrayList<CommandeNonCloturee> commandeNonCloturees= new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDENONCLOTUREE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeNonCloturee commandeNonCloturee = new CommandeNonCloturee();
                commandeNonCloturee.setCOMMANDE_CODE(cursor.getString(0));
                commandeNonCloturee.setFACTURE_CODE(cursor.getString(1));
                commandeNonCloturee.setFACTURECLIENT_CODE(cursor.getString(2));
                commandeNonCloturee.setDATE_COMMANDE(cursor.getString(3));
                commandeNonCloturee.setDATE_LIVRAISON((cursor.getString(4)));
                commandeNonCloturee.setDATE_CREATION(cursor.getString(5));
                commandeNonCloturee.setPERIODE_CODE(cursor.getString(6));
                commandeNonCloturee.setCOMMANDETYPE_CODE(cursor.getString(7));
                commandeNonCloturee.setCOMMANDESTATUT_CODE(cursor.getString(8));
                commandeNonCloturee.setDISTRIBUTEUR_CODE(cursor.getString(9));
                commandeNonCloturee.setVENDEUR_CODE(cursor.getString(10));
                commandeNonCloturee.setCLIENT_CODE(cursor.getString(11));
                commandeNonCloturee.setCREATEUR_CODE(cursor.getString(12));
                commandeNonCloturee.setLIVREUR_CODE(cursor.getString(13));
                commandeNonCloturee.setREGION_CODE(cursor.getString(14));
                commandeNonCloturee.setZONE_CODE(cursor.getString(15));
                commandeNonCloturee.setSECTEUR_CODE(cursor.getString(16));
                commandeNonCloturee.setSOUSSECTEUR_CODE(cursor.getString(17));
                commandeNonCloturee.setTOURNEE_CODE(cursor.getString(18));
                commandeNonCloturee.setVISITE_CODE(cursor.getString(19));
                commandeNonCloturee.setSTOCKDEPART_CODE(cursor.getString(20));
                commandeNonCloturee.setSTOCKDESTINATION_CODE(cursor.getString(21));
                commandeNonCloturee.setDESTINATION_CODE(cursor.getString(22));
                commandeNonCloturee.setTS(cursor.getString(23));
                commandeNonCloturee.setMONTANT_BRUT(cursor.getDouble(24));
                commandeNonCloturee.setREMISE(cursor.getDouble(25));
                commandeNonCloturee.setMONTANT_NET(cursor.getDouble(26));
                commandeNonCloturee.setVALEUR_COMMANDE(cursor.getDouble(27));
                commandeNonCloturee.setLITTRAGE_COMMANDE(cursor.getDouble(28));
                commandeNonCloturee.setTONNAGE_COMMANDE(cursor.getDouble(29));
                commandeNonCloturee.setKG_COMMANDE(cursor.getDouble(30));
                commandeNonCloturee.setCOMMENTAIRE(cursor.getString(31));
                commandeNonCloturee.setPAIEMENT_CODE(cursor.getInt(32));
                commandeNonCloturee.setNB_LIGNE(cursor.getInt(33));
                commandeNonCloturee.setCIRCUIT_CODE(cursor.getString(34));
                commandeNonCloturee.setCHANNEL_CODE(cursor.getString(35));
                commandeNonCloturee.setSTATUT_CODE(cursor.getString(36));
                commandeNonCloturee.setSOURCE(cursor.getString(37));
                commandeNonCloturee.setVERSION(cursor.getString(38));
                commandeNonCloturee.setGPS_LATITUDE(cursor.getString(39));
                commandeNonCloturee.setGPS_LONGITUDE(cursor.getString(40));
                commandeNonCloturee.setDISTANCE(cursor.getInt(41));

                commandeNonCloturees.add(commandeNonCloturee);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching commande non cloturee from table commandenoncloturee: "+commandeNonCloturees.toString());
        return commandeNonCloturees;
    }

    public ArrayList<CommandeNonCloturee> getListByCmdSC(String commandestatut_code) {
        ArrayList<CommandeNonCloturee> commandeNonCloturees = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDENONCLOTUREE +" WHERE "+KEY_COMMANDESTATUT_CODE +"!= '"+commandestatut_code+"'" ;;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeNonCloturee commandeNonCloturee = new CommandeNonCloturee();
                commandeNonCloturee.setCOMMANDE_CODE(cursor.getString(0));
                commandeNonCloturee.setFACTURE_CODE(cursor.getString(1));
                commandeNonCloturee.setFACTURECLIENT_CODE(cursor.getString(2));
                commandeNonCloturee.setDATE_COMMANDE(cursor.getString(3));
                commandeNonCloturee.setDATE_LIVRAISON((cursor.getString(4)));
                commandeNonCloturee.setDATE_CREATION(cursor.getString(5));
                commandeNonCloturee.setPERIODE_CODE(cursor.getString(6));
                commandeNonCloturee.setCOMMANDETYPE_CODE(cursor.getString(7));
                commandeNonCloturee.setCOMMANDESTATUT_CODE(cursor.getString(8));
                commandeNonCloturee.setDISTRIBUTEUR_CODE(cursor.getString(9));
                commandeNonCloturee.setVENDEUR_CODE(cursor.getString(10));
                commandeNonCloturee.setCLIENT_CODE(cursor.getString(11));
                commandeNonCloturee.setCREATEUR_CODE(cursor.getString(12));
                commandeNonCloturee.setLIVREUR_CODE(cursor.getString(13));
                commandeNonCloturee.setREGION_CODE(cursor.getString(14));
                commandeNonCloturee.setZONE_CODE(cursor.getString(15));
                commandeNonCloturee.setSECTEUR_CODE(cursor.getString(16));
                commandeNonCloturee.setSOUSSECTEUR_CODE(cursor.getString(17));
                commandeNonCloturee.setTOURNEE_CODE(cursor.getString(18));
                commandeNonCloturee.setVISITE_CODE(cursor.getString(19));
                commandeNonCloturee.setSTOCKDEPART_CODE(cursor.getString(20));
                commandeNonCloturee.setSTOCKDESTINATION_CODE(cursor.getString(21));
                commandeNonCloturee.setDESTINATION_CODE(cursor.getString(22));
                commandeNonCloturee.setTS(cursor.getString(23));
                commandeNonCloturee.setMONTANT_BRUT(cursor.getDouble(24));
                commandeNonCloturee.setREMISE(cursor.getDouble(25));
                commandeNonCloturee.setMONTANT_NET(cursor.getDouble(26));
                commandeNonCloturee.setVALEUR_COMMANDE(cursor.getDouble(27));
                commandeNonCloturee.setLITTRAGE_COMMANDE(cursor.getDouble(28));
                commandeNonCloturee.setTONNAGE_COMMANDE(cursor.getDouble(29));
                commandeNonCloturee.setKG_COMMANDE(cursor.getDouble(30));
                commandeNonCloturee.setCOMMENTAIRE(cursor.getString(31));
                commandeNonCloturee.setPAIEMENT_CODE(cursor.getInt(32));
                commandeNonCloturee.setNB_LIGNE(cursor.getInt(33));
                commandeNonCloturee.setCIRCUIT_CODE(cursor.getString(34));
                commandeNonCloturee.setCHANNEL_CODE(cursor.getString(35));
                commandeNonCloturee.setSTATUT_CODE(cursor.getString(36));
                commandeNonCloturee.setSOURCE(cursor.getString(37));
                commandeNonCloturee.setVERSION(cursor.getString(38));
                commandeNonCloturee.setGPS_LATITUDE(cursor.getString(39));
                commandeNonCloturee.setGPS_LONGITUDE(cursor.getString(40));
                commandeNonCloturee.setDISTANCE(cursor.getInt(41));

                commandeNonCloturees.add(commandeNonCloturee);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching commande non cloturee from table commandenoncloturee: "+selectQuery.toString());
        return commandeNonCloturees;
    }

    public ArrayList<CommandeNonCloturee> getListByClientCode(String client_code) {
        ArrayList<CommandeNonCloturee> commandeNonCloturees = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDENONCLOTUREE  +" WHERE "+ KEY_CLIENT_CODE +" = '"+client_code+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeNonCloturee commandeNonCloturee = new CommandeNonCloturee();
                commandeNonCloturee.setCOMMANDE_CODE(cursor.getString(0));
                commandeNonCloturee.setFACTURE_CODE(cursor.getString(1));
                commandeNonCloturee.setFACTURECLIENT_CODE(cursor.getString(2));
                commandeNonCloturee.setDATE_COMMANDE(cursor.getString(3));
                commandeNonCloturee.setDATE_LIVRAISON((cursor.getString(4)));
                commandeNonCloturee.setDATE_CREATION(cursor.getString(5));
                commandeNonCloturee.setPERIODE_CODE(cursor.getString(6));
                commandeNonCloturee.setCOMMANDETYPE_CODE(cursor.getString(7));
                commandeNonCloturee.setCOMMANDESTATUT_CODE(cursor.getString(8));
                commandeNonCloturee.setDISTRIBUTEUR_CODE(cursor.getString(9));
                commandeNonCloturee.setVENDEUR_CODE(cursor.getString(10));
                commandeNonCloturee.setCLIENT_CODE(cursor.getString(11));
                commandeNonCloturee.setCREATEUR_CODE(cursor.getString(12));
                commandeNonCloturee.setLIVREUR_CODE(cursor.getString(13));
                commandeNonCloturee.setREGION_CODE(cursor.getString(14));
                commandeNonCloturee.setZONE_CODE(cursor.getString(15));
                commandeNonCloturee.setSECTEUR_CODE(cursor.getString(16));
                commandeNonCloturee.setSOUSSECTEUR_CODE(cursor.getString(17));
                commandeNonCloturee.setTOURNEE_CODE(cursor.getString(18));
                commandeNonCloturee.setVISITE_CODE(cursor.getString(19));
                commandeNonCloturee.setSTOCKDEPART_CODE(cursor.getString(20));
                commandeNonCloturee.setSTOCKDESTINATION_CODE(cursor.getString(21));
                commandeNonCloturee.setDESTINATION_CODE(cursor.getString(22));
                commandeNonCloturee.setTS(cursor.getString(23));
                commandeNonCloturee.setMONTANT_BRUT(cursor.getDouble(24));
                commandeNonCloturee.setREMISE(cursor.getDouble(25));
                commandeNonCloturee.setMONTANT_NET(cursor.getDouble(26));
                commandeNonCloturee.setVALEUR_COMMANDE(cursor.getDouble(27));
                commandeNonCloturee.setLITTRAGE_COMMANDE(cursor.getDouble(28));
                commandeNonCloturee.setTONNAGE_COMMANDE(cursor.getDouble(29));
                commandeNonCloturee.setKG_COMMANDE(cursor.getDouble(30));
                commandeNonCloturee.setCOMMENTAIRE(cursor.getString(31));
                commandeNonCloturee.setPAIEMENT_CODE(cursor.getInt(32));
                commandeNonCloturee.setNB_LIGNE(cursor.getInt(33));
                commandeNonCloturee.setCIRCUIT_CODE(cursor.getString(34));
                commandeNonCloturee.setCHANNEL_CODE(cursor.getString(35));
                commandeNonCloturee.setSTATUT_CODE(cursor.getString(36));
                commandeNonCloturee.setSOURCE(cursor.getString(37));
                commandeNonCloturee.setVERSION(cursor.getString(38));
                commandeNonCloturee.setGPS_LATITUDE(cursor.getString(39));
                commandeNonCloturee.setGPS_LONGITUDE(cursor.getString(40));
                commandeNonCloturee.setDISTANCE(cursor.getInt(41));

                commandeNonCloturees.add(commandeNonCloturee);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande non cloturee from table commandenoncloturee: "+commandeNonCloturees.toString());
        return commandeNonCloturees;
    }

    public ArrayList<CommandeNonCloturee> getListALByClientCode(String client_code) {
        ArrayList<CommandeNonCloturee> commandeNonCloturees = new ArrayList<>();

        String selectQuery = "SELECT  * FROM  commandenonclotureeal WHERE commandenonclotureeal.CLIENT_CODE"+" = '"+client_code+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeNonCloturee commandeNonCloturee = new CommandeNonCloturee();
                commandeNonCloturee.setCOMMANDE_CODE(cursor.getString(0));
                commandeNonCloturee.setFACTURE_CODE(cursor.getString(1));
                commandeNonCloturee.setFACTURECLIENT_CODE(cursor.getString(2));
                commandeNonCloturee.setDATE_COMMANDE(cursor.getString(3));
                commandeNonCloturee.setDATE_LIVRAISON((cursor.getString(4)));
                commandeNonCloturee.setDATE_CREATION(cursor.getString(5));
                commandeNonCloturee.setPERIODE_CODE(cursor.getString(6));
                commandeNonCloturee.setCOMMANDETYPE_CODE(cursor.getString(7));
                commandeNonCloturee.setCOMMANDESTATUT_CODE(cursor.getString(8));
                commandeNonCloturee.setDISTRIBUTEUR_CODE(cursor.getString(9));
                commandeNonCloturee.setVENDEUR_CODE(cursor.getString(10));
                commandeNonCloturee.setCLIENT_CODE(cursor.getString(11));
                commandeNonCloturee.setCREATEUR_CODE(cursor.getString(12));
                commandeNonCloturee.setLIVREUR_CODE(cursor.getString(13));
                commandeNonCloturee.setREGION_CODE(cursor.getString(14));
                commandeNonCloturee.setZONE_CODE(cursor.getString(15));
                commandeNonCloturee.setSECTEUR_CODE(cursor.getString(16));
                commandeNonCloturee.setSOUSSECTEUR_CODE(cursor.getString(17));
                commandeNonCloturee.setTOURNEE_CODE(cursor.getString(18));
                commandeNonCloturee.setVISITE_CODE(cursor.getString(19));
                commandeNonCloturee.setSTOCKDEPART_CODE(cursor.getString(20));
                commandeNonCloturee.setSTOCKDESTINATION_CODE(cursor.getString(21));
                commandeNonCloturee.setDESTINATION_CODE(cursor.getString(22));
                commandeNonCloturee.setTS(cursor.getString(23));
                commandeNonCloturee.setMONTANT_BRUT(cursor.getDouble(24));
                commandeNonCloturee.setREMISE(cursor.getDouble(25));
                commandeNonCloturee.setMONTANT_NET(cursor.getDouble(26));
                commandeNonCloturee.setVALEUR_COMMANDE(cursor.getDouble(27));
                commandeNonCloturee.setLITTRAGE_COMMANDE(cursor.getDouble(28));
                commandeNonCloturee.setTONNAGE_COMMANDE(cursor.getDouble(29));
                commandeNonCloturee.setKG_COMMANDE(cursor.getDouble(30));
                commandeNonCloturee.setCOMMENTAIRE(cursor.getString(31));
                commandeNonCloturee.setPAIEMENT_CODE(cursor.getInt(32));
                commandeNonCloturee.setNB_LIGNE(cursor.getInt(33));
                commandeNonCloturee.setCIRCUIT_CODE(cursor.getString(34));
                commandeNonCloturee.setCHANNEL_CODE(cursor.getString(35));
                commandeNonCloturee.setSTATUT_CODE(cursor.getString(36));
                commandeNonCloturee.setSOURCE(cursor.getString(37));
                commandeNonCloturee.setVERSION(cursor.getString(38));
                commandeNonCloturee.setGPS_LATITUDE(cursor.getString(39));
                commandeNonCloturee.setGPS_LONGITUDE(cursor.getString(40));
                commandeNonCloturee.setDISTANCE(cursor.getInt(41));

                commandeNonCloturees.add(commandeNonCloturee);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande non cloturee à livrer from table commandenonclotureeal: "+commandeNonCloturees.toString());
        return commandeNonCloturees;
    }

    public ArrayList<CommandeNonCloturee> getListAEByClientCode(String client_code) {
        ArrayList<CommandeNonCloturee> commandeNonCloturees = new ArrayList<>();

        String selectQuery = "SELECT  * FROM  commandenonclotureeae WHERE commandenonclotureeae.CLIENT_CODE"+" = '"+client_code+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeNonCloturee commandeNonCloturee = new CommandeNonCloturee();
                commandeNonCloturee.setCOMMANDE_CODE(cursor.getString(0));
                commandeNonCloturee.setFACTURE_CODE(cursor.getString(1));
                commandeNonCloturee.setFACTURECLIENT_CODE(cursor.getString(2));
                commandeNonCloturee.setDATE_COMMANDE(cursor.getString(3));
                commandeNonCloturee.setDATE_LIVRAISON((cursor.getString(4)));
                commandeNonCloturee.setDATE_CREATION(cursor.getString(5));
                commandeNonCloturee.setPERIODE_CODE(cursor.getString(6));
                commandeNonCloturee.setCOMMANDETYPE_CODE(cursor.getString(7));
                commandeNonCloturee.setCOMMANDESTATUT_CODE(cursor.getString(8));
                commandeNonCloturee.setDISTRIBUTEUR_CODE(cursor.getString(9));
                commandeNonCloturee.setVENDEUR_CODE(cursor.getString(10));
                commandeNonCloturee.setCLIENT_CODE(cursor.getString(11));
                commandeNonCloturee.setCREATEUR_CODE(cursor.getString(12));
                commandeNonCloturee.setLIVREUR_CODE(cursor.getString(13));
                commandeNonCloturee.setREGION_CODE(cursor.getString(14));
                commandeNonCloturee.setZONE_CODE(cursor.getString(15));
                commandeNonCloturee.setSECTEUR_CODE(cursor.getString(16));
                commandeNonCloturee.setSOUSSECTEUR_CODE(cursor.getString(17));
                commandeNonCloturee.setTOURNEE_CODE(cursor.getString(18));
                commandeNonCloturee.setVISITE_CODE(cursor.getString(19));
                commandeNonCloturee.setSTOCKDEPART_CODE(cursor.getString(20));
                commandeNonCloturee.setSTOCKDESTINATION_CODE(cursor.getString(21));
                commandeNonCloturee.setDESTINATION_CODE(cursor.getString(22));
                commandeNonCloturee.setTS(cursor.getString(23));
                commandeNonCloturee.setMONTANT_BRUT(cursor.getDouble(24));
                commandeNonCloturee.setREMISE(cursor.getDouble(25));
                commandeNonCloturee.setMONTANT_NET(cursor.getDouble(26));
                commandeNonCloturee.setVALEUR_COMMANDE(cursor.getDouble(27));
                commandeNonCloturee.setLITTRAGE_COMMANDE(cursor.getDouble(28));
                commandeNonCloturee.setTONNAGE_COMMANDE(cursor.getDouble(29));
                commandeNonCloturee.setKG_COMMANDE(cursor.getDouble(30));
                commandeNonCloturee.setCOMMENTAIRE(cursor.getString(31));
                commandeNonCloturee.setPAIEMENT_CODE(cursor.getInt(32));
                commandeNonCloturee.setNB_LIGNE(cursor.getInt(33));
                commandeNonCloturee.setCIRCUIT_CODE(cursor.getString(34));
                commandeNonCloturee.setCHANNEL_CODE(cursor.getString(35));
                commandeNonCloturee.setSTATUT_CODE(cursor.getString(36));
                commandeNonCloturee.setSOURCE(cursor.getString(37));
                commandeNonCloturee.setVERSION(cursor.getString(38));
                commandeNonCloturee.setGPS_LATITUDE(cursor.getString(39));
                commandeNonCloturee.setGPS_LONGITUDE(cursor.getString(40));
                commandeNonCloturee.setDISTANCE(cursor.getInt(41));

                commandeNonCloturees.add(commandeNonCloturee);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande non cloturee à encaisser from table commandenonclotureeae: "+commandeNonCloturees.toString());
        return commandeNonCloturees;
    }


    public CommandeNonCloturee getCmdNonClotureeByCmdCode(String commande_code) {
        CommandeNonCloturee commandeNonCloturee = new CommandeNonCloturee();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDENONCLOTUREE +" WHERE "+ KEY_COMMANDE_CODE +" = '"+commande_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {

            commandeNonCloturee.setCOMMANDE_CODE(cursor.getString(0));
            commandeNonCloturee.setFACTURE_CODE(cursor.getString(1));
            commandeNonCloturee.setFACTURECLIENT_CODE(cursor.getString(2));
            commandeNonCloturee.setDATE_COMMANDE(cursor.getString(3));
            commandeNonCloturee.setDATE_LIVRAISON((cursor.getString(4)));
            commandeNonCloturee.setDATE_CREATION(cursor.getString(5));
            commandeNonCloturee.setPERIODE_CODE(cursor.getString(6));
            commandeNonCloturee.setCOMMANDETYPE_CODE(cursor.getString(7));
            commandeNonCloturee.setCOMMANDESTATUT_CODE(cursor.getString(8));
            commandeNonCloturee.setDISTRIBUTEUR_CODE(cursor.getString(9));
            commandeNonCloturee.setVENDEUR_CODE(cursor.getString(10));
            commandeNonCloturee.setCLIENT_CODE(cursor.getString(11));
            commandeNonCloturee.setCREATEUR_CODE(cursor.getString(12));
            commandeNonCloturee.setLIVREUR_CODE(cursor.getString(13));
            commandeNonCloturee.setREGION_CODE(cursor.getString(14));
            commandeNonCloturee.setZONE_CODE(cursor.getString(15));
            commandeNonCloturee.setSECTEUR_CODE(cursor.getString(16));
            commandeNonCloturee.setSOUSSECTEUR_CODE(cursor.getString(17));
            commandeNonCloturee.setTOURNEE_CODE(cursor.getString(18));
            commandeNonCloturee.setVISITE_CODE(cursor.getString(19));
            commandeNonCloturee.setSTOCKDEPART_CODE(cursor.getString(20));
            commandeNonCloturee.setSTOCKDESTINATION_CODE(cursor.getString(21));
            commandeNonCloturee.setTS(cursor.getString(23));
            commandeNonCloturee.setMONTANT_BRUT(cursor.getDouble(24));
            commandeNonCloturee.setREMISE(cursor.getDouble(25));
            commandeNonCloturee.setMONTANT_NET(cursor.getDouble(26));
            commandeNonCloturee.setVALEUR_COMMANDE(cursor.getDouble(27));
            commandeNonCloturee.setLITTRAGE_COMMANDE(cursor.getDouble(28));
            commandeNonCloturee.setTONNAGE_COMMANDE(cursor.getDouble(29));
            commandeNonCloturee.setKG_COMMANDE(cursor.getDouble(30));
            commandeNonCloturee.setCOMMENTAIRE(cursor.getString(31));
            commandeNonCloturee.setPAIEMENT_CODE(cursor.getInt(32));
            commandeNonCloturee.setNB_LIGNE(cursor.getInt(33));
            commandeNonCloturee.setCIRCUIT_CODE(cursor.getString(34));
            commandeNonCloturee.setCHANNEL_CODE(cursor.getString(35));
            commandeNonCloturee.setSTATUT_CODE(cursor.getString(36));
            commandeNonCloturee.setSOURCE(cursor.getString(37));
            commandeNonCloturee.setVERSION(cursor.getString(38));
            commandeNonCloturee.setGPS_LATITUDE(cursor.getString(39));
            commandeNonCloturee.setGPS_LONGITUDE(cursor.getString(40));
            commandeNonCloturee.setDISTANCE(cursor.getInt(41));

        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching commandes non cloturees from table commandenoncloturee: ");
        return commandeNonCloturee;
    }

    public void deleteCommandes() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_COMMANDENONCLOTUREE, null, null);
        db.close();
        Log.d(TAG, "Deleted all commandes non cloturees info from sqlite");
    }

    public static void synchronisationCommandeNonCloturee(final Context context){

        String tag_string_req = "COMMANDE_NON_CLOTUREE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_COMMANDENONCLOTUREE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ouvrir le logManager
                Log.d("CommandeNonCloturee ", ""+response);
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray CommandeNonCloturee = jObj.getJSONArray("CommandeNonCloturee");
                        //Toast.makeText(context, "Nombre de CommandeNonCloturee " +CommandeNonCloturee.length(), Toast.LENGTH_SHORT).show();
                        int cptInsert = 0,cptDeleted = 0;
                        //Ajout/Suppression/modification des articles dans la base de données.
                        for (int i = 0; i < CommandeNonCloturee.length(); i++) {
                            JSONObject uneCommandeNonCloturee = CommandeNonCloturee.getJSONObject(i);
                            CommandeNonClotureeManager commandeNonClotureeManager = new CommandeNonClotureeManager(context);

                            if(uneCommandeNonCloturee.getString("OPERATION").equals("DELETE")){
                                commandeNonClotureeManager.deleteByCcVersion(uneCommandeNonCloturee.getString("COMMANDE_CODE"),uneCommandeNonCloturee.getString("VERSION"));
                                cptDeleted++;
                            }else {
                                Log.d("CommandeNonCloturee", "onResponse: CommandeNonCloturee"+uneCommandeNonCloturee.toString());
                                commandeNonClotureeManager.add(new CommandeNonCloturee(uneCommandeNonCloturee));
                                cptInsert++;
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE_NON_CLOTUREE: Inserted: " + cptInsert + " Deleted: " + cptDeleted, "COMMANDE_NON_CLOTUREE", 1));

                        }

                        //logM.add("CommandeNonCloturee:OK Insert:"+cptInsert +"Deleted:"+cptDeleted ,"SyncuneCommandeNonCloturee");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //logM.add("CommandeNonCloturee :NOK Insert "+errorMsg ,"SyncCommandeNonCloturee");
                        //Toast.makeText(context,"CommandeNonCloturee:"+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE_NON_CLOTUREE: Error: "+errorMsg, "COMMANDE_NON_CLOTUREE", 0));

                        }

                    }

                } catch (JSONException e) {
                    //logM.add("CommandeNonCloturee : NOK Insert "+e.getMessage() ,"SyncCommandeNonCloturee");
                    e.printStackTrace();
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE_NON_CLOTUREE: Error: "+e.getMessage(), "COMMANDE_NON_CLOTUREE", 0));

                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "CommandeNonCloturee:"+error.getMessage(), Toast.LENGTH_LONG).show();
                //Log.d(TAG, "onErrorResponse: CommandeNonCloturee : "+error.getMessage());
                //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("CommandeNonCloturee : NOK Inserted "+error.getMessage() ,"SyncCommandeNonCloturee");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE_NON_CLOTUREE: Error: "+error.getMessage(), "COMMANDE_NON_CLOTUREE", 0));

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


                    CommandeNonClotureeManager commandeNonClotureeManager = new CommandeNonClotureeManager(context);
                    ArrayList<CommandeNonCloturee> commandeNonCloturees = new ArrayList<>();
                    commandeNonCloturees=commandeNonClotureeManager.getList();

                    Log.d(TAG, "getParams: commandenoncloturee  : "+commandeNonCloturees);

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    Log.d("uc cmdnc sync",gson.toJson(pref.getString("UTILISATEUR_CODE",null)));

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(commandeNonCloturees));
                }
                return arrayFinale;
            }
        };
        // Adding request to request queue

        strReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


    }

    public ArrayList<CommandeNonCloturee> getListCmdNC(String client_code, Context context){

        int Nbrecmd = 0;//Nombre de commdes différentes
        CommandeManager commandeManager = new CommandeManager(context);
        CommandeNonClotureeManager commandeNonClotureeManager = new CommandeNonClotureeManager(context);

        ArrayList<CommandeNonCloturee> commandeNonCloturees;
        ArrayList<Commande> commandes;
        ArrayList<CommandeNonCloturee> commandesFinales = new ArrayList<>();

        commandes = commandeManager.getListByClientCode(client_code);
        commandeNonCloturees = commandeNonClotureeManager.getListByClientCode(client_code);

        Log.d(TAG, "getListCmd: "+commandes.toString());
        Log.d(TAG, "getListCmd Size: "+commandes.size());

        Log.d(TAG, "getListCmdNC: "+commandeNonCloturees.toString());
        Log.d(TAG, "getListCmdNC Size: "+commandeNonCloturees.size());

        if(commandes.size()>0 && commandeNonCloturees.size()>0){
            for(int i=0;i<=commandes.size();i++){

                for(int j=0;j<=commandeNonCloturees.size();j++){

                    if(commandeNonCloturees.get(i).getCOMMANDE_CODE()==commandes.get(j).getCOMMANDE_CODE()){
                        break;
                    }else{
                        Nbrecmd++;
                    }
                }

                if(Nbrecmd==commandeNonCloturees.size()){
                    commandeNonCloturees.add(new CommandeNonCloturee(commandes.get(i)));
                }
            }
        }else if(commandes.size()>0 && commandeNonCloturees.size()==0){

            for(int i=0;i<commandes.size();i++){
                commandeNonCloturees.add(new CommandeNonCloturee(commandes.get(i)));
            }

        }

        return commandeNonCloturees;
    }

    public double getNumberRounded(double nombre){

        double number = nombre;
        int decimalsToConsider = 2;
        BigDecimal bigDecimal = new BigDecimal(number);
        BigDecimal roundedWithScale = bigDecimal.setScale(decimalsToConsider, BigDecimal.ROUND_HALF_UP);
        //System.out.println("Rounded value with setting scale = "+roundedWithScale);


        return roundedWithScale.doubleValue();
    }

}
