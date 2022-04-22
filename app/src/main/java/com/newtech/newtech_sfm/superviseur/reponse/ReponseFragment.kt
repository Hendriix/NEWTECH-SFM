package com.newtech.newtech_sfm.superviseur.reponse

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.newtech.newtech_sfm.Activity.ClientActivity
import com.newtech.newtech_sfm.Metier.QuestionReponse
import com.newtech.newtech_sfm.Metier.Reponse
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.databinding.FragmentReponseBinding
import com.newtech.newtech_sfm.superviseur.QuestionnaireViewModel


class ReponseFragment : Fragment() {

    private lateinit var binding: FragmentReponseBinding
    private val questionnaireViewModel: QuestionnaireViewModel by activityViewModels()
    private lateinit var linearLayout: LinearLayout
    private var isFormValid: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentReponseBinding>(
            inflater,
            R.layout.fragment_reponse, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments

        val questionnaireCode = bundle!!.getString("QUESTIONNAIRE_CODE")

        questionnaireViewModel.setQuestionnaireCode(questionnaireCode!!)

        Log.d(
            "ReponseFragment",
            "onViewCreated client code: " + questionnaireViewModel.getClientCode()
        )

        binding.determinateBar.visibility = View.VISIBLE
        binding.controlBtnLl.visibility = View.GONE

        questionnaireViewModel.getQuestionnaireQR()
            .observe(viewLifecycleOwner) {

                binding.determinateBar.visibility = View.GONE
                binding.controlBtnLl.visibility = View.VISIBLE

                val questionReponseList: ArrayList<QuestionReponse>? = it.TB_QUESTION_REPONSES


                //binding.qrLl.addView(cardview)

                if (questionReponseList!!.size > 0) {

                    for (i in 0..questionReponseList.size - 1) run {

                        val questionReponse: QuestionReponse = questionReponseList[i]


                        createLinearLayout(questionReponse)

                        /*TEXT VIEW*/
                        createTextView(questionReponse)

                        if (questionReponse.TB_REPONSES!!.size > 0) {

                            /*RADIO GROUP*/
                            val radioGroup = createRadioGroup(view, questionReponse)

                            val reponseList: ArrayList<Reponse>? = questionReponse.TB_REPONSES

                            for (j in 0..reponseList!!.size - 1) {


                                val reponse: Reponse = reponseList[j]
                                Log.d("ReponseFragment", "onViewCreated: " + reponse.toString())

                                if (reponse.REPONSE_CASE == 1) {

                                    Log.d(
                                        "ReponseFragment1",
                                        "onViewCreated: " + reponse.toString()
                                    )

                                    radioGroup.addView(createRadioButton(view, reponse))

                                    //cardView.addView(radioGroup)
                                    //binding.qrLl.addView(radioButton)

                                } else if (reponse.REPONSE_CASE == 2) {

                                    Log.d(
                                        "ReponseFragment2",
                                        "onViewCreated: " + reponse.toString()
                                    )

                                    createCheckBox(view, reponse, questionReponse)

                                } else if (reponse.REPONSE_CASE == 3) {

                                    Log.d(
                                        "ReponseFragment3",
                                        "onViewCreated: " + reponse.toString()
                                    )

                                    createTextInputField(questionReponse)

                                } else {

                                    Log.d("ReponseFragment3", "onViewCreated: nothing was found")

                                }


                            }

                            if (radioGroup.size > 0) {
                                val linearLayout =
                                    binding.qrLl.findViewWithTag<LinearLayout>(questionReponse.QUESTIONNAIRE_CODE + questionReponse.QUESTION_CODE)
                                linearLayout.addView(radioGroup)
                                binding.qrLl.removeView(linearLayout)
                                binding.qrLl.addView(linearLayout)
                            }

                        }


                    }
                }
            }

        binding.validerVrBtn.setOnClickListener {

            /*CHECK WHETHER FORM IS VALID OR NOT*/

            isFormValid = questionnaireViewModel.validateForm(requireView())
            val dialogTextMessage = getString(R.string.validate_message)

            if (isFormValid) {
                showNoticeDialog(dialogTextMessage)
            }

        }

        binding.annulerVrBtn.setOnClickListener {
            val dialogtextMessage = getString(R.string.cancel_message)
            showNoticeDialog(dialogtextMessage)
        }

    }

    fun createLinearLayout(questionReponse: QuestionReponse) {

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
        linearLayout.tag = questionReponse.QUESTIONNAIRE_CODE + questionReponse.QUESTION_CODE
        binding.qrLl.addView(linearLayout)


    }

    fun createTextView(questionReponse: QuestionReponse) {

        val linearLayout =
            binding.qrLl.findViewWithTag<LinearLayout>(questionReponse.QUESTIONNAIRE_CODE + questionReponse.QUESTION_CODE)

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
        textView.text = questionReponse.QUESTION_NOM
        textView.tag = questionReponse.QUESTION_CODE
        textView.setPadding(20, 10, 20, 10)
        textView.layoutParams = params

        linearLayout.addView(textView)

        binding.qrLl.removeView(linearLayout)

        binding.qrLl.addView(linearLayout)

    }

    fun createTextInputField(questionReponse: QuestionReponse) {

        val linearLayout =
            binding.qrLl.findViewWithTag<LinearLayout>(questionReponse.QUESTIONNAIRE_CODE + questionReponse.QUESTION_CODE)

        val textInputLayout = context?.let { TextInputEditText(it) }
        //val textInputEditText = TextInputEditText(textInputLayout!!.context)

        val params: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        params.setMargins(0, 20, 0, 20)

        textInputLayout!!.layoutParams = params
        //textInputLayout!!.layoutParams = view.layoutParams
        textInputLayout.tag = questionReponse.QUESTION_CODE
        textInputLayout.setPadding(20, 10, 20, 10)
        textInputLayout.imeOptions = EditorInfo.IME_ACTION_NEXT

        textInputLayout.onFocusChangeListener =
            OnFocusChangeListener { v, hasFocus ->

                if (!hasFocus) {

                    questionnaireViewModel.addResultEditText(
                        questionReponse,
                        textInputLayout.text.toString()
                    )

                }
                Log.d(
                    "ReponseFragment",
                    "createTextInputField: size : " + questionnaireViewModel.resultatList.size
                )
            }


        linearLayout.addView(textInputLayout)
        binding.qrLl.removeView(linearLayout)
        binding.qrLl.addView(linearLayout)

    }

    fun createRadioButton(view: View, reponse: Reponse): RadioButton {

        val radioButton = RadioButton(context)
        radioButton.layoutParams = view.layoutParams
        radioButton.text = reponse.REPONSE_NOM
        radioButton.tag = reponse.REPONSE_CODE
        radioButton.layoutParams.width =
            ViewGroup.LayoutParams.MATCH_PARENT
        radioButton.layoutParams.height =
            ViewGroup.LayoutParams.WRAP_CONTENT

        return radioButton
    }

    fun createRadioGroup(view: View, questionReponse: QuestionReponse): RadioGroup {

        val radioGroup = RadioGroup(context)
        radioGroup.orientation = RadioGroup.VERTICAL
        radioGroup.layoutParams = view.layoutParams
        radioGroup.layoutParams.width =
            ViewGroup.LayoutParams.MATCH_PARENT
        radioGroup.layoutParams.height =
            ViewGroup.LayoutParams.WRAP_CONTENT

        radioGroup.tag = questionReponse.QUESTION_CODE

        radioGroup.setOnCheckedChangeListener { group, checkedId ->

            val radioButton: RadioButton = view.findViewById(checkedId)
            questionnaireViewModel.addResultRadioButton(radioButton.tag.toString(), questionReponse)

            Log.d(
                "ReponseFragment",
                "client code : " + questionnaireViewModel.getClientCode()
            )

            Log.d(
                "ReponseFragment",
                "createRadioGroup: size : " + questionnaireViewModel.resultatList.size
            )

        }

        return radioGroup
    }

    fun createCheckBox(view: View, reponse: Reponse, questionReponse: QuestionReponse) {

        val linearLayout =
            binding.qrLl.findViewWithTag<LinearLayout>(questionReponse.QUESTIONNAIRE_CODE + questionReponse.QUESTION_CODE)

        val checkBox = CheckBox(context)
        checkBox.layoutParams = view.layoutParams
        checkBox.text = reponse.REPONSE_NOM
        checkBox.tag = reponse.REPONSE_CODE
        checkBox.layoutParams.width =
            ViewGroup.LayoutParams.MATCH_PARENT
        checkBox.layoutParams.height =
            ViewGroup.LayoutParams.WRAP_CONTENT
        checkBox.layoutParams = view.layoutParams


        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            questionnaireViewModel.addResultCheckBox(
                buttonView.tag.toString(),
                isChecked,
                questionReponse
            )
            Log.d(
                "ReponseFragment",
                "createRadioGroup: " + questionnaireViewModel.resultatList.size
            )
        }

        linearLayout.addView(checkBox)
        binding.qrLl.removeView(linearLayout)

        binding.qrLl.addView(linearLayout)
    }

    fun showNoticeDialog(dialogTextMessage: String) {

        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.custom_confirm_dialog)

        val confirmButton: Button = dialog.findViewById(R.id.btn_oui)
        val cancelButton: Button = dialog.findViewById(R.id.btn_non)
        val dialogText: TextView = dialog.findViewById(R.id.alertencaissement)
        val navController: NavController = this.findNavController()

        dialogText.text = dialogTextMessage

        confirmButton.setOnClickListener {
            dialog.dismiss()
            if (isFormValid) {
                navController.navigate(R.id.action_reponseFragment_to_reponseRecapFragment)
            } else {
                val intent = Intent(this.activity, ClientActivity::class.java)
                startActivity(intent)
                this.requireActivity().finish()
            }
        }

        cancelButton.setOnClickListener { dialog.dismiss() }

        dialog.show()

    }

}


