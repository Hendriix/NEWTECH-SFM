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
import com.newtech.newtech_sfm.Activity.SyncV2Activity;
import com.newtech.newtech_sfm.Configuration.AppConfig;
import com.newtech.newtech_sfm.Configuration.AppController;
import com.newtech.newtech_sfm.Configuration.SQLiteHandler;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.LogSync;
import com.newtech.newtech_sfm.Metier.StockPLigne;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stagiaireit2 on 18/07/2016.
 */
public class ArticleManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_ARTICLE = "article";

    private static final String
            KEY_ARTICLE_CODE ="ARTICLE_CODE",
            KEY_ARTICLE_DESIGNATION1 = "ARTICLE_DESIGNATION1",
            KEY_ARTICLE_DESIGNATION2 = "ARTICLE_DESIGNATION2",
            KEY_ARTICLE_DESIGNATION3 = "ARTICLE_DESIGNATION3",
            KEY_ARTICLE_SKU = "ARTICLE_SKU",
            KEY_LITTRAGE_UP = "LITTRAGE_UP",
            KEY_POIDKG_UP = "POIDKG_UP",
            KEY_LITTRAGE_US = "LITTRAGE_US",
            KEY_POIDKG_US = "POIDKG_US",
            KEY_NBUS_PAR_UP = "NBUS_PAR_UP",
            KEY_CATEGORIE_CODE = "CATEGORIE_CODE",
            KEY_TYPE_CODE = "TYPE_CODE",
            KEY_FAMILLE_CODE = "FAMILLE_CODE",
            KEY_DATE_CREATION = "DATE_CREATION",
            KEY_CREATEUR_CODE = "CREATEUR_CODE",
            KEY_INACTIF = "INACTIF",
            KEY_INACTIF_RAISON = "INACTIF_RAISON",
            KEY_RANG = "RANG",
            KEY_VERSION = "VERSION",
            KEY_IMAGE = "IMAGE",
            KEY_ARTICLE_PRIX = "ARTICLE_PRIX";

    public static String CREATE_ARTICLE_TABLE="CREATE TABLE " + TABLE_ARTICLE + "("
            +KEY_ARTICLE_CODE + " TEXT PRIMARY KEY,"
            + KEY_ARTICLE_DESIGNATION1 + " TEXT,"
            + KEY_ARTICLE_DESIGNATION2 + " TEXT,"
            + KEY_ARTICLE_DESIGNATION3 + " TEXT,"
            + KEY_ARTICLE_SKU + " NUMERIC,"
            +KEY_LITTRAGE_UP + " NUMERIC,"
            +KEY_POIDKG_UP + " NUMERIC,"
            +KEY_LITTRAGE_US + " NUMERIC,"
            +KEY_POIDKG_US + " NUMERIC,"
            +KEY_NBUS_PAR_UP + " NUMERIC,"
            +KEY_CATEGORIE_CODE + " TEXT,"
            +KEY_TYPE_CODE + " TEXT,"
            +KEY_FAMILLE_CODE + " TEXT,"
            +KEY_DATE_CREATION + " NUMERIC,"
            +KEY_CREATEUR_CODE + " TEXT,"
            +KEY_INACTIF + " NUMERIC,"
            +KEY_INACTIF_RAISON + " TEXT,"
            +KEY_RANG + " NUMERIC,"
            + KEY_VERSION + " TEXT,"
            + KEY_IMAGE+ " TEXT,"
            + KEY_ARTICLE_PRIX+ " NUMERIC" + ")";

    public ArticleManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE);
            db.execSQL(CREATE_ARTICLE_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database tables created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE);
        onCreate(db);
    }

    public void add(Article article) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ARTICLE_CODE, article.getARTICLE_CODE());
        values.put(KEY_ARTICLE_DESIGNATION1,article.getARTICLE_DESIGNATION1());
        values.put(KEY_ARTICLE_DESIGNATION2, article.getARTICLE_DESIGNATION2());
        values.put(KEY_ARTICLE_DESIGNATION3, article.getARTICLE_DESIGNATION3());
        values.put(KEY_ARTICLE_SKU, article.getARTICLE_SKU());
        values.put(KEY_LITTRAGE_UP, article.getLITTRAGE_UP());
        values.put(KEY_POIDKG_UP, article.getPOIDKG_UP());
        values.put(KEY_LITTRAGE_US, article.getLITTRAGE_US());
        values.put(KEY_POIDKG_US, article.getPOIDKG_US());
        values.put(KEY_NBUS_PAR_UP, article.getNBUS_PAR_UP());
        values.put(KEY_CATEGORIE_CODE, article.getCATEGORIE_CODE());
        values.put(KEY_TYPE_CODE, article.getTYPE_CODE());
        values.put(KEY_FAMILLE_CODE, article.getFAMILLE_CODE());
        values.put(KEY_DATE_CREATION, article.getDATE_CREATION());
        values.put(KEY_CREATEUR_CODE, article.getCREATEUR_CODE());
        values.put(KEY_INACTIF, article.getINACTIF());
        values.put(KEY_INACTIF_RAISON, article.getINACTIF_RAISON());
        values.put(KEY_RANG, article.getRANG());
        values.put(KEY_VERSION, article.getVERSION());
        values.put(KEY_IMAGE, article.getIMAGE());
        values.put(KEY_ARTICLE_PRIX, article.getARTICLE_PRIX());

        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_ARTICLE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New articles inserted into sqlite: " + id);
    }

    public Article get(String article_code) {
        Article article = new Article();

        String selectQuery = "SELECT  * FROM " + TABLE_ARTICLE+" WHERE "+KEY_ARTICLE_CODE+"='"+article_code+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if( cursor != null && cursor.moveToFirst() ) {
                article.setARTICLE_CODE(cursor.getString(0));
                article.setARTICLE_DESIGNATION1(cursor.getString(1));
                article.setARTICLE_DESIGNATION2(cursor.getString(2));
                article.setARTICLE_DESIGNATION3(cursor.getString(3));
                article.setARTICLE_SKU((cursor.getDouble(4)));
                article.setLITTRAGE_UP(cursor.getDouble(5));
                article.setPOIDKG_UP(cursor.getDouble(6));
                article.setLITTRAGE_US(cursor.getDouble(7));
                article.setPOIDKG_US(cursor.getDouble(8));
                article.setNBUS_PAR_UP(cursor.getDouble(9));
                article.setCATEGORIE_CODE(cursor.getString(10));
                article.setTYPE_CODE(cursor.getString(11));
                article.setFAMILLE_CODE(cursor.getString(12));
                article.setDATE_CREATION(cursor.getString(13));
                article.setCREATEUR_CODE(cursor.getString(14));
                article.setINACTIF(cursor.getDouble(15));
                article.setINACTIF_RAISON(cursor.getString(16));
                article.setRANG(cursor.getInt(17));
                article.setVERSION(cursor.getString(18));
                article.setIMAGE(cursor.getString(19));
                article.setARTICLE_PRIX(cursor.getDouble(20));
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching article from Sqlite: ");
        return article;
    }

    public ArrayList<Article> getArticlesForStockPLigne(ArrayList<StockPLigne> stockPLignes){

        //ArrayList<Article> articles = new ArrayList<>();
        ArrayList<Article> listWithoutDuplicates = new ArrayList<>();
        int existe;
        if(stockPLignes.size() > 0){

            for(int i = 0; i< stockPLignes.size() ; i++){

                Article article = get(stockPLignes.get(i).getARTICLE_CODE());
                existe = 0;

                if(listWithoutDuplicates.size()>0){

                    for(int j=0 ; j<listWithoutDuplicates.size() ; j++){

                        if(article.getARTICLE_CODE().equals(listWithoutDuplicates.get(j).getARTICLE_CODE())){
                            existe ++;
                        }
                    }

                    if(existe == 0){

                        listWithoutDuplicates.add(article);
                    }

                }else{

                    listWithoutDuplicates.add(article);
                }



            }
            //Log.d(TAG, "getArticlesForStockPLigne: "+articles.toString());
            //HashSet<Article> articleHashSet = new HashSet<Article>(articles);
            //listWithoutDuplicates = new ArrayList<Article>(articleHashSet);
            //articlesARetourner = (ArrayList<Article>) articles.stream().distinct().collect(Collectors.toList());
            Log.d(TAG, "getArticlesForStockPLigne: "+listWithoutDuplicates.toString());
            return  listWithoutDuplicates;

        }else{

            return  listWithoutDuplicates;

        }

    }

    public ArrayList<Article> getList() {
        ArrayList<Article> listArticles = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_ARTICLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Article ar = new Article();
                ar.setARTICLE_CODE(cursor.getString(0));
                ar.setARTICLE_DESIGNATION1(cursor.getString(1));
                ar.setARTICLE_DESIGNATION2(cursor.getString(2));
                ar.setARTICLE_DESIGNATION3(cursor.getString(3));
                ar.setARTICLE_SKU((cursor.getDouble(4)));
                ar.setLITTRAGE_UP(cursor.getDouble(5));
                ar.setPOIDKG_UP(cursor.getDouble(6));
                ar.setLITTRAGE_US(cursor.getDouble(7));
                ar.setPOIDKG_US(cursor.getDouble(8));
                ar.setNBUS_PAR_UP(cursor.getDouble(9));
                ar.setCATEGORIE_CODE(cursor.getString(10));
                ar.setTYPE_CODE(cursor.getString(11));
                ar.setFAMILLE_CODE(cursor.getString(12));
                ar.setDATE_CREATION(cursor.getString(13));
                ar.setCREATEUR_CODE(cursor.getString(14));
                ar.setINACTIF(cursor.getDouble(15));
                ar.setINACTIF_RAISON(cursor.getString(16));
                ar.setRANG(cursor.getInt(17));
                ar.setVERSION(cursor.getString(18));
                ar.setIMAGE(cursor.getString(19));
                ar.setARTICLE_PRIX(cursor.getDouble(20));

                listArticles.add(ar);
            }while(cursor.moveToNext());
        }

        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching articles from Sqlite: ");
        return listArticles;
    }

    public ArrayList<Article> getListJoinStockLigne() {
        ArrayList<Article> listArticles = new ArrayList<>();

        String selectQuery = "SELECT  article.* FROM " + TABLE_ARTICLE + " INNER JOIN stockligne on stockligne.ARTICLE_CODE = article.ARTICLE_CODE group by article.ARTICLE_CODE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Article ar = new Article();
                ar.setARTICLE_CODE(cursor.getString(0));
                ar.setARTICLE_DESIGNATION1(cursor.getString(1));
                ar.setARTICLE_DESIGNATION2(cursor.getString(2));
                ar.setARTICLE_DESIGNATION3(cursor.getString(3));
                ar.setARTICLE_SKU((cursor.getDouble(4)));
                ar.setLITTRAGE_UP(cursor.getDouble(5));
                ar.setPOIDKG_UP(cursor.getDouble(6));
                ar.setLITTRAGE_US(cursor.getDouble(7));
                ar.setPOIDKG_US(cursor.getDouble(8));
                ar.setNBUS_PAR_UP(cursor.getDouble(9));
                ar.setCATEGORIE_CODE(cursor.getString(10));
                ar.setTYPE_CODE(cursor.getString(11));
                ar.setFAMILLE_CODE(cursor.getString(12));
                ar.setDATE_CREATION(cursor.getString(13));
                ar.setCREATEUR_CODE(cursor.getString(14));
                ar.setINACTIF(cursor.getDouble(15));
                ar.setINACTIF_RAISON(cursor.getString(16));
                ar.setRANG(cursor.getInt(17));
                ar.setVERSION(cursor.getString(18));
                ar.setIMAGE(cursor.getString(19));
                ar.setARTICLE_PRIX(cursor.getDouble(20));

                listArticles.add(ar);
            }while(cursor.moveToNext());
        }

        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching articles from Sqlite: ");
        return listArticles;
    }

    public ArrayList<Article> getListByFamilleCode(String FamilleCode) {
        ArrayList<Article> listArticles = new ArrayList<>();
        String selectQuery ;
        if(FamilleCode.equals("tous"))  selectQuery= "SELECT  * FROM " + TABLE_ARTICLE;
        else  selectQuery = "SELECT  * FROM " + TABLE_ARTICLE +" WHERE "+KEY_FAMILLE_CODE +" = '"+FamilleCode+"' order by "+KEY_RANG+" asc";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Article ar = new Article();
                ar.setARTICLE_CODE(cursor.getString(0));
                ar.setARTICLE_DESIGNATION1(cursor.getString(1));
                ar.setARTICLE_DESIGNATION2(cursor.getString(2));
                ar.setARTICLE_DESIGNATION3(cursor.getString(3));
                ar.setARTICLE_SKU((cursor.getDouble(4)));
                ar.setLITTRAGE_UP(cursor.getDouble(5));
                ar.setPOIDKG_UP(cursor.getDouble(6));
                ar.setLITTRAGE_US(cursor.getDouble(7));
                ar.setPOIDKG_US(cursor.getDouble(8));
                ar.setNBUS_PAR_UP(cursor.getDouble(9));
                ar.setCATEGORIE_CODE(cursor.getString(10));
                ar.setTYPE_CODE(cursor.getString(11));
                ar.setFAMILLE_CODE(cursor.getString(12));
                ar.setDATE_CREATION(cursor.getString(13));
                ar.setCREATEUR_CODE(cursor.getString(14));
                ar.setINACTIF(cursor.getDouble(15));
                ar.setINACTIF_RAISON(cursor.getString(16));
                ar.setRANG(cursor.getInt(17));
                ar.setVERSION(cursor.getString(18));
                ar.setIMAGE(cursor.getString(19));
                ar.setARTICLE_PRIX(cursor.getDouble(20));
                listArticles.add(ar);
            }while(cursor.moveToNext());
        }

        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching articles from Sqlite: ");
        return listArticles;
    }
    public ArrayList<String> getListFamille() {
        ArrayList<String> listArticlDes = new ArrayList<>();
        String selectQuery = "SELECT DISTINCT "+KEY_FAMILLE_CODE+" FROM " + TABLE_ARTICLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                listArticlDes.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Famille from Sqlite: ");
        return listArticlDes;
    }

    public void deleteArticles() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_ARTICLE, null, null);
        db.close();
        Log.d(TAG, "Deleted all articles info from sqlite");
    }

    public int delete(String articleCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ARTICLE,KEY_ARTICLE_CODE+"=?",new String[]{articleCode});
    }

    public static void synchronisationArticle(final Context context){

        String tag_string_req = "ARTICLE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_ARTICLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ouvrir le logManager
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray articles = jObj.getJSONArray("Articles");
                        //Toast.makeText(context, "Nombre d'articles " +articles.length(), Toast.LENGTH_SHORT).show();
                        int cptInsert = 0,cptDeleted = 0;
                        //Ajout/Suppression/modification des articles dans la base de données.
                        for (int i = 0; i < articles.length(); i++) {
                            JSONObject unArticle = articles.getJSONObject(i);
                            ArticleManager articleManager = new ArticleManager(context);
                            if(unArticle.getString("OPERATION").equals("DELETE")){
                                articleManager.delete(unArticle.getString("ARTICLE_CODE"));
                                cptDeleted++;
                             }
                            else {
                                articleManager.add(new Article(unArticle));
                                cptInsert++;
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("ARTICLES: Inserted :"+cptInsert +" Deleted: "+cptDeleted,"ARTICLES",1));

                        }
                        //logM.add("ARTICLES: Insert:"+cptInsert +"Deleted:"+cptDeleted ,"ARTICLES", 1);
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //logM.add("ARTICLES: Insert "+errorMsg ,"ARTICLES",1);

                        //Toast.makeText(context,"ARTICLES:"+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("ARTICLES: Error: "+errorMsg,"ARTICLES",0));

                        }

                    }

                } catch (JSONException e) {
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("ARTICLES : Error: "+e.getMessage() ,"ARTICLES",0));

                    }
                    //logM.add("ARTICLES : Insert "+e.getMessage() ,"ARTICLES",0);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "ARTICLES:"+error.getMessage(), Toast.LENGTH_LONG).show();
                //LogSyncManager logM= new LogSyncManager(context);
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("ARTICLES : Error: "+error.getMessage() ,"ARTICLES",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    ArticleManager articleManager  = new ArticleManager(context);
                    for (int i = 0; i < articleManager.getList().size(); i++) {
                        if(articleManager.getList().get(i).getARTICLE_CODE()!=null && articleManager.getList().get(i).getVERSION() != null )
                            params.put(articleManager.getList().get(i).getARTICLE_CODE(), articleManager.getList().get(i).getVERSION());
                    }
                }
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }



}