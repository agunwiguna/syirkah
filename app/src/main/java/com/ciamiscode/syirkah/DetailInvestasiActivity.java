package com.ciamiscode.syirkah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailInvestasiActivity extends AppCompatActivity {

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
    TextView sisaInvestasi;

    ImageView imgProfile;

    Button btnInvestasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_investasi);

        namaInvestasi = findViewById(R.id.tv_detail_nama_investasi);
        descInvestasi = findViewById(R.id.tv_detail_deskripsi_investasi);
        tglMulai = findViewById(R.id.tv_detail_tgl_mulai);
        tglSelesai = findViewById(R.id.tv_detail_tgl_selesai);
        kebutuhanBiaya = findViewById(R.id.tv_detail_kebutuhan_biaya);
        totalBiaya = findViewById(R.id.tv_detail_total_biaya);
        namaPemilik = findViewById(R.id.tv_detail_nama_pemilik);
        perusahaanInvestasi = findViewById(R.id.tv_detail_perusahaan);
        imgProfile = findViewById(R.id.tv_detail_profile_investasi);
        btnInvestasi = findViewById(R.id.btnInvestasiNow);
        sisaInvestasi = findViewById(R.id.tv_detail_sisa_biaya);

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
        final String sisa = getIntent().getStringExtra(EXTRA_SISA);

        namaInvestasi.setText(nama_investasi);
        descInvestasi.setText(deskripsi);
        tglMulai.setText(tgl_mulai);
        tglSelesai.setText(tgl_selesai);
        kebutuhanBiaya.setText(kebutuhan_biaya);
        totalBiaya.setText(total_biaya);
        namaPemilik.setText(nama);
        perusahaanInvestasi.setText(perusahaan);
        sisaInvestasi.setText(sisa);

        String img_url = "http://syirkah.solution.dipointer.com/img/"+foto;
        Glide.with(this).load(img_url).into(imgProfile);

        btnInvestasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailInvestasiActivity.this,InvestorActivity.class);
                i.putExtra(InvestorActivity.EXTRA_ID_INVESTASI,id_investasi);
                i.putExtra(InvestorActivity.EXTRA_SISA,sisa);
                startActivity(i);
            }
        });
    }

}
