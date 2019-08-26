package com.ciamiscode.syirkah.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiEndPoint {

    private static final String BASE_URL = "http://syirkah.solution.dipointer.com/";

    private static Retrofit retrofit;

    public static Retrofit getClient(){

        if(retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return  retrofit;
    }

}
