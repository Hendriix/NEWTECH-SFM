package com.newtech.newtech_sfm.Activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.newtech.newtech_sfm.Metier.Impression;
import com.newtech.newtech_sfm.Metier.Imprimante;
import com.newtech.newtech_sfm.Metier.btPrintFile;
import com.newtech.newtech_sfm.Metier_Manager.ImpressionManager;
import com.newtech.newtech_sfm.Metier_Manager.ImprimanteMan;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.Service.BlutoothConnctionService;

import java.util.ArrayList;

/**
 * Created by sferricha on 08/08/2016.
 */

public class PrintActivity2 extends AppCompatActivity {

    private EditText mRemoteDevice;
    public static String message ="";

    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    Button mConnectButton;
    Button mBtnPrint;
    Button mBtnDefault;
    Button mBtnDisc;
    Button mBtnPrintLeft;
    TextView nom_imprimante;
    TextView mac_imprimante;
    TextView imprimante_statut;
    Button mBtnScan = null;
    TextView mLog = null;
    SwipeRefreshLayout srl;
    Button BtnActualiser;
    private static final String TAG = "btprint";
    private static final boolean D = true;
    private ImpressionManager impressionManager;
    ArrayList<Impression> impressions = new ArrayList<>();
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.printactivity2);

        setTitle("IMPRESSION");

        Imprimante imprimante = new Imprimante();
        ImprimanteMan imprimanteMan = new ImprimanteMan(getApplicationContext());


        srl =(SwipeRefreshLayout) findViewById(R.id.swipe_container);
        //srl.setOnRefreshListener(this);




        impressionManager= new ImpressionManager(getApplicationContext());


        final BlutoothConnctionService blutoothConnctionService = new BlutoothConnctionService();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //toolbar.setTitle("IMPRIMANTE");
        //toolbar.setTitleTextColor(Color.WHITE);

        mLog = (TextView) findViewById(R.id.log);
        mLog.setMovementMethod(ScrollingMovementMethod.getInstance());


        nom_imprimante=(TextView)findViewById(R.id.nom_imprimante_val);
        mac_imprimante=(TextView)findViewById(R.id.mac_imprimante_val);
        imprimante_statut=(TextView)findViewById(R.id.imprimante_statut);


        mRemoteDevice = (EditText) findViewById(R.id.remote_device);
        //Log.d("imprimante", "onCreate: "+imprimanteMan.getByStatut("default"));

        if(imprimanteMan.getNumberByStatut("default")>0){
            imprimante=imprimanteMan.getByStatut("default");
            Log.d("imprimante", "onCreate: "+imprimante.toString());
            mRemoteDevice.setText(imprimante.getADDMAC());
            nom_imprimante.setText("NOM         :"+imprimante.getLIBELLE());
            mac_imprimante.setText("ADRESSE MAC :"+imprimante.getADDMAC());

        }else{
            mRemoteDevice.setText("00:00:00:00:00:00");
            nom_imprimante.setText("NOM        :AUCUN");
            mac_imprimante.setText("ADRESSE MAC: 00:00:00:00:00:00");
        }

        if(blutoothConnctionService.imprimanteManager.btPrintService.getState() != btPrintFile.STATE_CONNECTED){
            imprimante_statut.setText("STATUT  :DISCONNECTED");
        }else{
            imprimante_statut.setText("STATUT  :CONNECTED");
        }
        mBtnScan = (Button) findViewById(R.id.button_scan);

        mBtnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDiscovery();
            }
        });



        mConnectButton = (Button) findViewById(R.id.connect);

        mConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!bluetoothAdapter.isEnabled()){
                    Toast.makeText(getApplicationContext(),"ALLUMER BLUETOOTH ET RESSAYER",Toast.LENGTH_SHORT).show();
                }else{
                    if(blutoothConnctionService.imprimanteManager.btPrintService.getState() != btPrintFile.STATE_CONNECTED){

                        blutoothConnctionService.imprimanteManager.setmRemoteDevice(mRemoteDevice.getText().toString());
                        blutoothConnctionService.imprimanteManager.connectToDevice();

                        Toast.makeText(getApplicationContext(),"CONNECTION",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getApplicationContext(),"ALREADY CONNECTED",Toast.LENGTH_SHORT).show();

                    }
                }



            }
        });


        mBtnDisc =(Button)findViewById(R.id.disconnect);
        mBtnDisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(blutoothConnctionService.imprimanteManager.btPrintService.getState() == btPrintFile.STATE_CONNECTED){

                    blutoothConnctionService.imprimanteManager.btPrintService.stop();

                }else{

                    Toast.makeText(getApplicationContext(),"ALREADY DISCONNECTED",Toast.LENGTH_SHORT).show();

                }


            }
        });


        mBtnDefault = (Button) findViewById(R.id.button_default);
        mBtnDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("STATE", "onClick: "+blutoothConnctionService.imprimanteManager.btPrintService.getState());
                //Log.d("STATE", "onClick: "+btPrintFile.STATE_CONNECTED);


                ImprimanteMan imprimanteMan = new ImprimanteMan(getApplication());

                if(blutoothConnctionService.imprimanteManager.btPrintService.getState()==btPrintFile.STATE_CONNECTED) {

                    imprimanteMan.deleteImprimante();
                    //Log.d("STATE1", "onClick: "+blutoothConnctionService.imprimanteManager.btPrintService.getState());
                    //Log.d("STATE1", "onClick: "+btPrintFile.STATE_CONNECTED);
                    Imprimante imprimante = new Imprimante(blutoothConnctionService.imprimanteManager.getmConnectedDeviceName(),mRemoteDevice.getText().toString(),"default");
                    imprimanteMan.add(imprimante);

                    //Log.d("imprimante", "onClick2: "+imprimante.toString());

                }else{
                    Toast.makeText(getApplicationContext(),"AUCUNE IMPRIMANTE TROUVEE",Toast.LENGTH_SHORT).show();
                    //Log.d("STATE2", "onClick: "+blutoothConnctionService.imprimanteManager.btPrintService.getState());
                    //Log.d("STATE2", "onClick: "+btPrintFile.STATE_CONNECTED);

                }
                //Log.d("STATE3", "onClick: "+imprimanteMan.getList().toString());

            }

        });

        mBtnPrintLeft = (Button) findViewById(R.id.Bt_PrintLeft);
        mBtnPrintLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(blutoothConnctionService.imprimanteManager.btPrintService!=null){

                    impressions=impressionManager.getListByStatutCode(0);
                    imprimer(impressions,blutoothConnctionService);

                }else{
                    Toast.makeText(getApplicationContext(),"MERCI DE CONTACTER VOTRE ADMINISTRATEUR",Toast.LENGTH_SHORT).show();

                }
            }

        });

        mBtnPrint = (Button) findViewById(R.id.Bt_Print);
        mBtnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(blutoothConnctionService.imprimanteManager.btPrintService!=null){

                    blutoothConnctionService.imprimanteManager.printESCP();

                }else{
                    Toast.makeText(getApplicationContext(),"MERCI DE CONTACTER VOTRE ADMINISTRATEUR",Toast.LENGTH_SHORT).show();

                }
            }

        });

        srl.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("Refresh", "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        ImprimanteMan imprimanteMan = new ImprimanteMan(getApplicationContext());
                        Imprimante imprimante = new Imprimante();

                        if(imprimanteMan.getNumberByStatut("default")>0){

                            imprimante=imprimanteMan.getByStatut("default");
                            Log.d("imprimante", "onCreate: "+imprimante.toString());
                            mRemoteDevice.setText(imprimante.getADDMAC());
                            nom_imprimante.setText("NOM         :"+imprimante.getLIBELLE());
                            mac_imprimante.setText("ADRESSE MAC :"+imprimante.getADDMAC());

                        }else{
                            mRemoteDevice.setText("00:00:00:00:00:00");
                            nom_imprimante.setText("NOM        :AUCUN");
                            mac_imprimante.setText("ADRESSE MAC: 00:00:00:00:00:00");
                        }

                        if(blutoothConnctionService.imprimanteManager.btPrintService.getState() != btPrintFile.STATE_CONNECTED){
                            imprimante_statut.setText("STATUT  :DISCONNECTED");
                        }else{
                            imprimante_statut.setText("STATUT  :CONNECTED");
                        }

                        srl.setRefreshing(false);
                    }
                }
        );



    }

    boolean bDiscoveryStarted = false;
    public void startDiscovery(){
        if (bDiscoveryStarted)
            return;
        bDiscoveryStarted = true;
        // Launch the DeviceListActivity to see devices and do scan
        Intent serverIntent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {


            case REQUEST_CONNECT_DEVICE:
                //addLog("onActivityResult: requestCode==REQUEST_CONNECT_DEVICE");
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    //addLog("resultCode==OK");
                    // Get the device MAC address
                    String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    mRemoteDevice.setText(address);

                    //addLog("onActivityResult: got device=" + address);
                    // Get the BLuetoothDevice object

                    //BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
                    //mRemoteDevice.setText(device.getAddress());
                    // Attempt to connect to the device
                    //addLog("onActivityResult: connecting device...");
                    //btPrintService.connect(device);
                    //connectToDevice(device);
                }
                bDiscoveryStarted = false;
                break;
            case REQUEST_ENABLE_BT:
                //addLog("requestCode==REQUEST_ENABLE_BT");
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    Log.i(TAG, "onActivityResult: resultCode==OK");
                    // Bluetooth is now enabled, so set up a chat session
                    Log.i(TAG, "onActivityResult: starting setupComm()...");
                    //setupComm();
                } else {
                    // User did not enable Bluetooth or an error occured
                    Log.d(TAG, "onActivityResult: BT not enabled");
                    Toast.makeText(this,"bt_not_enabled_leaving", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            case R.id.logout:
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(PrintActivity2.this, AuthActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                return true;

            case R.id.option2:
                Intent intt = new Intent(this, PrintActivity2.class);
                intt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intt);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void imprimer(ArrayList<Impression> impressions,BlutoothConnctionService blutoothConnctionService){

        if(blutoothConnctionService.imprimanteManager.btPrintService!=null){

            impressions=impressionManager.getListByStatutCode(0);
            boolean printed=false;
            //Log.d("blut", "impressionList "+impressions.toString());

            if(blutoothConnctionService.imprimanteManager.btPrintService.getState() == btPrintFile.STATE_CONNECTED){

                if(impressions.size()>0){
                    for(int i=0;i<impressions.size();i++){
                        if(impressions.get(i).getIMPRESSION_TEXT()!=null){
                            Log.d("print", "imprimer: "+impressions.get(i).getIMPRESSION_TEXT());
                            printed= BlutoothConnctionService.imprimanteManager.printText(impressions.get(i).getIMPRESSION_TEXT());
                            if(printed==true){
                                impressionManager.UpdateImpressionStatut(impressions.get(i).getIMPRESSION_CODE(),1);
                            }
                        }

                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Aucun fichier à imprimer",Toast.LENGTH_SHORT).show();

                }

                //blutoothConnctionService.imprimanteManager.printESCP();
                Log.d("blut", "onClick: "+"connected");

            }else{
                        /*Log.d("blut", "onClick: "+"NOT connected");

                        blutBroad.testImprimante.connectToDevice();
                        blutBroad.testImprimante.printESCP();*/
               Toast.makeText(getApplicationContext(),"PRESS THE BUTTON CONNECT AND TRY AGAIN",Toast.LENGTH_SHORT).show();

              /*  if(impressions.size()>0){
                    for(int i=0;i<impressions.size();i++){
                        if(impressions.get(i).getIMPRESSION_TEXT()!=null){
                            Log.d("print", "imprimer: "+impressions.get(i).getIMPRESSION_TEXT());
                            printed= BlutoothConnctionService.imprimanteManager.printText(impressions.get(i).getIMPRESSION_TEXT());
                            if(printed==true){
                                impressionManager.UpdateImpressionStatut(impressions.get(i).getIMPRESSION_CODE(),1);
                            }
                        }

                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Aucun fichier à imprimer",Toast.LENGTH_SHORT).show();

                }*/

            }

        }else{
            Toast.makeText(getApplicationContext(),"MERCI DE CONTACTER VOTRE ADMINISTRATEUR",Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(PrintActivity2.this, MenuActivity.class);
        startActivity(i);
        finish();
    }

}