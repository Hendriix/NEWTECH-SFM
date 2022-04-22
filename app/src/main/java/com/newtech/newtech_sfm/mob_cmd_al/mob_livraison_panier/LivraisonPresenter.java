package com.newtech.newtech_sfm.mob_cmd_al.mob_livraison_panier;

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
import com.newtech.newtech_sfm.Metier.Livraison;
import com.newtech.newtech_sfm.Metier.LivraisonGratuite;
import com.newtech.newtech_sfm.Metier.LivraisonLigne;
import com.newtech.newtech_sfm.Metier.LivraisonPromotion;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LivraisonPresenter {

    private static final String TAG = LivraisonPresenter.class.getName();
    private LivraisonPresenter.LivraisonView view;

    public LivraisonPresenter(LivraisonPresenter.LivraisonView livraisonView) {
        this.view = livraisonView;
    }

    public void syncLivraison(final Livraison livraison,
                              final ArrayList<LivraisonLigne> livraisonLignes,
                              final ArrayList<LivraisonGratuite> livraisonGratuites,
                              final ArrayList<LivraisonPromotion> livraisonPromotions) {

        if (view != null) {
            view.ShowLoading();
        }

        String tag_string_req = "LIVRAISON";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_LIVRAISON, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Livraison ", "" + response);
                int cptInsert = 0, cptDelete = 0;

                view.HideLoading();
                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error == 1) {

                        String message = jObj.getString("info");
                        view.ShowSuccess(message);

                        //logM.add("Livraison:OK Insert:"+cptInsert +"Delet:"+cptDelete ,"SyncLivraison");
                    } else {

                        String errorMsg = jObj.getString("info");
                        view.ShowSuccess(errorMsg);
                        //Toast.makeText(context, "livraison : "+errorMsg, Toast.LENGTH_LONG).show();


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    view.HideLoading();
                    //Toast.makeText(context, "livraison : "+"Json error: " +"erreur applcation livraison" + e.getMessage(), Toast.LENGTH_LONG).show();
                    view.ShowError(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                view.HideLoading();
                //Toast.makeText(context, "livraison : "+error.getMessage(), Toast.LENGTH_LONG).show();
                //view.ShowError(error.getMessage());
                Log.d(TAG, "onErrorResponse: " + error.getMessage().toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String, String> arrayFinale = new HashMap<>();
                HashMap<String, String> TabParams = new HashMap<>();
                TabParams.put("Table_Name", "tableLivraison");
                final GsonBuilder builder = new GsonBuilder();
                final Gson gson = builder.create();
                arrayFinale.put("Params", gson.toJson(TabParams));
                arrayFinale.put("Livraison", gson.toJson(livraison));
                arrayFinale.put("LivraisonLignes", gson.toJson(livraisonLignes));
                arrayFinale.put("LivraisonGratuites", gson.toJson(livraisonGratuites));
                arrayFinale.put("LivraisonPromotions", gson.toJson(livraisonPromotions));

                Log.d(TAG, "getParams: " + arrayFinale.toString());

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

    public interface LivraisonView {

        void ShowSuccess(String successMessage);

        void ShowError(String successMessage);

        void ShowLoading();

        void HideLoading();
    }
}
