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

import com.newtech.newtech_sfm.Activity.ViewChargementActivity;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.StockDemandeLigne;
import com.newtech.newtech_sfm.Metier_Manager.ListePrixLigneManager;
import com.newtech.newtech_sfm.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by TONPC on 14/04/2017.
 */

public class ChargementPanier_Adapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Article> articleLists;
    public static  final ArrayList<StockDemandeLigne> ListeStockDemandeLignes = new ArrayList<StockDemandeLigne>();
    Context context;
    //Client clientCourant= ClientActivity.clientCourant;

    private ArrayList<Article> arrayList;
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    //private String clientCode;

    private ArrayList<StockDemandeLigne>  stockDemandeLignes = ViewChargementActivity.stockDemandeLignes;


    public ChargementPanier_Adapter(Activity activity, List<Article> articleLists, Context context) {
        this.activity = activity;
        this.articleLists = articleLists;
        this.arrayList=new ArrayList<Article>();
        this.arrayList.addAll(articleLists);
        this.context = context;
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
            convertView = inflater.inflate(R.layout.catalogue_article_panier_ligne, null);

        Article article = articleLists.get(position);
        ListView uniteListView;

        //ArticlePrixManager articlePrixManager= new ArticlePrixManager(convertView.getContext());
        ListePrixLigneManager listePrixLigneManager = new ListePrixLigneManager(convertView.getContext());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        String dateLivraison =df.format(Calendar.getInstance().getTime());
        String date =df.format(Calendar.getInstance().getTime());

        //Articleprix articleprix= articlePrixManager.getArticlePrixBy_Unite_ArticlePrix(getItem(position).getARTICLE_CODE(),"CAISSE", clientCourant.getCIRCUIT_CODE(),date);
        //ListePrixLigne listePrixLigne= listePrixLigneManager.getListPrixLigneByLPCACUC(clientCourant.getLISTEPRIX_CODE(),getItem(position).getARTICLE_CODE(),"CAISSE");
        //Log.d(TAG, "getView: "+listePrixLigne.toString());
        ImageView article_image = (ImageView) convertView.findViewById(R.id.image_article);

        TextView article_designation = (TextView) convertView.findViewById(R.id.article_designation);
        TextView article_code = (TextView) convertView.findViewById(R.id.article_code);
        TextView article_prix = (TextView) convertView.findViewById(R.id.prix_article);
        TextView quantite_caisse= (TextView) convertView.findViewById(R.id.quantite_caisse);
        TextView quantite_unite= (TextView) convertView.findViewById(R.id.quantite_unite);

        ImageView bouteille_image= (ImageView) convertView.findViewById(R.id.bouteille_image);
        ImageView caisse_image =(ImageView) convertView.findViewById(R.id.caisse_image);


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
        //article_prix.setText("Prix :"+String.valueOf((int) listePrixLigne.getARTICLE_PRIX())+"DH");


        if(stockDemandeLignes.size()>0){

            ArrayList<StockDemandeLigne> stockDemandeLigneArrayList = getSDMBYAC(stockDemandeLignes,article.getARTICLE_CODE());

            Unite_StockDemande_Adapter unite_stockDemande_adapter = new Unite_StockDemande_Adapter(activity,stockDemandeLigneArrayList,context);
            uniteListView = (ListView) convertView.findViewById(R.id.unite_listview1);
            uniteListView.setAdapter(unite_stockDemande_adapter);

        }

        return convertView;
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    public ArrayList<StockDemandeLigne> getSDMBYAC(ArrayList<StockDemandeLigne> stockDemandeLignes, String article_code){//get commande ligne by article code

        ArrayList<StockDemandeLigne> stockDemandeLignes1 = new ArrayList<>();

        for(int i =0;i<stockDemandeLignes.size();i++){

            if(stockDemandeLignes.get(i).getARTICLE_CODE().equals(article_code)){
                stockDemandeLignes1.add(stockDemandeLignes.get(i));
            }
        }
        return stockDemandeLignes1;
    }
}
