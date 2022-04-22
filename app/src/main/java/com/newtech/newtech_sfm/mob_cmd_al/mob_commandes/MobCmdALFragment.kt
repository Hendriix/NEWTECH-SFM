package com.newtech.newtech_sfm.mob_cmd_al.mob_commandes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.databinding.FragmentMobCmdBinding
import com.newtech.newtech_sfm.mob_cmd_al.MobCmdALViewModel

class MobCmdALFragment() : Fragment() {

    private lateinit var binding: FragmentMobCmdBinding
    private val mobCmdALViewModel: MobCmdALViewModel by activityViewModels()
    private lateinit var mobCmdALFragmentAdapter: MobCmdALFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_mob_cmd, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController: NavController = this.findNavController()

        val bundle = this.arguments
        mobCmdALViewModel.setClientCode(bundle!!.getString("CLIENT_CODE")!!)

        binding.determinateBar.visibility = View.VISIBLE

        mobCmdALViewModel.getMobCmdAL()
            .observe(viewLifecycleOwner) { mobCmdAL ->
                // update UI
                binding.determinateBar.visibility = View.GONE

                mobCmdALFragmentAdapter =
                    MobCmdALFragmentAdapter(navController, mobCmdAL, context)
                binding.mobCmdRecyclerView.adapter = mobCmdALFragmentAdapter
                mobCmdALFragmentAdapter.notifyDataSetChanged()
            }

    }

}