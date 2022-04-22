package com.newtech.newtech_sfm.mob_cmd_al.mob_livraison_panier

import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.newtech.newtech_sfm.Configuration.Common
import com.newtech.newtech_sfm.Metier.*
import com.newtech.newtech_sfm.Metier_Manager.*
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.databinding.FragmentViewLivraisonBinding
import com.newtech.newtech_sfm.mob_cmd_al.MobCmdALViewModel
import org.json.JSONException
import org.json.JSONObject
import java.text.DecimalFormat

class MobViewLivraisonFragment : Fragment() {

    private val TAG: String = this.javaClass.name.toString()

    private lateinit var binding: FragmentViewLivraisonBinding
    private val mobCmdALViewModel: MobCmdALViewModel by activityViewModels()

    private var commandeSource = ""
    private var visiteCode = ""

    private var commande: Commande = Commande()
    private var commandeLignes: ArrayList<CommandeLigne> = arrayListOf()


    private var livraison: Livraison = Livraison()
    private var livraisonLignes: ArrayList<LivraisonLigne> = arrayListOf()

    private var livraisonGratuites: ArrayList<LivraisonGratuite> = arrayListOf()
    private var livraisonPromotions: ArrayList<LivraisonPromotion> = arrayListOf()
    private var livraisonPromotionsAppliquees: ArrayList<LivraisonPromotion> = arrayListOf()


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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_view_livraison, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialisation des managers
        initializeManagers()
        initializeData()
        drowLinesTable()
        updateLivraisonAndLivraisonLigne()

        binding.editBtn.setOnClickListener {
            val navController: NavController = this.findNavController()
            navController.navigate(R.id.action_mobViewLivraisonFragment_to_mobPanierLivraisonFragment)
        }

        binding.cancelBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.validateBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mobViewLivraisonFragment_to_mobEncaissementFragment)
        }
        
        /*binding.validateBtn.setOnClickListener {
            val pref: SharedPreferences = requireContext().getSharedPreferences("MyPref", 0)
            val gson2 = Gson()
            val json2 = pref.getString("User", "")
            val type = object : TypeToken<JSONObject?>() {}.type
            val user = gson2.fromJson<JSONObject>(json2, type)

            binding.validateBtn.isClickable = false
            
            val articles = java.util.ArrayList<String>()
            var stock_suffisant = true

            if (commandeSource != "Annuler") {
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
                                    articles[i], context
                                ) == false
                            ) {
                                //Toast.makeText(context,"Stock Insuffisant" , Toast.LENGTH_LONG).show();
                                stock_suffisant = false
                                break
                            }
                        }
                    } else {
                        Log.d(TAG, "onClick: stock ingérable")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            if (stock_suffisant == false) {
                Common.showToast("STOCK INSUFFISANT",context)
            } else {
                Log.d(TAG, "onClick: stock suffisant")
                if (livraison != null && (commandeSource == "Livraison" || commandeSource == "Annuler") && livraisonLignes.isEmpty() == false) {
                    if (livraisonPromotionsAppliquees.size > 0) {
                        mobCmdALViewModel.setLivraisonPromotions(livraisonPromotionsAppliquees)
                        /*for (unPromoAP in livraisonPromotionsAppliquees) {
                            unPromoAP.version = "to_insert"
                            livraisonPromotionManager!!.add(unPromoAP)
                        }*/
                    }
                    if (livraisonGratuites.size > 0) {
                        mobCmdALViewModel.setLivraisonGratuites(livraisonGratuites)
                        /*for (unLIVG in livraisonGratuites) {
                            livraisonGratuiteManager!!.add(unLIVG)
                            Log.d("LIVRAISON GRATUITE ", "GRATUITE: $unLIVG")
                        }*/
                    }

                    livraison.nB_LIGNE =
                        livraisonLignes.size


                    /*
                    final Dialog dialog = new Dialog(view.getContext());
                    dialog.setContentView(R.layout.alert_encaissement);
                    dialog.setTitle("ENCAISSEMENT");
                    dialog.setCanceledOnTouchOutside(false);
                    Button oui = (Button) dialog.findViewById(R.id.btn_oui);
                    Button non = (Button) dialog.findViewById(R.id.btn_non);
                    final TextView alertencaissement = (TextView) dialog.findViewById(R.id.alertencaissement);

                    final Dialog dialog1 = new Dialog(view.getContext());
                    dialog1.setContentView(R.layout.alert_imprimante);
                    dialog1.setTitle("Impression");
                    dialog1.setCanceledOnTouchOutside(false);
                    Button print = (Button) dialog1.findViewById(R.id.btn_print);
                    Button done = (Button) dialog1.findViewById(R.id.done_print);
                    final TextView nbr_copies = (TextView) dialog1.findViewById(R.id.nbr_copies);

                    print.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            boolean printed=false;
                            String impression_text=impressionManager.ImprimerLivraison(livraison,getApplicationContext());
                            for(int i=0;i<Integer.valueOf(nbr_copies.getText().toString());i++){

                                printed=BlutoothConnctionService.imprimanteManager.printText(impression_text);
                                ImprimanteManager.lastPrint=impression_text;
                                Log.d("print", "onClick: "+impression_text.toString());

                                if(printed==true){
                                    Log.d("printed", "onClick: "+"imprimeée");
                                    try {
                                        Impression impression = new Impression(getApplicationContext(),livraison.getLIVRAISON_CODE(),impression_text,"NORMAL",1,"LIVRAISON");
                                        impressionManager.add(impression);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }else{
                                    Log.d("printed", "onClick: "+"non imprimee");
                                    try {
                                        Impression impression = new Impression(getApplicationContext(),livraison.getLIVRAISON_CODE(),impression_text,"STOCKEE",0,"LIVRAISON");
                                        impressionManager.add(impression);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }

                        }
                    });

                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            try {
                                if(stockManager.checkGerable(user.getString("UTILISATEUR_CODE"),getApplicationContext())) {
                                    if (commandeSource.equals("Annuler") || commandeSource.equals("Livraison")) {

                                        for (int i = 0; i < livraisonLignes.size(); i++) {

                                            try {
                                                StockTransfert stockTransfert = new StockTransfert(livraison, livraisonLignes.get(i), getApplicationContext());

                                                //Log.d("stockTransfert", "onClick: "+ i +stockTransfert.toString());
                                                stockTransfertManager.add(stockTransfert);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


                                        }
                                    }

                                    stockManager.updateStock(getApplicationContext());

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if(commandeSource.equals("Annuler")){
                                visiteManager.updateVisite_VRDF(ClientActivity.visiteCourante.getVISITE_CODE(),-1,date_visite);

                            }else{
                                visiteManager.updateVisite_VRDF(ClientActivity.visiteCourante.getVISITE_CODE(),1,date_visite);

                            }



                            if(isNetworkAvailable()){
                                Toast.makeText(getApplicationContext(),"Synchronisation en cours",Toast.LENGTH_SHORT).show();

                                LivraisonManager.synchronisationLivraison(getApplicationContext());
                                LivraisonLigneManager.synchronisationLivraisonLigne(getApplicationContext());
                                LivraisonGratuiteManager.synchronisationLivraisonGratuite(getApplicationContext());
                                LivraisonPromotionManager.synchronisationLivraisonPromotion(getApplicationContext());

                            }

                            livraisonLignes.clear();
                            commande=null;
                            ListeCommandeLigne.clear();
                            livraison=null;

                            dialog1.dismiss();
                            Intent i = new Intent(view.getContext(), ClientActivity.class);

                            if(commandeSource.equals("Annuler")){
                                i.putExtra("VISITERESULTAT_CODE",-1);
                            }else{
                                i.putExtra("VISITERESULTAT_CODE",1);
                            }
                            startActivity(i);
                            finish();
                        }

                    });

                    oui.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            //Toast.makeText(getApplicationContext(),"ENCAISSEMENT HONEY",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(view.getContext(), EncaissementActivity.class);

                            EncaissementActivity.commande = null;
                            EncaissementActivity.commande = commande;

                            EncaissementActivity.commande_source = null;
                            EncaissementActivity.commande_source = "Livraison";

                            EncaissementActivity.livraison = null;
                            EncaissementActivity.encaissements = null;
                            EncaissementActivity.encaissements = new ArrayList<Encaissement>();
                            EncaissementActivity.encaissementslocaux = null;
                            EncaissementActivity.encaissementslocaux = new ArrayList<Encaissement>();
                            //EncaissementActivity.livraisonLignes.clear();

                            EncaissementActivity.livraison = livraison;
                            EncaissementActivity.livraisonLignes = livraisonLignes;

                            startActivity(i);
                            finish();
                        }

                    });

                    non.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            dialog1.show();
                        }

                    });

                    if(commandeSource.equals("Annuler")){
                        dialog1.show();
                    }else{
                        dialog.show();
                    }*/
                } else if (livraison.equals(null) || livraisonLignes.isEmpty() == true) {
                    Toast.makeText(
                        requireContext(),
                        "Finaliser votre livraison ou appuyer sur annuler pour terminer l'opération",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }*/


    }

    private fun initializeData() {
        commande = mobCmdALViewModel.getCommande()
        commandeLignes = mobCmdALViewModel.getCommandeLignes()
        livraison = mobCmdALViewModel.getLivraison()
        livraisonLignes = mobCmdALViewModel.getLivraisonLignes()
        commandeSource = mobCmdALViewModel.getCommandeSource()
    }

    private fun initializeManagers() {

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
    }

    private fun drowLinesTable() {
        if (livraisonLignes != null) {

            livraisonLignes =
                livraisonLigneManager!!.fixLivraisonLigneCode(livraisonLignes)

            for (i in livraisonLignes.indices) {
                val ligne1 = TableRow(context)
                val ligne_code = TextView(context)
                val art_code = TextView(context)
                val unite = TextView(context)
                val qte = TextView(context)
                val remise1 = TextView(context)
                val total = TextView(context)
                val unite1 = uniteManager!![livraisonLignes[i].unitE_CODE]

                ligne_code.ellipsize = TextUtils.TruncateAt.END
                unite.ellipsize = TextUtils.TruncateAt.END
                ligne_code.gravity = Gravity.CENTER
                art_code.gravity = Gravity.CENTER
                unite.gravity = Gravity.CENTER
                qte.gravity = Gravity.CENTER
                remise1.gravity = Gravity.CENTER
                total.gravity = Gravity.CENTER

                ligne_code.textSize = 15f
                art_code.textSize = 15f
                unite.textSize = 15f
                qte.textSize = 15f
                total.textSize = 15f

                ligne_code.setBackgroundResource(R.drawable.cell_shape)
                art_code.setBackgroundResource(R.drawable.cell_shape)
                unite.setBackgroundResource(R.drawable.cell_shape)
                qte.setBackgroundResource(R.drawable.cell_shape)
                remise1.setBackgroundResource(R.drawable.cell_shape)
                total.setBackgroundResource(R.drawable.cell_shape)

                ligne_code.text =
                    livraisonLignes[i].livraisonlignE_CODE.toString()
                art_code.text = livraisonLignes[i].articlE_CODE
                unite.text = unite1.unitE_NOM
                qte.text = livraisonLignes[i].qtE_COMMANDEE.toString()
                remise1.text = "0"
                total.text =
                    String.format("%.1f", livraisonLignes[i].montanT_NET)
                ligne1.addView(ligne_code)
                ligne1.addView(art_code)
                ligne1.addView(unite)
                ligne1.addView(qte)
                ligne1.addView(remise1)
                ligne1.addView(total)
                binding.linesTl.addView(ligne1)
            }
        }
    }

    private fun updateLivraisonAndLivraisonLigne() {

        var MONTANT_BRUT = 0.0
        var REMISE = 0.0
        var MONTANT_NET = 0.0
        var VALEUR_COMMANDEE = 0.0
        var LITTRAGE_COMMANDEE = 0.0
        var TONNAGE_COMMANDEE = 0.0
        var KG_COMMANDEE = 0.0

        var tRemise: Double? = 0.0

        for (i in livraisonLignes.indices) {

            MONTANT_BRUT += livraisonLignes[i].montanT_BRUT
            REMISE += livraisonLignes[i].remise
            MONTANT_NET += livraisonLignes[i].montanT_NET
            VALEUR_COMMANDEE += livraisonLignes[i].montanT_NET
            LITTRAGE_COMMANDEE += livraisonLignes[i].littragE_COMMANDEE
            TONNAGE_COMMANDEE += livraisonLignes[i].tonnagE_COMMANDEE
            KG_COMMANDEE += livraisonLignes[i].kG_COMMANDEE
            livraisonLignes[i].livraisoN_CODE = livraison.livraisoN_CODE
            livraisonLignes[i].livraisonlignE_CODE = livraisonLignes.size + i + 1

        }

        livraison.montanT_BRUT = MONTANT_BRUT
        livraison.remise = REMISE
        livraison.montanT_NET = MONTANT_NET
        livraison.valeuR_COMMANDE = VALEUR_COMMANDEE
        livraison.littragE_COMMANDE = LITTRAGE_COMMANDEE
        livraison.tonnagE_COMMANDE = TONNAGE_COMMANDEE
        livraison.kG_COMMANDE = KG_COMMANDEE
        livraison.visitE_CODE = visiteCode

        livraisonPromotionsAppliquees = promotionManager!!.GetPromoAP_LIV_QC(
            livraison,
            livraisonLignes,
            context
        )

        /* REMISE LIGNE */

        //############################################################ REMISE LIGNE ######################################################################
        for (i in livraisonLignes.indices) {
            var remiseLivraisonLigne = 0.0
            if (livraisonPromotionsAppliquees.size > 0) {
                for (unPromoAP in livraisonPromotionsAppliquees) {
                    if (unPromoAP.promO_MODECALCUL == "CA0015" && unPromoAP.promO_NIVEAU == "CA0018" && unPromoAP.livraisoN_CODE == livraison.livraisoN_CODE && unPromoAP.livraisonlignE_CODE == livraisonLignes[i].livraisonlignE_CODE) {
                        remiseLivraisonLigne += remiseLivraisonLigne.plus(unPromoAP.valeuR_PROMO)
                        livraisonPromotions.add(unPromoAP)
                    }
                }
            }
            tRemise = tRemise!!.plus(remiseLivraisonLigne)
            livraisonLignes[i].remise = tRemise
            livraisonLignes[i].montanT_NET = livraisonLignes[i].montanT_BRUT - tRemise
        }
        /////////////////

        /*LIVRAISON PROMOTIONS*/
        if (livraisonPromotionsAppliquees.size > 0) {
            for (unPromoAP in livraisonPromotionsAppliquees) {
                if (unPromoAP.promO_MODECALCUL == "CA0016" && unPromoAP.promO_NIVEAU == "CA0017" && unPromoAP.livraisoN_CODE == livraison.livraisoN_CODE) {
                    livraisonPromotions.add(unPromoAP)
                }
            }
        }
        ///////////////////////

        /*REMISE*/
        var remiseLivraison = 0.0
        if (livraisonPromotionsAppliquees.size > 0) {
            for (unPromoAP in livraisonPromotionsAppliquees) {
                if (unPromoAP.promO_MODECALCUL == "CA0015" && unPromoAP.promO_NIVEAU == "CA0017" && unPromoAP.livraisoN_CODE == livraison.livraisoN_CODE) {
                    remiseLivraison = remiseLivraison.plus(unPromoAP.valeuR_PROMO)
                    livraisonPromotions.add(unPromoAP)
                }
            }
        }
        /////////
        livraison.montanT_NET = livraison.montanT_BRUT - livraison.remise


        //################################################################################################################################################
        livraisonGratuites = livraisonGratuiteManager!!.getLivraisonGratuiteByCmdPromotion(
            livraisonPromotionsAppliquees,
            livraison,
            context
        )

        drowGratuiteLignes(livraisonGratuites)

        binding.totalPanierValueTv.setText(
            DecimalFormat("##.##").format(livraison.montanT_BRUT).toString()
        )

        binding.panierRemiseValueTv.setText(
            DecimalFormat("##.##").format(livraison.remise).toString()
        )
        binding.panierNetValueTv.setText(
            DecimalFormat("##.##").format(livraison.montanT_NET).toString()
        )

    }

    private fun drowGratuiteLignes(livraisonGratuites: ArrayList<LivraisonGratuite>) {
        if (livraisonGratuites != null) {

            for (i in livraisonGratuites.indices) {
                val ligne1 = TableRow(context)
                val art_code = TextView(context)
                val qte = TextView(context)
                art_code.ellipsize = TextUtils.TruncateAt.END
                qte.gravity = Gravity.CENTER
                art_code.textSize = 15f
                qte.textSize = 15f
                art_code.setText(livraisonGratuites.get(i).getARTICLE_CODE())
                qte.setText(livraisonGratuites.get(i).getQUANTITE().toString())
                ligne1.addView(art_code)
                ligne1.addView(qte)
                binding.gratuiteTl.addView(ligne1)
            }

        }
    }


}