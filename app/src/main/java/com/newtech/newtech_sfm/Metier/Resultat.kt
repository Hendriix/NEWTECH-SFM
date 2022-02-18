package com.newtech.newtech_sfm.Metier

class Resultat {

    var ID: Int = 0
    var QUESTION_CODE: String = ""
    var REPONSE_CODE: String = ""
    var REPONSE_TEXT: String = ""
    var DISTRIBUTEUR_CODE: String = ""
    var UTILISATEUR_CODE: String = ""
    var TACHE_CODE: String = ""
    var CLIENT_CODE: String = ""
    var VISITE_CODE: String = ""
    var CREATEUR_CODE: String = ""
    var DATE_CREATION: String = ""
    var QUESTIONNAIRE_CODE: String = ""


    constructor(reponse: Reponse) {

        this.QUESTION_CODE = reponse.QUESTION_CODE
        this.REPONSE_CODE = reponse.REPONSE_CODE
        this.REPONSE_TEXT = ""
        this.DISTRIBUTEUR_CODE = ""
        this.UTILISATEUR_CODE = ""
        this.TACHE_CODE = ""
        this.CLIENT_CODE = ""
        this.VISITE_CODE = ""
        this.CREATEUR_CODE = reponse.CREATEUR_CODE
        this.DATE_CREATION = reponse.DATE_CREATION
        this.QUESTIONNAIRE_CODE = ""

    }

    constructor()

    constructor(
        reponse: Reponse,
        reponseText: String,
        clientCode: String?,
        visiteCode: String?,
        distributeurCode: String?,
        utilisateurCode: String?,
        questionnaire_code: String
    ) {
        this.QUESTION_CODE = reponse.QUESTION_CODE
        this.REPONSE_CODE = reponse.REPONSE_CODE
        this.REPONSE_TEXT = reponseText
        this.DISTRIBUTEUR_CODE = distributeurCode!!
        this.UTILISATEUR_CODE = utilisateurCode!!
        this.TACHE_CODE = ""
        this.CLIENT_CODE = clientCode!!
        this.VISITE_CODE = visiteCode!!
        this.CREATEUR_CODE = reponse.CREATEUR_CODE
        this.DATE_CREATION = reponse.DATE_CREATION
        this.QUESTIONNAIRE_CODE = questionnaire_code
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Resultat

        if (QUESTION_CODE != other.QUESTION_CODE) return false
        if (REPONSE_CODE != other.REPONSE_CODE) return false

        return true
    }

    override fun hashCode(): Int {
        var result = QUESTION_CODE.hashCode()
        result = 31 * result + REPONSE_CODE.hashCode()
        return result
    }

    override fun toString(): String {
        return "Resultat(ID=$ID, QUESTION_CODE='$QUESTION_CODE', REPONSE_CODE='$REPONSE_CODE', REPONSE_TEXT='$REPONSE_TEXT', DISTRIBUTEUR_CODE='$DISTRIBUTEUR_CODE', UTILISATEUR_CODE='$UTILISATEUR_CODE', TACHE_CODE='$TACHE_CODE', CLIENT_CODE='$CLIENT_CODE', VISITE_CODE='$VISITE_CODE', CREATEUR_CODE='$CREATEUR_CODE', DATE_CREATION='$DATE_CREATION', QUESTIONNAIRE_CODE='$QUESTIONNAIRE_CODE')"
    }


}