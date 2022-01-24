package com.newtech.newtech_sfm.Metier;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Activity.ViewCommandeActivity;
import com.newtech.newtech_sfm.Metier_Manager.UniteManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;



/**
 * Created by stagiaireit2 on 02/08/2016.
 */
public class CommandeLigne {

    int
    COMMANDELIGNE_CODE;
    String
    COMMANDE_CODE,
    FACTURE_CODE,
    FAMILLE_CODE,
    ARTICLE_CODE,
    UNITE_CODE,
    ARTICLE_DESIGNATION;
    double
    ARTICLE_NBUS_PAR_UP,
    ARTICLE_PRIX,
    QTE_COMMANDEE,
    QTE_LIVREE,
    CAISSE_COMMANDEE,
    CAISSE_LIVREE,
    LITTRAGE_COMMANDEE,
    LITTRAGE_LIVREE,
    TONNAGE_COMMANDEE,
    TONNAGE_LIVREE,
    KG_COMMANDEE,
    KG_LIVREE,
    MONTANT_BRUT,
    REMISE,
    MONTANT_NET;
    String
    COMMENTAIRE,
    CREATEUR_CODE,
    DATE_CREATION,
    SOURCE,
    VERSION;



    public CommandeLigne() {
    }

    public CommandeLigne( JSONObject p) {
        try {
            this.COMMANDE_CODE=p.getString("COMMANDE_CODE");
            this.FACTURE_CODE=p.getString("FACTURE_CODE");
            this.FAMILLE_CODE=p.getString("FAMILLE_CODE");
            this.ARTICLE_CODE=p.getString("ARTICLE_CODE");
            this.ARTICLE_DESIGNATION=p.getString("ARTICLE_DESIGNATION");
            this.ARTICLE_NBUS_PAR_UP=p.getDouble("ARTICLE_NBUS_PAR_UP");
            this.ARTICLE_PRIX=p.getDouble("ARTICLE_PRIX");
            this.QTE_COMMANDEE=p.getDouble("QTE_COMMANDEE");
            this.QTE_LIVREE=p.getDouble("QTE_LIVREE");
            this.CAISSE_COMMANDEE=p.getDouble("CAISSE_COMMANDEE");
            this.CAISSE_LIVREE=p.getDouble("CAISSE_LIVREE");
            this.LITTRAGE_COMMANDEE=p.getDouble("LITTRAGE_COMMANDEE");
            this.LITTRAGE_LIVREE=p.getDouble("LITTRAGE_LIVREE");
            this.TONNAGE_COMMANDEE=p.getDouble("TONNAGE_COMMANDEE");
            this.TONNAGE_LIVREE=p.getDouble("TONNAGE_LIVREE");
            this.KG_COMMANDEE=p.getDouble("KG_COMMANDEE");
            this.KG_LIVREE=p.getDouble("KG_LIVREE");
            this.MONTANT_BRUT=getNumberRounded(p.getDouble("MONTANT_BRUT"));
            this.REMISE=getNumberRounded(p.getDouble("REMISE"));
            this.MONTANT_NET=getNumberRounded(p.getDouble("MONTANT_NET"));
            this.COMMENTAIRE=p.getString("COMMENTAIRE");
            this.CREATEUR_CODE=p.getString("CREATEUR_CODE");
            this.DATE_CREATION=p.getString("DATE_CREATION");
            this.UNITE_CODE=p.getString("UNITE_CODE");
            this.VERSION=p.getString("VERSION");
            this.SOURCE=p.getString("SOURCE");
            this.COMMANDELIGNE_CODE=p.getInt("COMMANDELIGNE_CODE");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public CommandeLigne( CommandeLigneALivrer cmdlAL) {
        try {
            this.COMMANDE_CODE=cmdlAL.getCOMMANDE_CODE();
            this.FACTURE_CODE=cmdlAL.getFACTURE_CODE();
            this.FAMILLE_CODE=cmdlAL.getFAMILLE_CODE();
            this.ARTICLE_CODE=cmdlAL.getARTICLE_CODE();
            this.ARTICLE_DESIGNATION=cmdlAL.getARTICLE_DESIGNATION();
            this.ARTICLE_NBUS_PAR_UP=cmdlAL.getARTICLE_NBUS_PAR_UP();
            this.ARTICLE_PRIX=cmdlAL.getARTICLE_PRIX();
            this.QTE_COMMANDEE=cmdlAL.getQTE_COMMANDEE()-cmdlAL.getQTE_LIVREE();
            this.QTE_LIVREE=cmdlAL.getQTE_LIVREE();
            this.CAISSE_COMMANDEE=cmdlAL.getCAISSE_COMMANDEE()-cmdlAL.getCAISSE_LIVREE();
            this.CAISSE_LIVREE=cmdlAL.getCAISSE_LIVREE();
            this.LITTRAGE_COMMANDEE=cmdlAL.getLITTRAGE_COMMANDEE()-cmdlAL.getLITTRAGE_LIVREE();
            this.LITTRAGE_LIVREE=cmdlAL.getLITTRAGE_LIVREE();
            this.TONNAGE_COMMANDEE=cmdlAL.getTONNAGE_COMMANDEE()-cmdlAL.getTONNAGE_LIVREE();
            this.TONNAGE_LIVREE=cmdlAL.getTONNAGE_LIVREE();
            this.KG_COMMANDEE=cmdlAL.getKG_COMMANDEE()-cmdlAL.getKG_LIVREE();
            this.KG_LIVREE=cmdlAL.getKG_LIVREE();
            this.MONTANT_BRUT=cmdlAL.getMONTANT_BRUT();
            this.REMISE=cmdlAL.getREMISE();
            this.MONTANT_NET=cmdlAL.getMONTANT_NET();
            this.COMMENTAIRE=cmdlAL.getCOMMENTAIRE();
            this.CREATEUR_CODE=cmdlAL.getCREATEUR_CODE();
            this.DATE_CREATION=cmdlAL.getDATE_CREATION();
            this.UNITE_CODE=cmdlAL.getUNITE_CODE();
            this.VERSION=cmdlAL.getVERSION();
            this.SOURCE=cmdlAL.getSOURCE();
            this.COMMANDELIGNE_CODE=cmdlAL.getCOMMANDELIGNE_CODE();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CommandeLigne( CommandeNonClotureeLigne cmdNCL) {

        try {
            this.COMMANDE_CODE=cmdNCL.getCOMMANDE_CODE();
            this.FACTURE_CODE=cmdNCL.getFACTURE_CODE();
            this.FAMILLE_CODE=cmdNCL.getFAMILLE_CODE();
            this.ARTICLE_CODE=cmdNCL.getARTICLE_CODE();
            this.ARTICLE_DESIGNATION=cmdNCL.getARTICLE_DESIGNATION();
            this.ARTICLE_NBUS_PAR_UP=cmdNCL.getARTICLE_NBUS_PAR_UP();
            this.ARTICLE_PRIX=cmdNCL.getARTICLE_PRIX();
            this.QTE_COMMANDEE=cmdNCL.getQTE_COMMANDEE();
            this.QTE_LIVREE=cmdNCL.getQTE_LIVREE();
            this.CAISSE_COMMANDEE=cmdNCL.getCAISSE_COMMANDEE();
            this.CAISSE_LIVREE=cmdNCL.getCAISSE_LIVREE();
            this.LITTRAGE_COMMANDEE=cmdNCL.getLITTRAGE_COMMANDEE();
            this.LITTRAGE_LIVREE=cmdNCL.getLITTRAGE_LIVREE();
            this.TONNAGE_COMMANDEE=cmdNCL.getTONNAGE_COMMANDEE();
            this.TONNAGE_LIVREE=cmdNCL.getTONNAGE_LIVREE();
            this.KG_COMMANDEE=cmdNCL.getKG_COMMANDEE();
            this.KG_LIVREE=cmdNCL.getKG_LIVREE();
            this.MONTANT_BRUT=cmdNCL.getMONTANT_BRUT();
            this.REMISE=cmdNCL.getREMISE();
            this.MONTANT_NET=cmdNCL.getMONTANT_NET();
            this.COMMENTAIRE=cmdNCL.getCOMMENTAIRE();
            this.CREATEUR_CODE=cmdNCL.getCREATEUR_CODE();
            this.DATE_CREATION=cmdNCL.getDATE_CREATION();
            this.UNITE_CODE=cmdNCL.getUNITE_CODE();
            this.VERSION=cmdNCL.getVERSION();
            this.SOURCE=cmdNCL.getSOURCE();
            this.COMMANDELIGNE_CODE=cmdNCL.getCOMMANDELIGNE_CODE();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CommandeLigne(Article monArticle , String date_livraison ,float quantite , String unite,double prix) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        this.FAMILLE_CODE = monArticle.getFAMILLE_CODE();
        this.ARTICLE_CODE = monArticle.getARTICLE_CODE();
        this.ARTICLE_DESIGNATION =monArticle.getARTICLE_DESIGNATION1();
        this.ARTICLE_NBUS_PAR_UP = monArticle.getNBUS_PAR_UP();

        this.ARTICLE_PRIX =prix;
        this.UNITE_CODE=unite;
        this.QTE_COMMANDEE =quantite ;
        this.QTE_LIVREE =0 ;

        if(unite.equals("BOUTEILLE")) this.CAISSE_COMMANDEE =quantite/ARTICLE_NBUS_PAR_UP ;
        if(unite.equals("CAISSE")) this.CAISSE_COMMANDEE =quantite;
        if(unite.equals("TONNE")) this.CAISSE_COMMANDEE =(quantite/0.92/monArticle.getLITTRAGE_US()/getARTICLE_NBUS_PAR_UP())*1000;
        //this.CAISSE_LIVREE=this.CAISSE_COMMANDEE;
        this.CAISSE_LIVREE=0;

        Log.d("commandeligne", quantite+"_"+this.CAISSE_COMMANDEE+"_"+unite+"_"+this.CAISSE_LIVREE);

        if(unite.equals("BOUTEILLE")) this.LITTRAGE_COMMANDEE =quantite*monArticle.getLITTRAGE_US();
        if(unite.equals("CAISSE")) this.LITTRAGE_COMMANDEE =quantite*monArticle.getLITTRAGE_UP();
        if(unite.equals("TONNE")) this.LITTRAGE_COMMANDEE =(quantite/0.92/monArticle.getLITTRAGE_UP())*1000;
        //this.LITTRAGE_LIVREE = this.LITTRAGE_COMMANDEE;
        this.LITTRAGE_LIVREE=0;

        if(unite.equals("BOUTEILLE")) this.TONNAGE_COMMANDEE = quantite*monArticle.getPOIDKG_US()/1000;
        if(unite.equals("CAISSE")) this.TONNAGE_COMMANDEE = quantite*monArticle.getPOIDKG_UP()/1000;
        if(unite.equals("TONNE")) this.TONNAGE_COMMANDEE = quantite;
        //this.TONNAGE_LIVREE = TONNAGE_COMMANDEE;
        this.TONNAGE_LIVREE=0;

        if(unite.equals("BOUTEILLE"))this.KG_COMMANDEE = quantite*monArticle.getPOIDKG_US();
        if(unite.equals("CAISSE")) this.KG_COMMANDEE = quantite*monArticle.getPOIDKG_UP();
        if(unite.equals("TONNE")) this.KG_COMMANDEE = quantite*1000;
        //this.KG_LIVREE = KG_COMMANDEE;
        this.KG_LIVREE=0;

        this.MONTANT_BRUT =getNumberRounded(prix*this.CAISSE_LIVREE);
        this.REMISE = 0;
        this.MONTANT_NET = getNumberRounded(this.MONTANT_BRUT);
        this.COMMANDELIGNE_CODE = ViewCommandeActivity.ListeCommandeLigne.size()+1;
        this.COMMENTAIRE = "to_insert";

        this.CREATEUR_CODE = "CREATEUR";

        this.DATE_CREATION = df.format(Calendar.getInstance().getTime());
        this.VERSION="non verifiee";
    }

    /*public CommandeLigne(String commande_code,Article monArticle , String date_livraison ,float quantite , String unite,double prix,int size,Context context) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);

        this.COMMANDE_CODE=commande_code;
        this.FACTURE_CODE=commande_code;
        this.FAMILLE_CODE = monArticle.getFAMILLE_CODE();
        this.ARTICLE_CODE = monArticle.getARTICLE_CODE();
        this.ARTICLE_DESIGNATION =monArticle.getARTICLE_DESIGNATION2();
        this.ARTICLE_NBUS_PAR_UP = monArticle.getNBUS_PAR_UP();

        this.ARTICLE_PRIX =prix;
        this.UNITE_CODE=unite;
        this.QTE_COMMANDEE =quantite ;
        this.QTE_LIVREE =0 ;

        if(unite.equals("BOUTEILLE")) this.CAISSE_COMMANDEE =quantite/ARTICLE_NBUS_PAR_UP ;
        if(unite.equals("CAISSE")) this.CAISSE_COMMANDEE =quantite;
        if(unite.equals("TONNE")) this.CAISSE_COMMANDEE =(quantite/0.92/monArticle.getLITTRAGE_US()/getARTICLE_NBUS_PAR_UP())*1000;
        //this.CAISSE_LIVREE=this.CAISSE_COMMANDEE;
        this.CAISSE_LIVREE=0;

        Log.d("commandeligne", quantite+"_"+this.CAISSE_COMMANDEE+"_"+unite+"_"+this.CAISSE_LIVREE);

        if(unite.equals("BOUTEILLE")) this.LITTRAGE_COMMANDEE =quantite*monArticle.getLITTRAGE_US();
        if(unite.equals("CAISSE")) this.LITTRAGE_COMMANDEE =quantite*monArticle.getLITTRAGE_UP();
        if(unite.equals("TONNE")) this.LITTRAGE_COMMANDEE =(quantite/0.92/monArticle.getLITTRAGE_UP())*1000;
        //this.LITTRAGE_LIVREE = this.LITTRAGE_COMMANDEE;
        this.LITTRAGE_LIVREE=0;

        if(unite.equals("BOUTEILLE")) this.TONNAGE_COMMANDEE = quantite*monArticle.getPOIDKG_US()/1000;
        if(unite.equals("CAISSE")) this.TONNAGE_COMMANDEE = quantite*monArticle.getPOIDKG_UP()/1000;
        if(unite.equals("TONNE")) this.TONNAGE_COMMANDEE = quantite;
        //this.TONNAGE_LIVREE = TONNAGE_COMMANDEE;
        this.TONNAGE_LIVREE=0;

        if(unite.equals("BOUTEILLE"))this.KG_COMMANDEE = quantite*monArticle.getPOIDKG_US();
        if(unite.equals("CAISSE")) this.KG_COMMANDEE = quantite*monArticle.getPOIDKG_UP();
        if(unite.equals("TONNE")) this.KG_COMMANDEE = quantite*1000;
        //this.KG_LIVREE = KG_COMMANDEE;
        this.KG_LIVREE=0;
        this.MONTANT_BRUT =getNumberRounded(prix*quantite);
        this.REMISE = 0;
        this.MONTANT_NET = getNumberRounded(this.MONTANT_BRUT);
        this.COMMANDELIGNE_CODE = size;
        this.COMMENTAIRE = "to_insert";
        try {
            this.CREATEUR_CODE = user.getString("UTILISATEUR_CODE");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.DATE_CREATION = df.format(Calendar.getInstance().getTime());
        this.VERSION="non verifiee";
        this.SOURCE=commande_code;
    }*/

    public CommandeLigne(String commande_code,Article monArticle , String date_livraison ,float quantite , String unite,double prix,int size,Context context) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);
        UniteManager uniteManager = new UniteManager(context);
        Unite unite1 = uniteManager.get(unite);
        Unite unite2 = uniteManager.getMaxFc(monArticle.getARTICLE_CODE());


        this.COMMANDE_CODE=commande_code;
        this.FACTURE_CODE=commande_code;
        this.FAMILLE_CODE = monArticle.getFAMILLE_CODE();
        this.ARTICLE_CODE = monArticle.getARTICLE_CODE();
        this.ARTICLE_DESIGNATION =monArticle.getARTICLE_DESIGNATION2();
        this.ARTICLE_NBUS_PAR_UP = monArticle.getNBUS_PAR_UP();

        this.ARTICLE_PRIX =prix;
        this.UNITE_CODE=unite;
        this.QTE_COMMANDEE =quantite ;
        this.QTE_LIVREE =0 ;


        try{

            this.CAISSE_COMMANDEE=(quantite*unite1.getFACTEUR_CONVERSION())/unite2.getFACTEUR_CONVERSION();

        }catch(ArithmeticException e){

        }
        this.CAISSE_LIVREE=0;

        Log.d("commandeligne", quantite+"_"+this.CAISSE_COMMANDEE+"_"+unite+"_"+this.CAISSE_LIVREE);

        this.LITTRAGE_COMMANDEE = unite1.getLITTRAGE()*quantite;
        this.LITTRAGE_LIVREE=0;

        try{
            this.TONNAGE_COMMANDEE = (quantite*unite1.getPOIDKG())/1000;
        }catch (ArithmeticException e){

        }
        this.TONNAGE_LIVREE=0;

        this.KG_COMMANDEE = unite1.getPOIDKG()*quantite;
        this.KG_LIVREE=0;

        this.MONTANT_BRUT =getNumberRounded(prix*quantite);
        this.REMISE = 0;
        this.MONTANT_NET = getNumberRounded(this.MONTANT_BRUT-this.REMISE);

        /*this.MONTANT_BRUT =getNumberRounded(prix*quantite);
        this.REMISE = 0;
        this.MONTANT_NET = getNumberRounded(this.MONTANT_BRUT);*/

        this.COMMANDELIGNE_CODE = size;
        this.COMMENTAIRE = "to_insert";
        try {
            this.CREATEUR_CODE = user.getString("UTILISATEUR_CODE");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.DATE_CREATION = df.format(Calendar.getInstance().getTime());
        this.VERSION="non verifiee";
        this.SOURCE=commande_code;
    }

    public CommandeLigne(String commande_code,Article monArticle,float quantite , String unite,double prix,int size,Context context) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {}.getType();
        final JSONObject user = gson2.fromJson(json2, type);

        UniteManager uniteManager = new UniteManager(context);
        Unite unite1 = uniteManager.get(unite);
        Unite unite2 = uniteManager.getMaxFc(monArticle.getARTICLE_CODE());

        this.COMMANDE_CODE=commande_code;
        this.FACTURE_CODE=commande_code;
        this.FAMILLE_CODE = monArticle.getFAMILLE_CODE();
        this.ARTICLE_CODE = monArticle.getARTICLE_CODE();
        this.ARTICLE_DESIGNATION =monArticle.getARTICLE_DESIGNATION2();
        this.ARTICLE_NBUS_PAR_UP = monArticle.getNBUS_PAR_UP();

        this.ARTICLE_PRIX =prix;
        this.UNITE_CODE=unite;

        this.QTE_COMMANDEE =quantite ;
        this.QTE_LIVREE =this.QTE_COMMANDEE ;

        try{

            this.CAISSE_COMMANDEE=(quantite*unite1.getFACTEUR_CONVERSION())/unite2.getFACTEUR_CONVERSION();

        }catch(ArithmeticException e){

        }

        this.CAISSE_LIVREE=this.CAISSE_COMMANDEE;

        Log.d("commandeligneF", quantite+"_"+this.CAISSE_COMMANDEE+"_"+unite+"_"+this.CAISSE_LIVREE);

        //this.LITTRAGE_COMMANDEE = (monArticle.getLITTRAGE_UP()/(unite1.getFACTEUR_CONVERSION()))*quantite;
        //this.LITTRAGE_LIVREE=this.LITTRAGE_COMMANDEE;

        this.LITTRAGE_COMMANDEE = unite1.getLITTRAGE()*quantite;
        this.LITTRAGE_LIVREE=this.LITTRAGE_COMMANDEE;

        /*try{
            this.TONNAGE_COMMANDEE = ((monArticle.getPOIDKG_UP()/(unite1.getFACTEUR_CONVERSION()))*quantite)/1000;
        }catch (ArithmeticException e){

        }*/

        try{
            this.TONNAGE_COMMANDEE = (quantite*unite1.getPOIDKG())/1000;
        }catch (ArithmeticException e){

        }


        this.TONNAGE_LIVREE=this.TONNAGE_COMMANDEE;

        //this.KG_COMMANDEE = (monArticle.getPOIDKG_UP()/(unite1.getFACTEUR_CONVERSION()))*quantite;
        //this.KG_LIVREE=this.KG_COMMANDEE;

        this.KG_COMMANDEE = unite1.getPOIDKG()*quantite;
        this.KG_LIVREE=this.KG_COMMANDEE;

        this.MONTANT_BRUT =getNumberRounded(prix*quantite);
        this.REMISE = 0;
        this.MONTANT_NET = getNumberRounded(this.MONTANT_BRUT-this.REMISE);

        this.COMMANDELIGNE_CODE = size;
        this.COMMENTAIRE = "to_insert";
        try {
            this.CREATEUR_CODE = user.getString("UTILISATEUR_CODE");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.DATE_CREATION = df.format(Calendar.getInstance().getTime());
        this.VERSION="non verifiee";
        this.SOURCE=commande_code;
    }

    public CommandeLigne( CommandeLigne commandeLigne , Commande commande) {

        try {

            this.COMMANDE_CODE=commande.getCOMMANDE_CODE();
            this.FACTURE_CODE=commande.getCOMMANDE_CODE();
            this.FAMILLE_CODE=commandeLigne.getFAMILLE_CODE();
            this.ARTICLE_CODE=commandeLigne.getARTICLE_CODE();
            this.ARTICLE_DESIGNATION=commandeLigne.getARTICLE_DESIGNATION();
            this.ARTICLE_NBUS_PAR_UP=commandeLigne.getARTICLE_NBUS_PAR_UP();
            this.ARTICLE_PRIX=commandeLigne.getARTICLE_PRIX();
            this.QTE_COMMANDEE=-commandeLigne.getQTE_COMMANDEE();
            this.QTE_LIVREE=-commandeLigne.getQTE_LIVREE();
            this.CAISSE_COMMANDEE=-commandeLigne.getCAISSE_COMMANDEE();
            this.CAISSE_LIVREE=-commandeLigne.getCAISSE_LIVREE();
            this.LITTRAGE_COMMANDEE=-commandeLigne.getLITTRAGE_COMMANDEE();
            this.LITTRAGE_LIVREE=-commandeLigne.getLITTRAGE_LIVREE();
            this.TONNAGE_COMMANDEE=-commandeLigne.getTONNAGE_COMMANDEE();
            this.TONNAGE_LIVREE=-commandeLigne.getTONNAGE_LIVREE();
            this.KG_COMMANDEE=-commandeLigne.getKG_COMMANDEE();
            this.KG_LIVREE=-commandeLigne.getKG_LIVREE();
            this.MONTANT_BRUT=-commandeLigne.getMONTANT_BRUT();
            this.REMISE=commandeLigne.getREMISE();
            this.MONTANT_NET=-commandeLigne.getMONTANT_NET();
            this.COMMENTAIRE = "to_insert";
            this.CREATEUR_CODE=commandeLigne.getCREATEUR_CODE();
            this.DATE_CREATION=commande.getDATE_CREATION();
            this.UNITE_CODE=commandeLigne.getUNITE_CODE();
            this.VERSION=commandeLigne.getVERSION();
            this.SOURCE=commande.getCOMMANDE_CODE();
            this.COMMANDELIGNE_CODE=commandeLigne.getCOMMANDELIGNE_CODE();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CommandeLigne( CommandeLigne commandeLigne) {

        try {

            this.COMMANDE_CODE=commandeLigne.getCOMMANDE_CODE();
            this.FACTURE_CODE=commandeLigne.getFACTURE_CODE();
            this.FAMILLE_CODE=commandeLigne.getFAMILLE_CODE();
            this.ARTICLE_CODE=commandeLigne.getARTICLE_CODE();
            this.ARTICLE_DESIGNATION=commandeLigne.getARTICLE_DESIGNATION();
            this.ARTICLE_NBUS_PAR_UP=commandeLigne.getARTICLE_NBUS_PAR_UP();
            this.ARTICLE_PRIX=commandeLigne.getARTICLE_PRIX();
            this.QTE_COMMANDEE=-commandeLigne.getQTE_COMMANDEE();
            this.QTE_LIVREE=-commandeLigne.getQTE_LIVREE();
            this.CAISSE_COMMANDEE=-commandeLigne.getCAISSE_COMMANDEE();
            this.CAISSE_LIVREE=-commandeLigne.getCAISSE_LIVREE();
            this.LITTRAGE_COMMANDEE=-commandeLigne.getLITTRAGE_COMMANDEE();
            this.LITTRAGE_LIVREE=-commandeLigne.getLITTRAGE_LIVREE();
            this.TONNAGE_COMMANDEE=-commandeLigne.getTONNAGE_COMMANDEE();
            this.TONNAGE_LIVREE=-commandeLigne.getTONNAGE_LIVREE();
            this.KG_COMMANDEE=-commandeLigne.getKG_COMMANDEE();
            this.KG_LIVREE=-commandeLigne.getKG_LIVREE();
            this.MONTANT_BRUT=-commandeLigne.getMONTANT_BRUT();
            this.REMISE=-commandeLigne.getREMISE();
            this.MONTANT_NET=-commandeLigne.getMONTANT_NET();
            this.COMMENTAIRE=commandeLigne.getCOMMENTAIRE();
            this.CREATEUR_CODE=commandeLigne.getCREATEUR_CODE();
            this.DATE_CREATION=commandeLigne.getDATE_CREATION();
            this.UNITE_CODE=commandeLigne.getUNITE_CODE();
            this.VERSION=commandeLigne.getVERSION();
            this.SOURCE=commandeLigne.getSOURCE();
            this.COMMANDELIGNE_CODE=commandeLigne.getCOMMANDELIGNE_CODE();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCOMMANDE_CODE() {
        return COMMANDE_CODE;
    }

    public String getFACTURE_CODE() {
        return FACTURE_CODE;
    }

    public String getFAMILLE_CODE() {
        return FAMILLE_CODE;
    }

    public String getARTICLE_CODE() {
        return ARTICLE_CODE;
    }

    public String getARTICLE_DESIGNATION() {
        return ARTICLE_DESIGNATION;
    }

    public double getARTICLE_NBUS_PAR_UP() {
        return ARTICLE_NBUS_PAR_UP;
    }

    public double getARTICLE_PRIX() {
        return ARTICLE_PRIX;
    }

    public double getQTE_COMMANDEE() {
        return QTE_COMMANDEE;
    }

    public double getQTE_LIVREE() {
        return QTE_LIVREE;
    }

    public double getCAISSE_COMMANDEE() {
        return CAISSE_COMMANDEE;
    }

    public double getCAISSE_LIVREE() {
        return CAISSE_LIVREE;
    }

    public double getLITTRAGE_COMMANDEE() {
        return LITTRAGE_COMMANDEE;
    }

    public double getLITTRAGE_LIVREE() {
        return LITTRAGE_LIVREE;
    }

    public double getTONNAGE_COMMANDEE() {
        return TONNAGE_COMMANDEE;
    }

    public double getTONNAGE_LIVREE() {
        return TONNAGE_LIVREE;
    }

    public double getKG_COMMANDEE() {
        return KG_COMMANDEE;
    }

    public double getKG_LIVREE() {
        return KG_LIVREE;
    }

    public double getMONTANT_BRUT() {
        return MONTANT_BRUT;
    }

    public double getREMISE() {
        return REMISE;
    }

    public double getMONTANT_NET() {
        return MONTANT_NET;
    }

    public String getCOMMENTAIRE() {
        return COMMENTAIRE;
    }

    public String getCREATEUR_CODE() {
        return CREATEUR_CODE;
    }

    public String getDATE_CREATION() {
        return DATE_CREATION;
    }

    public int getCOMMANDELIGNE_CODE() {
        return COMMANDELIGNE_CODE;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setCOMMANDE_CODE(String COMMANDE_CODE) {
        this.COMMANDE_CODE = COMMANDE_CODE;
    }

    public void setFACTURE_CODE(String FACTURE_CODE) {
        this.FACTURE_CODE = FACTURE_CODE;
    }

    public void setFAMILLE_CODE(String FAMILLE_CODE) {
        this.FAMILLE_CODE = FAMILLE_CODE;
    }

    public void setARTICLE_CODE(String ARTICLE_CODE) {
        this.ARTICLE_CODE = ARTICLE_CODE;
    }

    public void setARTICLE_DESIGNATION(String ARTICLE_DESIGNATION) {
        this.ARTICLE_DESIGNATION = ARTICLE_DESIGNATION;
    }

    public void setARTICLE_NBUS_PAR_UP(double ARTICLE_NBUS_PAR_UP) {
        this.ARTICLE_NBUS_PAR_UP = ARTICLE_NBUS_PAR_UP;
    }

    public void setARTICLE_PRIX(double ARTICLE_PRIX) {
        this.ARTICLE_PRIX = ARTICLE_PRIX;
    }

    public void setQTE_COMMANDEE(double QTE_COMMANDEE) {
        this.QTE_COMMANDEE = QTE_COMMANDEE;
    }

    public void setQTE_LIVREE(double QTE_LIVREE) {
        this.QTE_LIVREE = QTE_LIVREE;
    }

    public void setCAISSE_COMMANDEE(double CAISSE_COMMANDEE) {
        this.CAISSE_COMMANDEE = CAISSE_COMMANDEE;
    }

    public void setCAISSE_LIVREE(double CAISSE_LIVREE) {
        this.CAISSE_LIVREE = CAISSE_LIVREE;
    }

    public void setLITTRAGE_COMMANDEE(double LITTRAGE_COMMANDEE) {
        this.LITTRAGE_COMMANDEE = LITTRAGE_COMMANDEE;
    }

    public void setLITTRAGE_LIVREE(double LITTRAGE_LIVREE) {
        this.LITTRAGE_LIVREE = LITTRAGE_LIVREE;
    }

    public void setTONNAGE_COMMANDEE(double TONNAGE_COMMANDEE) {
        this.TONNAGE_COMMANDEE = TONNAGE_COMMANDEE;
    }

    public String getUNITE_CODE() {
        return UNITE_CODE;
    }

    public void setUNITE_CODE(String UNITE) {
        this.UNITE_CODE = UNITE;
    }

    public void setTONNAGE_LIVREE(double TONNAGE_LIVREE) {
        this.TONNAGE_LIVREE = TONNAGE_LIVREE;
    }

    public void setKG_COMMANDEE(double KG_COMMANDEE) {
        this.KG_COMMANDEE = KG_COMMANDEE;
    }

    public void setKG_LIVREE(double KG_LIVREE) {
        this.KG_LIVREE = KG_LIVREE;
    }

    public void setMONTANT_BRUT(double MONTANT_BRUT) {
        this.MONTANT_BRUT = MONTANT_BRUT;
    }

    public void setREMISE(double REMISE) {
        this.REMISE = REMISE;
    }

    public void setMONTANT_NET(double MONTANT_NET) {
        this.MONTANT_NET = MONTANT_NET;
    }

    public void setCOMMENTAIRE(String COMMENTAIRE) {
        this.COMMENTAIRE = COMMENTAIRE;
    }

    public void setCREATEUR_CODE(String CREATEUR_CODE) {
        this.CREATEUR_CODE = CREATEUR_CODE;
    }

    public void setDATE_CREATION(String DATE_CREATION) {
        this.DATE_CREATION = DATE_CREATION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public void setCOMMANDELIGNE_CODE(int COMMANDELIGNE_CODE) {
        this.COMMANDELIGNE_CODE = COMMANDELIGNE_CODE;
    }

    public String getSOURCE() {
        return SOURCE;
    }

    public void setSOURCE(String SOURCE) {
        this.SOURCE = SOURCE;
    }


    @Override
    public String toString() {
        return "CommandeLigne{" +
                "COMMANDELIGNE_CODE=" + COMMANDELIGNE_CODE +
                ", COMMANDE_CODE='" + COMMANDE_CODE + '\'' +
                ", FACTURE_CODE='" + FACTURE_CODE + '\'' +
                ", FAMILLE_CODE='" + FAMILLE_CODE + '\'' +
                ", ARTICLE_CODE='" + ARTICLE_CODE + '\'' +
                ", UNITE_CODE='" + UNITE_CODE + '\'' +
                ", ARTICLE_DESIGNATION='" + ARTICLE_DESIGNATION + '\'' +
                ", ARTICLE_NBUS_PAR_UP=" + ARTICLE_NBUS_PAR_UP +
                ", ARTICLE_PRIX=" + ARTICLE_PRIX +
                ", QTE_COMMANDEE=" + QTE_COMMANDEE +
                ", QTE_LIVREE=" + QTE_LIVREE +
                ", CAISSE_COMMANDEE=" + CAISSE_COMMANDEE +
                ", CAISSE_LIVREE=" + CAISSE_LIVREE +
                ", LITTRAGE_COMMANDEE=" + LITTRAGE_COMMANDEE +
                ", LITTRAGE_LIVREE=" + LITTRAGE_LIVREE +
                ", TONNAGE_COMMANDEE=" + TONNAGE_COMMANDEE +
                ", TONNAGE_LIVREE=" + TONNAGE_LIVREE +
                ", KG_COMMANDEE=" + KG_COMMANDEE +
                ", KG_LIVREE=" + KG_LIVREE +
                ", MONTANT_BRUT=" + MONTANT_BRUT +
                ", REMISE=" + REMISE +
                ", MONTANT_NET=" + MONTANT_NET +
                ", COMMENTAIRE='" + COMMENTAIRE + '\'' +
                ", CREATEUR_CODE='" + CREATEUR_CODE + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", SOURCE='" + SOURCE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object v) {

        boolean retVal = false;
        if (v instanceof CommandeLigne){
            CommandeLigne commandeLigne = (CommandeLigne) v;
            retVal = commandeLigne.getARTICLE_CODE() == this.getARTICLE_CODE();
        }
        return retVal;
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
