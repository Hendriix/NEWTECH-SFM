package com.newtech.newtech_sfm.Metier;

public class ChoufouniFamille {

    private String CHOUFOUNI_CODE;
    private String FAMILLE_CODE;
    private String VERSION;

    public ChoufouniFamille(String CHOUFOUNI_CODE, String FAMILLE_CODE, String VERSION) {
        this.CHOUFOUNI_CODE = CHOUFOUNI_CODE;
        this.FAMILLE_CODE = FAMILLE_CODE;
        this.VERSION = VERSION;
    }

    public ChoufouniFamille() {
    }

    public String getCHOUFOUNI_CODE() {
        return CHOUFOUNI_CODE;
    }

    public void setCHOUFOUNI_CODE(String CHOUFOUNI_CODE) {
        this.CHOUFOUNI_CODE = CHOUFOUNI_CODE;
    }

    public String getFAMILLE_CODE() {
        return FAMILLE_CODE;
    }

    public void setFAMILLE_CODE(String FAMILLE_CODE) {
        this.FAMILLE_CODE = FAMILLE_CODE;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }
}
