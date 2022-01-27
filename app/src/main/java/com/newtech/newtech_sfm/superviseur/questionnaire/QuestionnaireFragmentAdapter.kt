package com.newtech.newtech_sfm.superviseur.questionnaire

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.newtech.newtech_sfm.Metier.Questionnaire
import com.newtech.newtech_sfm.R

class QuestionnaireFragmentAdapter (
    private val dataSet: List<Questionnaire>,
    private val context: Context?
) :
    RecyclerView.Adapter<QuestionnaireFragmentAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mView: View? = null
        val code_questionnaire: TextView
        val nom_questionnaire: TextView
        val departement_questionnaire: TextView

        init {

            mView = view
            code_questionnaire = view.findViewById(R.id.code_questionnaire_tv)
            nom_questionnaire = view.findViewById(R.id.nom_questionnaire_tv)
            departement_questionnaire = view.findViewById(R.id.departement_questionnaire_tv)

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

        viewHolder.code_questionnaire.text = questionnaire.ID.toString()
        viewHolder.nom_questionnaire.text = questionnaire.QUESTIONNAIRE_NOM
        viewHolder.departement_questionnaire.text = questionnaire.DEPARTEMENT

    }

    override fun getItemCount() : Int {
        return dataSet.size
    }

}