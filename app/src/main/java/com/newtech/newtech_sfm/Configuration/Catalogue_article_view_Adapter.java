package com.newtech.newtech_sfm.Configuration;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier_Manager.ListePrixLigneManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TONPC on 14/04/2017.
 */

public class Catalogue_article_view_Adapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Article> articleLists;


    private ArrayList<Article> arrayList;
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    //private String clientCode;


    public Catalogue_article_view_Adapter(Activity activity, List<Article> articleLists) {
        this.activity = activity;
        this.articleLists = articleLists;
        this.arrayList=new ArrayList<Article>();
        this.arrayList.addAll(articleLists);
    }


    @Override
    public int getCount() {
        return articleLists.size();
    }

    @Override
    public Article getItem(int position) {
        return articleLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        convertView=null;

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.catalogue_article_view_ligne, null);

        ListePrixLigneManager listePrixLigneManager = new ListePrixLigneManager(convertView.getContext());


        ImageView article_image = (ImageView) convertView.findViewById(R.id.image_catalogue_article);

        TextView article_designation = (TextView) convertView.findViewById(R.id.article_catalogue_designation);
        TextView article_code = (TextView) convertView.findViewById(R.id.article_catalogue_code);
        TextView article_prix = (TextView) convertView.findViewById(R.id.prix_article_catalogue);

        Article article = articleLists.get(position);

        Log.d(TAG, "getView: articleimage"+article.getIMAGE());
        String base64Image = "";

        if(article.getIMAGE().toString().contains(",")){

            base64Image = String.valueOf(article.getIMAGE()).split(",")[1];

        }else{

            base64Image=article.getIMAGE();
        }


        if(base64Image.length()<10 || base64Image.equals("") || base64Image==null){

            if(getImageId(convertView.getContext(), getItem(position).getARTICLE_CODE().toLowerCase())>0){
                article_image.setImageResource(getImageId(convertView.getContext(), getItem(position).getARTICLE_CODE().toLowerCase()));

            }else{
                article_image.setImageResource(getImageId(convertView.getContext(),"bouteille_inconnu2"));

            }

        }else{

            Log.d(TAG, "image: "+article.getIMAGE());
            byte[] decodedString= Base64.decode(base64Image,Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            Log.d(TAG, "getView: decodedBytmap"+decodedByte);
            Log.d(TAG, "getView: decodeBytmap"+article.getIMAGE());
            Log.d(TAG, "getView: decodeBytmap to"+article.getIMAGE().toString());
            article_image.setImageBitmap(Bitmap.createScaledBitmap(decodedByte,decodedByte.getWidth() , decodedByte.getHeight(), false));
        }

        article_designation.setText(getItem(position).getARTICLE_DESIGNATION1());
        article_code.setText(getItem(position).getARTICLE_CODE());
        article_prix.setText("Prix :"+String.valueOf(article.getARTICLE_PRIX())+"DH");

        return convertView;
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }
}
