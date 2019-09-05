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

    @FormUrlEncoded
    @POST("store_logam.php")
    Call<ResponseModel> postLogam(@Field("id_user") String id_user,
                                     @Field("jenis") String jenis,
                                     @Field("berat") String berat,
                                     @Field("tahun_produksi") String tahun_produksi,
                                     @Field("harga_beli") String harga_beli,
                                     @Field("harga_jual") String harga_jual);

    @GET("tampil_logam_user.php")
    Call<ResponseModel> getLogamUser(@Query("id_user") String id_user);

    @FormUrlEncoded
    @POST("update_logam.php")
    Call<ResponseModel> updateLogam(@Field("id_logam") String id_logam,
                                  @Field("jenis") String jenis,
                                  @Field("berat") String berat,
                                  @Field("tahun_produksi") String tahun_produksi,
                                  @Field("harga_beli") String harga_beli,
                                  @Field("harga_jual") String harga_jual,
                                    @Field("status") String status);

    @FormUrlEncoded
    @POST("delete_logam.php")
    Call<ResponseModel> deleteLogam(@Field("id_logam") String id_logam);

    @GET("tampil_all_logam.php")
    Call<ResponseModel> getAllLogam();

    @GET("tampil_news.php")
    Call<ResponseModel> getAllNews();

    @FormUrlEncoded
    @POST("search_member.php")
    Call<ResponseModel> getSearchMember(@Field("search") String search);

    @FormUrlEncoded
    @POST("ubah_password.php")
    Call<ResponseModel> updatePassword(@Field("id_user") String id_user,
                                       @Field("password") String password,
                                       @Field("password_baru") String password_baru,
                                       @Field("ulangi_password_baru") String ulangi_password_baru);

    @FormUrlEncoded
    @POST("ubah_profile.php")
    Call<ResponseModel> updateProfile(@Field("id_user") String id_user,
                                       @Field("nama") String nama,
                                       @Field("alamat") String alamat,
                                       @Field("email") String email,
                                       @Field("telpon") String telpon,
                                       @Field("perusahaan") String perusahaan,
                                       @Field("alamat_perusahaan") String alamat_perusahaan,
                                       @Field("emas") String emas,
                                       @Field("perak") String perak,
                                       @Field("foto") String foto);

    @FormUrlEncoded
    @POST("ubah_profile_test.php")
    Call<ResponseModel> updateProfileTest(@Field("id_user") String id_user,
                                      @Field("nama") String nama,
                                      @Field("alamat") String alamat,
                                      @Field("email") String email,
                                      @Field("telpon") String telpon,
                                      @Field("perusahaan") String perusahaan,
                                      @Field("alamat_perusahaan") String alamat_perusahaan,
                                      @Field("emas") String emas,
                                      @Field("perak") String perak);


}
