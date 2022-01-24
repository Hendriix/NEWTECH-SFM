package com.newtech.newtech_sfm.Metier

import org.json.JSONException
import org.json.JSONObject

class RapportChoufouniArticle ( var ARTICLE_CODE: String? = null,
         var ARTICLE_DESIGNATION: String? = null,
         var QUANTITE: String? = null){



    constructor(rapportChoufouniArticle: JSONObject?) : this(){
        try {
            this.ARTICLE_CODE = rapportChoufouniArticle!!.getString("ARTICLE_CODE")
            this.ARTICLE_DESIGNATION = rapportChoufouniArticle!!.getString("ARTICLE_DESIGNATION")
            this.QUANTITE = rapportChoufouniArticle!!.getString("QUANTITE")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun toString(): String {
        return "RapportChoufouniArticle(ARTICLE_CODE=$ARTICLE_CODE, ARTICLE_DESIGNATION=$ARTICLE_DESIGNATION, QUANTITE=$QUANTITE)"
    }


}