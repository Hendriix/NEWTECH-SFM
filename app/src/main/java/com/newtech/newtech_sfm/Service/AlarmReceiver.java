package com.newtech.newtech_sfm.Service;

import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.newtech.newtech_sfm.Configuration.ImprimanteManager;
import com.newtech.newtech_sfm.Metier.btPrintFile;

/**
 * Created by TONPC on 16/06/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    public static ImprimanteManager imprimanteManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        // For our recurring task, we'll just display a message

        imprimanteManager = new ImprimanteManager(context);
        BlutDiscovery blutDiscovery = new BlutDiscovery();

        if(imprimanteManager.isMyServiceRunning(BlutDiscovery.class) && imprimanteManager.isMyServiceRunning(BlutoothConnctionService.class)){
            if(imprimanteManager.btPrintService.getState() == btPrintFile.STATE_CONNECTED){
                Log.d("AlarmReceiver2", ": connected");

                BlutDiscovery.isConnected=true;
            }else{
                Log.d("AlarmReceiver2", ": disconnected");

                BlutDiscovery.isConnected=false;
            }

            try{
                if(bluetoothAdapter.isEnabled() && BlutDiscovery.isConnected==false){
                    //Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
                    Log.d("AlarmReceiver2", "onReceive: I'm running");

                    bluetoothAdapter.startDiscovery();
                }else{
                    Log.d("AlarmReceiver2", ": connected");

                }
            }catch(Exception e){
                Log.d("AlarmReceiver", "onReceive: ");
            }

        }else{
            Log.d("AlarmReceiver2", ": stop alarm manager");

            Intent intent1 = new Intent(context, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                    intent1, PendingIntent.FLAG_CANCEL_CURRENT);
        }

    }

}


