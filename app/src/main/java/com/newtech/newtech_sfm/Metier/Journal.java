package com.newtech.newtech_sfm.Metier;

public class Journal {

    private int ID;
    private String IMEI;
    private String TYPE;
    private String LOG_TEXT;
    private String TS;
    private String COMMENTAIRE;


    public Journal() {
    }

    public Journal(String IMEI, String TYPE, String LOG_TEXT, String TS, String COMMENTAIRE) {
        this.IMEI = IMEI;
        this.TYPE = TYPE;
        this.LOG_TEXT = LOG_TEXT;
        this.TS = TS;
        this.COMMENTAIRE = COMMENTAIRE;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getLOG_TEXT() {
        return LOG_TEXT;
    }

    public void setLOG_TEXT(String LOG_TEXT) {
        this.LOG_TEXT = LOG_TEXT;
    }

    public String getTS() {
        return TS;
    }

    public void setTS(String TS) {
        this.TS = TS;
    }

    public String getCOMMENTAIRE() {
        return COMMENTAIRE;
    }

    public void setCOMMENTAIRE(String COMMENTAIRE) {
        this.COMMENTAIRE = COMMENTAIRE;
    }

    @Override
    public String toString() {
        return "Journal{" +
                "ID=" + ID +
                ", IMEI='" + IMEI + '\'' +
                ", TYPE='" + TYPE + '\'' +
                ", LOG_TEXT='" + LOG_TEXT + '\'' +
                ", TS='" + TS + '\'' +
                ", COMMENTAIRE='" + COMMENTAIRE + '\'' +
                '}';
    }
}
