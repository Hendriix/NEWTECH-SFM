package com.newtech.newtech_sfm.merchandising.rayon

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.switchmaterial.SwitchMaterial
import com.newtech.newtech_sfm.BuildConfig
import com.newtech.newtech_sfm.Configuration.Common
import com.newtech.newtech_sfm.Metier.Article
import com.newtech.newtech_sfm.Metier.Famille
import com.newtech.newtech_sfm.Metier.VisibiliteRayon
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.databinding.FragmentDialogRayonBinding
import com.newtech.newtech_sfm.merchandising.article.FamilyItemAdapter
import com.newtech.newtech_sfm.model.VisibiliteViewModel
import java.io.File
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class RayonDialogFragment : RayonFragmentView, DialogFragment() {

    private lateinit var binding: FragmentDialogRayonBinding
    private val sharedViewModel: VisibiliteViewModel by activityViewModels()

    val CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100

    val MEDIA_TYPE_IMAGE = 1;
    val MEDIA_TYPE_VIDEO = 2

    val REQUEST_IMAGE_CAPTURE = 1

    val PREVIEW_FIRST_IMAGE = 1
    val PREVIEW_SECOND_IMAGE = 2
    var START_CAPTURE = 0
    var CAMERA_CODE = 4

    private var fileUri_first_image: Uri? = null
    private var fileUri_second_image: Uri? = null

    var first_image_captured = false
    var second_image_captured = false

    var famille_spinner: Spinner? = null
    var show_article_switch : SwitchMaterial? = null
    var article_spinner: Spinner? = null
    var rayon_largeur_editText: EditText? = null
    var rayon_hauteur_editText: EditText? = null
    var commentaire_editText: EditText? = null
    var first_image: ImageView? = null
    var second_image: ImageView? = null
    var toolbar: Toolbar? = null

    var famille_code: String? = null
    var article_code: String? = null
    var largeur: Double? = null
    var hauteur: Double? = null
    var commentaire: String? = null


    // directory name to store captured images and videos
    val IMAGE_DIRECTORY_NAME = "Hello Camera"


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
        binding = DataBindingUtil.inflate<FragmentDialogRayonBinding>(
            inflater,
            R.layout.fragment_dialog_rayon, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var articleList: ArrayList<Article>
        val rayonFragmentPresenter = RayonFragmentPresenter(this)


        show_article_switch = binding.showArticleSwitch
        //toolbar = binding.dialogRayonToolbar
        first_image = binding.firstImage
        second_image = binding.secondImage

        rayon_largeur_editText = binding.rayonLargeurEt
        rayon_hauteur_editText = binding.rayonHauteurEt
        famille_spinner = binding.familleSpinner
        article_spinner = binding.articleSpinner
        commentaire_editText = binding.rayonCommentaireEt

        val valider_vr_Btn = binding.validerVrBtn
        val annuler_vr_btn = binding.annulerVrBtn

        val famillyList: List<Famille> = rayonFragmentPresenter.getFamillyList(context)

        famille_spinner!!.adapter = FamilyItemAdapter(context, famillyList)
        famille_spinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val famille: Famille = famille_spinner!!.getItemAtPosition(position) as Famille
                articleList =
                    rayonFragmentPresenter.getListByFamilly(context, famille.famillE_CODE)

                famille_code = famille.famillE_CODE.toString()

                article_spinner!!.adapter = ArticleItemAdapter(context, articleList)

                article_spinner!!.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            val article: Article =
                                article_spinner!!.getItemAtPosition(position) as Article
                            article_code = article.articlE_CODE.toString()
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        if (context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.CAMERA
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            requestCameraPermission()
        }

        if (Common.isDeviceSupportCamera(context)) {
            binding.firstImageBtn.setOnClickListener(View.OnClickListener {
                captureImage(
                    this.PREVIEW_FIRST_IMAGE
                )
            })
            binding.secondImageBtn.setOnClickListener(View.OnClickListener {
                captureImage(
                    this.PREVIEW_SECOND_IMAGE
                )
            })
        }

        valider_vr_Btn.setOnClickListener { view: View ->

            if(checkValidation()){
                saveRayon()
                view.findNavController()
                    .navigate(R.id.action_rayonDialogFragment_to_rayonFragment)
            }

        }

        annuler_vr_btn.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(R.id.action_rayonDialogFragment_to_rayonFragment)
        }

        show_article_switch!!.setOnCheckedChangeListener { buttonView, isChecked ->
            // Responds to switch being checked/unchecked
            if(isChecked){
                binding.articleTv.visibility = View.VISIBLE
                binding.articleSpinner.visibility = View.VISIBLE
            }else{
                binding.articleTv.visibility = View.INVISIBLE
                binding.articleSpinner.visibility = View.INVISIBLE
            }

        }

    }

    private fun saveRayon() {

            val df_code: DateFormat = SimpleDateFormat("yyMMddHHmmss")
            val date_code = df_code.format(Calendar.getInstance().time)

            val df_creation: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date_creation = df_creation.format(Calendar.getInstance().time)

            val encoded_first_image =
                Common.getEncodedImage(fileUri_first_image, this.PREVIEW_FIRST_IMAGE)

            var encoded_second_image : String = ""
            if(second_image_captured){
                encoded_second_image =
                    Common.getEncodedImage(fileUri_second_image, this.PREVIEW_SECOND_IMAGE)
            }

            largeur = rayon_largeur_editText!!.text.toString().toDoubleOrNull()
            hauteur = rayon_hauteur_editText!!.text.toString().toDoubleOrNull()
            commentaire = commentaire_editText!!.text.toString()

            if(!binding.showArticleSwitch.isChecked){
                article_code = "PAR FAMILLE"
            }

            val visiibiliteRayon = VisibiliteRayon()

            visiibiliteRayon.VISIBILITE_RAYON_CODE = "VR" + date_code
            visiibiliteRayon.VISIBILITE_CODE = sharedViewModel.getVisibliteCode()
            visiibiliteRayon.TYPE_CODE = "DEFAULT"
            visiibiliteRayon.STATUT_CODE = "DEFAULT"
            visiibiliteRayon.CATEGORIE_CODE = "DEFAULT"
            visiibiliteRayon.FAMILLE_CODE = famille_code.toString()
            visiibiliteRayon.ARTICLE_CODE = article_code.toString()
            visiibiliteRayon.LARGEUR = largeur!!
            visiibiliteRayon.HAUTEUR = hauteur!!
            visiibiliteRayon.COMMENTAIRE = "to_insert"
            visiibiliteRayon.COMMENTAIRE_RAYON = commentaire.toString()
            visiibiliteRayon.DATE_VISIBILITE_RAYON = date_creation
            visiibiliteRayon.DATE_CREATION = date_creation
            visiibiliteRayon.FIRST_IMAGE = encoded_first_image
            visiibiliteRayon.SECOND_IMAGE = encoded_second_image
            visiibiliteRayon.VERSION = "non verifiee"

            sharedViewModel.setVisibiliteRayon(visiibiliteRayon)

    }

    private fun requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity,
                Manifest.permission.CAMERA
            )
        ) {
            AlertDialog.Builder(context)
                .setTitle("PERMISSION NEEDED")
                .setMessage("TO TAKE PICTURES OF CLIENTS")
                .setPositiveButton(
                    "ok"
                ) { dialog, which ->
                    ActivityCompat.requestPermissions(
                        context as Activity,
                        arrayOf(Manifest.permission.CAMERA),
                        CAMERA_CODE
                    )
                }
                .setNegativeButton(
                    "cancel"
                ) { dialog, which ->
                    dialog.dismiss()
                }
                .create().show()
        } else {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(context, "CAMERA GRANTED", Toast.LENGTH_SHORT).show()

                if (context?.let {
                        ContextCompat.checkSelfPermission(
                            it,
                            Manifest.permission.CAMERA
                        )
                    } == PackageManager.PERMISSION_GRANTED
                ) {
                    //Toast.makeText(Client_Manager.this, "CAMERA already granted",
                    //Toast.LENGTH_SHORT).show();
                } else {

                    System.exit(0)
                }
            } else {
                Toast.makeText(context, "CAMERA DENIED", Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            this.CAMERA_CAPTURE_IMAGE_REQUEST_CODE -> when (resultCode) {
                Activity.RESULT_OK -> when (START_CAPTURE) {
                    this.PREVIEW_FIRST_IMAGE ->                                 //decodeFile(new File(fileUri_first_image.getPath()), PREVIEW_FIRST_IMAGE);
                        fileUri_first_image?.let {
                            previewCapturedImage(
                                it,
                                this.PREVIEW_FIRST_IMAGE
                            )
                        }
                    this.PREVIEW_SECOND_IMAGE ->                                 //decodeFile(new File(fileUri_second_image.getPath()), PREVIEW_SECOND_IMAGE);
                        fileUri_second_image?.let {
                            previewCapturedImage(
                                it,
                                this.PREVIEW_SECOND_IMAGE
                            )
                        }
                }
                Activity.RESULT_CANCELED ->                         // user cancelled Image capture
                    Toast.makeText(
                        context,
                        "Capture Image annulee", Toast.LENGTH_SHORT
                    )
                        .show()
                else ->                         // failed to capture image
                    Toast.makeText(
                        context,
                        "Désolé : Erreur capture d'image", Toast.LENGTH_SHORT
                    )
                        .show()
            }
        }
    }

    private fun captureImage(start_capture: Int) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        START_CAPTURE = start_capture
        if (start_capture == this.PREVIEW_FIRST_IMAGE) {
            fileUri_first_image = context?.let {
                this.getOutputMediaFile(this.MEDIA_TYPE_IMAGE)?.let { it1 ->
                    FileProvider.getUriForFile(
                        it,
                        BuildConfig.APPLICATION_ID + ".provider",
                        it1
                    )
                }
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri_first_image)
        } else {
            fileUri_second_image = context?.let {
                this.getOutputMediaFile(this.MEDIA_TYPE_IMAGE)?.let { it1 ->
                    FileProvider.getUriForFile(
                        it,
                        BuildConfig.APPLICATION_ID + ".provider",
                        it1
                    )
                }
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri_second_image)
        }

        startActivityForResult(intent, this.CAMERA_CAPTURE_IMAGE_REQUEST_CODE)
    }

    fun getOutputMediaFile(type: Int): File? {

        val mediaStorageDir = File(
            Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            this.IMAGE_DIRECTORY_NAME
        )

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }

        val timeStamp = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(Date())
        val mediaFile: File
        if (type == this.MEDIA_TYPE_IMAGE) {
            mediaFile = File(
                mediaStorageDir.path + File.separator
                        + "IMG_" + timeStamp + ".jpg"
            )
        } else if (type == this.MEDIA_TYPE_VIDEO) {
            mediaFile = File(
                (mediaStorageDir.path + File.separator
                        + "VID_" + timeStamp + ".mp4")
            )
        } else {
            return null
        }
        return mediaFile
    }

    private fun previewCapturedImage(fileUri: Uri, preview_captured: Int) {
        try {
            // bimatp factory
            val options = BitmapFactory.Options()

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8
            var bitmap = BitmapFactory.decodeFile(
                fileUri.path,
                options
            )
            try {
                val exif = ExifInterface(fileUri.path!!)
                val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1)
                Log.d("EXIF", "Exif: $orientation")
                val matrix = Matrix()
                if (orientation == 6) {
                    matrix.postRotate(90f)
                } else if (orientation == 3) {
                    matrix.postRotate(180f)
                } else if (orientation == 8) {
                    matrix.postRotate(270f)
                }
                bitmap = Bitmap.createBitmap(
                    bitmap,
                    0,
                    0,
                    bitmap.width,
                    bitmap.height,
                    matrix,
                    true
                ) // rotating bitmap
            } catch (e: Exception) {
                Log.d("Rayon dialog", "previewCapturedImage: " + e.message)
            }
            if (preview_captured == this.PREVIEW_FIRST_IMAGE) {
                first_image?.setVisibility(View.VISIBLE)
                first_image?.setImageBitmap(bitmap)
                this.first_image_captured = true
            } else {
                second_image?.setVisibility(View.VISIBLE)
                second_image?.setImageBitmap(bitmap)
                this.second_image_captured = true
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    private fun checkValidation(): Boolean {
        var result = true

        val famille =
            famille_spinner!!.adapter.getItem(famille_spinner!!.getSelectedItemPosition()) as Famille
        val strfamille = famille.famillE_CODE

        if (TextUtils.isEmpty(strfamille) || TextUtils.equals(strfamille, "FAMILLE")) {
            (famille_spinner!!.getChildAt(0) as TextView).error = "ce champs est obligatoire"
            result = false
            return result
        }

        if(binding.showArticleSwitch.isChecked){
            val article =
                article_spinner!!.adapter.getItem(article_spinner!!.getSelectedItemPosition()) as Article
            val strarticle = article.articlE_CODE

            if (TextUtils.isEmpty(strarticle) || TextUtils.equals(strarticle, "ARTICLE")) {
                (article_spinner!!.getChildAt(0) as TextView).error = "ce champs est obligatoire"
                result = false
                return result
            }
        }


        val strLargeurRayon: String = rayon_largeur_editText!!.getText().toString()
        if (TextUtils.isEmpty(strLargeurRayon)) {
            rayon_largeur_editText!!.setError("ce champs est obligatoire")
            result = false
        }

        val strHauteurRayon: String = rayon_hauteur_editText!!.getText().toString()
        if (TextUtils.isEmpty(strHauteurRayon)) {
            rayon_hauteur_editText!!.setError("ce champs est obligatoire")
            result = false
        }

        if (this.first_image_captured == false) {
            result = false
            Toast.makeText(context, "IL VOUS MANQUE L'IMAGE 1", Toast.LENGTH_SHORT).show()
            return result
        }
        /*if (this.second_image_captured == false) {
            result = false
            Toast.makeText(context, "IL VOUS MANQUE L'IMAGE 2", Toast.LENGTH_SHORT).show()
            return result
        }*/
        return result
    }
}