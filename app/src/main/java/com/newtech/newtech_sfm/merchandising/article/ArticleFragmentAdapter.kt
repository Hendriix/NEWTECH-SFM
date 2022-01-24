package com.newtech.newtech_sfm.merchandising.article

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.newtech.newtech_sfm.Configuration.Common
import com.newtech.newtech_sfm.Metier.Article
import com.newtech.newtech_sfm.Metier.VisibiliteLigne
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.model.VisibiliteViewModel

class ArticleFragmentAdapter(
    private val navController: NavController,
    private val dataSet: ArrayList<VisibiliteLigne>,
    private val context: Context?,
    private val sharedViewModel: VisibiliteViewModel
) :
    RecyclerView.Adapter<ArticleFragmentAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    val articleManager = ArticleManager(context)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        var mView: View? = null
        val article_designation: TextView
        val article_code: TextView
        val article_prix: TextView
        val article_image: ImageView
        val article_exist_image : ImageView
        val article_referencement_image : ImageView
        val article_position_image : ImageView
        val stock_rayon_image : ImageView
        val stock_magazin_image : ImageView



        init {
            // Define click listener for the ViewHolder's View.
            mView = view
            article_designation = view.findViewById(R.id.article_designation)
            article_code = view.findViewById(R.id.article_code)
            article_prix = view.findViewById(R.id.prix_article)
            article_image = view.findViewById<View>(R.id.image_article) as ImageView
            article_exist_image = view.findViewById<View>(R.id.existe_iv) as ImageView
            article_referencement_image = view.findViewById<View>(R.id.referencement_iv) as ImageView
            article_position_image = view.findViewById<View>(R.id.position_iv) as ImageView
            stock_rayon_image = view.findViewById<View>(R.id.stock_rayon_iv) as ImageView
            stock_magazin_image = view.findViewById<View>(R.id.stock_magazin_iv) as ImageView



            Common.setLocked(article_exist_image)
            Common.setLocked(article_referencement_image)
            Common.setLocked(article_position_image)
            Common.setLocked(stock_rayon_image)
            Common.setLocked(stock_magazin_image)

        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.article_custom_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val article: Article = articleManager.get(dataSet.get(position).articlE_CODE)
        val visibiliteLigne : VisibiliteLigne = dataSet.get(position)


        lateinit var base64Image: String

        viewHolder.article_designation.text = article.articlE_DESIGNATION1
        viewHolder.article_code.text = article.articlE_CODE
        viewHolder.article_prix.text = visibiliteLigne.prix.toString()

        when(visibiliteLigne.position){

            1 -> viewHolder.article_position_image.setImageResource(R.drawable.ic_left_top)
            2 -> viewHolder.article_position_image.setImageResource(R.drawable.ic_middle_top)
            3 -> viewHolder.article_position_image.setImageResource(R.drawable.ic_right_top)
            4 -> viewHolder.article_position_image.setImageResource(R.drawable.ic_left_middle)
            5 -> viewHolder.article_position_image.setImageResource(R.drawable.ic_milieu_milieu)
            6 -> viewHolder.article_position_image.setImageResource(R.drawable.ic_right_middle)
            7 -> viewHolder.article_position_image.setImageResource(R.drawable.ic_left_bottom)
            8 -> viewHolder.article_position_image.setImageResource(R.drawable.ic_middle_bottom)
            9 -> viewHolder.article_position_image.setImageResource(R.drawable.ic_right_bottom)
        }

        if (article.image.toString().contains(",")) {
            base64Image = article.image.toString().split(",").toTypedArray()[1]
        } else {
            base64Image = article.image
        }


        if (base64Image.length < 10 || base64Image == "" || base64Image == null) {
            if (Common.getImageId(
                    context,
                    article.articlE_CODE.lowercase()
                )!! > 0
            ) {
                viewHolder.article_image.setImageResource(
                    Common.getImageId(
                        context,
                        article.articlE_CODE.lowercase()
                    )
                )
                viewHolder.article_exist_image.setImageResource(
                    Common.getImageId(
                        context,
                        article.articlE_CODE.lowercase()
                    )
                )
            } else {
                viewHolder.article_image.setImageResource(
                    Common.getImageId(
                        context,
                        "bouteille_inconnu2"
                    )
                )

                viewHolder.article_exist_image.setImageResource(
                    Common.getImageId(
                        context,
                        "bouteille_inconnu2"
                    )
                )
            }
        } else {
            val decodedString: ByteArray = Base64.decode(base64Image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            viewHolder.article_image.setImageBitmap(
                Bitmap.createScaledBitmap(
                    decodedByte,
                    decodedByte.width,
                    decodedByte.height,
                    false
                )
            )

            viewHolder.article_exist_image.setImageBitmap(
                Bitmap.createScaledBitmap(
                    decodedByte,
                    decodedByte.width,
                    decodedByte.height,
                    false
                )
            )

        }

        when (visibiliteLigne.referencement){
            1 -> Common.setUnlocked(viewHolder.article_exist_image)
        }

        when (visibiliteLigne.visibilite){
            1 -> Common.setUnlocked(viewHolder.article_referencement_image)
        }

        when (visibiliteLigne.rupturE_MAGASIN){
            1 -> Common.setUnlocked(viewHolder.stock_magazin_image)
        }

        when (visibiliteLigne.rupturE_RAYON){
            1 -> Common.setUnlocked(viewHolder.stock_rayon_image)
        }



        viewHolder.mView?.setOnClickListener{

            sharedViewModel.setArticle(article)
            navController.navigate(R.id.action_articleFragment_to_visibiliteDialogFragment)
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size


}
