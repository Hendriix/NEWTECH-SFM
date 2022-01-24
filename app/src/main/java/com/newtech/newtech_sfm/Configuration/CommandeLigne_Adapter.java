package com.newtech.newtech_sfm.Configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier.Unite;
import com.newtech.newtech_sfm.Metier_Manager.UniteManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sferricha on 30/12/2016.
 */

public class CommandeLigne_Adapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<CommandeLigne> commandeligneLists;
    private ArrayList<CommandeLigne> arrayList;
    private Context context;


    public CommandeLigne_Adapter(Activity activity, List<CommandeLigne> commandeligneLists, Context context) {
        this.activity = activity;
        this.commandeligneLists = commandeligneLists;
        this.arrayList=new ArrayList<CommandeLigne>();
        this.arrayList.addAll(commandeligneLists);
        this.context = context;
    }

    @Override
    public int getCount() {
        return commandeligneLists.size();
    }

    @Override
    public Object getItem(int location) {
        return commandeligneLists.get(location);
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
            convertView = inflater.inflate(R.layout.commandeligne_ad_ligne, null);

        UniteManager uniteManager = new UniteManager(convertView.getContext());

        TextView commande_code = (TextView) convertView.findViewById(R.id.cmdcodetv);
        TextView commandeligne_code = (TextView) convertView.findViewById(R.id.cmdlignecodetv);
        TextView uniteligne = (TextView) convertView.findViewById(R.id.unitelignetv);
        TextView article_code = (TextView) convertView.findViewById(R.id.articlecodetv);

        CommandeLigne commandeligne = commandeligneLists.get(position);
        Unite unite = uniteManager.get(commandeligne.getUNITE_CODE());

        commande_code.setText("Commande code: "+commandeligne.getCOMMANDE_CODE());
        commandeligne_code.setText("Commandeligne code: "+String.valueOf(commandeligne.getCOMMANDELIGNE_CODE()));
        uniteligne.setText("Unite: "+String.valueOf(unite.getUNITE_NOM()));
        article_code.setText("Article code: "+commandeligne.getARTICLE_CODE()+" Quantité: "+commandeligne.getQTE_COMMANDEE());

        return convertView;
    }
}
