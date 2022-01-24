package com.newtech.newtech_sfm.DialogCircuit;

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

import com.newtech.newtech_sfm.Metier.Circuit;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.RecensementClient.RecensementClientActivity;

import java.util.ArrayList;

public class DialogCircuit extends Dialog implements  DialogCircuitPresenter.DialogCircuitView{

    private static final String TAG = DialogCircuit.class.getName();
    private ProgressDialog progressDialog;
    private Activity activity;
    private DialogCircuitAdapter dialogCircuitAdapter;
    DialogCircuitPresenter dialogCircuitPresenter;
    Context context;
    ListView circuit_lv;
    String dialog_titre = "";
    TextView cnom_tv;

    public DialogCircuit(Context context, Activity activity, String titre) {
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


        dialogCircuitPresenter = new DialogCircuitPresenter(this);
        circuit_lv = findViewById(R.id.circuit_lv);
        cnom_tv = findViewById(R.id.cnom_tv);


        cnom_tv.setText(dialog_titre);

        dialogCircuitPresenter.synchronisationCircuit(context);

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
    public void showSuccesCircuit(ArrayList<Circuit> circuits) {

        progressDialog.hide();
        Log.d(TAG, "showSuccesCircuit: "+circuits.toString());

        dialogCircuitAdapter = new DialogCircuitAdapter(circuits,activity);
        circuit_lv.setAdapter(dialogCircuitAdapter);

        circuit_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Circuit circuit = circuits.get(position);
                ((RecensementClientActivity)((Activity)context)).setCiruit(circuit);

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
