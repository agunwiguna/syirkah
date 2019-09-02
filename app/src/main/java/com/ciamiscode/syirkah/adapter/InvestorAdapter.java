package com.ciamiscode.syirkah.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ciamiscode.syirkah.R;
import com.ciamiscode.syirkah.model.InvestorModel;

import java.util.List;

public class InvestorAdapter extends RecyclerView.Adapter<InvestorAdapter.MyHolder>{

    Context ctx;
    List<InvestorModel> mList;

    public InvestorAdapter(Context ctx, List<InvestorModel> mList) {
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public InvestorAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_investor,parent,false);
        MyHolder holder = new MyHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder,final int position) {
        final InvestorModel im = mList.get(position);
        holder.nama.setText(im.getNama());
        holder.perusahaan.setText(im.getPerusahaan());
        holder.dana.setText(im.getDana());
        String img_url = "http://syirkah.solution.dipointer.com/img/"+im.getFoto();
        Glide.with(holder.itemView.getContext())
                .load(img_url)
                .into(holder.foto);
        holder.wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = im.getTelpon();
                String formatNumber = phoneNumber.replaceFirst("0","+62");
                String url = "https://api.whatsapp.com/send?phone="+formatNumber;
                Intent dialphone = new Intent(Intent.ACTION_VIEW);
                dialphone.setData(Uri.parse(url));
                ctx.startActivity(dialphone);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView foto;
        TextView nama,perusahaan,dana;
        Button wa;

        public MyHolder(View v) {
            super(v);
            nama = v.findViewById(R.id.tv_nama_investor);
            perusahaan = v.findViewById(R.id.tv_perusahaan_investor);
            dana = v.findViewById(R.id.tv_dana_investor);
            foto = v.findViewById(R.id.imgInvestor);
            wa = v.findViewById(R.id.btn_wa_investor);
        }
    }
}
