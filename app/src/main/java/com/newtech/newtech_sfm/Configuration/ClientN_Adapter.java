package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.ClientN;
import com.newtech.newtech_sfm.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by sferricha on 27/09/2016.
 */

public class ClientN_Adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ClientN> clientLists;
    private ArrayList<ClientN> arrayList;
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    public ClientN_Adapter(Activity activity, List<ClientN> clientLists) {
        this.activity = activity;
        this.clientLists = clientLists;
        this.arrayList = new ArrayList<ClientN>();
        this.arrayList.addAll(clientLists);
    }

    @Override
    public int getCount() {
        return clientLists.size();
    }

    @Override
    public Object getItem(int location) {
        return clientLists.get(location);
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
            convertView = inflater.inflate(R.layout.client_cataloque_ligne, null);

        ImageView client_image = (ImageView) convertView.findViewById(R.id.client_image);
        TextView client_nom = (TextView) convertView.findViewById(R.id.client_nom);
        TextView client_code = (TextView) convertView.findViewById(R.id.client_code);
        TextView client_adresse = (TextView) convertView.findViewById(R.id.client_addresse);


        ClientN clientN = clientLists.get(position);

        if (!clientN.getIMAGE().equals("")) {

            client_image.setImageBitmap(resizeBase64Image(clientN.getIMAGE()));

        } else {
            client_image.setImageResource(R.drawable.icone_pers);
            //Log.d(TAG, "image: "+client.getIMAGE());
            /*byte[] decodedString= Base64.decode(clientN.getIMAGE(),Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            Log.d(TAG, "getView: decodedBytmap"+decodedByte);
            Log.d(TAG, "getView: decodeBytmap"+clientN.getIMAGE());
            Log.d(TAG, "getView: decodeBytmap to"+clientN.getIMAGE().toString());
            client_image.setImageBitmap(Bitmap.createScaledBitmap(decodedByte,decodedByte.getWidth() , decodedByte.getHeight(), false));*/
        }

        client_nom.setText(clientN.getCLIENT_NOM());
        client_code.setText(clientN.getCLIENT_CODE());
        client_adresse.setText(clientN.getADRESSE_RUE());

        return convertView;
    }


    public void filter(String textfilter) {
        textfilter = textfilter.toLowerCase(Locale.getDefault());
        clientLists.clear();
        if (textfilter.length() == 0) {
            clientLists.addAll(arrayList);
        } else {
            for (ClientN clientN : arrayList) {
                if (clientN.getCLIENT_NOM().toLowerCase(Locale.getDefault()).contains(textfilter)) {
                    clientLists.add(clientN);
                }
            }
        }
        notifyDataSetChanged();

    }

    public Bitmap resizeBase64Image(String base64image) {

        byte[] encodeByte = Base64.decode(base64image.getBytes(), Base64.DEFAULT);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        Bitmap image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length, options);


        /*if(image.getHeight() <= 400 && image.getWidth() <= 400){
            return image;
        }*/

        image = Bitmap.createScaledBitmap(image, image.getWidth(), image.getHeight(), false);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        return image;

    }


}
