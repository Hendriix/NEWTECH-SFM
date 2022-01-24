package com.newtech.newtech_sfm.recensement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier_Manager.ClasseManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

public class RecensementAdapter extends RecyclerView.Adapter<RecensementAdapter.RecensementViewHolder> implements Filterable {

    private static final String TAG = RecensementAdapter.class.getName();
    private ArrayList<Client> clientArrayList;
    private ArrayList<Client> clientFullList;
    private ArrayList<Client> clientListFiltered;
    private Context context;
    private RecensementItemClickListener recensementItemClickListener;

    public RecensementAdapter(ArrayList<Client> dataList, Context context, RecensementItemClickListener recensementItemClickListener) {
        this.clientArrayList = dataList;
        this.context = context;
        clientFullList = new ArrayList<>(dataList);
        this.recensementItemClickListener = recensementItemClickListener;
    }


    @Override
    public RecensementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.client_cataloque_ligne, parent, false);
        return new RecensementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecensementViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        Client client = clientArrayList.get(position);

        String adresse_numero = "";
        String adresse_rue = "";
        String adresse_quartier ="";
        String classe_nom ="";


        ClasseManager classeManager = new ClasseManager(context);
        classe_nom = classeManager.get(client.getCLASSE_CODE()).getCLASSE_NOM();
        if(classe_nom!=null){

            if(classe_nom.equals("CLIENT A")){
                //Log.d(TAG, "getView: "+classe_nom);
                //Log.d(TAG, "getView: "+"helo helo helo");
                holder.client_nom.setText(client.getCLIENT_NOM()+" * ");
            }else{
                //Log.d(TAG, "getView: "+classe_nom);
                holder.client_nom.setText(client.getCLIENT_NOM());
                //Log.d(TAG, "getView: "+"nono nono nono");
            }
        }else{
            holder.client_nom.setText(client.getCLIENT_NOM());
        }

        if(client.getADRESSE_NR().equals("null")){
            adresse_numero = "N°X ";
        }else{
            adresse_numero = client.getADRESSE_NR();
        }

        if(client.getADRESSE_RUE().equals("null")){
            adresse_rue = "Rue x ";
        }else{
            adresse_rue = client.getADRESSE_RUE();
        }

        if(client.getADRESSE_QUARTIER().equals("null")){
            adresse_quartier = "Quartier x";
        }else{
            adresse_quartier = client.getADRESSE_QUARTIER();
        }

        holder.client_code.setText(client.getCLIENT_CODE());
        holder.client_adresse.setText(adresse_numero+" "+adresse_rue+" "+adresse_quartier);
        holder.client_classe.setText(client.getTOURNEE_CODE());

        String base64Image = "";

        if(client.getIMAGE().toString().contains(",")){

            base64Image = String.valueOf(client.getIMAGE()).split(",")[1];

        }else{

            base64Image=String.valueOf(client.getIMAGE());
        }

        if(base64Image.length()<10 || base64Image.equals("") || base64Image==null){

                holder.client_image.setImageResource(R.drawable.icone_pers);

        }else{
            byte[] decodedString= Base64.decode(base64Image,Base64.DEFAULT);

            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            holder.client_image.setImageBitmap(Bitmap.createScaledBitmap(decodedByte,decodedByte.getWidth() , decodedByte.getHeight(), false));
        }

    }

    @Override
    public int getItemCount() {
        return clientArrayList.size();
    }

    class RecensementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView client_nom;
        TextView client_code;
        TextView client_adresse;
        TextView client_classe;
        ImageView client_image;

        RecensementViewHolder(View itemView) {
            super(itemView);

            client_nom = itemView.findViewById(R.id.client_nom);
            client_code = itemView.findViewById(R.id.client_code);
            client_adresse = itemView.findViewById(R.id.client_addresse);
            client_classe = itemView.findViewById(R.id.client_classe);
            client_image = itemView.findViewById(R.id.client_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            recensementItemClickListener.onRecensementItemClick(position);
        }
    }

    /*public void filter(String textfilter){
        textfilter=textfilter.toLowerCase(Locale.getDefault());
        clientArrayList.clear();
        if (textfilter.length() == 0) {
            clientArrayList.addAll(clientArrayList);
        }
        else
        {
            for (Client client : clientArrayList)
            {
                if (client.getCLIENT_NOM().toLowerCase(Locale.getDefault()).contains(textfilter))
                {
                    clientArrayList.add(client);
                }
            }
        }
        notifyDataSetChanged();

    }*/

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                ArrayList<Client> clientListFilteredSecond = new ArrayList<>();



                if (charSequence == null || charSequence.length() == 0) {


                    clientListFiltered = clientFullList;

                } else {
                    String charString = charSequence.toString();

                    for (Client row : clientFullList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getCLIENT_NOM().toLowerCase().contains(charString.toLowerCase()) || row.getTOURNEE_CODE().contains(charSequence)) {
                            clientListFilteredSecond.add(row);
                        }
                    }

                    clientListFiltered = clientListFilteredSecond;
                }


                FilterResults filterResults = new FilterResults();
                filterResults.values = clientListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                clientArrayList.clear();
                clientArrayList = (ArrayList<Client>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    interface RecensementItemClickListener{
        void onRecensementItemClick(int position);
    }
}