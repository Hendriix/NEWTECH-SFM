package com.newtech.newtech_sfm.Metier

import org.json.JSONObject

class Reponse {

    var ID : Int = 0
    var QUESTION_CODE : String = ""
    var REPONSE_CODE : String = ""
    var REPONSE_NOM : String = ""
    var TYPE_CODE : String = ""
    var STATUT_CODE : String = ""
    var CATEGORIE_CODE : String = ""
    var REPONSE_CASE : Int = 0
    var CREATEUR_CODE : String = ""
    var DATE_CREATION : String = ""
    var AVEC_TEXT : Int = 0
    var REPONSE_DESCRIPTION : String = ""
    var COMMENTAIRE : String = ""
    var INACTIF : Int = 0

    constructor(reponse: JSONObject) {
        this.ID = reponse.getInt("ID")
        this.QUESTION_CODE = reponse.getString("QUESTION_CODE")
        this.REPONSE_CODE = reponse.getString("REPONSE_CODE")
        this.REPONSE_NOM = reponse.getString("REPONSE_NOM")
        this.TYPE_CODE = reponse.getString("TYPE_CODE")
        this.STATUT_CODE = reponse.getString("STATUT_CODE")
        this.CATEGORIE_CODE = reponse.getString("CATEGORIE_CODE")
        this.REPONSE_CASE = reponse.getInt("REPONSE_CASE")
        this.CREATEUR_CODE = reponse.getString("CREATEUR_CODE")
        this.DATE_CREATION = reponse.getString("DATE_CREATION")
        this.AVEC_TEXT = reponse.getInt("AVEC_TEXT")
        this.REPONSE_DESCRIPTION = reponse.getString("REPONSE_DESCRIPTION")
        this.COMMENTAIRE = reponse.getString("COMMENTAIRE")
        this.INACTIF = reponse.getInt("INACTIF")
    }



    override fun toString(): String {
        return "Reponse(ID=$ID, QUESTION_CODE='$QUESTION_CODE', REPONSE_CODE='$REPONSE_CODE', REPONSE_NOM='$REPONSE_NOM', TYPE_CODE='$TYPE_CODE', STATUT_CODE='$STATUT_CODE', CATEGORIE_CODE='$CATEGORIE_CODE', REPONSE_CASE=$REPONSE_CASE, CREATEUR_CODE='$CREATEUR_CODE', DATE_CREATION='$DATE_CREATION', AVEC_TEXT=$AVEC_TEXT, REPONSE_DESCRIPTION='$REPONSE_DESCRIPTION', COMMENTAIRE='$COMMENTAIRE', INACTIF=$INACTIF)"
    }

    constructor()


}