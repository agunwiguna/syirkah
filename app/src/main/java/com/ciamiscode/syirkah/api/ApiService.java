package com.ciamiscode.syirkah.api;

import com.ciamiscode.syirkah.model.ResponseModel;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseModel> loginRequest(@Field("email") String email,
                                     @Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseModel> register(@Field("nama") String nama,
                                 @Field("alamat") String alamat,
                                 @Field("email") String email,
                                 @Field("telpon") String telpon,
                                 @Field("perusahaan") String perusahaan,
                                 @Field("alamat_perusahaan") String alamat_perusahaan,
                                 @Field("password") String password,
                                 @Field("foto") String foto);

    @FormUrlEncoded
    @POST("store_investasi.php")
    Call<ResponseModel> postInvestasi(@Field("id_user") String id_user,
                                 @Field("nama_investasi") String nama_investasi,
                                      @Field("deskripsi") String deskripsi,
                                      @Field("tgl_mulai") String tgl_mulai,
                                      @Field("tgl_selesai") String tgl_selesai,
                                      @Field("kebutuhan_biaya") String kebutuhan_biaya,
                                      @Field("total_biaya") String total_biaya);

    @FormUrlEncoded
    @POST("update_investasi.php")
    Call<ResponseModel> updateInvestasi(@Field("id_investasi") String id_investasi,
                                      @Field("nama_investasi") String nama_investasi,
                                      @Field("deskripsi") String deskripsi,
                                      @Field("tgl_mulai") String tgl_mulai,
                                      @Field("tgl_selesai") String tgl_selesai,
                                      @Field("kebutuhan_biaya") String kebutuhan_biaya,
                                      @Field("total_biaya") String total_biaya);

    @FormUrlEncoded
    @POST("store_investor.php")
    Call<ResponseModel> postInvestor(@Field("id_user_investor") String id_user_investor,
                                      @Field("id_investasi") String id_investasi,
                                      @Field("dana") int dana,
                                      @Field("sisa") int sisa);

    @FormUrlEncoded
    @POST("delete_investasi.php")
    Call<ResponseModel> deleteInvestasi(@Field("id_investasi") String id_investasi);

    @GET("tampil_investasi.php")
    Call<ResponseModel> getInvestasi(@Query("id_user") String id_user);

    @GET("tampil_all_investasi.php")
    Call<ResponseModel> getAllInvestasi();

    @GET("tampil_member.php")
    Call<ResponseModel> getAllMember();

    @GET("tampil_limit_investasi.php")
    Call<ResponseModel> getAllLimitInvestasi();

    @GET("tampil_investor.php")
    Call<ResponseModel> getAllInvestor();


}
