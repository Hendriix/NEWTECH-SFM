package com.newtech.newtech_sfm.Metier;

import android.content.Context;
import android.util.Log;

import com.newtech.newtech_sfm.Metier_Manager.UniteManager;
import com.newtech.newtech_sfm.Metier_Manager.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by TONPC on 25/04/2017.
 */

public class LivraisonLigne {

    int
    LIVRAISONLIGNE_CODE;
    String
    LIVRAISON_CODE;
    double
    COMMANDELIGNE_CODE;
    String
    COMMANDE_CODE,
    FACTURE_CODE,
    FAMILLE_CODE,
    ARTICLE_CODE,
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
    TS,
    UNITE_CODE,
    SOURCE,
    VERSION;

    public LivraisonLigne(){

    }

    public LivraisonLigne(CommandeLigne commandeLigne,String livraison_code){

        this.LIVRAISONLIGNE_CODE=commandeLigne.getCOMMANDELIGNE_CODE();
        this.LIVRAISON_CODE=livraison_code;
        this.COMMANDELIGNE_CODE=commandeLigne.getCOMMANDELIGNE_CODE();
        this.COMMANDE_CODE=commandeLigne.getCOMMANDE_CODE();
        this.FACTURE_CODE=commandeLigne.getFACTURE_CODE();
        this.FAMILLE_CODE=commandeLigne.getFAMILLE_CODE();
        this.ARTICLE_CODE=commandeLigne.getARTICLE_CODE();
        this.ARTICLE_DESIGNATION=commandeLigne.getARTICLE_DESIGNATION();
        this.ARTICLE_NBUS_PAR_UP=commandeLigne.getARTICLE_NBUS_PAR_UP();
        this.ARTICLE_PRIX=commandeLigne.getARTICLE_PRIX();
        this.QTE_COMMANDEE=commandeLigne.getQTE_COMMANDEE();
        this.QTE_LIVREE=commandeLigne.getQTE_LIVREE();
        this.CAISSE_COMMANDEE=commandeLigne.getCAISSE_COMMANDEE();
        this.CAISSE_LIVREE=commandeLigne.getCAISSE_LIVREE();
        this.LITTRAGE_COMMANDEE=commandeLigne.getLITTRAGE_COMMANDEE();
        this.LITTRAGE_LIVREE=commandeLigne.getLITTRAGE_LIVREE();
        this.TONNAGE_COMMANDEE=commandeLigne.getTONNAGE_COMMANDEE();
        this.TONNAGE_LIVREE=commandeLigne.getTONNAGE_LIVREE();
        this.KG_COMMANDEE=commandeLigne.getKG_COMMANDEE();
        this.KG_LIVREE=commandeLigne.getKG_LIVREE();
        this.MONTANT_BRUT=getNumberRounded(commandeLigne.getMONTANT_BRUT());
        this.REMISE=getNumberRounded(commandeLigne.getREMISE());
        this.MONTANT_NET=getNumberRounded(commandeLigne.getMONTANT_NET());
        this.COMMENTAIRE=commandeLigne.getCOMMENTAIRE();
        this.CREATEUR_CODE=commandeLigne.getCREATEUR_CODE();
        this.DATE_CREATION=commandeLigne.getDATE_CREATION();
        //this.TS=commandeLigne.;
        this.UNITE_CODE=commandeLigne.getUNITE_CODE();
        this.SOURCE=commandeLigne.getSOURCE();
        this.VERSION="non_verifiee";
    }

    public LivraisonLigne( JSONObject l) {
        try {

            this.LIVRAISONLIGNE_CODE=l.getInt("LIVRAISONLIGNE_CODE");
            this.LIVRAISON_CODE=l.getString("LIVRAISON_CODE");
            this.COMMANDELIGNE_CODE=l.getInt("COMMANDELIGNE_CODE");
            this.COMMANDE_CODE=l.getString("COMMANDE_CODE");
            this.FACTURE_CODE=l.getString("FACTURE_CODE");
            this.FAMILLE_CODE=l.getString("FAMILLE_CODE");
            this.ARTICLE_CODE=l.getString("ARTICLE_CODE");
            this.ARTICLE_DESIGNATION=l.getString("ARTICLE_DESIGNATION");
            this.ARTICLE_NBUS_PAR_UP=l.getDouble("ARTICLE_NBUS_PAR_UP");
            this.ARTICLE_PRIX=l.getDouble("ARTICLE_PRIX");
            this.QTE_COMMANDEE=l.getDouble("QTE_COMMANDEE");
            this.QTE_LIVREE=l.getDouble("QTE_LIVREE");
            this.CAISSE_COMMANDEE=l.getDouble("CAISSE_COMMANDEE");
            this.CAISSE_LIVREE=l.getDouble("CAISSE_LIVREE");
            this.LITTRAGE_COMMANDEE=l.getDouble("LITTRAGE_COMMANDEE");
            this.LITTRAGE_LIVREE=l.getDouble("LITTRAGE_LIVREE");
            this.TONNAGE_COMMANDEE=l.getDouble("TONNAGE_COMMANDEE");
            this.TONNAGE_LIVREE=l.getDouble("TONNAGE_LIVREE");
            this.KG_COMMANDEE=l.getDouble("KG_COMMANDEE");
            this.KG_LIVREE=l.getDouble("KG_LIVREE");
            this.MONTANT_BRUT=getNumberRounded(l.getDouble("MONTANT_BRUT"));
            this.REMISE=getNumberRounded(l.getDouble("REMISE"));
            this.MONTANT_NET=getNumberRounded(l.getDouble("MONTANT_NET"));
            this.COMMENTAIRE=l.getString("COMMENTAIRE");
            this.CREATEUR_CODE=l.getString("CREATEUR_CODE");
            //Log.d("LivraisonLigne", "LivraisonLigne: "+l.getString("DATE_CREATION"));
            this.DATE_CREATION=l.getString("DATE_CREATION");
            this.TS=l.getString("TS");
            this.UNITE_CODE=l.getString("UNITE_CODE");
            this.SOURCE=l.getString("SOURCE");
            this.VERSION=l.getString("VERSION");



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public LivraisonLigne (String livraison_code,CommandeLigne commandeLigne, Context context){

        UserManager userManager = new UserManager(context);
        User user = userManager.get();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try{

            //this.LIVRAISONLIGNE_CODE=commandeLigne.getCOMMANDELIGNE_CODE();

            this.LIVRAISON_CODE=livraison_code;
            this.COMMANDELIGNE_CODE=commandeLigne.getCOMMANDELIGNE_CODE();
            this.COMMANDE_CODE=commandeLigne.getCOMMANDE_CODE();
            this.FACTURE_CODE=commandeLigne.getFACTURE_CODE();
            this.FAMILLE_CODE=commandeLigne.getFAMILLE_CODE();
            this.ARTICLE_CODE=commandeLigne.getARTICLE_CODE();
            this.ARTICLE_DESIGNATION=commandeLigne.getARTICLE_DESIGNATION();
            this.ARTICLE_NBUS_PAR_UP=commandeLigne.getARTICLE_NBUS_PAR_UP();
            this.ARTICLE_PRIX=commandeLigne.getARTICLE_PRIX();

            this.QTE_COMMANDEE=0;
            this.QTE_LIVREE=0;
            this.CAISSE_COMMANDEE=0;
            this.CAISSE_LIVREE=0;
            this.LITTRAGE_COMMANDEE=0;
            this.LITTRAGE_LIVREE=0;
            this.TONNAGE_COMMANDEE=0;
            this.TONNAGE_LIVREE=0;
            this.KG_COMMANDEE=0;
            this.KG_LIVREE=0;

            this.MONTANT_BRUT=0;
            this.REMISE=0;
            this.MONTANT_NET=0;

            this.COMMENTAIRE="to_insert";
            this.CREATEUR_CODE=commandeLigne.getCREATEUR_CODE();
            this.DATE_CREATION=df.format(Calendar.getInstance().getTime());
            this.UNITE_CODE=commandeLigne.getUNITE_CODE();
            this.SOURCE=commandeLigne.getSOURCE();
            this.VERSION="non_verifiee";


        }catch (Exception e){

        }
    }

    public LivraisonLigne (CommandeLigne commandeLigne,String livraison_code, Context context){

        UserManager userManager = new UserManager(context);
        User user = userManager.get();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try{

            /*this.LIVRAISONLIGNE_CODE=commandeLigne.getCOMMANDELIGNE_CODE();
            this.LIVRAISON_CODE=livraison_code;
            this.COMMANDELIGNE_CODE=commandeLigne.getCOMMANDELIGNE_CODE();
            this.COMMANDE_CODE=commandeLigne.getCOMMANDE_CODE();
            this.FACTURE_CODE=commandeLigne.getFACTURE_CODE();
            this.FAMILLE_CODE=commandeLigne.getFAMILLE_CODE();
            this.ARTICLE_CODE=commandeLigne.getARTICLE_CODE();
            this.ARTICLE_DESIGNATION=commandeLigne.getARTICLE_DESIGNATION();
            this.ARTICLE_NBUS_PAR_UP=commandeLigne.getARTICLE_NBUS_PAR_UP();
            this.ARTICLE_PRIX=commandeLigne.getARTICLE_PRIX();
            this.QTE_COMMANDEE=commandeLigne.getQTE_COMMANDEE();
            this.QTE_LIVREE=commandeLigne.getQTE_LIVREE();
            this.CAISSE_COMMANDEE=commandeLigne.getCAISSE_COMMANDEE();
            this.CAISSE_LIVREE=commandeLigne.getCAISSE_LIVREE();
            this.LITTRAGE_COMMANDEE=commandeLigne.getLITTRAGE_COMMANDEE();
            this.LITTRAGE_LIVREE=commandeLigne.getLITTRAGE_LIVREE();
            this.TONNAGE_COMMANDEE=commandeLigne.getTONNAGE_COMMANDEE();
            this.TONNAGE_LIVREE=commandeLigne.getTONNAGE_LIVREE();
            this.KG_COMMANDEE=commandeLigne.getKG_COMMANDEE();
            this.KG_LIVREE=commandeLigne.getKG_LIVREE();
            this.MONTANT_BRUT=getNumberRounded(commandeLigne.getMONTANT_BRUT());
            this.REMISE=getNumberRounded(commandeLigne.getREMISE());
            this.MONTANT_NET=getNumberRounded(commandeLigne.getMONTANT_NET());
            this.COMMENTAIRE="to_insert";
            this.CREATEUR_CODE=user.getUTILISATEUR_CODE();
            this.DATE_CREATION=df.format(Calendar.getInstance().getTime());
            this.UNITE_CODE=commandeLigne.getUNITE_CODE();
            this.SOURCE=commandeLigne.getSOURCE();
            this.VERSION="non_verifiee";*/

            this.LIVRAISONLIGNE_CODE=commandeLigne.getCOMMANDELIGNE_CODE();
            this.LIVRAISON_CODE=livraison_code;
            this.COMMANDELIGNE_CODE=commandeLigne.getCOMMANDELIGNE_CODE();
            this.COMMANDE_CODE=commandeLigne.getCOMMANDE_CODE();
            this.FACTURE_CODE=commandeLigne.getFACTURE_CODE();
            this.FAMILLE_CODE=commandeLigne.getFAMILLE_CODE();
            this.ARTICLE_CODE=commandeLigne.getARTICLE_CODE();
            this.ARTICLE_DESIGNATION=commandeLigne.getARTICLE_DESIGNATION();
            this.ARTICLE_NBUS_PAR_UP=commandeLigne.getARTICLE_NBUS_PAR_UP();
            this.ARTICLE_PRIX=commandeLigne.getARTICLE_PRIX();
            this.QTE_COMMANDEE=commandeLigne.getQTE_COMMANDEE();
            this.QTE_LIVREE=commandeLigne.getQTE_COMMANDEE();
            this.CAISSE_COMMANDEE=commandeLigne.getCAISSE_COMMANDEE();
            this.CAISSE_LIVREE=commandeLigne.getCAISSE_COMMANDEE();
            this.LITTRAGE_COMMANDEE=commandeLigne.getLITTRAGE_COMMANDEE();
            this.LITTRAGE_LIVREE=commandeLigne.getLITTRAGE_COMMANDEE();
            this.TONNAGE_COMMANDEE=commandeLigne.getTONNAGE_COMMANDEE();
            this.TONNAGE_LIVREE=commandeLigne.getTONNAGE_COMMANDEE();
            this.KG_COMMANDEE=commandeLigne.getKG_COMMANDEE();
            this.KG_LIVREE=commandeLigne.getKG_COMMANDEE();
            this.MONTANT_BRUT=getNumberRounded(commandeLigne.getMONTANT_BRUT());
            this.REMISE=getNumberRounded(commandeLigne.getREMISE());
            this.MONTANT_NET=getNumberRounded(commandeLigne.getMONTANT_NET());
            this.COMMENTAIRE="to_insert";
            this.CREATEUR_CODE=user.getUTILISATEUR_CODE();
            this.DATE_CREATION=df.format(Calendar.getInstance().getTime());
            this.UNITE_CODE=commandeLigne.getUNITE_CODE();
            this.SOURCE=commandeLigne.getSOURCE();
            this.VERSION="non_verifiee";

        }catch (Exception e){

        }
    }

    /*public LivraisonLigne(Livraison livraison,Article monArticle , String date_livraison ,float quantite , String unite,double prix,int size,Context context) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        UserManager userManager = new UserManager(context);
        User user = userManager.get();
        this.LIVRAISON_CODE=livraison.getLIVRAISON_CODE();
        this.COMMANDE_CODE=livraison.getCOMMANDE_CODE();
        this.FACTURE_CODE=livraison.getCOMMANDE_CODE();
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

        Log.d("livraisonligne", quantite+"_"+this.CAISSE_COMMANDEE+"_"+unite+"_"+this.CAISSE_LIVREE);

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
        this.CREATEUR_CODE = user.getUTILISATEUR_CODE();
        this.DATE_CREATION = df.format(Calendar.getInstance().getTime());
        this.VERSION="non verifiee";
        this.SOURCE=livraison.getSOURCE();
    }*/

    public LivraisonLigne(Livraison livraison,Article monArticle , String date_livraison ,float quantite , String unite,double prix,int size,Context context) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        UniteManager uniteManager = new UniteManager(context);
        Unite unite1 = uniteManager.get(unite);
        Unite unite2 = uniteManager.getMaxFc(monArticle.getARTICLE_CODE());

        UserManager userManager = new UserManager(context);
        User user = userManager.get();
        this.LIVRAISON_CODE=livraison.getLIVRAISON_CODE();
        this.COMMANDE_CODE=livraison.getCOMMANDE_CODE();
        this.FACTURE_CODE=livraison.getCOMMANDE_CODE();
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

        Log.d("commandeligne", quantite+"_"+this.CAISSE_COMMANDEE+"_"+unite+"_"+this.CAISSE_LIVREE);

        this.LITTRAGE_COMMANDEE = unite1.getLITTRAGE()*quantite;
        this.LITTRAGE_LIVREE=this.LITTRAGE_COMMANDEE;

        try{
            this.TONNAGE_COMMANDEE = (quantite*unite1.getPOIDKG())/1000;
        }catch (ArithmeticException e){

        }
        this.TONNAGE_LIVREE=this.TONNAGE_COMMANDEE;

        this.KG_COMMANDEE = unite1.getPOIDKG()*quantite;
        this.KG_LIVREE=this.KG_COMMANDEE;

        this.MONTANT_BRUT =getNumberRounded(prix*quantite);
        this.REMISE = 0;
        this.MONTANT_NET = getNumberRounded(this.MONTANT_BRUT-this.REMISE);



        this.COMMANDELIGNE_CODE = size;
        this.COMMENTAIRE = "to_insert";
        this.CREATEUR_CODE = user.getUTILISATEUR_CODE();
        this.DATE_CREATION = df.format(Calendar.getInstance().getTime());
        this.VERSION="non verifiee";
        this.SOURCE=livraison.getSOURCE();
    }


    public LivraisonLigne( LivraisonLigne livraisonLigne , Livraison livraison) {

        try {

            this.LIVRAISON_CODE=livraison.getLIVRAISON_CODE();
            this.LIVRAISONLIGNE_CODE=livraisonLigne.getLIVRAISONLIGNE_CODE();
            this.COMMANDE_CODE=livraison.getCOMMANDE_CODE();
            this.FACTURE_CODE=livraison.getCOMMANDE_CODE();
            this.FAMILLE_CODE=livraisonLigne.getFAMILLE_CODE();
            this.ARTICLE_CODE=livraisonLigne.getARTICLE_CODE();
            this.ARTICLE_DESIGNATION=livraisonLigne.getARTICLE_DESIGNATION();
            this.ARTICLE_NBUS_PAR_UP=livraisonLigne.getARTICLE_NBUS_PAR_UP();
            this.ARTICLE_PRIX=livraisonLigne.getARTICLE_PRIX();
            this.QTE_COMMANDEE=-livraisonLigne.getQTE_COMMANDEE();
            this.QTE_LIVREE=-livraisonLigne.getQTE_LIVREE();
            this.CAISSE_COMMANDEE=-livraisonLigne.getCAISSE_COMMANDEE();
            this.CAISSE_LIVREE=-livraisonLigne.getCAISSE_LIVREE();
            this.LITTRAGE_COMMANDEE=-livraisonLigne.getLITTRAGE_COMMANDEE();
            this.LITTRAGE_LIVREE=-livraisonLigne.getLITTRAGE_LIVREE();
            this.TONNAGE_COMMANDEE=-livraisonLigne.getTONNAGE_COMMANDEE();
            this.TONNAGE_LIVREE=-livraisonLigne.getTONNAGE_LIVREE();
            this.KG_COMMANDEE=-livraisonLigne.getKG_COMMANDEE();
            this.KG_LIVREE=-livraisonLigne.getKG_LIVREE();
            this.MONTANT_BRUT=-livraisonLigne.getMONTANT_BRUT();
            this.REMISE=livraisonLigne.getREMISE();
            this.MONTANT_NET=-livraisonLigne.getMONTANT_NET();
            this.COMMENTAIRE = "to_insert";
            this.CREATEUR_CODE=livraisonLigne.getCREATEUR_CODE();
            this.DATE_CREATION=livraison.getDATE_CREATION();
            this.UNITE_CODE=livraisonLigne.getUNITE_CODE();
            this.VERSION=livraisonLigne.getVERSION();
            this.SOURCE=livraison.getLIVRAISON_CODE();
            this.COMMANDELIGNE_CODE=livraisonLigne.getCOMMANDELIGNE_CODE();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public int getLIVRAISONLIGNE_CODE() {
        return LIVRAISONLIGNE_CODE;
    }

    public String getLIVRAISON_CODE() {
        return LIVRAISON_CODE;
    }

    public double getCOMMANDELIGNE_CODE() {
        return COMMANDELIGNE_CODE;
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

    public String getTS() {
        return TS;
    }

    public String getUNITE_CODE() {
        return UNITE_CODE;
    }

    public void setLIVRAISONLIGNE_CODE(int LIVRAISONLIGNE_CODE) {
        this.LIVRAISONLIGNE_CODE = LIVRAISONLIGNE_CODE;
    }

    public void setLIVRAISON_CODE(String LIVRAISON_CODE) {
        this.LIVRAISON_CODE = LIVRAISON_CODE;
    }

    public void setCOMMANDELIGNE_CODE(double COMMANDELIGNE_CODE) {
        this.COMMANDELIGNE_CODE = COMMANDELIGNE_CODE;
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

    public void setLITTRAGE_COMMANDEE(double LITTRAGE_COMMANDEE) {
        this.LITTRAGE_COMMANDEE = LITTRAGE_COMMANDEE;
    }

    public void setLITTRAGE_LIVREE(double LITTRAGE_LIVREE) {
        this.LITTRAGE_LIVREE = LITTRAGE_LIVREE;
    }

    public void setTONNAGE_COMMANDEE(double TONNAGE_COMMANDEE) {
        this.TONNAGE_COMMANDEE = TONNAGE_COMMANDEE;
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

    public void setTS(String TS) {
        this.TS = TS;
    }

    public void setUNITE_CODE(String UNITE_CODE) {
        this.UNITE_CODE = UNITE_CODE;
    }

    public double getCAISSE_COMMANDEE() {
        return CAISSE_COMMANDEE;
    }

    public void setCAISSE_COMMANDEE(double CAISSE_COMMANDEE) {
        this.CAISSE_COMMANDEE = CAISSE_COMMANDEE;
    }

    public double getCAISSE_LIVREE() {
        return CAISSE_LIVREE;
    }

    public void setCAISSE_LIVREE(double CAISSE_LIVREE) {
        this.CAISSE_LIVREE = CAISSE_LIVREE;
    }

    public String getSOURCE() {
        return SOURCE;
    }

    public void setSOURCE(String SOURCE) {
        this.SOURCE = SOURCE;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    @Override
    public String toString() {
        return "LivraisonLigne{" +
                "LIVRAISONLIGNE_CODE=" + LIVRAISONLIGNE_CODE +
                ", LIVRAISON_CODE='" + LIVRAISON_CODE + '\'' +
                ", COMMANDELIGNE_CODE=" + COMMANDELIGNE_CODE +
                ", COMMANDE_CODE='" + COMMANDE_CODE + '\'' +
                ", FACTURE_CODE='" + FACTURE_CODE + '\'' +
                ", FAMILLE_CODE='" + FAMILLE_CODE + '\'' +
                ", ARTICLE_CODE='" + ARTICLE_CODE + '\'' +
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
                ", TS='" + TS + '\'' +
                ", UNITE_CODE='" + UNITE_CODE + '\'' +
                ", SOURCE='" + SOURCE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
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
