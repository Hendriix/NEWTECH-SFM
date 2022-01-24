package com.newtech.newtech_sfm.Metier;

/**
 * Created by TONPC on 30/05/2017.
 */

public class Imprimante {

    private Integer ID;
    private String LIBELLE;
    private String ADDMAC;
    private String STATUT;


    public Imprimante() {
    }

    public Imprimante(String LIBELLE, String ADDMAC, String STATUT){
        this.LIBELLE=LIBELLE;
        this.ADDMAC=ADDMAC;
        this.STATUT=STATUT;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getLIBELLE() {
        return LIBELLE;
    }

    public void setLIBELLE(String LIBELLE) {
        this.LIBELLE = LIBELLE;
    }

    public String getADDMAC() {
        return ADDMAC;
    }

    public void setADDMAC(String ADDMAC) {
        this.ADDMAC = ADDMAC;
    }

    public String getSTATUT() {
        return STATUT;
    }

    public void setSTATUT(String STATUT) {
        this.STATUT = STATUT;
    }

    @Override
    public String toString() {
        return "Imprimante{" +
                "ID=" + ID +
                ", LIBELLE='" + LIBELLE + '\'' +
                ", ADDMAC='" + ADDMAC + '\'' +
                ", STATUT='" + STATUT + '\'' +
                '}';
    }
}
