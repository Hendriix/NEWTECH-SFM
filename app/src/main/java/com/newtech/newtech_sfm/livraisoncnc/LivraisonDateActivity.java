package com.newtech.newtech_sfm.livraisoncnc;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import com.newtech.newtech_sfm.Activity.AuthActivity;
import com.newtech.newtech_sfm.Activity.CatalogueTacheActivity;
import com.newtech.newtech_sfm.Activity.ClientActivity;
import com.newtech.newtech_sfm.Activity.Client_Manager;
import com.newtech.newtech_sfm.Activity.PrintActivity2;
import com.newtech.newtech_sfm.Activity.TourneeActivity;
import com.newtech.newtech_sfm.Configuration.Common;
import com.newtech.newtech_sfm.Configuration.ListDataSave;
import com.newtech.newtech_sfm.Configuration.Livraison_Adapter;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.Parametre;
import com.newtech.newtech_sfm.Metier_Manager.ClientManager;
import com.newtech.newtech_sfm.Metier_Manager.ParametreManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteManager;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.databinding.LivraisonDateActivityBinding;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LivraisonDateActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ResultCallback<LocationSettingsResult>,
        LivraisonDatePresenter.LivraisonDateView {


    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 3;
    private final String TAG = LivraisonDateActivity.class.getName();
    public static Client clientCourant = new Client();
    public static String activity_source = "";
    public static String tache_code = "";
    public static String tournee_code = "";
    public String client_code = "";
    public static String commande_source = "";
    public static String affectation_type = "";
    public static String affectation_valeur = "";
    ClientManager client_manager;

    private static Double latitude;
    private static Double longitude;

    Livraison_Adapter livraison_adapter;
    List<Client> client_list = new ArrayList<>();
    List<Client> clientsWithoutVisits = new ArrayList<>();
    List<Client> clientProches = new ArrayList<>();
    SearchManager searchmanager;

    Dialog clientLivraisonDialog;
    DatePickerDialog date_echeance_picker;
    Calendar calendar_echeance;

    String activitySource = null;
    String tacheCode = null;
    String tourneeCode = null;
    String commandeSource = null;
    String affectationType = null;
    String affectationValeur = null;

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    protected LocationSettingsRequest mLocationSettingsRequest;
    protected Location mCurrentLocation;
    protected Boolean mRequestingLocationUpdates;
    protected String mLastUpdateTime;
    int RQS_GooglePlayServices = 0;
    ParametreManager parametreManager;
    int distance_gps = 20;

    LivraisonDatePresenter livraisonDatePresenter;
    VisiteManager visiteManager;

    private ProgressDialog progressDialog;
    private LivraisonDateActivityBinding binding;

    ListDataSave listDataSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = LivraisonDateActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initialiseVariables();

        clientLivraisonDialog = new Dialog(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.livraison_tb);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        visiteManager = new VisiteManager(getApplicationContext());
        client_manager = new ClientManager(getApplicationContext());
        searchmanager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        initProgressDialog();



        parametreManager = new ParametreManager(this);
        Parametre parametre = parametreManager.get("GPSPROCHE");

        if(listDataSave.getDataString("DATE_HIER").equals("")){
            listDataSave.setDataString("DATE_HIER",Common.dateYesterday());
        }
        String date_livraison = listDataSave.getDataString("DATE_HIER");
        binding.dateEt.setText(date_livraison);

        livraisonDatePresenter = new LivraisonDatePresenter(this, getApplicationContext());

        if (parametre.getVALEUR() == null || parametre.getVALEUR().length() == 0) {
            distance_gps = 20;
        } else {
            distance_gps = Integer.parseInt(parametre.getVALEUR());
        }

        View child = getLayoutInflater().inflate(R.layout.no_data_found, null);
        ((ViewGroup) binding.livraisonLv.getParent()).addView(child);

        binding.livraisonLv.setEmptyView(child);

        livraisonDatePresenter.synchronisationClientNcAl(date_livraison, affectation_type, affectation_valeur);


        binding.dateBtn.setOnClickListener(view -> {

            calendar_echeance = Calendar.getInstance();
            int day = calendar_echeance.get(Calendar.DAY_OF_MONTH);
            int month = calendar_echeance.get(Calendar.MONTH);
            int year = calendar_echeance.get(Calendar.YEAR);

            date_echeance_picker = new DatePickerDialog(LivraisonDateActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    binding.dateEt.setText(i + "-" + (i1 + 01) + "-" + i2);
                    String echeance_date = String.valueOf(binding.dateEt.getText());
                    listDataSave.setDataString("DATE_HIER",echeance_date);
                    livraisonDatePresenter.synchronisationClientNcAl(echeance_date, affectation_type, affectation_valeur);

                }
            }, year, month, day);

            date_echeance_picker.show();
        });

        binding.livraisonLv.setTextFilterEnabled(true);
        setupSearchView();

        binding.livraisonLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Client client = (Client) livraison_adapter.getItem(position);
                ClientActivity.visiteCourante = null;
                ClientActivity.clientCourant = client_manager.get(client.getCLIENT_CODE());
                ClientActivity.commande_source = commande_source;
                ClientActivity.gps_latitude = latitude;
                ClientActivity.gps_longitude = longitude;
                ShowPopup(client);
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


        checkLocationSettings();

        setTitle("CLIENTS VISITES");

    }

    private void setupSearchView() {
        binding.livraisonSv.setIconifiedByDefault(false);
        binding.livraisonSv.setOnQueryTextListener(this);
        //mSearchView.setSubmitButtonEnabled(true);
        binding.livraisonSv.setQueryHint("SEARCH HERE");

    }

    public boolean onQueryTextChange(String newText) {

        String textfilter = binding.livraisonSv.getQuery().toString().toLowerCase(Locale.getDefault());
        if (commande_source.equals("LIVRAISON")) {
            livraison_adapter.filter(textfilter);
        }
        //Toast.makeText(this,textfilter, Toast.LENGTH_LONG).show();
        return true;
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        int variable;
        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.va_checkBox1:
                if (commande_source.equals("LIVRAISON")) {
                    if (checked) {
                        variable = 1;

                    } else {
                        variable = 0;
                    }
                    livraison_adapter.filterClientRestant(variable, clientsWithoutVisits);
                    // Remove the meat
                    break;
                }

            case R.id.va_checkBox2:

                if (commande_source.equals("LIVRAISON")) {
                    clientProches = client_manager.getClientProches(client_list, latitude, longitude, this);
                    if (checked) {
                        variable = 1;

                    } else {
                        variable = 0;
                    }
                    livraison_adapter.filterClientRestant(variable, clientProches);
                    // Remove the meat
                    break;
                }
        }
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_client, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            case R.id.add_client:
                Intent intent = new Intent(this, Client_Manager.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //finish();
                return true;
            case R.id.logout:
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.apply();
                Intent i = new Intent(LivraisonDateActivity.this, AuthActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                return true;

            case R.id.option2:
                Intent intt = new Intent(this, PrintActivity2.class);
                intt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intt);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (activity_source.equals("CatalogueTacheActivity")) {

            Intent i = new Intent(this, CatalogueTacheActivity.class);
            startActivity(i);

        } else {
            LivraisonDateActivity.tournee_code = null;
            LivraisonDateActivity.affectation_valeur = null;
            Intent i = new Intent(this, TourneeActivity.class);
            startActivity(i);
        }

        clearVariables();
        finish();
    }

    private void drawPolyLine(Client client) {

        double clientLatitue = Double.parseDouble(client.getGPS_LATITUDE().replace(",", "."));
        double clientLongitude = Double.parseDouble(client.getGPS_LONGITUDE().replace(",", "."));

        try {
            // Launch Waze to look for Hawaii:
            String url = "https://waze.com/ul?ll=" + clientLatitue + "," + clientLongitude + "&navigate=yes";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            // If Waze is not installed, open it in Google Play:
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.waze"));
            startActivity(intent);

        }
    }


    public void ShowPopup(Client client) {

        clientLivraisonDialog.setContentView(R.layout.client_file_pop_up);

        TextView closeTv;
        TextView clientNomTv;
        TextView clientAdresseTv;

        LinearLayout appeler_ll;
        LinearLayout trajet_ll;
        LinearLayout visiter_ll;

        closeTv = (TextView) clientLivraisonDialog.findViewById(R.id.close_tv);
        clientNomTv = (TextView) clientLivraisonDialog.findViewById(R.id.client_nom_tv);
        clientAdresseTv = (TextView) clientLivraisonDialog.findViewById(R.id.client_adresse_tv);

        appeler_ll = (LinearLayout) clientLivraisonDialog.findViewById(R.id.appeler_ll);
        trajet_ll = (LinearLayout) clientLivraisonDialog.findViewById(R.id.trajet_ll);
        visiter_ll = (LinearLayout) clientLivraisonDialog.findViewById(R.id.visiter_ll);

        clientNomTv.setText(client.getCLIENT_NOM().toUpperCase());
        clientAdresseTv.setText(client.getADRESSE_NR() + " " + client.getADRESSE_RUE() + " " + client.getADRESSE_QUARTIER());
        closeTv.setOnClickListener(view -> clientLivraisonDialog.dismiss());

        appeler_ll.setOnClickListener(view -> {
            clientLivraisonDialog.dismiss();
            Log.d(TAG, "onClick: " + "APPELER");

            callClient(client);

        });

        trajet_ll.setOnClickListener(view -> {
            clientLivraisonDialog.dismiss();
            Log.d(TAG, "onClick: " + "TRAJET");
            drawPolyLine(client);


        });

        visiter_ll.setOnClickListener(view -> {
            Log.d(TAG, "onClick: " + "VISITER");
            clientLivraisonDialog.dismiss();
            Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
            startActivity(intent);
            finish();
        });


        clientLivraisonDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        clientLivraisonDialog.show();
    }

    private void callClient(Client client) {

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
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the phone call

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        clientLivraisonDialog.dismiss();
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clientLivraisonDialog.dismiss();
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

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient,
                    mLocationRequest,
                    this
            ).setResultCallback(status -> {
                mRequestingLocationUpdates = true;
                //     setButtonsEnabledState();
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
        ).setResultCallback(status -> {
            mRequestingLocationUpdates = false;
            //   setButtonsEnabledState();
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
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

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

    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {

        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                Log.i(TAG, "All location settings are satisfied.");

                //Toast.makeText(this, "Location is already on.", Toast.LENGTH_SHORT).show();
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

    private void updateLocationUI() {
        if (mCurrentLocation != null) {

            latitude = mCurrentLocation.getLatitude();
            longitude = mCurrentLocation.getLongitude();
            float accuracy = mCurrentLocation.getAccuracy();

            Log.d(TAG, "updateLocationUI: latitude " + latitude);
            Log.d(TAG, "updateLocationUI: longitude " + longitude);
            Log.d(TAG, "updateLocationUI: accuracy " + accuracy);

            binding.vaTextView2.setText(String.valueOf(accuracy));

            if (accuracy < distance_gps) {
                binding.vaTextView2.setBackgroundColor(ContextCompat.getColor(this, R.color.good));
            } else {
                binding.vaTextView2.setBackgroundColor(ContextCompat.getColor(this, R.color.bad));
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");

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
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
        mGoogleApiClient.disconnect();
    }

    @Override
    public void showSuccess(ArrayList<Client> clients) {
        progressDialog.dismiss();

        client_list.clear();
        client_list = clients;
        livraison_adapter = new Livraison_Adapter(LivraisonDateActivity.this, clients);
        binding.livraisonLv.setAdapter(livraison_adapter);
    }

    @Override
    public void showError(String message) {
        progressDialog.dismiss();
        showText(message);
    }

    @Override
    public void showEmpty(String message) {
        progressDialog.dismiss();
        ArrayList<Client> clients = new ArrayList<>();
        livraison_adapter = new Livraison_Adapter(LivraisonDateActivity.this, clients);
        binding.livraisonLv.setAdapter(livraison_adapter);
        showText(message);
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.hide();
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(LivraisonDateActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Chargement en cours");
    }


    private void showText(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void initialiseVariables() {
        listDataSave = new ListDataSave(getApplicationContext(), "MyPref");

        activitySource = listDataSave.getDataString("ACTIVITY_SOURCE");
        activity_source = activitySource;

        tacheCode = listDataSave.getDataString("TACHE_CODE");
        tache_code = tacheCode;

        affectationType = listDataSave.getDataString("AFFECTATION_TYPE");
        affectation_type = affectationType;

        affectationValeur = listDataSave.getDataString("AFFECTATION_VALEUR");
        affectation_valeur = affectationValeur;

        commandeSource = listDataSave.getDataString("COMMANDE_SOURCE");
        commande_source = commandeSource;

    }

    private void clearVariables() {

        listDataSave.remove("ACTIVITY_SOURCE");
        activity_source = null;

        listDataSave.remove("TACHE_CODE");
        tache_code = null;

        listDataSave.remove("AFFECTATION_TYPE");
        affectation_type = null;

        listDataSave.remove("AFFECTATION_VALEUR");
        affectation_valeur = null;

        listDataSave.remove("COMMANDE_SOURCE");
        commande_source = null;

        listDataSave.remove("DATE_HIER");

    }


}
