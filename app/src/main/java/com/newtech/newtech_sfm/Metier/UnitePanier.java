package com.newtech.newtech_sfm.Metier;

/**
 * Created by stagiaireit2 on 29/07/2016.
 */
public class UnitePanier {

    private int quantite ;
    private double prix;
    private String unite ;
    private String ARTICLE_CODE ;


    public UnitePanier() {
    }
    public UnitePanier(int quantite, double prix, String unite, String ARTICLE_CODE) {
        this.quantite = quantite;
        this.prix = prix;
        this.unite = unite;
        this.ARTICLE_CODE = ARTICLE_CODE;
    }

    public int getQuantite() {
        return quantite;
    }

    public double getPrix() {
        return prix;
    }

    public String getUnite() {
        return unite;
    }

    public String getARTICLE_CODE() {
        return ARTICLE_CODE;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public void setARTICLE_CODE(String ARTICLE_CODE) {
        this.ARTICLE_CODE = ARTICLE_CODE;
    }

    @Override
    public String toString() {
        return "UnitePanier{" +
                "quantite=" + quantite +
                ", prix=" + prix +
                ", unite='" + unite + '\'' +
                ", ARTICLE_CODE='" + ARTICLE_CODE + '\'' +
                '}'+"\n\n";
    }
}
