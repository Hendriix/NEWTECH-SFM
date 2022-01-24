package com.newtech.newtech_sfm.DialogDistributeur;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Distributeur;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.RecensementClient.RecensementClientActivity;

import java.util.ArrayList;

public class DialogDistributeur extends Dialog implements  DialogDistributeurPresenter.DialogDistributeurView{

    private static final String TAG = DialogDistributeur.class.getName();
    private ProgressDialog progressDialog;
    private Activity activity;
    private DialogDistributeurAdapter dialogDistributeurAdapter;
    DialogDistributeurPresenter dialogDistributeurPresenter;
    Context context;
    ListView circuit_lv;
    String dialog_titre = "";
    TextView cnom_tv;


    public DialogDistributeur(Context context, Activity activity, String titre) {
        super(context);
        this.context = context;
        this.activity = activity;
        this.dialog_titre = titre;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.circuit_layout);
        initProgressDialog();


        dialogDistributeurPresenter = new DialogDistributeurPresenter(this);
        circuit_lv = findViewById(R.id.circuit_lv);
        cnom_tv = findViewById(R.id.cnom_tv);


        cnom_tv.setText(dialog_titre);

        dialogDistributeurPresenter.synchronisationDistributeur(context);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void showSuccesDistributeur(ArrayList<Distributeur> distributeurs) {

        progressDialog.hide();
        Log.d(TAG, "showSuccesDistributeur: "+distributeurs.toString());

        dialogDistributeurAdapter = new DialogDistributeurAdapter(distributeurs,activity);
        circuit_lv.setAdapter(dialogDistributeurAdapter);

        circuit_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Distributeur distributeur = distributeurs.get(position);
                ((RecensementClientActivity)((Activity)context)).setDistributeur(distributeur);

            }
        });

    }

    @Override
    public void showError(String message) {
        progressDialog.hide();
    }

    @Override
    public void showEmpty(String message) {
        progressDialog.hide();
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    private void initProgressDialog(){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Chargement en cours");
    }
}
