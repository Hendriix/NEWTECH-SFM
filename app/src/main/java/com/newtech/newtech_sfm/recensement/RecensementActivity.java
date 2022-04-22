package com.newtech.newtech_sfm.recensement;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.newtech.newtech_sfm.Activity.MenuActivity;
import com.newtech.newtech_sfm.Activity.ScanActivity;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.Parametre;
import com.newtech.newtech_sfm.Metier_Manager.ParametreManager;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.RecensementClient.RecensementClientActivity;
import com.newtech.newtech_sfm.Service.AlarmReceiver;
import com.newtech.newtech_sfm.Service.BlutDiscovery;
import com.newtech.newtech_sfm.Service.BlutoothConnctionService;
import com.newtech.newtech_sfm.TableauBordClient.TableauBordClientActivity;
import com.newtech.newtech_sfm.dialog.DialogRecensement;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class RecensementActivity extends AppCompatActivity implements RecensementPresenter.RecensementView,
        DialogRecensement.DialogRecensementView,
        RecensementAdapter.RecensementItemClickListener,
        SearchView.OnQueryTextListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ResultCallback<LocationSettingsResult>{

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 3;
    private static final int ZXING_CAMERA_PERMISSION = 1;
    private static final int CAMERA_CODE = 4;
    private static final String TAG = RecensementActivity.class.getName();
    private RecensementPresenter presenter;
    private ProgressDialog progressDialog;
    SearchView mSearchView;

    RecyclerView recyclerView;
    TextView latitudeTv, longitudeTv, clientEmptyTv;
    ImageView sync_proche_btn, sync_qr_btn;

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    protected LocationSettingsRequest mLocationSettingsRequest;
    protected Location mCurrentLocation;
    protected Boolean mRequestingLocationUpdates;
    protected String mLastUpdateTime;

    int RQS_GooglePlayServices=0;

    ParametreManager parametreManager;
    int distance_gps = 20;

    private static Double latitude = 0.0;
    private static Double longitude = 0.0;
    private static float accuracy;
    private TextView vaTextView2;
    private ArrayList<Client> clientArrayList = new ArrayList<>();
    DialogRecensement dialogRecensement;
    SearchManager searchmanager;
    RecensementAdapter recensementAdapter;
    Dialog myDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_catalogue_recensement);

        mSearchView= findViewById(R.id.search_client);
        recyclerView = findViewById(R.id.client_recensement_rv);
        latitudeTv = findViewById(R.id.latitude_tv);
        longitudeTv = findViewById(R.id.longitude_tv);
        clientEmptyTv = findViewById(R.id.client_empty_tv);
        sync_proche_btn = findViewById(R.id.sync_proche_btn);
        sync_qr_btn = findViewById(R.id.sync_qr_btn);
        vaTextView2=(TextView) findViewById(R.id.va_textView2);

        dialogRecensement = new DialogRecensement(RecensementActivity.this,this, 100);
        myDialog = new Dialog(this);

        setupSearchView();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        /*recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_client);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchmanager = (SearchManager) getSystemService(getApplicationContext().SEARCH_SERVICE);
        mSearchView=(SearchView) findViewById(R.id.search_client);

        parametreManager = new ParametreManager(this);
        Parametre parametre = parametreManager.get("GPSCHECK");

        if(parametre.getVALEUR()== null || parametre.getVALEUR().length() == 0){
            distance_gps =20;
        }else{
            distance_gps =Integer.parseInt(parametre.getVALEUR());
        }

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


        checkLocationSettings();

        latitudeTv.setText(String.valueOf(latitude));
        longitudeTv.setText(String.valueOf(longitude));

        initProgressDialog();

        presenter = new RecensementPresenter(this);

        sync_proche_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientEmptyTv.setVisibility(View.GONE);
                dialogRecensement.show();
                //presenter.synchronisationClient(getApplicationContext(),latitude,longitude);
            }
        });

        /*sync_qr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(RecensementActivity.this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent(RecensementActivity.this, ScanActivity.class);
                    startActivityForResult(intent, ZXING_CAMERA_PERMISSION);

                } else {
                    requestCameraPermission();
                }

            }
        });*/

        sync_qr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(RecensementActivity.this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                    IntentIntegrator integrator = new IntentIntegrator(RecensementActivity.this);

                    integrator.setPrompt("Approchez le téléphone du QR code");

                    integrator.setOrientationLocked(false);

                    integrator.initiateScan();

                } else {
                    requestCameraPermission();
                }

            }
        });

        clientEmptyTv.setVisibility(View.GONE);


        presenter.synchronisationClient(this,latitude,longitude,50);

        setTitle("VALIDATION RECENSEMENT");

    }


    @Override
    public void showSuccess(ArrayList<Client> clients) {
        clientArrayList.clear();
        progressDialog.dismiss();
        clientArrayList = clients;
        recensementAdapter = new RecensementAdapter(clients,this,this);
        recyclerView.setAdapter(recensementAdapter);
        recensementAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        clientArrayList.clear();
        progressDialog.dismiss();
        recensementAdapter = new RecensementAdapter(clientArrayList,this,this);
        recyclerView.setAdapter(recensementAdapter);
        recensementAdapter.notifyDataSetChanged();
        showMessage(message);
    }

    @Override
    public void showEmpty(String message) {
        clientArrayList.clear();
        progressDialog.dismiss();
        clientEmptyTv.setVisibility(View.VISIBLE);
        recensementAdapter = new RecensementAdapter(clientArrayList,this,this);
        recyclerView.setAdapter(recensementAdapter);
        recensementAdapter.notifyDataSetChanged();
        showMessage(message);
    }


    private void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    private void initProgressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Chargement en cours");
    }



    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        //mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("SEARCH HERE");

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String textfilter=mSearchView.getQuery().toString().toLowerCase(Locale.getDefault());
        recensementAdapter.getFilter().filter(textfilter);
        //Toast.makeText(this,textfilter, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(RecensementActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
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
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
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

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(Client_Manager.this, "ACCESS FINE LOCATION PERMISISION GRANTED!",
            //Toast.LENGTH_SHORT).show();

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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

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
            if (ContextCompat.checkSelfPermission(this,
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
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                Log.i(TAG, "All location settings are satisfied.");

                Toast.makeText(this, "Location is already on.", Toast.LENGTH_SHORT).show();
                startLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to" +
                        "upgrade location settings ");

                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
                    Toast.makeText(this, "Location dialog will be open", Toast.LENGTH_SHORT).show();
                    //

                    //move to step 6 in onActivityResult to check what action user has taken on settings dialog
                    status.startResolutionForResult(this, REQUEST_CHECK_SETTINGS);
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

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateLocationUI();
        //Toast.makeText(this, "location_updated_message)",Toast.LENGTH_SHORT).show();
    }

    /**
     * Sets the value of the UI fields for the location latitude, longitude and last update time.
     */
    private void updateLocationUI() {
        if (mCurrentLocation != null) {

            //latitudeZT.setText(String.format("%f", mCurrentLocation.getLatitude()));
            //longitudeZT.setText(String.format("%f",mCurrentLocation.getLongitude()));
            //accuracyET.setText(String.format("%f",mCurrentLocation.getAccuracy()));
            latitude = mCurrentLocation.getLatitude();
            longitude = mCurrentLocation.getLongitude();
            accuracy = mCurrentLocation.getAccuracy();

            latitudeTv.setText(String.valueOf(latitude));
            longitudeTv.setText(String.valueOf(longitude));

            vaTextView2.setText(String.valueOf(accuracy));

            if(accuracy <= distance_gps){
                vaTextView2.setBackgroundColor(ContextCompat.getColor(this,R.color.good));
            }else{
                vaTextView2.setBackgroundColor(ContextCompat.getColor(this,R.color.bad));
            }

            // mLastUpdateTimeTextView.setText(String.format("%s: %s", mLastUpdateTimeLabel,mLastUpdateTime));
            //Toast.makeText(this,"Location has been changed",Toast.LENGTH_SHORT).show();
            //updateCityAndPincode(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        progressDialog.dismiss();
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

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
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");

        if(!mGoogleApiClient.isConnected()){
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
        if (resultCode == ConnectionResult.SUCCESS){

//            Toast.makeText(getApplicationContext(),
//                    "isGooglePlayServicesAvailable SUCCESS",Toast.LENGTH_LONG).show();

            mGoogleApiClient.connect();
        }else{
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
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onRecensementItemClick(int position) {
        Client client = clientArrayList.get(position);
        Toast.makeText(this, client.getCLIENT_NOM(), Toast.LENGTH_SHORT).show();

        ShowPopup(client);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_client, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                this.onBackPressed();
                return true;

            case R.id.add_client:
                Toast.makeText(this,"PLUS PLUS",Toast.LENGTH_SHORT).show();
                //ViewChargementActivity.commandeSource=null;
                //ViewChargementActivity.stockDemande=null;
                //ViewChargementActivity.stockDemandeLignes.clear();

                // ViewChargementActivity.commandeSource= ChargementActivity.commandeSource;
                Intent intent = new Intent(this, RecensementClientActivity.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {

            new AlertDialog.Builder(this)
                    .setTitle("PERMISSION NEEDED")
                    .setMessage("TO TAKE PICTURES OF CLIENTS")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(RecensementActivity.this,
                                    new String[] {Manifest.permission.CAMERA}, CAMERA_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            stopService(new Intent(RecensementActivity.this, BlutoothConnctionService.class));
                            stopService(new Intent(RecensementActivity.this, BlutDiscovery.class));
                            Intent intent = new Intent(RecensementActivity.this, AlarmReceiver.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(RecensementActivity.this, 0,
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
                    new String[] {Manifest.permission.CAMERA}, CAMERA_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            // if the result is capturing Image
            case CAMERA_CODE :
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // successfully captured the image
                        // display it in image view
                        Log.d(TAG, "onActivityResult: "+ "RESULT OK");
                        Intent intent = new Intent(RecensementActivity.this, ScanActivity.class);
                        startActivity(intent);
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

            case ZXING_CAMERA_PERMISSION :
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // successfully captured the image
                        // display it in image view
                        //String client_code = data.getStringExtra("result");

                        String client_code = data.getStringExtra("result");
                        int qr_code;
                        try{
                            qr_code = Integer.parseInt(client_code);
                            //visiterClientQrVersion(qr_code);
                            presenter.synchronisationClient(RecensementActivity.this,qr_code);
                        }
                        catch (NumberFormatException ex){
                            Toast.makeText(getApplicationContext(),
                                    "QR CODE DOIT ETRE ENTIER !", Toast.LENGTH_SHORT)
                                    .show();
                        }

                        Log.d(TAG, "onActivityResult: "+ "RESULT OK");
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

            default:

                IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

                if(result != null) {

                    if(result.getContents() == null) {

                        //cancel
                        Toast.makeText(this,"Vous avez annulé le SCAN",Toast.LENGTH_SHORT).show();


                    } else {

                        //Scanned successfully

                        //String client_code = result.getContents();
                        //Toast.makeText(this,"SUCCEFULL "+client_code,Toast.LENGTH_SHORT).show();


                        String client_code = result.getContents();
                        //Toast.makeText(this,"SUCCEFULL "+client_code,Toast.LENGTH_SHORT).show();
                        int qr_code;
                        try{
                            qr_code = Integer.parseInt(client_code);
                            presenter.synchronisationClient(RecensementActivity.this,qr_code);
                        }
                        catch (NumberFormatException ex){
                            Toast.makeText(getApplicationContext(),
                                    "QR CODE DOIT ETRE ENTIER !", Toast.LENGTH_SHORT)
                                    .show();
                        }


                    }

                } else {

                    super.onActivityResult(requestCode, resultCode, data);
                    Toast.makeText(getApplicationContext(),
                            requestCode+" n'est pas trouvé ", Toast.LENGTH_SHORT)
                            .show();

                }

                break;
        }

    }

    @Override
    public void validerDistance(int distance) {
        presenter.synchronisationClient(this,latitude,longitude,distance);
        dialogRecensement.dismiss();

    }

    @Override
    public void annuler() {
        dialogRecensement.dismiss();
    }

    public void ShowPopup(Client client) {

        myDialog.setContentView(R.layout.client_file_pop_up);

        TextView tvclose;
        TextView tvClientNom;
        TextView tvClientAdresse;
        TextView tvVisite;

        LinearLayout appeler_ll;
        LinearLayout trajet_ll;
        LinearLayout visiter_ll;

        tvclose =(TextView) myDialog.findViewById(R.id.close_tv);
        tvClientNom =(TextView) myDialog.findViewById(R.id.client_nom_tv);
        tvClientAdresse =(TextView) myDialog.findViewById(R.id.client_adresse_tv);
        tvVisite = (TextView) myDialog.findViewById(R.id.visite_tv);

        appeler_ll = (LinearLayout) myDialog.findViewById(R.id.appeler_ll);
        trajet_ll = (LinearLayout)  myDialog.findViewById(R.id.trajet_ll);
        visiter_ll = (LinearLayout)  myDialog.findViewById(R.id.visiter_ll);

        tvVisite.setText("TABLEAU DE BORD");


        tvClientNom.setText(client.getCLIENT_NOM().toUpperCase());
        tvClientAdresse.setText(client.getADRESSE_NR()+" "+client.getADRESSE_RUE()+" "+client.getADRESSE_QUARTIER());
        //txtclose.setText("M");
        tvclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        appeler_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
                Log.d(TAG, "onClick: "+"APPELER");

                callClient(client);
                //Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +client.getCLIENT_TELEPHONE1()));
                //startActivity(intent);

            }
        });

        trajet_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
                Log.d(TAG, "onClick: "+"TRAJET");
                drawPolyLine(client);


            }
        });

        visiter_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+"VISITER");
                myDialog.dismiss();
                Intent i = new Intent(RecensementActivity.this, TableauBordClientActivity.class);
                TableauBordClientActivity.client_code = client.getCLIENT_CODE();
                TableauBordClientActivity.source = "RecensementActivity";
                startActivity(i);

            }
        });


        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    private void callClient(Client client){

        String number = ("tel:" + client.getCLIENT_TELEPHONE1());
        Intent mIntent = new Intent(Intent.ACTION_CALL);
        mIntent.setData(Uri.parse(number));
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);

            // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        } else {
            //You already have permission
            try {
                startActivity(mIntent);
            } catch(SecurityException e) {
                e.printStackTrace();
            }
        }

    }

    private void drawPolyLine(Client client){

        double clientLatitue = Double.parseDouble(client.getGPS_LATITUDE().replace(",",".")) ;
        double clientLongitude = Double.parseDouble(client.getGPS_LONGITUDE().replace(",","."));

        try
        {
            // Launch Waze to look for Hawaii:
            String url = "https://waze.com/ul?ll="+String.valueOf(clientLatitue)+","+String.valueOf(clientLongitude)+"&navigate=yes";
            Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
            startActivity( intent );
        }
        catch ( ActivityNotFoundException ex  )
        {
            // If Waze is not installed, open it in Google Play:
            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.waze"));
            startActivity(intent);

        }
    }
}
