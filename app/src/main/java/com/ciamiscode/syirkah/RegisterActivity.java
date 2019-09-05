package com.ciamiscode.syirkah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ciamiscode.syirkah.api.ApiEndPoint;
import com.ciamiscode.syirkah.api.ApiService;
import com.ciamiscode.syirkah.model.ResponseModel;
import com.ciamiscode.syirkah.utils.ImageUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ImageView img_user;
    TextView tv_login;
    static int REQUESTCODE = 1;
    static int PReqCode = 1;
    Uri pickedImgUri;

    private ProgressDialog progress;

    EditText edtNama,edtAlamat,edtEmail,edtTelpon,edtPerusahaan,edtAlamatPerusahaan,edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tv_login = findViewById(R.id.tv_login);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        img_user = findViewById(R.id.img_user);
        img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 22){

                    checkAndRequestForPermission();

                }else{
                    openGallery();
                }
            }
        });


        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pickedImgUri != null) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), pickedImgUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String encoded = ImageUtils.bitmapToBase64String(bitmap, 100);
                    register(encoded);

                }else{
                    Toast.makeText(RegisterActivity.this, "Foto profile tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null){

            //user has successfully picked an image
            pickedImgUri = data.getData();
            img_user.setImageURI(pickedImgUri);
        }
    }

    private void openGallery(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i,REQUESTCODE);
    }

    private void checkAndRequestForPermission() {

        if (ContextCompat.checkSelfPermission(RegisterActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE )
                != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(RegisterActivity.this, "Please accept for required permission",Toast.LENGTH_SHORT).show();
            }else {
                ActivityCompat.requestPermissions(RegisterActivity.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        }else{
            openGallery();
        }
    }

    private void register(String foto){

        edtNama = findViewById(R.id.tv_register_nama);
        edtEmail = findViewById(R.id.tv_register_email);
        edtAlamat = findViewById(R.id.tv_register_alamat);
        edtPerusahaan = findViewById(R.id.tv_register_perusahaan);
        edtAlamatPerusahaan = findViewById(R.id.tv_register_alamat_perusahaan);
        edtTelpon = findViewById(R.id.tv_register_telpon);
        edtPassword = findViewById(R.id.tv_regsiter_password);

        String nama = edtNama.getText().toString();
        String alamat = edtAlamat.getText().toString();
        String email = edtEmail.getText().toString();
        String telpon = edtTelpon.getText().toString();
        String perusahaan = edtPerusahaan.getText().toString();
        String alamat_perusahaan = edtAlamatPerusahaan.getText().toString();
        String password = edtPassword.getText().toString();

        boolean isEmptyFields = false;

        if (TextUtils.isEmpty(nama)){
            isEmptyFields = true;
            edtNama.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(alamat)){
            isEmptyFields = true;
            edtAlamat.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(email)){
            isEmptyFields = true;
            edtEmail.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(telpon)){
            isEmptyFields = true;
            edtTelpon.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(perusahaan)){
            isEmptyFields = true;
            edtPerusahaan.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(alamat_perusahaan)){
            isEmptyFields = true;
            edtAlamatPerusahaan.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(password)){
            isEmptyFields = true;
            edtPassword.setError("Field ini tidak boleh kosong");
        }

        if (!isEmptyFields){
            progress = new ProgressDialog(RegisterActivity.this);
            progress.setCancelable(false);
            progress.setMessage("Menyimpan ...");
            progress.show();

            ApiService api = ApiEndPoint.getClient().create(ApiService.class);
            Call<ResponseModel> call = api.register(nama,alamat,email,telpon,perusahaan,alamat_perusahaan,password,foto);
            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    String statusCode = response.body().getStatusCode();
                    String message = response.body().getMessage();

                    progress.dismiss();

                    if (statusCode.equals("200")) {
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        finish();
                    } else if (statusCode.equals("202")) {
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else if (statusCode.equals("500")) {
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(RegisterActivity.this, "Oops, Tidak Ada Koneksi Internet!! ", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
