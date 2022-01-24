package com.newtech.newtech_sfm.Fragement;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.newtech.newtech_sfm.Activity.RapportMenuActivity;
import com.newtech.newtech_sfm.Configuration.VisitesJournalieres_Adapter;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.Visite;
import com.newtech.newtech_sfm.Metier_Manager.CommandeManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VisiteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VisiteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VisiteFragment extends Fragment {
    VisitesJournalieres_Adapter visitesJournalieres_adapter;
    ListView listView;
    TextView nb_visites_tv;
    ArrayList<Visite> visites = new ArrayList<>();
    VisiteManager visiteManager;
    CommandeManager commandeManager;
    Context context;
    private OnFragmentInteractionListener mListener;

    public VisiteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment VentesMensuellesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VisiteFragment newInstance() {
        VisiteFragment fragment = new VisiteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_visites_layout, container, false);

        context = this.getContext();

        visiteManager = new VisiteManager(getContext());
        commandeManager = new CommandeManager(getContext());
        visites =visiteManager.getListActifVisites();

        listView = (ListView) view.findViewById(R.id.visites_journalieres_lv);
        nb_visites_tv = view.findViewById(R.id.nb_visites_tv);

        nb_visites_tv.setText(String.valueOf(visites.size()));

        View child = getLayoutInflater().inflate(R.layout.no_data_found, null);
        ((ViewGroup)listView.getParent()).addView(child);

        listView.setEmptyView(child);

        visitesJournalieres_adapter = new VisitesJournalieres_Adapter(getActivity(), visites);
        listView.setAdapter(visitesJournalieres_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Visite visite = (Visite) visitesJournalieres_adapter.getItem(position);
                ArrayList<Commande> commandes = commandeManager.getListByVisiteCode(visite.getVISITE_CODE());

                if(commandes.size() > 0){
                    Fragment fragment = CommandeFragment.newInstance(commandes);
                    /*if (fragment != null) {
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.rapport_frame_layout, fragment);
                        fragmentTransaction.addToBackStack(fragment.getClass().getName());
                        fragmentTransaction.commit();
                    }*/
                    ((RapportMenuActivity)getActivity()).replaceFragment(fragment);

                }else{

                    showMessage("LA VISITE EST INACTIVE");
                }
                //Intent intent = new Intent(getActivity(), VentesMensuellesPopUpActivity.class);
                //VentesMensuellesPopUpActivity.realisations = venteRow.getDETAILS();
                //startActivity(intent);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("VISITES JOURNALIERES");
    }

    private void showMessage(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }
}
