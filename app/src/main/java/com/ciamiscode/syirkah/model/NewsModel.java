package com.ciamiscode.syirkah.model;

import com.google.gson.annotations.SerializedName;

public class NewsModel {

    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("banner")
    private String banner;
    @SerializedName("created_at")
    private String created_at;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getBanner() {
        return banner;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
