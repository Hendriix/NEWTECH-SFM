package com.newtech.newtech_sfm.mob_cmd_al.mob_commandes

import android.content.Context
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.newtech.newtech_sfm.Metier.MobCmdAL
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.mob_cmd_al.MobCmdALViewModel

class MobCmdALFragmentAdapter(
    private val navController: NavController,
    private val dataSet: List<MobCmdAL>,
    private val context: Context?
) :
    RecyclerView.Adapter<MobCmdALFragmentAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mView: View? = null

        val client_nom_tv: TextView
        val vendeur_nom_tv: TextView
        val numero_commande_tv: TextView
        val date_commande_tv: TextView
        val montant_tv: TextView
        val reste_montant_tv: TextView
        val tonnage_tv: TextView
        val reste_tonnage_tv: TextView


        init {

            mView = view

            client_nom_tv = view.findViewById(R.id.client_nom_tv)
            vendeur_nom_tv = view.findViewById(R.id.vendeur_nom_tv)
            numero_commande_tv = view.findViewById(R.id.numero_commande_tv)
            date_commande_tv = view.findViewById(R.id.date_commande_tv)
            montant_tv = view.findViewById(R.id.montant_tv)
            reste_montant_tv = view.findViewById(R.id.reste_montant_tv)
            tonnage_tv = view.findViewById(R.id.tonnage_tv)
            reste_tonnage_tv = view.findViewById(R.id.reste_tonnage_tv)


        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.mob_commande_custom_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val mobCmdAL = dataSet.get(position)

        viewHolder.client_nom_tv.text = context!!.getString(R.string.client_nom,mobCmdAL.CLIENT_NOM)
        viewHolder.vendeur_nom_tv.text = context!!.getString(R.string.vendeur_nom,mobCmdAL.VENDEUR_NOM)
        viewHolder.numero_commande_tv.text = context!!.getString(R.string.numero_commande,mobCmdAL.COMMANDE_CODE)
        viewHolder.date_commande_tv.text = context!!.getString(R.string.date_commande,mobCmdAL.DATE_COMMANDE)
        viewHolder.montant_tv.text = context!!.getString(R.string.montant_net,mobCmdAL.MONTANT_NET)
        viewHolder.reste_montant_tv.text = context!!.getString(R.string.montant_reste,mobCmdAL.RESTE_MONTANT_NET)
        viewHolder.tonnage_tv.text = context!!.getString(R.string.tonnage_net,mobCmdAL.TONNAGE)
        viewHolder.reste_tonnage_tv.text = context!!.getString(R.string.tonnage_reste,mobCmdAL.RESTE_TONNAGE)


        viewHolder.mView?.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("COMMANDE_CODE", mobCmdAL.COMMANDE_CODE)
            navController.navigate(R.id.action_mobCmdALFragment_to_mobCmdLALFragment, bundle)

        }

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}