package com.newtech.newtech_sfm.Metier;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TbClient {

    private static final String  TAG = TbClient.class.getName();

    private String CHIFFRE_AFFAIRE;
    private String OBJECTIF;
    private String REALISATION;
    private String POURCENTAGE;
    private String CHOUFOUNI;
    private ArrayList<TbClientVisite> tbClientVisites;


    public TbClient() {
    }

    public TbClient(String CHIFFRE_AFFAIRE, String OBJECTIF, String REALISATION, String POURCENTAGE, String CHOUFOUNI, ArrayList<TbClientVisite> tbClientVisites) {
        this.CHIFFRE_AFFAIRE = CHIFFRE_AFFAIRE;
        this.OBJECTIF = OBJECTIF;
        this.REALISATION = REALISATION;
        this.POURCENTAGE = POURCENTAGE;
        this.CHOUFOUNI = CHOUFOUNI;
        this.tbClientVisites = tbClientVisites;
    }

    public TbClient(JSONObject tbClient) {
        ArrayList<TbClientVisite> tbClientVisiteArrayList = new ArrayList<>();
        try {

            this.CHIFFRE_AFFAIRE = tbClient.getString("CHIFFRE_AFFAIRE");
            this.OBJECTIF = tbClient.getString("OBJECTIF");
            this.REALISATION = tbClient.getString("REALISATION");
            this.POURCENTAGE = tbClient.getString("POURCENTAGE");
            this.CHOUFOUNI = tbClient.getString("CHOUFOUNI");
            JSONArray tbClientsVisites = tbClient.getJSONArray("tbClientVisites");
            if(tbClientsVisites.length() > 0){

                for(int i=0 ; i<tbClientsVisites.length() ; i++){

                    JSONObject secondObject = tbClientsVisites.getJSONObject(i);
                    TbClientVisite tbClientVisite = new TbClientVisite(secondObject);
                    tbClientVisiteArrayList.add(tbClientVisite);
                }


                Log.d(TAG, "onResponse: true" + tbClient.toString());

            }else{
                Log.d(TAG, "onResponse: false");
            }
            this.tbClientVisites = tbClientVisiteArrayList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getCHIFFRE_AFFAIRE() {
        return CHIFFRE_AFFAIRE;
    }

    public void setCHIFFRE_AFFAIRE(String CHIFFRE_AFFAIRE) {
        this.CHIFFRE_AFFAIRE = CHIFFRE_AFFAIRE;
    }

    public String getOBJECTIF() {
        return OBJECTIF;
    }

    public void setOBJECTIF(String OBJECTIF) {
        this.OBJECTIF = OBJECTIF;
    }

    public String getREALISATION() {
        return REALISATION;
    }

    public void setREALISATION(String REALISATION) {
        this.REALISATION = REALISATION;
    }

    public String getPOURCENTAGE() {
        return POURCENTAGE;
    }

    public void setPOURCENTAGE(String POURCENTAGE) {
        this.POURCENTAGE = POURCENTAGE;
    }

    public String getCHOUFOUNI() {
        return CHOUFOUNI;
    }

    public void setCHOUFOUNI(String CHOUFOUNI) {
        this.CHOUFOUNI = CHOUFOUNI;
    }

    public ArrayList<TbClientVisite> getTbClientVisites() {
        return tbClientVisites;
    }

    public void setTbClientVisites(ArrayList<TbClientVisite> tbClientVisites) {
        this.tbClientVisites = tbClientVisites;
    }

    @Override
    public String toString() {
        return "TbClient{" +
                "CHIFFRE_AFFAIRE='" + CHIFFRE_AFFAIRE + '\'' +
                ", OBJECTIF='" + OBJECTIF + '\'' +
                ", REALISATION='" + REALISATION + '\'' +
                ", POURCENTAGE='" + POURCENTAGE + '\'' +
                ", CHOUFOUNI='" + CHOUFOUNI + '\'' +
                ", tbClientVisites=" + tbClientVisites +
                '}';
    }
}
