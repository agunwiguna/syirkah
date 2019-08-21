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

import com.ciamiscode.syirkah.DetailInvestasiActivity;
import com.ciamiscode.syirkah.R;

public class ProjectFragment extends Fragment {

    CardView detInvestasi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project,null);

        detInvestasi = view.findViewById(R.id.cv_det_investasi);
        detInvestasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), DetailInvestasiActivity.class);
                startActivity(i);
            }
        });

        return view;
    }
}
