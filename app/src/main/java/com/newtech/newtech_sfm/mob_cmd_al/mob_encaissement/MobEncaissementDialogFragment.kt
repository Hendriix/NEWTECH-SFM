package com.newtech.newtech_sfm.mob_cmd_al.mob_encaissement

import android.os.Bundle
import android.os.UserManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.newtech.newtech_sfm.Activity.EncaissementActivity
import com.newtech.newtech_sfm.Metier.Encaissement
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.databinding.FragmentEncaissementEspeceDialogBinding
import com.newtech.newtech_sfm.mob_cmd_al.MobCmdALViewModel

class MobEncaissementDialogFragment : DialogFragment() {

    private val TAG: String = this.javaClass.name.toString()

    private lateinit var binding: FragmentEncaissementEspeceDialogBinding
    private val mobCmdALViewModel: MobCmdALViewModel by activityViewModels()

    var userManager : UserManager? = null

    /*override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.questionnaire_item_round_corner);
        return inflater.inflate(R.layout.encaissement_espece_dialog_layout, container, false)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_encaissement_espece_dialog, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //userManager = UserManager(context)
        val valeurLivraison =
            mobCmdALViewModel.getLivraison().montanT_NET - mobCmdALViewModel.sumEncaissement()

        binding.valeurLivraisonEt.setText(valeurLivraison.toString())
        binding.valeurLivraisonEt.isEnabled = false
        binding.montantEncaissementEt.setText(valeurLivraison.toString())


        binding.validerEnncaissementBtn.setOnClickListener {

            if (validateAmount()) {
                val strMontant = binding.montantEncaissementEt.getText().toString().toDouble()
                /*val encaissement =
                    Encaissement(,
                        EncaissementActivity.livraison,
                        "ESPECE",
                        strMontant)*/
            }
        }

    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun validateAmount(): Boolean {
        var result = true
        val strMontant: String = binding.montantEncaissementEt.getText().toString()
        if (TextUtils.isEmpty(strMontant)) {
            binding.montantEncaissementEt.setError("ce champs est obligatoire")
            result = false
        }
        if (!TextUtils.isEmpty(strMontant)) {
            if (Math.abs(strMontant.toDouble()) == 0.0) {
                binding.montantEncaissementEt.setError("doit être différent de zero")
                result = false
            }
        }
        return result
    }


}