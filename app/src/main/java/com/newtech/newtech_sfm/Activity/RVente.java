package com.newtech.newtech_sfm.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.newtech.newtech_sfm.Configuration.VenteMensuelle_Adapter;
import com.newtech.newtech_sfm.Metier.VenteRow;
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

public class RVente extends AppCompatActivity {

    VenteMensuelle_Adapter vm_adapter;
    List<VenteRow> vente_list = new ArrayList<VenteRow>();
    ListView mListView1;
    private Context context ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vente_kilogramme);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = RVente.this;

        mListView1 = (ListView) findViewById(R.id.vk_listview);

        vm_adapter = new VenteMensuelle_Adapter(this, vente_list);
        mListView1.setAdapter(vm_adapter);

        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_VIEW_VENTE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Log.d("Vente", "onResponse: "+response);
                    JSONObject jObj = new JSONObject(response);
                    // textViewObj=jObj.getInt("statut").;
                    int error = jObj.getInt("statut");
                    if (error==1) {

                        JSONArray ResultatsQ = jObj.getJSONArray("ResultatsQ");

                        for (int i = 0; i < ResultatsQ.length(); i++) {
                            JSONObject unResultat = ResultatsQ.getJSONObject(i);

                            VenteRow vente= new VenteRow();
                            vente.setDATE_VENTE(unResultat.getString("DATE"));
                            vente.setQUANTITE_VENDU((float)unResultat.getDouble("KVENTE"));
                            vente_list.add(vente);

                        }


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
