package com.ciamiscode.syirkah.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ciamiscode.syirkah.AddProjectActivity;
import com.ciamiscode.syirkah.DetailInvestorActivity;
import com.ciamiscode.syirkah.InvestasiActivity;
import com.ciamiscode.syirkah.R;
import com.ciamiscode.syirkah.UbahInvestasiActivity;
import com.ciamiscode.syirkah.api.ApiEndPoint;
import com.ciamiscode.syirkah.api.ApiService;
import com.ciamiscode.syirkah.model.InvestasiModel;
import com.ciamiscode.syirkah.model.ResponseModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvestasiAdapter extends RecyclerView.Adapter<InvestasiAdapter.MyHolder> {

    Context ctx;
    List<InvestasiModel> mList;
    private ProgressDialog progress;
    private List<InvestasiModel> mItems = new ArrayList<>();

    public InvestasiAdapter(Context ctx, List<InvestasiModel> mList) {
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_investasi,parent,false);
        MyHolder holder = new MyHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        final InvestasiModel im = mList.get(position);
        holder.nama_investasi.setText(im.getNama_investasi());

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        holder.kebutuhan_biaya.setText(formatRupiah.format((double)Integer.valueOf(im.getKebutuhan_biaya())));
        holder.updateInvestasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, DetailInvestorActivity.class);
                i.putExtra(DetailInvestorActivity.EXTRA_ID_INVESTASI,im.getId_investasi());
                i.putExtra(DetailInvestorActivity.EXTRA_NAMA_INVESTASI,im.getNama_investasi());
                i.putExtra(DetailInvestorActivity.EXTRA_DESKRIPSI,im.getDeskripsi());
                i.putExtra(DetailInvestorActivity.EXTRA_TGL_MULAI,im.getTgl_mulai());
                i.putExtra(DetailInvestorActivity.EXTRA_TGL_SELESAI,im.getTgl_selesai());
                i.putExtra(DetailInvestorActivity.EXTRA_KEBUTUHAN_BIAYA,im.getKebutuhan_biaya());
                i.putExtra(DetailInvestorActivity.EXTRA_TOTAL_BIAYA,im.getTotal_biaya());
                i.putExtra(DetailInvestorActivity.EXTRA_NAMA,im.getNama());
                i.putExtra(DetailInvestorActivity.EXTRA_PERUSAHAAN,im.getPerusahaan());
                i.putExtra(DetailInvestorActivity.EXTRA_FOTO,im.getFoto());
                i.putExtra(DetailInvestorActivity.EXTRA_SISA,im.getSisa());
                ctx.startActivity(i);
            }
        });

        holder.dotmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(ctx,holder.dotmenu );
                popup.inflate(R.menu.menu_crud_card);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){

                            case R.id.menu_ubah:

                                Intent i = new Intent(ctx, UbahInvestasiActivity.class);
                                i.putExtra(UbahInvestasiActivity.EXTRA_ID_INVESTASI,im.getId_investasi());
                                i.putExtra(UbahInvestasiActivity.EXTRA_NAMA_INVESTASI,im.getNama_investasi());
                                i.putExtra(UbahInvestasiActivity.EXTRA_DESKRIPSI,im.getDeskripsi());
                                i.putExtra(UbahInvestasiActivity.EXTRA_TGL_MULAI,im.getTgl_mulai());
                                i.putExtra(UbahInvestasiActivity.EXTRA_TGL_SELESAI,im.getTgl_selesai());
                                i.putExtra(UbahInvestasiActivity.EXTRA_KEBUTUHAN_BIAYA,im.getKebutuhan_biaya());
                                i.putExtra(UbahInvestasiActivity.EXTRA_TOTAL_BIAYA,im.getTotal_biaya());
                                ctx.startActivity(i);
                                ((Activity)ctx).finish();

                                return true;
                            case R.id.menu_hapus:

                                progress = new ProgressDialog(ctx);
                                progress.setCancelable(false);
                                progress.setMessage("Harap Tunggu ...");
                                progress.show();

                                ApiService api = ApiEndPoint.getClient().create(ApiService.class);
                                Call<ResponseModel> delete = api.deleteInvestasi(im.getId_investasi());
                                delete.enqueue(new Callback<ResponseModel>() {
                                    @Override
                                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                        String statusCode = response.body().getStatusCode();
                                        String message = response.body().getMessage();

                                        progress.dismiss();

                                        if (statusCode.equals("200")) {
                                            Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
                                            ctx.startActivity(new Intent(ctx,InvestasiActivity.class));
                                            ((Activity)ctx).finish();
                                        } else if (statusCode.equals("404")) {
                                            Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
                                        } else if (statusCode.equals("500")) {
                                            Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                                        progress.dismiss();
                                        Toast.makeText(ctx, "Oops, Tidak Ada Koneksi Internet!! ", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return true;
                        }
                        return false;
                    }
                });
                popup.show();
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
        CardView updateInvestasi;
        ImageView dotmenu;

        public MyHolder(View v) {
            super(v);

            nama_investasi = v.findViewById(R.id.tv_nama_investasi);
            kebutuhan_biaya = v.findViewById(R.id.tv_kebutuhan_investasi);
            updateInvestasi = v.findViewById(R.id.cv_ubah_investasi);
            dotmenu = v.findViewById(R.id.dot_menu);

        }
    }
}
