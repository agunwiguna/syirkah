package com.ciamiscode.syirkah;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ciamiscode.syirkah.api.ApiEndPoint;
import com.ciamiscode.syirkah.api.ApiService;
import com.ciamiscode.syirkah.model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahInvestasiActivity extends AppCompatActivity {

    public static final String EXTRA_ID_INVESTASI   = "extra_id_investasi";
    public static final String EXTRA_NAMA_INVESTASI   = "extra_nama_investasi";
    public static final String EXTRA_DESKRIPSI = "extra_deskripsi";
    public static final String EXTRA_TGL_MULAI = "extra_tgl_mulai";
    public static final String EXTRA_TGL_SELESAI = "extra_tgl_selesai";
    public static final String EXTRA_KEBUTUHAN_BIAYA = "extra_kebutuhan_biaya";
    public static final String EXTRA_TOTAL_BIAYA = "extra_total_biaya";

    EditText namaInvestasi;
    EditText descInvestasi;
    EditText tglMulai;
    EditText tglSelesai;
    EditText kebutuhanBiaya;
    EditText totalBiaya;

    Button btnUpdate;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_investasi);

        namaInvestasi = findViewById(R.id.et_ubah_nama_proyek);
        descInvestasi = findViewById(R.id.et_ubah_deskripsi_proyek);
        tglMulai = findViewById(R.id.et_ubah_tgl_mulai);
        tglSelesai = findViewById(R.id.et_ubah_tgl_selesai);
        kebutuhanBiaya = findViewById(R.id.et_ubah_kebutuhan_biaya_proyek);
        totalBiaya = findViewById(R.id.et_ubah_total_biaya_proyek);

        final String id_investasi_data = getIntent().getStringExtra(EXTRA_ID_INVESTASI);
        String nama_investasi = getIntent().getStringExtra(EXTRA_NAMA_INVESTASI);
        String deskripsi_investasi = getIntent().getStringExtra(EXTRA_DESKRIPSI);
        String tgl_mulai_investasi = getIntent().getStringExtra(EXTRA_TGL_MULAI);
        String tgl_selesai_investasi = getIntent().getStringExtra(EXTRA_TGL_SELESAI);
        String kebutuhan_baiaya_investasi = getIntent().getStringExtra(EXTRA_KEBUTUHAN_BIAYA);
        String total_biaya_investasi = getIntent().getStringExtra(EXTRA_TOTAL_BIAYA);

        namaInvestasi.setText(nama_investasi);
        descInvestasi.setText(deskripsi_investasi);
        tglMulai.setText(tgl_mulai_investasi);
        tglSelesai.setText(tgl_selesai_investasi);
        kebutuhanBiaya.setText(kebutuhan_baiaya_investasi);
        totalBiaya.setText(total_biaya_investasi);

        btnUpdate = findViewById(R.id.btn_ubah_investasi);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id_investasi = id_investasi_data;
                String nama_proyek = namaInvestasi.getText().toString();
                String deskripsi = descInvestasi.getText().toString();
                String tgl_mulai = tglMulai.getText().toString();
                String tgl_selesai = tglSelesai.getText().toString();
                String kebutuhan_biaya = kebutuhanBiaya.getText().toString();
                String total_biaya = totalBiaya.getText().toString();

                progress = new ProgressDialog(UbahInvestasiActivity.this);
                progress.setCancelable(false);
                progress.setMessage("Harap Tunggu ...");
                progress.show();

                ApiService api = ApiEndPoint.getClient().create(ApiService.class);
                Call<ResponseModel> call = api.updateInvestasi(id_investasi,nama_proyek,deskripsi,tgl_mulai,tgl_selesai,kebutuhan_biaya,total_biaya);
                call.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        String statusCode = response.body().getStatusCode();
                        String message = response.body().getMessage();

                        progress.dismiss();

                        Log.d("Retro", "Response");

                        if (statusCode.equals("200")) {
                            Toast.makeText(UbahInvestasiActivity.this, message, Toast.LENGTH_SHORT).show();
                        } else if (statusCode.equals("202")) {
                            Toast.makeText(UbahInvestasiActivity.this, message, Toast.LENGTH_SHORT).show();
                        } else if (statusCode.equals("404")) {
                            Toast.makeText(UbahInvestasiActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Log.d("Retro", "OnFailure");
                        progress.dismiss();
                        Toast.makeText(UbahInvestasiActivity.this, "Oops, Tidak Ada Koneksi Internet!! ", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
