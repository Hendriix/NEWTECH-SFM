package com.newtech.newtech_sfm.DialogClientFiltre;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.newtech.newtech_sfm.Activity.VisiteActivity;
import com.newtech.newtech_sfm.Metier.Categorie;
import com.newtech.newtech_sfm.Metier.Classe;
import com.newtech.newtech_sfm.Metier.Client;
import com.newtech.newtech_sfm.Metier.Tournee;
import com.newtech.newtech_sfm.Metier.Type;
import com.newtech.newtech_sfm.Metier_Manager.CategorieManager;
import com.newtech.newtech_sfm.Metier_Manager.ClasseManager;
import com.newtech.newtech_sfm.Metier_Manager.ClientManager;
import com.newtech.newtech_sfm.Metier_Manager.TourneeManager;
import com.newtech.newtech_sfm.Metier_Manager.TypeManager;
import com.newtech.newtech_sfm.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DialogClientFiltre extends DialogFragment {

    private static final String TAG = DialogClientFiltre.class.getName();
    private ProgressDialog progressDialog;

    private DialogFiltreTourneeAdapter dialogFiltreTourneeAdapter;
    private DialogFiltreTypeAdapter dialogFiltreTypeAdapter;
    private DialogFiltreClasseAdapter dialogFiltreClasseAdapter;
    private DialogFiltreCategorieAdapter dialogFiltreCategorieAdapter;

    private static Activity activity;
    private static Context context;

    TourneeManager tourneeManager;
    TypeManager typeManager;
    ClasseManager classeManager;
    CategorieManager categorieManager;
    ClientManager clientManager;

    ArrayList<Tournee> tourneeArrayList = new ArrayList<>();
    ArrayList<Type> typeArrayList = new ArrayList<>();
    ArrayList<Classe> classeArrayList = new ArrayList<>();
    ArrayList<Categorie> categorieArrayList = new ArrayList<>();


    LinearLayout tournee_ll,type_ll,categorie_ll,classe_ll;
    ListView tournee_lv,type_lv,categorie_lv,classe_lv;
    ImageView tournee_iv,type_iv,categorie_iv,classe_iv;

    Button valide_btn;
    Button clear_filter_btn;

    Boolean tournee_isopen = false;
    Boolean type_isopen = false;
    Boolean classe_isopen = false;
    Boolean categorie_isopen = false;
    String source = "";
    String tournee = "";

    SimpleDateFormat sdf;
    String dateVisiteAS;

    public DialogClientFiltre() {
    }

    public static DialogClientFiltre newInstance (Context mContext, Activity mActivity, String source, String tournee) {
        DialogClientFiltre dialogClientFiltre = new DialogClientFiltre();
        context = mContext;
        activity = mActivity;

        Bundle args = new Bundle();
        args.putString("source", source);
        args.putString("tournee", tournee);
        dialogClientFiltre.setArguments(args);

        return dialogClientFiltre;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        source = getArguments().getString("source");
        tournee = getArguments().getString("tournee");
        sdf=new SimpleDateFormat("yyyy-MM-dd");
        dateVisiteAS = sdf.format(new Date());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.multi_filter_client_layout, container, false);




        valide_btn = view.findViewById(R.id.valide_btn);
        clear_filter_btn = view.findViewById(R.id.clear_filter_btn);

        tournee_ll = view.findViewById(R.id.tournee_ll);
        type_ll = view.findViewById(R.id.type_ll);
        categorie_ll = view.findViewById(R.id.categorie_ll);
        classe_ll = view.findViewById(R.id.classe_ll);

        tournee_lv = view.findViewById(R.id.tournee_lv);
        type_lv = view.findViewById(R.id.type_lv);
        categorie_lv = view.findViewById(R.id.categorie_lv);
        classe_lv = view.findViewById(R.id.classe_lv);

        tournee_iv = view.findViewById(R.id.tournee_iv);
        type_iv = view.findViewById(R.id.type_iv);
        categorie_iv = view.findViewById(R.id.categorie_iv);
        classe_iv = view.findViewById(R.id.classe_iv);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_default);
        //definir notre toolbar en tant qu'actionBar
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle the menu item
                return true;
            }
        });
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setTitle("FILTRES");


        try{

            if(!source.equals("MenuActivity")){
                tournee_ll.setVisibility(View.GONE);
                tournee_iv.setVisibility(View.GONE);
            }


        }catch (NullPointerException e){

        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tourneeManager = new TourneeManager(context);
        typeManager = new TypeManager(context);
        classeManager = new ClasseManager(context);
        categorieManager = new CategorieManager(context);
        clientManager = new ClientManager(context);

        tourneeArrayList = tourneeManager.getList();
        typeArrayList = typeManager.getListByCatCode("CLIENT");
        classeArrayList = classeManager.getListByCateCode("CLIENT");
        categorieArrayList = categorieManager.getListByCateCode("CLIENT");

        dialogFiltreTourneeAdapter = new DialogFiltreTourneeAdapter(tourneeArrayList,activity);
        tournee_lv.setAdapter(dialogFiltreTourneeAdapter);

        dialogFiltreTypeAdapter = new DialogFiltreTypeAdapter(typeArrayList,activity);
        type_lv.setAdapter(dialogFiltreTypeAdapter);

        dialogFiltreClasseAdapter = new DialogFiltreClasseAdapter(classeArrayList,activity);
        classe_lv.setAdapter(dialogFiltreClasseAdapter);

        dialogFiltreCategorieAdapter = new DialogFiltreCategorieAdapter(categorieArrayList,activity);
        categorie_lv.setAdapter(dialogFiltreCategorieAdapter);


        tournee_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tournee_isopen){
                    tournee_isopen = false;
                    tournee_iv.setImageResource(R.drawable.plus_icone);
                    tournee_lv.setVisibility(View.GONE);
                }else{
                    tournee_isopen = true;
                    tournee_iv.setImageResource(R.drawable.minus_icone);
                    tournee_lv.setVisibility(View.VISIBLE);
                }
            }
        });

        type_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(type_isopen){
                    type_isopen = false;
                    type_iv.setImageResource(R.drawable.plus_icone);
                    type_lv.setVisibility(View.GONE);
                }else{
                    type_isopen = true;
                    type_iv.setImageResource(R.drawable.minus_icone);
                    type_lv.setVisibility(View.VISIBLE);
                }
            }
        });

        classe_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(classe_isopen){
                    classe_isopen = false;
                    classe_iv.setImageResource(R.drawable.plus_icone);
                    classe_lv.setVisibility(View.GONE);
                }else{
                    classe_isopen = true;
                    classe_iv.setImageResource(R.drawable.minus_icone);
                    classe_lv.setVisibility(View.VISIBLE);
                }
            }
        });

        categorie_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(categorie_isopen){
                    categorie_isopen = false;
                    categorie_iv.setImageResource(R.drawable.plus_icone);
                    categorie_lv.setVisibility(View.GONE);
                }else{
                    categorie_isopen = true;
                    categorie_iv.setImageResource(R.drawable.minus_icone);
                    categorie_lv.setVisibility(View.VISIBLE);
                }
            }
        });

        valide_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String chaine_tournee = "";
                if(tournee.equals("tous")){
                     chaine_tournee = dialogFiltreTourneeAdapter.getCheckedItems();
                }else{
                     chaine_tournee = "'"+tournee+"'";
                }

                String chaine_type = dialogFiltreTypeAdapter.getCheckedItems();
                String chaine_classe = dialogFiltreClasseAdapter.getCheckedItems();
                String chaine_categorie = dialogFiltreCategorieAdapter.getCheckedItems();

                // LISTE DES CLIENTS AVEC FILTRE ACTIF
                ArrayList<Client> clientArrayList =  new ArrayList<>();
                clientArrayList = clientManager.getListFiltered(chaine_tournee,chaine_type,chaine_classe,chaine_categorie);

                // LISTE DES CLIENTS NON VISITES AVEC FILTRE ACTIF
                ArrayList<Client> clientArrayList1 = new ArrayList<>();
                clientArrayList1 = clientManager.getListFilteredWithoutVisite(chaine_tournee,chaine_type,chaine_classe,chaine_categorie,dateVisiteAS);


                ((VisiteActivity)getActivity()).showFilteredClients(clientArrayList,clientArrayList1,chaine_tournee,chaine_type,chaine_classe,chaine_categorie);

            }
        });

        clear_filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(source.equals("VisiteActivity")){
                    ((VisiteActivity)getActivity()).clearClientFilter();
                }else{
                    ((CatalogueClientActivity)getActivity()).clearClientFilter();
                }*/
                ((VisiteActivity)getActivity()).clearClientFilter();
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }
}
