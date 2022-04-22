package com.newtech.newtech_sfm.superviseur.recap

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.GsonBuilder
import com.newtech.newtech_sfm.Configuration.AppConfig
import com.newtech.newtech_sfm.Configuration.AppController
import com.newtech.newtech_sfm.Metier.Resultat
import org.json.JSONObject

class ReponseRecapPresenter() {

    private var view: ReponseRecapView? = null

    constructor(reponseRecapView: ReponseRecapView) : this() {
        view = reponseRecapView
    }

    fun synchronisationResultat(context: Context, resultatArrayList: ArrayList<Resultat>) {

        val tag_string_req = "req_sync"
        val strReq: StringRequest = object : StringRequest(
            Method.POST, AppConfig.URL_SYNC_RESULTAT,
            Response.Listener { response ->

                try {
                    val jObj = JSONObject(response)
                    val error = jObj.getInt("statut")
                    if (error == 1) {
                        val syncResult = jObj.getBoolean("result")
                        val syncInfo = jObj.getString("info")
                        view!!.showSuccess(syncInfo,syncResult)

                    } else {
                        val errorMsg = jObj.getString("info")
                        view!!.showSuccess(errorMsg,false)
                    }
                } catch (e: Exception) {

                    e.printStackTrace()
                    view!!.showSuccess(e.message.toString(),false)
                }

            },
            Response.ErrorListener { error ->
                Log.d("QuestionnaireViewModel", "synchronisationResultat: error listener")
                view!!.showSuccess(error.message.toString(),false)
            }) {
            override fun getParams(): Map<String, String> {
                val arrayFinale = java.util.HashMap<String, String>()
                val pref = context.getSharedPreferences("MyPref", 0)
                if (pref.getString("is_login", null) == "ok") {
                    val TabParams = java.util.HashMap<String, String>()
                    TabParams["Table_Name"] = "Resultats"
                    val builder = GsonBuilder()
                    val gson = builder.create()
                    arrayFinale["Params"] = gson.toJson(TabParams)
                    arrayFinale["Data"] = gson.toJson(resultatArrayList)
                }
                return arrayFinale
            }

        }
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req)
    }

    interface ReponseRecapView {
        fun showSuccess(string : String,boolean: Boolean)
    }
}