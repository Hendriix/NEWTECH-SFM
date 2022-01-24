package com.newtech.newtech_sfm.ContratImage;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

import com.newtech.newtech_sfm.Activity.ClientActivity;
import com.newtech.newtech_sfm.BuildConfig;
import com.newtech.newtech_sfm.Metier.Choufouni;
import com.newtech.newtech_sfm.Metier.ChoufouniContrat;
import com.newtech.newtech_sfm.Metier.ChoufouniContratImage;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier_Manager.ChoufouniContratImageManager;
import com.newtech.newtech_sfm.Metier_Manager.ChoufouniContratManager;
import com.newtech.newtech_sfm.Metier_Manager.ChoufouniContratPullManager;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.Service.AlarmReceiver;
import com.newtech.newtech_sfm.Service.BlutDiscovery;
import com.newtech.newtech_sfm.Service.BlutoothConnctionService;
import com.newtech.newtech_sfm.Service.Gpstrackerservice;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ContratImageActivity extends AppCompatActivity implements ContratImagePresenter.ContratImageView{

    Button charger_rayon_btn;
    ImageView image_contrat1;

    private ContratImagePresenter presenter;
    private ProgressDialog progressDialog;
    public static String client_code;

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    public static final int PREVIEW_CONTRAT1 = 1;

    public int START_CAPTURE  = 0 ;
    public int CAMERA_CODE = 4;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";

    private Uri fileUri_contrat1;

    public static boolean contrat1_captured = false;

    private static final String TAG = com.newtech.newtech_sfm.ContratImage.ContratImageActivity.class.getSimpleName();
    Spinner_Choufouni_Contrat_Adapter spinner_choufouni_contrat_adapter;

    Spinner spinnerContratChoufouni;
    Button validerContrat;

    ScrollView data_found_layout;
    LinearLayout no_data_found_layout;

    ArrayList<Choufouni> choufounis = new ArrayList<Choufouni>();

    ChoufouniContratManager choufouniContratManager;
    ChoufouniContratPullManager choufouniContratPullManager;
    ChoufouniContratImageManager choufouniContratImageManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contrat_image_activity);
        setTitle("CONTRAT IMAGE");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinnerContratChoufouni = (Spinner) findViewById(R.id.contrat_spinner);
        validerContrat = findViewById(R.id.valider_contrat);
        data_found_layout = findViewById(R.id.scrollView_groupe);
        no_data_found_layout = findViewById(R.id.no_data_found_layout);

        choufouniContratManager =  new ChoufouniContratManager(this);
        choufouniContratImageManager =  new ChoufouniContratImageManager(this);
        choufouniContratPullManager = new ChoufouniContratPullManager(this);


        Intent intent = getIntent();
        if(intent!=null)
            client_code=intent.getStringExtra("CLIENT_CODE");

        validerContrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enregistrerContrat();
            }
        });
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {


        } else {
            requestCameraPermission();
        }

        image_contrat1 = (ImageView) findViewById(R.id.contrat_image1);
        charger_rayon_btn = findViewById(R.id.charger_rayon_btn);
        if(isDeviceSupportCamera()){
            charger_rayon_btn.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    captureImage1();
                }
            });
        }
        initProgressDialog();

        presenter = new ContratImagePresenter(this);
        presenter.getChoufouniContratList(getApplicationContext(),client_code);
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
                            ActivityCompat.requestPermissions(ContratImageActivity.this,
                                    new String[] {Manifest.permission.CAMERA}, CAMERA_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            stopService(new Intent(getApplicationContext(), BlutoothConnctionService.class));
                            stopService(new Intent(getApplicationContext(), BlutDiscovery.class));
                            Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0,
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_CODE)  {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "CAMERA GRANTED", Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(Client_Manager.this, "CAMERA already granted",
                    //Toast.LENGTH_SHORT).show();



                } else {



                    stopService(new Intent(this, BlutoothConnctionService.class));
                    stopService(new Intent(this, BlutDiscovery.class));
                    Intent intent = new Intent(this, AlarmReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
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
                stopService(new Intent(this, BlutoothConnctionService.class));
                stopService(new Intent(this, BlutDiscovery.class));
                Intent intent = new Intent(this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
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

    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    private void captureImage1() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        START_CAPTURE = 1;
        fileUri_contrat1 = FileProvider.getUriForFile(this,
                BuildConfig.APPLICATION_ID + ".provider",
                getOutputMediaFile(MEDIA_TYPE_IMAGE));

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri_contrat1);

        //Log.d(TAG, "captureImage: fileuuri1 "+fileUri_cin1.getPath().toString());
        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
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

        //Log.d(TAG, "getOutputMediaFile: path "+mediaStorageDir);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                //Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                //+ IMAGE_DIRECTORY_NAME + " directory");
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

    private void previewCapturedImage() {
        try {
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images

            options.inSampleSize = 8;

            Bitmap bitmap = BitmapFactory.decodeFile(fileUri_contrat1.getPath(), options);
            try {
                ExifInterface exif = new ExifInterface(fileUri_contrat1.getPath());
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                //Log.d("EXIF", "Exif: " + orientation);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                }
                else if (orientation == 3) {
                    matrix.postRotate(180);
                }
                else if (orientation == 8) {
                    matrix.postRotate(270);
                }
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true); // rotating bitmap
            }
            catch (Exception e) {

            }
            image_contrat1.setVisibility(View.VISIBLE);
            image_contrat1.setImageBitmap(bitmap);
            contrat1_captured = true;

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            // if the result is capturing Image
            case CAMERA_CAPTURE_IMAGE_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // successfully captured the image
                        // display it in image view
                        //Log.d(TAG, "onActivityResult: "+ "RESULT OK");
                        switch (START_CAPTURE){
                            case PREVIEW_CONTRAT1:
                                previewCapturedImage();
                                break;
                        }

                        break;
                    case Activity.RESULT_CANCELED:
                        // user cancelled Image capture
                        Toast.makeText(getApplicationContext(),
                                "Capture Image annulee", Toast.LENGTH_SHORT)
                                .show();
                        break;
                    default:
                        // failed to capture image
                        Toast.makeText(getApplicationContext(),
                                "Désolé : Erreur capture d'image", Toast.LENGTH_SHORT)
                                .show();
                        break;
                }
                break;

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }


    @Override
    public void showSuccess(ArrayList<ChoufouniContrat> choufouniContrats) {
        no_data_found_layout.setVisibility(View.GONE);
        data_found_layout.setVisibility(View.VISIBLE);
        validerContrat.setVisibility(View.VISIBLE);
        progressDialog.dismiss();
        spinner_choufouni_contrat_adapter = new Spinner_Choufouni_Contrat_Adapter(this,android.R.layout.simple_spinner_item,choufouniContrats);
        spinnerContratChoufouni.setAdapter(spinner_choufouni_contrat_adapter);
    }

    @Override
    public void showError(String message) {
        progressDialog.dismiss();
        showMessage(message);
    }

    @Override
    public void showEmpty(String message) {
        progressDialog.dismiss();
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

    private void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    private void enregistrerContrat(){

        if(checkValidation()){

            String encoded_image_contrat1 = "";
            DateFormat df_code = new SimpleDateFormat("yyMMddHHmmss");
            String date_code = df_code.format(Calendar.getInstance().getTime());

            DateFormat df_creation = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date_creation = df_creation.format(Calendar.getInstance().getTime());

            ChoufouniContrat choufouniContrat = (ChoufouniContrat) spinner_choufouni_contrat_adapter.getItem(spinnerContratChoufouni.getSelectedItemPosition()) ;
            String IMAGE_CODE = date_code;
            try{
                IMAGE_CODE = choufouniContrat.getCHOUFOUNI_CODE()+choufouniContrat.getCLIENT_CODE()+date_code;
            }catch (NullPointerException n){

            }

            int distance = 0;
            try{
                distance = getDistance(ClientActivity.clientCourant,ClientActivity.gps_latitude,ClientActivity.gps_longitude);
            }catch(ArithmeticException a){
            }


            encoded_image_contrat1 = getEncodedImage();

            ChoufouniContratImage choufouniContratImage1 = new ChoufouniContratImage();
            choufouniContratImage1.setCHOUFOUNI_CONTRAT_CODE(choufouniContrat.getCHOUFOUNI_CONTRAT_CODE());
            choufouniContratImage1.setIMAGE_CODE(IMAGE_CODE);
            choufouniContratImage1.setIMAGE(encoded_image_contrat1);
            choufouniContratImage1.setTYPE_CODE("TYPE");
            choufouniContratImage1.setCATEGORIE_CODE("CATEGORIE");
            choufouniContratImage1.setSTATUT_CODE("STATUT");
            choufouniContratImage1.setCOMMENTAIRE("to_insert");
            choufouniContratImage1.setDATE_CREATION(date_creation);
            choufouniContratImage1.setVERSION("to_insert");
            choufouniContratImage1.setGPS_LATITUDE(String.valueOf(Gpstrackerservice.latitude));
            choufouniContratImage1.setGPS_LONGITUDE(String.valueOf(Gpstrackerservice.longitude));

            choufouniContratImageManager.add(choufouniContratImage1);

            ChoufouniContratManager.synchronisationChoufouniContrat(this);
            ChoufouniContratImageManager.synchronisationChoufouniContratImage(this);

            onBackPressed();

        }else{
            Toast.makeText(this, "BAD", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, ClientActivity.class);
        startActivity(i);
        finish();
    }

    private boolean checkValidation() {
        boolean result = true;


        ChoufouniContrat choufouniContrat = (ChoufouniContrat) spinner_choufouni_contrat_adapter.getItem(spinnerContratChoufouni.getSelectedItemPosition());
        String strchoufouni=choufouniContrat.getTYPE_CODE();
        if(TextUtils.isEmpty(strchoufouni) || TextUtils.equals(strchoufouni,"CHOUFOUNI")){
            ((TextView)spinnerContratChoufouni.getChildAt(0)).setError("ce champs est obligatoire");
            result = false;
        }
        if(contrat1_captured == false){
            result = false;
            Toast.makeText(this, "IL VOUS MANQUE L'IMAGE 1", Toast.LENGTH_SHORT).show();
            return result;
        }

        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public String getEncodedImage(){

        String encodeedImage = "";

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //Log.d(TAG, "getEncodedImage: "+bitmapDrawable.getByteCount());
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inSampleSize = 8;
        Bitmap bitmap = BitmapFactory.decodeFile(fileUri_contrat1.getPath(),
                options);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] byteArray = byteArrayOutputStream .toByteArray();
        encodeedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return encodeedImage;
    }

    public int getDistance(Client client, double latitude, double longitude){


        double positionLatitude = latitude;
        double positionLongitude = longitude;

        float [] distance = new float[1];
        distance[0]=0;

        double clientLatitue = Double.parseDouble(client.getGPS_LATITUDE().replace(",",".")) ;
        double clientLongitude = Double.parseDouble(client.getGPS_LONGITUDE().replace(",","."));

        Location.distanceBetween(positionLatitude,positionLongitude,clientLatitue,clientLongitude,distance);

        return (int)distance[0];
    }
}
