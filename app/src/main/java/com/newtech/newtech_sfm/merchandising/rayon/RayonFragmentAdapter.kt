package com.newtech.newtech_sfm.merchandising.rayon

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.newtech.newtech_sfm.Metier.VisibiliteRayon
import com.newtech.newtech_sfm.Metier_Manager.FamilleManager
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.model.VisibiliteViewModel

class RayonFragmentAdapter (
    private val navController: NavController,
    private val dataSet: ArrayList<VisibiliteRayon>,
    private val context: Context?,
    private val sharedViewModel: VisibiliteViewModel
) :
    RecyclerView.Adapter<RayonFragmentAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mView: View? = null
        val code_rayon: TextView
        val famille_rayon: TextView
        val hauteur_rayon: TextView
        val largeur_rayon: TextView

        init {

            mView = view
            code_rayon = view.findViewById(R.id.code_rayon_tv)
            famille_rayon = view.findViewById(R.id.famille_rayon_tv)
            hauteur_rayon = view.findViewById(R.id.hauteur_rayon_tv)
            largeur_rayon = view.findViewById(R.id.largeur_rayon_tv)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rayon_custom_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val visibiliteRayon = dataSet.get(position)

        val dialog : Dialog =  Dialog(context!!);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_dialog);

        val alertMsg : TextView = dialog.findViewById(R.id.alert_msg_tv)
        val alertValidateBtn : TextView = dialog.findViewById(R.id.alert_validate_btn)
        val alertCancelBtn : TextView = dialog.findViewById(R.id.alert_msg_tv)

        alertMsg.text = "Voulez vous vraiment supprimer cette ligne"

        alertCancelBtn.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        alertValidateBtn.setOnClickListener(View.OnClickListener {
            sharedViewModel.deleteVisibiliteRayon(visibiliteRayon)
            notifyDataSetChanged()
            dialog.dismiss()
        })

        val familleManager = FamilleManager(context)

        val famille = familleManager.get(visibiliteRayon.FAMILLE_CODE)

        viewHolder.code_rayon.text = visibiliteRayon.VISIBILITE_RAYON_CODE
        viewHolder.famille_rayon.text = famille.famillE_NOM
        viewHolder.hauteur_rayon.text = visibiliteRayon.HAUTEUR.toString()
        viewHolder.largeur_rayon.text = visibiliteRayon.LARGEUR.toString()

        viewHolder.itemView.setOnLongClickListener(OnLongClickListener {
            dialog.show()
            true
        })

    }

    override fun getItemCount() : Int {
        return dataSet.size
    }

}