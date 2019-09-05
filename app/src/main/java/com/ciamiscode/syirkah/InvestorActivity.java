package com.ciamiscode.syirkah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ciamiscode.syirkah.api.ApiEndPoint;
import com.ciamiscode.syirkah.api.ApiService;
import com.ciamiscode.syirkah.fragment.ProjectFragment;
import com.ciamiscode.syirkah.model.ResponseModel;
import com.ciamiscode.syirkah.utils.SharedPrefManager;

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvestorActivity extends AppCompatActivity {

    public static final String EXTRA_ID_INVESTASI   = "extra_id_investasi";
    public static final String EXTRA_KEBUTUHAN_BIAYA_INVESTOR = "extra_kebutuhan_biaya";
    public static final String EXTRA_SISA = "extra_sisa";

    TextView kebutuhanBiaya,tvDana,tvSisa;
    EditText edtDana;

    Button btnSave;

    private ProgressDialog progress;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor);

        sharedPrefManager = new SharedPrefManager(this);

        kebutuhanBiaya = findViewById(R.id.kebutuhan_dana_investasi);

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        final String sisa_biaya = getIntent().getStringExtra(EXTRA_SISA);

        kebutuhanBiaya.setText(formatRupiah.format((double)Integer.valueOf(sisa_biaya)));

        edtDana = findViewById(R.id.et_dana_investor);

        btnSave = findViewById(R.id.btn_simpan_investor);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id_investasi = getIntent().getStringExtra(EXTRA_ID_INVESTASI);
                String id_user_investor = sharedPrefManager.getIdUser();
                String inputDana = edtDana.getText().toString().trim();

                boolean isEmptyFields = false;

                if(TextUtils.isEmpty(inputDana)){
                    isEmptyFields = true;
                    edtDana.setError("Field tidak boleh kosong");
                }

                int dana = Integer.parseInt(inputDana);
                int sisa = Integer.parseInt(sisa_biaya);

                int sisa_dana = sisa-dana;

                if(!isEmptyFields){

                    if (dana>sisa){
                        Toast.makeText(InvestorActivity.this,"Dana Melebihi Kebutuhan Biaya",Toast.LENGTH_SHORT).show();
                    }else{

                        progress = new ProgressDialog(InvestorActivity.this);
                        progress.setCancelable(false);
                        progress.setMessage("Menyimpan ...");
                        progress.show();

                        ApiService api = ApiEndPoint.getClient().create(ApiService.class);
                        Call<ResponseModel> addInvestor = api.postInvestor(id_user_investor,id_investasi,dana,sisa_dana);
                        addInvestor.enqueue(new Callback<ResponseModel>() {
                            @Override
                            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                String statusCode = response.body().getStatusCode();
                                String message = response.body().getMessage();

                                progress.dismiss();

                                edtDana.setText("");

                                if (statusCode.equals("200")) {
                                    Toast.makeText(InvestorActivity.this, message, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(InvestorActivity.this,MainActivity.class));
                                    finish();
                                } else if (statusCode.equals("202")) {
                                    Toast.makeText(InvestorActivity.this, message, Toast.LENGTH_SHORT).show();
                                } else if (statusCode.equals("404")) {
                                    Toast.makeText(InvestorActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseModel> call, Throwable t) {
                                progress.dismiss();
                                Toast.makeText(InvestorActivity.this, "Oops, Tidak Ada Koneksi Internet!! ", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }

            }
        });

    }

}
