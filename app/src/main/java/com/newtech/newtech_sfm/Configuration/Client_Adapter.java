package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier_Manager.ClasseManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteManager;
import com.newtech.newtech_sfm.R;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Client_Adapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Client> clientLists;
    private Context context;

    private ArrayList<Client> arrayList;
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private String clientCode;

    private static Bitmap decodedByte = null;


    public Client_Adapter(Activity activity, List<Client> clientLists, Context context) {
        this.activity = activity;
        this.clientLists = clientLists;
        this.arrayList = new ArrayList<Client>();
        this.arrayList.addAll(clientLists);
        this.context = context;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
            convertView = inflater.inflate(R.layout.client_cataloque_ligne, null);

        final ImageView client_image = (ImageView) convertView.findViewById(R.id.client_image);
        ImageView client_medal = (ImageView) convertView.findViewById(R.id.client_medal);
        //final ImageView expanded_image = (ImageView) convertView.findViewById(R.id.expanded_image);

        TextView client_nom = (TextView) convertView.findViewById(R.id.client_nom);
        TextView client_code = (TextView) convertView.findViewById(R.id.client_code);
        TextView client_adresse = (TextView) convertView.findViewById(R.id.client_addresse);
        TextView client_classe = (TextView) convertView.findViewById(R.id.client_classe);

        //ChoufouniContratManager choufouniContratManager = new ChoufouniContratManager(context);

        String adresse_numero = "";
        String adresse_rue = "";
        String adresse_quartier = "";

        Log.d(TAG, "getView: " + clientLists.toString());

        Client client = clientLists.get(position);
        clientCode = client.getCLIENT_CODE();
        ClasseManager classeManager = new ClasseManager(context);
        String classe_nom = classeManager.get(client.getCLASSE_CODE()).getCLASSE_NOM();

        String base64Image = "";

        if (client.getIMAGE().toString().contains(",")) {

            base64Image = String.valueOf(client.getIMAGE()).split(",")[1];

        } else {

            base64Image = String.valueOf(client.getIMAGE());
        }


        // Log.d(TAG, "getView: "+client.toString());
        //Log.d(TAG, "getView: Client code : "+client.getCLIENT_CODE()+" base 64 :"+base64Image);

        /*if(base64Image.length()<10 || base64Image.equals("") || base64Image==null){

                client_image.setImageResource(R.drawable.icone_pers);

        }else{

            Log.d(TAG, "image: "+client.getIMAGE());
            byte[] decodedString= Base64.decode(base64Image,Base64.DEFAULT);

            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);

            Log.d(TAG, "getView: decodedBytmap"+decodedByte);
            Log.d(TAG, "getView: decodeBytmap"+client.getIMAGE());
            Log.d(TAG, "getView: decodeBytmap to"+client.getIMAGE().toString());
            client_image.setImageBitmap(Bitmap.createScaledBitmap(decodedByte,decodedByte.getWidth() , decodedByte.getHeight(), false));
        }*/

        client_image.setImageResource(R.drawable.icone_pers);

        /*if(choufouniContratManager.getChoufouniContrat(context,clientCode)){
            client_medal.setVisibility(View.VISIBLE);
            client_medal.setImageResource(R.drawable.medal_choufouni);
        }*/

        if (classe_nom != null) {

            if (classe_nom.equals("CLIENT A")) {
                //Log.d(TAG, "getView: "+classe_nom);
                //Log.d(TAG, "getView: "+"helo helo helo");
                client_nom.setText(client.getCLIENT_NOM() + " * ");
            } else {
                //Log.d(TAG, "getView: "+classe_nom);
                client_nom.setText(client.getCLIENT_NOM());
                //Log.d(TAG, "getView: "+"nono nono nono");
            }
        } else {
            client_nom.setText(client.getCLIENT_NOM());
        }


        if (client.getADRESSE_NR().equals("null")) {
            adresse_numero = "N°X ";
        } else {
            adresse_numero = client.getADRESSE_NR();
        }

        if (client.getADRESSE_RUE().equals("null")) {
            adresse_rue = "Rue x ";
        } else {
            adresse_rue = client.getADRESSE_RUE();
        }

        if (client.getADRESSE_QUARTIER().equals("null")) {
            adresse_quartier = "Quartier x";
        } else {
            adresse_quartier = client.getADRESSE_QUARTIER();
        }


        client_code.setText(client.getCLIENT_CODE());
        client_adresse.setText(adresse_numero + " " + adresse_rue + " " + adresse_quartier);
        client_classe.setText(classe_nom);

        VisiteManager visiteManager = new VisiteManager(convertView.getContext());

        Log.d(TAG, "getView: " + date_visite);

        int couleur = visiteManager.getVisiteChecked(clientCode, date_visite);

        Log.d(TAG, "couleur code " + couleur);

        if (couleur == 1) {
            convertView.setBackgroundColor(Color.rgb(90, 255, 243));
        } else if (couleur == 2) {
            Log.d(TAG, "getView: red");
            convertView.setBackgroundColor(Color.rgb(255, 255, 90));

        } else if (couleur == 7) {

            Log.d(TAG, "getView: red");
            convertView.setBackgroundColor(Color.rgb(255, 51, 51));

        } else {

            convertView.setBackgroundColor(Color.WHITE);
        }

        return convertView;
    }


    public void filter(String textfilter) {
        textfilter = textfilter.toLowerCase(Locale.getDefault());
        clientLists.clear();
        if (textfilter.length() == 0) {
            clientLists.addAll(arrayList);
        } else {
            for (Client client : arrayList) {
                if (client.getCLIENT_NOM().toLowerCase(Locale.getDefault()).contains(textfilter)) {
                    clientLists.add(client);
                }
            }
        }
        notifyDataSetChanged();

    }

    public void filterClientRestant(int variable, List<Client> clientWithoutVisits) {
        clientLists.clear();

        if (variable == 1) {
            clientLists.addAll(clientWithoutVisits);

        } else {
            clientLists.addAll(arrayList);
        }
        notifyDataSetChanged();

    }

    public void filterClientProche(int variable, List<Client> clientsProches) {
        clientLists.clear();

        if (!clientsProches.equals(null)) {
            if (variable == 1) {
                clientLists.addAll(clientsProches);

            } else {
                clientLists.addAll(arrayList);
            }
        }

        notifyDataSetChanged();

    }

    public Bitmap decodeImage(String encodeImage) {

        byte[] decodedString = Base64.decode(encodeImage, 0);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public String resizeBase64Image(String base64image) {

        byte[] encodeByte = Base64.decode(base64image.getBytes(), Base64.DEFAULT);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        Bitmap image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length, options);

        /*if(image.getHeight() <= 200 && image.getWidth() <= 200){
            return base64image;
        }
*/

        image = Bitmap.createScaledBitmap(image, 60, 60, false);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] b = baos.toByteArray();
        System.gc();
        return Base64.encodeToString(b, Base64.NO_WRAP);

    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }


}
