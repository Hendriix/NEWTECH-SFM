package com.newtech.newtech_sfm.merchandising.recap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.databinding.FragmentArticleRecapBinding
import com.newtech.newtech_sfm.model.VisibiliteViewModel


class ArticleRecapFragment : Fragment() {

    private val sharedViewModel: VisibiliteViewModel by activityViewModels()
    private lateinit var binding: FragmentArticleRecapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentArticleRecapBinding>(
            inflater,
            R.layout.fragment_article_recap, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            viewModel = sharedViewModel
        }

        val textView : TextView = binding.quantiteTestTv

    }

}