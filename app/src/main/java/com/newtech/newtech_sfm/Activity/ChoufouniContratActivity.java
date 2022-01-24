package com.newtech.newtech_sfm.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.Log;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newtech.newtech_sfm.BuildConfig;
import com.newtech.newtech_sfm.Configuration.Spinner_Choufouni_Adapter;
import com.newtech.newtech_sfm.Metier.Choufouni;
import com.newtech.newtech_sfm.Metier.ChoufouniContrat;
import com.newtech.newtech_sfm.Metier.ChoufouniContratImage;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier_Manager.ChoufouniContratImageManager;
import com.newtech.newtech_sfm.Metier_Manager.ChoufouniContratManager;
import com.newtech.newtech_sfm.Metier_Manager.ChoufouniContratPullManager;
import com.newtech.newtech_sfm.Metier_Manager.ChoufouniManager;
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

public class ChoufouniContratActivity extends AppCompatActivity {

    Button charger_contrat_btn;
    Button charger_rayon_btn;
    ImageView image_contrat1;
    ImageView image_contrat2;

    public String SOURCE="";
    String CLIENT_CODE ="";
    String VENDEUR_CODE="";
    String DISTRIBUTEUR_CODE="";

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    public static final int PREVIEW_CONTRAT1 = 1;
    public static final int PREVIEW_CONTRAT2 = 2;

    public int START_CAPTURE  = 0 ;
    public int CAMERA_CODE = 4;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";

    private Uri fileUri_contrat1;
    private Uri fileUri_contrat2;

    public static boolean contrat1_captured = false;
    public static boolean contrat2_captured = false;

    private static final String TAG = ChoufouniContratActivity.class.getSimpleName();
    Spinner_Choufouni_Adapter spinner_choufouni_adapter;

    Spinner spinnerChoufouni;
    Button validerContrat;
    ArrayList<Choufouni> choufounis = new ArrayList<>();

    ChoufouniContratManager choufouniContratManager;
    ChoufouniContratPullManager choufouniContratPullManager;
    ChoufouniContratImageManager choufouniContratImageManager;

    ScrollView data_found_layout;
    LinearLayout no_data_found_layout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choufouni_contrat_activity);

        Toolbar toolbar = findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);

        spinnerChoufouni = findViewById(R.id.choufouni_spinner);
        validerContrat = findViewById(R.id.valider_contrat);
        charger_contrat_btn = findViewById(R.id.charger_contrat_btn);
        image_contrat1 =  findViewById(R.id.contrat_image1);
        charger_rayon_btn = findViewById(R.id.charger_rayon_btn);
        image_contrat2 =  findViewById(R.id.contrat_image2);
        data_found_layout = findViewById(R.id.scrollView_groupe);
        no_data_found_layout = findViewById(R.id.no_data_found_layout);


        choufouniContratManager =  new ChoufouniContratManager(this);
        choufouniContratImageManager =  new ChoufouniContratImageManager(this);
        choufouniContratPullManager = new ChoufouniContratPullManager(this);

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref", 0);
        if( pref1.getString("is_login", null).equals("ok")) {
            try{
                Gson gson2 = new Gson();
                String json2 = pref1.getString("User", "");
                java.lang.reflect.Type type = new TypeToken<JSONObject>() {}.getType();
                final JSONObject user = gson2.fromJson(json2, type);
                VENDEUR_CODE =user.getString("UTILISATEUR_CODE");
                DISTRIBUTEUR_CODE=user.getString("DISTRIBUTEUR_CODE");
            }
            catch (Exception e){
            }
        }

        Intent intent = getIntent();
        if(intent!=null)
        CLIENT_CODE=intent.getStringExtra("CLIENT_CODE");

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
        } else {
            requestCameraPermission();
        }


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ChoufouniManager choufouniManager = new ChoufouniManager(getApplicationContext());
        Choufouni choufouni = new Choufouni();
        choufouni.setCHOUFOUNI_CODE("CHOUFOUNI");
        choufouni.setCHOUFOUNI_NOM("CHOUFOUNI");

        choufounis = choufouniManager.getListActif();
        choufounis.add(0,choufouni);



        if(!choufouniContratManager.getChoufouniContrat(this,CLIENT_CODE)){
            no_data_found_layout.setVisibility(View.GONE);
            data_found_layout.setVisibility(View.VISIBLE);
            validerContrat.setVisibility(View.VISIBLE);
        }else{

            showMessage("CONTRAT EXISTE DEJA !");
        }

        if(isDeviceSupportCamera()){
            charger_contrat_btn.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    captureImage(PREVIEW_CONTRAT1);
                }
            });

            charger_rayon_btn.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    captureImage(PREVIEW_CONTRAT2);
                }
            });
        }

        spinner_choufouni_adapter = new Spinner_Choufouni_Adapter(this,android.R.layout.simple_spinner_item,choufounis);
        spinnerChoufouni.setAdapter(spinner_choufouni_adapter);

        validerContrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enregistrerContrat();
            }
        });


        setTitle("CONTRAT CHOUFOUNI");

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
                            ActivityCompat.requestPermissions(ChoufouniContratActivity.this,
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "CAMERA GRANTED", Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(Client_Manager.this, "CAMERA already granted",
                    //Toast.LENGTH_SHORT).show();


                } else {


                    stopService(new Intent(ChoufouniContratActivity.this, BlutoothConnctionService.class));
                    stopService(new Intent(ChoufouniContratActivity.this, BlutDiscovery.class));
                    Intent intent = new Intent(ChoufouniContratActivity.this, AlarmReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(ChoufouniContratActivity.this, 0,
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
                stopService(new Intent(ChoufouniContratActivity.this, BlutoothConnctionService.class));
                stopService(new Intent(ChoufouniContratActivity.this, BlutDiscovery.class));
                Intent intent = new Intent(ChoufouniContratActivity.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(ChoufouniContratActivity.this, 0,
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

    private void captureImage(int start_capture) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        START_CAPTURE = start_capture;
        if(start_capture == PREVIEW_CONTRAT1){


            fileUri_contrat1 = FileProvider.getUriForFile(ChoufouniContratActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    getOutputMediaFile(MEDIA_TYPE_IMAGE));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri_contrat1);

        }else{

            fileUri_contrat2 = FileProvider.getUriForFile(ChoufouniContratActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    getOutputMediaFile(MEDIA_TYPE_IMAGE));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri_contrat2);
        }
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
                        //Log.d(TAG, "onActivityResult: "+ "RESULT OK");
                        switch (START_CAPTURE) {
                            case PREVIEW_CONTRAT1:
                                //decodeFile(new File(fileUri_contrat1.getPath()), PREVIEW_CONTRAT1);
                                previewCapturedImage(fileUri_contrat1, PREVIEW_CONTRAT1);
                                break;

                            case PREVIEW_CONTRAT2:
                                //decodeFile(new File(fileUri_contrat2.getPath()), PREVIEW_CONTRAT2);
                                previewCapturedImage(fileUri_contrat2, PREVIEW_CONTRAT2);
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

    private boolean checkValidation() {
        boolean result = true;


        Choufouni choufouni = (Choufouni) spinner_choufouni_adapter.getItem(spinnerChoufouni.getSelectedItemPosition());
        String strchoufouni=choufouni .getTYPE_CODE();
        if(TextUtils.isEmpty(strchoufouni) || TextUtils.equals(strchoufouni,"CHOUFOUNI")){
            ((TextView)spinnerChoufouni.getChildAt(0)).setError("ce champs est obligatoire");
            result = false;
        }
        if(contrat1_captured == false){
            result = false;
            Toast.makeText(this, "IL VOUS MANQUE L'IMAGE 1", Toast.LENGTH_SHORT).show();
            return result;
        }
        if(contrat2_captured == false){
            result = false;
            Toast.makeText(this, "IL VOUS MANQUE L'IMAGE 2", Toast.LENGTH_SHORT).show();
            return result;
        }

        return result;
    }

    private void enregistrerContrat(){

        if(checkValidation()){

            String encoded_image_contrat1 = "";
            String encoded_image_contrat2 = "";
            DateFormat df_code = new SimpleDateFormat("yyMMddHHmmss");
            String date_code = df_code.format(Calendar.getInstance().getTime());


            DateFormat df_creation = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date_creation = df_creation.format(Calendar.getInstance().getTime());

            Choufouni choufouni = (Choufouni) spinner_choufouni_adapter.getItem(spinnerChoufouni.getSelectedItemPosition()) ;
            encoded_image_contrat1 = getEncodedImage(fileUri_contrat1,PREVIEW_CONTRAT1);
            encoded_image_contrat2 = getEncodedImage(fileUri_contrat2,PREVIEW_CONTRAT2);

            Log.d(TAG, "enregistrerContrat: VENDEUR CODE "+VENDEUR_CODE);

            String CHOUFOUNI_CONTRAT_CODE = choufouni.getCHOUFOUNI_CODE()+CLIENT_CODE+date_code;

            int distance = 0;
            try{
                distance = getDistance(ClientActivity.clientCourant,ClientActivity.gps_latitude,ClientActivity.gps_longitude);
            }catch(ArithmeticException a){
            }


            Toast.makeText(this, "GOOD", Toast.LENGTH_SHORT).show();
            ChoufouniContrat choufouniContrat = new ChoufouniContrat();
            choufouniContrat.setCHOUFOUNI_CONTRAT_CODE(CHOUFOUNI_CONTRAT_CODE);
            choufouniContrat.setCHOUFOUNI_CODE(choufouni.getCHOUFOUNI_CODE());
            choufouniContrat.setDISTRIBUTEUR_CODE(DISTRIBUTEUR_CODE);
            choufouniContrat.setUTILISATEUR_CODE(VENDEUR_CODE);
            choufouniContrat.setCLIENT_CODE(CLIENT_CODE);
            choufouniContrat.setDATE_CONTRAT(date_creation);
            choufouniContrat.setTYPE_CODE("TYPE");
            choufouniContrat.setCATEGORIE_CODE("CATEGORIE");
            choufouniContrat.setSTATUT_CODE("STATUT");
            choufouniContrat.setREMISE(0);
            choufouniContrat.setSOLDE(0);
            choufouniContrat.setCREATEUR_CODE("HAVD0001");
            choufouniContrat.setDATE_CREATION(date_creation);
            choufouniContrat.setCOMMENTAIRE("to_insert");
            choufouniContrat.setGPS_LATITUDE(String.valueOf(ClientActivity.gps_latitude));
            choufouniContrat.setGPS_LONGITUDE(String.valueOf(ClientActivity.gps_longitude));
            choufouniContrat.setDISTANCE(distance);

            ChoufouniContratImage choufouniContratImage1 = new ChoufouniContratImage();
            choufouniContratImage1.setCHOUFOUNI_CONTRAT_CODE(CHOUFOUNI_CONTRAT_CODE);
            choufouniContratImage1.setIMAGE_CODE(CHOUFOUNI_CONTRAT_CODE+"1");
            choufouniContratImage1.setIMAGE(encoded_image_contrat1);
            choufouniContratImage1.setTYPE_CODE("TYPE");
            choufouniContratImage1.setCATEGORIE_CODE("CATEGORIE");
            choufouniContratImage1.setSTATUT_CODE("STATUT");
            choufouniContratImage1.setCOMMENTAIRE("to_insert");
            choufouniContratImage1.setDATE_CREATION(date_creation);
            choufouniContratImage1.setCREATEUR_CODE(VENDEUR_CODE);
            choufouniContratImage1.setVERSION("to_insert");
            choufouniContratImage1.setGPS_LATITUDE(String.valueOf(ClientActivity.gps_latitude));
            choufouniContratImage1.setGPS_LONGITUDE(String.valueOf(ClientActivity.gps_longitude));
            choufouniContratImage1.setDISTANCE(distance);

            ChoufouniContratImage choufouniContratImage2 = new ChoufouniContratImage();
            choufouniContratImage2.setCHOUFOUNI_CONTRAT_CODE(CHOUFOUNI_CONTRAT_CODE);
            choufouniContratImage2.setIMAGE_CODE(CHOUFOUNI_CONTRAT_CODE+"2");
            choufouniContratImage2.setIMAGE(encoded_image_contrat2);
            choufouniContratImage2.setTYPE_CODE("TYPE");
            choufouniContratImage2.setCATEGORIE_CODE("CATEGORIE");
            choufouniContratImage2.setSTATUT_CODE("STATUT");
            choufouniContratImage2.setCOMMENTAIRE("to_insert");
            choufouniContratImage2.setDATE_CREATION(date_creation);
            choufouniContratImage2.setCREATEUR_CODE(VENDEUR_CODE);
            choufouniContratImage2.setVERSION("to_insert");
            choufouniContratImage2.setGPS_LATITUDE(String.valueOf(ClientActivity.gps_latitude));
            choufouniContratImage2.setGPS_LONGITUDE(String.valueOf(ClientActivity.gps_longitude));
            choufouniContratImage2.setDISTANCE(distance);

            Log.d(TAG, "choufouniContrat: 0 "+choufouniContrat.toString());
            Log.d(TAG, "enregistrerContrat: 1 "+choufouniContratImage1.toString());
            Log.d(TAG, "enregistrerContrat: 2 "+choufouniContratImage2.toString());

           if(choufouniContratManager.exist(CHOUFOUNI_CONTRAT_CODE) || choufouniContratPullManager.exist(CHOUFOUNI_CONTRAT_CODE)){

                Toast.makeText(this, "CONTRAT EXISTE DEJA", Toast.LENGTH_SHORT).show();

            }else{
                choufouniContratManager.add(choufouniContrat);
                choufouniContratImageManager.add(choufouniContratImage1);
                choufouniContratImageManager.add(choufouniContratImage2);

                ChoufouniContratManager.synchronisationChoufouniContrat(this);
                ChoufouniContratImageManager.synchronisationChoufouniContratImage(this);

                onBackPressed();
            }




        }else{
            Toast.makeText(this, "BAD", Toast.LENGTH_SHORT).show();
        }
    }

    public String getEncodedImage(Uri fileUri, int preview_captured){

        String encodeedImage = "";

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //Log.d(TAG, "getEncodedImage: "+bitmapDrawable.getByteCount());
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inSampleSize = 8;
        Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                options);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] byteArray = byteArrayOutputStream .toByteArray();
        encodeedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return encodeedImage;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =  new Intent(ChoufouniContratActivity.this,ClientActivity.class);
        startActivity(intent);
        finish();
    }

    private void previewCapturedImage(Uri fileUri, int preview_captured) {
        try {
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
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
                Log.d(TAG, "previewCapturedImage: "+e.getMessage());
            }

            if(preview_captured == PREVIEW_CONTRAT1){
                image_contrat1.setVisibility(View.VISIBLE);
                image_contrat1.setImageBitmap(bitmap);
                contrat1_captured = true;
            }else{
                image_contrat2.setVisibility(View.VISIBLE);
                image_contrat2.setImageBitmap(bitmap);
                contrat2_captured = true;
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    public int getDistance(Client client,double latitude,double longitude){


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
