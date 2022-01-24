package com.newtech.newtech_sfm.Configuration;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.newtech.newtech_sfm.Activity.ClientActivity;
import com.newtech.newtech_sfm.Activity.PanierAvoirActivity;
import com.newtech.newtech_sfm.Activity.SelectArticleAvoirActivity;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.Articleprix;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier_Manager.ArticlePrixManager;
import com.newtech.newtech_sfm.R;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by stagiaireit2 on 04/07/2016.
 */

public class RvAvoirAdapter extends RecyclerView.Adapter<RvAvoirAdapter.PersonViewHolder>{
    public static String currentActivity;
    public static final  ArrayList<CommandeLigne> list_comande = new ArrayList<CommandeLigne>();


    Client clientCourant=ClientActivity.clientCourant;
    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;
        TextView nbr_de_vente;
        TextView prix_article;


        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
            nbr_de_vente=(TextView)itemView.findViewById(R.id.nbr_art_vendue);
            prix_article=(TextView)itemView.findViewById(R.id.prix_article);

        }
    }
    List<Article> articles;
    public RvAvoirAdapter(List<Article> persons){
        this.articles = persons;
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_cell2_avoir, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, final int i) {
        personViewHolder.personName.setText(articles.get(i).getARTICLE_DESIGNATION1());
        personViewHolder.personAge.setText(articles.get(i).getARTICLE_CODE());
        if(getImageId(personViewHolder.itemView.getContext(), articles.get(i).getARTICLE_CODE().toLowerCase())>0)
            personViewHolder.personPhoto.setImageResource(getImageId(personViewHolder.itemView.getContext(), articles.get(i).getARTICLE_CODE().toLowerCase()));
        else
            personViewHolder.personPhoto.setImageResource(getImageId(personViewHolder.itemView.getContext(),"bouteille_inconnu2"));

        personViewHolder.nbr_de_vente.setText(String.valueOf(articles.get(i).nbr_vente));
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        String date =df.format(Calendar.getInstance().getTime());
        ArticlePrixManager articlePrixManager = new ArticlePrixManager(personViewHolder.itemView.getContext());
       if( clientCourant!=null){
        Articleprix articlePrix =articlePrixManager.getArticlePrixBy_Unite_ArticlePrix(articles.get(i).getARTICLE_CODE(),"CAISSE", clientCourant.getCIRCUIT_CODE(),date);
        personViewHolder.prix_article.setText(String.valueOf(articlePrix.getARTICLE_PRIX()));}

        personViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                if(RvAvoirAdapter.currentActivity.equals("SelectionAvoirActivity")) {

                    final Dialog dialog = new Dialog(personViewHolder.itemView.getContext());
                    dialog.setContentView(R.layout.alert_dialog_avoir);
                    dialog.setTitle(articles.get(i).getARTICLE_DESIGNATION1());
                    Button addPanierButton = (Button) dialog.findViewById(R.id.btn_add_panier);
                    Button cancelButton = (Button) dialog.findViewById(R.id.btn_annuler);

                    ArrayList<String> items= new ArrayList<>() ;
                    items.add("CAISSE");
                    items.add("BOUTEILLE");

                    TextView txt_vente = (TextView) dialog.findViewById(R.id.txt_vente);
                    if(articles.get(i).nbr_vente>0)
                        txt_vente.setText(String.valueOf(articles.get(i).nbr_vente));

                    final Spinner  dropdown = (Spinner)dialog.findViewById(R.id.spinner_unite);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(personViewHolder.itemView.getContext(),android.R.layout.simple_dropdown_item_1line ,items );
                    dropdown.setAdapter(adapter);

                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    addPanierButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            double totalPrix = 0;

                            String unite=dropdown.getSelectedItem().toString();
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
                            String dateLivraison =df.format(Calendar.getInstance().getTime());

                            ArticlePrixManager articlePrixManager = new ArticlePrixManager(view.getContext());
                            Articleprix articlePrix =articlePrixManager.getArticlePrixBy_Unite_ArticlePrix(articles.get(i).getARTICLE_CODE(),unite, clientCourant.getCIRCUIT_CODE(),dateLivraison);
                            Log.d("Extraction du prix de ","--"+articlePrix.getARTICLE_CODE());
                           double prixArticle = articlePrix.getARTICLE_PRIX();

                            TextView vente = (TextView) dialog.findViewById(R.id.txt_vente);
                            float qteArticle=0;
                            if(vente.getText().toString()!=null){
                             qteArticle=-(Integer.parseInt(vente.getText().toString()));}

                            String  s= SelectArticleAvoirActivity.total.getText().toString();
                            s = s.replace(",", "");
                            try
                            {
                                totalPrix = Double.parseDouble(s);
                            }catch(NumberFormatException e)
                            {
                                e.printStackTrace();
                            }

                            //totalPrix=Double.parseDouble(SelectArticleActivity.total.getText().toString());
                            totalPrix+=prixArticle*(qteArticle - articles.get(i).nbr_vente);
                            SelectArticleAvoirActivity.total.setText(String.valueOf(new DecimalFormat("##.###").format(totalPrix)));
                            CommandeLigne commandeLigne = new CommandeLigne(articles.get(i),dateLivraison ,qteArticle, unite,prixArticle);

                            /* mehdi code
                            if(list_comande.contains(commandeLigne)){
                                list_comande.remove(commandeLigne);
                            }
                            */

                            for (int i=0;i<list_comande.size();i++ ) {
                                Log.d("list_commande","-- obj "+commandeLigne.getARTICLE_CODE()+"-- tab "+list_comande.get(i).getARTICLE_CODE());
                                if(list_comande.get(i).getARTICLE_CODE()==commandeLigne.getARTICLE_CODE() ){
                                    list_comande.remove(i);
                                    Log.d("list_commande","-- Commande ligne deleted from panier"+i);
                                }
                            }


                            list_comande.add(commandeLigne);
                            int nbr=Integer.parseInt(personViewHolder.nbr_de_vente.getText().toString());
                            nbr=Integer.parseInt(vente.getText().toString());
                            articles.get(i).nbr_vente=nbr;
                            if(SelectArticleAvoirActivity.dropdown.getSelectedItem().toString().equals("tous"))
                            SelectArticleAvoirActivity.listArticleParFamilleCode.put("tous",articles);
                            else
                            SelectArticleAvoirActivity.listArticleParFamilleCode.put(SelectArticleAvoirActivity.dropdown.getSelectedItem().toString(),articles);
                            personViewHolder.nbr_de_vente.setText(String.valueOf(nbr));
                            Intent i = new Intent(view.getContext(), SelectArticleAvoirActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            view.getContext().startActivity(i);

                            SelectArticleAvoirActivity.terminer.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(final View view) {
                                    SharedPreferences pref = view.getContext().getSharedPreferences("Panier", 0);
                                    SharedPreferences.Editor editor = pref.edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(list_comande);
                                    editor.putString("MonPanier", json);
                                    editor.commit();
                                    Intent i = new Intent(view.getContext(), PanierAvoirActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    view.getContext().startActivity(i);


                                }
                                        });
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                else {
                    AlertDialog show = new AlertDialog.Builder(personViewHolder.itemView.getContext())
                            .setTitle(articles.get(i).getARTICLE_CODE())
                            .setMessage(articles.get(i).toString())
                            .setNeutralButton("OK", null)
                            .show();
                }
            }
        });
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

}