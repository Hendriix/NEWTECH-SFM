package com.newtech.newtech_sfm.Metier;

import java.util.ArrayList;

/**
 * Created by stagiaireit2 on 29/07/2016.
 */
public class Panier {

    ArrayList<UnitePanier> listUnitePaniers;

    public Panier(ArrayList<UnitePanier> listUnitePaniers) {
        this.listUnitePaniers = listUnitePaniers;
    }

    public ArrayList<UnitePanier> getListUnitePaniers() {
        return listUnitePaniers;
    }

    public void setListUnitePaniers(ArrayList<UnitePanier> listUnitePaniers) {
        this.listUnitePaniers = listUnitePaniers;
    }

    public void AjoutCommande(UnitePanier cmd){
        this.listUnitePaniers.add(cmd);
    }

    public Panier() {
    }
}
