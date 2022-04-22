package com.newtech.newtech_sfm.mob_cmd_al

import android.app.Application
import android.util.Log
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

class MobCmdALViewModel(application: Application) : AndroidViewModel(application) {

    private var clientCode: String = ""
    private var commandeCode: String = ""
    private var commandeSource: String = ""

    private var commande: Commande = Commande()
    private var commandeLignes: ArrayList<CommandeLigne> = arrayListOf()

    private var livraison : Livraison = Livraison()
    private var livraisonLignes : ArrayList<LivraisonLigne> = arrayListOf()

    private var livraisonPromotions: ArrayList<LivraisonPromotion> = arrayListOf()
    private var livraisonGratuites: ArrayList<LivraisonGratuite> = arrayListOf()

    private var encaissements: ArrayList<Encaissement> = arrayListOf()

    private val TAG = MobCmdALViewModel::class.java.name

    private val mobCmdALLiveData: MutableLiveData<List<MobCmdAL>> by lazy {
        MutableLiveData<List<MobCmdAL>>().also {
            loadMobCmdAL()
        }
    }

    private val mobCmdLALLiveData: MutableLiveData<List<MobCmdLAL>> by lazy {
        MutableLiveData<List<MobCmdLAL>>().also {
            loadMobCmdLAL()
        }
    }

    private fun loadMobCmdLAL() {
        val tag_string_req = "MOB_COMMANDE_LIGNES"

        val strReq: StringRequest = object : StringRequest(
            Method.POST, AppConfig.URL_SYNC_MOB_CMDL_AL,
            Response.Listener<String> { response ->

                Log.d(TAG, "loadMobCmdLAL: 1 "+response)

                try {
                    val jObj = JSONObject(response)
                    val error = jObj.getInt("statut")

                    if (error == 1) {
                        val mobCmdLALs = jObj.getJSONArray("mob_commande_lignes")
                        val commandeLALs = jObj.getJSONArray("commande_lignes")
                        val commandeAL = jObj.getJSONArray("commande")

                        val mobCmdALList: MutableList<MobCmdLAL> = arrayListOf()

                        for (i in 0 until mobCmdLALs.length()) {

                            val uneMobCmdAL: JSONObject = mobCmdLALs.getJSONObject(i)
                            mobCmdALList.add(MobCmdLAL(uneMobCmdAL))

                        }

                        for (i in 0 until commandeLALs.length()) {

                            val uneCommandeLigne: JSONObject = commandeLALs.getJSONObject(i)
                            commandeLignes.add(CommandeLigne(uneCommandeLigne))

                        }

                        val uneCommande: JSONObject = commandeAL.getJSONObject(0)
                        commande = Commande(uneCommande)


                        mobCmdLALLiveData.value = mobCmdALList

                    } else {
                        val errorMsg = jObj.getString("error_msg")
                        Log.d(TAG, "loadMobCmdLAL: 2 "+errorMsg)

                    }
                } catch (e: JSONException) {

                    e.printStackTrace()
                    Log.d(TAG, "loadMobCmdLAL: 3 "+e.message.toString())

                }
            },
            Response.ErrorListener { error -> //Toast.makeText(application.applicationContext, "DISTRIBUTEURS:"+error.getMessage(), Toast.LENGTH_LONG).show();

                //Show error
                Log.d(TAG, "loadMobCmdLAL: 4 "+error.message.toString())

            }) {
            override fun getParams(): Map<String, String> {

                val arrayFinale = HashMap<String, String>()
                val TabParams = HashMap<String, String?>()
                TabParams["COMMANDE_CODE"] = commandeCode
                val builder = GsonBuilder()
                val gson = builder.create()
                arrayFinale["Params"] = gson.toJson(TabParams)
                return arrayFinale

            }
        }
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req)
    }

    fun getMobCmdAL(): LiveData<List<MobCmdAL>> {
        return mobCmdALLiveData
    }

    fun getMobCmdLAL(): LiveData<List<MobCmdLAL>> {
        return mobCmdLALLiveData
    }

    private fun loadMobCmdAL() {
        val tag_string_req = "MOB_COMMANDE"

        val strReq: StringRequest = object : StringRequest(
            Method.POST, AppConfig.URL_SYNC_MOB_CMD_AL,
            Response.Listener<String> { response ->

                try {
                    val jObj = JSONObject(response)
                    val error = jObj.getInt("statut")

                    if (error == 1) {
                        val mobCmdALs = jObj.getJSONArray("Commandes")
                        val mobCmdALList: MutableList<MobCmdAL> = arrayListOf()

                        for (i in 0 until mobCmdALs.length()) {

                            val uneMobCmdAL: JSONObject = mobCmdALs.getJSONObject(i)
                            mobCmdALList.add(MobCmdAL(uneMobCmdAL))


                        }

                        mobCmdALLiveData.value = mobCmdALList

                    } else {
                        val errorMsg = jObj.getString("error_msg")

                    }
                } catch (e: JSONException) {

                    e.printStackTrace()

                }
            },
            Response.ErrorListener { error ->

                //Toast.makeText(application.applicationContext, "DISTRIBUTEURS:"+error.getMessage(), Toast.LENGTH_LONG).show();
                //Show error

            }) {
            override fun getParams(): Map<String, String> {

                val arrayFinale = HashMap<String, String>()
                val TabParams = HashMap<String, String?>()
                TabParams["CLIENT_CODE"] = clientCode
                val builder = GsonBuilder()
                val gson = builder.create()
                arrayFinale["Params"] = gson.toJson(TabParams)
                return arrayFinale

            }
        }
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req)
    }

    fun setClientCode(client_code: String) {

        clientCode = client_code

    }

    fun getClientCode(): String{
        return this.clientCode
    }

    fun setCommandeCode(commande_code: String) {

        commandeCode = commande_code

    }

    fun getCommandeLignes(): ArrayList<CommandeLigne> {
        return this.commandeLignes
    }

    fun setCommande(commande: Commande) {
        this.commande = commande
    }

    fun getCommande(): Commande {
        return this.commande
    }

    fun setLivraison(livraison: Livraison) {
        this.livraison = livraison
    }

    fun getLivraison(): Livraison {
        return this.livraison
    }

    fun setLivraisonLignes(livraisonLignes : ArrayList<LivraisonLigne>) {
        this.livraisonLignes = livraisonLignes
    }

    fun getLivraisonLignes(): ArrayList<LivraisonLigne> {
        return this.livraisonLignes
    }

    fun setLivraisonPromotions(livraisonPromotions: ArrayList<LivraisonPromotion>) {
        this.livraisonPromotions = livraisonPromotions
    }

    fun getLivraisonPromotions(): ArrayList<LivraisonPromotion> {
        return this.livraisonPromotions
    }

    fun setLivraisonGratuites(livraisonGratuites: ArrayList<LivraisonGratuite>) {
        this.livraisonGratuites = livraisonGratuites
    }

    fun getLivraisonGratuites(): ArrayList<LivraisonGratuite> {
        return this.livraisonGratuites
    }

    fun setEncaissements(encaissements: ArrayList<Encaissement>) {
        this.encaissements = encaissements
    }

    fun getEncaissements(): ArrayList<Encaissement> {
        return this.encaissements
    }

    fun addEncaissement(encaissement: Encaissement){
        this.encaissements.add(encaissement)
    }


    fun setCommandeSource(commandeSource: String) {

        this.commandeSource = commandeSource

    }

    fun getCommandeSource() :String{
        return this.commandeSource
    }

    fun sumEncaissement(): Double{
        var amount = 0.0
        for (i in getEncaissements().indices) {
            amount += encaissements[i].montant
        }
        return amount
    }
}