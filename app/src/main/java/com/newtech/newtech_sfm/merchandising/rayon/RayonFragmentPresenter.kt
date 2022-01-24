package com.newtech.newtech_sfm.merchandising.rayon

import android.content.Context
import com.newtech.newtech_sfm.Metier.Article
import com.newtech.newtech_sfm.Metier.Famille
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager
import com.newtech.newtech_sfm.Metier_Manager.FamilleManager


class RayonFragmentPresenter(rayonFragmentView: RayonFragmentView)  {

    val rayonFragmentView = rayonFragmentView

    fun getFamillyList(context: Context?): List<Famille> {

        val familleManager = FamilleManager(context)
        val famillyList: List<Famille> = familleManager.list

        return famillyList
    }

    fun getListByFamilly(context: Context?, familleCode: String?): ArrayList<Article> {
        val articleManager = ArticleManager(context)
        val articleList: ArrayList<Article> = articleManager.getListByFamilleCode(familleCode)

        return articleList
    }

}