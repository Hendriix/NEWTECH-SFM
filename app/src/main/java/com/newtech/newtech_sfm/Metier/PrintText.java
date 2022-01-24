package com.newtech.newtech_sfm.Metier;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Created by sferricha on 10/08/2016.
 */

public class PrintText  {

    btPrintFile btPrintService = null;
    private static final String TAG = "btprint";
    private static final boolean D = true;
    private Context context;

    public PrintText(Context ccontext) {

        context=ccontext;
    }

    byte[] escpQuery() {
        byte[] buf;
        String sBuf = "?{QST:HW}";
        ByteBuffer buf2;
        Charset charset = Charset.forName("UTF-8");
        buf2 = charset.encode(sBuf);
        buf2.put(0, (byte) 0x1B);
        return buf2.array();
    }


    public void printESCP() {
        btPrintService = new btPrintFile(mHandler,"00:1D:DF:8D:44:40");
        BluetoothDevice device;
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            device = bluetoothAdapter.getRemoteDevice("00:1D:DF:8D:44:40");
        } catch (Exception e) {

            //Toast.makeText(PrintActivity2.this, "Invalid BT MAC address", Toast.LENGTH_SHORT).show();
            //Toast.makeText(PrintActivity2.this, sMacAddr.toString(), Toast.LENGTH_SHORT).show();
            device = null;
        }

        if (device != null) {
            addLog("connecting to " + "00:1D:DF:8D:44:40");
            btPrintService.connect(device);
        } else {
            addLog("unknown remote device!");
        }
        if (btPrintService != null) {
            if (btPrintService.getState() == btPrintFile.STATE_CONNECTED) {
                String message = "w(          FUEL CITY\r\n" +
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
               // addLog("ESCP printed");
                Toast.makeText(context, "ESCP printed", Toast.LENGTH_SHORT).show();
            }
            else                 Toast.makeText(context, "non connecte ", Toast.LENGTH_SHORT).show();

        }
        else                 Toast.makeText(context, "btPrintService null ", Toast.LENGTH_SHORT).show();

    }

    void printString(String message) {
        if (btPrintService != null) {
            if (btPrintService.getState() == btPrintFile.STATE_CONNECTED) {
                byte[] buf = message.getBytes();
                btPrintService.write(buf);
                //addLog("messagestring printed");
            }
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

                       // Log.i(TAG, "handleMessage: MESSAGE_STATE_CHANGE: " + msg.arg1);  //arg1 was not used! by btPrintFile
                    //setConnectState(msg.arg1);
                    switch (msg.arg1) {
                        case btPrintFile.STATE_CONNECTED:
                            addLog("connected to: device");
                            //mConversationArrayAdapter.clear();
                           // Log.i(TAG, "handleMessage: STATE_CONNECTED: device");
                            break;
                        case btPrintFile.STATE_CONNECTING:
                            addLog("connecting...");
                            //Log.i(TAG, "handleMessage: STATE_CONNECTING: device");
                            break;
                        case btPrintFile.STATE_LISTEN:
                            addLog("connection ready");
                            //Log.i(TAG, "handleMessage: STATE_LISTEN");
                            break;
                        case btPrintFile.STATE_IDLE:
                            addLog("STATE_NONE");
                            //Log.i(TAG, "handleMessage: STATE_NONE: not connected");
                            break;
                        case btPrintFile.STATE_DISCONNECTED:
                            addLog("disconnected");
                            //Log.i(TAG, "handleMessage: STATE_DISCONNECTED");
                            break;
                    }
                    break;
                case msgTypes.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    //mConversationArrayAdapter.add("Me:  " + writeMessage);
                    addLog(writeMessage);
                    break;
                case msgTypes.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    //mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
                    addLog("recv>>>" + readMessage);
                    break;
                case msgTypes.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    //mConnectedDeviceName = msg.getData().getString(msgTypes.DEVICE_NAME);
                    //Toast.makeText(getApplicationContext(), "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(PrintActivity2.this, mConnectedDeviceName + "Connected", Toast.LENGTH_SHORT).show();

                    //Log.i(TAG, "handleMessage: CONNECTED TO: " + msg.getData().getString(msgTypes.DEVICE_NAME));
                    //printESCP();
                    //updateConnectButton(false);
                    addLog("device Connected");
                    break;
                case msgTypes.MESSAGE_TOAST:
//                    Toast toast = Toast.makeText(getApplicationContext(), msg.getData().getString(msgTypes.TOAST), Toast.LENGTH_SHORT);//.show();
//                    toast.setGravity(Gravity.CENTER,0,0);
//                    toast.show();
                   // Toast.makeText(PrintActivity2.this, msg.getData().getString(msgTypes.TOAST), Toast.LENGTH_SHORT).show();

                    //Log.i(TAG, "handleMessage: TOAST: " + msg.getData().getString(msgTypes.TOAST));
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
                    addLog("handleMessage: INFO: " + s);
                    break;
            }
        }
    };

    public void addLog(String s) {
        Toast.makeText(context, s.toString(), Toast.LENGTH_SHORT).show();

    }
}
