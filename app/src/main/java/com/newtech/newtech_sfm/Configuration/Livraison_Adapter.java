package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier_Manager.VisiteManager;
import com.newtech.newtech_sfm.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by sferricha on 27/09/2016.
 */

public class Livraison_Adapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Client> clientLists;

    private ArrayList<Client> arrayList;
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private String clientCode;



    public Livraison_Adapter(Activity activity, List<Client> clientLists) {
        this.activity = activity;
        this.clientLists = clientLists;
        this.arrayList=new ArrayList<Client>();
        this.arrayList.addAll(clientLists);
    }

    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    String date_visite = sdf.format(new Date());


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
            convertView = inflater.inflate(R.layout.livraison_ligne, null);

        ImageView client_image = (ImageView) convertView.findViewById(R.id.client_image);
        TextView client_nom = (TextView) convertView.findViewById(R.id.client_nom);
        TextView client_code = (TextView) convertView.findViewById(R.id.client_code);
        TextView client_adresse = (TextView) convertView.findViewById(R.id.client_addresse);

        Client client = clientLists.get(position);
        clientCode=client.getCLIENT_CODE();



        /*if(client.getIMAGE().toString().length()<1){
            client_image.setImageResource(R.drawable.icone_pers);
        }else{
            Log.d(TAG, "image: "+client.getIMAGE());
            byte[] decodedString= Base64.decode(client.getIMAGE(),Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            client_image.setImageBitmap(decodedByte);
        }*/

        client_image.setImageResource(R.drawable.icone_pers);
        client_nom.setText(client.getCLIENT_NOM());
        client_code.setText(client.getCLIENT_CODE());
        client_adresse.setText(client.getADRESSE_RUE());

        VisiteManager visiteManager= new VisiteManager(convertView.getContext());

        Log.d(TAG, "getView: "+date_visite);

        int couleur=visiteManager.getVisiteCheckedCmdAL(clientCode,date_visite);

        Log.d(TAG, "couleur code "+couleur);

            if(couleur==1)
            {
                convertView.setBackgroundColor(Color.rgb(90,255,243));

            }else if(couleur== 2){

                convertView.setBackgroundColor(Color.rgb(255,255,90));

            }else if(couleur== 7){

                Log.d(TAG, "getView: red");
                convertView.setBackgroundColor(Color.rgb(255, 51, 51));

            }else{

                convertView.setBackgroundColor(Color.WHITE);
            }

        return convertView;
    }


    public void filter(String textfilter){
        textfilter=textfilter.toLowerCase(Locale.getDefault());
        clientLists.clear();
        if (textfilter.length() == 0) {
            clientLists.addAll(arrayList);
        }
        else
        {
            for (Client client : arrayList)
            {
                if (client.getCLIENT_NOM().toLowerCase(Locale.getDefault()).contains(textfilter))
                {
                    clientLists.add(client);
                }
            }
        }
        notifyDataSetChanged();

    }

    public void filterClientRestant(int variable,List<Client> clientWithoutVisits){
        clientLists.clear();

        if (variable == 1) {
            clientLists.addAll(clientWithoutVisits);

        }
        else
        {
            clientLists.addAll(arrayList);
        }
        notifyDataSetChanged();

    }


}
