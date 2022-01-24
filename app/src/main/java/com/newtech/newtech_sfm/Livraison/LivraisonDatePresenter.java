package com.newtech.newtech_sfm.Livraison;

import android.content.Context;
import android.util.Log;

import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier_Manager.ClientManager;

import java.util.ArrayList;

public class LivraisonDatePresenter {


    private static final String TAG = LivraisonDateAdapter.class.getName();
    private LivraisonDatePresenter.LivraisonDateView view;
    private Context context;
    ClientManager clientManager;

    public LivraisonDatePresenter(LivraisonDatePresenter.LivraisonDateView livraisonDateView, Context context) {
        this.view = livraisonDateView;
        this.context = context;
        clientManager = new ClientManager(context);
    }

    void getListClientALivrerByVCD(String vendeur_code, String date_commande){

        Log.d(TAG, "getListClientALivrerByVC: "+vendeur_code);
        Log.d(TAG, "getListClientALivrerByVC: "+date_commande);

        view.showLoading();
        ArrayList<Client> clients = new ArrayList<>();
        clients = clientManager.getListClNLByVCD(vendeur_code,date_commande);
        Log.d(TAG, "getListClientALivrerByVC: "+clients.toString());
        if(clients.size() > 0){
            view.showSuccess(clients);
        }else{
            view.showEmpty("EMPTY");
        }

    }

    void getListClientALivrerByVC(String vendeur_code){

        Log.d(TAG, "getListClientALivrerByVC: "+vendeur_code);

        view.showLoading();
        ArrayList<Client> clients = new ArrayList<>();
        clients = clientManager.getListClNLByVC(vendeur_code);
        Log.d(TAG, "getListClientALivrerByVC: "+clients.toString());
        if(clients.size() > 0){
            view.showSuccess(clients);
        }else{
            view.showEmpty("EMPTY");
        }

    }


      void getListClientALivrerByTCD(String tournee_code, String date_commande){
        view.showLoading();
        ArrayList<Client> clients = new ArrayList<>();
        clients = clientManager.getListClNLByTCD(tournee_code,date_commande);

        if(clients.size() > 0){
            view.showSuccess(clients);
        }else{
            view.showEmpty("EMPTY");
        }

    }

    void getListClientALivrerByTC(String tournee_code){
        view.showLoading();
        ArrayList<Client> clients = new ArrayList<>();
        clients = clientManager.getListClNLByTC(tournee_code);

        if(clients.size() > 0){
            view.showSuccess(clients);
        }else{
            view.showEmpty("EMPTY");
        }

    }


    public interface LivraisonDateView{
        void showSuccess(ArrayList<Client> clients);
        void showError(String message);
        void showEmpty(String message);
        void showLoading();
    }

}
