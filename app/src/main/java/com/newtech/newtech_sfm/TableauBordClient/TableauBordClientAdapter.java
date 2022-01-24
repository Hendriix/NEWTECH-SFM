package com.newtech.newtech_sfm.TableauBordClient;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.newtech.newtech_sfm.Configuration.SQLiteHandler;
import com.newtech.newtech_sfm.Configuration.Unite_ClientArticle_Adapter;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.TbClientArticle;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

/**
 * Created by TONPC on 13/03/2017.
 */

public class TableauBordClientAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<TbClientArticle> tbClientArticleList;
    private ArrayList<Article> articleArrayList;
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    ArticleManager articleManager;
    Context context;


    public TableauBordClientAdapter(Activity activity,Context context, ArrayList<TbClientArticle> articles, ArrayList<Article> articleList) {
        this.tbClientArticleList = articles;
        this.context = context;
        this.articleArrayList = articleList;
        this.activity = activity;
        articleManager = new ArticleManager(context);
    }

    @Override
    public int getCount() {
        return articleArrayList.size();
    }

    @Override
    public Object getItem(int location) {
        return articleArrayList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (inflater == null)
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.tableau_bord_client_item, null);

        ImageView article_image = (ImageView) convertView.findViewById(R.id.image_article);

        TextView article_designation = (TextView) convertView.findViewById(R.id.article_designation);
        TextView article_code = (TextView) convertView.findViewById(R.id.article_code);
        TextView article_prix = (TextView) convertView.findViewById(R.id.prix_article);
        Button resultat_btn= (Button) convertView.findViewById(R.id.resultat_btn);

        ListView uniteListView;


        /*TbClientArticle tbClientArticle = tbClientArticleList.get(position);
        Article article = articleManager.get(tbClientArticle.getARTICLE_CODE());*/

        Article article = articleArrayList.get(position);
        //stockLigneManager = new StockLigneManager(context);

        //stockLignes = stockLigneManager.getListByArticleCode(article.getARTICLE_CODE());

        Log.d(TAG, "getView: articleimage"+article.getIMAGE());
        String base64Image = "";

        if(article.getIMAGE().toString().contains(",")){

            base64Image = String.valueOf(article.getIMAGE()).split(",")[1];

        }else{

            base64Image=article.getIMAGE();
        }


        if(base64Image.length()<10 || base64Image.equals("") || base64Image==null){

            if(getImageId(convertView.getContext(), article.getARTICLE_CODE().toLowerCase())>0){
                article_image.setImageResource(getImageId(convertView.getContext(), article.getARTICLE_CODE().toLowerCase()));

            }else{
                article_image.setImageResource(getImageId(convertView.getContext(),"bouteille_inconnu2"));

            }

        }else{

            Log.d(TAG, "image: "+article.getIMAGE());
            byte[] decodedString= Base64.decode(base64Image,Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            article_image.setImageBitmap(Bitmap.createScaledBitmap(decodedByte,decodedByte.getWidth() , decodedByte.getHeight(), false));
        }

        article_designation.setText(article.getARTICLE_DESIGNATION1());
        article_code.setText(article.getARTICLE_CODE());
        if(article.getARTICLE_PRIX()==0){

            article_prix.setText("Prix :"+String.valueOf((int) article.getARTICLE_PRIX())+"DH");

        }else{

            article_prix.setText("Prix :"+String.valueOf((int) article.getARTICLE_PRIX())+"DH");

        }



        if(tbClientArticleList.size()>0){

            ArrayList<TbClientArticle> tbClientArticleArrayList = getCAMBYAC(tbClientArticleList,article.getARTICLE_CODE());

            if (tbClientArticleArrayList.size()>0){
                resultat_btn.setBackgroundColor(ContextCompat.getColor(context,R.color.good));
                Log.d(TAG, "getView: green");
            }else{
                resultat_btn.setBackgroundColor(ContextCompat.getColor(context,R.color.bad));
                Log.d(TAG, "getView: red");
            }
            Unite_ClientArticle_Adapter unite_clientArticle_adapter = new Unite_ClientArticle_Adapter(activity,tbClientArticleArrayList,context);
            uniteListView = (ListView) convertView.findViewById(R.id.unite_listview1);
            uniteListView.setAdapter(unite_clientArticle_adapter);

        }else{
            resultat_btn.setBackgroundColor(ContextCompat.getColor(context,R.color.bad));
        }

        /*if(!tbClientArticle.getQUANTITE().equals("0")){
            resultat_btn.setBackgroundColor(ContextCompat.getColor(context,R.color.good));
        }else{
            resultat_btn.setBackgroundColor(ContextCompat.getColor(context,R.color.bad));
        }*/
        return convertView;
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    public ArrayList<TbClientArticle> getCAMBYAC(ArrayList<TbClientArticle> tbClientArticles, String article_code){//get commande ligne by article code

        ArrayList<TbClientArticle> tbClientArticles1 = new ArrayList<>();

        for(int i =0;i<tbClientArticles.size();i++){

            if(tbClientArticles.get(i).getARTICLE_CODE().equals(article_code)){
                tbClientArticles1.add(tbClientArticles.get(i));
            }
        }
        return tbClientArticles1;
    }
}
