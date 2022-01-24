package com.newtech.newtech_sfm.RapportQualitatif;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.newtech.newtech_sfm.Metier.RapportQualitatif;
import com.newtech.newtech_sfm.Metier.RapportQualitatifFamille;
import com.newtech.newtech_sfm.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RapportQualitativeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RapportQualitativeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RapportQualitativeFragment extends Fragment implements RapportQualitatifPresenter.RapportQualitatifView{
    RapportQualitatifFamilleAdapter rapportQualitatifFamilleAdapter;
    RapportQualitatifAdapter rapportQualitatifAdapter;
    ListView rapportQualitatifLvf;
    ListView rapportQualitatifLvs;
    Context context;
    private OnFragmentInteractionListener mListener;

    RapportQualitatifPresenter rapportQualitatifPresenter;

    private ProgressDialog progressDialog;

    EditText date_et;
    DatePickerDialog date_echeance_picker;
    Calendar calendar_echeance;
    Button date_btn;

    public RapportQualitativeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment VentesMensuellesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RapportQualitativeFragment newInstance() {
        RapportQualitativeFragment fragment = new RapportQualitativeFragment();
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
        View view = inflater.inflate(R.layout.fragment_rapport_qualitatif_layout, container, false);
        initProgressDialog();

        context = this.getContext();

        date_btn = view.findViewById(R.id.date_btn);
        date_et = view.findViewById(R.id.date_et);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DateVisiteAS = sdf.format(new Date());

        date_et.setText(String.valueOf(DateVisiteAS));


        rapportQualitatifPresenter = new RapportQualitatifPresenter(this);
        rapportQualitatifLvs = (ListView) view.findViewById(R.id.rapport_qualitatif_lvs);

        rapportQualitatifPresenter.getRapportdiscipline(context,DateVisiteAS);

        rapportQualitatifLvf = (ListView) view.findViewById(R.id.rapport_qualitatif_lvf);
        View child_f = getLayoutInflater().inflate(R.layout.no_data_found, null);
        ((ViewGroup)rapportQualitatifLvf.getParent()).addView(child_f);
        rapportQualitatifLvf.setEmptyView(child_f);


        rapportQualitatifLvs = (ListView) view.findViewById(R.id.rapport_qualitatif_lvs);
        View child_s = getLayoutInflater().inflate(R.layout.no_data_found, null);
        ((ViewGroup)rapportQualitatifLvs.getParent()).addView(child_s);
        rapportQualitatifLvs.setEmptyView(child_s);


        date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar_echeance = Calendar.getInstance();
                int day = calendar_echeance.get(Calendar.DAY_OF_MONTH);
                int month = calendar_echeance.get(Calendar.MONTH);
                int year = calendar_echeance.get(Calendar.YEAR);

                date_echeance_picker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        date_et.setText(i+"-"+(i1+01)+"-"+i2);
                        String echeance_date = String.valueOf(date_et.getText());
                        rapportQualitatifPresenter.getRapportdiscipline(getContext(),echeance_date);

                    }
                },year,month,day);

                date_echeance_picker.show();
            }
        });

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
    public void showSuccess(ArrayList<RapportQualitatif> rapportQualitatifs,ArrayList<RapportQualitatifFamille> rapportQualitatifFamilles) {

        progressDialog.dismiss();
        rapportQualitatifAdapter = new RapportQualitatifAdapter(getActivity(), rapportQualitatifs);
        rapportQualitatifLvf.setAdapter(rapportQualitatifAdapter);

        rapportQualitatifFamilleAdapter = new RapportQualitatifFamilleAdapter(getActivity(), rapportQualitatifFamilles);
        rapportQualitatifLvs.setAdapter(rapportQualitatifFamilleAdapter);

        Toast.makeText(this.getContext(),"SUCCESS",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showError(String message) {
        progressDialog.dismiss();
        Toast.makeText(this.getContext(),message.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmpty(String message) {
        progressDialog.dismiss();
        Toast.makeText(this.getContext(),message.toString(),Toast.LENGTH_SHORT).show();

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
        getActivity().setTitle("RAPPORT QUALITATIF");
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
