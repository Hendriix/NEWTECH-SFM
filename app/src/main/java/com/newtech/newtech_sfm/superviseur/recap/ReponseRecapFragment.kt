package com.newtech.newtech_sfm.superviseur.recap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.newtech.newtech_sfm.Metier.Question
import com.newtech.newtech_sfm.Metier.QuestionReponse
import com.newtech.newtech_sfm.Metier.Reponse
import com.newtech.newtech_sfm.Metier.Resultat
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.databinding.FragmentReponseRecapBinding
import com.newtech.newtech_sfm.superviseur.QuestionnaireViewModel

class ReponseRecapFragment : ReponseRecapView, Fragment() {

    private lateinit var binding: FragmentReponseRecapBinding
    private val questionnaireViewModel: QuestionnaireViewModel by activityViewModels()
    private lateinit var linearLayout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentReponseRecapBinding>(
            inflater,
            R.layout.fragment_reponse_recap, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val questionList = questionnaireViewModel.getQuestionList()

        for (i in 0..questionList!!.size - 1) {

            val question = questionList[i]

            if (questionnaireViewModel.checkIfQuestionHadResultat(question.QUESTION_CODE)) {
                /*LINEARLAYOUT FOR QUESTION AND REPONSE LIST*/
                createLinearLayout(view, question)

                /* TEXT VIEW FOR QUESTION */
                createQuestionTextView(view, question)

                val resultatList =
                    questionnaireViewModel.getResultatListByQuestionCode(question.QUESTION_CODE)

                for (j in 0..resultatList!!.size - 1) {

                    val resultat = resultatList[j]
                    val reponse =
                        questionnaireViewModel.getReponseByReponseCode(resultat.REPONSE_CODE)

                    /*TEXT VIEW FOR REPONSE*/
                    createReponseTextView(view, reponse)


                }
            }


        }
    }

    fun createLinearLayout(view: View, question: Question) {

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

    fun createQuestionTextView(view: View, question: Question) {

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
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.Black));
        textView.text = question.QUESTION_NOM
        textView.setPadding(20, 10, 20, 10)
        textView.layoutParams = params

        linearLayout.addView(textView)

        binding.recapLl.removeView(linearLayout)

        binding.recapLl.addView(linearLayout)

    }

    fun createReponseTextView(view: View, reponse: Reponse) {

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
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.Black));
        textView.text = reponse.REPONSE_NOM
        textView.setPadding(20, 10, 20, 10)
        textView.layoutParams = params

        linearLayout.addView(textView)

        binding.recapLl.removeView(linearLayout)

        binding.recapLl.addView(linearLayout)

    }

}