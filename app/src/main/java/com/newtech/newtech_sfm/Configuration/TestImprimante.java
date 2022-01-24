package com.newtech.newtech_sfm.Configuration;

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
public class TestImprimante {

    BluetoothAdapter bluetoothAdapter ;
    Context context;
    private static final String TAG = "btprint";
    private ArrayAdapter<String> mConversationArrayAdapter;
    public static btPrintFile btPrintService = null;
    private static final boolean D = true;
    private String mConnectedDeviceName = null;


    public TestImprimante(Context con) {
        this.context=con;
        mConversationArrayAdapter = new ArrayAdapter<String>(context,0);
         bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    }

    private void setupComm() {
        // Initialize the array adapter for the conversation thread
        Log.d(TAG, "setupComm()");
        btPrintService = new btPrintFile(context, mHandler);
        if (btPrintService == null)
            Log.e(TAG, "btPrintService init() failed");
    }

  public  void connectToDevice() {
        setupComm();
        String remote = "00:1D:DF:8D:44:40";
        if (remote.length() == 0)
            return;

        if (btPrintService.getState() == btPrintFile.STATE_CONNECTED) {
            btPrintService.stop();
            //setConnectState(btPrintFile.STATE_DISCONNECTED);
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
                    //setConnectState(msg.arg1);
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
    public void printESCP( ) {
        if (btPrintService != null) {
            if (btPrintService.getState() == btPrintFile.STATE_CONNECTED) {
                String message ="w(          Salim\r\n" +
                        "       8511 WHITESBURG DR\r\n" +
                        "      HUNTSVILLE, AL 35802\r\n" +
                        "         (256)585-6389\r\n\r\n" +
                        " Merchant ID: 1312\r\n" +
                        " Ref #: 0092\r\n\r\n" +
                        "w)      Sale\r\n" +
                        "w( XXXXXXXXXXX4003\r\n" +
                        " AMEX       Entry Method: Swiped\r\n\r\n\r\n" +
                        " Total:               $    53.22\r\n\r\n\r\n" +
                        " 12/21/12               13:41:23\r\n" +
                        " Inv #: 000092 Appr Code: 565815\r\n" +
                        " Transaction ID: 001194600911275\r\n" +
                        " Apprvd: Online   Batch#: 000035\r\n\r\n\r\n" +
                        "          Cutomer Copy\r\n" +
                        "           Thank You!\r\n\r\n\r\n\r\n";
                byte[] buf = message.getBytes();
                btPrintService.write(buf);
                addLog("ESCP printed");
            }
        }
    }

}
