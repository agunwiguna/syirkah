package com.ciamiscode.syirkah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ciamiscode.syirkah.api.BaseApiService;
import com.ciamiscode.syirkah.api.UtilsApi;
import com.ciamiscode.syirkah.utils.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;

    Button btn_login;
    TextView tv_register;
    EditText ed_email, ed_password;
    ProgressDialog loading;

    BaseApiService mApiService;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        sharedPrefManager = new SharedPrefManager(this);

        mApiService = UtilsApi.getAPIService();

        ed_email = findViewById(R.id.editTextEmail);
        ed_password = findViewById(R.id.editTextPassword);

        btn_login = findViewById(R.id.btnLogin);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = ed_email.getText().toString();
                String password = ed_password.getText().toString();

                boolean isEmptyFields = false;

                if(TextUtils.isEmpty(email)){
                    isEmptyFields = true;
                    ed_email.setError("Field ini tidak boleh kosong");
                }

                if(TextUtils.isEmpty(password)){
                    isEmptyFields = true;
                    ed_password.setError("Field ini tidak boleh kosong");
                }

                if(!isEmptyFields){

                    loading = ProgressDialog.show(LoginActivity.this,null,"Harap Tunggu..",true,false);

                    mApiService.loginRequest(ed_email.getText().toString(),ed_password.getText().toString())
                            .enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()){
                                        loading.dismiss();
                                        try {

                                            JSONObject jsonRESULT = new JSONObject(response.body().string());

                                            String message = jsonRESULT.getString("message");
                                            String status_code = jsonRESULT.getString("status_code");

                                            if (status_code.equals("200")){

                                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

                                                //get data user login
                                                String id_user = jsonRESULT.getJSONObject("user").getString("id_user");
                                                sharedPrefManager.saveSPString(SharedPrefManager.SP_ID_USER, id_user);

                                                String nama = jsonRESULT.getJSONObject("user").getString("nama");
                                                sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, nama);

                                                String perusahaan = jsonRESULT.getJSONObject("user").getString("perusahaan");
                                                sharedPrefManager.saveSPString(SharedPrefManager.SP_PERUSAHAAN, perusahaan);

                                                String foto = jsonRESULT.getJSONObject("user").getString("foto");
                                                sharedPrefManager.saveSPString(SharedPrefManager.SP_FOTO, foto);

                                                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                                                startActivity(new Intent(LoginActivity.this, MainActivity.class)
                                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                                finish();

                                            }else if (status_code.equals("400")){
                                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                            }else if (status_code.equals("404")){
                                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                            }else if (status_code.equals("500")){
                                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                            }

                                        }catch (JSONException e){
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(LoginActivity.this, "Oops.. Tidak ada koneksi Internet", Toast.LENGTH_SHORT).show();
                                }
                            });

                }

            }
        });

        tv_register = findViewById(R.id.tv_register);
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        if (sharedPrefManager.getSPSudahLogin()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan lagi untuk keluar..", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
