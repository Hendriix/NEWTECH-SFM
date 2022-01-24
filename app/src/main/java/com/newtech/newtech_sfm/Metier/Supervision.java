package com.newtech.newtech_sfm.Metier;

public class Supervision {

    private int ID;
    private String UTILISATEUR_CODE;
    private String IMEI;
    private String DATE_ACTION;
    private String VERSION_CODE;
    private String VERSION_NOM;
    private String IP;
    private int CONNECTED;
    private int PRINTER_CONNECTED;
    private String LAST_COMMANDE;
    private String LAST_VISITE;
    private String COMMENTAIRE;


    public Supervision() {
    }

    public Supervision(String UTILISATEUR_CODE, String IMEI, String DATE_ACTION, String VERSION_CODE, String VERSION_NOM, String IP, int CONNECTED, int PRINTER_CONNECTED, String LAST_COMMANDE, String LAST_VISITE, String COMMENTAIRE) {
        this.UTILISATEUR_CODE = UTILISATEUR_CODE;
        this.IMEI = IMEI;
        this.DATE_ACTION = DATE_ACTION;
        this.VERSION_CODE = VERSION_CODE;
        this.VERSION_NOM = VERSION_NOM;
        this.IP = IP;
        this.CONNECTED = CONNECTED;
        this.PRINTER_CONNECTED = PRINTER_CONNECTED;
        this.LAST_COMMANDE = LAST_COMMANDE;
        this.LAST_VISITE = LAST_VISITE;
        this.COMMENTAIRE = COMMENTAIRE;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUTILISATEUR_CODE() {
        return UTILISATEUR_CODE;
    }

    public void setUTILISATEUR_CODE(String UTILISATEUR_CODE) {
        this.UTILISATEUR_CODE = UTILISATEUR_CODE;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getDATE_ACTION() {
        return DATE_ACTION;
    }

    public void setDATE_ACTION(String DATE_ACTION) {
        this.DATE_ACTION = DATE_ACTION;
    }

    public String getVERSION_CODE() {
        return VERSION_CODE;
    }

    public void setVERSION_CODE(String VERSION_CODE) {
        this.VERSION_CODE = VERSION_CODE;
    }

    public String getVERSION_NOM() {
        return VERSION_NOM;
    }

    public void setVERSION_NOM(String VERSION_NOM) {
        this.VERSION_NOM = VERSION_NOM;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public int getCONNECTED() {
        return CONNECTED;
    }

    public void setCONNECTED(int CONNECTED) {
        this.CONNECTED = CONNECTED;
    }

    public int getPRINTER_CONNECTED() {
        return PRINTER_CONNECTED;
    }

    public void setPRINTER_CONNECTED(int PRINTER_CONNECTED) {
        this.PRINTER_CONNECTED = PRINTER_CONNECTED;
    }

    public String getLAST_COMMANDE() {
        return LAST_COMMANDE;
    }

    public void setLAST_COMMANDE(String LAST_COMMANDE) {
        this.LAST_COMMANDE = LAST_COMMANDE;
    }

    public String getLAST_VISITE() {
        return LAST_VISITE;
    }

    public void setLAST_VISITE(String LAST_VISITE) {
        this.LAST_VISITE = LAST_VISITE;
    }

    public String getCOMMENTAIRE() {
        return COMMENTAIRE;
    }

    public void setCOMMENTAIRE(String COMMENTAIRE) {
        this.COMMENTAIRE = COMMENTAIRE;
    }

    @Override
    public String toString() {
        return "Supervision{" +
                "ID=" + ID +
                ", UTILISATEUR_CODE='" + UTILISATEUR_CODE + '\'' +
                ", IMEI='" + IMEI + '\'' +
                ", DATE_ACTION='" + DATE_ACTION + '\'' +
                ", VERSION_CODE='" + VERSION_CODE + '\'' +
                ", VERSION_NOM='" + VERSION_NOM + '\'' +
                ", IP='" + IP + '\'' +
                ", CONNECTED=" + CONNECTED +
                ", PRINTER_CONNECTED=" + PRINTER_CONNECTED +
                ", LAST_COMMANDE='" + LAST_COMMANDE + '\'' +
                ", LAST_VISITE='" + LAST_VISITE + '\'' +
                ", COMMENTAIRE='" + COMMENTAIRE + '\'' +
                '}';
    }
}
