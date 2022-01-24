package com.newtech.newtech_sfm.Configuration;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.StockP;
import com.newtech.newtech_sfm.Metier.StockPLigne;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class StockPLigneViewModel extends ViewModel {

    SimpleDateFormat df2 = new SimpleDateFormat("yyMMddHHmmss");
    final String date_stockp = df2.format(new java.util.Date());

    String STOCKP_CODE = "STOCKP"+date_stockp;

    private ArrayList<StockPLigne> stockPLigneArrayList = new ArrayList<>();

    private final MutableLiveData<ArrayList<StockPLigne>> selectedStockPLignes = new MutableLiveData<>();

    public MutableLiveData<ArrayList<StockPLigne>> getSelectedStockPLignes() {
        return selectedStockPLignes;
    }

    public void setSelectedStockPLignes(ArrayList<StockPLigne> stockPLignes){

        selectedStockPLignes.setValue(stockPLignes);
        stockPLigneArrayList = null;
        setStockPLigneArrayList(stockPLignes);
    }

    public ArrayList<StockPLigne> getStockPLigneArrayList() {
        return stockPLigneArrayList;
    }

    public void setStockPLigneArrayList(ArrayList<StockPLigne> stockPLigneArrayList) {
        this.stockPLigneArrayList = stockPLigneArrayList;
    }

    public StockP getStockP(Client client, Context context) throws JSONException {

        StockP stockP;
        stockP = new StockP(client,context,STOCKP_CODE);
        return stockP;
    }
}
