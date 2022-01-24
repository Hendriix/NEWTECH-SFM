package com.newtech.newtech_sfm.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.newtech.newtech_sfm.Configuration.VisibiliteLigneViewModel;
import com.newtech.newtech_sfm.Fragement.ReferencementFragment;
import com.newtech.newtech_sfm.Fragement.ViewReferencementFragment;
import com.newtech.newtech_sfm.Metier.Visibilite;
import com.newtech.newtech_sfm.Metier.VisibiliteLigne;
import com.newtech.newtech_sfm.Metier_Manager.VisibiliteLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.VisibiliteManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteManager;
import com.newtech.newtech_sfm.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReferencementActivity extends AppCompatActivity implements ReferencementFragment.OnListFragmentInteractionListener,ViewReferencementFragment.OnListFragmentInteractionListener{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private VisibiliteLigneViewModel visibiliteLigneViewModel;
    private ArrayList<VisibiliteLigne> visibiliteLignes;
    VisibiliteLigneManager visibiliteLigneManager;
    VisibiliteManager visibiliteManager;
    private Visibilite visibilite;
    VisiteManager visiteManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.referencement);

        toolbar = findViewById(R.id.toolbar);
        viewPager =findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        visibiliteLigneManager = new VisibiliteLigneManager(getApplicationContext());
        visibiliteManager = new VisibiliteManager(getApplicationContext());
        visiteManager = new VisiteManager(getApplicationContext());


        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        setTitle("REF|VISIBILITE");

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ReferencementFragment(), "REFERENCEMENT");
        adapter.addFragment(new ViewReferencementFragment(), "RECAP");
        //adapter.addFragment(new PrisePhoto(), "PHOTO");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onListFragmentInteraction(VisibiliteLigne visibiliteLigne) {

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_referencement_visibilite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.enregistrer_referencement:
                //Intent intt = new Intent(this, PrintActivity2.class);
                //intt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //intt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(intt);
                //finish();
                Log.d("save ref", "onOptionsItemSelected: 1");
                enregistrerReferencementVisibilite();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Log.d("ViewPager", "onBackPressed: "+viewPager);

        if (viewPager.getCurrentItem() == 0) {

            Log.d("ViewPager", "onBackPressed: "+"1");
            super.onBackPressed();
            Intent intent = new Intent(this,ClientActivity.class);
            startActivity(intent);
            finish();

        }else {

            Log.d("ViewPager", "onBackPressed: "+viewPager.getCurrentItem());
            Log.d("ViewPager", "onBackPressed: "+"2");

            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    public void enregistrerReferencementVisibilite(){

        visibiliteLigneViewModel = ViewModelProviders.of(this).get(VisibiliteLigneViewModel.class);
        visibiliteLignes = visibiliteLigneViewModel.getVisibiliteLignes();
        visibilite = visibiliteLigneViewModel.getVisibilite(ClientActivity.clientCourant,getApplicationContext(),ClientActivity.visite_code);
        //Toast.makeText(getApplicationContext(),"SIZE "+visibiliteLignes.size(), Toast.LENGTH_SHORT).show();

        Log.d("save ref", "enregistrerReferencementVisibilite: "+visibiliteLignes.size());
        Log.d("save ref", "enregistrerReferencementVisibilite: "+visibiliteLignes.toString());

        if(visibiliteLigneManager.checkIfEmpty(visibiliteLignes)){

            //Toast.makeText(getApplicationContext(),"EMPTY", Toast.LENGTH_SHORT).show();
            showCustomDialog(visibiliteLignes,visibilite);

        }else{

            //Toast.makeText(getApplicationContext(),"FULL", Toast.LENGTH_SHORT).show();
            showCustomDialog(visibiliteLignes,visibilite);
        }

    }

    private void showCustomDialog(ArrayList<VisibiliteLigne> visibiliteLignes, Visibilite visibilite) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.referencement_dialog, viewGroup, false);
        Button valider_referencement = (Button) dialogView.findViewById(R.id.valider_referencement);
        Button annuler_referencement = (Button) dialogView.findViewById(R.id.annuler_referencement);
        TextView fragment_diaog_message = (TextView) dialogView.findViewById(R.id.fragment_diaog_message);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);


        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        fragment_diaog_message.setText("VOULEZ VOUS VRAIMENT VALIDER VOTRE REFERENCEMENT/VISIBILITE");
        alertDialog.show();

        annuler_referencement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"ANNULER", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

        valider_referencement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0;i<visibiliteLignes.size();i++){
                    visibiliteLigneManager.add(visibiliteLignes.get(i));
                }

                visibiliteManager.add(visibilite);

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date_visite=df.format(Calendar.getInstance().getTime());

                visiteManager.updateVisite_VRDF(ClientActivity.visiteCourante.getVISITE_CODE(),1,date_visite);

                alertDialog.dismiss();

                if(isNetworkAvailable()){
                    Toast.makeText(getApplicationContext(),"Synchronisation en cours",Toast.LENGTH_SHORT).show();
                    visibiliteManager.synchronisationVisibilite(getApplicationContext());
                    visibiliteLigneManager.synchronisationVisibiliteLigne(getApplicationContext());
                }

                Intent intent = new Intent(ReferencementActivity.this,ClientActivity.class);
                intent.putExtra("VISITERESULTAT_CODE",1);
                startActivity(intent);
                finish();

            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
