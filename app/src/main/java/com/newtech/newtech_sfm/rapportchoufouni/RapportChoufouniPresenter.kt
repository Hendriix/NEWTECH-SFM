package com.newtech.newtech_sfm.rapportchoufouni

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.GsonBuilder
import com.newtech.newtech_sfm.Configuration.AppConfig
import com.newtech.newtech_sfm.Configuration.AppController
import com.newtech.newtech_sfm.Metier.RapportChoufouni
import com.newtech.newtech_sfm.Metier_Manager.LogSyncManager
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList
import java.util.HashMap

class RapportChoufouniPresenter() {

    private val TAG = RapportChoufouniPresenter::class.java.name

    private var view: RapportChoufouniView? = null

    constructor (rapportChoufouniView: RapportChoufouniView?) : this() {
        view = rapportChoufouniView
    }

    fun getRapportChoufouni(context: Context?, utilisateur_code: String) {
        if (view != null) {
            view!!.showLoading()
        }
        val tag_string_req = "RAPPORT_CHOUFOUNI"
        val strReq: StringRequest = object : StringRequest(
            Method.POST, AppConfig.URL_GET_RAPPORT_CHOUFOUNI,
            Response.Listener { response ->
                //ouvrir le logManager
                Log.d("TableauBordClient ", "" + response)
                val logM = LogSyncManager(context)
                try {
                    val rapportChoufouniArrayList = ArrayList<RapportChoufouni>()
                    val jObj = JSONObject(response)
                    val error = jObj.getInt("statut")
                    if (error == 1) {
                        val rapportChoufounis = jObj.getJSONObject("Resultats")
                        //JSONObject firstObject = jObj.JSONObject("Resultats");
                        //Toast.makeText(context, "nombre de Classes  "+Classes.length()  , Toast.LENGTH_SHORT).show();
                        //JSONObject tbclientsJSONObject = tbclients.getJSONObject(0);
                        if (rapportChoufounis.length() > 0) {
                            val rapportChoufouni = RapportChoufouni(rapportChoufounis)
                            view!!.showSuccess(rapportChoufouni)
                        } else {
                            view!!.showEmpty("Aucune information trouvée")
                        }

                        //Log.d(TAG, "onResponse: "+tbClient);
                        //view.showSuccess(tbClient);
                    } else {
                        val errorMsg = jObj.getString("error_msg")
                        Log.d(TAG, "onResponse: $errorMsg")
                        view!!.showError(errorMsg)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    view!!.showError(e.message)
                }
            },
            Response.ErrorListener { view!!.showError("Un erreur est survenue merci de réessayer") }) {
            override fun getParams(): Map<String, String> {
                val arrayFinale = HashMap<String, String>()
                val TabParams = HashMap<String, String>()
                TabParams["UTILISATEUR_CODE"] = utilisateur_code
                //TabParams.put("CLIENT_CODE", "TACD006328");
                val builder = GsonBuilder()
                val gson = builder.create()
                arrayFinale["Params"] = gson.toJson(TabParams)
                return arrayFinale
            }
        }
        strReq.retryPolicy = DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req)
    }

    interface RapportChoufouniView {
        fun showSuccess(rapportChoufouni: RapportChoufouni)
        fun showError(message: String?)
        fun showEmpty(message: String?)
        fun showLoading()
    }
}