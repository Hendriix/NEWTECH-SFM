package com.newtech.newtech_sfm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.newtech.newtech_sfm.R;

public class DialogRecensement extends Dialog {

    private static final String TAG = DialogRecensement.class.getName();
    EditText distance_et;
    Button valider_btn,annuler_btn;
    int distance;
    Context context;

    DialogRecensementView dialogRecensementView;

    public DialogRecensement(Context context, DialogRecensementView dialogRecensementView, int distance) {
        super(context);
        this.dialogRecensementView = dialogRecensementView;
        this.context = context;
        this.distance = distance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.distance_recensement_dialog);

        distance_et= findViewById(R.id.distance_et);
        valider_btn= findViewById(R.id.btn_valider);
        annuler_btn= findViewById(R.id.btn_annuler);

        distance_et.setText(String.valueOf(distance));
        distance = Integer.parseInt(distance_et.getText().toString());

        valider_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(distance == 0 || distance_et.getText().toString().equals("")){
                    Toast.makeText(context,"distance invalide",Toast.LENGTH_SHORT).show();
                }else{
                    distance = Integer.parseInt(distance_et.getText().toString());
                    dialogRecensementView.validerDistance(distance);
                }

            }
        });

        annuler_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogRecensementView.annuler();
            }
        });

    }

    public interface DialogRecensementView{
        void validerDistance(int distance);
        void annuler();
    }


}
