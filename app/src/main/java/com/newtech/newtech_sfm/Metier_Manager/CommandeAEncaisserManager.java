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
import com.newtech.newtech_sfm.Metier.CommandeAEncaisser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TONPC on 15/08/2017.
 */

public class CommandeAEncaisserManager extends SQLiteOpenHelper{

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_COMMANDEAENCAISSER = "commandeaencaisser";


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

    public static String CREATE_COMMANDEAENCAISSER_TABLE = "CREATE TABLE " + TABLE_COMMANDEAENCAISSER + "("
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



    public CommandeAEncaisserManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE);
            db.execSQL(CREATE_COMMANDEAENCAISSER_TABLE);
        } catch (SQLException e) {
        }
        Log.d(TAG, "table CommandeAEncaisser created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMANDEAENCAISSER);
        onCreate(db);
    }

    public void add(CommandeAEncaisser commandeAEncaisser) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COMMANDE_CODE, commandeAEncaisser.getCOMMANDE_CODE());
        values.put(KEY_FACTURE_CODE, commandeAEncaisser.getFACTURE_CODE());
        values.put(KEY_FACTURECLIENT_CODE, commandeAEncaisser.getFACTURECLIENT_CODE());
        values.put(KEY_DATE_COMMANDE, commandeAEncaisser.getDATE_COMMANDE());
        values.put(KEY_DATE_LIVRAISON, commandeAEncaisser.getDATE_LIVRAISON());
        values.put(KEY_DATE_CREATION, commandeAEncaisser.getDATE_CREATION());
        values.put(KEY_PERIODE_CODE, commandeAEncaisser.getPERIODE_CODE());
        values.put(KEY_COMMANDETYPE_CODE, commandeAEncaisser.getCOMMANDETYPE_CODE());
        values.put(KEY_COMMANDESTATUT_CODE, commandeAEncaisser.getCOMMANDESTATUT_CODE());
        values.put(KEY_DISTRIBUTEUR_CODE, commandeAEncaisser.getDISTRIBUTEUR_CODE());
        values.put(KEY_VENDEUR_CODE, commandeAEncaisser.getVENDEUR_CODE());
        values.put(KEY_CLIENT_CODE, commandeAEncaisser.getCLIENT_CODE());
        values.put(KEY_CREATEUR_CODE, commandeAEncaisser.getCREATEUR_CODE());
        values.put(KEY_LIVREUR_CODE, commandeAEncaisser.getLIVREUR_CODE());
        values.put(KEY_REGION_CODE, commandeAEncaisser.getREGION_CODE());
        values.put(KEY_ZONE_CODE, commandeAEncaisser.getZONE_CODE());
        values.put(KEY_SECTEUR_CODE, commandeAEncaisser.getSECTEUR_CODE());
        values.put(KEY_SOUSSECTEUR_CODE, commandeAEncaisser.getSOUSSECTEUR_CODE());
        values.put(KEY_TOURNEE_CODE, commandeAEncaisser.getTOURNEE_CODE());
        values.put(KEY_VISITE_CODE, commandeAEncaisser.getVISITE_CODE());
        values.put(KEY_STOCKDEPART_CODE, commandeAEncaisser.getSTOCKDEPART_CODE());
        values.put(KEY_STOCKDESTINATION_CODE, commandeAEncaisser.getSTOCKDESTINATION_CODE());
        values.put(KEY_DESTINATION_CODE, commandeAEncaisser.getDESTINATION_CODE());
        values.put(KEY_MONTANT_BRUT, commandeAEncaisser.getMONTANT_BRUT());
        values.put(KEY_REMISE, commandeAEncaisser.getREMISE());
        values.put(KEY_MONTANT_NET, commandeAEncaisser.getMONTANT_NET());
        values.put(KEY_VALEUR_COMMANDE, commandeAEncaisser.getVALEUR_COMMANDE());
        values.put(KEY_LITTRAGE_COMMANDE, commandeAEncaisser.getLITTRAGE_COMMANDE());
        values.put(KEY_TONNAGE_COMMANDE, commandeAEncaisser.getTONNAGE_COMMANDE());
        values.put(KEY_KG_COMMANDE, commandeAEncaisser.getKG_COMMANDE());
        values.put(KEY_COMMENTAIRE, commandeAEncaisser.getCOMMENTAIRE());
        values.put(KEY_PAIEMENT_CODE, commandeAEncaisser.getPAIEMENT_CODE());
        values.put(KEY_CIRCUIT_CODE, commandeAEncaisser.getCIRCUIT_CODE());
        values.put(KEY_CHANNEL_CODE, commandeAEncaisser.getCHANNEL_CODE());
        values.put(KEY_STATUT_CODE, commandeAEncaisser.getSTATUT_CODE());
        values.put(KEY_VERSION, commandeAEncaisser.getVERSION());
        values.put(KEY_GPS_LATITUDE, commandeAEncaisser.getGPS_LATITUDE());
        values.put(KEY_GPS_LONGITUDE, commandeAEncaisser.getGPS_LONGITUDE());
        values.put(KEY_DISTANCE, commandeAEncaisser.getDISTANCE());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_COMMANDEAENCAISSER, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d(TAG, "nouvelle commande A Encaisser inseré dans la table CommandeAEncaisser: " + id);
    }

    public int delete(String commandeCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_COMMANDEAENCAISSER,KEY_COMMANDE_CODE+"=?",new String[]{commandeCode});
    }

    public void updateCommande(String commandeCode,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_COMMANDEAENCAISSER + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_COMMANDE_CODE +"= '"+commandeCode+"'" ;
        db.execSQL(req);
    }


    public void deleteCmdSynchronisee() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefCmdAS = sdf.format(new Date());
        String Where = " "+KEY_COMMENTAIRE+"='commande encaissee' and date("+KEY_DATE_CREATION+")<>date('"+DatefCmdAS+"') ";

        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_COMMANDEAENCAISSER, Where, null);
        db.close();
        Log.d(TAG, "Deleted all commandes verifiee from sqlite");
        Log.d(TAG, "deleteCmdSynchronisee: "+Where);
    }

    public ArrayList<CommandeAEncaisser> getList() {
        ArrayList<CommandeAEncaisser> commandeAEncaissers = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDEAENCAISSER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeAEncaisser cmd = new CommandeAEncaisser();
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

                commandeAEncaissers.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching commande a livrer from table commandeAEncaissers: ");
        return commandeAEncaissers;
    }


    public ArrayList<CommandeAEncaisser> getListByClientCode(String client_code) {
        ArrayList<CommandeAEncaisser> commandeAEncaissers = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDEAENCAISSER +" WHERE "+ KEY_CLIENT_CODE +" = '"+client_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeAEncaisser cmd = new CommandeAEncaisser();
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


                commandeAEncaissers.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande a livrer from table commandeAEncaissers: "+commandeAEncaissers.toString());
        return commandeAEncaissers;
    }


    public CommandeAEncaisser getCmdAEncaisserByCmdCode(String commande_code) {
        CommandeAEncaisser cmd = new CommandeAEncaisser();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMANDEAENCAISSER +" WHERE "+ KEY_COMMANDE_CODE +" = '"+commande_code+"'";
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
        Log.d(TAG, "Fetching Commande a livrer from table commandeAEncaisser: ");
        return cmd;
    }

    public void deleteCommandes() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_COMMANDEAENCAISSER, null, null);
        db.close();
        Log.d(TAG, "Deleted all commandesAEncaisser info from sqlite");
    }


    public static void synchronisationCommandeALivrer(final Context context){

        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_COMMANDEAENCAISSER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ouvrir le logManager
                Log.d("CommandeAEncaisser ", ""+response);
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray CommandeAEncaisser = jObj.getJSONArray("CommandeAEncaisser");
                        Toast.makeText(context, "Nombre de CommandeAEncaisser " +CommandeAEncaisser.length(), Toast.LENGTH_SHORT).show();
                        int cptInsert = 0,cptDeleted = 0;
                        //Ajout/Suppression/modification des articles dans la base de données.
                        for (int i = 0; i < CommandeAEncaisser.length(); i++) {
                            JSONObject uneCommandeAEncaisser = CommandeAEncaisser.getJSONObject(i);
                            CommandeAEncaisserManager commandeAEncaisserManager = new CommandeAEncaisserManager(context);
                            if(uneCommandeAEncaisser.getString("OPERATION").equals("DELETE")){
                                commandeAEncaisserManager.delete(uneCommandeAEncaisser.getString("COMMANDE_CODE"));
                                cptDeleted++;
                            }
                            else {
                                commandeAEncaisserManager.add(new CommandeAEncaisser(uneCommandeAEncaisser));
                                cptInsert++;
                            }
                        }
                        logM.add("CommandeAEncaisser:OK Insert:"+cptInsert +"Deleted:"+cptDeleted ,"SyncCommandeAEncaisser");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        logM.add("CommandeAEncaisser:NOK Insert onResponseError"+errorMsg ,"SyncCommandeAEncaisser");
                        Toast.makeText(context,"CommandeAEncaisser:"+errorMsg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    logM.add("CommandeAEncaisser : NOK Insert "+e.getMessage() ,"SyncCommandeAEncaisser");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "CommandeAEncaisser:"+error.getMessage(), Toast.LENGTH_LONG).show();
                LogSyncManager logM= new LogSyncManager(context);
                logM.add("CommandeAEncaisser : NOK Inserted ErrorListener"+error.getMessage() ,"SyncCommandeAEncaisser");

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> arrayFinale= new HashMap<>();
                //Map<String, String> params = new HashMap<String, String>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    CommandeAEncaisserManager commandeAEncaisserManager  = new CommandeAEncaisserManager(context);
                    ArrayList<CommandeAEncaisser> commandeAEncaissers = new ArrayList<>();
                    commandeAEncaissers=commandeAEncaisserManager.getList();



                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(commandeAEncaissers));
                }
                return arrayFinale;

            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

}
