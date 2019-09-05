package com.ciamiscode.syirkah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ciamiscode.syirkah.api.ApiEndPoint;
import com.ciamiscode.syirkah.api.ApiService;
import com.ciamiscode.syirkah.model.ResponseModel;
import com.ciamiscode.syirkah.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahPasswordActivity extends AppCompatActivity {

    EditText edtPassword,edtPasswordBaru,edtUlangiPassword;
    Button btnUpdate;
    SharedPrefManager sharedPrefManager;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);

        sharedPrefManager = new SharedPrefManager(this);

        edtPassword = findViewById(R.id.password_now);
        edtPasswordBaru = findViewById(R.id.password_new);
        edtUlangiPassword = findViewById(R.id.password_try);
        btnUpdate = findViewById(R.id.btn_ubah_password);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_user = sharedPrefManager.getIdUser();
                String password = edtPassword.getText().toString();
                String password_baru = edtPasswordBaru.getText().toString();
                String ulangi_password_baru = edtUlangiPassword.getText().toString();

                boolean isEmptyFields = false;

                if (TextUtils.isEmpty(password)){
                    isEmptyFields = true;
                    edtPassword.setError("Field harus di isi");
                }

                if (TextUtils.isEmpty(password_baru)){
                    isEmptyFields = true;
                    edtPasswordBaru.setError("Field harus di isi");
                }

                if (TextUtils.isEmpty(ulangi_password_baru)){
                    isEmptyFields = true;
                    edtUlangiPassword.setError("Field harus di isi");
                }

                if (!isEmptyFields){

                    progress = new ProgressDialog(UbahPasswordActivity.this);
                    progress.setCancelable(false);
                    progress.setMessage("Harap Tunggu ...");
                    progress.show();

                    ApiService api = ApiEndPoint.getClient().create(ApiService.class);
                    Call<ResponseModel> call = api.updatePassword(id_user,password,password_baru,ulangi_password_baru);
                    call.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            String statusCode = response.body().getStatusCode();
                            String message = response.body().getMessage();

                            progress.dismiss();

                            if (statusCode.equals("200")) {
                                Toast.makeText(UbahPasswordActivity.this, message, Toast.LENGTH_SHORT).show();

                            } else if (statusCode.equals("202")) {
                                Toast.makeText(UbahPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                                startActivity(new Intent(UbahPasswordActivity.this, LoginActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                finishAffinity();
                            } else if (statusCode.equals("202")) {
                                Toast.makeText(UbahPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                            }else if (statusCode.equals("250")) {
                                Toast.makeText(UbahPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                            }else if (statusCode.equals("300")) {
                                Toast.makeText(UbahPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                            }else if (statusCode.equals("404")) {
                                Toast.makeText(UbahPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            progress.dismiss();
                            Toast.makeText(UbahPasswordActivity.this, "Oops, Tidak Ada Koneksi Internet!! ", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });

    }
}
