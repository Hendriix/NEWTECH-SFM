package com.newtech.newtech_sfm.superviseur

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.GsonBuilder
import com.newtech.newtech_sfm.Configuration.AppConfig
import com.newtech.newtech_sfm.Configuration.AppController
import com.newtech.newtech_sfm.Metier.*
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap


class QuestionnaireViewModel(application: Application) :
    AndroidViewModel(application) {



    private val questionnairesLiveData: MutableLiveData<List<Questionnaire>> by lazy {
        MutableLiveData<List<Questionnaire>>().also {
            loadQuestionnaires(application)
        }
    }

    private val questionsLiveData: MutableLiveData<List<Question>> by lazy {
        MutableLiveData<List<Question>>().also {
            loadQuestions(application)
        }
    }

    private fun loadQuestions(application: Application) {

    }

    fun getQuestionnaires(): LiveData<List<Questionnaire>> {
        return questionnairesLiveData
    }


    private fun loadQuestionnaires(application: Application) {

        val tag_string_req = "QUESTIONNAIRE"

        val strReq: StringRequest = object : StringRequest(
            Method.POST, AppConfig.URL_SYNC_QUESTIONNAIRE,
            Response.Listener<String> { response ->

                try {
                    val jObj = JSONObject(response)
                    val error = jObj.getInt("statut")

                    if (error == 1) {
                        val questionnaires = jObj.getJSONArray("Questionnaire")
                        var questionnaireList : MutableList<Questionnaire> = arrayListOf()

                        for (i in 0 until questionnaires.length()) {

                            val unQuestionnaire: JSONObject = questionnaires.getJSONObject(i)
                            questionnaireList.add(Questionnaire(unQuestionnaire))


                        }
                        questionnairesLiveData!!.value = questionnaireList

                    } else {
                        val errorMsg = jObj.getString("error_msg")

                    }
                } catch (e: JSONException) {

                    e.printStackTrace()

                }
            },
            Response.ErrorListener { error -> //Toast.makeText(application.applicationContext, "DISTRIBUTEURS:"+error.getMessage(), Toast.LENGTH_LONG).show();

                    //Show error

            }) {
            override fun getParams(): Map<String, String> {

                val arrayFinale = HashMap<String, String>()
                val pref: SharedPreferences = application.applicationContext.getSharedPreferences("MyPref", 0)
                if (pref.getString("is_login", null) == "ok") {
                    val TabParams = HashMap<String, String?>()
                    TabParams["UTILISATEUR_CODE"] = pref.getString("UTILISATEUR_CODE", null)
                    val builder = GsonBuilder()
                    val gson = builder.create()
                    arrayFinale["Params"] = gson.toJson(TabParams)
                }
                return arrayFinale

            }
        }
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req)

    }

}