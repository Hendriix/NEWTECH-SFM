package com.newtech.newtech_sfm.Configuration;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.newtech.newtech_sfm.Metier.btPrintFile;
import com.newtech.newtech_sfm.Metier.msgTypes;

/**
 * Created by stagiaireit2 on 11/08/2016.
 */
public class ImprimanteManager {

    public static String lastPrint="lastprint";
    BluetoothAdapter bluetoothAdapter ;
    Context context;
    private static final String TAG = "btprint";
    private ArrayAdapter<String> mConversationArrayAdapter;
    public static btPrintFile btPrintService = null;
    private static final boolean D = true;
    private String mConnectedDeviceName = null;
    private String mRemoteDevice;
    // Intent request codes
    public String getmRemoteDevice() {
        return mRemoteDevice;
    }

    public void setmRemoteDevice(String remote) {
        this.mRemoteDevice = remote;
    }

    public String getmConnectedDeviceName() {
        return mConnectedDeviceName;
    }

    public void setmConnectedDeviceName(String mConnectedDeviceName) {
        this.mConnectedDeviceName = mConnectedDeviceName;
    }

    public ImprimanteManager(Context con) {
        this.context=con;
        mConversationArrayAdapter = new ArrayAdapter<String>(context,0);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void setupComm() {
        // Initialize the array adapter for the conversation thread
        Log.d(TAG, "setupComm()");
        btPrintService = new btPrintFile(context, mHandler);
        if (btPrintService == null)
            Log.e(TAG, "btPrintService init() failed");
    }

    public  void connectToDevice() {

        String remote = this.mRemoteDevice;
        if (remote.length() == 0)
            return;

        if (btPrintService.getState() == btPrintFile.STATE_CONNECTED) {
            btPrintService.stop();
            setConnectState(btPrintFile.STATE_DISCONNECTED);
            return;
        }

        String sMacAddr = remote;
        Toast.makeText(context, sMacAddr.toString(), Toast.LENGTH_SHORT).show();

        if (sMacAddr.contains(":") == false && sMacAddr.length() == 12) {
            // If the MAC address only contains hex digits without the
            // ":" delimiter, then add ":" to the MAC address string.
            char[] cAddr = new char[17];

            for (int i = 0, j = 0; i < 12; i += 2) {
                sMacAddr.getChars(i, i + 2, cAddr, j);
                j += 2;
                if (j < 17) {
                    cAddr[j++] = ':';
                }
            }

            sMacAddr = new String(cAddr);
        }

        BluetoothDevice device;
        try {
            device = bluetoothAdapter.getRemoteDevice(sMacAddr);
        } catch (Exception e) {

            Toast.makeText(context, "Invalid BT MAC address", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, sMacAddr.toString(), Toast.LENGTH_SHORT).show();
            device = null;
        }

        if (device != null) {
            addLog("connecting to " + sMacAddr);
            btPrintService.connect(device);
        } else {
            addLog("unknown remote device!");
        }
    }


    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msgTypes.MESSAGE_STATE_CHANGE:
                    Bundle bundle = msg.getData();
                    int status = bundle.getInt("state");
                    if (D)
                        Log.i(TAG, "handleMessage: MESSAGE_STATE_CHANGE: " + msg.arg1);  //arg1 was not used! by btPrintFile
                    setConnectState(msg.arg1);
                    switch (msg.arg1) {
                        case btPrintFile.STATE_CONNECTED:
                            addLog("connected to: " + mConnectedDeviceName);
                            mConversationArrayAdapter.clear();
                            Log.i(TAG, "handleMessage: STATE_CONNECTED: " + mConnectedDeviceName);
                            break;
                        case btPrintFile.STATE_CONNECTING:
                            addLog("connecting...");
                            Log.i(TAG, "handleMessage: STATE_CONNECTING: " + mConnectedDeviceName);
                            break;
                        case btPrintFile.STATE_LISTEN:
                            addLog("connection ready");
                            Log.i(TAG, "handleMessage: STATE_LISTEN");
                            break;
                        case btPrintFile.STATE_IDLE:
                            addLog("STATE_NONE");
                            Log.i(TAG, "handleMessage: STATE_NONE: not connected");
                            break;
                        case btPrintFile.STATE_DISCONNECTED:
                            addLog("disconnected");
                            Log.i(TAG, "handleMessage: STATE_DISCONNECTED");
                            break;
                    }
                    break;
                case msgTypes.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    mConversationArrayAdapter.add("Me:  " + writeMessage);
                    break;
                case msgTypes.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
                    addLog("recv>>>" + readMessage);
                    break;
                case msgTypes.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(msgTypes.DEVICE_NAME);
                    //Toast.makeText(getApplicationContext(), "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, mConnectedDeviceName + "Connected", Toast.LENGTH_SHORT).show();

                    Log.i(TAG, "handleMessage: CONNECTED TO: " + msg.getData().getString(msgTypes.DEVICE_NAME));
                    //printESCP();
                    //updateConnectButton(false);

                    break;
                case msgTypes.MESSAGE_TOAST:

                    Toast.makeText(context, msg.getData().getString(msgTypes.TOAST), Toast.LENGTH_SHORT).show();

                    Log.i(TAG, "handleMessage: TOAST: " + msg.getData().getString(msgTypes.TOAST));
                    addLog(msg.getData().getString(msgTypes.TOAST));
                    break;
                case msgTypes.MESSAGE_INFO:
                    addLog(msg.getData().getString(msgTypes.INFO));
                    //mLog.append(msg.getData().getString(msgTypes.INFO));
                    //mLog.refreshDrawableState();
                    String s = msg.getData().getString(msgTypes.INFO);
                    if (s.length() == 0)
                        s = String.format("int: %i" + msg.getData().getInt(msgTypes.INFO));
                    Log.i(TAG, "handleMessage: INFO: " + s);
                    break;
            }
        }
    };

    public void addLog(String s) {
        Toast.makeText(context,s, Toast.LENGTH_SHORT).show();

    }
    public boolean printText(String message) {

        boolean printed=false;

        if (!message.isEmpty()){
            if (btPrintService != null) {
                if (btPrintService.getState() == btPrintFile.STATE_CONNECTED) {

                    byte[] buf = message.getBytes();
                    btPrintService.write(buf);
                    addLog("Text printed");
                    printed=true;

                }
            }
       }
    return printed;
    }
    void setConnectState(Integer iState) {
        switch (iState) {
            case btPrintFile.STATE_CONNECTED:
                //updateConnectButton(true);
                break;
            case btPrintFile.STATE_DISCONNECTED:
                //updateConnectButton(false);
                break;
            case btPrintFile.STATE_CONNECTING:
                addLog("connecting...");
                break;
            case btPrintFile.STATE_LISTEN:
                addLog("listening...");
                break;
            case btPrintFile.STATE_IDLE:
                addLog("state none");
                break;
            default:
                addLog("unknown state var " + iState.toString());
        }
    }

    public void printESCP( ) {
        if (btPrintService != null) {
            if (btPrintService.getState() == btPrintFile.STATE_CONNECTED) {
                String message ="         TEST\r\n";

                byte[] buf = message.getBytes();
                btPrintService.write(buf);
                addLog("ESCP printed");
            }
        }
    }


    String Nchaine(String machaine,int size){
        int taille = machaine.length();
        String spaces="";
        if(size<taille) return machaine.substring(0,size);
            for(int i=0;i<size-taille;i++)
                spaces+=" ";
        return machaine+spaces;
    }
}
