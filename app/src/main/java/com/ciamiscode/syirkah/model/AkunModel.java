package com.ciamiscode.syirkah.model;

import com.google.gson.annotations.SerializedName;

public class AkunModel {

    @SerializedName("email")
    private String email;
    @SerializedName("passwword")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
