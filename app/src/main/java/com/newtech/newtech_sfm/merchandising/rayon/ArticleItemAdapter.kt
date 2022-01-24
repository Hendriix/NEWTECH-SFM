package com.newtech.newtech_sfm.merchandising.rayon

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.newtech.newtech_sfm.Metier.Article
import com.newtech.newtech_sfm.R

class ArticleItemAdapter : BaseAdapter {

    private val context: Context?
    private val dataset: List<Article>
    private val inflater: LayoutInflater

    constructor(context: Context?, dataset: List<Article>) : super() {
        this.context = context
        this.dataset = dataset
        this.inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return dataset.size
    }

    override fun getItem(position: Int): Any {
        return dataset.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // inflate the layout for each list row
        val rowView = inflater.inflate(R.layout.custom_spinner_item,parent,false)
        val article = dataset.get(position)

        val articleName : TextView = rowView.findViewById(R.id.familly_name)
        articleName.text = article.articlE_DESIGNATION1

        return rowView
    }
}



