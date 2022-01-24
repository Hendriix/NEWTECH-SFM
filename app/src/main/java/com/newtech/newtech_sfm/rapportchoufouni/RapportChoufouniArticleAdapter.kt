package com.newtech.newtech_sfm.rapportchoufouni

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newtech.newtech_sfm.Metier.RapportChoufouniArticle
import com.newtech.newtech_sfm.databinding.RapportChoufouniArticleItemBinding
import java.util.ArrayList

class RapportChoufouniArticleAdapter (

    private val dataSet: ArrayList<RapportChoufouniArticle>?,
    private val context: Context?
) :
    RecyclerView.Adapter<RapportChoufouniArticleAdapter.ViewHolder>() {

    inner class ViewHolder(val binding : RapportChoufouniArticleItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = RapportChoufouniArticleItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val rapportChoufouniArticle = dataSet!!.get(position)
        viewHolder.binding.articleDesignationTv.text = rapportChoufouniArticle.ARTICLE_DESIGNATION
        viewHolder.binding.articleQuantiteTv.text = rapportChoufouniArticle.QUANTITE

    }

    override fun getItemCount() : Int {
        return dataSet!!.size
    }

}