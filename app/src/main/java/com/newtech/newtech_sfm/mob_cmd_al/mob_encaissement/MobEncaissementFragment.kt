package com.newtech.newtech_sfm.mob_cmd_al.mob_encaissement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.databinding.FragmentMobEncaissementBinding
import com.newtech.newtech_sfm.mob_cmd_al.MobCmdALViewModel

class MobEncaissementFragment : Fragment() {

    private val TAG: String = this.javaClass.name.toString()

    private lateinit var binding: FragmentMobEncaissementBinding
    private val mobCmdALViewModel: MobCmdALViewModel by activityViewModels()
    private var mobEncaissementAdapter: MobEncaissementAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_mob_encaissement, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val child = layoutInflater.inflate(R.layout.no_data_found, null)
        (binding.encaissementLv.getParent() as ViewGroup).addView(child)
        binding.encaissementLv.setEmptyView(child)

        mobEncaissementAdapter =
            MobEncaissementAdapter(requireActivity(), mobCmdALViewModel.getEncaissements())

        binding.encaissementLv.adapter = mobEncaissementAdapter

        binding.encaissementLv.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                TODO("Not yet implemented")


            }
        }

        val totalPanier = mobCmdALViewModel.getLivraison().montanT_NET
        val totalEncaissement = mobCmdALViewModel.sumEncaissement()
        val reste = totalPanier - totalEncaissement

        binding.totalPanierValueTv.setText(totalPanier.toString())
        binding.panierEncaissementValueTv.setText(totalEncaissement.toString())
        binding.panierResteValueTv.setText(reste.toString())


        binding.ajouterEncaissementBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mobEncaissementFragment_to_mobEncaissementTypeFragment)
        }


    }
}