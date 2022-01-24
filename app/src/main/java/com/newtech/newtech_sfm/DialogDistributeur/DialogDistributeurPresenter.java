package com.newtech.newtech_sfm.DialogDistributeur;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.newtech.newtech_sfm.Metier.Distributeur;
import com.newtech.newtech_sfm.Metier_Manager.LogSyncManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DialogDistributeurPresenter {

    private static final String TAG = DialogDistributeurPresenter.class.getName();

    private DialogDistributeurView view;

    public DialogDistributeurPresenter(DialogDistributeurView dialogDistributeurView) {
        this.view = dialogDistributeurView;
    }

    public void synchronisationDistributeur(final Context context){

        if(view != null){
            view.showLoading();
        }
        // Tag used to cancel the request
        String tag_string_req = "DISTRIBUTEUR";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_DISTRIBUTEUR_BY_UC, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("client", "onResponse: "+response);
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    ArrayList<Distributeur> distributeurArrayList = new ArrayList<>();
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray distributeurs = jObj.getJSONArray("Distributeurs");
                        if(distributeurs.length()>0) {

                            //Ajout/Suppression/modification des clients dans la base de données.
                            for (int i = 0; i < distributeurs.length(); i++) {
                                JSONObject unDistributeur = distributeurs.getJSONObject(i);
                                distributeurArrayList.add(new Distributeur(unDistributeur));
                            }

                            view.showSuccesDistributeur(distributeurArrayList);
                        }else{
                            view.showEmpty("AUCUN DISTRIBUTEUR TROUVE");
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

                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    //Log.d("UTILISATEUR_CODE TACHE SYNC",gson.toJson(pref.getString("UTILISATEUR_CODE",null)));
                    arrayFinale.put("Params",gson.toJson(TabParams));
                    //Log.d("salim JSON",taches.toString());
                    //Log.d("salim JSON",gson.toJson(taches).toString());

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

    public interface DialogDistributeurView{

        void showSuccesDistributeur(ArrayList<Distributeur> distributeurs);
        void showError(String message);
        void showEmpty(String message);
        void showLoading();

    }
}
