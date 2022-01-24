package com.newtech.newtech_sfm.RapportFondamental;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.newtech.newtech_sfm.Metier.RapportFondamental;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RapportFondamentalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RapportFondamentalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RapportFondamentalFragment extends Fragment implements RapportFondamentalPresenter.RapportFondamentalView{

    Context context;
    private OnFragmentInteractionListener mListener;
    RapportFondamentalPresenter rapportFondamentalPresenter;
    private ProgressDialog progressDialog;

    LinearLayout rapport_f_ll;
    LinearLayout no_dat_found_ll;

    TextView un_tv;
    TextView nv_tv;
    TextView mv_tv;
    TextView univers_tv;
    TextView cv_tv;
    TextView couverture_tv;
    TextView cf_tv;
    TextView activation_tv;
    TextView cfgps_tv;
    TextView mf_tv;
    TextView tgpf_tv;
    TextView spf_tv;
    TextView njt_tv;
    TextView cfa_tv;
    TextView fa_tv;
    TextView fh_tv;
    TextView fsf_tv;
    TextView frz_tv;
    TextView fza_tv;


    public RapportFondamentalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment VentesMensuellesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RapportFondamentalFragment newInstance() {
        RapportFondamentalFragment fragment = new RapportFondamentalFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rapport_fondamental_layout, container, false);
        initProgressDialog();

        context = this.getContext();

        rapport_f_ll = view.findViewById(R.id.rf_ll);
        no_dat_found_ll = view.findViewById(R.id.no_data_found_l);

        un_tv = view.findViewById(R.id.un_tv);
        nv_tv = view.findViewById(R.id.nv_tv);
        mv_tv = view.findViewById(R.id.mv_tv);
        univers_tv = view.findViewById(R.id.univers_tv);
        cv_tv = view.findViewById(R.id.cv_tv);
        couverture_tv = view.findViewById(R.id.couverture_tv);
        cf_tv = view.findViewById(R.id.cf_tv);
        activation_tv = view.findViewById(R.id.activation_tv);
        cfgps_tv = view.findViewById(R.id.cfgps_tv);
        mf_tv = view.findViewById(R.id.mf_tv);
        tgpf_tv = view.findViewById(R.id.tgpf_tv);
        spf_tv = view.findViewById(R.id.spf_tv);
        njt_tv = view.findViewById(R.id.njt_tv);
        cfa_tv = view.findViewById(R.id.cfa_tv);
        fa_tv = view.findViewById(R.id.fa_tv);
        fh_tv = view.findViewById(R.id.fh_tv);
        fsf_tv = view.findViewById(R.id.fsf_tv);
        frz_tv = view.findViewById(R.id.frz_tv);
        fza_tv = view.findViewById(R.id.fza_tv);

        rapportFondamentalPresenter = new RapportFondamentalPresenter(this);

        rapportFondamentalPresenter.getRapportFondamental(context);

        //rapportQualitatifPresenter.getRapportdiscipline(getContext());

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

    @Override
    public void showSuccess(ArrayList<RapportFondamental> rapportFondamentals) {
        progressDialog.dismiss();
        RapportFondamental rapportFondamental = rapportFondamentals.get(0);

        un_tv.setText(String.valueOf(rapportFondamental.getUTILISATEUR_NOM()));
        nv_tv.setText(String.valueOf(rapportFondamental.getNB_VISITES()));
        mv_tv.setText(String.valueOf(rapportFondamental.getMOYENNE_VISITES()));
        univers_tv.setText(String.valueOf(rapportFondamental.getUNIVERS()));
        cv_tv.setText(String.valueOf(rapportFondamental.getNBC_VISITES()));
        couverture_tv.setText(String.valueOf(rapportFondamental.getCOUVERTURE()));
        cf_tv.setText(String.valueOf(rapportFondamental.getNBC_FACTURES()));
        activation_tv.setText(String.valueOf(rapportFondamental.getACTIVATION()));
        cfgps_tv.setText(String.valueOf(rapportFondamental.getNBC_FACTURES_GPS()));
        mf_tv.setText(String.valueOf(rapportFondamental.getMOYENNE_FACTURES()));
        tgpf_tv.setText(String.valueOf(rapportFondamental.getTAUX_GPS_FACTURES()));
        spf_tv.setText(String.valueOf(rapportFondamental.getNBSKUPF01()));
        njt_tv.setText(String.valueOf(rapportFondamental.getNBJ_TRAVAILLES()));
        cfa_tv.setText(String.valueOf(rapportFondamental.getCF_AFIA()));
        fa_tv.setText(String.valueOf(rapportFondamental.getNBFAFIA01()));
        fh_tv.setText(String.valueOf(rapportFondamental.getNBFHALA01()));
        fsf_tv.setText(String.valueOf(rapportFondamental.getNBFAFIASF01()));
        frz_tv.setText(String.valueOf(rapportFondamental.getNBFRIADZ01()));
        fza_tv.setText(String.valueOf(rapportFondamental.getNBFZAYTOUNI01()));

    }

    @Override
    public void showError(String message) {
        progressDialog.dismiss();
        Toast.makeText(this.getContext(),message,Toast.LENGTH_SHORT).show();
        rapport_f_ll.setVisibility(View.GONE);
        no_dat_found_ll.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty(String message) {
        progressDialog.dismiss();
        Toast.makeText(this.getContext(),message.toString(),Toast.LENGTH_SHORT).show();
        rapport_f_ll.setVisibility(View.GONE);
        no_dat_found_ll.setVisibility(View.VISIBLE);


    }

    @Override
    public void showLoading() {
        Toast.makeText(this.getContext(),"LOADING",Toast.LENGTH_SHORT).show();
        progressDialog.show();
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
        getActivity().setTitle("RAPPORT FONDAMENTAL");
    }

    private void showMessage(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }

    private void initProgressDialog(){
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Chargement en cours");
    }
}
