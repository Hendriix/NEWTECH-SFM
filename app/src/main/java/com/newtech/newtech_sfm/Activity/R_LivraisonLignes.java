package com.newtech.newtech_sfm.Activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.LivraisonGratuite;
import com.newtech.newtech_sfm.Metier.LivraisonLigne;
import com.newtech.newtech_sfm.Metier.Unite;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonGratuiteManager;
import com.newtech.newtech_sfm.Metier_Manager.LivraisonLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.UniteManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

public class R_LivraisonLignes extends AppCompatActivity {

    public static String livraison_code = "";

    LivraisonLigneManager livraisonLigneManager;
    LivraisonGratuiteManager livraisonGratuiteManager;
    UniteManager uniteManager;
    ArticleManager articleManager;

    ArrayList<LivraisonLigne> livraisonLignes;
    ArrayList<LivraisonGratuite> livraisonGratuites;

    TextView activity_title_tv;

    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande_lignes);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TableLayout livraison_ligne_details_table= (TableLayout)findViewById(R.id.commande_details_tablelayout);
        TableLayout livraison_gratuite_details_table= (TableLayout)findViewById(R.id.commande_gratuite_details_tablelayout);

        activity_title_tv = findViewById(R.id.activity_title_tv);
        activity_title_tv.setText("LIVRAISON_LIGNES");
        livraisonLigneManager = new LivraisonLigneManager(getApplicationContext());
        livraisonGratuiteManager = new LivraisonGratuiteManager(getApplicationContext());
        uniteManager = new UniteManager(getApplicationContext());
        articleManager = new ArticleManager(getApplicationContext());

        livraisonLignes = livraisonLigneManager.getListByLivraisonCode(livraison_code);
        livraisonGratuites = livraisonGratuiteManager.getListbyLivCode(livraison_code);

        context = getApplicationContext();

        for(int i=0;i<livraisonLignes.size();i++){

            LivraisonLigne livraisonLigne = livraisonLignes.get(i);
            TableRow ligne = new TableRow(this);

            TextView ligne_code = new TextView(this);
            TextView art_code = new TextView(this);
            TextView unite = new TextView(this);
            TextView qte = new TextView(this);

            Unite unite1 = uniteManager.get(livraisonLigne.getUNITE_CODE());
            Article article = articleManager.get(livraisonLigne.getARTICLE_CODE());

            ligne_code.setEllipsize(TextUtils.TruncateAt.END);
            unite.setEllipsize(TextUtils.TruncateAt.END);

            ligne_code.setGravity(Gravity.CENTER);
            art_code.setGravity(Gravity.CENTER);
            unite.setGravity(Gravity.CENTER);
            qte.setGravity(Gravity.CENTER);

            ligne_code.setTextSize(15);
            art_code.setTextSize(15);
            unite.setTextSize(15);
            qte.setTextSize(15);

            ligne_code.setBackgroundResource(R.drawable.cell_shape);
            art_code.setBackgroundResource(R.drawable.cell_shape);
            unite.setBackgroundResource(R.drawable.cell_shape);
            qte.setBackgroundResource(R.drawable.cell_shape);

            ligne_code.setText(String.valueOf(livraisonLigne.getLIVRAISONLIGNE_CODE()));
            art_code.setText(article.getARTICLE_DESIGNATION1());
            unite.setText(unite1.getUNITE_NOM());
            qte.setText(String.valueOf(livraisonLigne.getQTE_LIVREE()));
            //total.setText(String.valueOf(new DecimalFormat("##.##").format(ListeLivraisonLigne.get(i).getARTICLE_PRIX()*ListeLivraisonLigne.get(i).getQTE_COMMANDEE())));

            ligne.addView(ligne_code);
            ligne.addView(art_code);
            ligne.addView(unite);
            ligne.addView(qte);

            livraison_ligne_details_table.addView(ligne);

            //panierTotal+=ListeLivraisonLigne.get(i).getARTICLE_PRIX()*ListeLivraisonLigne.get(i).getQTE_COMMANDEE();
        }

        for(int i=0;i<livraisonGratuites.size();i++){

            LivraisonGratuite livraisonGratuite = livraisonGratuites.get(i);

            TableRow ligne = new TableRow(this);

            TextView art_code = new TextView(this);
            TextView qte = new TextView(this);

            //Unite unite1 = uniteManager.get(livraisonLigne.getUNITE_CODE());
            Article article = articleManager.get(livraisonGratuite.getARTICLE_CODE());


            art_code.setEllipsize(TextUtils.TruncateAt.END);
            qte.setEllipsize(TextUtils.TruncateAt.END);

            art_code.setGravity(Gravity.CENTER);
            qte.setGravity(Gravity.CENTER);

            art_code.setTextSize(15);
            qte.setTextSize(15);

            art_code.setBackgroundResource(R.drawable.cell_shape);
            qte.setBackgroundResource(R.drawable.cell_shape);

            art_code.setText(article.getARTICLE_DESIGNATION1());
            qte.setText(String.valueOf(livraisonGratuite.getQUANTITE()));


            ligne.addView(art_code);
            ligne.addView(qte);

            livraison_gratuite_details_table.addView(ligne);

            //panierTotal+=ListeLivraisonLigne.get(i).getARTICLE_PRIX()*ListeLivraisonLigne.get(i).getQTE_COMMANDEE();
        }


        setTitle("DETAILS LIVRAISONS");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
