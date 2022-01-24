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
import com.newtech.newtech_sfm.Metier.LivraisonLigne;
import com.newtech.newtech_sfm.Metier.LogSync;
import com.newtech.newtech_sfm.Metier.Promotionarticle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stagiaireit2 on 22/07/2016.
 */
public class PromotionarticleManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    // All Static variables

    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Article table name
    public static final String TABLE_PROMOTIONARTICLE = "promotionarticle";

    // Promotion Table Columns names
    private static final String
            KEY_ID="ID",
            KEY_PROMO_CODE="PROMO_CODE",
            KEY_CATEGORIE_CODE="CATEGORIE_CODE",
            KEY_TYPE_CODE="TYPE_CODE",
            KEY_VALEUR_AR="VALEUR_AR",
            KEY_DATE_CREATION="DATE_CREATION",
            KEY_VERSION="VERSION";

    public static String CREATE_PROMOTIONARTICLE_TABLE = "CREATE TABLE " + TABLE_PROMOTIONARTICLE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_PROMO_CODE + " TEXT ,"
            + KEY_CATEGORIE_CODE + " TEXT,"
            + KEY_TYPE_CODE + " TEXT,"
            + KEY_VALEUR_AR + " TEXT,"
            + KEY_DATE_CREATION + " NUMERIC,"
            + KEY_VERSION + " TEXT" + ")";
    public PromotionarticleManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_PROMOTIONARTICLE);
            db.execSQL(CREATE_PROMOTIONARTICLE_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROMOTIONARTICLE);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing Articles details in database
     * */
    public void add(Promotionarticle promotionarticle) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PROMO_CODE, promotionarticle.getPROMO_CODE());
        values.put(KEY_CATEGORIE_CODE,promotionarticle.getCATEGORIE_CODE());
        values.put(KEY_TYPE_CODE, promotionarticle.getTYPE_CODE());
        values.put(KEY_VALEUR_AR, promotionarticle.getVALEUR_AR());
        values.put(KEY_DATE_CREATION, promotionarticle.getDATE_CREATION());
        values.put(KEY_VERSION, promotionarticle.getVERSION());

        // Inserting Row
        //long id = db.insertWithOnConflict(TABLE_PROMOTIONARTICLE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        long id = db.insert(TABLE_PROMOTIONARTICLE, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New articles inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public ArrayList<Promotionarticle> getList() {
        ArrayList<Promotionarticle> listPromotionarticles = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_PROMOTIONARTICLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d("promotion", "getList: cursor "+cursor);
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Promotionarticle promotionarticle = new Promotionarticle();

                promotionarticle.setPROMO_CODE(cursor.getString(1));
                promotionarticle.setCATEGORIE_CODE(cursor.getString(2));
                promotionarticle.setTYPE_CODE(cursor.getString(3));
                promotionarticle.setVALEUR_AR(cursor.getString(4));
                promotionarticle.setDATE_CREATION(cursor.getString(5));
                promotionarticle.setVERSION(cursor.getString(6));
                Log.d("promotion", "getList: "+promotionarticle);
                listPromotionarticles.add(promotionarticle);
            }while(cursor.moveToNext());
        }

        //returner la listPromotionarticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Promotionarticles from Sqlite: "+listPromotionarticles);
        return listPromotionarticles;
    }

    public ArrayList<Promotionarticle> getPromArticle_version_code() {
        ArrayList<Promotionarticle> listPromotionarticles = new ArrayList<>();

        String selectQuery ="SELECT " + KEY_PROMO_CODE + " , " + KEY_VERSION  + " FROM "+ TABLE_PROMOTIONARTICLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Promotionarticle promotionarticle = new Promotionarticle();
                promotionarticle.setPROMO_CODE(cursor.getString(0));
                promotionarticle.setVERSION(cursor.getString(1));
                listPromotionarticles.add(promotionarticle);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Promotionarticles from Sqlite: ");
        return listPromotionarticles;
    }

    public ArrayList<Promotionarticle> getListByPromoCode(String promo_code) {
        ArrayList<Promotionarticle> listPromotionarticles = new ArrayList<>();

        String selectQuery = "SELECT  * "  +  " FROM " + TABLE_PROMOTIONARTICLE+" WHERE "+KEY_PROMO_CODE +" = '"+promo_code +"' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Promotionarticle promotionarticle = new Promotionarticle();

                promotionarticle.setPROMO_CODE(cursor.getString(1));
                promotionarticle.setCATEGORIE_CODE(cursor.getString(2));
                promotionarticle.setTYPE_CODE(cursor.getString(3));
                promotionarticle.setVALEUR_AR(cursor.getString(4));
                promotionarticle.setDATE_CREATION(cursor.getString(5));
                promotionarticle.setVERSION(cursor.getString(6));

                listPromotionarticles.add(promotionarticle);
            }while(cursor.moveToNext());
        }

        //returner la listPromotionarticles;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Promotionarticles from Sqlite: ");
        return listPromotionarticles;
    }
    /**
     * remise
     * */
    public Boolean Check_Implication(String promo_code, ArrayList<CommandeLigne> list_LCMD){
        Boolean result = false;
        ArrayList<Promotionarticle> listPromotionarticles = new ArrayList<Promotionarticle>();
        int nbIMP=0;

        listPromotionarticles=this.getListByPromoCode(promo_code);
        Log.d("promotion", "Check_Implication: promotionarticles "+listPromotionarticles);
        Log.d("promotion", "Check_Implication: promotionarticles size"+listPromotionarticles.size());

        if(list_LCMD.size()>0){
            if(listPromotionarticles.size()>0){
                for(int i=0;i<listPromotionarticles.size();i++){

                    if(listPromotionarticles.get(i).getCATEGORIE_CODE().equals("CA0011")){//Par Article
                        for(int j=0;j<list_LCMD.size();j++){
                            if(listPromotionarticles.get(i).getVALEUR_AR().equals(list_LCMD.get(j).getARTICLE_CODE())){
                                nbIMP++;
                            }
                        }

                    }else if(listPromotionarticles.get(i).getCATEGORIE_CODE().equals("CA0012")){//Par Famille
                        Log.d("promotion", "Check_Implication: CA0012");
                        for(int j=0;j<list_LCMD.size();j++){
                            Log.d("promotion", "Check_Implication: CA0012 FAMILLE before"+list_LCMD.get(j).getFAMILLE_CODE());
                            Log.d("promotion", "Check_Implication: CA0012 VALAF before"+listPromotionarticles.get(i).getVALEUR_AR());

                            if(listPromotionarticles.get(i).getVALEUR_AR().equals(list_LCMD.get(j).getFAMILLE_CODE())){
                                nbIMP++;
                                Log.d("promotion", "Check_Implication: CA0012 FAMILLE after"+list_LCMD.get(j).getFAMILLE_CODE());
                                Log.d("promotion", "Check_Implication: CA0012 VALAF after"+listPromotionarticles.get(i).getVALEUR_AR());
                            }
                        }
                    }

                }
            }
        }


        if(nbIMP>0){
            result=true;
        }else{
            result=false;
        }
        Log.d("promotion", "Check_Implication: "+ result);
        return result;
    }

    public Boolean Check_Implication_L(String promo_code, ArrayList<LivraisonLigne> list_LLIV){//implication livraison
        Boolean result = false;
        ArrayList<Promotionarticle> listPromotionarticles = new ArrayList<Promotionarticle>();
        int nbIMP=0;

        listPromotionarticles=this.getListByPromoCode(promo_code);
        Log.d("promotion", "Check_Implication: promotionarticles "+listPromotionarticles);
        Log.d("promotion", "Check_Implication: promotionarticles size"+listPromotionarticles.size());

        if(list_LLIV.size()>0){
            if(listPromotionarticles.size()>0){
                for(int i=0;i<listPromotionarticles.size();i++){

                    if(listPromotionarticles.get(i).getCATEGORIE_CODE().equals("CA0011")){//Par Article
                        for(int j=0;j<list_LLIV.size();j++){
                            if(listPromotionarticles.get(i).getVALEUR_AR().equals(list_LLIV.get(j).getARTICLE_CODE())){
                                nbIMP++;
                            }
                        }

                    }else if(listPromotionarticles.get(i).getCATEGORIE_CODE().equals("CA0012")){//Par Famille
                        Log.d("promotion", "Check_Implication: CA0012");
                        for(int j=0;j<list_LLIV.size();j++){
                            Log.d("promotion", "Check_Implication: CA0012 FAMILLE before"+list_LLIV.get(j).getFAMILLE_CODE());
                            Log.d("promotion", "Check_Implication: CA0012 VALAF before"+listPromotionarticles.get(i).getVALEUR_AR());

                            if(listPromotionarticles.get(i).getVALEUR_AR().equals(list_LLIV.get(j).getFAMILLE_CODE())){
                                nbIMP++;
                                Log.d("promotion", "Check_Implication: CA0012 FAMILLE after"+list_LLIV.get(j).getFAMILLE_CODE());
                                Log.d("promotion", "Check_Implication: CA0012 VALAF after"+listPromotionarticles.get(i).getVALEUR_AR());
                            }
                        }
                    }

                }
            }
        }


        if(nbIMP>0){
            result=true;
        }else{
            result=false;
        }
        Log.d("promotion", "Check_Implication: "+ result);
        return result;
    }
    //############################################################################   check implication PAR LIGNE (lcmd)
    public Boolean Check_ImplicationLCMD(String promo_code,CommandeLigne lCMD){
        Boolean result = false;
        ArrayList<Promotionarticle> listPromotionarticles = new ArrayList<Promotionarticle>();
        int nbIMP=0;

        listPromotionarticles=this.getListByPromoCode(promo_code);

        if(listPromotionarticles.size()>0){
            for(int i=0;i<listPromotionarticles.size();i++){
                if(listPromotionarticles.get(i).getCATEGORIE_CODE().equals("CA0011")){//Par Article

                        if(listPromotionarticles.get(i).getVALEUR_AR().equals(lCMD.getARTICLE_CODE())){
                            nbIMP++;
                        }

                }else if(listPromotionarticles.get(i).getCATEGORIE_CODE().equals("CA0012")){//Par Famille

                        if(listPromotionarticles.get(i).getVALEUR_AR().equals(lCMD.getFAMILLE_CODE())){
                            nbIMP++;
                        }

                }
            }
        }

        if(nbIMP>0){
            result=true;
        }else{
            result=false;
        }

        return result;
    }

    //############################################################################   check implication PAR LIGNE (llivraison)
    public Boolean Check_ImplicationLLIV(String promo_code,LivraisonLigne lLIV){
        Boolean result = false;
        ArrayList<Promotionarticle> listPromotionarticles = new ArrayList<Promotionarticle>();
        int nbIMP=0;

        listPromotionarticles=this.getListByPromoCode(promo_code);

        if(listPromotionarticles.size()>0){
            for(int i=0;i<listPromotionarticles.size();i++){
                if(listPromotionarticles.get(i).getCATEGORIE_CODE().equals("CA0011")){//Par Article

                    if(listPromotionarticles.get(i).getVALEUR_AR().equals(lLIV.getARTICLE_CODE())){
                        nbIMP++;
                    }

                }else if(listPromotionarticles.get(i).getCATEGORIE_CODE().equals("CA0012")){//Par Famille

                    if(listPromotionarticles.get(i).getVALEUR_AR().equals(lLIV.getFAMILLE_CODE())){
                        nbIMP++;
                    }

                }
            }
        }

        if(nbIMP>0){
            result=true;
        }else{
            result=false;
        }

        return result;
    }
    //############################################################################   GET_LCMDIMP  implication
    public ArrayList<CommandeLigne> get_LCMDIMP(String promo_code,ArrayList<CommandeLigne> list_lCMD){
        ArrayList<Promotionarticle> promotionArticles = new ArrayList<Promotionarticle>();
        ArrayList<CommandeLigne> list_lCMD_IMP = new ArrayList<CommandeLigne>();

        promotionArticles=this.getListByPromoCode(promo_code);

        Integer nbIMP=0;

        if(promotionArticles.size()>0){
            for(int i=0;i<promotionArticles.size();i++){
                if(promotionArticles.get(i).getCATEGORIE_CODE().equals("CA0011")){//Par Article
                    for(int j=0;j<list_lCMD.size();j++){
                        if(list_lCMD.get(j).getARTICLE_CODE().equals(promotionArticles.get(i).getVALEUR_AR())){
                            list_lCMD_IMP.add(list_lCMD.get(j));
                        }
                    }
                }else if(promotionArticles.get(i).getCATEGORIE_CODE().equals("CA0012")){//Par Famille
                    for(int j=0;j<list_lCMD.size();j++){
                        if(list_lCMD.get(j).getFAMILLE_CODE().equals(promotionArticles.get(i).getVALEUR_AR())){
                            list_lCMD_IMP.add(list_lCMD.get(j));
                        }
                    }
                }
            }
        }

        return list_lCMD_IMP;
    }

    //############################################################################   GET_LCMDIMP  implication
    public ArrayList<LivraisonLigne> get_LLIVIMP(String promo_code,ArrayList<LivraisonLigne> list_lLIV){
        ArrayList<Promotionarticle> promotionArticles = new ArrayList<Promotionarticle>();
        ArrayList<LivraisonLigne> list_lLIV_IMP = new ArrayList<LivraisonLigne>();

        promotionArticles=this.getListByPromoCode(promo_code);

        Integer nbIMP=0;

        if(promotionArticles.size()>0){
            for(int i=0;i<promotionArticles.size();i++){
                if(promotionArticles.get(i).getCATEGORIE_CODE().equals("CA0011")){//Par Article
                    for(int j=0;j<list_lLIV.size();j++){
                        if(list_lLIV.get(j).getARTICLE_CODE().equals(promotionArticles.get(i).getVALEUR_AR())){
                            list_lLIV_IMP.add(list_lLIV.get(j));
                        }
                    }
                }else if(promotionArticles.get(i).getCATEGORIE_CODE().equals("CA0012")){//Par Famille
                    for(int j=0;j<list_lLIV.size();j++){
                        if(list_lLIV.get(j).getFAMILLE_CODE().equals(promotionArticles.get(i).getVALEUR_AR())){
                            list_lLIV_IMP.add(list_lLIV.get(j));
                        }
                    }
                }
            }
        }

        return list_lLIV_IMP;
    }
    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deletePromotionarticles() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_PROMOTIONARTICLE, null, null);
        db.close();
        Log.d(TAG, "Deleted all promotionarticles info from sqlite");
    }

    /*public int delete(String promocode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PROMOTIONARTICLE,KEY_PROMO_CODE+"=?",new String[]{promocode});
    }*/

    public int delete(String tacheCode,String version) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PROMOTIONARTICLE,KEY_PROMO_CODE+"=? AND "+KEY_VERSION+"=?",new String[]{tacheCode,version});
    }

    public static void synchronisationPromArticleAffectation(final Context context){

        String tag_string_req = "PROMOTION_AFFECTATION";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_PROMOTION_ARTICLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                try {
                    Log.d("promotionarticle", "onResponse: "+response);
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray promotions_articles = jObj.getJSONArray("Promotionarticles");
                         //Toast.makeText(context, "nombre de promotions_articles "+promotions_articles.length(), Toast.LENGTH_SHORT).show();
                        if(promotions_articles.length()>0) {
                            PromotionarticleManager promotionarticleManager= new PromotionarticleManager(context);
                            Log.d("promotionarticle", "onResponse size: "+promotions_articles.length());

                            for (int i = 0; i < promotions_articles.length(); i++) {

                                JSONObject Unepromotions_article = promotions_articles.getJSONObject(i);
                                if (Unepromotions_article.getString("OPERATION").equals("DELETE")) {
                                    promotionarticleManager.delete(Unepromotions_article.getString("PROMO_CODE"),Unepromotions_article.getString("VERSION"));
                                    cptDelete++;
                                } else {
                                    Log.d("promotionarticle", "onResponse: insertion "+i);
                                    promotionarticleManager.add(new Promotionarticle(Unepromotions_article));
                                    cptInsert++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PROMOTION_AFFECTATION: Inserted: "+cptInsert +" Deleted: "+cptDelete ,"PROMOTION_AFFECTATION",1));

                        }

                        //logM.add("PROMOTION_AFFECTATION:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"PROMOTION_AFFECTATION");

                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(context, "PromoAR : "+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("PROMOTION_AFFECTATION:NOK"+ errorMsg ,"PROMOTION_AFFECTATION");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PROMOTION_AFFECTATION: Error: "+ errorMsg ,"PROMOTION_AFFECTATION",0));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "PromoAR : "+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("PROMOTION_AFFECTATION:NOK"+ e.getMessage() ,"PROMOTION_AFFECTATION");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PROMOTION_AFFECTATION: Error: "+ e.getMessage() ,"PROMOTION_AFFECTATION",0));

                    }


                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "PromoAR : "+error.getMessage(), Toast.LENGTH_LONG).show();
               //LogSyncManager logM= new LogSyncManager(context);
                //logM.add("PROMOTION_AFFECTATION:NOK"+ error.getMessage() ,"PROMOTION_AFFECTATION");
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PROMOTION_AFFECTATION: Error: "+ error.getMessage() ,"PROMOTION_AFFECTATION",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //Map<String, String> params = new HashMap<String, String>();
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    PromotionarticleManager promotionarticleManager = new PromotionarticleManager(context);
                    List<Promotionarticle> promotionarticles=promotionarticleManager.getList();

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    Log.d("UTILISATEUR_CODE PROMOTION ARTICLE SYNC",gson.toJson(pref.getString("UTILISATEUR_CODE",null)));

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(promotionarticles));
                    //Log.d("salim JSON",taches.toString());
                    //Log.d("salim JSON",gson.toJson(taches).toString());


                }
                return arrayFinale;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
