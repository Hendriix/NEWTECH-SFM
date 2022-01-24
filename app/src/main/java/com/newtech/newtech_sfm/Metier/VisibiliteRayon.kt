package com.newtech.newtech_sfm.Metier


class VisibiliteRayon {

    var VISIBILITE_RAYON_CODE: String = ""
    var VISIBILITE_CODE: String = ""
    var TYPE_CODE: String = ""
    var STATUT_CODE: String = ""
    var CATEGORIE_CODE: String = ""
    var FAMILLE_CODE: String = ""
    var ARTICLE_CODE: String = ""
    var LARGEUR: Double = 0.0
    var HAUTEUR: Double = 0.0
    var COMMENTAIRE: String = ""
    var COMMENTAIRE_RAYON: String = ""
    var DATE_VISIBILITE_RAYON: String = ""
    var DATE_CREATION: String = ""
    var FIRST_IMAGE: String = ""
    var SECOND_IMAGE: String = ""
    var VERSION: String = ""

    constructor(
        VISIBILITE_RAYON_CODE: String,
        VISIBILITE_CODE: String,
        TYPE_CODE: String,
        STATUT_CODE: String,
        CATEGORIE_CODE: String,
        FAMILLE_CODE: String,
        ARTICLE_CODE: String,
        LARGEUR: Double,
        HAUTEUR: Double,
        COMMENTAIRE: String,
        COMMENTAIRE_RAYON: String,
        DATE_VISIBILITE_RAYON: String,
        DATE_CREATION: String,
        FIRST_IMAGE: String,
        SECOND_IMAGE: String,
        VERSION: String
    ) {
        this.VISIBILITE_RAYON_CODE = VISIBILITE_RAYON_CODE
        this.VISIBILITE_CODE = VISIBILITE_CODE
        this.TYPE_CODE = TYPE_CODE
        this.STATUT_CODE = STATUT_CODE
        this.CATEGORIE_CODE = CATEGORIE_CODE
        this.FAMILLE_CODE = FAMILLE_CODE
        this.ARTICLE_CODE = ARTICLE_CODE
        this.LARGEUR = LARGEUR
        this.HAUTEUR = HAUTEUR
        this.COMMENTAIRE = COMMENTAIRE
        this.COMMENTAIRE_RAYON = COMMENTAIRE_RAYON
        this.DATE_VISIBILITE_RAYON = DATE_VISIBILITE_RAYON
        this.DATE_CREATION = DATE_CREATION
        this.FIRST_IMAGE = FIRST_IMAGE
        this.SECOND_IMAGE = SECOND_IMAGE
        this.VERSION = VERSION
    }

    constructor()

    override fun toString(): String {
        return "VisibiliteRayon(VISIBILITE_RAYON_CODE='$VISIBILITE_RAYON_CODE', VISIBILITE_CODE='$VISIBILITE_CODE', TYPE_CODE='$TYPE_CODE', STATUT_CODE='$STATUT_CODE', CATEGORIE_CODE='$CATEGORIE_CODE', FAMILLE_CODE='$FAMILLE_CODE', ARTICLE_CODE='$ARTICLE_CODE', LARGEUR=$LARGEUR, HAUTEUR=$HAUTEUR, COMMENTAIRE='$COMMENTAIRE', COMMENTAIRE_RAYON='$COMMENTAIRE_RAYON', DATE_VISIBILITE_RAYON='$DATE_VISIBILITE_RAYON', DATE_CREATION='$DATE_CREATION', VERSION='$VERSION')"
    }


}