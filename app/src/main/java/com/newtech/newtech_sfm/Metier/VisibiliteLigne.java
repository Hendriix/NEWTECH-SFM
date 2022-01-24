package com.newtech.newtech_sfm.Metier;

public class VisibiliteLigne {

    private String VISIBILITE_CODE;
    private int VISIBILITE_LIGNE_CODE;
    private String ARTICLE_CODE;
    private String FAMILLE_CODE;
    private int VISIBILITE;
    private int REFERENCEMENT;
    private String PROMOTION;
    private String ROTATION;
    private int POSITION;
    private double PRIX;
    private double STOCK_RAYON;
    private int RUPTURE_RAYON;
    private double STOCK_MAGASIN;
    private int RUPTURE_MAGASIN;
    private String COMMENTAIRE;
    private String COMMENTAIRE_LIGNE;
    private String VERSION;

    public VisibiliteLigne() {
    }

    public VisibiliteLigne(String VISIBILITE_CODE, int VISIBILITE_LIGNE_CODE, String ARTICLE_CODE, String FAMILLE_CODE, int VISIBILITE, int REFERENCEMENT, String PROMOTION, String ROTATION, int POSITION, double PRIX, double STOCK_RAYON, int RUPTURE_RAYON, double STOCK_MAGASIN, int RUPTURE_MAGASIN, String COMMENTAIRE, String COMMENTAIRE_LIGNE, String VERSION) {
        this.VISIBILITE_CODE = VISIBILITE_CODE;
        this.VISIBILITE_LIGNE_CODE = VISIBILITE_LIGNE_CODE;
        this.ARTICLE_CODE = ARTICLE_CODE;
        this.FAMILLE_CODE = FAMILLE_CODE;
        this.VISIBILITE = VISIBILITE;
        this.REFERENCEMENT = REFERENCEMENT;
        this.PROMOTION = PROMOTION;
        this.ROTATION = ROTATION;
        this.POSITION = POSITION;
        this.PRIX = PRIX;
        this.STOCK_RAYON = STOCK_RAYON;
        this.RUPTURE_RAYON = RUPTURE_RAYON;
        this.STOCK_MAGASIN = STOCK_MAGASIN;
        this.RUPTURE_MAGASIN = RUPTURE_MAGASIN;
        this.COMMENTAIRE = COMMENTAIRE;
        this.COMMENTAIRE_LIGNE = COMMENTAIRE_LIGNE;
        this.VERSION = VERSION;
    }

    public VisibiliteLigne(String VISIBILITE_CODE, String ARTICLE_CODE, String FAMILLE_CODE, int VISIBILITE_LIGNE_CODE , int visibilite, int referencement){

        this.VISIBILITE_CODE = VISIBILITE_CODE;
        this.VISIBILITE_LIGNE_CODE = VISIBILITE_LIGNE_CODE;
        this.ARTICLE_CODE = ARTICLE_CODE;
        this.FAMILLE_CODE = FAMILLE_CODE;
        this.PROMOTION = "AUCUNE";
        this.ROTATION = "DEFAULT";
        this.VISIBILITE = visibilite;
        this.REFERENCEMENT = referencement;
        this.COMMENTAIRE = "to_insert";
        this.COMMENTAIRE_LIGNE = "DEFAULT";
        this.VERSION = "non_verifiee";
    }

    public String getVISIBILITE_CODE() {
        return VISIBILITE_CODE;
    }

    public void setVISIBILITE_CODE(String VISIBILITE_CODE) {
        this.VISIBILITE_CODE = VISIBILITE_CODE;
    }

    public int getVISIBILITE_LIGNE_CODE() {
        return VISIBILITE_LIGNE_CODE;
    }

    public void setVISIBILITE_LIGNE_CODE(int VISIBILITE_LIGNE_CODE) {
        this.VISIBILITE_LIGNE_CODE = VISIBILITE_LIGNE_CODE;
    }

    public String getARTICLE_CODE() {
        return ARTICLE_CODE;
    }

    public void setARTICLE_CODE(String ARTICLE_CODE) {
        this.ARTICLE_CODE = ARTICLE_CODE;
    }

    public String getFAMILLE_CODE() {
        return FAMILLE_CODE;
    }

    public void setFAMILLE_CODE(String FAMILLE_CODE) {
        this.FAMILLE_CODE = FAMILLE_CODE;
    }

    public int getVISIBILITE() {
        return VISIBILITE;
    }

    public void setVISIBILITE(int VISIBILITE) {
        this.VISIBILITE = VISIBILITE;
    }

    public int getREFERENCEMENT() {
        return REFERENCEMENT;
    }

    public String getROTATION() {
        return ROTATION;
    }

    public void setROTATION(String ROTATION) {
        this.ROTATION = ROTATION;
    }

    public int getPOSITION() {
        return POSITION;
    }

    public void setPOSITION(int POSITION) {
        this.POSITION = POSITION;
    }

    public double getPRIX() {
        return PRIX;
    }

    public void setPRIX(double PRIX) {
        this.PRIX = PRIX;
    }

    public double getSTOCK_RAYON() {
        return STOCK_RAYON;
    }

    public void setSTOCK_RAYON(double STOCK_RAYON) {
        this.STOCK_RAYON = STOCK_RAYON;
    }

    public int getRUPTURE_RAYON() {
        return RUPTURE_RAYON;
    }

    public void setRUPTURE_RAYON(int RUPTURE_RAYON) {
        this.RUPTURE_RAYON = RUPTURE_RAYON;
    }

    public double getSTOCK_MAGASIN() {
        return STOCK_MAGASIN;
    }

    public void setSTOCK_MAGASIN(double STOCK_MAGASIN) {
        this.STOCK_MAGASIN = STOCK_MAGASIN;
    }

    public int getRUPTURE_MAGASIN() {
        return RUPTURE_MAGASIN;
    }

    public void setRUPTURE_MAGASIN(int RUPTURE_MAGASIN) {
        this.RUPTURE_MAGASIN = RUPTURE_MAGASIN;
    }

    public void setREFERENCEMENT(int REFERENCEMENT) {
        this.REFERENCEMENT = REFERENCEMENT;
    }

    public String getCOMMENTAIRE() {
        return COMMENTAIRE;
    }

    public void setCOMMENTAIRE(String COMMENTAIRE) {
        this.COMMENTAIRE = COMMENTAIRE;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public String getPROMOTION() {
        return PROMOTION;
    }

    public void setPROMOTION(String PROMOTION) {
        this.PROMOTION = PROMOTION;
    }

    public String getCOMMENTAIRE_LIGNE() {
        return COMMENTAIRE_LIGNE;
    }

    public void setCOMMENTAIRE_LIGNE(String COMMENTAIRE_LIGNE) {
        this.COMMENTAIRE_LIGNE = COMMENTAIRE_LIGNE;
    }

    @Override
    public String toString() {
        return "VisibiliteLigne{" +
                "VISIBILITE_CODE='" + VISIBILITE_CODE + '\'' +
                ", VISIBILITE_LIGNE_CODE=" + VISIBILITE_LIGNE_CODE +
                ", ARTICLE_CODE='" + ARTICLE_CODE + '\'' +
                ", FAMILLE_CODE='" + FAMILLE_CODE + '\'' +
                ", VISIBILITE=" + VISIBILITE +
                ", REFERENCEMENT=" + REFERENCEMENT +
                ", PROMOTION='" + PROMOTION + '\'' +
                ", ROTATION='" + ROTATION + '\'' +
                ", POSITION=" + POSITION +
                ", PRIX=" + PRIX +
                ", STOCK_RAYON=" + STOCK_RAYON +
                ", RUPTURE_RAYON=" + RUPTURE_RAYON +
                ", STOCK_MAGASIN=" + STOCK_MAGASIN +
                ", RUPTURE_MAGASIN=" + RUPTURE_MAGASIN +
                ", COMMENTAIRE='" + COMMENTAIRE + '\'' +
                ", COMMENTAIRE_LIGNE='" + COMMENTAIRE_LIGNE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}
