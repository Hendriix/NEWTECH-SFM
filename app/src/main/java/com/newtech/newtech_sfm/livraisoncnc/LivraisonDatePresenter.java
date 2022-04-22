package com.newtech.newtech_sfm.livraisoncnc;

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
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Configuration.AppConfig;
import com.newtech.newtech_sfm.Configuration.AppController;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier_Manager.ClientManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LivraisonDatePresenter {


    private static final String TAG = LivraisonDateAdapter.class.getName();
    private LivraisonDateView view;
    private Context context;
    ClientManager clientManager;

    public LivraisonDatePresenter(LivraisonDateView livraisonDateView, Context context) {
        this.view = livraisonDateView;
        this.context = context;
        clientManager = new ClientManager(context);
    }

    public void synchronisationClientNcAl(String date_commande, String affectation_type,String affectation_valeur) {

        if (view != null) {
            view.showLoading();
        }
        // Tag used to cancel the request
        String tag_string_req = "CLIENT";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_CLIENT_NON_CLOTURES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response);
                try {
                    ArrayList<Client> clientArrayList = new ArrayList<>();
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error == 1) {
                        JSONArray clients = jObj.getJSONArray("Clients");
                        if (clients.length() > 0) {

                            //Ajout/Suppression/modification des clients dans la base de données.
                            for (int i = 0; i < clients.length(); i++) {
                                JSONObject unClient = clients.getJSONObject(i);
                                clientArrayList.add(new Client(unClient));
                            }
                            view.hideLoading();
                            view.showSuccess(clientArrayList);
                        } else {
                            view.showEmpty("AUCUN CLIENT TROUVE");
                        }

                    } else {
                        view.hideLoading();
                        try{
                            String errorMsg = jObj.getString("info");
                            view.showError(errorMsg);
                        }catch(NullPointerException e){
                            view.showError("Une erreur est survenu, merci de contacter votre administrateur");
                        }


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    view.hideLoading();
                    view.showError("Une erreur est survenu, merci de contacter votre adiminstrateur");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                view.hideLoading();
                view.showError(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                HashMap<String, String> arrayFinale = new HashMap<>();
                HashMap<String, String> TabParams = new HashMap<>();

                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if (pref.getString("is_login", null) != null) {
                    if (pref.getString("is_login", null).equals("ok")) {

                        Gson gson2 = new Gson();
                        String json2 = pref.getString("User", "");
                        Type type = new TypeToken<JSONObject>() {
                        }.getType();
                        JSONObject user = gson2.fromJson(json2, type);

                        try {
                            TabParams.put("UTILISATEUR_CODE", user.getString("UTILISATEUR_CODE"));
                            TabParams.put("DATE_COMMANDE", date_commande);
                            TabParams.put("AFFECTATION_TYPE", affectation_type);
                            TabParams.put("AFFECTATION_VALEUR", affectation_valeur);

                            final GsonBuilder builder = new GsonBuilder();
                            final Gson gson = builder.create();

                            arrayFinale.put("Params", gson.toJson(TabParams));
                        } catch (Exception e) {

                        }

                    }
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


    public interface LivraisonDateView {
        void showSuccess(ArrayList<Client> clients);

        void showError(String message);

        void showEmpty(String message);

        void showLoading();

        void hideLoading();
    }

}
