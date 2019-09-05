package com.ciamiscode.syirkah.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ciamiscode.syirkah.R;
import com.ciamiscode.syirkah.fragment.DialogMemberFragment;
import com.ciamiscode.syirkah.model.UserModel;

import org.w3c.dom.Text;

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
    public void onBindViewHolder(MyHolder holder,final int position) {
        final UserModel um = mList.get(position);
        holder.nama.setText(um.getNama());
        holder.alamat.setText(um.getAlamat());

        String img_url = "http://syirkah.solution.dipointer.com/img/"+um.getFoto();

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_default_profile)
                .error(R.drawable.ic_default_profile)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(ctx).load(img_url)
                .apply(options)
                .into(holder.foto);

        holder.waMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = um.getTelpon();
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

        TextView nama;
        TextView alamat;
        ImageView foto;
        LinearLayout waMember;

        public MyHolder(View v) {
            super(v);

            nama = v.findViewById(R.id.tv_nama_member);
            alamat = v.findViewById(R.id.tv_alamat_member);
            foto = v.findViewById(R.id.imgMember);
            waMember = v.findViewById(R.id.wa_member);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    @SuppressLint("InflateParams") View modelBottomSheet = LayoutInflater.from(ctx).inflate(R.layout.dialog_member, null);

                    TextView dialogNama = modelBottomSheet.findViewById(R.id.dialog_nama_member);
                    TextView dialogPerusahaan = modelBottomSheet.findViewById(R.id.dialog_perusahaan_member);
                    TextView dialogAlamat = modelBottomSheet.findViewById(R.id.dialog_alamat_member);
                    TextView dialogEmas = modelBottomSheet.findViewById(R.id.dialog_emas_user);
                    TextView dialogPerak = modelBottomSheet.findViewById(R.id.dialog_perak_user);
                    ImageView dialogFoto = modelBottomSheet.findViewById(R.id.dialog_foto_member);
                    LinearLayout wa = modelBottomSheet.findViewById(R.id.dialog_wa_member);

                    dialogNama.setText(mList.get(getAdapterPosition()).getNama());
                    dialogPerusahaan.setText(mList.get(getAdapterPosition()).getPerusahaan());
                    dialogAlamat.setText(mList.get(getAdapterPosition()).getAlamat());
                    dialogEmas.setText(mList.get(getAdapterPosition()).getEmas());
                    dialogPerak.setText(mList.get(getAdapterPosition()).getPerak());

                    String img_url = "http://syirkah.solution.dipointer.com/img/"+mList.get(getAdapterPosition()).getFoto();
                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.ic_default_profile)
                            .error(R.drawable.ic_default_profile)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .priority(Priority.HIGH);

                    Glide.with(ctx).load(img_url)
                            .apply(options)
                            .into(dialogFoto);

                    wa.setOnClickListener(new View.OnClickListener() {
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
