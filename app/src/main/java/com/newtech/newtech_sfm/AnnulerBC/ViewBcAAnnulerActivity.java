package com.newtech.newtech_sfm.AnnulerBC;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.newtech.newtech_sfm.Activity.ClientActivity;
import com.newtech.newtech_sfm.Activity.NewPanierActivity;
import com.newtech.newtech_sfm.Metier.Commande;
import com.newtech.newtech_sfm.Metier.CommandeLigne;
import com.newtech.newtech_sfm.Metier.Unite;
import com.newtech.newtech_sfm.Metier.User;
import com.newtech.newtech_sfm.Metier_Manager.CommandeLigneManager;
import com.newtech.newtech_sfm.Metier_Manager.CommandeManager;
import com.newtech.newtech_sfm.Metier_Manager.UniteManager;
import com.newtech.newtech_sfm.Metier_Manager.UserManager;
import com.newtech.newtech_sfm.Metier_Manager.VisiteManager;
import com.newtech.newtech_sfm.R;
import com.newtech.newtech_sfm.dialog.DialogConfirmation;
import com.newtech.newtech_sfm.dialog.DialogInformation;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by TONPC on 19/04/2017.
 */

public class ViewBcAAnnulerActivity extends AppCompatActivity implements DialogConfirmation.AnnulerBcView,AnnulerBcPresenter.AnnulerBcView{

    public static String commandeSource;
    public static String visite_code;
    public static Commande commande= new Commande();
    public static  ArrayList<CommandeLigne> ListeCommandeLigne = new ArrayList<CommandeLigne>();

    CommandeManager commandeManager;
    CommandeLigneManager commandeLigneManager;
    UniteManager uniteManager;
    UserManager userManager ;
    User utilisateur;
    VisiteManager visiteManager;
    DialogConfirmation customDialog;
    private AnnulerBcPresenter presenter;
    private ProgressDialog progressDialog;
    private boolean isSuccesAnnulation = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        userManager = new UserManager(getApplicationContext());
        commandeManager = new CommandeManager(getApplicationContext());
        uniteManager = new UniteManager(getApplicationContext());
        visiteManager = new VisiteManager(getApplicationContext());
        commandeLigneManager = new CommandeLigneManager(getApplicationContext());
        presenter = new AnnulerBcPresenter(this);

        utilisateur = userManager.get();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_panier);

        initProgressDialog();
        TableLayout tableVente= (TableLayout)findViewById(R.id.tableLayout1);
        TableLayout tableVente2= (TableLayout)findViewById(R.id.tableLayout2);

        final Button valider =(Button)findViewById(R.id.btn_valider1);
        Button  annuler =(Button)findViewById(R.id.btn_annuler1);

        TextView panier_nbr_total =(TextView) findViewById(R.id.nbr_panier_total1);
        TextView panier_nbr_remise =(TextView) findViewById(R.id.nbr_panier_remise1);
        TextView panier_nbr_net =(TextView) findViewById(R.id.nbr_panier_net1);

        double panierTotal=0;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);

        if(ListeCommandeLigne!=null){

            ListeCommandeLigne = commandeLigneManager.fixCmdLigneCode(ListeCommandeLigne);


            for(int i=0;i<ListeCommandeLigne.size();i++){

                TableRow ligne1 = new TableRow(this);

                TextView ligne_code = new TextView(this);
                TextView art_code = new TextView(this);
                TextView unite = new TextView(this);
                TextView qte = new TextView(this);
                TextView remise1= new TextView(this);
                TextView total = new TextView(this);

                Unite unite1 = uniteManager.get(ListeCommandeLigne.get(i).getUNITE_CODE());

                ligne_code.setEllipsize(TextUtils.TruncateAt.END);
                unite.setEllipsize(TextUtils.TruncateAt.END);

                ligne_code.setGravity(Gravity.CENTER);
                art_code.setGravity(Gravity.CENTER);
                unite.setGravity(Gravity.CENTER);
                qte.setGravity(Gravity.CENTER);
                remise1.setGravity(Gravity.CENTER);
                total.setGravity(Gravity.CENTER);


                ligne_code.setTextSize(15);
                art_code.setTextSize(15);
                unite.setTextSize(15);
                qte.setTextSize(15);
                total.setTextSize(15);

                ligne_code.setBackgroundResource(R.drawable.cell_shape);
                art_code.setBackgroundResource(R.drawable.cell_shape);
                unite.setBackgroundResource(R.drawable.cell_shape);
                qte.setBackgroundResource(R.drawable.cell_shape);
                remise1.setBackgroundResource(R.drawable.cell_shape);
                total.setBackgroundResource(R.drawable.cell_shape);


                ligne_code.setText(String.valueOf(ListeCommandeLigne.get(i).getCOMMANDELIGNE_CODE()));
                art_code.setText(ListeCommandeLigne.get(i).getARTICLE_CODE());
                unite.setText(unite1.getUNITE_NOM());
                qte.setText(String.valueOf(ListeCommandeLigne.get(i).getQTE_COMMANDEE()));
                remise1.setText("0");
                //total.setText(String.valueOf(new DecimalFormat("##.##").format(ListeCommandeLigne.get(i).getARTICLE_PRIX()*ListeCommandeLigne.get(i).getQTE_COMMANDEE())));
                total.setText(String.format("%.1f",ListeCommandeLigne.get(i).getMONTANT_BRUT()));

                ligne1.addView(ligne_code);
                ligne1.addView(art_code);
                ligne1.addView(unite);
                ligne1.addView(qte);
                ligne1.addView(remise1);
                ligne1.addView(total);

                tableVente.addView(ligne1);

            }
        }

        panierTotal=commandeManager.getMontantNet(ListeCommandeLigne);

        panier_nbr_total.setText(String.valueOf(new DecimalFormat("##.##").format(panierTotal)));

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //##################################################################### REMISE GRATUITE ##########################################################

        panier_nbr_remise.setText(String.valueOf(new DecimalFormat("##.##").format(commande.getREMISE())));
        panier_nbr_net.setText(String.valueOf(new DecimalFormat("##.##").format(commande.getMONTANT_NET())));

        //################################################################# FIN REMISE ###################################################################


            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        customDialog = new DialogConfirmation(ViewBcAAnnulerActivity.this,this,"VOULEZ VOUS VRAIMENR ANNULER CETTE COMMANDE ??");

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                onBackPressed();
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                customDialog.show();
            }
        });

        setTitle("PANIER COMMANDES A ANNULER");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_panier, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.plus:

                if(commande==null){
                    new AlertDialog.Builder(ViewBcAAnnulerActivity.this)
                            .setTitle("ALERTE COMMANDE")
                            .setMessage("l'OBJET COMMANDE EST NULL")
                            .setPositiveButton("ANNULLER", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            })
                            .show();

                }else{

                    if(!commandeSource.equals("Annuler")){
                        Intent intent = new Intent(this, NewPanierActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    }

                }


        }
        return super.onOptionsItemSelected(item);
    }

    public final void annulerBc(){
        showText(commande.getCOMMANDE_CODE());
    }


    private void showText(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        ListeCommandeLigne.clear();
        commande=null;

        Intent i = new Intent(this, AnnulerBcActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void accepteAnnulation() {
        customDialog.dismiss();
        presenter.synchronisationCommandeAAnnuler(this,commande.getCLIENT_CODE(),commande.getVENDEUR_CODE(),commande.getCOMMANDE_CODE());
    }

    @Override
    public void refuseAnnulation() {
        customDialog.dismiss();
    }


    @Override
    public void showSuccess(String message, int code) {

        if(code == 0){
            isSuccesAnnulation = false;
        }else{
            isSuccesAnnulation = true;
        }

        progressDialog.dismiss();
        DialogInformation dialogInformation = new DialogInformation(ViewBcAAnnulerActivity.this,message);
        dialogInformation.show();
    }

    @Override
    public void showError(String message) {
        isSuccesAnnulation = false;
        progressDialog.dismiss();
        DialogInformation dialogInformation = new DialogInformation(ViewBcAAnnulerActivity.this, message);
        dialogInformation.show();
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    private void initProgressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Opération en cours");
    }

    public void switchToClientActivity(){

        if(isSuccesAnnulation){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date_visite=df.format(Calendar.getInstance().getTime());
            visiteManager.updateVisite_VRDF(ClientActivity.visiteCourante.getVISITE_CODE(),-1,date_visite);
            Intent i = new Intent(ViewBcAAnnulerActivity.this, ClientActivity.class);
            i.putExtra("VISITERESULTAT_CODE",-1);
            startActivity(i);
            finish();
        }else{
            Intent i = new Intent(ViewBcAAnnulerActivity.this, AnnulerBcActivity.class);
            startActivity(i);
            finish();
        }

    }

}
