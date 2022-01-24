package com.newtech.newtech_sfm.DialogTournee;

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

import com.newtech.newtech_sfm.Metier.Tournee;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.RecensementClient.RecensementClientActivity;

import java.util.ArrayList;

public class DialogTournee extends Dialog implements  DialogTourneePresenter.DialogTourneeView{

    private static final String TAG = DialogTournee.class.getName();
    private ProgressDialog progressDialog;
    private Activity activity;
    private DialogTourneeAdapter dialogTourneeAdapter;
    DialogTourneePresenter dialogTourneePresenter;
    Context context;

    ListView circuit_lv;
    TextView cnom_tv;

    String dialog_titre = "";
    String utilisateur_code = "";

    public DialogTournee(Context context, Activity activity, String titre, String utilisateur_code) {
        super(context);
        this.context = context;
        this.activity = activity;
        this.dialog_titre = titre;
        this.utilisateur_code = utilisateur_code;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.circuit_layout);
        initProgressDialog();


        dialogTourneePresenter = new DialogTourneePresenter(this);
        circuit_lv = findViewById(R.id.circuit_lv);
        cnom_tv = findViewById(R.id.cnom_tv);


        cnom_tv.setText(dialog_titre);

        dialogTourneePresenter.synchronisationTournee(context,utilisateur_code);

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
    public void showSuccesTournee(ArrayList<Tournee> tournees) {

        progressDialog.hide();
        Log.d(TAG, "showSuccesTournee: "+tournees.toString());

        dialogTourneeAdapter = new DialogTourneeAdapter(tournees,activity);
        circuit_lv.setAdapter(dialogTourneeAdapter);

        circuit_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tournee tournee = tournees.get(position);
                ((RecensementClientActivity)((Activity)context)).setTournee(tournee);

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
