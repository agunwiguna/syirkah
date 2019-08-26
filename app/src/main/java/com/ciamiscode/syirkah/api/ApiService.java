package com.ciamiscode.syirkah.api;

import com.ciamiscode.syirkah.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseModel> loginRequest(@Field("email") String email,
                                     @Field("password") String password);

    @FormUrlEncoded
    @POST("tambah.php")
    Call<ResponseModel> postAkun(@Field("email") String email,
                                   @Field("password") String password);


}
