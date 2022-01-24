package com.newtech.newtech_sfm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.newtech.newtech_sfm.R;

public class DialogConfirmation extends Dialog {

    public Button btn_oui, btn_non;

    private AnnulerBcView annulerBcView;
    private String message;

    public DialogConfirmation(Context context, AnnulerBcView annulerBcView, String msg) {
        super(context);
        this.annulerBcView = annulerBcView;
        this.message = msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_confirm_dialog);
        btn_oui = (Button) findViewById(R.id.btn_oui);
        btn_non = (Button) findViewById(R.id.btn_non);

        btn_oui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                annulerBcView.accepteAnnulation();
            }
        });

        btn_non.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                annulerBcView.refuseAnnulation();
            }
        });

    }

    public interface AnnulerBcView{
        void accepteAnnulation();
        void refuseAnnulation();
    }
}
