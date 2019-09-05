package com.ciamiscode.syirkah.model;

import com.google.gson.annotations.SerializedName;

public class LogamModel {

    @SerializedName("id_logam")
    private String id_logam;
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("jenis")
    private String jenis;
    @SerializedName("berat")
    private String berat;
    @SerializedName("tahun_produksi")
    private String tahun_produksi;
    @SerializedName("harga_beli")
    private String harga_beli;
    @SerializedName("harga_jual")
    private String harga_jual;
    @SerializedName("status")
    private String status;
    @SerializedName("nama")
    private String nama;
    @SerializedName("telpon")
    private String telpon;
    @SerializedName("perusahaan")
    private String perusahaan;

    public String getId_logam() {
        return id_logam;
    }

    public void setId_logam(String id_logam) {
        this.id_logam = id_logam;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getBerat() {
        return berat;
    }

    public void setBerat(String berat) {
        this.berat = berat;
    }

    public String getTahun_produksi() {
        return tahun_produksi;
    }

    public void setTahun_produksi(String tahun_produksi) {
        this.tahun_produksi = tahun_produksi;
    }

    public String getHarga_beli() {
        return harga_beli;
    }

    public void setHarga_beli(String harga_beli) {
        this.harga_beli = harga_beli;
    }

    public String getHarga_jual() {
        return harga_jual;
    }

    public void setHarga_jual(String harga_jual) {
        this.harga_jual = harga_jual;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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
}
