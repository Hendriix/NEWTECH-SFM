package com.newtech.newtech_sfm.Fragement;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.newtech.newtech_sfm.Activity.R_CommandeLignes;
import com.newtech.newtech_sfm.Fragement.CommandeFragment.OnListFragmentInteractionListener;
import com.newtech.newtech_sfm.Fragement.dummy.DummyContent.DummyItem;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.Tournee;
import com.newtech.newtech_sfm.Metier_Manager.ClientManager;
import com.newtech.newtech_sfm.Metier_Manager.ImpressionManager;
import com.newtech.newtech_sfm.Metier_Manager.TourneeManager;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.Service.BlutoothConnctionService;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCommandeFragmentRecyclerViewAdapter extends RecyclerView.Adapter<MyCommandeFragmentRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Commande> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Context context;
    ClientManager clientManager;
    TourneeManager tourneeManager;
    ImpressionManager impressionManager;

    public MyCommandeFragmentRecyclerViewAdapter(ArrayList<Commande> items, OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        this.context = context;
        clientManager = new ClientManager(context);
        tourneeManager = new TourneeManager(context);
        impressionManager = new ImpressionManager(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_commandefragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if(mValues.get(position).getCLIENT_CODE() != null){
            Client client = clientManager.get(mValues.get(position).getCLIENT_CODE());

            if(client!=null){
                holder.client_code.setText(client.getCLIENT_NOM());
            }else{
                holder.client_code.setText(mValues.get(position).getCLIENT_CODE());
            }
        }else{
            holder.client_code.setText(mValues.get(position).getCLIENT_CODE());

        }

        if(mValues.get(position).getTOURNEE_CODE() != null){
            Tournee tournee = tourneeManager.get(mValues.get(position).getTOURNEE_CODE());

            if(tournee!=null){
                holder.commande_tournee.setText(tournee.getTOURNEE_NOM());
            }else{
                holder.commande_tournee.setText(mValues.get(position).getTOURNEE_CODE());
            }
        }else{
            holder.commande_tournee.setText(mValues.get(position).getTOURNEE_CODE());

        }

        holder.commande_code.setText(mValues.get(position).getCOMMANDE_CODE());
        holder.commande_date.setText(mValues.get(position).getDATE_COMMANDE());
        holder.commande_montant.setText(String.valueOf(mValues.get(position).getMONTANT_NET())+" DH");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {

                    Intent intent = new Intent(context, R_CommandeLignes.class);
                    R_CommandeLignes.commande_code = mValues.get(position).getCOMMANDE_CODE();
                    context.startActivity(intent);

                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Log.d("CommandeGragmebt", "onLongClick: HALO");
                final Commande commande = mValues.get(position);
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.alert_imprimante_all_commande);
                dialog.setTitle("Impression");
                dialog.setCanceledOnTouchOutside(false);
                Button print = (Button) dialog.findViewById(R.id.imprimer);
                Button done = (Button) dialog.findViewById(R.id.terminer);
                final TextView nbr_copies = (TextView) dialog.findViewById(R.id.nbr_copies);

                print.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i=0;i<Integer.valueOf(nbr_copies.getText().toString());i++){

                            String impression_text=impressionManager.ImprimerCommande(commande,context);
                            Log.d("print", "onClick rapport: "+impression_text.toString());
                            BlutoothConnctionService.imprimanteManager.printText(impression_text);


                        }

                    }
                });


                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }

                });

                dialog.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public final TextView commande_code;
        public final TextView client_code;
        public final TextView commande_date;
        public final TextView commande_tournee;
        public final TextView commande_montant;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            commande_code = (TextView) view.findViewById(R.id.cmdcodetv);
            client_code = (TextView) view.findViewById(R.id.clientcodetv);
            commande_date = (TextView) view.findViewById(R.id.cmddatetv);
            commande_tournee = (TextView) view.findViewById(R.id.tourneecodetv);
            commande_montant = (TextView) view.findViewById(R.id.montanttv);


        }

    }
}
