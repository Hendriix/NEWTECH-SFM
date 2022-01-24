package com.newtech.newtech_sfm.Metier_Manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Base64;
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
import com.newtech.newtech_sfm.Metier.ChoufouniContratImage;
import com.newtech.newtech_sfm.Metier.LogSync;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChoufouniContratImageManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    public static final String TABLE_CHOUFOUNI_IMAGE = "choufounicontratimage";


    public static final String
    KEY_CHOUFOUNI_CONTRAT_CODE = "CHOUFOUNI_CONTRAT_CODE",
    KEY_IMAGE_CODE = "IMAGE_CODE",
    KEY_IMAGE = "IMAGE",
    KEY_TYPE_CODE = "TYPE_CODE",
    KEY_CATEGORIE_CODE = "CATEGORIE_CODE",
    KEY_STATUT_CODE = "STATUT_CODE",
    KEY_COMMENTAIRE = "COMMENTAIRE",
    KEY_DATE_CREATION = "DATE_CREATION ",
    KEY_CREATEUR_CODE = "CREATEUR_CODE ",
    KEY_VERSION = "VERSION",
    KEY_GPS_LATITUDE="GPS_LATITUDE",
    KEY_GPS_LONGITUDE="GPS_LONGITUDE",
    KEY_DISTANCE="DISTANCE";

    public static String CREATE_CHOUFOUNI_IMAGE_TABLE = "CREATE TABLE " + TABLE_CHOUFOUNI_IMAGE+ " ("

            + KEY_CHOUFOUNI_CONTRAT_CODE + " TEXT,"
            + KEY_IMAGE_CODE + " TEXT,"
            + KEY_IMAGE + " TEXT PRIMARY KEY,"
            + KEY_TYPE_CODE + " TEXT,"
            + KEY_CATEGORIE_CODE + " TEXT,"
            + KEY_STATUT_CODE + " TEXT,"
            + KEY_COMMENTAIRE + " TEXT,"
            + KEY_DATE_CREATION + " TEXT,"
            + KEY_CREATEUR_CODE + " TEXT,"
            + KEY_VERSION + " TEXT,"
            + KEY_GPS_LATITUDE + " TEXT,"
            + KEY_GPS_LONGITUDE + " TEXT,"
            + KEY_DISTANCE + " NUMERIC"+ ")";

    public ChoufouniContratImageManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAMILLE);
            db.execSQL(CREATE_CHOUFOUNI_IMAGE_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database CHOUFOUNIIMAGE tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHOUFOUNI_IMAGE);
        // Create tables again
        onCreate(db);
    }

    public void add(ChoufouniContratImage choufouniContratImage) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_CHOUFOUNI_CONTRAT_CODE ,choufouniContratImage.getCHOUFOUNI_CONTRAT_CODE());
        values.put(KEY_IMAGE_CODE ,choufouniContratImage.getIMAGE_CODE());
        values.put(KEY_IMAGE ,choufouniContratImage.getIMAGE());
        values.put(KEY_TYPE_CODE ,choufouniContratImage.getTYPE_CODE());
        values.put(KEY_CATEGORIE_CODE ,choufouniContratImage.getCATEGORIE_CODE());
        values.put(KEY_STATUT_CODE ,choufouniContratImage.getSTATUT_CODE());
        values.put(KEY_COMMENTAIRE ,choufouniContratImage.getCOMMENTAIRE());
        values.put(KEY_DATE_CREATION ,choufouniContratImage.getDATE_CREATION());
        values.put(KEY_CREATEUR_CODE ,choufouniContratImage.getCREATEUR_CODE());
        values.put(KEY_VERSION ,choufouniContratImage.getVERSION());
        values.put(KEY_GPS_LATITUDE, choufouniContratImage.getGPS_LATITUDE());
        values.put(KEY_GPS_LONGITUDE, choufouniContratImage.getGPS_LONGITUDE());
        values.put(KEY_DISTANCE, choufouniContratImage.getDISTANCE());
        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_CHOUFOUNI_IMAGE, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
        Log.d("CHOUFOUNI_IMAGE MANAGER", "New choufouniContratImage inserted into sqlite: " + id);

    }

    public ChoufouniContratImage get(String IMAGE_CODE) {

        ChoufouniContratImage choufouniContratImage = new ChoufouniContratImage();

        String selectQuery = "SELECT * FROM " + TABLE_CHOUFOUNI_IMAGE+ " WHERE "+ KEY_IMAGE_CODE +" = '"+IMAGE_CODE+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {

            choufouniContratImage.setCHOUFOUNI_CONTRAT_CODE(cursor.getString(0));
            choufouniContratImage.setIMAGE_CODE(cursor.getString(1));
            choufouniContratImage.setIMAGE(cursor.getString(2));
            choufouniContratImage.setTYPE_CODE(cursor.getString(3));
            choufouniContratImage.setCATEGORIE_CODE(cursor.getString(4));
            choufouniContratImage.setSTATUT_CODE(cursor.getString(5));
            choufouniContratImage.setCOMMENTAIRE(cursor.getString(6));
            choufouniContratImage.setDATE_CREATION(cursor.getString(7));
            choufouniContratImage.setCREATEUR_CODE(cursor.getString(8));
            choufouniContratImage.setVERSION(cursor.getString(9));
            choufouniContratImage.setGPS_LATITUDE(cursor.getString(10));
            choufouniContratImage.setGPS_LONGITUDE(cursor.getString(11));
            choufouniContratImage.setDISTANCE(cursor.getInt(12));

        }

        cursor.close();
        db.close();
        Log.d("CHOUFOUNI CONTRAT M", "fetching ");
        return choufouniContratImage;

    }

    public ArrayList<ChoufouniContratImage> getList() {
        ArrayList<ChoufouniContratImage> choufouniContratImages = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CHOUFOUNI_IMAGE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                ChoufouniContratImage choufouniContratImage = new ChoufouniContratImage();
                choufouniContratImage.setCHOUFOUNI_CONTRAT_CODE(cursor.getString(0));
                choufouniContratImage.setIMAGE_CODE(cursor.getString(1));
                choufouniContratImage.setIMAGE(cursor.getString(2));
                choufouniContratImage.setTYPE_CODE(cursor.getString(3));
                choufouniContratImage.setCATEGORIE_CODE(cursor.getString(4));
                choufouniContratImage.setSTATUT_CODE(cursor.getString(5));
                choufouniContratImage.setCOMMENTAIRE(cursor.getString(6));
                choufouniContratImage.setDATE_CREATION(cursor.getString(7));
                choufouniContratImage.setCREATEUR_CODE(cursor.getString(8));
                choufouniContratImage.setVERSION(cursor.getString(9));
                choufouniContratImage.setGPS_LATITUDE(cursor.getString(10));
                choufouniContratImage.setGPS_LONGITUDE(cursor.getString(11));
                choufouniContratImage.setDISTANCE(cursor.getInt(12));

                choufouniContratImages.add(choufouniContratImage);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Fetching code/version choufouniContratImages from Sqlite: "+choufouniContratImages.size());
        return choufouniContratImages;
    }

    public ArrayList<ChoufouniContratImage> getListNotInserted() {
        ArrayList<ChoufouniContratImage> choufouniContratImages = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CHOUFOUNI_IMAGE +" WHERE "+KEY_COMMENTAIRE+" = 'to_insert' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                ChoufouniContratImage choufouniContratImage = new ChoufouniContratImage();
                choufouniContratImage.setCHOUFOUNI_CONTRAT_CODE(cursor.getString(0));
                choufouniContratImage.setIMAGE_CODE(cursor.getString(1));
                choufouniContratImage.setIMAGE(cursor.getString(2));
                choufouniContratImage.setTYPE_CODE(cursor.getString(3));
                choufouniContratImage.setCATEGORIE_CODE(cursor.getString(4));
                choufouniContratImage.setSTATUT_CODE(cursor.getString(5));
                choufouniContratImage.setCOMMENTAIRE(cursor.getString(6));
                choufouniContratImage.setDATE_CREATION(cursor.getString(7));
                choufouniContratImage.setCREATEUR_CODE(cursor.getString(8));
                choufouniContratImage.setVERSION(cursor.getString(9));
                choufouniContratImage.setGPS_LATITUDE(cursor.getString(10));
                choufouniContratImage.setGPS_LONGITUDE(cursor.getString(11));
                choufouniContratImage.setDISTANCE(cursor.getInt(12));

                choufouniContratImages.add(choufouniContratImage);

            }while(cursor.moveToNext());
        }
        //returner la listArticles;
        cursor.close();
        db.close();

        Log.d(TAG, "Nombre choufouniContratImages from table choufouniContratImages: "+choufouniContratImages.size());
        return choufouniContratImages;
    }

    public int delete(String choufouni_image_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CHOUFOUNI_IMAGE,KEY_IMAGE_CODE+"=?",new String[]{choufouni_image_code});
    }

    public void updateChoufouniContratImage(String choufouni_contrat_code,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "UPDATE " +TABLE_CHOUFOUNI_IMAGE + " SET "+KEY_COMMENTAIRE  +"= '"+msg+"' WHERE "+KEY_IMAGE_CODE +"= '"+choufouni_contrat_code+"'" ;
        db.execSQL(req);
        db.close();

        Log.d("commande", "updateCommande: "+req);
    }

    public void deleteChoufouniContratImageSynchronisee() {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DatefCmdAS = sdf.format(new Date());
        Log.d(TAG, "deleteChoufouniContratSynchronisee: "+DatefCmdAS);

        String Where = " "+KEY_COMMENTAIRE+"='inserted' and date("+KEY_DATE_CREATION+")!='"+DatefCmdAS+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows verified
        db.delete(TABLE_CHOUFOUNI_IMAGE, Where, null);
        db.close();

    }

    public static void synchronisationChoufouniContratImage(final Context context){

        ChoufouniContratImageManager choufouniContratImageManager = new ChoufouniContratImageManager(context);
        choufouniContratImageManager.deleteChoufouniContratImageSynchronisee();

        String tag_string_req = "CHOUFOUNI_CONTRAT_IMAGE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_CHOUFOUNI_CONTRAT_IMAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                int cptInsert = 0,cptDelete = 0;
                Log.d(TAG, "onResponse: ChoufouniContratImage"+ response);
                try {
                    Log.d(TAG, "onResponse: ChoufouniContratImage"+ response);
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray ChoufouniContratImages = jObj.getJSONArray("result");
                        //Toast.makeText(context, "Nombre de ChoufouniContratImage  "+ChoufouniContratImages.length() , Toast.LENGTH_LONG).show();
                        // for(int i=0;i<6;i++)Toast.makeText(context, "voila : " +response, Toast.LENGTH_LONG).show();
                        Log.d("debugg","--@--"+"les ChoufouniContratImages : " +response);

                        if(ChoufouniContratImages.length()>0) {
                            ChoufouniContratImageManager ChoufouniContratImageManager = new ChoufouniContratImageManager(context);
                            for (int i = 0; i < ChoufouniContratImages.length(); i++) {
                                JSONObject unChoufouniContratImage = ChoufouniContratImages.getJSONObject(i);
                                if (unChoufouniContratImage.getString("Statut").equals("true")) {
                                    ChoufouniContratImageManager.updateChoufouniContratImage((unChoufouniContratImage.getString("IMAGE_CODE")),"inserted");
                                    cptInsert++;
                                }else{
                                    cptDelete++;
                                }
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CHOUFOUNI_CONTRAT_IMAGE_CODE: Inserted: "+cptInsert +" NotInserted: "+cptDelete ,"CHOUFOUNI_CONTRAT_IMAGE",1));

                        }

                        //logM.add("ChoufouniContratImage:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncChoufouniContratImage");


                    }else {
                        String errorMsg = jObj.getString("info");
                        //Toast.makeText(context, "ChoufouniContratImage : "+errorMsg, Toast.LENGTH_LONG).show();
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CHOUFOUNI_CONTRAT_IMAGE_CODE: Error: "+errorMsg ,"CHOUFOUNI_CONTRAT_IMAGE",0));

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "onResponse: ChoufouniContratImage exception"+ e.getMessage());
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CHOUFOUNI_CONTRAT_IMAGE_CODE: Error: "+e.getMessage() ,"CHOUFOUNI_CONTRAT_IMAGE",0));

                    }
                    //Toast.makeText(context, "ChoufouniContratImage : "+"Json error: " +"erreur applcation ChoufouniContratImage" + e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "ChoufouniContratImage : "+error.getMessage(), Toast.LENGTH_LONG).show();
                Log.d(TAG, "onResponse: ChoufouniContratImage error "+ error.getMessage());
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("CHOUFOUNI_CONTRAT_IMAGE_CODE: Error: "+error.getMessage() ,"CHOUFOUNI_CONTRAT_IMAGE",0));

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    ChoufouniContratImageManager ChoufouniContratImageManager  = new ChoufouniContratImageManager(context);
                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("Table_Name","tableChoufouniContratImage");
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(choufouniContratImageManager.getListNotInserted()));

                    Log.d("ccISynch", "Liste: "+ChoufouniContratImageManager.getListNotInserted().toString());
                    Log.d(TAG, "getParams: listnotinserted "+ChoufouniContratImageManager.getListNotInserted().size());
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


    private static ArrayList<ChoufouniContratImage> getEncodedList(ArrayList<ChoufouniContratImage> choufouniContratImages){

        ArrayList<ChoufouniContratImage> choufouniContratImageArrayList = new ArrayList<>();
        for(int i=0 ; i<choufouniContratImages.size() ; i++){

            ChoufouniContratImage  choufouniContratImage = choufouniContratImages.get(i);

            Log.d(TAG, "enregistrerContrat: path 2.5  "+choufouniContratImage.getIMAGE());

            choufouniContratImage.setIMAGE(getBase64FromPath(choufouniContratImage.getIMAGE()));
            choufouniContratImageArrayList.add(choufouniContratImage);

            Log.d(TAG, "enregistrerContrat: path 3 "+choufouniContratImage.getIMAGE());
        }

        return choufouniContratImageArrayList;

    }

    public static String getBase64FromPath(String path) {
        String base64 = "";
        try {/*from   w w w .  ja  va  2s  .  c om*/

            File file = new File(path);
            byte[] buffer = new byte[(int) file.length() + 100];
            //@SuppressWarnings("resource")
            int length = new FileInputStream(file).read(buffer);
            base64 = Base64.encodeToString(buffer, 0, length,
                    Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }

    public String getEncodedImage(Bitmap bitmapDrawable){

        String encodeedImage = "";

        int bitmapWidth = 0 ;
        int bitmapHeight = 0;
        bitmapWidth = bitmapDrawable.getWidth();
        bitmapHeight = bitmapDrawable.getHeight();

        if(bitmapWidth>200 || bitmapHeight>200){

            bitmapWidth = 200;
            bitmapHeight = 200;
        }

        Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmapDrawable,bitmapWidth,bitmapHeight,true);

        //Log.d(TAG, "enregistrer_clientn: width3 "+bitmapScaled.getWidth());
        //Log.d(TAG, "enregistrer_clientn: height3 "+bitmapScaled.getHeight());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapScaled.compress(Bitmap.CompressFormat.JPEG,100,baos);

        byte[] imageBytes = baos.toByteArray();

        encodeedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);

        return encodeedImage;
    }
}
