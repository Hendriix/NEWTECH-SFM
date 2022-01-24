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
import com.newtech.newtech_sfm.Configuration.StockPLigneViewModel;
import com.newtech.newtech_sfm.Fragement.PanierReleveStockFragment;
import com.newtech.newtech_sfm.Fragement.ReleveStockFragment;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.StockP;
import com.newtech.newtech_sfm.Metier.StockPLigne;
import com.newtech.newtech_sfm.Metier_Manager.StockPLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.StockPManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteManager;
import com.newtech.newtech_sfm.R;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReleveStockActivity extends AppCompatActivity implements ReleveStockFragment.OnListFragmentInteractionListener,PanierReleveStockFragment.OnListFragmentInteractionListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private StockPLigneViewModel stockPLigneViewModel;
    private ArrayList<StockPLigne> stockPLignes;
    StockPLigneManager stockPLigneManager;
    StockPManager stockPManager;
    StockP stockP;
    VisiteManager visiteManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.releve_stock);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        stockPLigneManager = new StockPLigneManager(getApplicationContext());
        stockPManager = new StockPManager(getApplicationContext());
        visiteManager = new VisiteManager(getApplicationContext());

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setTitle("RELEVE DE STOCK");

    }

    private void setupViewPager(ViewPager viewPager) {
        ReleveStockActivity.ViewPagerAdapter adapter = new ReleveStockActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ReleveStockFragment(), "SELECT ARTICLE");
        adapter.addFragment(new PanierReleveStockFragment(), "PANIER");
        //adapter.addFragment(new PrisePhoto(), "PHOTO");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onListFragmentInteraction(Article article) {

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
                try {
                    enregistrerReleveStock();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Intent intt = new Intent(this, PrintActivity2.class);
                //intt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //intt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(intt);
                //finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void enregistrerReleveStock() throws JSONException {

        stockPLigneViewModel = ViewModelProviders.of(this).get(StockPLigneViewModel.class);
        stockPLignes = stockPLigneViewModel.getStockPLigneArrayList();
        stockP = stockPLigneViewModel.getStockP(ClientActivity.clientCourant,getApplicationContext());
        //Toast.makeText(getApplicationContext(),"SIZE "+visibiliteLignes.size(), Toast.LENGTH_SHORT).show();

        Log.d("save ref", "enregistrerReferencementVisibilite: "+stockPLignes.size());
        Log.d("save ref", "enregistrerReferencementVisibilite: "+stockPLignes.toString());

        if(stockPLignes.size()>0){

            //Toast.makeText(getApplicationContext(),"EMPTY", Toast.LENGTH_SHORT).show();
            showCustomDialog(stockPLigneManager.fixStockPLigneCode(stockPLignes),stockP);

        }else{

            //Toast.makeText(getApplicationContext(),"FULL", Toast.LENGTH_SHORT).show();
            showCustomDialog(stockPLigneManager.fixStockPLigneCode(stockPLignes),stockP);
        }

    }

    private void showCustomDialog(ArrayList<StockPLigne> stockPLignes, StockP stockP) {
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

        fragment_diaog_message.setText("VOULEZ VOUS VRAIMENT VALIDER VOTRE RELEVE DE STOCK");
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
                for(int i = 0;i<stockPLignes.size();i++){
                    stockPLignes.get(i).setSTOCK_CODE(stockP.getSTOCK_CODE());
                    stockPLigneManager.add(stockPLignes.get(i));
                }

                stockPManager.add(stockP);

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date_visite=df.format(Calendar.getInstance().getTime());

                visiteManager.updateVisite_VRDF(ClientActivity.visiteCourante.getVISITE_CODE(),1,date_visite);

                alertDialog.dismiss();

                if(isNetworkAvailable()){
                    Toast.makeText(getApplicationContext(),"Synchronisation en cours",Toast.LENGTH_SHORT).show();
                    stockPManager.synchronisationStockP(getApplicationContext());
                    stockPLigneManager.synchronisationStockPLigne(getApplicationContext());
                }

                Intent intent = new Intent(ReleveStockActivity.this,ClientActivity.class);
                startActivity(intent);
                finish();

            }
        });
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
