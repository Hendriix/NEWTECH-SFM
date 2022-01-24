package com.newtech.newtech_sfm.merchandising.article

import android.content.Context
import com.newtech.newtech_sfm.Metier.Article
import com.newtech.newtech_sfm.Metier.Famille
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager
import com.newtech.newtech_sfm.Metier_Manager.FamilleManager

class ArticleFragmentPresenter(articleFragmentView: ArticleFragmentView)  {

    val articleFragmentView = articleFragmentView

    fun getFamillyList(context: Context?): List<Famille> {

        val familleManager = FamilleManager(context)
        val famillyList: List<Famille> = familleManager.list

        return famillyList
    }

    fun getArticleList(context: Context?): ArrayList<Article> {

        val articleManager = ArticleManager(context)
        val articleList: ArrayList<Article> = articleManager.list

        return articleList
    }

    fun getListByFamilly(context: Context?, familleCode: String?): ArrayList<Article> {
        val articleManager = ArticleManager(context)
        val articleList: ArrayList<Article> = articleManager.getListByFamilleCode(familleCode)

        return articleList
    }

    fun getList(context: Context?): ArrayList<Article> {
        val articleManager = ArticleManager(context)
        val articleList: ArrayList<Article> = articleManager.getList()

        return articleList
    }

}