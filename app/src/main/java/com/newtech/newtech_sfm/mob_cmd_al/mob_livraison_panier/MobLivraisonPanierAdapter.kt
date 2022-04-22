package com.newtech.newtech_sfm.mob_cmd_al.mob_livraison_panier

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newtech.newtech_sfm.Configuration.Common
import com.newtech.newtech_sfm.Configuration.LivraisonPanier_Adapter
import com.newtech.newtech_sfm.Configuration.RecyclerViewAdapter
import com.newtech.newtech_sfm.Configuration.Unite_Livraison_Adapter
import com.newtech.newtech_sfm.Metier.Article
import com.newtech.newtech_sfm.Metier.Client
import com.newtech.newtech_sfm.Metier.LivraisonLigne
import com.newtech.newtech_sfm.Metier_Manager.ClientManager
import com.newtech.newtech_sfm.Metier_Manager.ListePrixLigneManager
import com.newtech.newtech_sfm.Metier_Manager.StockLigneManager
import com.newtech.newtech_sfm.R

class MobLivraisonPanierAdapter : BaseAdapter {

    private var inflater: LayoutInflater? = null
    private var activity: Activity?
    private var articles: ArrayList<Article>
    private var livraisonLignes: ArrayList<LivraisonLigne>
    private var clientCode: String?
    private var context: Context?
    private var client: Client? = null
    private var stockLigneManager: StockLigneManager? = null
    private var listePrixLigneManager: ListePrixLigneManager? = null
    private var clientManager: ClientManager? = null

    constructor(
        activity: Activity?,
        articles: ArrayList<Article>,
        livraisonLignes: ArrayList<LivraisonLigne>,
        clientCode: String?,
        context: Context?
    ) : super() {
        this.activity = activity
        this.articles = articles
        this.livraisonLignes = livraisonLignes
        this.clientCode = clientCode
        this.context = context

        this.stockLigneManager = StockLigneManager(this.context)
        this.listePrixLigneManager = ListePrixLigneManager(this.context)
        this.clientManager = ClientManager(this.context)
        this.client = this.clientManager!!.get(clientCode)
    }

    override fun getCount(): Int {
        return articles.size
    }

    override fun getItem(position: Int): Any {
        return articles[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var convertView = convertView

        if (inflater == null) inflater = activity!!
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (convertView == null) convertView =
            inflater!!.inflate(R.layout.catalogue_article_panier_ligne, null)


        val article = articles[position]

        val article_designation =
            convertView!!.findViewById<View>(R.id.article_designation) as TextView
        val article_code = convertView!!.findViewById<View>(R.id.article_code) as TextView
        val article_prix = convertView!!.findViewById<View>(R.id.prix_article) as TextView
        val article_image = convertView.findViewById<View>(R.id.image_article) as ImageView

        Common.showImageView(article, article_image, context)

        val listePrixLigne = listePrixLigneManager!!.getPrixLigneByLPCACUC(
            client!!.listepriX_CODE,
            article.articlE_CODE
        )

        article_designation.text = article.articlE_DESIGNATION1
        article_code.text = article.articlE_CODE
        if (listePrixLigne.articlE_PRIX == 0.0) {
            article_prix.text = "Prix :" + article.articlE_PRIX + " Dhs"
        } else {
            article_prix.text = "Prix :" + listePrixLigne.articlE_PRIX + " Dhs"
        }

        if (livraisonLignes.size > 0) {
            val livraisonLigneArrayList: java.util.ArrayList<LivraisonLigne> =
                getLLBYAC(livraisonLignes, article.articlE_CODE)
            val unite_livraison_adapter =
                Unite_Livraison_Adapter(activity, livraisonLigneArrayList, context)
            var uniteListView: ListView =
                convertView.findViewById<View>(R.id.unite_listview1) as ListView
            uniteListView.setAdapter(unite_livraison_adapter)
        }

        val stockLignes = stockLigneManager!!.getListByArticleCode(article.articlE_CODE)
        if (stockLignes.size > 0) {
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            val recyclerView: RecyclerView = convertView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager = layoutManager
            val adapter = RecyclerViewAdapter(stockLignes, context)
            recyclerView.adapter = adapter
        }

        return convertView
    }

    fun getLLBYAC(
        livraisonLignes: ArrayList<LivraisonLigne>,
        article_code: String
    ): ArrayList<LivraisonLigne> { //get commande ligne by article code
        val livraisonLigneArrayList = ArrayList<LivraisonLigne>()
        for (i in livraisonLignes.indices) {
            if (livraisonLignes[i].articlE_CODE == article_code) {
                livraisonLigneArrayList.add(livraisonLignes[i])
            }
        }
        return livraisonLigneArrayList
    }
}