package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.newtech.newtech_sfm.Activity.SyncV2Activity;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.Logs;
import com.newtech.newtech_sfm.Metier.Parametre;
import com.newtech.newtech_sfm.Metier_Manager.LogsManager;
import com.newtech.newtech_sfm.Metier_Manager.ParametreManager;
import com.newtech.newtech_sfm.R;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Common {


    private static final String TAG = Common.class.getName();


    Boolean date_valide;

    public static boolean verifyDate(Context context, Activity activity){

        Dialog updateDialog;
        updateDialog = new Dialog(activity);
        ParametreManager parametreManager = new ParametreManager(context);
        Parametre parametre_date_serveur = parametreManager.get("DATE_SERVEUR");

        LogsManager logsManager = new LogsManager(context);

        try{

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String date_telephone = df.format(Calendar.getInstance().getTime());
            String date_serveur = parametre_date_serveur.getVALEUR();

            if(!date_serveur.equals(date_telephone)){


                updateDialog.setContentView(R.layout.update_popup);
                Button update_btn = (Button)updateDialog.findViewById(R.id.update_btn);
                TextView information_tv = (TextView)updateDialog.findViewById(R.id.information_tv);

                update_btn.setText("FERMER");
                information_tv.setText("LA DATE DU TELEPHONE N'EST PAS VALIDE L'APPLICATION SERA BLOQUEE ...");

                Logs logs = new Logs(context,"TP0075");
                logsManager.add(logs);
                LogsManager.synchronisationLogs(context);

                update_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        updateDialog.dismiss();
                        Intent intent = new Intent(activity, SyncV2Activity.class);
                        activity.startActivity(intent);
                        activity.finish();

                    }
                });
                updateDialog.setCanceledOnTouchOutside(false);
                updateDialog.setCancelable(false);
                updateDialog.show();

                return false;
            }

        }catch (NullPointerException e){
            Log.d(TAG, "onCreate: "+e.getMessage());
        }

        return true;
    }

    public static void verifyVersion(Context context,Activity activity, Boolean date_valide){

        Dialog updateDialog;
        updateDialog = new Dialog(activity);

        PackageManager packageManager;
        PackageInfo packageInfo;
        int versionCode;
        int version_server;

        packageManager = context.getPackageManager();
        packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            versionCode = (int) packageInfo.getLongVersionCode(); // avoid huge version numbers and you will be ok
        } else {
            //noinspection deprecation
            versionCode = packageInfo.versionCode;
        }

        ParametreManager parametreManager = new ParametreManager(context);
        Parametre parametre_version = parametreManager.get("VERSION");

        LogsManager logsManager = new LogsManager(context);

        if(parametre_version.getVALEUR() != null && date_valide == true){

            try{
                version_server = Integer.parseInt(parametre_version.getVALEUR());

                if(versionCode != version_server){
                    Log.d(TAG, "onCreate: update");

                    updateDialog.setContentView(R.layout.update_popup);
                    Button update_btn = (Button)updateDialog.findViewById(R.id.update_btn);
                    TextView information_tv = (TextView)updateDialog.findViewById(R.id.information_tv);

                    update_btn.setText("OK");
                    information_tv.setText("SYNCHRONISEZ VOS DONNEES ET PASSEZ SUR PLAYSTORE POUR LA MISE A JOUR.");

                    Logs logs = new Logs(context,"TP00764");
                    logsManager.add(logs);
                    LogsManager.synchronisationLogs(context);

                    update_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            updateDialog.dismiss();
                            Intent intent = new Intent(activity, SyncV2Activity.class);
                            activity.startActivity(intent);
                            activity.finish();
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

    }

    public static int findLastTrueValue(boolean mChecked[]){
        int lastelement = 0;
        for(int i=0 ; i<mChecked.length;i++){
            if(mChecked[i]){
                lastelement = i;
            }
        }
        return lastelement;
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    public static void  setLocked(ImageView v)
    {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);  //0 means grayscale
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);
        v.setImageAlpha(128);   // 128 = 0.5
    }

    public static void  setUnlocked(ImageView v)
    {
        v.setColorFilter(null);
        v.setImageAlpha(255);
    }

    public static HashMap<Integer,String> getPositions(){
        HashMap<Integer, String> positions = new HashMap<Integer, String>();
        positions.clear();

        positions.put(0, "TL");
        positions.put(1, "TM");
        positions.put(2, "TR");
        positions.put(3, "ML");
        positions.put(4, "MM");
        positions.put(5, "MR");
        positions.put(6, "BL");
        positions.put(7, "BM");
        positions.put(8, "BR");

        return positions;
    }

    public static boolean isDeviceSupportCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public static String getEncodedImage(Uri fileUri, int preview_captured){

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

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String dateYesterday() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }

    public static String getDate(){

        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_visite = df1.format(Calendar.getInstance().getTime());
        return date_visite;
    }
    
    public static void showImageView(Article article, ImageView imageView, Context context){
        
        String base64Image = "";

        if(article.getIMAGE().toString().contains(",")){

            base64Image = String.valueOf(article.getIMAGE()).split(",")[1];

        }else{

            base64Image=article.getIMAGE();
        }


        if(base64Image.length()<10 || base64Image.equals("") || base64Image==null){

            if(getImageId(context, article.getARTICLE_CODE().toLowerCase())>0){
                imageView.setImageResource(getImageId(context, article.getARTICLE_CODE().toLowerCase()));

            }else{
                imageView.setImageResource(getImageId(context,"bouteille_inconnu2"));

            }

        }else{
            byte[] decodedString= Base64.decode(base64Image,Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);

            imageView.setImageBitmap(Bitmap.createScaledBitmap(decodedByte,decodedByte.getWidth() , decodedByte.getHeight(), false));
        }
    }

    public static void showToast(String message, Context context){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

}
