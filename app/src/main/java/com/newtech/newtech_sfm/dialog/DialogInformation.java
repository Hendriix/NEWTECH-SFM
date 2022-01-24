package com.newtech.newtech_sfm.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.newtech.newtech_sfm.AnnulerBC.ViewBcAAnnulerActivity;
import com.newtech.newtech_sfm.R;

public class DialogInformation extends Dialog implements
        View.OnClickListener{

    public ViewBcAAnnulerActivity activity;
    public Button btn_ok;
    private TextView informaton_tv;
    private String message;

    public DialogInformation(ViewBcAAnnulerActivity activity, String msg) {
        super(activity);
        this.activity = activity;
        this.message = msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_information_dialog);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        informaton_tv = (TextView) findViewById(R.id.information_tv);
        informaton_tv.setText(message);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                activity.switchToClientActivity();
                break;
            default:
                break;
        }
        dismiss();
    }
}
