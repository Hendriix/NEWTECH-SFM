package com.newtech.newtech_sfm.TableauBordClient;

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
import com.newtech.newtech_sfm.Metier.TbClient;
import com.newtech.newtech_sfm.Metier_Manager.LogSyncManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TableauBordClientPresenter {

    private static final String TAG = TableauBordClientPresenter.class.getName();

    private TableauBordClientView view;

    public TableauBordClientPresenter(TableauBordClientView tableauBordClientView) {
        this.view = tableauBordClientView;
    }

    public void getTableauBordClient(final Context context, String client_code) {

        if(view != null){
            view.showLoading();
        }

        String tag_string_req = "TABLEAU_BORD_CLIENT";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_TABLEAU_BORD_CLIENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ouvrir le logManager
                Log.d("TableauBordClient ", "" + response);
                LogSyncManager logM = new LogSyncManager(context);
                try {
                    ArrayList<TbClient> tbClientArrayList = new ArrayList<>();
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error == 1) {
                        JSONObject tbclients = jObj.getJSONObject("Resultats");
                        //JSONObject firstObject = jObj.JSONObject("Resultats");
                        //Toast.makeText(context, "nombre de Classes  "+Classes.length()  , Toast.LENGTH_SHORT).show();
                        //JSONObject tbclientsJSONObject = tbclients.getJSONObject(0);

                        if(tbclients.length() > 0){
                            TbClient tbClient = new TbClient(tbclients);
                            view.showSuccess(tbClient);
                        }else{

                            view.showEmpty("Aucune information trouvée");
                        }

                        //Log.d(TAG, "onResponse: "+tbClient);
                        //view.showSuccess(tbClient);


                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Log.d(TAG, "onResponse: "+errorMsg);
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
                view.showError("Un erreur est survenue merci de réessayer");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> arrayFinale = new HashMap<>();
                HashMap<String, String> TabParams = new HashMap<>();
                TabParams.put("CLIENT_CODE", client_code);
                //TabParams.put("CLIENT_CODE", "TACD006328");
                final GsonBuilder builder = new GsonBuilder();
                final Gson gson = builder.create();
                arrayFinale.put("Params", gson.toJson(TabParams));
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

    public interface TableauBordClientView{
        void showSuccess(TbClient tbClient);
        void showError(String message);
        void showEmpty(String message);
        void showLoading();
    }
}
