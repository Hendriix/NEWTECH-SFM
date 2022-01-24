package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by stagiaireit2 on 30/06/2016.
 */
public class Article {

    private String ARTICLE_CODE;
    private String ARTICLE_DESIGNATION1;
    private String ARTICLE_DESIGNATION2;
    private String ARTICLE_DESIGNATION3;
    private double ARTICLE_SKU;
    private double LITTRAGE_UP;
    private double POIDKG_UP;
    private double LITTRAGE_US;
    private double POIDKG_US;
    private double NBUS_PAR_UP;
    private String CATEGORIE_CODE;
    private String TYPE_CODE;
    private String FAMILLE_CODE;
    private String DATE_CREATION;
    private String CREATEUR_CODE;
    private String TS;
    private double INACTIF;
    private String INACTIF_RAISON;
    private int RANG;
    private String VERSION;
    private String IMAGE;
    private double ARTICLE_PRIX;
    public  int nbr_vente=0;
    public int nbr_caisse=0;
    public int nbre_bouteille=0;



    public Article(JSONObject article) throws JSONException {
        try {
        this.ARTICLE_CODE = article.getString("ARTICLE_CODE");
        this.ARTICLE_DESIGNATION1 = article.getString("ARTICLE_DESIGNATION1");
        this.ARTICLE_DESIGNATION2 = article.getString("ARTICLE_DESIGNATION2");
        this.ARTICLE_DESIGNATION3 = article.getString("ARTICLE_DESIGNATION3");
        this.ARTICLE_SKU = article.getDouble("ARTICLE_SKU");
        this.LITTRAGE_UP = article.getDouble("LITTRAGE_UP");
        this.POIDKG_UP = article.getDouble("POIDKG_UP");
        this.LITTRAGE_US = article.getDouble("LITTRAGE_US");
        this.POIDKG_US = article.getDouble("POIDKG_US");
        this.NBUS_PAR_UP = article.getDouble("NBUS_PAR_UP");
        this.CATEGORIE_CODE = article.getString("CATEGORIE_CODE");
        this.TYPE_CODE = article.getString("TYPE_CODE");
        this.FAMILLE_CODE = article.getString("FAMILLE_CODE");
        this.DATE_CREATION = article.getString("DATE_CREATION");
        this.CREATEUR_CODE = article.getString("CREATEUR_CODE");
        this.INACTIF = article.getDouble("INACTIF");
        this.INACTIF_RAISON = article.getString("INACTIF_RAISON");
        this.RANG = article.getInt("RANG");
        this.VERSION =article.getString("VERSION");
        this.IMAGE =article.getString("IMAGE");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.ARTICLE_PRIX = article.getDouble("ARTICLE_PRIX");
    }

    public Article(Article article) {

            this.ARTICLE_CODE = article.getARTICLE_CODE();
            this.ARTICLE_DESIGNATION1 = article.getARTICLE_DESIGNATION1();
            this.ARTICLE_DESIGNATION2 = article.getARTICLE_DESIGNATION2();
            this.ARTICLE_DESIGNATION3 = article.getARTICLE_DESIGNATION3();
            this.ARTICLE_SKU = article.getARTICLE_SKU();
            this.LITTRAGE_UP = article.getLITTRAGE_UP();
            this.POIDKG_UP = article.getPOIDKG_UP();
            this.LITTRAGE_US = article.getLITTRAGE_US();
            this.POIDKG_US = article.getPOIDKG_US();
            this.NBUS_PAR_UP = article.getNBUS_PAR_UP();
            this.CATEGORIE_CODE = article.getCATEGORIE_CODE();
            this.TYPE_CODE = article.getTYPE_CODE();
            this.FAMILLE_CODE = article.getFAMILLE_CODE();
            this.DATE_CREATION = article.getDATE_CREATION();
            this.CREATEUR_CODE = article.getCREATEUR_CODE();
            this.INACTIF = article.getINACTIF();
            this.INACTIF_RAISON = article.getINACTIF_RAISON();
            this.RANG = article.getRANG();
            this.TS=article.getTS();
            this.VERSION=article.getVERSION();
            this.IMAGE=article.getIMAGE();
            this.ARTICLE_PRIX=article.getARTICLE_PRIX();
      }

    public Article() {
    }

    public String getARTICLE_CODE() {
        return ARTICLE_CODE;
    }
    public String getARTICLE_DESIGNATION1() {
        return ARTICLE_DESIGNATION1;
    }
    public String getARTICLE_DESIGNATION2() {
        return ARTICLE_DESIGNATION2;
    }
    public String getARTICLE_DESIGNATION3() {
        return ARTICLE_DESIGNATION3;
    }
    public double getARTICLE_SKU() {
        return ARTICLE_SKU;
    }
    public double getLITTRAGE_UP() {
        return LITTRAGE_UP;
    }
    public double getPOIDKG_UP() {
        return POIDKG_UP;
    }
    public double getLITTRAGE_US() {
        return LITTRAGE_US;
    }
    public double getPOIDKG_US() {
        return POIDKG_US;
    }
    public double getNBUS_PAR_UP() {
        return NBUS_PAR_UP;
    }
    public String getCATEGORIE_CODE() {
        return CATEGORIE_CODE;
    }
    public String getTYPE_CODE() {
        return TYPE_CODE;
    }
    public String getFAMILLE_CODE() {
        return FAMILLE_CODE;
    }
    public String getDATE_CREATION() {
        return DATE_CREATION;
    }
    public String getCREATEUR_CODE() {
        return CREATEUR_CODE;
    }
    public double getINACTIF() {
        return INACTIF;
    }
    public String getINACTIF_RAISON() {
        return INACTIF_RAISON;
    }
    public int getRANG() {
        return RANG;
    }
    public String getTS() {return TS; }
    public String getVERSION() {
        return VERSION;
    }



    public void setARTICLE_CODE(String ARTICLE_CODE) {
        this.ARTICLE_CODE = ARTICLE_CODE;
    }
    public void setARTICLE_DESIGNATION1(String ARTICLE_DESIGNATION1) {
        this.ARTICLE_DESIGNATION1 = ARTICLE_DESIGNATION1;
    }
    public void setARTICLE_DESIGNATION2(String ARTICLE_DESIGNATION2) {
        this.ARTICLE_DESIGNATION2 = ARTICLE_DESIGNATION2;
    }public void setARTICLE_DESIGNATION3(String ARTICLE_DESIGNATION3) {
        this.ARTICLE_DESIGNATION3 = ARTICLE_DESIGNATION3;
    }
    public void setARTICLE_SKU(double ARTICLE_SKU) {
        this.ARTICLE_SKU = ARTICLE_SKU;
    }
    public void setLITTRAGE_UP(double LITTRAGE_UP) {
        this.LITTRAGE_UP = LITTRAGE_UP;
    }
    public void setPOIDKG_UP(double  POIDKG_UP) {
        this.POIDKG_UP = POIDKG_UP;
    }
    public void setLITTRAGE_US(double  LITTRAGE_US) {
        this.LITTRAGE_US = LITTRAGE_US;
    }
    public void setPOIDKG_US(double  POIDKG_US) {
        this.POIDKG_US = POIDKG_US;
    }
    public void setNBUS_PAR_UP(double  NBUS_PAR_UP) {
        this.NBUS_PAR_UP = NBUS_PAR_UP;
    }
    public void setCATEGORIE_CODE(String  CATEGORIE_CODE) {
        this.CATEGORIE_CODE = CATEGORIE_CODE;
    }
    public void setTYPE_CODE(String TYPE_CODE) {
        this.TYPE_CODE = TYPE_CODE;
    }
    public void setFAMILLE_CODE(String  FAMILLE_CODE) {
        this.FAMILLE_CODE = FAMILLE_CODE;
    }
    public void setDATE_CREATION(String  DATE_CREATION) {
        this.DATE_CREATION = DATE_CREATION;
    }
    public void setCREATEUR_CODE(String CREATEUR_CODE) {
        this.CREATEUR_CODE = CREATEUR_CODE;
    }
    public void setTS(String TS) {
        this.TS = TS;
    }
    public void setINACTIF(double  INACTIF) {
        this.INACTIF = INACTIF;
    }
    public void setINACTIF_RAISON(String INACTIF_RAISON) {
        this.INACTIF_RAISON = INACTIF_RAISON;
    }
    public void setRANG(int  RANG) {
        this.RANG = RANG;
    }
    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }


    public String getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(String IMAGE) {
        this.IMAGE = IMAGE;
    }

    public double getARTICLE_PRIX() {
        return ARTICLE_PRIX;
    }

    public void setARTICLE_PRIX(double ARTICLE_PRIX) {
        this.ARTICLE_PRIX = ARTICLE_PRIX;
    }

    @Override
    public String toString() {
        return "Article{" +
                "ARTICLE_CODE='" + ARTICLE_CODE + '\'' +
                ", ARTICLE_DESIGNATION1='" + ARTICLE_DESIGNATION1 + '\'' +
                ", ARTICLE_DESIGNATION2='" + ARTICLE_DESIGNATION2 + '\'' +
                ", ARTICLE_DESIGNATION3='" + ARTICLE_DESIGNATION3 + '\'' +
                ", ARTICLE_SKU=" + ARTICLE_SKU +
                ", LITTRAGE_UP=" + LITTRAGE_UP +
                ", POIDKG_UP=" + POIDKG_UP +
                ", LITTRAGE_US=" + LITTRAGE_US +
                ", POIDKG_US=" + POIDKG_US +
                ", NBUS_PAR_UP=" + NBUS_PAR_UP +
                ", CATEGORIE_CODE='" + CATEGORIE_CODE + '\'' +
                ", TYPE_CODE='" + TYPE_CODE + '\'' +
                ", FAMILLE_CODE='" + FAMILLE_CODE + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", CREATEUR_CODE='" + CREATEUR_CODE + '\'' +
                ", TS='" + TS + '\'' +
                ", INACTIF=" + INACTIF +
                ", INACTIF_RAISON='" + INACTIF_RAISON + '\'' +
                ", RANG=" + RANG +
                ", VERSION='" + VERSION + '\'' +
                ", IMAGE='" + IMAGE + '\'' +
                ", ARTICLE_PRIX=" + ARTICLE_PRIX +
                ", nbr_vente=" + nbr_vente +
                ", nbr_caisse=" + nbr_caisse +
                ", nbre_bouteille=" + nbre_bouteille +
                '}';
    }
}
