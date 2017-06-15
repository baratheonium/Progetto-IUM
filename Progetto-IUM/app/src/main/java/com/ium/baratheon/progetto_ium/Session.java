package com.ium.baratheon.progetto_ium;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

/**
 * Created by Riccardo Locci on 15/06/2017.
 *
 */

class Session {
    private static Session instance;
    private SharedPreferences prefs;

    private Session(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    static Session getInstance(Context context){
        if (instance == null){
            instance = new Session(context);
        }
        return instance;
    }

    void setPrefs(String username, String password, User user){
        this.setUsername(username);
        this.setPassword(password);
        this.setUser(user);
    }

    private void setUsername(String username) {
        prefs.edit().putString("username", username).apply();
    }

    String getUsername() {
        return prefs.getString("username","");
    }

    public void removePrefs(){
        prefs.edit().clear().apply();
    }

    private void setPassword(String password){
        prefs.edit().putString("password", password).apply();
    }

    String getPassword() {
        return prefs.getString("password","");
    }

    public void setUser(User user){
        Gson gson = new Gson();
        String jsonUser = gson.toJson(user);
        prefs.edit().putString("user", jsonUser).apply();
    }

    public User getUser(){
        Gson gson = new Gson();
        return gson.fromJson(prefs.getString("user", ""), User.class);
    }

    public SharedPreferences getPrefs(){
        return this.prefs;
    }

    public Boolean hasPrefs(){
        return this.prefs.contains("username") && this.prefs.contains("password") && this.prefs.contains("user");
    }
}
