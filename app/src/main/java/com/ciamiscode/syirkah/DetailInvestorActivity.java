package com.ciamiscode.syirkah;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ciamiscode.syirkah.adapter.InvestorAdapter;
import com.ciamiscode.syirkah.api.ApiEndPoint;
import com.ciamiscode.syirkah.api.ApiService;
import com.ciamiscode.syirkah.model.InvestorModel;
import com.ciamiscode.syirkah.model.ResponseModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailInvestorActivity extends AppCompatActivity {

    public static final String EXTRA_ID_INVESTASI   = "extra_id_investasi";
    public static final String EXTRA_NAMA_INVESTASI   = "extra_nama_investasi";
    public static final String EXTRA_DESKRIPSI = "extra_deskripsi";
    public static final String EXTRA_TGL_MULAI = "extra_tgl_mulai";
    public static final String EXTRA_TGL_SELESAI = "extra_tgl_selesai";
    public static final String EXTRA_KEBUTUHAN_BIAYA = "extra_kebutuhan_biaya";
    public static final String EXTRA_TOTAL_BIAYA = "extra_total_biaya";
    public static final String EXTRA_NAMA = "extra_nama";
    public static final String EXTRA_FOTO = "extra_foto";
    public static final String EXTRA_PERUSAHAAN = "extra_perusahaan";
    public static final String EXTRA_SISA = "extra_sisa";

    TextView namaInvestasi;
    TextView descInvestasi;
    TextView tglMulai;
    TextView tglSelesai;
    TextView kebutuhanBiaya;
    TextView totalBiaya;
    TextView namaPemilik;
    TextView perusahaanInvestasi;
    TextView sisaBiaya;

    ImageView imgProfile;

    private InvestorAdapter viewAdapter;
    private List<InvestorModel> mItems = new ArrayList<>();
    RecyclerView recyclerView;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_investor);

        recyclerView = findViewById(R.id.recyclerInvestor);
        progress = findViewById(R.id.progress_bar);

        namaInvestasi = findViewById(R.id.tv_detail_investor_nama_investasi);
        descInvestasi = findViewById(R.id.tv_detail_investor_deskripsi_investasi);
        tglMulai = findViewById(R.id.tv_detail_investor_tgl_mulai);
        tglSelesai = findViewById(R.id.tv_detail_investor_tgl_selesai);
        kebutuhanBiaya = findViewById(R.id.tv_detail_investor_kebutuhan_biaya);
        totalBiaya = findViewById(R.id.tv_detail_investor_total_biaya);
        namaPemilik = findViewById(R.id.tv_detail_investor_nama_pemilik);
        perusahaanInvestasi = findViewById(R.id.tv_detail_investor_perusahaan);
        imgProfile = findViewById(R.id.tv_detail_investor_profile_investasi);
        sisaBiaya = findViewById(R.id.tv_detail_investor_sisa_biaya);

        final String id_investasi = getIntent().getStringExtra(EXTRA_ID_INVESTASI);
        String nama_investasi = getIntent().getStringExtra(EXTRA_NAMA_INVESTASI);
        String deskripsi = getIntent().getStringExtra(EXTRA_DESKRIPSI);
        String tgl_mulai = getIntent().getStringExtra(EXTRA_TGL_MULAI);
        String tgl_selesai = getIntent().getStringExtra(EXTRA_TGL_SELESAI);
        final String kebutuhan_biaya = getIntent().getStringExtra(EXTRA_KEBUTUHAN_BIAYA);
        String total_biaya = getIntent().getStringExtra(EXTRA_TOTAL_BIAYA);
        String nama = getIntent().getStringExtra(EXTRA_NAMA);
        String perusahaan = getIntent().getStringExtra(EXTRA_PERUSAHAAN);
        String foto = getIntent().getStringExtra(EXTRA_FOTO);
        String sisa = getIntent().getStringExtra(EXTRA_SISA);

        namaInvestasi.setText(nama_investasi);
        descInvestasi.setText(deskripsi);
        tglMulai.setText(tgl_mulai);
        tglSelesai.setText(tgl_selesai);
        kebutuhanBiaya.setText(kebutuhan_biaya);
        totalBiaya.setText(total_biaya);
        namaPemilik.setText(nama);
        perusahaanInvestasi.setText(perusahaan);
        sisaBiaya.setText(sisa);

        String img_url = "http://syirkah.solution.dipointer.com/img/"+foto;
        Glide.with(this).load(img_url).into(imgProfile);

        //viewAdapter = new InvestorAdapter(this,mItems);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setAdapter(viewAdapter);

        loadDataInvestor();

    }

    private void loadDataInvestor() {

        ApiService api = ApiEndPoint.getClient().create(ApiService.class);
        Call<ResponseModel> call = api.getAllInvestor();
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String statusCode = response.body().getStatusCode();
                progress.setVisibility(View.GONE);
                if (statusCode.equals("200")) {
                    mItems = response.body().getResult_investor();
                    viewAdapter = new InvestorAdapter(DetailInvestorActivity.this, mItems);
                    viewAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(viewAdapter);
                    viewAdapter.notifyDataSetChanged();
                }else if(statusCode.equals("404")){
                    Toast.makeText(DetailInvestorActivity.this, "Oops, Data masih kosong!! ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(DetailInvestorActivity.this, "Oops, Tidak Ada Koneksi Internet!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
