package com.newtech.newtech_sfm.Fragement;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.newtech.newtech_sfm.Configuration.StockPLigneViewModel;
import com.newtech.newtech_sfm.Configuration.Unite_StockPLigne_Adapter;
import com.newtech.newtech_sfm.Fragement.PanierReleveStockFragment.OnListFragmentInteractionListener;
import com.newtech.newtech_sfm.Fragement.dummy.DummyContent.DummyItem;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.StockPLigne;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter2 extends RecyclerView.Adapter<MyItemRecyclerViewAdapter2.ViewHolder> {

    private static final String TAG = MyViewReferencementRecyclerViewAdapter.class.getSimpleName();

    private final ArrayList<Article> mValues;
    private final PanierReleveStockFragment.OnListFragmentInteractionListener mListener;
    private final Context context;
    private final Fragment fragment;
    ArticleManager articleManager;
    private final StockPLigneViewModel stockPLigneViewModel;
    Unite_StockPLigne_Adapter unite_stockPLigne_adapter;

    public MyItemRecyclerViewAdapter2(ArrayList<StockPLigne> stockPLignes, PanierReleveStockFragment.OnListFragmentInteractionListener listener, Context context, Fragment fragment) {

        mListener = listener;
        this.context =context;
        this.fragment = fragment;
        Log.d(TAG, "onCreateView: activity "+fragment.getActivity().toString());
        stockPLigneViewModel = ViewModelProviders.of(fragment.getActivity()).get(StockPLigneViewModel.class);
        articleManager = new ArticleManager(context);
        mValues = articleManager.getArticlesForStockPLigne(stockPLignes);
        Log.d(TAG, "MyItemRecyclerViewAdapter2: "+mValues.toString());
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


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(mValues.get(position));
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

    public ArrayList<StockPLigne> getStockPLignes(Article article,ArrayList<StockPLigne> stockPLignes){
        ArrayList<StockPLigne> stockPLignes1 = new ArrayList<>();

        for(int i=0 ; i<stockPLignes.size();i++){
            if(stockPLignes.get(i).getARTICLE_CODE().equals(article.getARTICLE_CODE())){
                stockPLignes1.add(stockPLignes.get(i));
            }
        }

        return stockPLignes1;
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }



}
