package com.newtech.newtech_sfm.AnnulationCommande;

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
import com.newtech.newtech_sfm.Metier_Manager.CommandeManager;
import com.newtech.newtech_sfm.Metier_Manager.LogSyncManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AnnulerCommandePresenter {

    private static final String TAG = AnnulerCommandePresenter.class.getName();

    private AnnulerCommandeView view;

    public AnnulerCommandePresenter(AnnulerCommandeView recensementView) {
        this.view = recensementView;
    }

    public ArrayList<Commande> getCommandeAAnnuler(Context context, String client_code){
        CommandeManager commandeManager = new CommandeManager(context);
        ArrayList<Commande> commandeArrayList = new ArrayList<>();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date_commande=df.format(Calendar.getInstance().getTime());

        if(view != null){
            view.showLoading();
        }

        commandeArrayList = commandeManager.getListByCC_CD(client_code,date_commande);

        if(commandeArrayList.size() > 0){
            view.showSuccess(commandeArrayList);
        }else{
            view.showEmpty("AUCUNE COMMANDE A ANNULER");
        }

        return commandeArrayList;
    }

    public void synchronisationCommandeAAnnuler(final Context context, String client_code) {

        String tag_string_req = "COMMANDE_NON_CLOTUREE";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SYNC_COMMANDEAANNULER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ouvrir le logManager
                Log.d("CommandeNonCloturee ", "" + response);
                LogSyncManager logM = new LogSyncManager(context);
                try {
                    ArrayList<Commande> commandeArrayList = new ArrayList<>();
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("statut");
                    if (error == 1) {
                        JSONArray commandes = jObj.getJSONArray("Commandes");
                        if(commandes.length()>0) {

                            //Ajout/Suppression/modification des clients dans la base de données.
                            for (int i = 0; i < commandes.length(); i++) {
                                JSONObject uneCommande = commandes.getJSONObject(i);
                                commandeArrayList.add(new Commande(uneCommande));
                            }

                            view.showSuccess(commandeArrayList);
                        }else{
                            view.showEmpty("AUCUNE COMMANDE TROUVEE");
                        }

                        //logM.add("CommandeNonCloturee:OK Insert:"+cptInsert +"Deleted:"+cptDeleted ,"SyncuneCommandeNonCloturee");
                    } else {
                        String errorMsg = jObj.getString("error_msg");
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
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if (pref.getString("is_login", null).equals("ok")) {

                    HashMap<String, String> TabParams = new HashMap<>();
                    TabParams.put("UTILISATEUR_CODE", pref.getString("UTILISATEUR_CODE", null));
                    TabParams.put("CLIENT_CODE", client_code);

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

    public interface AnnulerCommandeView{
        void showSuccess(ArrayList<Commande> commandes);
        void showError(String message);
        void showEmpty(String message);
        void showLoading();
    }
}
