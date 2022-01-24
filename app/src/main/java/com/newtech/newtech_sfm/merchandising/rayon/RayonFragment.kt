package com.newtech.newtech_sfm.merchandising.rayon

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.newtech.newtech_sfm.Activity.ClientActivity
import com.newtech.newtech_sfm.Configuration.Common
import com.newtech.newtech_sfm.Metier.Visibilite
import com.newtech.newtech_sfm.Metier.VisibiliteLigne
import com.newtech.newtech_sfm.Metier.VisibiliteRayon
import com.newtech.newtech_sfm.Metier_Manager.VisibiliteLigneManager
import com.newtech.newtech_sfm.Metier_Manager.VisibiliteManager
import com.newtech.newtech_sfm.Metier_Manager.VisibiliteRayonManager
import com.newtech.newtech_sfm.Metier_Manager.VisiteManager
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.databinding.FragmentRayonBinding
import com.newtech.newtech_sfm.model.VisibiliteViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class RayonFragment : RayonFragmentView, Fragment() {

    private val sharedViewModel: VisibiliteViewModel by activityViewModels()
    private lateinit var binding: FragmentRayonBinding
    val df_code: DateFormat = SimpleDateFormat("yyMMddHHmmss")
    val date_code = df_code.format(Calendar.getInstance().time)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentRayonBinding>(
            inflater,
            R.layout.fragment_rayon, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val rayonRecyclerView = binding.rayonRecyclerView
        val navController: NavController = this.findNavController()
        val rayonFragmentPresenter = RayonFragmentPresenter(this)

        val addRayonBtn: FloatingActionButton = binding.addRayonBtn

        val validerVisibiliteBtn: Button = binding.validerVfBtn
        val retourVisibiliteBtn: Button = binding.retourVfBtn

        retourVisibiliteBtn.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(R.id.action_rayonFragment_to_articleFragment)
        }

        validerVisibiliteBtn.setOnClickListener { view: View ->
            /*view.findNavController()
                .navigate(R.id.action_rayonFragment_to_rayonDialogFragment)*/
            var visibiliteLignes = sharedViewModel.getVisibiliteLignes()

            var visibilite = sharedViewModel.getVisibilite(
                ClientActivity.clientCourant,
                context,
                ClientActivity.visite_code
            )

            var rayons = sharedViewModel.getVisibiliteRayons()


            for (i in visibiliteLignes.indices) {
                visibiliteLignes[i].visibilitE_CODE = "VC" + date_code
            }

            for (i in rayons.indices) {
                rayons[i].VISIBILITE_CODE = "VC" + date_code
            }

            visibilite!!.visibilitE_CODE = "VC" + date_code

            showCustomDialog(visibiliteLignes, visibilite, rayons)

        }

        addRayonBtn.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(R.id.action_rayonFragment_to_rayonDialogFragment)
        }



        rayonRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )

        var visibiliteRayonList: ArrayList<VisibiliteRayon>

        visibiliteRayonList = sharedViewModel.getVisibiliteRayons()

        if (visibiliteRayonList.size > 0) {
            binding.noDataFoundLl.visibility = View.GONE
            binding.validationLl.visibility = View.VISIBLE
        }

        rayonRecyclerView.adapter =
            RayonFragmentAdapter(navController, visibiliteRayonList, context, sharedViewModel)

    }

    private fun showCustomDialog(
        visibiliteLignes: ArrayList<VisibiliteLigne>,
        visibilite: Visibilite?,
        rayons: ArrayList<VisibiliteRayon>
    ) {

        val visibiliteManager = VisibiliteManager(context)
        val visibiliteLigneManager = VisibiliteLigneManager(context)
        val visibiliteRayonManager = VisibiliteRayonManager(context)
        val visiteManager = VisiteManager(context)

        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        val viewGroup: ViewGroup = requireActivity().findViewById<ViewGroup>(android.R.id.content)

        //then we will inflate the custom alert dialog xml that we created
        val dialogView =
            LayoutInflater.from(requireContext())
                .inflate(R.layout.referencement_dialog, viewGroup, false)

        val valider_referencement =
            dialogView.findViewById<View>(R.id.valider_referencement) as Button

        val annuler_referencement =
            dialogView.findViewById<View>(R.id.annuler_referencement) as Button

        val fragment_diaog_message =
            dialogView.findViewById<View>(R.id.fragment_diaog_message) as TextView

        //Now we need an AlertDialog.Builder object
        val builder = AlertDialog.Builder(requireContext())

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView)


        //finally creating the alert dialog and displaying it
        val alertDialog = builder.create()
        fragment_diaog_message.text = "VOULEZ VOUS VRAIMENT VALIDER VOTRE REFERENCEMENT/VISIBILITE"
        alertDialog.show()

        annuler_referencement.setOnClickListener { //Toast.makeText(getApplicationContext(),"ANNULER", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss()
        }

        valider_referencement.setOnClickListener {

            if(rayons.size > 0){
                for (i in visibiliteLignes.indices) {
                    visibiliteLigneManager.add(visibiliteLignes[i])
                }

                for (i in rayons.indices) {
                    visibiliteRayonManager.add(rayons[i])
                }

                visibiliteManager.add(visibilite)


                val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val date_visite = df.format(Calendar.getInstance().time)
                visiteManager.updateVisite_VRDF(
                    ClientActivity.visiteCourante.visitE_CODE,
                    1,
                    date_visite
                )
                alertDialog.dismiss()
                if (Common.isNetworkAvailable(requireContext())) {
                    Toast.makeText(
                        requireContext(),
                        "Synchronisation en cours",
                        Toast.LENGTH_SHORT
                    ).show()
                    VisibiliteManager.synchronisationVisibilite(requireContext())
                    VisibiliteLigneManager.synchronisationVisibiliteLigne(requireContext())
                    VisibiliteRayonManager.synchronisationVisibiliteRayon(requireContext())
                }

                val intent = Intent(this.activity, ClientActivity::class.java)
                intent.putExtra("VISITERESULTAT_CODE", 1)
                startActivity(intent)
                this.requireActivity().finish()

            }else{
                Toast.makeText(context, "Il faut compléter les photos des rayons", Toast.LENGTH_SHORT).show()
            }


        }
    }


}