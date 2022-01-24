package com.newtech.newtech_sfm.TableauBordClient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.newtech.newtech_sfm.Activity.AuthActivity;
import com.newtech.newtech_sfm.Activity.ClientActivity;
import com.newtech.newtech_sfm.Activity.Client_Manager;
import com.newtech.newtech_sfm.Activity.PrintActivity2;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.TbClient;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.recensement.RecensementActivity;

import java.util.ArrayList;

/**
 * Created by TONPC on 20/09/2017.
 */

public class TableauBordClientActivity extends AppCompatActivity implements TableauBordClientPresenter.TableauBordClientView{


    TableauBordClientAdapter tableauBordClientAdapter;
    TableauBordClientPresenter tableauBordClientPresenter;
    ArrayList<Article> articles = new ArrayList<Article>();
    ListView mListView1;
    ArticleManager articleManager;
    public static String client_code = "";
    public static String source = "";
    private ProgressDialog progressDialog;

    TextView ca_tv,obj_tv,rea_tv,choufouni_tv;

    ViewPager viewPager;
    TableauBordClientPagerAdapter tableauBordClientPagerAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tableau_bord_client_layout);
        articleManager = new ArticleManager(getApplicationContext());
        tableauBordClientPresenter =  new TableauBordClientPresenter(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initProgressDialog();
        //mListView1=(ListView) findViewById(R.id.tableau_bord_client_lv);
        viewPager = findViewById(R.id.viewpager);

        ca_tv = findViewById(R.id.ca_tv);
        obj_tv = findViewById(R.id.obj_tv);
        rea_tv = findViewById(R.id.rea_tv);
        choufouni_tv = findViewById(R.id.choufouni_tv);

        articles = articleManager.getList();

        //tableauBordClientAdapter=new TableauBordClientAdapter(this,articles);
        //mListView1.setAdapter(tableauBordClientAdapter);

        tableauBordClientPresenter.getTableauBordClient(getApplicationContext(),client_code);


        setTitle("TABLEAU DE BORD CLIENT");

    }

    public boolean onQueryTextSubmit(String query) {
        return false;
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
                Intent i = new Intent(TableauBordClientActivity.this, AuthActivity.class);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_client, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent();
        if(source.equals("ClientActivity")){
            i = new Intent(this, ClientActivity.class);
        }else{
            i = new Intent(this, RecensementActivity.class);
        }
        startActivity(i);
        finish();
    }

    @Override
    public void showSuccess(TbClient tbClient) {
        progressDialog.dismiss();

        ca_tv.setText(String.valueOf(tbClient.getCHIFFRE_AFFAIRE())+ " DH");
        obj_tv.setText(String.valueOf(tbClient.getOBJECTIF()));
        rea_tv.setText(String.valueOf(tbClient.getREALISATION())+ " T");
        choufouni_tv.setText(String.valueOf(tbClient.getCHOUFOUNI())+ "XXX");

        tableauBordClientPagerAdapter = new TableauBordClientPagerAdapter(TableauBordClientActivity.this,getApplicationContext(), tbClient.getTbClientVisites());
        viewPager.setAdapter(tableauBordClientPagerAdapter);
        viewPager.setPageMargin(20);

        showMessage("SUCCESS");
    }

    @Override
    public void showError(String message) {
        progressDialog.dismiss();
        showMessage(message);
    }

    @Override
    public void showEmpty(String message) {
        progressDialog.dismiss();
        showMessage(message);
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    private void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    private void initProgressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Chargement en cours");
    }

}
