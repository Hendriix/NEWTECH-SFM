package com.newtech.newtech_sfm.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.newtech.newtech_sfm.Configuration.ImprimanteManager;

/**
 * Created by TONPC on 30/05/2017.
 */

public class BlutoothConnctionService extends Service{

    private static final String TAG = "btprint";
    public static  ImprimanteManager imprimanteManager;

    @Override
    public void onCreate() {
        super.onCreate();
        serviceStartForgroundChannel();
        imprimanteManager=new ImprimanteManager(getApplicationContext());
        imprimanteManager.setupComm();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("alarm", "onDestroy:bluetoothconnection service destroyed");
        stopService(new Intent(this, BlutoothConnctionService.class));


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
