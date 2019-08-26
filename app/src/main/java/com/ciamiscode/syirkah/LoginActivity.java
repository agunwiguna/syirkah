package com.ciamiscode.syirkah;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ciamiscode.syirkah.api.ApiEndPoint;
import com.ciamiscode.syirkah.api.ApiService;
import com.ciamiscode.syirkah.fragment.HomeFragment;
import com.ciamiscode.syirkah.model.ResponseModel;
import com.ciamiscode.syirkah.model.UserModel;
import com.ciamiscode.syirkah.utils.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    TextView tv_register;
    EditText ed_email, ed_password;
    ProgressBar progressBar;
    ProgressDialog progress;

    Context mContext;
    ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        progressBar = findViewById(R.id.progress_bar);

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

                    ApiService api = ApiEndPoint.getClient().create(ApiService.class);
                    Call<ResponseModel> call = api.loginRequest(email,password);
                    call.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                            if(response.isSuccessful()){

                                String statusCode = response.body().getStatusCode();
                                String message = response.body().getMessage();

                                if (statusCode.equals("200")) {
                                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                    //i.putExtra(HomeFragment.EXTRA_NAMA,"Agun Wiguna");
                                    startActivity(i);
                                }else if (statusCode.equals("404")) {
                                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                } else if (statusCode.equals("400")) {
                                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                }else if (statusCode.equals("500")) {
                                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Oops, Tidak Ada Koneksi Internet!! ", Toast.LENGTH_SHORT).show();
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
    }
}
