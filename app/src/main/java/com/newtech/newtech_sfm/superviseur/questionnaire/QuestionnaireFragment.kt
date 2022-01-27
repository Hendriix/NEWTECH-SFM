package com.newtech.newtech_sfm.superviseur.questionnaire

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newtech.newtech_sfm.Metier.LogSync
import com.newtech.newtech_sfm.Metier.Questionnaire
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.databinding.FragmentArticleBinding
import com.newtech.newtech_sfm.databinding.FragmentQuestionnaireBinding
import com.newtech.newtech_sfm.superviseur.QuestionnaireViewModel
import kotlinx.android.synthetic.main.fragment_questionnaire.*

class QuestionnaireFragment() : QuestionnaireFragmentView, Fragment() {

    private lateinit var binding: FragmentQuestionnaireBinding
    private val questionnaireViewModel : QuestionnaireViewModel by activityViewModels()
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

        val questionnaireRecyclerView : RecyclerView = binding.questionnaireRecyclerView

        questionnaireRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )

        questionnaireViewModel.getQuestionnaires().observe(viewLifecycleOwner,  Observer<List<Questionnaire>>{ questionnaires ->
            // update UI
            questionnaireFragmentAdapter = QuestionnaireFragmentAdapter(questionnaires,context)
            questionnaire_recycler_view.adapter = questionnaireFragmentAdapter
            questionnaireFragmentAdapter.notifyDataSetChanged()
        })



    }


}

