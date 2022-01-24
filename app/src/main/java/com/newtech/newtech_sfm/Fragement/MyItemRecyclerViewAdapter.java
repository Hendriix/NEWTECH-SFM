package com.newtech.newtech_sfm.Fragement;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.newtech.newtech_sfm.Configuration.Spinner_Adapter;
import com.newtech.newtech_sfm.Configuration.StockPLigneViewModel;
import com.newtech.newtech_sfm.Configuration.Unite_StockPLigne_Adapter;
import com.newtech.newtech_sfm.Fragement.ReleveStockFragment.OnListFragmentInteractionListener;
import com.newtech.newtech_sfm.Fragement.dummy.DummyContent.DummyItem;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.StockPLigne;
import com.newtech.newtech_sfm.Metier.Unite;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.Metier_Manager.UniteManager;
import com.newtech.newtech_sfm.R;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {


    private static final String TAG = MyItemRecyclerViewAdapter.class.getSimpleName();

    private final ArrayList<Article> mValues;
    private final ReleveStockFragment.OnListFragmentInteractionListener mListener;
    private final Context context;
    private final Fragment fragment;
    ArticleManager articleManager;
    Unite unite;
    private final StockPLigneViewModel stockPLigneViewModel;
    ArrayList<StockPLigne> stockPLignes = new ArrayList<>();
    Unite_StockPLigne_Adapter unite_stockPLigne_adapter;

    public MyItemRecyclerViewAdapter(ArrayList<Article> items, ReleveStockFragment.OnListFragmentInteractionListener listener, Context context, Fragment fragment) {

        mValues = items;
        mListener = listener;
        this.context =context;
        this.fragment = fragment;
        stockPLigneViewModel = ViewModelProviders.of(fragment.getActivity()).get(StockPLigneViewModel.class);
        //stockPLigneViewModel.setSelectedVisibiliteLignes(mValues);
        articleManager = new ArticleManager(context);
        stockPLignes = stockPLigneViewModel.getStockPLigneArrayList();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.releve_stock_article_ligne, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Article article = articleManager.get(mValues.get(position).getARTICLE_CODE());
        Log.d(TAG, "onBindViewHolder: here here "+article.getARTICLE_CODE());

        holder.article_code.setText(mValues.get(position).getARTICLE_CODE());
        holder.article_designation.setText(article.getARTICLE_DESIGNATION1());

        Log.d(TAG, "onBindViewHolder: 1 : "+stockPLigneViewModel.getStockPLigneArrayList().size());
        Log.d(TAG, "onBindViewHolder: 1 : "+stockPLigneViewModel.getStockPLigneArrayList());

        Log.d(TAG, "onBindViewHolder: 2 : "+getStockPLignes(article,stockPLigneViewModel.getStockPLigneArrayList()).size());
        Log.d(TAG, "onBindViewHolder: 2 : "+getStockPLignes(article,stockPLigneViewModel.getStockPLigneArrayList()));

        if(getStockPLignes(article,stockPLigneViewModel.getStockPLigneArrayList()).size()>0){

            unite_stockPLigne_adapter = new Unite_StockPLigne_Adapter(fragment,
                    getStockPLignes(article,stockPLigneViewModel.getStockPLigneArrayList()),
                    context);

        }else{
            unite_stockPLigne_adapter = new Unite_StockPLigne_Adapter(fragment,
                    new ArrayList<StockPLigne>(),
                    context);
        }

        holder.listView.setAdapter(unite_stockPLigne_adapter);
        String base64Image = "";

        if(article.getIMAGE().toString().contains(",")){
            base64Image = String.valueOf(article.getIMAGE()).split(",")[1];
        }else{
            base64Image=article.getIMAGE();
        }
        if(base64Image.length()<10 || base64Image.equals("") || base64Image==null){
            if(getImageId(context, article.getARTICLE_CODE().toLowerCase())>0){
                holder.article_image.setImageResource(getImageId(context, article.getARTICLE_CODE().toLowerCase()));

            }else{
                holder.article_image.setImageResource(getImageId(context,"bouteille_inconnu2"));

            }
        }else{
            byte[] decodedString= Base64.decode(base64Image,Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            holder.article_image.setImageBitmap(Bitmap.createScaledBitmap(decodedByte,decodedByte.getWidth() , decodedByte.getHeight(), false));
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //int position = v.getTag();
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(mValues.get(position));

                    EditText quantite_vendue;
                    Spinner  dropdown_unite ;
                    Spinner_Adapter spinnerAdapter;
                    Button annuler_panier;
                    Button valider_panier;



                    UniteManager uniteManager = new UniteManager(context);
                    ArrayList<Unite> unites = new ArrayList<>();
                    unites = uniteManager.getListByArticleCode(article.getARTICLE_CODE());

                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.alert_dialog_releve_stock);
                    dialog.setTitle(article.getARTICLE_DESIGNATION1());


                    annuler_panier = (Button) dialog.findViewById(R.id.annuler_panier);
                    valider_panier = (Button) dialog.findViewById(R.id.valider_panier);
                    quantite_vendue= (EditText) dialog.findViewById(R.id.quantite_vendue_panier);
                    dropdown_unite = (Spinner)dialog.findViewById(R.id.spinner_choix_unite);



                    spinnerAdapter = new Spinner_Adapter(fragment.getActivity(),unites);
                    dropdown_unite.setAdapter(spinnerAdapter);
                    Log.d(TAG, "onClick: you clicked me "+mValues.get(position).getARTICLE_CODE());

                    dropdown_unite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {


                            int quantite_commandee=0;

                            unite = spinnerAdapter.getItem(position);

                            //Log.d("listprixligne", "onItemSelected: "+listePrixLigne.toString());
                            Log.d("unite", "onItemSelected: "+unite.toString());

                            quantite_vendue.getText().clear();

                            StockPLigne stockPLigne= getStockPLigne(article,unite.getUNITE_CODE(),stockPLignes);
                            //StockPLigne stockPLigne = new StockPLigne();

                            Log.d("NewPanierActivity", "onItemSelected: "+stockPLigne.toString());
                            if(stockPLigne.getQTE()>0){

                                quantite_vendue.setText(String.valueOf((int)stockPLigne.getQTE()));
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            // your code here
                        }

                    });

                    annuler_panier.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();
                        }
                    });

                    valider_panier.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(true){

                                float quantite_article=0;
                                double prixDefault=0;
                                double prixArticle=0;
                                String unite_code="";

                                //unite_code = unite.getUNITE_CODE();
                                //Log.d("unite", "onClick: "+unite_code);

                                if(unite==null){

                                    Toast.makeText(context,"Merci de Choisir une unité" , Toast.LENGTH_LONG).show();
                                    dialog.dismiss();

                                    //Log.d("unite1", "onClick: "+unite_code);

                                }else{

                                    unite_code=unite.getUNITE_CODE();
                                    //Log.d("unite2", "onClick: "+unite_code);

                                }

                                //Log.d("valider", "valeur: "+quantite_vendue.getText());

                                if(!String.valueOf(quantite_vendue.getText()).equals("")){
                                    quantite_article = Integer.parseInt(String.valueOf(quantite_vendue.getText()));
                                }

                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
                                String dateLivraison = df.format(Calendar.getInstance().getTime());


                                supprimerStockPLigne(article,unite_code,stockPLignes);

                                if(quantite_article!=0 && !String.valueOf(quantite_vendue.getText()).equals("") && unite!=null){

                                    ajouterStockPLigne(article,unite_code,quantite_article,context,stockPLignes);

                                }



                                unite_stockPLigne_adapter = new Unite_StockPLigne_Adapter(fragment,
                                        getStockPLignes(article,stockPLigneViewModel.getStockPLigneArrayList()),
                                        context);

                                holder.listView.setAdapter(unite_stockPLigne_adapter);


                                unite = null;

                                dialog.dismiss();


                            }
                        }


                    });

                    dialog.show();

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        TextView article_code = null;
        TextView article_designation = null;
        ImageView article_image = null;
        ListView listView = null;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            article_image = view.findViewById(R.id.image_article);
            article_code = view.findViewById(R.id.article_code);
            article_designation = view.findViewById(R.id.article_designation);
            listView = view.findViewById(R.id.unite_listview1);
        }

        /*@Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }*/
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    public void supprimerStockPLigne(Article article, String unite_code, ArrayList<StockPLigne> stockPLignes){

        ArrayList<StockPLigne> stockPLignes1 = stockPLignes;


        for(int i=0;i<stockPLignes1.size();i++){
            if(stockPLignes1.get(i).getARTICLE_CODE().equals(article.getARTICLE_CODE()) && stockPLignes1.get(i).getUNITE_CODE().equals(unite_code)){
                //Log.d("valider", "commandeligne: "+commandeLigne);
                stockPLignes1.remove(i);
            }
        }

        stockPLigneViewModel.setSelectedStockPLignes(stockPLignes1);
    }

    public void ajouterStockPLigne(Article article ,String unite_code,double quantite, Context context, ArrayList<StockPLigne> stockPLignes){
        ArrayList<StockPLigne> stockPLignes1 = stockPLignes;
        StockPLigne stockPLigne = null;
        try {
            stockPLigne = new StockPLigne(article,unite_code,quantite,context);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        stockPLignes1.add(stockPLigne);
        stockPLigneViewModel.setSelectedStockPLignes(stockPLignes1);

    }

    public void updateStockP(){

    }

    public StockPLigne getStockPLigne(Article article, String unite_code, ArrayList<StockPLigne> stockPLignes){
        StockPLigne stockPLigne = new StockPLigne();

        for(int i=0;i<stockPLignes.size();i++){
            if(stockPLignes.get(i).getARTICLE_CODE().equals(article.getARTICLE_CODE()) && stockPLignes.get(i).getUNITE_CODE().equals(unite_code)){
                stockPLigne=stockPLignes.get(i);
                break;
            }
        }

        return stockPLigne;

    }

    public ArrayList<StockPLigne> getStockPLignes(Article article,ArrayList<StockPLigne> stockPLignes){
        ArrayList<StockPLigne> stockPLignes1 = new ArrayList<>();

        for(int i=0 ; i<stockPLignes.size();i++){
            if(stockPLignes.get(i).getARTICLE_CODE().equals(article.getARTICLE_CODE())){
                stockPLignes1.add(stockPLignes.get(i));
            }
        }

        return stockPLignes1;
    }

}
