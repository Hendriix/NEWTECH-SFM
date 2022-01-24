package com.newtech.newtech_sfm.Service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.Configuration.GpsresolverActivity;
import com.newtech.newtech_sfm.Metier.Gpstracker;
import com.newtech.newtech_sfm.Metier_Manager.GpstrackerManager;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by sferricha on 16/12/2016.
 */

public class Gpstrackerservice extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ResultCallback<LocationSettingsResult> {

// *gps

    public static Double latitude;
    public static Double longitude;
    public static float accuracy;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public static float getAccuracy() {
        return accuracy;
    }

    public static void setAccuracy(float accuracy) {
        Gpstrackerservice.accuracy = accuracy;
    }

    // LogCat tag
    private static final String TAG = Gpstrackerservice.class.getSimpleName();
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
    protected final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
    protected final static String KEY_LOCATION = "location";
    protected final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";

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


    // Labels.
    protected String mLatitudeLabel;
    protected String mLongitudeLabel;
    protected String mLastUpdateTimeLabel;

    /**
     * Tracks the status of the location updates request. Value changes when the user presses the
     * Start Updates and Stop Updates buttons.
     */
    protected Boolean mRequestingLocationUpdates;

    /**
     * Time when the location was updated represented as a String.
     */
    protected String mLastUpdateTime;


    @Override
    public void onCreate() {

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


        super.onCreate();
    }


    /* ########################################    GPS ######################################################*/

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int conn_statut = 0;
        Intent i;
        if (null != intent && intent.getExtras() != null) {

            conn_statut = (int) intent.getExtras().get("connectionStatus");

            if (conn_statut == 1) {

                startLocationUpdates();
            } else if (conn_statut == 2) {
                Toast.makeText(getApplicationContext(), "erreur inconue activation gps", Toast.LENGTH_LONG).show();
            } else {
                onDestroy();
                System.exit(0);

            }
        } else {
            int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
            if (resultCode == ConnectionResult.SUCCESS) {

                //Toast.makeText(getApplicationContext(),"isGooglePlayServicesAvailable SUCCESS",Toast.LENGTH_LONG).show();

                mGoogleApiClient.connect();
            } else {

                GooglePlayServicesUtil.getErrorPendingIntent(resultCode, getApplicationContext(), RQS_GooglePlayServices);

            }


        }


        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        mGoogleApiClient.disconnect();
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
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
            if (ContextCompat.checkSelfPermission(Gpstrackerservice.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(Gpstrackerservice.this, "ACCESS FINE LOCATION PERMISISION GRANTED!",
                        Toast.LENGTH_SHORT).show();

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
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        Log.d(TAG, "onLocationChanged: "+latitude);
        Log.d(TAG, "onLocationChanged: "+longitude);
        Log.d(TAG, "onLocationChanged: accuracy "+location.getAccuracy());


        updateLocationUI();


        //Toast.makeText(getBaseContext(), "location_updated_message)",Toast.LENGTH_SHORT).show();
        Log.i("service", "location_updated_message");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {



        Intent i = new Intent(this, GpsresolverActivity.class);
        i.putExtra(GpsresolverActivity.CONNECT_RESULT_KEY, connectionResult);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    private void onConnectionFailed1(LocationSettingsResult locationSettingsResult){
        Intent i = new Intent(this, GpsresolverActivity.class);
        i.putExtra("locationSettingsResult", locationSettingsResult);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
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

                Toast.makeText(getBaseContext(), "Location is already on.", Toast.LENGTH_SHORT).show();
                startLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to" +
                        "upgrade location settings ");

                // Show the dialog by calling startResolutionForResult(), and check the result
                // in onActivityResult().
               // Toast.makeText(getBaseContext(), "Location dialog will be open", Toast.LENGTH_SHORT).show();
                //

                onConnectionFailed1(locationSettingsResult);

                //move to step 6 in onActivityResult to check what action user has taken on settings dialog
                //status.startResolutionForResult(getApplicationContext(), REQUEST_CHECK_SETTINGS);

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

            latitude=mCurrentLocation.getLatitude();
            longitude=mCurrentLocation.getLongitude();

            accuracy=mCurrentLocation.getAccuracy();
            // mLastUpdateTimeTextView.setText(String.format("%s: %s", mLastUpdateTimeLabel,mLastUpdateTime));

            //updateCityAndPincode(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude());
            String Utilisateur_code="";
            String Distributeur_code="";

            SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref", 0);
            if( pref1.getString("is_login", null).equals("ok")) {
                try{
                    Gson gson2 = new Gson();
                    String json2 = pref1.getString("User", "");
                    Type type = new TypeToken<JSONObject>() {}.getType();
                    final JSONObject user = gson2.fromJson(json2, type);
                    Utilisateur_code =user.getString("UTILISATEUR_CODE");
                    Distributeur_code=user.getString("DISTRIBUTEUR_CODE");
                }
                catch (Exception e){
                }
            }

            GpstrackerManager gpstrackerManager = new GpstrackerManager(getApplicationContext());
            Gpstracker gpstracker=new Gpstracker();
            gpstracker.setUTILISATEUR_CODE(Utilisateur_code.toString());
            gpstracker.setLATITUDE(latitude);
            gpstracker.setLONGITUDE(longitude);
            gpstracker.setDESCRIPTION(Distributeur_code);
            gpstracker.setVERSION("To_Insert");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            gpstracker.setTS(sdf.format(new Date()));

            //gpstrackerManager.add(gpstracker);
            //synchronisationGpstracker(getApplicationContext());

            Log.d(TAG, "updateLocationUI: updated");

            Log.i(TAG, "location_updated_message");
        }
    }


    /**
     *	This updateCityAndPincode method uses Geocoder api to map the latitude and longitude into city location or pincode.
     *	We can retrieve many details using this Geocoder class.
     *
     And yes the Geocoder will not work unless you have data connection or wifi connected to internet.
     */




    private void updateCityAndPincode(double latitude, double longitude)
    {
        try
        {
            /*Geocoder gcd = new Geocoder(FusedLocationWithSettingsDialog.this, Locale.getDefault());
            List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0)
            {

                tv_city.setText("City="+addresses.get(0).getLocality());
                tv_pincode.setText("Pincode="+addresses.get(0).getPostalCode());
                //  System.out.println(addresses.get(0).getLocality());
            }
            */
        }

        catch (Exception e)
        {
            Log.e(TAG,"exception:"+e.toString());
        }

    }




    int RQS_GooglePlayServices=0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
