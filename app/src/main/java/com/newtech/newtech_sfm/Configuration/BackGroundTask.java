package com.newtech.newtech_sfm.Configuration;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.newtech.newtech_sfm.Metier.LogSync;
import com.newtech.newtech_sfm.Metier_Manager.LogSyncManager;

import java.util.ArrayList;

public class BackGroundTask extends AsyncTask<Void,LogSync,Void> {


    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Context context;
    private SynchRecyclerAdapter synchRecyclerAdapter;
    private ArrayList<LogSync> logSyncs = new ArrayList<>();


    public BackGroundTask(RecyclerView recyclerView, ProgressBar progressBar, Context context) {
        this.recyclerView = recyclerView;
        this.progressBar = progressBar;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        //LogSyncManager logSyncManager = new LogSyncManager(context);
        //logSyncs = logSyncManager.getList();

        synchRecyclerAdapter = new SynchRecyclerAdapter(logSyncs);
        recyclerView.setAdapter(synchRecyclerAdapter);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        int i=0;

        LogSyncManager logSyncManager = new LogSyncManager(context);
        logSyncs = logSyncManager.getList();

        int size = logSyncs.size();

        Log.d("AsynckTask", "doInBackground: size "+size);

        while(i<size){

            Log.d("AsynckTask", "doInBackground while: "+i+" element "+logSyncs.get(i));
            publishProgress(logSyncs.get(i));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onProgressUpdate(LogSync... values) {
        Log.d("AsynckTask", "onProgressUpdate: "+values[0]);
        logSyncs.add(values[0]);
        synchRecyclerAdapter.notifyDataSetChanged();
    }


}
