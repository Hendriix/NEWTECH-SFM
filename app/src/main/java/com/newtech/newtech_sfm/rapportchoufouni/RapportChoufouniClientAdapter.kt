package com.newtech.newtech_sfm.rapportchoufouni

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newtech.newtech_sfm.Metier.RapportChoufouniClient
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.databinding.RapportChoufouniClientItemBinding
import java.util.ArrayList

class RapportChoufouniClientAdapter(


    private val dataSet: ArrayList<RapportChoufouniClient>?,
    private val context: Context?
) :
    RecyclerView.Adapter<RapportChoufouniClientAdapter.ViewHolder>() {

    inner class ViewHolder(val binding : RapportChoufouniClientItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = RapportChoufouniClientItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val rapportChoufouniClient = dataSet!!.get(position)

        val dialog =  Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.rapport_choufouni_article);

        val width = (context.resources.displayMetrics.widthPixels * 0.90).toInt()
        val height = (context.resources.displayMetrics.heightPixels * 0.90).toInt()

        dialog.window!!.setLayout(width,height)

        val articleRecyclerView : RecyclerView = dialog.findViewById(R.id.rapport_choufouni_article_rv)

        articleRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )

        val rapportChoufouniArticleAdapter = RapportChoufouniArticleAdapter(rapportChoufouniClient.rapportChoufouniArticle,context)

        articleRecyclerView.adapter = rapportChoufouniArticleAdapter

        viewHolder.binding.clientTv.text = "Nom: "+rapportChoufouniClient.CLIENT
        viewHolder.binding.tourneeTv.text = "Tournée: "+rapportChoufouniClient.TOURNEE
        viewHolder.binding.adresseTv.text = "Adresse: "+rapportChoufouniClient.ADRESSE
        viewHolder.binding.telephoneTv.text = "Téléphone: "+rapportChoufouniClient.TELEPHONE
        viewHolder.binding.chiffreAffaireTv.text = "Chiffre d'affaire: "+rapportChoufouniClient.CHIFFRE_AFFAIRE+" Dhs"
        viewHolder.binding.nbImageTv.text = "Nombre d'image: "+rapportChoufouniClient.CONTRAT_IMAGE

        if(rapportChoufouniClient.CONTRAT_EXIST == 0){
            viewHolder.binding.isChoufouniIv.setImageResource(R.drawable.contrat_possible_ic)
        }else{
            viewHolder.binding.isChoufouniIv.setImageResource(R.drawable.contrat_exist_ic)
        }

        viewHolder.itemView.setOnClickListener(View.OnClickListener {
            dialog.show()
            true
        })

    }

    override fun getItemCount() : Int {
        return dataSet!!.size
    }

}