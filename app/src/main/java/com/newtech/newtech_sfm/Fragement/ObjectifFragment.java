package com.newtech.newtech_sfm.Fragement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Configuration.AppConfig;
import com.newtech.newtech_sfm.Configuration.AppController;
import com.newtech.newtech_sfm.Configuration.ObjectifListAdapter;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ObjectifFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ObjectifFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObjectifFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView listView;
    ObjectifListAdapter objadapter;
    private List<ObjectifRow> objectiflist = new ArrayList<ObjectifRow>();
    private Context context ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ObjectifFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Objectif.
     */
    // TODO: Rename and change types and number of parameters
    public static ObjectifFragment newInstance(String param1, String param2) {
        ObjectifFragment fragment = new ObjectifFragment();
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

        View view = inflater.inflate(R.layout.fragment_objectif, container, false);

        listView = (ListView) view.findViewById(R.id.objectif_fragment_listeview);
        context = this.getActivity();

        View child = getLayoutInflater().inflate(R.layout.no_data_found, null);
        ((ViewGroup)listView.getParent()).addView(child);

        listView.setEmptyView(child);

        objadapter = new ObjectifListAdapter(this.getActivity(), objectiflist);
        listView.setAdapter(objadapter);

        final ProgressDialog pDialog = new ProgressDialog(this.getActivity());
        pDialog.setMessage("Patientez...");
        pDialog.show();

        String tag_string_req = "req_sync";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_VIEW_OBJECTIF, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.hide();
                // textViewObj.setText(response);
                //ouvrir le logManager

                try {

                    //Log.d("objectif", "onResponse: "+response);
                    JSONObject jObj = new JSONObject(response);
                    // textViewObj=jObj.getInt("statut").;
                    int error = jObj.getInt("statut");
                    if (error==1) {

                        JSONArray Resultats = jObj.getJSONArray("Resultats");
                        objectiflist.clear();
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
                Log.d("TAG", "onErrorResponse: ");
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
        getActivity().setTitle("OBJECTIF/REALISATION");
    }
}
