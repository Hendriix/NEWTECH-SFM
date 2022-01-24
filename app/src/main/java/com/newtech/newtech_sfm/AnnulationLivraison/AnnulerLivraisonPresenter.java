package com.newtech.newtech_sfm.AnnulationLivraison;

import android.content.Context;

import com.newtech.newtech_sfm.Metier.Livraison;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AnnulerLivraisonPresenter {

    private static final String TAG = AnnulerLivraisonPresenter.class.getName();

    private AnnulerLivraisonView view;

    public AnnulerLivraisonPresenter(AnnulerLivraisonView livraisonView) {
        this.view = livraisonView;
    }

    public ArrayList<Livraison> getLivraisonAAnnuler(Context context, String client_code){
        LivraisonManager livraisonManager = new LivraisonManager(context);
        ArrayList<Livraison> livraisonArrayList = new ArrayList<>();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date_commande=df.format(Calendar.getInstance().getTime());

        if(view != null){
            view.showLoading();
        }

        livraisonArrayList = livraisonManager.getListByCC_CD(client_code);

        if(livraisonArrayList.size() > 0){
            view.showSuccess(livraisonArrayList);
        }else{
            view.showEmpty("AUCUNE LIVRAISON A ANNULER");
        }

        return livraisonArrayList;
    }

    public interface AnnulerLivraisonView{
        void showSuccess(ArrayList<Livraison> livraisons);
        void showError(String message);
        void showEmpty(String message);
        void showLoading();
    }
}
