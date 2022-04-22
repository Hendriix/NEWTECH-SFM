package com.newtech.newtech_sfm.mob_cmd_al.mob_livraison_panier

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckedTextView
import android.widget.TextView
import com.newtech.newtech_sfm.Metier.Famille
import com.newtech.newtech_sfm.R

class FamilleAdapter(private var activity: Activity,
        private var familles: ArrayList<Famille>) : BaseAdapter() {

    private var inflater :LayoutInflater? = null

    override fun getCount(): Int {
        return familles.size
    }

    override fun getItem(position: Int): Any {
        return familles[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var convertView = convertView

        if (inflater == null) inflater = activity
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (convertView == null) convertView =
            inflater!!.inflate(R.layout.simple_spinner_item, null)

        val label = convertView!!.findViewById<View>(R.id.spinnertext1) as TextView

        val famille = familles.get(position)
        label.text = famille.famillE_NOM

        return convertView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView

        if (inflater == null) inflater = activity
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (convertView == null) convertView =
            inflater!!.inflate(R.layout.simple_spinner_dropdown_item, null)

        //TextView label=new TextView(activity);

        //TextView label=new TextView(activity);
        val label = convertView!!.findViewById<View>(R.id.spinnertext1) as CheckedTextView

        val famille = familles.get(position)
        label.text = famille.famillE_NOM

        return convertView
    }
}