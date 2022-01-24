package com.newtech.newtech_sfm.Metier;

import org.json.JSONException;
import org.json.JSONObject;

public class TbClientArticle {

    private String ARTICLE_CODE;
    private String ARTICLE_DESIGNATION;
    private String UNITE_CODE;
    private String QUANTITE;


    public TbClientArticle() {
    }

    public TbClientArticle(String ARTICLE_CODE, String ARTICLE_DESIGNATION, String UNITE_CODE, String QUANTITE) {
        this.ARTICLE_CODE = ARTICLE_CODE;
        this.ARTICLE_DESIGNATION = ARTICLE_DESIGNATION;
        this.UNITE_CODE = UNITE_CODE;
        this.QUANTITE = QUANTITE;
    }

    public TbClientArticle(JSONObject tbClient) {
        try {

            this.ARTICLE_CODE = tbClient.getString("ARTICLE_CODE");
            this.ARTICLE_DESIGNATION = tbClient.getString("ARTICLE_DESIGNATION");
            this.UNITE_CODE = tbClient.getString("UNITE_CODE");
            this.QUANTITE = tbClient.getString("QUANTITE");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getARTICLE_CODE() {
        return ARTICLE_CODE;
    }

    public void setARTICLE_CODE(String ARTICLE_CODE) {
        this.ARTICLE_CODE = ARTICLE_CODE;
    }

    public String getARTICLE_DESIGNATION() {
        return ARTICLE_DESIGNATION;
    }

    public void setARTICLE_DESIGNATION(String ARTICLE_DESIGNATION) {
        this.ARTICLE_DESIGNATION = ARTICLE_DESIGNATION;
    }

    public String getUNITE_CODE() {
        return UNITE_CODE;
    }

    public void setUNITE_CODE(String UNITE_CODE) {
        this.UNITE_CODE = UNITE_CODE;
    }

    public String getQUANTITE() {
        return QUANTITE;
    }

    public void setQUANTITE(String QUANTITE) {
        this.QUANTITE = QUANTITE;
    }

    @Override
    public String toString() {
        return "TbClientArticle{" +
                "ARTICLE_CODE='" + ARTICLE_CODE + '\'' +
                ", ARTICLE_DESIGNATION='" + ARTICLE_DESIGNATION + '\'' +
                ", UNITE_CODE='" + UNITE_CODE + '\'' +
                ", QUANTITE='" + QUANTITE + '\'' +
                '}';
    }
}
