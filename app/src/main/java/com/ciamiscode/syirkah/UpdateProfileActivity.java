package com.ciamiscode.syirkah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ciamiscode.syirkah.api.ApiEndPoint;
import com.ciamiscode.syirkah.api.ApiService;
import com.ciamiscode.syirkah.model.ResponseModel;
import com.ciamiscode.syirkah.utils.ImageUtils;
import com.ciamiscode.syirkah.utils.SharedPrefManager;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {

    ImageView imgProfile;
    EditText edtNama, edtAlamat, edtEmail, edtTelpon, edtPerusahaan, edtAlamatPerusahaan, edtEmas, edtPerak;
    SharedPrefManager sharedPrefManager;
    Button btnUbah;

    static int REQUESTCODE = 1;
    static int PReqCode = 1;
    Uri pickedImgUri;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        sharedPrefManager = new SharedPrefManager(this);

        edtNama = findViewById(R.id.edt_ubah_nama);
        edtAlamat = findViewById(R.id.edt_ubah_alamat);
        edtEmail = findViewById(R.id.edt_ubah_email);
        edtTelpon = findViewById(R.id.edt_ubah_telpon);
        edtPerusahaan = findViewById(R.id.edt_ubah_perusahaan);
        edtAlamatPerusahaan = findViewById(R.id.edt_ubah_alamat_perusahaan);
        edtEmas = findViewById(R.id.edt_ubah_emas);
        edtPerak = findViewById(R.id.edt_ubah_perak);
        imgProfile = findViewById(R.id.img_ubah_profile);

        edtNama.setText(sharedPrefManager.getSPNama());
        edtAlamat.setText(sharedPrefManager.getSPAlamat());
        edtEmail.setText(sharedPrefManager.getSPEmail());
        edtTelpon.setText(sharedPrefManager.getSPTelpon());
        edtPerusahaan.setText(sharedPrefManager.getSpPerusahaan());
        edtAlamatPerusahaan.setText(sharedPrefManager.getSpAlamatPerusahaan());
        edtEmas.setText(sharedPrefManager.getSpEmas());
        edtPerak.setText(sharedPrefManager.getSpPerak());

        String img_url = "http://syirkah.solution.dipointer.com/img/"+sharedPrefManager.getSpFoto();

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_default_profile)
                .error(R.drawable.ic_default_profile)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(getApplicationContext()).load(img_url)
                .apply(options)
                .into(imgProfile);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 22){

                    checkAndRequestForPermission();

                }else{
                    openGallery();
                }
            }
        });

        btnUbah = findViewById(R.id.btn_ubah_profile);
        btnUbah.setOnClickListener(new View.OnClickListener() {
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
                    updateProfile(encoded);

                }else{
                    updateProfileTest();
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
            imgProfile.setImageURI(pickedImgUri);
        }
    }

    private void openGallery(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i,REQUESTCODE);
    }

    private void checkAndRequestForPermission() {

        if (ContextCompat.checkSelfPermission(UpdateProfileActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE )
                != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(UpdateProfileActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(UpdateProfileActivity.this, "Please accept for required permission",Toast.LENGTH_SHORT).show();
            }else {
                ActivityCompat.requestPermissions(UpdateProfileActivity.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        }else{
            openGallery();
        }
    }

    private void updateProfile(String foto){

        String id_user = sharedPrefManager.getIdUser();
        String nama = edtNama.getText().toString();
        String alamat = edtAlamat.getText().toString();
        String email = edtEmail.getText().toString();
        String telpon = edtTelpon.getText().toString();
        String perusahaan = edtPerusahaan.getText().toString();
        String alamat_perusahaan = edtAlamatPerusahaan.getText().toString();
        String emas = edtEmas.getText().toString();
        String perak = edtPerak.getText().toString();

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

        if (TextUtils.isEmpty(emas)){
            isEmptyFields = true;
            edtEmas.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(perak)){
            isEmptyFields = true;
            edtPerak.setError("Field ini tidak boleh kosong");
        }

        if (!isEmptyFields){
            progress = new ProgressDialog(UpdateProfileActivity.this);
            progress.setCancelable(false);
            progress.setMessage("Menyimpan ...");
            progress.show();

            ApiService api = ApiEndPoint.getClient().create(ApiService.class);
            Call<ResponseModel> call = api.updateProfile(id_user,nama,alamat,email,telpon,perusahaan,alamat_perusahaan,emas,perak,foto);
            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    String statusCode = response.body().getStatusCode();
                    String message = response.body().getMessage();

                    progress.dismiss();

                    if (statusCode.equals("300")) {
                        Toast.makeText(UpdateProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                        startActivity(new Intent(UpdateProfileActivity.this, LoginActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finishAffinity();
                    } else if (statusCode.equals("303")) {
                        Toast.makeText(UpdateProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else if (statusCode.equals("500")) {
                        Toast.makeText(UpdateProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(UpdateProfileActivity.this, "Oops, Tidak Ada Koneksi Internet!! ", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void updateProfileTest(){

        String id_user = sharedPrefManager.getIdUser();
        String nama = edtNama.getText().toString();
        String alamat = edtAlamat.getText().toString();
        String email = edtEmail.getText().toString();
        String telpon = edtTelpon.getText().toString();
        String perusahaan = edtPerusahaan.getText().toString();
        String alamat_perusahaan = edtAlamatPerusahaan.getText().toString();
        String emas = edtEmas.getText().toString();
        String perak = edtPerak.getText().toString();

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

        if (TextUtils.isEmpty(emas)){
            isEmptyFields = true;
            edtEmas.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(perak)){
            isEmptyFields = true;
            edtPerak.setError("Field ini tidak boleh kosong");
        }

        if (!isEmptyFields){
            progress = new ProgressDialog(UpdateProfileActivity.this);
            progress.setCancelable(false);
            progress.setMessage("Menyimpan ...");
            progress.show();

            ApiService api = ApiEndPoint.getClient().create(ApiService.class);
            Call<ResponseModel> call = api.updateProfileTest(id_user,nama,alamat,email,telpon,perusahaan,alamat_perusahaan,emas,perak);
            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    String statusCode = response.body().getStatusCode();
                    String message = response.body().getMessage();

                    progress.dismiss();

                    if (statusCode.equals("200")) {
                        Toast.makeText(UpdateProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                        startActivity(new Intent(UpdateProfileActivity.this, LoginActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finishAffinity();
                    } else if (statusCode.equals("202")) {
                        Toast.makeText(UpdateProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else if (statusCode.equals("500")) {
                        Toast.makeText(UpdateProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(UpdateProfileActivity.this, "Oops, Tidak Ada Koneksi Internet!! ", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
