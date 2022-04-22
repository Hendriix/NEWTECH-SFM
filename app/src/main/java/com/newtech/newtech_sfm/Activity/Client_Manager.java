package com.newtech.newtech_sfm.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.newtech.newtech_sfm.Configuration.Spinner_Categorie_Adapter;
import com.newtech.newtech_sfm.Configuration.Spinner_Classe_Adapter;
import com.newtech.newtech_sfm.Configuration.Spinner_Type_Adapter;
import com.newtech.newtech_sfm.Configuration.Tournee_Adapter;
import com.newtech.newtech_sfm.Metier.Categorie;
import com.newtech.newtech_sfm.Metier.Classe;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.ClientN;
import com.newtech.newtech_sfm.Metier.Parametre;
import com.newtech.newtech_sfm.Metier.Tournee;
import com.newtech.newtech_sfm.Metier.Type;
import com.newtech.newtech_sfm.Metier_Manager.CategorieManager;
import com.newtech.newtech_sfm.Metier_Manager.ClasseManager;
import com.newtech.newtech_sfm.Metier_Manager.ClientManager;
import com.newtech.newtech_sfm.Metier_Manager.ClientNManager;
import com.newtech.newtech_sfm.Metier_Manager.ParametreManager;
import com.newtech.newtech_sfm.Metier_Manager.TourneeManager;
import com.newtech.newtech_sfm.Metier_Manager.TypeManager;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.Service.AlarmReceiver;
import com.newtech.newtech_sfm.Service.BlutDiscovery;
import com.newtech.newtech_sfm.Service.BlutoothConnctionService;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sferricha on 25/10/2016.
 */

public class Client_Manager extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ResultCallback<LocationSettingsResult> {

    public String SOURCE = "";

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public int CAMERA_CODE = 4;


    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";

    private Uri fileUri; // file url to store image/video

    private static float accuracy;

    SwitchMaterial edit_client_image_switch;
    Button charger_image_btn;


    SwitchMaterial edit_qr_switch;
    ImageView scan_qr_iv;
    EditText qr_code_et;


    SwitchMaterial edit_identity_switch;
    LinearLayout identity_ll;
    EditText client_nom;
    EditText client_prenom;
    EditText telephone;

    SwitchMaterial edit_adresse_switch;
    LinearLayout adresse_ll;
    EditText adresse_NR;
    EditText adresse_RUE;
    EditText adresse_Quartier;

    SwitchMaterial edit_categorie_switch;
    LinearLayout category_ll;
    Spinner spinnertournee;
    Spinner spinnerCategorie;
    Spinner spinnerClasse;
    Spinner spinnerType;

    SwitchMaterial edit_gps_switch;
    LinearLayout gps_ll;
    EditText latitudeZT;
    EditText longitudeZT;


    EditText accuracyET;
    TextView accuracyTV;

    Tournee_Adapter tourneAdapter;
    Spinner_Categorie_Adapter spinner_categorie_adapter;
    Spinner_Classe_Adapter spinner_classe_adapter;
    Spinner_Type_Adapter spinner_type_adapter;

    ArrayList<Tournee> listTournee = new ArrayList<>();
    ArrayList<Categorie> categories = new ArrayList<>();
    ArrayList<Classe> classes = new ArrayList<>();
    ArrayList<Type> types = new ArrayList<>();

    ImageView imageView1;

    ClientNManager clientN_Manager;
    ClientManager client_Manager;
    ParametreManager parametreManager;

    boolean isNew = false;
    // *gps

    // LogCat tag
    private static final String TAG = Client_Manager.class.getSimpleName();
    /**
     * Constant used in the location settings dialog.
     */
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    // Keys for storing activity state in the Bundle.
    //protected final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
    //protected final static String KEY_LOCATION = "location";
    //protected final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    protected LocationRequest mLocationRequest;

    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    protected LocationSettingsRequest mLocationSettingsRequest;

    /**
     * Represents a geographical location.
     */
    protected Location mCurrentLocation;


    /**
     * Tracks the status of the location updates request. Value changes when the user presses the
     * Start Updates and Stop Updates buttons.
     */
    protected Boolean mRequestingLocationUpdates;

    /**
     * Time when the location was updated represented as a String.
     */
    protected String mLastUpdateTime;


    int RQS_GooglePlayServices = 0;

    /*
     * Client N
     */
    ClientN clientN = new ClientN();
    Client client = new Client();

    int distance_gps = 20;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_manager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);

        if (ContextCompat.checkSelfPermission(Client_Manager.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(Client_Manager.this, "CAMERA PERMISISION GRANTED!",
            //Toast.LENGTH_SHORT).show();
            requestCameraPermission();

        }

        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit_client_image_switch = findViewById(R.id.edit_client_image_switch);
        charger_image_btn = findViewById(R.id.charger_image_btn);

        edit_qr_switch = findViewById(R.id.edit_qr_switch);
        scan_qr_iv = findViewById(R.id.scan_qr_iv);
        qr_code_et = findViewById(R.id.qr_code_et);

        edit_identity_switch = findViewById(R.id.edit_identity_switch);
        identity_ll = findViewById(R.id.identity_ll);
        client_nom = (EditText) findViewById(R.id.add_client_nom);
        client_prenom = (EditText) findViewById(R.id.add_client_prenom);
        telephone = (EditText) findViewById(R.id.aclient_phone);

        edit_adresse_switch = findViewById(R.id.edit_adresse_switch);
        adresse_ll = findViewById(R.id.adresse_ll);
        adresse_NR = (EditText) findViewById(R.id.ac_adresse_n);
        adresse_RUE = (EditText) findViewById(R.id.ac_adresse_r);
        adresse_Quartier = (EditText) findViewById(R.id.ac_adresse_q);

        edit_categorie_switch = findViewById(R.id.edit_categorie_switch);
        category_ll = findViewById(R.id.category_ll);
        spinnertournee = (Spinner) findViewById(R.id.actournee);
        spinnerCategorie = (Spinner) findViewById(R.id.accategorie);
        spinnerClasse = (Spinner) findViewById(R.id.acclasse);
        spinnerType = (Spinner) findViewById(R.id.actype);

        edit_gps_switch = findViewById(R.id.edit_gps_switch);
        gps_ll = findViewById(R.id.gps_ll);
        latitudeZT = (EditText) findViewById(R.id.ac_latitude);
        longitudeZT = (EditText) findViewById(R.id.ac_longitude);
        accuracyET = (EditText) findViewById(R.id.et_accuracy);
        accuracyTV = (TextView) findViewById(R.id.accuracy_tv);


        String client_CODE;
        String VENDEUR_CODE = "";
        String DISTRIBUTEUR_CODE = "";
        String CIRCUIT_CODE = "";

        clientN_Manager = new ClientNManager(getApplicationContext());
        client_Manager = new ClientManager(getApplicationContext());
        parametreManager = new ParametreManager(this);
        Parametre parametre = parametreManager.get("DISTANCEGPS");

        if (parametre.getVALEUR() == null || parametre.getVALEUR().length() == 0) {
            distance_gps = 20;
        } else {
            distance_gps = Integer.parseInt(parametre.getVALEUR());
        }


        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref", 0);
        if (pref1.getString("is_login", null).equals("ok")) {
            try {
                Gson gson2 = new Gson();
                String json2 = pref1.getString("User", "");
                java.lang.reflect.Type type = new TypeToken<JSONObject>() {
                }.getType();
                final JSONObject user = gson2.fromJson(json2, type);
                VENDEUR_CODE = user.getString("UTILISATEUR_CODE");
                DISTRIBUTEUR_CODE = user.getString("DISTRIBUTEUR_CODE");

                if (user.getString("PROFILE_CODE").equals("PF0002")) {
                    CIRCUIT_CODE = "CR0001";
                } else {
                    CIRCUIT_CODE = "CR0002";
                }
            } catch (Exception e) {
                Log.d(TAG, "onCreate: " + e.getMessage());
            }
        }
        Intent intent = getIntent();

        if (intent != null && intent.getStringExtra("CLIENT_CODE") != null && intent.getStringExtra("FROM") != null) {

            if (intent.getStringExtra("FROM").equals("CLIENT")) {

                isNew = false;
                client_CODE = intent.getStringExtra("CLIENT_CODE");

                client = client_Manager.get(client_CODE);

                clientN.setCLIENT_CODE(client.getCLIENT_CODE());
                clientN.setCLIENT_NOM(client.getCLIENT_NOM());
                clientN.setCLIENT_TELEPHONE1(client.getCLIENT_TELEPHONE1());
                clientN.setSTATUT_CODE(client.getSTATUT_CODE());
                clientN.setDISTRIBUTEUR_CODE(client.getDISTRIBUTEUR_CODE());
                clientN.setREGION_CODE(client.getREGION_CODE());
                clientN.setZONE_CODE(client.getZONE_CODE());
                clientN.setVILLE_CODE(client.getVILLE_CODE());
                clientN.setSECTEUR_CODE(client.getSECTEUR_CODE());
                clientN.setSOUSSECTEUR_CODE(client.getSOUSSECTEUR_CODE());
                clientN.setTOURNEE_CODE(client.getTOURNEE_CODE());
                clientN.setADRESSE_NR(client.getADRESSE_NR());
                clientN.setADRESSE_RUE(client.getADRESSE_RUE());
                clientN.setADRESSE_QUARTIER(client.getADRESSE_QUARTIER());
                clientN.setTYPE_CODE(client.getTYPE_CODE());
                clientN.setCATEGORIE_CODE(client.getCATEGORIE_CODE());
                clientN.setGROUPE_CODE(client.getGROUPE_CODE());
                clientN.setCLASSE_CODE(client.getCLASSE_CODE());
                clientN.setCIRCUIT_CODE(client.getCIRCUIT_CODE());
                clientN.setFAMILLE_CODE(client.getFAMILLE_CODE());
                clientN.setRANG(client.getRANG());
                clientN.setGPS_LATITUDE(client.getGPS_LATITUDE());
                clientN.setGPS_LONGITUDE(client.getGPS_LONGITUDE());
                clientN.setMODE_PAIEMENT(client.getMODE_PAIEMENT());
                clientN.setPOTENTIEL_TONNE(client.getPOTENTIEL_TONNE());
                clientN.setFREQUENCE_VISITE(client.getFREQUENCE_VISITE());
                clientN.setDATE_CREATION(client.getDATE_CREATION());
                clientN.setCREATEUR_CODE(VENDEUR_CODE);
                clientN.setINACTIF("0");
                clientN.setVERSION("To_Update");
                clientN.setIMAGE(client.getIMAGE());
                clientN.setLISTEPRIX_CODE(client.getLISTEPRIX_CODE());
                clientN.setQR_CODE(client.getQR_CODE());

            } else if (intent.getStringExtra("FROM").equals("CLIENTN")) {

                isNew = true;
                client_CODE = intent.getStringExtra("CLIENT_CODE");
                clientN = clientN_Manager.get(client_CODE);
                clientN.setCREATEUR_CODE(VENDEUR_CODE);
                clientN.setINACTIF("0");
                clientN.setVERSION("To_Insert");
                clientN.setIMAGE("");
                //clientN.setQR_CODE(0);


            } else {
                isNew = true;
                DateFormat df = new SimpleDateFormat("yyMMddHHmmss");
                client_CODE = VENDEUR_CODE + df.format(Calendar.getInstance().getTime());
                clientN.setCLIENT_CODE(client_CODE);
                clientN.setCLIENT_NOM("");
                clientN.setCLIENT_TELEPHONE1("");
                clientN.setSTATUT_CODE(1);
                clientN.setDISTRIBUTEUR_CODE(DISTRIBUTEUR_CODE);
                clientN.setREGION_CODE("RG0002");
                clientN.setZONE_CODE("Z0001");
                clientN.setVILLE_CODE("VL0058");
                clientN.setSECTEUR_CODE("SC0001");
                clientN.setSOUSSECTEUR_CODE("SSC0001");
                clientN.setTOURNEE_CODE("");
                clientN.setADRESSE_NR("");
                clientN.setADRESSE_RUE("");
                clientN.setADRESSE_QUARTIER("");
                clientN.setTYPE_CODE("");
                clientN.setCATEGORIE_CODE("");
                clientN.setGROUPE_CODE("GP0005");
                clientN.setCLASSE_CODE("");
                clientN.setCIRCUIT_CODE(CIRCUIT_CODE);
                clientN.setFAMILLE_CODE("FA0005");
                clientN.setRANG(0);
                clientN.setGPS_LATITUDE("");
                clientN.setGPS_LONGITUDE("");
                clientN.setMODE_PAIEMENT("ESPECE");
                clientN.setPOTENTIEL_TONNE("");
                clientN.setFREQUENCE_VISITE("");
                clientN.setCREATEUR_CODE(VENDEUR_CODE);
                clientN.setINACTIF("0");
                clientN.setVERSION("To_Insert");
                //clientN.setQR_CODE(0);
                clientN.setIMAGE("");
            }

        } else {
            isNew = true;
            DateFormat df = new SimpleDateFormat("yyMMddHHmmss");
            client_CODE = VENDEUR_CODE + df.format(Calendar.getInstance().getTime());
            clientN.setCLIENT_CODE(client_CODE);
            clientN.setCLIENT_NOM("");
            clientN.setCLIENT_TELEPHONE1("");
            clientN.setSTATUT_CODE(1);
            clientN.setDISTRIBUTEUR_CODE(DISTRIBUTEUR_CODE);
            clientN.setREGION_CODE("RG0002");
            clientN.setZONE_CODE("Z0001");
            clientN.setVILLE_CODE("VL0058");
            clientN.setSECTEUR_CODE("SC0001");
            clientN.setSOUSSECTEUR_CODE("SSC0001");
            clientN.setTOURNEE_CODE("");
            clientN.setADRESSE_NR("");
            clientN.setADRESSE_RUE("");
            clientN.setADRESSE_QUARTIER("");
            clientN.setTYPE_CODE("");
            clientN.setCATEGORIE_CODE("");
            clientN.setGROUPE_CODE("GP0005");
            clientN.setCLASSE_CODE("");
            clientN.setCIRCUIT_CODE(CIRCUIT_CODE);
            clientN.setFAMILLE_CODE("FA0005");
            clientN.setRANG(0);
            clientN.setGPS_LATITUDE("");
            clientN.setGPS_LONGITUDE("");
            clientN.setMODE_PAIEMENT("ESPECE");
            clientN.setPOTENTIEL_TONNE("");
            clientN.setFREQUENCE_VISITE("");
            clientN.setCREATEUR_CODE(VENDEUR_CODE);
            clientN.setINACTIF("0");
            clientN.setVERSION("To_Insert");
            //clientN.setQR_CODE(0);
            clientN.setIMAGE("");
        }

        /*  Client */


        SOURCE = intent.getStringExtra("SOURCE");
        //Toast.makeText(this,SOURCE,Toast.LENGTH_LONG).show();

        TourneeManager tourneeManager = new TourneeManager(getApplicationContext());
        Tournee client_tournee = tourneeManager.get(clientN.getTOURNEE_CODE());
        listTournee = tourneeManager.getList();

        if (client_tournee.getTOURNEE_CODE() == null) {

            client_tournee.setTOURNEE_NOM("TOURNEE");
            client_tournee.setTOURNEE_CODE("TOURNEE");
        }

        listTournee.add(0, client_tournee);


        CategorieManager categorieManager = new CategorieManager(getApplicationContext());
        Categorie categorie = categorieManager.get(clientN.getCATEGORIE_CODE());
        categories = categorieManager.getListByCateCode("CLIENT");

        if (categorie.getCATEGORIE_CODE() == null) {

            categorie.setCATEGORIE_NOM("CATEGORIE");
            categorie.setCATEGORIE_CODE("CATEGORIE");

        }

        categories.add(0, categorie);

        ClasseManager classeManager = new ClasseManager(getApplicationContext());
        Classe classe = classeManager.get(clientN.getCLASSE_CODE());
        classes = classeManager.getListByCateCode("CLIENT");

        Log.d(TAG, "onCreate: classeclasse" + classe);

        if (classe.getCLASSE_CODE() == null) {

            classe.setCLASSE_NOM("CLASSE");
            classe.setCLASSE_CODE("CLASSE");

        }

        classes.add(0, classe);

        TypeManager typeManager = new TypeManager(getApplicationContext());
        Type type = typeManager.get(clientN.getTYPE_CODE());
        types = typeManager.getListByCatCode("CLIENT");

        if (type.getTYPE_CODE() == null) {

            type.setTYPE_NOM("TYPE");
            type.setTYPE_CODE("TYPE");

        }

        types.add(0, type);


        //listTournee.add(client_tournee);

        tourneAdapter = new Tournee_Adapter(this, android.R.layout.simple_spinner_item, listTournee);
        spinnertournee.setAdapter(tourneAdapter);

        spinner_categorie_adapter = new Spinner_Categorie_Adapter(this, android.R.layout.simple_spinner_item, categories);
        spinnerCategorie.setAdapter(spinner_categorie_adapter);

        spinner_classe_adapter = new Spinner_Classe_Adapter(this, android.R.layout.simple_spinner_item, classes);
        spinnerClasse.setAdapter(spinner_classe_adapter);

        spinner_type_adapter = new Spinner_Type_Adapter(this, android.R.layout.simple_spinner_item, types);
        spinnerType.setAdapter(spinner_type_adapter);

        imageView1 = (ImageView) findViewById(R.id.ac_imageview);
        if (isDeviceSupportCamera()) {
            charger_image_btn.setOnClickListener(v -> captureImage());
        }


        if (clientN.getIMAGE() != null) {
            byte[] decodedString = Base64.decode(clientN.getIMAGE(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView1.setVisibility(View.VISIBLE);
            imageView1.setImageBitmap(decodedByte);
        }

        qr_code_et.setText(String.valueOf(clientN.getQR_CODE()));

        if (clientN.getCLIENT_NOM() != "") {
            ArrayList<String> result = splitString(clientN.getCLIENT_NOM());
            int longueur = result.size();
            String chaine = "";

            for (int i = 0; i < result.size(); i++) {

                if (i == 0) {
                    client_nom.setText(result.get(i));
                } else {
                    chaine += result.get(i) + " ";
                }
            }

            client_prenom.setText(chaine);
        } else {
            client_nom.setText(clientN.getCLIENT_NOM());
        }

        telephone.setText(clientN.getCLIENT_TELEPHONE1());
        adresse_NR.setText(clientN.getADRESSE_NR());
        adresse_RUE.setText(clientN.getADRESSE_RUE());
        adresse_Quartier.setText(clientN.getADRESSE_QUARTIER());
        latitudeZT.setText(clientN.getGPS_LATITUDE());
        longitudeZT.setText(clientN.getGPS_LONGITUDE());

        /*      VERY IMPORTANT **********
        latitudeZT.setEnabled(false);
        longitudeZT.setEnabled(false);
        accuracyET.setEnabled(false);*/

        enableDisableElements(isNew);

        scan_qr_iv.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                IntentIntegrator integrator = new IntentIntegrator(this);

                integrator.setPrompt("Approchez le téléphone du QR code");

                integrator.setOrientationLocked(false);

                integrator.initiateScan();

            } else {
                requestCameraPermission();
            }

        });

        qr_code_et.setEnabled(false);

        edit_client_image_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    charger_image_btn.setEnabled(true);
                } else {
                    charger_image_btn.setEnabled(false);
                }
            }
        });

        edit_identity_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    client_nom.setEnabled(true);
                    client_prenom.setEnabled(true);
                    telephone.setEnabled(true);
                } else {
                    client_nom.setEnabled(false);
                    client_prenom.setEnabled(false);
                    telephone.setEnabled(false);
                }
            }
        });

        edit_adresse_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    adresse_NR.setEnabled(true);
                    adresse_RUE.setEnabled(true);
                    adresse_Quartier.setEnabled(true);
                } else {
                    adresse_NR.setEnabled(false);
                    adresse_RUE.setEnabled(false);
                    adresse_Quartier.setEnabled(false);
                }
            }
        });

        edit_categorie_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spinnertournee.setEnabled(true);
                    spinnerClasse.setEnabled(true);
                    spinnerType.setEnabled(true);
                    spinnerCategorie.setEnabled(true);
                } else {
                    spinnertournee.setEnabled(false);
                    spinnerClasse.setEnabled(false);
                    spinnerType.setEnabled(false);
                    spinnerCategorie.setEnabled(false);
                }
            }
        });

        edit_gps_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    latitudeZT.setEnabled(true);
                    longitudeZT.setEnabled(true);
                } else {
                    latitudeZT.setEnabled(false);
                    longitudeZT.setEnabled(false);
                }
            }
        });


        /* GPS    */

        mRequestingLocationUpdates = false;
        mLastUpdateTime = "";

        // Kick off the process of building the GoogleApiClient, LocationRequest, and
        // LocationSettingsRequest objects.

        //step 1
        buildGoogleApiClient();

        //step 2
        createLocationRequest();

        //step 3
        buildLocationSettingsRequest();

        //step4
        checkLocationSettings();


        setTitle("CLIENT");

    }

    /**
     * Receiving activity result method will be called after closing the camera
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            // if the result is capturing Image
            case CAMERA_CAPTURE_IMAGE_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // successfully captured the image
                        // display it in image view
                        Log.d(TAG, "onActivityResult: " + "RESULT OK");
                        previewCapturedImage();
                        break;
                    case Activity.RESULT_CANCELED:
                        // user cancelled Image capture
                        Toast.makeText(getApplicationContext(),
                                "User cancelled image capture", Toast.LENGTH_SHORT)
                                .show();
                        break;
                    default:
                        // failed to capture image
                        Toast.makeText(getApplicationContext(),
                                "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                                .show();
                        break;
                }
                break;
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAG, "User agreed to make required location settings changes.");
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG, "User chose not to make required location settings changes.");
                        break;
                }
                break;

            case IntentIntegrator.REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // successfully captured the image
                        // display it in image view

                        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                        if (scanResult != null) {
                            // handle scan result
                            Log.d(TAG, "onActivityResult: " + scanResult.toString());
                            Toast.makeText(getApplicationContext(),
                                    scanResult.getContents(), Toast.LENGTH_SHORT)
                                    .show();
                            int qr_code;

                            try {
                                qr_code = Integer.parseInt(scanResult.getContents());
                                qr_code_et.setText(String.valueOf(qr_code));
                            } catch (NumberFormatException ex) {
                                Toast.makeText(getApplicationContext(),
                                        "QR CODE DOIT ETRE ENTIER !", Toast.LENGTH_SHORT)
                                        .show();
                            }

                        }

                        /*Toast.makeText(getApplicationContext(),
                                data.getStringExtra("result"), Toast.LENGTH_SHORT)
                                .show();*/
                        break;
                    case Activity.RESULT_CANCELED:
                        // user cancelled Image capture
                        Toast.makeText(getApplicationContext(),
                                "Vous avez annuler le SCAN", Toast.LENGTH_SHORT)
                                .show();
                        break;

                    default:
                        // failed to capture image
                        Toast.makeText(getApplicationContext(),
                                "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                                .show();
                        break;
                }
                break;
        }
    }


    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);

    }

    /*
     * Here we restore the fileUri again
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    /* ########################################    GPS ######################################################*/


    @Override
    protected void onStart() {
        super.onStart();

        if (!mGoogleApiClient.isConnected()) {
            //step 1
            buildGoogleApiClient();

            //step 2
            createLocationRequest();

            //step 3
            buildLocationSettingsRequest();

            //step4
            checkLocationSettings();
        }

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (resultCode == ConnectionResult.SUCCESS) {

//            Toast.makeText(getApplicationContext(),
//                    "isGooglePlayServicesAvailable SUCCESS",Toast.LENGTH_LONG).show();

            mGoogleApiClient.connect();
        } else {
            GooglePlayServicesUtil.getErrorDialog(resultCode, this, RQS_GooglePlayServices);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        // Within {@code onPause()}, we pause location updates, but leave the
        // connection to GoogleApiClient intact.  Here, we resume receiving
        // location updates if the user has requested them.

        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            //  Toast.makeText(FusedLocationWithSettingsDialog.this, "location was already on so detecting location now", Toast.LENGTH_SHORT).show();
            startLocationUpdates();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //step 1
    protected synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    //step 2
    protected void createLocationRequest() {

        mLocationRequest = LocationRequest.create()
                .setInterval(UPDATE_INTERVAL_IN_MILLISECONDS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        /*mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);*/
    }

    //step 3
    protected void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.setAlwaysShow(true);
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    //step 4

    protected void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(this);
    }

    /**
     * Requests location updates from the FusedLocationApi.
     */
    protected void startLocationUpdates() {

        if (ContextCompat.checkSelfPermission(Client_Manager.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient,
                    mLocationRequest,
                    this

            ).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    mRequestingLocationUpdates = true;
                    //     setButtonsEnabledState();
                }
            });

        }


    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient,
                this
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                mRequestingLocationUpdates = false;
                //   setButtonsEnabledState();
            }
        });
    }

    @Override
    public void onConnected(Bundle bundle) {

        Log.i(TAG, "Connected to GoogleApiClient");

        // If the initial location was never previously requested, we use
        // FusedLocationApi.getLastLocation() to get it. If it was previously requested, we store
        // its value in the Bundle and check for it in onCreate(). We
        // do not request it again unless the user specifically requests location updates by pressing
        // the Start Updates button.
        //
        // Because we cache the value of the initial location in the Bundle, it means that if the
        // user launches the activity,
        // moves to a new location, and then changes the device orientation, the original location
        // is displayed as the activity is re-created.
        if (mCurrentLocation == null) {
            if (ContextCompat.checkSelfPermission(Client_Manager.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(Client_Manager.this, "ACCESS FINE LOCATION PERMISISION GRANTED!",
                //Toast.LENGTH_SHORT).show();

                mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            }

            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            updateLocationUI();
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "Connection suspended");
    }

    @Override
    public void onLocationChanged(Location location) {

        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateLocationUI();
        //Toast.makeText(this, "location_updated_message)",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    /**
     * Invoked when settings dialog is opened and action taken
     *
     * @param locationSettingsResult This below OnResult will be used by settings dialog actions.
     */

    //step 5
    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {

        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                Log.i(TAG, "All location settings are satisfied.");

                Toast.makeText(Client_Manager.this, "Location is already on.", Toast.LENGTH_SHORT).show();
                startLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to" +
                        "upgrade location settings ");

                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
                    Toast.makeText(Client_Manager.this, "Location dialog will be open", Toast.LENGTH_SHORT).show();
                    //

                    //move to step 6 in onActivityResult to check what action user has taken on settings dialog
                    status.startResolutionForResult(Client_Manager.this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    Log.i(TAG, "PendingIntent unable to execute request.");
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog " +
                        "not created.");
                break;
        }
    }

    //step 6:


    /**
     * Sets the value of the UI fields for the location latitude, longitude and last update time.
     */
    private void updateLocationUI() {
        if (mCurrentLocation != null) {

            latitudeZT.setText(String.format("%f", mCurrentLocation.getLatitude()));
            longitudeZT.setText(String.format("%f", mCurrentLocation.getLongitude()));

            accuracy = mCurrentLocation.getAccuracy();


            accuracyTV.setText(String.valueOf(accuracy));

            if (accuracy <= distance_gps) {
                accuracyTV.setBackgroundColor(ContextCompat.getColor(this, R.color.good));
            } else {
                accuracyTV.setBackgroundColor(ContextCompat.getColor(this, R.color.bad));
            }
            // mLastUpdateTimeTextView.setText(String.format("%s: %s", mLastUpdateTimeLabel,mLastUpdateTime));
            //Toast.makeText(this,"Location has been changed",Toast.LENGTH_SHORT).show();
            //updateCityAndPincode(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude());
        }
    }

    /**
     * This updateCityAndPincode method uses Geocoder api to map the latitude and longitude into city location or pincode.
     * We can retrieve many details using this Geocoder class.
     * <p>
     * And yes the Geocoder will not work unless you have data connection or wifi connected to internet.
     */


    /*
     *Enregistrement.
     */
    private void enregistrer_clientn() {
        if (checkValidation()) {
            String encodeedImage = "";

            if (isNew || edit_client_image_switch.isChecked()) {

                if (fileUri == null) {

                    Bitmap bitmapDrawable = ((BitmapDrawable) imageView1.getDrawable()).getBitmap();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmapDrawable.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                    byte[] imageBytes = baos.toByteArray();
                    encodeedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);


                } else {
                    encodeedImage = getEncodedImage(fileUri);
                }

            }


            //Log.d(TAG, "enregistrer_clientn: "+encodeedImage);
            Tournee tournee = (Tournee) tourneAdapter.getItem(spinnertournee.getSelectedItemPosition());
            Categorie categorie = (Categorie) spinner_categorie_adapter.getItem(spinnerCategorie.getSelectedItemPosition());
            Type type = (Type) spinner_type_adapter.getItem(spinnerType.getSelectedItemPosition());
            Classe classe = (Classe) spinner_classe_adapter.getItem(spinnerClasse.getSelectedItemPosition());

            clientN.setCLIENT_NOM(client_nom.getText().toString() + " " + client_prenom.getText().toString());
            clientN.setCATEGORIE_CODE(categorie.getCATEGORIE_NOM());
            clientN.setCLIENT_TELEPHONE1(telephone.getText().toString());
            clientN.setADRESSE_NR(adresse_NR.getText().toString());
            clientN.setADRESSE_RUE(adresse_RUE.getText().toString());
            clientN.setADRESSE_QUARTIER(adresse_Quartier.getText().toString());
            clientN.setTOURNEE_CODE(tournee.getTOURNEE_CODE());
            clientN.setTYPE_CODE(type.getTYPE_CODE());
            clientN.setCATEGORIE_CODE(categorie.getCATEGORIE_CODE());
            clientN.setCLASSE_CODE(classe.getCLASSE_CODE());
            clientN.setGPS_LATITUDE(latitudeZT.getText().toString());
            clientN.setGPS_LONGITUDE(longitudeZT.getText().toString());
            clientN.setIMAGE(encodeedImage);

            if (edit_qr_switch.isChecked()) {
                clientN.setQR_CODE(Integer.parseInt(String.valueOf(qr_code_et.getText())));
            }

            clientN_Manager.add(clientN);
            ClientNManager.synchronisationClientN(getApplicationContext());

            //Intent i = new Intent(this, CatalogueClientNActivity.class);
            //startActivity(i);
            //finish();

            this.onBackPressed();
        }
    }


    private boolean checkValidation() {
        boolean result = true;


        if (edit_qr_switch.isChecked()) {

            String strQr_code = qr_code_et.getText().toString();
            int intQr_code = Integer.parseInt(qr_code_et.getText().toString());

            if (TextUtils.isEmpty(strQr_code) || intQr_code == 0) {
                qr_code_et.setError("ce champs est obligatoire");
                result = false;
            }
        }
        String strClient_Nom = client_nom.getText().toString();
        if (TextUtils.isEmpty(strClient_Nom)) {
            client_nom.setError("ce champs est obligatoire");
            result = false;
        }
        String strClient_prenom = client_prenom.getText().toString();
        if (TextUtils.isEmpty(strClient_prenom)) {
            client_prenom.setError("ce champs est obligatoire");
            result = false;
        }
        String strtelephone = telephone.getText().toString();
        if (TextUtils.isEmpty(strtelephone)) {
            telephone.setError("ce champs est obligatoire");
            result = false;
        }

        String stradresse_NR = adresse_NR.getText().toString();
        if (TextUtils.isEmpty(stradresse_NR)) {
            adresse_NR.setError("ce champs est obligatoire");
            result = false;
        }
        String stradresse_RUE = adresse_RUE.getText().toString();
        if (TextUtils.isEmpty(stradresse_RUE)) {
            adresse_RUE.setError("ce champs est obligatoire");
            result = false;
        }
        String stradresse_Quartier = adresse_Quartier.getText().toString();
        if (TextUtils.isEmpty(stradresse_Quartier)) {
            adresse_Quartier.setError("ce champs est obligatoire");
            result = false;
        }

        Tournee tournee = (Tournee) tourneAdapter.getItem(spinnertournee.getSelectedItemPosition());
        String strtournee = tournee.getTOURNEE_CODE();
        if (TextUtils.isEmpty(strtournee) || TextUtils.equals(strtournee, "TOURNEE")) {
            ((TextView) spinnertournee.getChildAt(0)).setError("ce champs est obligatoire");
            result = false;
        }

        Categorie categorie = (Categorie) spinner_categorie_adapter.getItem(spinnerCategorie.getSelectedItemPosition());
        String strcategorie = categorie.getCATEGORIE_CODE();
        if (TextUtils.isEmpty(strcategorie) || TextUtils.equals(strcategorie, "CATEGORIE")) {
            ((TextView) spinnerCategorie.getChildAt(0)).setError("ce champs est obligatoire");
            result = false;
        }

        Classe classe = (Classe) spinner_classe_adapter.getItem(spinnerClasse.getSelectedItemPosition());
        String strclasse = classe.getCLASSE_CODE();
        if (TextUtils.isEmpty(strclasse) || TextUtils.equals(strclasse, "CLASSE")) {
            ((TextView) spinnerClasse.getChildAt(0)).setError("ce champs est obligatoire");
            result = false;
        }

        Type type = (Type) spinner_type_adapter.getItem(spinnerType.getSelectedItemPosition());
        String strtype = type.getTYPE_CODE();
        if (TextUtils.isEmpty(strtype) || TextUtils.equals(strtype, "TYPE")) {
            ((TextView) spinnerType.getChildAt(0)).setError("ce champs est obligatoire");
            result = false;
        }

        String strlatitudeZT = latitudeZT.getText().toString();
        if (TextUtils.isEmpty(strlatitudeZT)) {
            latitudeZT.setError("ce champs est obligatoire");
            result = false;
        }

        String strlongitudeZT = longitudeZT.getText().toString();
        if (TextUtils.isEmpty(strlongitudeZT)) {
            longitudeZT.setError("ce champs est obligatoire");
            result = false;
        }


        if (isNew || edit_client_image_switch.isChecked()) {

            if (clientN.getIMAGE() == null) {
                if (fileUri == null) {
                    charger_image_btn.setError("l'image est obligatire");
                    result = false;
                }
            }

            Bitmap bitmapDrawable = ((BitmapDrawable) imageView1.getDrawable()).getBitmap();
            if (bitmapDrawable == null) {
                charger_image_btn.setError("l'image est obligatire");
                result = false;
            }


        }


        if (accuracy > distance_gps) {
            result = false;
            Toast.makeText(this, "MERCI DE GARDER UNE DISTANCE MOINS QUE " + distance_gps + " METRES", Toast.LENGTH_LONG).show();
        }

        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_clientmanager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;


            case R.id.enr_client:
                //Intent intt = new Intent(this, PrintActivity2.class);
                //intt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //intt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(intt);
                //finish();
                enregistrer_clientn();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<String> splitString(String chaine) {

        ArrayList<String> result = new ArrayList<>();
        String[] source;

        String first_chaine = "";
        String second_chaine = "";

        source = chaine.split(" ");
        first_chaine = source[0];

        for (int i = 1; i < source.length; i++) {

            second_chaine += source[i] + " ";

        }

        result.add(first_chaine);
        result.add(second_chaine);

        return result;
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {

            new AlertDialog.Builder(this)
                    .setTitle("PERMISSION NEEDED")
                    .setMessage("TO TAKE PICTURES OF CLIENTS")
                    .setPositiveButton("ok", (dialog, which) -> ActivityCompat.requestPermissions(Client_Manager.this,
                            new String[]{Manifest.permission.CAMERA}, CAMERA_CODE))
                    .setNegativeButton("cancel", (dialog, which) -> {
                        stopService(new Intent(Client_Manager.this, BlutoothConnctionService.class));
                        stopService(new Intent(Client_Manager.this, BlutDiscovery.class));
                        Intent intent = new Intent(Client_Manager.this, AlarmReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(Client_Manager.this, 0,
                                intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        try {
                            pendingIntent.send();
                        } catch (PendingIntent.CanceledException e) {
                            e.printStackTrace();
                        }
                        finish();
                        System.exit(0);
                        dialog.dismiss();
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, CAMERA_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "CAMERA GRANTED", Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(Client_Manager.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    stopService(new Intent(Client_Manager.this, BlutoothConnctionService.class));
                    stopService(new Intent(Client_Manager.this, BlutDiscovery.class));
                    Intent intent = new Intent(Client_Manager.this, AlarmReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(Client_Manager.this, 0,
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

                Toast.makeText(this, "CAMERA DENIED", Toast.LENGTH_SHORT).show();
                stopService(new Intent(Client_Manager.this, BlutoothConnctionService.class));
                stopService(new Intent(Client_Manager.this, BlutDiscovery.class));
                Intent intent = new Intent(Client_Manager.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(Client_Manager.this, 0,
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
    public void onBackPressed() {
        super.onBackPressed();
        /*Toast.makeText(this,SOURCE,
                Toast.LENGTH_LONG).show();
        if(SOURCE=="ClientActivity"){
            Intent i = new Intent(Client_Manager.this, ClientActivity.class);
            startActivity(i);
            finish();
        }*/

        if (SOURCE != null) {

            Class<?> className = null;
            try {
                className = Class.forName(getApplicationContext().getPackageName() + ".Activity." + SOURCE);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(Client_Manager.this, className);
            startActivity(intent);
            finish();

        } else {
            Intent i = new Intent(this, CatalogueClientNActivity.class);
            startActivity(i);
            finish();
        }


    }

    public String getEncodedImage(Uri fileUri) {

        String encodeedImage;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //Log.d(TAG, "getEncodedImage: "+bitmapDrawable.getByteCount());
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inSampleSize = 8;
        Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                options);

        try {
            ExifInterface exif = new ExifInterface(fileUri.getPath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true); // rotating bitmap
        } catch (Exception e) {
            Log.d(TAG, "getEncodedImage: " + e.getMessage());
        }

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] byteArray = byteArrayOutputStream.toByteArray();
        encodeedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);


        return encodeedImage;
    }

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /*
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        Log.d(TAG, "getOutputMediaFile: path " + mediaStorageDir);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    /*
     * Display image from a path to ImageView
     */
    private void previewCapturedImage() {
        try {
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;
            Log.d(TAG, "previewCapturedImage: " + fileUri.toString());
            Log.d(TAG, "previewCapturedImage: path " + fileUri.getPath());
            Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            try {
                ExifInterface exif = new ExifInterface(fileUri.getPath());
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                Log.d("EXIF", "Exif: " + orientation);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                } else if (orientation == 3) {
                    matrix.postRotate(180);
                } else if (orientation == 8) {
                    matrix.postRotate(270);
                }
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true); // rotating bitmap
            } catch (Exception e) {
                Log.d(TAG, "previewCapturedImage: " + e.getMessage());
            }
            imageView1.setVisibility(View.VISIBLE);
            imageView1.setImageBitmap(bitmap);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checking device has camera hardware or not
     */
    private boolean isDeviceSupportCamera() {
        // this device has a camera
        // no camera on this device
        return getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }


    /*
     * Capturing Camera Image will lauch camera app requrest image capture
     */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        /*fileUri = FileProvider.getUriForFile(Client_Manager.this,
                BuildConfig.APPLICATION_ID + ".provider",
                getOutputMediaFile(MEDIA_TYPE_IMAGE));*/

        fileUri = FileProvider.getUriForFile(Client_Manager.this,
                getPackageName() + ".provider",
                getOutputMediaFile(MEDIA_TYPE_IMAGE));

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        Log.d(TAG, "captureImage: fileuuri " + fileUri.getPath());
        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    private void enableDisableElements(boolean enable) {

        charger_image_btn.setEnabled(enable);
        client_nom.setEnabled(enable);
        client_prenom.setEnabled(enable);
        telephone.setEnabled(enable);
        adresse_NR.setEnabled(enable);
        adresse_RUE.setEnabled(enable);
        adresse_Quartier.setEnabled(enable);
        spinnertournee.setEnabled(enable);
        spinnerClasse.setEnabled(enable);
        spinnerType.setEnabled(enable);
        spinnerCategorie.setEnabled(enable);
        latitudeZT.setEnabled(false);
        longitudeZT.setEnabled(false);

        if (enable) {

            edit_client_image_switch.setVisibility(View.GONE);
            edit_identity_switch.setVisibility(View.GONE);
            edit_adresse_switch.setVisibility(View.GONE);
            edit_categorie_switch.setVisibility(View.GONE);

        } else {

            edit_client_image_switch.setVisibility(View.VISIBLE);
            edit_identity_switch.setVisibility(View.VISIBLE);
            edit_adresse_switch.setVisibility(View.VISIBLE);
            edit_categorie_switch.setVisibility(View.VISIBLE);
        }
    }

}
