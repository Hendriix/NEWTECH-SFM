package com.newtech.newtech_sfm.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.newtech.newtech_sfm.Configuration.Catalogue_article_view_Adapter;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.Famille;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.Metier_Manager.FamilleManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by stagiaireit2 on 29/06/2016.
 */
public class CatalogueActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    public static HashMap<String,List<Article>> listArticleParFamilleCode = new HashMap<String, List<Article>>();
    ArticleManager articleManager;
    Spinner spinner_catalogue_article;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalogue_article_view);

        articleManager = new ArticleManager(getApplicationContext());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<String> items= new ArrayList<>() ;
        FamilleManager familleManager = new FamilleManager(getApplicationContext());
        ArrayList<Famille> itemsFamille=  familleManager.getFamille_textList();

        Log.d("catalogue itemsfamille", "onCreate: "+itemsFamille.toString());

        for(int j=0;j<itemsFamille.size();j++){
            items.add(itemsFamille.get(j).getFAMILLE_NOM());
            listArticleParFamilleCode.put(itemsFamille.get(j).getFAMILLE_NOM(),articleManager.getListByFamilleCode(itemsFamille.get(j).getFAMILLE_CODE()));
        }

        Log.d("catalogue", "onCreate: "+listArticleParFamilleCode.toString());

        spinner_catalogue_article = (Spinner)findViewById(R.id.famille_catalogue_spinner);
        mListView= (ListView) findViewById(R.id.catalogue_article_listview1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,items );
        spinner_catalogue_article.setAdapter(adapter);

       /* if(items.size()==0){

            View child = getLayoutInflater().inflate(R.layout.no_data_found, null);
            ((ViewGroup)mListView.getParent()).addView(child);

            mListView.setEmptyView(child);

        }*/
       //mListView.removeAllViews();

        spinner_catalogue_article.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String familleCode = parent.getItemAtPosition(position).toString();

                List<Article> listArticleCatalogue = new ArrayList<Article>();

                if(!listArticleParFamilleCode.containsKey(familleCode)) {

                    listArticleCatalogue=articleManager.getListByFamilleCode(familleCode);
                }
                else {
                    listArticleCatalogue= listArticleParFamilleCode.get(familleCode);
                }

                Catalogue_article_view_Adapter catalogue_article_view_adapter= new Catalogue_article_view_Adapter(CatalogueActivity.this, listArticleCatalogue);



                mListView.setAdapter(catalogue_article_view_adapter);

                if(listArticleCatalogue.size()==0){

                    View child = getLayoutInflater().inflate(R.layout.no_data_found, null);
                    ((ViewGroup)mListView.getParent()).addView(child);

                    mListView.setEmptyView(child);
                }

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    }
                });

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }


        });


        setTitle("CATALOGUE ARTICLES");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                Intent i = new Intent(CatalogueActivity.this, AuthActivity.class);
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
        Intent i = new Intent(CatalogueActivity.this, MenuActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}