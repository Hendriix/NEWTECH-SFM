package com.newtech.newtech_sfm.thread;

import android.content.Context;
import android.util.Log;

import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;

public class MyThread implements Runnable{

    private static final String TAG = MyThread.class.getName();
    private Context context;

    public void MyThread(Context context){
        this.context = context;
    }


    @Override
    public void run() {
        synchronized (this){
            Log.d(TAG, "run: I'm running");
            ArticleManager.synchronisationArticle(context);
            notify();
        }

    }

}
