package com.newtech.newtech_sfm.Metier

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.util.*

data class RapportChoufouni( var OBJECTIF: String? = null,
                             var CONTRAT: String? = null,
                             var CONTRAT_IMAGE: String? = null,
                             var RESTE: String? = null,
                             var rapportChoufouniClients: ArrayList<RapportChoufouniClient>? = null) {




    constructor (rapportChoufouni: JSONObject?) : this(){
        val rapportChoufouniClientArrayList = ArrayList<RapportChoufouniClient>()
        try {
            this.OBJECTIF = rapportChoufouni!!.getString("OBJECTIF")
            this.CONTRAT = rapportChoufouni!!.getString("CONTRAT")
            this.CONTRAT_IMAGE = rapportChoufouni!!.getString("CONTRAT_IMAGE")
            this.RESTE = rapportChoufouni!!.getString("RESTE")

            val rapportChoufouniClient = rapportChoufouni!!.getJSONArray("rapportChoufouniClient")

            if (rapportChoufouniClient.length() > 0) {
                for (i in 0 until rapportChoufouniClient.length()-1) {
                    val secondObject = rapportChoufouniClient.getJSONObject(i)
                    val rcc = RapportChoufouniClient(secondObject)
                    rapportChoufouniClientArrayList.add(rcc)
                }

            } else {
                Log.d("TAG", "not enough r choufouni: ")
            }
            this.rapportChoufouniClients = rapportChoufouniClientArrayList
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }



    override fun toString(): String {
        return "RapportChoufouni(OBJECTIF=$OBJECTIF, CONTRAT=$CONTRAT, CONTRAT_IMAGE=$CONTRAT_IMAGE, RESTE=$RESTE, rapportChoufouniClients=$rapportChoufouniClients)"
    }


}