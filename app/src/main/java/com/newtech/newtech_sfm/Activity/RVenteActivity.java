package com.newtech.newtech_sfm.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.DefaultRetryPolicy;
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

public class RVenteActivity extends AppCompatActivity{

    VenteMensuelle_Adapter vm_adapter;
    List<VenteRow> vente_list = new ArrayList<VenteRow>();
    ListView mListView1;
    private Context context ;
    private float total;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vente_kilogramme);
        setTitle("VENTES MENSUELLES");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = RVenteActivity.this;

        mListView1 = (ListView) findViewById(R.id.vk_listview);

        vm_adapter = new VenteMensuelle_Adapter(this, vente_list);
        mListView1.setAdapter(vm_adapter);

        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VenteRow venteRow =(VenteRow)vm_adapter.getItem(position);
                new AlertDialog.Builder(RVenteActivity.this)
                        .setTitle("Quantité par article")
                        .setMessage(venteRow.getDESCRIPTION())
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
                //Intent intent=new Intent(getApplicationContext(),ClientActivity.class);
                //intent.putExtra("CLIENT_CODE",client.getCLIENT_CODE());
                //startActivity(intent);
            }
        });

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Patientez...");
        pDialog.show();

        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_VIEW_VENTE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.hide();

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
                            vente.setDESCRIPTION(unResultat.getString("DESCRIPTION"));
                            total=total+ vente.getQUANTITE_VENDU();
                            vente_list.add(vente);

                        }
                        VenteRow venteVide= new VenteRow();
                        venteVide.setDATE_VENTE("TOTAL (KG)");
                        venteVide.setQUANTITE_VENDU(total);
                        vente_list.add(venteVide);

                        vm_adapter.notifyDataSetChanged();
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
                Log.d("Vente", "onErrorResponse: message "+error.getMessage());
                Log.d("Vente", "onErrorResponse: cause "+error.getCause());

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
                    Log.d("Vente", "getParams: "+params.get("UTILISATEUR_CODE"));

                }
                return params;
            }
        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
