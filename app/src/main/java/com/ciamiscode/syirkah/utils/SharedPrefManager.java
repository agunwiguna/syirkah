package com.ciamiscode.syirkah.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    public static final String SP_SYIRKAH_APP = "spSyirkahApp";

    public static final String SP_ID_USER = "spIdUser";
    public static final String SP_NAMA = "spNama";
    public static final String SP_ALAMAT = "spAlamat";
    public static final String SP_EMAIL = "spEmail";
    public static final String SP_TELPON = "spTelpon";
    public static final String SP_PERUSAHAAN = "spPerusahaan";
    public static final String SP_ALAMAT_PERUSAHAAN = "spAlamatPerusahaan";
    public static final String SP_FOTO = "spFoto";
    public static final String SP_EMAS = "spEmas";
    public static final String SP_PERAK = "spPerak";

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_SYIRKAH_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getIdUser(){
        return sp.getString(SP_ID_USER, "");
    }

    public String getSPNama(){
        return sp.getString(SP_NAMA, "");
    }

    public String getSPAlamat(){
        return sp.getString(SP_ALAMAT, "");
    }

    public String getSPEmail(){
        return sp.getString(SP_EMAIL, "");
    }

    public String getSPTelpon(){
        return sp.getString(SP_TELPON, "");
    }

    public String getSpPerusahaan() {
        return sp.getString(SP_PERUSAHAAN,"");
    }

    public String getSpAlamatPerusahaan() {
        return sp.getString(SP_ALAMAT_PERUSAHAAN,"");
    }

    public String getSpFoto() {
        return sp.getString(SP_FOTO,"") ;
    }

    public String getSpEmas() {
        return sp.getString(SP_EMAS,"");
    }

    public String getSpPerak() {
        return sp.getString(SP_PERAK,"");
    }

    public Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }



}
