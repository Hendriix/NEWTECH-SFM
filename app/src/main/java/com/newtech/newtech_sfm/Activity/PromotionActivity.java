package com.newtech.newtech_sfm.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.newtech.newtech_sfm.Metier.Menu;
import com.newtech.newtech_sfm.R;

/**
 * Created by TONPC on 12/07/2017.
 */

public class PromotionActivity extends AppCompatActivity {

    ListView mListView;
    Menu[] Rapports3={
            new Menu("M1","Promotions", "Promotions" ,""),
            new Menu("M2","Promotion article", "PromotionArticle" ,""),
            new Menu("M3","Promotion palier", "PromotionPalier" ,""),
            new Menu("M4","Commande gratuite", "CommandeGratuiteActivity" ,""),
            new Menu("M5","Commande promotion", "CommandePromotionActivity" ,"")
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion);
        setTitle("PROMOTION");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView = (ListView) findViewById(R.id.listpromotion);

        ArrayAdapter<Menu> mAdapterRapport = new ArrayAdapter<Menu>(PromotionActivity.this,android.R.layout.simple_list_item_1, Rapports3);
        mListView.setAdapter(mAdapterRapport);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Menu ClickedMenu=Rapports3[i];

                Class<?> className = null;
                try {
                    className = Class.forName(getApplicationContext().getPackageName()+".Activity."+ClickedMenu.getLIEN().toString());

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                Intent intent =new Intent(PromotionActivity.this,className);
                startActivity(intent);

                //Toast.makeText(RapportActivity.this,ClickedMenu.getLIEN().toString()+getApplicationContext().getPackageName().toString() , Toast.LENGTH_LONG).show();

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
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
                Intent i = new Intent(PromotionActivity.this, AuthActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                return true;

            case R.id.option2:
                Intent intt = new Intent(PromotionActivity.this, PrintActivity2.class);
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

        Intent i = new Intent(PromotionActivity.this, ClientActivity.class);
        startActivity(i);
        finish();
    }
}
