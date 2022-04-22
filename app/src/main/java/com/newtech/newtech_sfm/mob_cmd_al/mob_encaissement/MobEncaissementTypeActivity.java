package com.newtech.newtech_sfm.mob_cmd_al.mob_encaissement;

import static java.lang.Math.abs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.newtech.newtech_sfm.Activity.EncaissementActivity;
import com.newtech.newtech_sfm.BuildConfig;
import com.newtech.newtech_sfm.Configuration.Spinner_Banque_Adapter;
import com.newtech.newtech_sfm.Metier.Encaissement;
import com.newtech.newtech_sfm.Metier.Menu;
import com.newtech.newtech_sfm.Metier.User;
import com.newtech.newtech_sfm.Metier_Manager.CreditManager;
import com.newtech.newtech_sfm.Metier_Manager.EncaissementManager;
import com.newtech.newtech_sfm.Metier_Manager.UserManager;
import com.newtech.newtech_sfm.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by TONPC on 02/08/2017.
 */

public class MobEncaissementTypeActivity extends AppCompatActivity {


    ListView mListView;
    Menu[] Rapports3={
            new Menu("M1","ENCAISSEMENT ESPECE", "ESPECE" ,""),
            new Menu("M2","ENCAISSEMENT CHEQUE", "CHEQUE" ,""),
            new Menu("M3","ENCAISSEMENT CREDIT", "CREDIT" ,"")
    };

    static String banque = "";
    Spinner spinnerBanque;
    Spinner_Banque_Adapter spinner_banque_adapter;
    UserManager userManager ;
    User utilisateur;
    CreditManager creditManager;
    ImageView cheque_image;
    Button charger_cheque_btn;
    DatePickerDialog date_echeance_picker;
    Calendar calendar_echeance;
    public static String commande_source="";
    // Activity request codes
    public static final int PREVIEW_CHEQUE = 1;
    private static boolean cheque_captured = false;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private static final String TAG = MobEncaissementTypeActivity.class.getSimpleName();

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    private Uri fileUri; // file url to store image/video

    TextView numero_cheque;
    //TextView numero_compte;
    TextView valeur_encaissement_cheque;
    TextView valeur_encaissement_espece;
    TextView valeur_encaissement_credit;
    TextView valeur_plafond;
    TextView valeur_echeance;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encaissement_types);
        setTitle("ENCAISSEMENT");


        userManager = new UserManager(getApplicationContext());
        utilisateur = userManager.get();
        creditManager = new CreditManager(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView = (ListView) findViewById(R.id.list_encaissement_type);


        ArrayAdapter<Menu> mAdapterRapport = new ArrayAdapter<Menu>(MobEncaissementTypeActivity.this,android.R.layout.simple_list_item_1, Rapports3);
        mListView.setAdapter(mAdapterRapport);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Menu ClickedMenu=Rapports3[i];

                if(ClickedMenu.getLIEN().toString().equals("ESPECE")){

                    SimpleDateFormat df2 = new SimpleDateFormat("yyMMddHHmmss");
                    final String date_encaissement = df2.format(new Date());

                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    final String date_encaissement1=df.format(Calendar.getInstance().getTime());


                    final Dialog dialog = new Dialog(view.getContext());
                    dialog.setContentView(R.layout.alert_encaissement_espece);
                    dialog.setTitle("ENCAISSEMENT ESPECE");
                    dialog.setCanceledOnTouchOutside(false);
                    Button terminer = (Button) dialog.findViewById(R.id.terminer_ee);
                    Button annuler = (Button) dialog.findViewById(R.id.annuler_ee);
                    valeur_encaissement_espece = (TextView) dialog.findViewById(R.id.valeur_ee);

                    if(commande_source.equals("Livraison")){
                        valeur_encaissement_espece.setText(String.valueOf(com.newtech.newtech_sfm.Activity.EncaissementActivity.livraison.getMONTANT_NET()-getValEncaissement(com.newtech.newtech_sfm.Activity.EncaissementActivity.encaissements)));
                    }else{
                        valeur_encaissement_espece.setText(String.valueOf(com.newtech.newtech_sfm.Activity.EncaissementActivity.commande.getMONTANT_NET()-getValEncaissement(com.newtech.newtech_sfm.Activity.EncaissementActivity.encaissements)));

                    }


                    terminer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            double montant=0;
                            //Toast.makeText(getApplicationContext(),"ENCAISSEMENT ESPECE",Toast.LENGTH_SHORT).show();
                            EncaissementManager encaissementManager = new EncaissementManager(getApplicationContext());

                            if(checkMontantEspece()){

                                montant = getNumberRounded(Float.parseFloat(String.valueOf(valeur_encaissement_espece.getText())));
                                double somme = montant+ com.newtech.newtech_sfm.Activity.EncaissementActivity.payecommande;
                                Log.d("encaissement", "onClick montant: "+montant);
                                Log.d("encaissement", "onClick payecommande: "+ com.newtech.newtech_sfm.Activity.EncaissementActivity.payecommande);
                                Log.d("encaissement", "onClick somme: "+somme);
                                Encaissement encaissement = new Encaissement();
                                if(commande_source.equals("Livraison")){
                                     encaissement = new Encaissement(utilisateur, com.newtech.newtech_sfm.Activity.EncaissementActivity.livraison,"ESPECE",montant);
                                }else{
                                    encaissement = new Encaissement(utilisateur, com.newtech.newtech_sfm.Activity.EncaissementActivity.commande,"ESPECE",montant);
                                }
                                Log.d(TAG, "onClick: encaissement espece "+encaissement.toString());
                                com.newtech.newtech_sfm.Activity.EncaissementActivity.encaissements.add(encaissement);
                                dialog.dismiss();
                                Intent intent = new Intent(MobEncaissementTypeActivity.this , com.newtech.newtech_sfm.Activity.EncaissementActivity.class);
                                startActivity(intent);
                                finish();

                            }else{
                                Toast.makeText(getApplicationContext(),"VERIFIER LES CHAMPS ET REESSAYER",Toast.LENGTH_SHORT).show();
                            }

                        }

                    });

                    annuler.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }

                    });

                    dialog.show();

                }else if(ClickedMenu.getLIEN().toString().equals("CHEQUE")){



                    SimpleDateFormat df2 = new SimpleDateFormat("yyMMddHHmmss");
                    final String date_encaissement = df2.format(new Date());
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    final String date_encaissement1=df.format(Calendar.getInstance().getTime());


                    final Dialog dialog = new Dialog(view.getContext());
                    dialog.setContentView(R.layout.alert_encaissement_cheque);
                    dialog.setTitle("ENCAISSEMENT CHEQUE");
                    dialog.setCanceledOnTouchOutside(false);
                    Button terminer = (Button) dialog.findViewById(R.id.terminer_ec);
                    Button annuler = (Button) dialog.findViewById(R.id.annuler_ec);

                    cheque_image =  dialog.findViewById(R.id.cheque_image);
                    charger_cheque_btn = dialog.findViewById(R.id.charger_cheque_btn);

                    if(isDeviceSupportCamera()){
                        charger_cheque_btn.setOnClickListener(new View.OnClickListener(){

                            @Override
                            public void onClick(View v) {
                                captureImage();
                            }
                        });
                    }

                    spinnerBanque = (Spinner) dialog.findViewById(R.id.nombanque);


                    ArrayList<String> items= new ArrayList<>() ;
                    items.add("Banque populaire");
                    items.add("Attijariwafa bank");
                    items.add("BMCE Bank");
                    items.add("Société générale Maroc");
                    items.add("BMCI");
                    items.add("Crédit agricole du Maroc");
                    items.add("Crédit du Maroc");
                    items.add("CIH Bank");
                    items.add("CFG Bank");

                    spinner_banque_adapter = new Spinner_Banque_Adapter(MobEncaissementTypeActivity.this,items);

                    spinnerBanque.setAdapter(spinner_banque_adapter);


                    numero_cheque= (TextView) dialog.findViewById(R.id.valeur_num_cheque);
                    //numero_compte= (TextView) dialog.findViewById(R.id.valeur_num_compte);
                    valeur_encaissement_cheque = (TextView) dialog.findViewById(R.id.valeur_ec);
                    if(commande_source.equals("Livraison")){
                        valeur_encaissement_cheque.setText(String.valueOf(getNumberRounded(com.newtech.newtech_sfm.Activity.EncaissementActivity.livraison.getMONTANT_NET()-getValEncaissement(com.newtech.newtech_sfm.Activity.EncaissementActivity.encaissements))));
                    }else{
                        valeur_encaissement_cheque.setText(String.valueOf(getNumberRounded(com.newtech.newtech_sfm.Activity.EncaissementActivity.commande.getMONTANT_NET()-getValEncaissement(com.newtech.newtech_sfm.Activity.EncaissementActivity.encaissements))));
                    }
                    spinnerBanque.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            banque = spinner_banque_adapter.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    terminer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            double montant=0;
                            String num_compte="";
                            String num_cheque = "";



                            if(checkNumCheque() && checkMontantCheque() && checkImage()){

                                String encoded_image_cheque = "";
                                encoded_image_cheque = getEncodedImage(fileUri);

                                montant = getNumberRounded(Float.parseFloat(String.valueOf(valeur_encaissement_cheque.getText())));
                                //num_compte = String.valueOf(numero_compte.getText());
                                num_cheque = String.valueOf(numero_cheque.getText());

                                //Toast.makeText(getApplicationContext(),"ENCAISSEMENT CHEQUE",Toast.LENGTH_SHORT).show();
                                //final Encaissement encaissement = new Encaissement(utilisateur,EncaissementActivity.commande,"CHEQUE",montant,banque,num_cheque,"DEFAULT");
                                Encaissement encaissement = new Encaissement();
                                if(commande_source.equals("Livraison")){
                                    encaissement = new Encaissement(utilisateur, com.newtech.newtech_sfm.Activity.EncaissementActivity.commande,"CHEQUE",montant,banque,num_cheque,"DEFAULT",encoded_image_cheque);

                                }else{
                                    encaissement = new Encaissement(utilisateur, com.newtech.newtech_sfm.Activity.EncaissementActivity.commande,"CHEQUE",montant,banque,num_cheque,"DEFAULT",encoded_image_cheque);

                                }

                                Log.d(TAG, "onClick: encaissement cheque "+encaissement.toString());
                                com.newtech.newtech_sfm.Activity.EncaissementActivity.encaissements.add(encaissement);
                                dialog.dismiss();
                                Intent intent = new Intent(MobEncaissementTypeActivity.this , com.newtech.newtech_sfm.Activity.EncaissementActivity.class);
                                startActivity(intent);
                                finish();

                            }else{
                                Toast.makeText(getApplicationContext(),"VERIFIER LES CHAMPS ET REESSAYER",Toast.LENGTH_SHORT).show();
                            }


                        }

                    });

                    annuler.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }

                    });

                    //dialog.show();
                    Toast.makeText(MobEncaissementTypeActivity.this,"En cours de développement",Toast.LENGTH_LONG).show();

                }else if(ClickedMenu.getLIEN().toString().equals("CREDIT")){

                    SimpleDateFormat df2 = new SimpleDateFormat("yyMMddHHmmss");
                    final String date_encaissement = df2.format(new Date());

                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    final String date_encaissement1=df.format(Calendar.getInstance().getTime());

                    Double montant_credit = creditManager.getSumResteCredit() + getValEncaissementCredit(com.newtech.newtech_sfm.Activity.EncaissementActivity.encaissements);

                    final Dialog dialog = new Dialog(view.getContext());
                    dialog.setContentView(R.layout.alert_encaissement_credit);
                    dialog.setTitle("ENCAISSEMENT CREDIT");
                    dialog.setCanceledOnTouchOutside(false);
                    Button terminer = (Button) dialog.findViewById(R.id.terminer_ecr);
                    Button annuler = (Button) dialog.findViewById(R.id.annuler_ecr);
                    Button echeance = (Button) dialog.findViewById(R.id.choisir_date);

                    valeur_encaissement_credit = (TextView) dialog.findViewById(R.id.valeur_ecr);
                    valeur_plafond = (TextView) dialog.findViewById(R.id.valeur_plafond);
                    valeur_echeance = (TextView) dialog.findViewById(R.id.valeur_echeance);

                    valeur_plafond.setEnabled(false);
                    valeur_echeance.setEnabled(false);
                    //valeur_plafond.setText(String.valueOf(0 - montant_credit ));
                    valeur_plafond.setText(String.valueOf(utilisateur.getPLAFOND()- montant_credit ));
                    if(commande_source.equals("Livraison")){
                        valeur_encaissement_credit.setText(String.valueOf(getNumberRounded(com.newtech.newtech_sfm.Activity.EncaissementActivity.livraison.getMONTANT_NET()-getValEncaissement(com.newtech.newtech_sfm.Activity.EncaissementActivity.encaissements))));
                    }else{
                        valeur_encaissement_credit.setText(String.valueOf(getNumberRounded(com.newtech.newtech_sfm.Activity.EncaissementActivity.commande.getMONTANT_NET()-getValEncaissement(com.newtech.newtech_sfm.Activity.EncaissementActivity.encaissements))));
                    }

                    echeance.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            calendar_echeance = Calendar.getInstance();
                            int day = calendar_echeance.get(Calendar.DAY_OF_MONTH);
                            int month = calendar_echeance.get(Calendar.MONTH);
                            int year = calendar_echeance.get(Calendar.YEAR);

                            date_echeance_picker = new DatePickerDialog(MobEncaissementTypeActivity.this, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                    valeur_echeance.setText(i+"-"+(i1+01)+"-"+i2);
                                }
                            },year,month,day);

                            date_echeance_picker.show();
                        }
                    });


                    terminer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            double montant=0;
                            String credit_echeance = String.valueOf(valeur_echeance.getText());
                            //Toast.makeText(getApplicationContext(),"ENCAISSEMENT ESPECE",Toast.LENGTH_SHORT).show();
                            EncaissementManager encaissementManager = new EncaissementManager(getApplicationContext());

                            if(checkMontantCredit() && checkCreditEcheance()){

                                montant = getNumberRounded(Float.parseFloat(String.valueOf(valeur_encaissement_credit.getText())));
                                double somme = montant+ com.newtech.newtech_sfm.Activity.EncaissementActivity.payecommande;
                                Log.d("encaissement", "onClick montant: "+montant);
                                Log.d("encaissement", "onClick payecommande: "+ com.newtech.newtech_sfm.Activity.EncaissementActivity.payecommande);
                                Log.d("encaissement", "onClick somme: "+somme);

                                //final Credit credit = new Credit(utilisateur,EncaissementActivity.commande,"CREDIT",montant);
                                //EncaissementActivity.credits.add(credit);

                                //final Encaissement encaissement = new Encaissement(utilisateur,EncaissementActivity.commande,"CREDIT",montant,0,credit_echeance);

                                Encaissement encaissement = new Encaissement();
                                if(commande_source.equals("Livraison")){
                                    encaissement = new Encaissement(utilisateur, com.newtech.newtech_sfm.Activity.EncaissementActivity.livraison,"CREDIT",montant,0,credit_echeance);

                                }else{
                                    encaissement = new Encaissement(utilisateur, com.newtech.newtech_sfm.Activity.EncaissementActivity.commande,"CREDIT",montant,0,credit_echeance);

                                }
                                Log.d(TAG, "onClick: encaissement credit "+encaissement.toString());
                                com.newtech.newtech_sfm.Activity.EncaissementActivity.encaissements.add(encaissement);

                                dialog.dismiss();
                                Intent intent = new Intent(MobEncaissementTypeActivity.this , com.newtech.newtech_sfm.Activity.EncaissementActivity.class);
                                startActivity(intent);
                                finish();

                            }else{
                                Toast.makeText(getApplicationContext(),"VERIFIER LES CHAMPS ET REESSAYER",Toast.LENGTH_SHORT).show();
                            }

                        }

                    });

                    annuler.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }

                    });

                    dialog.show();

                }else{
                    Toast.makeText(getApplicationContext(),"ENCAISSEMENT INCONNU",Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(RapportActivity.this,ClickedMenu.getLIEN().toString()+getApplicationContext().getPackageName().toString() , Toast.LENGTH_LONG).show();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            case R.id.logout:
                /*SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(MobEncaissementTypeActivity.this, AuthActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();*/
                return true;

            case R.id.option2:
                /*Intent intt = new Intent(MobEncaissementTypeActivity.this, PrintActivity2.class);
                intt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intt);
                finish();*/
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(MobEncaissementTypeActivity.this, com.newtech.newtech_sfm.Activity.EncaissementActivity.class);
        startActivity(i);
        finish();
    }

    public static Boolean isValidFloat(String value) {
        try {
            Double val = Double.valueOf(value);
            if (val != null)
                return true;
            else
                return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Boolean isValidInteger(String value) {
        try {
            Integer val = Integer.valueOf(value);
            if (val != null)
                return true;
            else
                return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public double getNumberRounded(double nombre){

        double number = nombre;
        int decimalsToConsider = 2;
        BigDecimal bigDecimal = new BigDecimal(number);
        BigDecimal roundedWithScale = bigDecimal.setScale(decimalsToConsider, BigDecimal.ROUND_HALF_UP);
        //System.out.println("Rounded value with setting scale = "+roundedWithScale);


        return roundedWithScale.doubleValue();
    }

    /**
     * Checking device has camera hardware or not
     * */
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


    /*
     * Capturing Camera Image will lauch camera app requrest image capture
     */
    private void captureImage() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = FileProvider.getUriForFile(MobEncaissementTypeActivity.this,
                BuildConfig.APPLICATION_ID + ".provider",
                getOutputMediaFile(MEDIA_TYPE_IMAGE));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        // start the image capture Intent
        MobEncaissementTypeActivity.this.startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    /**
     * Receiving activity result method will be called after closing the camera
     * */
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

        }
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

            }



            cheque_image.setImageBitmap(bitmap);

        } catch (NullPointerException e) {
            e.printStackTrace();
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

    private boolean checkNumCheque() {
        boolean result = true;
        String strNumeroCheque = numero_cheque.getText().toString();
        if(TextUtils.isEmpty(strNumeroCheque)){
            numero_cheque.setError("Ce champs est obligatoire");
            result = false;
        }

        //Log.d(TAG, "checkNumCheque: "+strNumeroCheque.length());
        if(strNumeroCheque.length()!=7){
            numero_cheque.setError("N°Chèque Comporte 7 chiffres");
            result = false;
        }

        return result;
    }

    private boolean checkMontantCheque() {

        boolean result = true;
        String strMontant = valeur_encaissement_cheque.getText().toString();

        if(TextUtils.isEmpty(strMontant)){
            valeur_encaissement_cheque.setError("ce champs est obligatoire");
            result = false;
        }

        if(!TextUtils.isEmpty(strMontant)){


            if(abs(getNumberRounded(Float.parseFloat(String.valueOf(strMontant))))== 0){

                valeur_encaissement_cheque.setError("doit être différent de zero");
                result = false;
            }
        }

        return result;
    }

    private boolean checkMontantEspece() {

        boolean result = true;
        String strMontant = valeur_encaissement_espece.getText().toString();

        if(TextUtils.isEmpty(strMontant)){
            valeur_encaissement_espece.setError("ce champs est obligatoire");
            result = false;
        }

        if(!TextUtils.isEmpty(strMontant)){
            if(abs(getNumberRounded(Float.parseFloat(String.valueOf(strMontant))))== 0){

                valeur_encaissement_espece.setError("doit être différent de zero");
                result = false;
            }
        }
        return result;
    }

    private boolean checkMontantCredit() {

        UserManager userManager = new UserManager(getApplicationContext());
        CreditManager creditManager = new CreditManager(getApplicationContext());
        User user = userManager.get();
        ArrayList<Encaissement> encaissements = EncaissementActivity.encaissements;
        boolean result = true;
        double plafond_utilisateur = 0 ;
        double somme = 0;
        double somme_credit = 0;

        //somme += user.getPLAFOND();
        somme_credit = creditManager.getSumResteCredit();
        somme += somme_credit;

        for(int i =0 ; i < encaissements.size(); i ++){

            if(encaissements.get(i).getTYPE_CODE().equals("CREDIT") ){
                somme+=encaissements.get(i).getMONTANT();
            }

        }
        //plafond_utilisateur = 0 - somme;
        plafond_utilisateur = user.getPLAFOND()- somme;
        String strMontant = valeur_encaissement_credit.getText().toString();


        if(TextUtils.isEmpty(strMontant)){
            valeur_encaissement_credit.setError("ce champs est obligatoire");
            result = false;
            return result;
        }

        if(!TextUtils.isEmpty(strMontant)){
            if(abs(getNumberRounded(Float.parseFloat(String.valueOf(strMontant))))== 0){

                valeur_encaissement_credit.setError("doit être supérieur à zero");
                result = false;
                return result;

            }
        }

        if(!TextUtils.isEmpty(strMontant)){
            if(getNumberRounded(Float.parseFloat(String.valueOf(strMontant)))> plafond_utilisateur){

                valeur_encaissement_credit.setError("Vous ne pouvez pas dépasser votre plafond");
                result = false;
                return result;
            }
        }

        return result;
    }

    private boolean checkCreditEcheance() {

        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String date_credit = df2.format(new Date());

        boolean result = true;

        UserManager userManager = new UserManager(getApplicationContext());
        User user = userManager.get();

        String strCreditEcheance = valeur_echeance.getText().toString();

        if(TextUtils.isEmpty(strCreditEcheance)){
            valeur_echeance.setError("ce champs est obligatoire");
            result = false;
            return result;
        }

        if(!TextUtils.isEmpty(strCreditEcheance)){
            try {
                Date date1 = df2.parse(date_credit);
                Date date2 = df2.parse(valeur_echeance.getText().toString());

                if(!compareTwoDates(date2,date1)){
                    valeur_echeance.setError("Date Inférieur au date crédit");
                    result = false;
                    return result;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private double getValEncaissement(ArrayList<Encaissement> encaissements){

        double montant = 0;

        for(int i = 0; i<encaissements.size(); i++){
            montant+=encaissements.get(i).getMONTANT();
        }
        return montant;
    }

    private double getValEncaissementCredit(ArrayList<Encaissement> encaissements){

        double montant = 0;

        for(int i = 0; i<encaissements.size(); i++){
            if(encaissements.get(i).getTYPE_CODE().equals("CREDIT")){

                montant+=encaissements.get(i).getMONTANT();

            }

        }
        return montant;
    }

    private boolean compareTwoDates (Date date1, Date date2){
        boolean result = false;

        if (date1.compareTo(date2) > 0) {
            Log.d("Encaissement", "compareTwoDates: Superieur");
            result = true;
        } else if (date1.compareTo(date2) < 0) {
            Log.d("Encaissement", "compareTwoDates: Inferieu");
            result = false;
        } else if (date1.compareTo(date2) == 0) {
            Log.d("Encaissement", "compareTwoDates: Egale");
            result = false;
        } else {
            Log.d("Encaissement", "compareTwoDates: problemes");
        }

        return result;
    }

    private void previewCapturedImage(Uri fileUri, int preview_captured) {
        try {
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 5;
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

            }

            if(preview_captured == PREVIEW_CHEQUE){
                cheque_image.setVisibility(View.VISIBLE);
                cheque_image.setImageBitmap(bitmap);
                cheque_captured = true;
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private boolean checkImage(){
        boolean  result = true;
        if(cheque_captured == false){
            result = false;
            charger_cheque_btn.setError("l'image est obligatire");
            return result;
        }

        return result;
    }

    public String getEncodedImage(Uri fileUri){

        String encodeedImage = "";

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //Log.d(TAG, "getEncodedImage: "+bitmapDrawable.getByteCount());
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inSampleSize = 5;
        Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                options);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] byteArray = byteArrayOutputStream .toByteArray();
        encodeedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return encodeedImage;
    }

}
