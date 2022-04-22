package com.newtech.newtech_sfm.superviseur.recap

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.newtech.newtech_sfm.Activity.ClientActivity
import com.newtech.newtech_sfm.Metier.Question
import com.newtech.newtech_sfm.Metier.Reponse
import com.newtech.newtech_sfm.Metier.Resultat
import com.newtech.newtech_sfm.Metier_Manager.VisiteManager
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.databinding.FragmentReponseRecapBinding
import com.newtech.newtech_sfm.superviseur.QuestionnaireViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ReponseRecapFragment : Fragment() , ReponseRecapPresenter.ReponseRecapView{

    private lateinit var binding: FragmentReponseRecapBinding
    private val questionnaireViewModel: QuestionnaireViewModel by activityViewModels()
    private lateinit var linearLayout: LinearLayout
    private lateinit var reponseRecapPresenter : ReponseRecapPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_reponse_recap, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reponseRecapPresenter = ReponseRecapPresenter(this)

        val questionList = questionnaireViewModel.getQuestionList()

        for (i in 0..questionList.size - 1) {

            val question = questionList[i]

            if (questionnaireViewModel.checkIfQuestionHadResultat(question.QUESTION_CODE)) {
                /*LINEARLAYOUT FOR QUESTION AND REPONSE LIST*/
                createLinearLayout(question)

                /* TEXT VIEW FOR QUESTION */
                createQuestionTextView(question)

                val resultatList =
                    questionnaireViewModel.getResultatListByQuestionCode(question.QUESTION_CODE)

                for (j in 0..resultatList.size - 1) {

                    val resultat = resultatList[j]
                    val reponse =
                        questionnaireViewModel.getReponseByReponseCode(resultat.REPONSE_CODE)

                    /*TEXT VIEW FOR REPONSE*/
                    createReponseTextView(reponse, resultat)


                }
            }


        }

        binding.validerVrBtn.setOnClickListener {
            showNoticeDialog(getString(R.string.validate_message))
        }

        binding.annulerVrBtn.setOnClickListener {
            showNoticeDialog(getString(R.string.cancel_message))
        }
    }

    fun createLinearLayout(question: Question) {

        linearLayout = LinearLayout(requireContext())
        val layoutparams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        linearLayout.orientation = LinearLayout.VERTICAL
        layoutparams.setMargins(10, 20, 10, 20)
        linearLayout.setLayoutParams(layoutparams)
        linearLayout.setPadding(25, 25, 25, 25)
        linearLayout.elevation = 20F
        linearLayout.setBackgroundResource(R.drawable.questionnaire_item_round_corner)
        linearLayout.tag = question.QUESTION_CODE
        binding.recapLl.addView(linearLayout)


    }

    fun createQuestionTextView(question: Question) {

        val linearLayout =
            binding.recapLl.findViewWithTag<LinearLayout>(question.QUESTION_CODE)

        val params: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        params.setMargins(0, 20, 0, 20)
        val textView = TextView(context)
        //textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17F)
        textView.textSize = 17F
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.Black))
        textView.text = question.QUESTION_NOM
        textView.setPadding(20, 10, 20, 10)
        textView.layoutParams = params

        linearLayout.addView(textView)

        binding.recapLl.removeView(linearLayout)

        binding.recapLl.addView(linearLayout)

    }

    fun createReponseTextView(reponse: Reponse, resultat: Resultat) {

        val linearLayout =
            binding.recapLl.findViewWithTag<LinearLayout>(reponse.QUESTION_CODE)

        val params: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        params.setMargins(0, 20, 0, 20)
        val textView = TextView(context)
        //textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17F)
        textView.textSize = 17F
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.Black))

        if (reponse.REPONSE_CASE.equals(3)) {
            textView.text = resultat.REPONSE_TEXT
        } else {
            textView.text = reponse.REPONSE_NOM
        }

        textView.setPadding(20, 10, 20, 10)
        textView.layoutParams = params

        linearLayout.addView(textView)

        binding.recapLl.removeView(linearLayout)

        binding.recapLl.addView(linearLayout)

    }

    fun showNoticeDialog(dialogTextMessage: String) {

        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.custom_confirm_dialog)

        val confirmButton: Button = dialog.findViewById(R.id.btn_oui)
        val cancelButton: Button = dialog.findViewById(R.id.btn_non)
        val dialogText: TextView = dialog.findViewById(R.id.alertencaissement)

        dialogText.text = dialogTextMessage

        confirmButton.setOnClickListener {
            dialog.dismiss()
            //questionnaireViewModel.synchronisationResultat(requireContext(),questionnaireViewModel.resultatList)
            reponseRecapPresenter.synchronisationResultat(requireContext(),questionnaireViewModel.resultatList)
        }

        cancelButton.setOnClickListener { dialog.dismiss() }

        dialog.show()

    }

    override fun showSuccess(string: String, boolean: Boolean) {

        showMessage(string)
        if(boolean){
            val visiteManager = VisiteManager(context)
            val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date_visite = df.format(Calendar.getInstance().time)
            visiteManager.updateVisite_VRDF(
                ClientActivity.visiteCourante.visitE_CODE,
                1,
                date_visite
            )
            val intent = Intent(this.activity, ClientActivity::class.java)
            intent.putExtra("VISITERESULTAT_CODE", 1)
            startActivity(intent)
            this.requireActivity().finish()
        }
    }

    fun showMessage(string: String){
        Toast.makeText(context,string,Toast.LENGTH_LONG).show()
    }

}