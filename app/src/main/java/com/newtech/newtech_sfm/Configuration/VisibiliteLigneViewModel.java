package com.newtech.newtech_sfm.Configuration;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.Visibilite;
import com.newtech.newtech_sfm.Metier.VisibiliteLigne;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class VisibiliteLigneViewModel extends ViewModel {

    //private LiveData<ArrayList<VisibiliteLigne>> visibiliteLignes;

    SimpleDateFormat df2 = new SimpleDateFormat("yyMMddHHmmss");
    final String date_visibilite = df2.format(new java.util.Date());

    String VISIBILITE_CODE = "VISREF"+date_visibilite;

    private ArrayList<VisibiliteLigne> visibiliteLigneArrayList = new ArrayList<>();

    private final MutableLiveData<ArrayList<VisibiliteLigne>> selectedVisibiliteLignes = new MutableLiveData<>();


    public MutableLiveData<ArrayList<VisibiliteLigne>> getSelectedArticle() {
        return selectedVisibiliteLignes;
    }

    public void setSelectedVisibiliteLignes(ArrayList<VisibiliteLigne> visibiliteLignes){
        selectedVisibiliteLignes.setValue(visibiliteLignes);
        visibiliteLigneArrayList = null;
        visibiliteLigneArrayList = visibiliteLignes;
    }

    public ArrayList<VisibiliteLigne> getVisibiliteLignes() {
        return visibiliteLigneArrayList;
    }

    /*public ArrayList<VisibiliteLigne> getVisibiliteLignes() {
            return visibiliteLignes;
        }

        public void setVisibiliteLignes(ArrayList<VisibiliteLigne> visibiliteLigneArrayList){
            this.visibiliteLignes = visibiliteLigneArrayList;
        }*/
    public void loadArticles() {
        // fetch articles here asynchronously
    }

    public ArrayList<VisibiliteLigne> getVisibiliteLignes(int visibilite,int referencement, ArrayList<Article> articles){

        ArrayList<VisibiliteLigne> visibiliteLignes = new ArrayList<>();

        for(int i=0; i<articles.size();i++){
            String ARTICLE_CODE = articles.get(i).getARTICLE_CODE();
            String FAMILLE_CODE= articles.get(i).getFAMILLE_CODE();
            VisibiliteLigne visibiliteLigne = new VisibiliteLigne(VISIBILITE_CODE,ARTICLE_CODE,FAMILLE_CODE
                    ,i,visibilite ,referencement);
            visibiliteLignes.add(visibiliteLigne);
        }
        return visibiliteLignes;


    }

    public Visibilite getVisibilite(Client client, Context context, String VISITE_CODE){

        Visibilite visibilite;
        visibilite = new Visibilite(client,context,VISITE_CODE,VISIBILITE_CODE);

        return visibilite;
    }


}
