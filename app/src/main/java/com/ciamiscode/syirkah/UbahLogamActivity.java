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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahLogamActivity extends AppCompatActivity {

    public static final String EXTRA_ID_LOGAM   = "extra_id_logam";
    public static final String EXTRA_JENIS   = "extra_jenis";
    public static final String EXTRA_BERAT   = "extra_berat";
    public static final String EXTRA_TAHUN_PRODUKSI   = "extra_tahun_produksi";
    public static final String EXTRA_HARGA_BELI   = "extra_harga_beli";
    public static final String EXTRA_HARGA_JUAL   = "extra_harga_jual";

    RadioGroup radioGroup,radioStatus;
    RadioButton radioButton, radioStatus_pil;
    EditText edtBerat,edtTahun,edtHargaBeli,edtHargaJual;
    private ProgressDialog progress;
    Button btnUbah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_logam);

        radioGroup = findViewById(R.id.radioGroupJenis);
        radioStatus = findViewById(R.id.radioGroupStatus);
        edtBerat = findViewById(R.id.edt_ubah_berat);
        edtTahun = findViewById(R.id.edt_ubah_tahun_produksi);
        edtHargaBeli = findViewById(R.id.edt_ubah_harga_beli);
        edtHargaJual = findViewById(R.id.edt_ubah_harga_jual);

        final String id_logam = getIntent().getStringExtra(EXTRA_ID_LOGAM);
        String berat_user = getIntent().getStringExtra(EXTRA_BERAT);
        String tahun_produksi_user = getIntent().getStringExtra(EXTRA_TAHUN_PRODUKSI);
        String harga_beli_user = getIntent().getStringExtra(EXTRA_HARGA_BELI);
        String harga_jual_user = getIntent().getStringExtra(EXTRA_HARGA_JUAL);

        edtBerat.setText(berat_user);
        edtTahun.setText(tahun_produksi_user);
        edtHargaBeli.setText(harga_beli_user);
        edtHargaJual.setText(harga_jual_user);

        btnUbah = findViewById(R.id.btn_ubah_logam);
        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);

                int status_user = radioStatus.getCheckedRadioButtonId();
                radioStatus_pil = findViewById(status_user);

                String jenis = String.valueOf(radioButton.getText());
                String status = String.valueOf(radioStatus_pil.getText());
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
                    progress = new ProgressDialog(UbahLogamActivity.this);
                    progress.setCancelable(false);
                    progress.setMessage("Mengubah ...");
                    progress.show();

                    ApiService api = ApiEndPoint.getClient().create(ApiService.class);
                    Call<ResponseModel> call = api.updateLogam(id_logam,jenis,berat,tahun_produksi,harga_beli,harga_jual,status);
                    call.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            String statusCode = response.body().getStatusCode();
                            String message = response.body().getMessage();

                            progress.dismiss();

                            if (statusCode.equals("200")) {
                                Toast.makeText(UbahLogamActivity.this, message, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UbahLogamActivity.this,SellActivity.class));
                                finish();
                            } else if (statusCode.equals("202")) {
                                Toast.makeText(UbahLogamActivity.this, message, Toast.LENGTH_SHORT).show();
                            } else if (statusCode.equals("404")) {
                                Toast.makeText(UbahLogamActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            progress.dismiss();
                            Toast.makeText(UbahLogamActivity.this, "Oops, Tidak Ada Koneksi Internet!! ", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
}
