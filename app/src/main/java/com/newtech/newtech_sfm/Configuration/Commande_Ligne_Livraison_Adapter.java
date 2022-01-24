package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TONPC on 07/04/2017.
 */

public class Commande_Ligne_Livraison_Adapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<CommandeLigne> commandeLigneLists;

    private ArrayList<CommandeLigne> arrayList;
    private static final String TAG = SQLiteHandler.class.getSimpleName();


    public Commande_Ligne_Livraison_Adapter(Activity activity, List<CommandeLigne> commandeLigneLists) {
        this.activity = activity;
        this.commandeLigneLists = commandeLigneLists;
        this.arrayList=new ArrayList<CommandeLigne>();
        this.arrayList.addAll(commandeLigneLists);
    }


    @Override
    public int getCount() {
        return commandeLigneLists.size();
    }

    @Override
    public Object getItem(int position) {
        return commandeLigneLists.get(position);
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
            convertView = inflater.inflate(R.layout.commandeligne_panier_ligne, null);



        ImageView article_image = (ImageView) convertView.findViewById(R.id.article_image);

        TextView article_designation = (TextView) convertView.findViewById(R.id.article_designation);
        TextView article_code = (TextView) convertView.findViewById(R.id.article_code);
        TextView quantite_caisse=(TextView) convertView.findViewById(R.id.quantite_caisse);
        TextView quantite_unite=(TextView) convertView.findViewById(R.id.quantite_bouteille);
        TextView total_prix = (TextView) convertView.findViewById(R.id.total_prix);

        if(getImageId(convertView.getContext(), commandeLigneLists.get(position).getARTICLE_CODE().toLowerCase())>0)
            article_image.setImageResource(getImageId(convertView.getContext(), commandeLigneLists.get(position).getARTICLE_CODE().toLowerCase()));
        else
            article_image.setImageResource(getImageId(convertView.getContext(),"bouteille_inconnu2"));

        ArticleManager articleManager = new ArticleManager(convertView.getContext());
        article_designation.setText(commandeLigneLists.get(position).getARTICLE_DESIGNATION());
        article_code.setText(commandeLigneLists.get(position).getARTICLE_CODE());
        quantite_caisse.setText(String.valueOf((int) commandeLigneLists.get(position).getCAISSE_COMMANDEE()));

        total_prix.setText(String.valueOf((int) commandeLigneLists.get(position).getMONTANT_NET()));

        return convertView;
    }


    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }
}
