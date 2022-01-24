package com.newtech.newtech_sfm.merchandising.article

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import com.newtech.newtech_sfm.Configuration.Common
import com.newtech.newtech_sfm.Metier.Article
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.databinding.FragmentDialogVisibiliteBinding
import com.newtech.newtech_sfm.model.VisibiliteViewModel


class VisibiliteDialogFragment : DialogFragment() {

    /** The system calls this to get the DialogFragment's layout, regardless
    of whether it's being displayed as a dialog or an embedded fragment. */

    private lateinit var binding: FragmentDialogVisibiliteBinding
    private val sharedViewModel: VisibiliteViewModel by activityViewModels()
    lateinit var article: LiveData<Article>
    var existe: Int = 0
    var visible: Int = 0
    var articlePrix: Double = 0.0
    var promotion: String = ""
    var position: Int = 0
    var ruptureMagazin: Int = 0
    var stockMagazin: Double = 0.0
    var ruptureRayon: Int = 0
    var stockRayon: Double = 0.0
    var commentaire: String = ""


    var articleVisiteImageView: ImageView? = null
    private var articleNomTextView: TextView? = null
    var existeCheckBox: CheckBox? = null
    var visibleCheckBox: CheckBox? = null
    var articlePrixEditText: EditText? = null
    var positionGridLayout: GridLayout? = null
    //Promotion Spinner
    var promotionSpinner: Spinner? = null
    var promotionDetailEditText: EditText? = null
    var ruptureMagazinCheckBox: CheckBox? = null
    var stockMagazinEditText: EditText? = null
    var ruptureRayonCheckBox: CheckBox? = null
    var stockRayonEditText: EditText? = null
    var commentaireEditText: EditText? = null
    var validerVisibiliteBtn : Button? = null
    var annulerVisibiliteBtn : Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        binding = DataBindingUtil.inflate<FragmentDialogVisibiliteBinding>(
            inflater,
            R.layout.fragment_dialog_visibilite, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        article = sharedViewModel.article

        articleVisiteImageView = binding.articleVisibiliteIv
        articleNomTextView = binding.articleNomTv
        existeCheckBox = binding.existeCb
        visibleCheckBox = binding.visibleCb
        articlePrixEditText = binding.articlePriceEt
        positionGridLayout = binding.positionGl
        //Promotion Spinner
        promotionSpinner = binding.promoSpinner
        promotionDetailEditText = binding.promotionDetailEt
        ruptureMagazinCheckBox = binding.ruptureMagazinCb
        stockMagazinEditText = binding.stockMagazinEt
        ruptureRayonCheckBox = binding.ruptureRayonCb
        stockRayonEditText = binding.stockRayonEt
        commentaireEditText = binding.commentaireEt
        validerVisibiliteBtn = binding.validerVfBtn
        annulerVisibiliteBtn = binding.annulerVfBtn


        articleNomTextView!!.text = article.value?.articlE_DESIGNATION1

        val promotions = arrayOf("AUCUNE","REMISE","GRATUITE","AUTRE")

        val spinnerArrayAdapter = ArrayAdapter<String>(
            requireContext(), android.R.layout.simple_spinner_item, promotions
        )
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        promotionSpinner!!.adapter = spinnerArrayAdapter

        //val positions: HashMap<Int, String>? = Common.getPositions()
        setArticleImage(article, articleVisiteImageView!!)

        val childCount: Int = positionGridLayout!!.childCount
        for (i in 0..(childCount - 1)) {

            val clickedImageView: ImageView = positionGridLayout!!.getChildAt(i) as ImageView
            clickedImageView.setOnClickListener(View.OnClickListener {
                position = i + 1
            })
        }

        validerVisibiliteBtn!!.setOnClickListener { view: View ->

            if(checkValidation()){
                saveVisibiliteLigne()
                view.findNavController()
                    .navigate(R.id.action_visibiliteDialogFragment_to_articleFragment)
            }

        }

        annulerVisibiliteBtn!!.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(R.id.action_visibiliteDialogFragment_to_articleFragment)
        }

    }


    /** The system calls this only when creating the layout in a dialog. */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    private fun setArticleImage(article: LiveData<Article>, articleVisiteImageView: ImageView) {

        lateinit var base64Image: String

        if (article.value?.image.toString().contains(",")) {
            base64Image = article.value!!.image.toString().split(",").toTypedArray()[1]
        } else {
            base64Image = article.value!!.image
        }


        if (base64Image.length < 10 || base64Image == "" || base64Image == null) {
            if (Common.getImageId(
                    context,
                    article.value!!.articlE_CODE.lowercase()
                ) > 0
            ) {
                articleVisiteImageView.setImageResource(
                    Common.getImageId(
                        context,
                        article.value!!.articlE_CODE.lowercase()
                    )
                )
            } else {
                articleVisiteImageView.setImageResource(
                    Common.getImageId(
                        context,
                        "bouteille_inconnu2"
                    )
                )
            }
        } else {
            val decodedString: ByteArray = Base64.decode(base64Image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            articleVisiteImageView.setImageBitmap(
                Bitmap.createScaledBitmap(
                    decodedByte,
                    decodedByte.width,
                    decodedByte.height,
                    false
                )
            )
        }

    }

    fun saveVisibiliteLigne() {

            val visibiliteLigne =
                sharedViewModel.getVisibiliteLigneByArticleCode(article.value?.articlE_CODE.toString())

            existe = intFromBoolean(binding.existeCb.isChecked)
            visibiliteLigne.referencement = existe

            visible = intFromBoolean(binding.visibleCb.isChecked)
            visibiliteLigne.visibilite = visible

            articlePrix = binding.articlePriceEt.text.toString().toDoubleOrNull()!!
            visibiliteLigne.prix = articlePrix

            //PROMOTIONS

            val promotionStr =
            promotionSpinner!!.adapter.getItem(promotionSpinner!!.getSelectedItemPosition()) as String

            promotion = promotionStr

            if (!TextUtils.equals(promotionStr, "AUCUNE")) {

                promotion = promotionStr+" "+promotionDetailEditText!!.text.toString()
            }

            visibiliteLigne.promotion = promotion
            // Position
            visibiliteLigne.position = position

            ruptureMagazin = intFromBoolean(binding.ruptureMagazinCb.isChecked)
            visibiliteLigne.rupturE_MAGASIN = ruptureMagazin

            stockMagazin = binding.stockMagazinEt.text.toString().toDoubleOrNull()!!
            visibiliteLigne.stocK_MAGASIN = stockMagazin

            ruptureRayon = intFromBoolean(binding.ruptureRayonCb.isChecked)
            visibiliteLigne.rupturE_RAYON = ruptureRayon

            stockRayon = binding.stockRayonEt.text.toString().toDoubleOrNull()!!
            visibiliteLigne.stocK_RAYON = stockRayon

            if(!binding.commentaireEt.text.toString().isEmpty()){
                commentaire = binding.commentaireEt.text.toString()
            }

            visibiliteLigne.commentairE_LIGNE = commentaire

    }

    fun intFromBoolean(checked: Boolean): Int {

        var intFromBool = 0

        if (checked) {
            intFromBool = 1
        }
        return intFromBool
    }

    private fun checkValidation(): Boolean {
        var result = true



        val strArticlePrix: String = articlePrixEditText!!.getText().toString()
        if (TextUtils.isEmpty(strArticlePrix)) {
            articlePrixEditText!!.setError("ce champs est obligatoire")
            result = false
            return result
        }

        if(position == 0){
            Toast.makeText(context, "Merci de choisir une position", Toast.LENGTH_SHORT).show()
            result = false
            return result
        }

        val promotion =
            promotionSpinner!!.adapter.getItem(promotionSpinner!!.getSelectedItemPosition()) as String
        val strPromotion = promotion

        if (!TextUtils.equals(strPromotion, "AUCUNE")) {
            if(TextUtils.isEmpty(promotionDetailEditText!!.text.toString())){
                promotionDetailEditText!!.setError("ce champs est obligatoire")
                result = false
                return result
            }

        }

        val strStockMagasin: String = stockMagazinEditText!!.getText().toString()
        if (TextUtils.isEmpty(strStockMagasin)) {
            stockMagazinEditText!!.setError("ce champs est obligatoire")
            result = false
            return result
        }


        val strStockRayon: String = stockRayonEditText!!.getText().toString()
        if (TextUtils.isEmpty(strStockRayon)) {
            stockRayonEditText!!.setError("ce champs est obligatoire")
            result = false
            return result
        }
        return result
    }


}