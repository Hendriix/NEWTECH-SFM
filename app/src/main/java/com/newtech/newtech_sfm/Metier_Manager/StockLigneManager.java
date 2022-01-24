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
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier.LivraisonLigne;
import com.newtech.newtech_sfm.Metier.Parametre;
import com.newtech.newtech_sfm.Metier.StockLigne;
import com.newtech.newtech_sfm.Metier.Unite;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockLigneManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_STOCK_LIGNE = "stockligne";

    public static final String
            KEY_STOCKLIGNE_CODE = "STOCKLIGNE_CODE",
            KEY_STOCK_CODE = "STOCK_CODE",
            KEY_FAMILLE_CODE = "FAMILLE_CODE ",
            KEY_ARTICLE_CODE = "ARTICLE_CODE",
            KEY_ARTICLE_DESIGNATION = "ARTICLE_DESIGNATION",
            KEY_ARTICLE_NBUS_PAR_UP = "ARTICLE_NBUS_PAR_UP",
            KEY_ARTICLE_PRIX = "ARTICLE_PRIX",
            KEY_UNITE_CODE = "UNITE_CODE",
            KEY_QTE = "QTE",
            KEY_LITTRAGE = "LITTRAGE",
            KEY_TONNAGE = "TONNAGE",
            KEY_COMMENTAIRE = "COMMENTAIRE",
            KEY_CREATEUR_CODE = "CREATEUR_CODE",
            KEY_DATE_CREATION = "DATE_CREATION",
            KEY_TS = "TS",
            KEY_VERSION = "VERSION";

    public static String CREATE_STOCKLIGNE_TABLE = "CREATE TABLE " + TABLE_STOCK_LIGNE+ " ("

            +KEY_STOCKLIGNE_CODE +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +KEY_STOCK_CODE +" TEXT,"
            +KEY_FAMILLE_CODE +" TEXT,"
            +KEY_ARTICLE_CODE +" TEXT,"
            +KEY_ARTICLE_DESIGNATION +" TEXT,"
            +KEY_ARTICLE_NBUS_PAR_UP +" NUMERIC,"
            +KEY_ARTICLE_PRIX +" NUMERIC,"
            +KEY_UNITE_CODE +" TEXT,"
            +KEY_QTE +" NUMERIC,"
            +KEY_LITTRAGE +" NUMERIC,"
            +KEY_TONNAGE +" NUMERIC,"
            +KEY_COMMENTAIRE +" TEXT,"
            +KEY_CREATEUR_CODE +" TEXT,"
            +KEY_DATE_CREATION +" TEXT,"
            +KEY_TS +" TEXT,"
            +KEY_VERSION +" TEXT" + ")";



    public StockLigneManager(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAMILLE);
            db.execSQL(CREATE_STOCKLIGNE_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database STOCKLIGNE tables created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCK_LIGNE);
        // Create tables again
        onCreate(db);

    }

    public int delete(String stock_code, String stockligne_code, String version) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_STOCK_LIGNE,KEY_STOCK_CODE+"=? AND "+KEY_STOCKLIGNE_CODE+"=? AND "+KEY_VERSION+"=?",new String[]{stock_code,stockligne_code,version});
        return result;

    }

    public void deleteStockLigneZero() {

        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_STOCK_LIGNE + " WHERE "+ KEY_QTE +" = "+0.0+";";
        db.execSQL(deleteQuery);
        db.close();

    }

    public void add(StockLigne stockLigne) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //values.put(KEY_STOCKLIGNE_CODE,stockLigne.getSTOCKLIGNE_CODE());
        values.put(KEY_STOCK_CODE,stockLigne.getSTOCK_CODE());
        values.put(KEY_FAMILLE_CODE,stockLigne.getFAMILLE_CODE());
        values.put(KEY_ARTICLE_CODE,stockLigne.getARTICLE_CODE());
        values.put(KEY_ARTICLE_DESIGNATION,stockLigne.getARTICLE_DESIGNATION());
        values.put(KEY_ARTICLE_NBUS_PAR_UP,stockLigne.getARTICLE_NBUS_PAR_UP());
        values.put(KEY_ARTICLE_PRIX,stockLigne.getARTICLE_PRIX());
        values.put(KEY_UNITE_CODE,stockLigne.getUNITE_CODE());
        values.put(KEY_QTE,stockLigne.getQTE());
        values.put(KEY_LITTRAGE,stockLigne.getLITTRAGE());
        values.put(KEY_TONNAGE,stockLigne.getTONNAGE());
        values.put(KEY_COMMENTAIRE,stockLigne.getCOMMENTAIRE());
        values.put(KEY_CREATEUR_CODE,stockLigne.getCREATEUR_CODE());
        values.put(KEY_DATE_CREATION,stockLigne.getDATE_CREATION());
        values.put(KEY_TS,stockLigne.getTS());
        values.put(KEY_VERSION,stockLigne.getVERSION());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_STOCK_LIGNE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d("STOCKLIGNE MANAGER", "New stockligne inserted into sqlite: " + id);

    }

    public StockLigne get(String STOCK_CODE,Double STOCKLIGNE_CODE) {

        StockLigne stockLigne = new StockLigne();
        String selectQuery = "SELECT * FROM " + TABLE_STOCK_LIGNE+ " WHERE "+ KEY_STOCK_CODE +" = '"+STOCK_CODE+"' AND "+ KEY_STOCKLIGNE_CODE +" = '"+STOCKLIGNE_CODE+"';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            stockLigne.setSTOCKLIGNE_CODE(cursor.getInt(0));
            stockLigne.setSTOCK_CODE(cursor.getString(1));
            stockLigne.setFAMILLE_CODE(cursor.getString(2));
            stockLigne.setARTICLE_CODE(cursor.getString(3));
            stockLigne.setARTICLE_DESIGNATION(cursor.getString(4));
            stockLigne.setARTICLE_NBUS_PAR_UP(cursor.getInt(5));
            stockLigne.setARTICLE_PRIX(cursor.getDouble(6));
            stockLigne.setUNITE_CODE(cursor.getString(7));
            stockLigne.setQTE(cursor.getInt(8));
            stockLigne.setLITTRAGE(cursor.getDouble(9));
            stockLigne.setTONNAGE(cursor.getDouble(10));
            stockLigne.setCOMMENTAIRE(cursor.getString(11));
            stockLigne.setCREATEUR_CODE(cursor.getString(12));
            stockLigne.setDATE_CREATION(cursor.getString(13));
            stockLigne.setTS(cursor.getString(14));
            stockLigne.setVERSION(cursor.getString(15));
        }

        cursor.close();
        db.close();
        Log.d("STOCKLIGNE MANAGER", "fetching ");
        return stockLigne;

    }

    public StockLigne getByAcUc(String ARTICLE_CODE,String UNITE_CODE) {// BY ARTICLE_CODE ET UNITE_CODE

        StockLigne stockLigne = new StockLigne();
        String selectQuery = "SELECT * FROM " + TABLE_STOCK_LIGNE+ " WHERE "+ KEY_ARTICLE_CODE +" = '"+ARTICLE_CODE+"' AND "+ KEY_UNITE_CODE +" = '"+UNITE_CODE+"';";
        Log.d(TAG, "getByAcUc: "+selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            stockLigne.setSTOCKLIGNE_CODE(cursor.getInt(0));
            stockLigne.setSTOCK_CODE(cursor.getString(1));
            stockLigne.setFAMILLE_CODE(cursor.getString(2));
            stockLigne.setARTICLE_CODE(cursor.getString(3));
            stockLigne.setARTICLE_DESIGNATION(cursor.getString(4));
            stockLigne.setARTICLE_NBUS_PAR_UP(cursor.getInt(5));
            stockLigne.setARTICLE_PRIX(cursor.getDouble(6));
            stockLigne.setUNITE_CODE(cursor.getString(7));
            stockLigne.setQTE(cursor.getInt(8));
            stockLigne.setLITTRAGE(cursor.getDouble(9));
            stockLigne.setTONNAGE(cursor.getDouble(10));
            stockLigne.setCOMMENTAIRE(cursor.getString(11));
            stockLigne.setCREATEUR_CODE(cursor.getString(12));
            stockLigne.setDATE_CREATION(cursor.getString(13));
            stockLigne.setTS(cursor.getString(14));
            stockLigne.setVERSION(cursor.getString(15));
        }

        cursor.close();
        db.close();
        Log.d("STOCKLIGNE MANAGER", "fetching ");
        return stockLigne;

    }

    public int updateStockLigneQte(String ARTICLE_CODE,String UNITE_CODE, double QTE){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_QTE,QTE);
        int result = db.update(TABLE_STOCK_LIGNE,contentValues,KEY_ARTICLE_CODE+"=? AND "+KEY_UNITE_CODE+"=?",new String[]{ARTICLE_CODE,UNITE_CODE});
        return result;
    }

    public int updateStockLigneQteInitiale(String ARTICLE_CODE,String UNITE_CODE, double QTE){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_QTE,QTE);
        int result = db.update(TABLE_STOCK_LIGNE,contentValues,KEY_ARTICLE_CODE+"=? AND "+KEY_UNITE_CODE+"=?",new String[]{ARTICLE_CODE,UNITE_CODE});
        db.close();
        return result;
    }

    public ArrayList<StockLigne> getList() {
        ArrayList<StockLigne> stockLignes = new ArrayList<StockLigne>();

        String selectQuery = "SELECT * FROM " + TABLE_STOCK_LIGNE + " ORDER BY "+ KEY_ARTICLE_DESIGNATION +" ASC ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                StockLigne stockLigne = new StockLigne();
                stockLigne.setSTOCKLIGNE_CODE(cursor.getInt(0));
                stockLigne.setSTOCK_CODE(cursor.getString(1));
                stockLigne.setFAMILLE_CODE(cursor.getString(2));
                stockLigne.setARTICLE_CODE(cursor.getString(3));
                stockLigne.setARTICLE_DESIGNATION(cursor.getString(4));
                stockLigne.setARTICLE_NBUS_PAR_UP(cursor.getInt(5));
                stockLigne.setARTICLE_PRIX(cursor.getDouble(6));
                stockLigne.setUNITE_CODE(cursor.getString(7));
                stockLigne.setQTE(cursor.getInt(8));
                stockLigne.setLITTRAGE(cursor.getDouble(9));
                stockLigne.setTONNAGE(cursor.getDouble(10));
                stockLigne.setCOMMENTAIRE(cursor.getString(11));
                stockLigne.setCREATEUR_CODE(cursor.getString(12));
                stockLigne.setDATE_CREATION(cursor.getString(13));
                stockLigne.setTS(cursor.getString(14));
                stockLigne.setVERSION(cursor.getString(15));
                stockLignes.add(stockLigne);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version StockLigne from Sqlite: "+stockLignes.size());
        return stockLignes;
    }

    public ArrayList<StockLigne> getListByArticleCode(String ARTICLE_CODE) {
        ArrayList<StockLigne> stockLignes = new ArrayList<StockLigne>();

        String selectQuery = "SELECT * FROM " + TABLE_STOCK_LIGNE + " WHERE "+ KEY_ARTICLE_CODE +" = '"+ARTICLE_CODE+"';";;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                StockLigne stockLigne = new StockLigne();
                stockLigne.setSTOCKLIGNE_CODE(cursor.getInt(0));
                stockLigne.setSTOCK_CODE(cursor.getString(1));
                stockLigne.setFAMILLE_CODE(cursor.getString(2));
                stockLigne.setARTICLE_CODE(cursor.getString(3));
                stockLigne.setARTICLE_DESIGNATION(cursor.getString(4));
                stockLigne.setARTICLE_NBUS_PAR_UP(cursor.getInt(5));
                stockLigne.setARTICLE_PRIX(cursor.getDouble(6));
                stockLigne.setUNITE_CODE(cursor.getString(7));
                stockLigne.setQTE(cursor.getInt(8));
                stockLigne.setLITTRAGE(cursor.getDouble(9));
                stockLigne.setTONNAGE(cursor.getDouble(10));
                stockLigne.setCOMMENTAIRE(cursor.getString(11));
                stockLigne.setCREATEUR_CODE(cursor.getString(12));
                stockLigne.setDATE_CREATION(cursor.getString(13));
                stockLigne.setTS(cursor.getString(14));
                stockLigne.setVERSION(cursor.getString(15));
                stockLignes.add(stockLigne);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version StockLigne from Sqlite: "+stockLignes.size());
        return stockLignes;
    }

    /*public void updateStockLigneQteVersion(Context context){

        ArticleManager articleManager = new ArticleManager(context);
        UniteManager uniteManager = new UniteManager(context);
        StockLigneManager stockLigneManager = new StockLigneManager(context);

        ArrayList<Article> articles;
        articles = articleManager.getList();

        for(int i = 0; i<articles.size() ;i++){

            ArrayList<StockLigne> stockLignes;
            Article article = articles.get(i);

            stockLignes = stockLigneManager.getListByArticleCode(article.getARTICLE_CODE());

            int NBUS_PAR_UP;
            int BOUTEILLE_TOTALE = 0;


            NBUS_PAR_UP = (int)articles.get(i).getNBUS_PAR_UP();

            Log.d(TAG, "updateStockLigneQteVersion: stocklignes "+stockLignes);
            Log.d(TAG, "updateStockLigneQteVersion: NBUS PAR UP "+NBUS_PAR_UP);

            if(stockLignes.size()>0){

                int BOUTEILLE;
                int CAISSE;
                String UNITE_CODE;
                String UNITE_NOM;

                for(int j=0;j<stockLignes.size();j++){

                    UNITE_CODE = stockLignes.get(j).getUNITE_CODE();

                    Log.d(TAG, "updateStockLigneQteVersion: unite code "+UNITE_CODE);
                    Unite unite;
                    unite = uniteManager.get(UNITE_CODE);
                    UNITE_NOM = unite.getUNITE_NOM();

                    Log.d(TAG, "updateStockLigneQteVersion: unite nom "+UNITE_NOM);

                    if(UNITE_NOM.equals("BOUTEILLE")){
                        BOUTEILLE_TOTALE += (int)stockLignes.get(j).getQTE();

                        Log.d(TAG, "updateStockLigneQteVersion: B BTOTAL "+BOUTEILLE_TOTALE);

                    }else if(UNITE_NOM.equals("CAISSE")){
                        BOUTEILLE_TOTALE += (int)(stockLignes.get(j).getQTE())*NBUS_PAR_UP;

                        Log.d(TAG, "updateStockLigneQteVersion: C BTOTAL "+BOUTEILLE_TOTALE);
                    }


                }

                CAISSE = BOUTEILLE_TOTALE/NBUS_PAR_UP;
                BOUTEILLE = BOUTEILLE_TOTALE % NBUS_PAR_UP;

                Log.d(TAG, "updateStockLigneQteVersion: CAISSE TOTAL "+CAISSE);
                Log.d(TAG, "updateStockLigneQteVersion: BOUTEILLE TOTAL "+BOUTEILLE);

                for(int k=0;k<stockLignes.size();k++){

                    UNITE_CODE = stockLignes.get(k).getUNITE_CODE();

                    Log.d(TAG, "updateStockLigneQteVersion: 3 unite code "+UNITE_CODE);
                    Unite unite;
                    unite = uniteManager.get(UNITE_CODE);
                    UNITE_NOM = unite.getUNITE_NOM();

                    Log.d(TAG, "updateStockLigneQteVersion: 3 unite nom "+UNITE_NOM);

                    if(UNITE_NOM.equals("BOUTEILLE")){

                        updateStockLigneQte(article.getARTICLE_CODE(),UNITE_CODE,BOUTEILLE);

                        Log.d(TAG, "updateStockLigneQteVersion: 3 B BTOTAL "+BOUTEILLE);

                    }else if(UNITE_NOM.equals("CAISSE")){

                        updateStockLigneQte(article.getARTICLE_CODE(),UNITE_CODE,CAISSE);

                        Log.d(TAG, "updateStockLigneQteVersion: 3 C BTOTAL "+CAISSE);
                    }

                }

                Log.d(TAG, "updateStockLigneQteVersion: "+stockLigneManager.getList());

                //deleteStockLigneZero();

            }else{

                Log.d(TAG, "updateStockLigneQteVersion: Aucune stockligne a mettre a jour" );
            }



        }

        deleteStockLigneZero();

    }*/

    public void updateStockLigneQteVersion(Context context){

        ArticleManager articleManager = new ArticleManager(context);
        UniteManager uniteManager = new UniteManager(context);
        StockLigneManager stockLigneManager = new StockLigneManager(context);

        ArrayList<Article> articles;
        articles = articleManager.getList();

        for(int i = 0; i<articles.size() ;i++){

            ArrayList<StockLigne> stockLignes;
            Article article = articles.get(i);

            ArrayList<Unite> unites;
            unites = uniteManager.getListByACDESC(article.getARTICLE_CODE());
            stockLignes = stockLigneManager.getListByArticleCode(article.getARTICLE_CODE());



            Log.d(TAG, "updateStockLigneQteVersion: stocklignes "+stockLignes);

            if(stockLignes.size()>0){

                int BOUTEILLE_TOTALE = 0;
                for(int j=0;j<stockLignes.size();j++){

                    String UNITE_CODE = stockLignes.get(j).getUNITE_CODE();
                    Log.d(TAG, "updateStockLigneQteVersion: unite code "+UNITE_CODE);

                    Unite unite;
                    unite = uniteManager.get(UNITE_CODE);

                    BOUTEILLE_TOTALE += (int)stockLignes.get(j).getQTE()*unite.getFACTEUR_CONVERSION();

                }

                int QTE = 0;
                for(int k=0;k<unites.size();k++){
                    QTE = (int) (BOUTEILLE_TOTALE/unites.get(k).getFACTEUR_CONVERSION());
                    updateStockLigneQte(article.getARTICLE_CODE(),unites.get(k).getUNITE_CODE(),QTE);
                    BOUTEILLE_TOTALE = (int) (BOUTEILLE_TOTALE%unites.get(k).getFACTEUR_CONVERSION());
                }

                Log.d(TAG, "updateStockLigneQteVersion: "+stockLigneManager.getList());

            }else{

                Log.d(TAG, "updateStockLigneQteVersion: Aucune stockligne a mettre a jour" );
            }
        }
        deleteStockLigneZero();

    }


    /*public Boolean checkStockLigneQteVersion(ArrayList<CommandeLigne> commandeLignes,String article_code,Context context){

        ArticleManager articleManager = new ArticleManager(context);
        UniteManager uniteManager = new UniteManager(context);
        StockLigneManager stockLigneManager = new StockLigneManager(context);

        Article article = new Article();
        article = articleManager.get(article_code);

        ArrayList<StockLigne> stockLignes;
        stockLignes = stockLigneManager.getListByArticleCode(article.getARTICLE_CODE());

        ArrayList<CommandeLigne> commandeLignes1 = new ArrayList<>();

        for(int i=0;i<commandeLignes.size();i++){

            if(commandeLignes.get(i).getARTICLE_CODE().equals(article_code)){

                commandeLignes1.add(commandeLignes.get(i));
            }

        }

        int NBUS_PAR_UP;
        int BOUTEILLE_TOTALE_CMD = 0;
        int BOUTEILLE_TOTALE_SL = 0;

        Log.d(TAG, "checkStockLigneQteVersion: "+article.toString());
        Log.d(TAG, "checkStockLigneQteVersion: "+article_code);

        NBUS_PAR_UP = (int)article.getNBUS_PAR_UP();

        for(int j=0;j<commandeLignes1.size();j++){

            String UNITE_CODE;
            String UNITE_NOM;

            UNITE_CODE = commandeLignes1.get(j).getUNITE_CODE();

            Log.d(TAG, "checkStockLigneQteVersion: CMD "+commandeLignes1.get(j).toString());

            Log.d(TAG, "checkStockLigneQteVersion: unite code CMD "+UNITE_CODE);
            Unite unite;
            unite = uniteManager.get(UNITE_CODE);
            UNITE_NOM = unite.getUNITE_NOM();

            Log.d(TAG, "checkStockLigneQteVersion: unite nom CMD "+UNITE_NOM);

            if(UNITE_NOM.equals("BOUTEILLE")){
                BOUTEILLE_TOTALE_CMD += (int)commandeLignes1.get(j).getQTE_COMMANDEE();

                Log.d(TAG, "checkStockLigneQteVersion: B BTOTAL CMD "+BOUTEILLE_TOTALE_CMD);

            }else if(UNITE_NOM.equals("CAISSE")){
                BOUTEILLE_TOTALE_CMD += (int)(commandeLignes1.get(j).getQTE_COMMANDEE())*NBUS_PAR_UP;

                Log.d(TAG, "checkStockLigneQteVersion: C BTOTAL CMD "+BOUTEILLE_TOTALE_CMD);
            }


        }

        if(stockLignes.size()>0){

            for(int j=0;j<stockLignes.size();j++){

                String UNITE_CODE;
                String UNITE_NOM;

                UNITE_CODE = stockLignes.get(j).getUNITE_CODE();

                Log.d(TAG, "checkStockLigneQteVersion: SL "+stockLignes.get(j).toString());

                Log.d(TAG, "checkStockLigneQteVersion: unite code SL"+UNITE_CODE);
                Unite unite;
                unite = uniteManager.get(UNITE_CODE);
                UNITE_NOM = unite.getUNITE_NOM();

                Log.d(TAG, "checkStockLigneQteVersion: unite nom SL "+UNITE_NOM);

                if(UNITE_NOM.equals("BOUTEILLE")){
                    BOUTEILLE_TOTALE_SL += (int)stockLignes.get(j).getQTE();

                    Log.d(TAG, "checkStockLigneQteVersion: B BTOTAL SL "+BOUTEILLE_TOTALE_SL);

                }else if(UNITE_NOM.equals("CAISSE")){
                    BOUTEILLE_TOTALE_SL += (int)(stockLignes.get(j).getQTE())*NBUS_PAR_UP;

                    Log.d(TAG, "checkStockLigneQteVersion: C BTOTAL SL "+BOUTEILLE_TOTALE_SL);
                }


            }

            if(BOUTEILLE_TOTALE_CMD > BOUTEILLE_TOTALE_SL){

                return false;

            }else{

                return true;
            }
        }else{

            return false;

        }

    }*/

    public Boolean checkStockLigneQteVersion(ArrayList<CommandeLigne> commandeLignes,String article_code,Context context){

        ArticleManager articleManager = new ArticleManager(context);
        UniteManager uniteManager = new UniteManager(context);
        StockLigneManager stockLigneManager = new StockLigneManager(context);

        Article article = new Article();
        article = articleManager.get(article_code);

        ArrayList<StockLigne> stockLignes;
        stockLignes = stockLigneManager.getListByArticleCode(article.getARTICLE_CODE());

        ArrayList<CommandeLigne> commandeLignes1 = new ArrayList<>();
        for(int i=0;i<commandeLignes.size();i++){
            if(commandeLignes.get(i).getARTICLE_CODE().equals(article_code)){
                commandeLignes1.add(commandeLignes.get(i));
            }
        }

        int BOUTEILLE_TOTALE_CMD = 0;
        int BOUTEILLE_TOTALE_SL = 0;

        Log.d(TAG, "checkStockLigneQteVersion: "+article.toString());
        Log.d(TAG, "checkStockLigneQteVersion: "+article_code);

        for(int j=0;j<commandeLignes1.size();j++){

            String UNITE_CODE;
            UNITE_CODE = commandeLignes1.get(j).getUNITE_CODE();

            Log.d(TAG, "checkStockLigneQteVersion: CMD "+commandeLignes1.get(j).toString());
            Log.d(TAG, "checkStockLigneQteVersion: unite code CMD "+UNITE_CODE);

            Unite unite;
            unite = uniteManager.get(UNITE_CODE);

            BOUTEILLE_TOTALE_CMD += commandeLignes1.get(j).getQTE_COMMANDEE()*unite.getFACTEUR_CONVERSION();
        }

        if(stockLignes.size()>0){

            for(int j=0;j<stockLignes.size();j++){

                String UNITE_CODE;
                UNITE_CODE = stockLignes.get(j).getUNITE_CODE();

                Log.d(TAG, "checkStockLigneQteVersion: SL "+stockLignes.get(j).toString());
                Log.d(TAG, "checkStockLigneQteVersion: unite code SL"+UNITE_CODE);

                Unite unite;
                unite = uniteManager.get(UNITE_CODE);

                BOUTEILLE_TOTALE_SL += (int)stockLignes.get(j).getQTE()*unite.getFACTEUR_CONVERSION();
            }

            if(BOUTEILLE_TOTALE_CMD > BOUTEILLE_TOTALE_SL){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }

    }

    public Boolean checkQteMinVersion(ArrayList<CommandeLigne> commandeLignes,String article_code,Context context){

        ArticleManager articleManager = new ArticleManager(context);
        UniteManager uniteManager = new UniteManager(context);
        ParametreManager parametreManager = new ParametreManager(context);


        int qte_min;
        Article article;
        article = articleManager.get(article_code);

        Parametre parametre_qte_min = parametreManager.get(article_code);

        ArrayList<CommandeLigne> commandeLignes1 = new ArrayList<>();
        for(int i=0;i<commandeLignes.size();i++){
            if(commandeLignes.get(i).getARTICLE_CODE().equals(article_code)){
                commandeLignes1.add(commandeLignes.get(i));
            }
        }

        int BOUTEILLE_TOTALE_CMD = 0;

        Log.d(TAG, "checkQteMinVersion: "+article.toString());
        Log.d(TAG, "checkQteMinVersion: "+article_code);

        for(int j=0;j<commandeLignes1.size();j++){

            String UNITE_CODE;
            UNITE_CODE = commandeLignes1.get(j).getUNITE_CODE();

            Log.d(TAG, "checkQteMinVersion: CMD "+commandeLignes1.get(j).toString());
            Log.d(TAG, "checkQteMinVersion: unite code CMD "+UNITE_CODE);

            Unite unite;
            unite = uniteManager.get(UNITE_CODE);

            BOUTEILLE_TOTALE_CMD += commandeLignes1.get(j).getQTE_COMMANDEE()*unite.getFACTEUR_CONVERSION();
        }

        if(parametre_qte_min.getVALEUR() != null ){

            try{

                qte_min = Integer.parseInt(parametre_qte_min.getVALEUR());

                if(BOUTEILLE_TOTALE_CMD < qte_min){
                    return false;
                }else{
                    return true;
                }

            }catch(NumberFormatException e){
                Log.d(TAG, "Check qte min: "+e.getMessage());
            }
        }

        return true;
    }

    public Boolean checkLivraisonStockLigneQteVersion(ArrayList<LivraisonLigne> livraisonLignes, String article_code, Context context){

        ArticleManager articleManager = new ArticleManager(context);
        UniteManager uniteManager = new UniteManager(context);
        StockLigneManager stockLigneManager = new StockLigneManager(context);

        Article article = new Article();
        article = articleManager.get(article_code);

        ArrayList<StockLigne> stockLignes;
        stockLignes = stockLigneManager.getListByArticleCode(article.getARTICLE_CODE());

        ArrayList<LivraisonLigne> livraisonLigneArrayList = new ArrayList<>();
        for(int i=0;i<livraisonLignes.size();i++){
            if(livraisonLignes.get(i).getARTICLE_CODE().equals(article_code)){
                livraisonLigneArrayList.add(livraisonLignes.get(i));
            }
        }

        int BOUTEILLE_TOTALE_CMD = 0;
        int BOUTEILLE_TOTALE_SL = 0;

        Log.d(TAG, "checkStockLigneQteVersion: "+article.toString());
        Log.d(TAG, "checkStockLigneQteVersion: "+article_code);

        for(int j=0;j<livraisonLigneArrayList.size();j++){

            String UNITE_CODE;
            UNITE_CODE = livraisonLigneArrayList.get(j).getUNITE_CODE();

            Log.d(TAG, "checkStockLigneQteVersion: CMD "+livraisonLigneArrayList.get(j).toString());
            Log.d(TAG, "checkStockLigneQteVersion: unite code CMD "+UNITE_CODE);

            Unite unite;
            unite = uniteManager.get(UNITE_CODE);

            BOUTEILLE_TOTALE_CMD += livraisonLigneArrayList.get(j).getQTE_LIVREE()*unite.getFACTEUR_CONVERSION();
        }

        if(stockLignes.size()>0){

            for(int j=0;j<stockLignes.size();j++){

                String UNITE_CODE;
                UNITE_CODE = stockLignes.get(j).getUNITE_CODE();

                Log.d(TAG, "checkStockLigneQteVersion: SL "+stockLignes.get(j).toString());
                Log.d(TAG, "checkStockLigneQteVersion: unite code SL"+UNITE_CODE);

                Unite unite;
                unite = uniteManager.get(UNITE_CODE);

                BOUTEILLE_TOTALE_SL += (int)stockLignes.get(j).getQTE()*unite.getFACTEUR_CONVERSION();
            }

            if(BOUTEILLE_TOTALE_CMD > BOUTEILLE_TOTALE_SL){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }

    }



    //SYNCHRONISATION STOCKP
    public static void synchronisationStockLigne(final Context context){

        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_STOCK_LIGNE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("stockligne", "onResponse: "+response);

                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    Log.d("stockligne response",response);
                    JSONObject jObj = new JSONObject(response);

                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray StockLignes = jObj.getJSONArray("StockLignes");
                        Toast.makeText(context, "nombre de stockligne  "+StockLignes.length()  , Toast.LENGTH_SHORT).show();
                        if(StockLignes.length()>0) {
                            StockLigneManager stockLigneManager = new StockLigneManager(context);
                            for (int i = 0; i < StockLignes.length(); i++) {
                                JSONObject stockLigne = StockLignes.getJSONObject(i);

                                Log.d(TAG, "onResponse: stockLigne"+stockLigne);

                                if (stockLigne.getString("OPERATION").equals("DELETE")) {
                                    stockLigneManager.delete(stockLigne.getString("STOCK_CODE"),stockLigne.getString("STOCKLIGNE_CODE"),stockLigne.getString("VERSION"));
                                    cptDelete++;
                                } else {
                                    stockLigneManager.add(new StockLigne(stockLigne));
                                    cptInsert++;
                                }
                            }

                        }
                        logM.add("StockLigne:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncStockLigne");
                    }else {
                        String errorMsg = jObj.getString("info");
                        Toast.makeText(context,
                                "StockLigne : "+errorMsg, Toast.LENGTH_LONG).show();
                        logM.add("StockLigne:NOK "+errorMsg ,"SyncStockLigne");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "StockLigne : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    logM.add("StockLigne:NOK "+e.getMessage() ,"SyncStockLigne");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        "StockLigne : "+error.getMessage(), Toast.LENGTH_LONG).show();
                LogSyncManager logM= new LogSyncManager(context);
                logM.add("StockLigne:NOK "+error.getMessage() ,"SyncStockLigne");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //Map<String, String> params = new HashMap<String, String>();
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    StockLigneManager stockLigneManager  = new StockLigneManager(context);
                    List<StockLigne> stockLignes = stockLigneManager.getList();

                    Log.d(TAG, "getParams: stockLigne"+stockLignes);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    //Log.d("UC STOCKP SYNC",gson.toJson(pref.getString("UTILISATEUR_CODE",null)));

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(stockLignes));

                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public ArrayList<StockLigne> fixStockLigneCode(ArrayList<StockLigne> stockLignes){

        ArrayList<StockLigne> stockLignes1 = stockLignes;

        for(int i=0 ; i<stockLignes1.size(); i++){

            stockLignes1.get(i).setSTOCKLIGNE_CODE(i+1);
        }

        return stockLignes1;
    }


}
