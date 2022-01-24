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
import com.newtech.newtech_sfm.Activity.SyncV2Activity;
import com.newtech.newtech_sfm.Configuration.AppConfig;
import com.newtech.newtech_sfm.Configuration.AppController;
import com.newtech.newtech_sfm.Configuration.SQLiteHandler;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier.CommandePromotion;
import com.newtech.newtech_sfm.Metier.Livraison;
import com.newtech.newtech_sfm.Metier.LivraisonLigne;
import com.newtech.newtech_sfm.Metier.LivraisonPromotion;
import com.newtech.newtech_sfm.Metier.LogSync;
import com.newtech.newtech_sfm.Metier.Promotion;
import com.newtech.newtech_sfm.Metier.Promotionpalier;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stagiaireit2 on 22/07/2016.
 */
public class PromotionManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    // All Static variables

    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Article table name
    public static final String TABLE_PROMOTION = "promotion";



    // Promotion Table Columns names
    private static final String
            KEY_ID="ID",
            KEY_PROMO_CODE="PROMO_CODE",
            KEY_PROMO_NOM="PROMO_NOM",
            KEY_PROMO_DESCRIPTION1="PROMO_DESCRIPTION1",
            KEY_PROMO_CATEGORIE="PROMO_CATEGORIE",
            KEY_PROMO_TYPE="PROMO_TYPE",
            KEY_PROMO_STATUT="PROMO_STATUT",
            KEY_DATE_DEBUT="DATE_DEBUT",
            KEY_DATE_FIN="DATE_FIN",
            KEY_PERIODE_CODE="PERIODE_CODE",
            KEY_CODE_PRIORITAIRE="CODE_PRIORITAIRE",
            KEY_PROMO_BASE="PROMO_BASE",
            KEY_PROMO_MODECALCUL="PROMO_MODECALCUL",
            KEY_PROMO_NIVEAU="PROMO_NIVEAU",
            KEY_PROMO_APPLIQUESUR="PROMO_APPLIQUESUR",
            KEY_PROMO_REPETITIONPALIER="PROMO_REPETITIONPALIER",
            KEY_INACTIF="INACTIF",
            KEY_RAISON_INACTIF="RAISON_INACTIF",
            KEY_CREATEUR_CODE="CREATEUR_CODE",
            KEY_DATE_CREATION="DATE_CREATION",
            KEY_TS="TS",
            KEY_COMMENTAIRE="COMMENTAIRE",
            KEY_VERSION="VERSION";

    public static  String CREATE_PROMOTION_TABLE = "CREATE TABLE " + TABLE_PROMOTION + "("
            +KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            +KEY_PROMO_CODE + " TEXT,"
            +KEY_PROMO_NOM + " TEXT,"
            +KEY_PROMO_DESCRIPTION1 + " TEXT,"
            +KEY_PROMO_CATEGORIE + " TEXT,"
            +KEY_PROMO_TYPE + " TEXT,"
            +KEY_PROMO_STATUT + " TEXT,"
            +KEY_DATE_DEBUT + " TEXT,"
            +KEY_DATE_FIN + " TEXT,"
            +KEY_PERIODE_CODE + " TEXT,"
            +KEY_CODE_PRIORITAIRE + " NUMERIC,"
            +KEY_PROMO_BASE + " TEXT,"
            +KEY_PROMO_MODECALCUL + " TEXT,"
            +KEY_PROMO_NIVEAU + " TEXT,"
            +KEY_PROMO_APPLIQUESUR + " TEXT,"
            +KEY_PROMO_REPETITIONPALIER + " TEXT,"
            +KEY_INACTIF + " NUMERIC,"
            +KEY_RAISON_INACTIF + " TEXT,"
            +KEY_CREATEUR_CODE + " TEXT,"
            +KEY_DATE_CREATION + " TEXT,"
            +KEY_TS + " TEXT,"
            +KEY_COMMENTAIRE + " TEXT,"
            +KEY_VERSION + " TEXT" + ")";


    public PromotionManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_PROMOTION);
            db.execSQL(CREATE_PROMOTION_TABLE);

        } catch (SQLException e) {

        }
        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROMOTION);
        // Create tables again
        onCreate(db);
    }
    /**
     * Storing Articles details in database
     * */
    public void add(Promotion promotion) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(promotion.getPROMO_CODE()!=null) {
            ContentValues values = new ContentValues();
            values.put(KEY_PROMO_CODE, promotion.getPROMO_CODE());
            values.put(KEY_PROMO_NOM, promotion.getPROMO_NOM());
            values.put(KEY_PROMO_DESCRIPTION1, promotion.getPROMO_DESCRIPTION1());
            values.put(KEY_PROMO_CATEGORIE, promotion.getPROMO_CATEGORIE());
            values.put(KEY_PROMO_TYPE, promotion.getPROMO_TYPE());
            values.put(KEY_PROMO_STATUT, promotion.getPROMO_STATUT());
            values.put(KEY_DATE_DEBUT, promotion.getDATE_DEBUT());
            values.put(KEY_DATE_FIN, promotion.getDATE_FIN());
            values.put(KEY_PERIODE_CODE, promotion.getPERIODE_CODE());
            values.put(KEY_CODE_PRIORITAIRE, promotion.getCODE_PRIORITAIRE());
            values.put(KEY_PROMO_BASE, promotion.getPROMO_BASE());
            values.put(KEY_PROMO_MODECALCUL, promotion.getPROMO_MODECALCUL());
            values.put(KEY_PROMO_NIVEAU, promotion.getPROMO_NIVEAU());
            values.put(KEY_PROMO_APPLIQUESUR, promotion.getPROMO_APPLIQUESUR());
            values.put(KEY_PROMO_REPETITIONPALIER, promotion.getPROMO_REPETITIONPALIER());
            values.put(KEY_INACTIF, promotion.getINACTIF());
            values.put(KEY_RAISON_INACTIF, promotion.getRAISON_INACTIF());
            values.put(KEY_CREATEUR_CODE, promotion.getCREATEUR_CODE());
            values.put(KEY_DATE_CREATION, promotion.getDATE_CREATION());
            values.put(KEY_TS, promotion.getTS());
            values.put(KEY_COMMENTAIRE, promotion.getCOMMENTAIRE());
            values.put(KEY_VERSION, promotion.getVERSION());

            // Inserting Row
            long id = db.insertWithOnConflict(TABLE_PROMOTION, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            db.close(); // Closing database connection

            Log.d(TAG, "New articles inserted into sqlite: " + id);
        }
    }


    /**
     * Getting user data from database
     * */
    public ArrayList<Promotion> getList() {
        ArrayList<Promotion> listPromotions = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_PROMOTION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Promotion promotion = new Promotion();
                promotion.setPROMO_CODE(cursor.getString(1));
                promotion.setPROMO_NOM(cursor.getString(2));
                promotion.setPROMO_DESCRIPTION1(cursor.getString(3));
                promotion.setPROMO_CATEGORIE(cursor.getString(4));
                promotion.setPROMO_TYPE(cursor.getString(5));
                promotion.setPROMO_STATUT(cursor.getString(6));
                promotion.setDATE_DEBUT(cursor.getString(7));
                promotion.setDATE_FIN(cursor.getString(8));
                promotion.setPERIODE_CODE(cursor.getString(9));
                promotion.setCODE_PRIORITAIRE(cursor.getInt(10));
                promotion.setPROMO_BASE(cursor.getString(11));
                promotion.setPROMO_MODECALCUL(cursor.getString(12));
                promotion.setPROMO_NIVEAU(cursor.getString(13));
                promotion.setPROMO_APPLIQUESUR(cursor.getString(14));
                promotion.setPROMO_REPETITIONPALIER(cursor.getString(15));
                promotion.setINACTIF(cursor.getInt(16));
                promotion.setRAISON_INACTIF(cursor.getString(17));
                promotion.setCREATEUR_CODE(cursor.getString(18));
                promotion.setDATE_CREATION(cursor.getString(19));
                promotion.setTS(cursor.getString(20));
                promotion.setCOMMENTAIRE(cursor.getString(21));
                promotion.setVERSION(cursor.getString(22));

                listPromotions.add(promotion);
                //Log.d("promotion", " : " + promotion.toString());
            }while(cursor.moveToNext());
        }

        //retourner la listPromotions;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Promotions from Sqlite: ");
        return listPromotions;
    }

    /**
     * Getting list by date
     * */
    public ArrayList<Promotion> getListByDate(String Date){
        ArrayList<Promotion> listPromotions = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_PROMOTION + " WHERE DATE('"+Date+"') BETWEEN DATE(" + KEY_DATE_DEBUT + ") and DATE(" + KEY_DATE_FIN+") ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "Fetching Promotions from Sqlite: "+selectQuery+"  cursor "+cursor.getCount());
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Promotion promotion = new Promotion();
                promotion.setPROMO_CODE(cursor.getString(1));
                promotion.setPROMO_NOM(cursor.getString(2));
                promotion.setPROMO_DESCRIPTION1(cursor.getString(3));
                promotion.setPROMO_CATEGORIE(cursor.getString(4));
                promotion.setPROMO_TYPE(cursor.getString(5));
                promotion.setPROMO_STATUT(cursor.getString(6));
                promotion.setDATE_DEBUT(cursor.getString(7));
                promotion.setDATE_FIN(cursor.getString(8));
                promotion.setPERIODE_CODE(cursor.getString(9));
                promotion.setCODE_PRIORITAIRE(cursor.getInt(10));
                promotion.setPROMO_BASE(cursor.getString(11));
                promotion.setPROMO_MODECALCUL(cursor.getString(12));
                promotion.setPROMO_NIVEAU(cursor.getString(13));
                promotion.setPROMO_APPLIQUESUR(cursor.getString(14));
                promotion.setPROMO_REPETITIONPALIER(cursor.getString(15));
                promotion.setINACTIF(cursor.getInt(16));
                promotion.setRAISON_INACTIF(cursor.getString(17));
                promotion.setCREATEUR_CODE(cursor.getString(18));
                promotion.setDATE_CREATION(cursor.getString(19));
                promotion.setTS(cursor.getString(20));
                promotion.setCOMMENTAIRE(cursor.getString(21));
                promotion.setVERSION(cursor.getString(22));

                listPromotions.add(promotion);
                Log.d(TAG, "Fetching Promotions from Sqlite: "+promotion.getPROMO_CODE());
            }while(cursor.moveToNext());
        }

        //returner la listPromotions;
        cursor.close();
        db.close();
        //Log.d(TAG, "date"+Date);
        Log.d(TAG, "Fetching Promotions from Sqlite: ");
        return listPromotions;
    }

    public ArrayList<Promotion> getPromotionCode_Version_List() {
        ArrayList<Promotion> listPromotion = new ArrayList<>();
        String selectQuery = "SELECT " + KEY_PROMO_CODE + "," + KEY_VERSION + " FROM " + TABLE_PROMOTION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Promotion pr = new Promotion();
                pr.setPROMO_CODE(cursor.getString(0));
                pr.setVERSION(cursor.getString(1));
                listPromotion.add(pr);
            } while (cursor.moveToNext());
        }
        return listPromotion;
    }

    public ArrayList<CommandePromotion> GetPromoAP_LIV_QL(Commande CMD, ArrayList<CommandeLigne> list_LCMD,Context context){
        ArrayList<CommandePromotion> list_PromotionCMD = new ArrayList<CommandePromotion>();
        ArrayList<CommandePromotion> list_PromoAP = new ArrayList<CommandePromotion>();
        try {


            PromotionaffectationManager promoAF_Manager = new PromotionaffectationManager(context);
            PromotionarticleManager promoAR_Manager = new PromotionarticleManager(context);
            PromotionpalierManager promoPL_Manager = new PromotionpalierManager(context);
            ClientManager client_Manager = new ClientManager(context);
            Promotionpalier max_PromoPalier;
            Double valeur_Promo = 0.0;

            ArrayList<Promotion> list_Promotion = new ArrayList<Promotion>();
            ArrayList<Promotion> list_Promo_AppliqueesAF = new ArrayList<Promotion>();
            ArrayList<Promotion> list_Promo_AppliqueesAR = new ArrayList<Promotion>();
            ArrayList<Promotion> list_Promo_AppliqueesPL = new ArrayList<Promotion>();

            ArrayList<Promotionpalier> list_PromotionPalier = new ArrayList<Promotionpalier>();
            ArrayList<Promotionpalier> list_PromotionPalierCMD = new ArrayList<Promotionpalier>();

            //list_Promo_AppliqueesPL=this.getList();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
            String date_string = CMD.getDATE_LIVRAISON().substring(0, 10);
            Date date_livraison = null;
            String sdate_livraison = null;
            try {
                date_livraison = df.parse(date_string);
                sdate_livraison = df.format(date_livraison);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            list_Promotion = getListByDate(sdate_livraison);
            //Log.d("Remise", "date1= "+sdate_livraison+" Date2: "+date_string);
            Log.d("Remise", "list_Promotion = " + list_Promotion.size());
            //###################################################### Affectation #############################################################
            if (list_Promotion.size() > 0) {
                Client client = client_Manager.get(CMD.getCLIENT_CODE());
                for (int i = 0; i < list_Promotion.size(); i++) {
                    if (promoAF_Manager.check_Affectation(list_Promotion.get(i).getPROMO_CODE(), client)) {
                        list_Promo_AppliqueesAF.add(list_Promotion.get(i));
                    }
                }
            }
            Log.d("Remise", "list_Promo_AppliqueesAF = " + list_Promo_AppliqueesAF.size());
            Log.d("Remise", "list_Promo_AppliqueesAF = " + list_Promo_AppliqueesAF.toString());
            //###################################################### Implication #############################################################
            if (list_Promo_AppliqueesAF.size() > 0) {
                for (int i = 0; i < list_Promo_AppliqueesAF.size(); i++) {
                    if (promoAR_Manager.Check_Implication(list_Promo_AppliqueesAF.get(i).getPROMO_CODE(), list_LCMD)) {
                        list_Promo_AppliqueesAR.add(list_Promo_AppliqueesAF.get(i));
                    }
                }
            }
            Log.d("Remise", "list_Promo_AppliqueesAR = " + list_Promo_AppliqueesAR.size());
            //###################################################### calcul ligne#############################################################
            if (list_Promo_AppliqueesAR.size() > 0) {
                Double cmd_MBrute = 0.0;
                Double cmd_MNET = 0.0;

                for (int i = 0; i < list_LCMD.size(); i++) {//boucle ligne cmd
                    Double lcmd_MBrute = list_LCMD.get(i).getMONTANT_BRUT();
                    Double lcmd_remise = 0.0;
                    Double lcmd_MNet = lcmd_MBrute;
                    CommandeLigne ligne_CMD = list_LCMD.get(i);

                    for (int j = 0; j < list_Promo_AppliqueesAR.size(); j++) {//boucle ligne promo appliqué et impliqué
                        if (promoAR_Manager.Check_ImplicationLCMD(list_Promo_AppliqueesAR.get(j).getPROMO_CODE(), ligne_CMD)) {
                            if (list_Promo_AppliqueesAR.get(j).getPROMO_NIVEAU().equals("CA0018")) {//Ligne
                                //calcul qte/valeur cmd
                                Double valeur_LCMD = 0.0;
                                if (list_Promo_AppliqueesAR.get(j).getPROMO_BASE().equals("CA0013")) {//caisse
                                    valeur_LCMD = ligne_CMD.getCAISSE_LIVREE();
                                } else if (list_Promo_AppliqueesAR.get(j).getPROMO_BASE().equals("CA0014")) {//Tonne
                                    valeur_LCMD = ligne_CMD.getTONNAGE_LIVREE();
                                } else if (list_Promo_AppliqueesAR.get(j).getPROMO_BASE().equals("CA0021")) {//Littre
                                    valeur_LCMD = ligne_CMD.getLITTRAGE_LIVREE();
                                } else {
                                    valeur_LCMD = 0.0;
                                }


                                list_PromotionPalier = promoPL_Manager.getPromoPalierByVBase(list_Promo_AppliqueesAR.get(j).getPROMO_CODE(), list_Promo_AppliqueesAR.get(j).getPROMO_BASE(), valeur_LCMD);
                                if (list_PromotionPalier.size() > 0) {
                                    max_PromoPalier = promoPL_Manager.getMaxPromoPalierByVBase(list_Promo_AppliqueesAR.get(j).getPROMO_CODE(), list_Promo_AppliqueesAR.get(j).getPROMO_BASE(), valeur_LCMD);
                                    if (list_Promo_AppliqueesAR.get(j).getPROMO_MODECALCUL().equals("CA0015")) {//taux
                                        if (list_Promo_AppliqueesAR.get(j).getPROMO_REPETITIONPALIER().equals("1")) {//repetition
                                            if (list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR().equals("CA0019")) {//surNet
                                                valeur_Promo = lcmd_MNet * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                            } else if (list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR().equals("CA0020")) {//sur base
                                                valeur_Promo = lcmd_MBrute * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                            }
                                            CommandePromotion commandePromotion = new CommandePromotion();

                                            commandePromotion.setCOMMANDE_PROMO_CODE(CMD.getCOMMANDE_CODE() + list_Promo_AppliqueesAR.get(j).getPROMO_CODE() + list_LCMD.get(i).getCOMMANDELIGNE_CODE());
                                            commandePromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(j).getPROMO_CODE());
                                            commandePromotion.setCOMMANDE_CODE(CMD.getCOMMANDE_CODE());
                                            int cmdligne = list_LCMD.get(i).getCOMMANDELIGNE_CODE();
                                            commandePromotion.setCOMMANDELIGNE_CODE(Integer.valueOf(cmdligne));
                                            commandePromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(j).getPROMO_NIVEAU());
                                            commandePromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(j).getPROMO_MODECALCUL());
                                            commandePromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR());
                                            commandePromotion.setMONTANT_BRUTE_AV(lcmd_MBrute);
                                            commandePromotion.setMONTANT_NET_AV(lcmd_MNet);
                                            commandePromotion.setVALEUR_PROMO(valeur_Promo);
                                            commandePromotion.setMONTANT_NET_AP(lcmd_MNet - valeur_Promo);
                                            commandePromotion.setGRATUITE_CODE("");

                                            list_PromotionCMD.add(commandePromotion);


                                            lcmd_MNet += lcmd_MNet - valeur_Promo;
                                            lcmd_remise += valeur_Promo;

                                        } else if (list_Promo_AppliqueesAR.get(j).getPROMO_REPETITIONPALIER().equals("0")) {//repetition
                                            Double valeurbase = Double.parseDouble(max_PromoPalier.getVALEUR_PBASE());
                                            Double d = valeur_LCMD / valeurbase;
                                            Integer repetitionPL = d.intValue();

                                            for (int h = 1; h <= repetitionPL; h++) {
                                                if (list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR().equals("CA0019")) {//surNet
                                                    valeur_Promo = lcmd_MNet * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                                    lcmd_remise += valeur_Promo;
                                                } else if (list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR().equals("CA0020")) {//sur base
                                                    valeur_Promo = lcmd_MBrute * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                                    lcmd_remise += valeur_Promo;
                                                }
                                                CommandePromotion commandePromotion = new CommandePromotion();

                                                commandePromotion.setCOMMANDE_PROMO_CODE(CMD.getCOMMANDE_CODE() + list_Promo_AppliqueesAR.get(j).getPROMO_CODE() + list_LCMD.get(i).getCOMMANDELIGNE_CODE());
                                                commandePromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(j).getPROMO_CODE());
                                                commandePromotion.setCOMMANDE_CODE(CMD.getCOMMANDE_CODE());
                                                int cmdligne = list_LCMD.get(i).getCOMMANDELIGNE_CODE();
                                                commandePromotion.setCOMMANDELIGNE_CODE(Integer.valueOf(cmdligne));
                                                commandePromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(j).getPROMO_NIVEAU());
                                                commandePromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(j).getPROMO_MODECALCUL());
                                                commandePromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR());
                                                commandePromotion.setMONTANT_BRUTE_AV(lcmd_MBrute);
                                                commandePromotion.setMONTANT_NET_AV(lcmd_MNet);
                                                commandePromotion.setVALEUR_PROMO(valeur_Promo);
                                                commandePromotion.setMONTANT_NET_AP(lcmd_MNet - valeur_Promo);
                                                commandePromotion.setGRATUITE_CODE("");

                                                list_PromotionCMD.add(commandePromotion);


                                                lcmd_MNet += lcmd_MNet - valeur_Promo;
                                            }

                                        } else {
                                            Double valeurbase = Double.parseDouble(max_PromoPalier.getVALEUR_PBASE());
                                            Double d = valeur_LCMD / valeurbase;
                                            Integer repetitionPL = d.intValue();
                                            Integer promoRPL = Integer.valueOf(list_Promo_AppliqueesAR.get(j).getPROMO_REPETITIONPALIER());

                                            while (promoRPL <= repetitionPL) {

                                                if (list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR().equals("CA0019")) {//surNet
                                                    valeur_Promo = lcmd_MNet * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                                    lcmd_remise += valeur_Promo;
                                                } else if (list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR().equals("CA0020")) {//sur base
                                                    valeur_Promo = lcmd_MBrute * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                                    lcmd_remise += valeur_Promo;
                                                }

                                                CommandePromotion commandePromotion = new CommandePromotion();

                                                commandePromotion.setCOMMANDE_PROMO_CODE(CMD.getCOMMANDE_CODE() + list_Promo_AppliqueesAR.get(j).getPROMO_CODE() + list_LCMD.get(i).getCOMMANDELIGNE_CODE());
                                                commandePromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(j).getPROMO_CODE());
                                                commandePromotion.setCOMMANDE_CODE(CMD.getCOMMANDE_CODE());
                                                int cmdligne = list_LCMD.get(i).getCOMMANDELIGNE_CODE();
                                                commandePromotion.setCOMMANDELIGNE_CODE(Integer.valueOf(cmdligne));
                                                commandePromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(j).getPROMO_NIVEAU());
                                                commandePromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(j).getPROMO_MODECALCUL());
                                                commandePromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR());
                                                commandePromotion.setMONTANT_BRUTE_AV(lcmd_MBrute);
                                                commandePromotion.setMONTANT_NET_AV(lcmd_MNet);
                                                commandePromotion.setVALEUR_PROMO(valeur_Promo);
                                                commandePromotion.setMONTANT_NET_AP(lcmd_MNet - valeur_Promo);
                                                commandePromotion.setGRATUITE_CODE("");

                                                list_PromotionCMD.add(commandePromotion);

                                                lcmd_MNet += lcmd_MNet - valeur_Promo;

                                                promoRPL++;

                                            }
                                        }

                                    } else if (list_Promo_AppliqueesAR.get(j).getPROMO_MODECALCUL().equals("CA0016")) {//Gratuite
                                        if (list_Promo_AppliqueesAR.get(j).getPROMO_REPETITIONPALIER().equals("1")) {//repetition
                                            Integer repetitionPL = 1;
                                            valeur_Promo = 1.0;
                                        } else if (list_Promo_AppliqueesAR.get(j).getPROMO_REPETITIONPALIER().equals("0")) {//repetition
                                            Double valeurbase = Double.parseDouble(max_PromoPalier.getVALEUR_PBASE());
                                            Double d = valeur_LCMD / valeurbase;
                                            Integer repetitionPL = d.intValue();
                                            valeur_Promo = Double.valueOf(repetitionPL);

                                        } else {
                                            Double valeurbase = Double.parseDouble(max_PromoPalier.getVALEUR_PBASE());
                                            Double d = valeur_LCMD / valeurbase;
                                            Integer repetitionPL = d.intValue();
                                            Integer promoRPL = Integer.valueOf(list_Promo_AppliqueesAR.get(j).getPROMO_REPETITIONPALIER());
                                            Integer repetitionAP = 0;

                                            while (promoRPL <= repetitionPL) {
                                                repetitionAP++;
                                                promoRPL++;
                                            }
                                            valeur_Promo = Double.valueOf(repetitionAP);
                                        }

                                        CommandePromotion commandePromotion = new CommandePromotion();

                                        commandePromotion.setCOMMANDE_PROMO_CODE(CMD.getCOMMANDE_CODE() + list_Promo_AppliqueesAR.get(j).getPROMO_CODE() + list_LCMD.get(i).getCOMMANDELIGNE_CODE());
                                        commandePromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(j).getPROMO_CODE());
                                        commandePromotion.setCOMMANDE_CODE(CMD.getCOMMANDE_CODE());
                                        int cmdligne = list_LCMD.get(i).getCOMMANDELIGNE_CODE();
                                        commandePromotion.setCOMMANDELIGNE_CODE(Integer.valueOf(cmdligne));
                                        commandePromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(j).getPROMO_NIVEAU());
                                        commandePromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(j).getPROMO_MODECALCUL());
                                        commandePromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR());
                                        commandePromotion.setMONTANT_BRUTE_AV(lcmd_MBrute);
                                        commandePromotion.setMONTANT_NET_AV(lcmd_MNet);
                                        commandePromotion.setVALEUR_PROMO(valeur_Promo);
                                        commandePromotion.setMONTANT_NET_AP(lcmd_MNet);
                                        commandePromotion.setGRATUITE_CODE(list_Promo_AppliqueesAR.get(j).getPROMO_CODE() + list_LCMD.get(i).getCOMMANDELIGNE_CODE());

                                        list_PromotionCMD.add(commandePromotion);


                                    }


                                }


                            }
                        }
                    }//fin boucle ligne promo appliquee

                    list_LCMD.get(i).setMONTANT_NET(lcmd_MNet);
                    list_LCMD.get(i).setREMISE(lcmd_remise);


                }//fin boucle ligne cmd


                //####################################################   Total
                Double RTC_CMD = 0.0;
                Double RTT_CMD = 0.0;
                Double RTL_CMD = 0.0;
                Double RTMB_CMD = 0.0;
                Double RTRS_CMD = 0.0;
                Double RTMN_CMD = 0.0;

                for (int i = 0; i < list_LCMD.size(); i++) {//boucle ligne cmd

                    RTC_CMD += list_LCMD.get(i).getCAISSE_LIVREE();
                    RTT_CMD += list_LCMD.get(i).getTONNAGE_LIVREE();
                    RTL_CMD += list_LCMD.get(i).getLITTRAGE_LIVREE();
                    RTMB_CMD += list_LCMD.get(i).getMONTANT_BRUT();
                    RTRS_CMD += list_LCMD.get(i).getREMISE();
                    RTMN_CMD += list_LCMD.get(i).getMONTANT_NET();
                }


                //####################################################   COMMANDE
                Double valeurCMD = 0.0;
                for (int i = 0; i < list_Promo_AppliqueesAR.size(); i++) {//boucle ligne promo appliqué et impliqué
                    if (list_Promo_AppliqueesAR.get(i).getPROMO_NIVEAU().equals("CA0017")) {//Commande

                        ArrayList<CommandeLigne> list_lCMD_IMP = new ArrayList<CommandeLigne>();

                        list_lCMD_IMP = promoAR_Manager.get_LCMDIMP(list_Promo_AppliqueesAR.get(i).getPROMO_CODE(), list_LCMD);

                        if (list_lCMD_IMP.size() > 0) {//check implication ligne cmd
                            Double RTC_CMD_IMP = 0.0;
                            Double RTT_CMD_IMP = 0.0;
                            Double RTL_CMD_IMP = 0.0;
                            Double RTMB_CMD_IMP = 0.0;
                            Double RTRS_CMD_IMP = 0.0;
                            Double RTMN_CMD_IMP = 0.0;

                            for (int j = 0; j < list_lCMD_IMP.size(); j++) {
                                RTC_CMD_IMP += list_lCMD_IMP.get(j).getCAISSE_LIVREE();
                                RTT_CMD_IMP += list_lCMD_IMP.get(j).getTONNAGE_LIVREE();
                                RTL_CMD_IMP += list_lCMD_IMP.get(j).getLITTRAGE_LIVREE();
                                RTMB_CMD_IMP += list_lCMD_IMP.get(j).getMONTANT_BRUT();
                                RTRS_CMD_IMP += list_lCMD_IMP.get(j).getREMISE();
                                RTMN_CMD_IMP += list_lCMD_IMP.get(j).getMONTANT_NET();
                            }

                            if (list_Promo_AppliqueesAR.get(i).getPROMO_BASE().equals("CA0013")) {//caisse
                                valeurCMD = RTC_CMD_IMP;
                            } else if (list_Promo_AppliqueesAR.get(i).getPROMO_BASE().equals("CA0014")) {//tonne
                                valeurCMD = RTT_CMD_IMP;
                            } else if (list_Promo_AppliqueesAR.get(i).getPROMO_BASE().equals("CA0021")) {//litre
                                valeurCMD = RTL_CMD_IMP;
                            } else if (list_Promo_AppliqueesAR.get(i).getPROMO_BASE().equals("CA0025")) {//ca
                                valeurCMD = RTMN_CMD_IMP;
                            } else {
                                valeurCMD = 0.0;
                            }
                            //Log.d("Remise", "valeurCMD = " + valeurCMD);
                            Double valeur_PROMO = 0.0;
                            list_PromotionPalierCMD = promoPL_Manager.getPromoPalierByVBase(list_Promo_AppliqueesAR.get(i).getPROMO_CODE(), list_Promo_AppliqueesAR.get(i).getPROMO_BASE(), valeurCMD);

                            Log.d("Remise", "CMD list_PromotionPalierCMD = " + list_PromotionPalierCMD.size());

                            if (list_PromotionPalierCMD.size() > 0) {
                                max_PromoPalier = promoPL_Manager.getMaxPromoPalierByVBase(list_Promo_AppliqueesAR.get(i).getPROMO_CODE(), list_Promo_AppliqueesAR.get(i).getPROMO_BASE(), valeurCMD);
                                Log.d("Remise", "max_PromoPalier = "+max_PromoPalier.toString());
                                Double valeurbase = Double.parseDouble(max_PromoPalier.getVALEUR_PBASE());

                                if (list_Promo_AppliqueesAR.get(i).getPROMO_MODECALCUL().equals("CA0015")) {//Taux

                                    if (list_Promo_AppliqueesAR.get(i).getPROMO_REPETITIONPALIER().equals("1")) {//repetition

                                        if (list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR().equals("CA0019")) {//sur net

                                            valeur_PROMO = RTMN_CMD_IMP * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                        } else if (list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR().equals("CA0020")) {//sur brut
                                            valeur_PROMO = RTMB_CMD_IMP * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                        }

                                        CommandePromotion commandePromotion = new CommandePromotion();

                                        commandePromotion.setCOMMANDE_PROMO_CODE(CMD.getCOMMANDE_CODE() + list_Promo_AppliqueesAR.get(i).getPROMO_CODE() + "0");
                                        commandePromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(i).getPROMO_CODE());
                                        commandePromotion.setCOMMANDE_CODE(CMD.getCOMMANDE_CODE());
                                        commandePromotion.setCOMMANDELIGNE_CODE(0);
                                        commandePromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(i).getPROMO_NIVEAU());
                                        commandePromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(i).getPROMO_MODECALCUL());
                                        commandePromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR());
                                        commandePromotion.setMONTANT_BRUTE_AV(RTMB_CMD);
                                        commandePromotion.setMONTANT_NET_AV(RTMN_CMD);
                                        commandePromotion.setVALEUR_PROMO(valeur_PROMO);
                                        commandePromotion.setMONTANT_NET_AP(RTMN_CMD - valeur_PROMO);
                                        commandePromotion.setGRATUITE_CODE("");

                                        list_PromotionCMD.add(commandePromotion);

                                        RTMN_CMD += RTMN_CMD - valeur_PROMO;
                                    } else if (list_Promo_AppliqueesAR.get(i).getPROMO_REPETITIONPALIER().equals("0")) {//repetition
                                        Double d = valeurCMD / valeurbase;
                                        Integer repetitionPL = d.intValue();

                                        for (int h = 1; h <= repetitionPL; h++) {
                                            if (list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR().equals("CA0019")) {//sur net
                                                valeur_PROMO = RTMN_CMD_IMP * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                            } else if (list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR().equals("CA0020")) {//sur brut
                                                valeur_PROMO = RTMB_CMD_IMP * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                            }
                                            CommandePromotion commandePromotion = new CommandePromotion();

                                            commandePromotion.setCOMMANDE_PROMO_CODE(CMD.getCOMMANDE_CODE() + list_Promo_AppliqueesAR.get(i).getPROMO_CODE() + "0");
                                            commandePromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(i).getPROMO_CODE());
                                            commandePromotion.setCOMMANDE_CODE(CMD.getCOMMANDE_CODE());
                                            commandePromotion.setCOMMANDELIGNE_CODE(0);
                                            commandePromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(i).getPROMO_NIVEAU());
                                            commandePromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(i).getPROMO_MODECALCUL());
                                            commandePromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR());
                                            commandePromotion.setMONTANT_BRUTE_AV(RTMB_CMD);
                                            commandePromotion.setMONTANT_NET_AV(RTMN_CMD);
                                            commandePromotion.setVALEUR_PROMO(valeur_PROMO);
                                            commandePromotion.setMONTANT_NET_AP(RTMN_CMD - valeur_PROMO);
                                            commandePromotion.setGRATUITE_CODE("");

                                            list_PromotionCMD.add(commandePromotion);

                                            RTMN_CMD += RTMN_CMD - valeur_PROMO;
                                        }

                                    } else {
                                        Double d = valeurCMD / valeurbase;
                                        Integer repetitionPL = d.intValue();
                                        Integer promoRPL = Integer.valueOf(list_Promo_AppliqueesAR.get(i).getPROMO_REPETITIONPALIER());

                                        while (promoRPL <= repetitionPL) {
                                            if (list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR().equals("CA0019")) {//sur net
                                                valeur_PROMO = RTMN_CMD_IMP * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                            } else if (list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR().equals("CA0020")) {//sur brut
                                                valeur_PROMO = RTMB_CMD_IMP * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                            }
                                            CommandePromotion commandePromotion = new CommandePromotion();

                                            commandePromotion.setCOMMANDE_PROMO_CODE(CMD.getCOMMANDE_CODE() + list_Promo_AppliqueesAR.get(i).getPROMO_CODE() + "0");
                                            commandePromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(i).getPROMO_CODE());
                                            commandePromotion.setCOMMANDE_CODE(CMD.getCOMMANDE_CODE());
                                            commandePromotion.setCOMMANDELIGNE_CODE(0);
                                            commandePromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(i).getPROMO_NIVEAU());
                                            commandePromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(i).getPROMO_MODECALCUL());
                                            commandePromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR());
                                            commandePromotion.setMONTANT_BRUTE_AV(RTMB_CMD);
                                            commandePromotion.setMONTANT_NET_AV(RTMN_CMD);
                                            commandePromotion.setVALEUR_PROMO(valeur_PROMO);
                                            commandePromotion.setMONTANT_NET_AP(RTMN_CMD - valeur_PROMO);
                                            commandePromotion.setGRATUITE_CODE("");

                                            list_PromotionCMD.add(commandePromotion);

                                            RTMN_CMD += RTMN_CMD - valeur_PROMO;
                                            promoRPL++;
                                        }
                                    }//fin repetition

                                } else if (list_Promo_AppliqueesAR.get(i).getPROMO_MODECALCUL().equals("CA0016")) {//Gratuite

                                    if (list_Promo_AppliqueesAR.get(i).getPROMO_REPETITIONPALIER().equals("1")) {//repetition
                                        Integer repetitionPL = 1;
                                        valeur_Promo = 1.0;
                                    } else if (list_Promo_AppliqueesAR.get(i).getPROMO_REPETITIONPALIER().equals("0")) {//repetition

                                        Double d = valeurCMD / valeurbase;
                                        Integer repetitionPL = d.intValue();
                                        valeur_Promo = Double.valueOf(repetitionPL);
                                    } else {

                                        Double d = valeurCMD / valeurbase;
                                        Integer repetitionPL = d.intValue();
                                        Integer promoRPL = Integer.valueOf(list_Promo_AppliqueesAR.get(i).getPROMO_REPETITIONPALIER());
                                        Integer repetitionAP = 0;

                                        while (promoRPL <= repetitionPL) {
                                            repetitionAP++;
                                            promoRPL++;
                                        }
                                        valeur_Promo = Double.valueOf(repetitionAP);
                                    }

                                    CommandePromotion commandePromotion = new CommandePromotion();

                                    commandePromotion.setCOMMANDE_PROMO_CODE(CMD.getCOMMANDE_CODE() + list_Promo_AppliqueesAR.get(i).getPROMO_CODE() + "0");
                                    commandePromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(i).getPROMO_CODE());
                                    commandePromotion.setCOMMANDE_CODE(CMD.getCOMMANDE_CODE());
                                    commandePromotion.setCOMMANDELIGNE_CODE(0);
                                    commandePromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(i).getPROMO_NIVEAU());
                                    commandePromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(i).getPROMO_MODECALCUL());
                                    commandePromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR());
                                    commandePromotion.setMONTANT_BRUTE_AV(RTMB_CMD);
                                    commandePromotion.setMONTANT_NET_AV(RTMN_CMD);
                                    commandePromotion.setVALEUR_PROMO(valeur_Promo);
                                    commandePromotion.setMONTANT_NET_AP(RTMN_CMD);
                                    commandePromotion.setGRATUITE_CODE(list_Promo_AppliqueesAR.get(i).getPROMO_CODE() + "0");

                                    list_PromotionCMD.add(commandePromotion);

                                }//fin mode calcul

                            }//fin promo palier
                        }// if ligne cmd impliquee
                    }//if commande
                }

            }//if table remise applique >0

            //Log.d("Remise", "list_PromotionCMD = "+list_PromotionCMD.toString());
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Promotion error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("Promotion: ",e.getMessage());
        }
        return list_PromotionCMD;
    }

    //methode de calcule des promotions des commandes selon les quantitées commandées

    public ArrayList<CommandePromotion> GetPromoAP_CMD_QC(Commande CMD, ArrayList<CommandeLigne> list_LCMD,Context context){
        ArrayList<CommandePromotion> list_PromotionCMD = new ArrayList<CommandePromotion>();
        ArrayList<CommandePromotion> list_PromoAP = new ArrayList<CommandePromotion>();
        try {


            PromotionaffectationManager promoAF_Manager = new PromotionaffectationManager(context);
            PromotionarticleManager promoAR_Manager = new PromotionarticleManager(context);
            PromotionpalierManager promoPL_Manager = new PromotionpalierManager(context);
            ClientManager client_Manager = new ClientManager(context);
            Promotionpalier max_PromoPalier;
            Double valeur_Promo = 0.0;

            ArrayList<Promotion> list_Promotion = new ArrayList<Promotion>();
            ArrayList<Promotion> list_Promo_AppliqueesAF = new ArrayList<Promotion>();
            ArrayList<Promotion> list_Promo_AppliqueesAR = new ArrayList<Promotion>();
            ArrayList<Promotion> list_Promo_AppliqueesPL = new ArrayList<Promotion>();

            ArrayList<Promotionpalier> list_PromotionPalier = new ArrayList<Promotionpalier>();
            ArrayList<Promotionpalier> list_PromotionPalierCMD = new ArrayList<Promotionpalier>();

            ArrayList<CommandeLigne> commandeLignes = new ArrayList<>();
            //list_Promo_AppliqueesPL=this.getList();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
            String date_string = CMD.getDATE_LIVRAISON().substring(0, 10);
            Date date_livraison = null;
            String sdate_livraison = null;
            try {
                date_livraison = df.parse(date_string);
                sdate_livraison = df.format(date_livraison);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //list_LCMD = commandeLignes;
            //CMD = CMD;

            /*if(CMD.getCOMMANDETYPE_CODE() == "2"){



                CMD = new Commande(CMD);

                for(int i = 0;i<list_LCMD.size();i++){
                    CommandeLigne commandeLigne = new CommandeLigne(list_LCMD.get(i));
                    commandeLignes.add(commandeLigne);
                }
                //list_LCMD.clear();
                list_LCMD = commandeLignes;

            }*/


            list_Promotion = getListByDate(sdate_livraison);
            Log.d("Remise", "date1= "+sdate_livraison+" Date2: "+date_string);
            Log.d("Remise", "list_Promotion = " + list_Promotion.size());
            //###################################################### Affectation #############################################################
            if (list_Promotion.size() > 0) {
                Client client = client_Manager.get(CMD.getCLIENT_CODE());
                for (int i = 0; i < list_Promotion.size(); i++) {
                    if (promoAF_Manager.check_Affectation(list_Promotion.get(i).getPROMO_CODE(), client)) {
                        list_Promo_AppliqueesAF.add(list_Promotion.get(i));
                    }
                }
            }
            Log.d("Remise", "list_Promo_AppliqueesAF = " + list_Promo_AppliqueesAF.size());
            //###################################################### Implication #############################################################
            if (list_Promo_AppliqueesAF.size() > 0) {
                for (int i = 0; i < list_Promo_AppliqueesAF.size(); i++) {
                    if (promoAR_Manager.Check_Implication(list_Promo_AppliqueesAF.get(i).getPROMO_CODE(), list_LCMD)) {
                        list_Promo_AppliqueesAR.add(list_Promo_AppliqueesAF.get(i));
                    }
                }
            }
            Log.d("Remise", "list_Promo_AppliqueesAR = " + list_Promo_AppliqueesAR.size());
            //###################################################### calcul ligne #############################################################
            if (list_Promo_AppliqueesAR.size() > 0) {
                Double cmd_MBrute = 0.0;
                Double cmd_MNET = 0.0;

                for (int i = 0; i < list_LCMD.size(); i++) {//boucle ligne cmd
                    Double lcmd_MBrute = list_LCMD.get(i).getMONTANT_BRUT();
                    Double lcmd_remise = 0.0;
                    Double lcmd_MNet = lcmd_MBrute;
                    CommandeLigne ligne_CMD = list_LCMD.get(i);

                    for (int j = 0; j < list_Promo_AppliqueesAR.size(); j++) {//boucle ligne promo appliqué et impliqué
                        if (promoAR_Manager.Check_ImplicationLCMD(list_Promo_AppliqueesAR.get(j).getPROMO_CODE(), ligne_CMD)) {
                            if (list_Promo_AppliqueesAR.get(j).getPROMO_NIVEAU().equals("CA0018")) {//Ligne
                                //calcul qte/valeur cmd
                                Double valeur_LCMD = 0.0;
                                if (list_Promo_AppliqueesAR.get(j).getPROMO_BASE().equals("CA0013")) {//caisse
                                    valeur_LCMD = ligne_CMD.getCAISSE_COMMANDEE();
                                } else if (list_Promo_AppliqueesAR.get(j).getPROMO_BASE().equals("CA0014")) {//Tonne
                                    valeur_LCMD = ligne_CMD.getTONNAGE_COMMANDEE();
                                } else if (list_Promo_AppliqueesAR.get(j).getPROMO_BASE().equals("CA0021")) {//Littre
                                    valeur_LCMD = ligne_CMD.getLITTRAGE_COMMANDEE();
                                } else {
                                    valeur_LCMD = 0.0;
                                }
                                Log.d("Remise", "valeur_LCMD = " + valeur_LCMD);

                                list_PromotionPalier = promoPL_Manager.getPromoPalierByVBase(list_Promo_AppliqueesAR.get(j).getPROMO_CODE(), list_Promo_AppliqueesAR.get(j).getPROMO_BASE(), valeur_LCMD);
                                if (list_PromotionPalier.size() > 0) {
                                    max_PromoPalier = promoPL_Manager.getMaxPromoPalierByVBase(list_Promo_AppliqueesAR.get(j).getPROMO_CODE(), list_Promo_AppliqueesAR.get(j).getPROMO_BASE(), valeur_LCMD);
                                    if (list_Promo_AppliqueesAR.get(j).getPROMO_MODECALCUL().equals("CA0015")) {//taux
                                        if (list_Promo_AppliqueesAR.get(j).getPROMO_REPETITIONPALIER().equals("1")) {//repetition
                                            if (list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR().equals("CA0019")) {//surNet
                                                valeur_Promo = lcmd_MNet * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                            } else if (list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR().equals("CA0020")) {//sur base
                                                valeur_Promo = lcmd_MBrute * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                            }
                                            CommandePromotion commandePromotion = new CommandePromotion();

                                            commandePromotion.setCOMMANDE_PROMO_CODE(CMD.getCOMMANDE_CODE() + list_Promo_AppliqueesAR.get(j).getPROMO_CODE() + list_LCMD.get(i).getCOMMANDELIGNE_CODE());
                                            commandePromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(j).getPROMO_CODE());
                                            commandePromotion.setCOMMANDE_CODE(CMD.getCOMMANDE_CODE());
                                            int cmdligne = list_LCMD.get(i).getCOMMANDELIGNE_CODE();
                                            commandePromotion.setCOMMANDELIGNE_CODE(Integer.valueOf(cmdligne));
                                            commandePromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(j).getPROMO_NIVEAU());
                                            commandePromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(j).getPROMO_MODECALCUL());
                                            commandePromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR());
                                            commandePromotion.setMONTANT_BRUTE_AV(lcmd_MBrute);
                                            commandePromotion.setMONTANT_NET_AV(lcmd_MNet);
                                            commandePromotion.setVALEUR_PROMO(valeur_Promo);
                                            commandePromotion.setMONTANT_NET_AP(lcmd_MNet - valeur_Promo);
                                            commandePromotion.setGRATUITE_CODE("");

                                            list_PromotionCMD.add(commandePromotion);


                                            lcmd_MNet += lcmd_MNet - valeur_Promo;
                                            lcmd_remise += valeur_Promo;

                                        } else if (list_Promo_AppliqueesAR.get(j).getPROMO_REPETITIONPALIER().equals("0")) {//repetition
                                            Double valeurbase = Double.parseDouble(max_PromoPalier.getVALEUR_PBASE());
                                            Double d = valeur_LCMD / valeurbase;
                                            Integer repetitionPL = d.intValue();

                                            for (int h = 1; h <= repetitionPL; h++) {
                                                if (list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR().equals("CA0019")) {//surNet
                                                    valeur_Promo = lcmd_MNet * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                                    lcmd_remise += valeur_Promo;
                                                } else if (list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR().equals("CA0020")) {//sur base
                                                    valeur_Promo = lcmd_MBrute * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                                    lcmd_remise += valeur_Promo;
                                                }
                                                CommandePromotion commandePromotion = new CommandePromotion();

                                                commandePromotion.setCOMMANDE_PROMO_CODE(CMD.getCOMMANDE_CODE() + list_Promo_AppliqueesAR.get(j).getPROMO_CODE() + list_LCMD.get(i).getCOMMANDELIGNE_CODE());
                                                commandePromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(j).getPROMO_CODE());
                                                commandePromotion.setCOMMANDE_CODE(CMD.getCOMMANDE_CODE());
                                                int cmdligne = list_LCMD.get(i).getCOMMANDELIGNE_CODE();
                                                commandePromotion.setCOMMANDELIGNE_CODE(Integer.valueOf(cmdligne));
                                                commandePromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(j).getPROMO_NIVEAU());
                                                commandePromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(j).getPROMO_MODECALCUL());
                                                commandePromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR());
                                                commandePromotion.setMONTANT_BRUTE_AV(lcmd_MBrute);
                                                commandePromotion.setMONTANT_NET_AV(lcmd_MNet);
                                                commandePromotion.setVALEUR_PROMO(valeur_Promo);
                                                commandePromotion.setMONTANT_NET_AP(lcmd_MNet - valeur_Promo);
                                                commandePromotion.setGRATUITE_CODE("");

                                                list_PromotionCMD.add(commandePromotion);


                                                lcmd_MNet += lcmd_MNet - valeur_Promo;
                                            }

                                        } else {
                                            Double valeurbase = Double.parseDouble(max_PromoPalier.getVALEUR_PBASE());
                                            Double d = valeur_LCMD / valeurbase;
                                            Integer repetitionPL = d.intValue();
                                            Integer promoRPL = Integer.valueOf(list_Promo_AppliqueesAR.get(j).getPROMO_REPETITIONPALIER());

                                            while (promoRPL <= repetitionPL) {

                                                if (list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR().equals("CA0019")) {//surNet
                                                    valeur_Promo = lcmd_MNet * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                                    lcmd_remise += valeur_Promo;
                                                } else if (list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR().equals("CA0020")) {//sur base
                                                    valeur_Promo = lcmd_MBrute * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                                    lcmd_remise += valeur_Promo;
                                                }

                                                CommandePromotion commandePromotion = new CommandePromotion();

                                                commandePromotion.setCOMMANDE_PROMO_CODE(CMD.getCOMMANDE_CODE() + list_Promo_AppliqueesAR.get(j).getPROMO_CODE() + list_LCMD.get(i).getCOMMANDELIGNE_CODE());
                                                commandePromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(j).getPROMO_CODE());
                                                commandePromotion.setCOMMANDE_CODE(CMD.getCOMMANDE_CODE());
                                                int cmdligne = list_LCMD.get(i).getCOMMANDELIGNE_CODE();
                                                commandePromotion.setCOMMANDELIGNE_CODE(Integer.valueOf(cmdligne));
                                                commandePromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(j).getPROMO_NIVEAU());
                                                commandePromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(j).getPROMO_MODECALCUL());
                                                commandePromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR());
                                                commandePromotion.setMONTANT_BRUTE_AV(lcmd_MBrute);
                                                commandePromotion.setMONTANT_NET_AV(lcmd_MNet);
                                                commandePromotion.setVALEUR_PROMO(valeur_Promo);
                                                commandePromotion.setMONTANT_NET_AP(lcmd_MNet - valeur_Promo);
                                                commandePromotion.setGRATUITE_CODE("");

                                                list_PromotionCMD.add(commandePromotion);

                                                lcmd_MNet += lcmd_MNet - valeur_Promo;

                                                promoRPL++;

                                            }
                                        }

                                    } else if (list_Promo_AppliqueesAR.get(j).getPROMO_MODECALCUL().equals("CA0016")) {//Gratuite

                                        if(list_Promo_AppliqueesAR.get(j).getPROMO_REPETITIONPALIER().equals("1")) {//repetition
                                            Integer repetitionPL = 1;
                                            valeur_Promo = 1.0;
                                        }else if(list_Promo_AppliqueesAR.get(j).getPROMO_REPETITIONPALIER().equals("0")) {//repetition
                                            Double valeurbase = Double.parseDouble(max_PromoPalier.getVALEUR_PBASE());
                                            Double d = valeur_LCMD / valeurbase;
                                            Integer repetitionPL = d.intValue();
                                            valeur_Promo = Double.valueOf(repetitionPL);

                                        }else{
                                            Double valeurbase = Double.parseDouble(max_PromoPalier.getVALEUR_PBASE());
                                            Double d = valeur_LCMD / valeurbase;
                                            Integer repetitionPL = d.intValue();
                                            Integer promoRPL = Integer.valueOf(list_Promo_AppliqueesAR.get(j).getPROMO_REPETITIONPALIER());
                                            Integer repetitionAP = 0;

                                            while (promoRPL <= repetitionPL) {
                                                repetitionAP++;
                                                promoRPL++;
                                            }
                                            valeur_Promo = Double.valueOf(repetitionAP);
                                        }

                                        CommandePromotion commandePromotion = new CommandePromotion();

                                        commandePromotion.setCOMMANDE_PROMO_CODE(CMD.getCOMMANDE_CODE() + list_Promo_AppliqueesAR.get(j).getPROMO_CODE() + list_LCMD.get(i).getCOMMANDELIGNE_CODE());
                                        commandePromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(j).getPROMO_CODE());
                                        commandePromotion.setCOMMANDE_CODE(CMD.getCOMMANDE_CODE());
                                        int cmdligne = list_LCMD.get(i).getCOMMANDELIGNE_CODE();
                                        commandePromotion.setCOMMANDELIGNE_CODE(Integer.valueOf(cmdligne));
                                        commandePromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(j).getPROMO_NIVEAU());
                                        commandePromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(j).getPROMO_MODECALCUL());
                                        commandePromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR());
                                        commandePromotion.setMONTANT_BRUTE_AV(lcmd_MBrute);
                                        commandePromotion.setMONTANT_NET_AV(lcmd_MNet);
                                        commandePromotion.setVALEUR_PROMO(valeur_Promo);
                                        commandePromotion.setMONTANT_NET_AP(lcmd_MNet);
                                        commandePromotion.setGRATUITE_CODE(list_Promo_AppliqueesAR.get(j).getPROMO_CODE() + list_LCMD.get(i).getCOMMANDELIGNE_CODE());

                                        list_PromotionCMD.add(commandePromotion);


                                    }


                                }


                            }
                        }
                    }//fin boucle ligne promo appliquee

                    list_LCMD.get(i).setMONTANT_NET(lcmd_MNet);
                    list_LCMD.get(i).setREMISE(lcmd_remise);


                }//fin boucle ligne cmd


                //####################################################   Total
                Double RTC_CMD = 0.0;
                Double RTT_CMD = 0.0;
                Double RTL_CMD = 0.0;
                Double RTMB_CMD = 0.0;
                Double RTRS_CMD = 0.0;
                Double RTMN_CMD = 0.0;

                for (int i = 0; i < list_LCMD.size(); i++) {//boucle ligne cmd

                    RTC_CMD += list_LCMD.get(i).getCAISSE_COMMANDEE();
                    RTT_CMD += list_LCMD.get(i).getTONNAGE_COMMANDEE();
                    RTL_CMD += list_LCMD.get(i).getLITTRAGE_COMMANDEE();
                    RTMB_CMD += list_LCMD.get(i).getMONTANT_BRUT();
                    RTRS_CMD += list_LCMD.get(i).getREMISE();
                    RTMN_CMD += list_LCMD.get(i).getMONTANT_NET();
                }


                //####################################################   COMMANDE
                Double valeurCMD = 0.0;
                for (int i = 0; i < list_Promo_AppliqueesAR.size(); i++) {//boucle ligne promo appliqué et impliqué
                    if (list_Promo_AppliqueesAR.get(i).getPROMO_NIVEAU().equals("CA0017")) {//Commande

                        ArrayList<CommandeLigne> list_lCMD_IMP = new ArrayList<CommandeLigne>();

                        list_lCMD_IMP = promoAR_Manager.get_LCMDIMP(list_Promo_AppliqueesAR.get(i).getPROMO_CODE(), list_LCMD);

                        if (list_lCMD_IMP.size() > 0) {//check implication ligne cmd
                            Double RTC_CMD_IMP = 0.0;
                            Double RTT_CMD_IMP = 0.0;
                            Double RTL_CMD_IMP = 0.0;
                            Double RTMB_CMD_IMP = 0.0;
                            Double RTRS_CMD_IMP = 0.0;
                            Double RTMN_CMD_IMP = 0.0;

                            for (int j = 0; j < list_lCMD_IMP.size(); j++) {
                                RTC_CMD_IMP += list_lCMD_IMP.get(j).getCAISSE_COMMANDEE();
                                RTT_CMD_IMP += list_lCMD_IMP.get(j).getTONNAGE_COMMANDEE();
                                RTL_CMD_IMP += list_lCMD_IMP.get(j).getLITTRAGE_COMMANDEE();
                                RTMB_CMD_IMP += list_lCMD_IMP.get(j).getMONTANT_BRUT();
                                RTRS_CMD_IMP += list_lCMD_IMP.get(j).getREMISE();
                                RTMN_CMD_IMP += list_lCMD_IMP.get(j).getMONTANT_NET();
                            }

                            if (list_Promo_AppliqueesAR.get(i).getPROMO_BASE().equals("CA0013")) {//caisse
                                valeurCMD = RTC_CMD_IMP;
                            } else if (list_Promo_AppliqueesAR.get(i).getPROMO_BASE().equals("CA0014")) {//tonne
                                valeurCMD = RTT_CMD_IMP;
                            } else if (list_Promo_AppliqueesAR.get(i).getPROMO_BASE().equals("CA0021")) {//litre
                                valeurCMD = RTL_CMD_IMP;
                            } else if (list_Promo_AppliqueesAR.get(i).getPROMO_BASE().equals("CA0025")) {//ca
                                valeurCMD = RTMN_CMD_IMP;
                            } else {
                                valeurCMD = 0.0;
                            }
                            //Log.d("Remise", "valeurCMD = " + valeurCMD);
                            Double valeur_PROMO = 0.0;
                            list_PromotionPalierCMD = promoPL_Manager.getPromoPalierByVBase(list_Promo_AppliqueesAR.get(i).getPROMO_CODE(), list_Promo_AppliqueesAR.get(i).getPROMO_BASE(), valeurCMD);

                            Log.d("Remise", "CMD list_PromotionPalierCMD = " + list_PromotionPalierCMD.size());

                            if (list_PromotionPalierCMD.size() > 0) {
                                max_PromoPalier = promoPL_Manager.getMaxPromoPalierByVBase(list_Promo_AppliqueesAR.get(i).getPROMO_CODE(), list_Promo_AppliqueesAR.get(i).getPROMO_BASE(), valeurCMD);
                                Log.d("Remise", "max_PromoPalier = "+max_PromoPalier.toString());
                                Double valeurbase = Double.parseDouble(max_PromoPalier.getVALEUR_PBASE());

                                if (list_Promo_AppliqueesAR.get(i).getPROMO_MODECALCUL().equals("CA0015")) {//Taux

                                    if (list_Promo_AppliqueesAR.get(i).getPROMO_REPETITIONPALIER().equals("1")) {//repetition

                                        if (list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR().equals("CA0019")) {//sur net

                                            valeur_PROMO = RTMN_CMD_IMP * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                        } else if (list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR().equals("CA0020")) {//sur brut
                                            valeur_PROMO = RTMB_CMD_IMP * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                        }

                                        CommandePromotion commandePromotion = new CommandePromotion();

                                        commandePromotion.setCOMMANDE_PROMO_CODE(CMD.getCOMMANDE_CODE() + list_Promo_AppliqueesAR.get(i).getPROMO_CODE() + "0");
                                        commandePromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(i).getPROMO_CODE());
                                        commandePromotion.setCOMMANDE_CODE(CMD.getCOMMANDE_CODE());
                                        commandePromotion.setCOMMANDELIGNE_CODE(0);
                                        commandePromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(i).getPROMO_NIVEAU());
                                        commandePromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(i).getPROMO_MODECALCUL());
                                        commandePromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR());
                                        commandePromotion.setMONTANT_BRUTE_AV(RTMB_CMD);
                                        commandePromotion.setMONTANT_NET_AV(RTMN_CMD);
                                        commandePromotion.setVALEUR_PROMO(valeur_PROMO);
                                        commandePromotion.setMONTANT_NET_AP(RTMN_CMD - valeur_PROMO);
                                        commandePromotion.setGRATUITE_CODE("");

                                        list_PromotionCMD.add(commandePromotion);

                                        RTMN_CMD += RTMN_CMD - valeur_PROMO;
                                    } else if (list_Promo_AppliqueesAR.get(i).getPROMO_REPETITIONPALIER().equals("0")) {//repetition
                                        Double d = valeurCMD / valeurbase;
                                        Integer repetitionPL = d.intValue();

                                        for (int h = 1; h <= repetitionPL; h++) {
                                            if (list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR().equals("CA0019")) {//sur net
                                                valeur_PROMO = RTMN_CMD_IMP * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                            } else if (list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR().equals("CA0020")) {//sur brut
                                                valeur_PROMO = RTMB_CMD_IMP * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                            }
                                            CommandePromotion commandePromotion = new CommandePromotion();

                                            commandePromotion.setCOMMANDE_PROMO_CODE(CMD.getCOMMANDE_CODE() + list_Promo_AppliqueesAR.get(i).getPROMO_CODE() + "0");
                                            commandePromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(i).getPROMO_CODE());
                                            commandePromotion.setCOMMANDE_CODE(CMD.getCOMMANDE_CODE());
                                            commandePromotion.setCOMMANDELIGNE_CODE(0);
                                            commandePromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(i).getPROMO_NIVEAU());
                                            commandePromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(i).getPROMO_MODECALCUL());
                                            commandePromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR());
                                            commandePromotion.setMONTANT_BRUTE_AV(RTMB_CMD);
                                            commandePromotion.setMONTANT_NET_AV(RTMN_CMD);
                                            commandePromotion.setVALEUR_PROMO(valeur_PROMO);
                                            commandePromotion.setMONTANT_NET_AP(RTMN_CMD - valeur_PROMO);
                                            commandePromotion.setGRATUITE_CODE("");

                                            list_PromotionCMD.add(commandePromotion);

                                            RTMN_CMD += RTMN_CMD - valeur_PROMO;
                                        }

                                    } else {
                                        Double d = valeurCMD / valeurbase;
                                        Integer repetitionPL = d.intValue();
                                        Integer promoRPL = Integer.valueOf(list_Promo_AppliqueesAR.get(i).getPROMO_REPETITIONPALIER());

                                        while (promoRPL <= repetitionPL) {
                                            if (list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR().equals("CA0019")) {//sur net
                                                valeur_PROMO = RTMN_CMD_IMP * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                            } else if (list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR().equals("CA0020")) {//sur brut
                                                valeur_PROMO = RTMB_CMD_IMP * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                            }
                                            CommandePromotion commandePromotion = new CommandePromotion();

                                            commandePromotion.setCOMMANDE_PROMO_CODE(CMD.getCOMMANDE_CODE() + list_Promo_AppliqueesAR.get(i).getPROMO_CODE() + "0");
                                            commandePromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(i).getPROMO_CODE());
                                            commandePromotion.setCOMMANDE_CODE(CMD.getCOMMANDE_CODE());
                                            commandePromotion.setCOMMANDELIGNE_CODE(0);
                                            commandePromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(i).getPROMO_NIVEAU());
                                            commandePromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(i).getPROMO_MODECALCUL());
                                            commandePromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR());
                                            commandePromotion.setMONTANT_BRUTE_AV(RTMB_CMD);
                                            commandePromotion.setMONTANT_NET_AV(RTMN_CMD);
                                            commandePromotion.setVALEUR_PROMO(valeur_PROMO);
                                            commandePromotion.setMONTANT_NET_AP(RTMN_CMD - valeur_PROMO);
                                            commandePromotion.setGRATUITE_CODE("");

                                            list_PromotionCMD.add(commandePromotion);

                                            RTMN_CMD += RTMN_CMD - valeur_PROMO;
                                            promoRPL++;
                                        }
                                    }//fin repetition

                                } else if (list_Promo_AppliqueesAR.get(i).getPROMO_MODECALCUL().equals("CA0016")) {//Gratuite

                                    if (list_Promo_AppliqueesAR.get(i).getPROMO_REPETITIONPALIER().equals("1")) {//repetition
                                        Integer repetitionPL = 1;
                                        valeur_PROMO = 1.0;
                                    } else if (list_Promo_AppliqueesAR.get(i).getPROMO_REPETITIONPALIER().equals("0")) {//repetition

                                        Double d = valeurCMD / valeurbase;
                                        Integer repetitionPL = d.intValue();
                                        Double valeurpromo = Double.parseDouble(max_PromoPalier.getVALEUR_PROMO());
                                        valeur_Promo = Double.valueOf(repetitionPL*valeurpromo);

                                    } else {

                                        Double d = valeurCMD / valeurbase;
                                        Integer repetitionPL = d.intValue();
                                        Integer promoRPL = Integer.valueOf(list_Promo_AppliqueesAR.get(i).getPROMO_REPETITIONPALIER());
                                        Integer repetitionAP = 0;

                                        while (promoRPL <= repetitionPL) {
                                            repetitionAP++;
                                            promoRPL++;
                                        }
                                        valeur_Promo = Double.valueOf(repetitionAP);
                                    }

                                    Log.d("Remise", "valeur_Promo = " + valeur_Promo);
                                    CommandePromotion commandePromotion = new CommandePromotion();

                                    commandePromotion.setCOMMANDE_PROMO_CODE(CMD.getCOMMANDE_CODE() + list_Promo_AppliqueesAR.get(i).getPROMO_CODE() + "0");
                                    commandePromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(i).getPROMO_CODE());
                                    commandePromotion.setCOMMANDE_CODE(CMD.getCOMMANDE_CODE());
                                    commandePromotion.setCOMMANDELIGNE_CODE(0);
                                    commandePromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(i).getPROMO_NIVEAU());
                                    commandePromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(i).getPROMO_MODECALCUL());
                                    commandePromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR());
                                    commandePromotion.setMONTANT_BRUTE_AV(RTMB_CMD);
                                    commandePromotion.setMONTANT_NET_AV(RTMN_CMD);
                                    commandePromotion.setVALEUR_PROMO(valeur_Promo);
                                    commandePromotion.setMONTANT_NET_AP(RTMN_CMD);
                                    commandePromotion.setGRATUITE_CODE(list_Promo_AppliqueesAR.get(i).getPROMO_CODE() + "0");

                                    Log.d("Remise", "commandepromotion = " + commandePromotion.toString());

                                    list_PromotionCMD.add(commandePromotion);

                                }//fin mode calcul

                            }//fin promo palier
                        }// if ligne cmd impliquee
                    }//if commande
                }

            }//if table remise applique >0

            //Log.d("Remise", "list_PromotionCMD = "+list_PromotionCMD.toString());
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Promotion error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("Promotion: ",e.getMessage());
        }
        Log.d("Remise", "commandepromotion = " + list_PromotionCMD.toString());

        return list_PromotionCMD;
    }

    public ArrayList<LivraisonPromotion> GetPromoAP_LIV_QC(Livraison LIV, ArrayList<LivraisonLigne> list_LLIV, Context context){
        ArrayList<LivraisonPromotion> list_PromotionLIV = new ArrayList<LivraisonPromotion>();
        ArrayList<LivraisonPromotion> list_PromoAP = new ArrayList<LivraisonPromotion>();
        try {

            PromotionaffectationManager promoAF_Manager = new PromotionaffectationManager(context);
            PromotionarticleManager promoAR_Manager = new PromotionarticleManager(context);
            PromotionpalierManager promoPL_Manager = new PromotionpalierManager(context);
            ClientManager client_Manager = new ClientManager(context);
            Promotionpalier max_PromoPalier;
            Double valeur_Promo = 0.0;

            ArrayList<Promotion> list_Promotion = new ArrayList<Promotion>();
            ArrayList<Promotion> list_Promo_AppliqueesAF = new ArrayList<Promotion>();
            ArrayList<Promotion> list_Promo_AppliqueesAR = new ArrayList<Promotion>();
            ArrayList<Promotion> list_Promo_AppliqueesPL = new ArrayList<Promotion>();

            ArrayList<Promotionpalier> list_PromotionPalier = new ArrayList<Promotionpalier>();
            ArrayList<Promotionpalier> list_PromotionPalierLIV = new ArrayList<Promotionpalier>();

            //list_Promo_AppliqueesPL=this.getList();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
            String date_string = LIV.getDATE_LIVRAISON().substring(0, 10);
            Date date_livraison = null;
            String sdate_livraison = null;
            try {
                date_livraison = df.parse(date_string);
                sdate_livraison = df.format(date_livraison);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            list_Promotion = getListByDate(sdate_livraison);
            Log.d("Remise", "date1= "+sdate_livraison+" Date2: "+date_string);
            Log.d("Remise", "list_Promotion = " + list_Promotion.size());
            //###################################################### Affectation #############################################################
            if (list_Promotion.size() > 0) {
                Client client = client_Manager.get(LIV.getCLIENT_CODE());
                for (int i = 0; i < list_Promotion.size(); i++) {
                    if (promoAF_Manager.check_Affectation(list_Promotion.get(i).getPROMO_CODE(), client)) {
                        list_Promo_AppliqueesAF.add(list_Promotion.get(i));
                    }
                }
            }
            Log.d("Remise", "list_Promo_AppliqueesAF = " + list_Promo_AppliqueesAF.size());
            //###################################################### Implication #############################################################
            if (list_Promo_AppliqueesAF.size() > 0) {
                for (int i = 0; i < list_Promo_AppliqueesAF.size(); i++) {
                    if (promoAR_Manager.Check_Implication_L(list_Promo_AppliqueesAF.get(i).getPROMO_CODE(), list_LLIV)) {
                        list_Promo_AppliqueesAR.add(list_Promo_AppliqueesAF.get(i));
                    }
                }
            }
            Log.d("Remise", "list_Promo_AppliqueesAR = " + list_Promo_AppliqueesAR.size());
            //###################################################### calcul ligne #############################################################
            if (list_Promo_AppliqueesAR.size() > 0) {
                Double liv_MBrute = 0.0;
                Double liv_MNET = 0.0;

                for (int i = 0; i < list_LLIV.size(); i++) {//boucle ligne liv
                    Double lliv_MBrute = list_LLIV.get(i).getMONTANT_BRUT();
                    Double lliv_remise = 0.0;
                    Double lliv_MNet = lliv_MBrute;
                    LivraisonLigne ligne_LIV = list_LLIV.get(i);

                    for (int j = 0; j < list_Promo_AppliqueesAR.size(); j++) {//boucle ligne promo appliqué et impliqué
                        if (promoAR_Manager.Check_ImplicationLLIV(list_Promo_AppliqueesAR.get(j).getPROMO_CODE(), ligne_LIV)) {
                            if (list_Promo_AppliqueesAR.get(j).getPROMO_NIVEAU().equals("CA0018")) {
                                //Ligne//calcul qte/valeur liv
                                Double valeur_LLIV = 0.0;
                                if (list_Promo_AppliqueesAR.get(j).getPROMO_BASE().equals("CA0013")) {//caisse
                                    valeur_LLIV = ligne_LIV.getCAISSE_COMMANDEE();
                                } else if (list_Promo_AppliqueesAR.get(j).getPROMO_BASE().equals("CA0014")) {//Tonne
                                    valeur_LLIV = ligne_LIV.getTONNAGE_COMMANDEE();
                                } else if (list_Promo_AppliqueesAR.get(j).getPROMO_BASE().equals("CA0021")) {//Littre
                                    valeur_LLIV = ligne_LIV.getLITTRAGE_COMMANDEE();
                                } else {
                                    valeur_LLIV = 0.0;
                                }
                                Log.d("Remise", "valeur_LLIV = " + valeur_LLIV);

                                list_PromotionPalier = promoPL_Manager.getPromoPalierByVBase(list_Promo_AppliqueesAR.get(j).getPROMO_CODE(), list_Promo_AppliqueesAR.get(j).getPROMO_BASE(), valeur_LLIV);
                                if (list_PromotionPalier.size() > 0) {
                                    max_PromoPalier = promoPL_Manager.getMaxPromoPalierByVBase(list_Promo_AppliqueesAR.get(j).getPROMO_CODE(), list_Promo_AppliqueesAR.get(j).getPROMO_BASE(), valeur_LLIV);
                                    if (list_Promo_AppliqueesAR.get(j).getPROMO_MODECALCUL().equals("CA0015")) {//taux
                                        if (list_Promo_AppliqueesAR.get(j).getPROMO_REPETITIONPALIER().equals("1")) {//repetition
                                            if (list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR().equals("CA0019")) {//surNet
                                                valeur_Promo = lliv_MNet * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                            } else if (list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR().equals("CA0020")) {//sur base
                                                valeur_Promo = lliv_MBrute * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                            }
                                            LivraisonPromotion livraisonPromotion = new LivraisonPromotion();

                                            livraisonPromotion.setLIVRAISON_PROMO_CODE(LIV.getLIVRAISON_CODE() + list_Promo_AppliqueesAR.get(j).getPROMO_CODE() + list_LLIV.get(i).getLIVRAISONLIGNE_CODE());
                                            livraisonPromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(j).getPROMO_CODE());
                                            livraisonPromotion.setLIVRAISON_CODE(LIV.getLIVRAISON_CODE());
                                            int livligne = list_LLIV.get(i).getLIVRAISONLIGNE_CODE();
                                            livraisonPromotion.setLIVRAISONLIGNE_CODE(Integer.valueOf(livligne));
                                            livraisonPromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(j).getPROMO_NIVEAU());
                                            livraisonPromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(j).getPROMO_MODECALCUL());
                                            livraisonPromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR());
                                            livraisonPromotion.setMONTANT_BRUTE_AV(lliv_MBrute);
                                            livraisonPromotion.setMONTANT_NET_AV(lliv_MNet);
                                            livraisonPromotion.setVALEUR_PROMO(valeur_Promo);
                                            livraisonPromotion.setMONTANT_NET_AP(lliv_MNet - valeur_Promo);
                                            livraisonPromotion.setGRATUITE_CODE("");

                                            list_PromotionLIV.add(livraisonPromotion);


                                            lliv_MNet += lliv_MNet - valeur_Promo;
                                            lliv_remise += valeur_Promo;

                                        } else if (list_Promo_AppliqueesAR.get(j).getPROMO_REPETITIONPALIER().equals("0")) {//repetition
                                            Double valeurbase = Double.parseDouble(max_PromoPalier.getVALEUR_PBASE());
                                            Double d = valeur_LLIV / valeurbase;
                                            Integer repetitionPL = d.intValue();

                                            for (int h = 1; h <= repetitionPL; h++) {
                                                if (list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR().equals("CA0019")) {//surNet
                                                    valeur_Promo = lliv_MNet * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                                    lliv_remise += valeur_Promo;
                                                } else if (list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR().equals("CA0020")) {//sur base
                                                    valeur_Promo = lliv_MBrute * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                                    lliv_remise += valeur_Promo;
                                                }
                                                LivraisonPromotion livraisonPromotion = new LivraisonPromotion();

                                                livraisonPromotion.setLIVRAISON_PROMO_CODE(LIV.getLIVRAISON_CODE() + list_Promo_AppliqueesAR.get(j).getPROMO_CODE() + list_LLIV.get(i).getLIVRAISONLIGNE_CODE());
                                                livraisonPromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(j).getPROMO_CODE());
                                                livraisonPromotion.setLIVRAISON_CODE(LIV.getLIVRAISON_CODE());
                                                int livligne = list_LLIV.get(i).getLIVRAISONLIGNE_CODE();
                                                livraisonPromotion.setLIVRAISONLIGNE_CODE(Integer.valueOf(livligne));
                                                livraisonPromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(j).getPROMO_NIVEAU());
                                                livraisonPromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(j).getPROMO_MODECALCUL());
                                                livraisonPromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR());
                                                livraisonPromotion.setMONTANT_BRUTE_AV(lliv_MBrute);
                                                livraisonPromotion.setMONTANT_NET_AV(lliv_MNet);
                                                livraisonPromotion.setVALEUR_PROMO(valeur_Promo);
                                                livraisonPromotion.setMONTANT_NET_AP(lliv_MNet - valeur_Promo);
                                                livraisonPromotion.setGRATUITE_CODE("");

                                                list_PromotionLIV.add(livraisonPromotion);


                                                lliv_MNet += lliv_MNet - valeur_Promo;
                                            }

                                        } else {
                                            Double valeurbase = Double.parseDouble(max_PromoPalier.getVALEUR_PBASE());
                                            Double d = valeur_LLIV / valeurbase;
                                            Integer repetitionPL = d.intValue();
                                            Integer promoRPL = Integer.valueOf(list_Promo_AppliqueesAR.get(j).getPROMO_REPETITIONPALIER());

                                            while (promoRPL <= repetitionPL) {

                                                if (list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR().equals("CA0019")) {//surNet
                                                    valeur_Promo = lliv_MNet * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                                    lliv_remise += valeur_Promo;
                                                } else if (list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR().equals("CA0020")) {//sur base
                                                    valeur_Promo = lliv_MBrute * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                                    lliv_remise += valeur_Promo;
                                                }

                                                LivraisonPromotion livraisonPromotion = new LivraisonPromotion();

                                                livraisonPromotion.setLIVRAISON_PROMO_CODE(LIV.getLIVRAISON_CODE() + list_Promo_AppliqueesAR.get(j).getPROMO_CODE() + list_LLIV.get(i).getLIVRAISONLIGNE_CODE());
                                                livraisonPromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(j).getPROMO_CODE());
                                                livraisonPromotion.setLIVRAISON_CODE(LIV.getLIVRAISON_CODE());
                                                int livligne = list_LLIV.get(i).getLIVRAISONLIGNE_CODE();
                                                livraisonPromotion.setLIVRAISONLIGNE_CODE(Integer.valueOf(livligne));
                                                livraisonPromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(j).getPROMO_NIVEAU());
                                                livraisonPromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(j).getPROMO_MODECALCUL());
                                                livraisonPromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR());
                                                livraisonPromotion.setMONTANT_BRUTE_AV(lliv_MBrute);
                                                livraisonPromotion.setMONTANT_NET_AV(lliv_MNet);
                                                livraisonPromotion.setVALEUR_PROMO(valeur_Promo);
                                                livraisonPromotion.setMONTANT_NET_AP(lliv_MNet - valeur_Promo);
                                                livraisonPromotion.setGRATUITE_CODE("");

                                                list_PromotionLIV.add(livraisonPromotion);

                                                lliv_MNet += lliv_MNet - valeur_Promo;

                                                promoRPL++;

                                            }
                                        }

                                    } else if (list_Promo_AppliqueesAR.get(j).getPROMO_MODECALCUL().equals("CA0016")) {//Gratuite

                                        if(list_Promo_AppliqueesAR.get(j).getPROMO_REPETITIONPALIER().equals("1")) {//repetition
                                            Integer repetitionPL = 1;
                                            valeur_Promo = 1.0;
                                        }else if(list_Promo_AppliqueesAR.get(j).getPROMO_REPETITIONPALIER().equals("0")) {//repetition
                                            Double valeurbase = Double.parseDouble(max_PromoPalier.getVALEUR_PBASE());
                                            Double d = valeur_LLIV / valeurbase;
                                            Integer repetitionPL = d.intValue();
                                            Double valeurpromo = Double.parseDouble(max_PromoPalier.getVALEUR_PROMO());
                                            valeur_Promo = Double.valueOf(repetitionPL*valeurpromo);

                                        }else{
                                            Double valeurbase = Double.parseDouble(max_PromoPalier.getVALEUR_PBASE());
                                            Double d = valeur_LLIV / valeurbase;
                                            Integer repetitionPL = d.intValue();
                                            Integer promoRPL = Integer.valueOf(list_Promo_AppliqueesAR.get(j).getPROMO_REPETITIONPALIER());
                                            Integer repetitionAP = 0;

                                            while (promoRPL <= repetitionPL) {
                                                repetitionAP++;
                                                promoRPL++;
                                            }
                                            valeur_Promo = Double.valueOf(repetitionAP);
                                        }

                                        LivraisonPromotion livraisonPromotion = new LivraisonPromotion();

                                        livraisonPromotion.setLIVRAISON_PROMO_CODE(LIV.getLIVRAISON_CODE() + list_Promo_AppliqueesAR.get(j).getPROMO_CODE() + list_LLIV.get(i).getLIVRAISONLIGNE_CODE());
                                        livraisonPromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(j).getPROMO_CODE());
                                        livraisonPromotion.setLIVRAISON_CODE(LIV.getLIVRAISON_CODE());
                                        int livligne = list_LLIV.get(i).getLIVRAISONLIGNE_CODE();
                                        livraisonPromotion.setLIVRAISONLIGNE_CODE(Integer.valueOf(livligne));
                                        livraisonPromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(j).getPROMO_NIVEAU());
                                        livraisonPromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(j).getPROMO_MODECALCUL());
                                        livraisonPromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(j).getPROMO_APPLIQUESUR());
                                        livraisonPromotion.setMONTANT_BRUTE_AV(lliv_MBrute);
                                        livraisonPromotion.setMONTANT_NET_AV(lliv_MNet);
                                        livraisonPromotion.setVALEUR_PROMO(valeur_Promo);
                                        livraisonPromotion.setMONTANT_NET_AP(lliv_MNet);
                                        livraisonPromotion.setGRATUITE_CODE(list_Promo_AppliqueesAR.get(j).getPROMO_CODE() + list_LLIV.get(i).getLIVRAISONLIGNE_CODE());

                                        list_PromotionLIV.add(livraisonPromotion);


                                    }


                                }


                            }
                        }
                    }//fin boucle ligne promo appliquee

                    list_LLIV.get(i).setMONTANT_NET(lliv_MNet);
                    list_LLIV.get(i).setREMISE(lliv_remise);


                }//fin boucle ligne liv


                //####################################################   Total
                Double RTC_LIV = 0.0;
                Double RTT_LIV = 0.0;
                Double RTL_LIV = 0.0;
                Double RTMB_LIV = 0.0;
                Double RTRS_LIV = 0.0;
                Double RTMN_LIV = 0.0;

                for (int i = 0; i < list_LLIV.size(); i++) {//boucle ligne liv

                    RTC_LIV += list_LLIV.get(i).getCAISSE_COMMANDEE();
                    RTT_LIV += list_LLIV.get(i).getTONNAGE_COMMANDEE();
                    RTL_LIV += list_LLIV.get(i).getLITTRAGE_COMMANDEE();
                    RTMB_LIV += list_LLIV.get(i).getMONTANT_BRUT();
                    RTRS_LIV += list_LLIV.get(i).getREMISE();
                    RTMN_LIV += list_LLIV.get(i).getMONTANT_NET();
                }


                //####################################################   LIVRAISON
                Double valeurLIV = 0.0;
                for (int i = 0; i < list_Promo_AppliqueesAR.size(); i++) {//boucle ligne promo appliqué et impliqué
                    if (list_Promo_AppliqueesAR.get(i).getPROMO_NIVEAU().equals("CA0017")) {//Livraison

                        ArrayList<LivraisonLigne> list_lLIV_IMP = new ArrayList<LivraisonLigne>();

                        list_lLIV_IMP = promoAR_Manager.get_LLIVIMP(list_Promo_AppliqueesAR.get(i).getPROMO_CODE(), list_LLIV);

                        Log.d("Remise", "GetPromoAP_LIV_QC: "+list_LLIV.size());
                        Log.d("Remise", "GetPromoAP_LIV_QC: "+list_LLIV.toString());

                        if (list_lLIV_IMP.size() > 0) {//check implication ligne liv
                            Double RTC_LIV_IMP = 0.0;
                            Double RTT_LIV_IMP = 0.0;
                            Double RTL_LIV_IMP = 0.0;
                            Double RTMB_LIV_IMP = 0.0;
                            Double RTRS_LIV_IMP = 0.0;
                            Double RTMN_LIV_IMP = 0.0;

                            for (int j = 0; j < list_lLIV_IMP.size(); j++) {
                                RTC_LIV_IMP += list_lLIV_IMP.get(j).getCAISSE_COMMANDEE();
                                RTT_LIV_IMP += list_lLIV_IMP.get(j).getTONNAGE_COMMANDEE();
                                RTL_LIV_IMP += list_lLIV_IMP.get(j).getLITTRAGE_COMMANDEE();
                                RTMB_LIV_IMP += list_lLIV_IMP.get(j).getMONTANT_BRUT();
                                RTRS_LIV_IMP += list_lLIV_IMP.get(j).getREMISE();
                                RTMN_LIV_IMP += list_lLIV_IMP.get(j).getMONTANT_NET();
                            }

                            if (list_Promo_AppliqueesAR.get(i).getPROMO_BASE().equals("CA0013")) {//caisse
                                valeurLIV = RTC_LIV_IMP;
                            } else if (list_Promo_AppliqueesAR.get(i).getPROMO_BASE().equals("CA0014")) {//tonne
                                valeurLIV = RTT_LIV_IMP;
                            } else if (list_Promo_AppliqueesAR.get(i).getPROMO_BASE().equals("CA0021")) {//litre
                                valeurLIV = RTL_LIV_IMP;
                            } else if (list_Promo_AppliqueesAR.get(i).getPROMO_BASE().equals("CA0025")) {//ca
                                valeurLIV = RTMN_LIV_IMP;
                            } else {
                                valeurLIV = 0.0;
                            }
                            Log.d("Remise", "valeurLIV = " + valeurLIV);
                            Double valeur_PROMO = 0.0;
                            list_PromotionPalierLIV = promoPL_Manager.getPromoPalierByVBase(list_Promo_AppliqueesAR.get(i).getPROMO_CODE(), list_Promo_AppliqueesAR.get(i).getPROMO_BASE(), valeurLIV);

                            Log.d("Remise", "LIV list_PromotionPalierLIV = " + list_PromotionPalierLIV.size());

                            if (list_PromotionPalierLIV.size() > 0) {
                                max_PromoPalier = promoPL_Manager.getMaxPromoPalierByVBase(list_Promo_AppliqueesAR.get(i).getPROMO_CODE(), list_Promo_AppliqueesAR.get(i).getPROMO_BASE(), valeurLIV);
                                Log.d("Remise", "max_PromoPalier = "+max_PromoPalier.toString());
                                Double valeurbase = Double.parseDouble(max_PromoPalier.getVALEUR_PBASE());

                                if (list_Promo_AppliqueesAR.get(i).getPROMO_MODECALCUL().equals("CA0015")) {//Taux

                                    if (list_Promo_AppliqueesAR.get(i).getPROMO_REPETITIONPALIER().equals("1")) {//repetition

                                        if (list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR().equals("CA0019")) {//sur net

                                            valeur_PROMO = RTMN_LIV_IMP * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                        } else if (list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR().equals("CA0020")) {//sur brut
                                            valeur_PROMO = RTMB_LIV_IMP * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                        }

                                        LivraisonPromotion livraisonPromotion = new LivraisonPromotion();

                                        livraisonPromotion.setLIVRAISON_PROMO_CODE(LIV.getLIVRAISON_CODE() + list_Promo_AppliqueesAR.get(i).getPROMO_CODE() + "0");
                                        livraisonPromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(i).getPROMO_CODE());
                                        livraisonPromotion.setLIVRAISON_CODE(LIV.getLIVRAISON_CODE());
                                        livraisonPromotion.setLIVRAISONLIGNE_CODE(0);
                                        livraisonPromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(i).getPROMO_NIVEAU());
                                        livraisonPromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(i).getPROMO_MODECALCUL());
                                        livraisonPromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR());
                                        livraisonPromotion.setMONTANT_BRUTE_AV(RTMB_LIV);
                                        livraisonPromotion.setMONTANT_NET_AV(RTMN_LIV);
                                        livraisonPromotion.setVALEUR_PROMO(valeur_PROMO);
                                        livraisonPromotion.setMONTANT_NET_AP(RTMN_LIV - valeur_PROMO);
                                        livraisonPromotion.setGRATUITE_CODE("");

                                        list_PromotionLIV.add(livraisonPromotion);

                                        RTMN_LIV += RTMN_LIV - valeur_PROMO;
                                    } else if (list_Promo_AppliqueesAR.get(i).getPROMO_REPETITIONPALIER().equals("0")) {//repetition
                                        Double d = valeurLIV / valeurbase;
                                        Integer repetitionPL = d.intValue();

                                        for (int h = 1; h <= repetitionPL; h++) {
                                            if (list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR().equals("CA0019")) {//sur net
                                                valeur_PROMO = RTMN_LIV_IMP * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;

                                                Log.d("Remise", "GetPromoAP_LIV_QC: "+ valeur_PROMO);
                                            } else if (list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR().equals("CA0020")) {//sur brut
                                                valeur_PROMO = RTMB_LIV_IMP * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                            }
                                            LivraisonPromotion livraisonPromotion = new LivraisonPromotion();

                                            livraisonPromotion.setLIVRAISON_PROMO_CODE(LIV.getLIVRAISON_CODE() + list_Promo_AppliqueesAR.get(i).getPROMO_CODE() + "0");
                                            livraisonPromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(i).getPROMO_CODE());
                                            livraisonPromotion.setLIVRAISON_CODE(LIV.getLIVRAISON_CODE());
                                            livraisonPromotion.setLIVRAISONLIGNE_CODE(0);
                                            livraisonPromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(i).getPROMO_NIVEAU());
                                            livraisonPromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(i).getPROMO_MODECALCUL());
                                            livraisonPromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR());
                                            livraisonPromotion.setMONTANT_BRUTE_AV(RTMB_LIV);
                                            livraisonPromotion.setMONTANT_NET_AV(RTMN_LIV);
                                            livraisonPromotion.setVALEUR_PROMO(valeur_PROMO);
                                            livraisonPromotion.setMONTANT_NET_AP(RTMN_LIV - valeur_PROMO);
                                            livraisonPromotion.setGRATUITE_CODE("");

                                            list_PromotionLIV.add(livraisonPromotion);

                                            RTMN_LIV += RTMN_LIV - valeur_PROMO;
                                            Log.d("Remise", "GetPromoAP_LIV_QC: "+ RTMB_LIV);
                                        }

                                    } else {
                                        Double d = valeurLIV / valeurbase;
                                        Integer repetitionPL = d.intValue();
                                        Integer promoRPL = Integer.valueOf(list_Promo_AppliqueesAR.get(i).getPROMO_REPETITIONPALIER());

                                        while (promoRPL <= repetitionPL) {
                                            if (list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR().equals("CA0019")) {//sur net
                                                valeur_PROMO = RTMN_LIV_IMP * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                            } else if (list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR().equals("CA0020")) {//sur brut
                                                valeur_PROMO = RTMB_LIV_IMP * Double.parseDouble(max_PromoPalier.getVALEUR_PROMO()) / 100;
                                            }
                                            LivraisonPromotion livraisonPromotion = new LivraisonPromotion();

                                            livraisonPromotion.setLIVRAISON_PROMO_CODE(LIV.getLIVRAISON_CODE() + list_Promo_AppliqueesAR.get(i).getPROMO_CODE() + "0");
                                            livraisonPromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(i).getPROMO_CODE());
                                            livraisonPromotion.setLIVRAISON_CODE(LIV.getLIVRAISON_CODE());
                                            livraisonPromotion.setLIVRAISONLIGNE_CODE(0);
                                            livraisonPromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(i).getPROMO_NIVEAU());
                                            livraisonPromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(i).getPROMO_MODECALCUL());
                                            livraisonPromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR());
                                            livraisonPromotion.setMONTANT_BRUTE_AV(RTMB_LIV);
                                            livraisonPromotion.setMONTANT_NET_AV(RTMN_LIV);
                                            livraisonPromotion.setVALEUR_PROMO(valeur_PROMO);
                                            livraisonPromotion.setMONTANT_NET_AP(RTMN_LIV - valeur_PROMO);
                                            livraisonPromotion.setGRATUITE_CODE("");

                                            list_PromotionLIV.add(livraisonPromotion);

                                            RTMN_LIV += RTMN_LIV - valeur_PROMO;
                                            promoRPL++;
                                        }
                                    }//fin repetition

                                } else if (list_Promo_AppliqueesAR.get(i).getPROMO_MODECALCUL().equals("CA0016")) {//Gratuite

                                    if (list_Promo_AppliqueesAR.get(i).getPROMO_REPETITIONPALIER().equals("1")) {//repetition
                                        Integer repetitionPL = 1;
                                        valeur_PROMO = 1.0;
                                    } else if (list_Promo_AppliqueesAR.get(i).getPROMO_REPETITIONPALIER().equals("0")) {//repetition

                                        Double d = valeurLIV / valeurbase;
                                        Integer repetitionPL = d.intValue();
                                        Double valeurpromo = Double.parseDouble(max_PromoPalier.getVALEUR_PROMO());
                                        valeur_Promo = Double.valueOf(repetitionPL*valeurpromo);
                                    } else {

                                        Double d = valeurLIV / valeurbase;
                                        Integer repetitionPL = d.intValue();
                                        Integer promoRPL = Integer.valueOf(list_Promo_AppliqueesAR.get(i).getPROMO_REPETITIONPALIER());
                                        Integer repetitionAP = 0;

                                        while (promoRPL <= repetitionPL) {
                                            repetitionAP++;
                                            promoRPL++;
                                        }
                                        valeur_Promo = Double.valueOf(repetitionAP);
                                    }

                                    Log.d("Remise", "valeur_Promo = " + valeur_Promo);
                                    LivraisonPromotion livraisonPromotion = new LivraisonPromotion();

                                    livraisonPromotion.setLIVRAISON_PROMO_CODE(LIV.getLIVRAISON_CODE() + list_Promo_AppliqueesAR.get(i).getPROMO_CODE() + "0");
                                    livraisonPromotion.setPROMO_CODE(list_Promo_AppliqueesAR.get(i).getPROMO_CODE());
                                    livraisonPromotion.setLIVRAISON_CODE(LIV.getLIVRAISON_CODE());
                                    livraisonPromotion.setLIVRAISONLIGNE_CODE(0);
                                    livraisonPromotion.setPROMO_NIVEAU(list_Promo_AppliqueesAR.get(i).getPROMO_NIVEAU());
                                    livraisonPromotion.setPROMO_MODECALCUL(list_Promo_AppliqueesAR.get(i).getPROMO_MODECALCUL());
                                    livraisonPromotion.setPROMO_APPLIQUESUR(list_Promo_AppliqueesAR.get(i).getPROMO_APPLIQUESUR());
                                    livraisonPromotion.setMONTANT_BRUTE_AV(RTMB_LIV);
                                    livraisonPromotion.setMONTANT_NET_AV(RTMN_LIV);
                                    livraisonPromotion.setVALEUR_PROMO(valeur_Promo);
                                    livraisonPromotion.setMONTANT_NET_AP(RTMN_LIV);
                                    livraisonPromotion.setGRATUITE_CODE(list_Promo_AppliqueesAR.get(i).getPROMO_CODE() + "0");

                                    Log.d("Remise", "livraisonpromotion = " + livraisonPromotion.toString());

                                    list_PromotionLIV.add(livraisonPromotion);

                                }//fin mode calcul

                            }//fin promo palier
                        }// if ligne liv impliquee
                    }//if livraison
                }

            }//if table remise applique >0

            Log.d("Remise", "list_PromotionLIV = "+list_PromotionLIV.toString());
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Promotion error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("Promotion: ",e.getMessage());
        }
        Log.d("Remise", "livraisonpromotion = " + list_PromotionLIV.toString());

        return list_PromotionLIV;
    }
    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deletePromotions() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_PROMOTION, null, null);
        db.close();
        Log.d(TAG, "Deleted all promotions info from sqlite");
    }

   /* public int delete(String promocode) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PROMOTION,KEY_PROMO_CODE+"=?",new String[]{promocode});
    }*/

    public int delete(String tacheCode,String version) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PROMOTION,KEY_PROMO_CODE+"=? AND "+KEY_VERSION+"=?",new String[]{tacheCode,version});
    }

    public static void synchronisationPromotion(final Context context){

        // Tag used to cancel the request
        String tag_string_req = "PROMOTION";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_PROMOTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogSyncManager logM= new LogSyncManager(context);
                Log.d("promotion", "onResponse: "+response);
                int cptInsert = 0,cptDeleted = 0;
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray promotions = jObj.getJSONArray("Promotions");
                        //Toast.makeText(context, "nombre de Promotion  "+promotions.length() , Toast.LENGTH_SHORT).show();
                        PromotionManager promotionManager = new PromotionManager(context);
                        for (int i = 0; i < promotions.length(); i++) {
                            JSONObject unePromotion = promotions.getJSONObject(i);
                            if (unePromotion.getString("OPERATION").equals("DELETE")) {
                                //Toast.makeText(context, "\nPromotion a supprimer   "+promotions.getJSONObject(i).getString("PROMO_CODE"), Toast.LENGTH_LONG).show();
                                promotionManager.delete(unePromotion.getString("PROMO_CODE"),unePromotion.getString("VERSION"));
                                cptDeleted++;
                            } else {
                                promotionManager.add(new Promotion(unePromotion));
                                cptInsert++;
                            }
                        }
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PROMOTION: Inserted: "+cptInsert +" Deleted: "+cptDeleted ,"PROMOTION",1));

                        }
                        //logM.add("PROMO:OK Insert "+cptInsert +" Del"+cptDeleted ,"SyncPromo");

                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(context,"Promotion :"+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("PROMO:NOK "+errorMsg ,"SyncPromo");
                        if(SyncV2Activity.logSyncViewModel != null){
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PROMOTION: Error: "+errorMsg ,"PROMOTION",0));

                        }


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "Promotion :"+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("PROMO:NOK "+e.getMessage() ,"SyncPromo");
                    if(SyncV2Activity.logSyncViewModel != null){
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PROMOTION: Error: "+e.getMessage() ,"PROMOTION",0));

                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Promotion :"+error.getMessage(), Toast.LENGTH_LONG).show();

                //LogSyncManager logM= new LogSyncManager(context);
                if(SyncV2Activity.logSyncViewModel != null){
                    SyncV2Activity.logSyncViewModel.setListMutableLiveData(new LogSync("PROMOTION: Error: "+error.getMessage(),"PROMOTION",0));

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //Map<String, String> params = new HashMap<String, String>();
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {
                    PromotionManager promotionManager = new PromotionManager(context);
                    List<Promotion> promotions=promotionManager.getList();

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    Log.d("UC PROMO SYNC",gson.toJson(pref.getString("UTILISATEUR_CODE",null)));

                    arrayFinale.put("Params",gson.toJson(TabParams));
                    arrayFinale.put("Data",gson.toJson(promotions));
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
