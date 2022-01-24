package com.newtech.newtech_sfm.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.newtech.newtech_sfm.Fragement.CommandeFragment;
import com.newtech.newtech_sfm.Fragement.LivraisonFragment;
import com.newtech.newtech_sfm.Fragement.ObjectifFragment;
import com.newtech.newtech_sfm.Fragement.VenteParArticleFragment;
import com.newtech.newtech_sfm.Fragement.VentesMensuellesFragment;
import com.newtech.newtech_sfm.Fragement.VisiteFragment;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.Livraison;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.RapportFondamental.RapportFondamentalFragment;
import com.newtech.newtech_sfm.RapportQualitatif.RapportQualitativeFragment;
import com.newtech.newtech_sfm.rapportchoufouni.RapportChoufouniFragment;

public class RapportMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,
        ObjectifFragment.OnFragmentInteractionListener,
        VenteParArticleFragment.OnFragmentInteractionListener,
        CommandeFragment.OnListFragmentInteractionListener,
        LivraisonFragment.OnListFragmentInteractionListener,
        VentesMensuellesFragment.OnFragmentInteractionListener,
        VisiteFragment.OnFragmentInteractionListener,
        RapportQualitativeFragment.OnFragmentInteractionListener,
        RapportFondamentalFragment.OnFragmentInteractionListener,
        RapportChoufouniFragment.OnFragmentInteractionListener{

    private boolean isVisiteReport = false;
    private static final String TAG = RapportMenuActivity.class.getName();
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapport_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        fragmentManager = getSupportFragmentManager();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState == null){
            ShowFragment(R.id.objectif);
        }


        View header = navigationView.getHeaderView(0);
        ImageView close_report_iv = header.findViewById(R.id.close_report_iv);
        close_report_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RapportMenuActivity.this,MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

        setTitle("MENU RAPPORT");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();


            int count = fragmentManager.getBackStackEntryCount();

            if(count > 1){

                    fragmentManager.popBackStack();

            }else{
                Intent intent = new Intent(RapportMenuActivity.this,MenuActivity.class);
                startActivity(intent);
                finish();
            }

            /*if(isVisiteReport){
                Fragment fragment = new VisiteFragment();
                if (fragment != null) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.rapport_frame_layout, fragment);
                    fragmentTransaction.commit();
                }
                isVisiteReport = false;
            }else{
                Intent intent = new Intent(RapportMenuActivity.this,MenuActivity.class);
                startActivity(intent);
                finish();
            }*/

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void ShowFragment(int itemId) {

        Fragment fragment = null;
        isVisiteReport = false;

        switch (itemId) {

            case R.id.vente_par_article:
                fragment = new VenteParArticleFragment();
                break;

            case R.id.objectif:
                fragment = new ObjectifFragment();
                break;

            case R.id.commandes:
                fragment = new CommandeFragment();
                break;

            case R.id.ventes_mensuelles:
                fragment = new VentesMensuellesFragment();
                break;

            case R.id.rapport_qualitatif:
                fragment = new RapportQualitativeFragment();
                break;

            case R.id.rapport_fondamental:
                fragment = new RapportFondamentalFragment();
                break;

            case R.id.rapport_choufouni:
                fragment = new RapportChoufouniFragment();
                break;

            case R.id.visites:
                isVisiteReport = true;
                fragment = new VisiteFragment();
                break;

            case R.id.livraisons:
                fragment = new LivraisonFragment();
                break;


        }


        replaceFragment(fragment);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void replaceFragment(Fragment fragment){

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.rapport_frame_layout, fragment);
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
            fragmentTransaction.commit();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        //Calling the ShowFragment() method here to show the our created menu as default menus.
        ShowFragment(item.getItemId());


        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(Commande commande) {

    }

    @Override
    public void onListFragmentInteraction(Livraison livraison) {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Log.d(TAG, "onConfigurationChanged: LANDSCAP");
        }else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Log.d(TAG, "onConfigurationChanged: PORTRAIT");
        }
    }
}
