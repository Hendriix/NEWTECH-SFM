package com.newtech.newtech_sfm.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.newtech.newtech_sfm.Metier_Manager.StockDemandeLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.StockDemandeManager;
import com.newtech.newtech_sfm.R;

/**
 * Created by csaylani on 12/04/2018.
 */

public class StockActivity extends AppCompatActivity{

    RelativeLayout chargement_layout;
    RelativeLayout dechargement_layout;
    RelativeLayout consulter_stock_layout;
    private AlphaAnimation alphaAnimation;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        alphaAnimation = new AlphaAnimation(3F, 0.8F);

        chargement_layout =(RelativeLayout)findViewById(R.id.chargement_layout);
        dechargement_layout =(RelativeLayout)findViewById(R.id.dechargement_layout);
        consulter_stock_layout =(RelativeLayout)findViewById(R.id.stock_layout);



        chargement_layout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.startAnimation(alphaAnimation);
                        //Toast.makeText(StockActivity.this,"CHARGEMENT",Toast.LENGTH_LONG).show();
                        ChargementActivity.commandeSource = "TP0024";
                        Intent intent = new Intent(StockActivity.this,ChargementActivity.class);
                        startActivity(intent);
                        finish();


                    }
                }
        );

        dechargement_layout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.startAnimation(alphaAnimation);
                        //Toast.makeText(StockActivity.this,"DECHARGEMENT",Toast.LENGTH_LONG).show();
                        ChargementActivity.commandeSource = "TP0025";
                        Intent intent = new Intent(StockActivity.this,ChargementActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );

        consulter_stock_layout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.startAnimation(alphaAnimation);

                        if(isNetworkAvailable()){
                            StockDemandeManager.synchronisationStockDemandeReceptionnee(getApplicationContext());
                            StockDemandeLigneManager.synchronisationStockDemandeLigneReceptionnee(getApplicationContext());
                        }else{

                            //Toast.makeText(StockActivity.this,"Vérifier votre connexion internet puis synchroniser",Toast.LENGTH_LONG).show();
                            showCustomDialog();
                        }

                        //Toast.makeText(StockActivity.this,"CONSULTER STOCK",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(StockActivity.this,StockLigneActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );

        setTitle("STOCK");
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
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
        Intent i = new Intent(this, MenuActivity.class);
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
}
