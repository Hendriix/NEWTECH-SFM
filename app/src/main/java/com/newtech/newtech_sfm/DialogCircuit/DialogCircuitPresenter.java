package com.newtech.newtech_sfm.DialogCircuit;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.newtech.newtech_sfm.Configuration.AppConfig;
import com.newtech.newtech_sfm.Configuration.AppController;
import com.newtech.newtech_sfm.Metier.Circuit;
import com.newtech.newtech_sfm.Metier_Manager.LogSyncManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DialogCircuitPresenter {

    private static final String TAG = DialogCircuitPresenter.class.getName();

    private DialogCircuitPresenter.DialogCircuitView view;

    public DialogCircuitPresenter(DialogCircuitPresenter.DialogCircuitView dialogCircuitView) {
        this.view = dialogCircuitView;
    }

    public void synchronisationCircuit(final Context context){

        if(view != null){
            view.showLoading();
        }
        // Tag used to cancel the request
        String tag_string_req = "CLIENT";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_CIRCUIT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("client", "onResponse: "+response);
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    ArrayList<Circuit> circuitArrayList = new ArrayList<>();
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray circuits = jObj.getJSONArray("Circuits");
                        if(circuits.length()>0) {

                            //Ajout/Suppression/modification des clients dans la base de données.
                            for (int i = 0; i < circuits.length(); i++) {
                                JSONObject unCircuit = circuits.getJSONObject(i);
                                circuitArrayList.add(new Circuit(unCircuit));
                            }

                            view.showSuccesCircuit(circuitArrayList);
                        }else{
                            view.showEmpty("AUCUN CIRCUIT TROUVE");
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

                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    public interface DialogCircuitView{

        void showSuccesCircuit(ArrayList<Circuit> circuits);
        void showError(String message);
        void showEmpty(String message);
        void showLoading();

    }
}
