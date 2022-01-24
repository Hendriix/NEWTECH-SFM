package com.newtech.newtech_sfm.Configuration;

import android.content.Context;

public class UpdateHelper {

    public static String KEY_UPDATE_ENABLE = "is_update";
    private static String KEY_UPDATE_VERSION = "version";
    private static  String KEU_UPDATE_URL = "update_url";


    public interface onUpdateCheckListener{
        void onUpdateCheckListener(String urlApp);
    }

    public static Builder with(Context context)
    {
        return new Builder(context);
    }

    private  onUpdateCheckListener onUpdateCheckListener;
    private Context context;

    public UpdateHelper(Context context,onUpdateCheckListener onUpdateCheckListener){
        this.onUpdateCheckListener = onUpdateCheckListener;
        this.context = context;
    }

    public void check(){

    }

    public static class Builder{

        private Context context;
        private onUpdateCheckListener onUpdateCheckListener;

        public Builder(Context context){
            this.context = context;
        }

        public Builder onUpdateCheck(onUpdateCheckListener onUpdateCheckListener)
        {
            this.onUpdateCheckListener = onUpdateCheckListener;
            return this;
        }


    }
}
