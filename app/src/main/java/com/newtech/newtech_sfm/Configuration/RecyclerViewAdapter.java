package com.newtech.newtech_sfm.Configuration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.newtech.newtech_sfm.Metier.StockLigne;
import com.newtech.newtech_sfm.Metier.Unite;
import com.newtech.newtech_sfm.Metier_Manager.UniteManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;


/**
 * Created by User on 2/12/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {



    private ArrayList<StockLigne> stockLignes;
    private ArrayList<StockLigne> arrayList;
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    Context mContext;



    public RecyclerViewAdapter(ArrayList<StockLigne> stockLignes, Context context) {

        Log.d(TAG, "RecyclerViewAdapter: ");
        this.stockLignes = stockLignes;
        mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.unite_ligne_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Log.d(TAG, "onBindViewHolder: i'm here i'm here 1");
        UniteManager uniteManager = new UniteManager(mContext);
        Unite unite = new Unite();

        String article_code = stockLignes.get(position).getARTICLE_CODE();
        String article_unite = stockLignes.get(position).getUNITE_CODE();

        Log.d(TAG, "onBindViewHolder: "+article_code);
        Log.d(TAG, "onBindViewHolder: "+article_unite);

        String base64Image1 = "";
        unite = uniteManager.getByACUC(article_code,article_unite);

        Log.d(TAG, "onBindViewHolder: "+unite.toString());

        holder.quantite_unite.setText(String.valueOf(stockLignes.get(position).getQTE()));

        if(unite.getIMAGE().toString().contains(",")){

            base64Image1 = String.valueOf(unite.getIMAGE()).split(",")[1];

        }else{

            base64Image1=unite.getIMAGE();
        }

        if(base64Image1.length()<10 || base64Image1.equals("") || base64Image1==null){

            if(getImageId(mContext, unite.getARTICLE_CODE().toLowerCase())>0){
                //caisse_image.setImageResource(getImageId(convertView.getContext(),"box_icone"));
                holder.image_unite.setImageResource(getImageId(mContext, unite.getARTICLE_CODE().toLowerCase()));

            }else{
                //caisse_image.setImageResource(getImageId(convertView.getContext(),"box_icone"));
                holder.image_unite.setImageResource(getImageId(mContext,"bouteille_inconnu2"));

            }

        }else{

            Log.d(TAG, "image: "+unite.getIMAGE());
            byte[] decodedString= Base64.decode(base64Image1,Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            Log.d(TAG, "getView: decodedBytmap"+decodedByte);
            Log.d(TAG, "getView: decodeBytmap"+unite.getIMAGE());
            Log.d(TAG, "getView: decodeBytmap to"+unite.getIMAGE().toString());
            holder.image_unite.setImageBitmap(Bitmap.createScaledBitmap(decodedByte,decodedByte.getWidth() , decodedByte.getHeight(), false));
        }


        //notifyDataSetChanged();

    }


    @Override
    public int getItemCount() {
        return stockLignes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image_unite;
        TextView quantite_unite;

        public ViewHolder(View itemView) {
            super(itemView);
            image_unite =(ImageView) itemView.findViewById(R.id.image_unite_panier);
            quantite_unite =(TextView) itemView.findViewById(R.id.quantite_unite_panier);
        }
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }
}
