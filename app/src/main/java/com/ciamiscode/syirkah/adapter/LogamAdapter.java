package com.ciamiscode.syirkah.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ciamiscode.syirkah.InvestasiActivity;
import com.ciamiscode.syirkah.R;
import com.ciamiscode.syirkah.SellActivity;
import com.ciamiscode.syirkah.UbahLogamActivity;
import com.ciamiscode.syirkah.api.ApiEndPoint;
import com.ciamiscode.syirkah.api.ApiService;
import com.ciamiscode.syirkah.model.LogamModel;
import com.ciamiscode.syirkah.model.ResponseModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogamAdapter extends RecyclerView.Adapter<LogamAdapter.MyHolder>{

    Context ctx;
    List<LogamModel> mList;
    private ProgressDialog progress;

    public LogamAdapter(Context ctx, List<LogamModel> mList) {
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public LogamAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_logam,parent,false);
        MyHolder holder = new MyHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        final LogamModel lm = mList.get(position);
        holder.txtJenis.setText(lm.getJenis());
        holder.txtBerat.setText(lm.getBerat());

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        holder.txtHargaJual.setText(formatRupiah.format((double)Integer.valueOf(lm.getHarga_jual())));
        holder.dotMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(ctx,holder.dotMenu );
                popup.inflate(R.menu.menu_crud_card);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()){
                            case R.id.menu_ubah:
                                Intent i = new Intent(ctx, UbahLogamActivity.class);
                                i.putExtra(UbahLogamActivity.EXTRA_ID_LOGAM,lm.getId_logam());
                                i.putExtra(UbahLogamActivity.EXTRA_BERAT,lm.getBerat());
                                i.putExtra(UbahLogamActivity.EXTRA_TAHUN_PRODUKSI,lm.getTahun_produksi());
                                i.putExtra(UbahLogamActivity.EXTRA_HARGA_BELI,lm.getHarga_beli());
                                i.putExtra(UbahLogamActivity.EXTRA_HARGA_JUAL,lm.getHarga_jual());
                                i.putExtra(UbahLogamActivity.EXTRA_JENIS,lm.getJenis());
                                ctx.startActivity(i);
                                ((Activity)ctx).finish();
                                return true;
                            case R.id.menu_hapus:

                                progress = new ProgressDialog(ctx);
                                progress.setCancelable(false);
                                progress.setMessage("Menghapus ...");
                                progress.show();

                                ApiService api = ApiEndPoint.getClient().create(ApiService.class);
                                Call<ResponseModel> delete = api.deleteLogam(lm.getId_logam());
                                delete.enqueue(new Callback<ResponseModel>() {
                                    @Override
                                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                        String statusCode = response.body().getStatusCode();
                                        String message = response.body().getMessage();

                                        progress.dismiss();

                                        if (statusCode.equals("200")) {
                                            Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
                                            ctx.startActivity(new Intent(ctx, SellActivity.class));
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

        TextView txtJenis, txtBerat, txtHargaJual;
        ImageView dotMenu;

        public MyHolder(View v) {
            super(v);

            txtJenis = v.findViewById(R.id.tv_jenis_user);
            txtBerat = v.findViewById(R.id.tv_gram_user);
            txtHargaJual = v.findViewById(R.id.tv_harga_jual_user);
            dotMenu = v.findViewById(R.id.dot_menu_logam);
        }
    }
}
