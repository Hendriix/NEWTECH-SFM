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
import com.newtech.newtech_sfm.Metier.CommandeLigneAEncaisser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TONPC on 15/08/2017.
 */

public class CommandeLigneAEncaisserManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_COMMANDE_LIGNEAENCAISSER = "commandeligneaencaisser";


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
            KEY_VERSION="VERSION";

    public static String CREATE_COMMANDE_LIGNEAENCAISSER_TABLE = "CREATE TABLE " + TABLE_COMMANDE_LIGNEAENCAISSER+ "("

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
            +KEY_VERSION  + " TEXT  "+")";


    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_COMMANDE_LIGNE);
            db.execSQL(CREATE_COMMANDE_LIGNEAENCAISSER_TABLE);
        } catch (SQLException e) {
        }
        Log.d(TAG, "table Commandeligne a Encaisser created"+CREATE_COMMANDE_LIGNEAENCAISSER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMANDE_LIGNEAENCAISSER);
        onCreate(db);

    }

    public CommandeLigneAEncaisserManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public int delete(String commandeLigneCode ,String commandeCode ) {
        Log.d(TAG, "Deleted commandesLignesaencaisser from sqlite : "+commandeLigneCode+" ; "+commandeCode);
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_COMMANDE_LIGNEAENCAISSER,KEY_COMMANDELIGNE_CODE+"=?  AND "+KEY_COMMANDE_CODE+"=?" ,new String[]{commandeLigneCode,commandeCode});

    }

    public void deleteCmdSynchronisee() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefCmdAS = sdf.format(new Date());
        String Where = " "+KEY_COMMENTAIRE+"='commande encaissee' and date("+KEY_DATE_CREATION+")!='"+DatefCmdAS+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_COMMANDE_LIGNEAENCAISSER, Where, null);
        db.close();
        Log.d(TAG, "Deleted all commandesLignes encaissees from sqlite");
    }

    public void deleteCommandesLignes() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_COMMANDE_LIGNEAENCAISSER, null, null);
        db.close();
        Log.d(TAG, "Deleted all commandesLignes a encaisser info from sqlite");
    }

    public void add(CommandeLigneAEncaisser commandeLigneAEncaisser) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(KEY_COMMANDELIGNE_CODE, commandeLigneAEncaisser.getCOMMANDELIGNE_CODE());
        values.put(KEY_COMMANDE_CODE, commandeLigneAEncaisser.getCOMMANDE_CODE());
        values.put(KEY_FACTURE_CODE, commandeLigneAEncaisser.getFACTURE_CODE());
        values.put(KEY_FAMILLE_CODE, commandeLigneAEncaisser.getFAMILLE_CODE());
        values.put(KEY_ARTICLE_CODE, commandeLigneAEncaisser.getARTICLE_CODE());
        values.put(KEY_ARTICLE_DESIGNATION, commandeLigneAEncaisser.getARTICLE_DESIGNATION());
        values.put(KEY_ARTICLE_NBUS_PAR_UP, commandeLigneAEncaisser.getARTICLE_NBUS_PAR_UP());
        values.put(KEY_ARTICLE_PRIX, commandeLigneAEncaisser.getARTICLE_PRIX());
        values.put(KEY_QTE_COMMANDEE, commandeLigneAEncaisser.getQTE_COMMANDEE());
        values.put(KEY_QTE_LIVREE, commandeLigneAEncaisser.getQTE_LIVREE());
        values.put(KEY_CAISSE_COMMANDEE, commandeLigneAEncaisser.getCAISSE_COMMANDEE());
        values.put(KEY_CAISSE_LIVREE, commandeLigneAEncaisser.getCAISSE_LIVREE());
        values.put(KEY_LITTRAGE_COMMANDEE, commandeLigneAEncaisser.getLITTRAGE_COMMANDEE());
        values.put(KEY_LITTRAGE_LIVREE, commandeLigneAEncaisser.getLITTRAGE_LIVREE());
        values.put(KEY_TONNAGE_COMMANDEE, commandeLigneAEncaisser.getTONNAGE_COMMANDEE());
        values.put(KEY_TONNAGE_LIVREE, commandeLigneAEncaisser.getTONNAGE_LIVREE());
        values.put(KEY_KG_COMMANDEE, commandeLigneAEncaisser.getKG_COMMANDEE());
        values.put(KEY_KG_LIVREE, commandeLigneAEncaisser.getKG_LIVREE());
        values.put(KEY_MONTANT_BRUT, commandeLigneAEncaisser.getMONTANT_BRUT());
        values.put(KEY_REMISE, commandeLigneAEncaisser.getREMISE());
        values.put(KEY_MONTANT_NET, commandeLigneAEncaisser.getMONTANT_NET());
        values.put(KEY_COMMENTAIRE, commandeLigneAEncaisser.getCOMMENTAIRE());
        values.put(KEY_CREATEUR_CODE, commandeLigneAEncaisser.getCREATEUR_CODE());
        values.put(KEY_DATE_CREATION, commandeLigneAEncaisser.getDATE_CREATION());
        values.put(KEY_UNITE, commandeLigneAEncaisser.getUNITE_CODE());
        values.put(KEY_VERSION, commandeLigneAEncaisser.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_COMMANDE_LIGNEAENCAISSER, null, values,SQLiteDatabase.CONFLICT_IGNORE);
        db.close(); // Closing database connection
        Log.d(TAG, "nouvelle commande ligne a encaisser inseré dans la table Commandeligneaencaisser: " + id);
        Log.d(TAG, "add: commande ligne a encaisser"+values);
    }

    public void updateCommandeLigne(String commandeligneCode,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_COMMANDE_LIGNEAENCAISSER + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_COMMANDE_CODE +"= '"+commandeligneCode+"'" ;
        db.execSQL(req);
    }

    public ArrayList<CommandeLigneAEncaisser> getList() {
        ArrayList<CommandeLigneAEncaisser> commandeLigneAEncaissers = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_COMMANDE_LIGNEAENCAISSER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "nb sal CommandeLigne a encaisser: "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeLigneAEncaisser cmd = new CommandeLigneAEncaisser();

                cmd.setCOMMANDELIGNE_CODE(cursor.getInt(0));
                cmd.setCOMMANDE_CODE(cursor.getString(1));
                cmd.setFACTURE_CODE(cursor.getString(2));
                cmd.setFAMILLE_CODE(cursor.getString(3));
                cmd.setARTICLE_CODE(cursor.getString(4));
                cmd.setARTICLE_DESIGNATION(cursor.getString(5));
                cmd.setARTICLE_NBUS_PAR_UP((cursor.getDouble(6)));
                cmd.setARTICLE_PRIX(cursor.getDouble(7));
                cmd.setQTE_COMMANDEE(cursor.getDouble(8));
                cmd.setQTE_LIVREE(cursor.getDouble(9));
                cmd.setCAISSE_COMMANDEE(cursor.getDouble(10));
                cmd.setCAISSE_LIVREE(cursor.getDouble(11));
                cmd.setLITTRAGE_COMMANDEE(cursor.getDouble(12));
                cmd.setLITTRAGE_LIVREE(cursor.getDouble(13));
                cmd.setTONNAGE_COMMANDEE(cursor.getDouble(14));
                cmd.setTONNAGE_LIVREE(cursor.getDouble(15));
                cmd.setKG_COMMANDEE(cursor.getDouble(16));
                cmd.setKG_LIVREE(cursor.getDouble(17));
                cmd.setMONTANT_BRUT(cursor.getDouble(18));
                cmd.setREMISE(cursor.getDouble(19));
                cmd.setMONTANT_NET(cursor.getDouble(20));
                cmd.setCOMMENTAIRE(cursor.getString(21));
                cmd.setCREATEUR_CODE(cursor.getString(22));
                cmd.setDATE_CREATION(cursor.getString(23));
                cmd.setUNITE_CODE(cursor.getString(24));
                cmd.setVERSION(cursor.getString(25));

                commandeLigneAEncaissers.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande ligne a encaisser from table CommandeLigneaencaisser: ");
        return commandeLigneAEncaissers;
    }

    public ArrayList<CommandeLigneAEncaisser> getListByArticleCode(String article_code) {
        ArrayList<CommandeLigneAEncaisser> commandeLigneAEncaissers = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_COMMANDE_LIGNEAENCAISSER+" WHERE "+KEY_ARTICLE_CODE +"= '"+article_code+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "nb sal CommandeLigne a encaisser: "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeLigneAEncaisser cmd = new CommandeLigneAEncaisser();

                cmd.setCOMMANDELIGNE_CODE(cursor.getInt(0));
                cmd.setCOMMANDE_CODE(cursor.getString(1));
                cmd.setFACTURE_CODE(cursor.getString(2));
                cmd.setFAMILLE_CODE(cursor.getString(3));
                cmd.setARTICLE_CODE(cursor.getString(4));
                cmd.setARTICLE_DESIGNATION(cursor.getString(5));
                cmd.setARTICLE_NBUS_PAR_UP((cursor.getDouble(6)));
                cmd.setARTICLE_PRIX(cursor.getDouble(7));
                cmd.setQTE_COMMANDEE(cursor.getDouble(8));
                cmd.setQTE_LIVREE(cursor.getDouble(9));
                cmd.setCAISSE_COMMANDEE(cursor.getDouble(10));
                cmd.setCAISSE_LIVREE(cursor.getDouble(11));
                cmd.setLITTRAGE_COMMANDEE(cursor.getDouble(12));
                cmd.setLITTRAGE_LIVREE(cursor.getDouble(13));
                cmd.setTONNAGE_COMMANDEE(cursor.getDouble(14));
                cmd.setTONNAGE_LIVREE(cursor.getDouble(15));
                cmd.setKG_COMMANDEE(cursor.getDouble(16));
                cmd.setKG_LIVREE(cursor.getDouble(17));
                cmd.setMONTANT_BRUT(cursor.getDouble(18));
                cmd.setREMISE(cursor.getDouble(19));
                cmd.setMONTANT_NET(cursor.getDouble(20));
                cmd.setCOMMENTAIRE(cursor.getString(21));
                cmd.setCREATEUR_CODE(cursor.getString(22));
                cmd.setDATE_CREATION(cursor.getString(23));
                cmd.setUNITE_CODE(cursor.getString(24));
                cmd.setVERSION(cursor.getString(25));
                commandeLigneAEncaissers.add(cmd);

            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande ligne a encaisser from table CommandeLigneaencaisser: ");
        return commandeLigneAEncaissers;
    }

    public ArrayList<CommandeLigneAEncaisser> getListByCommandeCode(String commande_code) {
        ArrayList<CommandeLigneAEncaisser> commandeLigneAEncaissers = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_COMMANDE_LIGNEAENCAISSER +" WHERE "+KEY_COMMANDE_CODE +"= '"+commande_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "nb sal CommandeLigneALivree: "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeLigneAEncaisser cmd = new CommandeLigneAEncaisser();

                cmd.setCOMMANDELIGNE_CODE(cursor.getInt(0));
                cmd.setCOMMANDE_CODE(cursor.getString(1));
                cmd.setFACTURE_CODE(cursor.getString(2));
                cmd.setFAMILLE_CODE(cursor.getString(3));
                cmd.setARTICLE_CODE(cursor.getString(4));
                cmd.setARTICLE_DESIGNATION(cursor.getString(5));
                cmd.setARTICLE_NBUS_PAR_UP((cursor.getDouble(6)));
                cmd.setARTICLE_PRIX(cursor.getDouble(7));
                cmd.setQTE_COMMANDEE(cursor.getDouble(8));
                cmd.setQTE_LIVREE(cursor.getDouble(9));
                cmd.setCAISSE_COMMANDEE(cursor.getDouble(10));
                cmd.setCAISSE_LIVREE(cursor.getDouble(11));
                cmd.setLITTRAGE_COMMANDEE(cursor.getDouble(12));
                cmd.setLITTRAGE_LIVREE(cursor.getDouble(13));
                cmd.setTONNAGE_COMMANDEE(cursor.getDouble(14));
                cmd.setTONNAGE_LIVREE(cursor.getDouble(15));
                cmd.setKG_COMMANDEE(cursor.getDouble(16));
                cmd.setKG_LIVREE(cursor.getDouble(17));
                cmd.setMONTANT_BRUT(cursor.getDouble(18));
                cmd.setREMISE(cursor.getDouble(19));
                cmd.setMONTANT_NET(cursor.getDouble(20));
                cmd.setCOMMENTAIRE(cursor.getString(21));
                cmd.setCREATEUR_CODE(cursor.getString(22));
                cmd.setDATE_CREATION(cursor.getString(23));
                cmd.setUNITE_CODE(cursor.getString(24));
                cmd.setVERSION(cursor.getString(25));

                commandeLigneAEncaissers.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande ligne a encaisser from table CommandeLigneaencaisser: "+commandeLigneAEncaissers);
        return commandeLigneAEncaissers;
    }


    public static void synchronisationCommandeLigneALivrer(final Context context){

        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_COMMANDE_LIGNEAENCAISSER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ouvrir le logManager
                Log.d("cmdliae ", ""+response);
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray CommandeLigneAEncaisser = jObj.getJSONArray("CommandeLigneAEncaisser");
                        Toast.makeText(context, "Nombre de CommandeLigneAEncaisser " +CommandeLigneAEncaisser.length(), Toast.LENGTH_SHORT).show();
                        int cptInsert = 0,cptDeleted = 0;
                        //Ajout/Suppression/modification des articles dans la base de données.
                        for (int i = 0; i < CommandeLigneAEncaisser.length(); i++) {
                            JSONObject uneCommandeLigneAEncaisser= CommandeLigneAEncaisser.getJSONObject(i);
                            CommandeLigneAEncaisserManager commandeLigneAEncaisserManager = new CommandeLigneAEncaisserManager(context);
                            if(uneCommandeLigneAEncaisser.getString("OPERATION").equals("DELETE")){
                                commandeLigneAEncaisserManager.delete(uneCommandeLigneAEncaisser.getString("COMMANDELIGNE_CODE"),uneCommandeLigneAEncaisser.getString("COMMANDE_CODE"));
                                cptDeleted++;
                            }
                            else {
                                Log.d("onrcmdligne", "onResponse: comdligne"+uneCommandeLigneAEncaisser.toString());
                                commandeLigneAEncaisserManager.add(new CommandeLigneAEncaisser(uneCommandeLigneAEncaisser));
                                cptInsert++;
                            }
                        }
                        logM.add("uneCommandeLigneAEncaisser:OK Insert:"+cptInsert +"Deleted:"+cptDeleted ,"SyncCommandeLigneAEncaisser");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        logM.add("uneCommandeLigneAEncaisser :NOK Insert "+errorMsg ,"SyncCommandeLigneAEncaisser");
                        Toast.makeText(context,"CommandeLigneAEncaisser:"+errorMsg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    logM.add("CommandeLigneAEncaisser : NOK Insert "+e.getMessage() ,"SyncCommandeLigneAEncaisser");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "CommandeLigneAEncaisser:"+error.getMessage(), Toast.LENGTH_LONG).show();
                LogSyncManager logM= new LogSyncManager(context);
                logM.add("CommandeLigneAEncaisser : NOK Inserted "+error.getMessage() ,"SyncCommandeLigneAEncaisser");

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


                    CommandeLigneAEncaisserManager commandeLigneAEncaisserManager = new CommandeLigneAEncaisserManager(context);
                    ArrayList<CommandeLigneAEncaisser> commandeLigneAEncaissers = new ArrayList<>();
                    commandeLigneAEncaissers=commandeLigneAEncaisserManager.getList();

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(commandeLigneAEncaissers));
                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

}
