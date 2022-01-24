package com.newtech.newtech_sfm.recensement;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.newtech.newtech_sfm.Configuration.AppConfig;
import com.newtech.newtech_sfm.Configuration.AppController;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier_Manager.LogSyncManager;
import com.newtech.newtech_sfm.Service.Gpstrackerservice;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecensementPresenter{

    private static final String TAG = RecensementPresenter.class.getName();

    private RecensementView view;

    public RecensementPresenter(RecensementView recensementView) {
        this.view = recensementView;
    }

    public void synchronisationClient(final Context context, double latitude, double longitude, int distance){

        if(view != null){
            view.showLoading();
        }
        // Tag used to cancel the request
        String tag_string_req = "CLIENT";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_CLIENT_RECENSEMENT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("client", "onResponse: "+response);
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    ArrayList<Client> clientArrayList = new ArrayList<>();
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray clients = jObj.getJSONArray("Clients");
                        if(clients.length()>0) {

                            //Ajout/Suppression/modification des clients dans la base de données.
                            for (int i = 0; i < clients.length(); i++) {
                                JSONObject unClient = clients.getJSONObject(i);
                                clientArrayList.add(new Client(unClient));
                            }

                            view.showSuccess(clientArrayList);
                        }else{
                            view.showEmpty("AUCUN CLIENT TROUVE");
                        }



                        //logM.add("CLIENTS : OK Inserted "+cptInsert +"Deleted "+cptDeleted ,"SyncClients");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        view.showError(errorMsg);
                        //Toast.makeText(context, "CLIENTS :"+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("CLIENTS : NOK Inserted error"+errorMsg ,"SyncClients");

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    view.showError(e.getMessage());
                    //Toast.makeText(context, "CLIENTS :"+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("CLIENTS : NOK Inserted JsonErr"+e.getMessage() ,"SyncClients");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                view.showError(error.getMessage().toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                HashMap<String,String> arrayFinale= new HashMap<>();
                HashMap<String,String > TabParams =new HashMap<>();
                Map<String, String> params = new HashMap<String, String>();
                try {

                    TabParams.put("GPS_LATITUDE",String.valueOf(latitude));
                    TabParams.put("GPS_LONGITUDE",String.valueOf(longitude));
                    TabParams.put("DISTANCE",String.valueOf(distance));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    arrayFinale.put("Params",gson.toJson(TabParams));

                }catch (Exception e ){

                }
                return arrayFinale;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    public void synchronisationClient(final Context context, double latitude, double longitude){

        if(view != null){
            view.showLoading();
        }
        // Tag used to cancel the request
        String tag_string_req = "CLIENT";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_CLIENT_RECENSEMENT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("client", "onResponse: "+response);
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    ArrayList<Client> clientArrayList = new ArrayList<>();
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray clients = jObj.getJSONArray("Clients");
                        if(clients.length()>0) {

                            //Ajout/Suppression/modification des clients dans la base de données.
                            for (int i = 0; i < clients.length(); i++) {
                                JSONObject unClient = clients.getJSONObject(i);
                                clientArrayList.add(new Client(unClient));
                            }

                            view.showSuccess(clientArrayList);
                        }else{
                            view.showEmpty("AUCUN CLIENT TROUVE");
                        }



                        //logM.add("CLIENTS : OK Inserted "+cptInsert +"Deleted "+cptDeleted ,"SyncClients");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        view.showError(errorMsg);
                        //Toast.makeText(context, "CLIENTS :"+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("CLIENTS : NOK Inserted error"+errorMsg ,"SyncClients");

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    view.showError(e.getMessage());
                    //Toast.makeText(context, "CLIENTS :"+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("CLIENTS : NOK Inserted JsonErr"+e.getMessage() ,"SyncClients");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                view.showError(error.getMessage().toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                HashMap<String,String> arrayFinale= new HashMap<>();
                HashMap<String,String > TabParams =new HashMap<>();
                Map<String, String> params = new HashMap<String, String>();
                try {

                    TabParams.put("GPS_LATITUDE",String.valueOf(latitude));
                    TabParams.put("GPS_LONGITUDE",String.valueOf(longitude));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    arrayFinale.put("Params",gson.toJson(TabParams));

                    Log.d(TAG, "getParams: lat "+Gpstrackerservice.latitude);
                    Log.d(TAG, "getParams: long "+Gpstrackerservice.longitude);

                }catch (Exception e ){

                }
                return arrayFinale;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    public void synchronisationClient(final Context context, int qr_code){

        if(view != null){
            view.showLoading();
        }
        // Tag used to cancel the request
        String tag_string_req = "CLIENT";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_CLIENT_BY_QR, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("client", "onResponse: "+response);
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    ArrayList<Client> clientArrayList = new ArrayList<>();
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray clients = jObj.getJSONArray("Clients");
                        if(clients.length()>0) {

                            //Ajout/Suppression/modification des clients dans la base de données.
                            for (int i = 0; i < clients.length(); i++) {
                                JSONObject unClient = clients.getJSONObject(i);
                                clientArrayList.add(new Client(unClient));
                            }

                            view.showSuccess(clientArrayList);
                        }else{
                            view.showEmpty("AUCUN CLIENT TROUVE");
                        }



                        //logM.add("CLIENTS : OK Inserted "+cptInsert +"Deleted "+cptDeleted ,"SyncClients");
                    }else {
                        String errorMsg = jObj.getString("error_msg");
                        view.showError(errorMsg);
                        //Toast.makeText(context, "CLIENTS :"+errorMsg, Toast.LENGTH_LONG).show();
                        //logM.add("CLIENTS : NOK Inserted error"+errorMsg ,"SyncClients");

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    view.showError(e.getMessage());
                    //Toast.makeText(context, "CLIENTS :"+"Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();
                    //logM.add("CLIENTS : NOK Inserted JsonErr"+e.getMessage() ,"SyncClients");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                view.showError(error.getMessage().toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                HashMap<String,String> arrayFinale= new HashMap<>();
                HashMap<String,String > TabParams =new HashMap<>();
                Map<String, String> params = new HashMap<String, String>();
                try {

                    TabParams.put("QR_CODE",String.valueOf(qr_code));

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    arrayFinale.put("Params",gson.toJson(TabParams));

                }catch (Exception e ){

                }
                return arrayFinale;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public interface RecensementView{
        void showSuccess(ArrayList<Client> clients);
        void showError(String message);
        void showEmpty(String message);
        void showLoading();
    }
}
