package com.newtech.newtech_sfm.merchandising.article

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.newtech.newtech_sfm.Metier.Famille
import com.newtech.newtech_sfm.R

class FamilyItemAdapter : BaseAdapter {

    private val context: Context?
    private val dataset: List<Famille>
    private val inflater: LayoutInflater

    constructor(context: Context?, dataset: List<Famille>) : super() {
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
        val familly = dataset.get(position)

        val famillyName : TextView = rowView.findViewById(R.id.familly_name)
        famillyName.text = familly.famillE_NOM

        return rowView
    }
}



