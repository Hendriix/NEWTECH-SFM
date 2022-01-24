package com.newtech.newtech_sfm.Metier;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TbClientVisite {

    private static final String TAG = TbClientVisite.class.getName();
    private String VISITE_DATE;
    private String INACTIF;
    private String REALISATION;
    private String CHIFFRE_AFFAIRE;
    private ArrayList<TbClientArticle> tbClientArticles;


    public TbClientVisite() {
    }

    public TbClientVisite(String VISITE_DATE, String INACTIF, String REALISATION, String CHIFFRE_AFFAIRE, ArrayList<TbClientArticle> tbClientArticles) {
        this.VISITE_DATE = VISITE_DATE;
        this.INACTIF = INACTIF;
        this.REALISATION = REALISATION;
        this.CHIFFRE_AFFAIRE = CHIFFRE_AFFAIRE;
        this.tbClientArticles = tbClientArticles;
    }

    public String getREALISATION() {
        return REALISATION;
    }

    public void setREALISATION(String REALISATION) {
        this.REALISATION = REALISATION;
    }

    public String getCHIFFRE_AFFAIRE() {
        return CHIFFRE_AFFAIRE;
    }

    public void setCHIFFRE_AFFAIRE(String CHIFFRE_AFFAIRE) {
        this.CHIFFRE_AFFAIRE = CHIFFRE_AFFAIRE;
    }

    public TbClientVisite(JSONObject tbClient) {

        ArrayList<TbClientArticle> tbClientArticleArrayList = new ArrayList<>();
        try {

            this.VISITE_DATE = tbClient.getString("VISITE_DATE");
            this.INACTIF = tbClient.getString("INACTIF");
            this.REALISATION = tbClient.getString("REALISATION");
            this.CHIFFRE_AFFAIRE = tbClient.getString("CHIFFRE_AFFAIRE");
            JSONArray tbClientsArticles= tbClient.getJSONArray("tbVisiteArticle");
            if(tbClientsArticles.length() > 0){

                for(int i=0 ; i<tbClientsArticles.length() ; i++){

                    JSONObject secondObject = tbClientsArticles.getJSONObject(i);
                    TbClientArticle tbClientArticle = new TbClientArticle(secondObject);
                    tbClientArticleArrayList.add(tbClientArticle);
                }



            }else{
                Log.d(TAG, "onResponse: false");
            }
            this.tbClientArticles = tbClientArticleArrayList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getVISITE_DATE() {
        return VISITE_DATE;
    }

    public void setVISITE_DATE(String VISITE_DATE) {
        this.VISITE_DATE = VISITE_DATE;
    }

    public String getINACTIF() {
        return INACTIF;
    }

    public void setINACTIF(String INACTIF) {
        this.INACTIF = INACTIF;
    }

    public ArrayList<TbClientArticle> getTbClientArticles() {
        return tbClientArticles;
    }

    public void setTbClientArticles(ArrayList<TbClientArticle> tbClientArticles) {
        this.tbClientArticles = tbClientArticles;
    }

    @Override
    public String toString() {
        return "TbClientVisite{" +
                "VISITE_DATE='" + VISITE_DATE + '\'' +
                ", INACTIF=" + INACTIF +
                ", REALISATION='" + REALISATION + '\'' +
                ", CHIFFRE_AFFAIRE='" + CHIFFRE_AFFAIRE + '\'' +
                ", tbClientArticles=" + tbClientArticles +
                '}';
    }
}
