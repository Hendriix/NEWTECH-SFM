package com.newtech.newtech_sfm.Metier;

/**
 * Created by sferricha on 08/08/2016.
 */

public class msgTypes {
    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    public static final String INFO = "info";
    public static final String STATE = "state";
    public static final String READ = "read";
    public static final String WRITE = "write";

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final int MESSAGE_INFO = 6;
}
