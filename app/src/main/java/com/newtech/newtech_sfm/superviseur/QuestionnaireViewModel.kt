package com.newtech.newtech_sfm.superviseur

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.TextView
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


class QuestionnaireViewModel(application: Application) :
    AndroidViewModel(application) {

    private var clientCode: String = ""
    private var visiteCode: String = ""
    private var distributeurCode: String = ""
    private var utilisateurCode: String = ""

    private val questionList: ArrayList<Question> = ArrayList()
    private val reponseList: ArrayList<Reponse> = ArrayList()
    val resultatList: ArrayList<Resultat> = ArrayList()

    private val questionnairesLiveData: MutableLiveData<List<Questionnaire>> by lazy {
        MutableLiveData<List<Questionnaire>>().also {
            loadQuestionnaires(application)
        }
    }

    private val questionnaireQRLiveData: MutableLiveData<QuestionnaireQR> by lazy {
        MutableLiveData<QuestionnaireQR>().also {
            loadQuestionnaireQR(application)
        }
    }

    private val resultatLiveData: MutableLiveData<ArrayList<Resultat>> by lazy {
        MutableLiveData<ArrayList<Resultat>>().also {
            loadResultats(application)
        }
    }

    /*private val questionListLiveData: MutableLiveData<ArrayList<Question>> by lazy {
        MutableLiveData<ArrayList<Question>>().also {
            loadQuestion(application)
        }
    }*/

    private fun loadResultats(application: Application) {
        resultatLiveData.value = resultatList
    }


    fun getQuestionnaires(): LiveData<List<Questionnaire>> {
        return questionnairesLiveData
    }

    fun getQuestionnaireQR(): LiveData<QuestionnaireQR> {
        return questionnaireQRLiveData
    }

    fun getResultatListByQuestionCode(questionCode: String): ArrayList<Resultat> {
        val resultatListByQuestionCode: ArrayList<Resultat> = arrayListOf()

        for (i in 0..resultatList.size - 1) {
            val resultat: Resultat = resultatList[i]
            if (resultat.QUESTION_CODE.equals(questionCode)) {

                resultatListByQuestionCode.add(resultat)
            }
        }

        return resultatListByQuestionCode
    }

    fun getReponseByReponseCode(reponseCode: String): Reponse {

        var reponseFound : Reponse = Reponse()
        for(i in 0..reponseList.size-1){

            val reponse = reponseList[i]
            if(reponse.REPONSE_CODE.equals(reponseCode)){

                reponseFound = reponse
                return reponseFound
            }
        }
        return reponseFound
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
                        val questionnaires = jObj.getJSONArray("Questionnaires")
                        val questionnaireList: MutableList<Questionnaire> = arrayListOf()

                        for (i in 0 until questionnaires.length()) {

                            val unQuestionnaire: JSONObject = questionnaires.getJSONObject(i)
                            questionnaireList.add(Questionnaire(unQuestionnaire))


                        }
                        questionnairesLiveData.value = questionnaireList

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
                val pref: SharedPreferences =
                    application.applicationContext.getSharedPreferences("MyPref", 0)
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

    private fun loadQuestionnaireQR(application: Application) {
        val tag_string_req = "QUESTIONNAIRE_REPONSES"

        val strReq: StringRequest = object : StringRequest(
            Method.POST, AppConfig.URL_SYNC_QUESTIONNAIRE_REPONSE,
            Response.Listener<String> { response ->

                //ouvrir le logManager
                Log.d("QVM QR", "" + response)
                try {
                    val jObj = JSONObject(response)
                    val error = jObj.getInt("statut")

                    if (error == 1) {
                        val questionnaireQrs = jObj.getJSONObject("Resultats")


                        if (questionnaireQrs.length() > 0) {

                            val questionnaireQR = QuestionnaireQR(questionnaireQrs)
                            questionnaireQRLiveData.value = questionnaireQR

                            setQuestionList(questionnaireQR)
                            setReponseList(questionnaireQR)

                            Log.d(
                                "QVM QR",
                                "loadQuestionnaireQR: $questionnaireQR"
                            )

                        } else {

                            Log.d("QVM QR", "loadQuestionnaireQR: Empty")

                        }


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
                val pref: SharedPreferences =
                    application.applicationContext.getSharedPreferences("MyPref", 0)
                if (pref.getString("is_login", null) == "ok") {
                    val TabParams = HashMap<String, String?>()
                    //TabParams["QUESTIONNAIRE_CODE"] = pref.getString("QUESTIONNAIRE_CODE", null)
                    TabParams["QUESTIONNAIRE_CODE"] = "QSTR00001"
                    val builder = GsonBuilder()
                    val gson = builder.create()
                    arrayFinale["Params"] = gson.toJson(TabParams)
                }
                return arrayFinale

            }
        }
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req)
    }

    private fun setQuestionList(QuestionnaireQR: QuestionnaireQR) {
        val questionReponseList: ArrayList<QuestionReponse>? = QuestionnaireQR.TB_QUESTION_REPONSES

        if (questionReponseList!!.size > 0) {
            for (i in 0..questionReponseList.size - 1) {

                val question = Question(questionReponseList[i])
                questionList.add(question)

            }
        }
    }

    fun getQuestionList() : ArrayList<Question>{
        return questionList
    }

    private fun setReponseList(QuestionnaireQR: QuestionnaireQR) {
        val questionReponseList: ArrayList<QuestionReponse>? = QuestionnaireQR.TB_QUESTION_REPONSES

        if (questionReponseList!!.size > 0) {

            for (i in 0..questionReponseList.size - 1) {

                val questionReponse: QuestionReponse = questionReponseList[i]

                if (questionReponse.TB_REPONSES!!.size > 0) {

                    for (j in 0..questionReponse.TB_REPONSES!!.size - 1) {

                        val reponse: Reponse = questionReponse.TB_REPONSES!![j]

                        reponseList.add(reponse)
                    }

                }

            }
        }
    }

    fun addResultRadioButton(reponseCode: String, questionReponse: QuestionReponse) {

        Log.d("QuestionnaireViewModel", "addResultRadioButton:client $clientCode")
        Log.d("QuestionnaireViewModel", "addResultRadioButton:getclient " + getClientCode())

        var resultat = Resultat()

        for (i in 0..reponseList.size - 1) {

            val reponse = reponseList[i]

            if (reponse.REPONSE_CODE.equals(reponseCode)) {
                resultat = Resultat(
                    reponse,
                    "",
                    clientCode,
                    visiteCode,
                    distributeurCode,
                    utilisateurCode,
                    questionReponse.QUESTIONNAIRE_CODE
                )

            }
        }

        for (j in 0..resultatList.size - 1) {

            if (resultat.QUESTION_CODE.equals(resultatList[j].QUESTION_CODE)) {
                resultatList.remove(resultatList[j])
                break
            }
        }

        resultatList.add(resultat)


    }

    fun addResultCheckBox(
        reponseCode: String,
        isChecked: Boolean,
        questionReponse: QuestionReponse
    ) {

        var resultat = Resultat()

        for (i in 0..reponseList.size - 1) {

            val reponse = reponseList[i]

            if (reponse.REPONSE_CODE.equals(reponseCode)) {
                resultat = Resultat(
                    reponse,
                    "",
                    getClientCode(),
                    getVisiteCode(),
                    getDistributeurCode(),
                    getUtilisateurCode(),
                    questionReponse.QUESTIONNAIRE_CODE
                )

            }
        }

        if (isChecked) {
            resultatList.add(resultat)
        } else {
            resultatList.remove(resultat)
        }

    }

    fun addResultEditText(questionReponse: QuestionReponse, editTextValue: String) {

        var resultat = Resultat()

        for (i in 0..reponseList.size - 1) {

            val reponse = reponseList[i]

            if (reponse.QUESTION_CODE.equals(questionReponse.QUESTION_CODE)) {
                resultat = Resultat(
                    reponse,
                    editTextValue,
                    getClientCode(),
                    getVisiteCode(),
                    getDistributeurCode(),
                    getUtilisateurCode(),
                    questionReponse.QUESTIONNAIRE_CODE
                )

            }
        }

        for (j in 0..resultatList.size - 1) {

            if (resultat.QUESTION_CODE.equals(resultatList[j].QUESTION_CODE)) {
                resultatList.remove(resultatList[j])
                break
            }
        }

        if (!editTextValue.equals("")) {
            resultatList.add(resultat)
        }


    }

    fun checkifResultatExist(questionCode: String): Boolean {

        var exist = false
        for (i in 0..resultatList.size - 1) {

            if (resultatList[i].QUESTION_CODE.equals(questionCode)) {
                exist = true
                return exist
            }
        }
        return exist
    }

    fun validateForm(view: View): Boolean {

        val questionList = this.questionList
        var questionTv: TextView
        var isFormValid = true
        for (i in 0..questionList.size - 1) {

            questionTv = view.findViewWithTag(questionList[i].QUESTION_CODE)

            if (!this.checkifResultatExist(questionList[i].QUESTION_CODE)) {
                isFormValid = false
                questionTv.error = "Ce champs est obligatoire"
                return isFormValid
            } else {
                questionTv.error = null
            }
        }
        return isFormValid
    }

    fun setClientCode(client_code: String) {

        Log.d("QuestionnaireViewModel 1", "setClientCode: " + client_code)

        clientCode = client_code

        Log.d("QuestionnaireViewModel 2", "setClientCode: " + getClientCode())

    }

    fun getClientCode(): String {
        return clientCode
    }

    fun setVisiteCode(visiteCode: String) {
        this.visiteCode = visiteCode
    }

    fun getVisiteCode(): String {
        return visiteCode
    }

    fun setDistributeurCode(distributeurCode: String) {
        this.distributeurCode = distributeurCode
    }

    fun getDistributeurCode(): String {
        return distributeurCode
    }

    fun setUtilisateurCode(utilisateurCode: String) {
        this.utilisateurCode = utilisateurCode
    }

    fun getUtilisateurCode(): String {
        return utilisateurCode
    }

    fun checkIfQuestionHadResultat(questionCode : String) : Boolean{

        var hasResultat = false

        for(i in 0..resultatList.size-1){

            val resultat = resultatList[i]

            if(resultat.QUESTION_CODE.equals(questionCode)){
                return true
            }
        }

        return hasResultat
    }



}