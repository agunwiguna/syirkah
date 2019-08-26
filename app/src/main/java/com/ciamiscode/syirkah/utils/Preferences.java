package com.ciamiscode.syirkah.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ciamiscode.syirkah.model.UserModel;

public class Preferences {

    private static final String PREF_SESSION = "com.ciamiscode.syirkah.session";

    private final static String REGISTER_EMAIL = "REGISTER_EMAIL";
    private final static String REGISTER_PASSWORD = "REGISTER_PASSWORD";
    private final static String REGISTER_NAMA = "REGISTER_NAMA";
    private final static String LOGIN_STATUS = "LOGIN_STATUS";

    private Context context;

    public Preferences(Context context){
        this.context = context;
    }

    public static void setUserPreferences(Context context, UserModel userModel){

        SharedPreferences preferences = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(REGISTER_EMAIL, userModel.getEmail());
        editor.putString(REGISTER_PASSWORD,userModel.getPasswword());
        editor.putString(REGISTER_NAMA,userModel.getNama());
        editor.apply();
    }

    public static String getRegisteredPassword(Context context){

        SharedPreferences preferences =  context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        return preferences.getString(REGISTER_PASSWORD,UtilStatic.DEFAULT_STRING);

    }

    public static String getRegisteredNama(Context context){
        SharedPreferences preferences =  context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        return preferences.getString(REGISTER_NAMA,UtilStatic.DEFAULT_STRING);
    }

    public static void setLoggedInStatus(Context context, boolean statusLogin){
        SharedPreferences preferences = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(LOGIN_STATUS,statusLogin);
        editor.apply();
    }

    public static boolean getLoggedInStatus(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        return preferences.getBoolean(LOGIN_STATUS,UtilStatic.DEFAULT_BOOLEAN);
    }

    public static void setLogout(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(LOGIN_STATUS);
        editor.apply();
    }

}
