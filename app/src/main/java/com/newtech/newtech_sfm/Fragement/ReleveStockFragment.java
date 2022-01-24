package com.newtech.newtech_sfm.Fragement;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newtech.newtech_sfm.Configuration.StockPLigneViewModel;
import com.newtech.newtech_sfm.Configuration.Unite_StockPLigne_Adapter;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ReleveStockFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    ArticleManager articleManager;
    Spinner select_famille;
    public static HashMap<String,ArrayList<Article>> listArticleParFamilleCode = new HashMap<String, ArrayList<Article>>();
    private ArrayList<Article> articles = new ArrayList<>();
    private static final String TAG = ReleveStockFragment.class.getSimpleName();
    private StockPLigneViewModel stockPLigneViewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReleveStockFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ReleveStockFragment newInstance(int columnCount) {
        ReleveStockFragment fragment = new ReleveStockFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        articleManager = new ArticleManager(getActivity());
        Unite_StockPLigne_Adapter unite_stockPLigne_adapter;

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        stockPLigneViewModel = ViewModelProviders.of(getActivity()).get(StockPLigneViewModel.class);



        /*select_famille = (Spinner) view.findViewById(R.id.famille_spinner);

        ArrayList<String> items= new ArrayList<>() ;
        FamilleManager familleManager = new FamilleManager(this.getActivity());
        ArrayList<Famille> itemsFamille=  familleManager.getFamille_textList();

        for(int j=0;j<itemsFamille.size();j++){
            items.add(itemsFamille.get(j).getFAMILLE_NOM());
            listArticleParFamilleCode.put(itemsFamille.get(j).getFAMILLE_NOM(),articleManager.getListByFamilleCode(itemsFamille.get(j).getFAMILLE_CODE()));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_dropdown_item_1line,items );
        select_famille.setAdapter(adapter);

        select_famille.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String familleCode = parent.getItemAtPosition(position).toString();

                Log.d(TAG, "onItemSelected: "+familleCode.toString());



                if(!listArticleParFamilleCode.containsKey(familleCode)) {

                    listArticleCatalogue=articleManager.getListByFamilleCode(familleCode);
                    Log.d(TAG, "onItemSelected: 1");

                }else {
                    listArticleCatalogue= listArticleParFamilleCode.get(familleCode);
                    Log.d(TAG, "onItemSelected: 2");
                }

                Log.d(TAG, "onItemSelected: "+listArticleCatalogue.toString());

                // Set the adapter

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            articles = articleManager.getList();
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            recyclerView.addItemDecoration(new DividerItemDecoration(context,
                    DividerItemDecoration.VERTICAL));

            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(articles, mListener,context,this));
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Article article);
    }
}
