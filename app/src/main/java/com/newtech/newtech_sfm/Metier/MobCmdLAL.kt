package com.newtech.newtech_sfm.Metier

import org.json.JSONException
import org.json.JSONObject


class MobCmdLAL {

    var COMMANDE_LIGNE_CODE: String = ""
    var ARTICLE_DESIGNATION: String = ""
    var ARTICLE_CODE: String = ""
    var ARTICLE_PRIX: String = ""
    var ARTICLE_IMAGE: String = ""
    var UNITE_NOM: String = ""
    var UNITE_IMAGE: String = ""
    var QTE_COMMANDEE: String = ""
    var QTE_LIVREE: String = ""
    var QTE_RESTE: String = ""
    var MONTANT: String = ""

    constructor(
        COMMANDE_LIGNE_CODE: String,
        ARTICLE_DESIGNATION: String,
        ARTICLE_CODE: String,
        ARTICLE_PRIX: String,
        ARTICLE_IMAGE: String,
        UNITE_NOM: String,
        UNITE_IMAGE: String,
        QTE_COMMANDEE: String,
        QTE_LIVREE: String,
        QTE_RESTE: String,
        MONTANT: String
    ) {
        this.COMMANDE_LIGNE_CODE = COMMANDE_LIGNE_CODE
        this.ARTICLE_DESIGNATION = ARTICLE_DESIGNATION
        this.ARTICLE_CODE = ARTICLE_CODE
        this.ARTICLE_PRIX = ARTICLE_PRIX
        this.ARTICLE_IMAGE = ARTICLE_IMAGE
        this.UNITE_NOM = UNITE_NOM
        this.UNITE_IMAGE = UNITE_IMAGE
        this.QTE_COMMANDEE = QTE_COMMANDEE
        this.QTE_LIVREE = QTE_LIVREE
        this.QTE_RESTE = QTE_RESTE
        this.MONTANT = MONTANT
    }

    constructor (mobCmdLAL: JSONObject) : this() {
        try {
            this.COMMANDE_LIGNE_CODE = mobCmdLAL.getString("COMMANDE_LIGNE_CODE")
            this.ARTICLE_DESIGNATION = mobCmdLAL.getString("ARTICLE_DESIGNATION")
            this.ARTICLE_CODE = mobCmdLAL.getString("ARTICLE_CODE")
            this.ARTICLE_PRIX = mobCmdLAL.getString("ARTICLE_PRIX")
            //this.ARTICLE_IMAGE = mobCmdLAL.getString("ARTICLE_IMAGE")
            this.UNITE_NOM = mobCmdLAL.getString("UNITE_NOM")
            //this.UNITE_IMAGE = mobCmdLAL.getString("UNITE_IMAGE")
            this.QTE_COMMANDEE = mobCmdLAL.getString("QTE_COMMANDEE")
            this.QTE_LIVREE = mobCmdLAL.getString("QTE_LIVREE")
            this.QTE_RESTE = mobCmdLAL.getString("QTE_RESTE")
            this.MONTANT = mobCmdLAL.getString("MONTANT")

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    constructor()

    override fun toString(): String {
        return "MobCmdLAL(COMMANDE_LIGNE_CODE='$COMMANDE_LIGNE_CODE', ARTICLE_DESIGNATION='$ARTICLE_DESIGNATION', ARTICLE_CODE='$ARTICLE_CODE', ARTICLE_PRIX='$ARTICLE_PRIX', ARTICLE_IMAGE='$ARTICLE_IMAGE', UNITE_NOM='$UNITE_NOM', UNITE_IMAGE='$UNITE_IMAGE', QTE_COMMANDEE='$QTE_COMMANDEE', QTE_LIVREE='$QTE_LIVREE', QTE_RESTE='$QTE_RESTE', MONTANT='$MONTANT')"
    }


}