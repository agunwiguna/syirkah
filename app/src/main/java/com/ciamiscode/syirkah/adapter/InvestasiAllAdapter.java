package com.ciamiscode.syirkah.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ciamiscode.syirkah.DetailInvestasiActivity;
import com.ciamiscode.syirkah.R;
import com.ciamiscode.syirkah.model.InvestasiModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class InvestasiAllAdapter extends RecyclerView.Adapter<InvestasiAllAdapter.MyHolder> {

    Context ctx;
    List<InvestasiModel> mList;

    public InvestasiAllAdapter(Context ctx, List<InvestasiModel> mList) {
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_investasi,parent,false);
        MyHolder holder = new MyHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final InvestasiModel im = mList.get(position);
        holder.nama_investasi.setText(im.getNama_investasi());

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        holder.kebutuhan_biaya.setText(formatRupiah.format((double)Integer.valueOf(im.getKebutuhan_biaya())));
        holder.cvAllInvestasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, DetailInvestasiActivity.class);
                i.putExtra(DetailInvestasiActivity.EXTRA_ID_INVESTASI,im.getId_investasi());
                i.putExtra(DetailInvestasiActivity.EXTRA_NAMA_INVESTASI,im.getNama_investasi());
                i.putExtra(DetailInvestasiActivity.EXTRA_DESKRIPSI,im.getDeskripsi());
                i.putExtra(DetailInvestasiActivity.EXTRA_TGL_MULAI,im.getTgl_mulai());
                i.putExtra(DetailInvestasiActivity.EXTRA_TGL_SELESAI,im.getTgl_selesai());
                i.putExtra(DetailInvestasiActivity.EXTRA_KEBUTUHAN_BIAYA,im.getKebutuhan_biaya());
                i.putExtra(DetailInvestasiActivity.EXTRA_TOTAL_BIAYA,im.getTotal_biaya());
                i.putExtra(DetailInvestasiActivity.EXTRA_NAMA,im.getNama());
                i.putExtra(DetailInvestasiActivity.EXTRA_PERUSAHAAN,im.getPerusahaan());
                i.putExtra(DetailInvestasiActivity.EXTRA_FOTO,im.getFoto());
                i.putExtra(DetailInvestasiActivity.EXTRA_SISA,im.getSisa());
                i.putExtra(DetailInvestasiActivity.EXTRA_TELPON,im.getTelpon());
                ctx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView nama_investasi;
        TextView kebutuhan_biaya;
        CardView cvAllInvestasi;

        public MyHolder(View v) {
            super(v);

            nama_investasi = v.findViewById(R.id.tv_all_nama_investasi);
            kebutuhan_biaya = v.findViewById(R.id.tv_all_kebutuhan_investasi);
            cvAllInvestasi = v.findViewById(R.id.cv_all_investasi);

        }
    }
}
