package com.ciamiscode.syirkah.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ciamiscode.syirkah.R;
import com.ciamiscode.syirkah.model.UserModel;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MyHolder> {

    Context ctx;
    List<UserModel> mList;

    public MemberAdapter(Context ctx, List<UserModel> mList) {
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member,parent,false);
        MyHolder holder = new MyHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final UserModel um = mList.get(position);
        holder.nama.setText(um.getNama());
        holder.alamat.setText(um.getAlamat());

        String img_url = "http://syirkah.solution.dipointer.com/img/"+um.getFoto();
        Glide.with(holder.itemView.getContext())
                .load(img_url)
                .into(holder.foto);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView nama;
        TextView alamat;
        ImageView foto;

        public MyHolder(View v) {
            super(v);

            nama = v.findViewById(R.id.tv_nama_member);
            alamat = v.findViewById(R.id.tv_alamat_member);
            foto = v.findViewById(R.id.imgMember);
        }
    }
}
