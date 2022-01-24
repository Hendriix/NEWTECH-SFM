package com.newtech.newtech_sfm.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.newtech.newtech_sfm.Configuration.ClientN_Adapter;
import com.newtech.newtech_sfm.Metier.ClientN;
import com.newtech.newtech_sfm.Metier_Manager.ClientNManager;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.Service.AlarmReceiver;
import com.newtech.newtech_sfm.Service.BlutDiscovery;
import com.newtech.newtech_sfm.Service.BlutoothConnctionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by sferricha on 06/12/2016.
 */

public class CatalogueClientNActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView mSearchView;
    ClientN_Adapter clientN_adapter;
    List<ClientN> clientn_list = new ArrayList<ClientN>();
    RecyclerView rv;
    ListView mListView1;
    SearchManager searchmanager;

    public int ACCES_COARSE_LOCATION_CODE = 3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientn_catalogue);

        ClientNManager clientn_manager = new ClientNManager(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_client);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("NOUVEAUX CLIENTS");

        searchmanager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) findViewById(R.id.search_clientn);

        clientn_list = clientn_manager.getList();

        mListView1 = (ListView) findViewById(R.id.clientn_listview1);

        View child = getLayoutInflater().inflate(R.layout.no_data_found, null);
        ((ViewGroup) mListView1.getParent()).addView(child);

        mListView1.setEmptyView(child);

        clientN_adapter = new ClientN_Adapter(this, clientn_list);

        mListView1.setAdapter(clientN_adapter);
        mListView1.setTextFilterEnabled(true);
        setupSearchView();

        if (ContextCompat.checkSelfPermission(CatalogueClientNActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(CatalogueClientNActivity.this, "ACCESS_COARSE LOCATION PERMISISION GRANTED!",
            //Toast.LENGTH_SHORT).show();


        } else {
            requestFineCoarsePermission();
        }

        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClientN clientN = (ClientN) clientN_adapter.getItem(position);
                //Intent intent=new Intent(getApplicationContext(),Client_Manager.class);
                //intent.putExtra("CLIENT_CODE",clientN.getCLIENT_CODE());
                //intent.putExtra("FROM","CLIENTN");
                //startActivity(intent);
                //finish();
            }
        });


    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        //mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");

    }

    public boolean onQueryTextChange(String newText) {

        String textfilter = mSearchView.getQuery().toString().toLowerCase(Locale.getDefault());
        clientN_adapter.filter(textfilter);
        //Toast.makeText(this,textfilter, Toast.LENGTH_LONG).show();

        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_client, menu);
        return true;
    }


    private void requestFineCoarsePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {

            new AlertDialog.Builder(this)
                    .setTitle("PERMISSION NEEDED")
                    .setMessage("TO SPOT THE PHONE")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(CatalogueClientNActivity.this,
                                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, ACCES_COARSE_LOCATION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            stopService(new Intent(CatalogueClientNActivity.this, BlutoothConnctionService.class));
                            stopService(new Intent(CatalogueClientNActivity.this, BlutDiscovery.class));
                            Intent intent = new Intent(CatalogueClientNActivity.this, AlarmReceiver.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(CatalogueClientNActivity.this, 0,
                                    intent, PendingIntent.FLAG_CANCEL_CURRENT);
                            try {
                                pendingIntent.send();
                            } catch (PendingIntent.CanceledException e) {
                                e.printStackTrace();
                            }
                            finish();
                            System.exit(0);

                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, ACCES_COARSE_LOCATION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACCES_COARSE_LOCATION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "ACCESS COARSE LOCATION GRANTED", Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(CatalogueClientNActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // Toast.makeText(CatalogueClientNActivity.this, "COARSE LOCATION already granted",
                    //Toast.LENGTH_SHORT).show();


                } else {


                    stopService(new Intent(CatalogueClientNActivity.this, BlutoothConnctionService.class));
                    stopService(new Intent(CatalogueClientNActivity.this, BlutDiscovery.class));
                    Intent intent = new Intent(CatalogueClientNActivity.this, AlarmReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(CatalogueClientNActivity.this, 0,
                            intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    try {
                        pendingIntent.send();
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }
                    finish();
                    System.exit(0);
                }

            } else {

                Toast.makeText(this, "COARSE LOCATION DENIED", Toast.LENGTH_SHORT).show();
                stopService(new Intent(CatalogueClientNActivity.this, BlutoothConnctionService.class));
                stopService(new Intent(CatalogueClientNActivity.this, BlutDiscovery.class));
                Intent intent = new Intent(CatalogueClientNActivity.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(CatalogueClientNActivity.this, 0,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);
                try {
                    pendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
                finish();
                System.exit(0);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            case R.id.add_client:
                Intent intent = new Intent(this, Client_Manager.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            case R.id.logout:
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(CatalogueClientNActivity.this, AuthActivity.class);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }


}
