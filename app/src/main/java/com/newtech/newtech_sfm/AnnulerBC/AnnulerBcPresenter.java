package com.newtech.newtech_sfm.AnnulerBC;

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
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier_Manager.LogSyncManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnnulerBcPresenter {

    private static final String TAG = AnnulerBcPresenter.class.getName();

    private AnnulerBcView view;

    public AnnulerBcPresenter(AnnulerBcView recensementView) {
        this.view = recensementView;
    }

    public void synchronisationCommandeAAnnuler(final Context context, String client_code, String utilisateur_code, String commande_code) {

        /*if(view != null){
            view.showLoading();
        }*/

        String tag_string_req = "COMMANDE_A_ANNULER";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_CANCEL_COMMANDE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ouvrir le logManager
                Log.d("CommandeAAnnuler ", "" + response);
                LogSyncManager logM = new LogSyncManager(context);
                try {
                    ArrayList<Commande> commandeArrayList = new ArrayList<>();
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    String message = jObj.getString("Resultat");
                    int code = jObj.getInt("code");
                    if (error == 1) {
                        view.showSuccess(message,code);
                        //logM.add("CommandeNonCloturee:OK Insert:"+cptInsert +"Deleted:"+cptDeleted ,"SyncuneCommandeNonCloturee");
                    } else {
                        String errorMsg = jObj.getString("info");
                        view.showError(errorMsg);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    view.showError(e.getMessage());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.showError("Une erreur est survenue merci de réessayer");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> arrayFinale = new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if (pref.getString("is_login", null).equals("ok")) {

                    HashMap<String, String> TabParams = new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE", utilisateur_code);
                    TabParams.put("CLIENT_CODE", client_code);
                    TabParams.put("COMMANDE_CODE", commande_code);

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();

                    arrayFinale.put("Params", gson.toJson(TabParams));
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

    public interface AnnulerBcView{
        void showSuccess(String message, int code);
        void showError(String message);
        void showLoading();
    }
}
