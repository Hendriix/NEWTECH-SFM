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
import android.widget.ListView;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier.StockLigne;
import com.newtech.newtech_sfm.Metier_Manager.StockLigneManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by TONPC on 14/04/2017.
 */

public class Stock_Ligne_Adapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Article> articles;
    Context context;
    StockLigneManager stockLigneManager;
    ArrayList<StockLigne> stockLignes;

    private ArrayList<Article> arrayList;
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    //private String clientCode;

    
    public Stock_Ligne_Adapter(Activity activity, List<Article> articles, Context context) {
        this.activity = activity;
        this.articles = articles;
        this.arrayList=new ArrayList<Article>();
        this.arrayList.addAll(articles);
        this.context = context;
    }


    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Article getItem(int position) {
        return articles.get(position);
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
            convertView = inflater.inflate(R.layout.stock_ligne_panier_ligne, null);


        ImageView article_image = (ImageView) convertView.findViewById(R.id.image_article);

        TextView article_designation = (TextView) convertView.findViewById(R.id.article_designation);
        TextView article_code = (TextView) convertView.findViewById(R.id.article_code);
        TextView article_prix = (TextView) convertView.findViewById(R.id.prix_article);
        TextView quantite_caisse= (TextView) convertView.findViewById(R.id.quantite_caisse);
        TextView quantite_unite= (TextView) convertView.findViewById(R.id.quantite_unite);

        ListView uniteListView;


        Article article = articles.get(position);

        stockLigneManager = new StockLigneManager(context);

        stockLignes = stockLigneManager.getListByArticleCode(article.getARTICLE_CODE());

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

        article_designation.setText(article.getARTICLE_DESIGNATION1());
        article_code.setText(getItem(position).getARTICLE_CODE());
        if(article.getARTICLE_PRIX()==0){

            article_prix.setText("Prix :"+String.valueOf((int) article.getARTICLE_PRIX())+"DH");

        }else{

            article_prix.setText("Prix :"+String.valueOf((int) article.getARTICLE_PRIX())+"DH");

        }


        if(stockLignes.size()>0){

            Unite_Stock_Ligne_Adapter unite_stock_ligne_adapter = new Unite_Stock_Ligne_Adapter(activity,stockLignes,context);
            uniteListView = (ListView) convertView.findViewById(R.id.unite_listview1);
            uniteListView.setAdapter(unite_stock_ligne_adapter);

        }

        return convertView;
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    public ArrayList<CommandeLigne> getCLBYAC(ArrayList<CommandeLigne> commandeLignes, String article_code){//get commande ligne by article code

        ArrayList<CommandeLigne> commandeLignes1 = new ArrayList<>();

        for(int i =0;i<commandeLignes.size();i++){

            if(commandeLignes.get(i).getARTICLE_CODE().equals(article_code)){
                commandeLignes1.add(commandeLignes.get(i));
            }
        }
        return commandeLignes1;
    }


    public void filter(String textfilter){
        textfilter=textfilter.toLowerCase(Locale.getDefault());
        articles.clear();
        if (textfilter.length() == 0) {
            articles.addAll(arrayList);
        }
        else
        {
            for (Article article : arrayList)
            {
                if (article.getARTICLE_DESIGNATION1().toLowerCase(Locale.getDefault()).contains(textfilter))
                {
                    articles.add(article);
                }
            }
        }
        notifyDataSetChanged();

    }
}
