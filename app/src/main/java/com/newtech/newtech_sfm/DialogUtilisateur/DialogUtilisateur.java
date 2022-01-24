package com.newtech.newtech_sfm.DialogUtilisateur;

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

import com.newtech.newtech_sfm.Metier.User;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.RecensementClient.RecensementClientActivity;

import java.util.ArrayList;

public class DialogUtilisateur extends Dialog implements  DialogUtilisateurPresenter.DialogUtilisateurView{

    private static final String TAG = DialogUtilisateur.class.getName();
    private ProgressDialog progressDialog;
    private Activity activity;
    private DialogUtilisateurAdapter dialogUtilisateurAdapter;
    DialogUtilisateurPresenter dialogUtilisateurPresenter;
    Context context;

    ListView circuit_lv;
    TextView cnom_tv;

    String dialog_titre = "";
    String circuit_code = "";
    String distributeur_code = "";

    public DialogUtilisateur(Context context, Activity activity, String titre, String circuit_code, String distributeur_code) {
        super(context);
        this.context = context;
        this.activity = activity;
        this.dialog_titre = titre;
        this.circuit_code = circuit_code;
        this.distributeur_code = distributeur_code;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.circuit_layout);
        initProgressDialog();


        dialogUtilisateurPresenter = new DialogUtilisateurPresenter(this);
        circuit_lv = findViewById(R.id.circuit_lv);
        cnom_tv = findViewById(R.id.cnom_tv);


        cnom_tv.setText(dialog_titre);

        dialogUtilisateurPresenter.synchronisationUtilisateur(context,circuit_code,distributeur_code);

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
    public void showSuccesUtilisateur(ArrayList<User> users) {

        progressDialog.hide();
        Log.d(TAG, "showSuccesUtilisateur: "+users.toString());

        dialogUtilisateurAdapter = new DialogUtilisateurAdapter(users,activity);
        circuit_lv.setAdapter(dialogUtilisateurAdapter);

        circuit_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = users.get(position);
                ((RecensementClientActivity)((Activity)context)).setUtilisateur(user);

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
