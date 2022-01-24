package com.newtech.newtech_sfm.Metier

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class RapportChoufouniClient {


     var TOURNEE: String? = null
     var CLIENT_CODE: String? = null
     var CLIENT: String? = null
     var ADRESSE: String? = null
     var TELEPHONE: String? = null
     var CHIFFRE_AFFAIRE: String? = null
     var CONTRAT_EXIST: Int? = null
     var CONTRAT_IMAGE: String? = null

     var rapportChoufouniArticle: ArrayList<RapportChoufouniArticle>? = null

    constructor(rapportChoufouniClient: JSONObject?){
        val rapportChoufouniArticleArrayList = ArrayList<RapportChoufouniArticle>()
        try {
            this.TOURNEE = rapportChoufouniClient!!.getString("TOURNEE")
            this.CLIENT_CODE = rapportChoufouniClient!!.getString("CLIENT_CODE")
            this.CLIENT = rapportChoufouniClient!!.getString("CLIENT")
            this.ADRESSE = rapportChoufouniClient!!.getString("ADRESSE")
            this.TELEPHONE = rapportChoufouniClient!!.getString("TELEPHONE")
            this.CHIFFRE_AFFAIRE = rapportChoufouniClient!!.getString("CHIFFRE_AFFAIRE")
            this.CONTRAT_EXIST = rapportChoufouniClient!!.getInt("CONTRAT_EXIST")
            this.CONTRAT_IMAGE = rapportChoufouniClient!!.getString("CONTRAT_IMAGE")

            val rapportChoufouniArticle = rapportChoufouniClient!!.getJSONArray("rapportChoufouniArticle")

            if (rapportChoufouniArticle.length() > 0) {
                for (i in 0 until rapportChoufouniArticle.length()-1) {
                    val secondObject = rapportChoufouniArticle.getJSONObject(i)
                    val rca = RapportChoufouniArticle(secondObject)
                    rapportChoufouniArticleArrayList.add(rca)
                }
            } else {
                Log.d("TAG", "not enough r choufouni client: ")
            }
            this.rapportChoufouniArticle = rapportChoufouniArticleArrayList
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun toString(): String {
        return "RapportChoufouniClient(TOURNEE=$TOURNEE, CLIENT=$CLIENT, ADRESSE=$ADRESSE, TELEPHONE=$TELEPHONE, CHIFFRE_AFFAIRE=$CHIFFRE_AFFAIRE, CONTRAT_EXIST=$CONTRAT_EXIST, CONTRAT_IMAGE=$CONTRAT_IMAGE, rapportChoufouniArticle=$rapportChoufouniArticle)"
    }


}
