package com.newtech.newtech_sfm.Activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.newtech.newtech_sfm.Configuration.LogSyncViewModel;
import com.newtech.newtech_sfm.Configuration.SynchronisationAdapter;
import com.newtech.newtech_sfm.Metier.LogSync;
import com.newtech.newtech_sfm.Metier.Parametre;
import com.newtech.newtech_sfm.Metier_Manager.LogSyncManager;
import com.newtech.newtech_sfm.Metier_Manager.ParametreManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by stagiaireit2 on 18/07/2016.
 */
public class SyncV2Activity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private static final String TAG = SyncV2Activity.class.getName();

    SearchView mSearchView;
    ListView listView;
    LogSyncManager logSyncManager;
    SwipeRefreshLayout swipeRefresh;
    SynchronisationAdapter synchronisationAdapter;
    public static LogSyncViewModel logSyncViewModel;

    PackageManager packageManager;
    PackageInfo packageInfo;
    ParametreManager parametreManager;

    int versionCode;
    int version_server;
    String playstore_url;

    Button update_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.synchronisation_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.include);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        logSyncManager = new LogSyncManager(getApplicationContext());
        parametreManager = new ParametreManager(getApplicationContext());
        update_btn = findViewById(R.id.update_btn);



        initializationViews();
        mSearchView=(SearchView) findViewById(R.id.tache_search);
        swipeRefresh.setRefreshing(true);

        logSyncViewModel = ViewModelProviders.of(this).get(LogSyncViewModel.class);

        Parametre parametre = parametreManager.get("VERSION");
        Parametre playstoreParametre = parametreManager.get("PLAYSTORE");



        packageManager = this.getPackageManager();
        packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            versionCode = (int) packageInfo.getLongVersionCode(); // avoid huge version numbers and you will be ok
        } else {
            //noinspection deprecation
            versionCode = packageInfo.versionCode;
        }

        if(parametre != null){
            try{

                version_server = Integer.parseInt(parametre.getVALEUR());

                if(versionCode != version_server){
                    update_btn.setVisibility(View.VISIBLE);
                }

            }catch (NumberFormatException e){
                Log.d(TAG, "onCreate: "+e.getMessage());
            }
        }

        playstore_url = "https://play.google.com/store/apps/details?id=com.newtech.newtech_sfm";
        if(playstoreParametre != null){

            try{
                playstore_url = playstoreParametre.getVALEUR();
            }catch (NullPointerException e){
                Log.d(TAG, "onCreate: "+e.getMessage());
            }
        }




        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    //intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.newtech.newtech_sfm"));
                    intent.setData(Uri.parse(playstore_url));
                    intent.setPackage("com.android.vending");
                    startActivity(intent);

                }catch(ActivityNotFoundException a){

                    Log.d(TAG, "onClick: "+a.getMessage());

                }

            }
        });


        getLogSyncs();

        // lambda expression
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLogSyncs();
            }
        });

        setTitle("SYNCHRONISATION");
    }

    private void prepareListView(List<LogSync> logSyncs) {

        View child = getLayoutInflater().inflate(R.layout.no_data_found, null);
        ((ViewGroup)listView.getParent()).addView(child);

        listView.setEmptyView(child);
        synchronisationAdapter = new SynchronisationAdapter(this,logSyncs);
        setupSearchView();
        listView.setAdapter(synchronisationAdapter);
        synchronisationAdapter.notifyDataSetChanged();

    }


    private void initializationViews() {
        swipeRefresh = findViewById(R.id.swiperefresh);
        listView = findViewById(R.id.synchronisation_listview);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getChildAt(0) != null) {
                    swipeRefresh.setEnabled(listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0);
                }
            }
        });
    }

    public void getLogSyncs() {

        //logSyncManager.deleteLogs();
        if(isNetworkAvailable()) {

            swipeRefresh.setRefreshing(true);
            List<LogSync> logSyncs = new ArrayList<>();
            logSyncViewModel.clearListMutableLiveData(logSyncs);
            // Create the observer which updates the UI.
            final Observer<List<LogSync>> logSyncObserver = new Observer<List<LogSync>>() {
                @Override
                public void onChanged(@Nullable final List<LogSync> logSyncs) {
                    // Update the UI, in this case, a TextView.
                    prepareListView(logSyncs);
                    swipeRefresh.setRefreshing(false);
                }
            };

            logSyncViewModel.getLogSyncList().observe(this, logSyncObserver);

        }else{

            swipeRefresh.setRefreshing(false);
            List<LogSync> logSyncs = new ArrayList<>();
            prepareListView(logSyncs);
            showCustomDialog();
            //Toast.makeText(SyncV2Activity.this, "Vérifier votre connexion internet puis synchroniser", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
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
                Intent i = new Intent(SyncV2Activity.this, AuthActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                return true;

            case R.id.option2:
                Intent intt = new Intent(SyncV2Activity.this, PrintActivity2.class);
                intt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intt);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SyncV2Activity.this, MenuActivity.class);
        startActivity(i);
        finish();
    }

    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.synchronisation_dialog, viewGroup, false);
        Button confirmer = (Button) dialogView.findViewById(R.id.confirmer);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);


        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"ANNULER", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        //mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("SEARCH HERE");

    }

    public boolean onQueryTextChange(String newText) {

        String textfilter=mSearchView.getQuery().toString().toLowerCase(Locale.getDefault());
        synchronisationAdapter.filter(textfilter);
        //Toast.makeText(this,textfilter, Toast.LENGTH_LONG).show();
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}
