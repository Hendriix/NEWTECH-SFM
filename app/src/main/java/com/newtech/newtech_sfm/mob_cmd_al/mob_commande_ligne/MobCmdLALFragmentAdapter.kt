package com.newtech.newtech_sfm.mob_cmd_al.mob_commande_ligne

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.newtech.newtech_sfm.Metier.MobCmdLAL
import com.newtech.newtech_sfm.R
import com.squareup.picasso.Picasso

class MobCmdLALFragmentAdapter(
    private val navController: NavController,
    private val dataSet: List<MobCmdLAL>,
    private val context: Context?
) :
    RecyclerView.Adapter<MobCmdLALFragmentAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mView: View? = null


        val article_designation_tv: TextView
        val article_code_tv: TextView
        val cmd_ligne_numero_tv: TextView
        val article_iv: ImageView
        val article_prix_tv: TextView
        val unite_nom_tv: TextView
        val unite_iv: ImageView
        val quantite_commandee_tv: TextView
        val quantite_livree_tv: TextView
        val quantite_reste_tv: TextView
        val valeur_ligne_tv: TextView


        init {

            mView = view


            article_designation_tv = view.findViewById(R.id.article_designation_tv)
            article_code_tv = view.findViewById(R.id.article_code_tv)
            cmd_ligne_numero_tv = view.findViewById(R.id.cmd_ligne_numero_tv)
            article_iv = view.findViewById(R.id.article_iv)
            article_prix_tv = view.findViewById(R.id.article_prix_tv)
            unite_nom_tv = view.findViewById(R.id.unite_nom_tv)
            unite_iv = view.findViewById(R.id.unite_iv)
            quantite_commandee_tv = view.findViewById(R.id.quantite_commandee_tv)
            quantite_livree_tv = view.findViewById(R.id.quantite_livree_tv)
            quantite_reste_tv = view.findViewById(R.id.quantite_reste_tv)
            valeur_ligne_tv = view.findViewById(R.id.valeur_ligne_tv)


        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.mob_commande_ligne_custom_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val mobCmdLAL = dataSet.get(position)

        viewHolder.article_designation_tv.text = context!!.getString(R.string.article_nom,mobCmdLAL.ARTICLE_DESIGNATION)
        viewHolder.article_code_tv.text = context!!.getString(R.string.article_code,mobCmdLAL.ARTICLE_CODE)
        viewHolder.cmd_ligne_numero_tv.text = context!!.getString(R.string.numero_commande,mobCmdLAL.COMMANDE_LIGNE_CODE)
        //Picasso.get().load(mobCmdLAL.ARTICLE_IMAGE).into(viewHolder.article_iv)
        viewHolder.unite_nom_tv.text = context!!.getString(R.string.article_unite,mobCmdLAL.UNITE_NOM)
        //Picasso.get().load(mobCmdLAL.UNITE_IMAGE).into(viewHolder.unite_iv)
        viewHolder.article_prix_tv.text = context!!.getString(R.string.article_prix,mobCmdLAL.ARTICLE_PRIX)
        viewHolder.quantite_commandee_tv.text = context!!.getString(R.string.qte_commandee,mobCmdLAL.QTE_COMMANDEE)
        viewHolder.quantite_livree_tv.text = context!!.getString(R.string.qte_livree,mobCmdLAL.QTE_LIVREE)
        viewHolder.quantite_reste_tv.text = context!!.getString(R.string.qte_reste,mobCmdLAL.QTE_RESTE)
        viewHolder.valeur_ligne_tv.text = context!!.getString(R.string.valeur_cmd_ligne,mobCmdLAL.MONTANT)


        /*viewHolder.mView?.setOnClickListener {


            val bundle = Bundle()
            bundle.putString("COMMANDE_CODE", mobCmdLAL.COMMANDE_CODE)
            navController.navigate(R.id.action_questionnaireFragment_to_reponseFragment, bundle)

        }*/

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}