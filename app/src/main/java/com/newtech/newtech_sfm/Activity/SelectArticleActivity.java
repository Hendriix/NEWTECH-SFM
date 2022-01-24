package com.newtech.newtech_sfm.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newtech.newtech_sfm.Configuration.RVAdapter;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.Famille;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.Metier_Manager.FamilleManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by stagiaireit2 on 28/07/2016.
 */
public class SelectArticleActivity extends Activity {
    public static TextView total;
    public static ImageView terminer;
    public static HashMap<String,List<Article>> listArticleParFamilleCode = new HashMap<String, List<Article>>();
    public static Spinner dropdown ;

    private String visite_Code;
    private String client_Code;
    private String tournee_Code;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_article);

        Intent intentClient=getIntent();


        total= (TextView) findViewById(R.id.total);
        terminer =(ImageView)findViewById(R.id.btn_terminer);



        final ArticleManager articleManager = new ArticleManager(getApplicationContext());


        ArrayList<String> items= new ArrayList<>() ;
        FamilleManager familleManager = new FamilleManager(getApplicationContext());
        ArrayList<Famille> itemsFamille=  familleManager.getFamille_textList();



        for(int j=0;j<itemsFamille.size();j++){
            items.add(itemsFamille.get(j).getFAMILLE_NOM());
            listArticleParFamilleCode.put(itemsFamille.get(j).getFAMILLE_NOM(),articleManager.getListByFamilleCode(itemsFamille.get(j).getFAMILLE_CODE()));
        }

        //items.add(0,"tous");
        dropdown = (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,items );
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String familleCode = parent.getItemAtPosition(position).toString();
                RecyclerView rv = (RecyclerView)findViewById(R.id.rv2);
                rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                List<Article> listArticleCatalogue = new ArrayList<Article>();


                 if(!listArticleParFamilleCode.containsKey(familleCode)) {

                     if(familleCode.equals("tous")){
                         for(int i=0; i<listArticleParFamilleCode.size();i++){
                             listArticleCatalogue.addAll(listArticleParFamilleCode.get(i));
                         }
                     }
                    listArticleCatalogue=articleManager.getListByFamilleCode(familleCode);
                }
                else listArticleCatalogue= listArticleParFamilleCode.get(familleCode);


                RVAdapter adapterArticle = new RVAdapter(listArticleCatalogue);
                rv.setAdapter(adapterArticle);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        setTitle("Selection d'articles");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(this, AuthActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                return true;

            case R.id.plus:

                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        listArticleParFamilleCode.clear();
        Intent i = new Intent(this, PanierActivity.class);
        //i.putExtra("TOURNEE_CODE",tournee_Code);
        //i.putExtra("CLIENT_CODE",client_Code);
        //i.putExtra("VISITE_CODE",visite_Code);
        startActivity(i);
        finish();
    }
}


