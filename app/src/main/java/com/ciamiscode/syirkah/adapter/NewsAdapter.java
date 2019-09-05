package com.ciamiscode.syirkah.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ciamiscode.syirkah.NewsActivity;
import com.ciamiscode.syirkah.R;
import com.ciamiscode.syirkah.model.NewsModel;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyHolder>{

    Context ctx;
    List<NewsModel> mList;

    public NewsAdapter(Context ctx, List<NewsModel> mList) {
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public NewsAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
        MyHolder holder = new MyHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder,final int position) {
        final NewsModel nm = mList.get(position);
        holder.txtTitle.setText(nm.getTitle());

        if (nm.getDescription().length() >= 0){
            if (nm.getDescription().length()>100){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.txtDescription.setText(Html.fromHtml(nm.getDescription().substring(0,100)+"...", Html.FROM_HTML_MODE_COMPACT));
                } else {
                    holder.txtDescription.setText(Html.fromHtml(nm.getDescription()));
                }
            }else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.txtDescription.setText(Html.fromHtml(nm.getDescription(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    holder.txtDescription.setText(Html.fromHtml(nm.getDescription()));
                }
            }
        }

        String img_url = "https://syirkah-web.solution.dipointer.com/src/news/"+nm.getBanner();
        Glide.with(ctx)
                .load(img_url)
                .into(holder.imgNews);
        holder.btnSelengkapnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx,NewsActivity.class);
                i.putExtra(NewsActivity.EXTRA_TITLE,nm.getTitle());
                i.putExtra(NewsActivity.EXTRA_DESCRIPTION,nm.getDescription());
                i.putExtra(NewsActivity.EXTRA_CREATED_AT,nm.getCreated_at());
                i.putExtra(NewsActivity.EXTRA_BANNER,nm.getBanner());
                ctx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView imgNews;
        TextView txtTitle;
        TextView txtDescription;
        Button btnSelengkapnya;

        public MyHolder(View v) {
            super(v);

            imgNews = v.findViewById(R.id.img_news);
            txtTitle = v.findViewById(R.id.tv_title_news);
            txtDescription = v.findViewById(R.id.tv_desc_news);
            btnSelengkapnya = v.findViewById(R.id.btn_selengkapnya);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ctx,NewsActivity.class);
                    i.putExtra(NewsActivity.EXTRA_TITLE,mList.get(getAdapterPosition()).getTitle());
                    i.putExtra(NewsActivity.EXTRA_DESCRIPTION,mList.get(getAdapterPosition()).getDescription());
                    i.putExtra(NewsActivity.EXTRA_CREATED_AT,mList.get(getAdapterPosition()).getCreated_at());
                    i.putExtra(NewsActivity.EXTRA_BANNER,mList.get(getAdapterPosition()).getBanner());
                    ctx.startActivity(i);
                }
            });
        }
    }
}
