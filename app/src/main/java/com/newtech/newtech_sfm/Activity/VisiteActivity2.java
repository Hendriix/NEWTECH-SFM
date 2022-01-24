package com.newtech.newtech_sfm.Activity;

import static com.newtech.newtech_sfm.R.id.visite_search_client;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.newtech.newtech_sfm.Configuration.Client_Adapter;
import com.newtech.newtech_sfm.Configuration.Livraison_Adapter;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier_Manager.ClientManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeNonClotureeManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteManager;
import com.newtech.newtech_sfm.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by stagiaireit2 on 26/07/2016.
 */
public class VisiteActivity2 extends AppCompatActivity implements SearchView.OnQueryTextListener {
    public static Client clientCourant=new Client();
    public static String activity_source="";
    public static String tache_code="";
    public static String tournee_code="";
    public String client_code="";
    public static String commande_source="";
    public static String affectation_type="";
    public static String affectation_valeur="";

    ClientManager client_manager ;

    SearchView mSearchView;
    Client_Adapter client_adapter;
    Livraison_Adapter livraison_adapter;

    List<Client> client_list = new ArrayList<Client>();
    List<Client> clientsWithoutVisits= new ArrayList<Client>();
    List<Client> clientProches = new ArrayList<>();
    RecyclerView rv;
    ListView mListView1;
    SearchManager searchmanager;

    private ProgressBar mProgressBar;
    private TextView vaTextView1;
    private TextView vaTextView2;
    private CheckBox vaCheckBox1;
    private CheckBox vaCheckBox2;

    private double NombreClient;
    private double NombreVisiteClient;
    private double ProgressionStatut;
    private int ClientRestants;

    private static Double latitude;
    private static Double longitude;
    private static float accuracy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.visite_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_client);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        client_manager = new ClientManager(getApplicationContext());

        searchmanager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView=(SearchView) findViewById(visite_search_client);
        mProgressBar = (ProgressBar) findViewById(R.id.va_progressBar1);
        vaCheckBox1=(CheckBox) findViewById(R.id.va_checkBox1);
        vaCheckBox2=(CheckBox) findViewById(R.id.va_checkBox2);
        mListView1=(ListView) findViewById(R.id.visite_listview1);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String DateVisiteAS = sdf.format(new Date());
        VisiteManager visite_manager=new VisiteManager(getApplicationContext());

        //double positionLatitude = Gpstrackerservice.latitude;
        //double positionLongitude = Gpstrackerservice.longitude;

        if(commande_source.equals("VISITE")){

            Toast.makeText(VisiteActivity2.this,"VISITE",
                    Toast.LENGTH_LONG).show();

            Log.d("visite", "onCreate: "+"visite");

            if(affectation_valeur.equals("tous")){
                client_list=client_manager.getList();
            }else{
                client_list=client_manager.getListByTourneCode(affectation_valeur);
            }

            Log.d("Client", "onCreate: "+client_list.toString());


            //clientProches = client_manager.getClientProches(client_list);

            NombreClient=client_manager.GetClientNombreByTourneeCode(affectation_valeur);
            clientsWithoutVisits=client_manager.getListWithVisite(affectation_valeur,DateVisiteAS);
            NombreVisiteClient=visite_manager.GetVisiteNombreByTourneeDate(affectation_valeur,DateVisiteAS);
            client_adapter=new Client_Adapter(this,client_list,getApplicationContext());
            mListView1.setAdapter(client_adapter);

            mListView1.setTextFilterEnabled(true);
            setupSearchView();

            mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Client client =(Client)client_adapter.getItem(position);
                    ClientActivity.visiteCourante=null;
                    ClientActivity.clientCourant=client_manager.get(client.getCLIENT_CODE());
                    ClientActivity.commande_source= commande_source;
                    Intent intent=new Intent(getApplicationContext(),ClientActivity.class);
                    //intent.putExtra("TACHE_CODE",tache_code);
                    startActivity(intent);
                    finish();
                }
            });



        }else if(commande_source.equals("LIVRAISON")){

            Toast.makeText(VisiteActivity2.this,"LIVRAISON",
                    Toast.LENGTH_LONG).show();
            CommandeNonClotureeManager commandeNonClotureeManager = new CommandeNonClotureeManager(getApplicationContext());
            Log.d("getListClNL", "onCreate: "+commandeNonClotureeManager.getListView());

            Log.d("getListClNL", "onCreate type: "+affectation_type);
            Log.d("getListClNL", "onCreate valeurs: "+affectation_valeur);

            if(affectation_type.equals("VENDEUR")){

                //client_list=client_manager.getListClNLByVC(affectation_valeur);
                Log.d("getListClNLByVC", "onCreate: "+client_list.size());
                Log.d("getListClNLByVC", "onCreate: "+client_list.toString());

            }else if(affectation_type.equals("TOURNEE")){

                //client_list=client_manager.getListClNLByTC(affectation_valeur);
                Log.d("getListClNLByTC", "onCreate: "+client_list.size());
                Log.d("getListClNLByTC", "onCreate: "+client_list.toString());
            }


            //Log.d("clientNL", "onCreate: "+client_list.toString());
            //CommandeNonClotureeManager commandeNonClotureeManager = new CommandeNonClotureeManager(getApplicationContext());
            //Log.d("clientNL", "onCreate: "+commandeNonClotureeManager.getListView());

            //clientProches = client_manager.getClientProches(client_list);

            NombreClient=client_list.size();
            NombreVisiteClient=visite_manager.GetVisiteNombreByDate(DateVisiteAS);
            clientsWithoutVisits=client_manager.getListWithoutVisiteCmdaL(DateVisiteAS);

            livraison_adapter=new Livraison_Adapter(this,client_list);
            mListView1.setAdapter(livraison_adapter);

            mListView1.setTextFilterEnabled(true);
            setupSearchView();

            mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Client client =(Client)livraison_adapter.getItem(position);
                    ClientActivity.visiteCourante=null;
                    ClientActivity.clientCourant=client_manager.get(client.getCLIENT_CODE());
                    ClientActivity.commande_source= commande_source;
                    Intent intent=new Intent(getApplicationContext(),ClientActivity.class);
                    //intent.putExtra("TACHE_CODE",tache_code);
                    startActivity(intent);
                    finish();
                }
            });

        }else if(commande_source.equals("ENCAISSEMENT")){

            Toast.makeText(VisiteActivity2.this,"ENCAISSEMENT",
                    Toast.LENGTH_LONG).show();
            //Log.d("livraison", "onCreate: "+client_manager.getListByCmdAL());
            CommandeNonClotureeManager commandeNonClotureeManager = new CommandeNonClotureeManager(getApplicationContext());
            Log.d("getListClNL", "onCreate: "+commandeNonClotureeManager.getListView());

            Log.d("getListClNL", "onCreate type: "+affectation_type);
            Log.d("getListClNL", "onCreate valeurs: "+affectation_valeur);

            if(affectation_type.equals("VENDEUR")){

                client_list=client_manager.getListClNEByVC(affectation_valeur);
                Log.d("getListClNEByVC", "onCreate: "+client_list.size());
                Log.d("getListClNEByVC", "onCreate: "+client_list.toString());

            }else if(affectation_type.equals("TOURNEE")){

                client_list=client_manager.getListClNEByTC(affectation_valeur);
                Log.d("getListClNEByTC", "onCreate: "+client_list.size());
                Log.d("getListClNEByTC", "onCreate: "+client_list.toString());

            }


            //clientProches = client_manager.getClientProches(client_list);


            NombreClient=client_list.size();
            NombreVisiteClient=visite_manager.GetVisiteNombreByDate(DateVisiteAS);
            clientsWithoutVisits=client_manager.getListWithoutVisiteCmdaL(DateVisiteAS);

            livraison_adapter=new Livraison_Adapter(this,client_list);
            mListView1.setAdapter(livraison_adapter);

            mListView1.setTextFilterEnabled(true);
            setupSearchView();

            mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Client client =(Client)livraison_adapter.getItem(position);
                    ClientActivity.visiteCourante=null;
                    ClientActivity.clientCourant=client_manager.get(client.getCLIENT_CODE());
                    ClientActivity.commande_source= commande_source;
                    Intent intent=new Intent(getApplicationContext(),ClientActivity.class);
                    //intent.putExtra("TACHE_CODE",tache_code);
                    startActivity(intent);
                    finish();
                }
            });
        }else{

            Toast.makeText(VisiteActivity2.this,"AUTRE",
                    Toast.LENGTH_LONG).show();
        }

        vaTextView1=(TextView) findViewById(R.id.va_textView1);
        vaTextView1.setText((int)NombreVisiteClient+"/"+(int)NombreClient+" CLIENTS VISITES");


        mProgressBar=(ProgressBar) findViewById(R.id.va_progressBar1);
        ProgressionStatut=(NombreVisiteClient/NombreClient)*100;

        ClientRestants =(int)(NombreClient-NombreVisiteClient);

        if(NombreVisiteClient>0 && ProgressionStatut<100){

            mProgressBar.setProgress((int)ProgressionStatut);

            Toast.makeText(VisiteActivity2.this,"IL VOUS RESTE ENCORE "+String.valueOf(ClientRestants)+" CLIENTS",
                    Toast.LENGTH_LONG).show();
        }

        if(ProgressionStatut==100){

            mProgressBar.setProgress((int)ProgressionStatut);

            Toast.makeText(VisiteActivity2.this,"VOUS AVEZ FINI VOS VISITES DES CLIENTS",
                    Toast.LENGTH_LONG).show();


        }

        setTitle("CLIENTS VISITES");

    }
    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        //mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("SEARCH HERE");

    }

    public boolean onQueryTextChange(String newText) {

        String textfilter=mSearchView.getQuery().toString().toLowerCase(Locale.getDefault());
        if(commande_source.equals("VISITE")){
            client_adapter.filter(textfilter);

        }else if(commande_source.equals("LIVRAISON")){
            livraison_adapter.filter(textfilter);
        }
        //Toast.makeText(this,textfilter, Toast.LENGTH_LONG).show();
        return true;
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        int variable;
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.va_checkBox1:
                if(commande_source.equals("VISITE")){
                    if (checked){
                        variable=1;
                        client_adapter.filterClientRestant(variable,clientsWithoutVisits );

                    }else{
                        variable=0;
                        client_adapter.filterClientRestant(variable,clientsWithoutVisits );
                    }
                    // Remove the meat
                    break;
                }else if(commande_source.equals("LIVRAISON")){
                    if (checked){
                        variable=1;
                        livraison_adapter.filterClientRestant(variable,clientsWithoutVisits );

                    }else{
                        variable=0;
                        livraison_adapter.filterClientRestant(variable,clientsWithoutVisits );
                    }
                    // Remove the meat
                    break;
                }

            case R.id.va_checkBox2:

                if (checked){
                    clientProches = client_manager.getClientProches(client_list,latitude,longitude,this);
                    variable=1;
                    client_adapter.filterClientProche(variable,clientProches );

                }else{
                    clientProches = client_manager.getClientProches(client_list,latitude,longitude,this);
                    variable=0;
                    client_adapter.filterClientProche(variable,clientProches );
                }
        }
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_client, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            case R.id.add_client:
                Intent intent=new Intent(this,Client_Manager.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //finish();
                return true;
            case R.id.logout:
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(VisiteActivity2.this, AuthActivity.class);
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

        if(activity_source=="CatalogueTacheActivity"){
            //Intent i = new Intent(this, CatalogueTourneeActivity.class);

            VisiteActivity2.activity_source = null;
            VisiteActivity2.commande_source = null;
            VisiteActivity2.tache_code = null;
            VisiteActivity2.affectation_type = null;
            VisiteActivity2.affectation_valeur = null;

            Intent i = new Intent(this, CatalogueTacheActivity.class);
            startActivity(i);
            finish();

        }else{
            VisiteActivity2.tournee_code = null;
            VisiteActivity2.affectation_valeur = null;
            Intent i = new Intent(this, TourneeActivity.class);
            startActivity(i);
            finish();
        }
    }

}
