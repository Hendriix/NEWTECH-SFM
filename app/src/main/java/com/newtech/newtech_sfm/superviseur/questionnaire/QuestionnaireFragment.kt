package com.newtech.newtech_sfm.superviseur.questionnaire


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.newtech.newtech_sfm.Metier.Questionnaire
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.databinding.FragmentQuestionnaireBinding
import com.newtech.newtech_sfm.superviseur.QuestionnaireViewModel
import kotlinx.android.synthetic.main.fragment_questionnaire.*

class QuestionnaireFragment() : Fragment() {

    private lateinit var binding: FragmentQuestionnaireBinding
    private val questionnaireViewModel: QuestionnaireViewModel by activityViewModels()
    private lateinit var questionnaireFragmentAdapter: QuestionnaireFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentQuestionnaireBinding>(
            inflater,
            R.layout.fragment_questionnaire, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // getting the bundle back from the android
        val bundle = this.arguments
        Log.d("ReponseFragment", "onViewCreated: " + bundle!!.getString("CLIENT_CODE"))

        questionnaireViewModel.setClientCode(bundle!!.getString("CLIENT_CODE")!!)
        questionnaireViewModel.setVisiteCode(bundle!!.getString("VISITE_CODE")!!)
        questionnaireViewModel.setDistributeurCode(bundle!!.getString("DISTRIBUTEUR_CODE")!!)
        questionnaireViewModel.setUtilisateurCode(bundle!!.getString("UTILISATEUR_CODE")!!)

        val navController: NavController = this.findNavController()

        binding.determinateBar.visibility = View.VISIBLE

        questionnaireViewModel.getQuestionnaires()
            .observe(viewLifecycleOwner, Observer<List<Questionnaire>> { questionnaires ->
                // update UI
                binding.determinateBar.visibility = View.GONE

                questionnaireFragmentAdapter =
                    QuestionnaireFragmentAdapter(navController, questionnaires, context)
                binding.questionnaireRecyclerView.adapter = questionnaireFragmentAdapter
                questionnaireFragmentAdapter.notifyDataSetChanged()
            })

        /*questionnaireRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )*/

    }


}

