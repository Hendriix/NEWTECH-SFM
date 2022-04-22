package com.newtech.newtech_sfm.mob_cmd_al.mob_encaissement

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.newtech.newtech_sfm.Metier.Encaissement
import com.newtech.newtech_sfm.Metier.Famille
import com.newtech.newtech_sfm.R

class MobEncaissementAdapter(private var activity: Activity,
                             private var encaissements: ArrayList<Encaissement>): BaseAdapter() {

    private var inflater :LayoutInflater? = null

    override fun getCount(): Int {
        return encaissements.size
    }

    override fun getItem(position: Int): Any {
        return encaissements[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView

        if (inflater == null) inflater = activity
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (convertView == null) convertView =
            inflater!!.inflate(R.layout.mob_encaissement_custom_item, null)

        val typeEncaissement = convertView!!.findViewById<View>(R.id.encaissement_type_tv) as TextView
        val montantEncaissement = convertView!!.findViewById<View>(R.id.encaissement_montant_tv) as TextView

        val encaissement = encaissements[position]

        typeEncaissement.text = activity.getString(R.string.encaissement_type,encaissement.typE_CODE)
        montantEncaissement.text = activity.getString(R.string.encaissement_montant,encaissement.montanT_ENCAISSE)



        return convertView
    }
}