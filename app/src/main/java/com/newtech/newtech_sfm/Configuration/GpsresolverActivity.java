package com.newtech.newtech_sfm.Configuration;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.newtech.newtech_sfm.Service.Gpstrackerservice;

/**
 * Created by sferricha on 26/12/2016.
 */

public class GpsresolverActivity extends AppCompatActivity {


    public static final String TAG = "GpsresolverActivity";
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    public static final String CONNECT_RESULT_KEY="connectionResult";
    public static final String CONN_STATUS_KEY = "connectionStatus";
    public static final int CONN_SUCCESS = 1;
    public static final int CONN_FAILED  = 2;
    public static final int CONN_CANCELLED = 3;

    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1111;

    private static final String ERROR_CODE_KEY = "errorCode";
    private static final String DIALOG_FRAG_TAG = "errorDialog";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate()");

        // No content needed.
        //setContentView(R.layout.activity_main);

        Intent i = getIntent();



        LocationSettingsResult result=i.getParcelableExtra("locationSettingsResult");

        Status status = result.getStatus();

        switch (status.getStatusCode()) {

            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to" +
                        "upgrade location settings ");

                // Show the dialog by calling startResolutionForResult(), and check the result
                // in onActivityResult().
                Toast.makeText(getBaseContext(), "Location dialog will be open", Toast.LENGTH_SHORT).show();
                //





                //move to step 6 in onActivityResult to check what action user has taken on settings dialog
                try {
                    status.startResolutionForResult(GpsresolverActivity.this, REQUEST_RESOLVE_ERROR);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }

                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog " +
                        "not created.");
                break;
        }

        Toast.makeText(getBaseContext(), "result)" + status.getStatusCode(),Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == REQUEST_RESOLVE_ERROR) {
            if (resultCode == RESULT_OK) {
                Log.i(TAG, "onActivityResult(): Connection problem resolved");
                sendStatusToService(CONN_SUCCESS);
            } else {
                sendStatusToService(CONN_CANCELLED);
                Log.w(TAG, "onActivityResult(): Resolution cancelled");
            }
            // Nothing more to do in this activity
            finish();
        }
    }

    private void sendStatusToService(int status) {
        Intent i = new Intent(this, Gpstrackerservice.class);
        i.putExtra("connectionStatus", status);
        startService(i);
    }

    // Fragment to display an error dialog
    public static class ErrorDialogFragment extends DialogFragment {

        public static ErrorDialogFragment newInstance(int errorCode) {
            ErrorDialogFragment f = new ErrorDialogFragment();
            // Pass the error that should be displayed
            Bundle args = new Bundle();
            args.putInt(ERROR_CODE_KEY, errorCode);
            f.setArguments(args);
            return f;
        }

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get the error code and retrieve the appropriate dialog
            int errorCode = getArguments().getInt(ERROR_CODE_KEY);
            return GooglePlayServicesUtil.getErrorDialog(
                    errorCode, getActivity(), REQUEST_RESOLVE_ERROR);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            Log.i(TAG, "Dialog dismissed");
        }
    }



}
