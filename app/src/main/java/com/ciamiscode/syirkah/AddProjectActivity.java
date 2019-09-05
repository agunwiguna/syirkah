package com.ciamiscode.syirkah;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.ciamiscode.syirkah.adapter.InvestasiAdapter;
import com.ciamiscode.syirkah.api.ApiEndPoint;
import com.ciamiscode.syirkah.api.ApiService;
import com.ciamiscode.syirkah.model.InvestasiModel;
import com.ciamiscode.syirkah.model.ResponseModel;
import com.ciamiscode.syirkah.utils.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProjectActivity extends AppCompatActivity {

    EditText edtNamaProyek;
    EditText edtDeskripsi;
    EditText edtTglMulai;
    EditText edtTglSelesai;
    EditText edtKebutuhanBiaya;
    EditText edtTotalBiaya;

    Button btnSave;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date,dates;

    private ProgressDialog progress;
    private InvestasiAdapter viewAdapter;
    private List<InvestasiModel> mItems = new ArrayList<>();

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        sharedPrefManager = new SharedPrefManager(this);
        viewAdapter = new InvestasiAdapter(this,mItems);

        edtNamaProyek = findViewById(R.id.et_nama_proyek);
        edtDeskripsi = findViewById(R.id.et_deskripsi_proyek);
        edtTglMulai = findViewById(R.id.et_tgl_mulai);
        edtTglSelesai = findViewById(R.id.et_tgl_selesai);
        edtKebutuhanBiaya = findViewById(R.id.et_kebutuhan_biaya_proyek);
        edtTotalBiaya = findViewById(R.id.et_total_biaya_proyek);

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR,year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }
        };

        edtTglMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddProjectActivity.this,date,myCalendar.get(Calendar.YEAR)
                ,myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dates = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR,year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabels();
            }
        };

        edtTglSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddProjectActivity.this,dates,myCalendar.get(Calendar.YEAR)
                        ,myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSave = findViewById(R.id.btn_simpan_investasi);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String id_user = sharedPrefManager.getIdUser();
                    String nama_proyek = edtNamaProyek.getText().toString();
                    String deskripsi = edtDeskripsi.getText().toString();
                    String tgl_mulai = edtTglMulai.getText().toString();
                    String tgl_selesai = edtTglSelesai.getText().toString();
                    String kebutuhan_biaya = edtKebutuhanBiaya.getText().toString();
                    String total_biaya = edtTotalBiaya.getText().toString();

                    boolean isEmptyFields = false;

                    if (TextUtils.isEmpty(nama_proyek)){
                        isEmptyFields = true;
                        edtNamaProyek.setError("Field ini tidak boleh kosong");
                    }

                    if (TextUtils.isEmpty(deskripsi)){
                        isEmptyFields = true;
                        edtDeskripsi.setError("Field ini tidak boleh kosong");
                    }

                    if (TextUtils.isEmpty(tgl_mulai)){
                        isEmptyFields = true;
                        edtTglMulai.setError("Field ini tidak boleh kosong");
                    }

                    if (TextUtils.isEmpty(tgl_selesai)){
                        isEmptyFields = true;
                        edtTglSelesai.setError("Field ini tidak boleh kosong");
                    }

                    if (TextUtils.isEmpty(kebutuhan_biaya)){
                        isEmptyFields = true;
                        edtKebutuhanBiaya.setError("Field ini tidak boleh kosong");
                    }

                    if (TextUtils.isEmpty(total_biaya)){
                        isEmptyFields = true;
                        edtTotalBiaya.setError("Field ini tidak boleh kosong");
                    }

                    if (!isEmptyFields){

                        progress = new ProgressDialog(AddProjectActivity.this);
                        progress.setCancelable(false);
                        progress.setMessage("Harap Tunggu ...");
                        progress.show();

                        ApiService api = ApiEndPoint.getClient().create(ApiService.class);
                        Call<ResponseModel> addInvestasiCall = api.postInvestasi(id_user,nama_proyek,deskripsi,tgl_mulai,tgl_selesai,kebutuhan_biaya,total_biaya);
                        addInvestasiCall.enqueue(new Callback<ResponseModel>() {
                            @Override
                            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                                String statusCode = response.body().getStatusCode();
                                String message = response.body().getMessage();

                                progress.dismiss();

                                edtNamaProyek.setText("");
                                edtDeskripsi.setText("");
                                edtTglMulai.setText("");
                                edtTglSelesai.setText("");
                                edtKebutuhanBiaya.setText("");
                                edtTotalBiaya.setText("");

                                if (statusCode.equals("200")) {
                                    Toast.makeText(AddProjectActivity.this, message, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(AddProjectActivity.this,InvestasiActivity.class));
                                    viewAdapter.notifyDataSetChanged();
                                    finish();
                                } else if (statusCode.equals("202")) {
                                    Toast.makeText(AddProjectActivity.this, message, Toast.LENGTH_SHORT).show();
                                } else if (statusCode.equals("404")) {
                                    Toast.makeText(AddProjectActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseModel> call, Throwable t) {
                                progress.dismiss();
                                Toast.makeText(AddProjectActivity.this, "Oops, Tidak Ada Koneksi Internet!! ", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edtTglMulai.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabels() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edtTglSelesai.setText(sdf.format(myCalendar.getTime()));
    }
}
