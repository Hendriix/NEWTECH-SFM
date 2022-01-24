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

import com.newtech.newtech_sfm.Metier.Unite;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TONPC on 18/04/2017.
 */

public class Spinner_Adapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<Unite> unites;

    private ArrayList<Unite> arrayList;
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private String tacheCode;


    public Spinner_Adapter(Activity activity, List<Unite> unites) {
        this.activity = activity;
        this.unites = unites;
        this.arrayList=new ArrayList<Unite>();
        this.arrayList.addAll(unites);
    }
    @Override
    public int getCount() {
        return unites.size();
    }

    @Override
    public Unite getItem(int position) {
        return unites.get(position);
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
            convertView = inflater.inflate(R.layout.spinner_panier_ligne, null);

        ImageView image_unite = (ImageView) convertView.findViewById(R.id.image_unite);
        TextView unite_type = (TextView) convertView.findViewById(R.id.unite_type);

        Unite unite = unites.get(position);

        unite_type.setText(unite.getUNITE_NOM());
        String base64Image = "";

        if(unite.getIMAGE().toString().contains(",")){

            base64Image = String.valueOf(unite.getIMAGE()).split(",")[1];

        }else{

            base64Image=unite.getIMAGE();
        }


        if(base64Image.length()<10 || base64Image.equals("") || base64Image==null){

            if(getImageId(convertView.getContext(), unite.getARTICLE_CODE().toLowerCase())>0){
                image_unite.setImageResource(getImageId(convertView.getContext(), unite.getARTICLE_CODE().toLowerCase()));

            }else{
                image_unite.setImageResource(getImageId(convertView.getContext(),"bouteille_inconnu2"));

            }

        }else{

            Log.d(TAG, "image: "+unite.getIMAGE());
            byte[] decodedString= Base64.decode(base64Image,Base64.DEFAULT);
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
