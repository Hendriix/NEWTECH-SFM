package com.newtech.newtech_sfm.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Configuration.Common;
import com.newtech.newtech_sfm.Configuration.ListDataSave;
import com.newtech.newtech_sfm.Configuration.RVAdapter;
import com.newtech.newtech_sfm.Metier.Logs;
import com.newtech.newtech_sfm.Metier.Parametre;
import com.newtech.newtech_sfm.Metier.User;
import com.newtech.newtech_sfm.Metier_Manager.ChoufouniContratImageManager;
import com.newtech.newtech_sfm.Metier_Manager.ChoufouniContratManager;
import com.newtech.newtech_sfm.Metier_Manager.ChoufouniContratPullManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeNonClotureeManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonManager;
import com.newtech.newtech_sfm.Metier_Manager.LogsManager;
import com.newtech.newtech_sfm.Metier_Manager.ParametreManager;
import com.newtech.newtech_sfm.Metier_Manager.StockDemandeLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.StockDemandeManager;
import com.newtech.newtech_sfm.Metier_Manager.StockTransfertManager;
import com.newtech.newtech_sfm.Metier_Manager.UniteManager;
import com.newtech.newtech_sfm.Metier_Manager.UserManager;
import com.newtech.newtech_sfm.Metier_Manager.UtilisateurUniqueManager;
import com.newtech.newtech_sfm.Metier_Manager.VisibiliteLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.VisibiliteManager;
import com.newtech.newtech_sfm.Metier_Manager.VisibiliteRayonManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteResultatManager;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.Service.AlarmReceiver;
import com.newtech.newtech_sfm.Service.BlutDiscovery;
import com.newtech.newtech_sfm.Service.BlutoothConnctionService;
import com.newtech.newtech_sfm.Service.Gpstrackerservice;
import com.newtech.newtech_sfm.recensement.RecensementActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by stagiaireit2 on 28/06/2016.
 */
public class MenuActivity extends AppCompatActivity {

    public String utilisateur_code = "";
    private static final String TAG = MenuActivity.class.getName();

    UtilisateurUniqueManager utilisateurUniqueManager;
    UserManager userManager;
    StockDemandeManager stockDemandeManager;
    StockDemandeLigneManager stockDemandeLigneManager;
    UniteManager uniteManager;
    VisibiliteManager visibiliteManager;
    VisibiliteLigneManager visibiliteLigneManager;
    VisibiliteRayonManager visibiliteRayonManager;
    ChoufouniContratManager choufouniContratManager;
    ChoufouniContratPullManager choufouniContratPullManager;
    LivraisonManager livraisonManager;
    LivraisonLigneManager livraisonLigneManager;
    ChoufouniContratImageManager choufouniContratImageManager;
    ParametreManager parametreManager;
    VisiteResultatManager visiteResultatManager;
    StockTransfertManager stockTransfertManager;
    CommandeNonClotureeManager commandeNonClotureeManager;
    CommandeManager commandeManager;
    LogsManager logsManager;

    ArrayList<User> users = new ArrayList<>();
    User user;
    public int WRITE_EXTERNAL_STORAGE_CODE = 5;
    private ArrayList<CardView> cardViewArrayList;
    Dialog myDialog;
    Dialog updateDialog;

    CardView tacheCv;
    CardView tourneeCv;
    CardView clientsCv;
    CardView catalogueCv;
    CardView rapportCv;
    CardView dbClientCv;
    CardView synchroniserCv;
    CardView stockCv;
    CardView quitterCv;
    CardView encaissementCv;
    CardView validerRecensementCv;

    PackageManager packageManager;
    PackageInfo packageInfo;
    int versionCode;
    int version_server;

    Boolean date_valide = true;
    ListDataSave listDataSave;


    public MenuActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        utilisateurUniqueManager = new UtilisateurUniqueManager(getApplicationContext());
        userManager = new UserManager(getApplicationContext());
        stockDemandeManager = new StockDemandeManager(getApplicationContext());
        stockDemandeLigneManager = new StockDemandeLigneManager(getApplicationContext());
        uniteManager = new UniteManager(getApplicationContext());
        userManager = new UserManager(getApplicationContext());
        visibiliteManager = new VisibiliteManager(getApplicationContext());
        visibiliteLigneManager = new VisibiliteLigneManager(getApplicationContext());
        visibiliteRayonManager = new VisibiliteRayonManager(getApplicationContext());
        choufouniContratManager = new ChoufouniContratManager(this);
        choufouniContratImageManager = new ChoufouniContratImageManager(this);
        parametreManager = new ParametreManager(this);
        visiteResultatManager = new VisiteResultatManager(this);
        stockTransfertManager = new StockTransfertManager(this);
        choufouniContratPullManager = new ChoufouniContratPullManager(this);
        livraisonManager = new LivraisonManager(this);
        livraisonLigneManager = new LivraisonLigneManager(this);
        commandeNonClotureeManager = new CommandeNonClotureeManager(this);
        commandeManager = new CommandeManager(this);
        logsManager = new LogsManager(this);

        myDialog = new Dialog(this);
        updateDialog = new Dialog(this);
        listDataSave = new ListDataSave(getApplicationContext(), "MyPref");


        Log.d(TAG, "onCreate: parametres : " + parametreManager.getList().toString());

        Parametre parametre_version = parametreManager.get("VERSION");
        Parametre parametre_date_serveur = parametreManager.get("DATE_SERVEUR");


        packageManager = this.getPackageManager();
        packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            versionCode = (int) packageInfo.getLongVersionCode(); // avoid huge version numbers and you will be ok
        } else {
            //noinspection deprecation
            versionCode = packageInfo.versionCode;
        }

        Log.d(TAG, "onCreate: 1" + parametre_date_serveur.toString());

        /*VERIFIER LA DATE ET LA VERSION DE L'APPLICATION*/

        Boolean date_valide = Common.verifyDate(getApplicationContext(), MenuActivity.this);
        Common.verifyVersion(getApplicationContext(), MenuActivity.this, date_valide);

        Log.d(TAG, "onCreate: " + commandeNonClotureeManager.getList());

        ///////////////////////////////////////////////////

        /*if(parametre_date_serveur.getVALEUR() != null){

            Log.d(TAG, "onCreate: 2"+parametre_date_serveur.toString());

            try{

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String date_telephone = df.format(Calendar.getInstance().getTime());
                String date_serveur = parametre_date_serveur.getVALEUR();

                if(!date_serveur.equals(date_telephone)){

                    date_valide = false;
                    updateDialog.setContentView(R.layout.update_popup);
                    Button update_btn = (Button)updateDialog.findViewById(R.id.update_btn);
                    TextView information_tv = (TextView)updateDialog.findViewById(R.id.information_tv);

                    update_btn.setText("FERMER");
                    information_tv.setText("LA DATE DU TELEPHONE N'EST PAS VALIDE L'APPLICATION SERA BLOQUEE ...");

                    Logs logs = new Logs(getApplicationContext(),"TP0075");
                    logsManager.add(logs);
                    LogsManager.synchronisationLogs(getApplicationContext());

                    update_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            updateDialog.dismiss();
                            Intent intent = new Intent(MenuActivity.this, SyncV2Activity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    updateDialog.setCanceledOnTouchOutside(false);
                    updateDialog.setCancelable(false);

                    updateDialog.show();
                }

            }catch (NullPointerException e){
                Log.d(TAG, "onCreate: "+e.getMessage());
            }
        }*/

        /*if(parametre_version.getVALEUR() != null && date_valide == true){

            try{
                version_server = Integer.parseInt(parametre_version.getVALEUR());

                if(versionCode != version_server){
                    Log.d(TAG, "onCreate: update");

                    updateDialog.setContentView(R.layout.update_popup);
                    Button update_btn = (Button)updateDialog.findViewById(R.id.update_btn);
                    TextView information_tv = (TextView)updateDialog.findViewById(R.id.information_tv);

                    update_btn.setText("OK");
                    information_tv.setText("SYNCHRONISEZ VOS DONNEES ET PASSEZ SUR PLAYSTORE POUR LA MISE A JOUR.");

                    Logs logs = new Logs(getApplicationContext(),"TP00764");
                    logsManager.add(logs);
                    LogsManager.synchronisationLogs(getApplicationContext());

                    update_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            updateDialog.dismiss();
                            Intent intent = new Intent(MenuActivity.this, SyncV2Activity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    updateDialog.setCanceledOnTouchOutside(false);
                    updateDialog.setCancelable(false);

                    updateDialog.show();
                }else{
                    Log.d(TAG, "onCreate: updated");
                }

            }catch(NumberFormatException e){
                Log.d(TAG, "onCreate: "+e.getMessage());
            }
        }


        //GET UTILISATEUR CODE FROM AUTHACTIVITY

        Intent myintent = getIntent();
        Bundle extras = myintent.getExtras();
        if (extras != null){
            if(extras.containsKey("UTILISATEUR_CODE")){
                utilisateur_code=extras.getString("UTILISATEUR_CODE");
            }
        }*/

        SharedPreferences pref = this.getSharedPreferences("MyPref", 0);
        Gson gson2 = new Gson();
        String json2 = pref.getString("User", "");
        Type type = new TypeToken<JSONObject>() {
        }.getType();
        JSONObject utilisateur = gson2.fromJson(json2, type);

        try {
            utilisateur_code = utilisateur.getString("UTILISATEUR_CODE");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        setContentView(R.layout.menu_dashboard_layout);

        tacheCv = findViewById(R.id.tache_cv);
        tourneeCv = findViewById(R.id.tournee_cv);
        clientsCv = findViewById(R.id.clients_cv);
        catalogueCv = findViewById(R.id.catalogue_cv);
        rapportCv = findViewById(R.id.rapport_cv);
        dbClientCv = findViewById(R.id.db_client_cv);
        synchroniserCv = findViewById(R.id.synchroniser_cv);
        stockCv = findViewById(R.id.stock_cv);
        quitterCv = findViewById(R.id.quitter_cv);
        encaissementCv = findViewById(R.id.encaissement_cv);
        validerRecensementCv = findViewById(R.id.validerRecensementCv);


        if (utilisateur_code != "" && utilisateur_code != null) {

            user = userManager.get(utilisateur_code);

            if (user.getUTILISATEUR_CODE() != null) {

                setTitle(user.getUTILISATEUR_NOM());
                if (user.getPROFILE_CODE().equals("PF0011") || user.getPROFILE_CODE().equals("PF0002")) {

                    hideView(encaissementCv);
                    hideView(stockCv);
                    hideView(validerRecensementCv);
                    /*encaissementCv.setVisibility(View.GONE);
                    stockCv.setVisibility(View.GONE);
                    validerRecensementCv.setVisibility(View.GONE);*/
                    //validerRecensementCv.setVisibility(View.GONE);

                } else if (user.getPROFILE_CODE().equals("PF0006")) {

                    hideView(clientsCv);
                    hideView(dbClientCv);
                    hideView(stockCv);
                    hideView(encaissementCv);

                    /*clientsCv.setVisibility(View.GONE);
                    dbClientCv.setVisibility(View.GONE);
                    stockCv.setVisibility(View.GONE);
                    encaissementCv.setVisibility(View.GONE);*/

                } else if (user.getPROFILE_CODE().equals("PF0012")) {

                    hideView(clientsCv);
                    hideView(dbClientCv);
                    hideView(stockCv);
                    hideView(encaissementCv);
                    hideView(validerRecensementCv);
                    /*clientsCv.setVisibility(View.GONE);
                    dbClientCv.setVisibility(View.GONE);
                    stockCv.setVisibility(View.GONE);
                    encaissementCv.setVisibility(View.GONE);
                    validerRecensementCv.setVisibility(View.GONE);*/

                } else {
                    hideView(validerRecensementCv);
                    //validerRecensementCv.setVisibility(View.GONE);
                }
            } else {
                setTitle("MENU");
            }
        } else {
            //Log.d("menu", "onCreate: 2 "+user.getUTILISATEUR_NOM());
            setTitle("MENU");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        setSupportActionBar(toolbar);

        startService(new Intent(this, Gpstrackerservice.class));

        if (ContextCompat.checkSelfPermission(MenuActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestWriteExternalStoragePermission();
        }

        Logs logs = new Logs(getApplicationContext(), "TP0073");
        logsManager.add(logs);
        logsManager.synchronisationLogs(getApplicationContext());

        if (tacheCv != null) {

            tacheCv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(MenuActivity.this, CatalogueTacheActivity.class);
                    listDataSave.remove("client_list");
                    listDataSave.remove("tournee_code");
                    listDataSave.remove("type_code");
                    listDataSave.remove("classe_code");
                    listDataSave.remove("categorie_code");
                    startActivity(intent);
                    finish();
                }
            });
        }

        if (tourneeCv != null) {
            tourneeCv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(MenuActivity.this, TourneeActivity.class);
                    listDataSave.remove("client_list");
                    listDataSave.remove("tournee_code");
                    listDataSave.remove("type_code");
                    listDataSave.remove("classe_code");
                    listDataSave.remove("categorie_code");
                    startActivity(intent);
                    finish();
                }
            });
        }

        if (clientsCv != null) {
            clientsCv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(MenuActivity.this, VisiteActivity.class);
                    VisiteActivity.commande_source = "VISITE";
                    VisiteActivity.activity_source = "MenuActivity";
                    VisiteActivity.affectation_valeur = "tous";
                    listDataSave.remove("client_list");
                    listDataSave.remove("tournee_code");
                    listDataSave.remove("type_code");
                    listDataSave.remove("classe_code");
                    listDataSave.remove("categorie_code");
                    //intent.putExtra("TOURNEE_CODE",tournee.getTOURNEE_CODE());
                    startActivity(intent);
                    finish();
                }
            });
        }

        if (synchroniserCv != null) {
            synchroniserCv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(MenuActivity.this, SyncV2Activity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        if (catalogueCv != null) {
            catalogueCv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    RVAdapter.currentActivity = "CatalogueActivity";
                    Intent intent = new Intent(MenuActivity.this, CatalogueActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }


        if (rapportCv != null) {
            rapportCv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    RVAdapter.currentActivity = "RapportActivity";
                    //Intent intent = new Intent(MenuActivity.this, RapportActivity.class);
                    Intent intent = new Intent(MenuActivity.this, RapportMenuActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        if (dbClientCv != null) {
            dbClientCv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    //CatalogueClientActivity.tournee_code = "tous";
                    Intent intent = new Intent(MenuActivity.this, CatalogueClientNActivity.class);
                    // Intent intent = new Intent(MenuActivity.this, AnnulerCommandeActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        if (quitterCv != null) {
            quitterCv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    Logs logs = new Logs(getApplicationContext(), "TP0074");
                    logsManager.add(logs);
                    LogsManager.synchronisationLogs(getApplicationContext());

                    stopService(new Intent(MenuActivity.this, BlutoothConnctionService.class));
                    stopService(new Intent(MenuActivity.this, BlutDiscovery.class));
                    Intent intent = new Intent(MenuActivity.this, AlarmReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(MenuActivity.this, 0,
                            intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    stopService(new Intent(MenuActivity.this, Gpstrackerservice.class));
                    //finish();
                    //System.exit(0);
                    Intent intent_login = new Intent(MenuActivity.this, AuthActivity.class);
                    startActivity(intent_login);
                    finish();
                }
            });
        }

        if (stockCv != null) {
            stockCv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    StockDemandeManager.synchronisationStockDemandeReceptionnee(getApplicationContext());
                    StockDemandeLigneManager.synchronisationStockDemandeLigneReceptionnee(getApplicationContext());

                    Intent intent = new Intent(MenuActivity.this, StockActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        if (encaissementCv != null) {
            encaissementCv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    Intent intent = new Intent(MenuActivity.this, CreditActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        if (validerRecensementCv != null) {
            validerRecensementCv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    Intent intent = new Intent(MenuActivity.this, RecensementActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

    }


    private void requestWriteExternalStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("PERMISSION NEEDED")
                    .setMessage("TO SAVE PICTURES OF CLIENTS")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MenuActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            stopService(new Intent(MenuActivity.this, BlutoothConnctionService.class));
                            stopService(new Intent(MenuActivity.this, BlutDiscovery.class));
                            Intent intent = new Intent(MenuActivity.this, AlarmReceiver.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(MenuActivity.this, 0,
                                    intent, PendingIntent.FLAG_CANCEL_CURRENT);
                            try {
                                pendingIntent.send();
                            } catch (PendingIntent.CanceledException e) {
                                e.printStackTrace();
                            }
                            finish();
                            System.exit(0);
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "WRITE EXTERNAL STORAGE GRANTED", Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(MenuActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(MenuActivity.this, "WRITE EXTERNAL STORAGE already granted",
                    //Toast.LENGTH_SHORT).show();

                } else {

                    stopService(new Intent(MenuActivity.this, BlutoothConnctionService.class));
                    stopService(new Intent(MenuActivity.this, BlutDiscovery.class));
                    Intent intent = new Intent(MenuActivity.this, AlarmReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(MenuActivity.this, 0,
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

                Toast.makeText(this, "WRITE EXTERNAL STORAGE DENIED", Toast.LENGTH_SHORT).show();
                stopService(new Intent(MenuActivity.this, BlutoothConnctionService.class));
                stopService(new Intent(MenuActivity.this, BlutDiscovery.class));
                Intent intent = new Intent(MenuActivity.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MenuActivity.this, 0,
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
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Appuyer sur QUITTER pour fermer l'application", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;

            case R.id.option1:

                String information = "VersionCode = "
                        + versionCode + "\nVersionName = "
                        + packageInfo.versionName;

                myDialog.setContentView(R.layout.about_popup);
                TextView version = (TextView) myDialog.findViewById(R.id.version);
                version.setText(information);

                myDialog.show();

                return true;

            case R.id.option2:
                Intent intt = new Intent(MenuActivity.this, PrintActivity2.class);
                intt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intt);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Log.d("Menu", "onStart: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Log.d("Menu", "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Log.d("Menu", "onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Log.d("Menu", "onRestart: ");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Log.d("Menu", "onDestroy: ");
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void hideView(View view) {
        GridLayout gridLayout = (GridLayout) view.getParent();
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            if (view == gridLayout.getChildAt(i)) {
                gridLayout.removeViewAt(i);
                break;
            }
        }
    }

    private void showText(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


}
