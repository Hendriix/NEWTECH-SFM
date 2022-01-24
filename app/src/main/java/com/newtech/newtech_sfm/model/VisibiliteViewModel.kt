package com.newtech.newtech_sfm.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newtech.newtech_sfm.Metier.*

class VisibiliteViewModel : ViewModel() {

    private var visibiliteLigneArrayList = ArrayList<VisibiliteLigne>()
    private var visibiliteRayonArrayList = ArrayList<VisibiliteRayon>()
    private var visibilite_code: String = ""
    private var famille_position: Int = -1

    private val _visibilite = MutableLiveData<Visibilite>()
    val visibilite: LiveData<Visibilite> = _visibilite

    private val _article = MutableLiveData<Article>()
    val article: LiveData<Article> = _article

    private val _visibiliteLigne = MutableLiveData<ArrayList<VisibiliteLigne>>()
    val visibiliteLigne: LiveData<ArrayList<VisibiliteLigne>> = _visibiliteLigne

    private val _visibiliteRayon = MutableLiveData<ArrayList<VisibiliteRayon>>()
    val visibiliteRayon: LiveData<ArrayList<VisibiliteRayon>> = _visibiliteRayon

    private val _quantite = MutableLiveData<Int>()
    val quantite: LiveData<Int> = _quantite


    private val _famille = MutableLiveData<String>()
    val famille: LiveData<String> = _famille

    fun setFamillePosition(position: Int) {
        famille_position = position
    }

    fun getFamillePosition(): Int {
        return famille_position
    }

    fun setVisibliteCode(codeVisibilite: String) {
        visibilite_code = codeVisibilite
    }

    fun getVisibliteCode(): String {
        return visibilite_code
    }

    fun setVisibilite(visibilite: Visibilite) {
        _visibilite.value = visibilite
    }

    fun setVisibiliteLigne(visibiliteLigne: VisibiliteLigne) {
        _visibiliteLigne.value?.add(visibiliteLigne)
    }

    fun setVisibiliteLignes(visibiliteLignes: ArrayList<VisibiliteLigne>) {
        _visibiliteLigne.value = visibiliteLignes
    }

    fun setVisibiliteRayon(visibiliteRayon: VisibiliteRayon) {
        visibiliteRayonArrayList.add(visibiliteRayon)
    }

    fun deleteVisibiliteRayon(visibiliteRayon: VisibiliteRayon) {
        visibiliteRayonArrayList.remove(visibiliteRayon)
    }

    fun setVisibiliteRayons(visibiliteRayons: ArrayList<VisibiliteRayon>) {
        _visibiliteRayon.value = visibiliteRayons
    }

    fun setQuantite(quantite: Int) {
        _quantite.value = quantite
    }

    fun setFamille(famille: String) {
        _famille.value = famille
    }

    fun setArticle(article: Article) {
        _article.value = article
    }

    fun createVisibiliteLignes(articles: ArrayList<Article>) {

        Log.d("merchandising", "createVisibiliteLignes: "+this.getVisibliteCode())

        if (this.visibiliteLigneArrayList.size <= 0) {
            val visibiliteLignes = ArrayList<VisibiliteLigne>()

            for (i in articles.indices) {
                val ARTICLE_CODE = articles[i].articlE_CODE
                val FAMILLE_CODE = articles[i].famillE_CODE
                val visibiliteLigne = VisibiliteLigne(
                    getVisibliteCode(), ARTICLE_CODE, FAMILLE_CODE, i+1, 0, 0
                )

                visibiliteLignes.add(visibiliteLigne)

            }
            setVisibiliteLignes(visibiliteLignes)
            this.visibiliteLigneArrayList = visibiliteLignes
        }

    }

    fun getVisibiliteLignes(): ArrayList<VisibiliteLigne> {
        return this.visibiliteLigneArrayList
    }

    fun getVisibiliteLignes(
        articles: ArrayList<Article>
    ): ArrayList<VisibiliteLigne> {

        val visibiliteLignes = ArrayList<VisibiliteLigne>()

        for (i in articles.indices) {
            val article_code: String = articles[i].articlE_CODE

            for (j in visibiliteLigneArrayList.indices) {
                val visibilite_ligne_article_code = visibiliteLigneArrayList[j].articlE_CODE

                if (article_code.equals(visibilite_ligne_article_code)) {
                    visibiliteLignes.add(visibiliteLigneArrayList[j])
                }
            }
        }

        return visibiliteLignes
    }

    fun getVisibiliteLigneByArticleCode(articleCode: String): VisibiliteLigne {

        var visibiliteLigne = VisibiliteLigne()

        for (i in visibiliteLigneArrayList.indices) {

            if (visibiliteLigneArrayList[i].articlE_CODE.equals(articleCode)) {
                visibiliteLigne = visibiliteLigneArrayList[i]
            }

        }

        return visibiliteLigne
    }

    fun getVisibiliteRayons(): ArrayList<VisibiliteRayon> {
        return visibiliteRayonArrayList
    }

    fun getVisibilite(client: Client?, context: Context?, visiteCode: String?): Visibilite? {
        val visibilite: Visibilite
        visibilite = Visibilite(client, context, visiteCode, this.getVisibliteCode())
        return visibilite
    }


}