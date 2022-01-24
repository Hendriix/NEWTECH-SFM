package com.newtech.newtech_sfm.Activity;

import static com.newtech.newtech_sfm.R.id.search_client;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newtech.newtech_sfm.Configuration.Client_Adapter;
import com.newtech.newtech_sfm.Configuration.ListDataSave;
import com.newtech.newtech_sfm.DialogClientFiltre.DialogClientFiltre;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier_Manager.ClientManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by stagiaireit2 on 26/07/2016.
 */
public class CatalogueClientActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView mSearchView;
    Client_Adapter client_adapter;
    List<Client> client_list = new ArrayList<Client>();
    RecyclerView rv;
    ListView mListView1;
    SearchManager searchmanager;

    DialogClientFiltre dialogClientFiltre;
    Button filter_btn;
    ClientManager client_manager;

    ListDataSave listDataSave;

    String tournee,type,classe,categorie;
    boolean filter_actif = false;

    public static String tournee_code="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_catalogue);

        dialogClientFiltre = dialogClientFiltre.newInstance(getApplicationContext(),CatalogueClientActivity.this,"CatalogueClientActivity","");
        filter_btn = findViewById(R.id.filter_btn);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchmanager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView=(SearchView) findViewById(search_client);
        client_manager = new ClientManager(getApplicationContext());
        listDataSave = new ListDataSave(getApplicationContext(),"MyPref");


        client_list = listDataSave.getDataList("client_list");

        if(client_list.size() <= 0){
            if(tournee_code.equals("tous")){
                client_list=client_manager.getList();
            }else{
                client_list=client_manager.getListByTourneCode(tournee_code);
            }
        }

        initListView();
        client_adapter=new Client_Adapter(this,client_list,getApplicationContext());
        initListViewAdapter(client_adapter);



        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                dialogClientFiltre.show(fragmentManager, "Dialog");

            }
        });



        setTitle("CLIENTS");

    }
    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        //mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("SEARCH HERE");

    }
    public boolean onQueryTextChange(String newText) {

        String textfilter=mSearchView.getQuery().toString().toLowerCase(Locale.getDefault());
        client_adapter.filter(textfilter);
        //Toast.makeText(this,textfilter, Toast.LENGTH_LONG).show();

        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_client, menu);
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
                Intent i = new Intent(CatalogueClientActivity.this, AuthActivity.class);
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

    public void showFilteredClient(ArrayList<Client> clientArrayList){

        dialogClientFiltre.dismiss();
        listDataSave.setDataList("client_list", clientArrayList);
        filter_btn.setBackgroundResource(R.drawable.filter_actif);

        client_list = clientArrayList;

        client_adapter=new Client_Adapter(this,client_list,getApplicationContext());

        mListView1.setAdapter(client_adapter);
        mListView1.setTextFilterEnabled(true);
        setupSearchView();

        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Client client =(Client)client_adapter.getItem(position);
                //Intent intent=new Intent(getApplicationContext(),ClientActivity.class);
                //intent.putExtra("CLIENT_CODE",client.getCLIENT_CODE());
                //startActivity(intent);
            }
        });

    }

    public void clearClientFilter(){

        dialogClientFiltre.dismiss();
        filter_btn.setBackgroundResource(R.drawable.filter_inactif);
        if(tournee_code.equals("tous")){
            client_list=client_manager.getList();
        }else{
            client_list=client_manager.getListByTourneCode(tournee_code);
        }

        client_adapter=new Client_Adapter(this,client_list,getApplicationContext());
        mListView1.setAdapter(client_adapter);
        client_adapter.notifyDataSetChanged();
    }

    private void initListView(){
        View child = getLayoutInflater().inflate(R.layout.no_data_found, null);
        ((ViewGroup)mListView1.getParent()).addView(child);
        mListView1.setEmptyView(child);
        mListView1.setTextFilterEnabled(true);
        setupSearchView();
    }

    private void initListViewAdapter(BaseAdapter mBaseAdapter){

        mListView1.setAdapter(mBaseAdapter);

        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Client client =(Client)mBaseAdapter.getItem(position);
                //Intent intent=new Intent(getApplicationContext(),ClientActivity.class);
                //intent.putExtra("CLIENT_CODE",client.getCLIENT_CODE());
                //startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listDataSave.remove("client_list");
    }
}
