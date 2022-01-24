package com.newtech.newtech_sfm.RapportFondamental;

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
import com.newtech.newtech_sfm.Metier.RapportFondamental;
import com.newtech.newtech_sfm.Metier_Manager.LogSyncManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RapportFondamentalPresenter {

    private static final String TAG = RapportFondamentalPresenter.class.getName();

    private RapportFondamentalView view;

    public RapportFondamentalPresenter(RapportFondamentalView rapportFondamentalView) {
        this.view = rapportFondamentalView;
    }

    public void getRapportFondamental(final Context context){

        if(view != null){
            view.showLoading();
        }
        // Tag used to cancel the request
        String tag_string_req = "RAPPORT FONDAMENTAL";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_RAPPORT_FONDAMENTAL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Rapport fondamental", "onResponse: "+response);

                LogSyncManager logM= new LogSyncManager(context);
                try {
                        ArrayList<RapportFondamental> rapportFondamentals = new ArrayList<>();

                        JSONObject jObj = new JSONObject(response);
                        int error = jObj.getInt("statut");
                        if (error==1) {

                            JSONArray rapportFondamental = jObj.getJSONArray("RapportFondamental");

                            if(rapportFondamental.length()>0) {

                                for (int i = 0; i < rapportFondamental.length(); i++) {
                                    JSONObject unRapportFondamental= rapportFondamental.getJSONObject(i);
                                    rapportFondamentals.add(new RapportFondamental(unRapportFondamental));
                                }

                            }

                            view.showSuccess(rapportFondamentals);

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
                view.showError(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                HashMap<String,String> arrayFinale= new HashMap<>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",pref.getString("UTILISATEUR_CODE",null));

                    //TabParams.put("UTILISATEUR_CODE","BMVD0037");

                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();


                    Log.d(TAG, "getParams: "+pref.getString("UTILISATEUR_CODE",null));
                    arrayFinale.put("Params",gson.toJson(TabParams));

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

    public interface RapportFondamentalView{
        void showSuccess(ArrayList<RapportFondamental> rapportFondamentals);
        void showError(String message);
        void showEmpty(String message);
        void showLoading();
    }
}
