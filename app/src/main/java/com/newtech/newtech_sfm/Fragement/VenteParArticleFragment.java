package com.newtech.newtech_sfm.Fragement;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.Impression;
import com.newtech.newtech_sfm.Metier.Unite;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.ImpressionManager;
import com.newtech.newtech_sfm.Metier_Manager.UniteManager;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.Service.BlutoothConnctionService;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VenteParArticleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VenteParArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VenteParArticleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static ImpressionManager impressionManager;

    TableLayout vente_par_article_tablelayout;
    Button mBtnPrint;
    String IMPRESSION_CODE;
    Context context;

    UniteManager uniteManager;
    ArticleManager articleManager;

    private String rapportText = "w(  Rapport : -- Vente Par Article Par Date --  \r\n"+
            "----------------------------------------------------\r\n" +
            "ARTICLE               UNITE             QUANTITE \r\n" +
            "----------------------------------------------------\r\n";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public VenteParArticleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VenteParArticleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VenteParArticleFragment newInstance(String param1, String param2) {
        VenteParArticleFragment fragment = new VenteParArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ventepararticle, container, false);
        impressionManager=new ImpressionManager(this.getContext());

        uniteManager=new UniteManager(this.getContext());
        articleManager=new ArticleManager(this.getContext());

        context = this.getActivity();
        vente_par_article_tablelayout = (TableLayout) view.findViewById(R.id.ventes_par_article_tablelayout);

        CommandeLigneManager commandeLigneManager = new CommandeLigneManager(context);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date=sdf.format(new Date()).toString();

        ArrayList<CommandeLigneManager.VenteArticle> ListeCommandeLigne;

        ListeCommandeLigne = commandeLigneManager.getRealisationArticleParDateUnite(date);



        double nbr_unite=0;
        double nbr_quantite=0;

        Log.d("ListeCommandeLigne", " "+ListeCommandeLigne.toString());

        if(ListeCommandeLigne!=null){

            for(int i=0;i<ListeCommandeLigne.size();i++){


                String article_code=ListeCommandeLigne.get(i).getARTICLE_CODE();
                String unite_code=ListeCommandeLigne.get(i).getUNITE_CODE();

                nbr_unite=0;
                nbr_quantite=0;

                Unite unite_ligne = uniteManager.get(unite_code);
                Article article = articleManager.get(ListeCommandeLigne.get(i).getARTICLE_CODE());

                TableRow ligne = new TableRow(context);

                TextView art_code = new TextView(context);
                TextView unite = new TextView(context);
                TextView quantite = new TextView(context);

                art_code.setGravity(Gravity.CENTER);
                unite.setGravity(Gravity.CENTER);
                quantite.setGravity(Gravity.CENTER);

                art_code.setTextSize(15);
                unite.setTextSize(15);
                quantite.setTextSize(15);

                nbr_quantite = ListeCommandeLigne.get(i).getQUANTITE_COMMANDEE();

                //art_code.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT,1));
                //unite.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT,1));
                //quantite.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT,1));

                art_code.setBackgroundResource(R.drawable.cell_shape);
                unite.setBackgroundResource(R.drawable.cell_shape);
                quantite.setBackgroundResource(R.drawable.cell_shape);


                art_code.setText(article.getARTICLE_DESIGNATION1());
                unite.setText(String.valueOf(unite_ligne.getUNITE_NOM()));
                quantite.setText(String.valueOf(nbr_quantite));

                ligne.addView(art_code);
                ligne.addView(unite);
                ligne.addView(quantite);

                vente_par_article_tablelayout.addView(ligne);

                rapportText+= Nchaine(article.getARTICLE_DESIGNATION1(),20)+"    "+Nchaine(unite_ligne.getUNITE_NOM(),15)+"   "+Nchaine(String.valueOf(nbr_quantite),15)+" \r\n";
            }

            rapportText+= "\r\n\r\n\r\n\r\n";
        }

        SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
        String date_commande=df.format(new java.util.Date());

        String VENDEUR_CODE="";

        SharedPreferences pref1 = context.getSharedPreferences("MyPref", 0);
        if( pref1.getString("is_login", null).equals("ok")) {
            try{
                Gson gson2 = new Gson();
                String json2 = pref1.getString("User", "");
                Type type = new TypeToken<JSONObject>() {}.getType();
                final JSONObject user = gson2.fromJson(json2, type);
                VENDEUR_CODE =user.getString("UTILISATEUR_CODE");}
            catch (Exception e){
            }
        }
        IMPRESSION_CODE = VENDEUR_CODE+date_commande;

        mBtnPrint = (Button) view.findViewById(R.id.buttonprint);

        mBtnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean printed=false;



                printed= BlutoothConnctionService.imprimanteManager.printText(rapportText);
                //ImprimanteManager.lastPrint=rapportText;

                if(printed==true){
                    Log.d("printed", "onClick: "+"imprimeée");
                    try {
                        Impression impression = new Impression(context,IMPRESSION_CODE,rapportText,"NORMAL",1,"RAPPORT");
                        impressionManager.add(impression);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    Log.d("printed", "onClick: "+"non imprimee");
                    try {
                        Impression impression = new Impression(context,IMPRESSION_CODE,rapportText,"STOCKEE",0,"RAPPORT");
                        impressionManager.add(impression);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                //BlutoothConnctionService.imprimanteManager.printText(rapportText);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    static String Nchaine(String machaine, int size){
        int taille = machaine.length();
        String spaces="";
        if(size<taille) return machaine.substring(0,size);
        for(int i=0;i<size-taille;i++)
            spaces+=" ";
        return machaine+spaces;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("VENTE PAR ARTICLE");
    }
}
