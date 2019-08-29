package com.ciamiscode.syirkah.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ciamiscode.syirkah.InvestasiActivity;
import com.ciamiscode.syirkah.R;
import com.ciamiscode.syirkah.SellActivity;
import com.ciamiscode.syirkah.SettingActivity;
import com.ciamiscode.syirkah.utils.SharedPrefManager;

public class ProfileFragment extends Fragment {

    CardView cv_setting,cv_sell,cv_investasi;
    private TextView namaProfile;
    private TextView namaPerushaan;
    private TextView emas;
    private ImageView foto;

    int img = R.drawable.ic_logo_syirkah;

    SharedPrefManager sharedPrefManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,null);

        cv_setting = view.findViewById(R.id.cv_setting);
        cv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), SettingActivity.class);
                startActivity(i);
            }
        });

        cv_sell = view.findViewById(R.id.cv_sell);
        cv_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), SellActivity.class);
                startActivity(i);
            }
        });

        cv_investasi = view.findViewById(R.id.cv_investasi);
        cv_investasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), InvestasiActivity.class);
                startActivity(i);
            }
        });

        sharedPrefManager = new SharedPrefManager(getContext());

        foto = view.findViewById(R.id.img_profile);
        String img_url = "http://syirkah.solution.dipointer.com/img/"+sharedPrefManager.getSpFoto();

        Glide.with(getContext())
                .load(img_url)
                .into(foto);

        namaProfile = view.findViewById(R.id.tv_nama_profile);
        namaProfile.setText(sharedPrefManager.getSPNama());

        namaPerushaan = view.findViewById(R.id.tv_nama_perusahaan);
        namaPerushaan.setText(sharedPrefManager.getSpPerusahaan());

        emas = view.findViewById(R.id.tv_emas_profile);
        emas.setText(sharedPrefManager.getIdUser());

        return view;
    }
}
