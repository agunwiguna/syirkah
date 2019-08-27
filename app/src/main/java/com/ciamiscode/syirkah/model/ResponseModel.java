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

    public String getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getUser() {
        return user;
    }
}
