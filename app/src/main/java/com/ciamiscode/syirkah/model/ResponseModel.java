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
    List<InvestorModel> result_investor;
    List<LogamModel> result_logam_user;
    List<LogamModel> result_logam;
    List<NewsModel> result_news;

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

    public List<InvestorModel> getResult_investor() {
        return result_investor;
    }

    public void setResult_investor(List<InvestorModel> result_investor) {
        this.result_investor = result_investor;
    }

    public List<LogamModel> getResult_logam_user() {
        return result_logam_user;
    }

    public void setResult_logam_user(List<LogamModel> result_logam_user) {
        this.result_logam_user = result_logam_user;
    }

    public List<LogamModel> getResult_logam() {
        return result_logam;
    }

    public void setResult_logam(List<LogamModel> result_logam) {
        this.result_logam = result_logam;
    }

    public List<NewsModel> getResult_news() {
        return result_news;
    }

    public void setResult_news(List<NewsModel> result_news) {
        this.result_news = result_news;
    }
}
