package com.ciamiscode.syirkah.api;

public class UtilsApi {

    public static final String BASE_URL_API = "http://syirkah.solution.dipointer.com/";

    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

}
