package com.newtech.newtech_sfm.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private static final String TAG = ScanActivity.class.getName();

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {

        //Log.d(TAG, "handleResult: "+rawResult.getText().toString());
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",rawResult.getText().toString());
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}






