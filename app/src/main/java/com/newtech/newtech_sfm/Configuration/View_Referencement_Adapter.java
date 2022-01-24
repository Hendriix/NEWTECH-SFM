package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.VisibiliteLigne;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

/**
 * Created by TONPC on 14/04/2017.
 */

public class View_Referencement_Adapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<VisibiliteLigne> visibiliteLignes;
    private Context context;
    ArticleManager articleManager;
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    //private String clientCode;


    public View_Referencement_Adapter(Activity activity, ArrayList<VisibiliteLigne> visibiliteLignes, Context context) {
        this.activity = activity;
        this.visibiliteLignes = visibiliteLignes;
        this.context = context;
    }


    @Override
    public int getCount() {
        return visibiliteLignes.size();
    }

    @Override
    public VisibiliteLigne getItem(int position) {
        return visibiliteLignes.get(position);
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
            convertView = inflater.inflate(R.layout.view_referencement_ligne, null);

        ImageView article_image = (ImageView) convertView.findViewById(R.id.image_article);
        TextView article_designation = (TextView) convertView.findViewById(R.id.article_designation);
        TextView article_code = (TextView) convertView.findViewById(R.id.article_code);

        VisibiliteLigne visibiliteLigne = visibiliteLignes.get(position);

        articleManager = new ArticleManager(context);
        Article article = articleManager.get(visibiliteLigne.getARTICLE_CODE());

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
            byte[] decodedString= Base64.decode(base64Image,Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            article_image.setImageBitmap(Bitmap.createScaledBitmap(decodedByte,decodedByte.getWidth() , decodedByte.getHeight(), false));
        }

        article_designation.setText(article.getARTICLE_DESIGNATION1());
        article_code.setText(article.getARTICLE_CODE());

        if(visibiliteLigne.getVISIBILITE() == 1)
        {
            convertView.setBackgroundColor(Color.rgb(90,255,243));

        }else if(visibiliteLigne.getVISIBILITE() == 0){

            convertView.setBackgroundColor(Color.WHITE);

        }

        return convertView;
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }
}
