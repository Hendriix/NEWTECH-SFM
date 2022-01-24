package com.newtech.newtech_sfm.RecensementClient;

public class RecensementClientPresenter {

    private static final String TAG = RecensementClientPresenter.class.getName();

    private RecensementClientPresenter.RecensementClientView view;

    public RecensementClientPresenter(RecensementClientPresenter.RecensementClientView recensementClientView) {
        this.view = recensementClientView;
    }

    public interface RecensementClientView{

        void showError(String message);
        void showEmpty(String message);
        void showLoading();

    }
}
