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
import com.newtech.newtech_sfm.Metier.CommandeLigneALivrer;

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
 * Created by stagiaireit2 on 02/08/2016.
 */
public class CommandeLigneALivrerManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_COMMANDE_LIGNEALIVRER = "commandelignealivrer";
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
            KEY_VERSION="VERSION",
            KEY_ID="ID";

   public static String CREATE_COMMANDE_LIGNEALIVRER_TABLE = "CREATE TABLE " + TABLE_COMMANDE_LIGNEALIVRER+ "("

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

    public CommandeLigneALivrerManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_COMMANDE_LIGNE);
            db.execSQL(CREATE_COMMANDE_LIGNEALIVRER_TABLE);
        } catch (SQLException e) {
        }
        Log.d(TAG, "table Commandeligne a livrer created"+CREATE_COMMANDE_LIGNEALIVRER_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMANDE_LIGNEALIVRER);
        onCreate(db);
    }

    public int delete(String commandeLigneCode ,String commandeCode ) {
        Log.d(TAG, "Deleted commandesLignes from sqlite : "+commandeLigneCode+" ; "+commandeCode);
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_COMMANDE_LIGNEALIVRER,KEY_COMMANDELIGNE_CODE+"=?  AND "+KEY_COMMANDE_CODE+"=?" ,new String[]{commandeLigneCode,commandeCode});

    }

    public void deleteCmdSynchronisee() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefCmdAS = sdf.format(new Date());
        String Where = " "+KEY_COMMENTAIRE+"='commande verifiee' and date("+KEY_DATE_CREATION+")!='"+DatefCmdAS+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_COMMANDE_LIGNEALIVRER, Where, null);
        db.close();
        Log.d(TAG, "Deleted all commandesLignes verifiee from sqlite");
    }

    public void deleteCommandesLignes() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_COMMANDE_LIGNEALIVRER, null, null);
        db.close();
        Log.d(TAG, "Deleted all commandesLignes info from sqlite");
    }
    public void add(CommandeLigneALivrer commandeLigneALivrer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(KEY_COMMANDELIGNE_CODE, commandeLigneALivrer.getCOMMANDELIGNE_CODE());
        values.put(KEY_COMMANDE_CODE, commandeLigneALivrer.getCOMMANDE_CODE());
        values.put(KEY_FACTURE_CODE, commandeLigneALivrer.getFACTURE_CODE());
        values.put(KEY_FAMILLE_CODE, commandeLigneALivrer.getFAMILLE_CODE());
        values.put(KEY_ARTICLE_CODE, commandeLigneALivrer.getARTICLE_CODE());
        values.put(KEY_ARTICLE_DESIGNATION, commandeLigneALivrer.getARTICLE_DESIGNATION());
        values.put(KEY_ARTICLE_NBUS_PAR_UP, commandeLigneALivrer.getARTICLE_NBUS_PAR_UP());
        values.put(KEY_ARTICLE_PRIX, commandeLigneALivrer.getARTICLE_PRIX());
        values.put(KEY_QTE_COMMANDEE, commandeLigneALivrer.getQTE_COMMANDEE());
        values.put(KEY_QTE_LIVREE, commandeLigneALivrer.getQTE_LIVREE());
        values.put(KEY_CAISSE_COMMANDEE, commandeLigneALivrer.getCAISSE_COMMANDEE());
        values.put(KEY_CAISSE_LIVREE, commandeLigneALivrer.getCAISSE_LIVREE());
        values.put(KEY_LITTRAGE_COMMANDEE, commandeLigneALivrer.getLITTRAGE_COMMANDEE());
        values.put(KEY_LITTRAGE_LIVREE, commandeLigneALivrer.getLITTRAGE_LIVREE());
        values.put(KEY_TONNAGE_COMMANDEE, commandeLigneALivrer.getTONNAGE_COMMANDEE());
        values.put(KEY_TONNAGE_LIVREE, commandeLigneALivrer.getTONNAGE_LIVREE());
        values.put(KEY_KG_COMMANDEE, commandeLigneALivrer.getKG_COMMANDEE());
        values.put(KEY_KG_LIVREE, commandeLigneALivrer.getKG_LIVREE());
        values.put(KEY_MONTANT_BRUT, commandeLigneALivrer.getMONTANT_BRUT());
        values.put(KEY_REMISE, commandeLigneALivrer.getREMISE());
        values.put(KEY_MONTANT_NET, commandeLigneALivrer.getMONTANT_NET());
        values.put(KEY_COMMENTAIRE, commandeLigneALivrer.getCOMMENTAIRE());
        values.put(KEY_CREATEUR_CODE, commandeLigneALivrer.getCREATEUR_CODE());
        values.put(KEY_DATE_CREATION, commandeLigneALivrer.getDATE_CREATION());
        values.put(KEY_UNITE, commandeLigneALivrer.getUNITE_CODE());
        values.put(KEY_VERSION, commandeLigneALivrer.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_COMMANDE_LIGNEALIVRER, null, values,SQLiteDatabase.CONFLICT_IGNORE);
        db.close(); // Closing database connection
        Log.d(TAG, "nouvelle commande ligne a livrer inseré dans la table Commandelignealivrer: " + id);
        Log.d(TAG, "add: commande ligne"+values);
    }

    public void updateCommandeLigne(String commandeligneCode,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_COMMANDE_LIGNEALIVRER + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_COMMANDE_CODE +"= '"+commandeligneCode+"'" ;
        db.execSQL(req);
    }
    public ArrayList<CommandeLigneALivrer> getList() {
        ArrayList<CommandeLigneALivrer> listCommandeLALivrer = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_COMMANDE_LIGNEALIVRER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "nb sal CommandeLigne: "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeLigneALivrer cmd = new CommandeLigneALivrer();

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

                listCommandeLALivrer.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande ligne a livrer from table CommandeLignealivrer: ");
        return listCommandeLALivrer;
    }

    public ArrayList<CommandeLigneALivrer> getListByArticleCode(String article_code) {
        ArrayList<CommandeLigneALivrer> listCommandeLALivrer = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_COMMANDE_LIGNEALIVRER+" WHERE "+KEY_ARTICLE_CODE +"= '"+article_code+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "nb sal CommandeLigne: "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeLigneALivrer cmd = new CommandeLigneALivrer();

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
                listCommandeLALivrer.add(cmd);

            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande ligne a livrer from table CommandeLigne: ");
        return listCommandeLALivrer;
    }

    public ArrayList<CommandeLigneALivrer> getListByCommandeCode(String commande_code) {
        ArrayList<CommandeLigneALivrer> listCommandeLALivrer = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_COMMANDE_LIGNEALIVRER +" WHERE "+KEY_COMMANDE_CODE +"= '"+commande_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "nb sal CommandeLigneALivree: "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeLigneALivrer cmd = new CommandeLigneALivrer();

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

                listCommandeLALivrer.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande ligne a livrer from table CommandeLignealivrer: "+listCommandeLALivrer);
        return listCommandeLALivrer;
    }

    public ArrayList<CommandeLigneALivrer> getListByCommandeCodeQl(String commande_code) {
        ArrayList<CommandeLigneALivrer> listCommandeLALivrer = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_COMMANDE_LIGNEALIVRER +" WHERE "+KEY_COMMANDE_CODE +"= '"+commande_code+"' AND "+KEY_QTE_LIVREE+" < "+KEY_QTE_COMMANDEE + " AND "+KEY_QTE_LIVREE+">= 0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "nb sal CommandeLigneALivree: "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeLigneALivrer cmd = new CommandeLigneALivrer();

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

                listCommandeLALivrer.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande ligne a livrer from table CommandeLignealivrer: "+listCommandeLALivrer);
        return listCommandeLALivrer;
    }


    ///////////////////////////////////////////////SYNCHRONISATION COMMENDELIGNE A LIVRER/////////////////////////////////////////////

    public static void synchronisationCommandeLigneALivrer(final Context context){

        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_COMMANDE_LIGNEALIVRER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ouvrir le logManager
                Log.d("CommandeLigneALivrer ", ""+response);
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray CommandeLigneALivrer = jObj.getJSONArray("CommandeLigneALivrer");
                        Toast.makeText(context, "Nombre de CommandeLigneALivrer " +CommandeLigneALivrer.length(), Toast.LENGTH_SHORT).show();
                        int cptInsert = 0,cptDeleted = 0;
                        //Ajout/Suppression/modification des articles dans la base de données.
                        for (int i = 0; i < CommandeLigneALivrer.length(); i++) {
                            JSONObject uneCommandeLigneALivrer = CommandeLigneALivrer.getJSONObject(i);
                            CommandeLigneALivrerManager commandeLigneALivrerManager = new CommandeLigneALivrerManager(context);
                            if(uneCommandeLigneALivrer.getString("OPERATION").equals("DELETE")){
                                commandeLigneALivrerManager.delete(uneCommandeLigneALivrer.getString("COMMANDELIGNE_CODE"),uneCommandeLigneALivrer.getString("COMMANDE_CODE"));
                                cptDeleted++;
                            }
                            else {
                                Log.d("onrcmdligne", "onResponse: comdligne"+uneCommandeLigneALivrer.toString());
                                commandeLigneALivrerManager.add(new CommandeLigneALivrer(uneCommandeLigneALivrer));
                                cptInsert++;
                            }
                        }
                        logM.add("CommandeLigneALivrer:OK Insert:"+cptInsert +"Deleted:"+cptDeleted ,"SyncArticles");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        logM.add("CommandeLigneALivrer :NOK Insert "+errorMsg ,"SyncCommandeLigneALivrer");
                        Toast.makeText(context,"CommandeLigneALivrer:"+errorMsg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    logM.add("CommandeLigneALivrer : NOK Insert "+e.getMessage() ,"SyncCommandeLigneALivrer");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "CommandeLigneALivrer:"+error.getMessage(), Toast.LENGTH_LONG).show();
                LogSyncManager logM= new LogSyncManager(context);
                logM.add("CommandeLigneALivrer : NOK Inserted "+error.getMessage() ,"SyncCommandeLigneALivrer");

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


                    CommandeLigneALivrerManager commandeLigneALivrerManager = new CommandeLigneALivrerManager(context);
                    ArrayList<CommandeLigneALivrer> commandeLigneALivrers = new ArrayList<>();
                    commandeLigneALivrers=commandeLigneALivrerManager.getList();

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(commandeLigneALivrers));
                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}