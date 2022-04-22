package com.newtech.newtech_sfm.Metier

import org.json.JSONException
import org.json.JSONObject


class MobCmdAL {

    var CLIENT_NOM: String = ""
    var VENDEUR_NOM: String = ""
    var COMMANDE_CODE: String = ""
    var DATE_COMMANDE: String = ""
    var MONTANT_NET: String = ""
    var RESTE_MONTANT_NET: String = ""
    var TONNAGE: String = ""
    var RESTE_TONNAGE: String = ""

    constructor(
        CLIENT_NOM: String,
        VENDEUR_NOM: String,
        COMMANDE_CODE: String,
        DATE_COMMANDE: String,
        MONTANT_NET: String,
        RESTE_MONTANT_NET: String,
        TONNAGE: String,
        RESTE_TONNAGE: String
    ) {
        this.CLIENT_NOM = CLIENT_NOM
        this.VENDEUR_NOM = VENDEUR_NOM
        this.COMMANDE_CODE = COMMANDE_CODE
        this.DATE_COMMANDE = DATE_COMMANDE
        this.MONTANT_NET = MONTANT_NET
        this.RESTE_MONTANT_NET = RESTE_MONTANT_NET
        this.TONNAGE = TONNAGE
        this.RESTE_TONNAGE = RESTE_TONNAGE
    }

    constructor (mobCmdAL : JSONObject) : this(){
        try {
            this.CLIENT_NOM = mobCmdAL.getString("CLIENT_NOM")
            this.VENDEUR_NOM = mobCmdAL.getString("VENDEUR_NOM")
            this.COMMANDE_CODE = mobCmdAL.getString("COMMANDE_CODE")
            this.DATE_COMMANDE = mobCmdAL.getString("DATE_COMMANDE")
            this.MONTANT_NET = mobCmdAL.getString("MONTANT_NET")
            this.RESTE_MONTANT_NET = mobCmdAL.getString("RESTE_MONTANT_NET")
            this.TONNAGE = mobCmdAL.getString("TONNAGE")
            this.RESTE_TONNAGE = mobCmdAL.getString("RESTE_TONNAGE")

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    constructor()

    override fun toString(): String {
        return "MobCmdAL(CLIENT_NOM='$CLIENT_NOM', VENDEUR_NOM='$VENDEUR_NOM', COMMANDE_CODE='$COMMANDE_CODE', DATE_COMMANDE='$DATE_COMMANDE', MONTANT_NET='$MONTANT_NET', RESTE_MONTANT_NET='$RESTE_MONTANT_NET', TONNAGE='$TONNAGE', RESTE_TONNAGE='$RESTE_TONNAGE')"
    }


}