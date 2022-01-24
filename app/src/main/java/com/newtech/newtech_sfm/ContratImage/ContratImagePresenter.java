package com.newtech.newtech_sfm.ContratImage;

import android.content.Context;

import com.newtech.newtech_sfm.Metier.ChoufouniContrat;
import com.newtech.newtech_sfm.Metier_Manager.ChoufouniContratManager;
import com.newtech.newtech_sfm.Metier_Manager.ChoufouniContratPullManager;

import java.util.ArrayList;

public class ContratImagePresenter {

    private static final String TAG = ContratImagePresenter.class.getName();

    private ContratImageView view;

    public ContratImagePresenter(ContratImageView contratImageView) {
        this.view = contratImageView;
    }


    public void getChoufouniContratList(Context context, String client_code){

        if(view != null){
            view.showLoading();
        }

        ChoufouniContratPullManager choufouniContratPullManager = new ChoufouniContratPullManager(context);
        ChoufouniContratManager choufouniContratManager = new ChoufouniContratManager(context);
        ArrayList<ChoufouniContrat> choufouniContratArrayList = new ArrayList<>();
        ArrayList<ChoufouniContrat> choufouniContratPullArrayList = new ArrayList<>();

        choufouniContratArrayList = choufouniContratManager.getListByClientCode(client_code);
        choufouniContratPullArrayList = choufouniContratPullManager.getListByClientCode(client_code);

        for(int i=0 ; i<choufouniContratPullArrayList.size();i++){
            choufouniContratArrayList.add(choufouniContratPullArrayList.get(i));
        }

        if(choufouniContratArrayList.size() > 0 ){
            view.showSuccess(choufouniContratArrayList);
        }else{
            view.showEmpty("AUCUN CONTRAT TROUVEE");
        }
    }

    public interface ContratImageView{
        void showSuccess(ArrayList<ChoufouniContrat> choufouniContrats);
        void showError(String message);
        void showEmpty(String message);
        void showLoading();
    }
}
