package com.newtech.newtech_sfm.Metier;

import android.os.Parcel;
import android.os.Parcelable;

import com.newtech.newtech_sfm.Metier_Manager.ClientManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by stagiaireit2 on 19/07/2016.
 */

public class Client implements Parcelable {


    String CLIENT_CODE,
    CLIENT_NOM,
    CLIENT_TELEPHONE1,
    CLIENT_TELEPHONE2;
    int STATUT_CODE;
    String DISTRIBUTEUR_CODE,
    REGION_CODE,
    ZONE_CODE,
    VILLE_CODE,
    SECTEUR_CODE,
    SOUSSECTEUR_CODE,
    TOURNEE_CODE;
    String ADRESSE_NR;
    String  ADRESSE_RUE,
    ADRESSE_QUARTIER,
    TYPE_CODE,
    CATEGORIE_CODE,
    GROUPE_CODE,
    CLASSE_CODE,
    CIRCUIT_CODE,
    FAMILLE_CODE;
    int RANG;
    String GPS_LATITUDE,
    GPS_LONGITUDE,
    MODE_PAIEMENT,
    POTENTIEL_TONNE;
    String  FREQUENCE_VISITE;
    String DATE_CREATION,
    CREATEUR_CODE,
    INACTIF,
    INACTIF_RAISON,
    STOCK_CODE,
    VERSION,
    IMAGE,
    LISTEPRIX_CODE;
    int QR_CODE;

    private ClientManager clientManager;

    protected Client(Parcel in) {
        CLIENT_CODE = in.readString();
        CLIENT_NOM = in.readString();
        CLIENT_TELEPHONE1 = in.readString();
        CLIENT_TELEPHONE2 = in.readString();
        STATUT_CODE = in.readInt();
        DISTRIBUTEUR_CODE = in.readString();
        REGION_CODE = in.readString();
        ZONE_CODE = in.readString();
        VILLE_CODE = in.readString();
        SECTEUR_CODE = in.readString();
        SOUSSECTEUR_CODE = in.readString();
        TOURNEE_CODE = in.readString();
        ADRESSE_NR = in.readString();
        ADRESSE_RUE = in.readString();
        ADRESSE_QUARTIER = in.readString();
        TYPE_CODE = in.readString();
        CATEGORIE_CODE = in.readString();
        GROUPE_CODE = in.readString();
        CLASSE_CODE = in.readString();
        CIRCUIT_CODE = in.readString();
        FAMILLE_CODE = in.readString();
        RANG = in.readInt();
        GPS_LATITUDE = in.readString();
        GPS_LONGITUDE = in.readString();
        MODE_PAIEMENT = in.readString();
        POTENTIEL_TONNE = in.readString();
        FREQUENCE_VISITE = in.readString();
        DATE_CREATION = in.readString();
        CREATEUR_CODE = in.readString();
        INACTIF = in.readString();
        INACTIF_RAISON = in.readString();
        STOCK_CODE = in.readString();
        VERSION = in.readString();
        IMAGE = in.readString();
        LISTEPRIX_CODE = in.readString();
        QR_CODE = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(CLIENT_CODE);
        dest.writeString(CLIENT_NOM);
        dest.writeString(CLIENT_TELEPHONE1);
        dest.writeString(CLIENT_TELEPHONE2);
        dest.writeInt(STATUT_CODE);
        dest.writeString(DISTRIBUTEUR_CODE);
        dest.writeString(REGION_CODE);
        dest.writeString(ZONE_CODE);
        dest.writeString(VILLE_CODE);
        dest.writeString(SECTEUR_CODE);
        dest.writeString(SOUSSECTEUR_CODE);
        dest.writeString(TOURNEE_CODE);
        dest.writeString(ADRESSE_NR);
        dest.writeString(ADRESSE_RUE);
        dest.writeString(ADRESSE_QUARTIER);
        dest.writeString(TYPE_CODE);
        dest.writeString(CATEGORIE_CODE);
        dest.writeString(GROUPE_CODE);
        dest.writeString(CLASSE_CODE);
        dest.writeString(CIRCUIT_CODE);
        dest.writeString(FAMILLE_CODE);
        dest.writeInt(RANG);
        dest.writeString(GPS_LATITUDE);
        dest.writeString(GPS_LONGITUDE);
        dest.writeString(MODE_PAIEMENT);
        dest.writeString(POTENTIEL_TONNE);
        dest.writeString(FREQUENCE_VISITE);
        dest.writeString(DATE_CREATION);
        dest.writeString(CREATEUR_CODE);
        dest.writeString(INACTIF);
        dest.writeString(INACTIF_RAISON);
        dest.writeString(STOCK_CODE);
        dest.writeString(VERSION);
        dest.writeString(IMAGE);
        dest.writeString(LISTEPRIX_CODE);
        dest.writeInt(QR_CODE);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    public ClientManager getClientManager() {
        return clientManager;
    }

    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    public Client() {

    }

    public Client(Client client){
        try{
            this.CLIENT_CODE = client.getCLIENT_CODE();
            this.CLIENT_NOM = client.getCLIENT_NOM();
            this.CLIENT_TELEPHONE1 = client.getCLIENT_TELEPHONE1();
            this.CLIENT_TELEPHONE2 = client.getCLIENT_TELEPHONE2();
            this.STATUT_CODE = client.getSTATUT_CODE();
            this.DISTRIBUTEUR_CODE = client.getDISTRIBUTEUR_CODE();
            this.REGION_CODE = client.getREGION_CODE();
            this.ZONE_CODE = client.getZONE_CODE();
            this.VILLE_CODE = client.getVILLE_CODE();
            this.SECTEUR_CODE = client.getSECTEUR_CODE();
            this.SOUSSECTEUR_CODE = client.getSOUSSECTEUR_CODE();
            this.TOURNEE_CODE = client.getTOURNEE_CODE();
            this.ADRESSE_NR = client.getADRESSE_NR();
            this.ADRESSE_RUE = client.getADRESSE_RUE();
            this.ADRESSE_QUARTIER = client.getADRESSE_QUARTIER();
            this.TYPE_CODE = client.getTYPE_CODE();
            this.CATEGORIE_CODE = client.getCATEGORIE_CODE();
            this.GROUPE_CODE = client.getGROUPE_CODE();
            this.CLASSE_CODE = client.getCLASSE_CODE();
            this.CIRCUIT_CODE = client.getCIRCUIT_CODE();
            this.FAMILLE_CODE = client.getFAMILLE_CODE();
            this.RANG = client.getRANG();
            this.GPS_LATITUDE = client.getGPS_LATITUDE();
            this.GPS_LONGITUDE = client.getGPS_LONGITUDE();
            this.MODE_PAIEMENT = client.getMODE_PAIEMENT();
            this.POTENTIEL_TONNE = client.getPOTENTIEL_TONNE();
            this.FREQUENCE_VISITE = client.getFREQUENCE_VISITE();
            this.DATE_CREATION = client.getDATE_CREATION();
            this.CREATEUR_CODE = client.getCREATEUR_CODE();
            this.INACTIF = client.getINACTIF();
            this.INACTIF_RAISON = client.getINACTIF_RAISON();
            this.STOCK_CODE = client.getSTOCK_CODE();
            this.VERSION = client.getVERSION();
            this.IMAGE = client.getIMAGE();
            this.LISTEPRIX_CODE = client.getLISTEPRIX_CODE();
            this.QR_CODE = client.getQR_CODE();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Client(JSONObject client) {

        try {
            this.CLIENT_CODE = client.getString("CLIENT_CODE");
            this.CLIENT_NOM = client.getString("CLIENT_NOM");
            this.CLIENT_TELEPHONE1 = client.getString("CLIENT_TELEPHONE1");
            this.CLIENT_TELEPHONE2 = client.getString("CLIENT_TELEPHONE2");
            this.STATUT_CODE = client.getInt("STATUT_CODE");
            this.DISTRIBUTEUR_CODE = client.getString("DISTRIBUTEUR_CODE");
            this.REGION_CODE = client.getString("REGION_CODE");
            this.ZONE_CODE = client.getString("ZONE_CODE");
            this.VILLE_CODE = client.getString("VILLE_CODE");
            this.SECTEUR_CODE = client.getString("SECTEUR_CODE");
            this.SOUSSECTEUR_CODE = client.getString("SOUSSECTEUR_CODE");
            this.TOURNEE_CODE = client.getString("TOURNEE_CODE");
            this.ADRESSE_NR = client.getString("ADRESSE_NR");
            this.ADRESSE_RUE = client.getString("ADRESSE_RUE");
            this.ADRESSE_QUARTIER = client.getString("ADRESSE_QUARTIER");
            this.TYPE_CODE = client.getString("TYPE_CODE");
            this.CATEGORIE_CODE = client.getString("CATEGORIE_CODE");
            this.GROUPE_CODE = client.getString("GROUPE_CODE");
            this.CLASSE_CODE = client.getString("CLASSE_CODE");
            this.CIRCUIT_CODE = client.getString("CIRCUIT_CODE");
            this.FAMILLE_CODE = client.getString("FAMILLE_CODE");
            this.RANG = client.getInt("RANG");
            this.GPS_LATITUDE = client.getString("GPS_LATITUDE");
            this.GPS_LONGITUDE = client.getString("GPS_LONGITUDE");
            this.MODE_PAIEMENT = client.getString("MODE_PAIEMENT");
            this.POTENTIEL_TONNE = client.getString("POTENTIEL_TONNE");
            this.FREQUENCE_VISITE = client.getString("FREQUENCE_VISITE");
            this.DATE_CREATION = client.getString("DATE_CREATION");
            this.CREATEUR_CODE = client.getString("CREATEUR_CODE");
            this.INACTIF = client.getString("INACTIF");
            this.INACTIF_RAISON = client.getString("INACTIF_RAISON");
            this.STOCK_CODE = client.getString("STOCK_CODE");
            this.VERSION = client.getString("VERSION");
            this.IMAGE = client.getString("IMAGE");
            this.LISTEPRIX_CODE = client.getString("LISTEPRIX_CODE");
            this.QR_CODE = client.getInt("QR_CODE");


        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void setCLIENT_CODE(String CLIENT_CODE) {
        this.CLIENT_CODE = CLIENT_CODE;
    }

    public void setCLIENT_NOM(String CLIENT_NOM) {
        this.CLIENT_NOM = CLIENT_NOM;
    }

    public void setCLIENT_TELEPHONE1(String CLIENT_TELEPHONE1) {
        this.CLIENT_TELEPHONE1 = CLIENT_TELEPHONE1;
    }

    public void setCLIENT_TELEPHONE2(String CLIENT_TELEPHONE2) {
        this.CLIENT_TELEPHONE2 = CLIENT_TELEPHONE2;
    }

    public void setSTATUT_CODE(int STATUT_CODE) {
        this.STATUT_CODE = STATUT_CODE;
    }

    public void setDISTRIBUTEUR_CODE(String DISTRIBUTEUR_CODE) {
        this.DISTRIBUTEUR_CODE = DISTRIBUTEUR_CODE;
    }

    public void setREGION_CODE(String REGION_CODE) {
        this.REGION_CODE = REGION_CODE;
    }

    public void setZONE_CODE(String ZONE_CODE) {
        this.ZONE_CODE = ZONE_CODE;
    }

    public void setVILLE_CODE(String VILLE_CODE) {
        this.VILLE_CODE = VILLE_CODE;
    }

    public void setSECTEUR_CODE(String SECTEUR_CODE) {
        this.SECTEUR_CODE = SECTEUR_CODE;
    }

    public void setSOUSSECTEUR_CODE(String SOUSSECTEUR_CODE) {
        this.SOUSSECTEUR_CODE = SOUSSECTEUR_CODE;
    }

    public void setTOURNEE_CODE(String TOURNEE_CODE) {
        this.TOURNEE_CODE = TOURNEE_CODE;
    }

    public void setADRESSE_NR(String ADRESSE_NR) {
        this.ADRESSE_NR = ADRESSE_NR;
    }

    public void setADRESSE_RUE(String ADRESSE_RUE) {
        this.ADRESSE_RUE = ADRESSE_RUE;
    }

    public void setADRESSE_QUARTIER(String ADRESSE_QUARTIER) {
        this.ADRESSE_QUARTIER = ADRESSE_QUARTIER;
    }

    public void setTYPE_CODE(String TYPE_CODE) {
        this.TYPE_CODE = TYPE_CODE;
    }

    public void setCATEGORIE_CODE(String CATEGORIE_CODE) {
        this.CATEGORIE_CODE = CATEGORIE_CODE;
    }

    public void setGROUPE_CODE(String GROUPE_CODE) {
        this.GROUPE_CODE = GROUPE_CODE;
    }

    public void setCLASSE_CODE(String CLASSE_CODE) {
        this.CLASSE_CODE = CLASSE_CODE;
    }

    public void setCIRCUIT_CODE(String CIRCUIT_CODE) {
        this.CIRCUIT_CODE = CIRCUIT_CODE;
    }

    public void setFAMILLE_CODE(String FAMILLE_CODE) {
        this.FAMILLE_CODE = FAMILLE_CODE;
    }

    public void setRANG(int RANG) {
        this.RANG = RANG;
    }

    public void setGPS_LATITUDE(String GPS_LATITUDE) {
        this.GPS_LATITUDE = GPS_LATITUDE;
    }

    public void setGPS_LONGITUDE(String GPS_LONGITUDE) {
        this.GPS_LONGITUDE = GPS_LONGITUDE;
    }

    public void setMODE_PAIEMENT(String MODE_PAIEMENT) {
        this.MODE_PAIEMENT = MODE_PAIEMENT;
    }

    public void setPOTENTIEL_TONNE(String POTENTIEL_TONNE) {
        this.POTENTIEL_TONNE = POTENTIEL_TONNE;
    }

    public void setFREQUENCE_VISITE(String FREQUENCE_VISITE) {
        this.FREQUENCE_VISITE = FREQUENCE_VISITE;
    }

    public void setDATE_CREATION(String DATE_CREATION) {
        this.DATE_CREATION = DATE_CREATION;
    }

    public void setCREATEUR_CODE(String CREATEUR_CODE) {
        this.CREATEUR_CODE = CREATEUR_CODE;
    }

    public void setINACTIF(String INACTIF) {
        this.INACTIF = INACTIF;
    }

    public void setINACTIF_RAISON(String INACTIF_RAISON) {
        this.INACTIF_RAISON = INACTIF_RAISON;
    }

    public void setSTOCK_CODE(String STOCK_CODE) {
        this.STOCK_CODE = STOCK_CODE;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public void setIMAGE(String IMAGE) {
        this.IMAGE = IMAGE;
    }

    public String getCLIENT_CODE() {
        return CLIENT_CODE;
    }

    public String getCLIENT_NOM() {
        return CLIENT_NOM;
    }

    public String getCLIENT_TELEPHONE1() {
        return CLIENT_TELEPHONE1;
    }

    public String getCLIENT_TELEPHONE2() {
        return CLIENT_TELEPHONE2;
    }

    public int getSTATUT_CODE() {
        return STATUT_CODE;
    }

    public String getDISTRIBUTEUR_CODE() {
        return DISTRIBUTEUR_CODE;
    }

    public String getREGION_CODE() {
        return REGION_CODE;
    }

    public String getZONE_CODE() {
        return ZONE_CODE;
    }

    public String getVILLE_CODE() {
        return VILLE_CODE;
    }

    public String getSECTEUR_CODE() {
        return SECTEUR_CODE;
    }

    public String getSOUSSECTEUR_CODE() {
        return SOUSSECTEUR_CODE;
    }

    public String getTOURNEE_CODE() {
        return TOURNEE_CODE;
    }

    public String getADRESSE_NR() {
        return ADRESSE_NR;
    }

    public String getADRESSE_RUE() {
        return ADRESSE_RUE;
    }

    public String getADRESSE_QUARTIER() {
        return ADRESSE_QUARTIER;
    }

    public String getTYPE_CODE() {
        return TYPE_CODE;
    }

    public String getCATEGORIE_CODE() {
        return CATEGORIE_CODE;
    }

    public String getGROUPE_CODE() {
        return GROUPE_CODE;
    }

    public String getCLASSE_CODE() {
        return CLASSE_CODE;
    }

    public String getCIRCUIT_CODE() {
        return CIRCUIT_CODE;
    }

    public String getFAMILLE_CODE() {
        return FAMILLE_CODE;
    }

    public int getRANG() {
        return RANG;
    }

    public String getGPS_LATITUDE() {
        return GPS_LATITUDE;
    }

    public String getGPS_LONGITUDE() {
        return GPS_LONGITUDE;
    }

    public String getMODE_PAIEMENT() {
        return MODE_PAIEMENT;
    }

    public String getPOTENTIEL_TONNE() {
        return POTENTIEL_TONNE;
    }

    public String getFREQUENCE_VISITE() {
        return FREQUENCE_VISITE;
    }

    public String getDATE_CREATION() {
        return DATE_CREATION;
    }

    public String getCREATEUR_CODE() {
        return CREATEUR_CODE;
    }

    public String getINACTIF() {
        return INACTIF;
    }

    public String getINACTIF_RAISON() {
        return INACTIF_RAISON;
    }

    public String getSTOCK_CODE() {
        return STOCK_CODE;
    }

    public String getVERSION() {
        return VERSION;
    }

    public String getIMAGE() {
        return IMAGE;
    }

    public String getLISTEPRIX_CODE() {
        return LISTEPRIX_CODE;
    }

    public void setLISTEPRIX_CODE(String LISTEPRIX_CODE) {
        this.LISTEPRIX_CODE = LISTEPRIX_CODE;
    }

    public int getQR_CODE() {
        return QR_CODE;
    }

    public void setQR_CODE(int QR_CODE) {
        this.QR_CODE = QR_CODE;
    }

    @Override
    public String toString() {
        return "Client{" +
                "CLIENT_CODE='" + CLIENT_CODE + '\'' +
                ", CLIENT_NOM='" + CLIENT_NOM + '\'' +
                ", CLIENT_TELEPHONE1='" + CLIENT_TELEPHONE1 + '\'' +
                ", CLIENT_TELEPHONE2='" + CLIENT_TELEPHONE2 + '\'' +
                ", STATUT_CODE=" + STATUT_CODE +
                ", DISTRIBUTEUR_CODE='" + DISTRIBUTEUR_CODE + '\'' +
                ", REGION_CODE='" + REGION_CODE + '\'' +
                ", ZONE_CODE='" + ZONE_CODE + '\'' +
                ", VILLE_CODE='" + VILLE_CODE + '\'' +
                ", SECTEUR_CODE='" + SECTEUR_CODE + '\'' +
                ", SOUSSECTEUR_CODE='" + SOUSSECTEUR_CODE + '\'' +
                ", TOURNEE_CODE='" + TOURNEE_CODE + '\'' +
                ", ADRESSE_NR='" + ADRESSE_NR + '\'' +
                ", ADRESSE_RUE='" + ADRESSE_RUE + '\'' +
                ", ADRESSE_QUARTIER='" + ADRESSE_QUARTIER + '\'' +
                ", TYPE_CODE='" + TYPE_CODE + '\'' +
                ", CATEGORIE_CODE='" + CATEGORIE_CODE + '\'' +
                ", GROUPE_CODE='" + GROUPE_CODE + '\'' +
                ", CLASSE_CODE='" + CLASSE_CODE + '\'' +
                ", CIRCUIT_CODE='" + CIRCUIT_CODE + '\'' +
                ", FAMILLE_CODE='" + FAMILLE_CODE + '\'' +
                ", RANG=" + RANG +
                ", GPS_LATITUDE='" + GPS_LATITUDE + '\'' +
                ", GPS_LONGITUDE='" + GPS_LONGITUDE + '\'' +
                ", MODE_PAIEMENT='" + MODE_PAIEMENT + '\'' +
                ", POTENTIEL_TONNE='" + POTENTIEL_TONNE + '\'' +
                ", FREQUENCE_VISITE='" + FREQUENCE_VISITE + '\'' +
                ", DATE_CREATION='" + DATE_CREATION + '\'' +
                ", CREATEUR_CODE='" + CREATEUR_CODE + '\'' +
                ", INACTIF='" + INACTIF + '\'' +
                ", INACTIF_RAISON='" + INACTIF_RAISON + '\'' +
                ", STOCK_CODE='" + STOCK_CODE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                ", LISTEPRIX_CODE='" + LISTEPRIX_CODE + '\'' +
                ", QR_CODE=" + QR_CODE +
                '}';
    }
}
