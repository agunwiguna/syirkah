package com.ciamiscode.syirkah.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ciamiscode.syirkah.R;
import com.ciamiscode.syirkah.utils.SharedPrefManager;

public class HomeFragment extends Fragment {

    private TextView tv_nama;

    SharedPrefManager sharedPrefManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,null);

        sharedPrefManager = new SharedPrefManager(getContext());

        tv_nama = view.findViewById(R.id.nama_user);
        tv_nama.setText(sharedPrefManager.getSPNama());

        return view;
    }
}
