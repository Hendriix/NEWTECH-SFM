package com.newtech.newtech_sfm.Configuration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.newtech.newtech_sfm.Metier.StockDemande;
import com.newtech.newtech_sfm.Metier_Manager.StockDemandeManager;

import java.util.ArrayList;

public class StockDemandeViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<StockDemande>> listMutableLiveData;
    private StockDemandeManager stockDemandeManager;

    public StockDemandeViewModel(@NonNull Application application) {
        super(application);
        stockDemandeManager = new StockDemandeManager(application);
    }



    public LiveData<ArrayList<StockDemande>> getListStockDemande(String commande_source){

        listMutableLiveData = new MutableLiveData<>();
        listMutableLiveData.setValue(stockDemandeManager.getListByTypeDemande(commande_source));
        return listMutableLiveData;

    }

}
