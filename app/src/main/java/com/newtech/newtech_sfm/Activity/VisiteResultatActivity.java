package com.newtech.newtech_sfm.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.newtech.newtech_sfm.Configuration.VisiteResultat_Adapter;
import com.newtech.newtech_sfm.Metier.Visite;
import com.newtech.newtech_sfm.Metier.VisiteResultat;
import com.newtech.newtech_sfm.Metier_Manager.ClientManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteResultatManager;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.livraisoncnc.LivraisonDateActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by TONPC on 22/02/2017.
 */

    public class VisiteResultatActivity extends AppCompatActivity {

    ListView vrListView;
    VisiteResultat_Adapter vr_Adapter;
    List<VisiteResultat> visiteResultat_list = new ArrayList<VisiteResultat>();
    Button vrButton;
    RadioButton vrRadioButton;
    Visite visite= new Visite();
    private String ButtonValue;
    private int visiteResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visiteresultat_activity);

        Intent intent = getIntent();
        final String client_Code=ClientActivity.clientCourant.getCLIENT_CODE();
        final String tournee_code=ClientActivity.clientCourant.getTOURNEE_CODE();
        final String visite_code=ClientActivity.visiteCourante.getVISITE_CODE();

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_vr);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        VisiteResultatManager visiteResultatManager = new VisiteResultatManager(getApplicationContext());
        visiteResultat_list = visiteResultatManager.getListNotOk();

        vr_Adapter = new VisiteResultat_Adapter(this,visiteResultat_list);
        vrListView = (ListView) findViewById(R.id.vr_listView);
        vrButton = (Button) findViewById(R.id.vr_buttonvalider);

        ClientManager client_Manager = new ClientManager(getApplicationContext());
        ClientActivity.clientCourant=client_Manager.get(client_Code);

        final VisiteManager visite_Manager = new VisiteManager(getApplicationContext());
        ClientActivity.visiteCourante = visite_Manager.get(visite_code);


        vrListView.setAdapter(vr_Adapter);


       vrListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                vr_Adapter.setSelectedIndex(position);
                vr_Adapter.notifyDataSetChanged();



                final VisiteResultat visiteresultat =(VisiteResultat) vr_Adapter.getItem(position);
                visiteResult = Integer.parseInt(visiteresultat.getVISITERESULTAT_CODE());


            }
        });

        vrButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if(vr_Adapter.getSelectedIndex()!= -1)
                {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date_visite=df.format(Calendar.getInstance().getTime());

                    Log.d("Visite resultat", "onClick: "+visiteResult);

                    visite_Manager.updateVisite_VRDF(visite_code,visiteResult,date_visite);

                    if(isNetworkAvailable()){
                        visite_Manager.synchronisationVisite(getApplicationContext());
                    }else{
                        Toast.makeText(VisiteResultatActivity.this,"Vérifier votre connexion internet puis synchroniser",Toast.LENGTH_LONG).show();

                    }

                    if(ClientActivity.commande_source.equals("VISITE") || ClientActivity.commande_source.equals("ENCAISSEMENT")){

                        //ClientActivity.commande_source="";
                        Intent intent=new Intent(getApplicationContext(),VisiteActivity.class);

                        intent.putExtra("VISITE_RESULTAT",visiteResult);
                        intent.putExtra("TOURNEE_CODE",ClientActivity.clientCourant.getTOURNEE_CODE());
                        intent.putExtra("CLIENT_CODE",ClientActivity.clientCourant.getCLIENT_CODE());

                        ClientActivity.clientCourant=null;
                        ClientActivity.commande_source=null;
                        //Toast.makeText(VisiteResultatActivity.this,"VISITE TACHE",
                               // Toast.LENGTH_LONG).show();

                        ClientActivity.visite_code=null;
                        ClientActivity.distributeur_code=null;
                        ClientActivity.utilisateur_code=null;
                        ClientActivity.tournee_code=null;
                        ClientActivity.visiteCourante=null;

                        startActivity(intent);
                        finish();

                    }else if(ClientActivity.commande_source.equals("LIVRAISON")) {
                        //ClientActivity.commande_source="";
                        Intent intent=new Intent(getApplicationContext(), LivraisonDateActivity.class);

                        intent.putExtra("VISITE_RESULTAT",visiteResult);
                        intent.putExtra("TOURNEE_CODE",ClientActivity.clientCourant.getTOURNEE_CODE());
                        intent.putExtra("CLIENT_CODE",ClientActivity.clientCourant.getCLIENT_CODE());

                        ClientActivity.clientCourant=null;
                        ClientActivity.commande_source=null;
                        //Toast.makeText(VisiteResultatActivity.this,"VISITE TACHE",
                        // Toast.LENGTH_LONG).show();

                        ClientActivity.visite_code=null;
                        ClientActivity.distributeur_code=null;
                        ClientActivity.utilisateur_code=null;
                        ClientActivity.tournee_code=null;
                        ClientActivity.visiteCourante=null;

                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(VisiteResultatActivity.this, "ACTIVITE SOURCE INCONNU", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(VisiteResultatActivity.this,"MERCI DE CHOISIR UN RESULTAT",
                            Toast.LENGTH_LONG).show();
                }



            }
        });

        setTitle(ClientActivity.clientCourant.getCLIENT_NOM());

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
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
        Intent i = new Intent(this, ClientActivity.class);
        //i.putExtra("CLIENT_CODE",ClientActivity.clientCourant.getCLIENT_CODE());
        //i.putExtra("VISITE_CODE",ClientActivity.visiteCourante.getVISITE_CODE());
        //i.putExtra("TOURNEE_CODE",tournee_code);
        startActivity(i);
        finish();
    }
}
