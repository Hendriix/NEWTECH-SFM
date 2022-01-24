package com.newtech.newtech_sfm.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.newtech.newtech_sfm.Configuration.AppConfig;
import com.newtech.newtech_sfm.Metier.Logs;
import com.newtech.newtech_sfm.Metier.User;
import com.newtech.newtech_sfm.Metier_Manager.LogsManager;
import com.newtech.newtech_sfm.Metier_Manager.UserManager;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.Service.AlarmReceiver;
import com.newtech.newtech_sfm.Service.BlutDiscovery;
import com.newtech.newtech_sfm.Service.BlutoothConnctionService;
import com.newtech.newtech_sfm.firebase.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mehdi on 22/06/2016.
 */
public class AuthActivity extends Activity {

    private EditText inputEmail;
    private EditText inputPassword;
    public static String login = "";

    UserManager userManager;
    User utilisateur;

    public int ACCESS_FINE_LOCATION_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.int_authentification);

        userManager = new UserManager(getApplicationContext());
        utilisateur = userManager.get();

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.pass);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);


        if (ContextCompat.checkSelfPermission(AuthActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestFineLocationPermission();
        }

        if (!utilisateur.equals(null)) {
            inputEmail.setText(String.valueOf(utilisateur.getUTILISATEUR_CODE()));
        } else {
            Toast.makeText(getApplicationContext(), "Merci de Reessayer Ou contacter votre administrateur:", Toast.LENGTH_SHORT).show();
        }

        inputEmail.setEnabled(false);

        inputPassword.setText(R.string.savola);

        inputPassword.setEnabled(false);

        // Firebase Instance Id
        //startService(new Intent(AuthActivity.this, MyFirebaseInstanceIDService.class));


        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();


                if (!email.isEmpty() && !password.isEmpty()) {

                    if (CheckUnique_Utilisateur(email)) {

                        LogsManager logsManager = new LogsManager(getApplicationContext());
                        Logs logs = new Logs(getApplicationContext(), "TP0072");
                        logsManager.add(logs);
                        LogsManager.synchronisationLogs(getApplicationContext());

                        Intent intent = new Intent(AuthActivity.this, MenuActivity.class);
                        intent.putExtra("UTILISATEUR_CODE", email);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Bienvenue " + utilisateur.getUTILISATEUR_NOM().toString(), Toast.LENGTH_SHORT).show();
                        finish();

                    } else {

                        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(getApplicationContext(), "L'APPLICATION EST BLOQUEE", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Entrez vos coordonnées", Toast.LENGTH_LONG).show();
                }
            }
        });
        setTitle("Autentification");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void requestFineLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {

            new AlertDialog.Builder(this)
                    .setTitle("PERMISSION NEEDED")
                    .setMessage("TO SPOT THE PHONE")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(AuthActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            stopService(new Intent(AuthActivity.this, BlutoothConnctionService.class));
                            stopService(new Intent(AuthActivity.this, BlutDiscovery.class));
                            Intent intent = new Intent(AuthActivity.this, AlarmReceiver.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(AuthActivity.this, 0,
                                    intent, PendingIntent.FLAG_CANCEL_CURRENT);
                            try {
                                pendingIntent.send();
                            } catch (PendingIntent.CanceledException e) {
                                e.printStackTrace();
                            }
                            finish();
                            System.exit(0);

                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ACCESS_FINE_LOCATION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "ACCESS FINE LOCATION GRANTED", Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(AuthActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(AuthActivity.this, "FINE LOCATION already granted!",
                    //Toast.LENGTH_SHORT).show();


                    Log.d("PERMISSION", "onRequestPermissionsResult: " + "FINE LOCATION already granted");

                } else {

                    Log.d("PERMISSION", "onRequestPermissionsResult: " + "FINE LOCATION not granted");

                    stopService(new Intent(AuthActivity.this, BlutoothConnctionService.class));
                    stopService(new Intent(AuthActivity.this, BlutDiscovery.class));
                    Intent intent = new Intent(AuthActivity.this, AlarmReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(AuthActivity.this, 0,
                            intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    try {
                        pendingIntent.send();
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }
                    finish();
                    System.exit(0);
                }

            } else {

                Toast.makeText(this, "FINE LOCATION DENIED", Toast.LENGTH_SHORT).show();
                stopService(new Intent(AuthActivity.this, BlutoothConnctionService.class));
                stopService(new Intent(AuthActivity.this, BlutDiscovery.class));
                Intent intent = new Intent(AuthActivity.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AuthActivity.this, 0,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);
                try {
                    pendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
                finish();
                System.exit(0);

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.option2:
                /* DO DELETE */
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean CheckUnique_Utilisateur(String utilisateur_code) {

        UserManager userManager = new UserManager(getApplicationContext());
        User user = userManager.get();

        if ((user.getUTILISATEUR_CODE().equals(utilisateur_code)) && user.getINACTIF().equals("0")) {

            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //storing token to mysql server
    private void sendTokenToServer(String utilisateur_code) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering Device...");
        progressDialog.show();

        final String token = SharedPrefManager.getInstance(this).getDeviceToken();
        final String email = utilisateur_code;

        if (token == null) {
            progressDialog.dismiss();
            Toast.makeText(this, "Token not generated", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_REGISTER_DEVICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = null;
                            try {
                                obj = new JSONObject(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(AuthActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(AuthActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UTILISATEUR_CODE", email);
                params.put("TOKEN", token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
