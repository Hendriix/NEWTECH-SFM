package com.newtech.newtech_sfm.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.newtech.newtech_sfm.Configuration.AppConfig;
import com.newtech.newtech_sfm.Configuration.AppController;
import com.newtech.newtech_sfm.Configuration.HttpsTrustManager;
import com.newtech.newtech_sfm.Configuration.ImprimanteManager;
import com.newtech.newtech_sfm.Metier.MyDataBase;
import com.newtech.newtech_sfm.Metier.User;
import com.newtech.newtech_sfm.Metier.UtilisateurUnique;
import com.newtech.newtech_sfm.Metier_Manager.UserManager;
import com.newtech.newtech_sfm.Metier_Manager.UtilisateurUniqueManager;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.Service.AlarmReceiver;
import com.newtech.newtech_sfm.Service.BlutDiscovery;
import com.newtech.newtech_sfm.Service.BlutoothConnctionService;
import com.newtech.newtech_sfm.Service.Gpstrackerservice;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    public static ImprimanteManager imprimanteManager;
    //static boolean connexion = false;
    private static ProgressDialog progressDialog;
    public static Boolean connexion = false;
    public static String imei = null;
    public static String uid = null;
    public int READ_PHONE_STATE_CODE = 1;
    private static final String TAG = MainActivity.class.getName();

    TelephonyManager telephonyManager;
    MyDataBase myDataBase;
    TextView uid_tv;
    TextView version_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        myDataBase= new MyDataBase(getApplicationContext(), AppConfig.DATABASE_NAME,null,AppConfig.DATABASE_VERSION);
        myDataBase.onCreate(myDataBase.getWritableDatabase());
        //myDataBase.onUpgrade(myDataBase.getReadableDatabase(),52,53);
        final UtilisateurUniqueManager utilisateurUniqueManager = new UtilisateurUniqueManager(getApplicationContext());
        final ArrayList<UtilisateurUnique> utilisateurUniques = utilisateurUniqueManager.getList();


        setContentView(R.layout.activity_main);
        ImageView I = (ImageView)findViewById(R.id.logoApp);
        uid_tv = findViewById(R.id.uid_tv);
        version_tv = findViewById(R.id.version_tv);
        //uid = Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10+ Build.TAGS.length() % 10 + Build.TYPE + Build.USER.length() % 10;
         uid = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        PackageManager packageManager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = packageManager.getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        version_tv.setText(info.versionName);

        if(uid != null){
            uid_tv.setText(uid);
        }
        telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);

        I.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                //Toast.makeText(MainActivity.this, "READ PHONE STATE PERMISISION GRANTED!",Toast.LENGTH_SHORT).show();
                progressDialog = ProgressDialog.show(MainActivity.this, "", "Connexion...");
                checkConnexion(imei,uid,myDataBase);


            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(MainActivity.this, BlutoothConnctionService.class));
            startForegroundService(new Intent(MainActivity.this, BlutDiscovery.class));
        } else {
            startService(new Intent(MainActivity.this, BlutoothConnctionService.class));
            startService(new Intent(MainActivity.this, BlutDiscovery.class));
        }

        //Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pintent = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000;
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pintent);



        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {

            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {


                imei = telephonyManager.getDeviceId();

                Toast.makeText(MainActivity.this, "READ PHONE STATE PERMISISION GRANTED!"+imei,
                        Toast.LENGTH_SHORT).show();
            } else {
                requestStatePhonePermission();
            }
        }




    }

    private void requestStatePhonePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)) {

            new AlertDialog.Builder(this)
                    .setTitle("PERMISSION NEEDED")
                    .setMessage("To identify the phone for secure connection")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[] {Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            stopService(new Intent(MainActivity.this, BlutoothConnctionService.class));
                            stopService(new Intent(MainActivity.this, BlutDiscovery.class));
                            Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
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
                    new String[] {Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == READ_PHONE_STATE_CODE)  {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "READ PHONE STATE GRANTED", Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                   // Toast.makeText(MainActivity.this, "READ PHONE STATE already granted!"+telephonyManager.getDeviceId(), Toast.LENGTH_SHORT).show();

                    imei = telephonyManager.getDeviceId();

                } else {

                    stopService(new Intent(MainActivity.this, BlutoothConnctionService.class));
                    stopService(new Intent(MainActivity.this, BlutDiscovery.class));
                    Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
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

                Toast.makeText(this, "READ PHONE STATE DENIED", Toast.LENGTH_SHORT).show();
                stopService(new Intent(MainActivity.this, BlutoothConnctionService.class));
                stopService(new Intent(MainActivity.this, BlutDiscovery.class));
                Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void checkConnexion(final String imei, final String uid, final MyDataBase myDataBase) {

        //Boolean connexion = false;
        String tag_string_req = "req_login";
        HttpsTrustManager.allowAllSSL();
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN_TEST, new Response.Listener<String>() {

        UserManager userManager = new UserManager(getApplicationContext());
        UtilisateurUniqueManager utilisateurUniqueManager = new UtilisateurUniqueManager(getApplicationContext());
        UtilisateurUnique utilisateurUnique;

            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);
                    Log.d("IMEI","-@@-"+response.toString());
                    int error = jObj.getInt("statut");
                    Log.d("IMEI", "onResponse: "+error);

                    if (error==1) {
                        //Log.d("check 1", "check: "+MainActivity.connexion);

                        JSONObject user = jObj.getJSONObject("user");
                        //JSONObject utilisateurunique = jObj.getJSONObject("user_unique");

                        //UtilisateurUnique utilisateurUnique_server = new UtilisateurUnique(utilisateurunique);
                        User utilisateur_server = new User(user);
                        User utilisateur_local = userManager.get();

                        //UtilisateurUnique utilisateurUnique_local = utilisateurUniqueManager.get();

                        if(!utilisateur_server.getVERSION().equals(utilisateur_local.getVERSION())){

                            Log.d("check version", " "+utilisateur_server.getVERSION()+" "+utilisateur_local.getVERSION());

                            if(!utilisateur_server.getUTILISATEUR_CODE().equals(utilisateur_local.getUTILISATEUR_CODE())){

                                Log.d("check uc", " "+utilisateur_server.getUTILISATEUR_CODE()+" "+utilisateur_local.getUTILISATEUR_CODE());
                                Log.d("check uc", "onResponse: "+utilisateur_local.getUTILISATEUR_CODE());

                                if(utilisateur_local.getUTILISATEUR_CODE()== null){
                                    Log.d("check uc 2", "onResponse: "+utilisateur_local.getUTILISATEUR_CODE());
                                    userManager.add(utilisateur_server);

                                }else{

                                    Log.d("check uc 3", "onResponse: "+utilisateur_local.getUTILISATEUR_CODE());
                                    myDataBase.onClear(myDataBase.getWritableDatabase());
                                    userManager.add(utilisateur_server);
                                }


                            }else{

                                //Log.d("check uc 2", " "+utilisateur_server.getUTILISATEUR_CODE()+" "+utilisateur_local.getUTILISATEUR_CODE());
                                //utilisateurUniqueManager.deleteUtilisateurUniques();
                                //utilisateurUniqueManager.add(utilisateurUnique_server);

                                userManager.deleteUtilisateurs();
                                userManager.add(utilisateur_server);
                            }
                        }

                        /**
                         * Create  session
                         */

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                        SharedPreferences.Editor editor = pref.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(user);
                        editor.putString("User", json);
                        editor.putString("is_login","ok");
                        editor.putString("UTILISATEUR_CODE",user.getString("UTILISATEUR_CODE"));
                        editor.putString("DISTRIBUTEUR_CODE",user.getString("DISTRIBUTEUR_CODE"));
                        editor.commit();

                        Intent intent = new Intent(MainActivity.this,AuthActivity.class);
                        intent.putExtra("UTILISATEUR_CODE",user.getString("UTILISATEUR_CODE"));
                        startActivity(intent);
                        finish();
                        progressDialog.dismiss();


                    }else if(error == 2){

                        String errorMsg = jObj.getString("info");
                        Log.d(TAG, "onResponse: "+errorMsg);
                        userManager.deleteUtilisateurs();
                        //utilisateurUniqueManager.deleteUtilisateurUniques();
                        Toast.makeText(getApplicationContext(),errorMsg, Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),"L'APPLICATION EST BLOQUEE IM", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();

                    }else{

                        Log.d(TAG, "onResponse: 33");
                        if(userManager.count()>0){

                            Intent intent = new Intent(MainActivity.this,AuthActivity.class);
                            startActivity(intent);
                            finish();
                            progressDialog.dismiss();

                        }else{

                            Toast.makeText(getApplicationContext(),"L'APPLICATION EST BLOQUEE", Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(),"MERCI DE REESSAYER:", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }

                        //Log.d("check 2", "check: "+MainActivity.connexion);
                        //Toast.makeText(getApplicationContext(),"CET APPAREIL EST BLOQUE MERCI DE CONTACTER VOTRE ADMINISTRATEUR",Toast.LENGTH_SHORT).show();
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("info");
                        Toast.makeText(getApplicationContext(),errorMsg, Toast.LENGTH_LONG).show();

                    }
                    //Log.d("Check connexion", "onResponse: "+connexion);

                } catch (JSONException e) {
                    Log.d(TAG, "onResponse: "+e.getMessage());
                    Toast.makeText(getApplicationContext(),"UNE ERREUR EST SURVENUE MERCI DE CONTACTER VOTRE ADMINISTRATEUR", Toast.LENGTH_LONG).show();
                    //Log.d("check 3", "check: "+MainActivity.connexion);
                    // JSON error
                    //Log.d("authentification", "onErrorResponse: "+response);
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " +"erreur applcation" + e.getMessage(), Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.d("check 4", "check: "+MainActivity.connexion);

                Log.d("authentification 2", "onErrorResponse: "+error);
                NetworkResponse response = error.networkResponse;

                if (error instanceof TimeoutError) {
                    Toast.makeText(MainActivity.this,"Timout",
                            Toast.LENGTH_LONG).show();

                } else if (error instanceof NoConnectionError) {
                    //TODO
                    Toast.makeText(MainActivity.this,"No Connection",
                            Toast.LENGTH_LONG).show();
                }else if (error instanceof AuthFailureError) {
                    //TODO
                    Toast.makeText(MainActivity.this,"AuthFailure",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    //TODO
                    Toast.makeText(MainActivity.this,"Server",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    //TODO
                    Toast.makeText(MainActivity.this,"Network",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    //TODO
                    Toast.makeText(MainActivity.this,"Parse",
                            Toast.LENGTH_LONG).show();
                }

                /*if(response != null && response.data != null){
                    Toast.makeText(getApplicationContext(),"ErrorMessage:"+response.statusCode, Toast.LENGTH_SHORT).show();

                    Log.d("abd", "Error: " + error
                            + ">>" + error.networkResponse.statusCode
                            + ">>" + error.networkResponse.data
                            + ">>" + error.getCause()
                            + ">>" + error.getMessage());
                }else{
                    String errorMessage=error.getClass().getSimpleName();
                    if(!TextUtils.isEmpty(errorMessage)){
                        Toast.makeText(getApplicationContext(),"ErrorMessage:"+errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }*/

                //VolleyLog.e("Error: ", error.getMessage());

                //UtilisateurUniqueManager utilisateurUniqueManager = new UtilisateurUniqueManager(getApplicationContext());
                UserManager userManager = new UserManager(getApplicationContext());

                if(userManager.count()>0){

                    Intent intent = new Intent(MainActivity.this,AuthActivity.class);
                    startActivity(intent);
                    finish();
                    progressDialog.dismiss();
                }else{

                    Toast.makeText(getApplicationContext(),"L'APPLICATION EST BLOQUEE ER", Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),"MERCI DE REESSAYER: ER", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }

            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                //Log.d("check 5", "check: "+MainActivity.connexion);
                //Log.d("check 5", "checkIMEI: "+imei);
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                Log.d(TAG, "getParams: SDK "+Build.VERSION.SDK_INT);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    params.put("IMEI", uid);
                } else {
                    params.put("IMEI", imei);
                }

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

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



    private Handler messageHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(connexion == true){
                Log.d("check connexion true ", "handleMessage: "+connexion);
                //Toast.makeText(getApplicationContext(), "check connexion true "+connexion, Toast.LENGTH_LONG).show();
            }else{
                Log.d("check connexion false ", "handleMessage: "+connexion);
                //Toast.makeText(getApplicationContext(), "check connexion false "+connexion, Toast.LENGTH_LONG).show();
            }
            progressDialog.dismiss();
        }
    };

    /*private class VerifierConnexion extends AsyncTask<String, Void, Boolean> {

        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        boolean connexion1;


        @Override
        protected Boolean doInBackground(String... params) {

            checkConnexion(imei);
            Log.d("check connexion", "while: "+MainActivity.connexion);

            return MainActivity.connexion;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            super.onPostExecute(b);
            //Toast.makeText(getApplicationContext(), "check connexion "+MainActivity.connexion, Toast.LENGTH_LONG).show();
        }

    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopService(new Intent(MainActivity.this, BlutoothConnctionService.class));
        stopService(new Intent(MainActivity.this, BlutDiscovery.class));
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
        stopService(new Intent(MainActivity.this, Gpstrackerservice.class));
        finish();
    }

}


