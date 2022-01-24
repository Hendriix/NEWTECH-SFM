package com.newtech.newtech_sfm.merchandising.article

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.newtech.newtech_sfm.Activity.ClientActivity
import com.newtech.newtech_sfm.Metier.Article
import com.newtech.newtech_sfm.Metier.Famille
import com.newtech.newtech_sfm.Metier.VisibiliteLigne
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.databinding.FragmentArticleBinding
import com.newtech.newtech_sfm.model.VisibiliteViewModel


class ArticleFragment() : ArticleFragmentView, Fragment() {

    private val sharedViewModel: VisibiliteViewModel by activityViewModels()
    private lateinit var binding: FragmentArticleBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentArticleBinding>(
            inflater,
            R.layout.fragment_article, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner = binding.famillySpinner
        val articleRecyclerView = binding.articleRecyclerView
        val nextButton: Button = binding.validerAfBtn
        val cancelButton: Button = binding.annulerAfBtn
        val navController: NavController = this.findNavController()
        val articleFragmentPresenter = ArticleFragmentPresenter(this)


        articleRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )

        val famillyList: List<Famille> = articleFragmentPresenter.getFamillyList(context)
        var articleList: ArrayList<Article>
        var visibiliteLigneList: ArrayList<VisibiliteLigne>

        articleList = articleFragmentPresenter.getList(context)

        sharedViewModel.createVisibiliteLignes(articleList)

        spinner.adapter = FamilyItemAdapter(context, famillyList)


        Log.d("merchandising", "onViewCreated: vc : "+sharedViewModel.getVisibliteCode())
        Log.d("merchandising", "onViewCreated: famille : "+sharedViewModel.getFamillePosition())

        if (sharedViewModel.getFamillePosition() != -1) {
            spinner.setSelection(sharedViewModel.getFamillePosition())
        }


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val famille: Famille = spinner.getItemAtPosition(position) as Famille

                sharedViewModel.setFamillePosition(position)
                articleList =
                    articleFragmentPresenter.getListByFamilly(context, famille.famillE_CODE)

                visibiliteLigneList = sharedViewModel.getVisibiliteLignes(articleList)

                articleRecyclerView.adapter = ArticleFragmentAdapter(
                    navController,
                    visibiliteLigneList,
                    context,
                    sharedViewModel
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        nextButton.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(R.id.action_articleFragment_to_rayonFragment)
        }

        cancelButton.setOnClickListener { view: View ->
            val intent = Intent(this.activity, ClientActivity::class.java)
            startActivity(intent)
            this.requireActivity().finish()
        }
    }


}
