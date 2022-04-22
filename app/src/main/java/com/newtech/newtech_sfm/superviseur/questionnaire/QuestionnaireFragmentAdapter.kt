package com.newtech.newtech_sfm.superviseur.questionnaire

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.newtech.newtech_sfm.Metier.Questionnaire
import com.newtech.newtech_sfm.R

class QuestionnaireFragmentAdapter(
    private val navController: NavController,
    private val dataSet: List<Questionnaire>,
    private val context: Context?
) :
    RecyclerView.Adapter<QuestionnaireFragmentAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mView: View? = null

        val nom_questionnaire: TextView
        val description_questionnaire: TextView

        init {

            mView = view

            nom_questionnaire = view.findViewById(R.id.nom_questionnaire_tv)
            description_questionnaire = view.findViewById(R.id.questionnaire_description_tv)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.questionnaire_custom_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val questionnaire = dataSet.get(position)

        viewHolder.nom_questionnaire.text = questionnaire.QUESTIONNAIRE_NOM
        viewHolder.description_questionnaire.text = questionnaire.DESCRIPTION

        viewHolder.mView?.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("QUESTIONNAIRE_CODE", questionnaire.QUESTIONNAIRE_CODE)
            navController.navigate(R.id.action_questionnaireFragment_to_reponseFragment, bundle)

        }

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}