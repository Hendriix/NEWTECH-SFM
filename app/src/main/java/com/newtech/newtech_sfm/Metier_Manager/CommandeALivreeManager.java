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
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Activity.ClientActivity;
import com.newtech.newtech_sfm.Configuration.AppConfig;
import com.newtech.newtech_sfm.Configuration.AppController;
import com.newtech.newtech_sfm.Configuration.SQLiteHandler;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.CommandeALivrer;
import com.newtech.newtech_sfm.Metier.CommandeGratuite;
import com.newtech.newtech_sfm.Metier.CommandeLigne;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stagiaireit2 on 02/08/2016.
 */
public class CommandeALivreeManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_COMMANDEALIVRER = "commandealivrer";

    Client clientCourant=ClientActivity.clientCourant;

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
            KEY_MONTANT_BRUT="MONTANT_BRUT",
            KEY_REMISE="REMISE",
            KEY_MONTANT_NET="MONTANT_NET",
            KEY_VALEUR_COMMANDE="VALEUR_COMMANDE",
            KEY_LITTRAGE_COMMANDE="LITTRAGE_COMMANDE",
            KEY_TONNAGE_COMMANDE="TONNAGE_COMMANDE",
            KEY_KG_COMMANDE="KG_COMMANDE",
            KEY_COMMENTAIRE="COMMENTAIRE",
            KEY_PAIEMENT_CODE="PAIEMENT_CODE",
            KEY_CIRCUIT_CODE="CIRCUIT_CODE",
            KEY_CHANNEL_CODE="CHANNEL_CODE",
            KEY_STATUT_CODE="STATUT_CODE",
            KEY_VERSION="VERSION",
            KEY_GPS_LATITUDE="GPS_LATITUDE",
            KEY_GPS_LONGITUDE="GPS_LONGITUDE",
            KEY_DISTANCE="DISTANCE";

    public static String CREATE_COMMANDEALIVRER_TABLE = "CREATE TABLE " + TABLE_COMMANDEALIVRER + "("
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
            +KEY_DESTINATION_CODE   + " TEXT,"
            +KEY_MONTANT_BRUT+ " NUMERIC ,"
            +KEY_REMISE + " NUMERIC ,"
            +KEY_MONTANT_NET+ " NUMERIC,"
            +KEY_VALEUR_COMMANDE   + " NUMERIC,"
            +KEY_LITTRAGE_COMMANDE  + " NUMERIC,"
            +KEY_TONNAGE_COMMANDE  + " NUMERIC,"
            +KEY_KG_COMMANDE+ " NUMERIC,"
            +KEY_COMMENTAIRE+ " TEXT,"
            +KEY_PAIEMENT_CODE + " NUMERIC,"
            +KEY_CIRCUIT_CODE+ " TEXT,"
            +KEY_CHANNEL_CODE+ " TEXT,"
            +KEY_STATUT_CODE+ " TEXT,"
            +KEY_VERSION+ " TEXT ,"
            +KEY_GPS_LATITUDE + " TEXT,"
            +KEY_GPS_LONGITUDE + " TEXT,"
            +KEY_DISTANCE + " NUMERIC"+ ")";

    public CommandeALivreeManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE);
            db.execSQL(CREATE_COMMANDEALIVRER_TABLE);
        } catch (SQLException e) {
        }
        Log.d(TAG, "table CommandeALivrer created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMANDEALIVRER);
        onCreate(db);
    }

    public void add(CommandeALivrer commandeALivrer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COMMANDE_CODE, commandeALivrer.getCOMMANDE_CODE());
        values.put(KEY_FACTURE_CODE, commandeALivrer.getFACTURE_CODE());
        values.put(KEY_FACTURECLIENT_CODE, commandeALivrer.getFACTURECLIENT_CODE());
        values.put(KEY_DATE_COMMANDE, commandeALivrer.getDATE_COMMANDE());
        values.put(KEY_DATE_LIVRAISON, commandeALivrer.getDATE_LIVRAISON());
        values.put(KEY_DATE_CREATION, commandeALivrer.getDATE_CREATION());
        values.put(KEY_PERIODE_CODE, commandeALivrer.getPERIODE_CODE());
        values.put(KEY_COMMANDETYPE_CODE, commandeALivrer.getCOMMANDETYPE_CODE());
        values.put(KEY_COMMANDESTATUT_CODE, commandeALivrer.getCOMMANDESTATUT_CODE());
        values.put(KEY_DISTRIBUTEUR_CODE, commandeALivrer.getDISTRIBUTEUR_CODE());
        values.put(KEY_VENDEUR_CODE, commandeALivrer.getVENDEUR_CODE());
        values.put(KEY_CLIENT_CODE, commandeALivrer.getCLIENT_CODE());
        values.put(KEY_CREATEUR_CODE, commandeALivrer.getCREATEUR_CODE());
        values.put(KEY_LIVREUR_CODE, commandeALivrer.getLIVREUR_CODE());
        values.put(KEY_REGION_CODE, commandeALivrer.getREGION_CODE());
        values.put(KEY_ZONE_CODE, commandeALivrer.getZONE_CODE());
        values.put(KEY_SECTEUR_CODE, commandeALivrer.getSECTEUR_CODE());
        values.put(KEY_SOUSSECTEUR_CODE, commandeALivrer.getSOUSSECTEUR_CODE());
        values.put(KEY_TOURNEE_CODE, commandeALivrer.getTOURNEE_CODE());
        values.put(KEY_VISITE_CODE, commandeALivrer.getVISITE_CODE());
        values.put(KEY_STOCKDEPART_CODE, commandeALivrer.getSTOCKDEPART_CODE());
        values.put(KEY_STOCKDESTINATION_CODE, commandeALivrer.getSTOCKDESTINATION_CODE());
        values.put(KEY_DESTINATION_CODE, commandeALivrer.getDESTINATION_CODE());
        values.put(KEY_MONTANT_BRUT, commandeALivrer.getMONTANT_BRUT());
        values.put(KEY_REMISE, commandeALivrer.getREMISE());
        values.put(KEY_MONTANT_NET, commandeALivrer.getMONTANT_NET());
        values.put(KEY_VALEUR_COMMANDE, commandeALivrer.getVALEUR_COMMANDE());
        values.put(KEY_LITTRAGE_COMMANDE, commandeALivrer.getLITTRAGE_COMMANDE());
        values.put(KEY_TONNAGE_COMMANDE, commandeALivrer.getTONNAGE_COMMANDE());
        values.put(KEY_KG_COMMANDE, commandeALivrer.getKG_COMMANDE());
        values.put(KEY_COMMENTAIRE, commandeALivrer.getCOMMENTAIRE());
        values.put(KEY_PAIEMENT_CODE, commandeALivrer.getPAIEMENT_CODE());
        values.put(KEY_CIRCUIT_CODE, commandeALivrer.getCIRCUIT_CODE());
        values.put(KEY_CHANNEL_CODE, commandeALivrer.getCHANNEL_CODE());
        values.put(KEY_STATUT_CODE, commandeALivrer.getSTATUT_CODE());
        values.put(KEY_VERSION, commandeALivrer.getVERSION());
        values.put(KEY_GPS_LATITUDE, commandeALivrer.getGPS_LATITUDE());
        values.put(KEY_GPS_LONGITUDE, commandeALivrer.getGPS_LONGITUDE());
        values.put(KEY_DISTANCE, commandeALivrer.getDISTANCE());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_COMMANDEALIVRER, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d(TAG, "nouvelle commande A Livree inseré dans la table Commande: " + id);
    }

    public int delete(String commandeCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_COMMANDEALIVRER,KEY_COMMANDE_CODE+"=?",new String[]{commandeCode});
    }

    public void updateCommande(String commandeCode,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_COMMANDEALIVRER + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_COMMANDE_CODE +"= '"+commandeCode+"'" ;
        db.execSQL(req);
    }

    public void deleteCmdSynchronisee() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefCmdAS = sdf.format(new Date());
        String Where = " "+KEY_COMMENTAIRE+"='commande verifiee' and date("+KEY_DATE_CREATION+")<>date('"+DatefCmdAS+"') ";

        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_COMMANDEALIVRER, Where, null);
        db.close();
        Log.d(TAG, "Deleted all commandes verifiee from sqlite");
        Log.d(TAG, "deleteCmdSynchronisee: "+Where);
    }


    public ArrayList<CommandeALivrer> getList() {
        ArrayList<CommandeALivrer> listCommandeALivrer = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDEALIVRER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeALivrer cmd = new CommandeALivrer();
                cmd.setCOMMANDE_CODE(cursor.getString(0));
                cmd.setFACTURE_CODE(cursor.getString(1));
                cmd.setFACTURECLIENT_CODE(cursor.getString(2));
                cmd.setDATE_COMMANDE(cursor.getString(3));
                cmd.setDATE_LIVRAISON((cursor.getString(4)));
                cmd.setDATE_CREATION(cursor.getString(5));
                cmd.setPERIODE_CODE(cursor.getString(6));
                cmd.setCOMMANDETYPE_CODE(cursor.getString(7));
                cmd.setCOMMANDESTATUT_CODE(cursor.getString(8));
                cmd.setDISTRIBUTEUR_CODE(cursor.getString(9));
                cmd.setVENDEUR_CODE(cursor.getString(10));
                cmd.setCLIENT_CODE(cursor.getString(11));
                cmd.setCREATEUR_CODE(cursor.getString(12));
                cmd.setLIVREUR_CODE(cursor.getString(13));
                cmd.setREGION_CODE(cursor.getString(14));
                cmd.setZONE_CODE(cursor.getString(15));
                cmd.setSECTEUR_CODE(cursor.getString(16));
                cmd.setSOUSSECTEUR_CODE(cursor.getString(17));
                cmd.setTOURNEE_CODE(cursor.getString(18));
                cmd.setVISITE_CODE(cursor.getString(19));
                cmd.setSTOCKDEPART_CODE(cursor.getString(20));
                cmd.setSTOCKDESTINATION_CODE(cursor.getString(21));
                cmd.setDESTINATION_CODE(cursor.getString(22));
                cmd.setMONTANT_BRUT(cursor.getDouble(23));
                cmd.setREMISE(cursor.getDouble(24));
                cmd.setMONTANT_NET(cursor.getDouble(25));
                cmd.setVALEUR_COMMANDE(cursor.getDouble(26));
                cmd.setLITTRAGE_COMMANDE(cursor.getDouble(27));
                cmd.setTONNAGE_COMMANDE(cursor.getDouble(28));
                cmd.setKG_COMMANDE(cursor.getDouble(29));
                cmd.setCOMMENTAIRE(cursor.getString(30));
                cmd.setPAIEMENT_CODE(cursor.getInt(31));
                cmd.setCIRCUIT_CODE(cursor.getString(32));
                cmd.setCHANNEL_CODE(cursor.getString(33));
                cmd.setSTATUT_CODE(cursor.getString(34));
                cmd.setVERSION(cursor.getString(35));
                cmd.setGPS_LATITUDE(cursor.getString(36));
                cmd.setGPS_LONGITUDE(cursor.getString(37));
                cmd.setDISTANCE(cursor.getInt(38));

                listCommandeALivrer.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching commande a livrer from table commandeAlivrer: ");
        return listCommandeALivrer;
    }

    public ArrayList<CommandeALivrer> getListByCmdSC(String commandestatut_code) {
        ArrayList<CommandeALivrer> listCommandeALivrer = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDEALIVRER +" WHERE "+KEY_COMMANDESTATUT_CODE +"!= '"+commandestatut_code+"'" ;;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeALivrer cmd = new CommandeALivrer();
                cmd.setCOMMANDE_CODE(cursor.getString(0));
                cmd.setFACTURE_CODE(cursor.getString(1));
                cmd.setFACTURECLIENT_CODE(cursor.getString(2));
                cmd.setDATE_COMMANDE(cursor.getString(3));
                cmd.setDATE_LIVRAISON((cursor.getString(4)));
                cmd.setDATE_CREATION(cursor.getString(5));
                cmd.setPERIODE_CODE(cursor.getString(6));
                cmd.setCOMMANDETYPE_CODE(cursor.getString(7));
                cmd.setCOMMANDESTATUT_CODE(cursor.getString(8));
                cmd.setDISTRIBUTEUR_CODE(cursor.getString(9));
                cmd.setVENDEUR_CODE(cursor.getString(10));
                cmd.setCLIENT_CODE(cursor.getString(11));
                cmd.setCREATEUR_CODE(cursor.getString(12));
                cmd.setLIVREUR_CODE(cursor.getString(13));
                cmd.setREGION_CODE(cursor.getString(14));
                cmd.setZONE_CODE(cursor.getString(15));
                cmd.setSECTEUR_CODE(cursor.getString(16));
                cmd.setSOUSSECTEUR_CODE(cursor.getString(17));
                cmd.setTOURNEE_CODE(cursor.getString(18));
                cmd.setVISITE_CODE(cursor.getString(19));
                cmd.setSTOCKDEPART_CODE(cursor.getString(20));
                cmd.setSTOCKDESTINATION_CODE(cursor.getString(21));
                cmd.setDESTINATION_CODE(cursor.getString(22));
                cmd.setMONTANT_BRUT(cursor.getDouble(23));
                cmd.setREMISE(cursor.getDouble(24));
                cmd.setMONTANT_NET(cursor.getDouble(25));
                cmd.setVALEUR_COMMANDE(cursor.getDouble(26));
                cmd.setLITTRAGE_COMMANDE(cursor.getDouble(27));
                cmd.setTONNAGE_COMMANDE(cursor.getDouble(28));
                cmd.setKG_COMMANDE(cursor.getDouble(29));
                cmd.setCOMMENTAIRE(cursor.getString(30));
                cmd.setPAIEMENT_CODE(cursor.getInt(31));
                cmd.setCIRCUIT_CODE(cursor.getString(32));
                cmd.setCHANNEL_CODE(cursor.getString(33));
                cmd.setSTATUT_CODE(cursor.getString(34));
                cmd.setVERSION(cursor.getString(35));
                cmd.setGPS_LATITUDE(cursor.getString(36));
                cmd.setGPS_LONGITUDE(cursor.getString(37));
                cmd.setDISTANCE(cursor.getInt(38));

                listCommandeALivrer.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching commande a livrer from table commandeAlivrer: "+selectQuery.toString());
        return listCommandeALivrer;
    }

    public ArrayList<CommandeALivrer> getListByClientCode(String client_code) {
        ArrayList<CommandeALivrer> listCommandeALivrer = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDEALIVRER +" WHERE "+ KEY_CLIENT_CODE +" = '"+client_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeALivrer cmd = new CommandeALivrer();
                cmd.setCOMMANDE_CODE(cursor.getString(0));
                cmd.setFACTURE_CODE(cursor.getString(1));
                cmd.setFACTURECLIENT_CODE(cursor.getString(2));
                cmd.setDATE_COMMANDE(cursor.getString(3));
                cmd.setDATE_LIVRAISON((cursor.getString(4)));
                cmd.setDATE_CREATION(cursor.getString(5));
                cmd.setPERIODE_CODE(cursor.getString(6));
                cmd.setCOMMANDETYPE_CODE(cursor.getString(7));
                cmd.setCOMMANDESTATUT_CODE(cursor.getString(8));
                cmd.setDISTRIBUTEUR_CODE(cursor.getString(9));
                cmd.setVENDEUR_CODE(cursor.getString(10));
                cmd.setCLIENT_CODE(cursor.getString(11));
                cmd.setCREATEUR_CODE(cursor.getString(12));
                cmd.setLIVREUR_CODE(cursor.getString(13));
                cmd.setREGION_CODE(cursor.getString(14));
                cmd.setZONE_CODE(cursor.getString(15));
                cmd.setSECTEUR_CODE(cursor.getString(16));
                cmd.setSOUSSECTEUR_CODE(cursor.getString(17));
                cmd.setTOURNEE_CODE(cursor.getString(18));
                cmd.setVISITE_CODE(cursor.getString(19));
                cmd.setSTOCKDEPART_CODE(cursor.getString(20));
                cmd.setSTOCKDESTINATION_CODE(cursor.getString(21));
                cmd.setDESTINATION_CODE(cursor.getString(22));
                cmd.setMONTANT_BRUT(cursor.getDouble(23));
                cmd.setREMISE(cursor.getDouble(24));
                cmd.setMONTANT_NET(cursor.getDouble(25));
                cmd.setVALEUR_COMMANDE(cursor.getDouble(26));
                cmd.setLITTRAGE_COMMANDE(cursor.getDouble(27));
                cmd.setTONNAGE_COMMANDE(cursor.getDouble(28));
                cmd.setKG_COMMANDE(cursor.getDouble(29));
                cmd.setCOMMENTAIRE(cursor.getString(30));
                cmd.setPAIEMENT_CODE(cursor.getInt(31));
                cmd.setCIRCUIT_CODE(cursor.getString(32));
                cmd.setCHANNEL_CODE(cursor.getString(33));
                cmd.setSTATUT_CODE(cursor.getString(34));
                cmd.setVERSION(cursor.getString(35));
                cmd.setGPS_LATITUDE(cursor.getString(36));
                cmd.setGPS_LONGITUDE(cursor.getString(37));
                cmd.setDISTANCE(cursor.getInt(38));

                listCommandeALivrer.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande a livrer from table commandeAlivrer: "+listCommandeALivrer.toString());
        return listCommandeALivrer;
    }

    public CommandeALivrer getCmdALivrerByCmdCode(String commande_code) {
        CommandeALivrer cmd = new CommandeALivrer();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDEALIVRER +" WHERE "+ KEY_COMMANDE_CODE +" = '"+commande_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {

                cmd.setCOMMANDE_CODE(cursor.getString(0));
                cmd.setFACTURE_CODE(cursor.getString(1));
                cmd.setFACTURECLIENT_CODE(cursor.getString(2));
                cmd.setDATE_COMMANDE(cursor.getString(3));
                cmd.setDATE_LIVRAISON((cursor.getString(4)));
                cmd.setDATE_CREATION(cursor.getString(5));
                cmd.setPERIODE_CODE(cursor.getString(6));
                cmd.setCOMMANDETYPE_CODE(cursor.getString(7));
                cmd.setCOMMANDESTATUT_CODE(cursor.getString(8));
                cmd.setDISTRIBUTEUR_CODE(cursor.getString(9));
                cmd.setVENDEUR_CODE(cursor.getString(10));
                cmd.setCLIENT_CODE(cursor.getString(11));
                cmd.setCREATEUR_CODE(cursor.getString(12));
                cmd.setLIVREUR_CODE(cursor.getString(13));
                cmd.setREGION_CODE(cursor.getString(14));
                cmd.setZONE_CODE(cursor.getString(15));
                cmd.setSECTEUR_CODE(cursor.getString(16));
                cmd.setSOUSSECTEUR_CODE(cursor.getString(17));
                cmd.setTOURNEE_CODE(cursor.getString(18));
                cmd.setVISITE_CODE(cursor.getString(19));
                cmd.setSTOCKDEPART_CODE(cursor.getString(20));
                cmd.setSTOCKDESTINATION_CODE(cursor.getString(21));
                cmd.setDESTINATION_CODE(cursor.getString(22));
                cmd.setMONTANT_BRUT(cursor.getDouble(23));
                cmd.setREMISE(cursor.getDouble(24));
                cmd.setMONTANT_NET(cursor.getDouble(25));
                cmd.setVALEUR_COMMANDE(cursor.getDouble(26));
                cmd.setLITTRAGE_COMMANDE(cursor.getDouble(27));
                cmd.setTONNAGE_COMMANDE(cursor.getDouble(28));
                cmd.setKG_COMMANDE(cursor.getDouble(29));
                cmd.setCOMMENTAIRE(cursor.getString(30));
                cmd.setPAIEMENT_CODE(cursor.getInt(31));
                cmd.setCIRCUIT_CODE(cursor.getString(32));
                cmd.setCHANNEL_CODE(cursor.getString(33));
                cmd.setSTATUT_CODE(cursor.getString(34));
                cmd.setVERSION(cursor.getString(35));
                cmd.setGPS_LATITUDE(cursor.getString(36));
                cmd.setGPS_LONGITUDE(cursor.getString(37));
                cmd.setDISTANCE(cursor.getInt(38));

        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande a livrer from table commandeAlivrer: ");
        return cmd;
    }


    public void deleteCommandes() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_COMMANDEALIVRER, null, null);
        db.close();
        Log.d(TAG, "Deleted all commandes info from sqlite");
    }


    ///////////////////////////////////////////////SYNCHRONISATION COMMENDE A LIVRAISON/////////////////////////////////////////////

    public static void synchronisationCommandeALivrer(final Context context){

        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_COMMANDEALIVRER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ouvrir le logManager
                Log.d("CommandeALivrer ", ""+response);
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray CommandeALivrer = jObj.getJSONArray("CommandeALivrer");
                        Toast.makeText(context, "Nombre de CommandeALivrer " +CommandeALivrer.length(), Toast.LENGTH_SHORT).show();
                        int cptInsert = 0,cptDeleted = 0;
                        //Ajout/Suppression/modification des articles dans la base de données.
                        for (int i = 0; i < CommandeALivrer.length(); i++) {
                            JSONObject uneCommandeALivrer = CommandeALivrer.getJSONObject(i);
                            CommandeALivreeManager commandeALivreManager = new CommandeALivreeManager(context);
                            if(uneCommandeALivrer.getString("OPERATION").equals("DELETE")){
                                commandeALivreManager.delete(uneCommandeALivrer.getString("COMMANDE_CODE"));
                                cptDeleted++;
                            }
                            else {
                                commandeALivreManager.add(new CommandeALivrer(uneCommandeALivrer));
                                cptInsert++;
                            }
                        }
                        logM.add("CommandeALivrer:OK Insert:"+cptInsert +"Deleted:"+cptDeleted ,"SyncCommandeALivrer");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        logM.add("CommandeALivrer:NOK Insert onResponseError"+errorMsg ,"SyncCommandeALivrer");
                        Toast.makeText(context,"CommandeALivrer:"+errorMsg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    logM.add("CommandeALivrer : NOK Insert "+e.getMessage() ,"SyncCommandeALivrer");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "CommandeALivrer:"+error.getMessage(), Toast.LENGTH_LONG).show();
                LogSyncManager logM= new LogSyncManager(context);
                logM.add("CommandeALivrer : NOK Inserted ErrorListener"+error.getMessage() ,"SyncCommandeALivrer");

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> arrayFinale= new HashMap<>();
                //Map<String, String> params = new HashMap<String, String>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    CommandeALivreeManager commandeALivrerManager  = new CommandeALivreeManager(context);
                    ArrayList<CommandeALivrer> commandeALivrers = new ArrayList<>();
                    commandeALivrers=commandeALivrerManager.getList();



                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(commandeALivrers));
                }
                return arrayFinale;

            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    static public String ImprimerCommande(Commande commande, ArrayList<CommandeLigne> lisCommandeLigne,Context context) {

        DecimalFormat formatDD = new DecimalFormat("#.00");
        DecimalFormat formatD = new DecimalFormat("#.0");
        TourneeManager tourneeManager = new TourneeManager(context);
        CommandeGratuiteManager commandeGratuiteManager = new CommandeGratuiteManager(context);
        ArticleManager articleManager=new ArticleManager(context);

        ArrayList<CommandeGratuite> list_commande_gratuite = new ArrayList<CommandeGratuite>();
        list_commande_gratuite=commandeGratuiteManager.getList();


                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                Gson gson2 = new Gson();
                String json2 = pref.getString("User", "");
                Type type = new TypeToken<JSONObject>() {}.getType();
                final JSONObject user = gson2.fromJson(json2, type);
                String User_Name="";
                 try {
                     User_Name= user.getString("UTILISATEUR_NOM");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Double TTC_BRUT = 0.0;
                Double REMISE = 0.0;
                Double TTC_APRES_REMISE = 0.0,TVA = 0.0,NET_PAYER = 0.0;
                String message ="              SAVOLA MOROCCO S.A\r\n" +
                        "w(----------------------------------------------------\r\n" +
                        "Facture Nr:"+commande.getCOMMANDE_CODE()+"\r\n" +
                        "Date:"+commande.getDATE_LIVRAISON() +"\r\n" +
                        "Client:"+ commande.getCLIENT_CODE()+" " + ClientActivity.clientCourant.getCLIENT_NOM()+"\r\n" +
                        "Tournee:"+ tourneeManager.get(commande.getTOURNEE_CODE()).getTOURNEE_NOM()+"\n"+
                        "Vendeur:"+User_Name+"\n" +
                        "----------------------------------------------------\n" +
                        "Produit           |QTE |PU   |Mt.Brut|Remise|Mt.Net\r\n" +
                        "\u001Bw(----------------------------------------------------\r\n";
                for(int i=0;i<lisCommandeLigne.size();i++) {
                    Double QTE =lisCommandeLigne.get(i).getQTE_COMMANDEE();

                    message += Nchaine(lisCommandeLigne.get(i).getARTICLE_DESIGNATION(),18) + "|"
                            +NchaineL(String.valueOf(QTE.intValue()),4)+"|"
                            +NchaineL(String.valueOf(formatD.format(lisCommandeLigne.get(i).getARTICLE_PRIX())),5)+"|"
                            +NchaineL(String.valueOf(formatD.format(lisCommandeLigne.get(i).getMONTANT_BRUT())),7)+"|"
                            +NchaineL(String.valueOf(formatD.format(lisCommandeLigne.get(i).getREMISE())),6)+"|"+
                             NchaineL(String.valueOf(formatD.format(lisCommandeLigne.get(i).getMONTANT_NET())),6) + "\r\n";
                    TTC_BRUT+=lisCommandeLigne.get(i).getMONTANT_BRUT();
                    REMISE+=lisCommandeLigne.get(i).getREMISE();
                    NET_PAYER+=lisCommandeLigne.get(i).getMONTANT_NET();
                }

                    String message2= "\u001Bw(----------------------------------------------------\r\n" +
                        "       TOTAL TTC Brut         :"+NchaineL(String.valueOf(formatDD.format(commande.getMONTANT_BRUT())),8)+"\r\n" +
                        "       Remise                 :"+NchaineL(String.valueOf(formatDD.format(commande.getREMISE())),8)+"\r\n" +
                        "       TOTAL TTC apres Remise :"+NchaineL(String.valueOf(formatDD.format(commande.getMONTANT_NET())),8)+"\r\n" +
                        "       dont TVA               :"+NchaineL(String.valueOf(formatDD.format(TVA)),8)+"\r\n" +
                        "            NET A payer       :"+NchaineL(String.valueOf(formatDD.format(commande.getMONTANT_NET())),8)+"\r\n" +
                        "\u001Bw(----------------------------------------------------\r\n\r\n";

                //Gratuitee
                    String message3="\u001Bw(\"Gratuite:\r\n" +
                        "Article             |QTE\r\n" +
                        "\u001Bw(-----------------------------\r\n";
                for(int i=0;i<list_commande_gratuite.size();i++){
                    Article article = new Article();
                    article=articleManager.get(list_commande_gratuite.get(i).getARTICLE_CODE());
                    message3+=Nchaine(article.getARTICLE_DESIGNATION1(),20) + "|"+NchaineL(String.valueOf(list_commande_gratuite.get(i).getQUANTITE()),4) + "\r\n";
                }

                message+=message2+message3+"\r\n\r\n\r\n\r\n";

        return message; }

    static String Nchaine(String machaine, int size){
        int taille = machaine.length();
        String spaces="";
        if(size<taille) return machaine.substring(0,size);
        for(int i=0;i<size-taille;i++)
            spaces+=" ";
        return machaine+spaces;
    }

    static String NchaineL(String machaine, int size){
        int taille = machaine.length();
        String spaces="";
        if(size<taille) return machaine.substring(0,size);
        for(int i=0;i<size-taille;i++)
            spaces+=" ";
        return spaces+machaine;
    }

    //RETURN VISTE BY CODE
    public int CheckCommandeByVisiteCode(String visite_code) {

        String selectQuery = "SELECT * FROM " + TABLE_COMMANDEALIVRER + " WHERE "+ KEY_VISITE_CODE +" = '"+visite_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int vcr=cursor.getCount();
        cursor.close();
        db.close();
        return vcr;

    }

}
