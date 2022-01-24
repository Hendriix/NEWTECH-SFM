package com.newtech.newtech_sfm.Metier_Manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Configuration.AppConfig;
import com.newtech.newtech_sfm.Configuration.SQLiteHandler;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.CommandeGratuite;
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier.Credit;
import com.newtech.newtech_sfm.Metier.Encaissement;
import com.newtech.newtech_sfm.Metier.Impression;
import com.newtech.newtech_sfm.Metier.Livraison;
import com.newtech.newtech_sfm.Metier.LivraisonGratuite;
import com.newtech.newtech_sfm.Metier.LivraisonLigne;
import com.newtech.newtech_sfm.Metier.StockDemande;
import com.newtech.newtech_sfm.Metier.StockDemandeLigne;
import com.newtech.newtech_sfm.Metier.StockLigne;
import com.newtech.newtech_sfm.Metier.Unite;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by TONPC on 02/06/2017.
 */

public class ImpressionManager extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    // Database Name
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;
    // Client table name
    public static final String TABLE_IMPRESSION = "impression";

    private static final String
            KEY_ID="ID",
            KEY_IMPRESSION_CODE="IMPRESSION_CODE",
            KEY_IMPRESSION_TEXT="IMPRESSION_TEXT",
            KEY_STATUT_CODE="STATUT_CODE",
            KEY_TYPE_CODE="TYPE_CODE",
            KEY_CATEGORIE_CODE="CATEGORIE_CODE",
            KEY_CREATEUR_CODE="CREATEUR_CODE",
            KEY_DATE_CREATION="DATE_CREATION",
            KEY_COMMENTAIRE="COMMENTAIRE"
                    ;

    public static  String CREATE_IMPRESSION_TABLE = "CREATE TABLE " + TABLE_IMPRESSION + " ("
            +KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            +KEY_IMPRESSION_CODE + " TEXT ,"
            + KEY_IMPRESSION_TEXT + " TEXT,"
            + KEY_STATUT_CODE + " NUMERIC,"
            + KEY_TYPE_CODE + " TEXT,"
            + KEY_CATEGORIE_CODE + " TEXT,"
            + KEY_CREATEUR_CODE + " TEXT,"
            + KEY_DATE_CREATION + " TEXT,"
            + KEY_COMMENTAIRE + " TEXT"
            + ")";



    public ImpressionManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_CLIENT);
            db.execSQL(CREATE_IMPRESSION_TABLE);

        } catch (SQLException e) {
        }
        Log.d(TAG, "Database tables impression created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMPRESSION);
        // Create tables again
        onCreate(db);

    }

    public void add(Impression impression) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IMPRESSION_CODE, impression.getIMPRESSION_CODE());
        values.put(KEY_IMPRESSION_TEXT, impression.getIMPRESSION_TEXT());
        values.put(KEY_STATUT_CODE, impression.getSTATUT_CODE());
        values.put(KEY_TYPE_CODE, impression.getTYPE_CODE());
        values.put(KEY_CATEGORIE_CODE, impression.getCATEGORIE_CODE());
        values.put(KEY_CREATEUR_CODE, impression.getCREATEUR_CODE());
        values.put(KEY_DATE_CREATION, impression.getDATE_CREATION());
        values.put(KEY_COMMENTAIRE, impression.getCOMMENTAIRE());


        // Inserting Row
        long id = db.insertWithOnConflict(TABLE_IMPRESSION, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        Log.d(TAG, "New impression inserted into sqlite: " + id);
    }


    public ArrayList<Impression> getList() {
        ArrayList<Impression> impressions = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_IMPRESSION ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Impression impression = new Impression();
                impression.setID(cursor.getInt(0));
                impression.setIMPRESSION_CODE(cursor.getString(1));
                impression.setIMPRESSION_TEXT(cursor.getString(2));
                impression.setSTATUT_CODE(cursor.getInt(3));
                impression.setTYPE_CODE(cursor.getString(4));
                impression.setCATEGORIE_CODE(cursor.getString(5));
                impression.setCREATEUR_CODE(cursor.getString(6));
                impression.setDATE_CREATION(cursor.getString(7));
                impression.setCOMMENTAIRE(cursor.getString(8));

                impressions.add(impression);
            }while(cursor.moveToNext());
        }
        //returner la gpstrackers;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching impressions from Sqlite: ");
        return impressions;
    }

    public ArrayList<Impression> getListByStatutCode(int statut) {
        ArrayList<Impression> impressions = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_IMPRESSION +" WHERE "+KEY_STATUT_CODE+" = '"+statut+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ) {
            do {
                Impression impression = new Impression();
                impression.setID(cursor.getInt(0));
                impression.setIMPRESSION_CODE(cursor.getString(1));
                impression.setIMPRESSION_TEXT(cursor.getString(2));
                impression.setSTATUT_CODE(cursor.getInt(3));
                impression.setTYPE_CODE(cursor.getString(4));
                impression.setCATEGORIE_CODE(cursor.getString(5));
                impression.setCREATEUR_CODE(cursor.getString(6));
                impression.setDATE_CREATION(cursor.getString(7));
                impression.setCOMMENTAIRE(cursor.getString(8));

                impressions.add(impression);
            }while(cursor.moveToNext());
        }
        //returner la gpstrackers;
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching impressions from Sqlite: ");
        return impressions;
    }

    public void UpdateImpressionStatut(String impression_code,int statut_code) {

        String selectQuery = "UPDATE impression SET STATUT_CODE = '"+statut_code+"' WHERE IMPRESSION_CODE = '"+impression_code+"'" ;
        Log.d("update", "UpdateImpression: "+selectQuery.toString());

        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(selectQuery);
        db.close();

    }

    static public String ImprimerCommande(Commande commande, Context context) {


        //////////////////////////////////////////////////VARIABLES/////////////////////////////////////////////////
        String coordonnes="";
        String societe="";
        String User_Name="";
        String User_tel="";
        String User_distributeur="";
        Double DROIT_TIMBRE = 0.0;
        Double TTC_BRUT = 0.0;
        Double REMISE = 0.0;
        Double TTC_APRES_REMISE = 0.0,TVA = 0.0,NET_PAYER = 0.0;
        Double SOMME_ENCAISSEMENT = 0.0;
        Double SOMME_CREDIT = 0.0;
        Double SOMME_ESPECE = 0.0;
        Double SOMME_CHEQUE = 0.0;
        Double RESTE = 0.0;
        DecimalFormat formatDD = new DecimalFormat("#.00");
        DecimalFormat formatD = new DecimalFormat("#.0");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_impression=df.format(Calendar.getInstance().getTime());
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////


        //////////////////////////////////////////////////MANAGER/////////////////////////////////////////////////
        TourneeManager tourneeManager = new TourneeManager(context);
        CommandeGratuiteManager commandeGratuiteManager = new CommandeGratuiteManager(context);
        CommandeLigneManager CommandeLigneManager = new CommandeLigneManager(context);
        ArticleManager articleManager=new ArticleManager(context);
        ClientManager clientManager = new ClientManager(context);
        DistributeurManager distributeurManager = new DistributeurManager(context);
        EncaissementManager encaissementManager = new EncaissementManager(context);
        UniteManager uniteManager = new UniteManager(context);
        CreditManager creditManager = new CreditManager(context);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////


        //////////////////////////////////////////////VARIABLES OBJETS/////////////////////////////////////////////
        ArrayList<CommandeGratuite> list_commande_gratuite = new ArrayList<CommandeGratuite>();
        ArrayList<CommandeLigne> commandeLignes = new ArrayList<CommandeLigne>();
        ArrayList<Encaissement> encaissements = new ArrayList<Encaissement>();
        ArrayList<Credit> credits = new ArrayList<>();
        Client client_commande = new Client();
        Unite unite = new Unite();
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////


        //////////////////////////////////////////////////OBJETS///////////////////////////////////////////////////
        list_commande_gratuite=commandeGratuiteManager.getListbyCmdCode(commande.getCOMMANDE_CODE());
        Log.d(TAG, "ImprimerCommande: "+list_commande_gratuite.toString());
        commandeLignes=CommandeLigneManager.getListByCommandeCode(commande.getCOMMANDE_CODE());
        client_commande= clientManager.get(commande.getCLIENT_CODE());
        encaissements= encaissementManager.getListByCmdCode(commande.getCOMMANDE_CODE());
        credits = creditManager.getListByCmdCode(commande.getCOMMANDE_CODE());
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////SHARED PREFERENCES/////////////////////////////////////////

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);


        try {
            User_Name= user.getString("UTILISATEUR_NOM");
            User_tel= user.getString("UTILISATEUR_TELEPHONE1");
            User_distributeur = user.getString("DISTRIBUTEUR_CODE");

        }catch (JSONException e) {
            e.printStackTrace();
        }

        if(User_distributeur==null){
            User_distributeur="DISTRIBUTEUR";
        }else{
            coordonnes = distributeurManager.get(User_distributeur).getDISTRIBUTEUR_ENTETE();
            societe = distributeurManager.get(User_distributeur).getDISTRIBUTEUR_NOM();
            Log.d("ENTETE ", " entete "+coordonnes);

            if(coordonnes==null){
                coordonnes = "ENTETE";
            }
        }
        if(User_tel==null){
            User_tel="0606060606";
        }
        if(User_Name==null){
            User_Name="UTILISATEUR";
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////SOMME MONTANT ENCAISSEMENT///////////////////////////////////

        if(encaissements.size()>0){

            for(int i=0;i<encaissements.size();i++){
                SOMME_ENCAISSEMENT+=encaissements.get(i).getMONTANT();
            }
        }

        if(credits.size()>0){
            for(int i=0;i<credits.size();i++){
                SOMME_CREDIT+=credits.get(i).getMONTANT_CREDIT();
            }
        }

        SOMME_ESPECE = encaissementManager.getSumEncByCcTC(commande.getCOMMANDE_CODE(), "ESPECE");
        SOMME_CHEQUE = encaissementManager.getSumEncByCcTC(commande.getCOMMANDE_CODE(), "CHEQUE");
        DROIT_TIMBRE =encaissementManager.calcDroitTimbre(SOMME_ESPECE);
        //DROIT_TIMBRE = commande.getMONTANT_BRUT()*(0.25/100);

        Log.d(TAG, "ImprimerCommande: "+DROIT_TIMBRE);

        RESTE = (commande.getMONTANT_NET())-SOMME_ENCAISSEMENT-SOMME_CREDIT;

        if(RESTE<=0){
            RESTE = 0.0;
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////CHAINE A IMPRIMER////////////////////////////////////////

        String message ="              "+societe+"                    \r\n" +
                "----------------------------------------------------\r\n" +
                coordonnes+"\r\n" +
                //chaine2+"\r\n" +
                //chaine3+"\r\n" +
                "----------------------------------------------------\r\n" +
                "Facture Nr:"+commande.getCOMMANDE_CODE()+"\r\n" +
                "Date:"+commande.getDATE_LIVRAISON() +"\r\n" +
                "Date impression :"+date_impression +"\r\n" +
                "Client:"+ commande.getCLIENT_CODE()+" " + client_commande.getCLIENT_NOM()+" TEL:"+client_commande.getCLIENT_TELEPHONE1()+"\r\n" +
                "Adresse:"+client_commande.getADRESSE_NR()+" "+client_commande.getADRESSE_RUE()+" "+client_commande.getADRESSE_QUARTIER()+"\r\n" +
                "Tournee:"+ tourneeManager.get(commande.getTOURNEE_CODE()).getTOURNEE_NOM()+"\n"+
                "Vendeur:"+User_Name+"    TEL:"+User_tel+"\n" +
                "----------------------------------------------------\n" +
                "Produit     |U|QTE |PU   |Mt.Brut|Remise|Mt.Net\r\n" +
                "----------------------------------------------------\r\n";

        for(int i=0;i<commandeLignes.size();i++) {
            unite = uniteManager.get(commandeLignes.get(i).getUNITE_CODE());
            Double QTE =commandeLignes.get(i).getQTE_COMMANDEE();

            message += Nchaine(commandeLignes.get(i).getARTICLE_DESIGNATION(),12) + "|"
                    +NchaineL(String.valueOf(unite.getUNITE_NOM()),1)+"|"
                    +NchaineL(String.valueOf(QTE.intValue()),4)+"|"
                    +NchaineL(String.valueOf(formatD.format(commandeLignes.get(i).getARTICLE_PRIX())),5)+"|"
                    +NchaineL(String.valueOf(formatD.format(commandeLignes.get(i).getMONTANT_BRUT())),7)+"|"
                    +NchaineL(String.valueOf(formatD.format(commandeLignes.get(i).getREMISE())),6)+"|"+
                    NchaineL(String.valueOf(formatD.format(commandeLignes.get(i).getMONTANT_NET())),6) + "\r\n";

            TTC_BRUT+=commandeLignes.get(i).getMONTANT_BRUT();
            REMISE+=commandeLignes.get(i).getREMISE();
            NET_PAYER+=commandeLignes.get(i).getMONTANT_NET();
        }

        String message2= "----------------------------------------------------\r\n" +
                "       TOTAL TTC Brut         :"+NchaineL(String.valueOf(formatDD.format(commande.getMONTANT_BRUT())),8)+"\r\n" +
                "       Montant Remise         :"+NchaineL(String.valueOf(formatDD.format(commande.getREMISE())),8)+"\r\n" +
                "       TOTAL TTC avec Remise  :"+NchaineL(String.valueOf(formatDD.format(commande.getMONTANT_NET())),8)+"\r\n" +
                //"       Montant TVA          :"+NchaineL(String.valueOf(formatDD.format(TVA)),8)+"\r\n" +
                "       DROIT Timbre           :"+NchaineL(String.valueOf(formatDD.format(DROIT_TIMBRE)),8)+"\r\n" +
                "       NET A payer            :"+NchaineL(String.valueOf(formatDD.format(commande.getMONTANT_NET()+DROIT_TIMBRE)),8)+"\r\n" +
                "       Montant Paye           :"+NchaineL(String.valueOf(formatDD.format(SOMME_ENCAISSEMENT+SOMME_CREDIT+DROIT_TIMBRE)),8)+"\r\n" +
                "       RESTE A payer          :"+NchaineL(String.valueOf(formatDD.format(RESTE)),8)+"\r\n" +
                //"       Mode de paiement       :"+NchaineL(String.valueOf("ESPECE"),8)+"\r\n" +
                "----------------------------------------------------\r\n\r\n";

        ////////////////////////////////////////////////////////GRATUITE///////////////////////////////////////////
        String message4 = "";
        if(list_commande_gratuite.size()>0){

            message4+="\"Gratuite:\r\n" +
                    "Article             |QTE\r\n" +
                    "-----------------------------\r\n";
            for(int i=0;i<list_commande_gratuite.size();i++){
                Article article = new Article();
                article=articleManager.get(list_commande_gratuite.get(i).getARTICLE_CODE());
                message4+=Nchaine(article.getARTICLE_DESIGNATION1(),20) + "|"+NchaineL(String.valueOf(list_commande_gratuite.get(i).getQUANTITE()),4) + "\r\n";
            }
        }

        String message3= "-------------------ENCAISSEMENT---------------\r\n" +
                "       ESPECE         :"+NchaineL(String.valueOf(formatDD.format(SOMME_ESPECE+DROIT_TIMBRE)),8)+"\r\n" +
                "       CHEQUE         :"+NchaineL(String.valueOf(formatDD.format(SOMME_CHEQUE)),8)+"\r\n" +
                "       CREDIT         :"+NchaineL(String.valueOf(formatDD.format(SOMME_CREDIT)),8)+"\r\n" +
                "----------------------------------------------------\r\n\r\n";

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        message+=message2+message3+message4+"\r\n\r\n\r\n\r\n";
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        return message;
    }

    static public String ImprimerLivraison(Livraison livraison, Context context) {


        //////////////////////////////////////////////////VARIABLES/////////////////////////////////////////////////
        String coordonnes="";
        String societe="";
        String User_Name="";
        String User_tel="";
        String User_distributeur="";
        Double DROIT_TIMBRE = 0.0;
        Double TTC_BRUT = 0.0;
        Double REMISE = 0.0;
        Double TTC_APRES_REMISE = 0.0,TVA = 0.0,NET_PAYER = 0.0;
        Double SOMME_ENCAISSEMENT = 0.0;
        Double SOMME_CREDIT = 0.0;
        Double SOMME_ESPECE = 0.0;
        Double SOMME_CHEQUE = 0.0;
        Double RESTE = 0.0;
        DecimalFormat formatDD = new DecimalFormat("#.00");
        DecimalFormat formatD = new DecimalFormat("#.0");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_impression=df.format(Calendar.getInstance().getTime());
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        String adresse_numero = "";
        String adresse_rue = "";
        String adresse_quartier ="";
        String tournee_nom="";
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////


        //////////////////////////////////////////////////MANAGER/////////////////////////////////////////////////
        TourneeManager tourneeManager = new TourneeManager(context);
        LivraisonGratuiteManager livraisonGratuiteManager = new LivraisonGratuiteManager(context);
        LivraisonLigneManager LivraisonLigneManager = new LivraisonLigneManager(context);
        ArticleManager articleManager=new ArticleManager(context);
        ClientManager clientManager = new ClientManager(context);
        DistributeurManager distributeurManager = new DistributeurManager(context);
        EncaissementManager encaissementManager = new EncaissementManager(context);
        UniteManager uniteManager = new UniteManager(context);
        CreditManager creditManager = new CreditManager(context);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////


        //////////////////////////////////////////////VARIABLES OBJETS/////////////////////////////////////////////
        ArrayList<LivraisonGratuite> list_livraison_gratuite = new ArrayList<LivraisonGratuite>();
        ArrayList<LivraisonLigne> livraisonLignes = new ArrayList<LivraisonLigne>();
        ArrayList<Encaissement> encaissements = new ArrayList<Encaissement>();
        ArrayList<Credit> credits = new ArrayList<>();
        Client client_livraison = new Client();
        Unite unite = new Unite();
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////


        //////////////////////////////////////////////////OBJETS///////////////////////////////////////////////////
        list_livraison_gratuite=livraisonGratuiteManager.getListbyLivraisonCode(livraison.getLIVRAISON_CODE());
        Log.d(TAG, "ImprimerLivraison: "+list_livraison_gratuite.toString());
        livraisonLignes=LivraisonLigneManager.getListByLivraisonCode(livraison.getLIVRAISON_CODE());
        Log.d(TAG, "ImprimerLivraison: "+list_livraison_gratuite.toString());
        client_livraison= clientManager.get(livraison.getCLIENT_CODE());
        encaissements= encaissementManager.getListByCmdCode(livraison.getLIVRAISON_CODE());
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////SHARED PREFERENCES/////////////////////////////////////////

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);


        try {
            User_Name= user.getString("UTILISATEUR_NOM");
            User_tel= user.getString("UTILISATEUR_TELEPHONE1");
            User_distributeur = user.getString("DISTRIBUTEUR_CODE");

        }catch (JSONException e) {
            e.printStackTrace();
        }

        if(User_distributeur==null){
            User_distributeur="DISTRIBUTEUR";
        }else{
            coordonnes = distributeurManager.get(User_distributeur).getDISTRIBUTEUR_ENTETE();
            societe = distributeurManager.get(User_distributeur).getDISTRIBUTEUR_NOM();
            Log.d("ENTETE ", " entete "+coordonnes);

            if(coordonnes==null){
                coordonnes = "ENTETE";
            }
        }
        if(User_tel==null){
            User_tel="0606060606";
        }
        if(User_Name==null){
            User_Name="UTILISATEUR";
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////SOMME MONTANT ENCAISSEMENT///////////////////////////////////

        if(encaissements.size()>0){

            for(int i=0;i<encaissements.size();i++){
                SOMME_ENCAISSEMENT+=encaissements.get(i).getMONTANT();
            }
        }

        if(credits.size()>0){
            for(int i=0;i<credits.size();i++){
                SOMME_CREDIT+=credits.get(i).getMONTANT_CREDIT();
            }
        }

        SOMME_ESPECE = encaissementManager.getSumEncByCcTC(livraison.getLIVRAISON_CODE(), "ESPECE");
        SOMME_CHEQUE = encaissementManager.getSumEncByCcTC(livraison.getLIVRAISON_CODE(), "CHEQUE");
        DROIT_TIMBRE =encaissementManager.calcDroitTimbre(SOMME_ESPECE);
        //DROIT_TIMBRE = commande.getMONTANT_BRUT()*(0.25/100);

        Log.d(TAG, "ImprimerCommande: "+DROIT_TIMBRE);

        RESTE = (livraison.getMONTANT_NET())-SOMME_ENCAISSEMENT-SOMME_CREDIT;

        if(RESTE<=0){
            RESTE = 0.0;
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        if(client_livraison.getADRESSE_NR().equals("null")){
            adresse_numero = "N°X ";
        }else{
            adresse_numero = client_livraison.getADRESSE_NR();
        }

        if(client_livraison.getADRESSE_RUE().equals("null")){
            adresse_rue = "Rue x ";
        }else{
            adresse_rue = client_livraison.getADRESSE_RUE();
        }

        if(client_livraison.getADRESSE_QUARTIER().equals("null")){
            adresse_quartier = "Quartier x";
        }else{
            adresse_quartier = client_livraison.getADRESSE_QUARTIER();
        }


        //////////////////////////////////////////////////CHAINE A IMPRIMER////////////////////////////////////////

        String message ="              "+societe+"                    \r\n" +
                "----------------------------------------------------\r\n" +
                coordonnes+"\r\n" +
                //chaine2+"\r\n" +
                //chaine3+"\r\n" +
                "----------------------------------------------------\r\n" +
                "Facture Nr:"+livraison.getLIVRAISON_CODE()+"\r\n" +
                "Date:"+livraison.getDATE_LIVRAISON() +"\r\n" +
                "Date impression :"+date_impression +"\r\n" +
                "Client:"+ livraison.getCLIENT_CODE()+" " + client_livraison.getCLIENT_NOM()+" TEL:"+client_livraison.getCLIENT_TELEPHONE1()+"\r\n" +
                "Adresse:"+adresse_numero+" "+adresse_rue+" "+adresse_quartier+"\r\n" +
                "Tournee:"+ livraison.getTOURNEE_CODE() +"\n"+
                "Vendeur:"+User_Name+"    TEL:"+User_tel+"\n" +
                "----------------------------------------------------\n" +
                "Produit       |U|QTE |PU   |Mt.Brut|Remise|Mt.Net\r\n" +
                "----------------------------------------------------\r\n";

        for(int i=0;i<livraisonLignes.size();i++) {
            unite = uniteManager.get(livraisonLignes.get(i).getUNITE_CODE());
            Double QTE =livraisonLignes.get(i).getQTE_COMMANDEE();

            message += Nchaine(livraisonLignes.get(i).getARTICLE_DESIGNATION(),14) + "|"
                    +NchaineL(String.valueOf(unite.getUNITE_NOM()),1)+"|"
                    +NchaineL(String.valueOf(QTE.intValue()),5)+"|"
                    +NchaineL(String.valueOf(formatD.format(livraisonLignes.get(i).getARTICLE_PRIX())),5)+"|"
                    +NchaineL(String.valueOf(formatD.format(livraisonLignes.get(i).getMONTANT_BRUT())),7)+"|"
                    +NchaineL(String.valueOf(formatD.format(livraisonLignes.get(i).getREMISE())),6)+"|"+
                    NchaineL(String.valueOf(formatD.format(livraisonLignes.get(i).getMONTANT_NET())),6) + "\r\n";

            TTC_BRUT+=livraisonLignes.get(i).getMONTANT_BRUT();
            REMISE+=livraisonLignes.get(i).getREMISE();
            NET_PAYER+=livraisonLignes.get(i).getMONTANT_NET();
        }

        String message2= "----------------------------------------------------\r\n" +
                "       TOTAL TTC Brut         :"+NchaineL(String.valueOf(formatDD.format(livraison.getMONTANT_BRUT())),8)+"\r\n" +
                "       Montant Remise         :"+NchaineL(String.valueOf(formatDD.format(livraison.getREMISE())),8)+"\r\n" +
                "       TOTAL TTC avec Remise  :"+NchaineL(String.valueOf(formatDD.format(livraison.getMONTANT_NET())),8)+"\r\n" +
                //"       Montant TVA          :"+NchaineL(String.valueOf(formatDD.format(TVA)),8)+"\r\n" +
                "       DROIT Timbre           :"+NchaineL(String.valueOf(formatDD.format(DROIT_TIMBRE)),8)+"\r\n" +
                "       NET A payer            :"+NchaineL(String.valueOf(formatDD.format(livraison.getMONTANT_NET()+DROIT_TIMBRE)),8)+"\r\n" +
                "       Montant Paye           :"+NchaineL(String.valueOf(formatDD.format(SOMME_ENCAISSEMENT+SOMME_CREDIT+DROIT_TIMBRE)),8)+"\r\n" +
                "       RESTE A payer          :"+NchaineL(String.valueOf(formatDD.format(RESTE)),8)+"\r\n" +
                //"       Mode de paiement       :"+NchaineL(String.valueOf("ESPECE"),8)+"\r\n" +
                "----------------------------------------------------\r\n\r\n";

        ////////////////////////////////////////////////////////GRATUITE///////////////////////////////////////////
        ////////////////////////////////////////////////////////GRATUITE///////////////////////////////////////////
        String message4 = "";
        if(list_livraison_gratuite.size()>0){

            message4+="\"Gratuite:\r\n" +
                    "Article             |QTE\r\n" +
                    "-----------------------------\r\n";
            for(int i=0;i<list_livraison_gratuite.size();i++){
                Article article = new Article();
                article=articleManager.get(list_livraison_gratuite.get(i).getARTICLE_CODE());
                message4+=Nchaine(article.getARTICLE_DESIGNATION1(),20) + "|"+NchaineL(String.valueOf(list_livraison_gratuite.get(i).getQUANTITE()),4) + "\r\n";
            }
        }

        String message3= "-------------------ENCAISSEMENT---------------\r\n" +
                "       ESPECE         :"+NchaineL(String.valueOf(formatDD.format(SOMME_ESPECE+DROIT_TIMBRE)),8)+"\r\n" +
                "       CHEQUE         :"+NchaineL(String.valueOf(formatDD.format(SOMME_CHEQUE)),8)+"\r\n" +
                "       CREDIT         :"+NchaineL(String.valueOf(formatDD.format(SOMME_CREDIT)),8)+"\r\n" +
                "----------------------------------------------------\r\n\r\n";

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        message+=message2+message3+message4+"\r\n\r\n\r\n\r\n";
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        return message;
    }

    static public String ImprimerStockDemande(StockDemande stockDemande, Context context) {


        //////////////////////////////////////////////////VARIABLES/////////////////////////////////////////////////
        String coordonnes="";
        String societe="";
        String User_Name="";
        String User_tel="";
        String User_distributeur="";
        Double DROIT_TIMBRE = 0.0;
        Double TTC_BRUT = 0.0;
        Double REMISE = 0.0;
        Double TTC_APRES_REMISE = 0.0,TVA = 0.0,NET_PAYER = 0.0;
        Double SOMME_ENCAISSEMENT = 0.0;
        Double RESTE = 0.0;
        DecimalFormat formatDD = new DecimalFormat("#.00");
        DecimalFormat formatD = new DecimalFormat("#.0");
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////


        //////////////////////////////////////////////////MANAGER/////////////////////////////////////////////////
        TourneeManager tourneeManager = new TourneeManager(context);
        StockDemandeLigneManager stockDemandeLigneManager = new StockDemandeLigneManager(context);
        ArticleManager articleManager=new ArticleManager(context);
        ClientManager clientManager = new ClientManager(context);
        DistributeurManager distributeurManager = new DistributeurManager(context);
        UniteManager uniteManager = new UniteManager(context);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////


        //////////////////////////////////////////////VARIABLES OBJETS/////////////////////////////////////////////

        ArrayList<StockDemandeLigne> stockDemandeLignes = new ArrayList<StockDemandeLigne>();
        Client client_commande = new Client();
        Unite unite = new Unite();
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////


        //////////////////////////////////////////////////OBJETS///////////////////////////////////////////////////

        stockDemandeLignes=stockDemandeLigneManager.getListByDemandeCode(stockDemande.getDEMANDE_CODE());

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////SHARED PREFERENCES/////////////////////////////////////////

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);


        try {
            User_Name= user.getString("UTILISATEUR_NOM");
            User_tel= user.getString("UTILISATEUR_TELEPHONE1");
            User_distributeur = user.getString("DISTRIBUTEUR_CODE");

        }catch (JSONException e) {
            e.printStackTrace();
        }

        if(User_distributeur==null){
            User_distributeur="DISTRIBUTEUR";
        }else{
            coordonnes = distributeurManager.get(User_distributeur).getDISTRIBUTEUR_ENTETE();
            societe = distributeurManager.get(User_distributeur).getDISTRIBUTEUR_NOM();
            Log.d("ENTETE ", " entete "+coordonnes);

            if(coordonnes==null){
                coordonnes = "ENTETE";
            }
        }
        if(User_tel==null){
            User_tel="0606060606";
        }
        if(User_Name==null){
            User_Name="UTILISATEUR";
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////CHAINE A IMPRIMER////////////////////////////////////////

        String message ="              "+societe+"                    \r\n" +
                "----------------------------------------------------\r\n" +
                coordonnes+"\r\n" +
                //chaine2+"\r\n" +
                //chaine3+"\r\n" +
                "----------------------------------------------------\r\n" +
                "Date:"+stockDemande.getDEMANDE_DATE() +"\r\n" +
                "Vendeur:"+User_Name+"    TEL:"+User_tel+"\n" +
                "----------------------------------------------------\n" +
                "Article   |Designation     |U|Cmd   |Liv   |Rec    \r\n" +
                "----------------------------------------------------\r\n";

        for(int i=0;i<stockDemandeLignes.size();i++) {
            unite = uniteManager.get(stockDemandeLignes.get(i).getUNITE_CODE());
            int CMD =stockDemandeLignes.get(i).getQTE_COMMANDEE();
            int LIV =stockDemandeLignes.get(i).getQTE_LIVREE();
            int REC =stockDemandeLignes.get(i).getQTE_RECEPTIONEE();

            message += Nchaine(stockDemandeLignes.get(i).getARTICLE_CODE(),10) + "|"
                    +Nchaine(stockDemandeLignes.get(i).getARTICLE_DESIGNATION(),16)+"|"
                    +Nchaine(String.valueOf(unite.getUNITE_NOM()),1)+"|"
                    +Nchaine(String.valueOf(CMD),6)+"|"
                    +Nchaine(String.valueOf(LIV),6)+"|"
                    +Nchaine(String.valueOf(REC),5)+"\r\n";

        }

        String message2= "----------------------------------------------------\r\n" +

                        "----------------------------------------------------\r\n\r\n";



        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        message+=message2+"\r\n\r\n\r\n\r\n";
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        return message;
    }

    static public String ImprimerStockLignes(ArrayList<StockLigne> stockLignes, Context context) {

        //////////////////////////////////////////////////VARIABLES/////////////////////////////////////////////////
        String coordonnes="";
        String societe="";
        String User_Name="";
        String User_tel="";
        String User_distributeur="";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_impression=df.format(Calendar.getInstance().getTime());
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////MANAGER/////////////////////////////////////////////////
        DistributeurManager distributeurManager = new DistributeurManager(context);
        UniteManager uniteManager = new UniteManager(context);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////SHARED PREFERENCES/////////////////////////////////////////

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);

        try {
            User_Name= user.getString("UTILISATEUR_NOM");
            User_tel= user.getString("UTILISATEUR_TELEPHONE1");
            User_distributeur = user.getString("DISTRIBUTEUR_CODE");

        }catch (JSONException e) {
            e.printStackTrace();
        }

        if(User_distributeur==null){
            User_distributeur="DISTRIBUTEUR";
        }else{
            coordonnes = distributeurManager.get(User_distributeur).getDISTRIBUTEUR_ENTETE();
            societe = distributeurManager.get(User_distributeur).getDISTRIBUTEUR_NOM();
            Log.d("ENTETE ", " entete "+coordonnes);

            if(coordonnes==null){
                coordonnes = "ENTETE";
            }
        }
        if(User_tel==null){
            User_tel="0606060606";
        }
        if(User_Name==null){
            User_Name="UTILISATEUR";
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////CHAINE A IMPRIMER////////////////////////////////////////

        String message ="              "+societe+"                    \r\n" +
                "----------------------------------------------------\r\n" +
                coordonnes+"\r\n" +
                "----------------------------------------------------\r\n" +
                "Date:"+date_impression+"\r\n" +
                "Vendeur:"+User_Name+"    TEL:"+User_tel+"\n" +
                "----------------------------------------------------\n" +
                "ARTICLE   |DESIGNATION       |UNITE    |QTE \r\n" +
                "----------------------------------------------------\r\n";

        for(int i=0;i<stockLignes.size();i++) {
            Unite unite = uniteManager.get(stockLignes.get(i).getUNITE_CODE());
            int CMD =stockLignes.get(i).getQTE();

            message += Nchaine(stockLignes.get(i).getARTICLE_CODE(),10) + "|"
                    +Nchaine(stockLignes.get(i).getARTICLE_DESIGNATION(),18)+"|"
                    +Nchaine(String.valueOf(unite.getUNITE_NOM()),9)+"|"
                    +Nchaine(String.valueOf(CMD),5)+"\r\n";

        }

        String message2= "----------------------------------------------------\r\n\n";
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        message+=message2+"\r\n\r\n"+"Signature : \r\n\n\n";

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        return message;
    }

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

    public double getNumberRounded(double nombre){

        double number = nombre;
        int decimalsToConsider = 2;
        BigDecimal bigDecimal = new BigDecimal(number);
        BigDecimal roundedWithScale = bigDecimal.setScale(decimalsToConsider, BigDecimal.ROUND_HALF_UP);
        //System.out.println("Rounded value with setting scale = "+roundedWithScale);


        return roundedWithScale.doubleValue();
    }

}
