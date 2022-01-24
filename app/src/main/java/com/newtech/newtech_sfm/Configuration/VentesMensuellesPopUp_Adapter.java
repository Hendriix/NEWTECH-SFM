package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by csaylani on 26/04/2018.
 */

public class VentesMensuellesPopUp_Adapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private Map<String,String> realisations;
    private ArrayList arrayList;
    Context context;
    ArticleManager articleManager;


    public VentesMensuellesPopUp_Adapter(Activity activity, Map<String,String> realisations, Context context) {
        this.activity = activity;
        this.realisations = realisations;
        arrayList = new ArrayList();
        arrayList.addAll(realisations.entrySet());
        this.context = context;
        articleManager = new ArticleManager(this.context);
    }



    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Map.Entry<String, String> getItem(int position) {
        return (Map.Entry) arrayList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {


        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.ventes_mensuelles_popup_ligne, null);

        Map.Entry<String, String> item = getItem(i);

        TextView article = (TextView)convertView.findViewById(R.id.vm_realisation_article);
        TextView quantite = (TextView)convertView.findViewById(R.id.vm_realisation_quantite);

        Article article1 = articleManager.get(item.getKey());
        article.setText(article1.getARTICLE_DESIGNATION1());

        quantite.setText(item.getValue());

        return convertView;
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }
}
