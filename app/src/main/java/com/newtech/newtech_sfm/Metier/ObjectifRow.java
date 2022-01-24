package com.newtech.newtech_sfm.Metier;

/**
 * Created by sferricha on 26/08/2016.
 */

public class ObjectifRow {

    private String FAMILLE_NOM;
    private Double Objectif;
    private Double Realisation;
    private Double Percent;

    public ObjectifRow(String FAMILLE_NOM, Double objectif, Double realisation, Double percent) {
        this.FAMILLE_NOM = FAMILLE_NOM;
        Objectif = objectif;
        Realisation = realisation;
        Percent = percent;
    }

    public ObjectifRow() {
    }

    public String getFAMILLE_NOM() {
        return FAMILLE_NOM;
    }

    public void setFAMILLE_NOM(String FAMILLE_NOM) {
        this.FAMILLE_NOM = FAMILLE_NOM;
    }

    public Double getObjectif() {
        return Objectif;
    }

    public void setObjectif(Double objectif) {
        Objectif = objectif;
    }

    public Double getRealisation() {
        return Realisation;
    }

    public void setRealisation(Double realisation) {
        Realisation = realisation;
    }

    public Double getPercent() {
        return Percent;
    }

    public void setPercent(Double percent) {
        Percent = percent;
    }
}
