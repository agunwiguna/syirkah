package com.ciamiscode.syirkah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ciamiscode.syirkah.api.ApiEndPoint;
import com.ciamiscode.syirkah.api.ApiService;
import com.ciamiscode.syirkah.model.ResponseModel;
import com.ciamiscode.syirkah.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLogamActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText edtBerat,edtTahun,edtHargaBeli,edtHargaJual;
    SharedPrefManager sharedPrefManager;
    private ProgressDialog progress;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_logam);

        sharedPrefManager = new SharedPrefManager(this);

        btnSave = findViewById(R.id.btn_simpan_logam);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertLogam();
            }
        });
    }

    public void insertLogam(){

        radioGroup = findViewById(R.id.radioGroupJenis);
        edtBerat = findViewById(R.id.edt_berat);
        edtTahun = findViewById(R.id.edt_tahun_produksi);
        edtHargaBeli = findViewById(R.id.edt_harga_beli);
        edtHargaJual = findViewById(R.id.edt_harga_jual);

        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);

        String jenis = String.valueOf(radioButton.getText());
        String id_user = sharedPrefManager.getIdUser();
        String berat = edtBerat.getText().toString();
        String tahun_produksi = edtTahun.getText().toString();
        String harga_beli = edtHargaBeli.getText().toString();
        String harga_jual = edtHargaJual.getText().toString();

        boolean isEmptyField = false;

        if (TextUtils.isEmpty(berat)){
            isEmptyField = true;
            edtBerat.setError("Field tidak boleh kosong");
        }

        if (TextUtils.isEmpty(tahun_produksi)){
            isEmptyField = true;
            edtTahun.setError("Field tidak boleh kosong");
        }

        if (TextUtils.isEmpty(harga_beli)){
            isEmptyField = true;
            edtHargaBeli.setError("Field tidak boleh kosong");
        }

        if (TextUtils.isEmpty(harga_jual)){
            isEmptyField = true;
            edtHargaJual.setError("Field tidak boleh kosong");
        }

        if(!isEmptyField){
            progress = new ProgressDialog(AddLogamActivity.this);
            progress.setCancelable(false);
            progress.setMessage("Menyimpan ...");
            progress.show();

            ApiService api = ApiEndPoint.getClient().create(ApiService.class);
            Call<ResponseModel> call = api.postLogam(id_user,jenis,berat,tahun_produksi,harga_beli,harga_jual);
            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    String statusCode = response.body().getStatusCode();
                    String message = response.body().getMessage();

                    progress.dismiss();

                    if (statusCode.equals("200")) {
                        Toast.makeText(AddLogamActivity.this, message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddLogamActivity.this,SellActivity.class));
                        finish();
                    } else if (statusCode.equals("202")) {
                        Toast.makeText(AddLogamActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else if (statusCode.equals("404")) {
                        Toast.makeText(AddLogamActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(AddLogamActivity.this, "Oops, Tidak Ada Koneksi Internet!! ", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
