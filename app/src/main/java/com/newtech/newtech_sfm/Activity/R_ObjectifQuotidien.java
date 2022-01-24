package com.newtech.newtech_sfm.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Configuration.AppConfig;
import com.newtech.newtech_sfm.Configuration.AppController;
import com.newtech.newtech_sfm.Configuration.ObjectifQuotidien_Adapter;
import com.newtech.newtech_sfm.Metier.ObjectifRow;
import com.newtech.newtech_sfm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TONPC on 28/03/2017.
 */

public class R_ObjectifQuotidien extends AppCompatActivity{
    ListView listView;
    ObjectifQuotidien_Adapter objadapter;
    private List<ObjectifRow> objectiflist = new ArrayList<ObjectifRow>();
    private Context context ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_objectif_quotidien);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = R_ObjectifQuotidien.this;

        listView = (ListView) findViewById(R.id.objectif_quotidien);

        objadapter = new ObjectifQuotidien_Adapter(this, objectiflist);
        listView.setAdapter(objadapter);

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();


        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_VIEW_OBJECTIF, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.hide();
                // textViewObj.setText(response);
                //ouvrir le logManager

                try {

                    Log.d("OBJECTIF", "onResponse: "+response);

                    JSONObject jObj = new JSONObject(response);
                    // textViewObj=jObj.getInt("statut").;
                    int error = jObj.getInt("statut");
                    if (error==1) {

                        JSONArray Resultats = jObj.getJSONArray("Resultats");

                        for (int i = 0; i < Resultats.length(); i++) {
                            JSONObject unResultat = Resultats.getJSONObject(i);

                            ObjectifRow objectif = new ObjectifRow();
                            objectif.setFAMILLE_NOM(unResultat.getString("FAMILLE_NOM"));
                            objectif.setObjectif(((Double) unResultat.getDouble("OBJECTIF")).doubleValue());
                            objectif.setRealisation(((Double) unResultat.getDouble("REALISATION")).doubleValue());
                            objectif.setPercent(((Double) unResultat.getDouble("PERCENT")).doubleValue());



                            // adding movie to movies array
                            objectiflist.add(objectif);



                            // unArticle.getString("UTILISATEUR_CODE");
                            // textViewObj.setText(jObj.getString("info"));

                        }
                        objadapter.notifyDataSetChanged();

                    }else {
                        String errorMsg = jObj.getString("info");

                        Toast.makeText(context,errorMsg, Toast.LENGTH_LONG).show();
                        //textViewObj.setText(errorMsg+"info");
                    }

                } catch (JSONException e) {
                    //   log.add("ARTICLES : NOK Insert "+e.getMessage() ,"SyncArticles");
                    e.printStackTrace();
                    //  textViewObj.setText(response + "exception " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();




                pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if( pref.getString("is_login", null).equals("ok")) {

                    Gson gson2 = new Gson();
                    String json2 = pref.getString("User", "");
                    Type type = new TypeToken<JSONObject>() {}.getType();
                    JSONObject user = gson2.fromJson(json2, type);
                    try {
                        params.put("UTILISATEUR_CODE", user.getString("UTILISATEUR_CODE"));
                    }catch (Exception e ){

                    }

                }
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
