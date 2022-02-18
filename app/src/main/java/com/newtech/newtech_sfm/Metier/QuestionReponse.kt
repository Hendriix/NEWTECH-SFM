package com.newtech.newtech_sfm.Metier

import android.util.Log
import org.json.JSONException
import org.json.JSONObject

class QuestionReponse (

                       var ID: Int = 0,
                       var QUESTION_CODE : String = "",
                       var QUESTION_NOM : String = "",
                       var QUESTIONNAIRE_CODE : String = "",
                       var TYPE_CODE : String = "",
                       var STATUT_CODE : String = "",
                       var CATEGORIE_CODE : String = "",
                       var CREATEUR_CODE : String = "",
                       var DATE_CREATION : String = "",
                       var QUESTION_GROUPE : String = "",
                       var RANG : Int = 0,
                       var COMMENTAIRE : String = "",
                       var INACTIF : Int = 0,
                       var TB_REPONSES: ArrayList<Reponse>? = null){


    constructor (tbReponse: JSONObject) : this(){
        val tbReponsesArrayList = ArrayList<Reponse>()
        try {
            this.ID = tbReponse.getInt("ID")
            this.QUESTION_CODE = tbReponse.getString("QUESTION_CODE")
            this.QUESTION_NOM = tbReponse.getString("QUESTION_NOM")
            this.QUESTIONNAIRE_CODE = tbReponse.getString("QUESTIONNAIRE_CODE")
            this.TYPE_CODE = tbReponse.getString("TYPE_CODE")
            this.STATUT_CODE = tbReponse.getString("STATUT_CODE")
            this.CATEGORIE_CODE = tbReponse.getString("CATEGORIE_CODE")
            this.CREATEUR_CODE = tbReponse.getString("CREATEUR_CODE")
            this.DATE_CREATION = tbReponse.getString("DATE_CREATION")
            this.QUESTION_GROUPE = tbReponse.getString("QUESTION_GROUPE")
            this.RANG = tbReponse.getInt("RANG")
            this.COMMENTAIRE = tbReponse.getString("COMMENTAIRE")
            this.INACTIF = tbReponse.getInt("INACTIF")
            val tbReponses = tbReponse.getJSONArray("TB_REPONSES")
            if (tbReponses.length() > 0) {
                for (i in 0 until tbReponses.length()) {
                    val secondObject = tbReponses.getJSONObject(i)
                    val tbReponse = Reponse(secondObject)
                    tbReponsesArrayList.add(tbReponse)
                }

                this.TB_REPONSES = tbReponsesArrayList

            } else {
                Log.d("QuestionReponse", "onResponse: false")
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun toString(): String {
        return "QuestionReponse(ID=$ID, QUESTION_CODE='$QUESTION_CODE', QUESTION_NOM='$QUESTION_NOM', QUESTIONNAIRE_CODE='$QUESTIONNAIRE_CODE', TYPE_CODE='$TYPE_CODE', STATUT_CODE='$STATUT_CODE', CATEGORIE_CODE='$CATEGORIE_CODE', CREATEUR_CODE='$CREATEUR_CODE', DATE_CREATION='$DATE_CREATION', QUESTION_GROUPE='$QUESTION_GROUPE', RANG=$RANG, COMMENTAIRE='$COMMENTAIRE', INACTIF=$INACTIF, TB_REPONSES=$TB_REPONSES)"
    }


}