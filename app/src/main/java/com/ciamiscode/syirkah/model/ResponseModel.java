package com.ciamiscode.syirkah.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class ResponseModel {

    @SerializedName("status_code")
    private String statusCode;
    @SerializedName("message")
    private String message;
    @SerializedName("user")
    private String user;

    List<InvestasiModel> result;
    List<InvestasiModel> result_all_investasi;
    List<InvestasiModel> result_limit_investasi;
    List<UserModel> result_member;

    public String getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public List<InvestasiModel> getResult() {
        return result;
    }

    public void setResult(List<InvestasiModel> result) {
        this.result = result;
    }

    public List<InvestasiModel> getResult_all_investasi() {
        return result_all_investasi;
    }

    public void setResult_all_investasi(List<InvestasiModel> result_all_investasi) {
        this.result_all_investasi = result_all_investasi;
    }

    public List<UserModel> getResult_member() {
        return result_member;
    }

    public void setResult_member(List<UserModel> result_member) {
        this.result_member = result_member;
    }

    public List<InvestasiModel> getResult_limit_investasi() {
        return result_limit_investasi;
    }

    public void setResult_limit_investasi(List<InvestasiModel> result_limit_investasi) {
        this.result_limit_investasi = result_limit_investasi;
    }
}
