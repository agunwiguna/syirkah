package com.ciamiscode.syirkah.model;

import com.google.gson.annotations.SerializedName;

public class InvestorModel {

    @SerializedName("nama")
    private String nama;
    @SerializedName("telpon")
    private String telpon;
    @SerializedName("perusahaan")
    private String perusahaan;
    @SerializedName("foto")
    private String foto;
    @SerializedName("dana")
    private String dana;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDana() {
        return dana;
    }

    public void setDana(String dana) {
        this.dana = dana;
    }
}
