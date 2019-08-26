package com.ciamiscode.syirkah.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ResponseModel {

    @SerializedName("status_code")
    private String statusCode;
    @SerializedName("message")
    private String message;

    public String getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
