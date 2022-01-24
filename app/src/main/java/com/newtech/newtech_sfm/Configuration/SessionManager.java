package com.newtech.newtech_sfm.Configuration;
/**
 * Created by Mehdi on 23/06/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.newtech.newtech_sfm.Activity.AuthActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SessionManager {

   SharedPreferences pref;
    Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;


    private static final String PREF_NAME = "user_data";

    // les attributs enregistrés
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "_UTILISATEUR_NOM";
    public static final String KEY_EMAIL = "UTILISATEUR_EMAIL";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void createLoginSession(JSONObject user,String code){
        editor.putBoolean(IS_LOGIN, true);
        try {
            editor.putString(KEY_NAME, user.getString(KEY_NAME));
            editor.putString(KEY_EMAIL, user.getString(KEY_EMAIL));
            editor.putString("code", code);
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public void checkLogin(){

        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, AuthActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }

    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put("code", pref.getString("code", null));

        return user;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, AuthActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}