package com.newtech.newtech_sfm.mob_cmd_al.mob_livraison_panier

import android.app.Dialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.newtech.newtech_sfm.Configuration.Spinner_Adapter
import com.newtech.newtech_sfm.Metier.*
import com.newtech.newtech_sfm.Metier_Manager.*
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.databinding.FragmentPanierLivraisonBinding
import com.newtech.newtech_sfm.mob_cmd_al.MobCmdALViewModel
import org.json.JSONException
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MobPanierLivraisonFragment : Fragment() {

    private val TAG: String = this.javaClass.name.toString()

    private lateinit var binding: FragmentPanierLivraisonBinding
    private val mobCmdALViewModel: MobCmdALViewModel by activityViewModels()

    private var clientManager: ClientManager? = null

    private var commande: Commande = Commande()
    private var commandeLignes: ArrayList<CommandeLigne> = arrayListOf()

    private var livraison: Livraison = Livraison()
    private var livraisonLignes: ArrayList<LivraisonLigne> = arrayListOf()

    private var familles: ArrayList<Famille> = arrayListOf()
    private var client: Client = Client()

    private var familleAdapter: FamilleAdapter? = null
    private var mobLivraisonPanierAdapter: MobLivraisonPanierAdapter? = null

    private var articles: ArrayList<Article>? = null

    var commandeManager: CommandeManager? = null
    var commandeLigneManager: CommandeLigneManager? = null

    var livraisonManager: LivraisonManager? = null
    var livraisonLigneManager: LivraisonLigneManager? = null

    var livraisonPromotionManager: LivraisonPromotionManager? = null
    var livraisonGratuiteManager: LivraisonGratuiteManager? = null

    var promotionManager: PromotionManager? = null
    var promotionGratuiteManager: PromotiongratuiteManager? = null

    var uniteManager: UniteManager? = null

    var stockManager: StockManager? = null
    var stockLigneManager: StockLigneManager? = null
    var stockTransfertManager: StockTransfertManager? = null

    var listPrixManager: ListePrixManager? = null
    var listePrixLigneManager: ListePrixLigneManager? = null

    var articleManager: ArticleManager? = null
    var articlePrixManager: ArticlePrixManager? = null

    var familleManager: FamilleManager? = null

    var unite: Unite? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_panier_livraison, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initializeManagers()
        initializeData()

        if (livraisonLignes.size > 0) {

            val valeurTotal = 0.0
            for (i in livraisonLignes.indices) {
                val livraisonLigne = livraisonLignes[i]
                val prix_article = listePrixLigneManager!!.getListPrixLigneByLPCACUC(
                    client.getLISTEPRIX_CODE(),
                    livraisonLigne.getARTICLE_CODE(),
                    livraisonLigne.getUNITE_CODE()
                ).articlE_PRIX
                    .toFloat()
                valeurTotal.plus((prix_article * livraisonLigne.getQTE_COMMANDEE()).toFloat())
            }
            binding.totalPanier.setText(String.format("%.1f", valeurTotal) + "Dhs")
            binding.totalVente.setText(String.format("%.1f", valeurTotal) + "Dhs")
        } else {
            binding.totalPanier.setText(0.toString() + "Dhs")
            binding.totalPanier.setText(0.toString() + "Dhs")
        }

        familleAdapter = FamilleAdapter(requireActivity(), familles)
        binding.familleSpinner.adapter = familleAdapter

        binding.familleSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val famille: Famille = familleAdapter!!.getItem(position) as Famille
                    articles = articleManager!!.getListByFamilleCode(famille.famillE_CODE)

                    mobLivraisonPanierAdapter = MobLivraisonPanierAdapter(
                        requireActivity(),
                        articles!!,
                        livraisonLignes,
                        mobCmdALViewModel.getClientCode(),
                        context
                    )

                    binding.panierLv.adapter = mobLivraisonPanierAdapter

                    binding.panierLv.onItemClickListener =
                        object : AdapterView.OnItemClickListener {
                            override fun onItemClick(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                val article =
                                    mobLivraisonPanierAdapter!!.getItem(position) as Article
                                val unites: ArrayList<Unite?>?
                                unites = uniteManager!!.getListByArticleCode(article.articlE_CODE)

                                val dialog = Dialog(view!!.context)
                                dialog.setContentView(R.layout.alert_dialog_panier)

                                val quantite_vendue =
                                    dialog.findViewById<View>(R.id.quantite_vendue_panier) as EditText

                                val prix_defaut =
                                    dialog.findViewById<View>(R.id.prix_par_defaut) as EditText

                                prix_defaut.setEnabled(false)

                                val dropdown_unite =
                                    dialog.findViewById<View>(R.id.spinner_choix_unite) as Spinner

                                val spinnerAdapter =
                                    Spinner_Adapter(requireActivity(), unites)

                                dropdown_unite.setAdapter(spinnerAdapter)

                                dropdown_unite.onItemSelectedListener =
                                    object : AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(
                                            parent: AdapterView<*>?,
                                            view: View?,
                                            position: Int,
                                            id: Long
                                        ) {
                                            unite = spinnerAdapter.getItem(position)
                                            listePrixLigneManager!!.getListPrixLigneByLPCACUC(
                                                client.listepriX_CODE,
                                                article.articlE_CODE,
                                                unite!!.getUNITE_CODE()
                                            )

                                            quantite_vendue.text.clear()
                                            prix_defaut.text.clear()

                                            val livraisonLigne = getLivraisonLigne(
                                                article,
                                                unite!!.getUNITE_CODE(),
                                                livraisonLignes
                                            )

                                            quantite_vendue.setText(
                                                (livraisonLigne!!.qtE_COMMANDEE.toInt()).toString()
                                            )


                                            prix_defaut.setText(
                                                livraisonLigne.articlE_PRIX.toString()
                                            )
                                        }

                                        override fun onNothingSelected(parent: AdapterView<*>?) {

                                        }

                                    }

                                val annulerPanier =
                                    dialog.findViewById<View>(R.id.annuler_panier) as Button
                                val validerPanier =
                                    dialog.findViewById<View>(R.id.valider_panier) as Button

                                annulerPanier.setOnClickListener { dialog.dismiss() }
                                validerPanier.setOnClickListener {
                                    var quantite_article = 0
                                    var prixDefault = 0.0
                                    var unite_code = ""


                                    if (unite == null) {
                                        showMessage("Merci de Choisir une unité")
                                        dialog.dismiss()
                                    } else {
                                        unite_code = unite!!.getUNITE_CODE()
                                    }

                                    if (quantite_vendue.text.toString() != "") {
                                        quantite_article =
                                            quantite_vendue.text.toString().toInt()
                                    }
                                    val listePrixLigne =
                                        listePrixLigneManager!!.getListPrixLigneByLPCACUC(
                                            client.getLISTEPRIX_CODE(),
                                            article.articlE_CODE,
                                            unite_code
                                        )

                                    if (listePrixLigne != null) {
                                        prixDefault = listePrixLigne.articlE_PRIX
                                    }

                                    val livraisonLigne =
                                        getLivraisonLigne(article, unite_code, livraisonLignes)

                                    if (quantite_article > livraisonLigne!!.getQTE_COMMANDEE()) {
                                        dialog.dismiss()
                                        mobLivraisonPanierAdapter!!.notifyDataSetChanged()
                                        showMessage("Vous ne pouver pas dépasser le max du quantite à livrer")
                                    } else {
                                        supprimerLivraisonLigne(
                                            article,
                                            unite_code
                                        )

                                        if (quantite_article != 0 && quantite_vendue.text.toString() != "") {
                                            ajouterLivraisonLigne(
                                                livraison,
                                                article,
                                                quantite_article,
                                                unite_code,
                                                prixDefault
                                            )
                                        }
                                        updateLivraison()

                                        binding.totalPanier.setText(livraison.valeuR_COMMANDE.toString() + "Dhs")
                                        binding.totalVente.setText(livraison.valeuR_COMMANDE.toString() + "Dhs")

                                        unite = null
                                        dialog.dismiss()
                                        mobLivraisonPanierAdapter!!.notifyDataSetChanged()
                                    }


                                }
                                dialog.show()
                            }


                        }

                    binding.validerPanier.setOnClickListener { v: View? ->

                        binding.validerPanier.isClickable = false
                        val articles =
                            ArrayList<String>()
                        var stock_suffisant = true
                        val pref: SharedPreferences =
                            context!!.getSharedPreferences("MyPref", 0)
                        val gson2 = Gson()
                        val json2 = pref.getString("User", "")
                        val type =
                            object : TypeToken<JSONObject?>() {}.type
                        val user = gson2.fromJson<JSONObject>(json2, type)
                        for (i in livraisonLignes.indices) {
                            if (!articles.contains(livraisonLignes[i].articlE_CODE)) {
                                articles.add(livraisonLignes[i].articlE_CODE)
                            }
                        }
                        try {
                            if (stockManager!!.checkGerable(
                                    user.getString("UTILISATEUR_CODE"),
                                    context
                                )
                            ) {
                                for (i in articles.indices) {
                                    if (stockLigneManager!!.checkLivraisonStockLigneQteVersion(
                                            livraisonLignes,
                                            articles[i],
                                            context
                                        ) == false
                                    ) {
                                        stock_suffisant = false
                                        break
                                    }
                                }
                            } else {

                                showMessage("STOCK INGERABLE")
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                        if (stock_suffisant == false) {

                            showMessage("STOCK INSUFFISANT")
                        } else {

                            showMessage("STOCK SUFFISANT")
                        }

                        findNavController().navigate(R.id.action_mobPanierLivraisonFragment_to_mobViewLivraisonFragment)

                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }

    }

    private fun updateLivraison(
    ) {
        var MONTANT_BRUT = 0.0
        var MONTANT_NET = 0.0
        var LITTRAGE_COMMANDE = 0.0
        var TONNAGE_COMMANDE = 0.0
        var KG_COMMANDE = 0.0
        var VALEUR_COMMANDE: Double

        for (i in livraisonLignes.indices) {
            MONTANT_BRUT += livraisonLignes[i].montanT_BRUT
            MONTANT_NET += livraisonLignes[i].montanT_NET
            LITTRAGE_COMMANDE += livraisonLignes[i].littragE_COMMANDEE
            TONNAGE_COMMANDE += livraisonLignes[i].tonnagE_COMMANDEE
            KG_COMMANDE += livraisonLignes[i].kG_COMMANDEE
        }
        VALEUR_COMMANDE = MONTANT_NET

        livraison.montanT_BRUT = MONTANT_BRUT
        livraison.montanT_NET = MONTANT_NET
        livraison.littragE_COMMANDE = LITTRAGE_COMMANDE
        livraison.tonnagE_COMMANDE = TONNAGE_COMMANDE
        livraison.kG_COMMANDE = KG_COMMANDE
        livraison.valeuR_COMMANDE = VALEUR_COMMANDE

        mobCmdALViewModel.setLivraison(livraison)
        mobCmdALViewModel.setLivraisonLignes(livraisonLignes)
    }

    private fun ajouterLivraisonLigne(
        livraison: Livraison,
        article: Article,
        quantiteArticle: Int,
        uniteCode: String,
        prixDefault: Double
    ) {
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ")
        val dateLivraison = df.format(Calendar.getInstance().time)
        val size = livraisonLignes.size + 1

        val livraisonLigne = LivraisonLigne(
            livraison,
            article,
            dateLivraison,
            quantiteArticle,
            uniteCode,
            prixDefault,
            size,
            context
        )
        livraisonLignes.add(livraisonLigne)
    }

    private fun supprimerLivraisonLigne(article: Article, uniteCode: String) {

        //var tempLivraisonLignes = livraisonLignes

        val size = livraisonLignes.size

        for (i in 0..size - 1) {
            if (livraisonLignes[i].articlE_CODE == article.articlE_CODE && livraisonLignes[i].unitE_CODE.equals(
                    uniteCode
                )
            ) {
                livraisonLignes.removeAt(i)
                break
            }
        }
    }

    private fun initializeData() {

        commande = mobCmdALViewModel.getCommande()
        commandeLignes = mobCmdALViewModel.getCommandeLignes()
        livraison = mobCmdALViewModel.getLivraison()
        livraisonLignes = mobCmdALViewModel.getLivraisonLignes()
        familles = familleManager!!.list
        client = clientManager!!.get(mobCmdALViewModel.getClientCode())

    }

    private fun initializeManagers() {

        clientManager = ClientManager(context)

        commandeManager = CommandeManager(context)
        commandeLigneManager = CommandeLigneManager(context)

        livraisonManager = LivraisonManager(context)
        livraisonLigneManager = LivraisonLigneManager(context)

        livraisonPromotionManager = LivraisonPromotionManager(context)
        livraisonGratuiteManager = LivraisonGratuiteManager(context)

        promotionManager = PromotionManager(context)
        promotionGratuiteManager = PromotiongratuiteManager(context)

        uniteManager = UniteManager(context)

        stockManager = StockManager(context)
        stockLigneManager = StockLigneManager(context)
        stockTransfertManager = StockTransfertManager(context)

        listPrixManager = ListePrixManager(context)
        listePrixLigneManager = ListePrixLigneManager(context)

        articleManager = ArticleManager(context)
        articlePrixManager = ArticlePrixManager(context)

        familleManager = FamilleManager(context)

    }

    fun getLivraisonLigne(
        article: Article,
        unite: String,
        livraisonLignes: ArrayList<LivraisonLigne>
    ): LivraisonLigne {
        var livraisonLigne = LivraisonLigne()
        for (i in livraisonLignes.indices) {
            if (livraisonLignes[i].articlE_CODE == article.articlE_CODE && livraisonLignes[i].unitE_CODE == unite) {
                livraisonLigne = livraisonLignes[i]
                break
            }
        }
        return livraisonLigne
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

}