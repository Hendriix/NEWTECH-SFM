package com.newtech.newtech_sfm.Metier

import org.json.JSONException
import org.json.JSONObject

class Questionnaire (

                    var ID: Int = 0,
                    var QUESTIONNAIRE_CODE: String = "",
                    var QUESTIONNAIRE_NOM: String = "",
                    var CATEGORIE_CODE: String = "",
                    var STATUT_CODE: String = "",
                    var TYPE_CODE: String = "",
                    var CREATEUR_CODE: String = "",
                    var DATE_CREATION: String = "",
                    var DEPARTEMENT_CODE: String = "",
                    var DATE_DEBUT: String = "",
                    var DATE_FIN: String = "",
                    var DESCRIPTION: String = "",
                    var COMMENTAIRE_DEBUT: String = "",
                    var COMMENTAIRE_FIN: String = "",
                    var COMMENTAIRE: String = "",
                    var INACTIF: Int = 0

                    ){

    constructor (questionnaire: JSONObject) : this(){
        try {
            this.ID = questionnaire.getInt("ID")
            this.QUESTIONNAIRE_CODE = questionnaire.getString("QUESTIONNAIRE_CODE")
            this.QUESTIONNAIRE_NOM = questionnaire.getString("QUESTIONNAIRE_NOM")
            this.CATEGORIE_CODE = questionnaire.getString("CATEGORIE_CODE")
            this.STATUT_CODE = questionnaire.getString("STATUT_CODE")
            this.TYPE_CODE = questionnaire.getString("TYPE_CODE")
            this.CREATEUR_CODE = questionnaire.getString("CREATEUR_CODE")
            this.DATE_CREATION = questionnaire.getString("DATE_CREATION")
            this.DEPARTEMENT_CODE = questionnaire.getString("DEPARTEMENT_CODE")
            this.DATE_DEBUT = questionnaire.getString("DATE_DEBUT")
            this.DATE_FIN = questionnaire.getString("DATE_FIN")
            this.DESCRIPTION = questionnaire.getString("DESCRIPTION")
            this.COMMENTAIRE_DEBUT = questionnaire.getString("COMMENTAIRE_DEBUT")
            this.COMMENTAIRE_FIN = questionnaire.getString("COMMENTAIRE_FIN")
            this.COMMENTAIRE = questionnaire.getString("COMMENTAIRE")
            this.INACTIF = questionnaire.getInt("INACTIF")

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun toString(): String {
        return "Questionnaire(ID=$ID, QUESTIONNAIRE_CODE='$QUESTIONNAIRE_CODE', QUESTIONNAIRE_NOM='$QUESTIONNAIRE_NOM', CATEGORIE_CODE='$CATEGORIE_CODE', STATUT_CODE='$STATUT_CODE', TYPE_CODE='$TYPE_CODE', CREATEUR_CODE='$CREATEUR_CODE', DATE_CREATION='$DATE_CREATION', DEPARTEMENT_CODE='$DEPARTEMENT_CODE', DATE_DEBUT='$DATE_DEBUT', DATE_FIN='$DATE_FIN', DESCRIPTION='$DESCRIPTION', COMMENTAIRE_DEBUT='$COMMENTAIRE_DEBUT', COMMENTAIRE_FIN='$COMMENTAIRE_FIN', COMMENTAIRE='$COMMENTAIRE', INACTIF=$INACTIF)"
    }


}

