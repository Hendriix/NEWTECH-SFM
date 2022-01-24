package com.newtech.newtech_sfm.Metier_Manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier.LogSync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stagiaireit2 on 02/08/2016.
 */
public class CommandeLigneManager  extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_COMMANDE_LIGNE = "commandeligne";
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
            KEY_ID="ID",
            KEY_SOURCE="SOURCE",
            KEY_VERSION="VERSION";

   public static String CREATE_COMMANDE_LIGNE_TABLE = "CREATE TABLE " + TABLE_COMMANDE_LIGNE+ "("

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
            +KEY_UNITE  + " TEXT  ,"
            +KEY_SOURCE + " TEXT ,"
            +KEY_VERSION  + " TEXT ,"
            +"PRIMARY KEY ("+KEY_COMMANDE_CODE+","+KEY_COMMANDELIGNE_CODE+"))";

    public CommandeLigneManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_COMMANDE_LIGNE);
            db.execSQL(CREATE_COMMANDE_LIGNE_TABLE);
        } catch (SQLException e) {
        }
        Log.d(TAG, "table Commandeligne created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMANDE_LIGNE);
        onCreate(db);
    }

    public int delete(String commandeLigneCode ,String commandeCode ) {
        Log.d(TAG, "Deleted commandesLignes from sqlite : "+commandeLigneCode+" ; "+commandeCode);
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_COMMANDE_LIGNE,KEY_COMMANDELIGNE_CODE+"=?  AND "+KEY_COMMANDE_CODE+"=?" ,new String[]{commandeLigneCode,commandeCode});

    }

    public void deleteCmdLSynchronisee() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefCmdAS = sdf.format(new Date());

        Log.d(TAG, "deleteCmdSynchronisee: "+DatefCmdAS);

        String Where = " "+KEY_COMMENTAIRE+"='inserted' and date("+KEY_DATE_CREATION+")!='"+DatefCmdAS+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_COMMANDE_LIGNE, Where, null);
        db.close();
        Log.d(TAG, "Deleted all commandesLignes verifiee from sqlite");
    }

    public int deleteByCmdCode(String commandeCode ) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_COMMANDE_LIGNE,KEY_COMMANDE_CODE+"=?" ,new String[]{commandeCode});

    }

    public void deleteCommandesLignes() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_COMMANDE_LIGNE, null, null);
        db.close();
        Log.d(TAG, "Deleted all commandesLignes info from sqlite");
    }
    public void add(CommandeLigne commandeLigne) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(KEY_COMMANDELIGNE_CODE, commandeLigne.getCOMMANDELIGNE_CODE());
        values.put(KEY_COMMANDE_CODE, commandeLigne.getCOMMANDE_CODE());
        values.put(KEY_FACTURE_CODE, commandeLigne.getFACTURE_CODE());
        values.put(KEY_FAMILLE_CODE, commandeLigne.getFAMILLE_CODE());
        values.put(KEY_ARTICLE_CODE, commandeLigne.getARTICLE_CODE());
        values.put(KEY_ARTICLE_DESIGNATION, commandeLigne.getARTICLE_DESIGNATION());
        values.put(KEY_ARTICLE_NBUS_PAR_UP, commandeLigne.getARTICLE_NBUS_PAR_UP());
        values.put(KEY_ARTICLE_PRIX, commandeLigne.getARTICLE_PRIX());
        values.put(KEY_QTE_COMMANDEE, commandeLigne.getQTE_COMMANDEE());
        values.put(KEY_QTE_LIVREE, commandeLigne.getQTE_LIVREE());
        values.put(KEY_CAISSE_COMMANDEE, commandeLigne.getCAISSE_COMMANDEE());
        values.put(KEY_CAISSE_LIVREE, commandeLigne.getCAISSE_LIVREE());
        values.put(KEY_LITTRAGE_COMMANDEE, commandeLigne.getLITTRAGE_COMMANDEE());
        values.put(KEY_LITTRAGE_LIVREE, commandeLigne.getLITTRAGE_LIVREE());
        values.put(KEY_TONNAGE_COMMANDEE, commandeLigne.getTONNAGE_COMMANDEE());
        values.put(KEY_TONNAGE_LIVREE, commandeLigne.getTONNAGE_LIVREE());
        values.put(KEY_KG_COMMANDEE, commandeLigne.getKG_COMMANDEE());
        values.put(KEY_KG_LIVREE, commandeLigne.getKG_LIVREE());
        values.put(KEY_MONTANT_BRUT, getNumberRounded(commandeLigne.getMONTANT_BRUT()));
        values.put(KEY_REMISE, getNumberRounded(commandeLigne.getREMISE()));
        values.put(KEY_MONTANT_NET, getNumberRounded(commandeLigne.getMONTANT_NET()));
        values.put(KEY_COMMENTAIRE, commandeLigne.getCOMMENTAIRE());
        values.put(KEY_CREATEUR_CODE, commandeLigne.getCREATEUR_CODE());
        values.put(KEY_DATE_CREATION, commandeLigne.getDATE_CREATION());
        values.put(KEY_UNITE, commandeLigne.getUNITE_CODE());
        values.put(KEY_SOURCE, commandeLigne.getSOURCE());
        values.put(KEY_VERSION, commandeLigne.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_COMMANDE_LIGNE, null, values,SQLiteDatabase.CONFLICT_IGNORE);
        db.close(); // Closing database connection
        Log.d(TAG, "nouvelle commandeLigne inseré dans la table Commandeligne: " + id);
        Log.d(TAG, "add: commande ligne"+values);
    }

    public void updateCommandeLigne(String commande_code,String commandeligneCode,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_COMMANDE_LIGNE + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_COMMANDE_CODE +"= '"+commande_code+"' AND "+KEY_COMMANDELIGNE_CODE+"= '"+commandeligneCode+"'" ;
        db.execSQL(req);
        db.close();
        Log.d("commandeligne", "updateCommandeligne: "+req);
    }

    public ArrayList<CommandeLigne> getList() {
        ArrayList<CommandeLigne> listCommandeL = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_COMMANDE_LIGNE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "nb sal CommandeLigne: "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeLigne cmd = new CommandeLigne();

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
                cmd.setSOURCE(cursor.getString(25));
                cmd.setVERSION(cursor.getString(26));

                listCommandeL.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande ligne from table CommandeLigne: ");
        return listCommandeL;
    }

    public ArrayList<CommandeLigne> getListNotInserted() {
        ArrayList<CommandeLigne> listCommandeL = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_COMMANDE_LIGNE +" WHERE "+KEY_COMMENTAIRE+" = 'to_insert' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "nb sal CommandeLigne: "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeLigne cmd = new CommandeLigne();

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
                cmd.setSOURCE(cursor.getString(25));
                cmd.setVERSION(cursor.getString(26));

                listCommandeL.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande ligne from table CommandeLigne: ");
        return listCommandeL;
    }

    public ArrayList<CommandeLigne> getListNonVerifie() {
        ArrayList<CommandeLigne> listCommandeL = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_COMMANDE_LIGNE +" WHERE "+KEY_COMMENTAIRE+"!= 'commandeligne verifiee' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "nb sal CommandeLigne: "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeLigne cmd = new CommandeLigne();

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
                cmd.setSOURCE(cursor.getString(25));
                cmd.setVERSION(cursor.getString(26));

                listCommandeL.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande ligne Non verif from table CommandeLigne: ");
        return listCommandeL;
    }

    public ArrayList<CommandeLigne> getListByArticleCode(String article_code) {
        ArrayList<CommandeLigne> listCommandeL = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_COMMANDE_LIGNE+" WHERE "+KEY_ARTICLE_CODE +"= '"+article_code+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "nb sal CommandeLigne: "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeLigne cmd = new CommandeLigne();

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
                cmd.setSOURCE(cursor.getString(25));
                cmd.setVERSION(cursor.getString(26));

                listCommandeL.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande ligne from table CommandeLigne: ");
        return listCommandeL;
    }

    public ArrayList<CommandeLigne> getListByCommandeCode(String commande_code) {
        ArrayList<CommandeLigne> listCommandeL = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_COMMANDE_LIGNE +" WHERE "+KEY_COMMANDE_CODE +"= '"+commande_code+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "nb sal CommandeLigne: "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                CommandeLigne cmd = new CommandeLigne();

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
                cmd.setSOURCE(cursor.getString(25));
                cmd.setVERSION(cursor.getString(26));

                listCommandeL.add(cmd);
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Commande ligne from table CommandeLigne: "+listCommandeL);
        return listCommandeL;
    }

    public void updateCmdLigneCode(ArrayList<CommandeLigne> commandeLignes){

        for(int i =0;i<commandeLignes.size();i++){
            commandeLignes.get(i).setCOMMANDELIGNE_CODE(i+1);
        }
    }

    public class VenteArticle{

        private String ARTICLE_CODE ;
        private int NOMBRE_BOUTEILLE;
        private double QUANTITE_COMMANDEE;
        private String UNITE_CODE;


        public VenteArticle(){

        }

        public String getARTICLE_CODE() {
            return ARTICLE_CODE;
        }

        public int getNOMBRE_BOUTEILLE() {
            return NOMBRE_BOUTEILLE;
        }

        public double getQUANTITE_COMMANDEE() {
            return QUANTITE_COMMANDEE;
        }

        public void setARTICLE_CODE(String ARTICLE_CODE) {
            this.ARTICLE_CODE = ARTICLE_CODE;
        }

        public void setNOMBRE_BOUTEILLE(int NOMBRE_BOUTEILLE) {
            this.NOMBRE_BOUTEILLE = NOMBRE_BOUTEILLE;
        }

        public void setQUANTITE_COMMANDEE(double QUANTITE_COMMANDEE) {
            this.QUANTITE_COMMANDEE = QUANTITE_COMMANDEE;
        }

        public String getUNITE_CODE() {
            return UNITE_CODE;
        }

        public void setUNITE_CODE(String UNITE_CODE) {
            this.UNITE_CODE = UNITE_CODE;
        }

        @Override
        public String toString() {
            return "VenteArticle{" +
                    "ARTICLE_CODE='" + ARTICLE_CODE + '\'' +
                    ", NOMBRE_BOUTEILLE=" + NOMBRE_BOUTEILLE +
                    ", QUANTITE_COMMANDEE=" + QUANTITE_COMMANDEE +
                    ", UNITE_CODE='" + UNITE_CODE + '\'' +
                    '}';
        }
    }

    public Map<String,Double>getReaArticlesParDate(String date){

        Map<String,Double> reapararticle=new HashMap<String,Double>();
        String selectQuery = "SELECT article.ARTICLE_CODE,Sum(commandeligne.QTE_LIVREE) as SQTEL FROM commandeligne inner join article on article.ARTICLE_CODE=commandeligne.ARTICLE_CODE "+
                " where date(commandeligne.DATE_CREATION)='"+date+"'  group by article.ARTICLE_CODE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                reapararticle.put(cursor.getString(0),cursor.getDouble(1));
            }while(cursor.moveToNext());
        }
        //retourner la listArticles;
        cursor.close();
        db.close();

        return reapararticle;
    }

    public ArrayList<VenteArticle> getRealisationArticleParDate(String date){


        ArrayList<VenteArticle> list = new ArrayList<>();
        String selectQuery = "SELECT article.ARTICLE_CODE,Sum(commandeligne.QTE_COMMANDEE) as SQTEL,commandeligne.ARTICLE_NBUS_PAR_UP FROM commandeligne inner join article on article.ARTICLE_CODE=commandeligne.ARTICLE_CODE "+
                " where date(commandeligne.DATE_CREATION)='"+date+"'  group by article.ARTICLE_CODE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d("ListeCommandeLigne1", "getRealisationArticleParDate: "+selectQuery);
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                VenteArticle va = new VenteArticle();
                va.setARTICLE_CODE(cursor.getString(0));
                va.setQUANTITE_COMMANDEE(cursor.getDouble(1));
                va.setNOMBRE_BOUTEILLE(cursor.getInt(2));
                list.add(va);
                Log.d("ListeCommandeLigne1", "getRealisationArticleParDateUnite: "+va.toString());

            }while(cursor.moveToNext());
        }

        Log.d("ListeCommandeLigne1", "getRealisationArticleParDateUnite: "+list.toString());
        return list;
    }

    public ArrayList<VenteArticle> getRealisationArticleParDateUnite(String date){

        ArrayList<VenteArticle> list = new ArrayList<>();
        String selectQuery = "SELECT article.ARTICLE_CODE,Sum(commandeligne.QTE_COMMANDEE) as SQTEL,commandeligne.ARTICLE_NBUS_PAR_UP,commandeligne.UNITE_CODE FROM commandeligne inner join article on article.ARTICLE_CODE=commandeligne.ARTICLE_CODE "+
                " where date(commandeligne.DATE_CREATION)='"+date+"'  group by article.ARTICLE_CODE,commandeligne.UNITE_CODE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d("ListeCommandeLigne", "getRealisationArticleParDate: "+selectQuery);
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                VenteArticle va = new VenteArticle();
                va.setARTICLE_CODE(cursor.getString(0));
                va.setQUANTITE_COMMANDEE(cursor.getDouble(1));
                va.setNOMBRE_BOUTEILLE(cursor.getInt(2));
                va.setUNITE_CODE(cursor.getString(3));
                list.add(va);
                Log.d("ListeCommandeLigne", "getRealisationArticleParDateUnite: "+va.toString());
            }while(cursor.moveToNext());
        }

        Log.d("ListeCommandeLigne", "getRealisationArticleParDateUnite: "+list.toString());
        return list;
    }

    public static void synchronisationCommandeLigne(final Context context){

        CommandeLigneManager commandeLigneManager = new CommandeLigneManager(context);
        commandeLigneManager.deleteCmdLSynchronisee();

        String tag_string_req = "COMMANDE_LIGNE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_COMMANDE_LIGNE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("commandeLignesync ", ""+response);
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                    try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray commandesLignes = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre de commandesLignes  "+commandesLignes.length() , Toast.LENGTH_LONG).show();
                        // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les Commande Lignes : " +commandesLignes.toString());

                        if(commandesLignes.length()>0) {
                            CommandeLigneManager commandeLigneManager = new CommandeLigneManager(context);
                            for (int i = 0; i < commandesLignes.length(); i++) {
                                JSONObject uneCommandeLigne = commandesLignes.getJSONObject(i);

                                if (uneCommandeLigne.getString("Statut").equals("true")) {
                                    commandeLigneManager.updateCommandeLigne((uneCommandeLigne.getString("COMMANDE_CODE")),(uneCommandeLigne.getString("COMMANDELIGNE_CODE")), "inserted");
                                    cptInsert++;
                                }else{
                                    cptDelete++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE_LIGNE: Inserted: "+cptInsert +" NotInserted: "+cptDelete ,"COMMANDE_LIGNE",1));

                        }

                    }else{
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "commandeligne error != 1: "+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE_LIGNE: Error: "+errorMsg,"COMMANDE_LIGNE",0));

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE_LIGNE: Error: "+e.getMessage(),"COMMANDE_LIGNE",0));

                        }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"commandeligne onError: "+error.getMessage(), Toast.LENGTH_LONG).show();
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("COMMANDE_LIGNE: Error: "+error.getMessage(),"COMMANDE_LIGNE",0));

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    CommandeLigneManager commandeLigneManager  = new CommandeLigneManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableCommandeLigne");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(commandeLigneManager.getListNotInserted()));
                    Log.d(TAG, "getParams: listnotinserted"+commandeLigneManager.getListNotInserted().size());
                    /*for(int i=0;i<commandeLigneManager.getList().size();i++){
                        Log.d("PROBLEME", "OBJET COMMANDE LIGNE: "+commandeLigneManager.getListNonVerifie().get(i));
                    }

                    Log.d("CommandeLignesynchsend", "Liste: "+commandeLigneManager.getList());*/


                }
                return arrayFinale;
            }
        };
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

    public ArrayList<CommandeLigne> fixCmdLigneCode(ArrayList<CommandeLigne> commandeLignes){

        ArrayList<CommandeLigne> commandeLignes1 = commandeLignes;

        for(int i=0 ; i<commandeLignes1.size(); i++){

            commandeLignes1.get(i).setCOMMANDELIGNE_CODE(i+1);
        }

        return commandeLignes1;
    }


}