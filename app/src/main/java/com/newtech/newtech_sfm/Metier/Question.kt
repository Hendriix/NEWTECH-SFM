package com.newtech.newtech_sfm.Metier

class Question {

    var ID: Int = 0
    var QUESTION_CODE : String = ""
    var QUESTION_NOM : String = ""
    var QUESTIONNAIRE_CODE : String = ""
    var TYPE_CODE : String = ""
    var STATUT_CODE : String = ""
    var CATEGORIE_CODE : String = ""
    var CREATEUR_CODE : String = ""
    var DATE_CREATION : String = ""
    var QUESTION_GROUPE : String = ""
    var RANG : Int = 0
    var COMMENTAIRE : String = ""
    var INACTIF : Int = 0

    override fun toString(): String {
        return "Question(ID=$ID, QUESTION_CODE='$QUESTION_CODE', QUESTION_NOM='$QUESTION_NOM', QUESTIONNAIRE_CODE=$QUESTIONNAIRE_CODE, TYPE_CODE='$TYPE_CODE', STATUT_CODE='$STATUT_CODE', CATEGORIE_CODE='$CATEGORIE_CODE', CREATEUR_CODE='$CREATEUR_CODE', DATE_CREATION='$DATE_CREATION', QUESTION_GROUPE='$QUESTION_GROUPE', RANG=$RANG, COMMENTAIRE='$COMMENTAIRE', INACTIF=$INACTIF)"
    }

    constructor(questionReponse : QuestionReponse){

        this.ID = questionReponse.ID
        this.QUESTION_CODE = questionReponse.QUESTION_CODE
        this.QUESTION_NOM = questionReponse.QUESTION_NOM
        this.QUESTIONNAIRE_CODE = questionReponse.QUESTIONNAIRE_CODE
        this.TYPE_CODE = questionReponse.TYPE_CODE
        this.STATUT_CODE = questionReponse.STATUT_CODE
        this.CATEGORIE_CODE = questionReponse.CATEGORIE_CODE
        this.CREATEUR_CODE = questionReponse.CREATEUR_CODE
        this.DATE_CREATION = questionReponse.DATE_CREATION
        this.QUESTION_GROUPE = questionReponse.QUESTION_GROUPE
        this.RANG = questionReponse.RANG
        this.COMMENTAIRE = questionReponse.COMMENTAIRE
        this.INACTIF = questionReponse.INACTIF
    }


}