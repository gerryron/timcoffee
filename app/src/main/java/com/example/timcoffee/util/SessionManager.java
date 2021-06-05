package com.example.timcoffee.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.timcoffee.model.User;

import java.util.HashMap;

public class SessionManager {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String NOMER_HP = "nomerHp";
    public static final String EMAIL = "email";
    public static final String ROLE = "role";

    public SessionManager (Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(User user) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(ID, String.valueOf(user.getId()));
        editor.putString(NAME, user.getName());
        editor.putString(NOMER_HP, user.getNomerHp());
        editor.putString(EMAIL, user.getEmail());
        editor.putString(ROLE, user.getRole());
        editor.commit();
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(ID, sharedPreferences.getString(ID, null));
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(NOMER_HP, sharedPreferences.getString(NOMER_HP, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(ROLE, sharedPreferences.getString(ROLE, null));
        return user;
    }

    public void logoutSession() {
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedin() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }
}
