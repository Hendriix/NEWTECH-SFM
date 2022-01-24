package com.newtech.newtech_sfm.rapportchoufouni

import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.newtech.newtech_sfm.Metier.RapportChoufouni
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.databinding.FragmentRapportChoufouniBinding
import org.json.JSONObject

class RapportChoufouniFragment() : Fragment() , RapportChoufouniPresenter.RapportChoufouniView{

    private val mListener: RapportChoufouniFragment.OnFragmentInteractionListener? = null
    private lateinit var binding: FragmentRapportChoufouniBinding
    private lateinit var rapportChoufouniPresenter : RapportChoufouniPresenter
    private lateinit var rapportChoufouniClientAdapter : RapportChoufouniClientAdapter
    private var progressDialog: ProgressDialog? = null

    // TODO: Rename and change types and number of parameters
    fun newInstance(): RapportChoufouniFragment? {
        return RapportChoufouniFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentRapportChoufouniBinding>(
            inflater,
            R.layout.fragment_rapport_choufouni, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rapportChoufouniPresenter = RapportChoufouniPresenter(this)
        val pref = requireActivity().getSharedPreferences("MyPref", 0)
        val gson2 = Gson()
        val json2 = pref.getString("User", "")
        val type = object : TypeToken<JSONObject?>() {}.type
        val user = gson2.fromJson<JSONObject>(json2, type)

        initProgressDialog()

        val linearLayoutManager : LinearLayoutManager = LinearLayoutManager(requireActivity())
        binding.choufouniClientsRv.layoutManager =linearLayoutManager
        rapportChoufouniPresenter.getRapportChoufouni(requireContext(),user.getString("UTILISATEUR_CODE"))
        requireActivity().title = "RAPPORT CHOUFOUNI"
        /*val myWebView = binding.webview
        myWebView.webViewClient = setupWebView()
        myWebView.loadUrl("https://webapp.savola.com/octopusnew/mb/ER_Choufouni_Client_national.php")*/

    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri?)
    }

    override fun showSuccess(rapportChoufouni: RapportChoufouni) {

        progressDialog!!.dismiss()
        binding.choufouniRapportLl.visibility = View.VISIBLE
        binding.objectifTv.text = rapportChoufouni.OBJECTIF
        binding.contratTv.text = rapportChoufouni.CONTRAT
        binding.contratImageTv.text = rapportChoufouni.CONTRAT_IMAGE
        binding.resteTv.text = rapportChoufouni.RESTE

        binding.choufouniClientsRv.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )


        rapportChoufouniClientAdapter = RapportChoufouniClientAdapter(rapportChoufouni.rapportChoufouniClients,requireContext())

        binding.choufouniClientsRv.adapter = rapportChoufouniClientAdapter

    }

    override fun showError(message: String?) {
        progressDialog!!.dismiss()
        showMessage(message)
    }

    override fun showEmpty(message: String?) {
        progressDialog!!.dismiss()
        showMessage(message)
    }

    override fun showLoading() {
        progressDialog!!.show()
    }

    private fun initProgressDialog() {
        progressDialog = ProgressDialog(requireActivity())
        progressDialog!!.setCancelable(false)
        progressDialog!!.setMessage("Chargement en cours")
    }

    private fun showMessage(message: String?) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
    }

    /*private fun setupWebView() : WebViewClient{

        val webViewClient: WebViewClient = object: WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                showProgressDialog()
                //super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                hideProgressDialog()
                //super.onPageFinished(view, url)
            }
        }
        binding.webview.webViewClient = webViewClient

        binding.webview.settings.defaultTextEncodingName = "utf-8"
        return webViewClient
    }

    private fun hideProgressDialog() {

        binding.determinateBar.visibility = View.GONE
    }

    private fun showProgressDialog() {
        binding.determinateBar.visibility = View.VISIBLE
        binding.determinateBar.incrementProgressBy(5)
    }*/

}