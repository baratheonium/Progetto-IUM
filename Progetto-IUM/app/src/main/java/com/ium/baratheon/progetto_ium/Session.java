package com.ium.baratheon.progetto_ium;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

/**
 * Created by utente on 25/05/2017.
 */

public class Session {
    private static Session instance;
    private SharedPreferences prefs;

    private Session(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static Session getInstance(Context context){
        if (instance == null){
            instance = new Session(context);
        }
        return instance;
    }

    public void setPrefs(String username, String password, User user){
        this.setUsername(username);
        this.setPassword(password);
        this.setUser(user);
    }

    public void setUsername(String username) {
        prefs.edit().putString("username", username).commit();
    }

    public String getUsername() {
        return prefs.getString("username","");
    }

    public void removePrefs(){
        prefs.edit().clear().commit();
    }

    public void setPassword(String password){
        prefs.edit().putString("password", password).commit();
    }

    public String getPassword() {
        return prefs.getString("password","");
    }

    public void setUser(User user){
        Gson gson = new Gson();
        String jsonUser = gson.toJson(user);
        prefs.edit().putString("user", jsonUser);
    }

    public User getUser(){
        Gson gson = new Gson();
        return gson.fromJson(prefs.getString("password", ""), User.class);
    }
}
