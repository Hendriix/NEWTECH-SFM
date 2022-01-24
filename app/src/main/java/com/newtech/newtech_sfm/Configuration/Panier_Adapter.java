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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newtech.newtech_sfm.Activity.ClientActivity;
import com.newtech.newtech_sfm.Activity.ViewCommandeActivity;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier.ListePrixLigne;
import com.newtech.newtech_sfm.Metier.StockLigne;
import com.newtech.newtech_sfm.Metier_Manager.ListePrixLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.StockLigneManager;
import com.newtech.newtech_sfm.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by TONPC on 14/04/2017.
 */

public class Panier_Adapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Article> articleLists;
    public static  final ArrayList<CommandeLigne> ListeCommandeLigne = new ArrayList<CommandeLigne>();
    Context context;

    Client clientCourant= ClientActivity.clientCourant;

    private ArrayList<Article> arrayList;
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    //private String clientCode;

    private ArrayList<CommandeLigne>  commandeLignes= ViewCommandeActivity.ListeCommandeLigne;

    public Panier_Adapter(Activity activity, List<Article> articleLists, Context context) {
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


        //ArticlePrixManager articlePrixManager= new ArticlePrixManager(convertView.getContext());
        ListePrixLigneManager listePrixLigneManager = new ListePrixLigneManager(convertView.getContext());
        StockLigneManager stockLigneManager = new StockLigneManager(convertView.getContext());



        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        String dateLivraison =df.format(Calendar.getInstance().getTime());
        String date =df.format(Calendar.getInstance().getTime());

        //Articleprix articleprix= articlePrixManager.getArticlePrixBy_Unite_ArticlePrix(getItem(position).getARTICLE_CODE(),"CAISSE", clientCourant.getCIRCUIT_CODE(),date);
        //ArrayList<ListePrixLigne> listePrixLignes = listePrixLigneManager.getListPrixLigneByLPCAC(clientCourant.getLISTEPRIX_CODE(),getItem(position).getARTICLE_CODE());
        ListePrixLigne listePrixLigne= listePrixLigneManager.getPrixLigneByLPCACUC(clientCourant.getLISTEPRIX_CODE(),getItem(position).getARTICLE_CODE());
        Log.d(TAG, "getView: "+listePrixLigne.toString());
        ImageView article_image = (ImageView) convertView.findViewById(R.id.image_article);

        TextView article_designation = (TextView) convertView.findViewById(R.id.article_designation);
        TextView article_code = (TextView) convertView.findViewById(R.id.article_code);
        TextView article_prix = (TextView) convertView.findViewById(R.id.prix_article);
        TextView quantite_caisse= (TextView) convertView.findViewById(R.id.quantite_caisse);
        TextView quantite_unite= (TextView) convertView.findViewById(R.id.quantite_unite);
        //TextView priorite_article= (TextView) convertView.findViewById(R.id.priorite_article);

        //ImageView bouteille_image= (ImageView) convertView.findViewById(R.id.bouteille_image);
        //ImageView caisse_image =(ImageView) convertView.findViewById(R.id.caisse_image);

        ListView uniteListView;

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
        if(listePrixLigne.getARTICLE_PRIX()==0){

            article_prix.setText("Prix :"+String.valueOf((int) article.getARTICLE_PRIX())+"DH");

        }else{

            article_prix.setText("Prix :"+String.valueOf((int) listePrixLigne.getARTICLE_PRIX())+"DH");

        }


        if(commandeLignes.size()>0){

        ArrayList<CommandeLigne> commandeLigneArrayList = getCLBYAC(commandeLignes,article.getARTICLE_CODE());
        Unite_Commande_Adapter unite_adapter = new Unite_Commande_Adapter(activity,commandeLigneArrayList,context);
        uniteListView = (ListView) convertView.findViewById(R.id.unite_listview1);
        uniteListView.setAdapter(unite_adapter);

        }

        ArrayList<StockLigne> stockLignes = stockLigneManager.getListByArticleCode(article.getARTICLE_CODE());

        if(stockLignes.size()>0){

            Log.d(TAG, "getView: you are in");
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            RecyclerView recyclerView = convertView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(layoutManager);
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(stockLignes,context);
            recyclerView.setAdapter(adapter);

        }else{
            Log.d(TAG, "getView: liststocklignevides vides vides");
        }


        //initRecyclerView(convertView.getContext(),stockLignes,convertView);

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

    public void initRecyclerView(Context context, ArrayList<StockLigne> stockLignes,View convertView){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = convertView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(stockLignes,context);
        recyclerView.setAdapter(adapter);
    }


}
