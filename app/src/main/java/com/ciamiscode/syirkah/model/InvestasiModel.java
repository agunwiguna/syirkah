package com.ciamiscode.syirkah.model;

import com.google.gson.annotations.SerializedName;

public class InvestasiModel {

    @SerializedName("id_user")
    private String id_user;
    @SerializedName("id_investasi")
    private String id_investasi;
    @SerializedName("nama_investasi")
    private String nama_investasi;
    @SerializedName("deskripsi")
    private String deskripsi;
    @SerializedName("tgl_mulai")
    private String tgl_mulai;
    @SerializedName("tgl_selesai")
    private String tgl_selesai;
    @SerializedName("kebutuhan_biaya")
    private String kebutuhan_biaya;
    @SerializedName("total_biaya")
    private String total_biaya;
    @SerializedName("nama")
    private String nama;
    @SerializedName("perusahaan")
    private String perusahaan;
    @SerializedName("foto")
    private String foto;
    @SerializedName("sisa")
    private String sisa;

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String  id_user) {
        this.id_user = id_user;
    }

    public String getNama_investasi() {
        return nama_investasi;
    }

    public void setNama_investasi(String nama_investasi) {
        this.nama_investasi = nama_investasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTgl_mulai() {
        return tgl_mulai;
    }

    public void setTgl_mulai(String tgl_mulai) {
        this.tgl_mulai = tgl_mulai;
    }

    public String getTgl_selesai() {
        return tgl_selesai;
    }

    public void setTgl_selesai(String tgl_selesai) {
        this.tgl_selesai = tgl_selesai;
    }

    public String getKebutuhan_biaya() {
        return kebutuhan_biaya;
    }

    public void setKebutuhan_biaya(String kebutuhan_biaya) {
        this.kebutuhan_biaya = kebutuhan_biaya;
    }

    public String getTotal_biaya() {
        return total_biaya;
    }

    public void setTotal_biaya(String total_biaya) {
        this.total_biaya = total_biaya;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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

    public String getId_investasi() {
        return id_investasi;
    }

    public void setId_investasi(String id_investasi) {
        this.id_investasi = id_investasi;
    }

    public String getSisa() {
        return sisa;
    }

    public void setSisa(String sisa) {
        this.sisa = sisa;
    }
}
