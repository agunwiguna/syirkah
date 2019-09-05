package com.ciamiscode.syirkah.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ciamiscode.syirkah.R;
import com.ciamiscode.syirkah.model.LogamModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class LogamAllAdapter extends RecyclerView.Adapter<LogamAllAdapter.MyHolder> {

    Context ctx;
    List<LogamModel> mList;

    public LogamAllAdapter(Context ctx, List<LogamModel> mList) {
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public LogamAllAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_logam,parent,false);
        MyHolder holder = new MyHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder,final int position) {
        final LogamModel lm = mList.get(position);
        holder.txtJenis.setText(lm.getJenis());
        holder.txtBerat.setText(lm.getBerat());
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        holder.txtHargaJual.setText(formatRupiah.format((double)Integer.valueOf(lm.getHarga_jual())));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView txtJenis, txtBerat, txtHargaJual;

        public MyHolder(View v) {
            super(v);

            txtJenis = v.findViewById(R.id.tv_jenis_all);
            txtBerat = v.findViewById(R.id.tv_gram_all);
            txtHargaJual = v.findViewById(R.id.tv_harga_jual_all);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    @SuppressLint("InflateParams") View modelBottomSheet = LayoutInflater.from(ctx).inflate(R.layout.dialog_buy, null);

                    TextView dialogJenis = modelBottomSheet.findViewById(R.id.dialog_jenis_logam);
                    TextView dialogBerat = modelBottomSheet.findViewById(R.id.dialog_berat_logam);
                    TextView hargaBeli = modelBottomSheet.findViewById(R.id.dialog_harga_beli_logam);
                    TextView hargaJual = modelBottomSheet.findViewById(R.id.dialog_harga_jual_logam);
                    TextView tahunProduksi = modelBottomSheet.findViewById(R.id.dialog_tahun_produksi_logam);
                    TextView penjualLogam = modelBottomSheet.findViewById(R.id.dialog_pemilik_logam);
                    LinearLayout callPemilik = modelBottomSheet.findViewById(R.id.call_penjual_logam);

                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

                    dialogJenis.setText(mList.get(getAdapterPosition()).getJenis());
                    dialogBerat.setText(mList.get(getAdapterPosition()).getBerat());
                    hargaBeli.setText(formatRupiah.format((double)Integer.valueOf(mList.get(getAdapterPosition()).getHarga_beli())));
                    hargaJual.setText(formatRupiah.format((double)Integer.valueOf(mList.get(getAdapterPosition()).getHarga_jual())));
                    tahunProduksi.setText(mList.get(getAdapterPosition()).getTahun_produksi());
                    penjualLogam.setText(mList.get(getAdapterPosition()).getNama());

                    callPemilik.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String phoneNumber = mList.get(getAdapterPosition()).getTelpon();
                            String formatNumber = phoneNumber.replaceFirst("0","+62");
                            String url = "https://api.whatsapp.com/send?phone="+formatNumber;
                            Intent dialphone = new Intent(Intent.ACTION_VIEW);
                            dialphone.setData(Uri.parse(url));
                            ctx.startActivity(dialphone);
                        }
                    });

                    BottomSheetDialog dialog = new BottomSheetDialog(ctx, R.style.AppBottomSheetDialogTheme);
                    dialog.setContentView(modelBottomSheet);
                    dialog.show();
                }
            });
        }
    }
}
