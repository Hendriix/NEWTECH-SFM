package com.newtech.newtech_sfm.Activity;

import static com.newtech.newtech_sfm.R.id.visite_search_client;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

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
import com.newtech.newtech_sfm.Configuration.Client_Adapter;
import com.newtech.newtech_sfm.Configuration.ListDataSave;
import com.newtech.newtech_sfm.Configuration.Livraison_Adapter;
import com.newtech.newtech_sfm.DialogClientFiltre.DialogClientFiltre;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.Parametre;
import com.newtech.newtech_sfm.Metier.Tournee;
import com.newtech.newtech_sfm.Metier_Manager.ClientManager;
import com.newtech.newtech_sfm.Metier_Manager.ParametreManager;
import com.newtech.newtech_sfm.Metier_Manager.TourneeManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteManager;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.Service.AlarmReceiver;
import com.newtech.newtech_sfm.Service.BlutDiscovery;
import com.newtech.newtech_sfm.Service.BlutoothConnctionService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by stagiaireit2 on 26/07/2016.
 */
public class VisiteActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ResultCallback<LocationSettingsResult> {


    // START STATIC FIELDS

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 3;
    private static final int ZXING_CAMERA_PERMISSION = 1;
    private final String TAG = VisiteActivity.class.getName();
    public static Client clientCourant=new Client();
    public static String activity_source="";
    public static String tache_code="";
    public static String tournee_code="";
    public String client_code="";
    public static String commande_source="";
    public static String affectation_type="";
    public static String affectation_valeur="";
    public static ImageView expanded_image;
    private float accuracy = 0;

    // END STATIC FIELDS /////////////////////////////////////////////////////////////

    // START ADAPTERS

    Client_Adapter client_adapter;
    Livraison_Adapter livraison_adapter;

    // END ADAPTERS ////////////////////////////////////////////////////////////////

    private double nombreClients;
    private double nombreClientsVisites;
    private double nombreClientsNonVisites;
    private double progressionStatut;

    // START CAMERA AND GPS

    private static final int CAMERA_CODE = 4;
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
    int distance_gps = 20;
    private static Double latitude;
    private static Double longitude;

    // END CAMERA AND GPS /////////////////////////////////////////////////////////


    // START DIALOG

    DialogClientFiltre dialogClientFiltre;
    Dialog myDialog;

    // END DIALOG //////////////////////////////////////////////////////////////////

    // START VIEW ITEMS

    Button filter_btn;
    Button scan_btn;
    ProgressBar mProgressBar;
    SearchView mSearchView;
    TextView vaTextView2;
    TextView vaTextView1;
    ListView mListView1;

    // END VIEW ITEMS ///////////////////////////////////////////////////////////////////////

    // OTHERS

    ListDataSave listDataSave;
    List<Client> client_list = new ArrayList<>();
    List<Client> clientsWithoutVisits= new ArrayList<>();
    List<Client> clientProches = new ArrayList<>();
    Parametre parametre;
    SimpleDateFormat sdf;
    String dateVisiteAS;

    String chaine_tournee = null;
    String chaine_type = null;
    String chaine_classe = null;
    String chaine_categorie = null;


    // END OTHERS ////////////////////////////////////////////////////////////////////////////

    // START MANAGERS

    VisiteManager visite_manager;
    ParametreManager parametreManager;
    SearchManager searchmanager;
    ClientManager client_manager ;

    // END MANAGERS ////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.visite_activity);

        dialogClientFiltre = DialogClientFiltre.newInstance(getApplicationContext(),VisiteActivity.this,activity_source,affectation_valeur);
        myDialog = new Dialog(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        expanded_image = (ImageView) findViewById(R.id.expanded_image);
        client_manager = new ClientManager(getApplicationContext());
        searchmanager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView=(SearchView) findViewById(visite_search_client);
        mListView1=(ListView) findViewById(R.id.visite_listview1);
        scan_btn = findViewById(R.id.scan_btn);
        filter_btn = findViewById(R.id.filter_btn);
        vaTextView1 = (TextView) findViewById(R.id.va_textView1);
        vaTextView2=(TextView) findViewById(R.id.va_textView2);
        mProgressBar =(ProgressBar) findViewById(R.id.va_progressBar1);

        listDataSave = new ListDataSave(getApplicationContext(),"MyPref");

        client_list = listDataSave.getDataList("client_list");

        chaine_tournee = listDataSave.getDataString("tournee_code");
        chaine_type = listDataSave.getDataString("type_code");
        chaine_classe = listDataSave.getDataString("classe_code");
        chaine_categorie = listDataSave.getDataString("categorie_code");


        if (chaine_tournee.equals("")){
            if(affectation_valeur.equals("tous")){
                chaine_tournee = affectation_valeur;
            }else{
                chaine_tournee = "'"+affectation_valeur+"'";
            }
        }

        visite_manager=new VisiteManager(getApplicationContext());
        parametreManager = new ParametreManager(this);
        parametre = parametreManager.get("GPSPROCHE");

        if(parametre.getVALEUR()== null || parametre.getVALEUR().length() == 0){
            distance_gps =20;
        }else{
            distance_gps =Integer.parseInt(parametre.getVALEUR());
        }

        sdf=new SimpleDateFormat("yyyy-MM-dd");
        dateVisiteAS = sdf.format(new Date());

        initListView();

        if(commande_source.equals("VISITE")){

            if(client_list.size() <= 0){
                client_list=client_manager.getListFiltered(chaine_tournee,chaine_type,chaine_classe,chaine_categorie);
            }else{
                filter_btn.setBackgroundResource(R.drawable.filter_actif);
            }


            clientsWithoutVisits=client_manager.getListFilteredWithoutVisite(chaine_tournee,chaine_type,chaine_classe,chaine_categorie,dateVisiteAS);

            nombreClients=client_list.size();
            nombreClientsNonVisites = clientsWithoutVisits.size();
            nombreClientsVisites = nombreClients-nombreClientsNonVisites;

            client_adapter=new Client_Adapter(this,client_list,getApplicationContext());

            initListViewAdapter(client_adapter);


        }else if(commande_source.equals("ENCAISSEMENT")){

            if(affectation_type.equals("VENDEUR")){
                client_list=client_manager.getListClNEByVC(affectation_valeur);
            }else if(affectation_type.equals("TOURNEE")){
                client_list=client_manager.getListClNEByTC(affectation_valeur);
            }

            nombreClients=client_list.size();
            nombreClientsVisites=visite_manager.GetVisiteNombreByDate(dateVisiteAS);
            clientsWithoutVisits=client_manager.getListWithoutVisiteCmdaL(dateVisiteAS);

            livraison_adapter=new Livraison_Adapter(this,client_list);
            initListViewAdapter(livraison_adapter);

        }else{

                showText("Source inconnu de tâche");
        }

        updateProgressData(nombreClientsVisites,nombreClients);

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

        filter_btn.setOnClickListener(v -> {

            FragmentManager fragmentManager = getSupportFragmentManager();
            dialogClientFiltre.show(fragmentManager, "Dialog");

        });

        scan_btn.setOnClickListener(v -> {

            if(checkAccuracy()){
                if (ContextCompat.checkSelfPermission(VisiteActivity.this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                    IntentIntegrator integrator = new IntentIntegrator(VisiteActivity.this);

                    integrator.setPrompt("Approchez le téléphone du QR code");

                    integrator.setOrientationLocked(false);

                    integrator.initiateScan();

                } else {
                    requestCameraPermission();
                }
            }else{
                showText("Merci de garder une précision inférieur ou égale à : "+distance_gps);
            }


        });

        setTitle("CLIENTS VISITES");

    }

    private void showText(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    private void setupSearchView() {

        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        //mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("SEARCH HERE");

    }

    public boolean onQueryTextChange(String newText) {

        String textfilter=mSearchView.getQuery().toString().toLowerCase(Locale.getDefault());
        if(commande_source.equals("VISITE")){
            client_adapter.filter(textfilter);
        }
        return true;
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        int variable;
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.va_checkBox1:

                if(commande_source.equals("VISITE")){
                    if (checked){
                        variable=1;

                    }else{
                        variable=0;
                    }
                    client_adapter.filterClientRestant(variable,clientsWithoutVisits );
                    // Remove the meat
                    break;
                }

            case R.id.va_checkBox2:

                if(commande_source.equals("VISITE")){
                    if (checked){
                        clientProches = client_manager.getClientProches(client_list,latitude,longitude,this);
                        variable=1;
                        client_adapter.filterClientRestant(variable,clientProches );

                    }else{
                        clientProches = client_manager.getClientProches(client_list,latitude,longitude,this);
                        variable=0;
                        client_adapter.filterClientRestant(variable,clientProches );
                    }
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
                Intent intent=new Intent(this,Client_Manager.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //finish();
                return true;
            case R.id.logout:
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(VisiteActivity.this, AuthActivity.class);
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

        Intent i;
        VisiteActivity.affectation_type = null;
        VisiteActivity.affectation_valeur = null;

        VisiteActivity.commande_source = null;
        VisiteActivity.tache_code = null;
        VisiteActivity.tournee_code = null;

        if(activity_source.equals("CatalogueTacheActivity")){
             i = new Intent(this, CatalogueTacheActivity.class);
        }else if(activity_source.equals("TourneeActivity")){
             i = new Intent(this, TourneeActivity.class);
        }else{
             i = new Intent(this, MenuActivity.class);
        }
        VisiteActivity.activity_source = null;
        startActivity(i);
        finish();
    }

    private void drawPolyLine(Client client){

        double clientLatitue = Double.parseDouble(client.getGPS_LATITUDE().replace(",",".")) ;
        double clientLongitude = Double.parseDouble(client.getGPS_LONGITUDE().replace(",","."));

        try
        {
            // Launch Waze to look for Hawaii:
            String url = "https://waze.com/ul?ll="+clientLatitue+","+clientLongitude+"&navigate=yes";
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

    public void ShowPopup(Client client) {

        myDialog.setContentView(R.layout.client_file_pop_up);

        TextView tvclose;
        TextView tvClientNom;
        TextView tvClientAdresse;

        LinearLayout appeler_ll;
        LinearLayout trajet_ll;
        LinearLayout visiter_ll;

        tvclose =(TextView) myDialog.findViewById(R.id.close_tv);
        tvClientNom =(TextView) myDialog.findViewById(R.id.client_nom_tv);
        tvClientAdresse =(TextView) myDialog.findViewById(R.id.client_adresse_tv);

        appeler_ll = (LinearLayout) myDialog.findViewById(R.id.appeler_ll);
        trajet_ll = (LinearLayout)  myDialog.findViewById(R.id.trajet_ll);
        visiter_ll = (LinearLayout)  myDialog.findViewById(R.id.visiter_ll);


        tvClientNom.setText(client.getCLIENT_NOM().toUpperCase());
        tvClientAdresse.setText(client.getADRESSE_NR()+" "+client.getADRESSE_RUE()+" "+client.getADRESSE_QUARTIER());
        //txtclose.setText("M");
        tvclose.setOnClickListener(view -> myDialog.dismiss());

        appeler_ll.setOnClickListener(view -> {
            myDialog.dismiss();
            Log.d(TAG, "onClick: "+"APPELER");

            callClient(client);
            //Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +client.getCLIENT_TELEPHONE1()));
            //startActivity(intent);

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
                Intent intent=new Intent(getApplicationContext(),ClientActivity.class);
                startActivity(intent);
                finish();
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the phone call

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: whattt");
        myDialog.dismiss();
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: whattt");
        super.onDestroy();
        myDialog.dismiss();
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
                    (com.google.android.gms.location.LocationListener) this
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
                (com.google.android.gms.location.LocationListener) this
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

    /**
     * Invoked when settings dialog is opened and action taken
     * @param locationSettingsResult
     *	This below OnResult will be used by settings dialog actions.
     */

    //step 5
    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {

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

    /**
     *	This OnActivityResult will listen when
     *	case LocationSettingsStatusCodes.RESOLUTION_REQUIRED: is called on the above OnResult
     */
    //step 6:


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

            Log.d(TAG, "updateLocationUI: latitude "+latitude);
            Log.d(TAG, "updateLocationUI: longitude "+longitude);
            Log.d(TAG, "updateLocationUI: accuracy "+ accuracy);

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
    /**
     *	This updateCityAndPincode method uses Geocoder api to map the latitude and longitude into city location or pincode.
     *	We can retrieve many details using this Geocoder class.
     *
     And yes the Geocoder will not work unless you have data connection or wifi connected to internet.
     */

    /* ########################################    GPS ######################################################*/

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: whattt");

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
        Log.d(TAG, "onResume: whattt");
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
        Log.d(TAG, "onStop: whattt");
        mGoogleApiClient.disconnect();
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
                            ActivityCompat.requestPermissions(VisiteActivity.this,
                                    new String[] {Manifest.permission.CAMERA}, CAMERA_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            stopService(new Intent(VisiteActivity.this, BlutoothConnctionService.class));
                            stopService(new Intent(VisiteActivity.this, BlutDiscovery.class));
                            Intent intent = new Intent(VisiteActivity.this, AlarmReceiver.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(VisiteActivity.this, 0,
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
                        Intent intent = new Intent(VisiteActivity.this, ScanActivity.class);
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
                        String client_code = data.getStringExtra("result");
                        int qr_code;
                        try{
                            qr_code = Integer.parseInt(client_code);
                            visiterClientQrVersion(qr_code);
                        }
                        catch (NumberFormatException ex){
                            Toast.makeText(getApplicationContext(),
                                    "QR CODE DOIT ETRE ENTIER !", Toast.LENGTH_SHORT)
                                    .show();
                        }



                        //Log.d(TAG, "onActivityResult: "+ "RESULT OK");
                        Toast.makeText(getApplicationContext(),
                                data.getStringExtra("result"), Toast.LENGTH_SHORT)
                                .show();
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

                        String client_code = result.getContents();
                        //Toast.makeText(this,"SUCCEFULL "+client_code,Toast.LENGTH_SHORT).show();
                        int qr_code;
                        try{
                            qr_code = Integer.parseInt(client_code);
                            visiterClientQrVersion(qr_code);
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

    private void visiter_client(String client_code){

        TourneeManager tourneeManager = new TourneeManager(this);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        Client client_tc = client_manager.getByCcTc(client_code,affectation_valeur);
        Client client_c = client_manager.get(client_code);

        if(client_tc.getCLIENT_CODE() == null){

            if(client_c.getCLIENT_CODE() == null){
                Toast.makeText(this,"CE CLIENT N'EXISTE PAS DANS VOTRE BDD",Toast.LENGTH_SHORT).show();
            }else{
                Tournee tournee = tourneeManager.get(client_c.getTOURNEE_CODE());

                if(tournee.getTOURNEE_CODE() == null){
                    Toast.makeText(this,"CE CLIENT N'EXISTE PAS DANS VOTRE BDD",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"C'EST UN CLIENT DE LA TOURNEE "+ tournee.getTOURNEE_NOM(),Toast.LENGTH_SHORT).show();
                }
            }

        }else{
            ClientActivity.visiteCourante=null;
            ClientActivity.clientCourant=client_manager.get(client_tc.getCLIENT_CODE());
            ClientActivity.commande_source= commande_source;
            ClientActivity.gps_latitude = latitude;
            ClientActivity.gps_longitude = longitude;
            ClientActivity.visite_source = "SCAN";
            ShowPopup(client_tc);
        }


    }

    private void visiterClientQrVersion(int qr_code){

        Log.d(TAG, "visiterClientQrVersion: "+qr_code);

        TourneeManager tourneeManager = new TourneeManager(this);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        Client client_tc = client_manager.getByCcQr(qr_code,affectation_valeur);
        Client client_c = client_manager.getByQr(qr_code);



        if(client_tc.getCLIENT_CODE() == null){

            if(client_c.getCLIENT_CODE() == null){
                Toast.makeText(this,"CE CLIENT N'EXISTE PAS DANS VOTRE BDD",Toast.LENGTH_SHORT).show();
            }else{

                if(affectation_valeur.equals("tous")){
                    ClientActivity.visiteCourante=null;
                    ClientActivity.clientCourant=client_manager.get(client_c.getCLIENT_CODE());
                    ClientActivity.commande_source= commande_source;
                    ClientActivity.gps_latitude = latitude;
                    ClientActivity.gps_longitude = longitude;
                    ClientActivity.visite_source = "SCAN";
                    ShowPopup(client_c);

                }else{

                    Tournee tournee = tourneeManager.get(client_c.getTOURNEE_CODE());

                    if(tournee.getTOURNEE_CODE() == null){
                        Toast.makeText(this,"CE CLIENT N'EXISTE PAS DANS VOTRE BDD",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this,"C'EST UN CLIENT DE LA TOURNEE "+ tournee.getTOURNEE_NOM(),Toast.LENGTH_SHORT).show();
                    }
                }

            }

        }else{
            ClientActivity.visiteCourante=null;
            ClientActivity.clientCourant=client_manager.get(client_tc.getCLIENT_CODE());
            ClientActivity.commande_source= commande_source;
            ClientActivity.gps_latitude = latitude;
            ClientActivity.gps_longitude = longitude;
            ClientActivity.visite_source = "SCAN";
            ShowPopup(client_tc);
        }


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void showFilteredClients(ArrayList<Client> clientArrayList,ArrayList<Client> clientListWithoutVisit,String chaine_tournee,String chaine_type,String chaine_classe,String chaine_categorie){

        dialogClientFiltre.dismiss();

        listDataSave.setDataList("client_list", clientArrayList);
        listDataSave.setDataString("tournee_code",chaine_tournee);
        listDataSave.setDataString("type_code",chaine_type);
        listDataSave.setDataString("classe_code",chaine_classe);
        listDataSave.setDataString("categorie_code",chaine_categorie);

        client_list.clear();
        filter_btn.setBackgroundResource(R.drawable.filter_actif);
        

        client_list = clientArrayList;

        clientsWithoutVisits = clientListWithoutVisit;

        client_adapter=new Client_Adapter(this,client_list,getApplicationContext());

        nombreClients = client_list.size();
        nombreClientsNonVisites = clientListWithoutVisit.size();
        nombreClientsVisites = nombreClients-nombreClientsNonVisites;
        
        updateProgressData(nombreClientsVisites,nombreClients);

        View child = getLayoutInflater().inflate(R.layout.no_data_found, null);
        ((ViewGroup)mListView1.getParent()).addView(child);

        mListView1.setEmptyView(child);
        initListViewAdapter(client_adapter);

    }

    public void clearClientFilter(){

        dialogClientFiltre.dismiss();
        filter_btn.setBackgroundResource(R.drawable.filter_inactif);

        client_list.clear();

        listDataSave.remove("tournee_code");
        listDataSave.remove("type_code");
        listDataSave.remove("classe_code");
        listDataSave.remove("categorie_code");

        if (chaine_tournee.equals("")){
            if(affectation_valeur.equals("tous")){
                chaine_tournee = affectation_valeur;
            }else{
                chaine_tournee = "'"+affectation_valeur+"'";
            }
        }

        chaine_type = "";
        chaine_classe = "";
        chaine_categorie = "";

        client_list = client_manager.getListFiltered(chaine_tournee,chaine_type,chaine_classe,chaine_categorie);
        clientsWithoutVisits = client_manager.getListFilteredWithoutVisite(chaine_tournee,chaine_type,chaine_classe,chaine_categorie,dateVisiteAS);

        nombreClients = client_list.size();
        nombreClientsNonVisites = clientsWithoutVisits.size();
        nombreClientsVisites = nombreClients-nombreClientsNonVisites;

        updateProgressData(nombreClientsVisites,nombreClients);

        client_adapter=new Client_Adapter(this,client_list,getApplicationContext());
        initListViewAdapter(client_adapter);

    }

    private void initListView(){
        View child = getLayoutInflater().inflate(R.layout.no_data_found, null);
        ((ViewGroup)mListView1.getParent()).addView(child);
        mListView1.setEmptyView(child);
        mListView1.setTextFilterEnabled(true);
        setupSearchView();
    }

    private void initListViewAdapter(BaseAdapter mBaseAdapter){

        mListView1.setAdapter(mBaseAdapter);
        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(checkAccuracy()){
                    Client client =(Client)mBaseAdapter.getItem(position);
                    ClientActivity.visiteCourante=null;
                    ClientActivity.clientCourant=client_manager.get(client.getCLIENT_CODE());
                    ClientActivity.commande_source= commande_source;
                    ClientActivity.gps_latitude = latitude;
                    ClientActivity.gps_longitude = longitude;
                    ClientActivity.visite_source = "CLIC";
                    ShowPopup(client);
                }else{
                    showText("Merci de garder une précision inférieur ou égale à : "+distance_gps);
                }



            }
        });
        mBaseAdapter.notifyDataSetChanged();
    }
    
    
    private void updateProgressData(double nbClientsVisites , double nbClients){

        if(nbClients>0){
            progressionStatut = (nbClientsVisites / nbClients) * 100;
        }else{
            progressionStatut = 0;
        }



        Log.d(TAG, "updateProgressData: nbcv "+nbClientsVisites);
        Log.d(TAG, "updateProgressData: nbc "+nbClients);
        Log.d(TAG, "updateProgressData: progress "+progressionStatut);

        vaTextView1.setText((int)nbClientsVisites+"/"+(int)nbClients+" CLIENTS VISITES");

        if(nbClientsVisites>0 && progressionStatut <100){
            mProgressBar.setProgress((int) progressionStatut);
        }

        if(progressionStatut ==100){
            mProgressBar.setProgress((int) progressionStatut);
        }
    }

    private boolean checkAccuracy(){
         boolean isChecked = false;
         if(accuracy<=distance_gps){
             isChecked = true;
         }
         return isChecked;
    }
}
