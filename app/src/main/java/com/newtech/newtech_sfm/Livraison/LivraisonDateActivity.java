package com.newtech.newtech_sfm.Livraison;

import static com.newtech.newtech_sfm.R.id.visite_search_client;

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
import android.graphics.Bitmap;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.newtech.newtech_sfm.Activity.AuthActivity;
import com.newtech.newtech_sfm.Activity.CatalogueTacheActivity;
import com.newtech.newtech_sfm.Activity.ClientActivity;
import com.newtech.newtech_sfm.Activity.Client_Manager;
import com.newtech.newtech_sfm.Activity.PrintActivity2;
import com.newtech.newtech_sfm.Activity.TourneeActivity;
import com.newtech.newtech_sfm.Configuration.Client_Adapter;
import com.newtech.newtech_sfm.Configuration.Livraison_Adapter;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.Parametre;
import com.newtech.newtech_sfm.Metier_Manager.ClientManager;
import com.newtech.newtech_sfm.Metier_Manager.ParametreManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteManager;
import com.newtech.newtech_sfm.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by stagiaireit2 on 26/07/2016.
 */
public class LivraisonDateActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ResultCallback<LocationSettingsResult> ,
        LivraisonDatePresenter.LivraisonDateView{


    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 3;
    private final String TAG = LivraisonDateActivity.class.getName();
    public static Client clientCourant=new Client();
    public static String activity_source="";
    public static String tache_code="";
    public static String tournee_code="";
    public String client_code="";
    public static String commande_source="";
    public static String affectation_type="";
    public static String affectation_valeur="";
    private static Bitmap decodedByte = null;
    public static ImageView expanded_image;
    ClientManager client_manager ;

    private static Double latitude;
    private static Double longitude;
    private static float accuracy;

    SearchView mSearchView;
    Client_Adapter client_adapter;
    Livraison_Adapter livraison_adapter;
    List<Client> client_list = new ArrayList<Client>();
    List<Client> clientsWithoutVisits= new ArrayList<Client>();
    List<Client> clientProches = new ArrayList<>();
    RecyclerView rv;
    ListView mListView1;
    SearchManager searchmanager;

    private ProgressBar mProgressBar;
    private TextView vaTextView1;
    private TextView vaTextView2;
    private CheckBox vaCheckBox1;
    private CheckBox vaCheckBox2;

    private double NombreClient;
    private double NombreVisiteClient;
    private double ProgressionStatut;
    private int ClientRestants;

    private ZXingScannerView zXingScannerView;
    public int CAMERA_CODE = 4;
    Dialog myDialog;

    EditText date_et;
    DatePickerDialog date_echeance_picker;
    Calendar calendar_echeance;
    Button date_btn;

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

    LivraisonDatePresenter livraisonDatePresenter;
    VisiteManager visiteManager;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.livraison_date_activity);
        myDialog = new Dialog(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.livraison_tb);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        expanded_image = (ImageView) findViewById(R.id.expanded_image);

        visiteManager = new VisiteManager(getApplicationContext());
        client_manager = new ClientManager(getApplicationContext());
        searchmanager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView=(SearchView) findViewById(visite_search_client);
        mProgressBar = (ProgressBar) findViewById(R.id.va_progressBar1);
        vaCheckBox1=(CheckBox) findViewById(R.id.va_checkBox1);
        vaCheckBox2=(CheckBox) findViewById(R.id.va_checkBox2);
        mListView1=(ListView) findViewById(R.id.livraison_lv);
        date_btn = (Button)findViewById(R.id.date_btn);
        date_et = (EditText) findViewById(R.id.date_et);
        vaTextView1=(TextView) findViewById(R.id.va_textView1);
        vaTextView2=(TextView) findViewById(R.id.va_textView2);
        mProgressBar=(ProgressBar) findViewById(R.id.livraison_pd);

        initProgressDialog();
        
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DateVisiteAS = sdf.format(new Date());
        String date_hier = dateYesterday();

        parametreManager = new ParametreManager(this);
        Parametre parametre = parametreManager.get("GPSPROCHE");

        date_et.setText(date_hier);

        livraisonDatePresenter = new LivraisonDatePresenter(this,getApplicationContext());

        if(parametre.getVALEUR()== null || parametre.getVALEUR().length() == 0){
            distance_gps =20;
        }else{
            distance_gps =Integer.parseInt(parametre.getVALEUR());
        }

        View child = getLayoutInflater().inflate(R.layout.no_data_found, null);
        ((ViewGroup)mListView1.getParent()).addView(child);

        mListView1.setEmptyView(child);

        if(commande_source.equals("LIVRAISON")){

            if(affectation_type.equals("VENDEUR")){
                livraisonDatePresenter.getListClientALivrerByVC(affectation_valeur);
            }else if(affectation_type.equals("TOURNEE")){
                livraisonDatePresenter.getListClientALivrerByTC(affectation_valeur);
            }

            if(affectation_type.equals("VENDEUR")){
                client_list = client_manager.getListClNLByVC(affectation_valeur);
            }else if(affectation_type.equals("TOURNEE")){
                client_list = client_manager.getListClNLByTC(affectation_valeur);
            }


            date_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    calendar_echeance = Calendar.getInstance();
                    int day = calendar_echeance.get(Calendar.DAY_OF_MONTH);
                    int month = calendar_echeance.get(Calendar.MONTH);
                    int year = calendar_echeance.get(Calendar.YEAR);

                    date_echeance_picker = new DatePickerDialog(LivraisonDateActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            date_et.setText(i+"-"+(i1+01)+"-"+i2);
                            String echeance_date = String.valueOf(date_et.getText());

                            if(affectation_type.equals("VENDEUR")){
                                livraisonDatePresenter.getListClientALivrerByVCD(affectation_valeur,echeance_date);
                            }else if(affectation_type.equals("TOURNEE")){
                                livraisonDatePresenter.getListClientALivrerByTCD(affectation_valeur,echeance_date);
                            }

                        }
                    },year,month,day);

                    date_echeance_picker.show();
                }
            });

            mListView1.setTextFilterEnabled(true);
            setupSearchView();

            mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Client client =(Client)livraison_adapter.getItem(position);
                    ClientActivity.visiteCourante=null;
                    ClientActivity.clientCourant=client_manager.get(client.getCLIENT_CODE());
                    ClientActivity.commande_source= commande_source;
                    ClientActivity.gps_latitude = latitude;
                    ClientActivity.gps_longitude = longitude;
                    ShowPopup(client);
                }
            });

        }else{

            Toast.makeText(LivraisonDateActivity.this,"AUTRE",
                    Toast.LENGTH_LONG).show();
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

        setTitle("CLIENTS VISITES");

    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        //mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("SEARCH HERE");

    }

    public boolean onQueryTextChange(String newText) {

        String textfilter=mSearchView.getQuery().toString().toLowerCase(Locale.getDefault());
        if(commande_source.equals("LIVRAISON")){
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
        switch(view.getId()) {
            case R.id.va_checkBox1:
                if(commande_source.equals("LIVRAISON")){
                    if (checked){
                        variable=1;
                        livraison_adapter.filterClientRestant(variable,clientsWithoutVisits );

                    }else{
                        variable=0;
                        livraison_adapter.filterClientRestant(variable,clientsWithoutVisits );
                    }
                    // Remove the meat
                    break;
                }

            case R.id.va_checkBox2:

                if(commande_source.equals("LIVRAISON")){
                    if (checked){
                        clientProches = client_manager.getClientProches(client_list,latitude,longitude,this);
                        variable=1;
                        livraison_adapter.filterClientRestant(variable,clientProches );

                    }else{
                        clientProches = client_manager.getClientProches(client_list,latitude,longitude,this);
                        variable=0;
                        livraison_adapter.filterClientRestant(variable,clientProches );
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

        if(activity_source=="CatalogueTacheActivity"){
            //Intent i = new Intent(this, CatalogueTourneeActivity.class);

            LivraisonDateActivity.activity_source = null;
            LivraisonDateActivity.commande_source = null;
            LivraisonDateActivity.tache_code = null;
            LivraisonDateActivity.affectation_type = null;
            LivraisonDateActivity.affectation_valeur = null;

            Intent i = new Intent(this, CatalogueTacheActivity.class);
            startActivity(i);
            finish();

        }else{
            LivraisonDateActivity.tournee_code = null;
            LivraisonDateActivity.affectation_valeur = null;
            Intent i = new Intent(this, TourneeActivity.class);
            startActivity(i);
            finish();
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

    /*public void scan(View view){
        zXingScannerView =new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();

    }*/

    /*@Override
    public void handleResult(Result result) {
        Toast.makeText(getApplicationContext(),result.getText(),Toast.LENGTH_SHORT).show();
        ClientActivity.visiteCourante=null;
        ClientActivity.clientCourant=client_manager.get(result.getText());
        ClientActivity.commande_source= commande_source;
        Intent intent=new Intent(getApplicationContext(),ClientActivity.class);
        //intent.putExtra("TACHE_CODE",tache_code);
        startActivity(intent);
        finish();
        //zXingScannerView.resumeCameraPreview(this);
    }*/

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
        Log.d(TAG, "onPause: ");
        myDialog.dismiss();
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onDestroy() {
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

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
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
            Log.d(TAG, "updateLocationUI: accuracy "+accuracy);

            vaTextView2.setText(String.valueOf(accuracy));

            if(accuracy < distance_gps){
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
    public void showSuccess(ArrayList<Client> clients) {
        progressDialog.dismiss();

        NombreClient=client_manager.GetClientNombreByLivraison();
        clientsWithoutVisits=client_manager.getListWithoutVisiteLivraison();
        NombreVisiteClient=visiteManager.GetVisiteNombre();

        client_list.clear();
        client_list = clients;

        vaTextView1.setText((int)NombreVisiteClient+"/"+(int)NombreClient+" CLIENTS VISITES");
        ProgressionStatut=(NombreVisiteClient/NombreClient)*100;

        ClientRestants =(int)(NombreClient-NombreVisiteClient);

        if(NombreVisiteClient>0 && ProgressionStatut<100){
            mProgressBar.setProgress((int)ProgressionStatut);
        }

        if(ProgressionStatut==100){
            mProgressBar.setProgress((int)ProgressionStatut);
        }

        livraison_adapter=new Livraison_Adapter(LivraisonDateActivity.this,clients);
        mListView1.setAdapter(livraison_adapter);
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
        livraison_adapter=new Livraison_Adapter(LivraisonDateActivity.this,clients);
        mListView1.setAdapter(livraison_adapter);
        showText(message);
    }

    @Override
    public void showLoading() {

    }

    private void initProgressDialog(){
        progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Chargement en cours");
    }


    private void showText(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public String dateYesterday() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }
}
