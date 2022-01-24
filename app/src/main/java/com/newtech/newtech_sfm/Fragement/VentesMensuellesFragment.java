package com.newtech.newtech_sfm.Fragement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Activity.VentesMensuellesPopUpActivity;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VentesMensuellesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VentesMensuellesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VentesMensuellesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    VenteMensuelle_Adapter venteMensuelle_adapter;
    List<VenteRow> venteRows = new ArrayList<VenteRow>();
    ListView listView;
    private Context context ;
    private float total;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public VentesMensuellesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VentesMensuellesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VentesMensuellesFragment newInstance(String param1, String param2) {
        VentesMensuellesFragment fragment = new VentesMensuellesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ventes_mensuelles, container, false);

        context = this.getContext();

        listView = (ListView) view.findViewById(R.id.ventes_mensuelles_fragment_listeview);

        View child = getLayoutInflater().inflate(R.layout.no_data_found, null);
        ((ViewGroup)listView.getParent()).addView(child);

        listView.setEmptyView(child);

        venteMensuelle_adapter = new VenteMensuelle_Adapter(getActivity(), venteRows);
        listView.setAdapter(venteMensuelle_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                VenteRow venteRow = (VenteRow) venteMensuelle_adapter.getItem(position);

                Intent intent = new Intent(getActivity(), VentesMensuellesPopUpActivity.class);
                VentesMensuellesPopUpActivity.realisations = venteRow.getDETAILS();
                startActivity(intent);

                //finish();
                //Intent intent=new Intent(getApplicationContext(),ClientActivity.class);
                //intent.putExtra("CLIENT_CODE",client.getCLIENT_CODE());
                //startActivity(intent);
                Log.d("VM", "onItemClick: "+venteRow.toString());
            }
        });

        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Patientez...");
        pDialog.show();

        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_VIEW_VENTE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.hide();

                try {

                    Log.d("Vente", "onResponse: " + response);
                    JSONObject jObj = new JSONObject(response);
                    // textViewObj=jObj.getInt("statut").;
                    int error = jObj.getInt("statut");
                    if (error == 1) {

                        JSONArray ResultatsQ = jObj.getJSONArray("ResultatsQ");

                        for (int i = 0; i < ResultatsQ.length(); i++) {
                            Map<String, String> Realisation = new HashMap<>();
                            JSONObject unResultat = ResultatsQ.getJSONObject(i);
                            JSONArray Details = unResultat.getJSONArray("DESCRIPTION");

                            VenteRow vente = new VenteRow();
                            vente.setDATE_VENTE(unResultat.getString("DATE"));
                            vente.setQUANTITE_VENDU((float) unResultat.getDouble("KVENTE"));

                            for(int j=0; j<Details.length();j++){
                                JSONObject unDetail= Details.getJSONObject(j);
                                Realisation.put(unDetail.getString("ARTICLE_CODE"),unDetail.getString("REALISATION"));
                            }

                            vente.setDETAILS(Realisation);
                            total = total + vente.getQUANTITE_VENDU();
                            venteRows.add(vente);

                        }
                        VenteRow venteVide = new VenteRow();
                        venteVide.setDATE_VENTE("TOTAL (KG)");
                        venteVide.setQUANTITE_VENDU(total);
                        venteRows.add(venteVide);

                        venteMensuelle_adapter.notifyDataSetChanged();
                    } else {
                        String errorMsg = jObj.getString("info");

                        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
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
                pDialog.hide();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("Vente", "onErrorResponse: message " + error.getMessage());
                Log.d("Vente", "onErrorResponse: cause " + error.getCause());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                if (pref.getString("is_login", null).equals("ok")) {

                    Gson gson2 = new Gson();
                    String json2 = pref.getString("User", "");
                    Type type = new TypeToken<JSONObject>() {
                    }.getType();
                    JSONObject user = gson2.fromJson(json2, type);
                    try {
                        params.put("UTILISATEUR_CODE", user.getString("UTILISATEUR_CODE"));
                    } catch (Exception e) {

                    }
                    Log.d("Vente", "getParams: " + params.get("UTILISATEUR_CODE"));

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

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("VENTES MENSUELLES");
    }
}
