package com.newtech.newtech_sfm.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.newtech.newtech_sfm.Configuration.ImprimanteManager;
import com.newtech.newtech_sfm.Metier.btPrintFile;
import com.newtech.newtech_sfm.Metier_Manager.ImprimanteMan;

import java.util.HashMap;

/**
 * Created by TONPC on 15/06/2017.
 */

public class BlutDiscovery extends Service {

    HashMap<String,String> DeviceNAmeAdresse = new HashMap<>();
    public static ImprimanteManager imprimanteManager;
    private String partnerDevAdd;
    public static boolean isConnected=false;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private static final String TAG = BlutDiscovery.class.getName();

    @Override
    public void onCreate() {

        super.onCreate();
        serviceStartForgroundChannel();
        imprimanteManager = new ImprimanteManager(getApplicationContext());
        ImprimanteMan imprimanteMan = new ImprimanteMan(getApplicationContext());


        if(bluetoothAdapter != null){
            if(!bluetoothAdapter.isEnabled()){
                bluetoothAdapter.enable();
            }
        }else{
            Log.d(TAG, "onCreate: adapter null");
        }

        if(!imprimanteMan.getByStatut("default").equals(null)){

            Log.d("AlarmReceiver2", "onCreate: condition1");
            partnerDevAdd=imprimanteMan.getByStatut("default").getADDMAC();
            Log.d("AlarmReceiver2", "onCreate: ");

            Log.d("alarm ", "ismyservice running : "+imprimanteManager.isMyServiceRunning(BlutoothConnctionService.class));
            if(imprimanteManager.isMyServiceRunning(BlutoothConnctionService.class)) {

                try{
                    if(imprimanteManager.btPrintService.getState() == btPrintFile.STATE_CONNECTED){
                        Log.d("AlarmReceiver2", "onCreate: condition2");

                        isConnected=true;
                    }else{
                        Log.d("AlarmReceiver2", "onCreate: condition3");

                        isConnected=false;
                    }
                }catch (NullPointerException r){

                }


            }



        }

        if(isConnected==false){
            Log.d("AlarmReceiver2", "onCreate: condition4");

            registerReceiver(mReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

            if(bluetoothAdapter != null){
                bluetoothAdapter.startDiscovery();
            }else{
                Log.d(TAG, "onCreate: adapter null");
            }
        }
    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("BROADCAST","ON RECEIVE");
            String action = intent.getAction();

            ImprimanteMan imprimanteMan = new ImprimanteMan(getApplicationContext());
            partnerDevAdd=imprimanteMan.getByStatut("default").getADDMAC();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                //Bluetooth b = new Bluetooth(device.getName(),device.getAddress());
                if(isConnected==false){
                    String DeviceName = device.getName();
                    String DeviceAddresse = device.getAddress();


                    DeviceNAmeAdresse.put(DeviceName,DeviceAddresse);
                    Log.d("AlarmReceiver2", "onReceive: "+DeviceNAmeAdresse.toString());

                    for (String key : DeviceNAmeAdresse.keySet()) {
                        Log.d("AlarmReceiver2", "Name: " + key + " Adresse: " + DeviceNAmeAdresse.get(key));

                        if(DeviceNAmeAdresse.get(key).equals(partnerDevAdd)){
                            Log.d("AlarmReceiver2", "mReceiver: connection");

                            imprimanteManager.setmRemoteDevice(partnerDevAdd);
                            imprimanteManager.connectToDevice();
                        }


                    }
                    DeviceNAmeAdresse.clear();
                    //list.add(b);
                }else{
                    Log.d("AlarmReceiver2", "Connected to printer: ");
                }

            }

        }
    };





    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        Log.d("alarm", "bluthDiscovery onDestroy: service destroyed");
        stopService(new Intent(this, BlutDiscovery.class));    }

    public void unRegisterBroadcastReceiver(){
        unregisterReceiver(mReceiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        serviceStartForgroundChannel();
        return super.onStartCommand(intent, flags, startId);
    }

    private void serviceStartForgroundChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "ID";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Printer service",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        }
    }
}
