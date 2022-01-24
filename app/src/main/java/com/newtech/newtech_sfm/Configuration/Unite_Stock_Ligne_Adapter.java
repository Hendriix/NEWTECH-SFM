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

import com.newtech.newtech_sfm.Metier.StockLigne;
import com.newtech.newtech_sfm.Metier.Unite;
import com.newtech.newtech_sfm.Metier_Manager.UniteManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TONPC on 20/09/2017.
 */

public class Unite_Stock_Ligne_Adapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<StockLigne> stockLignes;

    private ArrayList<StockLigne> arrayList;
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private String tacheCode;
    Context context;


    public Unite_Stock_Ligne_Adapter(Activity activity, List<StockLigne> stockLignes, Context context) {
        this.activity = activity;
        this.stockLignes = stockLignes;
        this.arrayList=new ArrayList<StockLigne>();
        this.arrayList.addAll(stockLignes);
        context = context;
    }


    @Override
    public int getCount() {
        return stockLignes.size();
    }

    @Override
    public Object getItem(int position) {
        return stockLignes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.unite_ligne_view, null);


        TextView quantite_unite = (TextView)convertView.findViewById(R.id.quantite_unite_panier);
        ImageView image_unite = (ImageView) convertView.findViewById(R.id.image_unite_panier);

        UniteManager uniteManager = new UniteManager(convertView.getContext());
        Unite unite = new Unite();

        String article_code = stockLignes.get(position).getARTICLE_CODE();
        String article_unite = stockLignes.get(position).getUNITE_CODE();
        String base64Image1 = "";
        unite = uniteManager.getByACUC(article_code,article_unite);

        quantite_unite.setText(String.valueOf(stockLignes.get(position).getQTE()));



            if(unite.getIMAGE().toString().contains(",")){

                base64Image1 = String.valueOf(unite.getIMAGE()).split(",")[1];

            }else{

                base64Image1=unite.getIMAGE();
            }



        if(base64Image1.length()<10 || base64Image1.equals("") || base64Image1==null){

            if(getImageId(convertView.getContext(), unite.getARTICLE_CODE().toLowerCase())>0){
                //caisse_image.setImageResource(getImageId(convertView.getContext(),"box_icone"));
                image_unite.setImageResource(getImageId(convertView.getContext(), unite.getARTICLE_CODE().toLowerCase()));

            }else{
                //caisse_image.setImageResource(getImageId(convertView.getContext(),"box_icone"));
                image_unite.setImageResource(getImageId(convertView.getContext(),"bouteille_inconnu2"));

            }

        }else{

            Log.d(TAG, "image: "+unite.getIMAGE());
            byte[] decodedString= Base64.decode(base64Image1,Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            Log.d(TAG, "getView: decodedBytmap"+decodedByte);
            Log.d(TAG, "getView: decodeBytmap"+unite.getIMAGE());
            Log.d(TAG, "getView: decodeBytmap to"+unite.getIMAGE().toString());
            image_unite.setImageBitmap(Bitmap.createScaledBitmap(decodedByte,decodedByte.getWidth() , decodedByte.getHeight(), false));
        }

        return convertView;

    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

}
