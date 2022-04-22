package com.newtech.newtech_sfm.Configuration;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier.LivraisonLigne;

import java.util.ArrayList;
import java.util.List;

public class ListDataSave {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public ListDataSave(Context mContext, String preferenceName) {
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setDataList(String tag, List<Client> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        // convert json data, then save
        String strJson = gson.toJson(datalist);
        //editor.clear();
        editor.putString(tag, strJson);
        editor.commit();

    }

    public List<Client> getDataList(String tag) {
        List<Client> datalist = new ArrayList<Client>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<Client>>() {
        }.getType());
        return datalist;

    }

    public void setDataBoolean(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public Boolean getDataBoolean(String key) {

        Boolean valeur;
        valeur = preferences.getBoolean(key, false);
        return valeur;
    }

    public void setDataString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getDataString(String key) {

        String valeur;
        valeur = preferences.getString(key, "");
        return valeur;
    }

    public void remove(String tag) {
        editor.remove(tag);
        editor.apply();
    }

    /*COMMANDE LIGNE*/

    public void setCommandeLigneList(String tag, ArrayList<LivraisonLigne> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        // convert json data, then save
        String strJson = gson.toJson(datalist);
        //editor.clear();
        editor.putString(tag, strJson);
        editor.commit();

    }

    public ArrayList<CommandeLigne> getCommandeLigneList(String tag) {

        ArrayList<CommandeLigne> datalist = new ArrayList<>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<ArrayList<CommandeLigne>>() {
        }.getType());
        return datalist;

    }

    /*LIVRAISON LIGNE*/

    public void setLivraisonLigneList(String tag, ArrayList<LivraisonLigne> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        // convert json data, then save
        String strJson = gson.toJson(datalist);
        //editor.clear();
        editor.putString(tag, strJson);
        editor.commit();

    }

    public ArrayList<LivraisonLigne> getLivraisonLigneList(String tag) {

        ArrayList<LivraisonLigne> datalist = new ArrayList<>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<ArrayList<LivraisonLigne>>() {
        }.getType());
        return datalist;

    }


}
