package com.newtech.newtech_sfm.Configuration;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.newtech.newtech_sfm.Activity.VisiteActivity;
import com.newtech.newtech_sfm.Metier.Tournee;
import com.newtech.newtech_sfm.R;

import java.util.List;

/**
 * Created by stagiaireit2 on 26/07/2016.
 */
public class RVAdapter_Tournees extends  RecyclerView.Adapter<RVAdapter_Tournees.PersonViewHolder>{
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
    public String utilisateur_code;

    public String getUtilisateur_code() {
        return utilisateur_code;
    }

    public void setUtilisateur_code(String utilisateur_code) {
        this.utilisateur_code = utilisateur_code;
    }

    List<Tournee> tournees;
    public RVAdapter_Tournees(List<Tournee> tournees){
        this.tournees = tournees;
    }

    @Override
    public int getItemCount() {
        return tournees.size();
    }


    @Override
    public RVAdapter_Tournees.PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_cell3, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final RVAdapter_Tournees.PersonViewHolder personViewHolder, final int i) {
        personViewHolder.personName.setText(tournees.get(i).getTOURNEE_NOM());
        personViewHolder.personAge.setText(tournees.get(i).getTOURNEE_CODE());
        personViewHolder.personPhoto.setImageResource(R.drawable.icone_route);
        personViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                VisiteActivity.activity_source="CatalogueTourneeActivity";
                Intent intent = new Intent(view.getContext(), VisiteActivity.class);
                VisiteActivity.commande_source="VISITE";
                intent.putExtra("TOURNEE_CODE",tournees.get(i).getTOURNEE_CODE());
                intent.putExtra("UTILISATEUR_CODE",utilisateur_code);
                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
