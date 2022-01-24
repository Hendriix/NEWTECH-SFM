package com.newtech.newtech_sfm.Metier;

public class Connection {

    private int ID;
    private String UTILISATEUR_CODE;
    private String IMEI;
    private String DATE_ACTION;
    private String VERSION_CODE;
    private String VERSION_NOM;
    private int CONNECTED;
    private String COMMENTAIRE;


    public Connection() {
    }

    public Connection(String UTILISATEUR_CODE, String IMEI, String DATE_ACTION, String VERSION_CODE, String VERSION_NOM, int CONNECTED, String COMMENTAIRE) {
        this.UTILISATEUR_CODE = UTILISATEUR_CODE;
        this.IMEI = IMEI;
        this.DATE_ACTION = DATE_ACTION;
        this.VERSION_CODE = VERSION_CODE;
        this.VERSION_NOM = VERSION_NOM;
        this.CONNECTED = CONNECTED;
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

    public int getCONNECTED() {
        return CONNECTED;
    }

    public void setCONNECTED(int CONNECTED) {
        this.CONNECTED = CONNECTED;
    }

    public String getCOMMENTAIRE() {
        return COMMENTAIRE;
    }

    public void setCOMMENTAIRE(String COMMENTAIRE) {
        this.COMMENTAIRE = COMMENTAIRE;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "ID=" + ID +
                ", UTILISATEUR_CODE='" + UTILISATEUR_CODE + '\'' +
                ", IMEI='" + IMEI + '\'' +
                ", DATE_ACTION='" + DATE_ACTION + '\'' +
                ", VERSION_CODE='" + VERSION_CODE + '\'' +
                ", VERSION_NOM='" + VERSION_NOM + '\'' +
                ", CONNECTED='" + CONNECTED + '\'' +
                ", COMMENTAIRE='" + COMMENTAIRE + '\'' +
                '}';
    }
}
