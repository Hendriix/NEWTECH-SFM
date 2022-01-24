package com.newtech.newtech_sfm.Metier;

/**
 * Created by sferricha on 16/12/2016.
 */

public class Gpstracker {

    private Integer ID;
    private String UTILISATEUR_CODE,
    TS,
    DESCRIPTION,
    VERSION;

    private double LATITUDE,LONGITUDE;

    Gpstracker instance;

    private static Gpstracker single_instance = null;

    // variable of type String
    public String s;

    // private constructor restricted to this class itself
    public Gpstracker(){

    }

    // static method to create instance of Singleton class
    public static Gpstracker getInstance()
    {
        if (single_instance == null)
            single_instance = new Gpstracker();

        return single_instance;
    }


    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setUTILISATEUR_CODE(String UTILISATEUR_CODE) {
        this.UTILISATEUR_CODE = UTILISATEUR_CODE;
    }

    public void setLATITUDE(Double LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public void setLONGITUDE(Double LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public void setTS(String TS) {
        this.TS = TS;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }


    public Integer getID() {
        return ID;
    }
    public String getUTILISATEUR_CODE() {
        return UTILISATEUR_CODE;
    }
    public Double getLATITUDE() {
        return LATITUDE;
    }
    public Double getLONGITUDE() {
        return LONGITUDE;
    }
    public String getTS() {
        return TS;
    }
    public String getDESCRIPTION() {
        return DESCRIPTION;
    }
    public String getVERSION() {
        return VERSION;
    }


    @Override
    public String toString() {
        return
                "ID : " + ID + "\n" +
                "UTILISATEUR_CODE : " + UTILISATEUR_CODE + "\n" +
                        "LATITUDE : " + LATITUDE + "\n" +
                        "LONGITUDE : " + LONGITUDE + "\n" +
                        "DESCRIPTION : " + DESCRIPTION + "\n" +
                        "VERSION : " + VERSION + "\n";
    }

}
