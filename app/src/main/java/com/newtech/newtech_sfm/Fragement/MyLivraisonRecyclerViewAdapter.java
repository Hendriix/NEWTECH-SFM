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

import com.newtech.newtech_sfm.Activity.R_LivraisonLignes;
import com.newtech.newtech_sfm.Fragement.LivraisonFragment.OnListFragmentInteractionListener;
import com.newtech.newtech_sfm.Fragement.dummy.DummyContent.DummyItem;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.Livraison;
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
public class MyLivraisonRecyclerViewAdapter extends RecyclerView.Adapter<MyLivraisonRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Livraison> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Context context;
    ClientManager clientManager;
    TourneeManager tourneeManager;
    ImpressionManager impressionManager;

    public MyLivraisonRecyclerViewAdapter(ArrayList<Livraison> items, OnListFragmentInteractionListener listener, Context context) {
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
                .inflate(R.layout.fragment_livraison, parent, false);
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
                holder.livraison_tournee.setText(tournee.getTOURNEE_NOM());
            }else{
                holder.livraison_tournee.setText(mValues.get(position).getTOURNEE_CODE());
            }
        }else{
            holder.livraison_tournee.setText(mValues.get(position).getTOURNEE_CODE());

        }

        holder.livraison_code.setText(mValues.get(position).getLIVRAISON_CODE());
        holder.livraison_date.setText(mValues.get(position).getLIVRAISON_DATE());
        holder.livraison_montant.setText(String.valueOf(mValues.get(position).getMONTANT_NET())+" DH");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    Intent intent = new Intent(context, R_LivraisonLignes.class);
                    R_LivraisonLignes.livraison_code = mValues.get(position).getLIVRAISON_CODE();
                    context.startActivity(intent);
                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Log.d("CommandeGragmebt", "onLongClick: HALO");
                final Livraison livraison = mValues.get(position);
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

                            String impression_text=impressionManager.ImprimerLivraison(livraison,context);
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
        public final TextView livraison_code;
        public final TextView client_code;
        public final TextView livraison_date;
        public final TextView livraison_tournee;
        public final TextView livraison_montant;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            livraison_code = (TextView) view.findViewById(R.id.livcodetv);
            client_code = (TextView) view.findViewById(R.id.livclientcodetv);
            livraison_date = (TextView) view.findViewById(R.id.livdatetv);
            livraison_tournee = (TextView) view.findViewById(R.id.livtourneecodetv);
            livraison_montant = (TextView) view.findViewById(R.id.livmontanttv);
        }

    }
}
