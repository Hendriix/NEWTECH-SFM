package com.newtech.newtech_sfm.mob_cmd_al.mob_encaissement

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.databinding.FragmentMobEncaissementTypeBinding
import com.newtech.newtech_sfm.mob_cmd_al.MobCmdALViewModel

class MobEncaissementTypeFragment : Fragment(){

    private val TAG: String = this.javaClass.name.toString()

    private lateinit var binding: FragmentMobEncaissementTypeBinding
    private val mobCmdALViewModel: MobCmdALViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_mob_encaissement_type, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.encaissementEspeceBtn.setOnClickListener{
            val mobEncaissementDialogFragment = MobEncaissementDialogFragment()
            mobEncaissementDialogFragment.show(parentFragmentManager,"mobEncaissementDialogFragment")
        }

    }

}