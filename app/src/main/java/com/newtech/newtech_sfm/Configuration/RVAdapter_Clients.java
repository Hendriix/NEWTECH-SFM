package com.newtech.newtech_sfm.Configuration;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.newtech.newtech_sfm.Activity.ClientActivity;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.R;

import java.util.List;

/**
 * Created by stagiaireit2 on 26/07/2016.
 */
public class RVAdapter_Clients   extends RecyclerView.Adapter<RVAdapter_Clients.PersonViewHolder>{

    Client clientCourant=ClientActivity.clientCourant;


    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }
    List<Client> clients;
    public RVAdapter_Clients(List<Client> persons){
        this.clients = persons;
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }


    @Override
    public RVAdapter_Clients.PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_cell3, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final RVAdapter_Clients.PersonViewHolder personViewHolder, final int i) {
        personViewHolder.personName.setText(clients.get(i).getCLIENT_NOM());
        personViewHolder.personAge.setText(clients.get(i).getCLIENT_CODE());
        personViewHolder.personPhoto.setImageResource(R.drawable.icone_pers);
        personViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(view.getContext(), ClientActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
                clientCourant=clients.get(i);
               /* AlertDialog show = new AlertDialog.Builder(personViewHolder.itemView.getContext())
                        .setTitle(clients.get(i).getCLIENT_CODE())
                        .setMessage(clients.get(i).toString() )
                        .setNeutralButton("OK", null)
                        .show();*/
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
