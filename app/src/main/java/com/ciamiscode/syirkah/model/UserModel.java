package com.ciamiscode.syirkah.model;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("nama")
    private String nama;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("email")
    private String email;
    @SerializedName("telpon")
    private String telpon;
    @SerializedName("perusahaan")
    private String perusahaan;
    @SerializedName("alamat_perusahaan")
    private String alamat_perusahaan;
    @SerializedName("password")
    private String passwword;
    @SerializedName("foto")
    private String foto;
    @SerializedName("emas")
    private String emas;
    @SerializedName("perak")
    private String perak;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswword() {
        return passwword;
    }

    public void setPasswword(String passwword) {
        this.passwword = passwword;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelpon() {
        return telpon;
    }

    public void setTelpon(String telpon) {
        this.telpon = telpon;
    }

    public String getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(String perusahaan) {
        this.perusahaan = perusahaan;
    }

    public String getAlamat_perusahaan() {
        return alamat_perusahaan;
    }

    public void setAlamat_perusahaan(String alamat_perusahaan) {
        this.alamat_perusahaan = alamat_perusahaan;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEmas() {
        return emas;
    }

    public void setEmas(String emas) {
        this.emas = emas;
    }

    public String getPerak() {
        return perak;
    }

    public void setPerak(String perak) {
        this.perak = perak;
    }
}
