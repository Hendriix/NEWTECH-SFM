package com.newtech.newtech_sfm.DialogTournee;

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
import com.newtech.newtech_sfm.Metier.Tournee;
import com.newtech.newtech_sfm.Metier_Manager.LogSyncManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DialogTourneePresenter {

    private static final String TAG = DialogTourneePresenter.class.getName();

    private DialogTourneeView view;

    public DialogTourneePresenter(DialogTourneeView dialogTourneeView) {
        this.view = dialogTourneeView;
    }

    public void synchronisationTournee(final Context context, final String utilisateur_code){

        if(view != null){
            view.showLoading();
        }
        // Tag used to cancel the request
        String tag_string_req = "TOURNEE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_TOURNEE_BY_UC, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("tournees", "onResponse: "+response);
                LogSyncManager logM= new LogSyncManager(context);
                try {
                    ArrayList<Tournee> tourneeArrayList = new ArrayList<>();
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error==1) {
                        JSONArray tournees = jObj.getJSONArray("Tournees");
                        if(tournees.length()>0) {

                            //Ajout/Suppression/modification des clients dans la base de données.
                            for (int i = 0; i < tournees.length(); i++) {
                                JSONObject uneTournee = tournees.getJSONObject(i);
                                tourneeArrayList.add(new Tournee(uneTournee));
                            }

                            view.showSuccesTournee(tourneeArrayList);
                        }else{
                            view.showEmpty("AUCUNE TOURNEE TROUVEE");
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


                    HashMap<String,String > TabParams =new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE",utilisateur_code);
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    arrayFinale.put("Params",gson.toJson(TabParams));
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

    public interface DialogTourneeView{

        void showSuccesTournee(ArrayList<Tournee> tournees);
        void showError(String message);
        void showEmpty(String message);
        void showLoading();

    }
}
