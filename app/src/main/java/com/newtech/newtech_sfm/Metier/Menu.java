package com.newtech.newtech_sfm.Metier;

/**
 * Created by sferricha on 13/08/2016.
 */

public class Menu {
    private String MENU_CODE;
    private String TITRE;
    private String LIEN;
    private String DESCRIPTION;

    public Menu(String MENU_CODE, String TITRE, String LIEN, String DESCRIPTION) {
        this.MENU_CODE = MENU_CODE;
        this.TITRE = TITRE;
        this.LIEN = LIEN;
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public String getLIEN() {
        return LIEN;
    }

    public String getTITRE() {
        return TITRE;
    }

    public String getMENU_CODE() {
        return MENU_CODE;
    }

    public void setMENU_CODE(String MENU_CODE) {
        this.MENU_CODE = MENU_CODE;
    }

    public void setTITRE(String TITRE) {
        this.TITRE = TITRE;
    }

    public void setLIEN(String LIEN) {
        this.LIEN = LIEN;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    @Override
    public String toString() {
        return TITRE;
    }
}
