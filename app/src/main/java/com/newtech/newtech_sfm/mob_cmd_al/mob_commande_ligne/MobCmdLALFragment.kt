package com.newtech.newtech_sfm.mob_cmd_al.mob_commande_ligne

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.newtech.newtech_sfm.Activity.ClientActivity
import com.newtech.newtech_sfm.Metier.Livraison
import com.newtech.newtech_sfm.Metier.LivraisonLigne
import com.newtech.newtech_sfm.Metier_Manager.LivraisonLigneManager
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.databinding.FragmentMobCmdLigneBinding
import com.newtech.newtech_sfm.mob_cmd_al.MobCmdALViewModel
import com.newtech.newtech_sfm.mob_cmd_al.mob_livraison_panier.ViewLivraisonActivity

class MobCmdLALFragment : Fragment() {

    private lateinit var binding: FragmentMobCmdLigneBinding
    private val mobCmdALViewModel: MobCmdALViewModel by activityViewModels()
    private lateinit var mobCmdLALFragmentAdapter: MobCmdLALFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_mob_cmd_ligne, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController: NavController = this.findNavController()

        val bundle = this.arguments
        mobCmdALViewModel.setCommandeCode(bundle!!.getString("COMMANDE_CODE")!!)

        binding.determinateBar.visibility = View.VISIBLE

        mobCmdALViewModel.getMobCmdLAL()
            .observe(viewLifecycleOwner) { mobCmdLAL ->
                // update UI
                binding.determinateBar.visibility = View.GONE
                binding.btnValidationLayout.visibility = View.VISIBLE

                mobCmdLALFragmentAdapter =
                    MobCmdLALFragmentAdapter(navController, mobCmdLAL, context)
                binding.mobCmdLigneRecyclerView.adapter = mobCmdLALFragmentAdapter
                mobCmdLALFragmentAdapter.notifyDataSetChanged()
            }


        binding.livrerBtn.setOnClickListener {

            val livraisonLigneManager = LivraisonLigneManager(context)

            val livraisonLignes: ArrayList<LivraisonLigne> = arrayListOf()

            val livraison = Livraison(

                mobCmdALViewModel.getCommande(),
                "LIVRAISON_CODE",
                context,
                ClientActivity.clientCourant,
                ClientActivity.gps_latitude,
                ClientActivity.gps_longitude

            )

            val livraisonLignesAL: ArrayList<LivraisonLigne> = livraisonLigneManager.getListAL(

                mobCmdALViewModel.getCommandeLignes(),
                "LIVRAISON_CODE",
                context

            )

            mobCmdALViewModel.setLivraison(livraison)
            mobCmdALViewModel.setLivraisonLignes(livraisonLignesAL)

            /*ViewLivraisonActivity.commande = mobCmdALViewModel.getCommande()
            ViewLivraisonActivity.ListeCommandeLigne = mobCmdALViewModel.getCommandeLignes()

            ViewLivraisonActivity.livraisonLignes = livraisonLignesAL
            ViewLivraisonActivity.livraison = livraison
            ViewLivraisonActivity.commandeSource = "Livraison"*/

            navController.navigate(R.id.action_mobCmdLALFragment_to_mobViewLivraisonFragment, bundle)

            /*val intent = Intent(context, ViewLivraisonActivity::class.java)
            intent.putExtra("COMMANDE_CODE", mobCmdALViewModel.getCommande().commandE_CODE)
            startActivity(intent)
            requireActivity().finish()*/

        }

        binding.annulerBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}