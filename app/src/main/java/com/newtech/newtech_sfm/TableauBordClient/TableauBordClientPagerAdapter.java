package com.newtech.newtech_sfm.TableauBordClient;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.TbClientVisite;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

public class TableauBordClientPagerAdapter extends PagerAdapter {

    Context context;
    ArrayList<TbClientVisite> pager;
    TableauBordClientAdapter tableauBordClientAdapter;
    ArrayList<Article> articles;
    ArticleManager articleManager;
    Activity activity;

    public TableauBordClientPagerAdapter(Activity activity,Context context, ArrayList<TbClientVisite> pager) {
        this.context = context;
        this.pager = pager;
        this.activity = activity;
        this.articleManager = new ArticleManager(context);
            }

    @Override
    public int getCount() {
        return pager.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public  Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.tableau_bord_client_ligne, container, false);

        TbClientVisite tbClientVisite = pager.get(position);
        ListView tableau_bord_client_lv = (ListView) view.findViewById(R.id.tableau_bord_client_lv);
        TextView visiteDate_tv = view.findViewById(R.id.visite_date_tv);
        TextView visite_resulta_tv = view.findViewById(R.id.visite_resulta_tv);
        TextView visite_ca_tv = view.findViewById(R.id.visite_ca_tv);
        TextView visite_tonnage_tv = view.findViewById(R.id.visite_tonnage_tv);

        visiteDate_tv.setText(tbClientVisite.getVISITE_DATE());
        visite_resulta_tv.setText(tbClientVisite.getINACTIF());
        visite_ca_tv.setText(tbClientVisite.getCHIFFRE_AFFAIRE() + " DH");
        visite_tonnage_tv.setText(tbClientVisite.getREALISATION() + " T");

        articles = articleManager.getList();

        tableauBordClientAdapter = new TableauBordClientAdapter(activity,context, tbClientVisite.getTbClientArticles(),articles);
        tableau_bord_client_lv.setAdapter(tableauBordClientAdapter);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }
}
